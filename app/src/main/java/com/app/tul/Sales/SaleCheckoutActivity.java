package com.app.tul.Sales;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.AddPaymentCardActivity;
import com.app.tul.BaseActivity;
import com.app.tul.LandingActivity;
import com.app.tul.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import java.util.ArrayList;

import adapters.CheckoutCardAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import model.CardLocalModel;
import model.SalesBookTulModel;
import model.SalesDeliveryDetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;
import utils.CryptLib;
import utils.CustomLoadingDialog;

public class SaleCheckoutActivity extends BaseActivity {

    private static final int ADD_CARD = 1;

    public EditText edCVV;

    @BindView(R.id.txt_toolbar_title)
    TextView txtToolBarTitle;

    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;
    @BindView(R.id.ed_location)
    EditText edLocation;
    @BindView(R.id.txt_tul_title)
    TextView txtTulTitle;
    @BindView(R.id.txt_quantity)
    TextView txtQuantity;
    @BindView(R.id.txt_price_calculate)
    TextView txtCalculatePrice;
    @BindView(R.id.txt_calculate_delivery_charges)
    TextView txtCalculateDeliveryCharges;
    @BindView(R.id.txt_delivery_charges)
    TextView txtDeliveryCharges;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.ll_delivery)
    LinearLayout llDelivery;

    @BindView(R.id.txt_delivery_type)
    TextView txtDeliveryType;

    @BindView(R.id.rv_card_details)
    RecyclerView rvCardDetails;
    @BindView(R.id.tv_add_new_card)
    TextView txtAddNewCard;
    @BindView(R.id.txt_next)
    TextView txtNext;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rg_payment)
    RadioGroup rgPayment;

    @BindView(R.id.rb_bank)
    RadioButton rbBank;
    @BindView(R.id.rb_card)
    RadioButton rbCard;

    @BindView(R.id.view_location)
    View viewLocation;

    CheckoutCardAdapter mAdapter;
    CardLocalModel.ResponseBean cardDetails;

    SalesDeliveryDetailModel mModel;
    ArrayList<CardLocalModel.ResponseBean> mArrayListCard = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_sale_checkout;
    }

    @Override
    protected void initUI() {
        edCVV = new EditText(this);

        txtToolBarTitle.setText(getString(R.string.checkout));
        txtToolBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        rvCardDetails.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        rvCardDetails.setNestedScrollingEnabled(false);
        rvCardDetails.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void onCreateStuff() {
        cardDetails = new CardLocalModel.ResponseBean();

        mModel = getIntent().getParcelableExtra("data");

        edLocation.setText(mModel.getAddress());

        txtTulTitle.setText(mModel.getTitle());

        txtQuantity.setText(getString(R.string.qty_) + mModel.getQuantity());

        txtPrice.setText(mModel.getPrice());

        txtCalculatePrice.setText(mModel.getPrice_calculate());

        txtDeliveryCharges.setText(mModel.getDelivery_cost());

        txtCalculateDeliveryCharges.setText(mModel.getDelivery_charges_calculate());

        txtTotalAmount.setText(mModel.getTotal_amount());

        if (mModel.getDelivery().equals("1")) {
            txtDeliveryType.setText(mModel.getDelivery_type());
            llDelivery.setVisibility(View.VISIBLE);
            edLocation.setVisibility(View.VISIBLE);
            viewLocation.setVisibility(View.VISIBLE);
        }
        setCardData();

        rbCard.setChecked(true);

        rbBank.setChecked(false);

        rgPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_card:
                        rvCardDetails.setEnabled(true);
                        rvCardDetails.setVisibility(View.VISIBLE);
                        txtAddNewCard.setVisibility(View.VISIBLE);

                        break;
                    case R.id.rb_bank:
                        rvCardDetails.setVisibility(View.GONE);
                        txtAddNewCard.setVisibility(View.GONE);
                        rvCardDetails.setEnabled(false);
                        break;
                }
            }
        });
    }

    void setCardData() {
        mArrayListCard.clear();
        mArrayListCard.addAll(db.getAllCards());
        if (mArrayListCard.size() > 0)
            mArrayListCard.get(0).setIs_selected(true);
        mAdapter = new CheckoutCardAdapter(this, mArrayListCard);
        rvCardDetails.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        txtAddNewCard.setOnClickListener(this);
        txtNext.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_new_card:
                Intent card = new Intent(mContext, AddPaymentCardActivity.class);
                startActivityForResult(card, ADD_CARD);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;

            case R.id.txt_next:
                if (rbCard.isChecked()) {
                    for (CardLocalModel.ResponseBean cardItem : mArrayListCard) {
                        if (cardItem.is_selected()) {
                            cardDetails = cardItem;
                            break;
                        }
                    }
                    if (cardDetails == null || mArrayListCard.size() == 0)
                        showAlert(llDelivery, getString(R.string.add_card_detail));
                    else if (TextUtils.isEmpty(edCVV.getText().toString().trim()))
                        showAlert(llDelivery, getString(R.string.add_cvv_number));
                    else {
                        if (connectedToInternet()) {
                            showProgress();
                            String cardNo = "";
                            try {
                                CryptLib cryptLib = new CryptLib();
                                cardNo = cryptLib.decryptSimple(cardDetails.getCard_number(), Constants.KEY, Constants.IV);
                            } catch (Exception e) {
                                Log.e("Exce  = ", e + "");
                            }
                            Card mStripeCard = new Card(cardNo.replaceAll(" ", ""), cardDetails.getExpiry_month(),
                                    cardDetails.getExpiry_year(), edCVV.getText().toString());
                            if (!mStripeCard.validateCVC()) {
                                showAlert(llDelivery, getString(R.string.invalid_cvv_number));
                            } else if (!mStripeCard.validateCard()) {
                                showAlert(llDelivery, getString(R.string.invalid_card));
                            } else {
                                hitStripeAPI(mStripeCard);
                            }
                        } else {
                            showInternetAlert(llDelivery);
                        }
                    }
                } else if (rbBank.isChecked()) {

                    if (connectedToInternet()) {
                        showProgress();
                        hitBookAPI("");
                    } else {
                        showInternetAlert(llDelivery);
                    }
                }
                break;

            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_CARD:
                    setCardData();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void hitStripeAPI(Card mStripeCard) {
        txtNext.setEnabled(false);
        Stripe stripe = null;
        try {
            stripe = new Stripe(Constants.STRIPE_KEY);
            stripe.createToken(mStripeCard, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    showAlert(llDelivery, error.getLocalizedMessage());
                    txtNext.setEnabled(true);
                    hideProgress();
                }

                @Override
                public void onSuccess(Token token) {
                    Log.e("stripe token", "is " + token);
                    hitBookAPI(token.getId());
                }
            });
        } catch (AuthenticationException e) {
            showAlert(llDelivery, e.getLocalizedMessage());
        }
    }


    private void hitBookAPI(String token) {
        final String paymentMode;
        txtNext.setEnabled(false);

        if (rbCard.isChecked()) {
            paymentMode = "0";
        } else {
            paymentMode = "1";
        }

        String deliveryType;
        if (mModel.getDelivery_type().contains(getString(R.string.national))) {
            deliveryType = "0";
        } else {
            deliveryType = "1";
        }
        Call<SalesBookTulModel> call = RetrofitClient.getInstance().buyATul(utils.getString("access_token", ""),
                mModel.getId(), mModel.getQuantity(), mModel.getEdit_count(), mModel.getDelivery(),
                deliveryType,
                paymentMode,
                mModel.getDelivery_cost().replace(mModel.getCurrency(), "").trim(),
                cardDetails.getCard_number(),
                cardDetails.getName_on_card(),
                String.valueOf(cardDetails.getExpiry_month()),
                String.valueOf(cardDetails.getExpiry_year()),
                mModel.getPrice().replace(mModel.getCurrency(), "").trim(),
                mModel.getTotal_amount().replace(mModel.getCurrency(), "").trim(),
                mModel.getAddress(),
                mModel.getLatitude(),
                mModel.getLongitude(),
                token,
                mModel.getPrimary_currency());
        call.enqueue(new Callback<SalesBookTulModel>() {
            @Override
            public void onResponse(Call<SalesBookTulModel> call, Response<SalesBookTulModel> response) {
                hideProgress();
                txtNext.setEnabled(true);

                if (response.body().getResponse() != null) {
                    Log.d("RESPONSE", response.toString());

                    Intent inSplash = new Intent(mContext, SalesBookingCompletedActivity.class);
                    inSplash.putExtra("booking_id", response.body().getResponse().getId());

                    if (paymentMode.equals("1")) {
                        inSplash.putExtra("mode", 1);
                    } else {
                        inSplash.putExtra("mode", 0);
                    }

                    inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(inSplash);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                } else {
                    txtNext.setEnabled(true);
                    CustomLoadingDialog.getLoader().dismissLoader();

                    try {
                        if (response.body().error.getCode().equals(errorAccessToken)) {
                            moveToSplash();
                        } else {
                            showAlert(llDelivery, response.body().error.getMessage());

                        }
                    } catch (Exception e) {
                        Toast.makeText(SaleCheckoutActivity.this, R.string.tull_removed_by_owner, Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(mContext, LandingActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesBookTulModel> call, Throwable t) {
                showAlert(llDelivery, t.getLocalizedMessage());
                txtNext.setEnabled(true);
                hideProgress();
//                showAlert(llDelivery, String.valueOf(t.printStackTrace()));
            }

        });
    }

    private void alertPauseDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alertPaymentDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Exceed").setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
