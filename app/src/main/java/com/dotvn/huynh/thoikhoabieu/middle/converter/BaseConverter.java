package com.dotvn.huynh.thoikhoabieu.middle.converter;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Manh Hoang Huynh on 12/09/2017.
 */

public interface BaseConverter<I, O extends RealmObject> {
    I fromRealmToModel(O realmObject);

    List<I> fromListRealmToListModel(List<O> realmList);

    O fromModelToRealm(I object);

    RealmList<O> fromListModelToListRealm(List<I> listObject);
}
