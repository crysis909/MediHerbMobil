package eu.mediherb.mediherbmobil.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import eu.mediherb.mediherbmobil.classes.MainRow;
import eu.mediherb.mediherbmobil.R;

public class MainButtonAdapter extends ArrayAdapter {

    private Context _con;
    private Activity _act;
    private ArrayList<MainRow> _rows;
    private static final int resource = R.layout.custom_row;

    public MainButtonAdapter(Context context, Activity _act, ArrayList<MainRow> rows) {
        super(context, resource, rows);
        this._con = context;
        this._act = _act;
        _rows = rows;
    }
    private class Viewholder{
        TextView small;
        TextView big;
        ImageView img;
    }

    @NonNull
    @Override
    public View getView(int position, View v,@NonNull ViewGroup parent) {
        MainRow item = _rows.get(position);
        Viewholder h;
        if(v == null){
            v = _act.getLayoutInflater().inflate(resource, null);
            h = new Viewholder();
            h.small = v.findViewById(R.id.row_main_small);
            h.big = v.findViewById(R.id.row_main_big);
            h.img = v.findViewById(R.id.row_main_img);
            v.setTag(h);
        }else
            h = (Viewholder) v.getTag();
        h.small.setText(item.getTextsmall());
        h.big.setText(item.getTextbig());
        h.img.setImageResource(item.getIcon());
        return v;
    }
}
