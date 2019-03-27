package eu.mediherb.mediherbmobil.classes;

public class MainRow {

    private int icon;
    private int textbig;
    private int textsmall;

    public MainRow(int icon, int textsmall, int textbig) {
        this.icon = icon;
        this.textbig = textbig;
        this.textsmall = textsmall;
    }

    //region Getter-Setter
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTextbig() {
        return textbig;
    }

    public void setTextbig(int textbig) {
        this.textbig = textbig;
    }

    public int getTextsmall() {
        return textsmall;
    }

    public void setTextsmall(int textsmall) {
        this.textsmall = textsmall;
    }
    //endregion
}
