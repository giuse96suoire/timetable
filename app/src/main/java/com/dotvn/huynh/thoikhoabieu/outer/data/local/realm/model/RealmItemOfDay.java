package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model;

/**
 * Created by Manh Hoang Huynh on 11/09/2017.
 */

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * ItemOfDay class: one item include a object (subject or somethings else) with start + end time
 */
public class RealmItemOfDay extends RealmObject {
    public static final String ID = "mId";
    public static final String SUBJECT_ID = "mRealmSubject.mId";
    @PrimaryKey
    private String mId;
    private String mStartTime;
    private String mEndTime;
    private RealmSubject mRealmSubject;
    private RealmOther mRealmOther;
    private String mEvent;

    public RealmItemOfDay() {
    }

    public RealmItemOfDay(String id, String startTime, String endTime, RealmSubject realmSubject, RealmOther realmOther, String event) {
        mId = id;
        mStartTime = startTime;
        mEndTime = endTime;
        mRealmSubject = realmSubject;
        mRealmOther = realmOther;
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

    public void setRealmSubject(RealmSubject realmSubject) {
        mRealmSubject = realmSubject;
    }

    public void setRealmOther(RealmOther realmOther) {
        mRealmOther = realmOther;
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

    public RealmSubject getRealmSubject() {
        return mRealmSubject;
    }

    public RealmOther getRealmOther() {
        return mRealmOther;
    }

    public String getEvent() {
        return mEvent;
    }
}
