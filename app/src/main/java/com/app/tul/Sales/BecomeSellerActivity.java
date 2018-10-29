package com.app.tul.Sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tul.BaseActivity;
import com.app.tul.R;

import butterknife.BindView;
import utils.Constants;

public class BecomeSellerActivity extends BaseActivity {

    private static final int REGGUEST = 3;

    @BindView(R.id.txt_company)
    TextView txtCompany;
    @BindView(R.id.txt_individual)
    TextView txtIndividual;
    @BindView(R.id.img_back)
    ImageView imgBack;

    @Override
    protected int getContentView() {
        return R.layout.activity_become_seller;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initListener() {
        txtCompany.setOnClickListener(this);
        txtIndividual.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        Intent start;
        switch (v.getId()) {
            case R.id.txt_company:
                start = new Intent(mContext, SellerCreateProfileActivity.class);
                start.putExtra(Constants.SALES_MODE, Constants.SALES_MODE_COMPANY);
                ((Activity) mContext).startActivity(start);
                ((Activity) mContext).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                break;
            case R.id.txt_individual:
                start = new Intent(mContext, SellerCreateProfileActivity.class);
                start.putExtra(Constants.SALES_MODE, Constants.SALES_MODE_INDIVIDUAL);
                ((Activity) mContext).startActivity(start);
                ((Activity) mContext).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);

                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

}
