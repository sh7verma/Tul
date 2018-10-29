package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import api.RetrofitClient;
import butterknife.BindView;
import model.SignupModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * Created by dev on 17/8/18.
 */

public class UserModeActivity extends BaseActivity {

    private static final int PHONE = 1;
    private static final int REGGUEST = 3;

    @BindView(R.id.txt_sales)
    TextView txtSales;
    @BindView(R.id.txt_rentals)
    TextView txtRentals;
    @BindView(R.id.img_back)
    ImageView imgBack;

    int mUserMode = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_mode;
    }

    @Override
    protected void initUI() {
        if (getIntent().hasExtra(Constants.REG_GUEST)) {
            imgBack.setVisibility(View.VISIBLE);
        } else {
            imgBack.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initListener() {
        txtRentals.setOnClickListener(this);
        txtSales.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_rentals:
                mUserMode = Constants.USER_RENTAL;
                if (ContextCompat.checkSelfPermission(this, READ_PHONE_STATE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE}, PHONE);
                } else {
                    if (connectedToInternet()) {
                        hitSkipAPI();
                    } else {
                        showInternetAlert(txtRentals);
                    }
                }

                break;

            case R.id.txt_sales:
                mUserMode = Constants.USER_BUY;
                if (ContextCompat.checkSelfPermission(this, READ_PHONE_STATE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE}, PHONE);
                } else {
                    if (connectedToInternet()) {
                        hitSkipAPI();
                    } else {
                        showInternetAlert(txtRentals);
                    }
                }

                break;

            case R.id.img_back:
                finish();
                break;
        }
    }

    private void hitSkipAPI() {

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI;
        IMEI = telephonyManager.getDeviceId();

        showProgress();
        Call<SignupModel> call = RetrofitClient.getInstance().userSkip(IMEI, String.valueOf(mUserMode));
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
                    utils.setInt("email_verify", response.body().getResponse().getEmail_verify());
                    utils.setInt("new_message", 1);
                    utils.setInt("path", 1);

                    utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    //Skip//
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
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
                    showAlert(txtRentals, response.body().error.getMessage());
                }

            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                ///
//                btnFb.loadingFailed();
                hideProgress();
                showAlert(txtRentals, t.getLocalizedMessage());
            }
        });
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

}
