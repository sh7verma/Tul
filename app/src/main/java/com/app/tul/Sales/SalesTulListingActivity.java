package com.app.tul.Sales;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.BaseActivity;
import com.app.tul.R;

import java.util.ArrayList;

import adapters.SalesTulListingAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import model.SalesListingTulModel;
import model.SalesTulDetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

/**
 * Created by dev on 22/8/18.
 */

public class SalesTulListingActivity extends BaseActivity {

    private static final int DELETE = 2;

    @BindView(R.id.img_back)
    ImageView imgBckImg;
    //    @BindView(R.id.txt_toolbar_title)
//    TextView txtToolbarTitle;
    @BindView(R.id.rv_tuls)
    RecyclerView rvTuls;
    @BindView(R.id.txt_no_tuls)
    TextView txtNoTuls;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    ArrayList<SalesTulDetailModel.ResponseBean> mNearByTulListingArray = new ArrayList<>();
    SalesTulListingAdapter mAdapter;

    LinearLayoutManager layoutManager;
    boolean isDeleted;
    ArrayList<Integer> deletedIds = new ArrayList<>();
    String mLatitude, mLongitude;

    @Override
    protected int getContentView() {
        return R.layout.activity_sales_listing;
    }

    @Override
    protected void initUI() {
//        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
//        txtToolbarTitle.setText(R.string.near_by_tul);
//        txtToolbarTitle.setAllCaps(false);

        layoutManager = new LinearLayoutManager(this);
        rvTuls.setLayoutManager(layoutManager);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("Search")) {
            mNearByTulListingArray = new ArrayList<>();
            mNearByTulListingArray = intent.getParcelableArrayListExtra("Search");
            mAdapter = new SalesTulListingAdapter(mContext, mNearByTulListingArray);
            rvTuls.setAdapter(mAdapter);

        }
    }

    @Override
    protected void onCreateStuff() {
        if (getIntent().hasExtra("Search")) {
            mNearByTulListingArray = new ArrayList<>();
            mNearByTulListingArray = getIntent().getParcelableArrayListExtra("Search");
            mAdapter = new SalesTulListingAdapter(mContext, mNearByTulListingArray);
            rvTuls.setAdapter(mAdapter);

        } else if (getIntent().hasExtra("tulIds")) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : getIntent().getStringArrayListExtra("tulIds")) {
                stringBuilder.append(id + ",");
            }
            String ids = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
            if (connectedToInternet())
                hitAPI(ids);
            else {
                showInternetAlert(rvTuls);
                if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
                    txtNoTuls.setVisibility(View.VISIBLE);
                }
            }
        } else {
            mLatitude = getIntent().getStringExtra("latitude");
            mLongitude = getIntent().getStringExtra("longitude");
            if (connectedToInternet())
                hitTulListAPI();
            else {
                showInternetAlert(rvTuls);
                if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
                    txtNoTuls.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void hitAPI(String ids) {
        showProgress();
        Call<SalesListingTulModel> call = RetrofitClient.getInstance().getNearByToolsListSales(utils.getString("access_token", ""), ids);
        call.enqueue(new Callback<SalesListingTulModel>() {
            @Override
            public void onResponse(Call<SalesListingTulModel> call, Response<SalesListingTulModel> response) {
                if (response.body().getResponse() != null) {
                    mNearByTulListingArray.addAll(response.body().getResponse());
                    mAdapter = new SalesTulListingAdapter(mContext, mNearByTulListingArray);
                    rvTuls.setAdapter(mAdapter);
                    if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
                        txtNoTuls.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (String.valueOf(response.body().getCode()).equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, utils);
                    } else {
                        showAlert(rvTuls, String.valueOf(response.body().getCode()));
                        if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
                            txtNoTuls.setVisibility(View.VISIBLE);
                        }
                    }
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<SalesListingTulModel> call, Throwable t) {
                showAlert(rvTuls, t.getLocalizedMessage());
                hideProgress();
                if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
                    txtNoTuls.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void hitTulListAPI() {
        showProgress();
        Call<SalesListingTulModel> call = RetrofitClient.getInstance().sellerTullistview(utils.getString("access_token", ""), mLatitude, mLongitude);
        call.enqueue(new Callback<SalesListingTulModel>() {
            @Override
            public void onResponse(Call<SalesListingTulModel> call, Response<SalesListingTulModel> response) {
                if (response.body().getResponse() != null) {
                    mNearByTulListingArray.addAll(response.body().getResponse());
                    mAdapter = new SalesTulListingAdapter(mContext, mNearByTulListingArray);
                    rvTuls.setAdapter(mAdapter);
                    if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
                        txtNoTuls.setVisibility(View.VISIBLE);
                    }

                } else {
                    if (String.valueOf(response.body().getCode()).equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, utils);
                    } else {
                        showAlert(rvTuls, String.valueOf(response.body().getCode()));
                        if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
                            txtNoTuls.setVisibility(View.VISIBLE);
                        }
                    }
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<SalesListingTulModel> call, Throwable t) {
                showAlert(rvTuls, t.getLocalizedMessage());
                hideProgress();
                if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
                    txtNoTuls.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
        llSearch.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
            case R.id.ll_search:
                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) != Constants.USER_RENTAL) {
                    Intent searchIntent = new Intent(mContext, SalesSearchActivity.class);
//                    searchIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(searchIntent);
//                    finish();
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }
                break;
        }
    }

    private void moveBack() {
//        if (isDeleted) {
//            Intent intent = new Intent();
//            intent.putIntegerArrayListExtra("deletedIds", deletedIds);
//            setResult(RESULT_OK, intent);
//        } else if (getIntent().hasExtra("getdataonback")) {
//            Intent intent = new Intent();
//            setResult(RESULT_OK, intent);
//        }
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            {
                if (requestCode == DELETE) {
                    int deletePosition = data.getIntExtra("position", 0);
                    if (data.hasExtra("is_deleted")) {
                        deletedIds.add(mNearByTulListingArray.get(deletePosition).getId());
                        mNearByTulListingArray.remove(data.getIntExtra("position", 0));
                        mAdapter.notifyDataSetChanged();
                        isDeleted = true;
                    } else if (data.hasExtra("tul_data")) {
                        /// getting back from after editing tul.
                        SalesTulDetailModel.ResponseBean tulResponse = data.getParcelableExtra("tul_data");
                        mNearByTulListingArray.get(deletePosition).setId(tulResponse.getId());
                        mNearByTulListingArray.get(deletePosition).setTitle(tulResponse.getTitle());
                        mNearByTulListingArray.get(deletePosition).setRating(tulResponse.getRating());
                        mNearByTulListingArray.get(deletePosition).setUser_id(tulResponse.getUser_id());
                        mNearByTulListingArray.get(deletePosition).setPrice(tulResponse.getPrice());
                        mNearByTulListingArray.get(deletePosition).setOwner(tulResponse.getOwner());
                        mNearByTulListingArray.get(deletePosition).setOwner_pic(tulResponse.getOwner_pic());
                        mNearByTulListingArray.get(deletePosition).setAttachment(tulResponse.getAttachment());
                        mNearByTulListingArray.get(deletePosition).setIs_wishlisted(tulResponse.getIs_wishlisted());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        Constants.PROFILE_ID = 0;
        super.onDestroy();
    }
}
