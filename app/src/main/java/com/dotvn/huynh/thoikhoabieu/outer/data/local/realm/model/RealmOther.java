package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Manh Hoang Huynh on 11/09/2017.
 */

public class RealmOther extends RealmObject{
    @PrimaryKey
    private String mId;
    private String mTitle;
    private String mDescription;

    public RealmOther() {

    }

    public RealmOther(String id, String title, String description) {
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
}
