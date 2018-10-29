package com.app.tul;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import api.RetrofitClient;
import butterknife.OnClick;
import model.BaseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Utils;

public class BroadCastDialog extends Activity {


    int mWidth, mHeight;
    View mDialog;
    Utils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = this.getWindow().getAttributes();
        wmlp.windowAnimations = R.style.DialogAnimation;
        wmlp.gravity = Gravity.CENTER_HORIZONTAL;
        wmlp.x = mWidth;
        wmlp.y = mHeight;
        this.setFinishOnTouchOutside(false);
        getDefaults();
        mDialog = LayoutInflater.from(this).inflate(R.layout.broadcast_dialog, null);
        setContentView(mDialog);
        utils = new Utils(this);
        String mess = getIntent().getStringExtra("message");
        dialogLikeDislike(mess);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String mess = intent.getStringExtra("message");
        dialogLikeDislike(mess);

    }

    void getDefaults() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
    }

    void dialogLikeDislike(String mess) {


        RelativeLayout.LayoutParams phone_params = new RelativeLayout.LayoutParams((int) (mWidth * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout main_lay = (LinearLayout) mDialog.findViewById(R.id.main_lay);
        phone_params.setMargins(0, mWidth / 8, 0, 0);
        phone_params.addRule(RelativeLayout.CENTER_IN_PARENT);
        main_lay.setLayoutParams(phone_params);
        GradientDrawable roundDrawable = (GradientDrawable) main_lay.getBackground();
        roundDrawable.setCornerRadius(mWidth / 32);

        LinearLayout.LayoutParams cancelParms = new LinearLayout.LayoutParams(mWidth / 7, mWidth / 7);
        ImageView imgBell = (ImageView) mDialog.findViewById(R.id.img_bell);
        imgBell.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        imgBell.setLayoutParams(cancelParms);

        TextView txtTittle = (TextView) mDialog.findViewById(R.id.txt_tittle);
        txtTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.060));
        txtTittle.setPadding(mWidth / 16, mWidth / 12, mWidth / 32, mWidth / 32);

        txtTittle.setText(getIntent().getStringExtra("title"));

        TextView txtMsg = (TextView) mDialog.findViewById(R.id.txt_msg);
        txtMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.050));
        txtMsg.setPadding(mWidth / 16, mWidth / 32, mWidth / 16, mWidth / 32);
        txtMsg.setText(mess);

        TextView txtLikeDislike = (TextView) mDialog.findViewById(R.id.txt_like_dislike);
        txtLikeDislike.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtLikeDislike.setPadding(mWidth / 24, mWidth / 20, mWidth / 24, mWidth / 20);

        txtLikeDislike.setText("OK");

        txtLikeDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }


}
