package eu.mediherb.mediherbmobil.classes.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Part {
    @SerializedName("teil")
    @Expose
    private String part;

    //region Getter
    public String getPart() {
        return part;
    }
    //endregion
}
