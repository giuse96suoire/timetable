package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.dayOfWeek;

import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;

/**
 * Created by Manh Hoang Huynh on 05/08/2017.
 */

public interface DayOfWeekContract {
    interface View extends BaseConstract.View{
        void showDialog();
        void showMessage();
        void showError();
    }

    interface Presenter {
        void loadData();
    }
}
