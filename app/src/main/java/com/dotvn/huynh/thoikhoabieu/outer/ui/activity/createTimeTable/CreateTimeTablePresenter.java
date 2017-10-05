package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.createTimeTable;

import android.util.Log;

import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.TimeTableInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.UserInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.manager.RemoteManagerBase;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.manager.RemoteManagerContainer;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 30/09/2017.
 */

public class CreateTimeTablePresenter implements CreateTimeTableContract.Presenter {
    private CreateTimeTableContract.View mView;
    private Interactors.TimeTableInteractor mTimeTableInteractor;
    private Interactors.UserInteractor mUserInteractor;
    private RemoteManagerBase mRemoteManager;
    public CreateTimeTablePresenter(CreateTimeTableContract.View view) {
        this.mView = view;
        mTimeTableInteractor = new TimeTableInteractor();
        mUserInteractor = new UserInteractor();
        mRemoteManager = RemoteManagerContainer.getRemoteManager();
    }

    /**
     * Just add to local, don't add to remote. Only add to remote when user sync data.
     * @param timeTable
     */
    @Override
    public void addTimeTable(final TimeTable timeTable) {

        mTimeTableInteractor.writeToLocal(timeTable, new LocalDAOCallback<TimeTable>() {
            @Override
            public void onSuccess(TimeTable item) {
                mView.showToast("Tạo thời khóa biểu local thành công");
            }

            @Override
            public void onSuccess(List<TimeTable> items) {
                mView.showToast("Tạo thời khóa biểu local thành công");

            }

            @Override
            public void onSuccess() {
                mView.showToast("Tạo thời khóa biểu local thành công");
            }

            @Override
            public void onError(Throwable error) {
                mView.showToast("Tạo thời khóa biểu local thất bại" + error.getMessage());
                Log.d(CreateTimeTableActivity.class.getSimpleName(), error.getMessage());
            }
        });
//        mTimeTableInteractor.writeToRemote(timeTable, new ThreeCallback<String>() {
//            @Override
//            public void onComplete() {
//
//            }
//
//            @Override
//            public void onSuccess(String key) {
//                if (!mRemoteManager.isLogined()) {
//                    return;
//                }
//                Map<String, String> timeTableMap = new HashMap<String, String>();
//                timeTableMap.put(key, timeTable.getName());
//                mUserInteractor.addTimeTableKeyToOwnListToRemote(
//                        mRemoteManager.getCurrentUser().getPhoneNumber(),
//                        timeTableMap, new ThreeCallback<Void>() {
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onSuccess(Void result) {
//                        mView.showToast("Thêm thời khóa biểu thành công");
//                    }
//
//                    @Override
//                    public void onFailure(String message) {
//                        mView.showToast("Thêm thời khóa biểu thất bại "+message);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//            }
//        });
    }
}
