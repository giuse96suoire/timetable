package com.dotvn.huynh.thoikhoabieu.outer.data.remote.manager;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class RemoteManagerContainer {
    public static RemoteManagerBase getRemoteManager() {
        return new FirebaseRemoteManager();
    }
}
