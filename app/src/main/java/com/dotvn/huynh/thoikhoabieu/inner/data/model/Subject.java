package com.dotvn.huynh.thoikhoabieu.inner.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Manh Hoang Huynh on 17/08/2017.
 * Current issue: I want both id and name unique, but can't with realm. So I set id as name.
 * I toLowerCase name to sure that toán & Toán will not be accepted (t & T)
 */

public class Subject implements Parcelable{

    public Subject(){}
    public Subject(String id, String name, Teacher teacher){
        this.mId = id.trim();
        this.mName = name.toLowerCase().trim();
        this.mTeacher = teacher;
    }
    private Teacher mTeacher;
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

    public void setTeacher(Teacher teacher) {
        mTeacher = teacher;
    }

    public Teacher getTeacher() {
        return mTeacher;
    }

    private Subject(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mTeacher = in.readParcelable(Teacher.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeParcelable(mTeacher, 0);
    }
    public static final Creator<Subject> CREATOR
            = new Creator<Subject>() {
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}
