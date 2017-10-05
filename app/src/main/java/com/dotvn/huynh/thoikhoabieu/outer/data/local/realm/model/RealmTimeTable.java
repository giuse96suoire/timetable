package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Manh Hoang Huynh on 27/09/2017.
 */

public class RealmTimeTable extends RealmObject {
    public static final String ID = "mId";
    @PrimaryKey
    private String mId;
    private String mName;
    private String mDescription;
    private RealmList<RealmOneDay> mData;
    private RealmList<RealmFriend> mCanRead;
    private RealmList<RealmFriend> mCanWrite;
    private String mOwnerId;

    public RealmTimeTable() {
    }

    public RealmTimeTable(String id, String name,String description,  RealmList<RealmOneDay> data, String ownerId, RealmList<RealmFriend>  canRead, RealmList<RealmFriend>  canWrite) {
        mId = id;
        mName = name;
        mDescription = description;
        mData = data;
        mOwnerId = ownerId;
        mCanRead = canRead;
        mCanWrite = canWrite;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setData(RealmList<RealmOneDay> data) {
        mData = data;
    }

    public void setCanRead(RealmList<RealmFriend> canRead) {
        mCanRead = canRead;
    }

    public void setCanWrite(RealmList<RealmFriend> canWrite) {
        mCanWrite = canWrite;
    }

    public void setOwnerId(String ownerId) {
        mOwnerId = ownerId;
    }

    public static String getID() {
        return ID;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public RealmList<RealmOneDay> getData() {
        return mData;
    }

    public RealmList<RealmFriend> getCanRead() {
        return mCanRead;
    }

    public RealmList<RealmFriend> getCanWrite() {
        return mCanWrite;
    }

    public String getOwnerId() {
        return mOwnerId;
    }
}
