package com.app.tul;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * Created by app on 9/19/2016.
 */
public class OptionPhotoSelection extends Activity {

    private static final int MULTIPLE_PERMISSIONS = 3;
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

    final int CAMERA_INTENT = 1;
    final int GALLERY_INTENT = 2;

    long mSystemTime;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

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

        txtOptionVp.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.045));
        txtOptionVp.setPadding(mScreenwidth / 64, mScreenwidth / 28, mScreenwidth / 64, mScreenwidth / 28);

        txtOptionGallery.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.045));
        txtOptionGallery.setPadding(mScreenwidth / 64, mScreenwidth / 28, mScreenwidth / 64, mScreenwidth / 28);

        txtCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mScreenwidth * 0.045));
        txtCancel.setPadding(mScreenwidth / 64, mScreenwidth / 28, mScreenwidth / 64, mScreenwidth / 28);
    }

    void showGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_INTENT);
    }

    @OnClick(R.id.txt_option_vp)
    void onFullView() {
        ArrayList<String> picArray = new ArrayList<>();
        picArray.add(getIntent().getStringExtra("path"));

        Intent intent = new Intent(OptionPhotoSelection.this, FullViewImageActivity.class);
        intent.putStringArrayListExtra("pic_array", picArray);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.txt_option_gallery)
    void onGallery() {
        if (ContextCompat.checkSelfPermission(OptionPhotoSelection.this, READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(OptionPhotoSelection.this, new String[]{READ_EXTERNAL_STORAGE}, READ_EXTERNAL_ID);
        } else {
            showGallery();
        }
    }

    @OnClick(R.id.txt_take_photo)
    void onCamera() {
        if (checkPermissions()) {
            startCameraActivity();
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(OptionPhotoSelection.this, p);
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

    void startCameraActivity() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mSystemTime = System.currentTimeMillis();
        File f = new File(Environment.getExternalStorageDirectory(), "TUL" + mSystemTime + ".png");
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            Uri photoURI = FileProvider.getUriForFile(OptionPhotoSelection.this,
                    BuildConfig.APPLICATION_ID + ".provider", f);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        } else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        }
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startActivityForResult(cameraIntent, CAMERA_INTENT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startCameraActivity();
                break;
            case READ_EXTERNAL_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    showGallery();
                break;
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length == 2) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
                        startCameraActivity();
                    }
                } else if (grantResults.length == 1) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
                        startCameraActivity();
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
            } else if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                beginCrop(selectedImage);
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
                ii.putExtra("path", picturePath);
                ii.putExtra("filePath", getRealPathFromURI(getImageUri(OptionPhotoSelection.this, getBitmap(picturePath))));
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
        int rotation = getImageOrientation(path);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap sourceBitmap = BitmapFactory.decodeFile(path, options);

        Bitmap imgBm = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix,
                true);

        return imgBm;
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