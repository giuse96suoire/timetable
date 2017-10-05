package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.profile;

import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.dotvn.huynh.thoikhoabieu.outer.TwoCallback;
import com.dotvn.huynh.thoikhoabieu.outer.ui.BaseConstract;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;

/**
 * Created by huynh.mh on 9/26/2017.
 */

public interface ProfileConstract {
    interface View extends BaseConstract.View{
        void finishActivity();
    }
    interface Presenter {
        void resendSmsVerify(TwoCallback<PhoneAuthCredential> callback);
        void sendSmsVerify(String phoneNumber, TwoCallback<PhoneAuthCredential> callback);
        void loginWithEmailAndPassword(String email, String password, ThreeCallback<AuthResult> callback);
        void loginWithCredential(AuthCredential credential, ThreeCallback<AuthResult> callback);
        void updatePassword(String password, ThreeCallback<Void> callback);
        void reAuth(AuthCredential authCredential, final ThreeCallback<Void> callback);
        void updateEmail(String email, ThreeCallback<Void> callback);
    }
}
