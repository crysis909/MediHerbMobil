package eu.mediherb.mediherbmobil.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.classes.gson.Herb;

public class HerbViewAdapter extends RecyclerView.Adapter<HerbViewAdapter.HerbViewHolder> {
    View.OnClickListener listener;
    private final List<Herb> dataList;

    public void setClickListener(View.OnClickListener callback){
        listener = callback;
    }



    public HerbViewAdapter(List<Herb> dataList){
        this.dataList = dataList;
    }

    class HerbViewHolder extends RecyclerView.ViewHolder{
        public  final View view;

        TextView bigText;
        TextView smallText;
        ImageView img;

        HerbViewHolder(View itemView){
            super(itemView);
            view = itemView;

            bigText = view.findViewById(R.id.row_main_big);
            smallText = view.findViewById(R.id.row_main_small);
            img = view.findViewById(R.id.row_main_img);
        }
    }

    @NonNull
    @Override
    public HerbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        RecyclerView.ViewHolder holder = new HerbViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                listener.onClick(view);
            }
        });

        return new HerbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HerbViewHolder holder, int position) {
        Herb item = dataList.get(position);

        holder.bigText.setText(item.getNameTrival());
        holder.smallText.setText(item.getNameWissenschaft());
        //TODO Change Pseudo photo to the real one
        holder.img.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

