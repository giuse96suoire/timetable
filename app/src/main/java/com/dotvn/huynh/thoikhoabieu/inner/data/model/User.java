package com.dotvn.huynh.thoikhoabieu.inner.data.model;

import java.util.List;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class User {
    private String mDisplayName;
    private String mEmail;
    private String mPhotoUrl;
    private String mProviderId;
    private String mUid;
    private String mPhoneNumber;
    private List<Friend> mFriends;

    public User() {
    }

    public User(String uid, String displayName, String email, String phoneNumber, String photoUrl, String providerId, List<Friend> friends) {
        mDisplayName = displayName;
        mEmail = email;
        mPhotoUrl = photoUrl;
        mProviderId = providerId;
        mUid = uid;
        mPhoneNumber = phoneNumber;
        mFriends = friends;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public void setProviderId(String providerId) {
        mProviderId = providerId;
    }


    public void setUid(String uid) {
        mUid = uid;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void setFriends(List<Friend> friends) {
        mFriends = friends;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public String getProviderId() {
        return mProviderId;
    }


    public String getUid() {
        return mUid;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public List<Friend> getFriends() {
        return mFriends;
    }
}
