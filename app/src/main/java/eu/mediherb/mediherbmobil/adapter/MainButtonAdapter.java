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

import eu.mediherb.mediherbmobil.MainRow;
import eu.mediherb.mediherbmobil.R;

public class MainButtonAdapter extends ArrayAdapter {

    private Context _con;
    private Activity _act;
    private ArrayList<MainRow> _rows;
    private static final int resource = R.layout.cust_btn;

    public MainButtonAdapter(Context context, Activity _act, ArrayList<MainRow> rows) {
        super(context, resource, rows);
        this._con = context;
        this._act = _act;
        _rows = rows;
        Log.e("NUKLEARFIRE", String.valueOf(this._rows.size()));
    }
    private class Viewholder{
        TextView small;
        TextView big;
        ImageView img;
    }

    @NonNull
    @Override
    public View getView(int position, View v,@NonNull ViewGroup parent) {
        _rows.get(position);
        Viewholder h;
        if(v == null){
            v = _act.getLayoutInflater().inflate(resource, null);
            h = new Viewholder();
            h.small = v.findViewById(R.id.row_main_text2);
            h.big = v.findViewById(R.id.row_main_text1);
            h.img = v.findViewById(R.id.row_main_img);
            v.setTag(h);
        }else
            h = (Viewholder) v.getTag();
        h.small.setText("Dummy 1");
        h.big.setText("Dummy2");
        h.img.setImageResource(R.drawable.ic_launcher_background);
        return v;
    }
}
