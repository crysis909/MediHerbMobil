package eu.mediherb.mediherbmobil.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.internal.phenotype.zzh;

import eu.mediherb.mediherbmobil.R;

public class ExpTextView extends View {

    private View root;
    private TextView title;
    private TextView text;
    private ConstraintLayout layout;
    private boolean isopen = false;



    public ExpTextView(Context context) {
        super(context);
        inti(context);
    }

    public ExpTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inti(context);
    }


    public void setTexte(int t1, int t2){
        title.setText(t1);
        text.setText(t2);
        Log.e("NUKLEARFIRE", "DRINNEN");
    }


    private void inti(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.cust_view, null);
        title = root.findViewById(R.id.textView2);
        text = root.findViewById(R.id.textView3);
        layout = root.findViewById(R.id.cust_view_layout);
        title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isopen){
                    ViewGroup.LayoutParams  params = text.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    text.setLayoutParams(params);
                }
                else{
                    ViewGroup.LayoutParams  params = text.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    text.setLayoutParams(params);
                }
            }
        });

    }
}
