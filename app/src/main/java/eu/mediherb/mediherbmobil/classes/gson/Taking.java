package eu.mediherb.mediherbmobil.classes.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taking {
    @SerializedName("einnahme")
    @Expose
    private String taking;

    //region Getter
    public String getTaking() {
        return taking;
    }
    //endregion2222
}
