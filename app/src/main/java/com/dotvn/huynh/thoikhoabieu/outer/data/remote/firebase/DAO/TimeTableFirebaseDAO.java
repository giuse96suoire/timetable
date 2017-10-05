package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.DAO;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.middle.converter.TimeTableConverter;
import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.dotvn.huynh.thoikhoabieu.outer.TwoCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.RemoteDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.FbUtil;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbTimeTable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Manh Hoang Huynh on 24/09/2017.
 * Thoose class that need call it should not know what is it, just only LocalDAO
 */

public class TimeTableFirebaseDAO extends RemoteDAO<TimeTable> {
    FirebaseDBHelpter<FbTimeTable> mFbDBHelper;

    public TimeTableFirebaseDAO() {
        mFbDBHelper = new FirebaseDBHelpter<>();
    }

    @Override
    public void read(final String id, final TwoCallback<TimeTable> callback) {
        mFbDBHelper.read(FbUtil.getTimeTableReference(id), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FbTimeTable data = dataSnapshot.getValue(FbTimeTable.class);
                callback.onComplete(TimeTableConverter.getInstance().fromFirebaseToModel(data));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    @Override
    public void write(String id, TimeTable value, ThreeCallback<Void> callback) {
        mFbDBHelper.write(
                TimeTableConverter.getInstance().fromModelToFirebase(value),
                FbUtil.getTimeTableReference(id),
                callback);
    }

    @Override
    public void updateProperty(String reference, Object value, ThreeCallback<Void> callback) {

    }


    @Override
    public void writeWithRandomId(TimeTable value, ThreeCallback<String> callback) {
        mFbDBHelper.writeWithRandomId(
                TimeTableConverter.getInstance().fromModelToFirebase(value),
                FbUtil.getTimeTableReference(null),
                callback);
    }

    @Override
    public void writeIfNotExist(String id, TimeTable value, ThreeCallback<Void> callback) {
        mFbDBHelper.writeIfNotExist(
                TimeTableConverter.getInstance().fromModelToFirebase(value),
                FbUtil.getTimeTableReference(id),
                callback);
    }

    @Override
    public void remove(String id, ThreeCallback<TimeTable> callback) {

    }



    @Override
    public void readOnce(String id, TwoCallback<TimeTable> callback) {

    }
}