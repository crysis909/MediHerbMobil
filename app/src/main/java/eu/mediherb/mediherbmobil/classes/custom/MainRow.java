package eu.mediherb.mediherbmobil.classes.custom;

public class MainRow {

    private int icon;
    private int textBig;
    private int textSmall;

    public MainRow(int icon, int textSmall, int textBig) {
        this.icon = icon;
        this.textBig = textBig;
        this.textSmall = textSmall;
    }

    //region Getter-Setter
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTextBig() {
        return textBig;
    }

    public void setTextBig(int textBig) {
        this.textBig = textBig;
    }

    public int getTextSmall() {
        return textSmall;
    }

    public void setTextSmall(int textSmall) {
        this.textSmall = textSmall;
    }

//endregion
}
