package com.dotvn.huynh.thoikhoabieu.outer.data.remote;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 * This class use to trigger callback after interact with db complete
 */

public interface RemoteDAOCallback<T> {
    void onSuccess(T result);
    void onError(Exception error);
}
