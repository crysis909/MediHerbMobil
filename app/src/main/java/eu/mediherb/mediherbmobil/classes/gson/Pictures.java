package eu.mediherb.mediherbmobil.classes.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Pictures {

    @SerializedName("BildLinkGanz")
    @Expose
    private List<BildLinkGanz> picFull = null;
    @SerializedName("BildLinkBluete")
    @Expose
    private List<BildLinkBluete> picBlossom = null;
    @SerializedName("BildLinkBlatt")
    @Expose
    private List<BildLinkBlatt> picLeaf = null;

    //region Getter
    public List<BildLinkGanz> getPicFull() {
        return picFull;
    }

    public List<BildLinkBluete> getPicBlossom() {
        return picBlossom;
    }

    public List<BildLinkBlatt> getPicLeaf() {
        return picLeaf;
    }

    public List<String> getPictures() {
        final String MAIN_URL = "https://mediherb.eu/";
        List<String> pictures = new ArrayList<>();

        for (BildLinkGanz pic: picFull) {
            pictures.add(MAIN_URL + pic.picLink);
        }

        for (BildLinkBluete pic: picBlossom) {
            pictures.add(MAIN_URL + pic.picLink);
        }

        for (BildLinkBlatt pic: picLeaf) {
            pictures.add(MAIN_URL + pic.picLink);
        }
        return pictures;
    }
    //endregion


    //region Sub-Classes
    private class BildLinkGanz {
        @SerializedName("bildLink")
        @Expose
        public String picLink;

        public String getPicLink() {
            return picLink;
        }
    }

    private class BildLinkBluete {
        @SerializedName("bildLink")
        @Expose
        public String picLink;

        public String getPicLink() {
            return picLink;
        }
    }

    private class BildLinkBlatt {
        @SerializedName("bildLink")
        @Expose
        public String picLink;

        public String getPicLink() {
            return picLink;
        }
    }
    //endregion
}
