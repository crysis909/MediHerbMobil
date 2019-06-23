package eu.mediherb.mediherbmobil.classes.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HerbColor {
    @SerializedName("farbe")
    @Expose
    private String color;

    //region Getter
    public String getColor() {
        return color;
    }
    //endregion
}
