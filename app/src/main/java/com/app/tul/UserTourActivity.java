package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.UserTourAdapter;
import butterknife.BindView;
import model.ContentModel;
import utils.Constants;

public class UserTourActivity extends BaseActivity {

    @BindView(R.id.txt_side_status)
    TextView txtSideStatus;
    @BindView(R.id.txt_next)
    TextView txtNext;
    @BindView(R.id.ll_lender)
    LinearLayout llLender;
    @BindView(R.id.rv_demo)
    RecyclerView rvDemo;
    @BindView(R.id.txt_how)
    TextView txtHow;

    private RecyclerView.LayoutManager mLayoutManager;
    private UserTourAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_tour;
    }

    @Override
    protected void initUI() {

        txtHow.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        txtSideStatus.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        txtNext.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        mLayoutManager = new LinearLayoutManager(this);
        rvDemo.setLayoutManager(mLayoutManager);
        mAdapter = new UserTourAdapter(mContext, loadUserData());
        rvDemo.setAdapter(mAdapter);
    }

    @Override
    protected void onCreateStuff() {
    }

    @Override
    protected void initListener() {
        txtNext.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return UserTourActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_next:
                if (txtNext.getText().toString().equalsIgnoreCase(("NEXT"))) {
                    txtNext.setText("GOT IT");

                    mAdapter = new UserTourAdapter(mContext, loadLenderData());
                    rvDemo.setAdapter(mAdapter);
                } else {
                    Intent inStarted = new Intent(mContext, LandingActivity.class);
                    startActivity(inStarted);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                break;
        }
    }

    private ArrayList<ContentModel> loadUserData() {
        ArrayList<ContentModel> loadUserData = new ArrayList<>();

        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {

            ContentModel itemContent = new ContentModel();
            itemContent.setTittle(getString(R.string.search_for_tull));
            itemContent.setDescrption(getString(R.string.search_tull_with_filter));

            ContentModel itemContent1 = new ContentModel();
            itemContent1.setTittle(getString(R.string.book_a_tull));
            itemContent1.setDescrption(getString(R.string.available_tull_displayed));

            ContentModel itemContent2 = new ContentModel();
            itemContent2.setTittle(getString(R.string.check_tull_status));
            itemContent2.setDescrption(getString(R.string.check_status_of_tull));

            loadUserData.add(itemContent);
            loadUserData.add(itemContent1);
            loadUserData.add(itemContent2);

        } else {

            ContentModel itemContent = new ContentModel();
            itemContent.setTittle(getString(R.string.search_for_seller));
            itemContent.setDescrption(getString(R.string.search_seller_with_filter));

            ContentModel itemContent1 = new ContentModel();
            itemContent1.setTittle(getString(R.string.book_a_sell));
            itemContent1.setDescrption(getString(R.string.available_seller_displayed));

            ContentModel itemContent2 = new ContentModel();
            itemContent2.setTittle(getString(R.string.check_tull_status));
            itemContent2.setDescrption(getString(R.string.check_status_of_tull));

            loadUserData.add(itemContent);
            loadUserData.add(itemContent1);
            loadUserData.add(itemContent2);
        }

        return loadUserData;

    }

    private ArrayList<ContentModel> loadLenderData() {

        ArrayList<ContentModel> loadUserData = new ArrayList<>();
        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
            txtSideStatus.setText(getResources().getString(R.string.lender_side));

            ContentModel itemContent = new ContentModel();
            itemContent.setTittle(getString(R.string.become_a_lender));
            itemContent.setDescrption(getString(R.string.tull_with_all_required_information));

            ContentModel itemContent1 = new ContentModel();
            itemContent1.setTittle(getString(R.string.check_tull_status));
            itemContent1.setDescrption(getString(R.string.track_the_tull_status));

            ContentModel itemContent2 = new ContentModel();
            itemContent2.setTittle(getString(R.string.dashboard_));
            itemContent2.setDescrption(getString(R.string.check_your_earning));

            loadUserData.add(itemContent);
            loadUserData.add(itemContent1);
            loadUserData.add(itemContent2);

        } else {
            txtSideStatus.setText(getResources().getString(R.string.seller_side));

            ContentModel itemContent = new ContentModel();
            itemContent.setTittle(getString(R.string.become_a_seller));
            itemContent.setDescrption(getString(R.string.tull_with_all_required_information));

            ContentModel itemContent1 = new ContentModel();
            itemContent1.setTittle(getString(R.string.check_tull_status));
            itemContent1.setDescrption(getString(R.string.track_the_tull_status));

            ContentModel itemContent2 = new ContentModel();
            itemContent2.setTittle(getString(R.string.oderhistory_));
            itemContent2.setDescrption(getString(R.string.check_your_earning));

            loadUserData.add(itemContent);
            loadUserData.add(itemContent1);
            loadUserData.add(itemContent2);
        }
        return loadUserData;
    }

}
