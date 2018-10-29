package com.app.tul.Sales;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.tul.BaseActivity;
import com.app.tul.R;

import java.text.ParseException;
import java.util.ArrayList;

import adapters.TulDetailImagesAdapter;
import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;
import model.AttachmentModel;
import model.SalesHistoryTulDetailModel;
import utils.Constants;

public class SalesHistoryTulDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

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

    @BindView(R.id.tv_tul_name)
    TextView tvTulName;
    @BindView(R.id.tv_tul_price)
    TextView tvTulPrice;
    @BindView(R.id.txt_order_date)
    TextView txtOrderDate;
    @BindView(R.id.tv_delivered_on)
    TextView txtDeliveredOn;
    @BindView(R.id.tv_delivered_date)
    TextView txtDeliveryDate;
    @BindView(R.id.txt_delivered_address_lable)
    TextView txtDeliveredAddressLable;
    @BindView(R.id.tv_delivered_address)
    TextView txtDeliveredAddress;
    @BindView(R.id.tv_payment_method)
    TextView txtPaymentMethod;
    @BindView(R.id.txt_tool_name)
    TextView txtToolName;
    @BindView(R.id.txt_quantity)
    TextView txtQuantity;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_delivery_charges)
    TextView txtDeliveryCharges;
    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;
    @BindView(R.id.ll_delivery_charges)
    LinearLayout llDeliveryCharges;
    @BindView(R.id.txt_delivery_status)
    TextView txtDeliveryStatus;
    @BindView(R.id.ll_delivery_address)
    LinearLayout llDeliveryAddress;
    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.ll_send_receipt)
    LinearLayout llSendReceipt;
    @BindView(R.id.ll_delivery_date)
    LinearLayout llDeliveryDate;

    SalesHistoryTulDetailModel.ResponseBean mTulModel;

    private boolean isHideToolbarView = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_sales_history_tul_detail;
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

        tvTulName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

    }

    @Override
    protected void onCreateStuff() {
        mTulModel = getIntent().getParcelableExtra("data");

        tvTulName.setText(mTulModel.getTitle());
        tvTulPrice.setText(mTulModel.getCurrency() + " " + mTulModel.getTotal_amount());

        try {
            txtOrderDate.setText(Constants.convertDateSalesTime(mTulModel.getCreated_at()));
            txtDeliveryDate.setText(Constants.convertDateSalesTime(mTulModel.getUpdated_at()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtDeliveredAddress.setText(mTulModel.getAddress());

        if (mTulModel.getPayment_mode() == 0) {
            txtPaymentMethod.setText(getString(R.string.card_payment));
        } else {
            txtPaymentMethod.setText(getString(R.string.bank_transfer));
        }

        if (mTulModel.getDelivery() == 0) {
            llDeliveryCharges.setVisibility(View.GONE);
            llDeliveryAddress.setVisibility(View.GONE);
        }

        txtToolName.setText(mTulModel.getTitle());
        txtQuantity.setText(getString(R.string.qty_) + " " + mTulModel.getQuantity());
        txtPrice.setText(mTulModel.getCurrency() + " " + mTulModel.getPrice());
        txtDeliveryCharges.setText(mTulModel.getCurrency() + " " + mTulModel.getDelivery_cost());
        txtTotalAmount.setText(mTulModel.getCurrency() + " " + mTulModel.getTotal_amount());

        if (mTulModel.getOrder_status().equals("0")) {
            txtDeliveryStatus.setText(mContext.getString(R.string.in_progress));
            llSendReceipt.setVisibility(View.VISIBLE);
            llDeliveryDate.setVisibility(View.GONE);
        } else if (mTulModel.getOrder_status().equals("1")) {
            txtDeliveryStatus.setText(mContext.getString(R.string.delivered_));
            llSendReceipt.setVisibility(View.GONE);
            txtDeliveredOn.setText(R.string.delivered_on);
            llDeliveryDate.setVisibility(View.VISIBLE);
        } else if (mTulModel.getOrder_status().equals("2")) {
            txtDeliveryStatus.setText(mContext.getString(R.string.cancelled));
            llSendReceipt.setVisibility(View.GONE);
            txtDeliveredOn.setText(R.string.cancelled_on_);
            llDeliveryDate.setVisibility(View.VISIBLE);
        }

        svMain.setNestedScrollingEnabled(true);

        vpTul.setAdapter(new TulDetailImagesAdapter(mContext, (ArrayList<AttachmentModel>) mTulModel.getAttachment()));
        cpIndicator.setViewPager(vpTul);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {

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
