package com.dotvn.huynh.thoikhoabieu.middle.converter;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmFriend;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbFriend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class FriendConverter {

    public static Friend fromFirebaseToModel(FbFriend firebaseFriend) {
        if (firebaseFriend == null) {
            return null;
        }
        return new Friend(
                firebaseFriend.getDisplayName(),
                firebaseFriend.getPhotoUrl(),
                firebaseFriend.getUid(),
                firebaseFriend.getPhoneNumber()
        );
    }

    public static  Friend fromMapFirebaseToModel(Map<String, String> firebaseFriend) {
        Friend friend = new Friend();
        for (Map.Entry<String, String> entry : firebaseFriend.entrySet()) {
            friend.setPhoneNumber(entry.getKey());
            friend.setUid(entry.getKey());
            friend.setDisplayName(entry.getValue());
        }
        return friend;
    }

    public static  FbFriend fromModelToFirebase(Friend friend) {
        if (friend == null) {
            return null;
        }
        return new FbFriend(
                friend.getDisplayName(),
                friend.getPhotoUrl(),
                friend.getUid(),
                friend.getPhoneNumber()
        );
    }

    public static  Map<String, String> fromModelToMapFirebase(Friend friend) {
        if (friend == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>(1);
        result.put(friend.getPhoneNumber(), friend.getDisplayName());
        return result;
    }

    public static  Map<String, String> fromListModelToMapFirebase(List<Friend> listFriend) {
        if (listFriend == null) {
            return null;
        }
        if (listFriend == null || listFriend.size() == 0) {
            return null;
        }
        Map<String, String> result = new HashMap<>(listFriend.size());
        for (Friend friend : listFriend) {
            result.put(friend.getPhoneNumber(), friend.getDisplayName());
        }
        return result;
    }

    public static  RealmFriend fromModelToRealm(Friend friend) {
        if (friend == null) {
            return null;
        }
        return new RealmFriend(
                friend.getDisplayName(),
                friend.getPhotoUrl(),
                friend.getUid(),
                friend.getPhoneNumber()
        );
    }

    public static  Friend fromRealmToModel(RealmFriend realmFriend) {
        if (realmFriend == null) {
            return null;
        }
        return new Friend(
                realmFriend.getDisplayName(),
                realmFriend.getPhotoUrl(),
                realmFriend.getUid(),
                realmFriend.getPhoneNumber()
        );
    }

    public static  Map<String, Friend> fromMapFirebaseToMapModel(Map<String, FbFriend> fbMap) {
        if (fbMap == null) {
            return null;
        }
        Map<String, Friend> result = new HashMap<>();
        for (Map.Entry<String, FbFriend> entry : fbMap.entrySet()) {
            result.put(entry.getKey(),
                    fromFirebaseToModel(entry.getValue()));
        }
        return result;
    }

    public static  Map<String, FbFriend> fromMapModelToMapFirebase(List<Friend> list) {
        if (list == null)
            return null;
        Map<String, FbFriend> result = new HashMap<>();
        for (Friend friend : list) {
            result.put(friend.getPhoneNumber(),
                    fromModelToFirebase(friend));
        }
        return result;
    }

    public static RealmList<RealmFriend> fromListModelToListRealm(List<Friend> list) {
        if (list == null) {
            return null;
        }
        RealmList<RealmFriend> result = new RealmList<>();
        for (Friend friend : list) {
            result.add(fromModelToRealm(friend));
        }
        return result;
    }

    public static List<Friend> fromMapFirebaseToListModel(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        List<Friend> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Friend friend = new Friend();
            friend.setPhoneNumber(entry.getKey());
            friend.setUid(entry.getKey());
            friend.setDisplayName(entry.getValue());
        }
        return result;
    }

    public static List<Friend> fromListRealmToListModel(List<RealmFriend> realmFriends) {
        if (realmFriends == null) {
            return null;
        }
        List<Friend> result = new ArrayList<>();
        for (RealmFriend realmFriend : realmFriends) {
            result.add(fromRealmToModel(realmFriend));
        }
        return result;
    }

    public static Friend fromUserModelToFriendModel(User user) {
        if (user == null) {
            return null;
        }
        return new Friend(
                user.getDisplayName() == null || user.getDisplayName().length() == 0 ? user.getPhoneNumber() : user.getDisplayName(),
                user.getPhotoUrl(),
                user.getUid(),
                user.getPhoneNumber());
    }
}
