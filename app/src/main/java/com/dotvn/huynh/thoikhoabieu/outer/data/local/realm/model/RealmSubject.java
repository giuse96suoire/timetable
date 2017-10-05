package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Manh Hoang Huynh on 17/08/2017.
 * Current issue: I want both id and name unique, but can't with realm. So I set id as name.
 */

public class RealmSubject extends RealmObject {

    public RealmSubject(String id, String name, RealmTeacher teacher) {
        this.mId = id;
        this.mName = name.toLowerCase();
        this.mTeacher = teacher;
    }

    @Ignore
    public static final String ID = "mId";
    @Ignore
    public static final String NAME = "mName";

    public RealmSubject() {
    }

    @Index
    @PrimaryKey
    private String mId;
    private String mName;
    private RealmTeacher mTeacher;

    public String getId() {
        return mId;
    }

    public String getName() {
        if (mName != null && mName.length() > 0) {
            return mName.substring(0, 1).toUpperCase() + mName.substring(1, mName.length());
        }
        return mName;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name.toLowerCase();
    }

    public void setTeacher(RealmTeacher teacher) {
        mTeacher = teacher;
    }

    public RealmTeacher getTeacher() {
        return mTeacher;
    }

}
