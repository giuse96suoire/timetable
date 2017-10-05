package com.dotvn.huynh.thoikhoabieu.inner.data.model;

/**
 * Created by Manh Hoang Huynh on 11/09/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ItemOfDay class: one item include a object (subject or somethings else) with start + end time
 */
public class ItemOfDay implements Parcelable{
    private String mId;
    private String mStartTime;
    private String mEndTime;
    private Subject mSubject;
    private Other mOther;
    private String mEvent;

    public ItemOfDay() {
    }

    public ItemOfDay(String id, String startTime, String endTime, Subject subject, Other other, String event) {
        mId = id;
        mStartTime = startTime;
        mEndTime = endTime;
        mSubject = subject;
        mOther = other;
        mEvent = event;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public void setSubject(Subject subject) {
        mSubject = subject;
    }

    public void setOther(Other other) {
        mOther = other;
    }

    public void setEvent(String event) {
        mEvent = event;
    }

    public String getId() {
        return mId;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public Subject getSubject() {
        return mSubject;
    }

    public Other getOther() {
        return mOther;
    }

    public String getEvent() {
        return mEvent;
    }

    private ItemOfDay(Parcel in) {
        mId = in.readString();
        mStartTime = in.readString();
        mEndTime = in.readString();
        mSubject = in.readParcelable(Subject.class.getClassLoader());
        mOther = in.readParcelable(Other.class.getClassLoader());
        mEvent = in.readString();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mStartTime);
        dest.writeString(mEndTime);
        dest.writeParcelable(mSubject, 0);
        dest.writeParcelable(mOther, 1);
        dest.writeString(mEvent);
    }
    public static final Creator<ItemOfDay> CREATOR
            = new Creator<ItemOfDay>() {
        public ItemOfDay createFromParcel(Parcel in) {
            return new ItemOfDay(in);
        }

        public ItemOfDay[] newArray(int size) {
            return new ItemOfDay[size];
        }
    };
}
