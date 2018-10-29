package com.app.tul.Sales;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.BaseActivity;
import com.app.tul.CapturePhotoVideoActivity;
import com.app.tul.LandingActivity;
import com.app.tul.OptionSelection;
import com.app.tul.R;
import com.app.tul.TullFullViewActivity;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import adapters.SalesInfoAdapter;
import adapters.TullImagesAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import customviews.MaterialEditText;
import model.AttachmentModel;
import model.CreateTulModel;
import model.ProfileModel;
import model.SalesPriceFee;
import model.SalesTulDetailModel;
import model.TulImageModel;
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

public class SalesListYourTulActivity extends BaseActivity {


    public static final String COMPRESSED_VIDEOS_DIR = "Compressed Videos/";
    private static final int MOVENEXT = 7;
    private static final int VIEWSCHANGE = 1;
    private static final int CONDITION = 10;
    private static final int DELIVERY = 11;
    private static final int CURRENCY = 12;

    ProgressDialog progDailog;
    File fileVideo;

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.ll_inner)
    LinearLayout llInner;
    @BindView(R.id.txt_hint_msg)
    TextView txtHintMsg;
    @BindView(R.id.ed_title)
    MaterialEditText edTitle;
    @BindView(R.id.ed_no_tuls)
    MaterialEditText edNoTuls;
    @BindView(R.id.ed_desc)
    MaterialEditText edDesc;
    @BindView(R.id.ed_price)
    MaterialEditText edPrice;
    @BindView(R.id.ed_condition)
    MaterialEditText edCondition;
    @BindView(R.id.ed_delivery)
    MaterialEditText edDelivery;
    @BindView(R.id.ll_delivery_charges)
    LinearLayout llDeliveryCharges;
    @BindView(R.id.ed_delivery_charges_local)
    MaterialEditText edDeliveryChargesLocal;
    @BindView(R.id.ed_delivery_charges_inter)
    MaterialEditText edDeliveryChargesInter;
    @BindView(R.id.txt_add_images)
    TextView txtAddImages;
    @BindView(R.id.ll_add_image)
    LinearLayout llAddImage;
    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    @BindView(R.id.txt_save)
    TextView txtSave;

    @BindView(R.id.txt_skip)
    TextView txtSkip;

    @BindView(R.id.img_price_info)
    ImageView imgPriceInfo;

    @BindView(R.id.ll_company)
    LinearLayout llCompany;
    @BindView(R.id.ed_company_name)
    EditText edCompanyName;
    @BindView(R.id.ed_vat)
    EditText edVat;
    @BindView(R.id.ed_currency)
    EditText edCurrency;
    @BindView(R.id.view_currency)
    View viewCurrency;

    int mImagesSize, mVideoSize;
    SalesListYourTulActivity mActivity;
    String mDelivery = "0";
    private int PIC = 5;
    private int FULLVIEW = 6;
    private TullImagesAdapter mImagesAdapter;

    private ArrayList<TulImageModel> mTulImagesArray = new ArrayList<>();
    private ArrayList<String> mAttachmentIds = new ArrayList<>();
    private boolean isCategoryEnabled = true;
    private boolean mPopupEnable;
    private boolean isEdit;
    private ArrayList<SalesPriceFee.ResponseBean> mPopupPriceArray;
    private SalesTulDetailModel.ResponseBean mViewTulModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_sales_list_your_tul;
    }

    @Override
    protected void initUI() {
        mActivity = this;
        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtToolbarTitle.setText(getResources().getString(R.string.list_tul));

        llInner.setPadding(mWidth / 24, 0, mWidth / 24, 0);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        txtHintMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        txtHintMsg.setPadding(mWidth / 24, mWidth / 24, mWidth / 24, mHeight / 24);

        edTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edTitle.setTypeface(typeface);

        edDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edDesc.setTypeface(typeface);

        edNoTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edNoTuls.setTypeface(typeface);

        edPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edPrice.setTypeface(typeface);

        edCondition.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edCondition.setTypeface(typeface);

        edCurrency.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edCurrency.setTypeface(typeface);

        edDelivery.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edDelivery.setTypeface(typeface);

        edDeliveryChargesLocal.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edDeliveryChargesLocal.setTypeface(typeface);

        edDeliveryChargesInter.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edDeliveryChargesInter.setTypeface(typeface);

        edVat.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edVat.setTypeface(typeface);

        edCompanyName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        edCompanyName.setTypeface(typeface);

        txtAddImages.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        txtAddImages.setPadding(0, mWidth / 24, 0, mWidth / 24);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(linearLayoutManager);
        mImagesAdapter = new TullImagesAdapter(mContext, mTulImagesArray, mActivity);
        rvImages.setAdapter(mImagesAdapter);
        rvImages.setItemAnimator(new DefaultItemAnimator());

        LinearLayout.LayoutParams addImagePArms = new LinearLayout.LayoutParams(mWidth / 4, mWidth / 4);
        addImagePArms.setMargins(0, 0, mWidth / 64, 0);
        llAddImage.setLayoutParams(addImagePArms);

        LinearLayout.LayoutParams saveParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        saveParms.setMargins(0, mHeight / 32, 0, 0);
        txtSave.setLayoutParams(saveParms);
        txtSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtSave.setPadding(0, mWidth / 28, 0, mWidth / 28);
    }

    @Override
    protected void onCreateStuff() {

        final Drawable img;

        if (utils.getString(Constants.IS_CURRENCY_SELECTED, "0").equals("0")) {
            edCurrency.setVisibility(View.VISIBLE);
            viewCurrency.setVisibility(View.VISIBLE);
            edCurrency.setText("GBP");
        } else {
            edCurrency.setVisibility(View.GONE);
            viewCurrency.setVisibility(View.GONE);
        }

        if (utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, "")).equals(Constants.POUND)) {
            img = ContextCompat.getDrawable(mContext, R.drawable.pound);

            edDeliveryChargesInter.setFloatingLabelText(getString(R.string.delivery_charges_inter_miles));
            edDeliveryChargesInter.setHint(getString(R.string.delivery_charges_inter_miles));

            edDeliveryChargesLocal.setFloatingLabelText(getString(R.string.delivery_charges_locally_miles));
            edDeliveryChargesLocal.setHint(getString(R.string.delivery_charges_locally_miles));
        } else {
            img = ContextCompat.getDrawable(mContext, R.drawable.euro);

            edDeliveryChargesInter.setFloatingLabelText(getString(R.string.delivery_charges_inter));
            edDeliveryChargesInter.setHint(getString(R.string.delivery_charges_inter));

            edDeliveryChargesLocal.setFloatingLabelText(getString(R.string.delivery_charges_locally));
            edDeliveryChargesLocal.setHint(getString(R.string.delivery_charges_locally));
        }

        img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));

        edPrice.setCompoundDrawables(img, null, null, null);

        edDeliveryChargesInter.setCompoundDrawables(img, null, null, null);
        edDeliveryChargesLocal.setCompoundDrawables(img, null, null, null);


        llDeliveryCharges.setVisibility(View.GONE);
        mPopupPriceArray = new ArrayList<>();

        if (getIntent().hasExtra("Create")) {
            txtSkip.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.GONE);
        } else {
            txtSkip.setVisibility(View.GONE);
            imgBack.setVisibility(View.VISIBLE);

        }
        if (utils.getInt(Constants.ISCOMPANY, 0) == Constants.IS_COMPANY) {
            llCompany.setVisibility(View.VISIBLE);
            edCompanyName.setText(utils.getString("first_name", ""));
            edVat.setText(utils.getString(Constants.VAT, ""));
        } else {
            llCompany.setVisibility(View.GONE);
        }

        hitPopUpApi();

        if (getIntent().hasExtra("tul_data")) {
            isEdit = true;
            mViewTulModel = getIntent().getParcelableExtra("tul_data");
            /// setting tul data
            edTitle.setText(mViewTulModel.getTitle());
            edDesc.setText(mViewTulModel.getDescription());
            edNoTuls.setText(String.valueOf(mViewTulModel.getQuantity()));
            edPrice.setText(mViewTulModel.getPrice());
            edCondition.setText(mViewTulModel.getCondition());
            mDelivery = String.valueOf(mViewTulModel.getDelivery_type());

            txtSave.setText(getString(R.string.update));

            if (mViewTulModel.getDelivery_type() == 0) {
                edDelivery.setText(mContext.getString(R.string.NO));
                llDeliveryCharges.setVisibility(View.GONE);
            } else {
                edDelivery.setText(mContext.getString(R.string.YES));
                llDeliveryCharges.setVisibility(View.VISIBLE);

                edDeliveryChargesLocal.setText(mViewTulModel.getDelivery_charges_local());
                edDeliveryChargesInter.setText(mViewTulModel.getDelivery_charges_int());
            }


            mTulImagesArray = new ArrayList<>();
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
                mTulImagesArray.add(tulImageModel);
            }
            mImagesAdapter = new TullImagesAdapter(mContext, mTulImagesArray, mActivity);
            rvImages.setAdapter(mImagesAdapter);

        }

    }


    @Override
    protected void initListener() {
        llAddImage.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        edCondition.setOnClickListener(this);
        edDelivery.setOnClickListener(this);
        imgPriceInfo.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtSkip.setOnClickListener(this);
        edCurrency.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_skip:
                if (getIntent().hasExtra("Create")) {
                    Intent inStarted = new Intent(mContext, LandingActivity.class);
                    inStarted.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                    startActivity(inStarted);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                break;

            case R.id.img_back:
//              alertDiscardDialog();
                moveBack();
                break;

            case R.id.img_price_info:
                PopupWindow popupwindow_obj = showEarningPopup();
                popupwindow_obj.showAsDropDown(imgPriceInfo, -100, -50);
                break;

            case R.id.ed_condition:

                ArrayList<String> condition = new ArrayList<>();
                condition.add(getString(R.string.NEW));
                condition.add(getString(R.string.Used));
                selectOptions(condition, CONDITION, edCondition.getText().toString());

                break;

            case R.id.ed_delivery:

                ArrayList<String> delivery = new ArrayList<>();
                delivery.add(getString(R.string.YES));
                delivery.add(getString(R.string.NO));
                selectOptions(delivery, DELIVERY, edDelivery.getText().toString());

                break;
            case R.id.ed_currency:
                ArrayList<String> currency = new ArrayList<>();
                currency.add("GBP");
                currency.add("EUR");
                selectOptions(currency, CURRENCY, edCurrency.getText().toString());

                break;
            case R.id.ll_add_image:
                if (mTulImagesArray.size() < 7) {
                    Intent inProfile = new Intent(mContext, CapturePhotoVideoActivity.class);
                    inProfile.putExtra("imageSize", mImagesSize);
                    inProfile.putExtra("videoSize", mVideoSize);
                    startActivityForResult(inProfile, PIC);
                } else {
                    showAlert(llAddImage, getString(R.string.error_image_video_count));
                }
                break;
            case R.id.txt_save:
                if (TextUtils.isEmpty(edTitle.getText().toString().trim()))
                    showAlert(llInner, getString(R.string.error_title));
                else if (TextUtils.isEmpty(edDesc.getText().toString().trim()))
                    showAlert(llInner, getString(R.string.error_descrption));
                else if (TextUtils.isEmpty(edNoTuls.getText().toString().trim()))
                    showAlert(llInner, getString(R.string.error_no_tuls));
                else if (Integer.parseInt(edNoTuls.getText().toString().trim()) == 0)
                    showAlert(llInner, getString(R.string.error_no_valid));
                else if (TextUtils.isEmpty(edPrice.getText().toString().trim()) || edPrice.getText().toString().equals("."))
                    showAlert(llInner, getString(R.string.error_price));
                else if (Double.parseDouble(edPrice.getText().toString().trim()) < Constants.MIN_PRICE)
                    showAlert(llInner, getString(R.string.error_valid_price));
                else if (Double.parseDouble(edPrice.getText().toString().trim()) > Constants.MAX_SALES_PRICE)
                    showAlert(llInner, getString(R.string.error_max_price));
                else if (mTulImagesArray.size() == 0)
                    showAlert(llInner, getString(R.string.error_image_video));
                else if (TextUtils.isEmpty(edCondition.getText().toString().trim()))
                    showAlert(llInner, getString(R.string.error_condition));
                else if (TextUtils.isEmpty(edDelivery.getText().toString().trim()))
                    showAlert(llInner, getString(R.string.error_delivery_Option));
                else if (mDelivery.equals("1") &&
                        TextUtils.isEmpty(edDeliveryChargesLocal.getText().toString().trim()))
                    showAlert(llInner, getString(R.string.error_delivery_Option_local));
                else if (mDelivery.equals("1") &&
                        TextUtils.isEmpty(edDeliveryChargesInter.getText().toString().trim())) {
                    showAlert(llInner, getString(R.string.error_delivery_Option_inter));
                } else {
                    if (isEdit) {
                        String videoPath = "";
                        for (TulImageModel imageModel : mTulImagesArray) {
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
                        for (TulImageModel imageModel : mTulImagesArray) {
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
                break;
        }
    }

    private void hitAPI(String videoPath) {
        showProgress();
//        String mDiscount = "";
//        if (!TextUtils.isEmpty(mListTulModel.discount))
//            mDiscount = String.format("%.2f", Double.parseDouble(mListTulModel.discount));
//
//        String mBookingDays = "0";
//        if (!TextUtils.isEmpty(mListTulModel.booking_days))
//            mBookingDays = mListTulModel.booking_days;

//        JsonObject additionalPrice = new JsonObject();
//        additionalPrice.addProperty("security_charges", mListTulModel.securityFee);
//
//        if (!TextUtils.isEmpty(mListTulModel.convienceFee))
//            additionalPrice.addProperty("fee", mListTulModel.convienceFee);
//        else
//            additionalPrice.addProperty("fee", "0.00");

//        JsonObject preferences = new JsonObject();
//        preferences.addProperty("available", mPreferencesTul.availbiltyMode);
//        preferences.addProperty("start_time", mPreferencesTul.startTime);
//        preferences.addProperty("end_time", mPreferencesTul.endTime);
//        if (mPreferencesTul.deleiveryAvailable)
//            preferences.addProperty("tull_delivery", "1");
//        else
//            preferences.addProperty("tull_delivery", "0");
//        preferences.addProperty("delivery_charges", mPreferencesTul.deliveryCharges);
//        preferences.addProperty("delivery_start_time", mPreferencesTul.startPickUpTime);
//        preferences.addProperty("delivery_end_time", mPreferencesTul.endPickUpTime);

        RequestBody reqFileVideo, reqFileThumb;
        MultipartBody.Part bodyVideo, bodyThumb;
        File mFileVideo, mFileThumb;

        reqFileVideo = RequestBody.create(MediaType.parse("video/*"), "");
        bodyVideo = MultipartBody.Part.createFormData("video", "", reqFileVideo);

        reqFileThumb = RequestBody.create(MediaType.parse("image/*"), "");
        bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", "", reqFileThumb);

//        String rules = "";
//        if (mListTulModel.rules != null && mListTulModel.rules.size() > 0) {
//            StringBuilder mBuilder = new StringBuilder();
//            for (String ruless : mListTulModel.rules) {
//                mBuilder.append(ruless + Constants.RULES_REGEX);
//            }
//            String mRules = mBuilder.toString();
//            rules = mRules.substring(0, mRules.length() - 9);
//            Log.e("Rules = ", rules);
//        }

        ArrayList<MultipartBody.Part> imagesArray = new ArrayList<>();
        Collections.reverse(mTulImagesArray);
        for (TulImageModel imageModel : mTulImagesArray) {
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

        Call<CreateTulModel> call = RetrofitClient.getInstance().createSalesTul(
                createPartFromString(utils.getString("access_token", "")),
                createPartFromString(edTitle.getText().toString().trim()),
                createPartFromString(edDesc.getText().toString().trim()),
                createPartFromString(edNoTuls.getText().toString().trim()),
                createPartFromString(edPrice.getText().toString().trim()),
                createPartFromString(edCondition.getText().toString().trim()),
                createPartFromString(mDelivery),
                createPartFromString(edDeliveryChargesLocal.getText().toString().trim()),
                createPartFromString(edDeliveryChargesInter.getText().toString().trim()),
                createPartFromString(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, ""))),
                createPartFromString(utils.getString(Constants.PRIMARY_CURRENCY, "")),
                imagesArray, bodyVideo, bodyThumb);

        call.enqueue(new Callback<CreateTulModel>() {
            @Override
            public void onResponse(Call<CreateTulModel> call, Response<CreateTulModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    addToLocal(response.body().getResponse());

                    utils.setString(Constants.IS_CURRENCY_SELECTED, "1");

                    if (getIntent().hasExtra("Create")) {
                        Intent inStarted = new Intent(mContext, LandingActivity.class);
                        inStarted.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(inStarted);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {
                        userBlockDialogUser(mContext);
                    } else if (response.body().error.getCode().equals(Constants.TRANSCHANGED)) {
//                        hitTransactionAPI();
//                        alertTransFeeDialog();
                    } else {
                        showAlert(llAddImage, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateTulModel> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void addToLocal(CreateTulModel.ResponseBean response) {
        ProfileModel.ResponseBean.ToolsBean toolsModel = new ProfileModel.ResponseBean.ToolsBean();
        toolsModel.setTitle(response.getTitle());
        toolsModel.setId(response.getId());
        toolsModel.setUser_id(response.getUser_id());
        toolsModel.setPrice(response.getPrice());
        toolsModel.setCondition(response.getCondition());
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


    private void addToLocalByEdit(SalesTulDetailModel.ResponseBean response) {
        ProfileModel.ResponseBean.ToolsBean toolsModel = new ProfileModel.ResponseBean.ToolsBean();
        toolsModel.setTitle(response.getTitle());
        toolsModel.setId(response.getId());
        toolsModel.setUser_id(response.getUser_id());
        toolsModel.setPrice(response.getPrice());
        toolsModel.setCondition(response.getCondition());
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


    private RequestBody createPartFromString(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    public String amountConversion(String am) {
        String amount;
        Double d = Double.parseDouble(am);
        Locale locale = new Locale("en", "UK");
        String pattern = "#0.00";

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);

        amount = decimalFormat.format(d);
        return amount;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PIC) {
                Log.e("Path = ", data.getStringExtra("filePath"));
                TulImageModel imageModel = new TulImageModel();
                if (data.getStringExtra("TYPE").equals(Constants.IMAGE_TEXT)) {
                    imageModel.setPath(data.getStringExtra("filePath"));
                    imageModel.setThumbnails(data.getStringExtra("filePath"));
                    imageModel.setType(Constants.IMAGE);
                } else if (data.getStringExtra("TYPE").equals(Constants.VIDEO_TEXT)) {
                    imageModel.setPath(data.getStringExtra("filePath"));
                    imageModel.setThumbnails(data.getStringExtra("thumbnail"));
                    imageModel.setType(Constants.VIDEO);
                }
                mTulImagesArray.add(0, imageModel);
                mImagesAdapter.notifyDataSetChanged();
                getImageVideoSize();
            } else if (requestCode == FULLVIEW) {
                mTulImagesArray.clear();
                mTulImagesArray.addAll((ArrayList) data.getParcelableArrayListExtra("tullImages"));
                mImagesAdapter.notifyDataSetChanged();

                mAttachmentIds.clear();
                mAttachmentIds.addAll(data.getStringArrayListExtra("attachment_ids"));

                getImageVideoSize();
            } else if (requestCode == MOVENEXT) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            } else if (requestCode == DELIVERY) {
                edDelivery.setText(data.getStringExtra("selected_data"));
                if (edDelivery.getText().toString().trim().equals(getString(R.string.YES))) {
                    mDelivery = "1";
                    llDeliveryCharges.setVisibility(View.VISIBLE);
                } else {
                    mDelivery = "0";
                    llDeliveryCharges.setVisibility(View.GONE);
                    edDeliveryChargesLocal.setText("");
                    edDeliveryChargesInter.setText("");
                }

            } else if (requestCode == CONDITION) {
                edCondition.setText(data.getStringExtra("selected_data"));
            } else if (requestCode == CURRENCY) {
                edCurrency.setText(data.getStringExtra("selected_data"));

                utils.setString(Constants.PRIMARY_CURRENCY, data.getStringExtra("selected_data"));

                final Drawable img;

                if (utils.getCurrency(data.getStringExtra("selected_data")).equals(Constants.POUND)) {
                    img = ContextCompat.getDrawable(mContext, R.drawable.pound);

                    edDeliveryChargesInter.setFloatingLabelText(getString(R.string.delivery_charges_inter_miles));
                    edDeliveryChargesInter.setHint(getString(R.string.delivery_charges_inter_miles));

                    edDeliveryChargesLocal.setFloatingLabelText(getString(R.string.delivery_charges_locally_miles));
                    edDeliveryChargesLocal.setHint(getString(R.string.delivery_charges_locally_miles));
                } else {
                    img = ContextCompat.getDrawable(mContext, R.drawable.euro);

                    edDeliveryChargesInter.setFloatingLabelText(getString(R.string.delivery_charges_inter));
                    edDeliveryChargesInter.setHint(getString(R.string.delivery_charges_inter));

                    edDeliveryChargesLocal.setFloatingLabelText(getString(R.string.delivery_charges_locally));
                    edDeliveryChargesLocal.setHint(getString(R.string.delivery_charges_locally));
                }

                img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));

                edPrice.setCompoundDrawables(img, null, null, null);

                edDeliveryChargesInter.setCompoundDrawables(img, null, null, null);
                edDeliveryChargesLocal.setCompoundDrawables(img, null, null, null);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getImageVideoSize() {
        mImagesSize = 0;
        mVideoSize = 0;
        for (TulImageModel mItem : mTulImagesArray) {
            if (mItem.getType().equals(Constants.IMAGE))
                mImagesSize++;
            else if (mItem.getType().equals(Constants.VIDEO))
                mVideoSize++;
        }
    }

    public void fullView(int position) {
        Log.e("Position = ", position + "");
        Intent fullViewIntent = new Intent(mContext, TullFullViewActivity.class);
        fullViewIntent.putExtra("position", position);
        fullViewIntent.putStringArrayListExtra("attachment_ids", mAttachmentIds);
        fullViewIntent.putParcelableArrayListExtra("tullImages", mTulImagesArray);
        startActivityForResult(fullViewIntent, FULLVIEW);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    private MultipartBody.Part prepareFilePart(File mFile) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), mFile);
        return MultipartBody.Part.createFormData("images[]", mFile.getName(), requestFile);
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

    private void hitEditAPI(String videoPath) {

        showProgress();
        RequestBody reqFileVideo, reqFileThumb;
        MultipartBody.Part bodyVideo, bodyThumb;
        File mFileVideo, mFileThumb;

        reqFileVideo = RequestBody.create(MediaType.parse("video/*"), "");
        bodyVideo = MultipartBody.Part.createFormData("video", "", reqFileVideo);

        reqFileThumb = RequestBody.create(MediaType.parse("image/*"), "");
        bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", "", reqFileThumb);

//        ArrayList<MultipartBody.Part> imagesArray = new ArrayList<>();
//        Collections.reverse(mTulImagesArray);
//        for (TulImageModel imageModel : mTulImagesArray) {
//            if (imageModel.getType().equals(Constants.IMAGE)) {
//                File mFileImage = new File(imageModel.getPath());
//                imagesArray.add(prepareFilePart(mFileImage));
//            } else {
//                mFileVideo = new File(videoPath);
//                mFileThumb = new File(imageModel.getThumbnails());
//
//                reqFileVideo = RequestBody.create(MediaType.parse("video/*"), mFileVideo);
//                reqFileThumb = RequestBody.create(MediaType.parse("image/*"), mFileThumb);
//
//                bodyVideo = MultipartBody.Part.createFormData("video", mFileVideo.getName(), reqFileVideo);
//                bodyThumb = MultipartBody.Part.createFormData("v_thumbnail", mFileThumb.getName(), reqFileThumb);
//            }
//        }
        ArrayList<MultipartBody.Part> imagesArray = new ArrayList<>();
        Collections.reverse(mTulImagesArray);
        for (TulImageModel imageModel : mTulImagesArray) {
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

        StringBuilder mBuilderAttachmentIds = new StringBuilder();
        String AttachmentIds = "";
        if (mAttachmentIds != null && mAttachmentIds.size() > 0) {
            for (String ids : mAttachmentIds) {
                mBuilderAttachmentIds.append(ids + ",");
            }
            String attachmentIds = mBuilderAttachmentIds.toString();
            AttachmentIds = attachmentIds.substring(0, attachmentIds.length() - 1);
        }
        Log.e("mAttachmentIds = ", AttachmentIds);

        Call<SalesTulDetailModel> call = RetrofitClient.getInstance().editSalesTul(
                createPartFromString(utils.getString("access_token", "")),
                createPartFromString(String.valueOf(mViewTulModel.getId())),
                createPartFromString(edTitle.getText().toString().trim()),
                createPartFromString(edDesc.getText().toString().trim()),
                createPartFromString(edNoTuls.getText().toString().trim()),
                createPartFromString(edPrice.getText().toString().trim()),
                createPartFromString(edCondition.getText().toString().trim()),
                createPartFromString(mDelivery),
                createPartFromString(edDeliveryChargesLocal.getText().toString().trim()),
                createPartFromString(edDeliveryChargesInter.getText().toString().trim()),
                createPartFromString(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, ""))),
                createPartFromString(utils.getString(Constants.PRIMARY_CURRENCY, "")),
                createPartFromString(AttachmentIds),
                imagesArray, bodyVideo, bodyThumb);

        call.enqueue(new Callback<SalesTulDetailModel>() {
            @Override
            public void onResponse(Call<SalesTulDetailModel> call, Response<SalesTulDetailModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {

                    db.deleteTulAttachemnetById(String.valueOf(mViewTulModel.getId()));


                    addToLocalByEdit(response.body().getResponse());

                    Intent intent = new Intent();
                    intent.putExtra("tul_data", response.body().getResponse());
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals(Constants.BLOCKEDERROR)) {
                        userBlockDialogUser(mContext);
                    } else if (response.body().error.getCode().equals(Constants.TRANSCHANGED)) {
//                        hitTransactionAPI();
//                        alertTransFeeDialog();
                    } else {
                        showAlert(llAddImage, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesTulDetailModel> call, Throwable t) {
                t.printStackTrace();
                hideProgress();
            }
        });

    }

    private void alertDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.all_data_discard)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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

    private void moveBack() {
        if (getIntent().hasExtra("Create")) {
            Intent inStarted = new Intent(mContext, LandingActivity.class);
            inStarted.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(inStarted);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else {
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    void selectOptions(ArrayList<String> mData, int intentType, String selected) {
        Intent in = new Intent(mContext, OptionSelection.class);
        in.putExtra("selected", selected);
        in.putStringArrayListExtra("data", mData);
        startActivityForResult(in, intentType);
    }

    private PopupWindow showEarningPopup() {

        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.info_sales_dialog_layout, null);

        LinearLayout.LayoutParams arrowParms = new LinearLayout.LayoutParams((int) (getResources().getDimension(R.dimen._15sdp)), (int) (getResources().getDimension(R.dimen._15sdp)));
        arrowParms.setMargins(0, 0, mWidth / 22, 0);
        arrowParms.gravity = Gravity.RIGHT;
        TextView txtArrow = (TextView) view.findViewById(R.id.txt_arrow1);
        txtArrow.setLayoutParams(arrowParms);

        TextView txtInfo = (TextView) view.findViewById(R.id.txt_info);
        txtInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
        txtInfo.setText(R.string.sale_trans_info);

        RecyclerView rvView = (RecyclerView) view.findViewById(R.id.rv_price);
        SalesInfoAdapter salesInfoAdapter;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvView.setLayoutManager(linearLayoutManager);
        salesInfoAdapter = new SalesInfoAdapter(mContext, mPopupPriceArray);
        rvView.setAdapter(salesInfoAdapter);

        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        return popupWindow;
    }

    void hitPopUpApi() {
        Call<SalesPriceFee> call = RetrofitClient.getInstance().salesPriceFee(utils.getString("access_token", ""));
        call.enqueue(new Callback<SalesPriceFee>() {
            @Override
            public void onResponse(Call<SalesPriceFee> call, Response<SalesPriceFee> response) {
                if (response.body().getResponse() != null) {
                    mPopupPriceArray.addAll(response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<SalesPriceFee> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        moveBack();
        super.onBackPressed();
    }
}
