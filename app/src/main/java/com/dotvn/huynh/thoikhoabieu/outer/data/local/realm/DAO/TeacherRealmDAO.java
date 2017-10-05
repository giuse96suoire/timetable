package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Teacher;
import com.dotvn.huynh.thoikhoabieu.middle.converter.TeacherConverter;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmTeacher;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 * Thoose class that need call it should not know what is it, just only LocalDAO
 */

public class TeacherRealmDAO extends LocalDAO<Teacher> {
    private RealmDbHelper<RealmTeacher> mRealmDbHelper; // compose pattern

    public TeacherRealmDAO() {
        this.mRealmDbHelper = new RealmDbHelper();
    }

    @Override
    public void get(String id, final LocalDAOCallback<Teacher> callback) {
        mRealmDbHelper.getAsync(RealmTeacher.ID, id, RealmTeacher.class, new LocalDAOCallback<RealmTeacher>() {
            @Override
            public void onSuccess(RealmTeacher item) {
                if (item.isValid()) {
                    callback.onSuccess(TeacherConverter.getInstance().fromRealmToModel(item));
                } else {
                    callback.onError(null);
                }
            }

            @Override
            public void onSuccess(List<RealmTeacher> items) {
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
    public void getByProperty(String compareWith, String valueToCompare, LocalDAOCallback<Teacher> callback) {

    }

    @Override
    public void getAll(final LocalDAOCallback<Teacher> callback) {
        mRealmDbHelper.getAllAsync(RealmTeacher.class, new LocalDAOCallback<RealmTeacher>() {
            @Override
            public void onSuccess(RealmTeacher item) {
                //  do not do anything here
            }

            @Override
            public void onSuccess(List<RealmTeacher> items) {
                callback.onSuccess(TeacherConverter.getInstance().fromListRealmToListModel(items));
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
    public void insert(Teacher item, final LocalDAOCallback<Teacher> callback) {
        mRealmDbHelper.insert(TeacherConverter.getInstance().fromModelToRealm(item), new LocalDAOCallback() {
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
    public void insertOrUpdate(Teacher item, LocalDAOCallback<Teacher> callback) {
        mRealmDbHelper.update(TeacherConverter.getInstance().fromModelToRealm(item), callback);
    }

    @Override
    public void update(Teacher item, LocalDAOCallback<Teacher> callback) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteList(List<Teacher> listItem, LocalDAOCallback<Teacher> callback) {

    }


    @Override
    public void delete(Teacher item) {
        mRealmDbHelper.removeById(RealmTeacher.class, RealmTeacher.ID, item.getId());
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void close() {
        mRealmDbHelper.close();
    }

}
