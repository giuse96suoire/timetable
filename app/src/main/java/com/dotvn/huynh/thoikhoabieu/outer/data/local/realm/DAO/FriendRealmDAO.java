package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.middle.converter.FriendConverter;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmFriend;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 24/09/2017.
 * Thoose class that need call it should not know what is it, just only LocalDAO
 */

public class FriendRealmDAO extends LocalDAO<Friend> {

    private RealmDbHelper<RealmFriend> mRealmDbHelper; // compose

    public FriendRealmDAO() {
        this.mRealmDbHelper = new RealmDbHelper<>();
    }

    @Override
    public void get(String id,final LocalDAOCallback<Friend> callback) {
        mRealmDbHelper.getAsync(RealmFriend.PHONE_NUMBER, id, RealmFriend.class, new LocalDAOCallback<RealmFriend>() {
            @Override
            public void onSuccess(RealmFriend item) {
                if (item.isValid()) {
                    callback.onSuccess(FriendConverter.fromRealmToModel(item));
                } else {
                    callback.onSuccess(FriendConverter.fromRealmToModel(null));
                }
            }

            @Override
            public void onSuccess(List<RealmFriend> items) {

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
    public void getByProperty(String compareWith, String valueToCompare, LocalDAOCallback<Friend> callback) {

    }

    @Override
    public void getAll(final LocalDAOCallback<Friend> callback) {
        mRealmDbHelper.getAllAsync(RealmFriend.class, new LocalDAOCallback<RealmFriend>() {
            @Override
            public void onSuccess(RealmFriend item) {

            }

            @Override
            public void onSuccess(List<RealmFriend> items) {
                callback.onSuccess(FriendConverter.fromListRealmToListModel(items));
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
    public void insert(Friend item, LocalDAOCallback<Friend> callback) {
        mRealmDbHelper.insert(FriendConverter.fromModelToRealm(item), callback);
    }

    @Override
    public void insertOrUpdate(Friend item, LocalDAOCallback<Friend> callback) {

    }

    @Override
    public void update(Friend item, LocalDAOCallback<Friend> callback) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteList(List<Friend> listItem, LocalDAOCallback<Friend> callback) {

    }


    @Override
    public void delete(Friend item) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void close() {

    }
}