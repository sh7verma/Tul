package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import adapters.PaymentPagerAdapter;
import butterknife.BindView;
import fragments.PaymentAccountFragment;
import fragments.PaymentCardFragment;

public class PaymentActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBckImg;

    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.tl_profile)
    TabLayout tabs;
    @BindView(R.id.vp_profile)
    ViewPager pager;

    PaymentPagerAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        txtToolbarTitle.setText(R.string.payment);

        mAdapter = new PaymentPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new PaymentCardFragment(), getString(R.string.saved_cards));
        mAdapter.addFragment(new PaymentAccountFragment(), getString(R.string.accounts));
        pager.setAdapter(mAdapter);
        tabs.setupWithViewPager(pager);
    }

    @Override
    protected void onCreateStuff() {
    }

    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return PaymentActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    private void moveBack() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }
}
