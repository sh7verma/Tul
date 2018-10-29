package com.app.tul;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.NearByTulListingAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import model.NearByTulListingModel;
import model.SearchResultModel;
import model.TulImageModel;
import model.ViewTulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class CategoryTulListAcitivty extends BaseActivity {

    private static final int SEARCH = 3;
    private static final int DELETE = 2;
    private int CATEGORY = 1;

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.tv_filter_count)
    TextView tvFilterCount;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.ll_category)
    LinearLayout llCategory;

    @BindView(R.id.rv_tuls)
    RecyclerView rvTuls;
    @BindView(R.id.txt_no_tuls)
    TextView txtNoTuls;

    ArrayList<NearByTulListingModel.ResponseBean> mNearByTulListingArray = new ArrayList<>();
    NearByTulListingAdapter mAdapter;

    ArrayList<Integer> deletedIds = new ArrayList<>();
    boolean isDeleted;

    LinearLayoutManager layoutManager;
    private boolean isLoading;

    private int mOffset = 0;
    private String mSelectedCat;
    private int mSelectedCatId;

    @Override
    protected int getContentView() {
        return R.layout.activity_category_tul_list;
    }

    @Override
    protected void initUI() {
        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setAllCaps(false);

        tvFilterCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));

        layoutManager = new LinearLayoutManager(this);
        rvTuls.setLayoutManager(layoutManager);
        rvTuls.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisibleItems = layoutManager.findLastVisibleItemPosition();
                if (mNearByTulListingArray.size() > 9) {
                    if (!isLoading && pastVisibleItems == mNearByTulListingArray.size() - 1) {
                        ///visible loader
                        mOffset++;
                        mNearByTulListingArray.add(null);
                        recyclerView.post(new Runnable() {
                            public void run() {
                                mAdapter.notifyItemInserted(mNearByTulListingArray.size() - 1);
                            }
                        });
                        hitAPI(getIntent().getStringExtra("id"));
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    protected void onCreateStuff() {
        mSelectedCat = getIntent().getStringExtra("category");
        mSelectedCatId = Integer.parseInt(getIntent().getStringExtra("id"));

        txtToolbarTitle.setText(getIntent().getStringExtra("category"));

        if (connectedToInternet())
            hitAPI(getIntent().getStringExtra("id"));
        else
            showInternetAlert(rvTuls);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        llCategory.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return CategoryTulListAcitivty.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
            case R.id.img_search:
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra("path", "category");
                intent.putExtra("category_id", mSelectedCatId);
                intent.putExtra("category", mSelectedCat);
                startActivityForResult(intent, SEARCH);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            case R.id.ll_category:
                Intent catIntent = new Intent(mContext, SelectCategoryActivity.class);
                catIntent.putExtra("selected_category", txtToolbarTitle.getText().toString());
                catIntent.putExtra("selected_categoryId", mSelectedCatId);
                startActivityForResult(catIntent, CATEGORY);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }

    private void hitAPI(String id) {
        if (mOffset == 0)
            showProgress();
        Call<NearByTulListingModel> call = RetrofitClient.getInstance().getToolsByCategory(utils.getString("access_token", ""), id, mOffset);
        call.enqueue(new Callback<NearByTulListingModel>() {
            @Override
            public void onResponse(Call<NearByTulListingModel> call, Response<NearByTulListingModel> response) {
                if (response.body().getResponse() != null) {
                    if (mOffset == 0) {
                        if (response.body().getResponse().size() > 0) {
                            txtNoTuls.setVisibility(View.GONE);
                            rvTuls.setVisibility(View.VISIBLE);
                            mNearByTulListingArray.clear();
                            mNearByTulListingArray.addAll(response.body().getResponse());
                            mAdapter = new NearByTulListingAdapter(mContext, mNearByTulListingArray);
                            rvTuls.setAdapter(mAdapter);
                        } else {
                            txtNoTuls.setVisibility(View.VISIBLE);
                            rvTuls.setVisibility(View.GONE);
                        }
                    } else {
                        mNearByTulListingArray.remove(mNearByTulListingArray.size() - 1);
                        mAdapter.notifyItemRemoved(mNearByTulListingArray.size());
                        mNearByTulListingArray.addAll(response.body().getResponse());
                        mAdapter.notifyDataSetChanged();
                        isLoading = false;
                    }
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, utils);
                    } else {
                        showAlert(rvTuls, response.body().error.getMessage());
                    }
                }
                if (mOffset == 0)
                    hideProgress();
            }

            @Override
            public void onFailure(Call<NearByTulListingModel> call, Throwable t) {
                showAlert(rvTuls, t.getLocalizedMessage());
                if (mOffset == 0)
                    hideProgress();
            }
        });
    }

    private void moveBack() {
        if (isDeleted) {
            Intent intent = new Intent();
            intent.putIntegerArrayListExtra("deletedIds", deletedIds);
            setResult(RESULT_OK, intent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
        }
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CATEGORY) {
                txtToolbarTitle.setText(data.getStringExtra("catName"));
                mSelectedCatId = data.getIntExtra("catId", 0);
                mSelectedCat = data.getStringExtra("catName");
                mOffset = 0;
                resetValues();
                rvTuls.setAdapter(null);
                if (connectedToInternet())
                    hitAPI(String.valueOf(mSelectedCatId));
                else
                    showAlert(llCategory, errorInternet);
            } else if (requestCode == DELETE) {
                int deletePosition = data.getIntExtra("position", 0);
                if (data.hasExtra("is_deleted")) {
                    deletedIds.add(mNearByTulListingArray.get(deletePosition).getId());
                    mNearByTulListingArray.remove(data.getIntExtra("position", 0));
                    mAdapter.notifyDataSetChanged();
                    isDeleted = true;
                } else if (data.hasExtra("tul_data")) {
                    /// getting back from after editing tul.
                    ViewTulModel.ResponseBean tulResponse = data.getParcelableExtra("tul_data");
                    mNearByTulListingArray.get(deletePosition).setId(tulResponse.getId());
                    mNearByTulListingArray.get(deletePosition).setTitle(tulResponse.getTitle());
                    mNearByTulListingArray.get(deletePosition).setRating(tulResponse.getRating());
                    mNearByTulListingArray.get(deletePosition).setUser_id(tulResponse.getUser_id());
                    mNearByTulListingArray.get(deletePosition).setPrice(tulResponse.getPrice());
                    mNearByTulListingArray.get(deletePosition).setOwner(tulResponse.getOwner());
                    mNearByTulListingArray.get(deletePosition).setOwner_pic(tulResponse.getOwner_pic());
                    mNearByTulListingArray.get(deletePosition).setAttachment(tulResponse.getAttachment());
                    mAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == SEARCH) {
                if (Constants.CATEGORY_TITLE == null) {
                    mOffset = 0;
                    rvTuls.setAdapter(null);
                    tvFilterCount.setVisibility(View.GONE);
                    if (connectedToInternet())
                        hitAPI(String.valueOf(mSelectedCatId));
                    else
                        showAlert(llCategory, errorInternet);
                } else {
                    if (Constants.CATEGORY_FILTER_COUNT != 0) {
                        tvFilterCount.setVisibility(View.VISIBLE);
                        tvFilterCount.setText(String.valueOf(Constants.CATEGORY_FILTER_COUNT));
                    } else {
                        tvFilterCount.setVisibility(View.GONE);
                    }
                    if (connectedToInternet())
                        hitSearchAPI();
                    else
                        showInternetAlert(llCategory);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void hitSearchAPI() {
        showProgress();

        int bestRated = 0;
        if (Constants.CATEGORY_BEST_RATED)
            bestRated = 1;

        double mlatitude, mLongitude;

        if (Constants.CATEGORY_LATITUDE == 0.0 && Constants.CATEGORY_LONGITUDE == 0.0) {
            mlatitude = Double.parseDouble(utils.getString("latitude", ""));
            mLongitude = Double.parseDouble(utils.getString("longitude", ""));
        } else {
            mlatitude = Constants.CATEGORY_LATITUDE;
            mLongitude = Constants.CATEGORY_LONGITUDE;
        }

        rvTuls.setAdapter(null);
        Call<NearByTulListingModel> call = RetrofitClient.getInstance().searchTulByCategory(utils.getString("access_token", ""),
                Constants.CATEGORY_TITLE, Constants.CATEGORY_ID, Constants.CATEGORY_DELIVERY_TYPE, Constants.CATEGORY_MAX_PRICE_SEARCH,
                Constants.CATEGORY_MIN_PRICE_SEARCH, Constants.CATEGORY_AVAILABILTY, String.valueOf(mlatitude), String.valueOf(mLongitude),
                bestRated, Constants.CATEGORY_DISTANCE);
        call.enqueue(new Callback<NearByTulListingModel>() {
            @Override
            public void onResponse(Call<NearByTulListingModel> call, Response<NearByTulListingModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    if (response.body().getResponse().size() == 0) {
                        rvTuls.setVisibility(View.GONE);
                        txtNoTuls.setVisibility(View.VISIBLE);
                    } else {
                        rvTuls.setVisibility(View.VISIBLE);
                        txtNoTuls.setVisibility(View.GONE);
                        mNearByTulListingArray.clear();
                        mNearByTulListingArray.addAll(response.body().getResponse());
                        mAdapter = new NearByTulListingAdapter(mContext, mNearByTulListingArray);
                        rvTuls.setAdapter(mAdapter);
                    }
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, utils);
                    } else {
                        showAlert(imgBack, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<NearByTulListingModel> call, Throwable t) {
                hideProgress();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Constants.PROFILE_ID = 0;
        Constants.CATEGORY_TITLE = null;
        super.onDestroy();
    }

    private void resetValues() {
        Constants.CATEGORY_TITLE = "";
        Constants.CATEGORY_ID = 0;
        Constants.CATEGORY = "";
        Constants.CATEGORY_MAX_PRICE_SEARCH = 999;
        Constants.CATEGORY_MIN_PRICE_SEARCH = 0;
        Constants.CATEGORY_AVAILABILTY = 0;
        Constants.CATEGORY_DELIVERY_TYPE = 2;
        Constants.CATEGORY_LATITUDE = 0.0;
        Constants.CATEGORY_LONGITUDE = 0.0;
        Constants.CATEGORY_ADDRESS = "";
        Constants.CATEGORY_FILTER_COUNT = 0;

        if (Constants.CATEGORY_FILTER_COUNT != 0) {
            tvFilterCount.setVisibility(View.VISIBLE);
            tvFilterCount.setText(String.valueOf(Constants.CATEGORY_FILTER_COUNT));
        } else {
            tvFilterCount.setVisibility(View.GONE);
        }

    }


}
