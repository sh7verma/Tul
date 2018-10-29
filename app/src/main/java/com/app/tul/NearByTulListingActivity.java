package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.NearByTulListingAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import model.NearByTulListingModel;
import model.ViewTulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class NearByTulListingActivity extends BaseActivity {

    private static final int DELETE = 2;

    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.rv_tuls)
    RecyclerView rvTuls;

    ArrayList<NearByTulListingModel.ResponseBean> mNearByTulListingArray = new ArrayList<>();
    NearByTulListingAdapter mAdapter;

    LinearLayoutManager layoutManager;
    boolean isDeleted;
    ArrayList<Integer> deletedIds = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_nearby_tul_listing;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.near_by_tul);
        txtToolbarTitle.setAllCaps(false);

        layoutManager = new LinearLayoutManager(this);
        rvTuls.setLayoutManager(layoutManager);
    }

    @Override
    protected void onCreateStuff() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String id : getIntent().getStringArrayListExtra("tulIds")) {
            stringBuilder.append(id + ",");
        }
        String ids = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
        if (connectedToInternet())
            hitAPI(ids);
        else
            showInternetAlert(rvTuls);
    }

    private void hitAPI(String ids) {
        showProgress();
        Call<NearByTulListingModel> call = RetrofitClient.getInstance().getNearByToolsList(utils.getString("access_token", ""), ids);
        call.enqueue(new Callback<NearByTulListingModel>() {
            @Override
            public void onResponse(Call<NearByTulListingModel> call, Response<NearByTulListingModel> response) {
                if (response.body().getResponse() != null) {
                    mNearByTulListingArray.addAll(response.body().getResponse());
                    mAdapter = new NearByTulListingAdapter(mContext, mNearByTulListingArray);
                    rvTuls.setAdapter(mAdapter);
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, utils);
                    } else {
                        showAlert(rvTuls, response.body().error.getMessage());
                    }
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<NearByTulListingModel> call, Throwable t) {
                showAlert(rvTuls, t.getLocalizedMessage());
                hideProgress();
            }
        });

    }

    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return NearByTulListingActivity.this;
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
        if (isDeleted) {
            Intent intent = new Intent();
            intent.putIntegerArrayListExtra("deletedIds", deletedIds);
            setResult(RESULT_OK, intent);
        } else if (getIntent().hasExtra("getdataonback")) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
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