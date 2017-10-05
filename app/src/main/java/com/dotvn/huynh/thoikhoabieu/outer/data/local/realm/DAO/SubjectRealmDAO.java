package com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.DAO;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Teacher;
import com.dotvn.huynh.thoikhoabieu.middle.converter.SubjectConverter;
import com.dotvn.huynh.thoikhoabieu.outer.data.DAOContainer;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmSubject;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 * Thoose class that need call it should not know what is it, just only LocalDAO
 * This class use for CRUD subject model
 */

public class SubjectRealmDAO extends LocalDAO<Subject> {

    private static SubjectRealmDAO mContext;

    public static SubjectRealmDAO getInstance() {
        if (mContext == null) {
            mContext = new SubjectRealmDAO();
        }
        return mContext;
    }

    private RealmDbHelper<RealmSubject> mRealmDbHelper; // compose pattern

    public SubjectRealmDAO() {
        this.mRealmDbHelper = new RealmDbHelper<>();
    }

    @Override
    public void get(String id, final LocalDAOCallback<Subject> callback) {
        mRealmDbHelper.getAsync(RealmSubject.ID, id, RealmSubject.class, new LocalDAOCallback<RealmSubject>() {
            @Override
            public void onSuccess(RealmSubject item) {
                if (item.isValid()) {
                    callback.onSuccess(SubjectConverter.getInstance().fromRealmToModel(item));
                } else {
                    callback.onError(null);
                }
            }

            @Override
            public void onSuccess(List<RealmSubject> items) {
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
    public void getByProperty(String compareWith, String valueToCompare, LocalDAOCallback<Subject> callback) {

    }

    @Override
    public void getAll(final LocalDAOCallback<Subject> callback) {
        mRealmDbHelper.getAllAsync(RealmSubject.class, new LocalDAOCallback<RealmSubject>() {
            @Override
            public void onSuccess(RealmSubject item) {
                //  do not do anything here
            }

            @Override
            public void onSuccess(List<RealmSubject> items) {
                callback.onSuccess(SubjectConverter.getInstance().fromListRealmToListModel(items));
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
    public void insert(final Subject subjectItem, final LocalDAOCallback<Subject> callback) {
        mRealmDbHelper.update(SubjectConverter.getInstance().fromModelToRealm(subjectItem), new LocalDAOCallback() {
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
    public void insertOrUpdate(Subject item, LocalDAOCallback<Subject> callback) {
        mRealmDbHelper.update(SubjectConverter.getInstance().fromModelToRealm(item), callback);
    }

    @Override
    public void update(Subject item, LocalDAOCallback<Subject> callback) {

    }

    @Override
    public void delete(String id) {
        mRealmDbHelper.removeById(RealmSubject.class, RealmSubject.NAME, id);
    }

    @Override
    public void deleteList(List<Subject> listItem, LocalDAOCallback<Subject> callback) {

    }


    @Override
    public void delete(Subject item) {
        mRealmDbHelper.removeById(RealmSubject.class, RealmSubject.ID, item.getId());
        LocalDAO<Teacher> teacherLocalDAO = DAOContainer.getLocalTeacerDAO();
        teacherLocalDAO.delete(item.getTeacher());
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void close() {
        mRealmDbHelper.close();
    }
}
