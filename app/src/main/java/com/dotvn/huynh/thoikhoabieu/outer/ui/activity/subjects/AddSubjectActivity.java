package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.subjects;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Subject;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.TwoButtonDialog;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.subjects.OnSubjectCheckedChangeListener;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.subjects.SubjectFragment;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.subjects.SubjectsFragmentContract;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.timeTable.TimeTableFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * This activity use for add subject to day
 * Input: list subject of day (if exist)
 * Output: return list subject that user select
 * Contain: Subject fragment to display list subject (with selectable mode). I do it for reuse code,
 * if it is too hard for developer, please remove it and create new list for it.
 */
public class AddSubjectActivity extends AppCompatActivity implements SubjectsActivityContract.View,
        OnSubjectCheckedChangeListener,
        TimeTableFragment.OnFragmentInteractionListener {
    /**
     * Unbinder of ButterKnife
     */
    private Unbinder mUnbinder;
    /**
     * Key for put extra data
     */
    public static final String DAY_OF_WEEK_STRING_EXTRA_KEY = "DAY_OF_WEEK";
    public static final String SUBJECTS_ID_ARRAY_EXTRA_KEY = "ARR_SUBJECTS_ID";
    public static final String DATA_RESULT_KEY = "DATA_RESULT_KEY";
    /**
     * Bind view (using ButterKnife)
     */
    @BindView(R.id.rv_list_subject_selected)
    RecyclerView mRvListSubjectSelected;
    @BindView(R.id.tv_no_subject_selected)
    TextView mTvNoSubjectSelected;
    /**
     * List subject id that exist in day. It was putted in extra when start this activity
     */
    private ArrayList<String> mSubjectsIdArr;
    /**
     * List selected subject contain subjects that user selected, return it when finish as result
     */
    private ArrayList<Subject> mListSelectedSubjects;
    private SelectedSubjectAdapter mSelectedSubjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        mUnbinder = ButterKnife.bind(this);

        mListSelectedSubjects = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvListSubjectSelected.setLayoutManager(linearLayoutManager);
        mSelectedSubjectAdapter = new SelectedSubjectAdapter(this, this, mListSelectedSubjects);
        mRvListSubjectSelected.setAdapter(mSelectedSubjectAdapter);

        String dayOfWeek = getIntent().getStringExtra(DAY_OF_WEEK_STRING_EXTRA_KEY);
        mSubjectsIdArr = getIntent().getStringArrayListExtra(SUBJECTS_ID_ARRAY_EXTRA_KEY);
        if (dayOfWeek != null) {
            setTitle(dayOfWeek);
        } else {
            setTitle(getString(R.string.title_select_subject));
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_24dp);
        }

        initSubjectFragment();
    }

    /**
     * List subject in this activity is not its. They come from Subject Fragment
     */
    private void initSubjectFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SubjectFragment.TAG);
        if (fragment == null) {
            if (mSubjectsIdArr != null && mSubjectsIdArr.size() > 0) {
                fragment = SubjectFragment.newInstance(true, mSubjectsIdArr);
            } else {
                fragment = SubjectFragment.newInstance(true);
            }
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment_subjects_container, fragment, SubjectFragment.TAG)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_finish_add_subject:
                TwoButtonDialog.create(
                        this,
                        mListSelectedSubjects.size() == 0 ? "Chưa môn nào được chọn, bạn chắc chứ ?" :
                                mListSelectedSubjects.size() +" môn học sẽ được thêm, bạn chắc chứ ?",
                        getString(R.string.label_review),
                        getString(R.string.label_finish),
                        new TwoButtonDialog.OnButtonClickListener() {
                            @Override
                            public void onNegativeButtonClick() {
                                // do nothing (dismiss as default)
                            }

                            @Override
                            public void onPositiveButtonClick() {
                                Intent intent = new Intent();
                                intent.putParcelableArrayListExtra(DATA_RESULT_KEY, mListSelectedSubjects);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }).show();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onSubjectCheckedChange(boolean isChecked, Subject subject) {
        if (isChecked) {
            addIfNotExist(subject);
        } else {
            removeIfExist(subject);
        }
        mSelectedSubjectAdapter.notifyDataSetChanged();
        mRvListSubjectSelected.scrollToPosition(mListSelectedSubjects.size() - 1);
        if (mListSelectedSubjects == null || mListSelectedSubjects.size() == 0) {
            setNoSubjectPanelVisible(true);
        } else {
            setNoSubjectPanelVisible(false);
        }
    }

    private void setNoSubjectPanelVisible(boolean isVisible) {
        mTvNoSubjectSelected.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * Add subject to list selected subject if it not exist in list
     *
     * @param subject
     */
    private void addIfNotExist(Subject subject) {
        int index = getIndexOfSubjectIfExist(subject);
        if (index == -1)
            mListSelectedSubjects.add(subject);
    }

    /**
     * Remove subject to list selected subject if it exist in list
     *
     * @param subject
     */
    private void removeIfExist(Subject subject) {
        int index = getIndexOfSubjectIfExist(subject);
        if (index != -1)
            mListSelectedSubjects.remove(index);
    }

    /**
     * Get index of subject in list selected subject
     *
     * @param subject
     * @return index of subject in list if exist, -1 if not exist
     */
    private int getIndexOfSubjectIfExist(Subject subject) {
        for (int i = 0; i < mListSelectedSubjects.size(); i++) {
            Subject s = mListSelectedSubjects.get(i);
            if (s.getName().equals(subject.getName())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_subjects_activity, menu);
        return true;
    }


    @Override
    public void showToast(String message) {

    }

    @Override
    public void showToast(int messageId) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void showAddSubjectDialog() {

    }

    @Override
    public void notifySelectedListChange(List<Subject> listSelectedSubject) {
        setNoSubjectPanelVisible((listSelectedSubject == null || listSelectedSubject.size() == 0) ? true : false);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_fragment_subjects_container);
        if (fragment != null && fragment instanceof SubjectsFragmentContract.View) {
            ((SubjectFragment) fragment).getAdapter().setListSelectedSubjects(listSelectedSubject);
        }
    }
}
