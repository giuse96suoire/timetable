package com.dotvn.huynh.thoikhoabieu.outer.ui.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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

import java.util.UUID;

/**
 * Created by Manh Hoang Huynh on 16/08/2017.
 */

public class AddSubjectDialog extends DialogFragment {
    public static final String TAG = AddSubjectDialog.class.getSimpleName();
    Interactors.SubjectInteractor mSubjectInteractor;
    Button mBtnFinish, mBtnDismiss;
    EditText mEtSubjectName;
    TextView mTvInputError;
    AutoCompleteTextView mActvTeacher;
    LocalDAOCallback<Subject> mCallback;
    Subject mSubject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        View view = inflater.inflate(R.layout.add_subject_dialog, container);
        mEtSubjectName = (EditText) view.findViewById(R.id.et_subject_name);
        mActvTeacher = (AutoCompleteTextView) view.findViewById(R.id.actv_teacher);
        mBtnFinish = (Button) view.findViewById(R.id.btn_finish);
        mBtnDismiss = (Button) view.findViewById(R.id.btn_dismiss);
        mTvInputError = (TextView) view.findViewById(R.id.tv_input_error);
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
            ((TextView) getView().findViewById(R.id.tv_dialog_title)).setText("Chỉnh sửa");
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
                String teacherName = mActvTeacher.getText().toString().trim();
                if (subjectName == null || subjectName.length() == 0 || teacherName == null || teacherName.length() == 0) {
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

    public void setOnFinishCallback(final LocalDAOCallback<Subject> callback) {
        mCallback = callback;
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
