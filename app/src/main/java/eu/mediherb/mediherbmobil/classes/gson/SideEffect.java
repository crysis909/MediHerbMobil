package eu.mediherb.mediherbmobil.classes.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SideEffect {
    @SerializedName("nebenwirkung")
    @Expose
    private String nebenwirkung;

    //region Getter
    public String getNebenwirkung() {
        return nebenwirkung;
    }
    //endregion
}
