package com.example.chenweiming.mypanelapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenweiming.mypanelapplication.model.GiftSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private List<GiftSection> giftSections;
    private GiftSectionFragment mCurrentFragment;

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        giftSections = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return giftSections == null ? 0 : giftSections.size();
    }

    @Override
    public Fragment getItem(int position) {
        GiftSection section = giftSections.get(position);
        return GiftSectionFragment.newInstance(section);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mCurrentFragment != object) {
            mCurrentFragment = ((GiftSectionFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public View getScrollableView() {
        if (mCurrentFragment == null) {
            return null;
        }
        return mCurrentFragment.getScrollableView();
    }

    public void setGiftSections(List<GiftSection> data) {
        giftSections.clear();
        giftSections.addAll(data);
        notifyDataSetChanged();
    }
}