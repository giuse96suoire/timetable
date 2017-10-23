package com.dotvn.huynh.thoikhoabieu.middle.converter;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmItemOfDay;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbItemOfDay;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Manh Hoang Huynh on 11/09/2017.
 */

public  class ItemOfDayConverter {

    /**
     * Convert from realm model to model (inner model)
     *
     * @param realmItemOfDay realm model to be converted
     * @return
     */
    public static ItemOfDay fromRealmToModel(RealmItemOfDay realmItemOfDay) {
        return new ItemOfDay(
                realmItemOfDay.getId(),
                realmItemOfDay.getStartTime(),
                realmItemOfDay.getEndTime(),
                SubjectConverter.fromRealmToModel(realmItemOfDay.getRealmSubject()),
                OtherConverter.fromRealmToModel(realmItemOfDay.getRealmOther()),
                realmItemOfDay.getEvent()
        );
    }


    /**
     * Convert from model to realm model (outer model)
     *
     * @param itemOfDay model to be converted
     * @return
     */
    public static RealmItemOfDay fromModelToRealm(ItemOfDay itemOfDay) {
        return new RealmItemOfDay(
                itemOfDay.getId(),
                itemOfDay.getStartTime(),
                itemOfDay.getEndTime(),
                SubjectConverter.fromModelToRealm(itemOfDay.getSubject()),
                OtherConverter.fromModelToRealm(itemOfDay.getOther()),
                itemOfDay.getEvent()
        );
    }

    /**
     * Convert from list realm model to list model (inner model)
     *
     * @param listRealmItemOfDay ist realm model to be converted
     * @return
     */
    public static List<ItemOfDay> fromListRealmToListModel(List<RealmItemOfDay> listRealmItemOfDay) {
        List<ItemOfDay> listItemOfDay = new ArrayList<>();
        if (listRealmItemOfDay != null && listRealmItemOfDay.size() != 0) {
            for (RealmItemOfDay realmItemOfDay : listRealmItemOfDay) {
                listItemOfDay.add(fromRealmToModel(realmItemOfDay));
            }
        }
        return listItemOfDay;
    }

    /**
     * Convert from list model to list realm (outer model)
     *
     * @param listItemOfDay ist realm model to be converted
     * @return
     */
    public static RealmList<RealmItemOfDay> fromListModelToListRealm(List<ItemOfDay> listItemOfDay) {
        RealmList<RealmItemOfDay> listRealmItemOfDay = new RealmList<>();
        if (listItemOfDay != null && listItemOfDay.size() != 0) {
            for (ItemOfDay itemOfDay : listItemOfDay) {
                listRealmItemOfDay.add(fromModelToRealm(itemOfDay));
            }
        }
        return listRealmItemOfDay;
    }

    public static List<ItemOfDay> fromListFirebaseToListModel(List<FbItemOfDay> fbItemOfDays) {
        List<ItemOfDay> result = new ArrayList<>();
        for (FbItemOfDay fbItemOfDay : fbItemOfDays) {
            result.add(fromFirebaseToModel(fbItemOfDay));
        }
        return result;
    }

    public static List<FbItemOfDay> fromListModelToListFirebase(List<ItemOfDay> itemOfDays) {
        List<FbItemOfDay> result = new ArrayList<>();
        for (ItemOfDay itemOfDay : itemOfDays) {
            result.add(fromModelToFirebase(itemOfDay));
        }
        return result;
    }

    public static ItemOfDay fromFirebaseToModel(FbItemOfDay fbItemOfDay) {
        return new ItemOfDay(
                fbItemOfDay.getId(),
                fbItemOfDay.getStartTime(),
                fbItemOfDay.getEndTime(),
                SubjectConverter.fromFirebaseToModel(fbItemOfDay.getSubject()),
                OtherConverter.fromFirebaseToModel(fbItemOfDay.getOther()),
                fbItemOfDay.getEvent()
        );
    }

    public static FbItemOfDay fromModelToFirebase(ItemOfDay itemOfDay) {
        return new FbItemOfDay(
                itemOfDay.getId(),
                itemOfDay.getStartTime(),
                itemOfDay.getEndTime(),
                SubjectConverter.fromModelToFirebase(itemOfDay.getSubject()),
                OtherConverter.fromModelToFirebase(itemOfDay.getOther()),
                itemOfDay.getEvent()
        );
    }
}
