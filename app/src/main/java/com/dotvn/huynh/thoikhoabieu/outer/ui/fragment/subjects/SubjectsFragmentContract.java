package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.subjects;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 18/08/2017.
 */

public interface SubjectsFragmentContract {
    interface View extends BaseConstract.View{
        void showAddSubjectDialog(Subject subject);
        void showRemoveSubjectDialog(Subject subject);
        void updateListSubjects(List<Subject> listSubject);
        SubjectAdapter getAdapter();
    }
    interface Presenter {
        void loadTeachers();
        void loadSubjects();
        void removeSubject(Subject subject);
        void onStop();
    }


}
