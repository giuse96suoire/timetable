package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.TimeTable;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.shared.SharedDB;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.addFriend.SearchFriendActivity;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.createTimeTable.CreateTimeTableActivity;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.login.LoginActivity;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.profile.ProfileActivity;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.TwoButtonDialog;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.friends.FriendsFragment;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.oneDay.OneDayFragment;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.setting.SettingFragment;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.subjects.SubjectFragment;
import com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.timeTable.TimeTableFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View,
        OneDayFragment.OnFragmentInteractionListener, TimeTableFragment.OnFragmentInteractionListener,
        SubjectFragment.OnFragmentInteractionListener, SettingFragment.OnFragmentInteractionListener,
        FriendsFragment.OnFragmentInteractionListener,
        View.OnClickListener, PopupMenu.OnMenuItemClickListener, AdapterView.OnItemSelectedListener {

    private Unbinder mUnbinder, mUnbinderActionBar;
    private MainActivityContract.Presenter mPresenter;
    private boolean mIsLogined;
    private TimeTableSpinnerAdapter mSpinnerTimeTableAdapter;
    private PopupMenu mMorePopupMenu;

    // bind view
    @BindView(R.id.item_sync)
    ImageView mIvItemSync;
    @BindView(R.id.item_notification)
    ImageView mIvItemNotification;
    @BindView(R.id.item_share)
    ImageView mIvItemShare;
    @BindView(R.id.item_add_friend)
    ImageView mIvItemAddFriend;
    @BindView(R.id.item_add_group)
    ImageView mIvItemAddGroup;
    @BindView(R.id.item_facebook)
    ImageView mIvItemFacebook;
    @BindView(R.id.item_more)
    ImageView mIvItemMore;

    @BindView(R.id.tv_menu_item_login)
    TextView mTvMenuItemUser;
    @BindView(R.id.tv_menu_item_friends)
    TextView mTvMenuItemFriends;
    @BindView(R.id.tv_menu_item_score_board)
    TextView mTvMenuItemScoreBoard;
    @BindView(R.id.tv_menu_item_settings)
    TextView mTvMenuItemSettings;
    @BindView(R.id.tv_menu_item_subjects)
    TextView mTvMenuItemSubjects;
    @BindView(R.id.tv_menu_item_teachers)
    TextView mTvMenuItemTeachers;
    @BindView(R.id.tv_menu_item_time_table)
    TextView mTvMenuItemTimeTable;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_activity_title)
    TextView mTvActivityTitle;
    @BindView(R.id.rl_user_info_panel)
    RelativeLayout mRlUserInfoPanel;

    @BindView(R.id.rl_left_drawer)
    RelativeLayout mRlLeftDrawer;

    @BindView(R.id.rv_right_drawer)
    FrameLayout mFlRightDrawer;

    @BindView(R.id.dl_main_drawer)
    DrawerLayout mDlMainDrawer;
    @BindView(R.id.sp_list_timetable)
    Spinner mSpListTimeTable;

    ProgressDialog mProgressDialog;


    // end bind view
    private List<TimeTable> mListTimeTableData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.main_action_bar);
        mUnbinderActionBar = ButterKnife.bind(getSupportActionBar().getCustomView());
        mUnbinder = ButterKnife.bind(this);
        initView();
        initDrawerMenu();
        mPresenter = new MainPresenter(this, this);
        mPresenter.regisUserLoginStateChagedCallBack();
        mListTimeTableData = new ArrayList<>();
        mSpinnerTimeTableAdapter = new TimeTableSpinnerAdapter(this, mListTimeTableData);
        mSpListTimeTable.setAdapter(mSpinnerTimeTableAdapter);
        mPresenter.loadData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mUnbinderActionBar.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                if (mDlMainDrawer.isDrawerOpen(Gravity.LEFT)) {
                    mDlMainDrawer.closeDrawer(Gravity.LEFT);
                } else {
                    mDlMainDrawer.openDrawer(Gravity.LEFT);
                }
                break;
        }
        return true;
    }

    private void showSyncDialog() {
        TwoButtonDialog.create(
                this,
                "Bạn sắp đồng bộ dữ liệu lên máy chủ, những người được share (nếu có) sẽ nhận được thông báo thay đổi",
                "Hủy",
                "Đồng bộ",
                new TwoButtonDialog.OnButtonClickListener() {
                    @Override
                    public void onNegativeButtonClick() {

                    }

                    @Override
                    public void onPositiveButtonClick() {
                        mPresenter.syncData();
                        startAnimation(mIvItemSync, R.anim.rotation);
                    }
                }
        ).show();
    }

    private void startAnimation(View v, @AnimRes int animId) {
        Animation anim = AnimationUtils.loadAnimation(this, animId);
        anim.setRepeatCount(Animation.INFINITE);
        v.startAnimation(anim);
    }

    private void stopAnimation(View v) {
        v.clearAnimation();
    }

    @Override
    public void stopSyncAnimation() {
        stopAnimation(mIvItemSync);
    }

    private void initView() {
        mIvItemNotification.setOnClickListener(this);
        mIvItemSync.setOnClickListener(this);
        mIvItemShare.setOnClickListener(this);
        mIvItemAddFriend.setOnClickListener(this);
        mIvItemAddGroup.setOnClickListener(this);
        mIvItemFacebook.setOnClickListener(this);
        mIvItemMore.setOnClickListener(this);
        initOptionMenuByContext(TimeTableFragment.TAG);
        mMorePopupMenu = new PopupMenu(this, mIvItemMore);
        mMorePopupMenu.getMenuInflater().inflate(R.menu.menu, mMorePopupMenu.getMenu());
        mMorePopupMenu.setOnMenuItemClickListener(this);
        mRlUserInfoPanel.setOnClickListener(this);
        mTvActivityTitle.setOnClickListener(this);
        mSpListTimeTable.setOnItemSelectedListener(this);
    }

    private void initDrawerMenu() {
        mTvMenuItemUser.setOnClickListener(this);
        mTvMenuItemFriends.setOnClickListener(this);
        mTvMenuItemScoreBoard.setOnClickListener(this);
        mTvMenuItemSettings.setOnClickListener(this);
        mTvMenuItemSubjects.setOnClickListener(this);
        mTvMenuItemTeachers.setOnClickListener(this);
        mTvMenuItemTimeTable.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        mDlMainDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.d("MainActivity", "onDrawerSlide");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d("MainActivity", "onDrawerOpened");

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d("MainActivity", "onDrawerClosed");

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.d("MainActivity", "onDrawerStateChanged " + newState);
                if (newState == DrawerLayout.STATE_DRAGGING && !mDlMainDrawer.isDrawerOpen(Gravity.LEFT)) {
                    Log.d("MainActivity", "startMenuItemAnimation ----> ");
                    startMenuItemAnimation();
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void showDialog() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showInfo() {

    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Đợi chút nhé");
            mProgressDialog.setIndeterminateDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int messageId) {

    }

    @Override
    public void updateData(List<TimeTable> listTimeTable) {
        mListTimeTableData.clear();
        if (listTimeTable != null) {
            mListTimeTableData.addAll(listTimeTable);
        }
        mSpinnerTimeTableAdapter.notifyDataSetChanged();
        if (mIsFirstTimeStart) {
            mIsFirstTimeStart = false;
            String currentTimeTableId = SharedDB.getInstance(this).getCurrentTimeTable();
            setCurrentTimeTable(currentTimeTableId == null ? mListTimeTableData.get(0).getId() : currentTimeTableId);
        }
    }

    private boolean mIsFirstTimeStart = true;

    @Override
    public void updateUiForUserInfo(User user) {
        showToast("Chào mừng " +
                (
                        (user.getDisplayName() != null && user.getDisplayName().length() != 0)
                                ? user.getDisplayName()
                                : user.getPhoneNumber()
                )
        );
        mTvUserName.setText(
                (user.getDisplayName() == null || user.getDisplayName().length() == 0)
                        ? user.getPhoneNumber()
                        : user.getDisplayName()
        );
        mTvMenuItemUser.setText("Đăng xuất");
        mIsLogined = true;
    }

    @Override
    public void removeUiWhenUserLogout() {
        showToast("Bạn đã đăng xuất");
        mTvMenuItemUser.setText("Đăng nhập");
        mIsLogined = false;
    }

    @Override
    public void initOptionMenuByContext(String tag) {
        if (tag.equals(SubjectFragment.TAG)) {
            mIvItemMore.setVisibility(View.GONE);
            mSpListTimeTable.setVisibility(View.GONE);
            mIvItemShare.setVisibility(View.GONE);
            mIvItemSync.setVisibility(View.GONE);
            mIvItemAddFriend.setVisibility(View.GONE);
            mIvItemAddGroup.setVisibility(View.GONE);
            mIvItemFacebook.setVisibility(View.GONE);
            mTvActivityTitle.setVisibility(View.VISIBLE);
        } else if (tag.equals(FriendsFragment.TAG)) {
            mSpListTimeTable.setVisibility(View.GONE);
            mIvItemMore.setVisibility(View.GONE);
            mIvItemShare.setVisibility(View.GONE);
            mIvItemSync.setVisibility(View.GONE);
            mIvItemSync.setVisibility(View.GONE);
            mIvItemFacebook.setVisibility(View.GONE);
            mIvItemAddFriend.setVisibility(View.VISIBLE);
            mTvActivityTitle.setVisibility(View.VISIBLE);
        } else if (tag.equals(TimeTableFragment.TAG)) {
            mSpListTimeTable.setVisibility(View.VISIBLE);
            mIvItemMore.setVisibility(View.VISIBLE);
            mIvItemShare.setVisibility(View.GONE);
            mIvItemSync.setVisibility(View.GONE);
            mIvItemAddFriend.setVisibility(View.GONE);
            mIvItemAddGroup.setVisibility(View.GONE);
            mIvItemFacebook.setVisibility(View.GONE);
            mTvActivityTitle.setVisibility(View.GONE);
        } else if (tag.equals(SettingFragment.TAG)) {
            mSpListTimeTable.setVisibility(View.GONE);
            mIvItemMore.setVisibility(View.GONE);
            mIvItemShare.setVisibility(View.GONE);
            mIvItemSync.setVisibility(View.GONE);
            mIvItemAddFriend.setVisibility(View.GONE);
            mIvItemAddGroup.setVisibility(View.GONE);
            mIvItemFacebook.setVisibility(View.VISIBLE);
            mTvActivityTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void changeTitle(String title) {
        mTvActivityTitle.setText(title);
    }

    @Override
    public void onSyncSuccess() {
        stopAnimation(mIvItemSync);
        mIvItemSync.setVisibility(View.GONE);
    }

    @Override
    public void onSyncFailure() {
        stopAnimation(mIvItemSync);
        mIvItemSync.setImageResource(R.drawable.ic_sync_problem_white_24dp);
    }

    @Override
    public void onSyncRunning() {
        mIvItemSync.setVisibility(View.VISIBLE);
        startAnimation(mIvItemSync, R.anim.rotation);
    }

    @Override
    public void setCurrentTimeTable(String timeTableId) {
        if (mListTimeTableData == null) {
            return;
        }
        int length = mListTimeTableData.size();
        if (length == 0) {
            return;
        }
        for (int i = 0; i < length; i++) {
            TimeTable timeTable = mListTimeTableData.get(i);
            if (timeTable.getId().equals(timeTableId)) {
                switchTimeTable(timeTable);
                mSpListTimeTable.setSelection(i);
                return;
            }
        }
        switchTimeTable(mListTimeTableData.get(0));
        mSpListTimeTable.setSelection(0);
    }


    @Override
    public void onClick(View v) {
        Log.d("xxx", FirebaseDatabase.getInstance().getReference().push().getKey());
        final int id = v.getId();
        switch (id) {
            case R.id.tv_menu_item_login:
            case R.id.tv_menu_item_friends:
            case R.id.tv_menu_item_score_board:
            case R.id.tv_menu_item_settings:
            case R.id.tv_menu_item_subjects:
            case R.id.tv_menu_item_teachers:
            case R.id.tv_menu_item_time_table:
                mDlMainDrawer.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onMenuItemClick(id);
                    }
                }, 220);
                break;
            case R.id.item_sync:

                break;
            case R.id.item_share:
                break;
            case R.id.item_notification:
                if (mDlMainDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    mDlMainDrawer.closeDrawer(Gravity.RIGHT);
                } else {
                    mDlMainDrawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.item_add_friend:
                startActivity(new Intent(MainActivity.this, SearchFriendActivity.class));
                break;
            case R.id.item_more:
                mMorePopupMenu.show();
                break;

        }
    }


    private void doSyncAction() {
        if (mIsLogined) {
            showSyncDialog();
        } else {
            TwoButtonDialog.create(
                    this,
                    "Bạn chưa đăng nhập. Hãy đăng nhập để đồng bộ dữ liệu ngay bây giờ",
                    "Hủy",
                    "Đăng nhập",
                    new TwoButtonDialog.OnButtonClickListener() {
                        @Override
                        public void onNegativeButtonClick() {

                        }

                        @Override
                        public void onPositiveButtonClick() {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    }
            ).show();
        }

    }

    private void onMenuItemClick(int id) {
        switch (id) {
            case R.id.rl_user_info_panel:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.tv_menu_item_login:
                if (mIsLogined) {
                    FirebaseAuth.getInstance().signOut();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.tv_menu_item_friends:
                changeTitle("Bạn bè");
                switchFragment(FriendsFragment.TAG);
                break;

            case R.id.tv_menu_item_score_board:
                break;

            case R.id.tv_menu_item_settings:
                changeTitle("Cài đặt");
                switchFragment(SettingFragment.TAG);
                break;

            case R.id.tv_menu_item_subjects:
                changeTitle("Môn học");
                switchFragment(SubjectFragment.TAG);
                break;

            case R.id.tv_menu_item_teachers:
                break;

            case R.id.tv_menu_item_time_table:
                switchFragment(TimeTableFragment.TAG);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void switchFragment(String TAG) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            if (TAG.equals(OneDayFragment.TAG)) {
                initOptionMenuByContext(OneDayFragment.TAG);
                fragment = new OneDayFragment();
            } else if (TAG.equals(SubjectFragment.TAG)) {
                initOptionMenuByContext(SubjectFragment.TAG);
                fragment = new SubjectFragment();
            } else if (TAG.equals(TimeTableFragment.TAG)) {
                initOptionMenuByContext(TimeTableFragment.TAG);
                fragment = TimeTableFragment.newInstance(mOneDaysIdArray);
            } else if (TAG.equals(SettingFragment.TAG)) {
                initOptionMenuByContext(SettingFragment.TAG);
                fragment = new SettingFragment();
            } else if (TAG.equals(FriendsFragment.TAG)) {
                initOptionMenuByContext(FriendsFragment.TAG);
                fragment = new FriendsFragment();
            }
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment_container, fragment, TAG)
                .commit();
    }
    private String[] mOneDaysIdArray = new String[7];
    private void switchTimeTable(TimeTable timeTable) {

        for (int i = 0; i < timeTable.getData().size(); i++) {
            mOneDaysIdArray[i] = timeTable.getData().get(i).getId();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment_container, TimeTableFragment.newInstance(mOneDaysIdArray), TimeTableFragment.TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    /**
     * Create animation for menu item (slide left to right, right to left)
     */
    private void startMenuItemAnimation() {
        int TIME_CONSTANT = 28;
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.left_to_right_slide);
        animation.setDuration(mTvMenuItemTimeTable.getText().length() * TIME_CONSTANT);
        mTvMenuItemTimeTable.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.left_to_right_slide);
        animation.setDuration(mTvMenuItemTeachers.getText().length() * TIME_CONSTANT);
        mTvMenuItemTeachers.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.left_to_right_slide);
        animation.setDuration(mTvMenuItemSubjects.getText().length() * TIME_CONSTANT);
        mTvMenuItemSubjects.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.left_to_right_slide);
        animation.setDuration(mTvMenuItemSettings.getText().length() * TIME_CONSTANT);
        mTvMenuItemSettings.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.left_to_right_slide);
        animation.setDuration(mTvMenuItemFriends.getText().length() * TIME_CONSTANT);
        mTvMenuItemFriends.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.left_to_right_slide);
        animation.setDuration(mTvMenuItemScoreBoard.getText().length() * TIME_CONSTANT);
        mTvMenuItemScoreBoard.startAnimation(animation);
    }

    @Override
    public void onBackPressed() {
        TwoButtonDialog.create(this, "Bạn muốn thoát ứng dụng phải không ? ", "Ở lại", "Thoát",
                new TwoButtonDialog.OnButtonClickListener() {
                    @Override
                    public void onNegativeButtonClick() {

                    }

                    @Override
                    public void onPositiveButtonClick() {
                        MainActivity.this.finish();
                    }
                }
        ).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_sync:
                doSyncAction();
                break;
            case R.id.item_share:
                break;
            case R.id.item_add_time_table:
                startActivity(new Intent(this, CreateTimeTableActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TimeTable timeTable = mListTimeTableData.get(position);
        switchTimeTable(timeTable);
        SharedDB.getInstance(this).setCurrentTimeTable(timeTable.getId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
