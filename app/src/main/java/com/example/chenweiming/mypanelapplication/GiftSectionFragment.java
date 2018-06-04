package com.example.chenweiming.mypanelapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenweiming.mypanelapplication.model.GiftSection;

/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class GiftSectionFragment extends Fragment {
    private static final String KEY_DATA = "key_data";
    private RecyclerView rvGifts;

    public static GiftSectionFragment newInstance(GiftSection section) {
        GiftSectionFragment fragment = new GiftSectionFragment();
        Bundle extra = new Bundle();
        extra.putParcelable(KEY_DATA, section);
        fragment.setArguments(extra);
        return fragment;
    }

    public View getScrollableView() {
        return rvGifts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift_section, null);
        rvGifts = view.findViewById(R.id.rvGifts);
        rvGifts.setLayoutManager(new GridLayoutManager(getContext(), 4));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        GiftSection giftSection = getArguments().getParcelable(KEY_DATA);
        rvGifts.setAdapter(new GiftAdpater(giftSection.giftList));
    }


}
