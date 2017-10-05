package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class RealmUser extends RealmObject{

    @PrimaryKey
    private String mUid;
    private String mDisplayName;
    private String mEmail;
    private String mPhoneNumber;
    private String mPhotoUrl;
    private String mProviderId;
    private RealmList<RealmFriend> mListFriend;

    public RealmUser() {
    }

    public RealmUser(String uid, String displayName, String email, String phoneNumber, String photoUrl, String providerId, RealmList<RealmFriend> listFriend) {
        mUid = uid;
        mDisplayName = displayName;
        mEmail = email;
        mPhotoUrl = photoUrl;
        mProviderId = providerId;
        mPhoneNumber = phoneNumber;
        mListFriend = listFriend;
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

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void setListFriend(RealmList<RealmFriend> listFriend) {
        mListFriend = listFriend;
    }

    public RealmList<RealmFriend> getListFriend() {
        return mListFriend;
    }
}
