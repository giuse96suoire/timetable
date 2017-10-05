package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Manh Hoang Huynh on 18/08/2017.
 */

public class RealmOneDay extends RealmObject {
    @Ignore
    public static final String ID = "mId";
    @Ignore
    public static final String DATE_OF_WEEK = "mDayOfWeek";

    public RealmOneDay() {
    }

    public RealmOneDay(String id, String dayOfWeek, RealmList<RealmItemOfDay> listRealmItemOfDay) {
        this.mId = id;
        this.mDayOfWeek = dayOfWeek;
        this.mListItemOfDay = listRealmItemOfDay;
    }

    private String mDayOfWeek;

    @Index
    @PrimaryKey
    private String mId;

    public void setDayOfWeek(String dayOfWeek) {
        mDayOfWeek = dayOfWeek;
    }

    public String getDayOfWeek() {
        return mDayOfWeek;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    private RealmList<RealmItemOfDay> mListItemOfDay;

    public RealmList<RealmItemOfDay> getListItemOfDay() {
        return mListItemOfDay;
    }

    public void setListItemOfDay(RealmList<RealmItemOfDay> listItemOfDay) {
        mListItemOfDay = listItemOfDay;
    }


}
