package com.app.tul;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.ReviewAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import customviews.SemiboldTextView;
import model.ReviewRatingModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TulReviewRatingsActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    SemiboldTextView tvToolbarTitle;

    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;

    @BindView(R.id.ll_main)
    RelativeLayout llMain;

    @BindView(R.id.txt_no_tuls)
    TextView txtNotTuls;

    @BindView(R.id.progress)
    ProgressBar progressBar;


    ReviewAdapter adapter;
    ArrayList<ReviewRatingModel.ResponseBean> mReviewArray = new ArrayList<ReviewRatingModel.ResponseBean>();

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_review_ratings;
    }

    @Override
    protected void initUI() {

        tvToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        tvToolbarTitle.setText(R.string.reviews_and_ratings);
        rvReviews.setLayoutManager(new LinearLayoutManager(mContext));
    }


    @Override
    protected void onCreateStuff() {
        adapter = new ReviewAdapter(TulReviewRatingsActivity.this, mReviewArray, 0, 0);
        rvReviews.setAdapter(adapter);

        if (connectedToInternet()) {
            hitAPI();
        } else {
            showInternetAlert(llMain);
            progressBar.setVisibility(View.GONE);
            txtNotTuls.setVisibility(View.VISIBLE);
            txtNotTuls.setText(getString(R.string.no_internet_connection));
        }

    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TulReviewRatingsActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }
    }

    private void hitAPI() {
        Call<ReviewRatingModel> call = RetrofitClient.getInstance().reviewsRatings(utils.getString("access_token", ""),
                getIntent().getIntExtra("tool_id", 0));
        call.enqueue(new Callback<ReviewRatingModel>() {
            @Override
            public void onResponse(Call<ReviewRatingModel> call, Response<ReviewRatingModel> response) {
                if (response.body().getResponse() != null) {
                    for (int i = 0; i < response.body().getResponse().size(); i++) {
                        mReviewArray.add(response.body().getResponse().get(i));
                    }
                    setData(mReviewArray, response.body().getAvg_rating(), response.body().getCount());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(rvReviews, response.body().error.getMessage());
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ReviewRatingModel> call, Throwable t) {
                showAlert(rvReviews, t.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    void setData(ArrayList<ReviewRatingModel.ResponseBean> mReviewArray, int avgRating, int count) {

        adapter = new ReviewAdapter(TulReviewRatingsActivity.this, mReviewArray, avgRating, count);
        rvReviews.setAdapter(adapter);

        if (mReviewArray.size() > 0) {
            txtNotTuls.setVisibility(View.GONE);
        } else {
            txtNotTuls.setVisibility(View.VISIBLE);
            txtNotTuls.setText(R.string.no_review_yet);
        }

    }

}
