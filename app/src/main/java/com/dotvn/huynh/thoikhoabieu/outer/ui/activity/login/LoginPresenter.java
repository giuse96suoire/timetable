package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.outer.ThreeCallback;
import com.dotvn.huynh.thoikhoabieu.outer.TwoCallback;
import com.dotvn.huynh.thoikhoabieu.outer.util.UserInfoUtil;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by huynh.mh on 9/26/2017.
 */

public class LoginPresenter implements LoginConstract.Presenter {
    private LoginConstract.View mView;
    private AuthCredential mAuthCredential;
    private Activity mActivity;
    private String mPhoneNumber; // use to resend code
    private String mVerificationId;
    private String mPassword;

    public LoginPresenter(LoginConstract.View view, Activity activity) {
        this.mView = view;
        this.mActivity = activity;
    }

    @Override
    public void resendSmsVerify(TwoCallback<PhoneAuthCredential> callback) {
        sendSmsVerify(mPhoneNumber, callback);
    }

    @Override
    public void sendSmsVerify(@NonNull String phoneNumber, @Nullable final TwoCallback<PhoneAuthCredential> callback) {
        mPhoneNumber = phoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                mActivity,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        mAuthCredential = phoneAuthCredential;
                        if (callback != null)
                            callback.onComplete(phoneAuthCredential);
                        mView.showCreatePasswordPanel();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            mView.showToast(R.string.message_phone_number_invalid);
                            mView.showRegisPanel();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            mView.showToast(R.string.message_we_have_sent_sms_to_you);
                        }
                        if (callback != null)
                            callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        mVerificationId = s;
                    }
                });        // OnVerificationStateChangedCallbacks
        mView.showVerifyCodePanel();
    }

    @Override
    public void loginWithEmailAndPassword(@NonNull String email, @NonNull String password, @Nullable final ThreeCallback<AuthResult> callback) {
        if (email.length() == 0 || password.length() == 0) {
            mView.showToast(R.string.message_please_fill_all_field);
            return;
        }
        mPassword = password;
        mView.showProgressDialog();
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (callback != null)
                            callback.onComplete();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (callback != null)
                            callback.onSuccess(authResult);
                        mView.dismissProgressDialog();
                        mView.showToast(R.string.message_login_success);
                        mView.finishActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (callback != null)
                            callback.onFailure(e.getMessage());
                        mView.dismissProgressDialog();
                        mView.showToast(R.string.message_login_failed);
                    }
                });
    }

    @Override
    public void loginWithCredential(@NonNull AuthCredential credential, @Nullable final ThreeCallback<AuthResult> callback) {
        FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        callback.onComplete();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        callback.onSuccess(authResult);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("public_profile", "user_friends"));
    }

    @Override
    public void updatePassword(@NonNull final String password, @Nullable final ThreeCallback<Void> callback) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mView.showProgressDialog();
            user.updatePassword(password)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (callback != null)
                                callback.onComplete();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if (callback != null)
                                callback.onSuccess(aVoid);
                            mView.dismissProgressDialog();
                            reAuth(mAuthCredential, new ThreeCallback<Void>() {
                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onSuccess(Void result) {
                                    if (user.getEmail() == null || user.getEmail().length() == 0) {
                                        updateEmail(UserInfoUtil.convertPhoneNumberToEmail(mPhoneNumber), new ThreeCallback<Void>() {
                                            @Override
                                            public void onComplete() {

                                            }

                                            @Override
                                            public void onSuccess(Void result) {
                                                mView.showToast("Update email thành công");
                                                mView.finishActivity();
                                            }

                                            @Override
                                            public void onFailure(String message) {
                                                mView.showToast("Update email thất bại");
                                                mView.finishActivity();
                                            }
                                        });
                                    } else {
                                        mView.showToast("Không cần update email, kết thúc");
                                        mView.finishActivity();
                                    }
                                }

                                @Override
                                public void onFailure(String message) {
                                    mView.showToast("Reauth thất bại, kết thúc");
                                    mView.finishActivity();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (callback != null)
                                callback.onFailure(e.getMessage());
                            mView.showToast("Update mật khẩu thất bại, kết thúc");
                            mView.dismissProgressDialog();
                        }
                    });
            return;
        }
        mView.showProgressDialog();
        loginWithCredential(mAuthCredential, new ThreeCallback<AuthResult>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(AuthResult result) {
                FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .updatePassword(password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (callback != null)
                                    callback.onComplete();
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (callback != null)
                                    callback.onSuccess(aVoid);
                                mView.dismissProgressDialog();
                                mView.finishActivity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (callback != null)
                                    callback.onFailure(e.getMessage());
                                mView.dismissProgressDialog();
                            }
                        });
            }

            @Override
            public void onFailure(String message) {
                mView.dismissProgressDialog();
            }
        });

    }

    @Override
    public void reAuth(@NonNull AuthCredential authCredential, @Nullable final ThreeCallback<Void> callback) {
        mView.showProgressDialog();
        FirebaseAuth.getInstance()
                .getCurrentUser().reauthenticate(mAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null)
                            callback.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (callback != null)
                            callback.onFailure(e.getMessage());
                        mView.dismissProgressDialog();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (callback != null)
                            callback.onSuccess(aVoid);
                        mView.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void updateEmail(@NonNull final String email, @Nullable final ThreeCallback<Void> callback) {
        mView.showProgressDialog();
        FirebaseAuth.getInstance().getCurrentUser().updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null)
                            callback.onComplete();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mView.dismissProgressDialog();
                        mView.showToast("update email thành công");
                        AuthCredential credential = EmailAuthProvider.getCredential(email, mPassword);
                        reAuth(credential, new ThreeCallback<Void>() {
                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onSuccess(Void result) {
                                if (callback != null)
                                    callback.onSuccess(result);
                            }

                            @Override
                            public void onFailure(String message) {

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (callback != null)
                            callback.onFailure(e.getMessage());
                        mView.showToast(R.string.message_create_first_info_failed);
                        mView.dismissProgressDialog();
                    }
                });

    }

    @Override
    public void verifyCode(@NonNull String code, @Nullable final ThreeCallback<AuthResult> callback) {
        final PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, code);
        mView.showProgressDialog();
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (callback != null)
                            callback.onComplete();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (callback != null)
                            callback.onSuccess(authResult);
                        mView.dismissProgressDialog();
                        mAuthCredential = phoneAuthCredential;
                        mView.showCreatePasswordPanel();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (callback != null)
                            callback.onFailure(e.getMessage());
                        mView.dismissProgressDialog();
                        mView.showToast(R.string.message_verify_code_invalid);
                    }
                });
    }
}
