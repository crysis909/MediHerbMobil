package eu.mediherb.mediherbmobil.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelOptions;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.classes.api.MediHerbService;
import eu.mediherb.mediherbmobil.classes.api.RetrofitClient;
import eu.mediherb.mediherbmobil.classes.custom.BugReport;
import eu.mediherb.mediherbmobil.classes.gson.Herb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class PhotoActivity extends AppCompatActivity {
    private static final int GALLERY = 1, CAMERA = 2;

    private static final int DIM_BATCH_SIZE = 1;
    private static final int DIM_PIXEL_SIZE = 3;
    private static final int DIM_IMG_SIZE_X = 224;
    private static final int DIM_IMG_SIZE_Y = 224;
    private static final int FLOAT_NUM_OF_BYTES_PER_CHANNEL = 4;

    private int[] intValues = new int[DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y];
    private Bitmap image;
    private ImageView imageView;
    private Uri imagePath;
    private HashMap<String, Float> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        configureHostedModelSource();
        configureLocalModelSource();

        requestMultiplePermissions();

        FloatingActionButton takePictureFab = findViewById(R.id.fabPhoto);
        imageView = findViewById(R.id.photoImageView);

        takePictureFab.setOnClickListener(photoOnClickListener);
    }

    //region Photo
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Wie soll das Foto ausgewählt werden?");
        String[] pictureDialogItems = {
                "Wählen Sie ein Foto aus Ihrer Gallery",
                "Machen Sie ein Foto" };
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            chooseFromGallery();
                            break;
                        case 1:
                            takePhoto();
                            break;
                    }
                });
        pictureDialog.show();
    }

    private View.OnClickListener photoOnClickListener = v -> {
        showPictureDialog();
    };

    public void chooseFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhoto() {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ignored) {
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(PhotoActivity.this,
                    "eu.mediherb.mediherbmobil.fileprovider", photoFile);

            imagePath = photoURI;
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(photoIntent, CAMERA);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    image = bitmap;
                    imageView.setImageBitmap(bitmap);

                    try {
                        runInference();

                    } catch (FirebaseMLException ignored) {
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(PhotoActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                image = bitmap;
                imageView.setImageBitmap(bitmap);

                try {
                    runInference();

                } catch (FirebaseMLException ignored) {
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(PhotoActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.N)
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(@NotNull Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private void showHerbs(@NotNull List<Herb> herbList)
    {
        List<Herb> finalList = new ArrayList<>();

        for(Herb herb : herbList){
            Float perc = modelList.get(herb.getNameTrival().toLowerCase());

            if(perc != null) {
                String percentage = String.format("%.2f", perc * 100);
                if(!percentage.equals("0,00")) {
                    herb.setPercentage(perc);
                    finalList.add(herb);
                }
            }
        }

        Collections.sort(finalList, Collections.reverseOrder());
        Gson gson = new Gson();

        Intent intent = new Intent(PhotoActivity.this, PlantListActivity.class);
        intent.putExtra("Herb", gson.toJson(finalList));

        startActivity(intent);
    }

    private void loadJSON()
    {
        try {
            MediHerbService service = RetrofitClient.getRetrofit().create(MediHerbService.class);

            Call<List<Herb>> call = service.getAllHerbs();


            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(PhotoActivity.this);
            progressDialog.setMessage("Lade....");
            progressDialog.setTitle("Kräuterliste");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();

            call.enqueue(new Callback<List<Herb>>() {
                @Override
                public void onResponse(@NotNull Call<List<Herb>> call, @NotNull Response<List<Herb>> response) {
                    List<Herb> herbList = response.body();
                    progressDialog.dismiss();
                    showHerbs(herbList);
                }

                @Override
                public void onFailure(@NotNull Call<List<Herb>> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(PhotoActivity.this, "Unable to load herbs\nCheck your internet connection", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            BugReport report = new BugReport(this, "Message:\n\n" + e.getMessage() + "\nStackTrace:\n\n" + Arrays.toString(e.getStackTrace()));
            report.sendToast();
            report.sendEmail();
        }
    }
    //endregion

    //region FireBase
    private void configureHostedModelSource(){
        FirebaseModelDownloadConditions.Builder conditionsBuilder =
                new FirebaseModelDownloadConditions.Builder().requireWifi();

        FirebaseModelDownloadConditions conditions = conditionsBuilder.build();

        FirebaseRemoteModel cloudSource = new FirebaseRemoteModel.Builder("herb-detektor")
                .enableModelUpdates(true)
                .setInitialDownloadConditions(conditions)
                .setUpdatesDownloadConditions(conditions)
                .build();
        FirebaseModelManager.getInstance().downloadRemoteModelIfNeeded(cloudSource);
        FirebaseModelManager.getInstance().registerRemoteModel(cloudSource);
    }

    private void configureLocalModelSource() {
        FirebaseLocalModel localSource =
                new FirebaseLocalModel.Builder("model")
                        .setAssetFilePath("model.lite")
                        .build();
        FirebaseModelManager.getInstance().registerLocalModel(localSource);
    }

    private FirebaseModelInterpreter createInterpreter() throws FirebaseMLException {
        FirebaseModelOptions options = new FirebaseModelOptions.Builder()
                .setRemoteModelName("herb-detektor")
                .setLocalModelName("model")
                .build();

        return FirebaseModelInterpreter.getInstance(options);
    }

    private FirebaseModelInputOutputOptions createInputOutputOptions() throws FirebaseMLException {

        return new FirebaseModelInputOutputOptions.Builder()
                .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 224, 224, 3})
                .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 23})
                .build();
    }

    private ByteBuffer bitmapToInputArray() {
        Bitmap bitmap = image;

        ByteBuffer imgData =
                ByteBuffer.allocateDirect(
                        FLOAT_NUM_OF_BYTES_PER_CHANNEL * DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE);

        imgData.order(ByteOrder.nativeOrder());
        bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
        imgData.rewind();
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),
                bitmap.getHeight());

        int pixel = 0;

        for (int x = 0; x < 224; x++) {
            for (int y = 0; y < 224; y++) {
                final int val = intValues[pixel++];
                imgData.putFloat(((val >> 16) & 0xFF) / 255.0f);
                imgData.putFloat(((val >> 8) & 0xFF) / 255.0f);
                imgData.putFloat((val & 0xFF) / 255.0f);
            }
        }

        return imgData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void runInference() throws FirebaseMLException {
        FirebaseModelInterpreter firebaseInterpreter = createInterpreter();
        ByteBuffer input = bitmapToInputArray();
        FirebaseModelInputOutputOptions inputOutputOptions = createInputOutputOptions();

        FirebaseModelInputs inputs = new FirebaseModelInputs.Builder()
                .add(input)
                .build();
        firebaseInterpreter.run(inputs, inputOutputOptions)
                .addOnSuccessListener(
                        result -> {
                            float[][] output = result.getOutput(0);
                            float[] probabilities = output[0];

                            try {
                                useInferenceResult(probabilities);
                            } catch (IOException ignored) {
                            }
                        })
                .addOnFailureListener(
                        Throwable::printStackTrace);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void useInferenceResult(@NotNull float[] probabilities) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getAssets().open("retrained_labels.txt")));
        modelList = new HashMap<>();
        for (float probability : probabilities) {
            String label = reader.readLine();
            modelList.put(label, probability);
        }
        modelList = (HashMap<String, Float>) sortByValue(modelList);

        loadJSON();
    }
    //endregion

}
