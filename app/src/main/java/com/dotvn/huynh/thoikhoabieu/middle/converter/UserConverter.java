package com.dotvn.huynh.thoikhoabieu.middle.converter;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmUser;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbUser;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class UserConverter {
    private static UserConverter mInstance;

    public static UserConverter getInstance() {
        if (mInstance == null) {
            mInstance = new UserConverter();
        }
        return mInstance;
    }
    public User fromFirebaseToModel(FirebaseUser firebaseUser) {
        return new User(
                firebaseUser.getUid(),
                firebaseUser.getDisplayName(),
                firebaseUser.getEmail(),
                firebaseUser.getPhoneNumber(),
                firebaseUser.getPhotoUrl() == null ? "" : firebaseUser.getPhotoUrl().toString(),
                firebaseUser.getProviderId(),
                null
        );
    }
    public User fromFirebaseToModel(FbUser firebaseUser) {
        return new User(
                firebaseUser.getUid(),
                firebaseUser.getDisplayName(),
                firebaseUser.getEmail(),
                firebaseUser.getPhoneNumber(),
                firebaseUser.getPhotoUrl(),
                firebaseUser.getProviderId(),
                FriendConverter.fromMapFirebaseToListModel(firebaseUser.getFriends())
        );
    }

    public User fromRealmToModel(RealmUser realmUser) {
        return new User(
                realmUser.getUid(),
                realmUser.getDisplayName(),
                realmUser.getEmail(),
                realmUser.getPhoneNumber(),
                realmUser.getPhotoUrl(),
                realmUser.getProviderId(),
                FriendConverter.fromListRealmToListModel(realmUser.getListFriend())
        );
    }

    public RealmUser fromModelToRealm(User user) {
        return new RealmUser(
                user.getUid(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPhotoUrl(),
                user.getProviderId(),
                FriendConverter.fromListModelToListRealm(user.getFriends())
        );
    }

    public FbUser fromModelToFirebase(User user) {
        return new FbUser(
                user.getUid(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPhotoUrl(),
                user.getProviderId(),
                FriendConverter.fromListModelToMapFirebase(user.getFriends())
        );
    }
}
