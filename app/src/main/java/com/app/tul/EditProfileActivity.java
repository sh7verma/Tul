package com.app.tul;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.Sales.SaleVerifyPhoneNumber;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.LoadingButton;
import customviews.MaterialEditText;
import model.DemoModel;
import model.SignupModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class EditProfileActivity extends BaseActivity {
    private static final int EDITPROFILE = 1;
    private static final int LOCATION = 11;
    final int RESULTCODE_COUNTRY = 4;
    private final int PIC = 2;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.ed_first_name)
    MaterialEditText edFirstName;
    @BindView(R.id.ed_last_name)
    EditText edLastName;
    @BindView(R.id.btn_update)
    LoadingButton btnUpdate;

    @BindView(R.id.txt_phone_hint)
    TextView txtPhoneHint;
    @BindView(R.id.txt_code)
    TextView txtCode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.ed_phone_no)
    EditText edPhoneNo;

    @BindView(R.id.ll_last_name)
    LinearLayout llLastName;

    @BindView(R.id.ed_vat)
    MaterialEditText edVat;
    @BindView(R.id.ll_vat)
    LinearLayout llVat;

    @BindView(R.id.ed_location)
    MaterialEditText edLocation;
    @BindView(R.id.view_location)
    View viewLocation;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.view_email)
    View view_email;

    @BindView(R.id.ed_unverified_email)
    EditText ed_unverified_email;
    @BindView(R.id.ll_unverified_email)
    LinearLayout llUnverifiedEmail;

    boolean isLoading;
    Double mLat, mLong;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (TextUtils.isEmpty(utils.getString(Constants.UNVERIFIED_EMAIL, ""))
                    || utils.getString(Constants.UNVERIFIED_EMAIL, "").equals(utils.getString("email", ""))) {
                llUnverifiedEmail.setVisibility(View.GONE);
            } else {
                llUnverifiedEmail.setVisibility(View.VISIBLE);
                ed_unverified_email.setText(utils.getString(Constants.UNVERIFIED_EMAIL, ""));
            }
            edEmail.setText(utils.getString("email", ""));

        }
    };
    private File mFile;
    private String mPath;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void initUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edFirstName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edFirstName.setTypeface(typeface);

        edLastName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edLastName.setTypeface(typeface);

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtToolbarTitle.setText(getResources().getString(R.string.edit_profile));

        edPhoneNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edPhoneNo.setTypeface(typeface);

        txtCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtCode.setTypeface(typeface);

        edVat.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edVat.setTypeface(typeface);

        edLocation.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edLocation.setTypeface(typeface);

        edEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edEmail.setTypeface(typeface);

        ed_unverified_email.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        ed_unverified_email.setTypeface(typeface);

    }

    @Override
    protected void onCreateStuff() {
        edFirstName.setText(utils.getString("first_name", ""));
        edFirstName.setSelection(edFirstName.getText().toString().length());

        edLastName.setText(utils.getString("last_name", ""));
        edLastName.setSelection(edLastName.getText().toString().length());

        edEmail.setText(utils.getString("email", ""));

        if (utils.getInt("path", 0) == 2) {/// fb login
            edEmail.setVisibility(View.GONE);
            view_email.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(utils.getString("country_code", "")))
            txtCode.setText(utils.getString("country_code", ""));
        else
            txtCode.setText("+44");

        edPhoneNo.setText(utils.getString("phone_number", ""));
        edPhoneNo.setSelection(edPhoneNo.getText().toString().length());

        mPath = utils.getString("profile_pic", "");


        getCountryCode();
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


        if (TextUtils.isEmpty(utils.getString(Constants.UNVERIFIED_EMAIL, ""))
                || utils.getString(Constants.UNVERIFIED_EMAIL, "").equals(utils.getString("email", ""))) {
            llUnverifiedEmail.setVisibility(View.GONE);
        } else {
            llUnverifiedEmail.setVisibility(View.VISIBLE);
            ed_unverified_email.setText(utils.getString(Constants.UNVERIFIED_EMAIL, ""));
        }

        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
            llVat.setVisibility(View.GONE);
            edLocation.setVisibility(View.GONE);
            viewLocation.setVisibility(View.GONE);
        } else {
            if (utils.getInt(Constants.ISSELLER, 0) == Constants.IS_SELLER) {
                edLocation.setVisibility(View.VISIBLE);
                viewLocation.setVisibility(View.VISIBLE);


                if (!TextUtils.isEmpty(utils.getString(Constants.USER_LATITUDE, ""))) {
                    mLat = Double.valueOf(utils.getString(Constants.USER_LATITUDE, ""));
                    mLong = Double.valueOf(utils.getString(Constants.USER_LONGITUDE, ""));
                    getAddress(Double.parseDouble(utils.getString(Constants.USER_LATITUDE, ""))
                            , Double.parseDouble(utils.getString(Constants.USER_LONGITUDE, "")));
                }
            } else {
                edLocation.setVisibility(View.GONE);
                viewLocation.setVisibility(View.GONE);
            }

            if (utils.getInt(Constants.ISCOMPANY, 0) == Constants.IS_COMPANY) {

                edFirstName.setHint(getString(R.string.company_name));
                edFirstName.setFloatingLabelText(getString(R.string.company_name));

                edLocation.setHint(getString(R.string.warehouse_location));
                edLocation.setFloatingLabelText(getString(R.string.warehouse_location));

                llLastName.setVisibility(View.GONE);
                edLastName.setText("");
                llVat.setVisibility(View.VISIBLE);
                edVat.setText(utils.getString(Constants.VAT, ""));
            } else {
                llVat.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.EMAIL_VERIFY));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        edLocation.setOnClickListener(this);
        ed_unverified_email.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return EditProfileActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_unverified_email:
                hitResendApi();
                break;
            case R.id.ll_code:
                Intent inSelectCountry = new Intent(this, CountryCodeActivity.class);
                startActivityForResult(inSelectCountry, RESULTCODE_COUNTRY);
                break;
            case R.id.img_profile:
                Intent inProfile = new Intent(mContext, OptionPhotoSelection.class);
                if (mPath != null) {
                    inProfile.putExtra("visible", "yes");
                    inProfile.putExtra("path", mPath);
                }
                startActivityForResult(inProfile, PIC);
                break;
            case R.id.btn_update:
                if (mFile == null && mPath == null) {
                    showAlert(btnUpdate, getString(R.string.error_pic));
                } else if (edFirstName.getText().toString().trim().length() < 2) {
                    if (getIntent().getIntExtra(Constants.SALES_MODE, 0) == Constants.SALES_MODE_COMPANY) {
                        showAlert(btnUpdate, getString(R.string.error_company_name));
                    } else {
                        showAlert(btnUpdate, getString(R.string.first_name));
                    }
                } else if (edPhoneNo.getText().toString().trim().length() < 2) {
                    showAlert(btnUpdate, getString(R.string.phone_no));
                } else if (edEmail.getText().toString().trim().isEmpty()) {
                    showAlert(edEmail, getResources().getString(R.string.enter_email));
                } else if (!validateEmail(edEmail.getText())) {
                    showAlert(edEmail, getResources().getString(R.string.enter_valid_email));
                } else if (TextUtils.isEmpty(edVat.getText())
                        && utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_BUY
                        && utils.getInt(Constants.ISSELLER, 0) == Constants.IS_SELLER
                        && utils.getInt(Constants.ISCOMPANY, 0) == Constants.IS_COMPANY) {
                    showAlert(edVat, getString(R.string.enter_vat));
                } else if ((edLocation.getText().toString().trim()).isEmpty()
                        && utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_BUY
                        && utils.getInt(Constants.ISSELLER, 0) == Constants.IS_SELLER) {
                    showAlert(edLocation, getString(R.string.enter_loc));
                } else {
                    if (connectedToInternet()) {
                        Constants.closeKeyboard(this, btnUpdate);
                        disableViews();
                        isLoading = true;
                        btnUpdate.startLoading();
//                        hitAPI();
                        hitSalesAPI();
                    } else
                        showInternetAlert(btnUpdate);
                }
                break;
            case R.id.ed_location:
                Intent addressIntent = new Intent(mContext, LocationSearchActivity.class);
                startActivityForResult(addressIntent, LOCATION);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.img_back:
                if (!isLoading) {
                    finish();
                    overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                }
                break;
        }
    }

    boolean validateEmail(CharSequence text) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
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
        RequestBody firstname = RequestBody.create(MediaType.parse("text/plain"), edFirstName.getText().toString().trim());
        RequestBody lastname = RequestBody.create(MediaType.parse("text/plain"), edLastName.getText().toString().trim());
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), firstname + " " + lastname);


        Call<SignupModel> call = RetrofitClient.getInstance().updateProfile(access_token, firstname, lastname, body);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body().getResponse() != null) {
                    isLoading = false;
                    btnUpdate.loadingSuccessful();
                    utils.setString("user_name", response.body().getResponse().getUsername());
                    utils.setString("first_name", response.body().getResponse().getFirst_name());
                    utils.setString("last_name", response.body().getResponse().getLast_name());
                    utils.setString("profile_pic", response.body().getResponse().getUser_pic());

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        isLoading = false;
                        btnUpdate.loadingFailed();
                        showAlert(btnUpdate, response.body().error.getMessage());
                    }
                }
                enableViews();
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                isLoading = false;
                btnUpdate.loadingFailed();
                enableViews();
                showAlert(btnUpdate, t.getLocalizedMessage());
            }
        });
    }

    void hitSalesAPI() {

        final RequestBody reqFile;
        MultipartBody.Part profile_pic;

        final RequestBody reqFileBank;
        MultipartBody.Part document = null;

        if (mFile != null) {
            reqFile = RequestBody.create(MediaType.parse("image/*"), mFile);
            profile_pic = MultipartBody.Part.createFormData("profile_pic", mFile.getName(), reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("image/*"), "");
            profile_pic = MultipartBody.Part.createFormData("profile_pic", "", reqFile);
        }

        RequestBody access_token = RequestBody.create(MediaType.parse("text/plain"),
                utils.getString("access_token", ""));
        RequestBody pin_code = RequestBody.create(MediaType.parse("text/plain"),
                txtCode.getText().toString());
        RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"),
                edPhoneNo.getText().toString().trim());
        RequestBody user_first_name = RequestBody.create(MediaType.parse("text/plain"),
                edFirstName.getText().toString().trim());
        RequestBody user_last_name = RequestBody.create(MediaType.parse("text/plain"),
                edLastName.getText().toString().trim());


        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody currency = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody account_number = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody sort_code = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody last_name = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody postal_code = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), "");

        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), edLocation.getText().toString().trim());
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mLat).trim());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mLong).trim());

        RequestBody is_company = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(utils.getInt(Constants.ISCOMPANY, 0)));

        RequestBody swift = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody is_account_added = RequestBody.create(MediaType.parse("text/plain"), "0");

        RequestBody account_type = RequestBody.create(MediaType.parse("text/plain"), "0");

        RequestBody is_seller = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(utils.getInt(Constants.ISSELLER, Constants.IS_SELLER)));

        RequestBody Is_number_updated;
        if (utils.getString("phone_number", "").equals(edPhoneNo.getText().toString())) {
            Is_number_updated = RequestBody.create(MediaType.parse("text/plain"), "0");
        } else {
            Is_number_updated = RequestBody.create(MediaType.parse("text/plain"), "1");
        }

        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edEmail.getText().toString().trim());

        RequestBody email_changed;
        if (utils.getString("email", "").equals(edEmail.getText().toString())) {
            email_changed = RequestBody.create(MediaType.parse("text/plain"), "0");
        } else {
            email_changed = RequestBody.create(MediaType.parse("text/plain"), "1");
        }

        RequestBody vat = RequestBody.create(MediaType.parse("text/plain"), utils.getString(Constants.VAT, ""));

        Call<SignupModel> call = RetrofitClient.getInstance().createSellerProfile(user_first_name, user_last_name
                , pin_code, phone_number, country_code, currency, account_number, sort_code, first_name,
                last_name, address, city, postal_code, state, dob, location, latitude,
                longitude, access_token, is_company, swift, is_account_added, Is_number_updated, is_seller, vat,
                account_type, email_changed, email, profile_pic, document);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                //
                if (response.body().getResponse() != null) {
                    isLoading = false;
                    btnUpdate.cancelLoading();

                    utils.setString("access_token", response.body().getResponse().getAccess_token());
                    utils.setString("email", response.body().getResponse().getEmail());
//                  utils.setInt("status", response.body().getResponse().getStatus());
                    utils.setString("user_name", response.body().getResponse().getUsername());
                    utils.setString("first_name", response.body().getResponse().getFirst_name());
                    utils.setString("last_name", response.body().getResponse().getLast_name());
                    utils.setString("phone_number", response.body().getResponse().getPhone_number());
                    utils.setString("country_code", response.body().getResponse().getCountry_code());
                    utils.setString("profile_pic", response.body().getResponse().getUser_pic());
                    utils.setInt("otp", response.body().getResponse().getOtp());

                    utils.setString(Constants.USER_LATITUDE, response.body().getResponse().getLatitude());
                    utils.setString(Constants.USER_LONGITUDE, response.body().getResponse().getLongitude());

                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
                    utils.setInt(Constants.ISSELLER, response.body().getResponse().getIs_seller());
                    utils.setInt(Constants.ISCOMPANY, response.body().getResponse().getIs_company());
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());
                    utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());

                    if (!utils.getString("phone_number", "").equals(edPhoneNo.getText().toString())) {
                        Intent verifyIntent = new Intent(mContext, SaleVerifyPhoneNumber.class);
                        if (getIntent().hasExtra("edit")) {
                            verifyIntent.putExtra("edit", "edit");
                        }
                        verifyIntent.putExtra("code", txtCode.getText().toString());
                        verifyIntent.putExtra("phone", edPhoneNo.getText().toString().trim());
                        startActivityForResult(verifyIntent, EDITPROFILE);

                    } else if (utils.getString(Constants.UNVERIFIED_EMAIL, "").equals(edEmail.getText().toString())) {
                        finish();
                        Intent intent = new Intent(mContext, EditVerifyEmail.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        isLoading = false;
                        btnUpdate.loadingFailed();
                        showAlert(btnUpdate, response.body().error.getMessage());
                    }
                }
                enableViews();
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PIC) {
                Log.e("Path = ", data.getStringExtra("filePath"));
                mPath = data.getStringExtra("filePath");
                mFile = new File(data.getStringExtra("filePath"));
                Picasso.with(mContext).load(mFile).resize(Constants.dpToPx(80), Constants.dpToPx(80))
                        .into(imgProfile);
            } else if (requestCode == EDITPROFILE) {
                if (utils.getString(Constants.UNVERIFIED_EMAIL, "").equals(edEmail.getText().toString())) {
                    Intent intent = new Intent(mContext, EditVerifyEmail.class);
                    finish();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } else if (requestCode == RESULTCODE_COUNTRY) {
                txtCode.setText(data.getStringExtra("Country_code"));
            } else if (requestCode == LOCATION) {

                mLat = Double.parseDouble(data.getStringExtra("latitude"));
                mLong = Double.parseDouble(data.getStringExtra("longitude"));
                getAddress(mLat, mLong);

            }
        }
    }

    void getAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getSubLocality() + ", " + addresses.get(0).getSubAdminArea();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            String s = addresses.get(0).getSubAdminArea() + ", " + state + ", " + country + ".";

            edLocation.setText(s.replace("null,", ""));
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


    private void disableViews() {
        edFirstName.setEnabled(false);
        edLastName.setEnabled(false);
        imgProfile.setEnabled(false);
    }

    private void enableViews() {
        edFirstName.setEnabled(true);
        edLastName.setEnabled(true);
        imgProfile.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (!isLoading)
            super.onBackPressed();
    }

    void hitResendApi() {
        showProgress();
        Call<DemoModel> call = RetrofitClient.getInstance().resendEditEmail(utils.getString("access_token", ""), utils.getString(Constants.UNVERIFIED_EMAIL, ""));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                Intent intent = new Intent(mContext, EditVerifyEmail.class);
                finish();
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(edFirstName, t.getLocalizedMessage());
            }
        });
    }

}
