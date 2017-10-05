package com.dotvn.huynh.thoikhoabieu.outer.data.local;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 08/09/2017.
 * This class use to trigger callback after interact with db complete
 */

public interface LocalDAOCallback<T> {
    void onSuccess(T item);
    void onSuccess(List<T> items);
    void onSuccess();
    void onError(Throwable error);
}
