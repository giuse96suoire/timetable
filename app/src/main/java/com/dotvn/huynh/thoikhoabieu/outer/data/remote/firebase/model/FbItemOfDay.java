package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model;

/**
 * Created by Manh Hoang Huynh on 11/09/2017.
 */

import io.realm.annotations.PrimaryKey;

/**
 * ItemOfDay class: one item include a object (subject or somethings else) with start + end time
 */
public class FbItemOfDay {
    @PrimaryKey
    private String mId;
    private String mStartTime;
    private String mEndTime;
    private FbSubject mSubject;
    private FbOther mOther;
    private String mEvent;

    public FbItemOfDay() {
    }

    public FbItemOfDay(String id, String startTime, String endTime, FbSubject subject, FbOther other, String event) {
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

    public void setSubject(FbSubject subject) {
        mSubject = subject;
    }

    public void setOther(FbOther other) {
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

    public FbSubject getSubject() {
        return mSubject;
    }

    public FbOther getOther() {
        return mOther;
    }

    public String getEvent() {
        return mEvent;
    }

}
