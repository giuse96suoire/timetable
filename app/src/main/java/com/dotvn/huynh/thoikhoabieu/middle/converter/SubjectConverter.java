package com.dotvn.huynh.thoikhoabieu.middle.converter;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmSubject;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbSubject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Manh Hoang Huynh on 07/09/2017.
 * This class use for converting from inner model to outer model
 * Example:
 * realm object -> object
 * firebase object -> object
 */

public class SubjectConverter implements BaseConverter<Subject, RealmSubject> {
    private static SubjectConverter mInstance;

    public static SubjectConverter getInstance() {
        if (mInstance == null) {
            mInstance = new SubjectConverter();
        }
        return mInstance;
    }

    /**
     * Convert from realm model to model (inner model)
     *
     * @param realmSubject realm model to be converted
     * @return
     */
    @Override
    public Subject fromRealmToModel(RealmSubject realmSubject) {
        if (realmSubject == null) {
            return new Subject();
        }
        return new Subject(
                realmSubject.getId(),
                realmSubject.getName(),
                TeacherConverter.getInstance().fromRealmToModel(realmSubject.getTeacher())
        );
    }

    /**
     * Convert from model to realm model (outer model)
     *
     * @param subject model to be converted
     * @return
     */
    @Override
    public RealmSubject fromModelToRealm(Subject subject) {
        return new RealmSubject(
                subject.getId(),
                subject.getName(),
                TeacherConverter.getInstance().fromModelToRealm(subject.getTeacher())
        );
    }

    /**
     * Convert from list model to list realm(outer model)
     *
     * @param listObject is model to be converted
     * @return RealmList<RealmSubject>
     */
    @Override
    public RealmList<RealmSubject> fromListModelToListRealm(List<Subject> listObject) {
        RealmList<RealmSubject> listSubject = new RealmList<>();
        if (listObject != null && listObject.size() != 0) {
            for (Subject subject : listObject) {
                listSubject.add(fromModelToRealm(subject));
            }
        }
        return listSubject;
    }

    /**
     * Convert from list realm model to list model (inner model)
     *
     * @param listRealmSubject ist realm model to be converted
     * @return
     */
    @Override
    public List<Subject> fromListRealmToListModel(List<RealmSubject> listRealmSubject) {
        List<Subject> listSubject = new ArrayList<>();
        if (listRealmSubject != null && listRealmSubject.size() != 0) {
            for (RealmSubject realmSubject : listRealmSubject) {
                listSubject.add(fromRealmToModel(realmSubject));
            }
        }
        return listSubject;
    }

    public Subject fromFirebaseToModel(FbSubject fbSubject) {
        return new Subject(
                fbSubject.getId(),
                fbSubject.getName(),
                TeacherConverter.getInstance().fromFirebaseToModel(fbSubject.getTeacher())
        );
    }
    public FbSubject fromModelToFirebase(Subject subject) {
        return new FbSubject(
                subject.getId(),
                subject.getName(),
                TeacherConverter.getInstance().fromModelToFirebase(subject.getTeacher())
        );
    }

}
