package com.dotvn.huynh.thoikhoabieu.outer.data.remote.manager;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public abstract class RemoteManagerBase {
    public abstract boolean isLogined();
    public abstract void regisOnLoginStateChanged(OnLoginStateChangedCallback callback);
    public abstract User getCurrentUser();
    public interface OnLoginStateChangedCallback {
        void onLogined(User user);
        void onLogout();
    }
}
