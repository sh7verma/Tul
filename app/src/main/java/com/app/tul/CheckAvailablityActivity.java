package com.app.tul;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import api.RetrofitClient;
import butterknife.BindView;
import model.AvailbiltyModel;
import model.DemoModel;
import model.ViewTulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

/**
 * Created by applify on 11/14/2017.
 */

public class CheckAvailablityActivity extends BaseActivity implements OnDateSelectedListener, OnRangeSelectedListener, OnMonthChangedListener {


    private static final int PAUSED = 1;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.txt_time)
    TextView txtTime;

    @BindView(R.id.ll_type)
    LinearLayout llType;

    @BindView(R.id.ll_pickup)
    LinearLayout llPickup;
    @BindView(R.id.img_pickup)
    ImageView imgPickup;
    @BindView(R.id.txt_select_type)
    TextView txtSelectType;
    @BindView(R.id.txt_pickup)
    TextView txtPickup;
    @BindView(R.id.txt_address_pickup)
    TextView txtAddressPickup;

    @BindView(R.id.ll_deleiver)
    LinearLayout llDeleiver;
    @BindView(R.id.ll_deliver_address)
    LinearLayout llDeliverAddress;
    @BindView(R.id.img_deleiver)
    ImageView imgDeleiver;
    @BindView(R.id.txt_deliver)
    TextView txtDeliver;
    @BindView(R.id.txt_address_deleivery)
    TextView txtAddressDeleivery;

    @BindView(R.id.ll_no_tuls)
    LinearLayout llNoTuls;
    @BindView(R.id.ed_no_tuls)
    EditText edNoTuls;

    @BindView(R.id.ll_cost)
    LinearLayout llCost;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_book)
    TextView tvBook;

    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;

    boolean isRangeEnabled, isDateSelected;
    ViewTulModel.ResponseBean.PreferencesBean prefrencesModel;
    ViewTulModel.ResponseBean.AdditionalPriceBean mViewTulAdditional;
    ViewTulModel.ResponseBean mViewTulModel;
    int mDayCount = 1, mCount = 1, mDeliveryMode;
    double mTotalPrice = 1.00, mPrice;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private ArrayList<String> mDatesArray = new ArrayList<>();
    private ArrayList<String> mDaysArray = new ArrayList<>();
    private ArrayList<String> mSelectedDays = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_check_availbilty;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText("Select Date");

        txtTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        llType.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 32);

        txtSelectType.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtSelectType.setPadding(mWidth / 64, 0, 0, mHeight / 64);

        txtPickup.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtPickup.setPadding(mWidth / 32, 0, 0, 0);

        txtAddressPickup.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtAddressPickup.setPadding(mWidth / 32, 0, 0, mHeight / 64);

        txtDeliver.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtDeliver.setPadding(mWidth / 32, 0, 0, 0);

        txtAddressDeleivery.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtAddressDeleivery.setPadding(mWidth / 32, 0, 0, 0);

        ///calendar views...
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnRangeSelectedListener(this);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        calendarView.setOnMonthChangedListener(this);
        calendarView.setTileSize(mWidth / 7);

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendarView.state().edit().setMinimumDate(calendar.getTime()).commit();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 99);
        calendarView.state().edit().setMaximumDate(calendar.getTime()).commit();
        ////----- End

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvTotal.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvBook.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvBook.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        llNoTuls.setPadding(mWidth / 32, mHeight / 16, mWidth / 32, mHeight / 16);

        edNoTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        edNoTuls.setPadding(0, mHeight / 64, 0, mHeight / 64);
        edNoTuls.setTypeface(typefaceRegular);

        llCost.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);
    }

    @Override
    protected void onCreateStuff() {
        mViewTulModel = getIntent().getParcelableExtra("tul_data");
        prefrencesModel = mViewTulModel.getPreferences();
        mViewTulAdditional = mViewTulModel.getAdditional_price();
        mDaysArray.clear();
        if (prefrencesModel.getAvailable().equals("Only Weekends")) {
            mDaysArray.add("Saturday");
            mDaysArray.add("Sunday");
        } else if (prefrencesModel.getAvailable().equals("Weekdays")) {
            mDaysArray.add("Monday");
            mDaysArray.add("Tuesday");
            mDaysArray.add("Wednesday");
            mDaysArray.add("Thursday");
            mDaysArray.add("Friday");
        }

        mPrice = Double.parseDouble(mViewTulModel.getPrice());
        setData();

        edNoTuls.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    String ss = s.toString();
                    mCount = Integer.parseInt(ss);
                    mTotalPrice = mCount * mPrice * mDayCount;
                    tvPrice.setText(mViewTulModel.getCurrency() + " " + String.format("%.2f", mTotalPrice));
                } else {
                    tvPrice.setText(mViewTulModel.getCurrency()+ " 0.0");
                    mTotalPrice = 1;
                    mCount = 0;
                }

                tvTotal.setText(" Total");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (connectedToInternet())
            hitAPI();
        else
            showInternetAlert(llCost);
    }

    private void hitAPI() {

        showProgress();
        Call<AvailbiltyModel> call = RetrofitClient.getInstance().checkAvailbilty(utils.getString("access_token", ""),
                mViewTulModel.getId());
        call.enqueue(new Callback<AvailbiltyModel>() {
            @Override
            public void onResponse(Call<AvailbiltyModel> call, Response<AvailbiltyModel> response) {
                if (response.body().getResponse() != null) {
                    mDatesArray.clear();
                    mDatesArray.addAll(response.body().getResponse());
                    calendarView.addDecorator(new PrimeDayDisableDecorator());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(getString(R.string.pause_error))) {
                        alertPauseDialog(response.body().error.getMessage());
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {
                        userBlockDialogUser(mContext);
                    } else {
                        showAlert(llCost, response.body().error.getMessage());
                    }
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<AvailbiltyModel> call, Throwable t) {
                showAlert(llCost, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

    private void setData() {
        txtTime.setText("Start Time " + prefrencesModel.getStart_time() + " to "
                + prefrencesModel.getEnd_time());
        txtAddressPickup.setText("(" + mViewTulModel.getAddress() + ")");
        tvPrice.setText(mViewTulModel.getCurrency() + " " + String.format("%.2f", Double.parseDouble(mViewTulModel.getPrice())));
        tvTotal.setText(" per day");

        if (prefrencesModel.getTull_delivery().equals("1")) {
            llDeleiver.setVisibility(View.VISIBLE);
            llDeliverAddress.setVisibility(View.VISIBLE);
        } else {
            llDeleiver.setVisibility(View.GONE);
            llDeliverAddress.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        tvBook.setOnClickListener(this);
        llDeleiver.setOnClickListener(this);
        llPickup.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return CheckAvailablityActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_book:
                if (mSelectedDays.size() == 0)
                    showAlert(llCost, "Please select at least one date");
                else if (TextUtils.isEmpty(edNoTuls.getText().toString().trim()) ||
                        Integer.parseInt(edNoTuls.getText().toString().trim()) == 0)
                    showAlert(llCost, "Please enter valid no. of TULs");
                else {
                    if (connectedToInternet())
                        hitAvailbiltyAPI();
                    else
                        showInternetAlert(llCost);
                }
                break;
            case R.id.ll_deleiver:
                imgDeleiver.setImageResource(R.mipmap.ic_radio_btn_s);
                imgPickup.setImageResource(R.mipmap.ic_radio_btn);
                mDeliveryMode = 2;
                break;
            case R.id.ll_pickup:
                imgDeleiver.setImageResource(R.mipmap.ic_radio_btn);
                imgPickup.setImageResource(R.mipmap.ic_radio_btn_s);
                mDeliveryMode = 1;
                break;
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    private void hitAvailbiltyAPI() {
        showProgress();
        StringBuilder mBuilder = new StringBuilder();
        for (String days : mSelectedDays) {
            mBuilder.append(days + ",");
        }
        mDayCount = mSelectedDays.size();
        String selectedDays = mBuilder.toString().substring(0, mBuilder.toString().length() - 1);
        Call<DemoModel> call = RetrofitClient.getInstance().checkAvailbiltyByDate(utils.getString("access_token", ""),
                mViewTulModel.getId(), selectedDays, Integer.parseInt(edNoTuls.getText().toString()));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    Intent intent = new Intent(mContext, TulCheckOutDetailsActivity.class);
                    intent.putExtra("selected_dates", mSelectedDays);
                    intent.putExtra("quantity", edNoTuls.getText().toString());
                    intent.putExtra("delivery_mode", mDeliveryMode);
                    intent.putExtra("tul_price", mTotalPrice);
                    intent.putExtra("tool_title", mViewTulModel.getTitle());
                    intent.putExtra("latitude", mViewTulModel.getLatitude());
                    intent.putExtra("longitude", mViewTulModel.getLongitude());
                    intent.putExtra("tul_data", mViewTulModel);
                    intent.putExtra("data_price", mViewTulModel.getAdditional_price());
                    if (mDeliveryMode == 2)
                        intent.putExtra("delivery_charges", prefrencesModel.getDelivery_charges());
                    else
                        intent.putExtra("delivery_charges", "0");

                    if (mDayCount >= mViewTulModel.getDiscount_days())
                        intent.putExtra("show_discount", 1);
                    else
                        intent.putExtra("show_discount", 0);

                    startActivityForResult(intent, PAUSED);
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(getString(R.string.pause_error))) {
                        alertPauseDialog(response.body().error.getMessage());
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {

                        userBlockDialogUser(mContext);
                    } else {
                        showAlert(llCost, response.body().error.getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
            }
        });
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.getDate());

        if (!isDateSelected) {
            mSelectedDays.clear();
            mSelectedDays.add(dateFormat.format(calendar.getTime()));
            isDateSelected = true;
        } else {
            isDateSelected = false;
            mSelectedDays.clear();
        }
        if (isRangeEnabled) {
            isRangeEnabled = false;
            widget.clearSelection();
            widget.setSelectedDate(date);
        }

        mTotalPrice = mPrice;
        mDayCount = 1;
        mTotalPrice = mDayCount * mPrice * mCount;
        tvPrice.setText(mViewTulModel.getCurrency()+ " " + String.format("%.2f", mTotalPrice));
        tvTotal.setText(" Total");
    }

    @Override
    public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
        if (!isRangeEnabled) {
            isRangeEnabled = true;
            mDayCount = dates.size();
            mTotalPrice = mDayCount * mPrice * mCount;
            tvPrice.setText(mViewTulModel.getCurrency() + " " + String.format("%.2f", mTotalPrice));
            tvTotal.setText(" Total");
        }
        isDateSelected = false;
        mSelectedDays.clear();
        for (CalendarDay dayItem : dates) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dayItem.getDate());
            mSelectedDays.add(dateFormat.format(calendar.getTime()));
        }

    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        widget.getCalendarContentDescription();
    }

    private void alertPauseDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Booking Paused").setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        moveBack();
        super.onBackPressed();
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PAUSED) {
                /// booking paused
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class PrimeDayDisableDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(day.getDate());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            if (mDatesArray.contains(dateFormat.format(calendar.getTime()))) {
                return true;
            } else {
                if (mDaysArray.size() > 0) {
                    if (mDaysArray.contains(dayFormat.format(calendar.getTime()))) {
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }

    }
}
