package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.addFriend;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.TwoButtonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchFriendActivity extends AppCompatActivity implements SearchFriendConstract.View, View.OnClickListener {
    private Unbinder mUnbinder;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.et_friend_phone_number)
    EditText mEtFriendPhoneNumber;
    @BindView(R.id.cv_scan_friend_in_contact)
    CardView mCvScanFriendInContact;
    @BindView(R.id.rv_list_friend)
    RecyclerView mRvListFriend;
    private FriendAdapter mFriendAdapter;
    private SearchFriendConstract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private List<Friend> mListFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        mUnbinder = ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_24dp);
        mPresenter = new SearchFriendPresenter(this);
        mListFriend = new ArrayList<>();
        mFriendAdapter = new FriendAdapter(this, this, mListFriend, false, true);
        mRvListFriend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvListFriend.setNestedScrollingEnabled(false);
        mRvListFriend.setAdapter(mFriendAdapter);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
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
    public void showDetailFriendDialog(Friend friend) {

    }

    @Override
    public void showAddFriendDialog(final Friend friend) {
        TwoButtonDialog.create(this, "Thêm " + friend.getDisplayName() + " vào danh sách bạn bè ?",
                "Hủy",
                "Thêm",
                new TwoButtonDialog.OnButtonClickListener() {
                    @Override
                    public void onNegativeButtonClick() {

                    }

                    @Override
                    public void onPositiveButtonClick() {
                        mPresenter.addFriend(friend);
                    }
                }
        ).show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void finishActivity() {

    }

    @Override
    public void initView() {
        mTvSearch.setOnClickListener(this);
        mCvScanFriendInContact.setOnClickListener(this);
        mEtFriendPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.toString().length() == 0) {
                    mTvSearch.setEnabled(false);
                } else {
                    mTvSearch.setEnabled(true);
                }
            }
        });
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
    public void updateData(List<Friend> listFriend) {
        mListFriend.clear();
        if (listFriend != null)
            mListFriend.addAll(listFriend);
        mFriendAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(Friend friend) {
        mListFriend.clear();
        if (friend != null)
            mListFriend.add(friend);
        mFriendAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_search:
                String friendPhoneNumber = mEtFriendPhoneNumber.getText().toString();
                if (friendPhoneNumber == null || friendPhoneNumber.length() == 0) {
                    showToast("Nhập gì đó đi");
                    return;
                }
                mPresenter.searchFriend(friendPhoneNumber);
                break;
            case R.id.cv_scan_friend_in_contact:
                break;
        }
    }
}
