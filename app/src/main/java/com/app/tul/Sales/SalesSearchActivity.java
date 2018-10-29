package com.app.tul.Sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.SwitchCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.BaseActivity;
import com.app.tul.LocationSearchActivity;
import com.app.tul.OptionSelection;
import com.app.tul.R;

import java.util.ArrayList;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.RangeSeekBar;
import model.SalesListingTulModel;
import model.SalesSearchModel;
import model.SalesTulDetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesSearchActivity extends BaseActivity {

    static final int CATEGORY = 1;
    static final int LOCATION = 2;
    private static final int CONDITION = 3;

    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.img_cross)
    ImageView imgCross;

    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.tv_clear)
    TextView tvClear;

    @BindView(R.id.ll_upper)
    LinearLayout llUpper;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.tv_condition)
    TextView tvCondition;
    @BindView(R.id.tv_location)
    TextView tvLocation;

    @BindView(R.id.tv_best_rating)
    TextView tvBestRating;
    @BindView(R.id.sc_rated)
    SwitchCompat scRated;

    @BindView(R.id.ll_price_range)
    LinearLayout llPriceRange;
    @BindView(R.id.tv_price_lable)
    TextView tvPriceLable;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.seekbar_price)
    RangeSeekBar seekBar;

    @BindView(R.id.ll_distance_range)
    LinearLayout llDistanceRange;
    @BindView(R.id.tv_distance_label)
    TextView tvDistanceLabel;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.seekbar_distance)
    RangeSeekBar seekBarDistance;

    @BindView(R.id.tv_done)
    TextView tvDone;
    SalesSearchModel mSearchModel;
    ArrayList<SalesTulDetailModel.ResponseBean> mNearByTulListingArray = new ArrayList<>();
    private String mAddress = "", mCondition = "";
    private int minPrice = 0, maxPrice = 999, mDistance = 101;
    private double mLatitude = 0.0, mLongitude = 0.0;

    @Override
    protected int getContentView() {
        return R.layout.activity_sales_search;
    }

    @Override
    protected void initUI() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/regular.ttf");

        llUpper.setPadding(mWidth / 26, mHeight / 42, mWidth / 26, mHeight / 42);

        tvToolbar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));


        tvClear.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, mHeight / 42);

        edSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        edSearch.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        edSearch.setLayoutParams(layoutParams);
        edSearch.setCompoundDrawablePadding(mWidth / 32);
        edSearch.setTypeface(typeface);

        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams3.setMargins(0, 0, 0, mHeight / 42);

        tvCondition.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvCondition.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvCondition.setLayoutParams(layoutParams3);
        tvCondition.setCompoundDrawablePadding(mWidth / 32);

        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams5.setMargins(0, 0, 0, mHeight / 42);

        tvLocation.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvLocation.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvLocation.setLayoutParams(layoutParams5);
        tvLocation.setCompoundDrawablePadding(mWidth / 32);

        tvBestRating.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvBestRating.setPadding(mWidth / 32, mHeight / 32, 0, mHeight / 52);

        llPriceRange.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        tvPriceLable.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvPrice.setText(utils.getBothCurrency() + " " + 0 + "-" + 999 + "+");

        llDistanceRange.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, 0);

        tvDistanceLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        tvDistance.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        tvDistance.setText("100 Miles/Km+");


        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams4.setMargins(mWidth / 32, 0, mWidth / 32, mHeight / 42);

        tvDone.setPadding(0, mWidth / 26, 0, mWidth / 26);
        tvDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvDone.setLayoutParams(layoutParams4);

    }

    @Override
    protected void onCreateStuff() {

        setDate();
        seekBar.setNotifyWhileDragging(true);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                tvPrice.setText(utils.getBothCurrency() + " " + minValue + "-" + maxValue);
                maxPrice = (int) maxValue;
                minPrice = (int) minValue;
                if (maxPrice == 999) {
                    tvPrice.setText(utils.getBothCurrency() + " " + minValue + "-" + maxValue + "+");
                }
            }
        });


        seekBarDistance.setNotifyWhileDragging(true);
        seekBarDistance.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
//                if (utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, "")).equals(Constants.POUND)) {
                tvDistance.setText(maxValue + " Miles/Km");
//                } else {
//                    tvDistance.setText(maxValue + " Km");
//                }

                mDistance = (int) maxValue;
                if (mDistance == 100) {
                    tvDistance.setText(maxValue + " Miles/Km+");
                }
            }
        });


    }

    void setDate() {
        mSearchModel = SalesSearchModel.getInstance();

        mDistance = mSearchModel.distance;
        mLatitude = mSearchModel.latitude;
        mLongitude = mSearchModel.longitude;
        minPrice = mSearchModel.minPrice;
        maxPrice = mSearchModel.maxPrice;
        mAddress = mSearchModel.address;
        mCondition = mSearchModel.condition;

        edSearch.setText(mSearchModel.title);

        edSearch.setSelection(mSearchModel.title.length());

        scRated.setChecked(mSearchModel.bestRated);

        seekBar.setSelectedMaxValue(mSearchModel.maxPrice);
        seekBar.setSelectedMinValue(mSearchModel.minPrice);

        seekBarDistance.setSelectedMaxValue(mSearchModel.distance);

        tvLocation.setText(mSearchModel.address);
        tvCondition.setText(mSearchModel.condition);

//        if (utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, "")).equals(Constants.POUND)) {
//            if (mSearchModel.distance == 101) {
//                tvDistance.setText("100 Miles");
//            } else {
//                tvDistance.setText(mSearchModel.distance + " Miles");
//            }
//        } else {
        if (mSearchModel.distance == 101) {
            tvDistance.setText("100 Miles/Km+");
        } else {
            tvDistance.setText(mSearchModel.distance + " Miles/Km");
        }
//        }

        tvPrice.setText(utils.getBothCurrency() + " " + minPrice + "-" + maxPrice + "+");

    }

    @Override
    protected void initListener() {
        tvLocation.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        tvCondition.setOnClickListener(this);
        imgCross.setOnClickListener(this);

    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_cross:
                moveBack();
                break;
            case R.id.tv_location:
                Intent addressIntent = new Intent(mContext, LocationSearchActivity.class);
                startActivityForResult(addressIntent, LOCATION);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.tv_clear:
                mSearchModel = null;
                SalesSearchModel.setInstance(mSearchModel);
                setDate();
                break;
            case R.id.tv_condition:
                ArrayList<String> condition = new ArrayList<>();
                condition.add(getString(R.string.NEW));
                condition.add(getString(R.string.Used));
                selectOptions(condition, CONDITION, tvCondition.getText().toString());
                break;

            case R.id.tv_done:
                if (connectedToInternet()) {
                    mSearchModel.title = edSearch.getText().toString().trim();
                    mSearchModel.maxPrice = maxPrice;
                    mSearchModel.minPrice = minPrice;
                    mSearchModel.distance = mDistance;
                    mSearchModel.latitude = mLatitude;
                    mSearchModel.longitude = mLongitude;
                    mSearchModel.address = mAddress;
                    mSearchModel.condition = mCondition;

                    if (scRated.isChecked())
                        mSearchModel.bestRated = true;
                    else
                        mSearchModel.bestRated = false;

                    SalesSearchModel.setInstance(mSearchModel);
                    hitAPI();
                } else {
                    showInternetAlert(llDistanceRange);
                }
                break;
        }
    }

    private void hitAPI() {
        showProgress();
        String rating;
        String lat;
        String lang;
        if (mSearchModel.bestRated)
            rating = "1";
        else
            rating = "0";

        if (mLongitude == 0.0 && mLatitude == 0.0) {
            lat = "";
            lang = "";
        } else {
            lat = String.valueOf(mLatitude);
            lang = String.valueOf(mLongitude);
        }


        Call<SalesListingTulModel> call = RetrofitClient.getInstance().salesSearch(utils.getString("access_token", "")
                , mSearchModel.title
                , String.valueOf(mSearchModel.maxPrice)
                , String.valueOf(mSearchModel.minPrice)
                , lat
                , lang
                , mSearchModel.condition
                , rating
                , String.valueOf(mSearchModel.distance));
        call.enqueue(new Callback<SalesListingTulModel>() {

            @Override
            public void onResponse(Call<SalesListingTulModel> call, Response<SalesListingTulModel> response) {
                hideProgress();
                mNearByTulListingArray = new ArrayList<>();
                mNearByTulListingArray.addAll(response.body().getResponse());

                if (mNearByTulListingArray.size() > 0) {
                    Intent intent = new Intent(mContext, SalesTulListingActivity.class);
                    intent.putParcelableArrayListExtra("Search", mNearByTulListingArray);
                    startActivity(intent);
//                    finish();
                    overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                } else {
                    Toast.makeText(SalesSearchActivity.this, getString(R.string.no_result_fount), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SalesListingTulModel> call, Throwable t) {
                hideProgress();
            }
        });
//
//        new Callback<SalesListingTulModel>() {
//            @Override
//            public void onResponse(Call<SalesListingTulModel> call, Response<SalesListingTulModel> response) {
//                if (response.body().getResponse() != null) {
//                    mNearByTulListingArray.addAll(response.body().getResponse());
//                    mAdapter = new SalesTulListingAdapter(mContext, mNearByTulListingArray);
//                    rvTuls.setAdapter(mAdapter);
//                    if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
//                        txtNoTuls.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    if (String.valueOf(response.body().getCode()).equals(getResources().getString(R.string.invalid_access_token))) {
//                        Constants.moveToSplash(mContext, utils);
//                    } else {
//                        showAlert(rvTuls, String.valueOf(response.body().getCode()));
//                        if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
//                            txtNoTuls.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
//                hideProgress();
//            }
//
//            @Override
//            public void onFailure(Call<SalesListingTulModel> call, Throwable t) {
//                showAlert(rvTuls, t.getLocalizedMessage());
//                hideProgress();
//                if (mNearByTulListingArray != null && mNearByTulListingArray.size() == 0) {
//                    txtNoTuls.setVisibility(View.VISIBLE);
//                }
//            }
//        }
    }


    void selectOptions(ArrayList<String> mData, int intentType, String selected) {
        Intent in = new Intent(mContext, OptionSelection.class);
        in.putExtra("selected", selected);
        in.putStringArrayListExtra("data", mData);
        startActivityForResult(in, intentType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LOCATION:
                    mLatitude = Double.parseDouble(data.getStringExtra("latitude"));
                    mLongitude = Double.parseDouble(data.getStringExtra("longitude"));
                    mAddress = data.getStringExtra("address");
                    tvLocation.setText(mAddress);
                case CONDITION:
                    tvCondition.setText(data.getStringExtra("selected_data"));
                    mCondition = data.getStringExtra("selected_data");
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }
}
