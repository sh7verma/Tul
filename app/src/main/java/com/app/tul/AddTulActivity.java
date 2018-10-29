package com.app.tul;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import adapters.TullImagesAdapter;
import butterknife.BindView;
import customviews.MaterialEditText;
import model.TulImageModel;
import model.TulModel;
import utils.Constants;

public class AddTulActivity extends BaseActivity {


    private static final int MOVENEXT = 7;
    private static final int CURRENCY = 12;
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
    @BindView(R.id.ll_category)
    LinearLayout llCategory;
    @BindView(R.id.txt_category_hint)
    TextView txtCategoryHint;
    @BindView(R.id.txt_category_value)
    TextView txtCategoryValue;
    @BindView(R.id.ed_desc)
    MaterialEditText edDesc;
    @BindView(R.id.ed_no_tuls)
    MaterialEditText edNoTuls;
    @BindView(R.id.ed_price)
    MaterialEditText edPrice;
    @BindView(R.id.ed_your_earning)
    MaterialEditText edYourEarning;
    @BindView(R.id.img_your_earning_info)
    ImageView imgYourEarningInfo;
    @BindView(R.id.ed_no_bookings)
    MaterialEditText edNoBookings;
    @BindView(R.id.ed_discount)
    MaterialEditText edDiscount;
    @BindView(R.id.txt_additional_prices)
    TextView txtAdditionalPrices;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.txt_address_hint)
    TextView txtAddressHint;
    @BindView(R.id.txt_address_value)
    TextView txtAddressValue;
    @BindView(R.id.txt_rules)
    TextView txtRules;
    @BindView(R.id.txt_add_images)
    TextView txtAddImages;
    @BindView(R.id.ll_add_image)
    LinearLayout llAddImage;
    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    @BindView(R.id.txt_save)
    TextView txtSave;
    @BindView(R.id.txt_discard)
    TextView txtDiscard;

    @BindView(R.id.ed_currency)
    EditText edCurrency;
    @BindView(R.id.view_currency)
    View viewCurrency;


    int mImagesSize, mVideoSize;
    AddTulActivity mAddTulActivity;
    TulModel.ListTulModel mListTulModel;
    private int CATEGORY = 1;
    private int PRICE = 2;
    private int RULES = 3;
    private int ADDRESS = 4;
    private int PIC = 5;
    private int FULLVIEW = 6;
    private String mSelectedCat = "", mSecurityFee, mConvienceFee, mAddress;
    private int mSelectedCatId;
    private double mLatitude, mLongitude;
    private double mTransactionPercentage = 0;
    private ArrayList<String> mRulesArray = new ArrayList<>();
    private TullImagesAdapter mImagesAdapter;
    private ArrayList<TulImageModel> mTulImagesArray = new ArrayList<>();
    private ArrayList<String> mAttachmentIds = new ArrayList<>();
    private boolean isCategoryEnabled = true;
    private boolean mPopupEnable;

    @Override
    protected int getContentView() {
        return R.layout.activity_list_tul;
    }

    @Override
    protected void initUI() {
        mAddTulActivity = this;
        hideViews();

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtToolbarTitle.setText(getResources().getString(R.string.list_tul));

        llInner.setPadding(mWidth / 24, 0, mWidth / 24, 0);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        txtHintMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        txtHintMsg.setPadding(mWidth / 24, mWidth / 24, mWidth / 24, mHeight / 24);

        edTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edTitle.setTypeface(typeface);

        txtCategoryHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        txtCategoryHint.setPadding(0, mWidth / 32, 0, 0);

        txtCategoryValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtCategoryValue.setPadding(0, mHeight / 28, 0, mHeight / 28);

        edDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edDesc.setTypeface(typeface);

        edNoTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edNoTuls.setTypeface(typeface);

        edPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edPrice.setTypeface(typeface);

        edYourEarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edYourEarning.setTypeface(typeface);

        edNoBookings.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edNoBookings.setTypeface(typeface);

        edDiscount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edDiscount.setTypeface(typeface);

        edCurrency.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        edCurrency.setTypeface(typeface);

        txtAdditionalPrices.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtAdditionalPrices.setPadding(0, mHeight / 28, 0, mHeight / 28);

        txtAddressHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtAddressHint.setPadding(0, mWidth / 32, 0, mWidth / 64);

        txtAddressValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtAddressValue.setPadding(0, mHeight / 28, 0, mHeight / 28);

        txtRules.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtRules.setPadding(0, mHeight / 28, 0, mHeight / 28);

        txtAddImages.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtAddImages.setPadding(0, mWidth / 24, 0, mWidth / 24);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(linearLayoutManager);
        mImagesAdapter = new TullImagesAdapter(mContext, mTulImagesArray, mAddTulActivity);
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

        txtDiscard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtDiscard.setPadding(0, mWidth / 28, 0, mWidth / 28);

    }

    private void hideViews() {
        txtCategoryHint.setVisibility(View.GONE);
        txtAddressHint.setVisibility(View.GONE);
        imgBack.setOnClickListener(this);
        txtRules.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtDiscard.setOnClickListener(this);
    }

    @Override
    protected void onCreateStuff() {
        mListTulModel = TulModel.getListTul();
//        final Drawable img = ContextCompat.getDrawable(mContext, R.drawable.pound);
//        img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));

        if (utils.getString(Constants.IS_CURRENCY_SELECTED, "0").equals("0")) {
            edCurrency.setVisibility(View.VISIBLE);
            viewCurrency.setVisibility(View.VISIBLE);
            edCurrency.setText("GBP");
        } else {
            edCurrency.setVisibility(View.GONE);
            viewCurrency.setVisibility(View.GONE);
        }


        final Drawable img;
        if (utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,"")).equals(Constants.POUND)) {
            img = ContextCompat.getDrawable(mContext, R.drawable.pound);
        } else {
            img = ContextCompat.getDrawable(mContext, R.drawable.euro);
        }

        img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));
        edPrice.setCompoundDrawables(img, null, null, null);

        edYourEarning.setCompoundDrawables(img, null, null, null);

        if (mListTulModel.tranastionPercentage != null)
            mTransactionPercentage = Double.parseDouble(mListTulModel.tranastionPercentage);

        if (getIntent().hasExtra("disable_category")) {
            llCategory.setEnabled(false);
            isCategoryEnabled = false;
        }
        edYourEarning.setEnabled(false);

        edPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().startsWith(".")) {
                    s = "0.";
                    edPrice.setText(s);
                    edPrice.setSelection(s.length());
                }
                if (s.length() > 0) {
                    mPopupEnable = true;
                    double i = Double.parseDouble(String.valueOf(s));
                    i = i - ((mTransactionPercentage * i) / 100);
                    edYourEarning.setText(amountConversion(String.format("%.10f", i)));
                } else {
                    edYourEarning.setText("");
                    mPopupEnable = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initListener() {
        llCategory.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        txtAdditionalPrices.setOnClickListener(this);
        llAddImage.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgYourEarningInfo.setOnClickListener(this);
        edCurrency.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return AddTulActivity.this;
    }

    @Override
    protected void onResume() {
        setData();
        super.onResume();
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

    private void setData() {
        if (mListTulModel.updateData != null && mListTulModel.updateData) {
            mListTulModel.updateData = false;
            if (!TextUtils.isEmpty(mListTulModel.title)) {
                edTitle.setText(mListTulModel.title);
                edTitle.setSelection(edTitle.getText().toString().length());

                if (!TextUtils.isEmpty(mListTulModel.category)) {
                    mSelectedCat = mListTulModel.category;
                    mSelectedCatId = mListTulModel.categoryId;

                    txtCategoryValue.setPadding(0, mWidth / 64, 0, mWidth / 24);
                    txtCategoryValue.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));
                    txtCategoryHint.setVisibility(View.VISIBLE);
                    txtCategoryValue.setText(mSelectedCat);
                }
                if (!TextUtils.isEmpty(mListTulModel.description)) {
                    edDesc.setText(mListTulModel.description);
                    edDesc.setSelection(edDesc.getText().toString().length());
                }
                if (!TextUtils.isEmpty(mListTulModel.noOfTuls)) {
                    edNoTuls.setText(mListTulModel.noOfTuls);
                    edNoTuls.setSelection(edNoTuls.getText().toString().length());
                }
                if (!TextUtils.isEmpty(mListTulModel.price)) {
                    edPrice.setText(mListTulModel.price);
                    edPrice.setSelection(edPrice.getText().toString().length());
                }
                if (!TextUtils.isEmpty(mListTulModel.booking_days)) {
                    edNoBookings.setText(mListTulModel.booking_days);
                    edNoBookings.setSelection(edNoBookings.getText().toString().length());
                }
                if (!TextUtils.isEmpty(mListTulModel.discount)) {
                    edDiscount.setText(mListTulModel.discount);
                    edDiscount.setSelection(edDiscount.getText().toString().length());
                }
                if (!TextUtils.isEmpty(mListTulModel.securityFee)) {
                    mSecurityFee = mListTulModel.securityFee;
                    txtAdditionalPrices.setTextColor(ContextCompat.getColor(this, R.color.black_color));
                }
                if (!TextUtils.isEmpty(mListTulModel.convienceFee)) {
                    mConvienceFee = mListTulModel.convienceFee;
                }
                if (!TextUtils.isEmpty(mListTulModel.address)) {
                    mAddress = mListTulModel.address;
                    mLatitude = mListTulModel.latitude;
                    mLongitude = mListTulModel.longitude;

                    txtAddressValue.setPadding(0, mWidth / 64, 0, mWidth / 24);
                    txtAddressValue.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));
                    txtAddressHint.setVisibility(View.VISIBLE);
                    txtAddressValue.setText(mAddress);
                    txtAddressValue.setAllCaps(false);
                }
                if (mListTulModel.rules != null) {
                    mRulesArray = mListTulModel.rules;
                    txtRules.setTextColor(ContextCompat.getColor(this, R.color.black_color));
                }
                if (mListTulModel.imagesVideo != null) {
                    mTulImagesArray = mListTulModel.imagesVideo;
                    mImagesAdapter = new TullImagesAdapter(mContext, mTulImagesArray, mAddTulActivity);
                    rvImages.setAdapter(mImagesAdapter);
                    rvImages.setItemAnimator(new DefaultItemAnimator());
                }
                getImageVideoSize();
            }
        }
        if (mListTulModel.isEdit) {
            mSelectedCat = mListTulModel.category;
            mSelectedCatId = mListTulModel.categoryId;

            txtCategoryValue.setPadding(0, mWidth / 64, 0, mWidth / 24);
            txtCategoryValue.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));
            txtCategoryHint.setVisibility(View.VISIBLE);
            txtCategoryValue.setText(mSelectedCat);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_your_earning_info:
                if (mPopupEnable) {
                    PopupWindow popupwindow_obj = showEarningPopup(edPrice.getText().toString().trim(),
                            mListTulModel.tranastionPercentage);
                    popupwindow_obj.showAsDropDown(imgYourEarningInfo, 0, -50);
                }
                break;
            case R.id.img_back:
                mListTulModel.updateData = true;
                TulModel.setListTul(mListTulModel);
                moveBack();
                break;
            case R.id.ed_currency:
                ArrayList<String> currency = new ArrayList<>();
                currency.add("GBP");
                currency.add("EUR");
                selectOptions(currency, CURRENCY, edCurrency.getText().toString());

                break;
            case R.id.ll_category:
                if (isCategoryEnabled) {
                    Intent catIntent = new Intent(mContext, SelectCategoryActivity.class);
                    catIntent.putExtra("selected_category", mSelectedCat);
                    catIntent.putExtra("selected_categoryId", mSelectedCatId);
                    startActivityForResult(catIntent, CATEGORY);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } else
                    showAlert(llAddImage, getString(R.string.cannot_edit_category));
                break;
            case R.id.txt_additional_prices:
                Intent priceIntent = new Intent(mContext, AdditionalPricesActivity.class);
                if (mListTulModel.isEdit)
                    priceIntent.putExtra("is_edit", "yes");
                priceIntent.putExtra("security_price", mSecurityFee);
                priceIntent.putExtra("transaction_percentage", mListTulModel.tranastionPercentage);
                priceIntent.putExtra("convience_price", mConvienceFee);
                startActivityForResult(priceIntent, PRICE);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.txt_rules:
                Intent rulesIntent = new Intent(mContext, RulesActivity.class);
                if (mListTulModel.isEdit)
                    rulesIntent.putExtra("is_edit", "yes");
                rulesIntent.putStringArrayListExtra("rules_array", mRulesArray);
                startActivityForResult(rulesIntent, RULES);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.ll_address:
                Intent addressIntent = new Intent(mContext, LocationSearchActivity.class);
                startActivityForResult(addressIntent, ADDRESS);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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
            case R.id.txt_discard:
                alertDiscardDialog();
                break;
            case R.id.txt_save:
                if (TextUtils.isEmpty(edTitle.getText().toString().trim()))
                    showAlert(llInner, getString(R.string.error_title));
                else if (mSelectedCatId == 0)
                    showAlert(llInner, getString(R.string.error_category));
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
                else if (Double.parseDouble(edPrice.getText().toString().trim()) > Constants.MAX_PRICE)
                    showAlert(llInner, getString(R.string.error_max_price));
//                else if (TextUtils.isEmpty(edNoBookings.getText().toString().trim()))
//                    showAlert(llInner, getString(R.string.error_booking_empty));
                else if (!TextUtils.isEmpty(edNoBookings.getText().toString()) && Integer.parseInt(edNoBookings.getText().toString()) <= 0) {
                    showAlert(llInner, getString(R.string.error_booking_days));
                } else if (!TextUtils.isEmpty(edNoBookings.getText().toString().trim()) && TextUtils.isEmpty(edDiscount.getText().toString().trim()))
                    showAlert(llInner, getString(R.string.enter_discount_percentage));
                else if (TextUtils.isEmpty(edNoBookings.getText().toString().trim()) && !TextUtils.isEmpty(edDiscount.getText().toString().trim()))
                    showAlert(llInner, getString(R.string.enter_no_booking_days));
                else if (!TextUtils.isEmpty(edDiscount.getText().toString().trim()) && edDiscount.getText().toString().equals("."))
                    showAlert(llInner, getString(R.string.error_discount));
                else if (!TextUtils.isEmpty(edDiscount.getText().toString().trim()) && Double.parseDouble(edDiscount.getText().toString().trim()) < Constants.MIN_DISCOUNT)
                    showAlert(llInner, getString(R.string.error_discount));
                else if (!TextUtils.isEmpty(edDiscount.getText().toString().trim()) && Double.parseDouble(edDiscount.getText().toString().trim()) > Constants.MAX_DISCOUNT)
                    showAlert(llInner, getString(R.string.error_max_discount));
                else if (TextUtils.isEmpty(mSecurityFee))
                    showAlert(llInner, getString(R.string.error_addtional_prices));
                else if (TextUtils.isEmpty(mAddress))
                    showAlert(llInner, getString(R.string.error_address));
//                else if (mRulesArray.size() == 0)
//                    showAlert(llInner, getString(R.string.error_rules));
                else if (Integer.parseInt(edNoTuls.getText().toString().trim()) < mListTulModel.active_bookings)
                    showAlert(llInner, getString(R.string.number_cannot_lessthan_already_booked_tul));
                else if (mTulImagesArray.size() == 0)
                    showAlert(llInner, getString(R.string.error_image_video));
                else {
                    mListTulModel.title = edTitle.getText().toString().trim();
                    mListTulModel.category = mSelectedCat;
                    mListTulModel.categoryId = mSelectedCatId;
                    mListTulModel.description = edDesc.getText().toString().trim();
                    mListTulModel.noOfTuls = edNoTuls.getText().toString().trim();
                    mListTulModel.price = edPrice.getText().toString().trim();
                    mListTulModel.booking_days = edNoBookings.getText().toString().trim().trim();
                    mListTulModel.discount = edDiscount.getText().toString().trim();
                    mListTulModel.securityFee = mSecurityFee;
                    mListTulModel.convienceFee = mConvienceFee;
                    mListTulModel.address = mAddress;
                    mListTulModel.latitude = mLatitude;
                    mListTulModel.longitude = mLongitude;
                    mListTulModel.rules = mRulesArray;
                    mListTulModel.imagesVideo = mTulImagesArray;
                    mListTulModel.attachments_ids = mAttachmentIds;
                    TulModel.setListTul(mListTulModel);

                    Intent intent = new Intent(mContext, PreferencesActivity.class);
                    intent.putExtra("transaction_fee", mListTulModel.tranastionPercentage);
                    startActivityForResult(intent, MOVENEXT);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                break;
        }
    }

    void selectOptions(ArrayList<String> mData, int intentType, String selected) {
        Intent in = new Intent(mContext, OptionSelection.class);
        in.putExtra("selected", selected);
        in.putStringArrayListExtra("data", mData);
        startActivityForResult(in, intentType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CATEGORY) {
                txtCategoryValue.setPadding(0, mWidth / 64, 0, mWidth / 24);
                txtCategoryValue.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));
                txtCategoryHint.setVisibility(View.VISIBLE);
                txtCategoryValue.setText(data.getStringExtra("catName"));

                mSelectedCat = Constants.EMPTY;
                mSelectedCat = data.getStringExtra("catName");
                mSelectedCatId = data.getIntExtra("catId", 0);
            } else if (requestCode == PRICE) {
                mSecurityFee = Constants.EMPTY;
                mConvienceFee = Constants.EMPTY;
                mSecurityFee = data.getStringExtra("security_fee");
                mConvienceFee = data.getStringExtra("convience_fee");
                txtAdditionalPrices.setTextColor(ContextCompat.getColor(this, R.color.black_color));
            } else if (requestCode == RULES) {
                mRulesArray.clear();
                mRulesArray = data.getStringArrayListExtra("rules_array");
                if (data.getStringArrayListExtra("rules_array").size() > 0) {
                    Log.e("Rules Size = ", mRulesArray.size() + "");
                    txtRules.setTextColor(ContextCompat.getColor(this, R.color.black_color));
                } else {
                    txtRules.setTextColor(ContextCompat.getColor(this, R.color.hint_color_dark));
                }
            } else if (requestCode == ADDRESS) {
                mLatitude = Double.parseDouble(data.getStringExtra("latitude"));
                mLongitude = Double.parseDouble(data.getStringExtra("longitude"));
                mAddress = Constants.EMPTY;
                mAddress = data.getStringExtra("address");

                txtAddressValue.setPadding(0, mWidth / 64, 0, mWidth / 24);
                txtAddressValue.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));
                txtAddressHint.setVisibility(View.VISIBLE);
                txtAddressValue.setText(data.getStringExtra("address"));
                txtAddressValue.setAllCaps(false);
            } else if (requestCode == PIC) {
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
            } else if (requestCode == CURRENCY) {

                edCurrency.setText(data.getStringExtra("selected_data"));

                utils.setString(Constants.PRIMARY_CURRENCY, data.getStringExtra("selected_data"));

                final Drawable img;
                if (utils.getCurrency(data.getStringExtra("selected_data")).equals(Constants.POUND)) {
                    img = ContextCompat.getDrawable(mContext, R.drawable.pound);
                } else {
                    img = ContextCompat.getDrawable(mContext, R.drawable.euro);
                }

                img.setBounds(0, 0, (int) (mWidth * 0.05), (int) (mWidth * 0.05));
                edPrice.setCompoundDrawables(img, null, null, null);
                edYourEarning.setCompoundDrawables(img, null, null, null);

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

    private void alertDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_message)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListTulModel.title = null;
                        mListTulModel.attachments_ids = new ArrayList<String>();
                        for (TulImageModel imageModel : mTulImagesArray) {
                            if (imageModel.isEdit()) {
                                mListTulModel.attachments_ids.
                                        add(String.valueOf(imageModel.getId()));
                            }
                        }
                        TulModel.setListTul(mListTulModel);
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

    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        if (getIntent().hasExtra("BACKTOLANDING")) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private PopupWindow showEarningPopup(String price, String transaction_percentage) {
        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.info_dialog_layout, null);

        LinearLayout.LayoutParams arrowParms = new LinearLayout.LayoutParams((int) (getResources().getDimension(R.dimen._15sdp)), (int) (getResources().getDimension(R.dimen._15sdp)));
        arrowParms.setMargins(0, 0, mWidth / 22, 0);
        arrowParms.gravity = Gravity.RIGHT;
        TextView txtArrow = (TextView) view.findViewById(R.id.txt_arrow1);
        txtArrow.setLayoutParams(arrowParms);

        TextView txtInfo = (TextView) view.findViewById(R.id.txt_info);
        txtInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        txtInfo.setText("This is the amount you will earn after deducting TUL fees of " + transaction_percentage + "% from your " +
                utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY,"")) + price +
                " listings.");
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        return popupWindow;
    }


}
