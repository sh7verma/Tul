package com.app.tul;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.FullImageViewAdapter;


public class FullViewImageActivity extends Activity implements View.OnClickListener {

    Context con;
    int mWidth, mHeight;

    TextView txtDone;
    ViewPager vpImages;
    LinearLayout llPic;
    ArrayList<String> pic_array = new ArrayList<>();
    int position;
    FullImageViewAdapter adp_fullview;
    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullviewimage);

        con = FullViewImageActivity.this;
        pic_array.clear();
        pic_array.addAll(getIntent().getStringArrayListExtra("pic_array"));

        if (getIntent().hasExtra("position"))
            position = getIntent().getIntExtra("position", 0);

        if (getIntent().hasExtra("path"))
            path = getIntent().getStringExtra("path");
        else
            path = "1";
        getDefaults();
        initUI();
        initListener();
        populateUI();

    }

    void initUI() {
        llPic = (LinearLayout) findViewById(R.id.ll_pic);
        txtDone = (TextView) findViewById(R.id.txt_done);
        txtDone.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        vpImages = (ViewPager) findViewById(R.id.vp_images);
    }

    void getDefaults() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
    }

    void initListener() {
        txtDone.setOnClickListener(this);
    }

    void populateUI() {
        adp_fullview = new FullImageViewAdapter(con, pic_array, path, mWidth, mHeight);
        vpImages.setAdapter(adp_fullview);
        vpImages.setOffscreenPageLimit(pic_array.size());
        vpImages.setCurrentItem(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_done:
                finish();
        }
    }
}
