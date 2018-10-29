package com.app.tul;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.FullImageViewAdapter;
import adapters.TullFullImageAdapter;
import butterknife.BindView;
import model.TulImageModel;

public class TullFullViewActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.vp_images)
    ViewPager vpImages;
    @BindView(R.id.txt_exit)
    TextView txtExit;

    private ArrayList<TulImageModel> mTulImagesArray = new ArrayList<>();
    private ArrayList<String> mAttachmentIds = new ArrayList<>();
    private TullFullImageAdapter mAdapter;

    private int mIntialSize;

    @Override
    protected int getContentView() {
        return R.layout.activity_tull_full_view;
    }

    @Override
    protected void initUI() {

        if (getIntent().hasExtra("hide")) {
            imgDelete.setVisibility(View.GONE);
            txtExit.setVisibility(View.VISIBLE);
        }

        setData();
    }

    private void setData() {
        mTulImagesArray.clear();
        mTulImagesArray = getIntent().getParcelableArrayListExtra("tullImages");

        if (getIntent().hasExtra("attachment_ids")) {
            mAttachmentIds.clear();
            mAttachmentIds.addAll(getIntent().getStringArrayListExtra("attachment_ids"));
        }

        mIntialSize = mTulImagesArray.size();
        mAdapter = new TullFullImageAdapter(mContext, mTulImagesArray);
        vpImages.setAdapter(mAdapter);
        vpImages.setOffscreenPageLimit(mTulImagesArray.size());
        vpImages.setCurrentItem(getIntent().getIntExtra("position", 0));
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        txtExit.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TullFullViewActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (mIntialSize == mTulImagesArray.size()) {
                    finish();
                    overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                } else {
                    moveBack();
                }
                break;
            case R.id.img_delete:
                alertDialog();
                break;
            case R.id.txt_exit:
                finish();
                overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                break;
        }
    }

    private void moveBack() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("tullImages", mTulImagesArray);
        if (getIntent().hasExtra("attachment_ids"))
            intent.putStringArrayListExtra("attachment_ids", mAttachmentIds);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }

    @Override
    public void onBackPressed() {
        if (mIntialSize == mTulImagesArray.size()) {
            finish();
            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
        } else {
            moveBack();
        }
        super.onBackPressed();
    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int position = vpImages.getCurrentItem();
                        if (getIntent().hasExtra("attachment_ids"))
                            mAttachmentIds.add(String.valueOf(mTulImagesArray.get(position).getId()));
                        mTulImagesArray.remove(vpImages.getCurrentItem());
                        mAdapter = new TullFullImageAdapter(mContext, mTulImagesArray);
                        vpImages.setAdapter(mAdapter);
                        if (mTulImagesArray.size() > 0)
                            vpImages.setCurrentItem(position - 1);
                        else
                            moveBack();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


}
