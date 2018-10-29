package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dev on 6/9/18.
 */

public class SalesDeliveryDetailModel implements Parcelable {

    int id;
    String title;
    int quantity;
    int edit_count;
    String delivery;
    String delivery_type;
    String payment_mode;
    String delivery_cost;
    String card_number;
    String name_on_card;
    String expiry_month;
    String expiry_year;
    String price;
    String total_amount;
    String address;
    String latitude;
    String longitude;
    String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    String source_token;
    String primary_currency;
    String price_calculate;
    String delivery_charges_calculate;

    public SalesDeliveryDetailModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice_calculate() {
        return price_calculate;
    }

    public void setPrice_calculate(String price_calculate) {
        this.price_calculate = price_calculate;
    }

    public String getDelivery_charges_calculate() {
        return delivery_charges_calculate;
    }

    public void setDelivery_charges_calculate(String delivery_charges_calculate) {
        this.delivery_charges_calculate = delivery_charges_calculate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getEdit_count() {
        return edit_count;
    }

    public void setEdit_count(int edit_count) {
        this.edit_count = edit_count;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(String delivery_cost) {
        this.delivery_cost = delivery_cost;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getName_on_card() {
        return name_on_card;
    }

    public void setName_on_card(String name_on_card) {
        this.name_on_card = name_on_card;
    }

    public String getExpiry_month() {
        return expiry_month;
    }

    public void setExpiry_month(String expiry_month) {
        this.expiry_month = expiry_month;
    }

    public String getExpiry_year() {
        return expiry_year;
    }

    public void setExpiry_year(String expiry_year) {
        this.expiry_year = expiry_year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSource_token() {
        return source_token;
    }

    public void setSource_token(String source_token) {
        this.source_token = source_token;
    }

    public String getPrimary_currency() {
        return primary_currency;
    }

    public void setPrimary_currency(String primary_currency) {
        this.primary_currency = primary_currency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeInt(this.quantity);
        dest.writeInt(this.edit_count);
        dest.writeString(this.delivery);
        dest.writeString(this.delivery_type);
        dest.writeString(this.payment_mode);
        dest.writeString(this.delivery_cost);
        dest.writeString(this.card_number);
        dest.writeString(this.name_on_card);
        dest.writeString(this.expiry_month);
        dest.writeString(this.expiry_year);
        dest.writeString(this.price);
        dest.writeString(this.total_amount);
        dest.writeString(this.address);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.currency);
        dest.writeString(this.source_token);
        dest.writeString(this.primary_currency);
        dest.writeString(this.price_calculate);
        dest.writeString(this.delivery_charges_calculate);
    }

    protected SalesDeliveryDetailModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.quantity = in.readInt();
        this.edit_count = in.readInt();
        this.delivery = in.readString();
        this.delivery_type = in.readString();
        this.payment_mode = in.readString();
        this.delivery_cost = in.readString();
        this.card_number = in.readString();
        this.name_on_card = in.readString();
        this.expiry_month = in.readString();
        this.expiry_year = in.readString();
        this.price = in.readString();
        this.total_amount = in.readString();
        this.address = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.currency = in.readString();
        this.source_token = in.readString();
        this.primary_currency = in.readString();
        this.price_calculate = in.readString();
        this.delivery_charges_calculate = in.readString();
    }

    public static final Creator<SalesDeliveryDetailModel> CREATOR = new Creator<SalesDeliveryDetailModel>() {
        @Override
        public SalesDeliveryDetailModel createFromParcel(Parcel source) {
            return new SalesDeliveryDetailModel(source);
        }

        @Override
        public SalesDeliveryDetailModel[] newArray(int size) {
            return new SalesDeliveryDetailModel[size];
        }
    };
}
