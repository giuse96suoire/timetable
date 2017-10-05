package com.dotvn.huynh.thoikhoabieu.inner.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by Manh Hoang Huynh on 11/09/2017.
 */

public class Other implements Parcelable{
    private String mId;
    private String mTitle;
    private String mDescription;

    public Other() {
        this.mId = UUID.randomUUID().toString();
    }

    public Other( String id, String title, String description) {
        mId = id;
        mTitle = title;
        mDescription = description;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }


    private Other(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
    }
    public static final Creator<Other> CREATOR
            = new Creator<Other>() {
        public Other createFromParcel(Parcel in) {
            return new Other(in);
        }

        public Other[] newArray(int size) {
            return new Other[size];
        }
    };
}
