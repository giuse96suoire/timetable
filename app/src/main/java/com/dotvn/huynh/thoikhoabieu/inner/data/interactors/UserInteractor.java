package com.dotvn.huynh.thoikhoabieu.inner.data.interactors;

import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.middle.converter.FriendConverter;
import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.dotvn.huynh.thoikhoabieu.outer.TwoCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO.FriendRealmDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.RemoteDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.DAO.UserFirebaseDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.FbUtil;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Manh Hoang Huynh on 07/09/2017.
 */

public class UserInteractor implements Interactors.UserInteractor {
    private RemoteDAO<User> mUserRemoteDAO;
    private LocalDAO<Friend> mFriendLocalDAO;
    public UserInteractor() {
        this.mUserRemoteDAO = new UserFirebaseDAO();
        this.mFriendLocalDAO = new FriendRealmDAO();
    }

    @Override
    public void readFromLocal(LocalDAOCallback<User> callback) {

    }

    @Override
    public void searchFromRemote(final String id, final TwoCallback<User> callback) {
        mUserRemoteDAO.readOnce(id, new TwoCallback<User>() {
            @Override
            public void onComplete(User result) {
                callback.onComplete(result);
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });
    }

    @Override
    public void searchToAddFromRemote(String id,final TwoCallback<User> callback) {
        mUserRemoteDAO.readOnce(id, new TwoCallback<User>() {
            @Override
            public void onComplete(final User result) {
                mFriendLocalDAO.get(result.getPhoneNumber(), new LocalDAOCallback<Friend>() {
                    @Override
                    public void onSuccess(Friend item) {
                        if (item == null) {
                            callback.onComplete(result);
                        } else {
                            callback.onComplete(null);
                        }
                    }

                    @Override
                    public void onSuccess(List<Friend> items) {

                    }

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
            }

            @Override
            public void onFailure(String message) {
                callback.onFailure(message);
            }
        });
    }

    @Override
    public void findFromLocal(LocalDAOCallback<User> callback) {

    }

    @Override
    public void addFriendToRemote(String userId, Friend friend, ThreeCallback<Void> callback) {
        mUserRemoteDAO.updateProperty(
                FbUtil.getUserPropertyReference(userId,FbUser.PROPERTY_FRIENDS),
                FriendConverter.getInstance().fromModelToFirebase(friend),
                callback
                );
    }

    @Override
    public void removeFriendFromRemote(String userId, ThreeCallback<Void> callback) {

    }

    @Override
    public void addFriendToLocal(Friend friend, LocalDAOCallback<Friend> callback) {
        mFriendLocalDAO.insert(friend, callback);
    }

    @Override
    public void addTimeTableKeyToOwnListToRemote(String userId, Map<String, String> timetableMap, ThreeCallback<Void> callback) {
        for (Map.Entry<String, String> entry : timetableMap.entrySet())
        {
            mUserRemoteDAO.updateProperty(
                    FbUtil.getUserPropertyReference(userId,FbUser.PROPERTY_TIMETABLE) +"/"+entry.getKey() ,
                    entry.getValue(),
                    callback
            );
        }

    }

    @Override
    public void updateDisplayName(String name, String id, ThreeCallback<Void> callback) {

    }

    @Override
    public void readFromRemote(String id, TwoCallback<User> callback) {

    }

    @Override
    public void writeToLocal(User user, LocalDAOCallback<User> callback) {

    }

    @Override
    public void writeToRemote(String id, User user, ThreeCallback<Void> callback) {

    }

    @Override
    public void writeToRemoteIfNotExist(String id, User user, ThreeCallback<Void> callback) {
        mUserRemoteDAO.writeIfNotExist(id, user, callback);
    }


    @Override
    public void closeConnection() {

    }
}
