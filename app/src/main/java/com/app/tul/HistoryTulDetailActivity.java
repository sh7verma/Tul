package com.app.tul;


import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;

import adapters.TulDetailImagesAdapter;
import adapters.TulHistoryDetailPagerAdapter;
import butterknife.BindView;
import fragments.HistoryHelpFragment;
import fragments.HistoryReceiptFragment;
import me.relex.circleindicator.CircleIndicator;
import model.AttachmentModel;
import model.BookTulModel;
import utils.Constants;

public class HistoryTulDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {


    @BindView(R.id.cl_main)
    CoordinatorLayout clMain;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.rv_pager)
    RelativeLayout rvPager;
    @BindView(R.id.vp_tul)
    ViewPager vpTul;
    @BindView(R.id.cp_indicator)
    CircleIndicator cpIndicator;


    @BindView(R.id.ll_toolbar_name)
    LinearLayout llToolbarName;
    @BindView(R.id.tv_toolbar_name)
    TextView tvToolbarName;

    @BindView(R.id.ll_name)
    LinearLayout llTulName;
    @BindView(R.id.tv_tul_name)
    TextView tvTulName;
    @BindView(R.id.tv_tul_price)
    TextView tvTulPrice;

    @BindView(R.id.txt_bookings)
    TextView txtBookings;

    @BindView(R.id.ll_tul_lent)
    LinearLayout llTulLent;
    @BindView(R.id.tv_lend_title)
    TextView tvLentTitle;
    @BindView(R.id.tv_returned_title)
    TextView tvReturnedTitle;

    @BindView(R.id.ll_dates)
    LinearLayout llDates;
    @BindView(R.id.tv_lend_date)
    TextView tvLendDate;
    @BindView(R.id.tv_returned_date)
    TextView tvReturnedDate;

    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;

    @BindView(R.id.ll_rating)
    LinearLayout llRating;
    @BindView(R.id.tv_rating_title)
    TextView tvRatingTitle;

    @BindView(R.id.rl_stars)
    RelativeLayout rlStars;
    @BindView(R.id.ll_stars)
    LinearLayout llStars;
    @BindView(R.id.srb_tul_rating)
    SimpleRatingBar srbRating;

    @BindView(R.id.tl_tul_history_detail)
    TabLayout tabs;
    @BindView(R.id.vp_tul_history)
    ViewPager vpTulHistory;

    TulHistoryDetailPagerAdapter mAdapter;
    BookTulModel.ResponseBean mTulModel;

    private boolean isHideToolbarView = false;
    private int mPath=0;


    @Override
    protected int getContentView() {
        return R.layout.activity_history_tul_detail;
    }

    @Override
    protected void initUI() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);

        collapsingToolbar.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(this);

        LinearLayout.LayoutParams rvParams = new LinearLayout.LayoutParams(mWidth, mHeight / 3);
        rvPager.setLayoutParams(rvParams);

        RelativeLayout.LayoutParams imgParms = new RelativeLayout.LayoutParams(mWidth, mHeight / 3);
        vpTul.setLayoutParams(imgParms);

        tvToolbarName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        llTulName.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mWidth / 32);
        tvTulName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        tvTulPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        txtBookings.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtBookings.setPadding(mWidth / 32, 0, mWidth / 32, mWidth / 32);

        llTulLent.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, 0);
        tvLentTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
        tvReturnedTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));

        llDates.setPadding(mWidth / 32, 0, mWidth / 32, mHeight / 32);
        tvLendDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvReturnedDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        llRating.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);
        tvRatingTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        LinearLayout.LayoutParams starParms = new LinearLayout.LayoutParams((int) (mWidth * .4), mHeight / 32);
        rlStars.setLayoutParams(starParms);

        RelativeLayout.LayoutParams insideStarParms = new RelativeLayout.LayoutParams((int) (mWidth * .4), mHeight / 32);
        llStars.setLayoutParams(insideStarParms);

        srbRating.setStarSize(mWidth / 26);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.setMargins(mWidth / 32, 0, mWidth / 32, 0);
        view1.setLayoutParams(layoutParams);
        view2.setLayoutParams(layoutParams);

        mAdapter = new TulHistoryDetailPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new HistoryHelpFragment(), getString(R.string.HELP));
        mAdapter.addFragment(new HistoryReceiptFragment(), getString(R.string.RECEIPT));
        vpTulHistory.setAdapter(mAdapter);

        tabs.setupWithViewPager(vpTulHistory);
    }

    @Override
    protected void onCreateStuff() {
        mTulModel = getIntent().getParcelableExtra("data");

        if (getIntent().getIntExtra("path", 0) == 1) {
            /// borrower
            mPath=1;
            tvLentTitle.setText("RENTED ON");
            tvReturnedTitle.setText("RETURNED ON");
            try {
                tvLendDate.setText(Constants.convertDate(mTulModel.getBorrower_received_at()));
                tvReturnedDate.setText(Constants.convertDate(mTulModel.getBorrower_returned_at()));

            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } else {
            /// lender
            mPath=2;
            tvLentTitle.setText("LENT ON");
            tvReturnedTitle.setText("RECEIVED ON");
            try {
                tvLendDate.setText(Constants.convertDate(mTulModel.getHandover_at()));
                tvReturnedDate.setText(Constants.convertDate(mTulModel.getLander_received_at()));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

        }
        tvTulName.setText(mTulModel.getTitle());
        txtBookings.setText("No of Bookings: " + mTulModel.getQuantity());
        tvToolbarName.setText(mTulModel.getTitle());
        tvTulPrice.setText(mTulModel.getCurrency() + " " + mTulModel.getPrice());
        srbRating.setRating(mTulModel.getRating());
        try {
            if (mTulModel.getBorrower_status() == 3 || mTulModel.getLender_status() == 3) {
                tvLentTitle.setText("CANCELLED ON");
                tvLendDate.setText(Constants.convertDate(mTulModel.getCancelled_at()));
                tvReturnedTitle.setVisibility(View.INVISIBLE);
                tvReturnedDate.setVisibility(View.INVISIBLE);
                llRating.setVisibility(View.GONE);
            } else {
                llRating.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Log.e("Exce = ", e + "");
        }

        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) mTulModel.getAttachment()));
        cpIndicator.setViewPager(vpTul);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((HistoryReceiptFragment) mAdapter.getItem(1)).setdata(mTulModel,mPath);
            }
        }, 1000);

    }

    @Override
    protected void initListener() {
        llStars.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                finishAfterTransition();
            else
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Context getContext() {
        return HistoryTulDetailActivity.this;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(i) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            llToolbarName.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            llToolbarName.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }
}
