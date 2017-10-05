package com.dotvn.huynh.thoikhoabieu.inner.data.interactors;

import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.dotvn.huynh.thoikhoabieu.outer.TwoCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.DAOContainer;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.RemoteDAO;

/**
 * Created by Manh Hoang Huynh on 07/09/2017.
 */

public class TimeTableInteractor implements Interactors.TimeTableInteractor {
    private RemoteDAO<TimeTable> mRemoteDAO;
    private LocalDAO<TimeTable> mLocalDAO;
    public  TimeTableInteractor() {
        mRemoteDAO = DAOContainer.getRemoteTimeTableDAO();
        mLocalDAO = DAOContainer.getLocalTimeTableDAO();
    }


    @Override
    public void readAllFromLocal(LocalDAOCallback<TimeTable> callback) {
        mLocalDAO.getAll(callback);
    }

    @Override
    public void readFromLocal(String timeTableId, LocalDAOCallback<TimeTable> callback) {

    }

    @Override
    public void readAllFromRemote(TwoCallback<TimeTable> callback) {

    }

    @Override
    public void writeToLocal(TimeTable timeTable, LocalDAOCallback<TimeTable> callback) {
        mLocalDAO.insert(timeTable, callback);
    }

    @Override
    public void writeToRemote(TimeTable timeTable, ThreeCallback<String> callback) {
        mRemoteDAO.writeWithRandomId(timeTable, callback);
    }

    @Override
    public void updateToRemote(TimeTable timeTable, ThreeCallback<Void> callback) {

    }

    @Override
    public void removeFromLocal(TimeTable timeTable) {

    }

    @Override
    public void removeFromRemote(String id, ThreeCallback<TimeTable> callback) {
        mRemoteDAO.remove(id, callback);
    }


    @Override
    public void removeAllFromLocal() {

    }

    @Override
    public void removeAllFromRemote(ThreeCallback<TimeTable> callback) {
    }


    @Override
    public void closeConnection() {

    }
}
