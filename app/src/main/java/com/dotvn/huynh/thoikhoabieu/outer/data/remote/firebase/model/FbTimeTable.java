package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manh Hoang Huynh on 27/09/2017.
 */

public class FbTimeTable {
    private String mId;
    private String mName;
    private Map<String, FbOneDay> mData = new HashMap<>(7);
    private String mOwnerId;
    private Map<String, String> mCanRead = new HashMap<>();
    private Map<String, String> mCanWrite = new HashMap<>();
    private String mDescription;
    public FbTimeTable() {
    }

    public FbTimeTable(String id, String name, String description, Map<String, FbOneDay> data, String ownerId, Map<String, String> canRead, Map<String, String> canWrite) {
        mId = id;
        mName = name;
        mData = data;
        mOwnerId = ownerId;
        mCanRead = canRead;
        mCanWrite = canWrite;
        mDescription = description;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setData(Map<String, FbOneDay> data) {
        mData = data;
    }

    public void setOwnerId(String ownerId) {
        mOwnerId = ownerId;
    }

    public void setCanRead(Map<String, String> canRead) {
        mCanRead = canRead;
    }

    public void setCanWrite(Map<String, String> canWrite) {
        mCanWrite = canWrite;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Map<String, FbOneDay> getData() {
        return mData;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public Map<String, String> getCanRead() {
        return mCanRead;
    }

    public Map<String, String> getCanWrite() {
        return mCanWrite;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }
}
