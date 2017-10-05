package com.dotvn.huynh.thoikhoabieu.outer.ui;

/**
 * Created by Manh Hoang Huynh on 02/10/2017.
 */

public interface BaseConstract {
    interface View {
        void showToast(String message);
        void showToast(int messageId);
        void showProgressDialog();
        void dismissProgressDialog();
    }
}
