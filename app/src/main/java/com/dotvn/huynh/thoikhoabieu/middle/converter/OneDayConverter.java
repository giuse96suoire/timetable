package com.dotvn.huynh.thoikhoabieu.middle.converter;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmItemOfDay;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmOneDay;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbOneDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

/**
 * Created by Manh Hoang Huynh on 07/09/2017.
 */

public class OneDayConverter implements BaseConverter<com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay, RealmOneDay> {
    private static OneDayConverter mInstance;

    public static OneDayConverter getInstance() {
        if (mInstance == null) {
            mInstance = new OneDayConverter();
        }
        return mInstance;
    }

    /**
     * Convert from realm to model (inner model)
     *
     * @param realmOneDay ist realm model to be converted
     * @return FbOneDay
     */
    public com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay fromRealmToModel(RealmOneDay realmOneDay) {
        if (realmOneDay == null) {
            return null;
        }
        List<ItemOfDay> listItemOfDay = new ArrayList<>();
        if (realmOneDay.getListItemOfDay() != null && realmOneDay.getListItemOfDay().size() != 0) {
            for (RealmItemOfDay realmItemOfDay : realmOneDay.getListItemOfDay()) {
                listItemOfDay.add(ItemOfDayConverter.getInstance().fromRealmToModel(realmItemOfDay));
            }
        }
        return new com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay(
                realmOneDay.getId(),
                realmOneDay.getDayOfWeek(),
                listItemOfDay
        );
    }


    /**
     * Convert from  List realm to List model (inner model)
     *
     * @param listRealmOneDay is list realm model to be converted
     * @return List<FbOneDay>
     */
    @Override
    public List<OneDay> fromListRealmToListModel(List<RealmOneDay> listRealmOneDay) {
        List<com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay> listOneDay = new ArrayList<>();
        if (listRealmOneDay != null && listRealmOneDay.size() != 0) {
            for (RealmOneDay realmOneDay : listRealmOneDay) {
                listOneDay.add(fromRealmToModel(realmOneDay));
            }
        }
        return listOneDay;
    }

    /**
     * Convert from  model to realm(outer model)
     *
     * @param oneDay is model to be converted
     * @return RealmOneDay
     */
    public RealmOneDay fromModelToRealm(com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay oneDay) {
        RealmList<RealmItemOfDay> listRealmItemOfDay = new RealmList<>();
        if (oneDay.getListItemOfDay() != null && oneDay.getListItemOfDay().size() != 0) {
            for (ItemOfDay itemOfDay : oneDay.getListItemOfDay()) {
                listRealmItemOfDay.add(ItemOfDayConverter.getInstance().fromModelToRealm(itemOfDay));
            }
        }
        return new RealmOneDay(
                oneDay.getId(),
                oneDay.getDayOfWeek(),
                listRealmItemOfDay
        );
    }

    /**
     * Convert from list realm to list model(inner model)
     *
     * @param listOneday is model to be converted
     * @return RealmList<RealmOneDay>
     */
    public RealmList<RealmOneDay> fromListModelToListRealm(List<OneDay> listOneday) {
        RealmList<RealmOneDay> listRealmOneDay = new RealmList<>();
        if (listOneday != null && listOneday.size() != 0) {
            for (com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay oneday : listOneday) {
                listRealmOneDay.add(fromModelToRealm(oneday));
            }
        }
        return listRealmOneDay;
    }

    /**
     * Convert from  list model to firebase map(outer model)
     *
     * @param listOneDay is list model to be converted
     * @return RealmOneDay
     */
    public Map<String, FbOneDay> fromListModelToMapFirebase(List<OneDay> listOneDay) {
        if (listOneDay == null) {
            return null;
        }
        Map<String, FbOneDay> result = new HashMap<>(7);
        for (OneDay oneDay : listOneDay) {
            result.put(oneDay.getDayOfWeek(), fromModelToFirebase(oneDay));
        }
        return result;
    }

    public OneDay fromFirebaseToModel(FbOneDay fbOneDay) {
        return new com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay(
                fbOneDay.getId(),
                fbOneDay.getDayOfWeek(),
                ItemOfDayConverter.getInstance().fromListFirebaseToListModel(fbOneDay.getListItemOfDay())
        );
    }

    public FbOneDay fromModelToFirebase(com.dotvn.huynh.thoikhoabieu.inner.data.model.OneDay oneDay) {
        return new FbOneDay(
                oneDay.getId(),
                oneDay.getDayOfWeek(),
                ItemOfDayConverter.getInstance().fromListModelToListFirebase(oneDay.getListItemOfDay())
        );
    }

    public Map<String, OneDay> fromMapFirebaseToMapModel(Map<String, FbOneDay> fbMap) {
        Map<String, OneDay> result = new HashMap<>();
        for (Map.Entry<String, FbOneDay> entry : fbMap.entrySet()) {
            result.put(entry.getKey(),
                    fromFirebaseToModel(entry.getValue()));
        }
        return result;
    }

    public List<OneDay> fromMapFirebaseToListModel(Map<String, FbOneDay> fbMap) {
        List<OneDay> result = new ArrayList<>();
        for (Map.Entry<String, FbOneDay> entry : fbMap.entrySet()) {
            result.add(fromFirebaseToModel(entry.getValue()));
        }
        return result;
    }

    public Map<String, FbOneDay> fromMapModelToMapFirebase(Map<String, OneDay> map) {
        Map<String, FbOneDay> result = new HashMap<>();
        for (Map.Entry<String, OneDay> entry : map.entrySet()) {
            result.put(entry.getKey(),
                    fromModelToFirebase(entry.getValue()));
        }
        return result;
    }
}
