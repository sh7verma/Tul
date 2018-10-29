package com.app.tul;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.MaterialEditText;
import model.DemoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 27/11/17.
 */

public class ChangePasswordActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.ll_outer)
    LinearLayout llOuter;
    @BindView(R.id.ll_enter_fields)
    LinearLayout llEnterFields;
    @BindView(R.id.ed_current_password)
    MaterialEditText edCurrentPassword;
    @BindView(R.id.ed_new_password)
    MaterialEditText edNewPassword;
    @BindView(R.id.ed_re_enter_new_password)
    MaterialEditText edReEnterNewPassword;
    @BindView(R.id.ll_done)
    LinearLayout llDone;
    @BindView(R.id.txt_done)
    TextView txtDone;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText("CHANGE PASSWORD");

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        edCurrentPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        edCurrentPassword.setTypeface(typeface);

        edNewPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        edNewPassword.setTypeface(typeface);

        edReEnterNewPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        edReEnterNewPassword.setTypeface(typeface);

        txtDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

    }

    @Override
    protected void onCreateStuff() {
        edReEnterNewPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    verifyDetails();
                }
                return true;
            }
        });
    }

    private void hitAPI() {
        Call<DemoModel> call = RetrofitClient.getInstance().changePassword(utils.getString("access_token", ""),
                edCurrentPassword.getText().toString().trim(),
                edNewPassword.getText().toString().trim());
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    Toast.makeText(ChangePasswordActivity.this, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                    moveBack();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llDone, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(llDone, t.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void initListener() {
        llDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return ChangePasswordActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_done:
                verifyDetails();
                break;
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    private void verifyDetails() {
        if (edCurrentPassword.getText().toString().trim().length() < 8) {
            showAlert(llDone, getString(R.string.error_password));
        } else if (edNewPassword.getText().toString().trim().length() < 8) {
            showAlert(llDone, getString(R.string.error_password));
        } else if (edReEnterNewPassword.getText().toString().trim().length() < 8) {
            showAlert(llDone, getString(R.string.error_password));
        } else if (!edNewPassword.getText().toString().trim().equals(edReEnterNewPassword.getText().toString().trim())) {
            showAlert(llDone, getString(R.string.password_mismatch));
        } else {
            if (connectedToInternet()) {
                showProgress();
                hitAPI();
            } else
                showInternetAlert(llDone);
        }
    }

    private void moveBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            finishAfterTransition();
        else {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }
}
