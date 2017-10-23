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

public class OneDayConverter {


    /**
     * Convert from realm to model (inner model)
     *
     * @param realmOneDay ist realm model to be converted
     * @return FbOneDay
     */
    public static OneDay fromRealmToModel(RealmOneDay realmOneDay) {
        if (realmOneDay == null) {
            return null;
        }
        List<ItemOfDay> listItemOfDay = new ArrayList<>();
        if (realmOneDay.getListItemOfDay() != null && realmOneDay.getListItemOfDay().size() != 0) {
            for (RealmItemOfDay realmItemOfDay : realmOneDay.getListItemOfDay()) {
                listItemOfDay.add(ItemOfDayConverter.fromRealmToModel(realmItemOfDay));
            }
        }
        return new OneDay(
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
    public static List<OneDay> fromListRealmToListModel(List<RealmOneDay> listRealmOneDay) {
        List<OneDay> listOneDay = new ArrayList<>();
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
    public static RealmOneDay fromModelToRealm(OneDay oneDay) {
        RealmList<RealmItemOfDay> listRealmItemOfDay = new RealmList<>();
        if (oneDay.getListItemOfDay() != null && oneDay.getListItemOfDay().size() != 0) {
            for (ItemOfDay itemOfDay : oneDay.getListItemOfDay()) {
                listRealmItemOfDay.add(ItemOfDayConverter.fromModelToRealm(itemOfDay));
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
    public static RealmList<RealmOneDay> fromListModelToListRealm(List<OneDay> listOneday) {
        RealmList<RealmOneDay> listRealmOneDay = new RealmList<>();
        if (listOneday != null && listOneday.size() != 0) {
            for (OneDay oneday : listOneday) {
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
    public static Map<String, FbOneDay> fromListModelToMapFirebase(List<OneDay> listOneDay) {
        if (listOneDay == null) {
            return null;
        }
        Map<String, FbOneDay> result = new HashMap<>(7);
        for (OneDay oneDay : listOneDay) {
            result.put(oneDay.getDayOfWeek(), fromModelToFirebase(oneDay));
        }
        return result;
    }

    public static OneDay fromFirebaseToModel(FbOneDay fbOneDay) {
        return new OneDay(
                fbOneDay.getId(),
                fbOneDay.getDayOfWeek(),
                ItemOfDayConverter.fromListFirebaseToListModel(fbOneDay.getListItemOfDay())
        );
    }

    public static FbOneDay fromModelToFirebase(OneDay oneDay) {
        return new FbOneDay(
                oneDay.getId(),
                oneDay.getDayOfWeek(),
                ItemOfDayConverter.fromListModelToListFirebase(oneDay.getListItemOfDay())
        );
    }

    public static Map<String, OneDay> fromMapFirebaseToMapModel(Map<String, FbOneDay> fbMap) {
        Map<String, OneDay> result = new HashMap<>();
        for (Map.Entry<String, FbOneDay> entry : fbMap.entrySet()) {
            result.put(entry.getKey(),
                    fromFirebaseToModel(entry.getValue()));
        }
        return result;
    }

    public static List<OneDay> fromMapFirebaseToListModel(Map<String, FbOneDay> fbMap) {
        List<OneDay> result = new ArrayList<>();
        for (Map.Entry<String, FbOneDay> entry : fbMap.entrySet()) {
            result.add(fromFirebaseToModel(entry.getValue()));
        }
        return result;
    }

    public static Map<String, FbOneDay> fromMapModelToMapFirebase(Map<String, OneDay> map) {
        Map<String, FbOneDay> result = new HashMap<>();
        for (Map.Entry<String, OneDay> entry : map.entrySet()) {
            result.put(entry.getKey(),
                    fromModelToFirebase(entry.getValue()));
        }
        return result;
    }
}
