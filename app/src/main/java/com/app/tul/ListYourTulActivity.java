package com.app.tul;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import api.RetrofitClient;
import butterknife.BindView;
import customviews.BoldTextView;
import customviews.MediumTextView;
import customviews.SemiboldTextView;
import model.AttachmentModel;
import model.CreateTulModel;
import model.ProfileModel;
import model.TransactionModel;
import model.TulImageModel;
import model.TulModel;
import model.ViewTulModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;
import video.VideoCompressListener;
import video.VideoCompressor;
import videoUtils.SGLog;
import videoUtils.Worker;

public class ListYourTulActivity extends BaseActivity {

    public static final String COMPRESSED_VIDEOS_DIR = "Compressed Videos/";
    private static final int VIEWSCHANGE = 1;
    File fileVideo;
    ProgressDialog progDailog;

    @BindView(R.id.ll_heading_container)
    LinearLayout llHeadingContainer;
    @BindView(R.id.rl_main_container)
    RelativeLayout rlMainContainer;
    @BindView(R.id.tv_detail)
    MediumTextView tvDetail;
    @BindView(R.id.tv_title)
    SemiboldTextView tvTitle;
    @BindView(R.id.txt_primary)
    TextView txtPrimary;
    @BindView(R.id.img_back)
    ImageView imgBckImg;

    @BindView(R.id.tv_first)
    BoldTextView tvFirst;
    @BindView(R.id.tv_second)
    BoldTextView tvSecond;
    @BindView(R.id.tv_third)
    BoldTextView tvThird;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;

    @BindView(R.id.ll_list_main_container)
    LinearLayout llListMainContainer;

    @BindView(R.id.ll_first_container)
    LinearLayout llFirstContainer;
    @BindView(R.id.ll_second_container)
    LinearLayout llSecondContainer;
    @BindView(R.id.ll_third_container)
    LinearLayout llThirdContainer;
    @BindView(R.id.ll_list_container)
    LinearLayout llListContainer;

    @BindView(R.id.img_dot1)
    RelativeLayout imgDot1;
    @BindView(R.id.img_dot2)
    RelativeLayout imgDot2;
    @BindView(R.id.img_dot3)
    RelativeLayout imgDot3;

    @BindView(R.id.view_line1)
    View viewLine1;
    @BindView(R.id.view_line2)
    View viewLine2;
    @BindView(R.id.bt_done)
    Button btDone;

    TulModel mTulModel;
    TulModel.ListTulModel mListTulModel;
    TulModel.PreferencesTul mPreferencesTul;
    TulModel.BankDetailsTul mBankDetailsTul;
    ViewTulModel.ResponseBean mViewTulModel;
    private boolean isEdit;
    private String mTransactionPercentage = "0.0";

    @Override
    protected int getContentView() {
        return R.layout.activity_list_your_tul;
    }

    @Override
    protected void initUI() {

        Typeface typefacetitle = Typeface.createFromAsset(getContext()
                .getAssets(), "fonts/bold.ttf");
        tvTitle.setTypeface(typefacetitle);

        Typeface typefaceDetail = Typeface.createFromAsset(getContext()
                .getAssets(), "fonts/semibold.ttf");
        tvDetail.setTypeface(typefaceDetail);
        tvFirst.setTypeface(typefaceDetail);
        tvSecond.setTypeface(typefaceDetail);
        tvThird.setTypeface(typefaceDetail);
        btDone.setTypeface(typefaceDetail);

        Typeface typefaceMedium = Typeface.createFromAsset(getContext()
                .getAssets(), "fonts/regular.ttf");
        tvDetail.setTypeface(typefaceMedium);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(mWidth / 10, 0, mWidth / 10, 0);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.toolbar);
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.bt_done);
        rlMainContainer.setLayoutParams(layoutParams);

        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));

        tvDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvDetail.setPadding(0, 0, 0, mHeight / 10);

        tvFirst.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvSecond.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        tvThird.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        txtPrimary.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtPrimary.setVisibility(View.GONE);

        btDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

        LinearLayout.LayoutParams layoutParamsview = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Constants.dpToPx(1));
        layoutParamsview.setMargins(0, 0, 0, mWidth / 15);
        view1.setLayoutParams(layoutParamsview);
        view2.setLayoutParams(layoutParamsview);
        view3.setLayoutParams(layoutParamsview);

        llFirstContainer.setPadding(0, mWidth / 40, 0, mWidth / 12);
        llSecondContainer.setPadding(0, mWidth / 40, 0, mWidth / 12);
        llThirdContainer.setPadding(0, mWidth / 40, 0, mWidth / 12);

        RelativeLayout.LayoutParams layoutParamsmainContainer = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsmainContainer.setMargins(0, mWidth / 35, 0, mWidth / 35);
        layoutParamsmainContainer.addRule(RelativeLayout.BELOW, R.id.ll_heading_container);
        llListMainContainer.setLayoutParams(layoutParamsmainContainer);

        LinearLayout.LayoutParams layoutParamscircle = new LinearLayout.LayoutParams(mWidth / 10, mWidth / 10);
        layoutParamscircle.gravity = Gravity.CENTER;
        imgDot1.setLayoutParams(layoutParamscircle);
        imgDot2.setLayoutParams(layoutParamscircle);
        imgDot3.setLayoutParams(layoutParamscircle);

        LinearLayout.LayoutParams layoutParamscircleline = new LinearLayout.LayoutParams(mWidth / 200, mWidth / 8);
        layoutParamscircleline.gravity = Gravity.CENTER;
        viewLine1.setLayoutParams(layoutParamscircleline);
        viewLine2.setLayoutParams(layoutParamscircleline);

        RelativeLayout.LayoutParams layoutParamsbutton = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsbutton.setMargins(mWidth / 25, 0, mWidth / 25, mWidth / 25);
        layoutParamsbutton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        btDone.setLayoutParams(layoutParamsbutton);
        btDone.setPadding(0, mWidth / 20, 0, mWidth / 20);

    }

    @Override
    protected void onCreateStuff() {
        if (getIntent().hasExtra("tul_data")) {
            tvDetail.setText(R.string.edit_basic_detailof_);
            isEdit = true;
            mViewTulModel = getIntent().getParcelableExtra("tul_data");
            /// setting tul data
            mListTulModel = new TulModel.ListTulModel();
            mListTulModel.title = mViewTulModel.getTitle();
            mListTulModel.category = mViewTulModel.getCategory_name();
            mListTulModel.categoryId = mViewTulModel.getCategory_id();
            mListTulModel.description = mViewTulModel.getDescription();
            mListTulModel.noOfTuls = String.valueOf(mViewTulModel.getQuantity());
            mListTulModel.price = mViewTulModel.getPrice();
            mListTulModel.discount = mViewTulModel.getDiscount_percentage();
            if (mViewTulModel.getDiscount_days() != 0)
                mListTulModel.booking_days = String.valueOf(mViewTulModel.getDiscount_days());
            else
                mListTulModel.booking_days = Constants.EMPTY;
            mListTulModel.securityFee = mViewTulModel.getAdditional_price().getSecurity_charges();
            mListTulModel.convienceFee = mViewTulModel.getAdditional_price().getFee();
            mListTulModel.address = mViewTulModel.getAddress();
            mListTulModel.latitude = Double.parseDouble(mViewTulModel.getLatitude());
            mListTulModel.longitude = Double.parseDouble(mViewTulModel.getLongitude());
            mListTulModel.rules = (ArrayList<String>) mViewTulModel.getRules();
            mListTulModel.active_bookings = mViewTulModel.getActive_bookings();
            mListTulModel.updateData = true;
            mListTulModel.isEdit = true;

            mListTulModel.imagesVideo = new ArrayList<>();
            for (AttachmentModel attachmentModel : mViewTulModel.getAttachment()) {
                TulImageModel tulImageModel = new TulImageModel();
                tulImageModel.setId(attachmentModel.getId());
                tulImageModel.setEdit(true);
                tulImageModel.setPath(attachmentModel.getAttachment());
                if (attachmentModel.getType().equals(Constants.IMAGE_TEXT)) {
                    tulImageModel.setType(Constants.IMAGE);
                    tulImageModel.setThumbnails(attachmentModel.getAttachment());
                } else {
                    tulImageModel.setType(Constants.VIDEO);
                    tulImageModel.setThumbnails(attachmentModel.getThumbnail());
                }
                mListTulModel.imagesVideo.add(tulImageModel);
            }
            TulModel.setListTul(mListTulModel);

            mPreferencesTul = new TulModel.PreferencesTul();
            mPreferencesTul.isEdit = true;
            mPreferencesTul.availbiltyMode = mViewTulModel.getPreferences().getAvailable();
            mPreferencesTul.startTime = mViewTulModel.getPreferences().getStart_time();
            mPreferencesTul.endTime = mViewTulModel.getPreferences().getEnd_time();
            mPreferencesTul.deliveryCharges = mViewTulModel.getPreferences().getDelivery_charges();
            mPreferencesTul.startPickUpTime = mViewTulModel.getPreferences().getDelivery_start_time();
            mPreferencesTul.endPickUpTime = mViewTulModel.getPreferences().getDelivery_end_time();
            if (mViewTulModel.getPreferences().getTull_delivery().equals("0"))
                mPreferencesTul.deleiveryAvailable = false;
            else
                mPreferencesTul.deleiveryAvailable = true;
            mPreferencesTul.updateData = true;
            TulModel.setPrefrencesTul(mPreferencesTul);

            llSecondContainer.setEnabled(true);
            llThirdContainer.setEnabled(true);

            mBankDetailsTul = new TulModel.BankDetailsTul();
            mBankDetailsTul.countryCode = mViewTulModel.getBank_detail().getCountry_code();
            mBankDetailsTul.currency = mViewTulModel.getBank_detail().getCurrency();
            mBankDetailsTul.accountNo = mViewTulModel.getBank_detail().getAccount_number();
            mBankDetailsTul.sortCode = mViewTulModel.getBank_detail().getSort_code();
            mBankDetailsTul.firstName = mViewTulModel.getBank_detail().getFirst_name();
            mBankDetailsTul.lastName = mViewTulModel.getBank_detail().getLast_name();
            mBankDetailsTul.address = mViewTulModel.getBank_detail().getAddress();
            mBankDetailsTul.city = mViewTulModel.getBank_detail().getCity();
            mBankDetailsTul.state = mViewTulModel.getBank_detail().getState();
            mBankDetailsTul.postalCode = mViewTulModel.getBank_detail().getPostal_code();
            mBankDetailsTul.dob = mViewTulModel.getBank_detail().getDob();
            mBankDetailsTul.swift = mViewTulModel.getBank_detail().getSwift();
            mBankDetailsTul.account_type = mViewTulModel.getBank_detail().getAccount_type();
            mBankDetailsTul.updateData = false;
            TulModel.setBankDetailsTul(mBankDetailsTul);

            enableViewFirst();
            enableViewSecond();
            enableViewThird();

            btDone.setVisibility(View.VISIBLE);
        } else {
            mListTulModel = TulModel.getListTul();
            mPreferencesTul = TulModel.getPrefrencesTul();
            mBankDetailsTul = TulModel.getBankDetailsTul();

            llSecondContainer.setEnabled(false);
            llThirdContainer.setEnabled(false);
        }
        mListTulModel.tranastionPercentage = utils.getString("transaction_percentage", "");
        mTransactionPercentage = utils.getString("transaction_percentage", "");
        hitTransactionAPI();
    }


    @Override
    protected void initListener() {
        btDone.setOnClickListener(this);
        llFirstContainer.setOnClickListener(this);
        llSecondContainer.setOnClickListener(this);
        llThirdContainer.setOnClickListener(this);
        imgBckImg.setOnClickListener(this);
    }


    @Override
    protected Context getContext() {
        return this;
    }

    public void disableViewFirst() {
        imgDot1.setBackgroundResource(R.drawable.white_circle);
        viewLine1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_color));
    }

    public void disableViewSecond() {
        imgDot2.setBackgroundResource(R.drawable.white_circle);
        viewLine2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_color));
    }

    public void disableViewThird() {
        imgDot3.setBackgroundResource(R.drawable.white_circle);
    }

    public void enableViewFirst() {
        imgDot1.setBackgroundResource(R.drawable.circle_checked);
        viewLine1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_color));
    }

    public void enableViewSecond() {
        imgDot2.setBackgroundResource(R.drawable.circle_checked);
        viewLine2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_color));
    }

    public void enableViewThird() {
        imgDot3.setBackgroundResource(R.drawable.circle_checked);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (mListTulModel.title != null || mPreferencesTul.availbiltyMode != null
                        || mBankDetailsTul.accountNo != null) {
                    alertDiscardDialog();
                } else
                    moveBack();
                break;

            case R.id.bt_done:
                if (connectedToInternet()) {
                    if (mListTulModel.title == null)
                        showAlert(llListContainer, getString(R.string.list_your_tull));
                    else if (mPreferencesTul.availbiltyMode == null)
                        showAlert(llListContainer, getString(R.string.pls_add_prefrences));
//                    else if (mBankDetailsTul.accountNo == null)
//                        showAlert(llListContainer, getString(R.string.pls_add_bank_detail));
                    else {
                        if (isEdit) {
                            String videoPath = "";
                            for (TulImageModel imageModel : mListTulModel.imagesVideo) {
                                if (!imageModel.isEdit()) {
                                    if (imageModel.getType().equals(Constants.VIDEO)) {
                                        videoPath = imageModel.getPath();
                                        break;
                                    }
                                }
                            }
                            if (TextUtils.isEmpty(videoPath)) {
                                hitEditAPI("");
                            } else {
                                compressVideo(videoPath);
                            }
                        } else {
                            String videoPath = "";
                            for (TulImageModel imageModel : mListTulModel.imagesVideo) {
                                if (imageModel.getType().equals(Constants.VIDEO)) {
                                    videoPath = imageModel.getPath();
                                    break;
                                }
                            }
                            if (TextUtils.isEmpty(videoPath)) {
                                hitAPI("");
                            } else {
                                compressVideo(videoPath);
                            }
                        }
                    }
                } else
                    showInternetAlert(llListContainer);
                break;
            case R.id.ll_first_container:
                Intent intentList = new Intent(mContext, AddTulActivity.class);
                if (isEdit)
                    intentList.putExtra("disable_category", "yes");
                intentList.putExtra("BACKTOLANDING", "yes");
                startActivityForResult(intentList, VIEWSCHANGE);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.ll_second_container:
                Intent intentPrefrences = new Intent(mContext, PreferencesActivity.class);
                intentPrefrences.putExtra("transaction_fee", mTransactionPercentage);
                intentPrefrences.putExtra("BACKTOLANDING", "yes");
                startActivityForResult(intentPrefrences, VIEWSCHANGE);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.ll_third_container:
                if (isEdit) {
                    Toast toast;
                    toast = Toast.makeText(mContext, R.string.already_link_with_tull, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Intent intentBank = new Intent(mContext, AddBankDetailActivity.class);
                    intentBank.putExtra("BACKTOLANDING", "yes");
                    startActivityForResult(intentBank, VIEWSCHANGE);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                break;
        }
    }



    private void hitTransactionAPI() {
        Call<TransactionModel> call = RetrofitClient.getInstance().getTransactionPercentage(utils.getString("access_token", ""));
        call.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                if (response.body().getResponse() != null) {
                    Log.e("Transaction %age = ", response.body().getResponse());
                    mListTulModel.tranastionPercentage = response.body().getResponse();
                    mTransactionPercentage = response.body().getResponse();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llListContainer, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable t) {
                showAlert(llListContainer, t.getLocalizedMessage());
            }
        });

    }

    private void hitEditAPI(String videoPath) {
        showProgress();

        String mBookingDays = "0";
        if (!TextUtils.isEmpty(mListTulModel.booking_days))
            mBookingDays = mListTulModel.booking_days;

        String mDiscount = "";
        if (!TextUtils.isEmpty(mListTulModel.discount))
            mDiscount = String.format("%.2f", Double.parseDouble(mListTulModel.discount));

        JsonObject additionalPrice = new JsonObject();
        additionalPrice.addProperty("security_charges", mListTulModel.securityFee);
        if (!TextUtils.isEmpty(mListTulModel.convienceFee))
            additionalPrice.addProperty("fee", mListTulModel.convienceFee);
        else
            additionalPrice.addProperty("fee", "0.00");

        JsonObject preferences = new JsonObject();
        preferences.addProperty("available", mPreferencesTul.availbiltyMode);
        preferences.addProperty("start_time", mPreferencesTul.startTime);
        preferences.addProperty("end_time", mPreferencesTul.endTime);

        if (mPreferencesTul.deleiveryAvailable)
            preferences.addProperty("tull_delivery", "1");
        else
            preferences.addProperty("tull_delivery", "0");

        preferences.addProperty("delivery_charges", mPreferencesTul.deliveryCharges);
        preferences.addProperty("delivery_start_time", mPreferencesTul.startPickUpTime);
        preferences.addProperty("delivery_end_time", mPreferencesTul.endPickUpTime);

        RequestBody reqFileVideo, reqFileThumb;
        MultipartBody.Part bodyVideo, bodyThumb;
        File mFileVideo, mFileThumb;

        reqFileVideo = RequestBody.create(MediaType.parse("video/*"), "");
        bodyVideo = MultipartBody.Part.createFormData("video", "", reqFileVideo);

        reqFileThumb = RequestBody.create(MediaType.parse("image/*"), "");
        bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", "", reqFileThumb);

        String rules = "";
        if (mListTulModel.rules != null && mListTulModel.rules.size() > 0) {
            StringBuilder mBuilder = new StringBuilder();
            for (String ruless : mListTulModel.rules) {
                mBuilder.append(ruless + Constants.RULES_REGEX);
            }
            String mRules = mBuilder.toString();
            rules = mRules.substring(0, mRules.length() - 9);
            Log.e("Rules = ", rules);
        }

        StringBuilder mBuilderAttachmentIds = new StringBuilder();
        String mAttachmentIds = "";
        if (mListTulModel.attachments_ids != null && mListTulModel.attachments_ids.size() > 0) {
            for (String ids : mListTulModel.attachments_ids) {
                mBuilderAttachmentIds.append(ids + ",");
            }
            String attachmentIds = mBuilderAttachmentIds.toString();
            mAttachmentIds = attachmentIds.substring(0, attachmentIds.length() - 1);
        }
        Log.e("mAttachmentIds = ", mAttachmentIds);

        ArrayList<MultipartBody.Part> imagesArray = new ArrayList<>();
        Collections.reverse(mListTulModel.imagesVideo);
        for (TulImageModel imageModel : mListTulModel.imagesVideo) {
            if (!imageModel.isEdit()) {
                if (imageModel.getType().equals(Constants.IMAGE)) {
                    File mFileImage = new File(imageModel.getPath());
                    imagesArray.add(prepareFilePart(mFileImage));
                } else {
                    mFileVideo = new File(videoPath);
                    mFileThumb = new File(imageModel.getThumbnails());

                    reqFileVideo = RequestBody.create(MediaType.parse("video/*"), mFileVideo);
                    reqFileThumb = RequestBody.create(MediaType.parse("image/*"), mFileThumb);

                    bodyVideo = MultipartBody.Part.createFormData("video", mFileVideo.getName(), reqFileVideo);
                    bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", mFileThumb.getName(), reqFileThumb);
                }
            }
        }

        Call<ViewTulModel> call = RetrofitClient.getInstance().editTul(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(mListTulModel.title),
                createPartFromString(String.valueOf(mListTulModel.categoryId)),
                createPartFromString(mListTulModel.description),
                createPartFromString(mListTulModel.noOfTuls),
                createPartFromString(mListTulModel.price),
                createPartFromString(mBookingDays),
                createPartFromString(mDiscount),
                createPartFromString(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,""))),
                createPartFromString(String.valueOf(additionalPrice)),
                createPartFromString(mListTulModel.address),
                createPartFromString(String.valueOf(mListTulModel.latitude)),
                createPartFromString(String.valueOf(mListTulModel.longitude)),
                createPartFromString(String.valueOf(rules)),
                createPartFromString(String.valueOf(preferences)),
                createPartFromString(String.valueOf(mViewTulModel.getId())),
                createPartFromString(mAttachmentIds),
                createPartFromString(mTransactionPercentage),
                createPartFromString(utils.getString(Constants.PRIMARY_CURRENCY, "")),
                imagesArray, bodyVideo, bodyThumb);

        call.enqueue(new Callback<ViewTulModel>() {
            @Override
            public void onResponse(Call<ViewTulModel> call, Response<ViewTulModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    db.deleteTulAttachemnetById(String.valueOf(mViewTulModel.getId()));
                    clearData();
                    utils.setString("visible_primary", "");
                    addToLocalByEdit(response.body().getResponse());
                    utils.setInt("lender", 1);
                    Intent intent = new Intent();
                    intent.putExtra("tul_data", response.body().getResponse());
                    setResult(RESULT_OK, intent);
                    if (response.body().getResponse().getTransaction_percentage().equals(mTransactionPercentage)) {
                        finish();
                    } else {
                        mTransactionPercentage = response.body().getResponse().getTransaction_percentage();
                        utils.setString("transaction_percentage",mTransactionPercentage);
                        alertEditTransFeeDialog();
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {
                        userBlockDialogUser(mContext);
                    } else {
                        showAlert(llListContainer, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewTulModel> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void hitAPI(String videoPath) {
        showProgress();

        String mDiscount = "";
        if (!TextUtils.isEmpty(mListTulModel.discount))
            mDiscount = String.format("%.2f", Double.parseDouble(mListTulModel.discount));

        String mBookingDays = "0";
        if (!TextUtils.isEmpty(mListTulModel.booking_days))
            mBookingDays = mListTulModel.booking_days;

        JsonObject additionalPrice = new JsonObject();
        additionalPrice.addProperty("security_charges", mListTulModel.securityFee);

        if (!TextUtils.isEmpty(mListTulModel.convienceFee))
            additionalPrice.addProperty("fee", mListTulModel.convienceFee);
        else
            additionalPrice.addProperty("fee", "0.00");

        JsonObject preferences = new JsonObject();
        preferences.addProperty("available", mPreferencesTul.availbiltyMode);
        preferences.addProperty("start_time", mPreferencesTul.startTime);
        preferences.addProperty("end_time", mPreferencesTul.endTime);
        if (mPreferencesTul.deleiveryAvailable)
            preferences.addProperty("tull_delivery", "1");
        else
            preferences.addProperty("tull_delivery", "0");
        preferences.addProperty("delivery_charges", mPreferencesTul.deliveryCharges);
        preferences.addProperty("delivery_start_time", mPreferencesTul.startPickUpTime);
        preferences.addProperty("delivery_end_time", mPreferencesTul.endPickUpTime);

        RequestBody reqFileVideo, reqFileThumb;
        MultipartBody.Part bodyVideo, bodyThumb;
        File mFileVideo, mFileThumb;

        reqFileVideo = RequestBody.create(MediaType.parse("video/*"), "");
        bodyVideo = MultipartBody.Part.createFormData("video", "", reqFileVideo);

        reqFileThumb = RequestBody.create(MediaType.parse("image/*"), "");
        bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", "", reqFileThumb);

        String rules = "";
        if (mListTulModel.rules != null && mListTulModel.rules.size() > 0) {
            StringBuilder mBuilder = new StringBuilder();
            for (String ruless : mListTulModel.rules) {
                mBuilder.append(ruless + Constants.RULES_REGEX);
            }
            String mRules = mBuilder.toString();
            rules = mRules.substring(0, mRules.length() - 9);
            Log.e("Rules = ", rules);
        }

        ArrayList<MultipartBody.Part> imagesArray = new ArrayList<>();
        Collections.reverse(mListTulModel.imagesVideo);
        for (TulImageModel imageModel : mListTulModel.imagesVideo) {
            if (imageModel.getType().equals(Constants.IMAGE)) {
                File mFileImage = new File(imageModel.getPath());
                imagesArray.add(prepareFilePart(mFileImage));
            } else {
                mFileVideo = new File(videoPath);
                mFileThumb = new File(imageModel.getThumbnails());

                reqFileVideo = RequestBody.create(MediaType.parse("video/*"), mFileVideo);
                reqFileThumb = RequestBody.create(MediaType.parse("image/*"), mFileThumb);

                bodyVideo = MultipartBody.Part.createFormData("video", mFileVideo.getName(), reqFileVideo);
                bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", mFileThumb.getName(), reqFileThumb);
            }
        }

        Call<CreateTulModel> call = RetrofitClient.getInstance().createTul(createPartFromString(utils.getString("access_token", "")),
                createPartFromString(mListTulModel.title),
                createPartFromString(String.valueOf(mListTulModel.categoryId)),
                createPartFromString(mListTulModel.description),
                createPartFromString(mListTulModel.noOfTuls),
                createPartFromString(mListTulModel.price),
                createPartFromString(mBookingDays),
                createPartFromString(mDiscount),
                createPartFromString(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,""))),
                createPartFromString(String.valueOf(additionalPrice)),
                createPartFromString(mListTulModel.address),
                createPartFromString(String.valueOf(mListTulModel.latitude)),
                createPartFromString(String.valueOf(mListTulModel.longitude)),
                createPartFromString(String.valueOf(rules)),
                createPartFromString(String.valueOf(preferences)),
                createPartFromString(utils.getString("accountId", "")),
                createPartFromString(mTransactionPercentage),
                createPartFromString(utils.getString(Constants.PRIMARY_CURRENCY, "")),
                imagesArray, bodyVideo, bodyThumb);

        call.enqueue(new Callback<CreateTulModel>() {
            @Override
            public void onResponse(Call<CreateTulModel> call, Response<CreateTulModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    clearData();
                    utils.setString("visible_primary", "");
                    addToLocal(response.body().getResponse());
                    utils.setInt("lender", 1);
                    utils.setString(Constants.IS_CURRENCY_SELECTED, "1");
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {
                        userBlockDialogUser(mContext);
                    } else if (response.body().error.getCode().equals(Constants.TRANSCHANGED)) {
                        hitTransactionAPI();
                        alertTransFeeDialog();
                    } else {
                        showAlert(llListContainer, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateTulModel> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void clearData() {
        mTulModel = new TulModel();
        mListTulModel.title = null;
        mListTulModel.isEdit = false;
        mPreferencesTul.availbiltyMode = null;
        mPreferencesTul.isEdit = false;
        mBankDetailsTul.accountNo = null;
        TulModel.setListTul(mListTulModel);
        TulModel.setPrefrencesTul(mPreferencesTul);
        TulModel.setBankDetailsTul(mBankDetailsTul);
    }

    private void addToLocal(CreateTulModel.ResponseBean response) {
        ProfileModel.ResponseBean.ToolsBean toolsModel = new ProfileModel.ResponseBean.ToolsBean();
        toolsModel.setTitle(response.getTitle());
        toolsModel.setId(response.getId());
        toolsModel.setUser_id(response.getUser_id());
        toolsModel.setPrice(response.getPrice());
        toolsModel.setTransaction_percentage(response.getTransaction_percentage());
        db.addListedTul(toolsModel);

        for (CreateTulModel.ResponseBean.AttachmentBean attachmentBean : response.getAttachment()) {
            AttachmentModel profileAttachmentBean = new AttachmentModel();
            profileAttachmentBean.setId(attachmentBean.getId());
            profileAttachmentBean.setTool_id(attachmentBean.getTool_id());
            profileAttachmentBean.setAttachment(attachmentBean.getAttachment());
            profileAttachmentBean.setThumbnail(attachmentBean.getThumbnail());
            profileAttachmentBean.setType(attachmentBean.getType());
            db.addAttachment(profileAttachmentBean);
        }
    }

    private void addToLocalByEdit(ViewTulModel.ResponseBean response) {
        ProfileModel.ResponseBean.ToolsBean toolsModel = new ProfileModel.ResponseBean.ToolsBean();
        toolsModel.setTitle(response.getTitle());
        toolsModel.setId(response.getId());
        toolsModel.setUser_id(response.getUser_id());
        toolsModel.setPrice(response.getPrice());
        toolsModel.setTransaction_percentage(response.getTransaction_percentage());
        db.addListedTul(toolsModel);

        for (AttachmentModel attachmentBean : response.getAttachment()) {
            AttachmentModel profileAttachmentBean = new AttachmentModel();
            profileAttachmentBean.setId(attachmentBean.getId());
            profileAttachmentBean.setTool_id(attachmentBean.getTool_id());
            profileAttachmentBean.setAttachment(attachmentBean.getAttachment());
            profileAttachmentBean.setThumbnail(attachmentBean.getThumbnail());
            profileAttachmentBean.setType(attachmentBean.getType());
            db.addAttachment(profileAttachmentBean);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == VIEWSCHANGE) {
                if (mListTulModel.title != null) {
                    mListTulModel.updateData = true;
                    enableViewFirst();
                    llSecondContainer.setEnabled(true);
                } else {
                    mListTulModel.updateData = false;
                    disableViewFirst();
                    llSecondContainer.setEnabled(false);
                }
                if (mPreferencesTul.availbiltyMode != null) {
                    mPreferencesTul.updateData = true;
                    enableViewSecond();
                    llSecondContainer.setEnabled(true);
                    llThirdContainer.setEnabled(true);
                } else {
                    mPreferencesTul.updateData = false;
                    disableViewSecond();
                    llThirdContainer.setEnabled(false);
                }
                if (mBankDetailsTul.accountNo != null) {
                    mBankDetailsTul.updateData = true;
                    enableViewThird();
                    llThirdContainer.setEnabled(true);
                } else {
                    mBankDetailsTul.updateData = false;
                    disableViewThird();
                    llThirdContainer.setEnabled(true);
                }

                if (mListTulModel.title != null && mPreferencesTul.availbiltyMode != null)
                    btDone.setVisibility(View.VISIBLE);
                else
                    btDone.setVisibility(View.INVISIBLE);

                if (utils.getString("visible_primary", "").equals("yes")) {
                    txtPrimary.setVisibility(View.VISIBLE);
                    llThirdContainer.setEnabled(false);
                } else {
                    txtPrimary.setVisibility(View.GONE);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private MultipartBody.Part prepareFilePart(File mFile) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), mFile);
        return MultipartBody.Part.createFormData("images[]", mFile.getName(), requestFile);
    }

    private RequestBody createPartFromString(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    private void alertDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.all_data_discard)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListTulModel.title = null;
                        mListTulModel.isEdit = false;
                        mListTulModel.active_bookings = 0;
                        mPreferencesTul.availbiltyMode = null;
                        mPreferencesTul.isEdit = false;
                        mBankDetailsTul.accountNo = null;
                        TulModel.setListTul(mListTulModel);
                        utils.setString("visible_primary", "");
                        TulModel.setPrefrencesTul(mPreferencesTul);
                        TulModel.setBankDetailsTul(mBankDetailsTul);
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

    private void alertTransFeeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Admin_Percentage)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearData();
                        disableViewFirst();
                        disableViewSecond();
                        btDone.setVisibility(View.GONE);
                        llSecondContainer.setEnabled(false);
                        llThirdContainer.setEnabled(false);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alertEditTransFeeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Admin_Percentage)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        if (mListTulModel.title != null || mPreferencesTul.availbiltyMode != null
                || mBankDetailsTul.accountNo != null) {
            alertDiscardDialog();
        } else
            moveBack();
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


    private void compressVideo(String path) {

        progDailog = ProgressDialog.show(this, "Please wait ...", "Compressing Video ...", true);
        progDailog.setCancelable(false);

        VideoCompressor.compress(this, path, new VideoCompressListener() {
            @Override
            public void onSuccess(final String outputFile, String filename, long duration) {
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
                        SGLog.e("video compress success:" + outputFile);
                        progDailog.dismiss();
                        if (isEdit)
                            hitEditAPI(outputFile);
                        else
                            hitAPI(outputFile);
                    }
                });
            }

            @Override
            public void onFail(final String reason) {
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "video compress failed:" + reason, Toast.LENGTH_SHORT).show();
                        SGLog.e("video compress failed:" + reason);
                        progDailog.dismiss();
                    }
                });
            }

            @Override
            public void onProgress(final int progress) {
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
                        SGLog.e("video compress progress:" + progress);
                    }
                });
            }
        });
    }


}
