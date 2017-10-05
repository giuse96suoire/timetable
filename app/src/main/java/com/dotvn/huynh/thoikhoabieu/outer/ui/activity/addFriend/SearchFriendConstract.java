package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.addFriend;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;

import java.util.List;

/**
 * Created by huynh.mh on 9/26/2017.
 */

public interface SearchFriendConstract {
    interface View extends BaseConstract.View{
        void showDetailFriendDialog(Friend friend);
        void showAddFriendDialog(Friend friend);
        void finishActivity();
        void initView();
        void updateData(List<Friend> listFriend);
        void updateData(Friend friend);
    }
    interface Presenter {
        void searchFriend(String friendId);
        void addFriend(Friend friend);
    }
}
