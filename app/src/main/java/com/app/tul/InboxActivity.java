package com.app.tul;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import adapters.ProfileViewpagerAdapter;
import butterknife.BindView;
import fragments.InboxMessagesFragment;
import fragments.InboxNotificationFragment;

public class InboxActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView tvTitle;

    @BindView(R.id.tl_inbox)
    TabLayout tabs;
    @BindView(R.id.vp_inbox)
    ViewPager pager;
    @BindView(R.id.img_search)
    ImageView imgSearch;

    ProfileViewpagerAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_inbox;
    }

    @Override
    protected void initUI() {
        tvTitle.setText("INBOX");
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));

        mAdapter = new ProfileViewpagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new InboxMessagesFragment(), getString(R.string.MESSAGES));
        mAdapter.addFragment(new InboxNotificationFragment(), getString(R.string.NOTIFICATIONs));
        pager.setAdapter(mAdapter);
        tabs.setupWithViewPager(pager);
    }

    @Override
    protected void onCreateStuff() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
                    imgSearch.setVisibility(View.INVISIBLE);
                }else {
                    imgSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return InboxActivity.this;
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
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }
}
