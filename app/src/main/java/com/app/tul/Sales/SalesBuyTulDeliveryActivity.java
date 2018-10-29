package com.app.tul.Sales;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.BaseActivity;
import com.app.tul.LocationSearchActivity;
import com.app.tul.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import model.SalesDeliveryDetailModel;
import model.SalesTulDetailModel;
import utils.Constants;

public class SalesBuyTulDeliveryActivity extends BaseActivity {

    private static final int LOCATION = 1;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.ll_delivery)
    LinearLayout llDelivery;

    @BindView(R.id.sc_delivery)
    SwitchCompat scDelivery;

    @BindView(R.id.txt_national_charges)
    TextView txtNationalCharges;
    @BindView(R.id.txt_international_charges)
    TextView txtInternationalCharges;

    @BindView(R.id.ed_location)
    EditText edLocation;

    @BindView(R.id.txt_tul_title)
    TextView txtTulTitle;
    @BindView(R.id.txt_quantity_count)
    TextView txtQuantityCount;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.img_minus)
    ImageView imgMinus;
    @BindView(R.id.txt_calculate_price)
    TextView txtCalculatePrice;
    @BindView(R.id.txt_price)
    TextView txtPrice;

    @BindView(R.id.ll_delivery_charges)
    LinearLayout llDeliveryCharges;
    @BindView(R.id.txt_calculate_delivery_charges)
    TextView txtCalculateDeliveryCharges;
    @BindView(R.id.txt_delivery_charges)
    TextView txtDeliveryCharges;

    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;

    @BindView(R.id.txt_next)
    TextView txtNext;

    @BindView(R.id.txt_delivery_type)
    TextView txtDeliveryType;
    @BindView(R.id.ll_delivery_switch)
    LinearLayout llDeliverySwitch;

    @BindView(R.id.txt_toolbar_title)
    TextView txtToolBarTitle;
    Double mLat, mLong;

    int mMaxQuantity;
    int mSelectedQuantity = 1;
    boolean isNational = false;
    int mDelivery = 0;

    boolean isMiles = false;

    SalesDeliveryDetailModel mModel;
    Float price;
    Float charges;
    double total;
    String Delivery_charges_int;
    String Delivery_charges_local;
    private SalesTulDetailModel.ResponseBean mViewTulModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_sales_buy_tul_delivery;
    }

    @Override
    protected void initUI() {
        txtToolBarTitle.setText(getString(R.string.delivery));
        txtToolBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
    }

    @Override
    protected void onCreateStuff() {


        mViewTulModel = getIntent().getParcelableExtra("data");

        if (mViewTulModel.getDelivery_type() == 1) {
            hideDeliveryView(false);
        } else {
            hideDeliveryView(true);
        }

        if (mViewTulModel.getDelivery_charges_int().contains(Constants.POUND)) {
            Delivery_charges_int = mViewTulModel.getDelivery_charges_int().replace(Constants.POUND, "");
        } else if (mViewTulModel.getDelivery_charges_int().contains(Constants.EURO)) {
            Delivery_charges_int = mViewTulModel.getDelivery_charges_int().replace(Constants.EURO, "");
        } else {
            Delivery_charges_int = mViewTulModel.getDelivery_charges_int();
        }

        if (mViewTulModel.getDelivery_charges_local().contains(Constants.POUND)) {
            Delivery_charges_local = mViewTulModel.getDelivery_charges_local().replace(Constants.POUND, "");
        } else if (mViewTulModel.getDelivery_charges_local().contains(Constants.EURO)) {
            Delivery_charges_local = mViewTulModel.getDelivery_charges_local().replace(Constants.EURO, "");
        } else {
            Delivery_charges_local = mViewTulModel.getDelivery_charges_local();
        }

        scDelivery.setChecked(false);

        llDeliverySwitch.setVisibility(View.GONE);

        scDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llDeliverySwitch.setVisibility(View.VISIBLE);

                } else {
                    llDeliverySwitch.setVisibility(View.GONE);
                    mDelivery = 0;
                    edLocation.setText("");
                    mLat = null;
                    mLong = null;
                    hitDeliveryCharges();
                    calculatePrice();

                }
            }
        });

        setData();
    }

    private void setData() {

        mMaxQuantity = mViewTulModel.getQuantity();
        if (utils.getCurrency(mViewTulModel.getPrimary_currency()).equals(Constants.POUND)) {
            txtNationalCharges.setText(mViewTulModel.getCurrency() + " " + Delivery_charges_local +
                    " per/mile(per Quantity)");
            txtInternationalCharges.setText(mViewTulModel.getCurrency() + " " + Delivery_charges_int +
                    " per/mile(per Quantity)");

            isMiles = true;
        } else {
            isMiles = false;
            txtNationalCharges.setText(mViewTulModel.getCurrency() + " " + Delivery_charges_local +
                    " per/km(per Quantity)");
            txtInternationalCharges.setText(mViewTulModel.getCurrency() + " " + Delivery_charges_int +
                    " per/km(per Quantity)");
        }
        txtTulTitle.setText(mViewTulModel.getTitle());
        calculatePrice();

    }

    private void calculatePrice() {

        txtQuantityCount.setText(mSelectedQuantity + "");

        txtCalculatePrice.setText(mViewTulModel.getCurrency() + " " + mViewTulModel.getPrice() + " X " + mSelectedQuantity);

        price = Float.valueOf(mViewTulModel.getPrice()) * mSelectedQuantity;

        txtPrice.setText(mViewTulModel.getCurrency() + " " + price);
        calculateTotal();

    }

    void calculateTotal() {

        price = Float.valueOf(mViewTulModel.getPrice()) * mSelectedQuantity;

        if (!isNational) {
            charges = Float.valueOf(Delivery_charges_int) * getDistance() * mSelectedQuantity;
        } else {
            charges = Float.valueOf(Delivery_charges_local) * getDistance() * mSelectedQuantity;
        }

        if (charges > 0) {
            mDelivery = 1;
        }

        total = price + charges;
        total = Math.round(total* 100.0) / 100.0;

        txtTotalAmount.setText(mViewTulModel.getCurrency() + " " + total);
    }


    void calculateDeliveryCharges() {
        Float charges;

        if (!isNational) {
            txtCalculateDeliveryCharges.setText(mViewTulModel.getCurrency() + " " +
                    Delivery_charges_int + " X " + getDistance() + " X " + mSelectedQuantity);
            charges = Float.valueOf(Delivery_charges_int) * getDistance() * mSelectedQuantity;


        } else {
            txtCalculateDeliveryCharges.setText(mViewTulModel.getCurrency() + " " +
                    Delivery_charges_local + " X " + getDistance() + " X " + mSelectedQuantity);

            charges = Float.valueOf(Delivery_charges_local) * getDistance() * mSelectedQuantity;

        }
        txtDeliveryCharges.setText(mViewTulModel.getCurrency() + " " + charges);
        calculateTotal();
    }

    void hideDeliveryView(boolean enable) {
        if (enable) {
            llDelivery.setVisibility(View.GONE);
            llDeliveryCharges.setVisibility(View.GONE);
        } else {
            llDelivery.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {
        edLocation.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        imgMinus.setOnClickListener(this);
        txtNext.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    void hitDeliveryCharges() {
        llDeliveryCharges.setVisibility(View.GONE);
        txtCalculateDeliveryCharges.setText("");
        txtDeliveryCharges.setText("");
        txtDeliveryType.setText("");
    }

    @Override
    protected Context getContext() {
        return this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ed_location:
                Intent addressIntent = new Intent(mContext, LocationSearchActivity.class);
                startActivityForResult(addressIntent, LOCATION);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.img_add:
                if (mSelectedQuantity < mMaxQuantity) {
                    mSelectedQuantity++;
                    calculatePrice();
                    calculateDeliveryCharges();
                }
                break;

            case R.id.img_minus:
                if (mSelectedQuantity > 1) {
                    mSelectedQuantity--;
                    calculatePrice();
                    calculateDeliveryCharges();
                }
                break;

            case R.id.txt_next:
                mModel = new SalesDeliveryDetailModel();

                mModel.setId(mViewTulModel.getId());
                mModel.setEdit_count(mViewTulModel.getEdit_count());
                mModel.setPrimary_currency(mViewTulModel.getPrimary_currency());

                mModel.setTotal_amount(txtTotalAmount.getText().toString().trim());

                mModel.setPrice(txtPrice.getText().toString().trim());
                mModel.setPrice_calculate(txtCalculatePrice.getText().toString().trim());
                if (mDelivery == 0) {
                    mModel.setDelivery_cost("");
                    mModel.setDelivery_charges_calculate("");
                }
                mModel.setDelivery_cost(txtDeliveryCharges.getText().toString().trim());
                mModel.setDelivery_charges_calculate(txtCalculateDeliveryCharges.getText().toString().trim());

                mModel.setAddress(edLocation.getText().toString().trim());
                mModel.setQuantity(mSelectedQuantity);

                mModel.setTitle(txtTulTitle.getText().toString().trim());
                mModel.setDelivery(String.valueOf(mDelivery));


                mModel.setDelivery_type(txtDeliveryType.getText().toString());

                mModel.setCurrency(mViewTulModel.getCurrency());
                if (scDelivery.isChecked() && !TextUtils.isEmpty(edLocation.getText())) {
                    mModel.setLatitude(mLat.toString().trim());
                    mModel.setLongitude(mLong.toString().trim());
                } else {
                    mModel.setLatitude("");
                    mModel.setLongitude("");
                    mModel.setDelivery_type("");
                    mModel.setAddress("");
                }

                if (scDelivery.isChecked() && TextUtils.isEmpty(edLocation.getText())) {
                    showAlert(llDelivery, getString(R.string.enter_delivery_address));
                    return;
                }
//                Toast.makeText(mContext, getString(R.string.Work_in_progress), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SaleCheckoutActivity.class);
                intent.putExtra("data", mModel);
                startActivity(intent);


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == LOCATION) {
                mLat = Double.parseDouble(data.getStringExtra("latitude"));
                mLong = Double.parseDouble(data.getStringExtra("longitude"));

                edLocation.setText(data.getStringExtra("address").replace("null,", ""));

                getAddress(mLat, mLong);
                getDeliveryType();
                llDeliveryCharges.setVisibility(View.VISIBLE);
            }
        }
    }

    void getAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getSubLocality() + ", " + addresses.get(0).getSubAdminArea();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            String s = address + ", " + city + ", " + state + ", " + country + ".";

        }
    }

    void getDeliveryType() {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        String country = "";
        String countryTul = "";

        try {
            addresses = geocoder.getFromLocation(mLat, mLong, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            country = addresses.get(0).getCountryName();
        }

        Geocoder geocoderTul;
        List<Address> addressesTul = null;
        geocoderTul = new Geocoder(this, Locale.getDefault());

        try {
            addressesTul = geocoderTul.getFromLocation(Double.parseDouble(mViewTulModel.getLatitude()),
                    Double.parseDouble(mViewTulModel.getLongitude()), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressesTul != null && addressesTul.size() > 0) {
            countryTul = addressesTul.get(0).getCountryName();
        }

        if (country.equals(countryTul)) {
            txtDeliveryType.setText("(" + getString(R.string.in_your_country) + ")");
            isNational = true;
        } else {
            txtDeliveryType.setText("(" + getString(R.string.internationa) + ")");
            isNational = false;
        }
        calculateDeliveryCharges();
    }


    float getDistance() {
        try {
            Location locationA = new Location("point A");

            locationA.setLatitude(mLat);
            locationA.setLongitude(mLong);

            Location locationB = new Location("point B");

            locationB.setLatitude(Double.parseDouble(mViewTulModel.getLatitude()));
            locationB.setLongitude(Double.parseDouble(mViewTulModel.getLongitude()));

            // 1meter ==0.000621371miles
            // 1meter ==0.001 km
            if (isMiles) {
                return Float.parseFloat(String.format("%.2f", (locationA.distanceTo(locationB) * 0.000621371)));
            } else {
                return Float.parseFloat(String.format("%.2f", (locationA.distanceTo(locationB) * 0.001)));
            }


        } catch (Exception e) {
            return 0;
        }

    }

}
