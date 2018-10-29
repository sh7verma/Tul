package com.app.tul.Sales;

import android.app.Activity;
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
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.app.tul.BaseActivity;
import com.app.tul.ChatActivity;
import com.app.tul.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import adapters.MoreTulAdapter;
import adapters.TulDetailImagesAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import customviews.CircleTransform;
import customviews.HeaderView;
import dialogs.ShareTulDialog;
import dialogs.ShowMoreDialog;
import me.relex.circleindicator.CircleIndicator;
import model.AttachmentModel;
import model.CardModel;
import model.ChatDialogModel;
import model.CheckChatModel;
import model.DemoModel;
import model.NearByTulListingModel;
import model.SalesTulDetailModel;
import model.ViewTulModel;
import retrofit2.Call;
import retrofit2.Response;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.FirebaseListeners;

public class SalesTulDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, OnMapReadyCallback {

    private static final int CANCEL = 1;
    private static final int EDIT_TUL = 2;
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
    @BindView(R.id.btn_shortlist)
    LikeButton btnShortlist;

    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;

    @BindView(R.id.ll_data)
    LinearLayout llData;

    @BindView(R.id.txt_tul_title)
    TextView txtTulTitle;
    @BindView(R.id.rl_stars)
    RelativeLayout rlStars;
    @BindView(R.id.ll_stars)
    LinearLayout llStars;
    @BindView(R.id.srb_tul)
    SimpleRatingBar srbTul;
    @BindView(R.id.img_your_earning_info)
    ImageView imgYourEarningInfo;

    @BindView(R.id.txt_discount)
    TextView txtDiscount;

    @BindView(R.id.ll_tul_own)
    LinearLayout llTulOwn;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.tv_tul_name)
    TextView tvTulName;
    @BindView(R.id.tv_tul_price)
    TextView tvTulPrice;
    @BindView(R.id.tv_tul_time_interval)
    TextView tvTulTimeInterval;
    @BindView(R.id.rating_bar_default)
    SimpleRatingBar simpleRatingBar;
    @BindView(R.id.ll_stars_own)
    LinearLayout llStarsOwn;

    @BindView(R.id.ll_other_user_tul)
    LinearLayout llOtherUserTul;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_profile)
    LinearLayout llProfile;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.img_chat)
    ImageView imgChat;
    @BindView(R.id.txt_owner)
    TextView txtOwner;
    @BindView(R.id.txt_owner_name)
    TextView txtOwnerName;

    @BindView(R.id.tv_about_tul_title)
    TextView tvAboutTulTitle;
    @BindView(R.id.tv_about_tul_detail)
    TextView tvAboutTulDetail;

    @BindView(R.id.tv_address_title)
    TextView tvAddressTitle;
    @BindView(R.id.tv_delivery_available)
    TextView tvDeliveryAvailable;
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
    @BindView(R.id.tv_tul_linked_account2)
    TextView tvTulLinkedAccounthidden;

    @BindView(R.id.ll_check_availbilty)
    LinearLayout llCheckAvailbilty;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_per_day)
    TextView tvPerDay;
    @BindView(R.id.tv_availbilty)
    TextView tvAvailbilty;
    @BindView(R.id.tv_tul_linked_account)
    TextView tvTulLinkedAccount;


    @BindView(R.id.ll_pause_booking)
    LinearLayout llPauseBooking;
    @BindView(R.id.tv_pause_booking)
    TextView tvPauseBooking;
    @BindView(R.id.tv_pause)
    TextView tvPause;

    @BindView(R.id.ll_cancel_booking)
    LinearLayout llCancelBooking;
    @BindView(R.id.tv_cancellation_charges)
    TextView tvCancellationCharges;
    @BindView(R.id.tv_charges)
    TextView tvCharges;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

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

    SupportMapFragment mapFragment;
    @BindView(R.id.tv_see_more)
    TextView tvSeeMore;
    @BindView(R.id.ll_more_tuls)
    LinearLayout llMoreTuls;
    @BindView(R.id.tv_more_tuls)
    TextView tvMoreTuls;
    @BindView(R.id.rv_more_tuls)
    RecyclerView rvMoreTuls;

    @BindView(R.id.ll_quantity)
    LinearLayout llquantity;
    @BindView(R.id.txt_quantity)
    TextView txtQuantity;
    @BindView(R.id.txt_quantity_title)
    TextView txtQuantityTitle;
    @BindView(R.id.txt_buy)
    TextView txtBuy;
    @BindView(R.id.txt_tool_condition)
    TextView txtToolCondition;

    @BindView(R.id.txt_own_tul_price)
    TextView txtOwnTulPrice;

    @BindView(R.id.img_share)
    ImageView imgShare;


    ArrayList<NearByTulListingModel.ResponseBean> mMoreTulArrayList = new ArrayList<>();
    MoreTulAdapter mAdapter;
    boolean isChatDisables;
    private boolean isHideToolbarView = false;
    private SalesTulDetailModel.ResponseBean mTulModel;
    //    private SearchResultModel.ResponseBean mSearchTulModel;
    private SalesTulDetailModel.ResponseBean mViewTulModel;
    private boolean mShowOptions = true;
    private boolean isEdit;

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_detail_sales;
    }

    @Override
    protected void initUI() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        collapsingToolbar.setTitle(" ");

        appBarLayout.addOnOffsetChangedListener(this);

        CollapsingToolbarLayout.LayoutParams imgParms = new CollapsingToolbarLayout.LayoutParams(mWidth, mHeight / 3);
        vpTul.setLayoutParams(imgParms);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        /// own tul detail
        tvTulName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.050));
        tvTulName.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mWidth / 64);

        simpleRatingBar.setStarSize((float) (mWidth * 0.04));
        simpleRatingBar.setPadding(mWidth / 32, mWidth / 32, 0, mHeight / 32);

        tvTulPrice.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvTulPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));

        txtOwnTulPrice.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        txtOwnTulPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));


        tvTulTimeInterval.setPadding(0, mHeight / 32, 0, 0);
        tvTulTimeInterval.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.030));

        ////
        txtTulTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtTulTitle.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

//        srbTul.setPadding(0, 0, mWidth / 32, 0);

        txtOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

        LinearLayout.LayoutParams nameParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameParms.setMargins(mWidth / 32, 0, mWidth / 32, mWidth / 32);
        llName.setLayoutParams(nameParms);

        txtOwnerName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

        LinearLayout.LayoutParams profileParms = new LinearLayout.LayoutParams(mWidth / 8, mWidth / 8);
        profileParms.setMargins(mWidth / 64, 0, mWidth / 32, 0);
        imgProfile.setLayoutParams(profileParms);

        txtDiscount.setPadding(mWidth / 32, 0, 0, mWidth / 32);
        txtDiscount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvAboutTulTitle.setPadding(mWidth / 32, mHeight / 32, 0, mWidth / 64);
        tvAboutTulTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvAboutTulDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvAboutTulDetail.setPadding(mWidth / 32, 0, mWidth / 32, mHeight / 32);

        tvAddressTitle.setPadding(mWidth / 32, mHeight / 32, 0, mWidth / 64);
        tvAddressTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvDeliveryAvailable.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mWidth / 64);
        tvDeliveryAvailable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulStartTime.setPadding(mWidth / 32, mHeight / 32, 0, 0);
        tvTulStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulStartTimeNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvTulStartTimeNo.setPadding(mWidth / 32, 0, 0, mHeight / 32);

        tvTulEndTime.setPadding(0, mHeight / 32, mWidth / 32, 0);
        tvTulEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        tvTulEndTimeNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvTulEndTimeNo.setPadding(0, 0, mWidth / 32, mHeight / 32);

        llCheckAvailbilty.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);

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

        tvTulLinkedAccount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulLinkedAccount.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvTulLinkedAccounthidden.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.055));
        tvTulLinkedAccounthidden.setPadding(mWidth / 32, mHeight / 20, mWidth / 32, mHeight / 20);

        LinearLayout.LayoutParams starParms = new LinearLayout.LayoutParams((int) (mWidth * .4), mHeight / 32);
        rlStars.setLayoutParams(starParms);

        RelativeLayout.LayoutParams rlStarParms = new RelativeLayout.LayoutParams((int) (mWidth * .4), mHeight / 32);
        llStars.setLayoutParams(rlStarParms);

        LinearLayout.LayoutParams mapParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight / 4);
        mapParms.setMargins(mWidth / 32, 0, mWidth / 32, 0);
        rlMap.setLayoutParams(mapParms);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.setMargins(mWidth / 32, 0, mWidth / 32, 0);
        view2.setLayoutParams(layoutParams);
        view3.setLayoutParams(layoutParams);
        view4.setLayoutParams(layoutParams);
        view5.setLayoutParams(layoutParams);
        view6.setLayoutParams(layoutParams);
        view7.setLayoutParams(layoutParams);

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvPerDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvAvailbilty.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvAvailbilty.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvAvailbilty.setEnabled(false);

        /// Cancel Layouts...
        llCancelBooking.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);

        tvCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvCancellationCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvCancel.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvCancel.setEnabled(false);
        ///

        /// pause section
        llPauseBooking.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);

        txtBuy.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);
        txtBuy.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvPauseBooking.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        tvPause.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvPause.setPadding(mWidth / 16, mWidth / 32, mWidth / 16, mWidth / 32);
        /////
        btnShortlist.setUnlikeDrawableRes(R.mipmap.ic_shortlist);
        btnShortlist.setLikeDrawableRes(R.mipmap.ic_shortlisted);

        tvSeeMore.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvSeeMore.setPadding(0, 0, mWidth / 32, mHeight / 32);

        tvSeeMore.setVisibility(View.GONE);

        llMoreTuls.setVisibility(View.GONE);

        tvMoreTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvMoreTuls.setPadding(mWidth / 32, mHeight / 32, 0, mHeight / 60);

        rvMoreTuls.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        txtQuantityTitle.setPadding(mWidth / 32, mHeight / 32, 0, 0);
        txtQuantityTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        txtQuantity.setPadding(0, mHeight / 32, mWidth / 32, 0);
        txtQuantity.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

    }

    @Override
    protected void onCreateStuff() {
        imgChat.setVisibility(View.GONE);
        if (getIntent().hasExtra("data")) {
            imgProfile.setEnabled(false);
            llProfile.setEnabled(false);
            /// getting data from category listing.
            mTulModel = getIntent().getParcelableExtra("data");
            if (connectedToInternet())
                hitAPI();
            else {
                showInternetAlert(llData);
                pbLoad.setVisibility(View.GONE);
            }
//            setData();
            setFullTulData(mTulModel);
        } else if (getIntent().hasExtra("search_data")) {
//            /// getting data from search result activity
//            mSearchTulModel = getIntent().getParcelableExtra("search_data");
//            if (connectedToInternet())
//                hitAPI();
//            else {
//                showInternetAlert(llData);
//                pbLoad.setVisibility(View.GONE);
//            }
//            setSearchData();
        } else {/// getting data from Api by passing tul Id
            if (connectedToInternet()) {
                svMain.setVisibility(View.INVISIBLE);
                showProgress();
                hitAPI();
            } else
                showInternetAlert(llData);
        }

        btnShortlist.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (connectedToInternet()) {
                    hitShortListAPI();
//                    if (getIntent().hasExtra("data")) {
//                        db.addShortListedTul(mTulModel);
//                        for (AttachmentModel attachmentItem : mTulModel.getAttachment()) {
//                            db.addShortListAttachment(attachmentItem);
//                        }
//                    } else {
//                        NearByTulListingModel.ResponseBean tulItem = new NearByTulListingModel.ResponseBean();
//                        tulItem.setId(mViewTulModel.getId());
//                        tulItem.setTitle(mViewTulModel.getTitle());
//                        tulItem.setOwner(mViewTulModel.getOwner());
//                        tulItem.setOwner_pic(mViewTulModel.getOwner_pic());
//                        tulItem.setPrice(mViewTulModel.getPrice());
//                        tulItem.setUser_id(mViewTulModel.getUser_id());
//                        tulItem.setRating(mViewTulModel.getRating());
//                        tulItem.setAttachment(mViewTulModel.getAttachment());
//                        db.addShortListedTul(tulItem);
//                        for (AttachmentModel attachmentItem : mViewTulModel.getAttachment()) {
//                            db.addShortListAttachment(attachmentItem);
//                        }
//                    }
                } else {
                    if (btnShortlist.isLiked()) {
                        btnShortlist.setLiked(false);
                    } else {
                        btnShortlist.setLiked(true);
                    }
                    showInternetAlert(llData);
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (connectedToInternet()) {
                    hitShortListAPI();
//                    if (getIntent().hasExtra("data"))
//                        db.deleteShortListTul(String.valueOf(mTulModel.getId()));
//                    else
//                        db.deleteShortListTul(String.valueOf(mViewTulModel.getId()));
                } else {
                    showInternetAlert(llData);
                }

            }

        });


        if (db.getAllCards().size() == 0)
            hitCardsAPI();

    }

    @Override
    protected void initListener() {
        tvTulRules.setOnClickListener(this);
        tvTulAdditionalPrices.setOnClickListener(this);
        tvTulReviewsRating.setOnClickListener(this);
        tvTulCancellationPolices.setOnClickListener(this);
        tvTulPreferences.setOnClickListener(this);
        tvTulLinkedAccount.setOnClickListener(this);
        tvAvailbilty.setOnClickListener(this);
        tvSeeMore.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        llStars.setOnClickListener(this);
        tvPause.setOnClickListener(this);
        imgChat.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        llStarsOwn.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        imgYourEarningInfo.setOnClickListener(this);

        txtBuy.setOnClickListener(this);
        imgShare.setOnClickListener(this);

    }


    private void hideViews() {
        llMoreTuls.setVisibility(View.GONE);
        llCheckAvailbilty.setVisibility(View.GONE);
        llCancelBooking.setVisibility(View.GONE);
    }

    private void setData() {
        if (mTulModel.getUser_id() == Integer.parseInt(utils.getString("user_id", ""))) {
            llTulOwn.setVisibility(View.VISIBLE);
            llOtherUserTul.setVisibility(View.GONE);
//            tvTulLinkedAccount.setVisibility(View.VISIBLE);
        }
        ///
        tvTulName.setText(mTulModel.getTitle());
        simpleRatingBar.setRating(mTulModel.getRating());
        ///
        if (mTulModel.getIs_wishlisted() == 0) {
            btnShortlist.setLiked(false);
        } else {
            btnShortlist.setLiked(true);
        }
        toolbarHeaderView.bindTo(mTulModel.getTitle());
        txtTulTitle.setText(mTulModel.getTitle());
        txtOwnerName.setText(mTulModel.getOwner());
        Picasso.with(mContext).load(mTulModel.getOwner_pic())
                .resize(mWidth / 8, mWidth / 8).centerCrop().placeholder(R.mipmap.ic_add_image)
                .transform(new CircleTransform())
                .into(imgProfile);
        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) mTulModel.getAttachment()));
        cpIndicator.setViewPager(vpTul);
        tvPrice.setText(mTulModel.getCurrency() + " " + mTulModel.getPrice());
        srbTul.setRating(mTulModel.getRating());
    }

//    private void setSearchData() {
//        if (mSearchTulModel.getUser_id() == Integer.parseInt(utils.getString("user_id", ""))) {
//            llTulOwn.setVisibility(View.VISIBLE);
//            llOtherUserTul.setVisibility(View.GONE);
//            tvTulLinkedAccount.setVisibility(View.VISIBLE);
//        }
//        ///
//        tvTulName.setText(mSearchTulModel.getTitle());
//        simpleRatingBar.setRating(mSearchTulModel.getRating());
//        ///
//
//        toolbarHeaderView.bindTo(mSearchTulModel.getTitle());
//        txtTulTitle.setText(mSearchTulModel.getTitle());
//        txtOwnerName.setText(mSearchTulModel.getOwner());
//        tvPrice.setText(Constants.POUND + " " + mSearchTulModel.getPrice());
//        Picasso.with(mContext).load(mSearchTulModel.getOwnerPic())
//                .resize(mWidth / 8, mWidth / 8).centerCrop().placeholder(R.mipmap.ic_add_image)
//                .transform(new CircleTransform())
//                .into(imgProfile);
//        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) mSearchTulModel.getAttachment()));
//        cpIndicator.setViewPager(vpTul);
//        tvCharges.setText(Constants.POUND + " " + "5");
//        srbTul.setRating(mSearchTulModel.getRating());
//    }

    private void hitShortListAPI() {
        if (utils.getInt(Constants.ISGUEST, 0) == 1) {
            loginFirst(mContext);
            if (btnShortlist.isLiked()) {
                btnShortlist.setLiked(false);
            } else {
                btnShortlist.setLiked(true);
            }
        } else {
            if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
                userBlockDialog(mContext);
                if (btnShortlist.isLiked()) {
                    btnShortlist.setLiked(false);
                } else {
                    btnShortlist.setLiked(true);
                }
            } else {
                Call<DemoModel> call;
//                if (getIntent().hasExtra("data"))
//                    call = RetrofitClient.getInstance().shortListTul(utils.getString("access_token", ""), mTulModel.getId());
//                else if (getIntent().hasExtra("search_data"))
//                    call = RetrofitClient.getInstance().shortListTul(utils.getString("access_token", ""), mSearchTulModel.getId());
//                else
                call = RetrofitClient.getInstance().shortListTulSales(utils.getString("access_token", ""), getIntent().getIntExtra("id", 1));
                call.enqueue(new retrofit2.Callback<DemoModel>() {
                    @Override
                    public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                        if (response.body().response != null) {
                            isEdit = true;
                            if (response.body().response.getMessage().equals("deleted from wishlist")) {
                                if (mViewTulModel != null) {
                                    mViewTulModel.setIs_wishlisted(0);
                                    db.deleteShortListTul(String.valueOf(mViewTulModel.getId()));
                                }
                            } else if (response.body().response.getMessage().equals("added to wishlist")) {
                                if (mViewTulModel != null) {
                                    mViewTulModel.setIs_wishlisted(1);

                                    NearByTulListingModel.ResponseBean tulItem = new NearByTulListingModel.ResponseBean();
                                    tulItem.setId(mViewTulModel.getId());
                                    tulItem.setTitle(mViewTulModel.getTitle());
                                    tulItem.setOwner(mViewTulModel.getOwner());
                                    tulItem.setOwner_pic(mViewTulModel.getOwner_pic());
                                    tulItem.setPrice(mViewTulModel.getPrice());
                                    tulItem.setUser_id(mViewTulModel.getUser_id());
                                    tulItem.setRating(mViewTulModel.getRating());
                                    tulItem.setAttachment(mViewTulModel.getAttachment());
                                    tulItem.setCondition(mViewTulModel.getCondition());
                                    db.addShortListedTul(tulItem);
                                    for (AttachmentModel attachmentItem : mViewTulModel.getAttachment()) {
                                        db.addShortListAttachment(attachmentItem);
                                    }
                                }
                            }
                        } else {
                            if (response.body().error.getCode().equals(errorAccessToken)) {
                                moveToSplash();
                            } else {
                                showAlert(llData, response.body().error.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DemoModel> call, Throwable t) {
                        showAlert(llData, t.getLocalizedMessage());
                    }
                });
            }
        }
    }

    private void hitAPI() {
        Call<SalesTulDetailModel> call;
//        if (getIntent().hasExtra("data"))
//            call = RetrofitClient.getInstance().viewTul(utils.getString("access_token", ""),
//                    mTulModel.getId());
//        else if (getIntent().hasExtra("search_data"))
//            call = RetrofitClient.getInstance().viewTul(utils.getString("access_token", ""),
//                    mSearchTulModel.getId());
//        else if (getIntent().hasExtra("calendar_tul_id"))
//            call = RetrofitClient.getInstance().viewTul(utils.getString("access_token", ""),
//                    getIntent().getIntExtra("calendar_tul_id", 0));
//        else
        call = RetrofitClient.getInstance().salesToolDetail(utils.getString("access_token", ""),
                getIntent().getIntExtra("id", 1));
        call.enqueue(new retrofit2.Callback<SalesTulDetailModel>() {
            @Override
            public void onResponse(Call<SalesTulDetailModel> call, Response<SalesTulDetailModel> response) {
                if (response.body().getResponse() != null) {
                    mViewTulModel = response.body().getResponse();
                    tvAvailbilty.setEnabled(true);
                    imgProfile.setEnabled(true);
                    llProfile.setEnabled(true);
                    if (getIntent().hasExtra("data")) {
//                        setTulData(response.body().getResponse());
                        setFullTulData(response.body().getResponse());
                        llData.setVisibility(View.VISIBLE);
                        pbLoad.setVisibility(View.GONE);
                        tvMoreTuls.setText(getString(R.string.more_tuls_by) + " " + mViewTulModel.getOwner());
                        hitGetMoreTulsAPI();
                    } else if (getIntent().hasExtra("search_data")) {
                        setTulData(response.body().getResponse());
                        llData.setVisibility(View.VISIBLE);
                        pbLoad.setVisibility(View.GONE);
                        tvCancel.setEnabled(true);
                    } else if (getIntent().hasExtra("calendar_tul_id")) {
                        setFullTulData(response.body().getResponse());
                        hideProgress();
                    } else {
                        setFullTulData(response.body().getResponse());
                        hideProgress();
                        tvMoreTuls.setText(getString(R.string.more_tuls_by) + " " + mViewTulModel.getOwner());
                        hitGetMoreTulsAPI();
                    }
                } else {
                    if (String.valueOf(response.body().error.getCode()).equals("1111")) {
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        Toast.makeText(mContext, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    if (String.valueOf(response.body().error.getCode()).equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        if (getIntent().hasExtra("data")) {
                            pbLoad.setVisibility(View.GONE);
                        } else {
                            hideProgress();
                        }
                        showAlert(llData, String.valueOf(response.body().error.getMessage()));
                    }
                    if (String.valueOf(response.body().error.getCode()).equals("401")) {
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        Toast.makeText(mContext, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesTulDetailModel> call, Throwable t) {
                showAlert(llData, t.getLocalizedMessage());
                if (getIntent().hasExtra("data")) {
                    pbLoad.setVisibility(View.GONE);
                } else {
                    hideProgress();
                }
            }
        });
    }

    private void setTulData(SalesTulDetailModel.ResponseBean responseBean) {
        if (getIntent().hasExtra("search_data")) {
            vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) responseBean.getAttachment()));
            cpIndicator.setViewPager(vpTul);
        }

        tvAddressTitle.setText(responseBean.getAddress());

        tvAboutTulTitle.setText(getString(R.string.about_this_)/* +" "+ responseBean.getCategory_name()*/);

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

//        tvTulStartTimeNo.setText(responseBean.getPreferences().getStart_time());
//        tvTulEndTimeNo.setText(responseBean.getPreferences().getEnd_time());

        if (!String.valueOf(mViewTulModel.getUser_id()).equals(utils.getString("user_id", ""))) {

            btnShortlist.setVisibility(View.VISIBLE);

        }

        if (responseBean.getIs_wishlisted() == 1)
            btnShortlist.setLiked(true);
        else
            btnShortlist.setLiked(false);

        mapFragment.getMapAsync(this);

//        if (responseBean.getPause_status().equals("0")) {
//            /// show pause booking
//            tvPause.setText("PAUSE");
//            tvPauseBooking.setText("Pause TUL Booking");
//        } else {
//            /// show resume booking
//            tvPause.setText("RESUME");
//            tvPauseBooking.setText("Resume TUL Booking");
//            tvAvailbilty.setText("Booking Closed");
//            tvAvailbilty.setEnabled(false);
//        }

//        if (utils.getString("user_id", "").equals(String.valueOf(mViewTulModel.getUser_id()))) {
        tvTulPrice.setText(mViewTulModel.getCurrency() + " " + mViewTulModel.getPrice());
        txtOwnTulPrice.setText(mViewTulModel.getCurrency() + " " + mViewTulModel.getPrice());
//            llPrice.setVisibility(View.VISIBLE);
//            llCheckAvailbilty.setVisibility(View.GONE);
//            llPauseBooking.setVisibility(View.VISIBLE);
//            imgChat.setVisibility(View.GONE);
//            if (mViewTulModel.getDiscount_days() != 0) {
//                txtDiscount.setVisibility(View.VISIBLE);
//                txtDiscount.setText("Discount offered: " + mViewTulModel.getDiscount_percentage() + "% off on  " + mViewTulModel.getDiscount_days() + " days bookings.");
//            }
//        } else {
//            llCheckAvailbilty.setVisibility(View.VISIBLE);
//            llPauseBooking.setVisibility(View.GONE);

//            imgChat.setVisibility(View.VISIBLE);

//            if (mViewTulModel.getDiscount_days() != 0) {
//                txtDiscount.setVisibility(View.VISIBLE);
//                txtDiscount.setText("Get " + mViewTulModel.getDiscount_percentage() + "% off if you book for at least " + mViewTulModel.getDiscount_days() + " days");
//            }
//        }
        invalidateOptionsMenu();
    }

    private void setFullTulData(SalesTulDetailModel.ResponseBean responseBean) {

        txtToolCondition.setVisibility(View.VISIBLE);
        txtToolCondition.setText(responseBean.getCondition() + "");

        svMain.setVisibility(View.VISIBLE);
        pbLoad.setVisibility(View.GONE);
        llData.setVisibility(View.VISIBLE);
        llquantity.setVisibility(View.VISIBLE);

        txtQuantity.setText(responseBean.getQuantity() + "");

        if (responseBean.getUser_id() == Integer.parseInt(utils.getString("user_id", ""))) {
            llTulOwn.setVisibility(View.VISIBLE);
            llOtherUserTul.setVisibility(View.GONE);
//            tvTulLinkedAccount.setVisibility(View.VISIBLE);
            txtBuy.setVisibility(View.GONE);
        } else {
            llTulOwn.setVisibility(View.GONE);
            llOtherUserTul.setVisibility(View.VISIBLE);
//          tvTulLinkedAccount.setVisibility(View.VISIBLE);
            txtBuy.setVisibility(View.VISIBLE);
        }

        if (responseBean.getDelivery_type() == 1) {
            tvDeliveryAvailable.setVisibility(View.VISIBLE);
        } else {
            tvDeliveryAvailable.setVisibility(View.GONE);
        }
//        if(responseBean.get)

        toolbarHeaderView.bindTo(responseBean.getTitle());
        txtTulTitle.setText(responseBean.getTitle());
        txtOwnerName.setText(responseBean.getOwner());
        Picasso.with(mContext).load(responseBean.getOwner_pic())
                .resize(mWidth / 8, mWidth / 8)
                .centerCrop().placeholder(R.mipmap.ic_small_ph_tul)
                .transform(new CircleTransform())
                .into(imgProfile);

        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) responseBean.getAttachment()));
        cpIndicator.setViewPager(vpTul);


//        tvPrice.setText(Constants.POUND + " " + responseBean.getPrice());

        tvAddressTitle.setText(responseBean.getAddress());
//        if (mViewTresponseBeanulModel.getDiscount_days() != 0) {
//            txtDiscount.setVisibility(View.VISIBLE);
//            txtDiscount.setText("Get " + mViewTulModel.getDiscount_percentage() + "% off if you book for at least " + mViewTulModel.getDiscount_days() + " days");
//        }
        tvAboutTulTitle.setText(getString(R.string.about_this_)/*+" " + responseBean.getCategory_name()*/);

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

//        tvTulStartTimeNo.setText(responseBean.getPreferences().getStart_time());
//        tvTulEndTimeNo.setText(responseBean.getPreferences().getEnd_time());

        if (responseBean.getIs_wishlisted() == 1)
            btnShortlist.setLiked(true);
        else
            btnShortlist.setLiked(false);

        tvTulPrice.setText(responseBean.getCurrency() + " " + responseBean.getPrice());

        txtOwnTulPrice.setText(responseBean.getCurrency() + " " + responseBean.getPrice());

        if (utils.getString("user_id", "").equals(String.valueOf(responseBean.getUser_id()))) {
//            llCheckAvailbilty.setVisibility(View.GONE);
//            llPauseBooking.setVisibility(View.VISIBLE);
//            imgChat.setVisibility(View.GONE);
            btnShortlist.setVisibility(View.GONE);

//            llPrice.setVisibility(View.VISIBLE);
            tvTulName.setText(responseBean.getTitle());
            simpleRatingBar.setRating(responseBean.getRating());
        } else {
            srbTul.setRating(responseBean.getRating());
            btnShortlist.setVisibility(View.VISIBLE);
//            llCheckAvailbilty.setVisibility(View.VISIBLE);
//            llPauseBooking.setVisibility(View.GONE);
//            imgChat.setVisibility(View.VISIBLE);
        }

//        if (responseBean.getPause_status().equals("0")) {
//            /// show pause booking
//            tvPause.setText("PAUSE");
//            tvPauseBooking.setText("Pause TUL Booking");
//        } else {
//            /// show resume booking
//            tvPause.setText("RESUME");
//            tvPauseBooking.setText("Resume TUL Booking");
//            tvAvailbilty.setEnabled(false);
//        }

        mapFragment.getMapAsync(this);
        invalidateOptionsMenu();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            moveBack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
//            case R.id.img_your_earning_info:
//                PopupWindow popupwindow_obj = showEarningPopup(mViewTulModel.getTransaction_percentage(), mViewTulModel.getPrice());
//                popupwindow_obj.showAsDropDown(imgYourEarningInfo, 0, -70);
//                break;
//            case R.id.tv_tul_linked_account:
//                ViewTulModel.ResponseBean.BankDetailBean bankBean = mViewTulModel.getBank_detail();
//                intent = new Intent(this, TulAccountDetailActivity.class);
//                intent.putExtra("data", bankBean);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                break;
//            case R.id.img_chat:
//                if (utils.getInt(Constants.ISGUEST, 0) == 0) {
//                    if (Constants.CHAT_PROFILE_ID == 0) {
//                        int sortArray[] = new int[]{mViewTulModel.getUser_id(), Integer.parseInt(utils.getString("user_id", ""))};
//                        Arrays.sort(sortArray);
//                        String dialogueId = String.valueOf(sortArray[0]) + "," + String.valueOf(sortArray[1]);
//                        if (connectedToInternet()) {
//                            CustomLoadingDialog.getLoader().showLoader(mContext);
//                            checkDialog(dialogueId, mViewTulModel);
//                        } else {
//                            showInternetAlert(llData);
//                        }
//                    } else {
//                        intent = new Intent();
//                        setResult(RESULT_OK, intent);
//                        finish();
//                    }
//                } else {
//                    loginFirst(mContext);
//                }
//
//                break;
//            case R.id.tv_pause:
//                /// 0 for resume, 1 for pause
//                if (mViewTulModel.getPause_status().equals("0"))
//                    pauseTul(getString(R.string.pause_message), 1);
//                else
//                    pauseTul(getString(R.string.resume_message), 0);
//                break;
//            case R.id.tv_tul_rules:
//                ArrayList<String> mRulesArray = (ArrayList<String>) mViewTulModel.getRules();
//                intent = new Intent(this, TulRulesActivity.class);
//                intent.putStringArrayListExtra("data", mRulesArray);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                break;
//            case R.id.tv_tul_additional_prices:
//                ViewTulModel.ResponseBean.AdditionalPriceBean additionalPriceBean = mViewTulModel.getAdditional_price();
//                intent = new Intent(this, TulAdditionalChargesActivity.class);
//                intent.putExtra("user_id", mViewTulModel.getUser_id());
//                intent.putExtra("transaction_percentage", mViewTulModel.getTransaction_percentage());
//                intent.putExtra("data", additionalPriceBean);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                break;
//            case R.id.tv_tul_reviews_rating:
//                intent = new Intent(this, TulReviewRatingsActivity.class);
//                intent.putExtra("tool_id", mViewTulModel.getId());
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                break;
//            case R.id.tv_tul_cancellation_policies:
//                intent = new Intent(this, TulCancellationPolicies.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                break;
//            case R.id.tv_tul_preferences:
//                ViewTulModel.ResponseBean.PreferencesBean prefrencesBean = mViewTulModel.getPreferences();
//                intent = new Intent(this, TulViewPreferencesActivity.class);
//                intent.putExtra("transaction_percentage", mViewTulModel.getTransaction_percentage());
//                intent.putExtra("user_id", mViewTulModel.getUser_id());
//                intent.putExtra("data", prefrencesBean);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                break;
//            case R.id.tv_availbilty:
//                if (utils.getInt(Constants.ISGUEST, 0) == 0) {
//                    intent = new Intent(this, CheckAvailablityActivity.class);
//                    intent.putExtra("tul_data", mViewTulModel);
//                    intent.putExtra("transaction_percentage", mViewTulModel.getTransaction_percentage());
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                } else {
//                    loginFirst(this);
//                }
//                break;
            case R.id.img_profile:
                /// not implemented break because same functionality in both cases.

            case R.id.ll_profile:
                if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                    if (!utils.getString("user_id", "").equals(mViewTulModel.getUser_id())) {
                        if (Constants.PROFILE_ID != mViewTulModel.getUser_id()) {
                            Constants.TUL_ID = mViewTulModel.getId();
                            intent = new Intent(mContext, SellerProfileActivity.class);
                            intent.putExtra("other_user_id", mViewTulModel.getUser_id());
                            intent.putExtra("other_user_name", mViewTulModel.getOwner());
                            intent.putExtra("other_user_pic", mViewTulModel.getOwner_pic());
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        } else {
                            finish();
                            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                        }
                    }
                } else {
                    loginFirst(this);
                }
                break;

            case R.id.txt_buy:
                if (connectedToInternet()) {
                    if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                        if (mViewTulModel != null) {
                            Intent intent1 = new Intent(this, SalesBuyTulDeliveryActivity.class);
                            intent1.putExtra("data", mViewTulModel);
                            startActivity(intent1);
                        }
                    } else {
                        loginFirst(this);
                    }
                } else {
                    showInternetAlert(llCancelBooking);
                }
                break;
            case R.id.img_share:
                if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                    new ShareTulDialog(mContext, mWidth, "", 2, mViewTulModel.getId()).showDialog();
                } else {
                    loginFirst(this);
                }

                break;
        }
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            Log.e("Map Ready = ", "Yes");
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(mContext, R.raw.style));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(Double.parseDouble(mViewTulModel.getLatitude()), Double.parseDouble(mViewTulModel.getLongitude()))).zoom(14).build();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mViewTulModel.getLatitude())
                    , Double.parseDouble(mViewTulModel.getLongitude())))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_pin)));
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } catch (Exception e) {

        }
    }

    private void pauseTul(String message, final int status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (connectedToInternet()) {
//                            hitPauseAPI(status);
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

//    private void hitPauseAPI(final int status) {
//        showProgress();
//        Call<DemoModel> call = RetrofitClient.getInstance().pauseTul(utils.getString("access_token", ""),
//                mViewTulModel.getId(), status);
//        call.enqueue(new Callback<DemoModel>() {
//            @Override
//            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
//                hideProgress();
//                if (response.body().response != null) {
//                    if (status == 1) {
//                        mViewTulModel.setPause_status("1");
//                        tvPause.setText("RESUME");
//                        tvPauseBooking.setText("Resume TUL Booking");
//                    } else {
//                        mViewTulModel.setPause_status("0");
//                        tvPause.setText("PAUSE");
//                        tvPauseBooking.setText("Pause TUL Booking");
//                    }
//                } else {
//                    if (response.body().error.getCode().equals(errorAccessToken)) {
//                        moveToSplash();
//                    } else {
//                        Toast.makeText(mContext, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DemoModel> call, Throwable t) {
//                hideProgress();
//                Toast.makeText(mContext, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {

        if (getIntent().hasExtra("search_data")) {
            /// getting data from search section
            if (isEdit)
                moveBackWithoutData();
            else {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        } else if (getIntent().hasExtra("id")) {
            /// getting data from cluster item click (LandingActivity)
            if (getIntent().hasExtra("short")) {
                moveBackWithoutData();
            } else if (getIntent().hasExtra("list")) {
                moveBackWithoutData();
            } else {

                finish();
                overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
            }
        } else if (getIntent().hasExtra("data")) {
            /// getting data from nearbytullistadapter
            if (isEdit)
                moveBackWithData();
            else
                moveBackWithoutData();
        } else {
            moveBackWithData();
        }

    }

    private void moveBackWithData() {
        Intent intent = new Intent();
        intent.putExtra("tul_data", mViewTulModel);
        intent.putExtra("position", getIntent().getIntExtra("position", 0));
        setResult(RESULT_OK, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
        }
    }

    private void moveBackWithoutData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finishAfterTransition();
        } else {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mViewTulModel != null) {
            if (mShowOptions) {
                if (utils.getString("user_id", "").equals(String.valueOf(mViewTulModel.getUser_id()))) {
                    menu.add("Edit").setIcon(R.mipmap.ic_edit).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
//                            Toast.makeText(mContext, R.string.Work_in_progress, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, SalesListYourTulActivity.class);
                            intent.putExtra("tul_data", mViewTulModel);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivityForResult(intent, EDIT_TUL);
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);


                            return true;
                        }
                    }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                    menu.add("Delete").setIcon(R.mipmap.ic_delete).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (connectedToInternet())
                                alertDeleteDialog();
                            else
                                showInternetAlert(llData);

                            return true;
                        }
                    }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                } else {
                    menu.add("Report").setIcon(R.mipmap.ic_report).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
                                userBlockDialog(mContext);
                            } else {
                                alertReportDialog();
                            }
                            return true;
                        }
                    }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                }
            } else {
                if (!utils.getString("user_id", "").equals(String.valueOf(mViewTulModel.getUser_id()))) {
                    menu.add("Report").setIcon(R.mipmap.ic_report).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
                                userBlockDialog(mContext);
                            } else {
                                alertReportDialog();
                            }
                            return true;
                        }
                    }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void hitCardsAPI() {
        Call<CardModel> call = RetrofitClient.getInstance().viewCard(utils.getString("access_token", ""));
        Log.d("access_token", utils.getString("access_token", ""));
        call.enqueue(new retrofit2.Callback<CardModel>() {
            @Override
            public void onResponse(Call<CardModel> call, Response<CardModel> response) {
                Log.d("responsexcz", response.toString());
                if (response.body().getResponse() != null) {
                    if (response.body().getResponse().size() > 0) {
                        for (int i = 0; i < response.body().getResponse().size(); i++) {
                            db.addCard(response.body().getResponse().get(i));
                        }
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        showAlert(llCancelBooking, response.body().error.getMessage());
                    } else {
                        showAlert(llCancelBooking, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<CardModel> call, Throwable t) {
                showAlert(llCancelBooking, t.getMessage());
            }
        });
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
                            showInternetAlert(llData);
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


    private void alertReportDialog() {
        if (utils.getInt(Constants.ISGUEST, 0) == 0) {
            if (utils.getInt(Constants.BLOCKSTATUS, 0) == 1) {
                userBlockDialog(mContext);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("REPORT");
                builder.setMessage(getString(R.string.report_msg))
                        .setCancelable(false)
                        .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (connectedToInternet()) {
                                    hitReportAPI();
                                    dialog.cancel();
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                } else
                                    showInternetAlert(llData);
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
            loginFirst(this);
        }

    }

    private void hitDeleteAPI() {
        showProgress();
        Call<DemoModel> call;
        if (getIntent().hasExtra("data"))
            call = RetrofitClient.getInstance().delete_a_sale_tool(utils.getString("access_token", ""), mTulModel.getId());
        else
            call = RetrofitClient.getInstance().delete_a_sale_tool(utils.getString("access_token", ""), mViewTulModel.getId());

        call.enqueue(new retrofit2.Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    if (getIntent().hasExtra("data"))
                        db.deleteTul(String.valueOf(mTulModel.getId()));
                    else
                        db.deleteTul(String.valueOf(mViewTulModel.getId()));

                    Intent intent = new Intent();
                    if (getIntent().hasExtra("position"))
                        intent.putExtra("position", getIntent().getIntExtra("position", 0));
                    if (getIntent().hasExtra("id"))
                        intent.putExtra("id", getIntent().getIntExtra("id", 0));
                    intent.putExtra("is_deleted", true);
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
                        showAlert(llData, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(llData, t.getLocalizedMessage());
            }
        });

    }

    private void hitReportAPI() {
//        Toast.makeText(mContext, R.string.Work_in_progress, Toast.LENGTH_SHORT).show();
        Call<DemoModel> call;
        call = RetrofitClient.getInstance().ReportSele(utils.getString("access_token", ""),
                mViewTulModel.getId()
        );

        call.enqueue(new retrofit2.Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                if (response.body().response != null) {
                    showAlert(llData, response.body().response.getMessage());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llData, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(llData, t.getLocalizedMessage());
            }
        });
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
            ssb.setSpan(new MySpannable(false) {
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
            if (requestCode == CANCEL) {
                /// remove from booking array
                Intent intent = new Intent();
                intent.putExtra("position", getIntent().getIntExtra("position", 0));
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            } else if (requestCode == EDIT_TUL) {
                mViewTulModel = data.getParcelableExtra("tul_data");
                setFullTulData(mViewTulModel);
                isEdit = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void hitGetMoreTulsAPI() {
        if (mViewTulModel.getUser_id() != Integer.parseInt(utils.getString("user_id", ""))) {
            Call<NearByTulListingModel> call = RetrofitClient.getInstance().getMoreToolsSales(utils.getString("access_token", ""),
                    mViewTulModel.getId());
            call.enqueue(new retrofit2.Callback<NearByTulListingModel>() {
                @Override
                public void onResponse(Call<NearByTulListingModel> call, Response<NearByTulListingModel> response) {
                    if (response.body().getResponse() != null) {
                        mMoreTulArrayList.addAll(response.body().getResponse());
                        if (mMoreTulArrayList.size() > 0) {
                            llMoreTuls.setVisibility(View.VISIBLE);
                            rvMoreTuls.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                            mAdapter = new MoreTulAdapter(SalesTulDetailActivity.this, mMoreTulArrayList);
                            rvMoreTuls.setAdapter(mAdapter);
                        } else {
                            llMoreTuls.setVisibility(View.GONE);
                        }
                    } else {
                        if (response.body().error.getCode().equals(errorAccessToken)) {
                            moveToSplash();
                        } else {
                            showAlert(llData, response.body().error.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<NearByTulListingModel> call, Throwable t) {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        Constants.TUL_ID = 0;
        super.onDestroy();
    }

    private void updateProfileDialogs(final String dialogId, final String otherUserId) {
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child(Constants.USER_ENDPOINT).child(utils.getString("user_id", ""))
                .child("chat_dialog_ids").child(dialogId).setValue(dialogId);

        mRef.child(Constants.USER_ENDPOINT).child(otherUserId)
                .child("chat_dialog_ids").child(dialogId).setValue(dialogId);

        db.addDialog(dialogId);
    }

    public void regitserChatDialog(String dialogueId, ViewTulModel.ResponseBean responseBean) {

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        String key = mRef.child(Constants.CHAT_ENDPOINT).push().getKey();

        long time = System.currentTimeMillis();
        Log.e("Current Time = ", time + "");
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        ChatDialogModel mChatDialog = new ChatDialogModel();

//      particpantId = user_id;
        mChatDialog.setChat_dialog_id(key);
        mChatDialog.setLast_message(Constants.CHAT_REGEX);
        mChatDialog.setLast_message_id("");
        mChatDialog.setLast_message_sender_id("");
        mChatDialog.setLast_message_time(String.valueOf(new Date(time).getTime()));
        mChatDialog.setParticipant_ids(dialogueId);
        mChatDialog.setDialog_created_time(String.valueOf(new Date(time).getTime()));

        /// delete dialog time
        Map<String, String> map = new HashMap<String, String>();
        map.put(utils.getString("user_id", ""), String.valueOf(new Date(time).getTime()));
        map.put(String.valueOf(responseBean.getUser_id()), String.valueOf(new Date(time).getTime()));
        mChatDialog.setDelete_dialog_time(map);

        /// access_token
        Map<String, String> mapAccessToken = new HashMap<String, String>();
        mapAccessToken.put(utils.getString("user_id", ""), utils.getString("access_token", ""));
        mapAccessToken.put(String.valueOf(responseBean.getUser_id()), responseBean.getAccess_token());
        mChatDialog.setAccess_token(mapAccessToken);

        /// platform status
        Map<String, String> mapPlatformStatus = new HashMap<String, String>();
        mapPlatformStatus.put(utils.getString("user_id", ""), String.valueOf(Constants.PLATFORM_STATUS));
        mapPlatformStatus.put(String.valueOf(responseBean.getUser_id()), responseBean.getPlatform_status());
        mChatDialog.setPlatform_status(mapPlatformStatus);

        /// profile_pic
        Map<String, String> mapProfilePic = new HashMap<String, String>();
        mapProfilePic.put(utils.getString("user_id", ""), utils.getString("profile_pic", ""));
        mapProfilePic.put(String.valueOf(responseBean.getUser_id()), responseBean.getOwner_pic());
        mChatDialog.setProfile_pic(mapProfilePic);

        /// device_token
        Map<String, String> mapDeviceToken = new HashMap<String, String>();
        mapDeviceToken.put(utils.getString("user_id", ""), utils.getString("device_token", ""));
        mapDeviceToken.put(String.valueOf(responseBean.getUser_id()), responseBean.getDevice_token());
        mChatDialog.setPush_token(mapDeviceToken);

        /// unread count
        Map<String, String> mapUnreadCount = new HashMap<String, String>();
        mapUnreadCount.put(utils.getString("user_id", ""), "0");
        mapUnreadCount.put(String.valueOf(responseBean.getUser_id()), "0");
        mChatDialog.setUnread_count(mapUnreadCount);

        /// name
        Map<String, String> mapName = new HashMap<String, String>();
        mapName.put(utils.getString("user_id", ""), utils.getString("user_name", ""));
        mapName.put(String.valueOf(responseBean.getUser_id()), responseBean.getOwner());
        mChatDialog.setName(mapName);

        /// mute
        Map<String, String> mapMute = new HashMap<String, String>();
        mapMute.put(utils.getString("user_id", ""), Constants.UNMUTE);
        mapMute.put(String.valueOf(responseBean.getUser_id()), Constants.UNMUTE);
        mChatDialog.setMute(mapMute);

        //block status
        Map<String, String> blockmap = new HashMap<String, String>();
        blockmap.put(utils.getString("user_id", ""), "0");
        blockmap.put(String.valueOf(responseBean.getUser_id()), "0");
        mChatDialog.setBlock_status(blockmap);

        //Delete Dialog status
        Map<String, String> deleteDialogMap = new HashMap<String, String>();
        deleteDialogMap.put(utils.getString("user_id", ""), "0");
        deleteDialogMap.put(String.valueOf(responseBean.getUser_id()), "0");
        mChatDialog.setDelete_outer_dialog(deleteDialogMap);

        updateProfileDialogs(key, String.valueOf(responseBean.getUser_id()));
//        updateToServer(key, responseBean.getUser_id());

        mRef.child(Constants.CHAT_ENDPOINT).child(key).setValue(mChatDialog);
        FirebaseListeners.getInstance().startChatListener(key);
        db.addConversation(mChatDialog);

        Intent inChat = new Intent(mContext, ChatActivity.class);
        inChat.putExtra("dialog_id", mChatDialog.getChat_dialog_id());
        inChat.putExtra("name", responseBean.getOwner());
        inChat.putExtra("other_block_status", mChatDialog.getBlock_status().get(responseBean.getUser_id()));
        inChat.putExtra("my_block_status", mChatDialog.getBlock_status().get(utils.getString("user_id", "")));


        dialogApi(key, String.valueOf(responseBean.getUser_id()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat option = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(((Activity) mContext), imgProfile, "profile");
            mContext.startActivity(inChat, option.toBundle());
        } else {
            mContext.startActivity(inChat);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
        CustomLoadingDialog.getLoader().dismissLoader();
        mContext.startActivity(inChat);
    }

    //
    private void checkDialog(final String dialogueId, final ViewTulModel.ResponseBean responseBean) {

        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

        final Query query = mRef.child(Constants.CHAT_ENDPOINT).orderByChild("participant_ids").equalTo("" + dialogueId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isChatDisabled(responseBean.getId(), dialogueId, responseBean, dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print(databaseError);
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }


    void isChatDisabled(int id, final String dialogueId, final ViewTulModel.ResponseBean responseBean, final DataSnapshot dataSnapshot) {
        Call<CheckChatModel> call = RetrofitClient.getInstance().checkChat(utils.getString("access_token", ""), String.valueOf(id));
        call.enqueue(new retrofit2.Callback<CheckChatModel>() {
            @Override
            public void onResponse(Call<CheckChatModel> call, Response<CheckChatModel> response) {
                if (response != null) {
                    CustomLoadingDialog.getLoader().dismissLoader();
                    if (response.body().getResponse().getIs_chat_block() == 0) {
                        if (dataSnapshot.getValue() == null) {
                            regitserChatDialog(dialogueId, responseBean);
                        } else {
                            ChatDialogModel mChat = null;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                mChat = data.getValue(ChatDialogModel.class);
                            }
                            Intent inChat = new Intent(mContext, ChatActivity.class);
                            inChat.putExtra("dialog_id", mChat.getChat_dialog_id());
                            inChat.putExtra("name", responseBean.getOwner());
                            inChat.putExtra("other_block_status", mChat.getBlock_status().get(String.valueOf(responseBean.getUser_id())));
                            inChat.putExtra("my_block_status", mChat.getBlock_status().get(utils.getString("user_id", "")));

                            mContext.startActivity(inChat);
                        }
                    } else {
                        if (response.body().getResponse().getUser_block_status() == 1) {
                            // I am Blocked
                            userBlockDialogUser(mContext);
                        } else if (response.body().getResponse().getOwner_block_status() == 1) {
                            //Other is Blocked
                            chatBlockDialogUser(mContext, getResources().getString(R.string.owner_is_blocked));
                        } else if (response.body().getResponse().getAdmin_pause_status() == 1) {
                            //Place Blocked
                            chatBlockDialogUser(mContext, getResources().getString(R.string.place_is_blocked));
                        }
                    }
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getUser_block_status());

                } else {
                    CustomLoadingDialog.getLoader().dismissLoader();
                    showAlert(llData, response.body().error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CheckChatModel> call, Throwable t) {
                CustomLoadingDialog.getLoader().dismissLoader();
                showAlert(llCancelBooking, t.getMessage());
            }
        });
    }

    void dialogApi(String dialogId, String otherUserId) {
        Call<String> call = RetrofitClient.getInstance().updateDialog(utils.getString("access_token", ""), dialogId, otherUserId);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
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
