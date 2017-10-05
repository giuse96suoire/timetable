package com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.DAO;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.middle.converter.UserConverter;
import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.dotvn.huynh.thoikhoabieu.outer.TwoCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.RemoteDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.FbUtil;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Manh Hoang Huynh on 24/09/2017.
 * Thoose class that need call it should not know what is it, just only LocalDAO
 */

public class UserFirebaseDAO extends RemoteDAO<User> {
    FirebaseDBHelpter<FbUser> mFbDBHelper;

    public UserFirebaseDAO() {
        mFbDBHelper = new FirebaseDBHelpter<>();
    }

    @Override
    public void read(final String id, final TwoCallback<User> callback) {
        mFbDBHelper.read(FbUtil.getTimeTableReference(id), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FbUser data = dataSnapshot.getValue(FbUser.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    @Override
    public void write(String id, User value, ThreeCallback<Void> callback) {

    }

    @Override
    public void updateProperty(String reference, Object value, ThreeCallback<Void> callback) {
        mFbDBHelper.writeProperty(reference, value, callback);
    }

    @Override
    public void writeWithRandomId(User value, ThreeCallback<String> callback) {

    }



    @Override
    public void writeIfNotExist(String id, User value, ThreeCallback<Void> callback) {
        mFbDBHelper.writeIfNotExist(
                UserConverter.getInstance().fromModelToFirebase(value),
                FbUtil.getUserReference(id),
                callback);
    }

    @Override
    public void remove(String id, ThreeCallback<User> callback) {

    }


    @Override
    public void readOnce(String id, final TwoCallback<User> callback) {
        mFbDBHelper.readOne(FbUtil.getUserReference(id), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    callback.onComplete(null);
                } else {
                    FbUser fbUser = dataSnapshot.getValue(FbUser.class);
                    callback.onComplete(UserConverter.getInstance().fromFirebaseToModel(fbUser));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }


}