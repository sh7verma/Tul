package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.LoadingButton;
import customviews.MaterialEditText;
import model.SignupModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;

/**
 * Created by applify on 9/26/2017.
 */

public class SignupActivity extends BaseActivity {
    private static final int REGGUEST = 3;

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_email_login)
    MaterialEditText edEmailLogin;
    @BindView(R.id.ed_password_login)
    MaterialEditText edPasswordLogin;
    @BindView(R.id.txt_hint_msg)
    TextView txtHintMsg;
    @BindView(R.id.btn_signup)
    LoadingButton btnSignup;

    boolean isLoading;

    @Override
    protected int getContentView() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edEmailLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edEmailLogin.setTypeface(typeface);

        edPasswordLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edPasswordLogin.setTypeface(typeface);

        txtHintMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
    }

    @Override
    protected void onCreateStuff() {
        ClickableSpan termsCond = new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ds.linkColor);    // you can use custom color
                ds.setUnderlineText(false);    // this remove the underline
            }


            @Override
            public void onClick(View widget) {
                // TODO Auto-generated method stub
                Uri uri = Uri.parse("http://www.thetulapp.com/terms");
                Intent inTerms = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(inTerms);
            }
        };

        SpannableString ss = new SpannableString(getString(R.string.agree_term_conditions));
        ss.setSpan(new ForegroundColorSpan(Color.GRAY), 0, ss.length()-21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(termsCond, ss.length() - 21, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.WHITE), ss.length() - 21, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD), ss.length() - 21, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtHintMsg.setText(ss);
        txtHintMsg.setMovementMethod(LinkMovementMethod.getInstance());

        edPasswordLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    verifyDetails();
                }
                return true;
            }
        });

    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

    }

    @Override
    protected Context getContext() {
        return SignupActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (!isLoading) {
                    finish();
                    overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                }
            case R.id.btn_signup:
                verifyDetails();
                break;
        }
    }

    private void verifyDetails() {
        if (edEmailLogin.getText().toString().trim().isEmpty()) {
            showAlert(btnSignup, getResources().getString(R.string.enter_email));
            isLoading = false;
        } else if (!validateEmail(edEmailLogin.getText())) {
            showAlert(btnSignup, getResources().getString(R.string.enter_valid_email));
            isLoading = false;
        } else if (edPasswordLogin.getText().toString().trim().isEmpty()) {
            showAlert(btnSignup, getResources().getString(R.string.enter_password));
            isLoading = false;
        } else if (edPasswordLogin.getText().toString().trim().length() < 8) {
            showAlert(btnSignup, getResources().getString(R.string.error_password));
            isLoading = false;
        } else {
            if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                Constants.closeKeyboard(this, btnSignup);
                disableViews();
                hitAPI();
            } else {
                showAlert(btnSignup, errorInternet);
            }
        }
    }

    private void hitAPI() {
        isLoading = true;
        btnSignup.startLoading();
        Call<SignupModel> call = RetrofitClient.getInstance().userSignup(edEmailLogin.getText().toString().trim(),
                edPasswordLogin.getText().toString(), platformStatus, Constants.EMAIL_LOGIN,
                utils.getString("device_token", ""), Constants.EMPTY, Constants.EMPTY,
                Constants.EMPTY, Constants.EMPTY,
                String.valueOf(utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL)));
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body().getResponse() != null) {
                    utils.setString("user_id", String.valueOf(response.body().getResponse().getId()));
                    utils.setString("access_token", response.body().getResponse().getAccess_token());
                    utils.setString("email", response.body().getResponse().getEmail());
                    utils.setInt("status", response.body().getResponse().getStatus());
                    utils.setString("user_name", response.body().getResponse().getUsername());
                    utils.setString("phone_number", response.body().getResponse().getPhone_number());
                    utils.setString("country_code", response.body().getResponse().getCountry_code());
                    utils.setString("profile_pic", response.body().getResponse().getUser_pic());
                    utils.setString("first_name", response.body().getResponse().getFirst_name());
                    utils.setString("last_name", response.body().getResponse().getLast_name());

                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());

                    utils.setInt(Constants.USER_LOGIN_MODE,response.body().getResponse().getLogin_type());


                    utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());


                    isLoading = false;
                    btnSignup.loadingSuccessful();

                    Intent inStarted = new Intent(mContext, VerifyEmailActivity.class);
                    if (getIntent().hasExtra(Constants.REG_GUEST)) {
                        inStarted.putExtra(Constants.REG_GUEST, 1);
                        startActivityForResult(inStarted, REGGUEST);
                    } else {
                        startActivity(inStarted);
                        finish();
                    }
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } else {
                    isLoading = false;
                    btnSignup.loadingFailed();
                    showAlert(btnSignup, response.body().error.getMessage());
                }
                enableViews();
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                isLoading = false;
                btnSignup.loadingFailed();
                enableViews();
                showAlert(btnSignup, t.getLocalizedMessage());
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REGGUEST) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (!isLoading)
            super.onBackPressed();
    }

    boolean validateEmail(CharSequence text) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    private void disableViews() {
        edEmailLogin.setEnabled(false);
        edPasswordLogin.setEnabled(false);
    }

    private void enableViews() {
        edEmailLogin.setEnabled(true);
        edPasswordLogin.setEnabled(true);
    }
}
