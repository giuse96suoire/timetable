package com.dotvn.huynh.thoikhoabieu.inner.data.interactors;

import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.outer.data.DAOContainer;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmItemOfDay;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 07/09/2017.
 */

public class ItemOfDayInteractor implements Interactors.ItemOfDayInteractor {
    private LocalDAO<ItemOfDay> mLocalItemOfDayDAO;

    public ItemOfDayInteractor() {
        mLocalItemOfDayDAO = DAOContainer.getLocalItemOfDayDAO();
    }


    @Override
    public void readAllFromLocal(LocalDAOCallback<ItemOfDay> callback) {

    }

    @Override
    public void readItemOfDayBySubject(Subject subject, LocalDAOCallback<ItemOfDay> callback) {
        mLocalItemOfDayDAO.getByProperty(RealmItemOfDay.SUBJECT_ID, subject.getId(), callback);
    }

    @Override
    public void writeToLocal(ItemOfDay itemOfDay, LocalDAOCallback<ItemOfDay> callback) {

    }

    @Override
    public void updateToLocal(ItemOfDay itemOfDay, LocalDAOCallback<ItemOfDay> callback) {
        mLocalItemOfDayDAO.insertOrUpdate(itemOfDay, callback);
    }

    @Override
    public void removeFromLocal(ItemOfDay itemOfDay) {

    }

    @Override
    public void removeListFromLocal(List<ItemOfDay> listItemOfDay, LocalDAOCallback<ItemOfDay> callback) {
        mLocalItemOfDayDAO.deleteList(listItemOfDay, callback);
    }

    @Override
    public void removeAllFromLocal() {

    }

    @Override
    public void closeConnection() {
        mLocalItemOfDayDAO.close();
    }
}
