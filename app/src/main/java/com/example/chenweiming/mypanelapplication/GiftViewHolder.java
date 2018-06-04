package com.example.chenweiming.mypanelapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.chenweiming.mypanelapplication.model.Gift;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class GiftViewHolder extends RecyclerView.ViewHolder{
    private SimpleDraweeView simpleDraweeView;
    private TextView textView;

    public GiftViewHolder(View itemView) {
        super(itemView);
        simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.gift_icon);
        textView = (TextView) itemView.findViewById(R.id.gift_title);
    }

    public void bindView(Gift gift) {
        FrescoHelper.loadInto(gift.iconUrl, simpleDraweeView);
        textView.setText(gift.text);
    }
}
