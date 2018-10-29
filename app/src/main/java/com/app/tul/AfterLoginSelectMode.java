package com.app.tul;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by dev on 17/9/18.
 */

public class AfterLoginSelectMode extends BaseActivity {

    @BindView(R.id.txt_sales)
    TextView txtSales;
    @BindView(R.id.txt_rentals)
    TextView txtRentals;
    @BindView(R.id.img_back)
    ImageView imgBack;
    private String status = "0";

    @Override
    protected int getContentView() {
        return R.layout.activity_user_mode;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void onCreateStuff() {
        imgBack.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        txtRentals.setOnClickListener(this);
        txtSales.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_rentals:
                status = "0";
                if (connectedToInternet()) {
                    hitSwitchApi();
                } else {
                    showInternetAlert(txtRentals);
                }
                break;

            case R.id.txt_sales:
                status = "1";
                if (connectedToInternet()) {
                    hitSwitchApi();
                } else {
                    showInternetAlert(txtRentals);
                }
                break;
        }
    }

    private void hitSwitchApi() {
        showProgress();
        Call<SignupModel> call = RetrofitClient.getInstance().switch_profile(utils.getString("access_token", ""), status);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {

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
                    utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());

                    //Skip//
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());
                    utils.setInt(Constants.USER_LOGIN_MODE, response.body().getResponse().getLogin_type());

                    utils.setString(Constants.USER_LATITUDE, response.body().getResponse().getLatitude());
                    utils.setString(Constants.USER_LONGITUDE, response.body().getResponse().getLongitude());

                    Intent inSplash = new Intent(mContext, LandingActivity.class);
                    inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(inSplash);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(txtRentals, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                hideProgress();
                showAlert(txtRentals, t.getMessage());
            }
        });
    }

}
