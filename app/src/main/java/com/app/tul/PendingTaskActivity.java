package com.app.tul;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.Space;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class PendingTaskActivity extends BaseActivity {

    private static final int PHONE = 4;
    final int CANCEL_CLICKED = 0;
    final int RECEIVED_CLICKED = 1;
    final int NOT_RECEIVED_CLICKED = 2;
    public boolean gestureTouch = false;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.ll_upper)
    LinearLayout llUpper;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.ll_tul_name)
    LinearLayout llTulName;
    @BindView(R.id.tv_tul_name)
    TextView tvTulName;
    @BindView(R.id.tv_tul_type)
    TextView tvTulType;
    @BindView(R.id.tv_booking_date)
    TextView tvBookingDate;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ll_below)
    LinearLayout llBelow;
    @BindView(R.id.ll_owner)
    LinearLayout llOwner;
    @BindView(R.id.tv_owner)
    TextView tvOwner;
    @BindView(R.id.tv_owner_name)
    TextView tvOwnerName;
    @BindView(R.id.tv_not_received)
    TextView tvNotReceived;
    @BindView(R.id.txt_toolbar_clear)
    TextView txtGestureClear;
    @BindView(R.id.gesture_signature)
    GestureOverlayView gestureSignature;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.ll_buttons)
    LinearLayout llButton;
    @BindView(R.id.tv_bt_not_received)
    TextView btNotReceived;
    @BindView(R.id.tv_bt_received)
    TextView btReceived;
    @BindView(R.id.space)
    Space space;
    @BindView(R.id.txt_done)
    TextView txtDone;
    boolean isSigned = false;
    Bitmap bitmap;
    BookTulModel.ResponseBean mPendingModels;
    private double mEarnedPrice;

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_signature;
    }

    @Override
    protected void initUI() {

        llUpper.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, 0);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mHeight / 6, mHeight / 6);
        imgUser.setLayoutParams(layoutParams);

        llTulName.setPadding(mWidth / 16, 0, 0, 0);

        tvTulName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));

        tvTulType.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTulType.setPadding(0, 0, 0, mWidth / 32);

        tvBookingDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

        llBelow.setPadding(mWidth / 32, mHeight / 60, mWidth / 32, mHeight / 32);

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(0, mHeight / 42, 0, 0);
        tvNotReceived.setLayoutParams(layoutParams1);

        tvNotReceived.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvNotReceived.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);

        tvOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvOwnerName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        llButton.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        RelativeLayout.LayoutParams doneParms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        doneParms.setMargins(mWidth / 32, 0, mWidth / 32, mWidth / 32);
        doneParms.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        txtDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtDone.setPadding(0, mHeight / 48, 0, mHeight / 48);
        txtDone.setLayoutParams(doneParms);

        btReceived.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btReceived.setPadding(0, mHeight / 42, 0, mHeight / 42);

        btNotReceived.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btNotReceived.setPadding(0, mHeight / 42, 0, mHeight / 42);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mWidth / 32, ViewGroup.LayoutParams.MATCH_PARENT);
        space.setLayoutParams(params);

        tvSign.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(0, mWidth / 52, mWidth / 52, 0);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        txtGestureClear.setLayoutParams(layoutParams2);

        txtGestureClear.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtGestureClear.setPadding(mWidth / 52, mWidth / 52, mWidth / 52, mWidth / 52);

    }

    @Override
    protected void onCreateStuff() {
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        if (getIntent().hasExtra("data")) {
            mPendingModels = getIntent().getParcelableExtra("data");
            setData();
        } else if (getIntent().hasExtra("booking_id")) {
            if (connectedToInternet())
                hitPushAPI();
            else
                showInternetAlert(llBelow);
        }

        if (getIntent().hasExtra("push_id"))
            hitReadNotificationAPI(getIntent().getStringExtra("push_id"));

        gestureSignature.setDrawingCacheEnabled(true);
        gestureSignature.setGestureStrokeWidth((float) (mWidth * 0.01));
        gestureSignature.setAlwaysDrawnWithCacheEnabled(true);
        gestureSignature.setHapticFeedbackEnabled(false);
        gestureSignature.cancelLongPress();
        gestureSignature.cancelClearAnimation();
        gestureSignature.addOnGestureListener(new GestureOverlayView.OnGestureListener() {

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

    private void hitPushAPI() {
        llMain.setVisibility(View.INVISIBLE);
        showProgress();
        Call<BookTulModel> call = RetrofitClient.getInstance().getPushDeatilBookingId(utils.getString("access_token", ""),
                Integer.parseInt(getIntent().getStringExtra("booking_id")),
                Integer.parseInt(getIntent().getStringExtra("push_type")));
        call.enqueue(new Callback<BookTulModel>() {
            @Override
            public void onResponse(Call<BookTulModel> call, Response<BookTulModel> response) {
                hideProgress();
                llMain.setVisibility(View.VISIBLE);
                if (response.body().getResponse() != null) {
                    setPushData(response.body().getResponse());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(getString(R.string.error_already_marked))) {
                        finish();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BookTulModel> call, Throwable t) {
                hideProgress();
                showAlert(llBelow, t.getLocalizedMessage());
            }
        });

    }

    private void setPushData(BookTulModel.ResponseBean response) {
        mPendingModels = response;
        tvTulName.setText(mPendingModels.getTitle());
        tvTulType.setText(mPendingModels.getCategory_name());
        if (mPendingModels.getAttachment().get(0).getType().equals(Constants.IMAGE_TEXT))
            Picasso.with(this).load(mPendingModels.getAttachment().get(0).getAttachment()).resize(mHeight / 6, mHeight / 6).centerCrop().into(imgUser);
        else
            Picasso.with(this).load(mPendingModels.getAttachment().get(0).getThumbnail()).resize(mHeight / 6, mHeight / 6).centerCrop().into(imgUser);

        if (mPendingModels.getType() == 1) {/// cancelled by lender.
            // Visible on borrower side
            tvNotReceived.setText(R.string.camcellled_);
            llButton.setVisibility(View.GONE);
            txtDone.setVisibility(View.VISIBLE);
            displayCancelled();
            tvOwnerName.setText(mPendingModels.getOwner());
        } else if (mPendingModels.getType() == 2) {/// cancelled by borrower
            /// visible on lender side
            tvNotReceived.setText(R.string.camcellled_);
            llButton.setVisibility(View.GONE);
            txtDone.setVisibility(View.VISIBLE);
            tvOwner.setText(R.string.borrower_);
            displayCancelled();
            tvOwnerName.setText(mPendingModels.getBorrower());
        } else if (mPendingModels.getType() == 3) {/// Returned by borrower
            /// visible on lender section
            tvNotReceived.setText(R.string.returned_);
            llButton.setVisibility(View.VISIBLE);
            txtDone.setVisibility(View.GONE);
            btReceived.setText(R.string.received_);
            btNotReceived.setText(R.string.not_received_);
            tvOwner.setText(R.string.borrower_);
            tvOwnerName.setText(mPendingModels.getBorrower());
        } else if (mPendingModels.getType() == 4) {/// Handedover by lender section
            /// visible on borrower side
            tvNotReceived.setText(R.string.borrower_);
            llButton.setVisibility(View.VISIBLE);
            txtDone.setVisibility(View.GONE);
            tvOwnerName.setText(mPendingModels.getOwner());
        }

        try {
            tvDate.setText(Constants.convertDate(mPendingModels.getDelivery_date()) + " -  " + Constants.convertDate(mPendingModels.getReturn_date()));
        } catch (Exception e) {
            Log.e("Date Exce  = ", e + "");
        }

        mEarnedPrice = Double.parseDouble(mPendingModels.getTotal_amount()) -
                (Double.parseDouble(mPendingModels.getTransaction_fee()) + Double.parseDouble(mPendingModels.getAdditional_charges().getSecurity_charges()));

    }

    public void setData() {

       /*type 1 if lender cancel booking
        type 2: if borrower cancel booking
        type 3: if borrower mark tool as return
        type 4: if lender mark as handover tul*/

        tvTulName.setText(mPendingModels.getTitle());
        tvTulType.setText(mPendingModels.getCategory_name());
        if (mPendingModels.getAttachment().get(0).getType().equals(Constants.IMAGE_TEXT))
            Picasso.with(this).load(mPendingModels.getAttachment().get(0).getAttachment()).resize(mHeight / 6, mHeight / 6).centerCrop().into(imgUser);
        else
            Picasso.with(this).load(mPendingModels.getAttachment().get(0).getThumbnail()).resize(mHeight / 6, mHeight / 6).centerCrop().into(imgUser);

        tvOwnerName.setText(mPendingModels.getOwner());

        if (mPendingModels.getType() == 1) {/// cancelled by lender.
            // Visible on borrower side
            tvNotReceived.setText(R.string.camcellled_);
            llButton.setVisibility(View.GONE);
            txtDone.setVisibility(View.VISIBLE);
            displayCancelled();
            tvOwnerName.setText(mPendingModels.getOwner());
        } else if (mPendingModels.getType() == 2) {/// cancelled by borrower
            /// visible on lender side
            tvNotReceived.setText(R.string.camcellled_);
            llButton.setVisibility(View.GONE);
            txtDone.setVisibility(View.VISIBLE);
            tvOwner.setText(R.string.borrower_);
            displayCancelled();
            tvOwnerName.setText(mPendingModels.getBorrower());
        } else if (mPendingModels.getType() == 3) {/// Returned by borrower
            /// visible on lender section
            tvNotReceived.setText(R.string.returned_);
            llButton.setVisibility(View.VISIBLE);
            txtDone.setVisibility(View.GONE);
            btReceived.setText(R.string.received_);
            btNotReceived.setText(R.string.not_received_);
            tvOwner.setText(R.string.borrower_);
            tvOwnerName.setText(mPendingModels.getBorrower());
        } else if (mPendingModels.getType() == 4) {/// Handedover by lender section
            /// visible on borrower side
            tvNotReceived.setText(R.string.handed_over_);
            llButton.setVisibility(View.VISIBLE);
            txtDone.setVisibility(View.GONE);
            tvOwnerName.setText(mPendingModels.getOwner());
        }

        try {
            tvDate.setText(Constants.convertDate(mPendingModels.getDelivery_date()) + " -  " + Constants.convertDate(mPendingModels.getReturn_date()));
        } catch (Exception e) {
            Log.e("Date Exce  = ", e + "");
        }

        mEarnedPrice = Double.parseDouble(mPendingModels.getTotal_amount()) -
                (Double.parseDouble(mPendingModels.getTransaction_fee()) + Double.parseDouble(mPendingModels.getAdditional_charges().getSecurity_charges()));
    }

    private void displayCancelled() {
        gestureSignature.setVisibility(View.INVISIBLE);
        tvSign.setText(R.string.this_tull_canceled);
        tvSign.setTextColor(ContextCompat.getColor(this, R.color.black_color));
        tvSign.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtGestureClear.setVisibility(View.GONE);
        txtDone.setText(R.string.ok_);
    }

    @Override
    protected void initListener() {
        txtDone.setOnClickListener(this);
        btReceived.setOnClickListener(this);
        btNotReceived.setOnClickListener(this);
        txtGestureClear.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return PendingTaskActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_done:
                if (ContextCompat.checkSelfPermission(mContext, READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(PendingTaskActivity.this, new String[]{READ_EXTERNAL_STORAGE}, CANCEL_CLICKED);
                } else {
                    onCancelClicked();
                }
                break;
            case R.id.tv_bt_received:
                if (ContextCompat.checkSelfPermission(mContext, READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(PendingTaskActivity.this, new String[]{READ_EXTERNAL_STORAGE}, RECEIVED_CLICKED);
                } else {
                    onReceivedClicked();
                }
                break;
            case R.id.tv_bt_not_received:
                if (ContextCompat.checkSelfPermission(mContext, READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(PendingTaskActivity.this, new String[]{READ_EXTERNAL_STORAGE}, NOT_RECEIVED_CLICKED);
                } else {
                    onNotReceivedClicked();
                }
                break;
            case R.id.txt_toolbar_clear:
                gestureSignature.cancelClearAnimation();
                gestureSignature.clear(true);
                gestureSignature.invalidate();
                gestureSignature.clearAnimation();
                isSigned = false;
                break;

        }
    }

    private void onNotReceivedClicked() {
        if (isSigned) {
            bitmap = Bitmap.createBitmap(gestureSignature.getDrawingCache());
            if (bitmap != null) {
                if (connectedToInternet()) {
                    showProgress();
                    if (mPendingModels.getType() == 4) {
                        /// lender mark this as handover.
                        // Now Borrower marking this as not received.
                        hitBorrowerNotReceivedAPI(getFile());
                    } else if (mPendingModels.getType() == 3) {
                        /// borrower mark this tul as returned.
                        // Now lender marking this tul as not received back.
                        hitLenderNotReceivedAPI(getFile());
                    }
                } else
                    showAlert(llBelow, errorInternet);
            } else {
                showAlert(llMain, getString(R.string.sign_above));
            }
        } else
            showAlert(llMain, getString(R.string.sign_above));
    }

    private void onReceivedClicked() {
        if (isSigned) {
            bitmap = Bitmap.createBitmap(gestureSignature.getDrawingCache());
            if (bitmap != null) {
                if (connectedToInternet()) {
                    showProgress();
                    if (mPendingModels.getType() == 4) {
                        /// lender mark this as handover.
                        // Now Borrower marking this as received.
                        hitBorrowerReceivedAPI(getFile());
                    } else if (mPendingModels.getType() == 3) {
                        /// borrower mark this tul as returned.
                        // Now lender marking this tul as received back.
                        hitLenderReceivedAPI(getFile());
                    }
                } else
                    showAlert(llBelow, errorInternet);
            } else {
                showAlert(llMain, getString(R.string.sign_above));
            }
        } else
            showAlert(llMain, getString(R.string.sign_above));
    }

    private void onCancelClicked() {
        if (connectedToInternet()) {
            showProgress();
            hitCancelAPI();
        } else
            showAlert(llBelow, errorInternet);
    }

    private void hitBorrowerReceivedAPI(File file) {
        Call<DemoModel> call = RetrofitClient.getInstance().markAsReceivedByBorrower(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(String.valueOf(mPendingModels.getBooking_id())),
                createPartFromString(String.valueOf(mPendingModels.getTool_id())),
                prepareFilePart(file));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    db.updateBorrowerStatus(mPendingModels.getBooking_id(),
                            response.body().response.getDate(), "1");

                    if (LandingActivity.landingActivity == null) {
                        Intent intent = new Intent(mContext, LandingActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                    } else {
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals("300")) {
                        finish();
                        Toast.makeText(PendingTaskActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void hitBorrowerNotReceivedAPI(File file) {
        Call<DemoModel> call = RetrofitClient.getInstance().markAsNotReceivedbyBorrower(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(String.valueOf(mPendingModels.getBooking_id())),
                createPartFromString(String.valueOf(mPendingModels.getTool_id())),
                prepareFilePart(file));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
//                    Toast.makeText(mContext, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                    alertPOPUP(1);
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals("300")) {
                        finish();
                        Toast.makeText(PendingTaskActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void hitLenderReceivedAPI(File file) {
        Call<DemoModel> call = RetrofitClient.getInstance().markAsReceivedByLender(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(String.valueOf(mPendingModels.getBooking_id())),
                createPartFromString(String.valueOf(mPendingModels.getTool_id())),
                prepareFilePart(file));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    /// delete from active booking tul lend section.
                    db.deleteLendedTul(mPendingModels.getBooking_id(), String.valueOf(mPendingModels.getTool_id()));
                    /// adding in tul history lend section.
                    db.addHistoryLendTul(mPendingModels);
                    for (AttachmentModel attachmentModel : mPendingModels.getAttachment()) {
                        db.addHistoryLentAttachment(attachmentModel);
                    }

                    Intent intent = new Intent(mContext, RateYourExperienceActivity.class);
                    if (LandingActivity.landingActivity == null)
                        intent.putExtra("move_path", "push");
                    intent.putExtra("date", response.body().response.getDate());
                    intent.putExtra("path", 1);
                    intent.putExtra("tool_id", String.valueOf(mPendingModels.getTool_id()));
                    intent.putExtra("booking_id", String.valueOf(mPendingModels.getBooking_id()));
                    intent.putExtra("owner", mPendingModels.getOwner());
                    intent.putExtra("ownerPic", mPendingModels.getOwner_pic());
                    intent.putExtra("totalAmount", String.format("%.2f", mEarnedPrice));
                    intent.putExtra("address", mPendingModels.getAddress());
                    startActivity(intent);
                    finish();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals("300")) {
                        finish();
                        Toast.makeText(PendingTaskActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void hitLenderNotReceivedAPI(File file) {
        Call<DemoModel> call = RetrofitClient.getInstance().markAsNotReceivedbyLender(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(String.valueOf(mPendingModels.getBooking_id())),
                createPartFromString(String.valueOf(mPendingModels.getTool_id())),
                prepareFilePart(file));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
//                    Toast.makeText(mContext, response.body().response.getMessage(), Toast.LENGTH_SHORT).show();
                    alertPOPUP(2);
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals("300")) {
                        finish();
                        Toast.makeText(PendingTaskActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void hitCancelAPI() {
        Call<DemoModel> call = RetrofitClient.getInstance().confirmCancel(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(String.valueOf(mPendingModels.getBooking_id())),
                createPartFromString(String.valueOf(mPendingModels.getTool_id())),
                prepareFilePart());
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    /*type 1 if lender cancel booking
                    type 2: if borrower cancel booking*/
                    if (mPendingModels.getType() == 1) {
                        /// delete from booking section
                        db.deleteBooking(mPendingModels.getBooking_id(),
                                String.valueOf(mPendingModels.getTool_id()));

                        if (mPendingModels != null) {
                            mPendingModels.setCancelled_at(response.body().response.getDate());
                            mPendingModels.setLender_status(3);
                            db.addHistoryRentedTul(mPendingModels);
                            for (AttachmentModel attachmentModel : mPendingModels.getAttachment()) {
                                db.addHistoryRentedAttachment(attachmentModel);
                            }
                        }
                    } else if (mPendingModels.getType() == 2) {/// delete from lent section
                        db.deleteLendedTul(mPendingModels.getBooking_id(),
                                String.valueOf(mPendingModels.getTool_id()));

                        /// adding in history lented table
                        if (mPendingModels != null) {
                            mPendingModels.setCancelled_at(response.body().response.getDate());
                            mPendingModels.setBorrower_status(3);
                            db.addHistoryLendTul(mPendingModels);
                            for (AttachmentModel attachmentModel : mPendingModels.getAttachment()) {
                                db.addHistoryLentAttachment(attachmentModel);
                            }
                        }
                    }
                    if (LandingActivity.landingActivity == null) {
                        finish();
                        Intent intent = new Intent(mContext, LandingActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                    } else {
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    }
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

    private void hitReportAPI() {
        showProgress();
        Call<DemoModel> call = RetrofitClient.getInstance().ReportTul(
                utils.getString("access_token", ""),
                mPendingModels.getTool_id(),
                Constants.LENDER_REPORT,
                mPendingModels.getUser_id(),
                mPendingModels.getBorrower_id(), 1);

        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    showAlert(llMain, response.body().response.getMessage());
                    finish();
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

    private void hitReadNotificationAPI(final String push_id) {

        Call<DemoModel> call = RetrofitClient.getInstance().readNotifications(utils.getString("access_token", ""),
                push_id);
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                if (response.body().response != null) {
                    db.updateNotificationReadStatus(Integer.parseInt(getIntent().getStringExtra("push_id")), "1");
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llBelow, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llBelow, t.getLocalizedMessage());
                db.updateNotificationReadStatus(Integer.parseInt(push_id), "0");
            }
        });
    }

    private void alertPOPUP(int path) {
        String message;
        if (path == 1)
            message = getString(R.string.report_contact_owner);
        else
            message = getString(R.string.report_contact_borrower);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (ContextCompat.checkSelfPermission(PendingTaskActivity.this, CALL_PHONE)
                                == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(PendingTaskActivity.this, new String[]{CALL_PHONE}, PHONE);
                        } else {
                            onCallCustomer();
                            if (LandingActivity.landingActivity == null) {
                                finish();
                                Intent intent = new Intent(mContext, LandingActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                            } else {
                                finish();
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                            }
                        }
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.report, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        hitReportAPI();
                        dialog.cancel();
                    }
                })

                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (LandingActivity.landingActivity == null) {
                            finish();
                            Intent intent = new Intent(mContext, LandingActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                        } else {
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


    void onCallCustomer() {
        String phoneNo = "tel:" + mPendingModels.getCountry_code() + mPendingModels.getPhone_number();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNo));
        startActivity(callIntent);
    }


    private RequestBody createPartFromString(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    private MultipartBody.Part prepareFilePart(File mFile) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), mFile);
        return MultipartBody.Part.createFormData("signature", mFile.getName(), requestFile);
    }

    private MultipartBody.Part prepareFilePart() {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), "");
        return MultipartBody.Part.createFormData("signature", "", requestFile);
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CANCEL_CLICKED:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onCancelClicked();
                break;
            case RECEIVED_CLICKED:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onReceivedClicked();
                break;
            case NOT_RECEIVED_CLICKED:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onNotReceivedClicked();
                break;
            case PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCallCustomer();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private File getFile() {
        File file = new File(mContext.getCacheDir(), "signature");
        try {
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
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
