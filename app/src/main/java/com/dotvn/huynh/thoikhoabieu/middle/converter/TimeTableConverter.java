package com.dotvn.huynh.thoikhoabieu.middle.converter;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmFriend;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmOneDay;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmTimeTable;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbTimeTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by huynh.mh on 9/25/2017.
 */

public class TimeTableConverter {
 
    public static TimeTable fromFirebaseToModel(FbTimeTable fbTimeTable) {
        return new TimeTable(
                fbTimeTable.getId(),
                fbTimeTable.getName(),
                fbTimeTable.getDescription(),
                OneDayConverter.fromMapFirebaseToListModel(fbTimeTable.getData()),
                fbTimeTable.getOwnerId(),
                FriendConverter.fromMapFirebaseToListModel(fbTimeTable.getCanRead()),
                FriendConverter.fromMapFirebaseToListModel(fbTimeTable.getCanWrite())
        );
    }

    public static FbTimeTable fromModelToFirebase(TimeTable timeTable) {
        return new FbTimeTable(
                timeTable.getId(),
                timeTable.getName(),
                timeTable.getDescription(),
                OneDayConverter.fromListModelToMapFirebase(timeTable.getData()),
                timeTable.getOwnerId(),
                FriendConverter.fromListModelToMapFirebase(timeTable.getCanRead()),
                FriendConverter.fromListModelToMapFirebase(timeTable.getCanWrite())
        );
    }

    public static TimeTable fromRealmToModel(RealmTimeTable realmTimeTable) {

        return  new TimeTable(
                realmTimeTable.getId(),
                realmTimeTable.getName(),
                realmTimeTable.getDescription(),
                OneDayConverter.fromListRealmToListModel(realmTimeTable.getData()),
                realmTimeTable.getOwnerId(),
                FriendConverter.fromListRealmToListModel(realmTimeTable.getCanRead()),
                FriendConverter.fromListRealmToListModel(realmTimeTable.getCanWrite())
        );
    }

    public static RealmTimeTable fromModelToRealm(TimeTable timeTable) {
        if (timeTable == null) {
            return null;
        }
        RealmTimeTable result = new RealmTimeTable();
        result.setId(timeTable.getId());
        result.setName(timeTable.getName());
        result.setDescription(timeTable.getDescription());
        RealmList<RealmOneDay> realmOneDays = new RealmList<>();
        if (timeTable.getData() != null) {
            for (OneDay oneDay : timeTable.getData()) {
                realmOneDays.add(OneDayConverter.fromModelToRealm(oneDay));
            }
        }

        result.setData(realmOneDays);
        RealmList<RealmFriend> canRead = FriendConverter.fromListModelToListRealm(timeTable.getCanRead());
        RealmList<RealmFriend> canWrite = FriendConverter.fromListModelToListRealm(timeTable.getCanWrite());
        result.setCanRead(canRead);
        result.setCanWrite(canWrite);
        return result;
    }

    public static List<TimeTable> fromListRealmToListModel(List<RealmTimeTable> realmTimeTable) {
        List<TimeTable> result = new ArrayList<>();
        for (RealmTimeTable rt : realmTimeTable) {
            result.add(fromRealmToModel(rt));
        }
        return result;
    }

}
