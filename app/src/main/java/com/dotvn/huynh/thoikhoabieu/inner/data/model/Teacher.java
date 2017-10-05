package com.dotvn.huynh.thoikhoabieu.inner.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Manh Hoang Huynh on 17/08/2017.
 */

public class Teacher implements Parcelable {
    private String mId;
    private String mName;
    private String mSex = "Nam";
    private String mTelephoneNumber = "Trống";

    public Teacher() {
    }

    public Teacher(String id, String name, String sex, String telephone) {
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

    private Teacher(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mSex = in.readString();
        mTelephoneNumber = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mSex);
        dest.writeString(mTelephoneNumber);
    }
    public static final Creator<Teacher> CREATOR
            = new Creator<Teacher>() {
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };
}
