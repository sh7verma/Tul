package com.app.tul;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.Connection_Detector;
import utils.Constants;
import utils.CustomLoadingDialog;

/**
 * Created by applify on 10/13/2017.
 */

public class CapturePhotoVideoActivity extends Activity {

    final int CAMERA_INTENT = 1;
    final int VIDEO_INTENT = 2;
    final int IMAGE_GALLERY_INTENT = 3;
    private static final int VIDEO_GALLERY_INTENT = 4;
    private static final int MULTIPLE_PERMISSIONS = 5;

    int mScreenwidth, mScreenheight;

    @BindView(R.id.txt_option_vp)
    TextView txtOptionVp;
    @BindView(R.id.txt_option_gallery)
    TextView txtOptionGallery;
    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    @BindView(R.id.txt_take_photo)
    TextView txtTakePhoto;
    @BindView(R.id.view_full)
    View viewFull;

    final int WRITE_EXTERNAL_ID = 1;
    final int READ_EXTERNAL_ID = 2;


    long mSystemTime;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private String camerapathVideo;
    private int mOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = this.getWindow().getAttributes();

        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        this.setFinishOnTouchOutside(true);
        setContentView(R.layout.dialog_photoselection);
        getDefaults();
        getWindow().setLayout((mScreenwidth), (int) (mScreenheight * 0.35));
        ButterKnife.bind(this);
        initUI();

        if (getIntent().hasExtra("visible")) {
            txtOptionVp.setVisibility(View.VISIBLE);
            viewFull.setVisibility(View.VISIBLE);
        } else {
            txtOptionVp.setVisibility(View.GONE);
            viewFull.setVisibility(View.GONE);
        }
    }

    void initUI() {

        txtTakePhoto.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.045));
        txtTakePhoto.setPadding(mScreenwidth / 64, mScreenwidth / 28, mScreenwidth / 64, mScreenwidth / 28);
        txtTakePhoto.setText(R.string.add_photo);
        txtTakePhoto.setAllCaps(true);

        txtOptionVp.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.045));
        txtOptionVp.setPadding(mScreenwidth / 64, mScreenwidth / 28, mScreenwidth / 64, mScreenwidth / 28);

        txtOptionGallery.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.045));
        txtOptionGallery.setPadding(mScreenwidth / 64, mScreenwidth / 28, mScreenwidth / 64, mScreenwidth / 28);
        txtOptionGallery.setText(R.string.capture_video);
        txtOptionGallery.setAllCaps(true);

        txtCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.045));
        txtCancel.setPadding(mScreenwidth / 64, mScreenwidth / 28, mScreenwidth / 64, mScreenwidth / 28);
    }


    @OnClick(R.id.txt_option_vp)
    void onFullView() {
        ArrayList<String> picArray = new ArrayList<>();
        picArray.add(getIntent().getStringExtra("path"));

        Intent intent = new Intent(CapturePhotoVideoActivity.this, FullViewImageActivity.class);
        intent.putStringArrayListExtra("pic_array", picArray);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.txt_option_gallery)
    void onVideo() {
        mOption = 2;
        if (checkPermissions()) {
            if (getIntent().getIntExtra("videoSize", 0) < 1)
                startVideoActivity();
            else
                Toast.makeText(this, R.string.error_video_count, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.txt_take_photo)
    void onCamera() {
        mOption = 1;
        if (checkPermissions()) {
            if (getIntent().getIntExtra("imageSize", 0) < 6)
                startCameraActivity();
            else
                Toast.makeText(this, R.string.error_image_count, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(CapturePhotoVideoActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @OnClick(R.id.txt_cancel)
    void onCancel() {
        finish();
    }

    void getDefaults() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenwidth = dm.widthPixels;
        mScreenheight = dm.heightPixels;
    }

    private void startCameraActivity() {
        mOption = 1;
        new BottomSheet.Builder(CapturePhotoVideoActivity.this, R.style.BottomSheet_Dialog)
                .title("Pick One")
                .sheet(R.menu.menu_options_add_tul).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.item_camera:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mSystemTime = System.currentTimeMillis();
                        File f = new File(Environment.getExternalStorageDirectory(), "TUL" + mSystemTime + ".png");
                        if (android.os.Build.VERSION.SDK_INT >= 24) {
                            Uri photoURI = FileProvider.getUriForFile(CapturePhotoVideoActivity.this,
                                    BuildConfig.APPLICATION_ID + ".provider", f);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        } else {
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        }
                        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        startActivityForResult(cameraIntent, CAMERA_INTENT);
                        break;
                    case R.id.item_gallery:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, IMAGE_GALLERY_INTENT);
                        break;
                }
            }
        }).show();
    }

    void startVideoActivity() {
        try {
            mOption = 2;
            new BottomSheet.Builder(CapturePhotoVideoActivity.this, R.style.BottomSheet_Dialog)
                    .title("Pick One")
                    .sheet(R.menu.menu_options_add_tul).listener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case R.id.item_camera:
                            Calendar c = Calendar.getInstance();
                            camerapathVideo = Environment.getExternalStorageDirectory() + "/TUL/Video/Sent/" + "VID" + c.getTimeInMillis() + ".mp4";
                            File file = new File(camerapathVideo);
                            file.getParentFile().mkdirs();
                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            if (android.os.Build.VERSION.SDK_INT >= 24) {
                                Uri photoURI = FileProvider.getUriForFile(CapturePhotoVideoActivity.this,
                                        BuildConfig.APPLICATION_ID + ".provider", file);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            } else {
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                            }
                            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                            startActivityForResult(intent, VIDEO_INTENT);
                            break;
                        case R.id.item_gallery:
                            Intent intentVideoGallery = new Intent();
                            intentVideoGallery.setType("video/*");
                            intentVideoGallery.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intentVideoGallery, VIDEO_GALLERY_INTENT);
                            break;
                    }
                }
            }).show();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length == 2) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
                        if (mOption == 1)
                            startCameraActivity();
                        else
                            startVideoActivity();
                    }
                } else if (grantResults.length == 1) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
                        if (mOption == 1)
                            startCameraActivity();
                        else
                            startVideoActivity();
                    }
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAMERA_INTENT && resultCode == RESULT_OK) {
                File dir = Environment.getExternalStorageDirectory();
                File f = new File(dir, "TUL" + mSystemTime + ".png");
                Log.e("camera photo", "is " + f.getAbsolutePath());
                Uri rr = Uri.fromFile(new File(f.getAbsolutePath()));
                beginCrop(rr);
            } else if (requestCode == IMAGE_GALLERY_INTENT && resultCode == RESULT_OK) {
                // image from gallery
                Uri uri = data.getData();
                beginCrop(uri);
            } else if (requestCode == VIDEO_INTENT && resultCode == RESULT_OK) {
                if (!camerapathVideo.equals("")) {
                    File ff = new File(camerapathVideo);
                    Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(ff.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                    Intent ii = new Intent();
                    ii.putExtra("TYPE", Constants.VIDEO_TEXT);
                    ii.putExtra("thumbnail", getRealPathFromURI(getImageUri(CapturePhotoVideoActivity.this, bitmap)));
                    ii.putExtra("filePath", camerapathVideo);
                    setResult(RESULT_OK, ii);
                    finish();
                } else {
                    Toast.makeText(this, R.string.something_was_wrong, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == VIDEO_GALLERY_INTENT && resultCode == RESULT_OK) {
                try {
                    final Uri uri = data.getData();
                    final String path = FileUtils.getPath(this, uri);
                    File file = new File(path);
                    long length = file.length();
                    if ((length / 1024) > (1024 * 100)) {//more than 100MB
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_size_exceeds), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File ff = new File(path);
                    Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(ff.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                    Intent ii = new Intent();
                    ii.putExtra("TYPE", Constants.VIDEO_TEXT);
                    ii.putExtra("thumbnail", getRealPathFromURI(getImageUri(CapturePhotoVideoActivity.this, bitmap)));
                    ii.putExtra("filePath", path);
                    setResult(RESULT_OK, ii);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, R.string.something_was_wrong, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                Log.e("crop photo", "is " + data);
                handleCrop(resultCode, data);
            }

        } catch (Exception e) {
            Log.e("exc photo", "is " + e);
            e.printStackTrace();
        }
    }

    private void handleCrop(int resultCode, Intent result) {
        try {
            if (resultCode == RESULT_OK) {
                Uri ur = Crop.getOutput(result);
                String picturePath = FileUtils.getPath(this, ur);
                Intent ii = new Intent();
                ii.putExtra("TYPE", Constants.IMAGE_TEXT);
                ii.putExtra("filePath", getRealPathFromURI(getImageUri(CapturePhotoVideoActivity.this, getBitmap(picturePath))));
                setResult(RESULT_OK, ii);
                finish();
            } else if (resultCode == Crop.RESULT_ERROR) {
                Toast.makeText(this, R.string.something_was_wrong, Toast.LENGTH_SHORT)
                        .show();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(this, R.string.something_was_wrong, Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(String path) {
        Bitmap scaledBitmap = null;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap sourceBitmap = BitmapFactory.decodeFile(path, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
            sourceBitmap = BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError e) {
            Log.e("Exce = ", e + "");
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(sourceBitmap, middleX - sourceBitmap.getWidth() / 2, middleY - sourceBitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        ExifInterface exif;
        try {
            exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);

        return scaledBitmap;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }
}
