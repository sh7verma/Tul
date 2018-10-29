package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import customviews.MaterialEditText;
import utils.Constants;


public class AdditionalPricesActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.txt_hint_msg)
    TextView txtHintMsg;

    @BindView(R.id.ed_security)
    MaterialEditText edSecurity;
    @BindView(R.id.ed_convience)
    MaterialEditText edConvience;
    @BindView(R.id.ed_extra_earning)
    MaterialEditText edExtraEarning;
    @BindView(R.id.img_extra_earning_info)
    ImageView imgExtraEarningInfo;

    @BindView(R.id.txt_hint_charges)
    TextView txtHintCharges;

    @BindView(R.id.txt_done)
    TextView txtDone;

    double mTransactionPercentage = 0;
    private boolean mPopupEnable;

    @Override
    protected int getContentView() {
        return R.layout.activity_additional_prices;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtToolbarTitle.setText(getResources().getString(R.string.add_additional_prices));

        llMain.setPadding(mWidth / 32, 0, mWidth / 32, mWidth / 32);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");

        txtHintMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtHintMsg.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        edSecurity.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edSecurity.setTypeface(typeface);

        edConvience.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edConvience.setTypeface(typeface);

        edExtraEarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edExtraEarning.setTypeface(typeface);

        LinearLayout.LayoutParams doneParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        doneParms.setMargins(mWidth / 32, 0, mWidth / 32, mWidth / 32);
        txtDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtDone.setPadding(mWidth / 32, mWidth / 24, mWidth / 32, mWidth / 24);
        txtDone.setLayoutParams(doneParms);

        txtHintCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtHintCharges.setPadding(0, mWidth / 64, 0, mWidth / 24);

    }

    private void setData() {
        edSecurity.setText(getIntent().getStringExtra("security_price"));
        edSecurity.setSelection(edSecurity.getText().toString().length());

        edConvience.setText(getIntent().getStringExtra("convience_price"));
        edConvience.setSelection(edConvience.getText().toString().length());

        if (!TextUtils.isEmpty(edConvience.getText().toString())) {
            double i = Double.parseDouble(String.valueOf(edConvience.getText().toString()));
            i = i - ((mTransactionPercentage * i) / 100);
            edExtraEarning.setText(amountConversion(String.format("%.10f", i)));
        }
    }

    @Override
    protected void onCreateStuff() {

        mTransactionPercentage = Double.parseDouble(getIntent().getStringExtra("transaction_percentage"));

//        final Drawable img = ContextCompat.getDrawable(mContext, R.drawable.pound);
//        img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));

        final Drawable img;
        if (utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,"")).equals(Constants.POUND)) {
            img = ContextCompat.getDrawable(mContext, R.drawable.pound);
        } else {
            img = ContextCompat.getDrawable(mContext, R.drawable.euro);
        }

        img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));

        edConvience.setCompoundDrawables(img, null, null, null);
        edSecurity.setCompoundDrawables(img, null, null, null);
        edExtraEarning.setCompoundDrawables(img, null, null, null);

        edConvience.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().startsWith(".")) {
                    s = "0.";
                    edConvience.setText(s);
                    edConvience.setSelection(s.length());
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

        setData();
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


    @Override
    protected void initListener() {
        txtDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgExtraEarningInfo.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return AdditionalPricesActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_extra_earning_info:
                if (mPopupEnable) {
                    PopupWindow popupwindow_obj = showEarningPopup(edConvience.getText().toString().trim(),
                            getIntent().getStringExtra("transaction_percentage"));
                    popupwindow_obj.showAsDropDown(imgExtraEarningInfo, 4, -55);
                }
                break;
            case R.id.txt_done:
                if (TextUtils.isEmpty(edSecurity.getText().toString().trim()) || edSecurity.getText().toString().equals(".")) {
                    showAlert(llMain, getString(R.string.security_fee_error));
                } else if (Double.parseDouble(edSecurity.getText().toString().trim()) < Constants.MIN_PRICE) {
                    showAlert(llMain, "Security deposit cannot be less than " + utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,"")) + " 0.1");
                } else if (Double.parseDouble(edSecurity.getText().toString().trim()) > Constants.MAX_SECURITY_CHARGES) {
                    showAlert(llMain, getString(R.string.error_max_security_price));
                } else if (edConvience.getText().toString().trim().length() > 0) {
                    if (edConvience.getText().toString().equals("."))
                        showAlert(llMain, getString(R.string.extra_fee_error));
                    else {
                        if ((Double.parseDouble(edConvience.getText().toString()) < Constants.MIN_PRICE)) {
                            showAlert(llMain, "Extra fee cannot be less than " + utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,"")) + " 0.1");
                        }
                        if ((Double.parseDouble(edConvience.getText().toString()) > Constants.MAX_PRICE)) {
                            showAlert(llMain, getString(R.string.error_max_fee));
                        } else {
                            Constants.closeKeyboard(mContext, llMain);
                            Intent intent = new Intent();
                            intent.putExtra("security_fee", edSecurity.getText().toString().trim());
                            intent.putExtra("convience_fee", edConvience.getText().toString().trim());
                            setResult(RESULT_OK, intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        }
                    }
                } else {
                    Constants.closeKeyboard(mContext, llMain);
                    Intent intent = new Intent();
                    intent.putExtra("security_fee", edSecurity.getText().toString().trim());
                    intent.putExtra("convience_fee", edConvience.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
                break;
            case R.id.img_back:
                Constants.closeKeyboard(mContext, llMain);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }
    }

    private PopupWindow showEarningPopup(String price, String transaction_percentage) {
        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.info_dialog_layout, null);

        LinearLayout.LayoutParams arrowParms = new LinearLayout.LayoutParams((int) (getResources().getDimension(R.dimen._15sdp)), (int) (getResources().getDimension(R.dimen._15sdp)));
        arrowParms.setMargins(0, 0, mWidth / 30, 0);
        arrowParms.gravity= Gravity.RIGHT;
        TextView txtArrow = (TextView) view.findViewById(R.id.txt_arrow1);
        txtArrow.setLayoutParams(arrowParms);

        TextView txtInfo = (TextView) view.findViewById(R.id.txt_info);
        txtInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        txtInfo.setText("This is the amount you will earn after deducting TUL fees of " + transaction_percentage + "% from your "
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
