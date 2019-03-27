package eu.mediherb.mediherbmobil.classes.api;

import java.util.List;

import eu.mediherb.mediherbmobil.classes.gson.Herb;
import eu.mediherb.mediherbmobil.classes.gson.Symptom;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MediHerbService {

    @GET("herb")
    Call<List<Herb>> getAllHerbs();

    @GET("symptom")
    Call<List<Symptom>> getAllSymptoms();
}
