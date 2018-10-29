package com.app.tul;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import model.CreateStripeAccountModel;
import model.ViewTulModel;
import utils.Constants;

public class TulAccountDetailActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.ll_inside)
    LinearLayout llInside;

    @BindView(R.id.ll_country_code)
    LinearLayout llCountryCode;
    @BindView(R.id.txt_currency_hint)
    TextView txtCurrencyHint;
    @BindView(R.id.txt_currency_value)
    TextView txtCurrencyValue;

    @BindView(R.id.ll_currency)
    LinearLayout llCurrency;
    @BindView(R.id.txt_country_code_hint)
    TextView txtCountryCodeHint;
    @BindView(R.id.txt_country_code_value)
    TextView txtCountryCodeValue;

    @BindView(R.id.ll_account_no)
    LinearLayout llAccountNo;
    @BindView(R.id.txt_account_no_hint)
    TextView txtAccountNoHint;
    @BindView(R.id.txt_account_no_value)
    TextView txtAccountNoValue;

    @BindView(R.id.ll_sort_code)
    LinearLayout llSortCode;
    @BindView(R.id.txt_sort_code_hint)
    TextView txtSortCodeHint;
    @BindView(R.id.txt_sort_code_value)
    TextView txtSortCodeValue;

    @BindView(R.id.ll_first_name)
    LinearLayout llFirstName;
    @BindView(R.id.txt_first_name_hint)
    TextView txtFirstNameHint;
    @BindView(R.id.txt_first_name_value)
    TextView txtFirstNameValue;

    @BindView(R.id.ll_last_name)
    LinearLayout llLastName;
    @BindView(R.id.txt_last_name_hint)
    TextView txtLastNameHint;
    @BindView(R.id.txt_last_name_value)
    TextView txtLastNameValue;

    @BindView(R.id.txt_address_hint)
    TextView txtAddressHint;
    @BindView(R.id.txt_address_value)
    TextView txtAddressValue;

    @BindView(R.id.ll_city)
    LinearLayout llCity;
    @BindView(R.id.txt_city_hint)
    TextView txtCityHint;
    @BindView(R.id.txt_city_value)
    TextView txtCityValue;

    @BindView(R.id.ll_state)
    LinearLayout llState;
    @BindView(R.id.txt_state_hint)
    TextView txtStateHint;
    @BindView(R.id.txt_state_value)
    TextView txtStateValue;

    @BindView(R.id.ll_postal_code)
    LinearLayout llPostelCode;
    @BindView(R.id.txt_postal_code_hint)
    TextView txtPostalCodeHint;
    @BindView(R.id.txt_postal_code_value)
    TextView txtPostalCodeValue;

    @BindView(R.id.ll_dob)
    LinearLayout llDob;
    @BindView(R.id.txt_dob_hint)
    TextView txtDobHint;
    @BindView(R.id.txt_dob_value)
    TextView txtDobValue;

    @BindView(R.id.ll_address)
    LinearLayout llAddress;

    @BindView(R.id.ll_city_state)
    LinearLayout ll_city_state;

    @BindView(R.id.ll_postal_code_dob)
    LinearLayout ll_postal_code_dob;

    @BindView(R.id.txt_swift)
    TextView txtSwift;

    @BindView(R.id.ll_swift)
    LinearLayout llSwift;

    @BindView(R.id.txt_swift_hint)
    TextView txtSwiftHint;

    ViewTulModel.ResponseBean.BankDetailBean bankModel;
    CreateStripeAccountModel.ResponseBean accountModel;


    @Override
    protected int getContentView() {
        return R.layout.activity_tul_account_detail;
    }

    @Override
    protected void initUI() {

        llInside.setPadding(mWidth / 24, mWidth / 24, mWidth / 24, 0);

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.linked_account_);

        txtCountryCodeHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtCountryCodeHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtCountryCodeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtCountryCodeValue.setPadding(0, 0, 0, mWidth / 28);

        txtCurrencyHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtCurrencyHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtCurrencyValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtCurrencyValue.setPadding(0, 0, 0, mWidth / 28);

        txtAccountNoHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtAccountNoHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtAccountNoValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtAccountNoValue.setPadding(0, 0, 0, mWidth / 28);

        txtSortCodeHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtSortCodeHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtSortCodeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtSortCodeValue.setPadding(0, 0, 0, mWidth / 28);

        txtFirstNameHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtFirstNameHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtFirstNameValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtFirstNameValue.setPadding(0, 0, 0, mWidth / 28);

        txtLastNameHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtLastNameHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtLastNameValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtLastNameValue.setPadding(0, 0, 0, mWidth / 28);

        txtAddressHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtAddressHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtAddressValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtAddressValue.setPadding(0, 0, 0, mWidth / 28);

        txtCityHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtCityHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtCityValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtCityValue.setPadding(0, 0, 0, mWidth / 28);

        txtStateHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtStateHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtStateValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtStateValue.setPadding(0, 0, 0, mWidth / 28);

        txtSwiftHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtSwiftHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtSwift.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtSwift.setPadding(0, 0, 0, mWidth / 28);

        txtPostalCodeHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtPostalCodeHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtPostalCodeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtPostalCodeValue.setPadding(0, 0, 0, mWidth / 28);

        txtDobHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtDobHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtDobValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtDobValue.setPadding(0, 0, 0, mWidth / 28);

        if (getIntent().hasExtra("data"))
            setData();
        else
            setAccountData();
    }

    private void setAccountData() {
        accountModel = getIntent().getParcelableExtra("data_account");

        txtCountryCodeValue.setText(accountModel.getCountry_code());

        txtCurrencyValue.setText(accountModel.getCurrency());


        txtAccountNoValue.setText(accountModel.getAccount_number());
        txtSortCodeValue.setText(accountModel.getSort_code());
        txtFirstNameValue.setText(accountModel.getFirst_name());
        txtLastNameValue.setText(accountModel.getLast_name());
        txtAddressValue.setText(accountModel.getAddress());
        txtCityValue.setText(accountModel.getCity());
        txtStateValue.setText(accountModel.getState());
        txtPostalCodeValue.setText(accountModel.getPostal_code());
        txtDobValue.setText(accountModel.getDob());


        if (!android.text.TextUtils.isEmpty(accountModel.getSwift())) {
            llSwift.setVisibility(View.VISIBLE);
            txtSwift.setText(accountModel.getSwift());
        }
        if (accountModel.getAccount_type().equals("1")) {
            visibleSwiftView(true);
        } else {
            visibleSwiftView(false);
        }


    }

    private void setData() {
        bankModel = getIntent().getParcelableExtra("data");
        txtCountryCodeValue.setText(bankModel.getCountry_code());
        txtCurrencyValue.setText(bankModel.getCurrency());
        txtAccountNoValue.setText(bankModel.getAccount_number());
        txtSortCodeValue.setText(bankModel.getSort_code());
        txtFirstNameValue.setText(bankModel.getFirst_name());
        txtLastNameValue.setText(bankModel.getLast_name());
        txtAddressValue.setText(bankModel.getAddress());
        txtCityValue.setText(bankModel.getCity());
        txtStateValue.setText(bankModel.getState());
        txtPostalCodeValue.setText(bankModel.getPostal_code());
        txtDobValue.setText(bankModel.getDob());
        if (!android.text.TextUtils.isEmpty(bankModel.getSwift())) {
            llSwift.setVisibility(View.VISIBLE);
            txtSwift.setText(bankModel.getSwift());
        }

        if (bankModel.getAccount_type().equals("1")) {
            visibleSwiftView(true);
        } else {
            visibleSwiftView(false);
        }
    }

    @Override
    protected void onCreateStuff() {
        if (utils.getString(Constants.IS_CURRENCY_SELECTED, "0").equals("1")) {
            txtCurrencyValue.setText(utils.getString(Constants.PRIMARY_CURRENCY, ""));

            if (utils.getString(Constants.PRIMARY_CURRENCY, "").equals("GBP")) {
                txtAccountNoHint.setText(R.string.account_no_);
                txtSwiftHint.setText(R.string.sort_code_);
            } else {
                txtAccountNoHint.setText(R.string.iban_);
                txtSwiftHint.setText(getString(R.string.swift));
            }
        }
    }

    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TulAccountDetailActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    void visibleSwiftView(boolean visible) {
        if (visible) {
            ll_city_state.setVisibility(View.VISIBLE);
            llAddress.setVisibility(View.VISIBLE);
            llSortCode.setVisibility(View.VISIBLE);
            ll_postal_code_dob.setVisibility(View.VISIBLE);
        } else {
            ll_city_state.setVisibility(View.GONE);
            llAddress.setVisibility(View.GONE);
            llSortCode.setVisibility(View.GONE);
            ll_postal_code_dob.setVisibility(View.GONE);
        }


    }

}
