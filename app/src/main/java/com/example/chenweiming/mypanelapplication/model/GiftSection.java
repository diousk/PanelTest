package com.example.chenweiming.mypanelapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class GiftSection implements Parcelable {
    public String categoryTitle;
    public List<Gift> giftList;

    public GiftSection() {
    }

    protected GiftSection(Parcel in) {
        categoryTitle = in.readString();
        if (in.readByte() == 0x01) {
            giftList = new ArrayList<Gift>();
            in.readList(giftList, Gift.class.getClassLoader());
        } else {
            giftList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryTitle);
        if (giftList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(giftList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GiftSection> CREATOR = new Parcelable.Creator<GiftSection>() {
        @Override
        public GiftSection createFromParcel(Parcel in) {
            return new GiftSection(in);
        }

        @Override
        public GiftSection[] newArray(int size) {
            return new GiftSection[size];
        }
    };
}
