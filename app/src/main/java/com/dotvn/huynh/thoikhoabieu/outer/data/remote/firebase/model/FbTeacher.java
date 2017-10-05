package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model;

/**
 * Created by Manh Hoang Huynh on 17/08/2017.
 */

public class FbTeacher {
    private String mId;
    private String mName;
    private String mSex = "Nam";
    private String mTelephoneNumber = "Trống";

    public FbTeacher() {
    }

    public FbTeacher(String id, String name, String sex, String telephone) {
        this.mId = id;
        this.mName = name;
        this.mSex = sex;
        this.mTelephoneNumber = telephone;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
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
