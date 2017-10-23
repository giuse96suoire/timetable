package com.dotvn.huynh.thoikhoabieu.middle.converter;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Other;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmOther;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbOther;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Manh Hoang Huynh on 07/09/2017.
 */

public class OtherConverter {
 
    /**
     * Convert from  model to realm(outer model)
     *
     * @param other is model to be converted
     * @return RealmOther
     */
    public static RealmOther fromModelToRealm(Other other) {
        if (other == null) {
            return new RealmOther();
        }
        return new RealmOther(
                other.getId(),
                other.getTitle(),
                other.getDescription()
        );
    }

    /**
     * Convert from realm to model (inner model)
     *
     * @param realmOther ist realm model to be converted
     * @return Other
     */
    public static Other fromRealmToModel(RealmOther realmOther) {
        if (realmOther == null) {
            return new Other();
        }
        return new Other(
                realmOther.getId(),
                realmOther.getTitle(),
                realmOther.getDescription()
        );
    }

    /**
     * Convert from  List realm to List model (inner model)
     *
     * @param realmListOther is list realm model to be converted
     * @return List<Other>
     */
    public static List<Other> fromListRealmToListModel(List<RealmOther> realmListOther) {
        List<Other> listOther = new ArrayList<>();
        if (realmListOther != null && realmListOther.size() != 0) {
            for (RealmOther realmOther : realmListOther) {
                listOther.add(fromRealmToModel(realmOther));
            }
        }
        return listOther;
    }

    /**
     * Convert from list realm to list model(inner model)
     *
     * @param listOther is model to be converted
     * @return RealmList<RealmOther>
     */
    public static RealmList<RealmOther> fromListModelToListRealm(List<Other> listOther) {
        RealmList<RealmOther> listRealmOther = new RealmList<>();
        if (listOther != null && listOther.size() != 0) {
            for (Other other : listOther) {
                listRealmOther.add(fromModelToRealm(other));
            }
        }
        return listRealmOther;
    }
    public static Other fromFirebaseToModel(FbOther fbOther) {
        return new Other(
                fbOther.getId(),
                fbOther.getTitle(),
                fbOther.getDescription()
        );
    }

    public static FbOther fromModelToFirebase(Other other) {
        return new FbOther(
                other.getId(),
                other.getTitle(),
                other.getDescription()
        );
    }
}
