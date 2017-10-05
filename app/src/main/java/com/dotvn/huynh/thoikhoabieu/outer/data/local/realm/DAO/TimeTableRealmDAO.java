package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.middle.converter.TimeTableConverter;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmTimeTable;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 * Thoose class that need call it should not know what is it, just only LocalDAO
 */

public class TimeTableRealmDAO extends LocalDAO<TimeTable> {
    private RealmDbHelper<RealmTimeTable> mRealmDbHelper; // compose

    public TimeTableRealmDAO() {
        this.mRealmDbHelper = new RealmDbHelper<>();
    }

    @Override
    public void get(String id, final LocalDAOCallback<TimeTable> callback) {
        mRealmDbHelper.getAsync(RealmTimeTable.ID, id, RealmTimeTable.class, new LocalDAOCallback<RealmTimeTable>() {
            @Override
            public void onSuccess(RealmTimeTable item) {
                if (item.isValid()) {
                    callback.onSuccess(TimeTableConverter.getInstance().fromRealmToModel(item));
                } else {
                    callback.onError(null);
                }
            }

            @Override
            public void onSuccess(List<RealmTimeTable> items) {
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
    public void getByProperty(String compareWith, String valueToCompare, LocalDAOCallback<TimeTable> callback) {

    }

    @Override
    public void getAll(final LocalDAOCallback<TimeTable> callback) {
        mRealmDbHelper.getAllAsync(RealmTimeTable.class, new LocalDAOCallback<RealmTimeTable>() {
            @Override
            public void onSuccess(RealmTimeTable item) {
                //  do not do anything here
            }

            @Override
            public void onSuccess(List<RealmTimeTable> items) {
                callback.onSuccess(TimeTableConverter.getInstance().fromListRealmToListModel(items));
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
    public void insert(TimeTable item, final LocalDAOCallback<TimeTable> callback) {
        mRealmDbHelper.insert(TimeTableConverter.getInstance().fromModelToRealm(item), new LocalDAOCallback() {
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
                if (callback != null)
                    callback.onSuccess();
            }

            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void insertOrUpdate(TimeTable item, LocalDAOCallback<TimeTable> callback) {
        mRealmDbHelper.update(TimeTableConverter.getInstance().fromModelToRealm(item), callback);
    }

    @Override
    public void update(TimeTable item, LocalDAOCallback<TimeTable> callback) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteList(List<TimeTable> listItem, LocalDAOCallback<TimeTable> callback) {

    }


    @Override
    public void delete(TimeTable item) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void close() {
        mRealmDbHelper.close();
    }

}
