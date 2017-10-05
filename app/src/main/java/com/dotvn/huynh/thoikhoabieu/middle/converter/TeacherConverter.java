package com.dotvn.huynh.thoikhoabieu.middle.converter;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Teacher;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.realm.model.RealmTeacher;
import com.dotvn.huynh.thoikhoabieu.outer.data.remote.firebase.model.FbTeacher;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Manh Hoang Huynh on 07/09/2017.
 */

public class TeacherConverter implements BaseConverter<Teacher, RealmTeacher> {
    private static TeacherConverter mInstance;

    public static TeacherConverter getInstance() {
        if (mInstance == null) {
            mInstance = new TeacherConverter();
        }
        return mInstance;
    }

    /**
     * Convert from realm to model(inner model)
     *
     * @param realmTeacher is model to be converted
     * @return Teacher
     */
    public Teacher fromRealmToModel(RealmTeacher realmTeacher) {
        if (realmTeacher == null) {
            return new Teacher();
        }
        return new Teacher(
                realmTeacher.getId(),
                realmTeacher.getName(),
                realmTeacher.getSex(),
                realmTeacher.getTelephoneNumber()
        );
    }

    /**
     * Convert from model to realm(outer model)
     *
     * @param teacher is model to be converted
     * @return RealmTeacher
     */
    public RealmTeacher fromModelToRealm(Teacher teacher) {
        if (teacher == null) {
            return new RealmTeacher();
        }
        return new RealmTeacher(
                teacher.getId(),
                teacher.getName(),
                teacher.getSex(),
                teacher.getTelephoneNumber()
        );
    }

    /**
     * Convert from list model to list realm(outer model)
     *
     * @param listTeacher is model to be converted
     * @return RealmTeacher
     */
    public RealmList<RealmTeacher> fromListModelToListRealm(List<Teacher> listTeacher) {
        RealmList<RealmTeacher> listRealmTeacher = new RealmList<>();
        if (listTeacher != null && listTeacher.size() != 0) {
            for (Teacher teacher : listTeacher) {
                listRealmTeacher.add(fromModelToRealm(teacher));
            }
        }
        return listRealmTeacher;
    }


    /**
     * Convert from list realm to list model(inner model)
     *
     * @param realmListTeacher is model to be converted
     * @return RealmTeacher
     */
    public List<Teacher> fromListRealmToListModel(List<RealmTeacher> realmListTeacher) {
        List<Teacher> listTeacher = new ArrayList<>();
        if (realmListTeacher != null && realmListTeacher.size() != 0) {
            for (RealmTeacher teacher : realmListTeacher) {
                listTeacher.add(fromRealmToModel(teacher));
            }
        }
        return listTeacher;
    }

    public Teacher fromFirebaseToModel(FbTeacher fbTeacher) {
        return new Teacher(
                fbTeacher.getId(),
                fbTeacher.getName(),
                fbTeacher.getSex(),
                fbTeacher.getTelephoneNumber()
        );
    }


    public FbTeacher fromModelToFirebase(Teacher teacher) {
        return new FbTeacher(
                teacher.getId(),
                teacher.getName(),
                teacher.getSex(),
                teacher.getTelephoneNumber()
        );
    }

}
