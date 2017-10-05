package com.dotvn.huynh.thoikhoabieu.inner.data.model;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 18/08/2017.
 */

public class OneDay {

    public OneDay() {
    }

    public OneDay(String id, String dayOfWeek, List<ItemOfDay> itemOfDay) {
        this.mId = id;
        this.mDayOfWeek = dayOfWeek;
        this.mListItemOfDay = itemOfDay;
    }

    private String mId;
    private String mDayOfWeek;
    private List<ItemOfDay> mListItemOfDay;

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

    public void setListItemOfDay(List<ItemOfDay> itemOfDay) {
        mListItemOfDay = itemOfDay;
    }

    public List<ItemOfDay> getListItemOfDay() {
        return mListItemOfDay;
    }
}
