package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 18/08/2017.
 */

public class FbOneDay {

    public FbOneDay() {
    }

    public FbOneDay(String id, String dayOfWeek, List<FbItemOfDay> itemOfDay) {
        this.mId = id;
        this.mDayOfWeek = dayOfWeek;
        this.mListItemOfDay = itemOfDay;
    }

    private String mId;
    private String mDayOfWeek;
    private List<FbItemOfDay> mListItemOfDay;

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

    public void setListItemOfDay(List<FbItemOfDay> itemOfDay) {
        mListItemOfDay = itemOfDay;
    }

    public List<FbItemOfDay> getListItemOfDay() {
        return mListItemOfDay;
    }
}
