package com.example.chenweiming.mypanelapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

    public GiftAdpater(List<Gift> gifts) {
        giftList = new ArrayList<>(gifts);
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        GiftViewHolder viewHolder = new GiftViewHolder(inflater.inflate(R.layout.layout_gift_item, parent, false));
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
