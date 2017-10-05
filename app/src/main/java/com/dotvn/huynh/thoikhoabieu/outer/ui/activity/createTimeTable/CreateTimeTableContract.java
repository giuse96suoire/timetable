package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.createTimeTable;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;

/**
 * Created by Manh Hoang Huynh on 30/09/2017.
 */

public interface CreateTimeTableContract {
    interface View extends BaseConstract.View{
        void initView();
        void showSnackbar(int messageId);
        void showSnackbar(String message);
    }
    interface Presenter {
        void addTimeTable(TimeTable timeTable);
    }
}
