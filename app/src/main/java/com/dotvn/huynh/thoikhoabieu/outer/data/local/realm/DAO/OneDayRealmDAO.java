package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.middle.converter.OneDayConverter;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmOneDay;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 * Thoose class that need call it should not know what is it, just only LocalDAO
 */

public class OneDayRealmDAO extends LocalDAO<OneDay> {
    private RealmDbHelper<RealmOneDay> mRealmDbHelper; // compose

    public OneDayRealmDAO() {
        this.mRealmDbHelper = new RealmDbHelper<>();
    }

    @Override
    public void get(String id, final LocalDAOCallback<OneDay> callback) {
        mRealmDbHelper.getAsync(RealmOneDay.ID, id, RealmOneDay.class, new LocalDAOCallback<RealmOneDay>() {
            @Override
            public void onSuccess(RealmOneDay item) {
                if (item.isValid()) {
                    callback.onSuccess(OneDayConverter.fromRealmToModel(item));
                } else {
                    callback.onError(null);
                }
            }

            @Override
            public void onSuccess(List<RealmOneDay> items) {
                //  do not do anything here
            }

            @Override
            public void onSuccess() {
                //  do not do anything here
            }

            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void getByProperty(String compareWith, String valueToCompare, LocalDAOCallback<OneDay> callback) {

    }

    @Override
    public void getAll(final LocalDAOCallback<OneDay> callback) {
        mRealmDbHelper.getAllAsync(RealmOneDay.class, new LocalDAOCallback<RealmOneDay>() {
            @Override
            public void onSuccess(RealmOneDay item) {
                //  do not do anything here
            }

            @Override
            public void onSuccess(List<RealmOneDay> items) {
                callback.onSuccess(OneDayConverter.fromListRealmToListModel(items));
            }

            @Override
            public void onSuccess() {
                //  do not do anything here
            }

            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void insert(OneDay item, final LocalDAOCallback<OneDay> callback) {
        mRealmDbHelper.insert(OneDayConverter.fromModelToRealm(item), new LocalDAOCallback() {
            @Override
            public void onSuccess(Object item) {
                // do not do anything
            }

            @Override
            public void onSuccess(List items) {
                // do not do anything
            }

            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void insertOrUpdate(OneDay item, LocalDAOCallback<OneDay> callback) {
        mRealmDbHelper.update(OneDayConverter.fromModelToRealm(item), callback);
    }

    @Override
    public void update(OneDay item, LocalDAOCallback<OneDay> callback) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteList(List<OneDay> listItem, LocalDAOCallback<OneDay> callback) {

    }


    @Override
    public void delete(OneDay item) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void close() {
        mRealmDbHelper.close();
    }

}
