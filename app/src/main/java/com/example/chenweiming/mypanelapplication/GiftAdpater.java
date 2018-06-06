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
    private Gift selectedGift;

    public GiftAdpater(List<Gift> gifts, int orientation, boolean setDefault) {
        giftList = new ArrayList<>(gifts);
        this.orientation = orientation;

        if (setDefault) {
            selectDefault();
        }
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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

    public void selectDefault() {
        Log.d("Gift", "selectDefault prevSelectedPos: " + selectedPos);
        if (giftList.size() > 0) {
            selectedPos = 0;
            selectedGift = giftList.get(0);
            notifyItemChanged(selectedPos);
        }
    }

    public void resetSelection() {
        Log.d("Gift", "resetSelection prevSelectedPos: " + selectedPos);
        if (selectedPos == RecyclerView.NO_POSITION) {
            // do nothing if never selected
            return;
        }
        selectedGift = null;

        int prevSelectedPos = selectedPos;
        selectedPos = RecyclerView.NO_POSITION;
        notifyItemChanged(prevSelectedPos);
    }

    public Gift getSelectedGift() {
        return selectedGift;
    }

    public class GiftViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleDraweeView;
        private TextView title;
        private TextView price;

        public GiftViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.gift_icon);
            title = (TextView) itemView.findViewById(R.id.gift_title);
            price = (TextView) itemView.findViewById(R.id.gift_price);
        }

        public void bindView(final Gift gift) {
            itemView.setSelected(selectedPos == getLayoutPosition());

            FrescoHelper.loadInto(gift.iconUrl, simpleDraweeView);
            title.setText(gift.text);
            price.setText(gift.price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(selectedPos);
                    selectedPos = getLayoutPosition();
                    selectedGift = gift;
                    notifyItemChanged(selectedPos);
                }
            });
        }
    }
}
