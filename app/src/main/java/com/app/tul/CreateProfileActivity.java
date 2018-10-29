package com.app.tul;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.LoadingButton;
import model.SignupModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class CreateProfileActivity extends BaseActivity {

    private static final int REGGUEST = 3;

    final int RESULTCODE_COUNTRY = 1;
    private final int PIC = 2;

    @BindView(R.id.txt_create)
    TextView txtCreate;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.txt_phone_hint)
    TextView txtPhoneHint;
    @BindView(R.id.txt_code)
    TextView txtCode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.ed_first_name)
    EditText edFirstName;
    @BindView(R.id.ed_last_name)
    EditText edLastName;
    @BindView(R.id.ed_phone_no)
    EditText edPhoneNo;
    @BindView(R.id.btn_verify)
    LoadingButton btnVerify;
    boolean isLoading;
    private File mFile;
    private String mPath;

    @Override
    protected int getContentView() {
        return R.layout.activity_create_profile;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edFirstName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edFirstName.setTypeface(typeface);

        edLastName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edLastName.setTypeface(typeface);

        edPhoneNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edPhoneNo.setTypeface(typeface);

        txtCreate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        txtPhoneHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

        txtCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
    }

    @Override
    protected void onCreateStuff() {
        getCountryCode();
        if (!TextUtils.isEmpty(utils.getString("user_name", "")))
            setData();
//        utils.setInt("email_verify", 1);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        edPhoneNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    verifyDetails();
                }
                return true;
            }
        });
    }

    private void setData() {
        edFirstName.setText(utils.getString("first_name", ""));
        edFirstName.setSelection(edFirstName.getText().toString().length());

        edLastName.setText(utils.getString("last_name", ""));
        edLastName.setSelection(edLastName.getText().toString().length());

        if (!TextUtils.isEmpty(utils.getString("country_code", "")))
            txtCode.setText(utils.getString("country_code", ""));
        else
            txtCode.setText("+44");

        edPhoneNo.setText(utils.getString("phone_number", ""));
        edPhoneNo.setSelection(edPhoneNo.getText().toString().length());

        mPath = utils.getString("profile_pic", "");

        if (!TextUtils.isEmpty(utils.getString("profile_pic", "")))
            Picasso.with(mContext).load(utils.getString("profile_pic", ""))
                    .resize(Constants.dpToPx(80), Constants.dpToPx(80)).centerCrop()
                    .placeholder(R.mipmap.ic_add_image).into(imgProfile, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Log.e("Error = ", "Yes");
                }
            });
        else
            Picasso.with(mContext).load(R.mipmap.ic_add_image).resize(Constants.dpToPx(80), Constants.dpToPx(80)).centerCrop()
                    .into(imgProfile);

    }

    @Override
    protected void initListener() {
        llCode.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return CreateProfileActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_code:
                Intent inSelectCountry = new Intent(CreateProfileActivity.this, CountryCodeActivity.class);
                startActivityForResult(inSelectCountry, RESULTCODE_COUNTRY);
                break;
            case R.id.btn_verify:
                verifyDetails();
                break;
            case R.id.img_profile:
                Intent inProfile = new Intent(mContext, OptionPhotoSelection.class);
                if (!TextUtils.isEmpty(mPath)) {
                    inProfile.putExtra("visible", "yes");
                    inProfile.putExtra("path", mPath);
                }
                startActivityForResult(inProfile, PIC);
                break;
        }
    }

    private void verifyDetails() {
        if (mFile == null && TextUtils.isEmpty(mPath)) {
            showAlert(btnVerify, getString(R.string.error_pic));
        } else if (edFirstName.getText().toString().length() < 2) {
            showAlert(btnVerify, getString(R.string.error_first_name));
        } else if (edLastName.getText().toString().length() < 2) {
            showAlert(btnVerify, getString(R.string.error_last_name));
        } else if ((txtCode.getText().toString().trim()).isEmpty()) {
            showAlert(btnVerify, getString(R.string.error_code));
        } else if ((edPhoneNo.getText().toString().trim()).isEmpty()) {
            showAlert(btnVerify, getString(R.string.enter_phone));
        } else if (!validatePhone()) {
            showAlert(btnVerify, getString(R.string.enter_valid_number));
        } else {
            if (connectedToInternet()) {
                Constants.closeKeyboard(this, btnVerify);
                disableViews();
                isLoading = true;
                btnVerify.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hitAPI();
                    }
                }, 2000);
            } else
                showInternetAlert(btnVerify);
        }
    }

    private void hitAPI() {

        final RequestBody reqFile;
        MultipartBody.Part body;

        if (mFile != null) {
            reqFile = RequestBody.create(MediaType.parse("image/*"), mFile);
            body = MultipartBody.Part.createFormData("profile_pic", mFile.getName(), reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("image/*"), "");
            body = MultipartBody.Part.createFormData("profile_pic", "", reqFile);
        }

        RequestBody access_token = RequestBody.create(MediaType.parse("text/plain"), utils.getString("access_token", ""));
        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"), txtCode.getText().toString());
        RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"), edPhoneNo.getText().toString().trim());
        RequestBody firstname = RequestBody.create(MediaType.parse("text/plain"), edFirstName.getText().toString().trim());
        RequestBody lastname = RequestBody.create(MediaType.parse("text/plain"), edLastName.getText().toString().trim());
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), firstname + " " + lastname);

        Call<SignupModel> call = RetrofitClient.getInstance().createProfile(firstname, lastname, country_code, phone_number, access_token, body);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body().getResponse() != null) {
                    isLoading = false;
                    btnVerify.cancelLoading();

                    utils.setString("access_token", response.body().getResponse().getAccess_token());
                    utils.setString("email", response.body().getResponse().getEmail());
                    utils.setInt("status", response.body().getResponse().getStatus());
                    utils.setString("user_name", response.body().getResponse().getUsername());
                    utils.setString("first_name", response.body().getResponse().getFirst_name());
                    utils.setString("last_name", response.body().getResponse().getLast_name());
                    utils.setString("phone_number", response.body().getResponse().getPhone_number());
                    utils.setString("country_code", response.body().getResponse().getCountry_code());
                    utils.setString("profile_pic", response.body().getResponse().getUser_pic());
                    utils.setInt("otp", response.body().getResponse().getOtp());

                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());

                    utils.setInt(Constants.ISSELLER, response.body().getResponse().getIs_seller());
                    utils.setInt(Constants.ISCOMPANY, response.body().getResponse().getIs_company());
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());

                    utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());
                    utils.setString(Constants.USER_LATITUDE, response.body().getResponse().getLatitude());
                    utils.setString(Constants.USER_LONGITUDE, response.body().getResponse().getLongitude());
                    Intent verifyIntent = new Intent(mContext, VerifyPhoneActivity.class);
                    if (getIntent().hasExtra(Constants.REG_GUEST)) {
                        verifyIntent.putExtra(Constants.REG_GUEST, 1);
                        startActivityForResult(verifyIntent, REGGUEST);
                    } else {
                        startActivity(verifyIntent);
                    }

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        isLoading = false;
                        btnVerify.loadingFailed();
                        showAlert(btnVerify, response.body().error.getMessage());
                    }
                }
                enableViews();
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                isLoading = false;
                btnVerify.loadingFailed();
                enableViews();
                showAlert(btnVerify, t.getLocalizedMessage());
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULTCODE_COUNTRY) {
                txtCode.setText(data.getStringExtra("Country_code"));
            } else if (requestCode == PIC) {
                Log.e("Path = ", data.getStringExtra("filePath"));
                mPath = data.getStringExtra("filePath");
                mFile = new File(data.getStringExtra("filePath"));
                Picasso.with(mContext).load(mFile).resize(Constants.dpToPx(80), Constants.dpToPx(80))
                        .into(imgProfile);
            } else if (requestCode == REGGUEST) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }


    void getCountryCode() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tm.getNetworkCountryIso().toUpperCase();
        String[] rl = getResources().getStringArray(R.array.CountryCodes);

        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(countryCode.trim())) {
                txtCode.setText("+" + g[0]);
                utils.setString("country_code", "+" + g[0]);
                break;
            }
        }
    }

    boolean validatePhone() {
        Pattern pattern = Pattern.compile("[0-9+]{5,20}");
        Matcher matcher = pattern.matcher(edPhoneNo.getText());
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        if (!isLoading)
            super.onBackPressed();
    }

    private void disableViews() {
        edFirstName.setEnabled(false);
        edLastName.setEnabled(false);
        edPhoneNo.setEnabled(false);
        imgProfile.setEnabled(false);
        txtCode.setEnabled(false);
    }

    private void enableViews() {
        edFirstName.setEnabled(true);
        edLastName.setEnabled(true);
        imgProfile.setEnabled(true);
        edPhoneNo.setEnabled(true);
        txtCode.setEnabled(true);
    }

}
