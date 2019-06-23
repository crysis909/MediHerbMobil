package eu.mediherb.mediherbmobil.classes.custom;

import com.multilevelview.models.RecyclerViewItem;

public class Item extends RecyclerViewItem {

    String text="";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Item(int level) {
        super(level);
    }
}
