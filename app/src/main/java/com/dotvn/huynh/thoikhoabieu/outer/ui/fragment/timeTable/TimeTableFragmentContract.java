package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.timeTable;

import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmOneDay;
import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;

/**
 * Created by Manh Hoang Huynh on 05/08/2017.
 */

public interface TimeTableFragmentContract {
    interface View extends BaseConstract.View{
        void displayOneDayData(RealmOneDay oneDay);
        void showDialog();
        void showError();
        void showInfo();
    }
    interface Presenter {
        void loadData();
    }
    interface Interacter {

    }
}
