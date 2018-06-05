package com.example.chenweiming.mypanelapplication;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.chenweiming.mypanelapplication.model.Gift;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class GiftAdpater extends RecyclerView.Adapter<GiftViewHolder> {
    private List<Gift> giftList;
    private int orientation;

    public GiftAdpater(List<Gift> gifts, int orientation) {
        giftList = new ArrayList<>(gifts);
        this.orientation = orientation;
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.d("Rv", "onCreateViewHolder ori: " + orientation);
        int layoutResId = orientation == Configuration.ORIENTATION_PORTRAIT ? R.layout.layout_gift_item: R.layout.layout_gift_item_land;
        GiftViewHolder viewHolder = new GiftViewHolder(inflater.inflate(layoutResId, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        holder.bindView(giftList.get(position));
    }

    @Override
    public int getItemCount() {
        return giftList.size();
    }

    public void setGiftList(List<Gift> data) {
        giftList.clear();
        giftList.addAll(data);
        notifyDataSetChanged();
    }
}
