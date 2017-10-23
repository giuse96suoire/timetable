package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.createTimeTable;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.TwoButtonDialog;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.friends.FriendsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreateTimeTableActivity extends AppCompatActivity
        implements FriendsFragment.OnFragmentInteractionListener,
        CreateTimeTableContract.View,
        TabLayout.OnTabSelectedListener {
    private Unbinder mUnbinder;
    private ArrayList<String> mFriendIdArr;
    @BindView(R.id.tl_header_timetable_setting)
    TabLayout mTlHeaderTimeTableSetting;
    @BindView(R.id.rl_timetable_general_setting)
    LinearLayout mLlTimeTableGeneralSetting;
    @BindView(R.id.rl_timetable_share_setting)
    FrameLayout mFlTimeTableShareSetting;
    @BindView(R.id.et_timetable_name)
    EditText mEtTimeTableName;
    @BindView(R.id.et_timetable_description)
    EditText mEtTimeTableDescription;
    private CreateTimeTableContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_time_table);
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new CreateTimeTablePresenter(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void initView() {
//        Toolbar toolbar = findViewById(R.id.toolbar_create_timetable);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_24dp);
        mTlHeaderTimeTableSetting.addOnTabSelectedListener(this);
        initFragmentFriend();
    }

    private void initFragmentFriend() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FriendsFragment.TAG);
        if (fragment == null) {
            if (mFriendIdArr != null && mFriendIdArr.size() > 0) {
                fragment = FriendsFragment.newInstance(true, mFriendIdArr);
            } else {
                fragment = FriendsFragment.newInstance(true);
            }
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rl_timetable_share_setting, fragment, FriendsFragment.TAG)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_create_timetable, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_finish_create_timetable:
                doCreateTimetableAction();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void doCreateTimetableAction() {
        final String timeTableName = mEtTimeTableName.getText().toString();
        final String timeTableDescription = mEtTimeTableDescription.getText().toString();
        if (timeTableName == null || timeTableName.length() == 0) {
            showToast("Vui lòng nhập đầy đủ thông tin");
            return;
        }
        TwoButtonDialog.create(this, "Hoàn tất tạo thời khóa biểu ?", "Xem lại", "Hoàn tất", new TwoButtonDialog.OnButtonClickListener() {
            @Override
            public void onNegativeButtonClick() {

            }

            @Override
            public void onPositiveButtonClick() {
                mPresenter.addTimeTable(TimeTable.createNew(timeTableName, timeTableDescription));
                finish();
            }
        }).show();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackbar(int messageId) {

    }

    @Override
    public void showSnackbar(String message) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        switch (position) {
            case 0:
                mLlTimeTableGeneralSetting.setVisibility(View.VISIBLE);
                mFlTimeTableShareSetting.setVisibility(View.GONE);
                break;
            case 1:
                mLlTimeTableGeneralSetting.setVisibility(View.GONE);
                mFlTimeTableShareSetting.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
