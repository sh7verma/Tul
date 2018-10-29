package com.app.tul;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.TimeZone;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.MaterialEditText;
import customviews.RoundedTransformation;
import model.CreateStripeAccountModel;
import model.TulModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

import static model.TulModel.getBankDetailsTul;
import static model.TulModel.setBankDetailsTul;

public class AddPaymentAccountActivity extends BaseActivity {

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
    //
    @BindView(R.id.ed_swift)
    MaterialEditText edSwift;

    @BindView(R.id.txt_add_documnet)
    TextView txtAddDocumnet;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.txt_save)
    TextView txtSave;
    @BindView(R.id.txt_discard)
    TextView txtDiscard;
    ProgressDialog progDailog;

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


    boolean isStripe = false;

    Calendar dateSelected = Calendar.getInstance(TimeZone.getDefault());
    Calendar defaultSelected = Calendar.getInstance();

    SimpleDateFormat mShowDob = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    TulModel.BankDetailsTul mBankDetailsTul;
    private File mFile;
    private String mPath;
    private boolean accountAlready;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_bank_detail;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        llInside.setPadding(mWidth / 24, mWidth / 24, mWidth / 24, 0);

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        txtToolbarTitle.setText(R.string.add_new_account);

        txtBankDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
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
        saveParms.setMargins(0, mHeight / 32, 0, mHeight / 32);
        txtSave.setLayoutParams(saveParms);

        txtSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtSave.setPadding(0, mWidth / 28, 0, mWidth / 28);

        txtDiscard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtDiscard.setPadding(0, mWidth / 28, 0, mWidth / 28);
        txtDiscard.setVisibility(View.GONE);

        LinearLayout.LayoutParams imgParms = new LinearLayout.LayoutParams(mWidth / 6, mWidth / 6);
        imgAdd.setLayoutParams(imgParms);

    }

    @Override
    protected void onCreateStuff() {
        visibleStripeView(false);
        mBankDetailsTul = getBankDetailsTul();
        if (db.getAllAccounts().size() > 0)
            accountAlready = true;


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

        getPrimaryDialog();
    }

    @Override
    protected void initListener() {
        if (!accountAlready) {
            llCountryCode.setOnClickListener(this);
//            llCurrency.setOnClickListener(this);
        }

        llDob.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtDiscard.setOnClickListener(this);
        imgBckImg.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBankDetailsTul.updateData) {
            setData();
            mBankDetailsTul.updateData = false;
        }
    }

    private void setData() {

        if (mBankDetailsTul.accountNo != null) {

            txtCountryCodeValue.setText(mBankDetailsTul.countryCode);

            txtCurrencyValue.setText(mBankDetailsTul.currency);

            edAccountNo.setText(mBankDetailsTul.accountNo);
            edAccountNo.setSelection(edAccountNo.getText().toString().trim().length());

            edSortCode.setText(mBankDetailsTul.sortCode);
            edSortCode.setSelection(edSortCode.getText().toString().trim().length());

            edFirstName.setText(mBankDetailsTul.firstName);
            edFirstName.setSelection(edFirstName.getText().toString().trim().length());

            edLastName.setText(mBankDetailsTul.lastName);
            edLastName.setSelection(edLastName.getText().toString().trim().length());

            edAddress.setText(mBankDetailsTul.address);
            edAddress.setSelection(edAddress.getText().toString().trim().length());

            edCity.setText(mBankDetailsTul.city);
            edCity.setSelection(edCity.getText().toString().trim().length());

            edState.setText(mBankDetailsTul.state);
            edState.setSelection(edState.getText().toString().trim().length());

            edSwift.setText(mBankDetailsTul.swift);
            edSwift.setSelection(edSwift.getText().toString().trim().length());

            edPostalCode.setText(mBankDetailsTul.postalCode);
            edPostalCode.setSelection(edPostalCode.getText().toString().trim().length());

            txtDobValue.setText(mBankDetailsTul.dob);

            mPath = mBankDetailsTul.documentPath;
            mFile = mBankDetailsTul.documnetFile;

            Picasso.with(mContext).load(mFile).resize(mWidth / 6, mWidth / 6)
                    .into(imgAdd);

            if (mBankDetailsTul.account_type.equals("1")) {
                isStripe = true;
            } else {
                isStripe = false;
            }
        }

        visibleStripeView(isStripe);
    }

    @Override
    protected Context getContext() {
        return AddPaymentAccountActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.txt_discard:
                alertDiscardDialog();
                break;
//            case R.id.ll_currency:
//                selectOptions(getAdd_currency(), CURENCY, txtCurrencyValue.getText().toString());
//                break;
            case R.id.ll_country_code:

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
                previousSelected();
                break;
            case R.id.txt_save:
                if (TextUtils.isEmpty(txtCountryCodeValue.getText().toString().trim()))
                    showAlert(llInside, getString(R.string.error_country_code));
                else if (TextUtils.isEmpty(txtCurrencyValue.getText().toString().trim()))
                    showAlert(llInside, getString(R.string.error_currency));
                else if (TextUtils.isEmpty(edAccountNo.getText().toString().trim()))
                    showAlert(llInside, getString(R.string.error_account));
                else if (edAccountNo.getText().length() < 4)
                    showAlert(llInside, getString(R.string.error_valid_account));
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
                else if (TextUtils.isEmpty(mPath) && isStripe)
                    showAlert(llInside, getString(R.string.error_document));
                else {
                    if (connectedToInternet()) {

                        mBankDetailsTul.countryCode = txtCountryCodeValue.getText().toString();
                        mBankDetailsTul.currency = txtCurrencyValue.getText().toString();
                        mBankDetailsTul.accountNo = edAccountNo.getText().toString();
                        mBankDetailsTul.sortCode = edSortCode.getText().toString();
                        mBankDetailsTul.firstName = edFirstName.getText().toString();
                        mBankDetailsTul.lastName = edLastName.getText().toString();
                        mBankDetailsTul.address = edAddress.getText().toString();
                        mBankDetailsTul.city = edCity.getText().toString();
                        mBankDetailsTul.state = edState.getText().toString();
                        mBankDetailsTul.postalCode = edPostalCode.getText().toString();
                        mBankDetailsTul.dob = txtDobValue.getText().toString();
                        mBankDetailsTul.documentPath = mPath;
                        mBankDetailsTul.documnetFile = mFile;

                        mBankDetailsTul.swift = edSwift.getText().toString();

                        if (isStripe) {
                            mBankDetailsTul.account_type = "1";
                        } else {
                            mBankDetailsTul.account_type = "0";
                        }

                        setBankDetailsTul(mBankDetailsTul);
                        hitAPI();
                    } else {
                        showInternetAlert(llCountryCode);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
//                case CURENCY:
//                    txtCurrencyValue.setText(data.getStringExtra("selected_data"));
//
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
                            .transform(new RoundedTransformation(10, 0))
                            .into(imgAdd);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void selectOptions(ArrayList<String> mData, int intentType, String selected) {
        Intent in = new Intent(mContext, OptionSelection.class);
        in.putExtra("selected", selected);
        in.putStringArrayListExtra("data", mData);
        startActivityForResult(in, intentType);
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
        country.add("CH-SWITZERLAND");

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
        return country;
    }

    public void previousSelected() {
        defaultSelected.add(Calendar.YEAR, -13);
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
                        mBankDetailsTul.accountNo = null;
                        TulModel.setBankDetailsTul(mBankDetailsTul);
                        dialog.cancel();
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
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

    private void hitAPI() {

        showProgress();
        final RequestBody reqFile;
        MultipartBody.Part body;

        if (mFile != null) {
            reqFile = RequestBody.create(MediaType.parse("image/*"), mFile);
            body = MultipartBody.Part.createFormData("document", mFile.getName(), reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("image/*"), "");
            body = MultipartBody.Part.createFormData("profile_pic", "", reqFile);
        }

        RequestBody access_token = RequestBody.create(MediaType.parse("text/plain"), utils.getString("access_token", ""));


        RequestBody country_code;
        String[] s = txtCountryCodeValue.getText().toString().split("-");
        country_code = RequestBody.create(MediaType.parse("text/plain"), s[0].trim());

        RequestBody currency = RequestBody.create(MediaType.parse("text/plain"), txtCurrencyValue.getText().toString().trim());
        RequestBody account_number = RequestBody.create(MediaType.parse("text/plain"), edAccountNo.getText().toString().trim());
        RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), edFirstName.getText().toString().trim());
        RequestBody last_name = RequestBody.create(MediaType.parse("text/plain"), edLastName.getText().toString().trim());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), edAddress.getText().toString().trim());
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), edCity.getText().toString().trim());
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), edState.getText().toString().trim());
        RequestBody postal_code = RequestBody.create(MediaType.parse("text/plain"), edPostalCode.getText().toString());
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), txtDobValue.getText().toString());
        RequestBody sort_code = RequestBody.create(MediaType.parse("text/plain"), edSortCode.getText().toString());
        RequestBody swift = RequestBody.create(MediaType.parse("text/plain"), edSwift.getText().toString());

        RequestBody account_type = RequestBody.create(MediaType.parse("text/plain"), mBankDetailsTul.account_type);

        Call<CreateStripeAccountModel> call = RetrofitClient.getInstance().stripeBank(access_token, country_code, currency
                , account_number, first_name, last_name, address, city, state, postal_code, dob, sort_code, swift,
                account_type, body);
        call.enqueue(new Callback<CreateStripeAccountModel>() {
            @Override
            public void onResponse(Call<CreateStripeAccountModel> call, Response<CreateStripeAccountModel> response) {
                if (response.body().getResponse() != null) {
                    db.addAccount(response.body().getResponse());

                    utils.setString(Constants.IS_CURRENCY_SELECTED, "1");
                    utils.setString(Constants.PRIMARY_CURRENCY,txtCurrencyValue.getText().toString().trim());

                    Toast.makeText(AddPaymentAccountActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    moveToNext(response.body().getResponse().getAccountId());
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, utils);
                    } else {
                        showAlert(llInside, response.body().error.getMessage());
                    }
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<CreateStripeAccountModel> call, Throwable t) {
                showAlert(llInside, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

    private void moveToNext(int accountId) {
        Intent intent = new Intent();
        intent.putExtra("accountId", accountId);
        intent.putExtra("accountAlready", accountAlready);
        setResult(RESULT_OK, intent);
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
                        visibleStripeView(isStripe);

                    } else {
                        accountAlready = true;

                        Log.e("No Account = ", "NO");
                        utils.setString("accountId", response.body().getResponse().getAccount());

                        mBankDetailsTul.countryCode = response.body().getResponse().getCountry_code();
                        mBankDetailsTul.currency = response.body().getResponse().getCurrency();

                        mBankDetailsTul.account_type = response.body().getResponse().getAccount_type();

                        if (mBankDetailsTul.account_type.equals("1")) {
                            isStripe = true;
                        } else {
                            isStripe = false;
                        }

                        visibleStripeView(isStripe);

                        txtCountryCodeValue.setText(mBankDetailsTul.countryCode);
                        txtCurrencyValue.setText(mBankDetailsTul.currency);
                    }

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llCountryCode, response.body().error.getMessage());
                    }
                }
                progDailog.dismiss();
            }

            @Override
            public void onFailure(Call<CreateStripeAccountModel> call, Throwable t) {
                showAlert(llCountryCode, t.getLocalizedMessage());
                progDailog.dismiss();
            }
        });
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
            ll_stripe_logo.setVisibility(View.GONE);

        }
    }

}
