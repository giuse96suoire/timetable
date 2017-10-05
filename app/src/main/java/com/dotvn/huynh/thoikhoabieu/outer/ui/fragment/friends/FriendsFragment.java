package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.friends;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.addFriend.FriendAdapter;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.addFriend.SearchFriendConstract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FriendsFragment extends Fragment implements SearchFriendConstract.View {
    public static final String TAG = FriendsFragment.class.getSimpleName();
    public static final String KEY_SELECTABLE_MODE = "KEY_SELECTABLE_MODE";
    public static final String KEY_FRIEND_ID_ARRAY = "KEY_FRIEND_ID_ARRAY";
    private Unbinder mUnbinder;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.rv_list_friend)
    RecyclerView mRvListFriend;
    private List<Friend> mListFriend;
    private FriendAdapter mFriendAdapter;
    private FriendsConstract.Preseneter mPreseneter;
    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment newInstance(boolean isSelectableMode, ArrayList<String> friendIdArr) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_SELECTABLE_MODE, isSelectableMode);
        args.putStringArrayList(KEY_FRIEND_ID_ARRAY, friendIdArr);
        fragment.setArguments(args);
        return fragment;
    }

    public static FriendsFragment newInstance(boolean isSelectableMode) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_SELECTABLE_MODE, isSelectableMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void showDetailFriendDialog(Friend friend) {

    }

    @Override
    public void showAddFriendDialog(Friend friend) {

    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void finishActivity() {

    }

    @Override
    public void initView() {
        mListFriend = new ArrayList<>();
        mFriendAdapter = new FriendAdapter(getContext(), this, mListFriend, false, false);
        mRvListFriend.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvListFriend.setAdapter(mFriendAdapter);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showToast(int messageId) {

    }

    @Override
    public void updateData(List<Friend> listFriend) {
        mListFriend.clear();
        if (listFriend != null) {
            mListFriend.addAll(listFriend);
        }
        mFriendAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(Friend friend) {
        mListFriend.clear();
        if (friend != null) {
            mListFriend.add(friend);
        }
        mFriendAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mPreseneter = new FriendsPresenter(this);
        mPreseneter.getAllFriend();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
