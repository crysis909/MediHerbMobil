package eu.mediherb.mediherbmobil.classes.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HerbBlossom {
    @SerializedName("nameTrival")
    @Expose
    private String nameTrival;
    @SerializedName("nameWissenschaft")
    @Expose
    private String nameWissenschaft;
    @SerializedName("bluetezeitFrom")
    @Expose
    private Integer bluetezeitFrom;
    @SerializedName("bluetezeitTo")
    @Expose
    private Integer bluetezeitTo;
    @SerializedName("bluetenform")
    @Expose
    private String bluetenform;
    @SerializedName("stand")
    @Expose
    private String stand;

    //region Getter
    public String getNameTrival() {
        return nameTrival;
    }

    public String getNameWissenschaft() {
        return nameWissenschaft;
    }

    public Integer getBluetezeitFrom() {
        return bluetezeitFrom;
    }

    public Integer getBluetezeitTo() {
        return bluetezeitTo;
    }

    public String getBluetenform() {
        return bluetenform;
    }

    public String getStand() {
        return stand;
    }
    //endregion
}
