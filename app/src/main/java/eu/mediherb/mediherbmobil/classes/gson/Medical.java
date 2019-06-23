package eu.mediherb.mediherbmobil.classes.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Medical {

    @SerializedName("Wirkstoff")
    @Expose
    private List<Substance> substances = null;
    @SerializedName("Symptom")
    @Expose
    private List<Symptom> symptoms = null;
    @SerializedName("Einnahme")
    @Expose
    private List<Taking> takings = null;
    @SerializedName("Teil")
    @Expose
    private List<Part> parts = null;
    @SerializedName("Nebenwirkung")
    @Expose
    private List<SideEffect> sideEffects = null;

    //region Getter
    public List<Substance> getSubstances() {
        return substances;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public List<Taking> getTakings() {
        return takings;
    }

    public List<Part> getParts() {
        return parts;
    }

    public List<SideEffect> getSideEffects() {
        return sideEffects;
    }
    //endregion

}
