package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.main;

import android.content.Context;

import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.TimeTableInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.UserInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.DayConstant;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.FbConstant;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.FbUtil;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.manager.RemoteManagerBase;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.manager.RemoteManagerContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manh Hoang Huynh on 18/08/2017.
 */

public class MainPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.View mView;
    private Context mContext;
    private RemoteManagerBase mRemoteManager;
    private Interactors.TimeTableInteractor mTimeTableInteractor;
    private Interactors.UserInteractor mUserInteractor;

    public MainPresenter(Context context, MainActivityContract.View view) {
        this.mContext = context.getApplicationContext();
        this.mView = view;
        this.mRemoteManager = RemoteManagerContainer.getRemoteManager();
        this.mTimeTableInteractor = new TimeTableInteractor();
        this.mUserInteractor = new UserInteractor();
    }

    @Override
    public void loadData() {
        mView.showProgressDialog();
        mTimeTableInteractor.readAllFromLocal(new LocalDAOCallback<TimeTable>() {
            @Override
            public void onSuccess(TimeTable item) {
                // do nothing
            }

            @Override
            public void onSuccess(List<TimeTable> items) {
                mView.dismissProgressDialog();
                if (items == null || items.size() == 0) {
                    mTimeTableInteractor.writeToLocal(TimeTable.createNew("Thời khóa biểu", "Đây là thời khóa biểu mặc định"), null);
                } else {
                    mView.updateData(items);
                }
            }

            @Override
            public void onSuccess() {
                mView.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable error) {
                mView.dismissProgressDialog();
            }
        });
    }

    @Override
    public void regisUserLoginStateChagedCallBack() {
        mRemoteManager.regisOnLoginStateChanged(new RemoteManagerBase.OnLoginStateChangedCallback() {
            @Override
            public void onLogined(User user) {
                addUserIfNotExist(user);
                mView.updateUiForUserInfo(user);
            }

            @Override
            public void onLogout() {
                mView.removeUiWhenUserLogout();
            }
        });
    }

    @Override
    public void addUserIfNotExist(User user) {
        mUserInteractor.writeToRemoteIfNotExist(user.getPhoneNumber(), user, new ThreeCallback<Void>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Void result) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    public void syncData(TimeTable timeTable) {
        User cuser = mRemoteManager.getCurrentUser();
        if (cuser == null) {
            mView.showToast("Đồng bộ thất bại. Bạn chưa đăng nhập. ");
            return;
        }
        mView.onSyncRunning();
        mTimeTableInteractor.writeToRemote(timeTable, new ThreeCallback<String>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(String result) {
                mView.onSyncSuccess();
                mView.showToast("Đồng bộ thành công");
            }

            @Override
            public void onFailure(String message) {
                mView.showToast("Đồng bộ thất bại "+message);
                mView.onSyncFailure();
            }
        });
    }

}
