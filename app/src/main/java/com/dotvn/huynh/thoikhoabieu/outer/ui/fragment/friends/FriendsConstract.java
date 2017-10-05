package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.friends;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 30/09/2017.
 */

public interface FriendsConstract {
    interface View extends BaseConstract.View{

        void showDetailFriendDialog(Friend friend);

        void showAddFriendDialog(Friend friend);

        void finishActivity();

        void initView();

        void updateData(List<Friend> listFriend);

        void updateData(Friend friend);
    }

    interface Preseneter {
        void getAllFriend();
    }
}
