package com.dotvn.huynh.thoikhoabieu.outer.ui.fragment.friends;

import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.outer.data.DAOContainer;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAO;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.ui.activity.addFriend.SearchFriendConstract;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 30/09/2017.
 */

public class FriendsPresenter implements FriendsConstract.Preseneter {
    private SearchFriendConstract.View mView;
    private LocalDAO<Friend> mFriendLocalDAO;
    public FriendsPresenter(SearchFriendConstract.View view) {
        mView = view;
        mFriendLocalDAO = DAOContainer.getLocalFriendDAO();
    }
    @Override
    public void getAllFriend() {
        mFriendLocalDAO.getAll(new LocalDAOCallback<Friend>() {
            @Override
            public void onSuccess(Friend item) {

            }

            @Override
            public void onSuccess(List<Friend> items) {
                mView.updateData(items);
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
