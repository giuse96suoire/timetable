package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model;

import android.os.Parcel;

/**
 * Created by Manh Hoang Huynh on 17/08/2017.
 * Current issue: I want both id and name unique, but can't with realm. So I set id as name.
 * I toLowerCase name to sure that toán & Toán will not be accepted (t & T)
 */

public class FbSubject {

    public FbSubject(){}
    public FbSubject(String id, String name, FbTeacher teacher){
        this.mId = id.trim();
        this.mName = name.toLowerCase().trim();
        this.mTeacher = teacher;
    }
    private FbTeacher mTeacher;
    private String mId;
    private String mName;
    public String getId() {
        return mId;
    }

    public String getName() {
        if (mName != null && mName.length() > 0) {
            //upper case first character
            return mName.substring(0, 1).toUpperCase() + mName.substring(1, mName.length());
        }
        return mName;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name.toLowerCase().trim();
    }

    public void setTeacher(FbTeacher teacher) {
        mTeacher = teacher;
    }

    public FbTeacher getTeacher() {
        return mTeacher;
    }

    private FbSubject(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mTeacher = in.readParcelable(FbTeacher.class.getClassLoader());
    }

}
