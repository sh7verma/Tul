package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adapters.OtherUserTulAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import customviews.HeaderView;
import model.NearByTulListingModel;
import model.ProfileModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherUserProfileActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {


    private static final int MOVE_BACK = 1;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_header_view)
    HeaderView toolbarHeaderView;

    @BindView(R.id.float_header_view)
    HeaderView floatHeaderView;

    @BindView(R.id.ll_main)
    CoordinatorLayout llMain;

    @BindView(R.id.img_profile)
    ImageView imgProfile;

    @BindView(R.id.ll_tab)
    LinearLayout llTabs;

    @BindView(R.id.txt_tab)
    TextView txtTab;

    @BindView(R.id.rv_tuls)
    RecyclerView rvTul;
    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    LinearLayoutManager layoutManager;
    OtherUserTulAdapter mAdapter;

    ArrayList<NearByTulListingModel.ResponseBean> mToolArray = new ArrayList<>();

    ProfileModel profileModel = new ProfileModel();

    private boolean isHideToolbarView = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_other_user_profile;
    }

    @Override
    protected void initUI() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);

        collapsingToolbarLayout.setTitle(" ");

        appBarLayout.addOnOffsetChangedListener(this);

        CollapsingToolbarLayout.LayoutParams imgParms = new CollapsingToolbarLayout.LayoutParams(mWidth, mHeight / 3);
        imgProfile.setLayoutParams(imgParms);

        imgProfile.setColorFilter(ContextCompat.getColor(mContext, R.color.transperent), android.graphics.PorterDuff.Mode.MULTIPLY);
        layoutManager = new LinearLayoutManager(this);

        rvTul.setLayoutManager(layoutManager);
        txtTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

    }

    @Override
    protected void onCreateStuff() {
        toolbarHeaderView.bindTo(getIntent().getStringExtra("other_user_name"));
        floatHeaderView.bindTo(getIntent().getStringExtra("other_user_name"));

        Picasso.with(mContext).load(getIntent().getStringExtra("other_user_pic"))
                .resize(mHeight / 3, mHeight / 3).centerCrop()
                .placeholder(R.mipmap.ic_image_uploading_tul)
                .into(imgProfile);

        if (connectedToInternet()) {
            hitAPI();
        } else {
            showInternetAlert(llMain);
            progressBar.setVisibility(View.GONE);
            svMain.setVisibility(View.VISIBLE);
            rvTul.setVisibility(View.GONE);
            txtTab.setText(getString(R.string.listed_tuls) + " (" + 0 + ")");
        }
    }

    @Override
    protected void initListener() {
        imgProfile.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return OtherUserProfileActivity.this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            moveBack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_profile:
                ArrayList<String> picArray = new ArrayList<>();
                picArray.add(getIntent().getStringExtra("other_user_pic"));
                Intent fullViewintent = new Intent(mContext, FullViewImageActivity.class);
                fullViewintent.putStringArrayListExtra("pic_array", picArray);
                startActivity(fullViewintent);
                break;
        }
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

    private void hitAPI() {
        Call<NearByTulListingModel> call = RetrofitClient.getInstance().otherUserProfile(utils.getString("access_token", "")
                , getIntent().getIntExtra("other_user_id", 0));
        call.enqueue(new Callback<NearByTulListingModel>() {
            @Override
            public void onResponse(Call<NearByTulListingModel> call, Response<NearByTulListingModel> response) {
                if (response.body().getResponse() != null) {
                    mToolArray.clear();
                    mToolArray.addAll(response.body().getResponse());
                    setData();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NearByTulListingModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setData() {
        if (mToolArray.size() > 0) {
            mAdapter = new OtherUserTulAdapter(OtherUserProfileActivity.this, mToolArray);
            rvTul.setAdapter(mAdapter);
            rvTul.setVisibility(View.VISIBLE);
            svMain.setVisibility(View.GONE);
        } else {
            rvTul.setVisibility(View.GONE);
            svMain.setVisibility(View.VISIBLE);
        }
        txtTab.setText(getString(R.string.listed_tuls) + " (" + mToolArray.size() + ")");
    }

    public void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MOVE_BACK:
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
