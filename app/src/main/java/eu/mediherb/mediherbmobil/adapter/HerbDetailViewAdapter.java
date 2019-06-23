package eu.mediherb.mediherbmobil.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.multilevelview.MultiLevelAdapter;

import java.util.List;


public class HerbDetailViewAdapter extends MultiLevelAdapter {


    public HerbDetailViewAdapter(List<?> recyclerViewItems) {
        super(recyclerViewItems);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}