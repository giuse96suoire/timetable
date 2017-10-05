package com.dotvn.huynh.thoikhoabieu.outer.data.remote.manager;

import android.support.annotation.NonNull;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.middle.converter.UserConverter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class FirebaseRemoteManager extends RemoteManagerBase {
    FirebaseAuth mFirebaseAuth;

    public FirebaseRemoteManager() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean isLogined() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    @Override
    public void regisOnLoginStateChanged(final OnLoginStateChangedCallback callback) {
        mFirebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    callback.onLogout();
                } else {
                    callback.onLogined(UserConverter.getInstance().fromFirebaseToModel(firebaseUser));
                }
            }
        });
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        return UserConverter.getInstance().fromFirebaseToModel(firebaseUser);
    }
}
