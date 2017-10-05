package com.dotvn.huynh.thoikhoabieu.inner.data.interactors;

import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.outer.data.DAOContainer;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;

/**
 * Created by Manh Hoang Huynh on 07/09/2017.
 */

public class OneDayInteractor implements Interactors.OneDayInteractor {
    private LocalDAO<OneDay> mLocalOneDayDAO;

    public OneDayInteractor() {
        mLocalOneDayDAO = DAOContainer.getLocalOneDayDAO();
    }

    @Override
    public void readOneDayDataFromLocal(String dayOfWeek, LocalDAOCallback<OneDay> callback) {
        mLocalOneDayDAO.get(dayOfWeek, callback);
    }

    @Override
    public void writeOneDayDataToLocal(OneDay oneDay, LocalDAOCallback<OneDay> callback) {
        mLocalOneDayDAO.insertOrUpdate(oneDay, callback);
    }

    @Override
    public void updateOneDayDataToLocal(OneDay oneDay, LocalDAOCallback<OneDay> callback) {
        mLocalOneDayDAO.insertOrUpdate(oneDay, callback);
    }

    @Override
    public void closeConnection() {
        mLocalOneDayDAO.close();
    }


}
