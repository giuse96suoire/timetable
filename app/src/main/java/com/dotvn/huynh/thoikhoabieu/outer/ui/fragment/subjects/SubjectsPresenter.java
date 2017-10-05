package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.subjects;

import android.util.Log;

import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.ItemOfDayInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.SubjectInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.TeacherInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.ItemOfDay;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 18/08/2017.
 */

public class SubjectsPresenter implements SubjectsFragmentContract.Presenter {
    private static SubjectsFragmentContract.View mView;
    private Interactors.SubjectInteractor mSubjectInteracter;
    private Interactors.TeacherInteractor mTeacherInteracter;
    private Interactors.ItemOfDayInteractor mItemOfDayInteractor;
    private LocalDAOCallback mGetDataCallBack = new LocalDAOCallback<Subject>() {
        @Override
        public void onSuccess(Subject item) {
            mView.dismissProgressDialog();
        }

        @Override
        public void onSuccess(List<Subject> items) {
            mView.dismissProgressDialog();
            mView.updateListSubjects(items);
        }

        @Override
        public void onSuccess() {
            mView.dismissProgressDialog();

        }

        @Override
        public void onError(Throwable error) {
            mView.dismissProgressDialog();
            if (error == null) {
//                mView.showToast("Không có dữ liệu");
            } else {
                mView.showToast(error.getMessage());
            }
        }
    };

    public SubjectsPresenter(SubjectsFragmentContract.View view) {
        this.mView = view;
        this.mSubjectInteracter = new SubjectInteractor();
        this.mTeacherInteracter = new TeacherInteractor();
        this.mItemOfDayInteractor = new ItemOfDayInteractor();
    }

    @Override
    public void loadTeachers() {

    }

    @Override
    public void loadSubjects() {
        mView.showProgressDialog();
        mSubjectInteracter.readAllFromLocal(mGetDataCallBack);
    }

    @Override
    public void removeSubject(final Subject subject) {
        mItemOfDayInteractor.readItemOfDayBySubject(subject, new LocalDAOCallback<ItemOfDay>() {
            @Override
            public void onSuccess(ItemOfDay item) {
                Log.d("xxx", "onSuccess: "+item.getSubject().getName());
            }

            @Override
            public void onSuccess(List<ItemOfDay> items) {
                Log.d("xxx", "onSuccess: "+items.size());
                mItemOfDayInteractor.removeListFromLocal(items, new LocalDAOCallback<ItemOfDay>() {
                    @Override
                    public void onSuccess(ItemOfDay item) {

                    }

                    @Override
                    public void onSuccess(List<ItemOfDay> items) {
                    }

                    @Override
                    public void onSuccess() {
                        mSubjectInteracter.removeFromLocal(subject);
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
            }

            @Override
            public void onSuccess() {
                Log.d("xxx", "onError");
            }

            @Override
            public void onError(Throwable error) {
                Log.d("xxx", "onError: "+error.getMessage());
            }
        });
    }

    @Override
    public void onStop() {
        mSubjectInteracter.closeConnection();
    }
}
