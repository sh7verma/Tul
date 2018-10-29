package com.app.tul;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.MaterialEditText;
import model.BookTulModel;
import model.CardLocalModel;
import model.ViewTulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;
import utils.CryptLib;

public class AddPaymentCardActivity extends BaseActivity {

    private static final int MONTH = 1;
    private static final int YEAR = 2;
    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.ed_name_on_card)
    MaterialEditText edNameOnCard;

    @BindView(R.id.ed_card_no)
    MaterialEditText edCardNo;
    @BindView(R.id.ll_expiry_month)
    LinearLayout llExpiryMonth;

    @BindView(R.id.tv_expiry_month)
    TextView tvExpiryMonth;
    @BindView(R.id.tv_expiry_month_hint)
    TextView tvExpiryMonthHint;
    @BindView(R.id.ll_expiry_year)

    LinearLayout llExpiryYear;
    @BindView(R.id.tv_expiry_year)
    TextView tvExpiryYear;
    @BindView(R.id.tv_expiry_year_hint)
    TextView tvExpiryYearHint;

    @BindView(R.id.ll_save_card)
    LinearLayout llSaveCard;
    @BindView(R.id.img_save)
    ImageView imgSave;
    @BindView(R.id.txt_save)
    TextView txtSave;
    @BindView(R.id.ed_cvv_no)
    MaterialEditText edCvvNo;

    @BindView(R.id.bt_add_card)
    RelativeLayout btAddCard;
    @BindView(R.id.txt_btn)
    TextView tvBtn;

    @BindView(R.id.ll_inner)
    LinearLayout llInner;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.view6)
    View view6;

    boolean mSaveCard;
    ViewTulModel.ResponseBean mViewTulModel;
    ViewTulModel.ResponseBean.AdditionalPriceBean additionalPriceBean;
    ArrayList<String> mSelectedDates = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_add_payment_card;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));


        llInner.setPadding(mWidth / 22, mHeight / 32, mWidth / 22, mHeight / 28);

        edNameOnCard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edNameOnCard.setTypeface(typeface);

        edNameOnCard.setFloatingLabelTextSize((int) (mWidth * 0.035));
        edNameOnCard.setFloatingTextTypeface(typefaceRegular);

        edCardNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edCardNo.setTypeface(typeface);

        edCardNo.setFloatingLabelTextSize((int) (mWidth * 0.035));
        edCardNo.setFloatingTextTypeface(typefaceRegular);

        tvExpiryMonth.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        tvExpiryMonth.setPadding(0, 0, 0, mHeight / 64);
        tvExpiryMonth.setTypeface(typeface);

        tvExpiryMonthHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        tvExpiryMonthHint.setPadding(0, mHeight / 32, 0, mHeight / 66);
        tvExpiryMonthHint.setTypeface(typefaceRegular);

        tvExpiryYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        tvExpiryYear.setPadding(0, 0, 0, mHeight / 64);
        tvExpiryYear.setTypeface(typeface);

        tvExpiryYearHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        tvExpiryYearHint.setPadding(0, mHeight / 32, 0, mHeight / 66);
        tvExpiryYearHint.setTypeface(typefaceRegular);

        llSaveCard.setPadding(0, mHeight / 64, 0, mHeight / 64);

        edCvvNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edCvvNo.setTypeface(typeface);

        edCvvNo.setFloatingLabelTextSize((int) (mWidth * 0.035));
        edCvvNo.setFloatingTextTypeface(typefaceRegular);

        txtSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        txtSave.setPadding(mWidth / 32, 0, 0, 0);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        view1.setLayoutParams(layoutParams2);
        view2.setLayoutParams(layoutParams2);
        view3.setLayoutParams(layoutParams2);
        view5.setLayoutParams(layoutParams2);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        layoutParams.setMargins(0, 0, 0, mHeight / 24);

        if (getIntent().hasExtra("checkout")) {
            edCvvNo.setVisibility(View.VISIBLE);
            view5.setVisibility(View.VISIBLE);
            view6.setVisibility(View.VISIBLE);
            llSaveCard.setVisibility(View.VISIBLE);
            view4.setLayoutParams(layoutParams2);
            view6.setLayoutParams(layoutParams);
        } else {
            view4.setLayoutParams(layoutParams);
            view5.setLayoutParams(layoutParams2);
        }


        tvBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        tvBtn.setPadding(0, mWidth / 28, 0, mWidth / 28);
    }

    @Override
    protected void onCreateStuff() {
        if (getIntent().hasExtra("checkout")) {
            txtToolbarTitle.setText(R.string.checkout);
            mViewTulModel = getIntent().getParcelableExtra("tul_data");
            additionalPriceBean = mViewTulModel.getAdditional_price();
            mSelectedDates = getIntent().getStringArrayListExtra("selected_dates");
            tvBtn.setText(R.string.proceed_to_pay);
        } else
            txtToolbarTitle.setText(R.string.add_new_card);

        edCardNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ss = s.toString();
                if (before == 0) { // new character added
                    if (s.length() == 4 || s.length() == 9 || s.length() == 14 || s.length() == 19) {
                        edCardNo.setText(s + " ");
                        edCardNo.setSelection((edCardNo.getText().toString()).length());
                    }
                } else { // back pressed
                    if (s.length() == 4 || s.length() == 9 || s.length() == 14 || s.length() == 19) {
                        edCardNo.setText(ss.substring(0, ss.length() - 1));
                        edCardNo.setSelection((edCardNo.getText().toString()).length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initListener() {
        btAddCard.setOnClickListener(this);
        imgBckImg.setOnClickListener(this);
        llExpiryMonth.setOnClickListener(this);
        llExpiryYear.setOnClickListener(this);
        llSaveCard.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return AddPaymentCardActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_card:
                if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 1) {
                    if (getIntent().hasExtra("checkout"))
                        verifyCardDetails();
                    else
                        verifyDetails();
                } else {
                    verifyEmailDialog();
                }
                break;
            case R.id.ll_expiry_month:
                selectOptions(getMonth(), MONTH, tvExpiryMonth.getText().toString());
                break;
            case R.id.ll_expiry_year:
                selectOptions(getYear(), YEAR, tvExpiryYear.getText().toString());
                break;
            case R.id.img_back:
                Constants.closeKeyboard(mContext, llInner);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.ll_save_card:
                if (mSaveCard) {
                    imgSave.setImageResource(R.mipmap.ic_no_save);
                    mSaveCard = false;
                } else {
                    imgSave.setImageResource(R.mipmap.ic_save);
                    mSaveCard = true;
                }
                break;
        }
    }

    private void verifyEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(R.string.verify_email_2)
                .setCancelable(false)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (getIntent().hasExtra("checkout"))
                            verifyCardDetails();
                        else
                            verifyDetails();
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void verifyCardDetails() {
        if (TextUtils.isEmpty(edNameOnCard.getText().toString().trim()))
            showAlert(llInner, getString(R.string.error_name));
        else if (!validateCardNumber())
            showAlert(llInner, getString(R.string.error_valid_card_no));
        else if (TextUtils.isEmpty(tvExpiryMonth.getText().toString().trim()))
            showAlert(llInner, getString(R.string.error_month));
        else if (TextUtils.isEmpty(tvExpiryYear.getText().toString().trim()))
            showAlert(llInner, getString(R.string.error_year));
        else if (!validateExpiryDate(tvExpiryMonth.getText().toString() + " / " + tvExpiryYear.getText().toString().substring(2, 4)))
            showAlert(llInner, "Invalid expiry date");
        else if (TextUtils.isEmpty(tvExpiryYear.getText().toString().trim()))
            showAlert(llInner, "Invalid CVV Number");
        else {
            String cardNo = "";
            try {
                CryptLib cryptLib = new CryptLib();
                cardNo = cryptLib.encryptSimple(edCardNo.getText().toString().trim(), Constants.KEY, Constants.IV);
                Log.e("Ency = ", cardNo);
                Log.e("DEcy = ", cryptLib.decryptSimple(cardNo, Constants.KEY, Constants.IV));
            } catch (Exception e) {
                Log.e("Exce  = ", e + "");
            }
            if (mSaveCard && db.getCard(cardNo.substring(0, cardNo.length() - 1)) != 0) {
                showAlert(llInner, getString(R.string.already_save_card));
            } else {
                if (connectedToInternet()) {
                    final Card mStripeCard = new Card(edCardNo.getText().toString().trim().replaceAll(" ", ""), Integer.parseInt(tvExpiryMonth.getText().toString()),
                            Integer.parseInt(tvExpiryYear.getText().toString()), edCvvNo.getText().toString());
                    if (!mStripeCard.validateCVC()) {
                        showAlert(llInner, "Invalid cvv number");
                    } else if (!mStripeCard.validateCard()) {
                        showAlert(llInner, "Invalid card");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.are_your_sure_want_make_payment)
                                .setCancelable(false)
                                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        hitStripeAPI(mStripeCard);
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
                } else {
                    showInternetAlert(llInner);
                }
            }
        }
    }

    private void hitBookAPI(String token) {
        String deliverydate, returndate;
        deliverydate = mSelectedDates.get(0);
        if (mSelectedDates.size() > 0)
            returndate = mSelectedDates.get(mSelectedDates.size() - 1);
        else
            returndate = mSelectedDates.get(0);

        StringBuilder mBuilder = new StringBuilder();
        for (String days : mSelectedDates) {
            mBuilder.append(days + ",");
        }
        String selectedDates = mBuilder.toString().substring(0, mBuilder.toString().length() - 1);

        String savecard;
        if (mSaveCard)
            savecard = "1";
        else
            savecard = "0";

        String deliverytype;
        if (getIntent().getIntExtra("delivery_mode", 0) == 1)
            deliverytype = "2";
        else
            deliverytype = "1";

        String cardNo = "";
        try {
            CryptLib cryptLib = new CryptLib();
            cardNo = cryptLib.encryptSimple(edCardNo.getText().toString().trim(), Constants.KEY, Constants.IV);
        } catch (Exception e) {
            Log.e("Exce  = ", e + "");
        }

        JsonObject additionalPrice = new JsonObject();
        additionalPrice.addProperty("security_charges", additionalPriceBean.getSecurity_charges());
        additionalPrice.addProperty("fee", additionalPriceBean.getFee());

        Call<BookTulModel> call = RetrofitClient.getInstance().bookATul(utils.getString("access_token", ""),
                mViewTulModel.getId(), additionalPrice.toString(), mViewTulModel.getPrice(), deliverydate, returndate, additionalPriceBean.getSecurity_charges(),
                String.valueOf(getIntent().getDoubleExtra("tul_price", 0)), getIntent().getStringExtra("address"), getIntent().getStringExtra("latitude"),
                getIntent().getStringExtra("longitude"), selectedDates, token, deliverytype, getIntent().getStringExtra("delivery_charges"),
                getIntent().getStringExtra("quantity"), getIntent().getStringExtra("extra_charges"), savecard, cardNo,
                edNameOnCard.getText().toString().trim(), tvExpiryMonth.getText().toString(),
                tvExpiryYear.getText().toString(), mGson.toJson(mViewTulModel.getPreferences()),
                mViewTulModel.getQuantity(), mViewTulModel.getAddress(),
                getIntent().getStringExtra("discount_price"), getIntent().getStringExtra("base_price"),
                getIntent().getStringExtra("transaction_fee"), getIntent().getStringExtra("transaction_percentage"),
                getIntent().getIntExtra("extra_fee", 0), getIntent().getStringExtra("discount_percentage"), getIntent().getStringExtra("discount"));
        call.enqueue(new Callback<BookTulModel>() {
            @Override
            public void onResponse(Call<BookTulModel> call, Response<BookTulModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    if (mSaveCard)
                        db.addCard(response.body().getCard_details());

                    db.addMyBookingByAddCard(response.body().getResponse());
                    Intent inSplash = new Intent(mContext, ActiveBookingActivity.class);
                    inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(inSplash);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(getString(R.string.pause_error))) {
                        alertPauseDialog("Booking Paused", response.body().error.getMessage());
                    } else if (response.body().error.getCode().equals(getString(R.string.edit_error))) {
                        alertPauseDialog("Details Changed", response.body().error.getMessage());
                    } else if (response.body().error.getCode().equals(getString(R.string.payment_exceed_code))) {
                        alertPaymentDialog(response.body().error.getMessage());
                    } else {
                        showAlert(llInner, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BookTulModel> call, Throwable t) {
                showAlert(llInner, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

    private void hitStripeAPI(Card mStripeCard) {
        showProgress();
        Stripe stripe = null;
        try {
            stripe = new Stripe(Constants.STRIPE_KEY);
            stripe.createToken(mStripeCard, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    showAlert(llInner, error.getLocalizedMessage());
                    hideProgress();
                }

                @Override
                public void onSuccess(Token token) {
                    Log.e("stripe token", "is " + token);
                    hitBookAPI(token.getId());
                }
            });
        } catch (AuthenticationException e) {
            showAlert(llInner, e.getLocalizedMessage());
        }
    }

    public void verifyDetails() {
        if (TextUtils.isEmpty(edNameOnCard.getText().toString().trim()))
            showAlert(llInner, getString(R.string.error_name));
        else if (!validateCardNumber())
            showAlert(llInner, getString(R.string.error_valid_card_no));
        else if (TextUtils.isEmpty(tvExpiryMonth.getText().toString().trim()))
            showAlert(llInner, getString(R.string.error_month));
        else if (TextUtils.isEmpty(tvExpiryYear.getText().toString().trim()))
            showAlert(llInner, getString(R.string.error_year));
        else if (!validateExpiryDate(tvExpiryMonth.getText().toString() + " / " + tvExpiryYear.getText().toString().substring(2, 4))) {
            showAlert(llInner, getString(R.string.invalid_expiry_date));
        } else {
            if (connectedToInternet()) {
                hitAPI();
            } else {
                showInternetAlert(llInner);
            }
        }
    }


    void selectOptions(ArrayList<String> mData, int intentType, String selected) {
        Intent in = new Intent(mContext, OptionSelection.class);
        in.putExtra("selected", selected);
        in.putStringArrayListExtra("data", mData);
        startActivityForResult(in, intentType);
    }

    private ArrayList<String> getMonth() {
        ArrayList<String> month = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                month.add("0" + i);
            } else {
                month.add(String.valueOf(i));
            }
        }
        return month;
    }

    private ArrayList<String> getYear() {
        int intYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> year = new ArrayList<>();
        for (int i = intYear; i < intYear + 50; i++) {
            year.add(String.valueOf(i));
        }
        return year;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case MONTH:
                    tvExpiryMonth.setText(data.getStringExtra("selected_data"));
                    break;
                case YEAR:
                    tvExpiryYear.setText(data.getStringExtra("selected_data"));
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void hitAPI() {
        showProgress();
        String cardNo = "";
        try {
            CryptLib cryptLib = new CryptLib();
            cardNo = cryptLib.encryptSimple(edCardNo.getText().toString().trim(), Constants.KEY, Constants.IV);

            Log.e("Ency = ", cardNo);
            Log.e("DEcy = ", cryptLib.decryptSimple(cardNo, Constants.KEY, Constants.IV));

        } catch (Exception e) {
            Log.e("Exce  = ", e + "");
        }
        Call<CardLocalModel> call = RetrofitClient.getInstance().saveCard(utils.getString("access_token", ""),
                cardNo, edNameOnCard.getText().toString().trim(),
                Integer.parseInt(String.valueOf(tvExpiryMonth.getText())),
                Integer.parseInt(String.valueOf(tvExpiryYear.getText())));
        call.enqueue(new Callback<CardLocalModel>() {
            @Override
            public void onResponse(Call<CardLocalModel> call, Response<CardLocalModel> response) {
                if (response.body().getResponse() != null) {
                    db.addCard(response.body().getResponse());
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        showAlert(llInner, response.body().error.getMessage());
                    } else {
                        showAlert(llInner, response.body().error.getMessage());
                    }
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<CardLocalModel> call, Throwable t) {
                showAlert(llInner, t.getMessage());
                hideProgress();
            }
        });
    }

    boolean validateCardNumber() { // length check to 20 because spaces are also included
        String mNumber = (edCardNo.getText().toString()).replaceAll(" ", "");
        Pattern pattern = Pattern.compile("^[0-9]{12,19}$");
        Matcher matcher = pattern.matcher(mNumber);
        if (matcher.matches()) {
            return validateCardNumber_LuhnAlgo(mNumber);
        } else {
            return false;
        }
    }

    boolean validateCardNumber_LuhnAlgo(String mCardNumber) {
        char[] digitsChar = mCardNumber.toCharArray();
        int total = 0, flag = 0, digit;
        for (int i = digitsChar.length - 1; i >= 0; i--) {
            flag += 1;
            digit = Integer.parseInt("" + digitsChar[i]);
            if (flag % 2 == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = digit - 9;
                }
            }
            total = total + digit;
        }

        if (total % 10 == 0) {
            return true;
        } else {
            return false;
        }
    }

    boolean validateExpiryDate(String expiryDate) {
        if (expiryDate.length() > 3) { //is expiry field even filled once
            String[] startEnd = expiryDate.split(" / ");
            if (startEnd.length == 2) { //both month and year are filled
                Pattern pattern = Pattern.compile("^[0-9]{2}$");
                for (int i = 0; i < startEnd.length; i++) {
                    Matcher matcher = pattern.matcher(startEnd[i]);
                    if (!matcher.matches()) {
                        return false;
                    }
                    if (i == 0) { //month should not be less than / equal to 0 or greater than 12
                        if (Integer.parseInt(startEnd[0]) <= 0 ||
                                Integer.parseInt(startEnd[0]) > 12) {
                            return false;
                        }
                    }
                }

                Calendar currentCal = Calendar.getInstance();
                Log.e("current month", "is " + (currentCal.get(Calendar.MONTH)));
                if (Integer.parseInt("20" + startEnd[1]) > currentCal.get(Calendar.YEAR)) { // if year is greater than current year. all cool
                    return true;
                } else if (Integer.parseInt("20" + startEnd[1]) == currentCal.get(Calendar.YEAR)) { //if year is same as my current year, please check month
                    if (Integer.parseInt(startEnd[0]) >= (currentCal.get(Calendar.MONTH) + 1)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void alertPauseDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alertPaymentDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Exceed").setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
