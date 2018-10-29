package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
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
import utils.Constants;

public class LoginActivity extends BaseActivity {
    private static final int REGGUEST = 3;

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_email_login)
    MaterialEditText edEmailLogin;
    @BindView(R.id.ed_password_login)
    MaterialEditText edPasswordLogin;
    @BindView(R.id.txt_forgot)
    TextView txtForgot;
    @BindView(R.id.btn_login)
    LoadingButton btnLogin;

    boolean isLoading;

    DatabaseReference mDatabase;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edEmailLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edEmailLogin.setTypeface(typeface);

        edPasswordLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edPasswordLogin.setTypeface(typeface);

        txtForgot.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.038));

    }

    @Override
    protected void onCreateStuff() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        txtForgot.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return LoginActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (!isLoading) {
                    finish();
                    overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                }
                break;
            case R.id.txt_forgot:
                if (!isLoading) {
                    startActivityForResult(new Intent(mContext, ForgotPassword.class), 1);
                }
                break;
            case R.id.btn_login:
                verifyDetails();
                break;
        }
    }

    private void verifyDetails() {
        isLoading = true;
        if (edEmailLogin.getText().toString().trim().isEmpty()) {
            showAlert(btnLogin, getResources().getString(R.string.enter_email));
            isLoading = false;
        } else if (!validateEmail(edEmailLogin.getText())) {
            showAlert(btnLogin, getResources().getString(R.string.enter_valid_email));
            isLoading = false;
        } else if (edPasswordLogin.getText().toString().trim().isEmpty()) {
            showAlert(btnLogin, getResources().getString(R.string.enter_password));
            isLoading = false;
        } else if (edPasswordLogin.getText().toString().trim().length() < 8) {
            showAlert(btnLogin, getResources().getString(R.string.error_password));
            isLoading = false;
        } else {
            if (connectedToInternet()) {
                btnLogin.startLoading();
                disableViews();
                Constants.closeKeyboard(this, btnLogin);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hitAPI();
                    }
                }, 2000);
            } else {
                showInternetAlert(btnLogin);
            }
        }
    }

    private void hitAPI() {
        Call<SignupModel> call = RetrofitClient.getInstance().userSignin(edEmailLogin.getText().toString().trim(),
                edPasswordLogin.getText().toString().trim(), platformStatus, utils.getString("device_token", ""),String.valueOf(utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL)));
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
                                 utils.setInt("lender", response.body().getResponse().getLender());
                                 utils.setString("first_name", response.body().getResponse().getFirst_name());
                                 utils.setString("last_name", response.body().getResponse().getLast_name());
                                 utils.setInt("email_verify", response.body().getResponse().getEmail_verify());
                                 utils.setInt("new_message", 1);
                                 utils.setInt("path", 1);

                                 utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                                 utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                                 utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
                                 utils.setInt(Constants.ISSELLER, response.body().getResponse().getIs_seller());
                                 utils.setInt(Constants.ISCOMPANY, response.body().getResponse().getIs_company());
                                 utils.setInt(Constants.USER_LOGIN_MODE,response.body().getResponse().getLogin_type());
                                 utils.setString(Constants.VAT, response.body().getResponse().getVat());

                                 utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                                 utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                                 utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());

                                 utils.setString(Constants.USER_LATITUDE, response.body().getResponse().getLatitude());
                                 utils.setString(Constants.USER_LONGITUDE, response.body().getResponse().getLongitude());

                                 isLoading = false;
                                 btnLogin.loadingSuccessful();

                                 if (response.body().getResponse().getEmail_verify() == 0) {
                                     /// email not verified
                                     if (utils.getInt(Constants.ISEMAILSKIP, 0) == 1) {
                                         if (utils.getInt("status", 0) == 0 || utils.getInt("status", 0) == 1) {
                                             Intent intent = new Intent(mContext, CreateProfileActivity.class);
                                             if (getIntent().hasExtra(Constants.REG_GUEST)) {
                                                 intent.putExtra(Constants.REG_GUEST, 1);
                                                 startActivityForResult(intent, REGGUEST);
                                             } else {
                                                 startActivity(intent);
                                             }
                                             finish();
                                             overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                         } else if (utils.getInt("status", 0) == 2) {
                                             if (getIntent().hasExtra(Constants.REG_GUEST)) {
                                                 Intent intent = new Intent();
                                                 setResult(RESULT_OK, intent);
                                                 finish();
                                             } else {
                                                 Intent intent = new Intent(mContext, LandingActivity.class);
                                                 startActivity(intent);
                                                 finish();
                                             }
                                             overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                         }
                                     } else {
                                         Intent inStarted = new Intent(mContext, VerifyEmailActivity.class);
                                         startActivity(inStarted);
                                         finish();
                                         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                     }
                                 } else {
                                     /// email verified
                                     if (response.body().getResponse().getStatus() == 0 || response.body().getResponse().getStatus() == 1) {
                                         Intent inStarted = new Intent(mContext, CreateProfileActivity.class);
                                         if (getIntent().hasExtra(Constants.REG_GUEST)) {
                                             inStarted.putExtra(Constants.REG_GUEST, 1);
                                             startActivityForResult(inStarted, REGGUEST);
                                         } else {
                                             inStarted.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                             inStarted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(inStarted);
                                         }
                                         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                     } else {
                                         registerToFirebase(response.body().getResponse());
                                         if (getIntent().hasExtra(Constants.REG_GUEST)) {
                                             Intent intent = new Intent();
                                             setResult(RESULT_OK, intent);
                                             finish();
                                         } else {
                                             Intent inStarted = new Intent(mContext, LandingActivity.class);
                                             inStarted.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                             inStarted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(inStarted);
                                         }
                                         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                     }
                                 }
                             } else {
                                 isLoading = false;
                                 btnLogin.loadingFailed();
                                 showAlert(btnLogin, response.body().error.getMessage());
                             }
                             enableViews();
                         }

                         @Override
                         public void onFailure(Call<SignupModel> call, Throwable t) {
                             isLoading = false;
                             btnLogin.loadingFailed();
                             enableViews();
                             showAlert(btnLogin, t.getLocalizedMessage());
                         }
                     }

        );
    }

    @Override
    public void onBackPressed() {
        if (!isLoading) {
            super.onBackPressed();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (resultCode == REGGUEST) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    showAlert(btnLogin, data.getStringExtra("message"));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void registerToFirebase(SignupModel.ResponseBean response) {
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("access_token", response.getAccess_token());
        tempMap.put("online_status", "1");
        tempMap.put("profile_pic", response.getUser_pic());
        tempMap.put("push_token", utils.getString("device_token", ""));
        tempMap.put("platform_status", String.valueOf(Constants.PLATFORM_STATUS));
        tempMap.put("block_status", "0");
        tempMap.put("messages_notification", "1");
        tempMap.put("user_id", String.valueOf(response.getId()));
        tempMap.put("first_name", response.getFirst_name());
        tempMap.put("badge_count", "0");
        tempMap.put("last_name", response.getLast_name());
        tempMap.put("name", response.getFirst_name() + " " + response.getLast_name());
        utils.setString(Constants.USER_LATITUDE, response.getLatitude());
        utils.setString(Constants.USER_LONGITUDE, response.getLongitude());
        mDatabase.child(Constants.USER_ENDPOINT).child(String.valueOf(response.getId())).updateChildren(tempMap);
    }

}
