package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class FbFriend {
    private String mDisplayName;
    private String mPhotoUrl;
    private String mUid;
    private String mPhoneNumber;
    public FbFriend() {
    }

    public FbFriend(String displayName, String photoUrl, String uid, String phoneNumber) {
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
