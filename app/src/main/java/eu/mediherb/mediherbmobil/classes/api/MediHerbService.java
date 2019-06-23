package eu.mediherb.mediherbmobil.classes.api;

import java.util.List;

import eu.mediherb.mediherbmobil.classes.gson.Blossom;
import eu.mediherb.mediherbmobil.classes.gson.Botanical;
import eu.mediherb.mediherbmobil.classes.gson.Herb;
import eu.mediherb.mediherbmobil.classes.gson.Leaf;
import eu.mediherb.mediherbmobil.classes.gson.Medical;
import eu.mediherb.mediherbmobil.classes.gson.Pictures;
import eu.mediherb.mediherbmobil.classes.gson.Symptom;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MediHerbService {

    //Basic
    @GET("herb")
    Call<List<Herb>> getAllHerbs();

    @GET("symptom")
    Call<List<Symptom>> getAllSymptoms();

    //SymptomSearch
    @GET("symptom/{herbID}")
    Call<List<Herb>> getHerbsBySymptom(@Path("herbID") int herbID);

    //Details
    @GET("botanical/{herbID}")
    Call<Botanical> getBotanicalInfo(@Path("herbID") int herbID);

    @GET("medical/{herbID}")
    Call<Medical> getMedicalInfo(@Path("herbID") int herbID);

    @GET("leaf/{herbID}")
    Call<List<Leaf>> getLeafInfo(@Path("herbID") int herbID);

    @GET("blossom/{herbID}")
    Call<Blossom> getBlossomInfo(@Path("herbID") int herbID);

    @GET("pictures/{herbID}")
    Call<Pictures> getPictures(@Path("herbID") int herbID);
}
