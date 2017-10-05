package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class RealmFriend extends RealmObject{
    public static final String PHONE_NUMBER = "mPhoneNumber";
    public static final String ID = "mUid";
    private String mDisplayName;
    private String mPhotoUrl;
    @PrimaryKey
    private String mUid;
    private String mPhoneNumber;
    public RealmFriend() {
    }

    public RealmFriend(String displayName, String photoUrl, String uid, String phoneNumber) {
        mDisplayName = displayName;
        mPhotoUrl = photoUrl;
        mUid = uid;
        mPhoneNumber = phoneNumber;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public String getUid() {
        return mUid;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }
}
