package com.dotvn.huynh.thoikhoabieu.inner.data.model;

import com.dotvn.huynh.thoikhoabieu.outer.data.DayConstant;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.FbConstant;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.FbUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manh Hoang Huynh on 27/09/2017.
 */

public class TimeTable {
    private String mId;
    private String mName;
    private String mDescription;
    private List<OneDay> mData;
    private String mOwnerId;
    private List<Friend> mCanRead;
    private List<Friend> mCanWrite;

    public TimeTable() {
    }

    public TimeTable(String id, String name, String description, List<OneDay> data, String ownerId, List<Friend> canRead, List<Friend> canWrite) {
        mId = id;
        mName = name;
        mDescription = description;
        mData = data;
        mOwnerId = ownerId;
        mCanRead = canRead;
        mCanWrite = canWrite;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setData(List<OneDay> data) {
        mData = data;
    }

    public void setOwnerId(String ownerId) {
        mOwnerId = ownerId;
    }

    public void setCanRead(List<Friend> canRead) {
        mCanRead = canRead;
    }

    public void setCanWrite(List<Friend> canWrite) {
        mCanWrite = canWrite;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public List<OneDay> getData() {
        return mData;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public List<Friend> getCanRead() {
        return mCanRead;
    }

    public List<Friend> getCanWrite() {
        return mCanWrite;
    }

    public static TimeTable createNew(String name, String description) {
            TimeTable timeTable = new TimeTable();
            timeTable.setId(FbUtil.generateRandomKeyForRefence(FbConstant.TIME_TABLE_REFERENCE));
            List<OneDay> listOneDay = new ArrayList<>();
            OneDay oneDay = new OneDay();
            oneDay.setId(DayConstant.MONDAY + timeTable.getId());
            oneDay.setDayOfWeek(DayConstant.MONDAY);
            listOneDay.add(oneDay);
            oneDay = new OneDay();
            oneDay.setDayOfWeek(DayConstant.TUESDAY);
            oneDay.setId(DayConstant.TUESDAY + timeTable.getId());
            listOneDay.add(oneDay);
            oneDay = new OneDay();
            oneDay.setDayOfWeek(DayConstant.WEDNESDAY);
            oneDay.setId(DayConstant.WEDNESDAY + timeTable.getId());
            listOneDay.add(oneDay);
            oneDay = new OneDay();
            oneDay.setDayOfWeek(DayConstant.THURSDAY);
            oneDay.setId(DayConstant.THURSDAY + timeTable.getId());
            listOneDay.add(oneDay);
            oneDay = new OneDay();
            oneDay.setDayOfWeek(DayConstant.FRIDAY);
            oneDay.setId(DayConstant.FRIDAY + timeTable.getId());
            listOneDay.add(oneDay);
            oneDay = new OneDay();
            oneDay.setDayOfWeek(DayConstant.SATURDAY);
            oneDay.setId(DayConstant.SATURDAY + timeTable.getId());
            listOneDay.add(oneDay);
            oneDay = new OneDay();
            oneDay.setDayOfWeek(DayConstant.SUNDAY);
            oneDay.setId(DayConstant.SUNDAY + timeTable.getId());
            listOneDay.add(oneDay);
            timeTable.setData(listOneDay);
            timeTable.setName(name);
            timeTable.setDescription(description);
            return timeTable;
    }
}
