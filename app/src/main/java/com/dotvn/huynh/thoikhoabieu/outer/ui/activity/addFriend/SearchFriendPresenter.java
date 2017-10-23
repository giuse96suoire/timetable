package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.addFriend;

import com.dotvn.huynh.thoikhoabieu.inner.data.Interactors;
import com.dotvn.huynh.thoikhoabieu.inner.data.interactors.UserInteractor;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.Friend;
import com.dotvn.huynh.thoikhoabieu.inner.data.model.User;
import com.dotvn.huynh.thoikhoabieu.middle.converter.FriendConverter;
import com.dotvn.huynh.thoikhoabieu.outer.TwoCallback;
import com.dotvn.huynh.thoikhoabieu.outer.data.local.LocalDAOCallback;
import com.dotvn.huynh.thoikhoabieu.outer.util.UserInfoUtil;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by Manh Hoang Huynh on 28/09/2017.
 */

public class SearchFriendPresenter implements SearchFriendConstract.Presenter {
    private SearchFriendConstract.View mView;
    private Interactors.UserInteractor mUserInteractor;
    public SearchFriendPresenter(SearchFriendConstract.View view) {
        this.mView = view;
        this.mUserInteractor = new UserInteractor();
    }
    @Override
    public void searchFriend(String friendId) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            mView.showToast("Bạn chưa đăng nhập");
            return;
        }
        mView.showProgressDialog();
        mUserInteractor.searchToAddFromRemote(UserInfoUtil.convertPhoneNumberToE164(friendId), new TwoCallback<User>() {
            @Override
            public void onComplete(User result) {
                mView.dismissProgressDialog();
                if (result == null ||
                        (FirebaseAuth.getInstance().getCurrentUser() != null
                                && result != null &&
                                result.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
                    mView.showToast("Không tìm thấy");
                    mView.updateData(FriendConverter.fromUserModelToFriendModel(null));
                } else {
                    mView.showToast("Đã tìm thấy "+result.getPhoneNumber());
                    mView.updateData(FriendConverter.fromUserModelToFriendModel(result));
                }
            }

            @Override
            public void onFailure(String message) {
                mView.dismissProgressDialog();
                mView.showToast("Tìm kiếm thất bại");
            }
        });
    }

    @Override
    public void addFriend(Friend friend) {
        mUserInteractor.addFriendToLocal(friend, new LocalDAOCallback<Friend>() {
            @Override
            public void onSuccess(Friend item) {

            }

            @Override
            public void onSuccess(List<Friend> items) {

            }

            @Override
            public void onSuccess() {
                mView.showToast("Thêm bạn thành công");
            }

            @Override
            public void onError(Throwable error) {

            }
        });
//        FirebaseUser cuser = FirebaseAuth.getCurrentUser();
//        if (cuser == null) {
//            mView.showToast("Bạn chưa đăng nhập");
//            return;
//        }
//        String ref = FbUtil.getUserReference(cuser.getPhoneNumber()) +"/friends/"+friend.getPhoneNumber() ;
//        mUserInteractor.addFriendToRemote(ref, friend, new ThreeCallback<Void>() {
//            @Override
//            public void onComplete() {
//
//            }
//
//            @Override
//            public void onSuccess(Void result) {
//                mView.showToast("Thêm bạn thành công");
//            }
//
//            @Override
//            public void onFailure(String message) {
//                mView.showToast("Thêm bạn thất bại");
//            }
//        });
    }
}
