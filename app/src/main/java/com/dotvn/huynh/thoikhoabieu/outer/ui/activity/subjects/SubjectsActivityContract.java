package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.subjects;

import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 18/08/2017.
 */

public interface SubjectsActivityContract {
    interface View extends BaseConstract.View{
        void showToast(String message);
        void showAddSubjectDialog();
        void notifySelectedListChange(List<com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject> listSelectedSubject);

    }
    interface Presenter {
        void loadSubjects();
    }

}
