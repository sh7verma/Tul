package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.LoadingButton;
import customviews.MaterialEditText;
import model.CallResendModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends BaseActivity {

    @BindView(R.id.ed_email_login)
    MaterialEditText edEmailLogin;
    @BindView(R.id.btn_reset)
    LoadingButton btnReset;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_forgot)
    TextView txtForgot;
    @BindView(R.id.txt_msg)
    TextView txtMsg;
    boolean isLoading;

    @Override
    protected int getContentView() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void initUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edEmailLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edEmailLogin.setTypeface(typeface);

        txtForgot.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        txtMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return ForgotPassword.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                isLoading = true;
                if (edEmailLogin.getText().toString().trim().isEmpty()) {
                    showAlert(btnReset, getResources().getString(R.string.enter_email));
                    isLoading = false;
                } else if (!validateEmail(edEmailLogin.getText())) {
                    showAlert(btnReset, getResources().getString(R.string.enter_valid_email));
                    isLoading = false;
                } else {
                    if (connectedToInternet()) {
                        isLoading = true;
                        btnReset.startLoading();
                        disableViews();
                        hitAPI();
                    } else {
                        showInternetAlert(btnReset);
                    }
                }
                break;
            case R.id.img_back:
                if (!isLoading) {
                    finish();
                    overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                }
                break;
        }
    }

    private void hitAPI() {
        Call<CallResendModel> call = RetrofitClient.getInstance().forgetPassword(edEmailLogin.getText().toString().trim());
        call.enqueue(new Callback<CallResendModel>() {
            @Override
            public void onResponse(Call<CallResendModel> call, Response<CallResendModel> response) {

                if (response.body().getResponse() != null) {
                    Toast.makeText(ForgotPassword.this, response.body().getResponse(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("message", response.body().getResponse());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    btnReset.loadingFailed();
                    showAlert(btnReset, response.body().error.getMessage());
                }
                isLoading = false;
                enableViews();
            }

            @Override
            public void onFailure(Call<CallResendModel> call, Throwable t) {
                isLoading = false;
                btnReset.cancelLoading();
                enableViews();
                showAlert(btnReset, t.getLocalizedMessage());
            }
        });
    }

    boolean validateEmail(CharSequence text) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }


    @Override
    public void onBackPressed() {
        if (!isLoading) {
            super.onBackPressed();
        }
    }

    private void disableViews() {
        edEmailLogin.setEnabled(false);
    }

    private void enableViews() {
        edEmailLogin.setEnabled(true);
    }
}
