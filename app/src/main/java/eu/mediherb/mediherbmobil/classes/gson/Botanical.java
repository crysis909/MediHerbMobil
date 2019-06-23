package eu.mediherb.mediherbmobil.classes.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Botanical {
    @SerializedName("Standort")
    @Expose
    private List<Location> locations = null;
    @SerializedName("Gebiet")
    @Expose
    private List<Area> areas = null;



    //region Getter
    public List<Location> getLocations() {
        return locations;
    }

    public List<Area> getAreas() {
        return areas;
    }
    //endregion
}

