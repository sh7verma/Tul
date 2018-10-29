package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.LoadingButtonFacebook;
import model.SignupModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;

import static android.Manifest.permission.READ_PHONE_STATE;


public class AfterWalkthroughActivity extends BaseActivity {

    private static final int PHONE = 1;
    private static final int REGGUEST = 3;

    @BindView(R.id.txt_login)
    TextView txtLogin;
    @BindView(R.id.txt_signup)
    TextView txtSignup;
    //    @BindView(R.id.txt_facebook)
//    TextView txtFacebook;
    @BindView(R.id.btn_fb)
    LoadingButtonFacebook btnFb;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.ll_below)
    LinearLayout llBelow;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.txt_skip)
    TextView txtSkip;
    String mName, mEmail = "", mId, mImageUrl, mFirstName, mLastName;
    DatabaseReference mDatabase;
    /// facebook Code
    private CallbackManager callbackManager;
    private AccessToken accessToken;

    ///
    @Override
    protected int getContentView() {
        return R.layout.activity_after_walkthrough;
    }

    @Override
    protected void initUI() {
        txtLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        txtSignup.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtSkip.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

//        txtFacebook.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        LinearLayout.LayoutParams belowParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        belowParms.setMargins(mWidth / 7, 0, mWidth / 7, 0);
        llBelow.setLayoutParams(belowParms);
        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_BUY) == Constants.USER_BUY && getIntent().hasExtra(Constants.REG_GUEST)) {
            imgBack.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreateStuff() {

        //reset Skip data//
//        utils.setInt(Constants.BLOCKSTATUS, 0);
//        utils.setInt(Constants.ISGUEST, 0);
//        utils.setInt(Constants.ISEMAILSKIP, 0);

        getIntent().getIntExtra("regguest", 1);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        utils.setString("device_token", FirebaseInstanceId.getInstance().getToken());
//        loginButton.performClick();
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        accessToken = loginResult.getAccessToken();
                        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // TODO Auto-generated method stub
                                    Log.e("data", "" + object + "");
                                    try {
                                        URL image_value = new URL("http://graph.facebook.com/" + object.getString("id") + "/picture?width=640&&height=640");
                                        Log.e("url", image_value + "");
                                        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                                            mImageUrl = image_value + "";

                                            mName = object.getString("first_name") + object.getString("last_name");
                                            mFirstName = object.getString("first_name");
                                            mLastName = object.getString("last_name");
                                            mId = object.getString("id");
                                            if (object.has("email"))
                                                mEmail = object.getString("email");

                                            hitAPI();
                                            LoginManager.getInstance().logOut();
                                        } else {
                                            showAlert(loginButton, errorInternet);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // info.setText("Login attempt cancelled.");
                        Log.e("error", "cancel");
                        btnFb.loadingFailed();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        // info.setText("Login attempt failed.");
                        Log.e("error", e + "");
                    }
                });

    }

    @Override
    protected void initListener() {
        txtLogin.setOnClickListener(this);
        txtSignup.setOnClickListener(this);
        txtSkip.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return AfterWalkthroughActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_login:
                utils.setString("device_token", FirebaseInstanceId.getInstance().getToken());
                Intent login = new Intent(mContext, LoginActivity.class);
                if (getIntent().hasExtra(Constants.REG_GUEST)) {
                    login.putExtra(Constants.REG_GUEST, 1);
                    startActivityForResult(login, REGGUEST);
                } else {
                    startActivity(login);
                }
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            case R.id.txt_signup:
                utils.setString("device_token", FirebaseInstanceId.getInstance().getToken());
                Intent signup = new Intent(mContext, SignupActivity.class);
                if (getIntent().hasExtra(Constants.REG_GUEST)) {
                    signup.putExtra(Constants.REG_GUEST, 1);
                    startActivityForResult(signup, REGGUEST);
                } else {
                    startActivity(signup);
                }
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            case R.id.txt_skip:
                if (ContextCompat.checkSelfPermission(this, READ_PHONE_STATE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE}, PHONE);
                } else {
                    if (connectedToInternet()) {
                        hitSkipAPI();
                    } else {
                        showInternetAlert(llBelow);
                    }
                }
                break;
            case R.id.img_back:
                finish();
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hitSkipAPI();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REGGUEST) {
                finish();
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void hitAPI() {
        btnFb.startLoading();
        Call<SignupModel> call = RetrofitClient.getInstance().userSignup(mEmail,
                Constants.EMPTY, platformStatus, Constants.FACEBOOK_LOGIN, utils.getString("device_token", ""), mId,
                mImageUrl, mFirstName, mLastName, String.valueOf(utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL)));
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body().getResponse() != null) {
                    btnFb.loadingSuccessful();
                    utils.setString("user_id", String.valueOf(response.body().getResponse().getId()));
                    utils.setString("access_token", response.body().getResponse().getAccess_token());
                    utils.setString("email", response.body().getResponse().getEmail());
                    utils.setInt("status", response.body().getResponse().getStatus());
                    utils.setString("user_name", response.body().getResponse().getUsername());
                    utils.setString("first_name", response.body().getResponse().getFirst_name());
                    utils.setString("last_name", response.body().getResponse().getLast_name());
                    utils.setString("phone_number", response.body().getResponse().getPhone_number());
                    utils.setString("country_code", response.body().getResponse().getCountry_code());
                    utils.setString("profile_pic", response.body().getResponse().getUser_pic());
                    utils.setInt("lender", response.body().getResponse().getLender());
                    utils.setInt(Constants.ISSELLER, response.body().getResponse().getIs_seller());
                    utils.setInt(Constants.ISCOMPANY, response.body().getResponse().getIs_company());
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());

                    utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    utils.setString(Constants.USER_LATITUDE, response.body().getResponse().getLatitude());
                    utils.setString(Constants.USER_LONGITUDE, response.body().getResponse().getLongitude());


                    utils.setInt("email_verify", response.body().getResponse().getEmail_verify());
                    utils.setInt("path", 2);
                    utils.setInt("new_message", 1);

                    //Skip//
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());
                    utils.setInt(Constants.USER_LOGIN_MODE, response.body().getResponse().getLogin_type());
                    utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());

                    if (response.body().getResponse().getStatus() == 0 ||
                            response.body().getResponse().getStatus() == 1) {
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
                    } else if (response.body().getResponse().getStatus() == 2) {
                        registerToFirebase(response.body().getResponse());
                        if (getIntent().hasExtra(Constants.REG_GUEST)) {
                            finish();
                        } else {
                            Intent inStarted = new Intent(mContext, LandingActivity.class);
                            inStarted.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            inStarted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(inStarted);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        }
                    }
                } else {
                    btnFb.loadingFailed();
                    hideProgress();
                    showAlert(loginButton, response.body().error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                btnFb.loadingFailed();
                showAlert(loginButton, t.getLocalizedMessage());
            }
        });
    }

    private void hitSkipAPI() {

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI;
        IMEI = telephonyManager.getDeviceId();

        showProgress();
        Call<SignupModel> call = RetrofitClient.getInstance().userSkip(IMEI, String.valueOf(utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL)));
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body().getResponse() != null) {
                    hideProgress();
                    utils.setString("user_id", String.valueOf(response.body().getResponse().getId()));
                    utils.setString("access_token", response.body().getResponse().getAccess_token());
                    utils.setString("email", response.body().getResponse().getEmail());
                    utils.setInt("status", response.body().getResponse().getStatus());
                    utils.setString("user_name", response.body().getResponse().getUsername());
                    utils.setString("first_name", response.body().getResponse().getFirst_name());
                    utils.setString("last_name", response.body().getResponse().getLast_name());
                    utils.setString("phone_number", response.body().getResponse().getPhone_number());
                    utils.setString("country_code", response.body().getResponse().getCountry_code());
                    utils.setString("profile_pic", response.body().getResponse().getUser_pic());
                    utils.setInt("lender", response.body().getResponse().getLender());
                    utils.setInt(Constants.ISSELLER, response.body().getResponse().getIs_seller());
                    utils.setInt(Constants.ISCOMPANY, response.body().getResponse().getIs_company());
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());
                    utils.setInt("email_verify", response.body().getResponse().getEmail_verify());
                    utils.setInt("new_message", 1);
                    utils.setInt("path", 1);
                    utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    //Skip//
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());
                    utils.setInt(Constants.USER_LOGIN_MODE, response.body().getResponse().getLogin_type());
                    utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());
                    utils.setString(Constants.USER_LATITUDE, response.body().getResponse().getLatitude());
                    utils.setString(Constants.USER_LONGITUDE, response.body().getResponse().getLongitude());

                    if (getIntent().hasExtra(Constants.REG_GUEST)) {
                        finish();
                    } else {
                        Intent Skip = new Intent(mContext, LandingActivity.class);
                        startActivity(Skip);
                        finish();
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }
                } else {
//                    btnFb.loadingFailed();
                    hideProgress();
                    showAlert(loginButton, response.body().error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                hideProgress();
//                btnFb.loadingFailed();
                showAlert(loginButton, t.getLocalizedMessage());
            }
        });
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
