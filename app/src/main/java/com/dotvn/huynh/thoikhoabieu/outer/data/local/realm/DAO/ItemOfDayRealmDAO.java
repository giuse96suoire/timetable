package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.middle.converter.ItemOfDayConverter;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmItemOfDay;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 * Thoose class that need call it should not know what is it, just only LocalDAO
 */

public class ItemOfDayRealmDAO extends LocalDAO<ItemOfDay> {
    private RealmDbHelper<RealmItemOfDay> mRealmDbHelper; // compose pattern

    public ItemOfDayRealmDAO() {
        this.mRealmDbHelper = new RealmDbHelper<>();
    }

    @Override
    public void get(String id, final LocalDAOCallback<ItemOfDay> callback) {
        mRealmDbHelper.getAsync(RealmItemOfDay.ID, id, RealmItemOfDay.class, new LocalDAOCallback<RealmItemOfDay>() {
            @Override
            public void onSuccess(RealmItemOfDay item) {
                if (item.isValid()) {
                    callback.onSuccess(ItemOfDayConverter.getInstance().fromRealmToModel(item));
                } else {
                    callback.onError(null);
                }
            }

            @Override
            public void onSuccess(List<RealmItemOfDay> items) {
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
    public void getByProperty(String compareWith, String valueToCompare,final LocalDAOCallback<ItemOfDay> callback) {
        mRealmDbHelper.getAllAsync(compareWith, valueToCompare, RealmItemOfDay.class, new LocalDAOCallback<RealmItemOfDay>() {
            @Override
            public void onSuccess(RealmItemOfDay item) {
                callback.onSuccess(ItemOfDayConverter.getInstance().fromRealmToModel(item));
            }

            @Override
            public void onSuccess(List<RealmItemOfDay> items) {
                callback.onSuccess(ItemOfDayConverter.getInstance().fromListRealmToListModel(items));
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
    public void getAll(final LocalDAOCallback<ItemOfDay> callback) {
        mRealmDbHelper.getAllAsync(RealmItemOfDay.class, new LocalDAOCallback<RealmItemOfDay>() {
            @Override
            public void onSuccess(RealmItemOfDay item) {
                //  do not do anything here
            }

            @Override
            public void onSuccess(List<RealmItemOfDay> items) {
                callback.onSuccess(ItemOfDayConverter.getInstance().fromListRealmToListModel(items));
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
    public void insert(ItemOfDay item, final LocalDAOCallback<ItemOfDay> callback) {
        mRealmDbHelper.insert(ItemOfDayConverter.getInstance().fromModelToRealm(item), new LocalDAOCallback() {
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
    public void insertOrUpdate(ItemOfDay item, LocalDAOCallback<ItemOfDay> callback) {
        mRealmDbHelper.update(ItemOfDayConverter.getInstance().fromModelToRealm(item), callback);
    }

    @Override
    public void update(ItemOfDay item, LocalDAOCallback<ItemOfDay> callback) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteList(List<ItemOfDay> listItem,  LocalDAOCallback<ItemOfDay> callback) {
        RealmList<RealmItemOfDay> realmItemOfDays = ItemOfDayConverter.getInstance().fromListModelToListRealm(listItem);
        if (realmItemOfDays != null) {
            for (RealmItemOfDay itemOfDay : realmItemOfDays) {
                mRealmDbHelper.removeById(RealmItemOfDay.class, RealmItemOfDay.ID, itemOfDay.getId());
            }
        }
        callback.onSuccess();
    }

    @Override
    public void delete(ItemOfDay item) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void close() {
        mRealmDbHelper.close();
    }

}
