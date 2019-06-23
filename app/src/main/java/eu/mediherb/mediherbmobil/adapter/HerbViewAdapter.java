package eu.mediherb.mediherbmobil.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.transform.CircleTransform;
import eu.mediherb.mediherbmobil.classes.gson.Herb;

public class HerbViewAdapter extends RecyclerView.Adapter<HerbViewAdapter.HerbViewHolder> {
    private List<Herb> mTestItemList;
    private View.OnClickListener mOnItemClickListener;

    public HerbViewAdapter(List<Herb> testItemList) {
        this.mTestItemList = testItemList;
    }

    @NotNull
    @Override
    public HerbViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);

        return new HerbViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull HerbViewHolder holder, int position) {
        try {
            final String MAIN_URL = "https://mediherb.eu/";
            Herb item = mTestItemList.get(position);

            if (item != null) {
                final String url = MAIN_URL + item.getBildLink();

                holder.bigText.setText(item.getNameTrival());
                holder.smallText.setText(item.getNameWissenschaft());
                holder.percText.setText(String.format("%.2f", item.getPercentage() * 100));
                Picasso.get()
                        .load(url)
                        .fit()
                        .transform(new CircleTransform())
                        .placeholder(R.drawable.ic_leaf)
                        .error(R.mipmap.ic_launcher_round)
                        .into(holder.img);
            }

        } catch (Exception e) {
            Log.e("Download", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return mTestItemList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    class HerbViewHolder extends RecyclerView.ViewHolder {
        TextView bigText;
        TextView smallText;
        TextView percText;
        ImageView img;

        HerbViewHolder(View itemView) {
            super(itemView);
            bigText = itemView.findViewById(R.id.row_main_big);
            smallText = itemView.findViewById(R.id.row_main_small);
            percText = itemView.findViewById(R.id.row_main_perc);
            img = itemView.findViewById(R.id.row_main_img);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}


