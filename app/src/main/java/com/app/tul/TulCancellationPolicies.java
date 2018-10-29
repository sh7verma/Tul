package com.app.tul;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.TulCancellationPoliciesAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import customviews.SemiboldTextView;
import model.CancellationPolicyModel;
import model.DemoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TulCancellationPolicies extends BaseActivity {

    @BindView(R.id.txt_toolbar_title)
    SemiboldTextView tvToolbarTitle;

    @BindView(R.id.lv_cancellation_policies)
    ListView lvCancellationPolicies;

    @BindView(R.id.txt_content)
    TextView txtContent;

    @BindView(R.id.img_back)
    ImageView imgBack;

    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_cancellation_policies;
    }

    @Override
    protected void initUI() {

        onCreateStuff();

        tvToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        tvToolbarTitle.setText(R.string.cancellation_policies);

        txtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtContent.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        lvCancellationPolicies.setPadding(mWidth / 16, 0, mWidth / 16, 0);


    }

    @Override
    protected void onCreateStuff() {

        if (connectedToInternet())
            hitAPI();

        lvCancellationPolicies.setAdapter(new TulCancellationPoliciesAdapter(this, arrayList));
    }

    private void hitAPI() {
        showProgress();
        Call<CancellationPolicyModel> call = RetrofitClient.getInstance().cancellationPolicy(utils.getString("access_token", ""));
        call.enqueue(new Callback<CancellationPolicyModel>() {
            @Override
            public void onResponse(Call<CancellationPolicyModel> call, Response<CancellationPolicyModel> response) {
                if (response.body().getResponse() != null) {
                    txtContent.setText(response.body().getResponse().get(0));
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(lvCancellationPolicies, response.body().error.getMessage());
                    }
                }

                hideProgress();
            }

            @Override
            public void onFailure(Call<CancellationPolicyModel> call, Throwable t) {
                hideProgress();
            }
        });
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TulCancellationPolicies.this;
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
}
