package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model;

import java.util.Map;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class FbUser {
    public static final String PROPERTY_DISPLAY_NAME = "displayName";
    public static final String PROPERTY_EMAIL = "email";
    public static final String PROPERTY_PHOTO_URL = "photoUrl";
    public static final String PROPERTY_PROVIDER_ID = "providerId";
    public static final String PROPERTY_UID = "uid";
    public static final String PROPERTY_PHONE_NUMBER = "phoneNumber";
    public static final String PROPERTY_FRIENDS = "friends";
    public static final String PROPERTY_TIMETABLE = "timeTables";
    private String mDisplayName;
    private String mEmail;
    private String mPhotoUrl;
    private String mProviderId;
    private String mUid;
    private String mPhoneNumber;
    private Map<String, String> mFriends;

    public FbUser() {
    }

    public FbUser(String uid, String displayName, String email, String phoneNumber, String photoUrl, String providerId, Map<String, String> friends) {
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

    public void setFriends(Map<String, String> friends) {
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

    public Map<String, String> getFriends() {
        return mFriends;
    }
}
