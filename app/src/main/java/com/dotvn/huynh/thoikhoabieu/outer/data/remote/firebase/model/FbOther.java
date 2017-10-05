package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model;

import android.os.Parcel;

import java.util.UUID;

/**
 * Created by Manh Hoang Huynh on 11/09/2017.
 */

public class FbOther {
    private String mId;
    private String mTitle;
    private String mDescription;

    public FbOther() {
        this.mId = UUID.randomUUID().toString();
    }

    public FbOther(String id, String title, String description) {
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


    private FbOther(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();

    }
}
