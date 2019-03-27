package eu.mediherb.mediherbmobil.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.adapter.HerbViewAdapter;
import eu.mediherb.mediherbmobil.classes.api.MediHerbService;
import eu.mediherb.mediherbmobil.classes.api.RetrofitClient;
import eu.mediherb.mediherbmobil.classes.gson.Herb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantListActivity extends AppCompatActivity {

    private HerbViewAdapter adapter;
    private RecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        MediHerbService service = RetrofitClient.getRetrofit().create(MediHerbService.class);

        Call<List<Herb>> call = service.getAllHerbs();
        call.enqueue(new Callback<List<Herb>>() {
            @Override
            public void onResponse(Call<List<Herb>> call, Response<List<Herb>> response) {
                loadDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Herb>> call, Throwable t) {
                Toast.makeText(PlantListActivity.this, "Unable to load herbs", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadDataList(List<Herb> herbList){
        view = findViewById(R.id.herbView);
        adapter = new HerbViewAdapter(herbList);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = view.indexOfChild(v);
                Toast.makeText(PlantListActivity.this, Integer.toString(pos), Toast.LENGTH_LONG).show();

            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(PlantListActivity.this);
        view.setLayoutManager(manager);
        view.setAdapter(adapter);
    }

}
