package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 14/9/18.
 */

public class SalesHistoryTulDetailModel extends BaseModel implements Parcelable {

    public static final Parcelable.Creator<SalesHistoryTulDetailModel> CREATOR = new Parcelable.Creator<SalesHistoryTulDetailModel>() {
        @Override
        public SalesHistoryTulDetailModel createFromParcel(Parcel source) {
            return new SalesHistoryTulDetailModel(source);
        }

        @Override
        public SalesHistoryTulDetailModel[] newArray(int size) {
            return new SalesHistoryTulDetailModel[size];
        }
    };
    /**
     * response : [{"id":76,"user_id":308,"tool_id":31,"delivery":0,"delivery_type":0,"delivery_cost":"","converted_delivery_cost":"0.0","primary_currency":"GBP","price":"122.00","total_amount":"122.00","payment_mode":1,"address":"","latitude":"","longitude":"","quantity":"1","payment_status":"0","order_status":"1","converted_amount":"122.0","order_id":"LKO8LLLF2R","created_at":"2018-09-10T13:05:04.000Z","updated_at":"2018-09-10T13:12:33.000Z","transaction_fee":"12.2","seller_rating":0,"buyer_rating":0,"converted_transaction_fee":"12.2","currency":"£","title":"Bsbs","attachment":[{"id":57,"tool_id":31,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15360666090.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1536066609_thumb0.jpg","type":"image"}],"owner_id":280,"owner":"Jasjeet ","first_name":"Jasjeet","last_name":"","owner_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536066124.jpg","buyer_id":308,"buyer":"Shubham 143","buyer_first_name":"Shubham","buyer_last_name":"143","buyer_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536579263.jpg"},{"id":75,"user_id":308,"tool_id":31,"delivery":0,"delivery_type":0,"delivery_cost":"0.00","converted_delivery_cost":"0.0","primary_currency":"GBP","price":"122.00","total_amount":"122.00","payment_mode":1,"address":"Gurudwara Sahib Road, Sector 82, JLPL Industrial Area, Punjab 140308, India,JLPL Industrial Area , Sahibzada Ajit Singh Nagar,Punjab,India","latitude":"30.6577767","longitude":"76.7327188","quantity":"1","payment_status":"0","order_status":"1","converted_amount":"122.0","order_id":"2GCLUNPA31","created_at":"2018-09-10T12:52:50.000Z","updated_at":"2018-09-10T12:56:35.000Z","transaction_fee":"12.2","seller_rating":0,"buyer_rating":0,"converted_transaction_fee":"12.2","currency":"£","title":"Bsbs","attachment":[{"id":57,"tool_id":31,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15360666090.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1536066609_thumb0.jpg","type":"image"}],"owner_id":280,"owner":"Jasjeet ","first_name":"Jasjeet","last_name":"","owner_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536066124.jpg","buyer_id":308,"buyer":"Shubham 143","buyer_first_name":"Shubham","buyer_last_name":"143","buyer_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536579263.jpg"}]
     * code : 111
     */

    private int code;
    private List<ResponseBean> response;

    public SalesHistoryTulDetailModel() {
    }

    protected SalesHistoryTulDetailModel(Parcel in) {
        this.code = in.readInt();
        this.response = new ArrayList<ResponseBean>();
        in.readList(this.response, ResponseBean.class.getClassLoader());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeList(this.response);
    }

    public static class ResponseBean implements Parcelable {

        /**
         * id : 76
         * user_id : 308
         * tool_id : 31
         * delivery : 0
         * delivery_type : 0
         * delivery_cost :
         * converted_delivery_cost : 0.0
         * primary_currency : GBP
         * price : 122.00
         * total_amount : 122.00
         * payment_mode : 1
         * address :
         * latitude :
         * longitude :
         * quantity : 1
         * payment_status : 0
         * order_status : 1
         * converted_amount : 122.0
         * order_id : LKO8LLLF2R
         * created_at : 2018-09-10T13:05:04.000Z
         * updated_at : 2018-09-10T13:12:33.000Z
         * transaction_fee : 12.2
         * seller_rating : 0
         * buyer_rating : 0
         * converted_transaction_fee : 12.2
         * currency : £
         * title : Bsbs
         * attachment : [{"id":57,"tool_id":31,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15360666090.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1536066609_thumb0.jpg","type":"image"}]
         * owner_id : 280
         * owner : Jasjeet
         * first_name : Jasjeet
         * "country_code": "+91",
         * "phone_number": "9041433279",
         * "owner_pic": "https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536066124.jpg",
         * "buyer_id": 312,
         * "buyer": "Shubham Verma",
         * "buyer_first_name": "Shubham",
         * "buyer_last_name": "Verma",
         * "buyer_country_code": "+91",
         * "buyer_phone_number": "7837350186",
         * "buyer_pic : https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536579263.jpg"
         */

        private int id;
        private int user_id;
        private int tool_id;
        private int delivery;
        private int delivery_type;
        private String delivery_cost;
        private String converted_delivery_cost;
        private String primary_currency;
        private String price;
        private String total_amount;
        private int payment_mode;
        private String address;
        private String latitude;
        private String longitude;
        private String quantity;
        private String payment_status;
        private String order_status;
        private String converted_amount;
        private String order_id;
        private String created_at;
        private String updated_at;
        private String transaction_fee;
        private int seller_rating;
        private int buyer_rating;
        private String converted_transaction_fee;
        private String currency;
        private String title;
        private int owner_id;
        private String owner;
        private String first_name;
        private String last_name;
        private String owner_pic;
        private int buyer_id;
        private String buyer;
        private String buyer_first_name;
        private String buyer_last_name;
        private String buyer_pic;
        private String buyer_country_code;
        private String buyer_phone_number;
        private String country_code;
        private String phone_number;
        private List<AttachmentModel> attachment;

        public ResponseBean() {
        }

        public String getBuyer_country_code() {
            return buyer_country_code;
        }

        public void setBuyer_country_code(String buyer_country_code) {
            this.buyer_country_code = buyer_country_code;
        }

        public String getBuyer_phone_number() {
            return buyer_phone_number;
        }

        public void setBuyer_phone_number(String buyer_phone_number) {
            this.buyer_phone_number = buyer_phone_number;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getTool_id() {
            return tool_id;
        }

        public void setTool_id(int tool_id) {
            this.tool_id = tool_id;
        }

        public int getDelivery() {
            return delivery;
        }

        public void setDelivery(int delivery) {
            this.delivery = delivery;
        }

        public int getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(int delivery_type) {
            this.delivery_type = delivery_type;
        }

        public String getDelivery_cost() {
            return delivery_cost;
        }

        public void setDelivery_cost(String delivery_cost) {
            this.delivery_cost = delivery_cost;
        }

        public String getConverted_delivery_cost() {
            return converted_delivery_cost;
        }

        public void setConverted_delivery_cost(String converted_delivery_cost) {
            this.converted_delivery_cost = converted_delivery_cost;
        }

        public String getPrimary_currency() {
            return primary_currency;
        }

        public void setPrimary_currency(String primary_currency) {
            this.primary_currency = primary_currency;
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

        public int getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(int payment_mode) {
            this.payment_mode = payment_mode;
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

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getConverted_amount() {
            return converted_amount;
        }

        public void setConverted_amount(String converted_amount) {
            this.converted_amount = converted_amount;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getTransaction_fee() {
            return transaction_fee;
        }

        public void setTransaction_fee(String transaction_fee) {
            this.transaction_fee = transaction_fee;
        }

        public int getSeller_rating() {
            return seller_rating;
        }

        public void setSeller_rating(int seller_rating) {
            this.seller_rating = seller_rating;
        }

        public int getBuyer_rating() {
            return buyer_rating;
        }

        public void setBuyer_rating(int buyer_rating) {
            this.buyer_rating = buyer_rating;
        }

        public String getConverted_transaction_fee() {
            return converted_transaction_fee;
        }

        public void setConverted_transaction_fee(String converted_transaction_fee) {
            this.converted_transaction_fee = converted_transaction_fee;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(int owner_id) {
            this.owner_id = owner_id;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getOwner_pic() {
            return owner_pic;
        }

        public void setOwner_pic(String owner_pic) {
            this.owner_pic = owner_pic;
        }

        public int getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(int buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getBuyer() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer = buyer;
        }

        public String getBuyer_first_name() {
            return buyer_first_name;
        }

        public void setBuyer_first_name(String buyer_first_name) {
            this.buyer_first_name = buyer_first_name;
        }

        public String getBuyer_last_name() {
            return buyer_last_name;
        }

        public void setBuyer_last_name(String buyer_last_name) {
            this.buyer_last_name = buyer_last_name;
        }

        public String getBuyer_pic() {
            return buyer_pic;
        }

        public void setBuyer_pic(String buyer_pic) {
            this.buyer_pic = buyer_pic;
        }

        public List<AttachmentModel> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<AttachmentModel> attachment) {
            this.attachment = attachment;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.user_id);
            dest.writeInt(this.tool_id);
            dest.writeInt(this.delivery);
            dest.writeInt(this.delivery_type);
            dest.writeString(this.delivery_cost);
            dest.writeString(this.converted_delivery_cost);
            dest.writeString(this.primary_currency);
            dest.writeString(this.price);
            dest.writeString(this.total_amount);
            dest.writeInt(this.payment_mode);
            dest.writeString(this.address);
            dest.writeString(this.latitude);
            dest.writeString(this.longitude);
            dest.writeString(this.quantity);
            dest.writeString(this.payment_status);
            dest.writeString(this.order_status);
            dest.writeString(this.converted_amount);
            dest.writeString(this.order_id);
            dest.writeString(this.created_at);
            dest.writeString(this.updated_at);
            dest.writeString(this.transaction_fee);
            dest.writeInt(this.seller_rating);
            dest.writeInt(this.buyer_rating);
            dest.writeString(this.converted_transaction_fee);
            dest.writeString(this.currency);
            dest.writeString(this.title);
            dest.writeInt(this.owner_id);
            dest.writeString(this.owner);
            dest.writeString(this.first_name);
            dest.writeString(this.last_name);
            dest.writeString(this.owner_pic);
            dest.writeInt(this.buyer_id);
            dest.writeString(this.buyer);
            dest.writeString(this.buyer_first_name);
            dest.writeString(this.buyer_last_name);
            dest.writeString(this.buyer_pic);
            dest.writeString(this.buyer_country_code);
            dest.writeString(this.buyer_phone_number);
            dest.writeString(this.country_code);
            dest.writeString(this.phone_number);
            dest.writeTypedList(this.attachment);
        }

        protected ResponseBean(Parcel in) {
            this.id = in.readInt();
            this.user_id = in.readInt();
            this.tool_id = in.readInt();
            this.delivery = in.readInt();
            this.delivery_type = in.readInt();
            this.delivery_cost = in.readString();
            this.converted_delivery_cost = in.readString();
            this.primary_currency = in.readString();
            this.price = in.readString();
            this.total_amount = in.readString();
            this.payment_mode = in.readInt();
            this.address = in.readString();
            this.latitude = in.readString();
            this.longitude = in.readString();
            this.quantity = in.readString();
            this.payment_status = in.readString();
            this.order_status = in.readString();
            this.converted_amount = in.readString();
            this.order_id = in.readString();
            this.created_at = in.readString();
            this.updated_at = in.readString();
            this.transaction_fee = in.readString();
            this.seller_rating = in.readInt();
            this.buyer_rating = in.readInt();
            this.converted_transaction_fee = in.readString();
            this.currency = in.readString();
            this.title = in.readString();
            this.owner_id = in.readInt();
            this.owner = in.readString();
            this.first_name = in.readString();
            this.last_name = in.readString();
            this.owner_pic = in.readString();
            this.buyer_id = in.readInt();
            this.buyer = in.readString();
            this.buyer_first_name = in.readString();
            this.buyer_last_name = in.readString();
            this.buyer_pic = in.readString();
            this.buyer_country_code = in.readString();
            this.buyer_phone_number = in.readString();
            this.country_code = in.readString();
            this.phone_number = in.readString();
            this.attachment = in.createTypedArrayList(AttachmentModel.CREATOR);
        }

        public static final Creator<ResponseBean> CREATOR = new Creator<ResponseBean>() {
            @Override
            public ResponseBean createFromParcel(Parcel source) {
                return new ResponseBean(source);
            }

            @Override
            public ResponseBean[] newArray(int size) {
                return new ResponseBean[size];
            }
        };
    }
}
