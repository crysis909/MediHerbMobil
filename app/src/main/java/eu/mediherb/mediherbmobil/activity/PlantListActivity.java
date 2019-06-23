package eu.mediherb.mediherbmobil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.adapter.HerbViewAdapter;
import eu.mediherb.mediherbmobil.classes.custom.BugReport;
import eu.mediherb.mediherbmobil.classes.api.MediHerbService;
import eu.mediherb.mediherbmobil.classes.api.RetrofitClient;
import eu.mediherb.mediherbmobil.classes.gson.Herb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantListActivity extends AppCompatActivity {
    private HerbViewAdapter adapter;
    private RecyclerView herbView;
    private List<Herb> herbList;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int pos = viewHolder.getAdapterPosition();

            Log.i("MediHerb", String.valueOf(herbList.size()));

            Herb herb = herbList.get(pos);
            Gson gson = new Gson();
            Intent intent = new Intent(PlantListActivity.this, PlantDetailActivity.class);
            intent.putExtra("Herb", gson.toJson(herb));

            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        String json = null;
        Intent intent = getIntent();

        if (intent.hasExtra("Herb"))
            json = intent.getStringExtra("Herb");


        if(json == null || json.equals("null"))
            loadJSON();
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Herb>>(){}.getType();
            herbList =  gson.fromJson(json, listType);
            loadDataList(herbList);
        }
    }


    private void loadDataList(final List<Herb> herbList) {
        herbView = findViewById(R.id.herbView);
        adapter = new HerbViewAdapter(herbList);

        herbView.setLayoutManager(new LinearLayoutManager(this));
        herbView.setAdapter(adapter);
        adapter.setOnItemClickListener(listener);
    }

    private void loadJSON() {
        try {
            MediHerbService service = RetrofitClient.getRetrofit().create(MediHerbService.class);

            Call<List<Herb>> call = service.getAllHerbs();

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(PlantListActivity.this);
            progressDialog.setMessage("Lade....");
            progressDialog.setTitle("Kräuterliste");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();

            call.enqueue(new Callback<List<Herb>>() {
                @Override
                public void onResponse(Call<List<Herb>> call, Response<List<Herb>> response) {
                    herbList = response.body();
                    progressDialog.dismiss();
                    loadDataList(herbList);
                }

                @Override
                public void onFailure(Call<List<Herb>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(PlantListActivity.this, "Unable to load herbs\nCheck your internet connection", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            BugReport report = new BugReport(this, "Message:\n\n" + e.getMessage() + "\nStackTrace:\n\n" + Arrays.toString(e.getStackTrace()));
            report.sendToast();
            report.sendEmail();
        }
    }
}
