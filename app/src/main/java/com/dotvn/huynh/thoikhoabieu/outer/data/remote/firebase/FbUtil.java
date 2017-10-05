package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Manh Hoang Huynh on 28/09/2017.
 */

public class FbUtil {
    public static String getUsersReference() {
        return FbConstant.USER_REFERENCE;
    }
    public static String getUserReference(String id) {
        return FbConstant.USER_REFERENCE+"/"+id;
    }
    public static String getTimeTablesReference() {
        return FbConstant.TIME_TABLE_REFERENCE;
    }
    public static String getTimeTableReference(String id) {
        if (id == null) {
            return FbConstant.TIME_TABLE_REFERENCE;
        }
        return FbConstant.TIME_TABLE_REFERENCE+"/"+id;
    }
    public static String getUserPropertyReference(@NonNull String uid, @NonNull String propertyKey) {
        return FbConstant.USER_REFERENCE+"/"+uid+"/"+propertyKey;
    }
    public static String generateRandomKeyForRefence(@NonNull String reference) {
        return FirebaseDatabase.getInstance().getReference(reference).push().getKey();
    }
}
