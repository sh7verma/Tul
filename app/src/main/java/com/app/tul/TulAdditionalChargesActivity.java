package com.app.tul;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
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
import model.ViewTulModel;
import utils.Constants;

public class TulAdditionalChargesActivity extends BaseActivity {

    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.txt_bank_detail)
    TextView txtBankDetail;

    @BindView(R.id.tv_tul_security_charges)
    TextView tvSecurityCharges;
    @BindView(R.id.tv_tul_security_charges_no)
    TextView tvSecurityChargesNo;

    @BindView(R.id.ll_extra_earning)
    LinearLayout llExtraEarning;
    @BindView(R.id.tv_extra_earning_hint)
    TextView tvExtraEarningHint;
    @BindView(R.id.tv_extra_earning_value)
    TextView tvExtraEarningValue;
    @BindView(R.id.img_your_earning_info)
    ImageView imgYourEarningInfo;

    @BindView(R.id.tv_tul_convenience_fee)
    TextView tvTulConvenienceFee;

    @BindView(R.id.tv_tul_convenience_fee_no)
    TextView tvTulConvenienceFeeNo;

    @BindView(R.id.txt_hint_charges)
    TextView txtHintCharges;

    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    ViewTulModel.ResponseBean.AdditionalPriceBean mViewTulAdditional;

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_additional_charges;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.additional_prices);

        tvSecurityCharges.setPadding(mWidth / 20, mHeight / 25, 0, mHeight / 25);
        tvSecurityCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvSecurityChargesNo.setPadding(0, mHeight / 25, mWidth / 20, mHeight / 25);
        tvSecurityChargesNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvExtraEarningHint.setPadding(mWidth / 20, mHeight / 25, 0, mHeight / 25);
        tvExtraEarningHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvExtraEarningValue.setPadding(0, mHeight / 25, 0, mHeight / 25);
        tvExtraEarningValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        txtBankDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtBankDetail.setPadding(mWidth / 24, mWidth / 32, mWidth / 24, mHeight / 24);

        tvTulConvenienceFee.setPadding(mWidth / 20, mHeight / 25, 0, mHeight / 25);
        tvTulConvenienceFee.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvTulConvenienceFeeNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        tvTulConvenienceFeeNo.setPadding(0, mHeight / 25, mWidth / 20, mHeight / 25);

        txtHintCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtHintCharges.setPadding(mWidth / 20, mWidth / 64, mWidth / 20, mWidth / 24);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.setMargins(mWidth / 20, 0, mWidth / 20, 0);
        view1.setLayoutParams(layoutParams);
        view2.setLayoutParams(layoutParams);
        view3.setLayoutParams(layoutParams);
    }

    @Override
    protected void onCreateStuff() {
        mViewTulAdditional = getIntent().getParcelableExtra("data");
        if (getIntent().getIntExtra("user_id", 0) == Integer.parseInt(utils.getString("user_id", ""))) {
            txtHintCharges.setVisibility(View.GONE);
            llExtraEarning.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
        }
        setData();
    }

    private void setData() {
        tvSecurityChargesNo.setText(mViewTulAdditional.getAdd_currency() + " " + mViewTulAdditional.getSecurity_charges());
        if (!TextUtils.isEmpty(mViewTulAdditional.getFee()) && !mViewTulAdditional.getFee().equals("0.00")) {
            tvTulConvenienceFeeNo.setText(mViewTulAdditional.getAdd_currency() + " " + mViewTulAdditional.getFee());
            tvExtraEarningValue.setText(mViewTulAdditional.getAdd_currency() + " " +
                    getEarningAmount(mViewTulAdditional.getFee(), getIntent().getStringExtra("transaction_percentage")));
        } else {
            tvTulConvenienceFeeNo.setText(mViewTulAdditional.getAdd_currency() + " 0.00");
            tvExtraEarningValue.setText(mViewTulAdditional.getAdd_currency() + " 0.00");
        }
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        imgYourEarningInfo.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TulAdditionalChargesActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.img_your_earning_info:
                if (!TextUtils.isEmpty(mViewTulAdditional.getFee())) {
                    PopupWindow popupwindow_obj = showEarningPopup(mViewTulAdditional.getFee(),
                            getIntent().getStringExtra("transaction_percentage"));
                    popupwindow_obj.showAsDropDown(imgYourEarningInfo, 5, -55);
                }
                break;

        }
    }

    private String getEarningAmount(String price, String transactionPercentage) {
        double i = Double.parseDouble(price);
        i = i - ((Double.parseDouble(transactionPercentage) * i) / 100);
        return amountConversion(String.format("%.10f", i));
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

    private PopupWindow showEarningPopup(String transaction_percentage, String price) {
        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.info_dialog_layout, null);

        LinearLayout.LayoutParams arrowParms = new LinearLayout.LayoutParams((int) (getResources().getDimension(R.dimen._15sdp)), (int) (getResources().getDimension(R.dimen._15sdp)));
        arrowParms.setMargins(0, 0, mWidth / 64, 0);
        arrowParms.gravity = Gravity.RIGHT;
        TextView txtArrow = (TextView) view.findViewById(R.id.txt_arrow1);
        txtArrow.setLayoutParams(arrowParms);

        TextView txtInfo = (TextView) view.findViewById(R.id.txt_info);
        txtInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        txtInfo.setText("This is the extra amount you will earn after deducting extra fee of " + transaction_percentage + "% from your "
               + utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,"")) + price +
                "  listings.");
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        return popupWindow;
    }


}
