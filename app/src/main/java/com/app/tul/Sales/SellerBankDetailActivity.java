package com.app.tul.Sales;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.BaseActivity;
import com.app.tul.OptionPhotoSelection;
import com.app.tul.OptionSelection;
import com.app.tul.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import customviews.MaterialEditText;
import model.SalesBankDetailModel;
import utils.Constants;

public class SellerBankDetailActivity extends BaseActivity {


    private static final int COUNTRY = 1;
    private static final int CURENCY = 2;
    private static final int DOCUMENT = 3;
    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.txt_bank_detail)
    TextView txtBankDetail;

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

    @BindView(R.id.ed_account_no)
    MaterialEditText edAccountNo;
    @BindView(R.id.ed_sort_code)
    MaterialEditText edSortCode;

    @BindView(R.id.ed_first_name)
    MaterialEditText edFirstName;
    @BindView(R.id.ed_last_name)
    MaterialEditText edLastName;

    @BindView(R.id.ed_address)
    MaterialEditText edAddress;

    @BindView(R.id.ed_city)
    MaterialEditText edCity;
    @BindView(R.id.ed_state)
    MaterialEditText edState;

    @BindView(R.id.ed_postal_code)
    MaterialEditText edPostalCode;
    @BindView(R.id.ll_dob)
    LinearLayout llDob;
    @BindView(R.id.txt_dob_hint)
    TextView txtDobHint;
    @BindView(R.id.txt_dob_value)
    TextView txtDobValue;

    @BindView(R.id.ed_swift)
    MaterialEditText edSwift;

    @BindView(R.id.txt_add_documnet)
    TextView txtAddDocumnet;
    @BindView(R.id.img_add)
    ImageView imgAdd;

    @BindView(R.id.ll_city)
    LinearLayout ll_city;
    @BindView(R.id.ll_postal_code)
    LinearLayout ll_postal_code;
    @BindView(R.id.view_address)
    View view_address;
    @BindView(R.id.view_sort)
    View view_sort;


    @BindView(R.id.ll_stripe_logo)
    LinearLayout ll_stripe_logo;

    @BindView(R.id.txt_save)
    TextView txtSave;
    @BindView(R.id.txt_discard)
    TextView txtDiscard;
    Calendar dateSelected = Calendar.getInstance(TimeZone.getDefault());
    Calendar defaultSelected = Calendar.getInstance();
    SimpleDateFormat mShowDob = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    SalesBankDetailModel mSalesBankDetailModel;

    boolean isStripe = false;

    private File mFile;
    private String mPath;

    @Override
    protected int getContentView() {
        return R.layout.activity_seller_bank_detail;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        llInside.setPadding(mWidth / 24, mWidth / 24, mWidth / 24, 0);

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.account_details);

        txtBankDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        txtBankDetail.setPadding(mWidth / 24, mWidth / 32, mWidth / 24, mHeight / 24);

        txtCountryCodeHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtCountryCodeHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtCountryCodeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtCountryCodeValue.setPadding(0, 0, 0, mWidth / 28);

        txtCurrencyHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtCurrencyHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtCurrencyValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtCurrencyValue.setPadding(0, 0, 0, mWidth / 28);

        edAccountNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edAccountNo.setTypeface(typeface);

        edSortCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edSortCode.setTypeface(typeface);

        edFirstName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edFirstName.setTypeface(typeface);

        edLastName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edLastName.setTypeface(typeface);

        edAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edAddress.setTypeface(typeface);

        edSwift.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edSwift.setTypeface(typeface);

        edCity.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edCity.setTypeface(typeface);

        edState.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edState.setTypeface(typeface);

        edPostalCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edPostalCode.setTypeface(typeface);

        txtDobHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtDobHint.setPadding(0, mWidth / 28, 0, mWidth / 64);

        txtDobValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
//        txtDobValue.setPadding(0, 0, 0, mWidth / 28);

        txtAddDocumnet.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtAddDocumnet.setPadding(0, mWidth / 28, 0, mWidth / 28);

        LinearLayout.LayoutParams saveParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        saveParms.setMargins(0, mHeight / 32, 0, 0);
        txtSave.setLayoutParams(saveParms);
        txtSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtSave.setPadding(0, mWidth / 28, 0, mWidth / 28);

        txtDiscard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtDiscard.setPadding(0, mWidth / 28, 0, mWidth / 28);

        LinearLayout.LayoutParams imgParms = new LinearLayout.LayoutParams(mWidth / 6, mWidth / 6);
        imgAdd.setLayoutParams(imgParms);

    }

    @Override
    protected void onCreateStuff() {

        visibleStripeView(false);

        mSalesBankDetailModel = SalesBankDetailModel.getInstance();
        defaultSelected.add(Calendar.YEAR, -13);
        setData();
    }

    @Override
    protected void initListener() {
        if (getIntent().hasExtra("accountAlready")) {
            edAccountNo.setFocusableInTouchMode(false);
            edAddress.setFocusableInTouchMode(false);
            edCity.setFocusableInTouchMode(false);
            edFirstName.setFocusableInTouchMode(false);
            edLastName.setFocusableInTouchMode(false);
            edPostalCode.setFocusableInTouchMode(false);
            edSortCode.setFocusableInTouchMode(false);
            edState.setFocusableInTouchMode(false);
            edSwift.setFocusableInTouchMode(false);

            txtDiscard.setVisibility(View.GONE);
            txtAddDocumnet.setVisibility(View.GONE);
            imgAdd.setVisibility(View.GONE);

        } else {
            llCountryCode.setOnClickListener(this);
            llDob.setOnClickListener(this);
            imgAdd.setOnClickListener(this);
            txtDiscard.setOnClickListener(this);
        }
        imgBckImg.setOnClickListener(this);
        txtSave.setOnClickListener(this);

    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
            case R.id.txt_discard:
                alertDiscardDialog();
                break;
//            case R.id.ll_currency:
//                selectOptions(getCurrency(), CURENCY, txtCurrencyValue.getText().toString());
//                break;
            case R.id.ll_country_code:
//                selectOptions(getCountry(), COUNTRY, txtCountryCodeValue.getText().toString());
                if (utils.getString(Constants.IS_CURRENCY_SELECTED, "0").equals("1")) {
                    if (TextUtils.isEmpty(txtCurrencyValue.getText())) {
                        selectOptions(getCountry(), COUNTRY, txtCountryCodeValue.getText().toString());
                    } else if (txtCurrencyValue.getText().equals("EUR")) {
                        selectOptions(getEurCountry(), COUNTRY, txtCountryCodeValue.getText().toString());
                    } else if (txtCurrencyValue.getText().equals("GBP")) {
                        selectOptions(getGbCountry(), COUNTRY, txtCountryCodeValue.getText().toString());
                    }
                } else {
                    selectOptions(getCountry(), COUNTRY, txtCountryCodeValue.getText().toString());
                }

                break;
            case R.id.img_add:
                Intent inProfile = new Intent(mContext, OptionPhotoSelection.class);
                if (mPath != null) {
                    inProfile.putExtra("visible", "yes");
                    inProfile.putExtra("path", mPath);
                }
                startActivityForResult(inProfile, DOCUMENT);
                break;
            case R.id.ll_dob:
                Constants.closeKeyboard(mContext, llInside);
                previousSelected();
                break;
            case R.id.txt_save:
                if (TextUtils.isEmpty(txtCountryCodeValue.getText().toString().trim()))
                    showAlert(llInside, getString(R.string.error_country_code));
                else if (TextUtils.isEmpty(txtCurrencyValue.getText().toString().trim()))
                    showAlert(llInside, getString(R.string.error_currency));
                else if (TextUtils.isEmpty(edAccountNo.getText().toString().trim()))
                    showAlert(llInside, getString(R.string.error_account));
                else if (TextUtils.isEmpty(edSortCode.getText().toString().trim()) && isStripe)
                    showAlert(llInside, getString(R.string.error_sort_code));
                else if (TextUtils.isEmpty(edFirstName.getText().toString().trim()))
                    showAlert(llInside, getString(R.string.error_first_name));
                else if (TextUtils.isEmpty(edLastName.getText().toString().trim()))
                    showAlert(llInside, getString(R.string.error_last_name));
                else if (TextUtils.isEmpty(edAddress.getText().toString().trim()) && isStripe)
                    showAlert(llInside, getString(R.string.error_address));
                else if (TextUtils.isEmpty(edCity.getText().toString().trim()) && isStripe)
                    showAlert(llInside, getString(R.string.error_city));
                else if (TextUtils.isEmpty(edState.getText().toString().trim()) && isStripe)
                    showAlert(llInside, getString(R.string.error_state));
                else if (TextUtils.isEmpty(edPostalCode.getText().toString().trim()) && isStripe)
                    showAlert(llInside, getString(R.string.error_postal_code));
                else if (TextUtils.isEmpty(txtDobValue.getText().toString().trim()) && isStripe)
                    showAlert(llInside, getString(R.string.error_dob));
                else if (TextUtils.isEmpty(mPath) && !getIntent().hasExtra("accountAlready") && isStripe)
                    showAlert(llInside, getString(R.string.error_document));
                else {
                    mSalesBankDetailModel.countryCode = txtCountryCodeValue.getText().toString();
                    mSalesBankDetailModel.currency = txtCurrencyValue.getText().toString();
                    mSalesBankDetailModel.accountNo = edAccountNo.getText().toString();
                    mSalesBankDetailModel.sortCode = edSortCode.getText().toString();
                    mSalesBankDetailModel.firstName = edFirstName.getText().toString();
                    mSalesBankDetailModel.lastName = edLastName.getText().toString();
                    mSalesBankDetailModel.address = edAddress.getText().toString();
                    mSalesBankDetailModel.city = edCity.getText().toString();
                    mSalesBankDetailModel.state = edState.getText().toString();
                    mSalesBankDetailModel.postalCode = edPostalCode.getText().toString();
                    mSalesBankDetailModel.dob = txtDobValue.getText().toString();
                    mSalesBankDetailModel.swift = edSwift.getText().toString();
                    mSalesBankDetailModel.documentPath = mPath;
                    mSalesBankDetailModel.documnetFile = mFile;

                    if (isStripe) {
                        mSalesBankDetailModel.account_type = "1";
                    } else {
                        mSalesBankDetailModel.account_type = "0";
                    }

                    SalesBankDetailModel.setSalesBankDetailModel(mSalesBankDetailModel);

                    if (connectedToInternet()) {
                        Intent in = new Intent();
                        in.putExtra("account_name", edFirstName.getText().toString().trim() + " "
                                + edLastName.getText().toString().trim());
                        setResult(RESULT_OK, in);
                        finish();
                    }
                }
                break;
        }
    }

    private void setData() {

        if (utils.getString(Constants.IS_CURRENCY_SELECTED, "0").equals("1")) {
            txtCurrencyValue.setText(utils.getString(Constants.PRIMARY_CURRENCY, ""));

            if (utils.getString(Constants.PRIMARY_CURRENCY, "").equals("GBP")) {
                edAccountNo.setHint("ACCOUNT NO.");
                edAccountNo.setFloatingLabelText("ACCOUNT NO.");

                edSwift.setHint("SORT CODE");
                edSwift.setFloatingLabelText("SORT CODE");
            } else {
                edAccountNo.setHint("IBAN");
                edAccountNo.setFloatingLabelText("IBAN");

                edSwift.setHint(getString(R.string.swift));
                edSwift.setFloatingLabelText(getString(R.string.swift));
            }
        }

        if (mSalesBankDetailModel.accountNo != null) {
            txtCountryCodeValue.setText(mSalesBankDetailModel.countryCode);

            txtCurrencyValue.setText(mSalesBankDetailModel.currency);

            edAccountNo.setText(mSalesBankDetailModel.accountNo);
            edAccountNo.setSelection(edAccountNo.getText().toString().trim().length());

            edSortCode.setText(mSalesBankDetailModel.sortCode);
            edSortCode.setSelection(edSortCode.getText().toString().trim().length());

            edFirstName.setText(mSalesBankDetailModel.firstName);
            edFirstName.setSelection(edFirstName.getText().toString().trim().length());

            edLastName.setText(mSalesBankDetailModel.lastName);
            edLastName.setSelection(edLastName.getText().toString().trim().length());

            edAddress.setText(mSalesBankDetailModel.address);
            edAddress.setSelection(edAddress.getText().toString().trim().length());

            edSwift.setText(mSalesBankDetailModel.swift);
            edSwift.setSelection(edSwift.getText().toString().trim().length());

            edCity.setText(mSalesBankDetailModel.city);
            edCity.setSelection(edCity.getText().toString().trim().length());

            edState.setText(mSalesBankDetailModel.state);
            edState.setSelection(edState.getText().toString().trim().length());

            edPostalCode.setText(mSalesBankDetailModel.postalCode);
            edPostalCode.setSelection(edPostalCode.getText().toString().trim().length());

            txtDobValue.setText(mSalesBankDetailModel.dob);
            mPath = mSalesBankDetailModel.documentPath;
            mFile = mSalesBankDetailModel.documnetFile;
            if (mFile != null) {
                Picasso.with(mContext).load(mFile).resize(mWidth / 6, mWidth / 6)
                        .into(imgAdd);
            }

            if (mSalesBankDetailModel.account_type.equals("1")) {
                isStripe = true;
            } else {
                isStripe = false;
            }
        }

        visibleStripeView(isStripe);

    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    void selectOptions(ArrayList<String> mData, int intentType, String selected) {
        Intent in = new Intent(mContext, OptionSelection.class);
        in.putExtra("selected", selected);
        in.putStringArrayListExtra("data", mData);
        startActivityForResult(in, intentType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
//                case CURENCY:
//                    txtCurrencyValue.setText(data.getStringExtra("selected_data"));
//                    break;
                case COUNTRY:
                    txtCountryCodeValue.setText(data.getStringExtra("selected_data"));

                    if (getStripeCountry().contains(txtCountryCodeValue.getText().toString())) {
                        visibleStripeView(isStripe);
                    } else {
                        visibleStripeView(isStripe);
                    }

                    if (txtCountryCodeValue.getText().equals("GB- UNITED KINGDOM")) {
                        txtCurrencyValue.setText("GBP");
                    } else {
                        txtCurrencyValue.setText("EUR");
                    }

                    if (data.getStringExtra("selected_data").equals("GB- UNITED KINGDOM")) {
                        edAccountNo.setHint("ACCOUNT NO.");
                        edAccountNo.setFloatingLabelText("ACCOUNT NO.");

                        edSwift.setHint("SORT CODE");
                        edSwift.setFloatingLabelText("SORT CODE");
                    } else {
                        edAccountNo.setHint("IBAN");
                        edAccountNo.setFloatingLabelText("IBAN");

                        edSwift.setHint(getString(R.string.swift));
                        edSwift.setFloatingLabelText(getString(R.string.swift));
                    }

                    break;
                case DOCUMENT:
                    Log.e("Path = ", data.getStringExtra("filePath"));
                    mPath = data.getStringExtra("filePath");
                    mFile = new File(data.getStringExtra("filePath"));
                    Picasso.with(mContext).load(mFile).resize(mWidth / 6, mWidth / 6)
                            .into(imgAdd);
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private ArrayList<String> getCountry() {

        ArrayList<String> country = new ArrayList<>();
//        country.add("PT");
//        country.add("AT");
//        country.add("BE");
//        country.add("BG");
//        country.add("SE");
//        country.add("SI");
//        country.add("DE");
//        country.add("SK");
//        country.add("DK");
//        country.add("GB");
//        country.add("EE");
//        country.add("ES");
//        country.add("FI");
//        country.add("FR");
//        country.add("GR");
//        country.add("HR");
//        country.add("CY");
//        country.add("CZ");
//        country.add("HU");
//        country.add("IE");
//        country.add("IT");
//        country.add("LT");
//        country.add("LU");
//        country.add("LV");
//        country.add("MT");
//        country.add("NL");
//        country.add("PL");
//        country.add("RO");
        country.add("PT- PORTUGAL");
        country.add("AT- AUSTRIA");
        country.add("BE- BELGIUM");
        country.add("BG- BULGARIA");
        country.add("SE- SWEDEN");
        country.add("SI-SLOVENIA");
        country.add("DE-GERMANY");
        country.add("SK-SLOVAKIA");
        country.add("DK-DENMARK");
        country.add("GB- UNITED KINGDOM");
        country.add("EE-ESTONIA");
        country.add("ES-SPAIN");
        country.add("FI-FINLAND");
        country.add("FR- FRANCE");
        country.add("GR-GREECE");
        country.add("HR-CROATIA");
        country.add("CY-REPUBLIC OF CYPRUS");
        country.add("CZ-CZECH REPUBLIC");
        country.add("HU-HUNGARY");
        country.add("IE-IRELAND");
        country.add("IT- ITALY");
        country.add("LT-LITHUANIA");
        country.add("LU- LUXEMBOURG");
        country.add("LV-LATVIA");
        country.add("MT- MALTA");
        country.add("NL-NETHERLANDS");
        country.add("PL-POLAND");
        country.add("RO-ROMANIA");
        country.add("CH-SWITZERLAND");

        Collections.sort(country, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        return country;
    }

    private ArrayList<String> getEurCountry() {

        ArrayList<String> country = new ArrayList<>();
//        country.add("PT");
//        country.add("AT");
//        country.add("BE");
//        country.add("BG");
//        country.add("SE");
//        country.add("SI");
//        country.add("DE");
//        country.add("SK");
//        country.add("DK");
//        country.add("EE");
//        country.add("ES");
//        country.add("FI");
//        country.add("FR");
//        country.add("GR");
//        country.add("HR");
//        country.add("CY");
//        country.add("CZ");
//        country.add("HU");
//        country.add("IE");
//        country.add("IT");
//        country.add("LT");
//        country.add("LU");
//        country.add("LV");
//        country.add("MT");
//        country.add("NL");
//        country.add("PL");
//        country.add("RO");

        country.add("PT- PORTUGAL");
        country.add("AT- AUSTRIA");
        country.add("BE- BELGIUM");
        country.add("BG- BULGARIA");
        country.add("SE- SWEDEN");
        country.add("SI- SLOVENIA");
        country.add("DE- GERMANY");
        country.add("SK- SLOVAKIA");
        country.add("DK- DENMARK");
        country.add("EE- ESTONIA");
        country.add("ES- SPAIN");
        country.add("FI- FINLAND");
        country.add("FR- FRANCE");
        country.add("GR- GREECE");
        country.add("HR- CROATIA");
        country.add("CY- REPUBLIC OF CYPRUS");
        country.add("CZ- CZECH REPUBLIC");
        country.add("HU- HUNGARY");
        country.add("IE- IRELAND");
        country.add("IT- ITALY");
        country.add("LT- LITHUANIA");
        country.add("LU- LUXEMBOURG");
        country.add("LV- LATVIA");
        country.add("MT- MALTA");
        country.add("NL- NETHERLANDS");
        country.add("PL- POLAND");
        country.add("RO- ROMANIA");
        country.add("CH- SWITZERLAND");

        Collections.sort(country, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        return country;
    }

    private ArrayList<String> getGbCountry() {
        ArrayList<String> country = new ArrayList<>();
        country.add("GB- UNITED KINGDOM");
        return country;
    }

    private ArrayList<String> getStripeCountry() {

        ArrayList<String> stripeCountry = new ArrayList<>();

        stripeCountry.add("AT");
        stripeCountry.add("BE");
        stripeCountry.add("DK");
        stripeCountry.add("FR");
        stripeCountry.add("FI");
        stripeCountry.add("DE");
        stripeCountry.add("IT");
        stripeCountry.add("IE");
        stripeCountry.add("LU");
        stripeCountry.add("NL");
        stripeCountry.add("PT");
        stripeCountry.add("SE");
        stripeCountry.add("ES");
        stripeCountry.add("GB");

        return stripeCountry;

    }

    private ArrayList<String> getCurrency() {
        ArrayList<String> country = new ArrayList<>();
        country.add("GBP");
        country.add("EUR");
        return country;
    }

    public void previousSelected() {
        defaultSelected.add(Calendar.MONTH, 0);
        defaultSelected.add(Calendar.DAY_OF_MONTH, 0);
        Calendar cal = dateSelected;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                txtDobValue.setText(mShowDob.format(dateSelected.getTime()));
            }

        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        datePickerDialog.getDatePicker().setMaxDate(defaultSelected.getTimeInMillis());
        datePickerDialog.setTitle("");
        datePickerDialog.show();
    }

    private void alertDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_message)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearData();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    void clearData() {
        mSalesBankDetailModel.countryCode = "";
        mSalesBankDetailModel.currency = "";
        mSalesBankDetailModel.accountNo = null;
        mSalesBankDetailModel.sortCode = "";
        mSalesBankDetailModel.firstName = "";
        mSalesBankDetailModel.lastName = "";
        mSalesBankDetailModel.address = "";
        mSalesBankDetailModel.city = "";
        mSalesBankDetailModel.state = "";
        mSalesBankDetailModel.postalCode = "";
        mSalesBankDetailModel.dob = "";
        mSalesBankDetailModel.swift = "";
        mSalesBankDetailModel.documentPath = "";
        mSalesBankDetailModel.account_type = "";
        mSalesBankDetailModel.documnetFile = null;
        SalesBankDetailModel.setSalesBankDetailModel(mSalesBankDetailModel);

        Intent in = new Intent();
        setResult(RESULT_OK, in);
        in.putExtra("account_name", "");

        finish();
    }

    void visibleStripeView(boolean visible) {
        if (visible) {
            edSortCode.setVisibility(View.VISIBLE);
            view_sort.setVisibility(View.VISIBLE);
            edAddress.setVisibility(View.VISIBLE);
            view_address.setVisibility(View.VISIBLE);
            ll_city.setVisibility(View.VISIBLE);
            ll_postal_code.setVisibility(View.VISIBLE);
            txtAddDocumnet.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.VISIBLE);
            ll_stripe_logo.setVisibility(View.VISIBLE);
        } else {
            edSortCode.setVisibility(View.GONE);
            view_sort.setVisibility(View.GONE);
            edAddress.setVisibility(View.GONE);
            view_address.setVisibility(View.GONE);
            ll_city.setVisibility(View.GONE);
            ll_postal_code.setVisibility(View.GONE);
            txtAddDocumnet.setVisibility(View.GONE);
            imgAdd.setVisibility(View.GONE);
            ll_stripe_logo.setVisibility(View.VISIBLE);
        }


    }

}
