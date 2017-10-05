package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.main;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 05/08/2017.
 */

public interface MainActivityContract {
    interface View extends BaseConstract.View{
        void showDialog();
        void showError();
        void showInfo();
        void stopSyncAnimation();
        void updateData(List<TimeTable> listTimeTable);
        void updateUiForUserInfo(User user);
        void removeUiWhenUserLogout();
        void initOptionMenuByContext(String tag);
        void changeTitle(String title);
        void onSyncSuccess();
        void onSyncFailure();
        void onSyncRunning();
        void setCurrentTimeTable(String timeTableId);
    }
    interface Presenter {
        void loadData();
        void regisUserLoginStateChagedCallBack();
        void addUserIfNotExist(User user);
        void syncData();
    }

}
