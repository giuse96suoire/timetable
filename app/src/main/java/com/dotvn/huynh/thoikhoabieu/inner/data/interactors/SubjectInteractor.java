package com.dotvn.huynh.thoikhoabieu.inner.data.interactors;

import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.outer.data.DAOContainer;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;

/**
 * Created by Manh Hoang Huynh on 18/08/2017.
 */

public class SubjectInteractor implements Interactors.SubjectInteractor {

    private LocalDAO<Subject> mSubjectLocalDAO;
    private LocalDAO<ItemOfDay> mItemOfDayLocalDAO;

    public SubjectInteractor() {
        mSubjectLocalDAO = DAOContainer.getLocalSubjectDAO();
        mItemOfDayLocalDAO = DAOContainer.getLocalItemOfDayDAO();
    }

    @Override
    public void readAllFromLocal(LocalDAOCallback<Subject> callback) {
        mSubjectLocalDAO.getAll(callback);
    }

    @Override
    public void writeToLocal(Subject subject, LocalDAOCallback<Subject> callback) {
        mSubjectLocalDAO.insert(subject, callback);
    }

    @Override
    public void removeFromLocal(Subject subject) {
        mSubjectLocalDAO.delete(subject);
    }

    @Override
    public void removeAllFromLocal() {

    }

    @Override
    public void closeConnection() {
        mSubjectLocalDAO.close();
    }
}
