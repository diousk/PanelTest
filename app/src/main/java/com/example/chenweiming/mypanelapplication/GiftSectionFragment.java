package com.example.chenweiming.mypanelapplication;

import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenweiming.mypanelapplication.model.Gift;
import com.example.chenweiming.mypanelapplication.model.GiftSection;

/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class GiftSectionFragment extends Fragment {
    private static final String KEY_DATA = "key_data";
    private static final String KEY_INDEX = "key_index";
    private RecyclerView rvGifts;
    private GiftAdpater giftAdpater;
    private int orientation;
    private GiftSection giftSection;
    private int tabIndex;
    private boolean setDefault = false;

    public static GiftSectionFragment newInstance(GiftSection section, int position) {
        GiftSectionFragment fragment = new GiftSectionFragment();
        Bundle extra = new Bundle();
        extra.putParcelable(KEY_DATA, section);
        extra.putInt(KEY_INDEX, position);
        fragment.setArguments(extra);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift_section, null);
        rvGifts = view.findViewById(R.id.rvGifts);
        orientation = getResources().getConfiguration().orientation;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        giftSection = getArguments().getParcelable(KEY_DATA);
        tabIndex = getArguments().getInt(KEY_INDEX);
        onOrientationChanged(orientation);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (orientation != newConfig.orientation) {
            orientation = newConfig.orientation;
            onOrientationChanged(orientation);
        }
    }

    public void resetSelection() {
        setDefault = false;
        if (giftAdpater != null) {
            giftAdpater.resetSelection();
        }
    }

    public void selectDefault() {
        Log.d("Section","tabIndex: " + tabIndex);
        setDefault = true;
        if (giftAdpater != null) {
            giftAdpater.selectDefault();
        }
    }

    public Gift getSelectedGift() {
        Gift gift = null;
        if (giftAdpater != null) {
            gift = giftAdpater.getSelectedGift();
        }
        return gift;
    }

    private void onOrientationChanged(int orientation) {
        rvGifts.setAdapter(null);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvGifts.setLayoutManager(new GridLayoutManager(getContext(), 4));
        } else {
            rvGifts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        }

        giftAdpater = new GiftAdpater(giftSection.giftList, orientation, setDefault);
        rvGifts.setAdapter(giftAdpater);
    }
}