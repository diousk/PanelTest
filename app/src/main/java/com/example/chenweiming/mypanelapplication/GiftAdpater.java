package com.example.chenweiming.mypanelapplication;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chenweiming.mypanelapplication.model.Gift;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class GiftAdpater extends RecyclerView.Adapter<GiftAdpater.GiftViewHolder> {
    private List<Gift> giftList;
    private int orientation;

    private int selectedPos = RecyclerView.NO_POSITION;
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
        return new GiftViewHolder(inflater.inflate(layoutResId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        holder.bindView(giftList.get(position));
    }

    @Override
    public int getItemCount() {
        return giftList.size();
    }

    public void resetSelection() {
        Log.d("Gift", "resetSelection prevSelectedPos: " + selectedPos);
        if (selectedPos == RecyclerView.NO_POSITION) {
            // do nothing if never selected
            return;
        }

        int prevSelectedPos = selectedPos;
        selectedPos = RecyclerView.NO_POSITION;
        notifyItemChanged(prevSelectedPos);
    }

    public class GiftViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleDraweeView;
        private TextView textView;

        public GiftViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.gift_icon);
            textView = (TextView) itemView.findViewById(R.id.gift_title);
        }

        public void bindView(Gift gift) {
            itemView.setSelected(selectedPos == getLayoutPosition());

            FrescoHelper.loadInto(gift.iconUrl, simpleDraweeView);
            textView.setText(gift.text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(selectedPos);
                    selectedPos = getLayoutPosition();
                    notifyItemChanged(selectedPos);
                }
            });
        }
    }
}
