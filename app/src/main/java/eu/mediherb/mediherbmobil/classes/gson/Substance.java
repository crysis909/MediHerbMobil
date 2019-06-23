package eu.mediherb.mediherbmobil.classes.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Substance {
    @SerializedName("wirkstoff")
    @Expose
    private String substance;

    //region Getter
    public String getSubstance() {
        return substance;
    }
    //endregion
}
