package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import utils.Utils;

/**
 * Created by dev on 12/10/18.
 */

public class ChangeLanguageActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.rg_languages)
    RadioGroup rgLanguages;
    @BindView(R.id.rb_english)
    RadioButton rbEnglish;
    @BindView(R.id.rb_french)
    RadioButton rbFrench;
    @BindView(R.id.rb_german)
    RadioButton rbGerman;
    @BindView(R.id.rb_italian)
    RadioButton rbItalian;
    @BindView(R.id.rb_spanish)
    RadioButton rbSpanish;
    @BindView(R.id.rb_romanian)
    RadioButton rbRomanian;

    @BindView(R.id.ll_done)
    LinearLayout llDone;
    @BindView(R.id.txt_done)
    TextView txtDone;

    Utils utils;
    Integer id;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_language;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.change_language);
        txtToolbarTitle.setAllCaps(true);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        rbEnglish.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        rbEnglish.setTypeface(typeface);

        rbFrench.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        rbFrench.setTypeface(typeface);

        rbItalian.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        rbItalian.setTypeface(typeface);

        rbSpanish.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        rbSpanish.setTypeface(typeface);

        rbGerman.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        rbGerman.setTypeface(typeface);

        rbRomanian.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        rbRomanian.setTypeface(typeface);

        txtDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
    }

    @Override
    protected void onCreateStuff() {

        utils = new Utils(mContext);

        String lng = utils.getString("selected_lang", "en").trim();
        switch (lng) {
            case "en":
                rbEnglish.setChecked(true);
                break;
            case "de":
                rbGerman.setChecked(true);
                break;
            case "fr":
                rbFrench.setChecked(true);
                break;
            case "it":
                rbItalian.setChecked(true);
                break;
            case "ro":
                rbRomanian.setChecked(true);
                break;
            case "es":
                rbSpanish.setChecked(true);
                break;
            default:
                rbEnglish.setChecked(true);
        }

        id = rgLanguages.getCheckedRadioButtonId();
    }

    @Override
    protected void initListener() {

        imgBack.setOnClickListener(this);
        txtDone.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return ChangeLanguageActivity.this;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
            case R.id.txt_done:
                ChangeLanguage();
                moveToLandingActivity();
                break;
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

    private void moveToLandingActivity() {
        Intent inLanding;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            inLanding = new Intent(mContext, LandingActivity.class);
            inLanding.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            inLanding.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(inLanding);
        } else {
            inLanding = new Intent(mContext, LandingActivity.class);
            inLanding.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            inLanding.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(inLanding);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    private void ChangeLanguage() {
        id = rgLanguages.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rb_english:
                utils.setBoolean("is_language", true);
                utils.setString("selected_lang", "en");
                SetLocal("en");
                break;
            case R.id.rb_french:
                utils.setBoolean("is_language", true);
                utils.setString("selected_lang", "fr");
                SetLocal("fr");
                break;
            case R.id.rb_german:
                utils.setBoolean("is_language", true);
                utils.setString("selected_lang", "de");
                SetLocal("de");
                break;
            case R.id.rb_italian:
                utils.setBoolean("is_language", true);
                utils.setString("selected_lang", "it");
                SetLocal("it");
                break;
            case R.id.rb_romanian:
                utils.setBoolean("is_language", true);
                utils.setString("selected_lang", "ro");
                SetLocal("ro");
                break;
            case R.id.rb_spanish:
                utils.setBoolean("is_language", true);
                utils.setString("selected_lang", "es");
                SetLocal("es");
                break;
        }

    }

    private void SetLocal(String lng) {
        Locale locale;
        locale = new Locale(lng);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
