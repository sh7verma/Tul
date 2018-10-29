package com.app.tul.Sales;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.app.tul.BaseActivity;
import com.app.tul.LandingActivity;
import com.app.tul.R;

import api.RetrofitClient;
import butterknife.BindView;

public class SalesBookingCompletedActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.txt_next)
    TextView txtNext;

    String card = RetrofitClient.BASE_URL + "success/";
    String bank = RetrofitClient.BASE_URL + "offline/";

    @Override
    protected int getContentView() {
        return R.layout.activity_sales_booking_completed;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void onCreateStuff() {

        int id = getIntent().getIntExtra("booking_id", 0);

        if (getIntent().getIntExtra("mode", 0) == 1) {
            webView.loadUrl(bank + String.valueOf(id));
        } else {
            webView.loadUrl(card + String.valueOf(id));
        }
    }

    @Override
    protected void initListener() {
        txtNext.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_next:
                moveNext();
                break;
        }

    }

    void moveNext() {
        Intent inSplash = new Intent(mContext, LandingActivity.class);
        finish();
        startActivity(inSplash);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
