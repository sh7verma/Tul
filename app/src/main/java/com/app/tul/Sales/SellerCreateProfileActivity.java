package com.app.tul.Sales;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
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

import com.app.tul.BaseActivity;
import com.app.tul.CountryCodeActivity;
import com.app.tul.LocationSearchActivity;
import com.app.tul.OptionPhotoSelection;
import com.app.tul.R;
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
import model.CreateStripeAccountModel;
import model.SalesBankDetailModel;
import model.SignupModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class SellerCreateProfileActivity extends BaseActivity {

    private static final int REGGUEST = 3;
    private static final int LOCATION = 1;
    private static final int ACCOUNT = 5;

    final int RESULTCODE_COUNTRY = 4;
    final int PIC = 2;

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

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
    MaterialEditText edFirstName;

    @BindView(R.id.ed_last_name)
    MaterialEditText edLastName;

    @BindView(R.id.ll_last_name)
    LinearLayout llLastName;

    @BindView(R.id.ed_phone_no)
    EditText edPhoneNo;
    @BindView(R.id.btn_verify)
    LoadingButton btnVerify;
    @BindView(R.id.ed_vat)
    MaterialEditText edVat;
    @BindView(R.id.ll_vat)
    LinearLayout llVat;

    @BindView(R.id.ed_location)
    MaterialEditText edLocation;
    @BindView(R.id.ed_account)
    MaterialEditText edAccount;


    boolean isLoading;
    SalesBankDetailModel mSalesBankDetailModel;
    Double mLat, mLong;
    ProgressDialog progDailog;
    private File mFile;
    private String mPath;
    private int isCompany = 0;
    private boolean accountAlready = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_seller_create_profile;
    }

    @Override
    protected void initUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.create_profile);

        edFirstName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edFirstName.setTypeface(typeface);

        edLocation.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edLocation.setTypeface(typeface);

        edAccount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edAccount.setTypeface(typeface);

        edVat.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edVat.setTypeface(typeface);

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
        mSalesBankDetailModel = SalesBankDetailModel.getInstance();

        if (getIntent().getIntExtra(Constants.SALES_MODE, 0) == Constants.SALES_MODE_COMPANY) {
            edFirstName.setHint(R.string.company_name);
            edFirstName.setFloatingLabelText(getString(R.string.company_name));

            edLocation.setHint(R.string.warehouse_location);
            edLocation.setFloatingLabelText(getString(R.string.warehouse_location));

            llVat.setVisibility(View.VISIBLE);
            llLastName.setVisibility(View.GONE);
            isCompany = 1;
            edLastName.setText("");
            edVat.setText("");
            edFirstName.setText("");
        }

        getPrimaryDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard(this);
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
        edLocation.setOnClickListener(this);
        edAccount.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                mSalesBankDetailModel.clearData();
                finish();
                break;

            case R.id.ll_code:
                Intent inSelectCountry = new Intent(this, CountryCodeActivity.class);
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
            case R.id.ed_location:
                Intent addressIntent = new Intent(mContext, LocationSearchActivity.class);
                startActivityForResult(addressIntent, LOCATION);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.ed_account:
                Intent intent = new Intent(mContext, SellerBankDetailActivity.class);
                if (accountAlready) {
                    intent.putExtra("accountAlready", "accountAlready");
                }
                startActivityForResult(intent, ACCOUNT);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

    private void verifyDetails() {
        mSalesBankDetailModel = SalesBankDetailModel.getInstance();
        if (mFile == null && TextUtils.isEmpty(mPath)) {
            showAlert(btnVerify, getString(R.string.error_pic));
        } else if (edFirstName.getText().toString().length() < 2) {
            if (getIntent().getIntExtra(Constants.SALES_MODE, 0) == Constants.SALES_MODE_COMPANY) {
                showAlert(btnVerify, getString(R.string.error_company_name));
            } else {
                showAlert(btnVerify, getString(R.string.error_first_name));
            }
        } else if (getIntent().getIntExtra(Constants.SALES_MODE, 0) == Constants.SALES_MODE_INDIVIDUAL
                && edLastName.getText().toString().length() < 2) {
            showAlert(btnVerify, getString(R.string.error_last_name));
        } else if ((txtCode.getText().toString().trim()).isEmpty()) {
            showAlert(btnVerify, getString(R.string.error_code));
        } else if ((edPhoneNo.getText().toString().trim()).isEmpty()) {
            showAlert(btnVerify, getString(R.string.enter_phone));
        } else if ((edLocation.getText().toString().trim()).isEmpty()) {
            showAlert(btnVerify, getString(R.string.enter_loc));
        } else if (getIntent().getIntExtra(Constants.SALES_MODE, 0) == Constants.SALES_MODE_COMPANY
                && (edVat.getText().toString().trim()).isEmpty()) {
            showAlert(btnVerify, getString(R.string.enter_vat));
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
            } else {
                showInternetAlert(btnVerify);
            }
        }
    }


    boolean validatePhone() {
        Pattern pattern = Pattern.compile("[0-9+]{5,20}");
        Matcher matcher = pattern.matcher(edPhoneNo.getText());
        return matcher.matches();
    }

    private void disableViews() {
        edFirstName.setEnabled(false);
        edPhoneNo.setEnabled(false);
        imgProfile.setEnabled(false);
        edVat.setEnabled(false);
        edLastName.setEnabled(false);
        edLocation.setEnabled(false);
        edAccount.setEnabled(false);
        txtCode.setEnabled(false);
    }

    private void enableViews() {
        edFirstName.setEnabled(true);
        imgProfile.setEnabled(true);
        edPhoneNo.setEnabled(true);
        edVat.setEnabled(true);
        edLastName.setEnabled(true);
        edLocation.setEnabled(true);
        edAccount.setEnabled(true);
        txtCode.setEnabled(true);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            hideKeyboard(this);

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

            } else if (requestCode == LOCATION) {

                mLat = Double.parseDouble(data.getStringExtra("latitude"));
                mLong = Double.parseDouble(data.getStringExtra("longitude"));
                getAddress(mLat, mLong);

            } else if (requestCode == ACCOUNT) {

                edAccount.setText(data.getStringExtra("account_name"));

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

    void hitAPI() {

        final RequestBody reqFile;
        MultipartBody.Part profile_pic;

        final RequestBody reqFileBank;
        MultipartBody.Part document;


        if (mFile != null) {
            reqFile = RequestBody.create(MediaType.parse("image/*"), mFile);
            profile_pic = MultipartBody.Part.createFormData("profile_pic", mFile.getName(), reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("image/*"), "");
            profile_pic = MultipartBody.Part.createFormData("profile_pic", "", reqFile);
        }

        if (mSalesBankDetailModel.documnetFile != null) {
            reqFileBank = RequestBody.create(MediaType.parse("image/*"), mSalesBankDetailModel.documnetFile);
            document = MultipartBody.Part.createFormData("document", mSalesBankDetailModel.documnetFile.getName(), reqFileBank);
        } else {
            reqFileBank = RequestBody.create(MediaType.parse("image/*"), "");
            document = MultipartBody.Part.createFormData("document", "", reqFileBank);
        }

        RequestBody access_token = RequestBody.create(MediaType.parse("text/plain"), utils.getString("access_token", ""));
        RequestBody pin_code = RequestBody.create(MediaType.parse("text/plain"), txtCode.getText().toString());
        RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"), edPhoneNo.getText().toString().trim());
        RequestBody user_first_name = RequestBody.create(MediaType.parse("text/plain"), edFirstName.getText().toString().trim());
        RequestBody user_last_name = RequestBody.create(MediaType.parse("text/plain"), edLastName.getText().toString().trim());

        RequestBody country_code;

        if (mSalesBankDetailModel.countryCode != null) {
            String[] s = mSalesBankDetailModel.countryCode.split("-");
            country_code = RequestBody.create(MediaType.parse("text/plain"), s[0].trim());
        } else {
            country_code = RequestBody.create(MediaType.parse("text/plain"), "");
        }

        RequestBody currency = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.currency.trim());

        RequestBody account_number;
        if (mSalesBankDetailModel.accountNo != null) {
            account_number = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.accountNo.trim());
        } else {
            account_number = RequestBody.create(MediaType.parse("text/plain"), "");
        }

        RequestBody sort_code = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.sortCode.trim());
        RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.firstName.trim());
        RequestBody last_name = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.lastName.trim());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.address.trim());
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.city.trim());
        RequestBody postal_code = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.postalCode.trim());
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.state.trim());
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.dob.trim());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), edLocation.getText().toString().trim());
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mLat).trim());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mLong).trim());

        RequestBody is_company = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(isCompany));

        RequestBody swift = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.swift);
        RequestBody is_seller = RequestBody.create(MediaType.parse("text/plain"), "1");

        RequestBody account_type = RequestBody.create(MediaType.parse("text/plain"), mSalesBankDetailModel.account_type);

        RequestBody is_account_added;
        if (accountAlready) {
            is_account_added = RequestBody.create(MediaType.parse("text/plain"), "0");
        } else {
            is_account_added = RequestBody.create(MediaType.parse("text/plain"), "1");
        }

        if (TextUtils.isEmpty(mSalesBankDetailModel.accountNo)) {
            is_account_added = RequestBody.create(MediaType.parse("text/plain"), "0");
        }

        RequestBody Is_number_updated;
        if (utils.getString("phone_number", "").equals(edPhoneNo.getText().toString())) {
            Is_number_updated = RequestBody.create(MediaType.parse("text/plain"), "0");
        } else {
            Is_number_updated = RequestBody.create(MediaType.parse("text/plain"), "1");
        }
        RequestBody vat = RequestBody.create(MediaType.parse("text/plain"), edVat.getText().toString().trim());


        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), utils.getString("email", ""));

        RequestBody email_changed = RequestBody.create(MediaType.parse("text/plain"), "0");


        Call<SignupModel> call = RetrofitClient.getInstance().createSellerProfile(user_first_name, user_last_name,
                pin_code, phone_number, country_code, currency, account_number, sort_code, first_name,
                last_name, address, city, postal_code, state, dob, location, latitude,
                longitude, access_token, is_company, swift, is_account_added, Is_number_updated, is_seller, vat, account_type, email_changed, email, profile_pic, document);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                //
                if (response.body().getResponse() != null) {
                    isLoading = false;
                    btnVerify.cancelLoading();

                    utils.setString("access_token", response.body().getResponse().getAccess_token());
                    utils.setString("email", response.body().getResponse().getEmail());
//                    utils.setInt("status", response.body().getResponse().getStatus());
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
                        verifyIntent.putExtra("code", txtCode.getText().toString());
                        verifyIntent.putExtra("phone", edPhoneNo.getText().toString().trim());
                        startActivity(verifyIntent);
                    } else {
                        Intent inStarted = new Intent(mContext, SalesListYourTulActivity.class);
//                        startActivity(inStarted);
//                        Intent inStarted = new Intent(mContext, LandingActivity.class);
                        inStarted.putExtra("Create", "Create");
                        inStarted.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(inStarted);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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
                btnVerify.cancelLoading();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSalesBankDetailModel.clearData();
        finish();
    }

    private void getPrimaryDialog() {
        progDailog = ProgressDialog.show(this, getString(R.string.please_wait_dot), getString(R.string.fetching_your_primary_account), true);
        progDailog.setCancelable(false);
        Call<CreateStripeAccountModel> call = RetrofitClient.getInstance()
                .getPrimaryAccount(utils.getString("access_token", ""));
        call.enqueue(new Callback<CreateStripeAccountModel>() {
            @Override
            public void onResponse(Call<CreateStripeAccountModel> call, Response<CreateStripeAccountModel> response) {

                if (response.body().getResponse() != null) {
                    if (response.body().getResponse().getAccountId() == 0) {
                        Log.e("No Account = ", "Yes");
                        utils.setString("visible_primary", "no");
                        accountAlready = false;

                    } else {
                        accountAlready = true;

                        Log.e("No Account = ", "NO");
                        utils.setString("accountId", response.body().getResponse().getAccount());
                        mSalesBankDetailModel.countryCode = response.body().getResponse().getCountry_code();
                        mSalesBankDetailModel.currency = response.body().getResponse().getCurrency();
                        mSalesBankDetailModel.accountNo = response.body().getResponse().getAccount_number();
                        mSalesBankDetailModel.sortCode = response.body().getResponse().getSort_code();
                        mSalesBankDetailModel.firstName = response.body().getResponse().getFirst_name();
                        mSalesBankDetailModel.lastName = response.body().getResponse().getLast_name();
                        mSalesBankDetailModel.address = response.body().getResponse().getAddress();
                        mSalesBankDetailModel.city = response.body().getResponse().getCity();
                        mSalesBankDetailModel.state = response.body().getResponse().getState();
                        mSalesBankDetailModel.postalCode = response.body().getResponse().getPostal_code();
                        mSalesBankDetailModel.dob = response.body().getResponse().getDob();

                        mSalesBankDetailModel.account_type = response.body().getResponse().getAccount_type();

                        if (response.body().getResponse().getSwift() == null) {
                            mSalesBankDetailModel.swift = "";
                        } else {
                            mSalesBankDetailModel.swift = response.body().getResponse().getSwift();
                        }
                        edAccount.setText(mSalesBankDetailModel.firstName + " "
                                + mSalesBankDetailModel.lastName);

                        SalesBankDetailModel.setSalesBankDetailModel(mSalesBankDetailModel);

                    }

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(txtToolbarTitle, response.body().error.getMessage());
                    }
                }
                progDailog.dismiss();
            }

            @Override
            public void onFailure(Call<CreateStripeAccountModel> call, Throwable t) {
                showAlert(txtToolbarTitle, t.getLocalizedMessage());
                progDailog.dismiss();
            }
        });
    }


    //
//    isLoading = false;
//                btnVerify.loadingFailed();
//    enableViews();
//    showAlert(btnVerify, t.getLocalizedMessage());
}
