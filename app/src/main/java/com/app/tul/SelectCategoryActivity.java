package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.CategoryAdapter;
import adapters.LandingCategoriesAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import model.CategoriesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;


public class SelectCategoryActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.txt_hint_msg)
    TextView txtHintMsg;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.txt_done)
    TextView txtDone;


    CategoryAdapter mCategoryAdapter;
    private ArrayList<CategoriesModel.ResponseBean> mCategoriesArray = new ArrayList<>();
    public String mSelectedCat = "";
    public int mSelectedCatId;
    public SelectCategoryActivity mSelectCategoryActivity;


    @Override
    protected int getContentView() {
        return R.layout.activity_select_category;
    }

    @Override
    protected void initUI() {

        rvCategories.setLayoutManager(new LinearLayoutManager(this));

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtToolbarTitle.setText(getResources().getString(R.string.select_category));

        llMain.setPadding(mWidth / 32, 0, mWidth / 32, mWidth / 32);

        txtHintMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtHintMsg.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        txtDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtDone.setPadding(0, mWidth / 24, 0, mWidth / 24);
    }

    @Override
    protected void onCreateStuff() {
        mSelectedCat = getIntent().getStringExtra("selected_category");
        mSelectedCatId = getIntent().getIntExtra("selected_categoryId", 0);
        mSelectCategoryActivity = this;
        getCategories();
    }


    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        txtDone.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return SelectCategoryActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_done:
                if (mSelectedCatId == 0)
                    showAlert(llMain, "Please select category.");
                else {
                    Intent intent = new Intent();
                    intent.putExtra("catName", mSelectedCat);
                    intent.putExtra("catId", mSelectedCatId);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
                break;
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }
    }

    private void getCategories() {
        if (utils.getString("categories", "").equals("")) {
            if (connectedToInternet()) {
                Call<CategoriesModel> call = RetrofitClient.getInstance().getCategories();
                call.enqueue(new Callback<CategoriesModel>() {
                    @Override
                    public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                        if (response.body().getResponse() != null) {
                            mCategoriesArray.addAll(response.body().getResponse());
                            mCategoryAdapter = new CategoryAdapter(mContext, mCategoriesArray, mSelectedCat, mSelectCategoryActivity);
                            rvCategories.setAdapter(mCategoryAdapter);
                        } else {
                            if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                                Constants.moveToSplash(mContext, utils);
                            } else {
                                showAlert(llMain, response.body().error.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoriesModel> call, Throwable t) {
                        showAlert(llMain, t.getLocalizedMessage());
                    }
                });
            } else {
                Toast.makeText(mContext, getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            CategoriesModel categoriesModel = mGson.fromJson(utils.getString("categories", ""), CategoriesModel.class);
            mCategoriesArray.addAll(categoriesModel.getResponse());
            mCategoryAdapter = new CategoryAdapter(mContext, mCategoriesArray, mSelectedCat, mSelectCategoryActivity);
            rvCategories.setAdapter(mCategoryAdapter);
        }
    }
}
