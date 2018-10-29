package com.app.tul;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import api.RetrofitClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import dialogs.PreferencesAvailableOnDialog;
import model.CreateStripeAccountModel;
import model.TulImageModel;
import model.TulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;


public class PreferencesActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private static final int MOVENEXT = 7;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.tv_preferences_detail)
    TextView tvPreferencesDetail;
    @BindView(R.id.tv_preferences_available_label)
    TextView tvPreferencesAvailableLabel;
    @BindView(R.id.tv_preferences_available)
    TextView tvPreferencesAvailable;

    @BindView(R.id.ll_inner)
    LinearLayout llInner;
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.tv_preferences_start_time_label)
    TextView tvPreferencesStartTimeLabel;
    @BindView(R.id.tv_preferences_start_time)
    TextView tvPreferencesStartTime;

    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.tv_preferences_end_time_label)
    TextView tvPreferencesEndTimeLabel;
    @BindView(R.id.tv_preferences_end_time)
    TextView tvPreferencesEndTime;

    @BindView(R.id.tv_tul_available_delivery)
    TextView tvTulAvailableDelivery;

    @BindView(R.id.edt_delivery_charge)
    EditText edtDeleveryCharge;
    @BindView(R.id.ed_extra_earning)
    EditText edExtraEarning;
    @BindView(R.id.ll_extra_earning)
    LinearLayout llExtraEarning;
    @BindView(R.id.img_extra_earning_info)
    ImageView imgExtraEarningInfo;
    @BindView(R.id.txt_delivery_pickup_time_label)
    TextView txtDeliveryPickupTimeLabel;
    @BindView(R.id.sw_preferences_tul_available_delivery)
    SwitchCompat swAvailableDelivery;

    @BindView(R.id.txt_delivery_pickup_start_time)
    TextView txtDeliveryPickupStartTime;
    @BindView(R.id.txt_delivery_pickup_end_time)
    TextView txtDeliveryPickupEndTime;


    @BindView(R.id.ll_preferences_buttons_container)
    LinearLayout llBtContainer;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.bt_discard)
    Button btDiscard;
    @BindView(R.id.ll_available)
    LinearLayout llAvailable;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_delivery_available)
    LinearLayout llDeliveryAvailable;

    @BindView(R.id.ll_pickup_time)
    LinearLayout llPickupTime;
    @BindView(R.id.ll_time_left)
    LinearLayout llTimeLeft;
    @BindView(R.id.ll_time_right)
    LinearLayout llTimeRight;
    @BindView(R.id.view1)
    LinearLayout view1;
    @BindView(R.id.view2)
    LinearLayout view2;
    @BindView(R.id.view3)
    LinearLayout view3;
    @BindView(R.id.view4)
    LinearLayout view4;
    @BindView(R.id.rl_preferences_main_container)
    RelativeLayout rlMainContainer;

    @BindView(R.id.txt_hint_charges)
    TextView txtHintCharges;

    TulModel.PreferencesTul mPreferencesTul;
    ProgressDialog progDailog;
    private boolean mPopupEnable;
    double mTransactionPercentage = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_preferences;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        txtToolbarTitle.setText(R.string.prefrences);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(mWidth / 80, 0, 0, 0);
        param.addRule(RelativeLayout.CENTER_VERTICAL);
        imgBckImg.setLayoutParams(param);

        llInner.setPadding(mWidth / 24, 0, mWidth / 24, 0);

        tvPreferencesDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        tvPreferencesDetail.setPadding(mWidth / 24, mWidth / 24, mWidth / 24, mHeight / 24);

        Typeface typefaceRegular = Typeface.createFromAsset(getContext()
                .getAssets(), "fonts/regular.ttf");

        Typeface typeface = Typeface.createFromAsset(getContext()
                .getAssets(), "fonts/semibold.ttf");

        btDiscard.setTypeface(typeface);
        btSave.setTypeface(typeface);

        tvPreferencesAvailableLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvPreferencesAvailable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvPreferencesAvailable.setPadding(0, mWidth / 64, 0, 0);

        tvPreferencesStartTimeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvPreferencesStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvPreferencesEndTimeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvPreferencesEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvTulAvailableDelivery.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulAvailableDelivery.setPadding(0, mWidth / 24, 0, mWidth / 24);

        txtDeliveryPickupTimeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        txtDeliveryPickupStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtDeliveryPickupStartTime.setPadding(0, mWidth / 64, 0, mWidth / 32);

        txtDeliveryPickupEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtDeliveryPickupEndTime.setPadding(0, mWidth / 64, 0, mWidth / 32);

        edtDeleveryCharge.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edtDeleveryCharge.setTypeface(typeface);

        edExtraEarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edExtraEarning.setTypeface(typeface);

        txtHintCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtHintCharges.setPadding(0, mWidth / 64, 0, mWidth / 24);

        btDiscard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        btSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        btSave.setTypeface(typeface);

        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        param1.setMargins(mWidth / 24, 0, mWidth / 24, 0);
        llBtContainer.setLayoutParams(param1);

        llAvailable.setPadding(0, mWidth / 32, 0, mWidth / 32);

        llTime.setPadding(0, mWidth / 32, 0, mWidth / 32);

        llPickupTime.setPadding(0, mWidth / 28, 0, mWidth / 28);

        llTimeLeft.setPadding(0, mWidth / 32, 0, mWidth / 32);

        llTimeRight.setPadding(0, mWidth / 32, 0, mWidth / 32);

        LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mWidth * 0.004));
        param3.setMargins(mWidth / 62, mWidth / 32, mWidth / 42, 0);
        llTimeLeft.setLayoutParams(param3);

        LinearLayout.LayoutParams param4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mWidth * 0.004));
        param4.setMargins(0, mWidth / 32, mWidth / 42, 0);
        llTimeRight.setLayoutParams(param4);

    }

    @Override
    protected void onCreateStuff() {
        mTransactionPercentage = Double.parseDouble(getIntent().getStringExtra("transaction_fee"));
        mPreferencesTul = TulModel.getPrefrencesTul();
        llEndTime.setEnabled(false);

        final Drawable img = ContextCompat.getDrawable(mContext, R.drawable.pound);
        img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));
        edtDeleveryCharge.setCompoundDrawables(img, null, null, null);
        edExtraEarning.setCompoundDrawables(img, null, null, null);
        edtDeleveryCharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().startsWith(".")) {
                    s = "0.";
                    edtDeleveryCharge.setText(s);
                    edtDeleveryCharge.setSelection(s.length());
                }
                if (s.length() > 0) {
                    mPopupEnable = true;
                    double i = Double.parseDouble(String.valueOf(s));
                    i = i - ((mTransactionPercentage * i) / 100);
                    edExtraEarning.setText(amountConversion(String.format("%.10f", i)));
                } else {
                    mPopupEnable = false;
                    edExtraEarning.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void initListener() {
        llStartTime.setOnClickListener(this);
        llEndTime.setOnClickListener(this);
        llAvailable.setOnClickListener(this);
        tvTulAvailableDelivery.setOnClickListener(this);
        swAvailableDelivery.setOnCheckedChangeListener(this);
        txtDeliveryPickupEndTime.setOnClickListener(this);
        txtDeliveryPickupStartTime.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btDiscard.setOnClickListener(this);
        imgBckImg.setOnClickListener(this);
        imgExtraEarningInfo.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return PreferencesActivity.this;
    }


    @Override
    protected void onResume() {
        if (mPreferencesTul.updateData) {
            setData();
            mPreferencesTul.updateData = false;
        }
        super.onResume();
    }

    public String amountConversion(String am) {
        String amount;
        Double d = Double.parseDouble(am);
        Locale locale = new Locale("en", "UK");
        String pattern = "#0.00";

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);

        amount = decimalFormat.format(d);
        return amount;
    }


    private void setData() {
        if (mPreferencesTul.availbiltyMode != null) {

            tvPreferencesAvailable.setText(mPreferencesTul.availbiltyMode);
            tvPreferencesStartTime.setText(mPreferencesTul.startTime);
            tvPreferencesEndTime.setText(mPreferencesTul.endTime);

            if (mPreferencesTul.deleiveryAvailable) {
                swAvailableDelivery.setChecked(true);
                llPickupTime.setVisibility(View.VISIBLE);
                edtDeleveryCharge.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);

                txtDeliveryPickupStartTime.setText(mPreferencesTul.startPickUpTime);
                txtDeliveryPickupEndTime.setText(mPreferencesTul.endPickUpTime);
                edtDeleveryCharge.setText(mPreferencesTul.deliveryCharges);
                edtDeleveryCharge.setSelection(edtDeleveryCharge.getText().toString().length());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_extra_earning_info:
                if (mPopupEnable) {
                    PopupWindow popupwindow_obj = showEarningPopup(edtDeleveryCharge.getText().toString().trim(),
                            getIntent().getStringExtra("transaction_fee"));
                    popupwindow_obj.showAsDropDown(imgExtraEarningInfo, 4, -55);
                }
                break;
            case R.id.img_back:
                mPreferencesTul.updateData = true;
                TulModel.setPrefrencesTul(mPreferencesTul);
                moveBack();
                break;
            case R.id.bt_discard:
                alertDiscardDialog();
                break;
            case R.id.bt_save:
                if (TextUtils.isEmpty(tvPreferencesStartTime.getText().toString())) {
                    showAlert(llAvailable, getString(R.string.error_start_time));
                    return;
                } else if (TextUtils.isEmpty(tvPreferencesEndTime.getText().toString())) {
                    showAlert(llAvailable, getString(R.string.error_end_time));
                    return;
                }
                if (edtDeleveryCharge.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(edtDeleveryCharge.getText().toString().trim()))
                        showAlert(llInner, getString(R.string.error_delivery_charges));
                    else if (Double.parseDouble(edtDeleveryCharge.getText().toString().trim()) < Constants.MIN_PRICE)
                        showAlert(llInner, getString(R.string.error_valid_charges));
                    else if (Double.parseDouble(edtDeleveryCharge.getText().toString().trim()) > Constants.MAX_PRICE)
                        showAlert(llInner, getString(R.string.error_max_price));
                    else if (TextUtils.isEmpty(txtDeliveryPickupStartTime.getText().toString()))
                        showAlert(llAvailable, getString(R.string.error_delivery_start_time));
                    else if (TextUtils.isEmpty(txtDeliveryPickupEndTime.getText().toString()))
                        showAlert(llAvailable, getString(R.string.error_delivery_end_time));
                    else
                        moveToNext();
                } else {
                    moveToNext();
                }
                break;
            case R.id.ll_start_time: {
                final Calendar c = Calendar.getInstance();
                final int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(mContext,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                tvPreferencesStartTime.setText(updateTime(hourOfDay, minute));
                                tvPreferencesEndTime.setText(updateTime(hourOfDay, minute) + " (Next Day)");
                            }
                        }, mHour, mMinute, false);
                tpd.show();
                break;
            }
           /* case R.id.ll_end_time: {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(mContext,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                tvPreferencesEndTime.setText(updateTime(hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                tpd.show();
                break;
            }*/
            case R.id.txt_delivery_pickup_start_time: {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(mContext,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtDeliveryPickupStartTime.setText(updateTime(hourOfDay, minute) + " - ");
                            }
                        }, mHour, mMinute, false);

                tpd.show();
                break;
            }
            case R.id.txt_delivery_pickup_end_time:
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(mContext,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtDeliveryPickupEndTime.setText(updateTime(hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                tpd.show();
                break;

            case R.id.ll_available:
                new PreferencesAvailableOnDialog(mContext, mWidth, tvPreferencesAvailable.getText().toString()).showDialog();
                break;

            case R.id.tv_tul_available_delivery:
                if (swAvailableDelivery.isChecked()) {
                    swAvailableDelivery.setChecked(true);
                } else {
                    swAvailableDelivery.setChecked(false);
                }
                break;

        }
    }

    private void moveToNext() {
        mPreferencesTul.availbiltyMode = tvPreferencesAvailable.getText().toString();
        mPreferencesTul.startTime = tvPreferencesStartTime.getText().toString();
        mPreferencesTul.endTime = tvPreferencesEndTime.getText().toString();
        mPreferencesTul.deliveryCharges = edtDeleveryCharge.getText().toString();
        mPreferencesTul.startPickUpTime = txtDeliveryPickupStartTime.getText().toString();
        mPreferencesTul.endPickUpTime = txtDeliveryPickupEndTime.getText().toString();
        mPreferencesTul.deleiveryAvailable = swAvailableDelivery.isChecked();

        TulModel.setPrefrencesTul(mPreferencesTul);

        if (mPreferencesTul.isEdit) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            if (connectedToInternet())
                getPrimaryDialog();
            else
                showAlert(llAvailable, errorInternet);
        }
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
                        Intent intent = new Intent(mContext, AddBankDetailActivity.class);
                        startActivityForResult(intent, MOVENEXT);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        Log.e("No Account = ", "NO");
                        utils.setString("accountId", response.body().getResponse().getAccount());
                        TulModel.BankDetailsTul mBankDetailsTul = TulModel.getBankDetailsTul();
                        mBankDetailsTul.countryCode = response.body().getResponse().getCountry_code();
                        mBankDetailsTul.currency = response.body().getResponse().getCurrency();
                        mBankDetailsTul.accountNo = response.body().getResponse().getAccount_number();
                        mBankDetailsTul.sortCode = response.body().getResponse().getSort_code();
                        mBankDetailsTul.firstName = response.body().getResponse().getFirst_name();
                        mBankDetailsTul.lastName = response.body().getResponse().getLast_name();
                        mBankDetailsTul.address = response.body().getResponse().getAddress();
                        mBankDetailsTul.city = response.body().getResponse().getCity();
                        mBankDetailsTul.state = response.body().getResponse().getState();
                        mBankDetailsTul.postalCode = response.body().getResponse().getPostal_code();
                        mBankDetailsTul.dob = response.body().getResponse().getDob();
                        mBankDetailsTul.swift = response.body().getResponse().getSwift();
                        mBankDetailsTul.account_type = response.body().getResponse().getAccount_type();
                        TulModel.setBankDetailsTul(mBankDetailsTul);
                        moveToList();
                    }

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llAvailable, response.body().error.getMessage());
                    }
                }
                progDailog.dismiss();
            }

            @Override
            public void onFailure(Call<CreateStripeAccountModel> call, Throwable t) {
                showAlert(llAvailable, t.getLocalizedMessage());
                progDailog.dismiss();
            }
        });
    }

    private void moveToList() {
        utils.setString("visible_primary", "yes");
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setResultOfAvailableOn(String result) {
        tvPreferencesAvailable.setText(result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == MOVENEXT) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();


        return aTime;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.sw_preferences_tul_available_delivery: {
                if (isChecked) {
                    llPickupTime.setVisibility(View.VISIBLE);
                    edtDeleveryCharge.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    llExtraEarning.setVisibility(View.VISIBLE);
                } else {
                    llPickupTime.setVisibility(View.GONE);
                    edtDeleveryCharge.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                    llExtraEarning.setVisibility(View.GONE);
                }
            }
            break;
        }
    }


    private void alertDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_message)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPreferencesTul.availbiltyMode = null;
                        TulModel.setPrefrencesTul(mPreferencesTul);
                        moveBack();
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

    @Override
    public void onBackPressed() {
        mPreferencesTul.updateData = true;
        TulModel.setPrefrencesTul(mPreferencesTul);
        moveBack();
    }

    private void moveBack() {
        if (getIntent().hasExtra("BACKTOLANDING")) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


    private PopupWindow showEarningPopup(String price, String transaction_percentage) {
        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.info_dialog_layout, null);

        LinearLayout.LayoutParams arrowParms = new LinearLayout.LayoutParams((int) (getResources().getDimension(R.dimen._15sdp)), (int) (getResources().getDimension(R.dimen._15sdp)));
        arrowParms.setMargins(0, 0, mWidth / 22, 0);
        arrowParms.gravity = Gravity.RIGHT;
        TextView txtArrow = (TextView) view.findViewById(R.id.txt_arrow1);
        txtArrow.setLayoutParams(arrowParms);

        TextView txtInfo = (TextView) view.findViewById(R.id.txt_info);
        txtInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        txtInfo.setText("This is the amount you will earn after deducting Delivery charges of " + transaction_percentage + "% from your "
                + utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,"")) + price +
                " listings.");
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        return popupWindow;
    }



}