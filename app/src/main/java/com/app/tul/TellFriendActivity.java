package com.app.tul;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import api.RetrofitClient;
import butterknife.BindView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class TellFriendActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.img_illustration)
    ImageView imgIllustration;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_off)
    TextView tvOffer;

    @BindView(R.id.ll_share)
    LinearLayout llShare;

    @BindView(R.id.ll_facebook)
    LinearLayout llFacebook;
    @BindView(R.id.img_facebook)
    ImageView imgFacebook;
    @BindView(R.id.tv_facebook)
    TextView tvFacebook;

    @BindView(R.id.ll_whatsapp)
    LinearLayout llWhatapp;
    @BindView(R.id.img_whatsapp)
    ImageView imgWhatsapp;
    @BindView(R.id.tv_whatsapp)
    TextView tvWhatsapp;

    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.img_email)
    ImageView imgEmail;
    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.ll_message)
    LinearLayout llMessage;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.tv_message)
    TextView tvMessage;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    final int READ_EXTERNAL_ID = 2;
    final int WRITE_EXTERNAL_ID = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_tell_friend;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        txtToolbarTitle.setText("TELL A FRIEND");

        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        tvName.setPadding(mWidth / 22, mHeight / 16, mWidth / 32, mHeight / 38);

        tvOffer.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvOffer.setPadding(mWidth / 22, 0, 0, mHeight / 18);

        LinearLayout.LayoutParams layoutPar = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutPar.setMargins(mWidth / 32, 0, mWidth / 32, mHeight / 32);
        llShare.setLayoutParams(layoutPar);

        tvFacebook.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
        tvFacebook.setPadding(0, mWidth / 64, 0, 0);

        tvEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
        tvEmail.setPadding(0, mWidth / 64, 0, 0);

        tvWhatsapp.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
        tvWhatsapp.setPadding(0, mWidth / 64, 0, 0);

        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
        tvMessage.setPadding(0, mWidth / 64, 0, 0);

    }

    @Override
    protected void onCreateStuff() {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        llFacebook.setOnClickListener(this);
        llWhatapp.setOnClickListener(this);
        llEmail.setOnClickListener(this);
        llMessage.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TellFriendActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
            case R.id.ll_facebook:
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("TUL")
                            .setContentDescription(getResources().getString(R.string.share_content))
                            .setContentUrl(Uri.parse("www.thetulapp.com"))
                            .setImageUrl(Uri.parse(RetrofitClient.BASE_URL + "assets/TUL-logo.png"))
                            .build();
                    shareDialog.show(linkContent);
                }
                break;
            case R.id.ll_whatsapp:
                if (ContextCompat.checkSelfPermission(TellFriendActivity.this, READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(TellFriendActivity.this, new String[]{READ_EXTERNAL_STORAGE}, READ_EXTERNAL_ID);
                } else {
                    shareWhatsapp();
                }
                break;
            case R.id.ll_message:
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("sms_body", getResources().getString(R.string.share_content));
                startActivity(Intent.createChooser(smsIntent, "SMS:"));
                break;
            case R.id.ll_email:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "TUL APP");
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_content));
                startActivity(intent);
                break;
        }
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void shareWhatsapp() {
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.share_img);
        File sdcard = Environment.getExternalStorageDirectory();
        File directory = new File(sdcard.getAbsolutePath()
                + "/TUL");
        if (directory.exists()) {
            directory.delete();
        }
        directory.mkdirs();

        File file1 = new File(directory, "share.png");
        if (!file1.exists()) {
            try {
                FileOutputStream outputStream = new FileOutputStream(file1);
                image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Intent tweetIntent = new Intent(Intent.ACTION_SEND);
            String path = Environment.getExternalStorageDirectory().toString();
            String imagepath = path + "/TUL/share.png";
            File f = new File(imagepath);
            tweetIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.share_content));
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                Uri photoURI = FileProvider.getUriForFile(TellFriendActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider", f);
                tweetIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
            } else {
                tweetIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            }
            tweetIntent.setType("image/*");
            PackageManager pm = getPackageManager();
            List<ResolveInfo> lract = pm.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);
            boolean resolved = false;
            for (ResolveInfo ri : lract) {
                if (ri.activityInfo.name.contains("whatsapp")) {
                    tweetIntent.setClassName(ri.activityInfo.packageName, ri.activityInfo.name);
                    resolved = true;
                    break;
                }
            }
            if (resolved) {
                System.out.println("yes has twitter");
                startActivity(resolved ? tweetIntent : Intent.createChooser(tweetIntent, "Choose one"));
            } else {
                Toast.makeText(mContext, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
            }
        } catch (final ActivityNotFoundException e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    shareWhatsapp();
                break;
            case READ_EXTERNAL_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    shareWhatsapp();
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
