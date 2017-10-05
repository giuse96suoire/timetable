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
    private static TimeTableConverter mInstance;

    public static TimeTableConverter getInstance() {
        if (mInstance == null) {
            mInstance = new TimeTableConverter();
        }
        return mInstance;
    }

    public TimeTable fromFirebaseToModel(FbTimeTable fbTimeTable) {
        return new TimeTable(
                fbTimeTable.getId(),
                fbTimeTable.getName(),
                fbTimeTable.getDescription(),
                OneDayConverter.getInstance().fromMapFirebaseToListModel(fbTimeTable.getData()),
                fbTimeTable.getOwnerId(),
                FriendConverter.getInstance().fromMapFirebaseToListModel(fbTimeTable.getCanRead()),
                FriendConverter.getInstance().fromMapFirebaseToListModel(fbTimeTable.getCanWrite())
        );
    }

    public FbTimeTable fromModelToFirebase(TimeTable timeTable) {
        return new FbTimeTable(
                timeTable.getId(),
                timeTable.getName(),
                timeTable.getDescription(),
                OneDayConverter.getInstance().fromListModelToMapFirebase(timeTable.getData()),
                timeTable.getOwnerId(),
                FriendConverter.getInstance().fromListModelToMapFirebase(timeTable.getCanRead()),
                FriendConverter.getInstance().fromListModelToMapFirebase(timeTable.getCanWrite())
        );
    }

    public TimeTable fromRealmToModel(RealmTimeTable realmTimeTable) {

        return  new TimeTable(
                realmTimeTable.getId(),
                realmTimeTable.getName(),
                realmTimeTable.getDescription(),
                OneDayConverter.getInstance().fromListRealmToListModel(realmTimeTable.getData()),
                realmTimeTable.getOwnerId(),
                FriendConverter.getInstance().fromListRealmToListModel(realmTimeTable.getCanRead()),
                FriendConverter.getInstance().fromListRealmToListModel(realmTimeTable.getCanWrite())
        );
    }

    public RealmTimeTable fromModelToRealm(TimeTable timeTable) {
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
                realmOneDays.add(OneDayConverter.getInstance().fromModelToRealm(oneDay));
            }
        }

        result.setData(realmOneDays);
        RealmList<RealmFriend> canRead = FriendConverter.getInstance().fromListModelToListRealm(timeTable.getCanRead());
        RealmList<RealmFriend> canWrite = FriendConverter.getInstance().fromListModelToListRealm(timeTable.getCanWrite());
        result.setCanRead(canRead);
        result.setCanWrite(canWrite);
        return result;
    }

    public List<TimeTable> fromListRealmToListModel(List<RealmTimeTable> realmTimeTable) {
        List<TimeTable> result = new ArrayList<>();
        for (RealmTimeTable rt : realmTimeTable) {
            result.add(fromRealmToModel(rt));
        }
        return result;
    }

}
