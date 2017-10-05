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

public class ItemOfDayConverter implements BaseConverter<ItemOfDay, RealmItemOfDay> {
    private static ItemOfDayConverter mInstance;

    public static ItemOfDayConverter getInstance() {
        if (mInstance == null) {
            mInstance = new ItemOfDayConverter();
        }
        return mInstance;
    }

    /**
     * Convert from realm model to model (inner model)
     *
     * @param realmItemOfDay realm model to be converted
     * @return
     */
    @Override
    public ItemOfDay fromRealmToModel(RealmItemOfDay realmItemOfDay) {
        return new ItemOfDay(
                realmItemOfDay.getId(),
                realmItemOfDay.getStartTime(),
                realmItemOfDay.getEndTime(),
                SubjectConverter.getInstance().fromRealmToModel(realmItemOfDay.getRealmSubject()),
                OtherConverter.getInstance().fromRealmToModel(realmItemOfDay.getRealmOther()),
                realmItemOfDay.getEvent()
        );
    }


    /**
     * Convert from model to realm model (outer model)
     *
     * @param itemOfDay model to be converted
     * @return
     */
    public RealmItemOfDay fromModelToRealm(ItemOfDay itemOfDay) {
        return new RealmItemOfDay(
                itemOfDay.getId(),
                itemOfDay.getStartTime(),
                itemOfDay.getEndTime(),
                SubjectConverter.getInstance().fromModelToRealm(itemOfDay.getSubject()),
                OtherConverter.getInstance().fromModelToRealm(itemOfDay.getOther()),
                itemOfDay.getEvent()
        );
    }

    /**
     * Convert from list realm model to list model (inner model)
     *
     * @param listRealmItemOfDay ist realm model to be converted
     * @return
     */
    public List<ItemOfDay> fromListRealmToListModel(List<RealmItemOfDay> listRealmItemOfDay) {
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
    public RealmList<RealmItemOfDay> fromListModelToListRealm(List<ItemOfDay> listItemOfDay) {
        RealmList<RealmItemOfDay> listRealmItemOfDay = new RealmList<>();
        if (listItemOfDay != null && listItemOfDay.size() != 0) {
            for (ItemOfDay itemOfDay : listItemOfDay) {
                listRealmItemOfDay.add(fromModelToRealm(itemOfDay));
            }
        }
        return listRealmItemOfDay;
    }

    public List<ItemOfDay> fromListFirebaseToListModel(List<FbItemOfDay> fbItemOfDays) {
        List<ItemOfDay> result = new ArrayList<>();
        for (FbItemOfDay fbItemOfDay : fbItemOfDays) {
            result.add(fromFirebaseToModel(fbItemOfDay));
        }
        return result;
    }

    public List<FbItemOfDay> fromListModelToListFirebase(List<ItemOfDay> itemOfDays) {
        List<FbItemOfDay> result = new ArrayList<>();
        for (ItemOfDay itemOfDay : itemOfDays) {
            result.add(fromModelToFirebase(itemOfDay));
        }
        return result;
    }

    public ItemOfDay fromFirebaseToModel(FbItemOfDay fbItemOfDay) {
        return new ItemOfDay(
                fbItemOfDay.getId(),
                fbItemOfDay.getStartTime(),
                fbItemOfDay.getEndTime(),
                SubjectConverter.getInstance().fromFirebaseToModel(fbItemOfDay.getSubject()),
                OtherConverter.getInstance().fromFirebaseToModel(fbItemOfDay.getOther()),
                fbItemOfDay.getEvent()
        );
    }

    public FbItemOfDay fromModelToFirebase(ItemOfDay itemOfDay) {
        return new FbItemOfDay(
                itemOfDay.getId(),
                itemOfDay.getStartTime(),
                itemOfDay.getEndTime(),
                SubjectConverter.getInstance().fromModelToFirebase(itemOfDay.getSubject()),
                OtherConverter.getInstance().fromModelToFirebase(itemOfDay.getOther()),
                itemOfDay.getEvent()
        );
    }
}
