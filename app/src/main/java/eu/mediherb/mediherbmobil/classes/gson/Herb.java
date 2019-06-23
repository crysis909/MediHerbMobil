package eu.mediherb.mediherbmobil.classes.gson;

public class Herb implements Comparable< Herb >{

    private Integer herbID;
    private String nameTrival;
    private String nameWissenschaft;
    private Integer sammelzeitTo;
    private String dosierung;
    private String warnungen;
    private Integer hoeheFrom;
    private Integer hoeheTo;
    private Integer bluetezeitFrom;
    private String familie;
    private Integer bluetezeitTo;
    private String auffaelligkeiten;
    private String pflanzentype;
    private Integer sammelzeitFrom;
    private String bildLink;

    private Float percentage = 0.00f;

    //region Getter
    public Integer getHerbID() {
        return herbID;
    }

    public String getNameTrival() {
        return nameTrival;
    }

    public String getNameWissenschaft() {
        return nameWissenschaft;
    }

    public Integer getSammelzeitTo() {
        return sammelzeitTo;
    }

    public String getDosierung() {
        return dosierung;
    }

    public String getWarnungen() {
        return warnungen;
    }

    public Integer getHoeheFrom() {
        return hoeheFrom;
    }

    public Integer getHoeheTo() {
        return hoeheTo;
    }

    public Integer getBluetezeitFrom() {
        return bluetezeitFrom;
    }

    public String getFamilie() {
        return familie;
    }

    public Integer getBluetezeitTo() {
        return bluetezeitTo;
    }

    public String getAuffaelligkeiten() {
        return auffaelligkeiten;
    }

    public String getPflanzentype() {
        return pflanzentype;
    }

    public Integer getSammelzeitFrom() {
        return sammelzeitFrom;
    }

    public String getBildLink() {
        return bildLink;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    @Override
    public int compareTo(Herb o) {
        return this.getPercentage().compareTo(o.getPercentage());
    }
    //endregion

}