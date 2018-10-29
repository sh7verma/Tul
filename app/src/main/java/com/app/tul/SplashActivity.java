package com.app.tul;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.util.Locale;

import butterknife.BindView;
import database.Db;
import io.fabric.sdk.android.Fabric;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.Utils;

/**
 * Created by applify on 9/25/2017.
 */

public class SplashActivity extends Activity {
    Context mContext;
    Utils util;
    int mWidth, mHeight;

    @BindView(R.id.img_logo)
    ImageView imgLogo;

    Db db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        mContext = SplashActivity.this;
        db = new Db(mContext);

        util = new Utils(mContext);
        SetDefaultLanguage();
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        mWidth = display.widthPixels;
        mHeight = display.heightPixels;
        Log.e("Height = ", mHeight + " width " + mWidth);
        util.setInt("width", mWidth);
        util.setInt("height", mHeight);

        FirebaseApp.initializeApp(mContext);
        util.setString("device_token", FirebaseInstanceId.getInstance().getToken());

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.app.tul",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {

        }
        CustomLoadingDialog.initLoader();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (util.getString("access_token", "").equals("")) {
//                    Intent intent = new Intent(mContext, WalkthroughActivity.class);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                    Intent start = new Intent(mContext, UserModeActivity.class);
                    startActivity(start);
                    finish();
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                } else {
                    if (util.getInt(Constants.ISGUEST, 0) == 1) {
//                        Intent intent = new Intent(mContext, LandingActivity.class);
                        Intent intent = new Intent(mContext, AfterLoginSelectMode.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    } else if (util.getInt(Constants.EMAIL_VERIFY, 0) == 0) {
                        if (util.getInt(Constants.ISEMAILSKIP, 0) == 1) {
                            if (util.getInt("status", 0) == 0 || util.getInt("status", 0) == 1) {
                                Intent intent = new Intent(mContext, CreateProfileActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            } else if (util.getInt("status", 0) == 2) {
//                                Intent intent = new Intent(mContext, LandingActivity.class);
                                Intent intent = new Intent(mContext, AfterLoginSelectMode.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            }
                        } else {
                            Intent intent = new Intent(mContext, WalkthroughActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        }
                    } else {
                        if (util.getInt("status", 0) == 0 || util.getInt("status", 0) == 1) {
                            Intent intent = new Intent(mContext, CreateProfileActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        } else if (util.getInt("status", 0) == 2) {
//                            Intent intent = new Intent(mContext, LandingActivity.class);
                            Intent intent = new Intent(mContext, AfterLoginSelectMode.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        }
                    }
                }
            }
        }, 2000);
    }
    public void SetDefaultLanguage() {
        if (util.getBoolean("is_language", false)) {
            String lng = util.getString("selected_lang", "en").trim();
            Locale locale;
            locale = new Locale(lng);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
        else {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources()
                    .updateConfiguration(
                            config,
                            getBaseContext().getResources()
                                    .getDisplayMetrics());
            util.setString("selected_lang","en");
        }
    }
}
