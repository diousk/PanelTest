package com.example.chenweiming.mypanelapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class Gift implements Parcelable{
    public String iconUrl;
    public String text;
    public String price;

    public Gift() {
    }

    @Override
    public String toString() {
        return "Gift{" +
                "iconUrl='" + iconUrl + '\'' +
                ", text='" + text + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    protected Gift(Parcel in) {
        iconUrl = in.readString();
        text = in.readString();
        price = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iconUrl);
        dest.writeString(text);
        dest.writeString(price);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Gift> CREATOR = new Parcelable.Creator<Gift>() {
        @Override
        public Gift createFromParcel(Parcel in) {
            return new Gift(in);
        }

        @Override
        public Gift[] newArray(int size) {
            return new Gift[size];
        }
    };
}
