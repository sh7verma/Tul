package com.app.tul;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import adapters.TulDetailImagesAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import customviews.HeaderView;
import dialogs.ShowMoreDialog;
import me.relex.circleindicator.CircleIndicator;
import model.AttachmentModel;
import model.DemoModel;
import model.ProfileModel;
import model.ViewTulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class TulDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, OnMapReadyCallback {

    private static final int EDIT_TUL = 1;

    @BindView(R.id.cl_main)
    CoordinatorLayout clMain;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_header_view)
    HeaderView toolbarHeaderView;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.vp_tul)
    ViewPager vpTul;
    @BindView(R.id.cp_indicator)
    CircleIndicator cpIndicator;

    @BindView(R.id.tv_tul_name)
    TextView tvTulName;
    @BindView(R.id.tv_tul_price)
    TextView tvTulPrice;
    @BindView(R.id.tv_tul_time_interval)
    TextView tvTulTimeInterval;
    @BindView(R.id.rating_bar_default)
    SimpleRatingBar simpleRatingBar;
    @BindView(R.id.ll_stars)
    LinearLayout llStars;
    @BindView(R.id.img_your_earning_info)
    ImageView imgYourEarningInfo;

    @BindView(R.id.ll_data)
    LinearLayout llData;

    @BindView(R.id.ll_tul_total_main_container)
    LinearLayout llTotalMainContainer;

    @BindView(R.id.pb_load)
    ProgressBar pbLoad;
    @BindView(R.id.ll_total_tuls)
    LinearLayout llTotailTuls;
    @BindView(R.id.tv_total_tuls)
    TextView tvTotalTuls;
    @BindView(R.id.tv_total_tuls_no)
    TextView tvTotalTulsNo;

    @BindView(R.id.ll_booked_tuls)
    LinearLayout llBookedTuls;
    @BindView(R.id.tv_booked_tuls)
    TextView tvBookedTuls;
    @BindView(R.id.tv_booked_tuls_no)
    TextView tvBookedTulNo;

    @BindView(R.id.ll_free_tuls)
    LinearLayout llFreeTuls;
    @BindView(R.id.tv_free_tul)
    TextView tvFreeTul;
    @BindView(R.id.tv_free_tuls_no)
    TextView tvFreeTulNo;

    @BindView(R.id.ll_rented_main)
    LinearLayout llRentedMainContainer;

    @BindView(R.id.ll_rented)
    LinearLayout llRented;
    @BindView(R.id.tv_rented)
    TextView tvRented;
    @BindView(R.id.tv_rented_no)
    TextView tvRentedNo;

    @BindView(R.id.ll_total_earned)
    LinearLayout llEarned;
    @BindView(R.id.tv_total_earned)
    TextView tvTotalEarned;
    @BindView(R.id.tv_total_earned_no)
    TextView tvTotalEarnedNo;

    @BindView(R.id.tv_about_tul_title)
    TextView tvAboutTulTitle;
    @BindView(R.id.tv_about_tul_detail)
    TextView tvAboutTulDetail;

    @BindView(R.id.txt_discount)
    TextView txtDiscount;
    @BindView(R.id.txt_earning)
    TextView txtEarning;

    @BindView(R.id.tv_address_title)
    TextView tvAddressTitle;
    @BindView(R.id.rl_map)
    RelativeLayout rlMap;
    @BindView(R.id.ll_map)
    LinearLayout llMap;

    @BindView(R.id.ll_tul_time)
    LinearLayout llTulTime;

    @BindView(R.id.ll_tul_start_time)
    LinearLayout llTulStartTime;
    @BindView(R.id.tv_tul_start_time)
    TextView tvTulStartTime;
    @BindView(R.id.tv_tul_start_time_no)
    TextView tvTulStartTimeNo;

    @BindView(R.id.ll_tul_end_time)
    LinearLayout llTulEndTime;
    @BindView(R.id.tv_tul_end_time)
    TextView tvTulEndTime;
    @BindView(R.id.tv_tul_end_time_no)
    TextView tvTulEndTimeNo;

    @BindView(R.id.tv_tul_rules)
    TextView tvTulRules;
    @BindView(R.id.tv_tul_additional_prices)
    TextView tvTulAdditionalPrices;
    @BindView(R.id.tv_tul_reviews_rating)
    TextView tvTulReviewsRating;
    @BindView(R.id.tv_tul_cancellation_policies)
    TextView tvTulCancellationPolices;
    @BindView(R.id.tv_tul_preferences)
    TextView tvTulPreferences;
    @BindView(R.id.tv_tul_linked_account)
    TextView tvTulLinkedAccount;
    @BindView(R.id.tv_tul_linked_account2)
    TextView tvTulLinkedAccounthidden;

    @BindView(R.id.ll_pause_booking)
    LinearLayout llPauseBooking;
    @BindView(R.id.tv_pause_booking)
    TextView tvPauseBooking;
    @BindView(R.id.tv_pause)
    TextView tvPause;

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
    @BindView(R.id.view7)
    View view7;
    @BindView(R.id.view8)
    View view8;

    SupportMapFragment mapFragment;
    private boolean isHideToolbarView = false;

    private ProfileModel.ResponseBean.ToolsBean mTulModel;
    private ViewTulModel.ResponseBean mViewTulModel;
    private boolean isEdit;

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_detail;
    }

    @Override
    protected void initUI() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);

        collapsingToolbar.setTitle(" ");

        appBarLayout.addOnOffsetChangedListener(this);

        CollapsingToolbarLayout.LayoutParams imgParms = new CollapsingToolbarLayout.LayoutParams(mWidth, mHeight / 3);
        vpTul.setLayoutParams(imgParms);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        tvTulName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.050));
        tvTulName.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mWidth / 64);

        simpleRatingBar.setStarSize((float) (mWidth * 0.04));
        simpleRatingBar.setPadding(mWidth / 32, 0, 0, mHeight / 32);

        tvTulPrice.setPadding(0, mHeight / 32, 0, 0);
        tvTulPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.050));

        txtDiscount.setPadding(mWidth / 32, 0, 0, mWidth / 32);
        txtDiscount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        txtEarning.setPadding(mWidth / 32, 0, 0, mWidth / 32);
        txtEarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulTimeInterval.setPadding(0, mHeight / 32, 0, 0);
        tvTulTimeInterval.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.030));

        tvTotalTuls.setPadding(mWidth / 32, mHeight / 32, 0, mWidth / 84);
        tvTotalTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvTotalTulsNo.setPadding(mWidth / 32, 0, 0, mHeight / 32);
        tvTotalTulsNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvBookedTuls.setPadding(0, mHeight / 32, 0, mWidth / 84);
        tvBookedTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvBookedTulNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvBookedTulNo.setPadding(0, 0, 0, mHeight / 32);

        tvFreeTul.setPadding(0, mHeight / 32, mWidth / 32, mWidth / 84);
        tvFreeTul.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvFreeTulNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvFreeTulNo.setPadding(0, 0, mWidth / 32, mHeight / 32);

        tvRented.setPadding(mWidth / 32, mHeight / 32, 0, mWidth / 84);
        tvRented.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvRentedNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvRentedNo.setPadding(mWidth / 32, 0, 0, mHeight / 32);

        tvTotalEarned.setPadding(0, mHeight / 32, mWidth / 32, mWidth / 84);
        tvTotalEarned.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvTotalEarnedNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvTotalEarnedNo.setPadding(0, 0, mWidth / 32, mHeight / 32);

        tvAboutTulTitle.setPadding(mWidth / 32, mHeight / 32, 0, mWidth / 64);
        tvAboutTulTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvAboutTulDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvAboutTulDetail.setPadding(mWidth / 32, 0, 0, mHeight / 32);

        tvAddressTitle.setPadding(mWidth / 32, mHeight / 32, 0, mWidth / 64);
        tvAddressTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulStartTime.setPadding(mWidth / 32, mHeight / 32, 0, 0);
        tvTulStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulStartTimeNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvTulStartTimeNo.setPadding(mWidth / 32, 0, 0, mHeight / 32);

        tvTulEndTime.setPadding(0, mHeight / 32, mWidth / 32, 0);
        tvTulEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulEndTimeNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvTulEndTimeNo.setPadding(0, 0, mWidth / 32, mHeight / 32);

        llPauseBooking.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);

        tvTulRules.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulRules.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvTulAdditionalPrices.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);
        tvTulAdditionalPrices.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvTulReviewsRating.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulReviewsRating.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvTulCancellationPolices.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulCancellationPolices.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvTulPreferences.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulPreferences.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvTulLinkedAccounthidden.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.055));
        tvTulLinkedAccounthidden.setPadding(mWidth / 32, mHeight / 20, mWidth / 32, mHeight / 20);

        tvTulLinkedAccount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulLinkedAccount.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        LinearLayout.LayoutParams mapParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight / 4);
        mapParms.setMargins(mWidth / 32, 0, mWidth / 32, 0);
        rlMap.setLayoutParams(mapParms);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.setMargins(mWidth / 32, 0, mWidth / 32, 0);
        view1.setLayoutParams(layoutParams);
        view2.setLayoutParams(layoutParams);
        view3.setLayoutParams(layoutParams);
        view4.setLayoutParams(layoutParams);
        view5.setLayoutParams(layoutParams);
        view6.setLayoutParams(layoutParams);
        view7.setLayoutParams(layoutParams);
        view8.setLayoutParams(layoutParams);

        tvPauseBooking.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvPause.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvPause.setPadding(mWidth / 16, mWidth / 32, mWidth / 16, mWidth / 32);

    }

    @Override
    protected void onCreateStuff() {
        if (getIntent().hasExtra("data")) {
            mTulModel = getIntent().getParcelableExtra("data");
            llData.setVisibility(View.INVISIBLE);
            pbLoad.setVisibility(View.VISIBLE);
            setData();
        } else {
            clMain.setVisibility(View.INVISIBLE);
            pbLoad.setVisibility(View.GONE);
        }

        if (connectedToInternet()) {
            hitAPI();
        } else {
            showInternetAlert(llBookedTuls);
            pbLoad.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        tvTulRules.setOnClickListener(this);
        tvTulAdditionalPrices.setOnClickListener(this);
        tvTulReviewsRating.setOnClickListener(this);
        tvTulCancellationPolices.setOnClickListener(this);
        tvTulPreferences.setOnClickListener(this);
        tvTulLinkedAccount.setOnClickListener(this);
        tvPause.setOnClickListener(this);
        llStars.setOnClickListener(this);
        llPauseBooking.setOnClickListener(this);
        imgYourEarningInfo.setOnClickListener(this);
    }

    private void hitAPI() {
        Call<ViewTulModel> call;
        if (getIntent().hasExtra("tul_id")) {
            showProgress();
            call = RetrofitClient.getInstance().viewTul(utils.getString("access_token", ""),
                    getIntent().getIntExtra("tul_id", 0));
        } else
            call = RetrofitClient.getInstance().viewTul(utils.getString("access_token", ""),
                    mTulModel.getId());
        call.enqueue(new Callback<ViewTulModel>() {
            @Override
            public void onResponse(Call<ViewTulModel> call, Response<ViewTulModel> response) {
                if (response.body().getResponse() != null) {
                    mViewTulModel = response.body().getResponse();
                    if (getIntent().hasExtra("data")) {
                        setTulData(response.body().getResponse());
                        llData.setVisibility(View.VISIBLE);
                        pbLoad.setVisibility(View.GONE);
                    } else {
                        clMain.setVisibility(View.VISIBLE);
                        hideProgress();
                        setFullTulData(response.body().getResponse());
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals("401")) {
                        if (getIntent().hasExtra("tul_id"))
                            hideProgress();

                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        Toast.makeText(mContext, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        showAlert(llBookedTuls, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewTulModel> call, Throwable t) {
                if (getIntent().hasExtra("tul_id"))
                    hideProgress();
                showAlert(llBookedTuls, t.getLocalizedMessage());
            }
        });
    }

    private void setData() {
        toolbarHeaderView.bindTo(mTulModel.getTitle());
        tvTulName.setText(mTulModel.getTitle());
        tvTulPrice.setText(mTulModel.getCurrency()+ " " + getEarningAmount(mTulModel.getPrice(), mTulModel.getTransaction_percentage()));
        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) mTulModel.getAttachment()));
        cpIndicator.setViewPager(vpTul);
    }

    private void setTulData(ViewTulModel.ResponseBean responseBean) {
        if(responseBean.getBank_detail().getAccount_number()==null){
            tvTulLinkedAccount.setVisibility(View.GONE);
        }else {
            tvTulLinkedAccount.setVisibility(View.VISIBLE);
        }


        llPauseBooking.setVisibility(View.VISIBLE);

        if (mViewTulModel.getDiscount_days() != 0) {
            txtDiscount.setVisibility(View.VISIBLE);
            txtDiscount.setText("Discount offered: " + mViewTulModel.getDiscount_percentage() + "% off on  " + mViewTulModel.getDiscount_days() + " days bookings.");
        }

        tvTotalTulsNo.setText(String.valueOf(responseBean.getQuantity()));
        tvBookedTulNo.setText(String.valueOf(responseBean.getActive_bookings()));
        tvFreeTulNo.setText(String.valueOf(responseBean.getQuantity() - responseBean.getActive_bookings()));
        tvRentedNo.setText(String.valueOf(responseBean.getTotal_booking()) + " Bookings");
        tvTotalEarnedNo.setText(responseBean.getCurrency() + " " + responseBean.getTotal_earnings());
        tvAddressTitle.setText(responseBean.getAddress());
        txtEarning.setVisibility(View.GONE);
        txtEarning.setText("You will earn " + responseBean.getCurrency()+ " " + getEarningAmount(mViewTulModel.getPrice(), mViewTulModel.getTransaction_percentage()));
        tvAboutTulTitle.setText("ABOUT THIS " + responseBean.getCategory_name());

        tvTulPrice.setText(responseBean.getCurrency()+ " " + getEarningAmount(responseBean.getPrice(),
                responseBean.getTransaction_percentage()));

        simpleRatingBar.setRating(responseBean.getRating());

        tvAboutTulDetail.setText(responseBean.getDescription());
        tvAboutTulDetail.post(new Runnable() {
            @Override
            public void run() {
                int lineCnt = tvAboutTulDetail.getLineCount();
                if (lineCnt > 3) {
                    makeTextViewResizable(tvAboutTulDetail, 3, "...Read More", true);
                }
            }
        });


        tvAboutTulDetail.setText(responseBean.getDescription());
        tvTulStartTimeNo.setText(responseBean.getPreferences().getStart_time());
        tvTulEndTimeNo.setText(responseBean.getPreferences().getEnd_time());

        if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
            // user is blocked
            tvPause.setText("SUSPENDED");
            tvPauseBooking.setText("");
        } else if (responseBean.getAdmin_pause_status() == 1) {
            /// tul is blocked
            tvPause.setText("SUSPENDED");
            tvPauseBooking.setText("");
        } else if (responseBean.getPause_status().equals("0")) {
            /// show pause booking
            tvPause.setText("PAUSE");
            tvPauseBooking.setText(R.string.pause_tull_booking);
        } else {
            /// show resume booking
            tvPause.setText("RESUME");
            tvPauseBooking.setText(R.string.resume_tull_booking);
        }

        invalidateOptionsMenu();
        mapFragment.getMapAsync(this);
    }


    private void setFullTulData(ViewTulModel.ResponseBean responseBean) {

        if(responseBean.getBank_detail().getAccount_number()==null){
            tvTulLinkedAccount.setVisibility(View.GONE);
        }else {
            tvTulLinkedAccount.setVisibility(View.VISIBLE);
        }

        llPauseBooking.setVisibility(View.VISIBLE);
        toolbarHeaderView.bindTo(responseBean.getTitle());
        tvTulName.setText(responseBean.getTitle());
        tvTulPrice.setText(mViewTulModel.getCurrency() + " " + getEarningAmount(mViewTulModel.getPrice(), mViewTulModel.getTransaction_percentage()));
        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) responseBean.getAttachment()));
        cpIndicator.setViewPager(vpTul);

        if (mViewTulModel.getDiscount_days() != 0) {
            txtDiscount.setVisibility(View.VISIBLE);
            txtDiscount.setText("Discount offered: " + mViewTulModel.getDiscount_percentage() + "% off on  " + mViewTulModel.getDiscount_days() + " days bookings.");
        }

        tvTotalTulsNo.setText(String.valueOf(responseBean.getQuantity()));
        tvBookedTulNo.setText(String.valueOf(responseBean.getActive_bookings()));
        tvFreeTulNo.setText(String.valueOf(responseBean.getQuantity() - responseBean.getActive_bookings()));
        tvRentedNo.setText(String.valueOf(responseBean.getTotal_booking()) + " Bookings");
        tvTotalEarnedNo.setText(mViewTulModel.getCurrency()+ " " + responseBean.getTotal_earnings());

        tvAddressTitle.setText(responseBean.getAddress());
        txtEarning.setVisibility(View.GONE);
        txtEarning.setText("You will earn " + mViewTulModel.getCurrency() + " " + getEarningAmount(mViewTulModel.getPrice(), mViewTulModel.getTransaction_percentage()));
        tvAboutTulTitle.setText("ABOUT THIS " + responseBean.getCategory_name());

        simpleRatingBar.setRating(responseBean.getRating());

        tvAboutTulDetail.setText(responseBean.getDescription());
        tvAboutTulDetail.post(new Runnable() {
            @Override
            public void run() {
                int lineCnt = tvAboutTulDetail.getLineCount();
                if (lineCnt > 3) {
                    makeTextViewResizable(tvAboutTulDetail, 3, "...Read More", true);
                }
            }
        });

        if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
            // user is blocked
            tvPause.setText("SUSPENDED");
            tvPauseBooking.setText("");
        } else if (responseBean.getAdmin_pause_status() == 1) {
            /// tul is blocked
            tvPause.setText("SUSPENDED");
            tvPauseBooking.setText("");
        } else if (responseBean.getPause_status().equals("0")) {
            /// show pause booking
            tvPause.setText("PAUSE");
            tvPauseBooking.setText(getString(R.string.pause_tul_booking));
        } else {
            /// show resume booking
            tvPause.setText("RESUME");
            tvPauseBooking.setText(getString(R.string.resume_tull_booking));
        }


        tvAboutTulDetail.setText(responseBean.getDescription());
        tvTulStartTimeNo.setText(responseBean.getPreferences().getStart_time());
        tvTulEndTimeNo.setText(responseBean.getPreferences().getEnd_time());
        invalidateOptionsMenu();
        mapFragment.getMapAsync(this);
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
    protected Context getContext() {
        return TulDetailActivity.this;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_your_earning_info:
                PopupWindow popupwindow_obj;
                if (mTulModel != null) {
                    popupwindow_obj = showEarningPopup(mTulModel.getTransaction_percentage(), mTulModel.getPrice());
                    popupwindow_obj.showAsDropDown(imgYourEarningInfo, 0, -70);
                } else if (mViewTulModel != null) {
                    popupwindow_obj = showEarningPopup(mViewTulModel.getTransaction_percentage(), mViewTulModel.getPrice());
                    popupwindow_obj.showAsDropDown(imgYourEarningInfo, 0, -70);
                }
                break;
            case R.id.tv_tul_rules:
                ArrayList<String> mRulesArray = (ArrayList<String>) mViewTulModel.getRules();
                intent = new Intent(this, TulRulesActivity.class);
                intent.putStringArrayListExtra("data", mRulesArray);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_tul_additional_prices:
                ViewTulModel.ResponseBean.AdditionalPriceBean additionalPriceBean = mViewTulModel.getAdditional_price();
                additionalPriceBean.setAdd_currency(mViewTulModel.getCurrency());
                intent = new Intent(this, TulAdditionalChargesActivity.class);
                intent.putExtra("user_id", mViewTulModel.getUser_id());
                intent.putExtra("transaction_percentage", mViewTulModel.getTransaction_percentage());
                intent.putExtra("data", additionalPriceBean);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_tul_reviews_rating:
                intent = new Intent(this, TulReviewRatingsActivity.class);
                intent.putExtra("tool_id", mViewTulModel.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_tul_cancellation_policies:
                intent = new Intent(this, TulCancellationPolicies.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_tul_preferences:
                ViewTulModel.ResponseBean.PreferencesBean prefrencesBean = mViewTulModel.getPreferences();
                intent = new Intent(this, TulViewPreferencesActivity.class);
                intent.putExtra("user_id", mViewTulModel.getUser_id());
                intent.putExtra("transaction_percentage", mViewTulModel.getTransaction_percentage());
                intent.putExtra("data", prefrencesBean);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_tul_linked_account:
                ViewTulModel.ResponseBean.BankDetailBean bankBean = mViewTulModel.getBank_detail();
                intent = new Intent(this, TulAccountDetailActivity.class);
                intent.putExtra("data", bankBean);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_pause:
                if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
                    userBlockDialog(mContext);
                } else if (mViewTulModel.getAdmin_pause_status() == 1) {
                    placeBlockDialog(mContext);
                }
                /// 0 for resume, 1 for pause
                else if (mViewTulModel.getPause_status().equals("0"))
                    pauseTul(getString(R.string.pause_message), 1);
                else
                    pauseTul(getString(R.string.resume_message), 0);
                break;
        }
    }

    private PopupWindow showEarningPopup(String transaction_percentage, String price) {
        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.info_dialog_layout, null);

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

    private void pauseTul(String message, final int status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (connectedToInternet()) {
                            hitPauseAPI(status);
                        } else
                            Toast.makeText(mContext, errorInternet, Toast.LENGTH_SHORT).show();
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

    private void hitPauseAPI(final int status) {
        showProgress();
        Call<DemoModel> call = RetrofitClient.getInstance().pauseTul(utils.getString("access_token", ""),
                mViewTulModel.getId(), status);
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    if (status == 1) {
                        mViewTulModel.setPause_status("1");
                        tvPause.setText("RESUME");
                        tvPauseBooking.setText(getString(R.string.resume_tull_booking));
                    } else {
                        mViewTulModel.setPause_status("0");
                        tvPause.setText("PAUSE");
                        tvPauseBooking.setText(getString(R.string.pause_tul_booking));
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        Toast.makeText(TulDetailActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(TulDetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isEdit) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mViewTulModel != null) {
            menu.add("Edit").setIcon(R.mipmap.ic_edit).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
                        userBlockDialog(mContext);
                    } else if (mViewTulModel.getAdmin_pause_status() == 1) {
                        placeBlockDialog(mContext);
                    } else {
                        Intent intent = new Intent(mContext, ListYourTulActivity.class);
                        intent.putExtra("tul_data", mViewTulModel);
                        startActivityForResult(intent, EDIT_TUL);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }
                    return true;
                }
            }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            menu.add("Delete").setIcon(R.mipmap.ic_delete).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (connectedToInternet())
                        alertDeleteDialog();
                    else
                        showInternetAlert(llBookedTuls);
                    return true;
                }
            }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    private void alertDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_tul)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (connectedToInternet()) {
                            dialog.cancel();
                            hitDeleteAPI();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        } else
                            showInternetAlert(llBookedTuls);
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

    private void hitDeleteAPI() {
        showProgress();
        Call<DemoModel> call = RetrofitClient.getInstance().deleteTul(utils.getString("access_token", ""), mViewTulModel.getId());
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    db.deleteTul(String.valueOf(mViewTulModel.getId()));
                    Intent intent = new Intent();
                    intent.putExtra("position", getIntent().getIntExtra("position", 0));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals("401")) {
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        Toast.makeText(mContext, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        showAlert(llBookedTuls, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llBookedTuls, t.getLocalizedMessage());
                hideProgress();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("Map Ready = ", "Yes");
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(mContext, R.raw.style));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(Double.parseDouble(mViewTulModel.getLatitude()), Double.parseDouble(mViewTulModel.getLongitude()))).zoom(14).build();
        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mViewTulModel.getLatitude())
                , Double.parseDouble(mViewTulModel.getLongitude())))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_loc_pin)));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
        }
    }

    public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                            viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                            viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                            viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new TulDetailActivity.MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    new ShowMoreDialog(mContext, mWidth, mHeight, mViewTulModel.getDescription()).showDialog();
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);
        }
        return ssb;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT_TUL) {
                mTulModel = null;
                mViewTulModel = data.getParcelableExtra("tul_data");
                setFullTulData(mViewTulModel);
                isEdit = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class MySpannable extends ClickableSpan {
        private boolean isUnderline = false;

        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setTextSize(Constants.dpToPx(12));
            ds.setTypeface(Typeface.DEFAULT_BOLD);
            ds.setColor(Color.parseColor("#000000"));
        }

        @Override
        public void onClick(View widget) {

        }
    }
}
