package eu.mediherb.mediherbmobil.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
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
import com.google.firebase.ml.custom.FirebaseModelOutputs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import eu.mediherb.mediherbmobil.classes.custom.MainRow;
import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.adapter.MainButtonAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private MainButtonAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.mainList);
        ArrayList<MainRow>rows = new ArrayList<>();
        rows.add(new MainRow(R.drawable.ic_photo_camera, R.string.photoTextBig, R.string.photoTextSmall));
        //rows.add(new MainRow(R.drawable.ic_leaf, R.string.plantIdentTextBig, R.string.plantIdentTextSmall));
        rows.add(new MainRow(R.drawable.ic_medidosis, R.string.compainSearchTextBig, R.string.compainSearchTextSmall));
        rows.add(new MainRow(R.drawable.ic_search, R.string.plantsListBig, R.string.plantsListSmall));
        rows.add(new MainRow(R.drawable.ic_info, R.string.aboutBig, R.string.aboutSmall));

        _adapter = new MainButtonAdapter(this, this, rows);
        list.setAdapter(_adapter);
        list.setOnItemClickListener(this);

        FirebaseApp.initializeApp(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, PhotoActivity.class);
                break;
                /*
            case 1:
                intent = new Intent(this, PlantIdentActivity.class);
                break;
                */
            case 1:
                intent = new Intent(this, ComplainSearchActivity.class);
                break;
            case 2:
                intent = new Intent(this, PlantListActivity.class);
                break;
            case 3:
                intent = new Intent(this, AboutActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);
    }


}
