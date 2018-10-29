package com.app.tul;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import api.RetrofitClient;
import butterknife.BindView;
import model.AttachmentModel;
import model.BookTulModel;
import model.DemoModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class SignatureActivity extends BaseActivity {

    private static final int RETURNED_TUL = 1;
    private static final int RECEIVED_TUL = 2;
    private static final int DONE_CLICKED = 3;
    public boolean gestureTouch = false;
    @BindView(R.id.txt_toolbar_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_clear)
    TextView txtGestureClear;

    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.tv_sign_detail)
    TextView tvSignDetail;
    @BindView(R.id.tv_done)
    TextView tvDone;
    @BindView(R.id.tv_report)
    TextView tvReport;

    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.gesture_signature)
    GestureOverlayView gestureView;
    boolean isSigned = false;
    Bitmap bitmap;
    private String mPath;

    BookTulModel.ResponseBean mTulDataModel;
    public int mReportType;
    private double mEarnedPrice;

    @Override
    protected int getContentView() {
        return R.layout.activity_signature;
    }

    @Override
    protected void initUI() {

        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        tvTitle.setText("SIGNATURE");

        txtGestureClear.setVisibility(View.VISIBLE);
        txtGestureClear.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtGestureClear.setText("CLEAR");
        txtGestureClear.setPadding(0, 0, mWidth / 32, 0);

        tvSignDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvSignDetail.setPadding(mWidth / 22, mHeight / 32, mWidth / 22, mHeight / 32);

        gestureView.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvDone.setPadding(mWidth / 32, mHeight / 42, mWidth / 32, mHeight / 42);

        tvReport.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvReport.setPadding(mWidth / 32, mHeight / 42, mWidth / 32, mHeight / 42);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(mWidth / 22, 0, mWidth / 22, mHeight / 48);
        tvDone.setLayoutParams(layoutParams);
        tvReport.setLayoutParams(layoutParams);

        tvSign.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        gestureView.setDrawingCacheEnabled(true);
        gestureView.setGestureStrokeWidth((float) (mWidth * 0.01));
        gestureView.setAlwaysDrawnWithCacheEnabled(true);
        gestureView.setHapticFeedbackEnabled(false);
        gestureView.cancelLongPress();
        gestureView.cancelClearAnimation();
        gestureView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {

            @Override
            public void onGesture(GestureOverlayView arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                Log.e("on gesture", "on");
                isSigned = false;
            }

            @Override
            public void onGestureCancelled(GestureOverlayView arg0,
                                           MotionEvent arg1) {
                // TODO Auto-generated method stub
                Log.e("on gesture", "cancel");
                isSigned = false;
            }

            @Override
            public void onGestureEnded(GestureOverlayView arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                Log.e("on gesture", "ended");
                isSigned = true;
            }

            @Override
            public void onGestureStarted(GestureOverlayView arg0,
                                         MotionEvent arg1) {
                // TODO Auto-generated method stub
                Log.e("on gesture", "start");
                tvSign.setVisibility(View.GONE);
                isSigned = false;
                if (arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    gestureTouch = false;
                } else {
                    gestureTouch = true;
                }
            }
        });

    }

    @Override
    protected void onCreateStuff() {
        mPath = getIntent().getStringExtra("path");

        if (getIntent().hasExtra("tulData")) {
            mTulDataModel = getIntent().getParcelableExtra("tulData");
            mEarnedPrice = Double.parseDouble(mTulDataModel.getTotal_amount()) -
                    (Double.parseDouble(mTulDataModel.getTransaction_fee()) + Double.parseDouble(mTulDataModel.getAdditional_charges().getSecurity_charges()));
        }

        if (mPath.equals("1"))
            tvSignDetail.setText("I hereby declare that I am cancelling TUL " + mTulDataModel.getTitle() + " booking.");

        if (mPath.equals("1") || mPath.equals("2") || mPath.equals("3"))
            tvReport.setVisibility(View.GONE);

        if (mPath.equals("4"))/// borrower returning tul
            mReportType = Constants.USER_REPORT;
        else if (mPath.equals("5"))/// lender receiving tul
            mReportType = Constants.LENDER_REPORT;
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        txtGestureClear.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return SignatureActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.tv_report:
                alertReportDialog();
                break;
            case R.id.tv_done:
                if (ContextCompat.checkSelfPermission(mContext, READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(SignatureActivity.this, new String[]{READ_EXTERNAL_STORAGE}, DONE_CLICKED);
                } else {
                    onDoneClicked();
                }
                break;
            case R.id.txt_toolbar_clear:
                gestureView.cancelClearAnimation();
                gestureView.clear(true);
                gestureView.invalidate();
                gestureView.clearAnimation();
                isSigned = false;
                break;
        }
    }

    private void alertReportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.report_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (connectedToInternet())
                            hitReportAPI();
                        else
                            Toast.makeText(mContext, errorInternet, Toast.LENGTH_SHORT).show();
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

    private void hitLenderReceivedAPI(File file) {
        Call<DemoModel> call = RetrofitClient.getInstance().markAsReceivedByLender(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(getIntent().getStringExtra("booking_id")),
                createPartFromString(getIntent().getStringExtra("tool_id")),
                prepareFilePart(file));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
//                    db.updateLentStatus(Integer.parseInt(getIntent().getStringExtra("booking_id")),
//                            response.body().response.getDate(),"2");
                    /// deleteing from active booking lent table.
                    db.deleteLendedTul(Integer.parseInt(getIntent().getStringExtra("booking_id")), getIntent().getStringExtra("tool_id"));
                    /// adding value in history lend table
                    db.addHistoryLendTul(mTulDataModel);
                    for (AttachmentModel attachmentModel : mTulDataModel.getAttachment()) {
                        db.addHistoryLentAttachment(attachmentModel);
                    }
//                    Toast.makeText(SignatureActivity.this, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, RateYourExperienceActivity.class);
                    intent.putExtra("date", response.body().response.getDate());
                    intent.putExtra("path", 1);
                    intent.putExtra("tool_id", getIntent().getStringExtra("tool_id"));
                    intent.putExtra("booking_id", getIntent().getStringExtra("booking_id"));
                    intent.putExtra("owner", getIntent().getStringExtra("owner"));
                    intent.putExtra("ownerPic", getIntent().getStringExtra("ownerPic"));
                    intent.putExtra("totalAmount", String.format("%.2f", mEarnedPrice));
                    intent.putExtra("address", getIntent().getStringExtra("address"));
                    intent.putExtra("borrower", getIntent().getStringExtra("borrower"));
                    intent.putExtra("borrower_pic", getIntent().getStringExtra("borrower_pic"));
                    startActivityForResult(intent, RECEIVED_TUL);
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

    private void hitBorrowerReturnedAPI(File file) {
        Call<DemoModel> call = RetrofitClient.getInstance().markAsReturnedByBorrower(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(getIntent().getStringExtra("booking_id")),
                createPartFromString(getIntent().getStringExtra("tool_id")),
                prepareFilePart(file));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    /// updating borrow status in booking table
                    db.updateBorrowerStatus(Integer.parseInt(getIntent().getStringExtra("booking_id")),
                            response.body().response.getDate(), "2");
//                    Toast.makeText(SignatureActivity.this, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, RateYourExperienceActivity.class);
                    intent.putExtra("date", response.body().response.getDate());
                    intent.putExtra("path", 2);
                    intent.putExtra("tool_id", getIntent().getStringExtra("tool_id"));
                    intent.putExtra("booking_id", getIntent().getStringExtra("booking_id"));
                    intent.putExtra("owner", getIntent().getStringExtra("owner"));
                    intent.putExtra("ownerPic", getIntent().getStringExtra("ownerPic"));
                    intent.putExtra("totalAmount", getIntent().getStringExtra("totalAmount"));
                    intent.putExtra("address", getIntent().getStringExtra("address"));
                    startActivityForResult(intent, RETURNED_TUL);
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

    private void hitBorrowerReceivedAPI(File file) {
        Call<DemoModel> call = RetrofitClient.getInstance().markAsReceivedByBorrower(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(getIntent().getStringExtra("booking_id")),
                createPartFromString(getIntent().getStringExtra("tool_id")),
                prepareFilePart(file));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    db.updateBorrowerStatus(Integer.parseInt(getIntent().getStringExtra("booking_id")),
                            response.body().response.getDate(), "1");
//                    Toast.makeText(SignatureActivity.this, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                    moveBackChangeStatus(response.body().response.getDate());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

    private void hitHandOverAPI(File file) {
        Call<DemoModel> call = RetrofitClient.getInstance().markAsHandover(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(getIntent().getStringExtra("booking_id")),
                createPartFromString(getIntent().getStringExtra("tool_id")),
                prepareFilePart(file));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    db.updateLentStatus(Integer.parseInt(getIntent().getStringExtra("booking_id")), response.body().response.getDate(), "1");
//                    Toast.makeText(SignatureActivity.this, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                    moveBackChangeStatus(response.body().response.getDate());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }


    private void hitCancelAPI(File file) {
        Call<DemoModel> call = RetrofitClient.getInstance().cancelBooking(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(getIntent().getStringExtra("booking_id")),
                createPartFromString(getIntent().getStringExtra("tool_id")),
                createPartFromString(getIntent().getStringExtra("cancel_status")),
                prepareFilePart(file));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    if (getIntent().getStringExtra("cancel_status").equals("1")) {
                        /// lender
                        db.deleteLendedTul(Integer.parseInt(getIntent().getStringExtra("booking_id"))
                                , getIntent().getStringExtra("tool_id"));
                        /// adding in history lented table
                        if (mTulDataModel != null) {
                            mTulDataModel.setCancelled_at(response.body().response.getDate());
                            mTulDataModel.setLender_status(3);
                            db.addHistoryLendTul(mTulDataModel);
                            for (AttachmentModel attachmentModel : mTulDataModel.getAttachment()) {
                                db.addHistoryLentAttachment(attachmentModel);
                            }
                        }
                    } else if (getIntent().getStringExtra("cancel_status").equals("2")) {
                        /// booking
                        /// delete from booking section
                        db.deleteBooking(Integer.parseInt(getIntent().getStringExtra("booking_id"))
                                , getIntent().getStringExtra("tool_id"));
                        /// adding in history rented table
                        if (mTulDataModel != null) {
                            mTulDataModel.setCancelled_at(response.body().response.getDate());
                            mTulDataModel.setBorrower_status(3);
                            db.addHistoryRentedTul(mTulDataModel);
                            for (AttachmentModel attachmentModel : mTulDataModel.getAttachment()) {
                                db.addHistoryRentedAttachment(attachmentModel);
                            }
                        }
                    }

                    Toast.makeText(SignatureActivity.this, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                    moveBack();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals("301")) {
                        Toast.makeText(SignatureActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                hideProgress();
            }
        });

    }

    private void hitReportAPI() {
        Call<DemoModel> call = RetrofitClient.getInstance().ReportTul(utils.getString("access_token", ""),
                Integer.parseInt(getIntent().getStringExtra("tool_id")),
                mReportType,
                getIntent().getIntExtra("user_id", 0),
                getIntent().getIntExtra("borrower_id", 0),1);

        call.enqueue(new retrofit2.Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                if (response.body().response != null) {
                    showAlert(llMain, response.body().response.getMessage());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                hideProgress();
                showAlert(llMain, t.getLocalizedMessage());
            }
        });
    }


    private void moveBack() {
        Intent intent = new Intent();
        if (getIntent().hasExtra("position"))
            intent.putExtra("position", getIntent().getIntExtra("position", 0));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void moveBackChangeStatus(String date) {
        Intent intent = new Intent();
        if (getIntent().hasExtra("position"))
            intent.putExtra("position", getIntent().getIntExtra("position", 0));
        intent.putExtra("date", date);
        setResult(RESULT_OK, intent);
        finish();
    }

    private RequestBody createPartFromString(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    private MultipartBody.Part prepareFilePart(File mFile) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), mFile);
        return MultipartBody.Part.createFormData("signature", mFile.getName(), requestFile);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RETURNED_TUL) {
                moveBackChangeStatus(data.getStringExtra("date"));
            } else if (requestCode == RECEIVED_TUL) {
                moveBackChangeStatus(data.getStringExtra("date"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case DONE_CLICKED:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onDoneClicked();
                break;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void onDoneClicked() {
        if (isSigned) {
            bitmap = Bitmap.createBitmap(gestureView.getDrawingCache());

            if (bitmap != null) {
                if (connectedToInternet()) {
                    showProgress();
                    if (mPath.equals("1"))/// cancel booking from lent and booking
                        hitCancelAPI(getFile());
                    else if (mPath.equals("2"))/// lender marked as handover
                        hitHandOverAPI(getFile());
                    else if (mPath.equals("3"))/// borrower marked as received
                        hitBorrowerReceivedAPI(getFile());
                    else if (mPath.equals("4"))/// borrower marked as returned
                        hitBorrowerReturnedAPI(getFile());
                    else if (mPath.equals("5"))/// lender received tul back
                        hitLenderReceivedAPI(getFile());
                } else
                    showAlert(llMain, errorInternet);
            } else {
                showAlert(llMain, getString(R.string.sign_above));
            }
        } else
            showAlert(llMain, getString(R.string.sign_above));
    }

    private File getFile() {
        File file = new File(mContext.getCacheDir(), "signature");
        try {
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
