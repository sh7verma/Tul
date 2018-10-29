package dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.tul.BuildConfig;
import com.app.tul.R;
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
import butterknife.ButterKnife;
import utils.Connection_Detector;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by dev on 27/8/18.
 */

public class ShareTulDialog implements View.OnClickListener {

    final int READ_EXTERNAL_ID = 2;
    final int WRITE_EXTERNAL_ID = 1;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_facebook)
    LinearLayout llFacebook;
    @BindView(R.id.ll_whatsapp)
    LinearLayout llWhatsapp;
    @BindView(R.id.ll_linkedin)
    LinearLayout llLinkedin;
    @BindView(R.id.ll_message)
    LinearLayout llMessage;
    @BindView(R.id.ll_copy)
    LinearLayout llCopy;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    int tool_id;
    String url;
    private Context mContext;
    private int mWidth;
    private Dialog mDialog;
    private String mText;
    private int path;

    public ShareTulDialog(Context context, int mWidth, String text, int path, int tool_id) {
        mContext = context;
        this.mWidth = mWidth;
        this.path = path;
        this.tool_id = tool_id;
        this.mText = text;
    }

    public void showDialog() {
        View view;
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(mContext, R.layout.dialog_tul_share, null);
        mDialog.setContentView(view);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, view);
        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideAnim;
        initUI();
        mDialog.show();
        initListeners();
    }

    private void initUI() {

    }

    private void initListeners() {
        llTop.setOnClickListener(this);
        llFacebook.setOnClickListener(this);
        llWhatsapp.setOnClickListener(this);
        llLinkedin.setOnClickListener(this);
        llMessage.setOnClickListener(this);
        llCopy.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog((Activity) mContext);
    }

    @Override
    public void onClick(View v) {

        url = RetrofitClient.BASE_URL + "tuls?id=" + tool_id;

        switch (v.getId()) {
            case R.id.ll_top:
                mDialog.dismiss();
                break;
            case R.id.ll_linkedin:
                if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                    shareLinkedIn();
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ll_copy:
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", url);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, mContext.getResources().getString(R.string.copy_), Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_facebook:
                if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("TUL")
                                .setContentDescription(mContext.getResources().getString(R.string.share_content))
                                .setContentUrl(Uri.parse(url))

//                        13.228.223.105:3031//tuls?id=78236
//                                .setContentUrl(Uri.parse(RetrofitClient.BASE_URL + "/tuls?id=" + tool_id))
                                .setImageUrl(Uri.parse(RetrofitClient.BASE_URL + "assets/TUL-logo.png"))
                                .build();
                        shareDialog.show(linkContent);
                    }
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ll_whatsapp:
                if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                    if (ContextCompat.checkSelfPermission(mContext, READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{READ_EXTERNAL_STORAGE}, READ_EXTERNAL_ID);
                    } else {
                        shareWhatsapp();
                    }
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ll_message:
                Uri sms_uri = Uri.parse("smsto:" + "");
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                sms_intent.putExtra("sms_body", url);
                mContext.startActivity(sms_intent);


//                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
//                smsIntent.setType("vnd.android-dir/mms-sms");
//                smsIntent.putExtra("sms_body", mContext.getResources().getString(R.string.share_content));
//                mContext.startActivity(Intent.createChooser(smsIntent, "SMS:"));
                break;

        }
    }

    private void shareWhatsapp() {
        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.share_img);
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

            tweetIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    url);

            if (android.os.Build.VERSION.SDK_INT >= 24) {
                Uri photoURI = FileProvider.getUriForFile(mContext,
                        BuildConfig.APPLICATION_ID + ".provider", f);
                tweetIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
            } else {
                tweetIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            }
            tweetIntent.setType("image/*");
            PackageManager pm = mContext.getPackageManager();
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
                System.out.println("yes has WhatsApp");
                mContext.startActivity(resolved ? tweetIntent : Intent.createChooser(tweetIntent, "Choose one"));
            } else {
                Toast.makeText(mContext, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
            }
        } catch (final ActivityNotFoundException e) {
        }
    }


    void shareLinkedIn() {
//        String url = "https://api.linkedin.com/v1/people/~/shares";
//
//        String payload = "{" +
//                "\"comment\":\"Check out developer.linkedin.com! " +
//                "http://linkd.in/1FC2PyG\"," +
//                "\"visibility\":{" +
//                "    \"code\":\"anyone\"}" +
//                "}";
//
//        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
//        APIHelper.postRequest(this, url, payload, new ApiListener() {
//            @Override
//            public void onApiSuccess(ApiResponse apiResponse) {
//                // Success!
//            }
//
//            @Override
//            public void onApiError(LIApiError liApiError) {
//                // Error making POST request!
//            }
//        });

        String mData = url;
//        String mUrl = "https://www.linkedin.com/shareArticle?" +
//                "mini=true&" +
//                "url=" + mContext.getResources().getString(R.string.share_content) + "&";
        String mUrl = url;

        Intent linkedinIntent = new Intent(Intent.ACTION_SEND);
        linkedinIntent.setType("text/plain");
//          .setImageUrl(Uri.parse(RetrofitClient.BASE_URL + "assets/TUL-logo.png"))
        linkedinIntent.putExtra(Intent.EXTRA_TEXT, mData); //here I have added app page url .
        boolean linkedinAppFound = false; //check app insall in your phone .
        List<ResolveInfo> matches2 = mContext.getPackageManager()
                .queryIntentActivities(linkedinIntent, 0);
        for (ResolveInfo info : matches2) {
            if (info.activityInfo.packageName.toLowerCase().startsWith( //added packageName & className
                    "com.linkedin")) {
                linkedinIntent.setPackage(info.activityInfo.packageName);
                linkedinAppFound = true;
                break;
            }
        }
        if (linkedinAppFound) {
            mContext.startActivity(linkedinIntent);
        } else {
            Toast.makeText(mContext, "LinkedIn app not Insatlled in your mobile", Toast.LENGTH_LONG).show();
            Intent share = new Intent();
            share.setAction(Intent.ACTION_VIEW);
            share.setData(Uri.parse(mUrl));
            mContext.startActivity(share);
        }
    }


}
