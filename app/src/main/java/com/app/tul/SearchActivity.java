package com.app.tul;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import customviews.RangeSeekBar;
import dialogs.SearchAvailbiltyDialog;
import utils.Constants;


public class SearchActivity extends BaseActivity {

    static final int CATEGORY = 1;
    static final int LOCATION = 2;

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
    @BindView(R.id.tv_categories)
    TextView tvCategories;
    @BindView(R.id.tv_availbilty_modes)
    TextView tvAvailbiltyModes;
    @BindView(R.id.tv_pickup)
    TextView tvPickup;
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

    private String mSelectedCat = "", mAddress = "";
    private int mSelectedCatId, mAvailbiltyMode = 0, mPickupMode = 2,
            minPrice = 0, maxPrice = 999, mDistance = 100, mCount;
    private double mLatitude = 0.0, mLongitude = 0.0;
    private boolean mCategoryPath;


    @Override
    protected int getContentView() {
        return R.layout.activity_search;
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

        tvCategories.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvCategories.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvCategories.setLayoutParams(layoutParams3);
        tvCategories.setCompoundDrawablePadding(mWidth / 32);

        tvAvailbiltyModes.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvAvailbiltyModes.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvAvailbiltyModes.setLayoutParams(layoutParams3);
        tvAvailbiltyModes.setCompoundDrawablePadding(mWidth / 32);

        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams5.setMargins(0, mHeight / 42, 0, mHeight / 42);

        tvPickup.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvPickup.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        tvPickup.setCompoundDrawablePadding(mWidth / 32);

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
        seekBar.setNotifyWhileDragging(true);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                tvPrice.setText(utils.getBothCurrency() + " " + minValue + "-" + maxValue);
                minPrice = (int) minValue;
                maxPrice = (int) maxValue;
                if (maxPrice == 999) {
                    tvPrice.setText(utils.getBothCurrency() + " "
                            + minValue + "-" + maxValue + "+");
                }
            }
        });


        seekBarDistance.setNotifyWhileDragging(true);
        seekBarDistance.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                tvDistance.setText(maxValue + " Miles/Km");

                mDistance = (int) maxValue;
                if (mDistance == 100) {
                    tvDistance.setText(maxValue + " Miles/Km+");
                }
            }
        });


        scRated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        setPrefilledData();
    }

    @SuppressLint("SetTextI18n")
    private void setPrefilledData() {
        if (getIntent().hasExtra("path")) {
            mCategoryPath = true;
            tvCategories.setText(getIntent().getStringExtra("category"));
            tvCategories.setEnabled(false);
            mSelectedCat = getIntent().getStringExtra("category");
            mSelectedCatId = getIntent().getIntExtra("category_id", 0);

            if (Constants.CATEGORY_TITLE != null) {
                edSearch.setText(Constants.CATEGORY_TITLE);
                edSearch.setSelection(edSearch.getText().toString().trim().length());

                Constants.CATEGORY_TITLE = edSearch.getText().toString().toLowerCase().trim();
                tvCategories.setText(Constants.CATEGORY_CATEGORY);

                mSelectedCatId = Constants.CATEGORY_CATEGORY_ID;
                mSelectedCat = Constants.CATEGORY_CATEGORY;
                maxPrice = Constants.CATEGORY_MAX_PRICE_SEARCH;
                minPrice = Constants.CATEGORY_MIN_PRICE_SEARCH;
                mAvailbiltyMode = Constants.CATEGORY_AVAILABILTY;
                mPickupMode = Constants.CATEGORY_DELIVERY_TYPE;
                mDistance = Constants.CATEGORY_DISTANCE;
                mLatitude = Constants.CATEGORY_LATITUDE;
                mLongitude = Constants.CATEGORY_LONGITUDE;
                mAddress = Constants.CATEGORY_ADDRESS;

                tvLocation.setText(mAddress);

                if (Constants.CATEGORY_BEST_RATED)
                    scRated.setChecked(true);
                else
                    scRated.setChecked(false);

//            0 for pickup, 1 for delivery,2 for both
//            1 for weekdays, 2 for weekend

                if (mAvailbiltyMode == 1)
                    tvAvailbiltyModes.setText(R.string.weekdays);
                else if (mAvailbiltyMode == 2)
                    tvAvailbiltyModes.setText(R.string.only_weekends);
                else
                    tvAvailbiltyModes.setText(R.string.all);

                if (mPickupMode == 0)
                    tvPickup.setText(R.string.pickup);
                else if (mPickupMode == 2)
                    tvPickup.setText(R.string.both);
                else
                    tvPickup.setText(R.string.deliver_at_your_location);

                seekBar.setSelectedMaxValue(maxPrice);
                seekBar.setSelectedMinValue(minPrice);

                seekBarDistance.setSelectedMaxValue(mDistance);

                tvDistance.setText(mDistance + " Miles");

                if (maxPrice == 999) {
                    tvPrice.setText(utils.getBothCurrency() + " "
                            + minPrice + "-" + maxPrice + "+");
                }
                if (mDistance == 100) {
                    tvDistance.setText(mDistance + " Miles/Km+");
                }

                tvPrice.setText(utils.getBothCurrency() + " " + minPrice + "-" + maxPrice);
            }
        } else {
            /// landing screen...
            if (Constants.TITLE != null) {
                edSearch.setText(Constants.TITLE);
                edSearch.setSelection(edSearch.getText().toString().trim().length());

                Constants.TITLE = edSearch.getText().toString().toLowerCase().trim();
                tvCategories.setText(Constants.CATEGORY);

                mSelectedCatId = Constants.CATEGORY_ID;
                mSelectedCat = Constants.CATEGORY;
                maxPrice = Constants.MAX_PRICE_SEARCH;
                minPrice = Constants.MIN_PRICE_SEARCH;
                mAvailbiltyMode = Constants.AVAILABILTY;
                mPickupMode = Constants.DELIVERY_TYPE;
                mDistance = Constants.DISTANCE;
                mLatitude = Constants.LATITUDE;
                mLongitude = Constants.LONGITUDE;
                mAddress = Constants.ADDRESS;

                tvLocation.setText(mAddress);

                if (Constants.BEST_RATED)
                    scRated.setChecked(true);
                else
                    scRated.setChecked(false);

//            0 for pickup, 1 for delivery,2 for both
//            1 for weekdays, 2 for weekend

                if (mAvailbiltyMode == 1)
                    tvAvailbiltyModes.setText(R.string.weekdays);
                else if (mAvailbiltyMode == 2)
                    tvAvailbiltyModes.setText(R.string.only_weekends);
                else
                    tvAvailbiltyModes.setText(R.string.all);

                if (mPickupMode == 0)
                    tvPickup.setText(R.string.pickup);
                else if (mPickupMode == 2)
                    tvPickup.setText(R.string.both);
                else
                    tvPickup.setText(R.string.deliver_at_your_location);

                seekBar.setSelectedMaxValue(maxPrice);
                seekBar.setSelectedMinValue(minPrice);

                seekBarDistance.setSelectedMaxValue(mDistance);

                tvDistance.setText(mDistance + " Miles");
                tvPrice.setText(utils.getBothCurrency() + " " + minPrice + "-" + maxPrice);

                if (maxPrice == 999) {
                    tvPrice.setText(utils.getBothCurrency() + " "
                            + minPrice + "-" + maxPrice + "+");
                }
                if (mDistance == 100) {
                    tvDistance.setText(mDistance + " Miles/Km+");
                }
            }
        }

    }

    @Override
    protected void initListener() {
        tvAvailbiltyModes.setOnClickListener(this);
        tvPickup.setOnClickListener(this);
        tvCategories.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        imgCross.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return SearchActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cross:
                moveBack();
                break;
            case R.id.tv_clear:
                tempResetValues();
                break;
            case R.id.tv_categories:
                Intent catIntent = new Intent(mContext, SelectCategoryActivity.class);
                catIntent.putExtra("selected_category", mSelectedCat);
                catIntent.putExtra("selected_categoryId", mSelectedCatId);
                startActivityForResult(catIntent, CATEGORY);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_availbilty_modes:
                new SearchAvailbiltyDialog(mContext, mWidth, tvAvailbiltyModes.getText().toString(), 1).showDialog();
                break;
            case R.id.tv_pickup:
                new SearchAvailbiltyDialog(mContext, mWidth, tvPickup.getText().toString(), 2).showDialog();
                break;
            case R.id.tv_location:
                Intent addressIntent = new Intent(mContext, LocationSearchActivity.class);
                startActivityForResult(addressIntent, LOCATION);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_done:
                if (connectedToInternet()) {
                    mCount = 0;
                    if (!TextUtils.isEmpty(edSearch.getText().toString().trim()))
                        mCount++;
                    if (mSelectedCatId != 0 && !getIntent().hasExtra("path"))
                        mCount++;
                    if (mAvailbiltyMode != 0)
                        mCount++;
                    if (mPickupMode != 2)
                        mCount++;
                    if (scRated.isChecked())
                        mCount++;
                    if (minPrice != 0)
                        mCount++;
                    if (maxPrice != 999)
                        mCount++;
                    if (mDistance != 101)
                        mCount++;
                    if (!TextUtils.isEmpty(mAddress))
                        mCount++;

                    if (mCategoryPath) {
                        Constants.CATEGORY_TITLE = edSearch.getText().toString().trim();
                        Constants.CATEGORY_CATEGORY_ID = mSelectedCatId;
                        Constants.CATEGORY_CATEGORY = mSelectedCat;
                        Constants.CATEGORY_MAX_PRICE_SEARCH = maxPrice;
                        Constants.CATEGORY_MIN_PRICE_SEARCH = minPrice;
                        Constants.CATEGORY_AVAILABILTY = mAvailbiltyMode;
                        Constants.CATEGORY_DELIVERY_TYPE = mPickupMode;
                        Constants.CATEGORY_DISTANCE = mDistance;
                        Constants.CATEGORY_LATITUDE = mLatitude;
                        Constants.CATEGORY_LONGITUDE = mLongitude;
                        Constants.CATEGORY_ADDRESS = mAddress;
                        Constants.CATEGORY_FILTER_COUNT = mCount;

                        if (scRated.isChecked())
                            Constants.CATEGORY_BEST_RATED = true;
                        else
                            Constants.CATEGORY_BEST_RATED = false;

                    } else {
                        Constants.TITLE = edSearch.getText().toString().trim();
                        Constants.CATEGORY_ID = mSelectedCatId;
                        Constants.CATEGORY = mSelectedCat;
                        Constants.MAX_PRICE_SEARCH = maxPrice;
                        Constants.MIN_PRICE_SEARCH = minPrice;
                        Constants.AVAILABILTY = mAvailbiltyMode;
                        Constants.DELIVERY_TYPE = mPickupMode;
                        Constants.DISTANCE = mDistance;
                        Constants.LATITUDE = mLatitude;
                        Constants.LONGITUDE = mLongitude;
                        Constants.ADDRESS = mAddress;
                        Constants.FILTER_COUNT = mCount;

                        if (scRated.isChecked())
                            Constants.BEST_RATED = true;
                        else
                            Constants.BEST_RATED = false;
                    }


                    if (getIntent().hasExtra("path")) {
                        // category
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        moveBack();
                    } else {
                        Intent searchResult = new Intent(mContext, SearchResultActivity.class);
                        startActivity(searchResult);
                        finish();
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }
                } else
                    showAlert(llMain, errorInternet);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CATEGORY:
                    tvCategories.setText(data.getStringExtra("catName"));
                    mSelectedCat = Constants.EMPTY;
                    mSelectedCat = data.getStringExtra("catName");
                    mSelectedCatId = data.getIntExtra("catId", 0);
                    break;
                case LOCATION:
                    mLatitude = Double.parseDouble(data.getStringExtra("latitude"));
                    mLongitude = Double.parseDouble(data.getStringExtra("longitude"));
                    mAddress = data.getStringExtra("address");
                    tvLocation.setText(mAddress);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setResultOfAvailableOn(String result, int availability) {
        mAvailbiltyMode = availability;
        tvAvailbiltyModes.setText(result);
    }

    public void setResultOfPickup(String result, int delivery_type) {
        mPickupMode = delivery_type;
        tvPickup.setText(result);
    }

    private void alertDialogClear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.clear_filter_warining)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        resetValues();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }

    private void resetValues() {
        if (mCategoryPath) {
            Constants.CATEGORY_TITLE = null;
            edSearch.setText("");
            if (!getIntent().hasExtra("path")) {
                tvCategories.setText("");
                mSelectedCatId = 0;
                mSelectedCat = "";
            }
            tvAvailbiltyModes.setText("");
            tvPickup.setText("");
            scRated.setChecked(false);

            mAvailbiltyMode = 0;
            mPickupMode = 2;
            minPrice = 0;
            maxPrice = 999;
            mDistance = 101;
            mAddress = "";
            mLatitude = 0.0;
            mLongitude = 0.0;

            seekBar.setSelectedMaxValue(maxPrice);
            seekBar.setSelectedMinValue(minPrice);
            seekBarDistance.setSelectedMaxValue(100);
            tvLocation.setText(mAddress);
            tvDistance.setText("100 Miles/Km+");
            tvPrice.setText(utils.getBothCurrency() + " " + minPrice + "-" + maxPrice + "+");
        } else {
            Constants.TITLE = null;
            edSearch.setText("");
            if (!getIntent().hasExtra("path")) {
                tvCategories.setText("");
                mSelectedCatId = 0;
                mSelectedCat = "";
            }
            tvAvailbiltyModes.setText("");
            tvPickup.setText("");
            scRated.setChecked(false);

            mAvailbiltyMode = 0;
            mPickupMode = 2;
            minPrice = 0;
            maxPrice = 999;
            mDistance = 101;
            mAddress = "";
            mLatitude = 0.0;
            mLongitude = 0.0;

            seekBar.setSelectedMaxValue(maxPrice);
            seekBar.setSelectedMinValue(minPrice);
            seekBarDistance.setSelectedMaxValue(100);
            tvLocation.setText(mAddress);
            tvDistance.setText("100 Miles/Km+");
            tvPrice.setText(utils.getBothCurrency() + " " + minPrice + "-" + maxPrice + "+");
        }
    }

    private void tempResetValues() {

        if (mCategoryPath) {
            edSearch.setText("");
            if (!getIntent().hasExtra("path")) {
                tvCategories.setText("");
                mSelectedCatId = 0;
                mSelectedCat = "";
            }
            tvAvailbiltyModes.setText("");
            tvPickup.setText("");
            scRated.setChecked(false);
            minPrice = 0;
            maxPrice = 999;
            mDistance = 101;
            mAddress = "";
            mLatitude = 0.0;
            mLongitude = 0.0;

            seekBar.setSelectedMaxValue(maxPrice);
            seekBar.setSelectedMinValue(minPrice);
            seekBarDistance.setSelectedMaxValue(100);

            tvLocation.setText(mAddress);
            tvDistance.setText("100 Miles/Km+");
            tvPrice.setText(utils.getBothCurrency() + " " + minPrice + "-" + maxPrice + "+");
        } else {
            edSearch.setText("");
            if (!getIntent().hasExtra("path")) {
                tvCategories.setText("");
                mSelectedCatId = 0;
                mSelectedCat = "";
            }
            tvAvailbiltyModes.setText("");
            tvPickup.setText("");
            scRated.setChecked(false);
            minPrice = 0;
            maxPrice = 999;
            mDistance = 101;
            mAddress = "";
            mLatitude = 0.0;
            mLongitude = 0.0;

            seekBar.setSelectedMaxValue(maxPrice);
            seekBar.setSelectedMinValue(minPrice);
            seekBarDistance.setSelectedMaxValue(100);

            tvLocation.setText(mAddress);
            tvDistance.setText("100 Miles/Km+");
            tvPrice.setText(utils.getBothCurrency() + " " + minPrice + "-" + maxPrice + "+");
        }
    }
}
