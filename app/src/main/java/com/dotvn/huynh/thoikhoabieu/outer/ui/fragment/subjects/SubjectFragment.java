package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.subjects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.ui.CanSelectAllItem;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.main.MainActivityContract;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.subjectDetail.SubjectDetailActivity;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.subjects.AddSubjectActivity;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.subjects.SubjectsActivityContract;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.AddSubjectDialog;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.ProgressDialog;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.TwoButtonDialog;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.timeTable.TimeTableFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;


public class SubjectFragment extends Fragment implements SubjectsFragmentContract.View, OnClickListener, OnSubjectCheckedChangeListener, CanSelectAllItem {
    public static final String TAG = SubjectFragment.class.getSimpleName();
    private Unbinder mUnbinder;
    private TimeTableFragment.OnFragmentInteractionListener mListener;
    @BindView(R.id.rv_list_subject)
    RecyclerView mRvListSubjects;
    @BindView(R.id.tv_open_bottom_panel)
    TextView mTvCreateSubject;
    @BindView(R.id.rl_nodata)
    RelativeLayout mRlNodata;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private List<Subject> mListSubject;
    private SubjectAdapter mAdapter;
    private SubjectsFragmentContract.Presenter mPresenter;
    private boolean mIsSelectableMode;
    private static final String KEY_SELECTABLE_MODE = "KEY_SELECTABLE_MODE";
    private static final String KEY_SELECT_SUBJECTS_ID = "KEY_SELECT_SUBJECTS_ID";
    private Fragment mAddSubjectDialog;
    private ArrayList<String> mListSelectedSubjectId = null;

    public SubjectFragment() {
    }

    public static SubjectFragment newInstance(boolean isSelectableMode) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_SELECTABLE_MODE, isSelectableMode);
        fragment.setArguments(args);
        return fragment;
    }

    public static SubjectFragment newInstance(boolean isSelectableMode, ArrayList<String> selectedSubjects) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_SELECTABLE_MODE, isSelectableMode);
        args.putStringArrayList(KEY_SELECT_SUBJECTS_ID, selectedSubjects);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsSelectableMode = getArguments().getBoolean(KEY_SELECTABLE_MODE);
            mListSelectedSubjectId = getArguments().getStringArrayList(KEY_SELECT_SUBJECTS_ID);
        }
        if (getActivity() instanceof AddSubjectActivity) {

        } else {
            getActivity().setTitle(getString(R.string.title_subjects));
        }

        //// TODO: 16/09/2017 provide it by dagger, not new operation
        mPresenter = new SubjectsPresenter(this);
    }

    /**
     * Init view, include bind view, set listener,... should be called before do anything with view
     */
    private void initView() {
        mTvCreateSubject.setOnClickListener(this);
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadSubjects();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Init list data, adapter,.. prepare to display subject data
     */
    private void initListSubjectData() {
        mListSubject = new ArrayList<>();
        mAdapter = new SubjectAdapter(getContext(), this, mListSubject, mIsSelectableMode, false, mListSelectedSubjectId);
        mAdapter.setOnSubjectCheckedChangeListener(this);
        if (mIsSelectableMode) {
            if (getActivity() instanceof OnSubjectCheckedChangeListener) {
                mAdapter.setOnSubjectCheckedChangeListener((OnSubjectCheckedChangeListener) getActivity());
            }
        }
        mRvListSubjects.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mRvListSubjects.setLayoutManager(new LinearLayoutManager(getContext()));
        initListSubjectData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadSubjects();
            }
        }, 80);
    }

    @Override
    public SubjectAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TimeTableFragment.OnFragmentInteractionListener) {
            mListener = (TimeTableFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private DialogFragment mProgressDialog;

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog();
        }
        mProgressDialog.show(getFragmentManager(), "");
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message) {
        if (getContext() != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int messageId) {

    }

    @Override
    public void showRemoveSubjectDialog(final Subject subject) {
        TwoButtonDialog.create(
                getContext(),
                "Bạn có muốn xóa môn " + subject.getName() + " không ?",
                "Không",
                "Có",
                new TwoButtonDialog.OnButtonClickListener() {
                    @Override
                    public void onNegativeButtonClick() {
                    }

                    @Override
                    public void onPositiveButtonClick() {
                        mPresenter.removeSubject(subject);
                        if (mIsSelectableMode) {
                            if (getActivity() instanceof OnSubjectCheckedChangeListener) {
                                ((OnSubjectCheckedChangeListener) getActivity()).onSubjectCheckedChange(false, subject);
                            }
                            mAdapter.removeSubjectFromSelectedList(subject);
                        }
                    }
                }
        ).show();
    }


    @Override
    public void updateListSubjects(List<Subject> listSubject) {
        if (mSrlRefresh != null && mSrlRefresh.isRefreshing()) {
            mSrlRefresh.setRefreshing(false);
        }
        if (listSubject != null) {
            if (listSubject.size() > 0) {
                if (mRlNodata != null)
                    mRlNodata.setVisibility(View.GONE);

            } else {
                if (mRlNodata != null)
                    mRlNodata.setVisibility(View.VISIBLE);
            }
            mListSubject.clear();
            mListSubject.addAll(listSubject);
            mAdapter.notifyDataSetChanged();
        } else {
            mRlNodata.setVisibility(View.VISIBLE);
        }
        if (isAdded()) {
            if (getActivity() instanceof SubjectsActivityContract.View) {
//                getActivity().setTitle(getString(R.string.title_add_subject) +"( đã chọn "+mAdapter.getListSelectedSubjects().size()+")");
            } else {
                getActivity().setTitle(
                        getString(R.string.title_subjects) +
                                " (" + (listSubject == null ? 0 : listSubject.size()) + ")");
            }

        }
    }

    @Override
    public void showDetailSubjectActivity(Subject subject) {
        Intent intent = new Intent(getContext(), SubjectDetailActivity.class);
        intent.putExtra(SubjectDetailActivity.KEY_SUBJECT_EXTRA, subject);
        startActivity(intent);
    }

    @Override
    public void enableSelectMode(boolean isEnable) {
        Activity activity = getActivity();
        if (activity instanceof MainActivityContract.View) {
            ((MainActivityContract.View)activity).enableSelectMode(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Show dialog add subject
     */
    @Override
    public void showAddSubjectDialog(Subject subject) {
        if (mAddSubjectDialog == null) {
            mAddSubjectDialog = getFragmentManager().findFragmentByTag(AddSubjectDialog.TAG);
            if (mAddSubjectDialog == null) {
                mAddSubjectDialog = new AddSubjectDialog();
            }
        }
        ((AddSubjectDialog) mAddSubjectDialog).setSubject(subject);
        ((AddSubjectDialog) mAddSubjectDialog).setCurrentListSubject(mListSubject);
        ((AddSubjectDialog) mAddSubjectDialog).setOnFinishCallback(new LocalDAOCallback<Subject>() {
            @Override
            public void onSuccess(Subject item) {
                ((AddSubjectDialog) mAddSubjectDialog).dismiss();
            }

            @Override
            public void onSuccess(List<Subject> items) {

            }

            @Override
            public void onSuccess() {
                ((AddSubjectDialog) mAddSubjectDialog).dismiss();
            }

            @Override
            public void onError(Throwable error) {
                if (error instanceof RealmPrimaryKeyConstraintException) {
                    showToast("Môn học này đã tồn tại");
                }
            }
        });
        ((AddSubjectDialog) mAddSubjectDialog).show(getFragmentManager(), AddSubjectDialog.TAG);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_open_bottom_panel:
                showAddSubjectDialog(null);
                break;
        }
    }

    @Override
    public void onSubjectCheckedChange(boolean isChecked, Subject subject) {

    }

    @Override
    public void selectAll() {
        mAdapter.selectAllItem();
    }

    @Override
    public void cancelSelectAll() {
        mAdapter.cancelSelectAllItem();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * If you want activity that contain this fragment handle callback
     * when an item check state change, implement it
     */

}
