package com.dotvn.huynh.thoikhoabieu.outer.ui.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.SubjectInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Teacher;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manh Hoang Huynh on 16/08/2017.
 */

public class AddSubjectDialog extends DialogFragment {
    public static final String TAG = AddSubjectDialog.class.getSimpleName();
    private Interactors.SubjectInteractor mSubjectInteractor;
    private TextView mBtnFinish, mBtnDismiss;
    private EditText mEtSubjectName;
    private TextView mTvInputError;
    private AutoCompleteTextView mActvTeacher;
    private LocalDAOCallback<Subject> mCallback;
    private Subject mSubject;
    private List<Subject> mListSubject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        View view = inflater.inflate(R.layout.add_subject_dialog, container);
        mEtSubjectName = view.findViewById(R.id.et_subject_name);
        mActvTeacher = view.findViewById(R.id.actv_teacher);
        mBtnFinish = view.findViewById(R.id.btn_finish);
        mBtnDismiss = view.findViewById(R.id.btn_dismiss);
        mTvInputError = view.findViewById(R.id.tv_input_error);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        clearData();
        if (mSubject != null) {
            mEtSubjectName.setText(mSubject.getName());
            mActvTeacher.setText(mSubject.getTeacher().getName());
        }

    }

    /**
     * Set subject in case edit subject
     *
     * @param subject to edit (nullable)
     */
    public void setSubject(Subject subject) {
        this.mSubject = subject;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSubjectInteractor = new SubjectInteractor();
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName = mEtSubjectName.getText().toString().trim();
                if (isSubjectNameHasExisted(subjectName)) {
                    mTvInputError.setVisibility(View.VISIBLE);
                    mTvInputError.setText("Môn này đã tồn tại");
                    return;
                }
                String teacherName = mActvTeacher.getText().toString().trim();
                if (subjectName == null || subjectName.length() == 0 || teacherName == null || teacherName.length() == 0) {
                    mTvInputError.setText("Hãy nhập đầy đủ thông tin");
                    mTvInputError.setVisibility(View.VISIBLE);
                    return;
                }
                if (mSubject == null) {
                    mSubject = new Subject();
                    mSubject.setId(UUID.randomUUID().toString());
                    Teacher teacher = new Teacher();
                    teacher.setName(teacherName);
                    teacher.setId(UUID.randomUUID().toString());
                    mSubject.setTeacher(teacher);
                } else {
                    mSubject.getTeacher().setName(teacherName);
                }
                mSubject.setName(subjectName);
                mSubjectInteractor.writeToLocal(mSubject, mCallback);
            }
        });
        mBtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    private boolean isSubjectNameHasExisted(String subjectName) {
        if (mListSubject == null || mListSubject.size() == 0) {
            Log.d("xxx","mListSubject != null || mListSubject.size() == 0");
            return false;
        }
        for (Subject s : mListSubject) {
            if (s.getName().trim().toLowerCase().equals(subjectName.trim().toLowerCase())) {
                Log.d("xxx","is exist =================>");
                return true;
            }
        }
        return false;
    }
    public void setOnFinishCallback(final LocalDAOCallback<Subject> callback) {
        mCallback = callback;
    }
    public void setCurrentListSubject(List<Subject> listSubject) {
        mListSubject = listSubject;
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    private void clearData() {
        mEtSubjectName.setText("");
        mActvTeacher.setText("");
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}
