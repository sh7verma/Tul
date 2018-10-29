package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import adapters.WalkthroughAdapter;
import butterknife.BindView;
import customviews.CirclePageIndicator;

public class WalkthroughActivity extends BaseActivity {

    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.cp_indicator)
    CirclePageIndicator cpIndicator;
    @BindView(R.id.txt_start)
    TextView txtStart;
    @BindView(R.id.vp_walk)
    ViewPager vpWalk;

    private WalkthroughAdapter mAdapter;
    private int[] walkArray = {R.mipmap.walk1, R.mipmap.walk2, R.mipmap.walk3};
    private Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (vpWalk.getCurrentItem() == vpWalk.getAdapter().getCount() - 1) {
                vpWalk.setCurrentItem(0);
            } else {
                vpWalk.setCurrentItem(vpWalk.getCurrentItem() + 1);
            }
            mHandler.postDelayed(this, 8000);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_walkthrough;
    }

    @Override
    protected void initUI() {
        txtStart.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
    }

    @Override
    protected void onCreateStuff() {
        mAdapter = new WalkthroughAdapter(mContext, walkArray);
        vpWalk.setAdapter(mAdapter);
        cpIndicator.setViewPager(vpWalk);
        cpIndicator.setSnap(true);
//        vpWalk.setPageTransformer(true, new DepthPageTransformer());
    }

    @Override
    protected void onResume() {
        mHandler.postDelayed(runnable, 8000);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    protected void initListener() {
        txtStart.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return WalkthroughActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_start:

                Intent start = new Intent(mContext, UserModeActivity.class);
                startActivity(start);
                finish();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

//                Intent start = new Intent(mContext, AfterWalkthroughActivity.class);
//                startActivity(start);
//                finish();
//                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                break;
        }
    }


}
