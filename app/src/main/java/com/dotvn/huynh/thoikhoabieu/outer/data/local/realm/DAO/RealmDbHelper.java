package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO;

import android.util.Log;

import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Manh Hoang Huynh on 17/08/2017.
 */

public class RealmDbHelper<E extends RealmObject> {
    /**
     * mResult: this variable was declare in global mode because it will be collect by GC if
     * declare in local -> data will be not lived update
     */
    private RealmResults<E> mResult;
    private E mSingleRealmResult;
    private Realm mRealm;

    public RealmDbHelper() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("tkb.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        mRealm = Realm.getInstance(configuration);
    }

    public void insert(final E item, final LocalDAOCallback callback) {
        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(item);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });

    }

    public void update(final E item, final LocalDAOCallback callback) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(item);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onError(error);
            }
        });
    }

    public E get(String compareWith, String valueToCompare, Class<E> classObject) {
        return mRealm.where(classObject)
                .equalTo(compareWith, valueToCompare)
                .findFirst();
    }

    public void getAsync(String compareWith, String valueToCompare, final Class<E> classObject,
                         final LocalDAOCallback<E> callback) {
        mSingleRealmResult = mRealm.where(classObject)
                .equalTo(compareWith, valueToCompare)
                .findFirstAsync();
        RealmChangeListener<E> listener = new RealmChangeListener<E>() {
            @Override
            public void onChange(E e) {
                callback.onSuccess(e);
            }
        };
        mSingleRealmResult.addChangeListener(listener);
    }

    public void getAllAsync(String compareWith, String valueToCompare, final Class<E> classObject,
                            final LocalDAOCallback<E> callback) {
        mResult = mRealm.where(classObject)
                .equalTo(compareWith, valueToCompare)
                .findAllAsync();
        mResult.addChangeListener(new RealmChangeListener<RealmResults<E>>() {
            @Override
            public void onChange(RealmResults<E> es) {
                callback.onSuccess(es);
            }
        });
    }

    public void getAllAsync(final Class<E> classObject, final LocalDAOCallback<E> callback) {
        mResult = mRealm.where(classObject)
                .findAllAsync();
        mResult.addChangeListener(new RealmChangeListener<RealmResults<E>>() {
            @Override
            public void onChange(RealmResults<E> es) {
                callback.onSuccess(es);
            }
        });
    }

    public void removeById(final Class<E> classObject, final String compareWith, final String equalTo) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<E> result = realm.where(classObject).equalTo(compareWith, equalTo).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    public void removeList(final RealmList<E> realmListItem) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmListItem.deleteAllFromRealm();
            }
        });
    }

    public void removeAll(final Class<E> classObject) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<E> result = mRealm.where(classObject).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    public void close() {
        Log.d("xxx", "close connection ======================================================?");
        if (mResult == null) {
            return;
        }
        mResult.removeAllChangeListeners();
        mRealm.close();
    }
}
