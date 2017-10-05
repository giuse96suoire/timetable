package com.dotvn.huynh.thoikhoabieu.outer.data.local.shared;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Manh Hoang Huynh on 23/09/2017.
 */

public class SharedDB {
    private Context mContext;
    private final String DB_NAME = "tkb";
    private final String KEY_FIRST_USE = "KEY_FIRST_USE";
    private final String KEY_CURRENT_TIME_TABLE = "CURRENT_TIME_TABLE";
    private static SharedDB mInstance;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;

    public static SharedDB getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedDB(context);
        }
        return mInstance;
    }

    public SharedDB(Context context) {
        this.mContext = context.getApplicationContext();
        this.mShared = mContext.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        this.mEditor = mShared.edit();
    }

    public void setFirstTimeUse(boolean isFirstTime) {
        setBoolean(KEY_FIRST_USE, isFirstTime);
    }
    public void setCurrentTimeTable(String timeTableId) {
        setString(KEY_CURRENT_TIME_TABLE, timeTableId);
    }

    public boolean isFirstTimeUse() {
        return mShared.getBoolean(KEY_FIRST_USE, true);
    }
    public String getCurrentTimeTable() {
        return mShared.getString(KEY_CURRENT_TIME_TABLE, null);
    }

    private void setBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }
    private void setString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }
}
