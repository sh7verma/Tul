package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.CircleTransform;
import interfaces.HistoryRatingListener;
import model.DemoModel;
import model.SalesBookingModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class RateYourExperienceActivity extends BaseActivity {

    static HistoryRatingListener mRatingGivenPast,mRatingGivenActive;
    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.cv_main)
    CardView cvMain;
    @BindView(R.id.cv_lower)
    CardView cvLower;
    @BindView(R.id.tv_rented)
    TextView tvRented;
    @BindView(R.id.ll_card_inner)
    LinearLayout llCardInner;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.srb_tul)
    SimpleRatingBar srbTul;
    @BindView(R.id.ed_review)
    EditText edReview;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    SalesBookingModel mModel;

    public static void RatingInterfaceActive(HistoryRatingListener listener) {
        mRatingGivenActive = listener;
    }
    public static void RatingInterfacePast(HistoryRatingListener listener) {
        mRatingGivenPast = listener;
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_rate_your_experience;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        rlMain.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.rate_your_experience);

        imgBckImg.setVisibility(View.GONE);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, mHeight / 26, 0, mHeight / 32);
        cvMain.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(mWidth / 6, mWidth / 6);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imgUser.setLayoutParams(layoutParams1);

        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(0, mHeight / 60, mHeight / 60, 0);
        layoutParams2.gravity = Gravity.END;
        tvRented.setLayoutParams(layoutParams2);

        llCardInner.setPadding(mWidth / 32, mHeight / 14, mWidth / 32, mHeight / 32);

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        tvPrice.setPadding(0, mHeight / 64, 0, mHeight / 22);
        tvPrice.setText(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, "")) + "20");

        tvRating.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvRating.setPadding(0, mHeight / 22, 0, mHeight / 32);

        tvRented.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvRented.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);

        srbTul.setStarSize((float) (mWidth * 0.1));
        srbTul.setPadding(0, mWidth / 42, 0, mWidth / 32);

        edReview.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        edReview.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        edReview.setTypeface(typefaceRegular);

        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight / 4);
        layoutParams3.setMargins(mWidth / 32, 0, mWidth / 32, mHeight / 32);
        cvLower.setLayoutParams(layoutParams3);

        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams4.setMargins(mWidth / 32, 0, mWidth / 32, mHeight / 32);
        tvSubmit.setLayoutParams(layoutParams4);
        tvSubmit.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvSubmit.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);
    }

    @Override
    protected void onCreateStuff() {
        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == 0) {
            tvAddress.setText(getIntent().getStringExtra("address"));
            tvPrice.setText(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, "")) + " " + String.format("%.2f", Double.parseDouble(getIntent().getStringExtra("totalAmount"))));

            if (getIntent().getIntExtra("path", 0) == 1) {
                tvRented.setText(R.string.lented);
                displayProfileData(getIntent().getStringExtra("borrower"), getIntent().getStringExtra("borrower_pic"));
            } else {
                tvRented.setText(R.string.rented);
                displayProfileData(getIntent().getStringExtra("owner"), getIntent().getStringExtra("ownerPic"));
            }

            svMain.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            svMain.setFocusable(true);
            svMain.setFocusableInTouchMode(true);
            svMain.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.requestFocusFromTouch();
                    return false;
                }
            });
        } else {
            hitBookingAPI();
        }
    }

    private void displayProfileData(String name, String profilePic) {
        tvName.setText(name);
        Picasso.with(mContext).load(profilePic)
                .resize(mWidth / 6, mWidth / 6)
                .transform(new CircleTransform())
                .centerCrop().into(imgUser);
    }

    @Override
    protected void initListener() {
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return RateYourExperienceActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (connectedToInternet()) {

                    if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == 0) {
                        if (srbTul.getRating() == 0)
                            showAlert(llCardInner, getString(R.string.please_rate));
                        else
                            hitAPI();
                    } else {
                        hitSalesAPI();
                    }

                } else
                    showAlert(llCardInner, errorInternet);
                break;
        }
    }

    private void hitAPI() {
        /// 1 for borrower 2 for lender
        showProgress();
        Call<DemoModel> call = RetrofitClient.getInstance().feedBackRating(utils.getString("access_token", ""),
                getIntent().getStringExtra("booking_id"), getIntent().getStringExtra("tool_id"), (int) srbTul.getRating(),
                edReview.getText().toString(), getIntent().getIntExtra("path", 0));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    if (getIntent().hasExtra("move_path")) {
                        if (LandingActivity.landingActivity == null) {
                            Intent intent = new Intent(mContext, LandingActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                        } else {
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        }
                    } else {
                        Toast.makeText(mContext, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("date", getIntent().getStringExtra("date"));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(llCardInner, t.getLocalizedMessage());
            }
        });
    }

    private void hitSalesAPI() {
        /// 1 for seller 2 for buyer
        showProgress();
        Call<DemoModel> call;
        if (String.valueOf(mModel.getResponse().getOwner_id()).equals(utils.getString("user_id", "0"))) {
            call = RetrofitClient.getInstance().feedBackSalesRating(utils.getString("access_token", ""),
                    getIntent().getStringExtra("booking_id"), (int) srbTul.getRating(),
                    edReview.getText().toString(), 1);
        } else {
            call = RetrofitClient.getInstance().feedBackSalesRating(utils.getString("access_token", ""),
                    getIntent().getStringExtra("booking_id"), (int) srbTul.getRating(),
                    edReview.getText().toString(), 2);
        }

        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {

//                    Toast.makeText(RateYourExperienceActivity.this, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                    if (getIntent().hasExtra("push_type")) {
                        Intent intent = new Intent(mContext, LandingActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                    } else {
                        finish();

                        mRatingGivenPast.onRatingGiven();
                        mRatingGivenActive.onRatingGiven();


                    }

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(llCardInner, t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void hitBookingAPI() {
        showProgress();
        Call<SalesBookingModel> call = RetrofitClient.getInstance().getSalesBookingData(utils.getString("access_token", ""),
                getIntent().getStringExtra("booking_id"));
        call.enqueue(new Callback<SalesBookingModel>() {
            @Override
            public void onResponse(Call<SalesBookingModel> call, Response<SalesBookingModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    mModel = response.body();
                    tvAddress.setText(response.body().getResponse().getAddress());
                    tvPrice.setText(response.body().getResponse().getCurrency() + " " +
                            response.body().getResponse().getTotal_amount());
                    tvName.setText(response.body().getResponse().getOwner());
                    tvRented.setText(R.string.delivered_);
                    displayProfileData(response.body().getResponse().getOwner(), response.body().getResponse().getOwner_pic());

//                    intent.putExtra("date", response.body().response.getDate());
//                    intent.putExtra("path", 1);
//                    intent.putExtra("tool_id", String.valueOf(mPendingModels.getTool_id()));
//                    intent.putExtra("booking_id", String.valueOf(mPendingModels.getBooking_id()));
//                    intent.putExtra("owner", mPendingModels.getOwner());
//                    intent.putExtra("ownerPic", mPendingModels.getOwner_pic());
//                    intent.putExtra("totalAmount", String.format("%.2f", mEarnedPrice));
//                    intent.putExtra("address", mPendingModels.getAddress());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals("300")) {
                        finish();
                        Toast.makeText(mContext, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesBookingModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

}
