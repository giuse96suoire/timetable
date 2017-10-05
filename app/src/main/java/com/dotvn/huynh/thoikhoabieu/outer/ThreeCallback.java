package com.dotvn.huynh.thoikhoabieu.outer;

/**
 * Created by huynh.mh on 9/26/2017.
 */

public interface ThreeCallback<T> {
    void onComplete();
    void onSuccess(T result);
    void onFailure(String message);
}
