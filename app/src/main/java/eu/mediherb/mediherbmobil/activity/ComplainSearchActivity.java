package eu.mediherb.mediherbmobil.activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.adapter.SymptomSpinnerAdapter;
import eu.mediherb.mediherbmobil.classes.custom.BugReport;
import eu.mediherb.mediherbmobil.classes.api.MediHerbService;
import eu.mediherb.mediherbmobil.classes.api.RetrofitClient;
import eu.mediherb.mediherbmobil.classes.gson.Herb;
import eu.mediherb.mediherbmobil.classes.gson.Symptom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplainSearchActivity extends AppCompatActivity {
    private List<Symptom> symptomList;
    private FloatingActionButton fab;
    private Spinner dropDown;
    private SymptomSpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_search);

        dropDown = findViewById(R.id.complainDropDown);
        fab = findViewById(R.id.fabPhoto);

        loadSympJSON();
    }

    private void loadDataList(final List<Symptom> symptomList){

        adapter = new SymptomSpinnerAdapter(this,
                android.R.layout.simple_spinner_item, symptomList.toArray(new Symptom[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(adapter);
        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                final Symptom symptom = adapter.getItem(position);

                fab.setOnClickListener(v -> {
                    try {
                        assert symptom != null;
                        loadHerbJSON(symptom.getSymptomID());
                    }
                    catch (AssertionError e){
                        Toast.makeText(ComplainSearchActivity.this, "Haben Sie ein Symptom ausgewählt?", Toast.LENGTH_LONG).show();
                    }
                });
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            };
        });
    }

    private void loadSympJSON(){
        try {
            MediHerbService service = RetrofitClient.getRetrofit().create(MediHerbService.class);

            Call<List<Symptom>> call = service.getAllSymptoms();

            call.enqueue(new Callback<List<Symptom>>() {
                @Override
                public void onResponse(@NotNull Call<List<Symptom>> call, @NotNull Response<List<Symptom>> response) {
                    assert response.body() != null;
                    symptomList = response.body();
                    loadDataList(symptomList);
                }

                @Override
                public void onFailure(@NotNull Call<List<Symptom>> call, @NotNull Throwable t) {
                    Toast.makeText(ComplainSearchActivity.this, "Unable to load herbs\nCheck your internet connection", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e)
        {
            BugReport report = new BugReport(this, "Message:\n" + e.getMessage() + "\n\nStackTrace:\n" + Arrays.toString(e.getStackTrace()));
            report.sendToast();
            report.sendEmail();
        }
    }

    private void loadHerbJSON(final int sympID) {
        try {
            MediHerbService service = RetrofitClient.getRetrofit().create(MediHerbService.class);

            Call<List<Herb>> call = service.getHerbsBySymptom(sympID);

            call.enqueue(new Callback<List<Herb>>() {
                @Override
                public void onResponse(@NotNull Call<List<Herb>> call, @NotNull Response<List<Herb>> response) {
                    Gson gson = new Gson();
                    List<Herb> herbList = response.body();

                    Intent intent = new Intent(ComplainSearchActivity.this, PlantListActivity.class);
                    intent.putExtra("Herb", gson.toJson(herbList));

                    startActivity(intent);
                }

                @Override
                public void onFailure(@NotNull Call<List<Herb>> call, @NotNull Throwable t) {
                    Toast.makeText(ComplainSearchActivity.this, "Unable to load herbs\nCheck your internet connection\n", Toast.LENGTH_LONG).show();
                    Log.e("MediHerb", t.getMessage());
                }
            });
        } catch (Exception e) {
            BugReport report = new BugReport(this, "Message:\n\n" + e.getMessage() + "\nStackTrace:\n\n" + Arrays.toString(e.getStackTrace()));
            report.sendToast();
            report.sendEmail();
        }
    }
}
