package com.dotvn.huynh.thoikhoabieu.outer.ui.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dotvn.huynh.thoikhoabieu.R;
import com.dotvn.huynh.thoikhoabieu.outer.ui.dialog.TwoButtonDialog;
import com.dotvn.huynh.thoikhoabieu.outer.util.UserInfoUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.AuthCredential;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements
        LoginConstract.View,
        View.OnClickListener {
    private Unbinder mUnbinder;
    private CallbackManager mCallbackManager;
    @BindView(R.id.btn_login_fb)
    Button mBtnLoginFacebook;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.tv_go_to_register)
    TextView mTvGoToRegister;
    @BindView(R.id.tv_back_to_login)
    TextView mTvBackToLogin;
    @BindView(R.id.cv_login)
    CardView mCvLogin;
    @BindView(R.id.cv_register)
    CardView mCvRegister;
    @BindView(R.id.cv_verify)
    CardView mCvVerify;
    @BindView(R.id.et_telephone_number_register)
    EditText mEtTelephoneNumberRegister;
    @BindView(R.id.et_telephone_number_login)
    EditText mEtTelephoneNumberLogin;
    @BindView(R.id.et_password_login)
    EditText mEtPasswordLogin;
    @BindView(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @BindView(R.id.tv_cancel_verify)
    TextView mTvCancelVerify;
    @BindView(R.id.tv_resend_verify_code)
    TextView mTvResendVerifyCode;
    @BindView(R.id.btn_verify)
    Button mBtnVerify;
    @BindView(R.id.btn_finish_create_password)
    Button mBtnFinishCretePassword;
    @BindView(R.id.et_create_password)
    EditText mEtCreatePassword;
    private String mPhoneNumber;
    @BindView(R.id.cv_create_password)
    CardView mCvCreatePassword;
    private AuthCredential mAuthCredential;
    private ProgressDialog mProgressDialog;
    private LoginConstract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_24dp);
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Hủy đăng nhập", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        initView();
        mPresenter = new LoginPresenter(this, this);
    }

    private void initView() {
        mBtnLoginFacebook.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvGoToRegister.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mTvBackToLogin.setOnClickListener(this);
        mEtVerifyCode.setOnClickListener(this);
        mTvCancelVerify.setOnClickListener(this);
        mTvResendVerifyCode.setOnClickListener(this);
        mBtnVerify.setOnClickListener(this);
        mBtnFinishCretePassword.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login_fb:
                mPresenter.loginWithFacebook();
                break;
            case R.id.btn_login:
                String email = UserInfoUtil.convertPhoneNumberToEmail(mEtTelephoneNumberLogin.getText().toString());
                String password = mEtPasswordLogin.getText().toString();
                mPresenter.loginWithEmailAndPassword(email, password, null);
                break;
            case R.id.btn_register:
                String phoneNumberE164 = UserInfoUtil.convertPhoneNumberToE164(mEtTelephoneNumberRegister.getText().toString());
                mPresenter.sendSmsVerify(phoneNumberE164, null);
                break;
            case R.id.tv_go_to_register:
                showRegisPanel();
                break;
            case R.id.tv_back_to_login:
                showLoginPanel();
                break;
            case R.id.tv_cancel_verify:
                showRegisPanel();
                break;
            case R.id.tv_resend_verify_code:
                mPresenter.resendSmsVerify(null);
                break;
            case R.id.btn_finish_create_password:
                String newPassword = mEtCreatePassword.getText().toString();
                mPresenter.updatePassword(newPassword, null);
                break;
            case R.id.btn_verify:
                String code = mEtVerifyCode.getText().toString();
                if (code == null || code.length() == 0) {
                    showToast("Mã xác nhận không được để trống");
                    return;
                }
                mPresenter.verifyCode(mEtVerifyCode.getText().toString().trim(), null);
                break;
        }
    }


    private void startAnimationInOut(final View viewIn) {
        View viewOut = mCvLogin;
        if (mCvLogin.getVisibility() == View.VISIBLE) {
            viewOut = mCvLogin;
        } else if (mCvCreatePassword.getVisibility() == View.VISIBLE) {
            viewOut = mCvCreatePassword;
        } else if (mCvRegister.getVisibility() == View.VISIBLE) {
            viewOut = mCvRegister;
        } else if (mCvVerify.getVisibility() == View.VISIBLE) {
            viewOut = mCvVerify;
        }
        final View viewToOut = viewOut;
        startAnimation(viewToOut, R.anim.push_right_out, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewToOut.setVisibility(View.GONE);
                viewIn.setVisibility(View.VISIBLE);
                startAnimation(viewIn, R.anim.push_right_in, null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startAnimation(View view, @AnimRes int animId, Animation.AnimationListener callback) {
        Animation anim = AnimationUtils.loadAnimation(this, animId);
        anim.setAnimationListener(callback);
        view.startAnimation(anim);

    }

    @Override
    public void showDialog() {

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
    public void finishActivity() {
        finish();
    }

    @Override
    public void showLoginPanel() {
        startAnimationInOut(mCvLogin);
        setTitle(getString(R.string.title_login));
    }

    @Override
    public void showRegisPanel() {
        startAnimationInOut(mCvRegister);
        setTitle(getString(R.string.title_register));
    }

    @Override
    public void showVerifyCodePanel() {
        startAnimationInOut(mCvVerify);
        setTitle(getString(R.string.title_verify));
    }

    @Override
    public void showCreatePasswordPanel() {
        startAnimationInOut(mCvCreatePassword);
        setTitle(getString(R.string.title_create_password));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_LONG).show();;
    }

    @Override
    public void showExitDialog() {
        TwoButtonDialog.create(this,
                "Bạn có muốn thoát đăng nhập ?",
                "Hủy",
                "Thoát",
                new TwoButtonDialog.OnButtonClickListener() {
                    @Override
                    public void onNegativeButtonClick() {

                    }

                    @Override
                    public void onPositiveButtonClick() {
                        finishActivity();
                    }
                }
        )
                .show();
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }
}
