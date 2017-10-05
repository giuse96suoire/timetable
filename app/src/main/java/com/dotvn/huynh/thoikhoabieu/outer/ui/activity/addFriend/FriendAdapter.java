package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.addFriend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manh Hoang Huynh on 21/08/2017.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    // list data
    private List<Friend> mListFriend;
    // mIsSelectableMode: true if in SubjectActivity (can check to select subject)
    // mIsViewTimeMode: true if in OneDayFragment (view start time, end time - no checkable)
    private boolean mIsSelectableMode, mIsViewTimeMode;

    // mListSelectedFriends: list selected subject in SubjectActivity
    private List<Friend> mListSelectedFriends;

    // mLastestLoadedPosission: the last index of item that was loaded with animation
    private int mLastestLoadedPosission = -1;
    private Context mContext;
    private SearchFriendConstract.View mView;
    private boolean mIsSearchMode;
    public FriendAdapter(Context context, SearchFriendConstract.View view, List<Friend> listFriend, boolean isSelectableMode, boolean isSearchMode) {
        this.mListFriend = listFriend;
        this.mIsSelectableMode = isSelectableMode;
        if (isSelectableMode) {
            mListSelectedFriends = new ArrayList<>();
        }
        this.mContext = context;
        this.mView = view;
        this.mIsSearchMode = isSearchMode;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new FriendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Friend friend = mListFriend.get(position);
        holder.mTvFriendName.setText(friend.getDisplayName() == null || friend.getDisplayName().length() == 0 ? "Chưa đặt tên" : friend.getDisplayName());
        holder.mTvFriendPhoneNumber.setText(friend.getPhoneNumber());
        String standFor = "?";
        if (friend.getDisplayName() != null && friend.getDisplayName().length() >= 1) {
            standFor = friend.getDisplayName().substring(0, 1).toUpperCase();
        }
        holder.mTvFriendNameStandFor.setText(standFor);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsSelectableMode) {
                    holder.mCbFriendSelect.setChecked(!holder.mCbFriendSelect.isChecked());
                } else {
                    mView.showDetailFriendDialog(friend);
                }
            }
        });

        holder.mCbFriendSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addIfNotExist(friend);
                } else {
                    removeIfExist(friend);
                }
            }
        });

        if (mIsSelectableMode) {
            if (getIndexOfSubjectIfExist(friend) != -1) {
                holder.mCbFriendSelect.setChecked(true);
            } else {
                holder.mCbFriendSelect.setChecked(false);
            }
        }
        setAnimation(holder.itemView, position);


        holder.itemView.setBackground(
                position % 2 == 0
                        ? mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector1)
                        : mContext.getResources().getDrawable(R.drawable.subject_item_backgroud_selector0)
        );
        if (mIsSearchMode) {
            holder.mIbAddThisFriend.setVisibility(View.VISIBLE);
            holder.mIbCallToFriend.setVisibility(View.GONE);
            holder.mIbSmsToFriend.setVisibility(View.GONE);
            holder.mIbAddThisFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.showAddFriendDialog(friend);
                }
            });
        } else {
            holder.mIbAddThisFriend.setVisibility(View.GONE);
            holder.mIbCallToFriend.setVisibility(View.VISIBLE);
            holder.mIbSmsToFriend.setVisibility(View.VISIBLE);
        }

    }

    private void addIfNotExist(Friend friend) {
        int index = getIndexOfSubjectIfExist(friend);
        if (index == -1)
            mListSelectedFriends.add(friend);
    }

    private void removeIfExist(Friend friend) {
        int index = getIndexOfSubjectIfExist(friend);
        if (index != -1)
            mListSelectedFriends.remove(index);
    }

    private int getIndexOfSubjectIfExist(Friend friend) {
        for (int i = 0; i < mListSelectedFriends.size(); i++) {
            Friend f = mListSelectedFriends.get(i);
            if (f.getPhoneNumber().equals(friend.getPhoneNumber())) {
                return i;
            }
        }
        return -1;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > mLastestLoadedPosission) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            mLastestLoadedPosission = position;
        }
    }

    @Override
    public int getItemCount() {
        return mListFriend.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "ViewHolderFriend";
        private TextView mTvFriendName;
        private TextView mTvFriendPhoneNumber;
        private TextView mTvFriendNameStandFor;
        private ImageButton mIbSmsToFriend;
        private ImageButton mIbCallToFriend;
        private ImageButton mIbAddThisFriend;
        private CheckBox mCbFriendSelect;
        public ViewHolder(View itemView) {
            super(itemView);
            mTvFriendName = (TextView) itemView.findViewById(R.id.tv_friend_name);
            mTvFriendNameStandFor = (TextView) itemView.findViewById(R.id.tv_friend_name_stand_for);
            mTvFriendPhoneNumber = (TextView) itemView.findViewById(R.id.tv_friend_phone_number);
            mIbSmsToFriend = (ImageButton) itemView.findViewById(R.id.ib_sms_friend);
            mIbCallToFriend = (ImageButton) itemView.findViewById(R.id.ib_call_friend);
            mIbAddThisFriend = (ImageButton) itemView.findViewById(R.id.ib_add_this_friend);
            mCbFriendSelect = (CheckBox) itemView.findViewById(R.id.cb_select_friend);
        }

    }

}
