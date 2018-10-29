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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import model.ViewTulModel;
import utils.Constants;

public class TulViewPreferencesActivity extends BaseActivity {

    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.tv_preferences_available_hint)
    TextView tvPreferencesAvailableHint;
    @BindView(R.id.tv_preferences_available)
    TextView tvPreferencesAvailable;

    @BindView(R.id.ll_inner)
    LinearLayout llInner;
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.tv_preferences_start_time_hint)
    TextView tvPreferencesStartTimeHint;
    @BindView(R.id.tv_preferences_start_time)
    TextView tvPreferencesStartTime;

    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.tv_preferences_end_time_hint)
    TextView tvPreferencesEndTimeHint;
    @BindView(R.id.tv_preferences_end_time)
    TextView tvPreferencesEndTime;

    @BindView(R.id.tv_tul_available_delivery)
    TextView tvTulAvailableDelivery;

    @BindView(R.id.txt_delivery_pickup_time_label)
    TextView txtDeliveryPickupTimeHint;

    @BindView(R.id.tv_delivery_charges_hint)
    TextView tvDeliveryChargesHint;
    @BindView(R.id.tv_delivery_charges)
    TextView tvDeliveryCharges;
    @BindView(R.id.img_your_earning_info)
    ImageView imgYourEarningInfo;

    @BindView(R.id.ll_delivery_earning)
    LinearLayout llDeliveryEarning;
    @BindView(R.id.tv_delivery_earning_hint)
    TextView tvDeliveryEarningHint;
    @BindView(R.id.tv_delivery_earning)
    TextView tvDeliveryEarning;

    @BindView(R.id.txt_delivery_pickup_start_time)
    TextView txtDeliveryPickupStartTime;
    @BindView(R.id.txt_delivery_pickup_end_time)
    TextView txtDeliveryPickupEndTime;

    @BindView(R.id.ll_available)
    LinearLayout llAvailable;
    @BindView(R.id.ll_time)
    LinearLayout llTime;

    @BindView(R.id.txt_hint_charges)
    TextView txtHintCharges;

    @BindView(R.id.ll_pickup_time)
    LinearLayout llPickupTime;
    @BindView(R.id.ll_time_left)
    LinearLayout llTimeLeft;
    @BindView(R.id.ll_time_right)
    LinearLayout llTimeRight;
    @BindView(R.id.view1)
    LinearLayout view1;
    @BindView(R.id.view3)
    LinearLayout view3;
    @BindView(R.id.view4)
    LinearLayout view4;
    @BindView(R.id.rl_preferences_main_container)
    RelativeLayout rlMainContainer;
    @BindView(R.id.view3_1)
    View view3_1;
    ViewTulModel.ResponseBean.PreferencesBean prefrencesModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_view_preferences;
    }

    @Override
    protected void initUI() {
        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.prefrences);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(mWidth / 80, 0, 0, 0);
        param.addRule(RelativeLayout.CENTER_VERTICAL);
        imgBckImg.setLayoutParams(param);

        llInner.setPadding(mWidth / 24, 0, mWidth / 24, 0);

        tvPreferencesAvailableHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvPreferencesAvailable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvPreferencesAvailable.setPadding(0, mWidth / 64, 0, 0);

        tvPreferencesStartTimeHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvPreferencesStartTimeHint.setPadding(0, 0, 0, mWidth / 64);

        tvPreferencesStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvPreferencesEndTimeHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvPreferencesEndTimeHint.setPadding(0, 0, 0, mWidth / 64);

        tvPreferencesEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvTulAvailableDelivery.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulAvailableDelivery.setPadding(0, mWidth / 24, 0, mWidth / 24);

        txtDeliveryPickupTimeHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvDeliveryChargesHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvDeliveryCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvDeliveryCharges.setPadding(0, mWidth / 64, 0, mWidth / 64);

        tvDeliveryEarningHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvDeliveryEarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvDeliveryEarning.setPadding(0, mWidth / 64, 0, mWidth / 64);

        txtDeliveryPickupStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtDeliveryPickupStartTime.setPadding(0, mWidth / 64, 0, mWidth / 32);

        txtDeliveryPickupEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtDeliveryPickupEndTime.setPadding(0, mWidth / 64, 0, mWidth / 32);

        llAvailable.setPadding(0, mWidth / 32, 0, mWidth / 32);

        llTime.setPadding(0, mWidth / 32, 0, mWidth / 32);

        llPickupTime.setPadding(0, mWidth / 28, 0, mWidth / 28);

        llTimeLeft.setPadding(0, mWidth / 32, 0, mWidth / 32);

        llTimeRight.setPadding(0, mWidth / 32, 0, mWidth / 32);

        llDeliveryEarning.setPadding(0, mWidth / 32, 0, mWidth / 32);

        txtHintCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtHintCharges.setPadding(0, mWidth / 64, 0, mWidth / 24);


        LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mWidth * 0.004));
        param3.setMargins(mWidth / 62, mWidth / 32, mWidth / 42, 0);
        llTimeLeft.setLayoutParams(param3);

        LinearLayout.LayoutParams param4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mWidth * 0.004));
        param4.setMargins(0, mWidth / 32, mWidth / 42, 0);
        llTimeRight.setLayoutParams(param4);

        setData();

    }

    @Override
    protected void onCreateStuff() {
        if (getIntent().getIntExtra("user_id", 0) == Integer.parseInt(utils.getString("user_id", ""))) {
            txtHintCharges.setText(getString(R.string.delivery_msg));
            llDeliveryEarning.setVisibility(View.GONE);
            view3_1.setVisibility(View.GONE);
        }
    }


    private void setData() {
        prefrencesModel = getIntent().getParcelableExtra("data");
        tvPreferencesAvailable.setText(prefrencesModel.getAvailable());
        tvPreferencesStartTime.setText(prefrencesModel.getStart_time());
        tvPreferencesEndTime.setText(prefrencesModel.getEnd_time());

        if (prefrencesModel.getTull_delivery().equals("0")) {
            tvTulAvailableDelivery.setText(R.string.tull_delivery_not_available);
            hideView();
        } else {
            tvDeliveryCharges.setText(prefrencesModel.getCurrency()+ " " + prefrencesModel.getDelivery_charges());
            tvDeliveryEarning.setText(getEarningAmount(prefrencesModel.getDelivery_charges(), getIntent().getStringExtra("transaction_percentage")));
            txtDeliveryPickupStartTime.setText(prefrencesModel.getDelivery_start_time());
            txtDeliveryPickupEndTime.setText(" - " + prefrencesModel.getDelivery_end_time());
        }
    }


    void hideView() {
        tvDeliveryChargesHint.setVisibility(View.GONE);
        tvDeliveryCharges.setVisibility(View.GONE);
        txtDeliveryPickupTimeHint.setVisibility(View.GONE);
        txtDeliveryPickupStartTime.setVisibility(View.GONE);
        txtDeliveryPickupEndTime.setVisibility(View.GONE);
        llDeliveryEarning.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
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

    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
        imgYourEarningInfo.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TulViewPreferencesActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_your_earning_info:
                if (!TextUtils.isEmpty(prefrencesModel.getDelivery_charges())) {
                    PopupWindow popupwindow_obj = showEarningPopup(getIntent().getStringExtra("transaction_percentage"), prefrencesModel.getDelivery_charges());
                    popupwindow_obj.showAsDropDown(imgYourEarningInfo, 5, -55);
                }
                break;
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
