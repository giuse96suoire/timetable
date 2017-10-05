package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Manh Hoang Huynh on 17/08/2017.
 */

public class RealmTeacher extends RealmObject {

    public RealmTeacher() {
    }


    public RealmTeacher(String id, String name, String sex, String telephone) {
        this.mId = id;
        this.mName = name;
        this.mSex = sex;
        this.mTelephoneNumber = telephone;
    }

    @Ignore
    public static final String ID = "mId";
    @Ignore
    public static final String NAME = "mName";
    @Index
    @PrimaryKey
    private String mId;
    private String mName;
    private String mSex;
    private String mTelephoneNumber;
    private RealmSubject mSubject;

    public static String getID() {
        return ID;
    }

    public static String getNAME() {
        return NAME;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setSubject(RealmSubject subject) {
        mSubject = subject;
    }

    public RealmSubject getSubject() {
        return mSubject;
    }

    public String getSex() {
        return mSex;
    }

    public String getTelephoneNumber() {
        return mTelephoneNumber;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        mTelephoneNumber = telephoneNumber;
    }
}
