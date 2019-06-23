package eu.mediherb.mediherbmobil.classes.gson;

import android.graphics.Color;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Blossom {
    @SerializedName("Herb")
    @Expose
    private List<HerbBlossom> herbs = null;
    @SerializedName("Farbe")
    @Expose
    private List<HerbColor> colors = null;


    //region Getter
    public List<HerbBlossom> getHerbs() {
        return herbs;
    }

    public List<HerbColor> getColors() { return colors; }
    //endregion

}
