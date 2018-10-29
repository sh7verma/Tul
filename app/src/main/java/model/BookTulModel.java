package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by applify on 11/17/2017.
 */

public class BookTulModel extends BaseModel {


    /**
     * response : {"tool_id":20,"user_id":20,"owner":"Naina Lalla","first_name":"Naina","last_name":"Lalla","owner_pic":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1510143091.jpeg","booking_id":13,"lender_status":0,"borrower_status":0,"title":"Ababa Sbs sbs S Jab. Sjsn Sbs. Sjsn s ","additional_charges":"{\"security_charges\":\"94\",\"fee\":\"94\"}","total_amount":"252","delivery_cost":0,"price":"64","quantity":1,"currency":"£","delivery_date":"2017-11-18T00:00:00.000Z","return_date":"2017-11-18T00:00:00.000Z","selected_date":"18-11-2017","attachment":[{"id":24,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1510203733_thumb0.jpeg","tool_id":20,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15102037330.jpeg","type":"image"}],"type":1}
     * card_details : {"id":61,"user_id":53,"card_number":"4242 4242 4242 4242","name_on_card":"Rajat","expiry_month":1,"expiry_year":2019,"created_at":"2017-11-17T12:59:41.000Z","updated_at":"2017-11-17T12:59:41.000Z"}
     * code : 111
     */

    private ResponseBean response;
    private CardLocalModel.ResponseBean card_details;
    private int code;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public CardLocalModel.ResponseBean getCard_details() {
        return card_details;
    }

    public void setCard_details(CardLocalModel.ResponseBean card_details) {
        this.card_details = card_details;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResponseBean implements Parcelable {
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
        /**
         * tool_id : 89
         * user_id : 63
         * owner : Hunny Goyal
         * first_name : Hunny
         * last_name : Goyal
         * owner_pic : https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1511343089.jpg
         * phone_number : 9888454536
         * country_code : +44
         * address : Unnamed Road, Sector 82, JLPL Industrial Area, Punjab 140308, India,JLPL Industrial Area , Sahibzada Ajit Singh Nagar,Punjab,India
         * lender_status : 2
         * borrower_status : 2
         * delivery_cost : 0
         * price : 25
         * currency : £
         * delivery_date : 2017-12-02 00:00
         * return_date : 2017-12-03 00:00
         * selected_date : 02-12-2017,03-12-2017
         * booked_at : 2017-11-24 04:53
         * handover_at : 2017-11-24 04:56
         * lander_received_at :
         * borrower_received_at : 2017-11-24 04:55
         * borrower_returned_at : 2017-11-24 04:55
         * cancelled_at :
         * booking_id : 67
         * title : Test
         * additional_charges : {"security_charges":"2","fee":"1"}
         * additional_price : {"security_charges":"2","fee":"1"}
         * total_amount : 53
         * attachment : [{"id":98,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1511499023_thumb0.jpg","tool_id":89,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15114990230.jpg","type":"image"}]
         * user_type : 2
         * refund_status : 0
         * rating : 0
         */

        private int tool_id;
        private int user_id;
        private int borrower_id;
        private String owner;
        private String category_name;
        private String first_name;
        private String last_name;
        private String owner_pic;
        private String phone_number;
        private String country_code;
        private String address;
        private int lender_status;
        private int borrower_status;
        private String borrower;
        private String borrower_pic;
        private String delivery_cost;
        private String price;
        private String base_price;
        private String discount;
        private String discount_percentage;
        private String currency;
        private String delivery_date;
        private String return_date;
        private String selected_date;
        private String booked_at;
        private String handover_at;
        private String lander_received_at;
        private String borrower_received_at;
        private String borrower_returned_at;
        private String cancelled_at;
        private int booking_id;
        private String title;
        private AdditionalChargesBean additional_charges;
        private AdditionalPriceBean additional_price;
        private String total_amount;
        private int user_type;
        private int quantity;
        private int refund_status;
        private int rating;
        private String cancel_percentage;
        private String transaction_fee;
        private String primary_currency;
        private int extra_charges;
        private String transaction_percentage;
        private int type;
        private List<AttachmentModel> attachment;

        public ResponseBean() {
        }

        protected ResponseBean(Parcel in) {
            this.tool_id = in.readInt();
            this.user_id = in.readInt();
            this.borrower_id = in.readInt();
            this.owner = in.readString();
            this.category_name = in.readString();
            this.first_name = in.readString();
            this.last_name = in.readString();
            this.owner_pic = in.readString();
            this.phone_number = in.readString();
            this.country_code = in.readString();
            this.address = in.readString();
            this.lender_status = in.readInt();
            this.borrower_status = in.readInt();
            this.borrower = in.readString();
            this.borrower_pic = in.readString();
            this.delivery_cost = in.readString();
            this.price = in.readString();
            this.base_price = in.readString();
            this.discount = in.readString();
            this.discount_percentage = in.readString();
            this.currency = in.readString();
            this.delivery_date = in.readString();
            this.return_date = in.readString();
            this.selected_date = in.readString();
            this.booked_at = in.readString();
            this.handover_at = in.readString();
            this.lander_received_at = in.readString();
            this.borrower_received_at = in.readString();
            this.borrower_returned_at = in.readString();
            this.cancelled_at = in.readString();
            this.booking_id = in.readInt();
            this.title = in.readString();
            this.additional_charges = in.readParcelable(AdditionalChargesBean.class.getClassLoader());
            this.additional_price = in.readParcelable(AdditionalPriceBean.class.getClassLoader());
            this.total_amount = in.readString();
            this.user_type = in.readInt();
            this.quantity = in.readInt();
            this.refund_status = in.readInt();
            this.rating = in.readInt();
            this.cancel_percentage = in.readString();
            this.transaction_fee = in.readString();
            this.primary_currency = in.readString();
            this.extra_charges = in.readInt();
            this.transaction_percentage = in.readString();
            this.type = in.readInt();
            this.attachment = in.createTypedArrayList(AttachmentModel.CREATOR);
        }

        public String getPrimary_currency() {
            return primary_currency;
        }

        public void setPrimary_currency(String primary_currency) {
            this.primary_currency = primary_currency;
        }

        public int getTool_id() {
            return tool_id;
        }

        public void setTool_id(int tool_id) {
            this.tool_id = tool_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getBorrower_id() {
            return borrower_id;
        }

        public void setBorrower_id(int borrower_id) {
            this.borrower_id = borrower_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
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

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getLender_status() {
            return lender_status;
        }

        public void setLender_status(int lender_status) {
            this.lender_status = lender_status;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getBorrower_status() {
            return borrower_status;
        }

        public void setBorrower_status(int borrower_status) {
            this.borrower_status = borrower_status;
        }

        public String getBorrower() {
            return borrower;
        }

        public void setBorrower(String borrower) {
            this.borrower = borrower;
        }

        public String getBorrower_pic() {
            return borrower_pic;
        }

        public void setBorrower_pic(String borrower_pic) {
            this.borrower_pic = borrower_pic;
        }

        public String getCancel_percentage() {
            return cancel_percentage;
        }

        public void setCancel_percentage(String cancel_percentage) {
            this.cancel_percentage = cancel_percentage;
        }

        public String getTransaction_fee() {
            return transaction_fee;
        }

        public void setTransaction_fee(String transaction_fee) {
            this.transaction_fee = transaction_fee;
        }

        public String getTransaction_percentage() {
            return transaction_percentage;
        }

        public void setTransaction_percentage(String transaction_percentage) {
            this.transaction_percentage = transaction_percentage;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscount_percentage() {
            return discount_percentage;
        }

        public void setDiscount_percentage(String discount_percentage) {
            this.discount_percentage = discount_percentage;
        }

        public int getExtra_charges() {
            return extra_charges;
        }

        public void setExtra_charges(int extra_charges) {
            this.extra_charges = extra_charges;
        }

        public String getDelivery_cost() {
            return delivery_cost;
        }

        public void setDelivery_cost(String delivery_cost) {
            this.delivery_cost = delivery_cost;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBase_price() {
            return base_price;
        }

        public void setBase_price(String base_price) {
            this.base_price = base_price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDelivery_date() {
            return delivery_date;
        }

        public void setDelivery_date(String delivery_date) {
            this.delivery_date = delivery_date;
        }

        public String getReturn_date() {
            return return_date;
        }

        public void setReturn_date(String return_date) {
            this.return_date = return_date;
        }

        public String getSelected_date() {
            return selected_date;
        }

        public void setSelected_date(String selected_date) {
            this.selected_date = selected_date;
        }

        public String getBooked_at() {
            return booked_at;
        }

        public void setBooked_at(String booked_at) {
            this.booked_at = booked_at;
        }

        public String getHandover_at() {
            return handover_at;
        }

        public void setHandover_at(String handover_at) {
            this.handover_at = handover_at;
        }

        public String getLander_received_at() {
            return lander_received_at;
        }

        public void setLander_received_at(String lander_received_at) {
            this.lander_received_at = lander_received_at;
        }

        public String getBorrower_received_at() {
            return borrower_received_at;
        }

        public void setBorrower_received_at(String borrower_received_at) {
            this.borrower_received_at = borrower_received_at;
        }

        public String getBorrower_returned_at() {
            return borrower_returned_at;
        }

        public void setBorrower_returned_at(String borrower_returned_at) {
            this.borrower_returned_at = borrower_returned_at;
        }

        public String getCancelled_at() {
            return cancelled_at;
        }

        public void setCancelled_at(String cancelled_at) {
            this.cancelled_at = cancelled_at;
        }

        public int getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(int booking_id) {
            this.booking_id = booking_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public BookTulModel.ResponseBean.AdditionalChargesBean getAdditional_charges() {
            return additional_charges;
        }

        public void setAdditional_charges(BookTulModel.ResponseBean.AdditionalChargesBean additional_charges) {
            this.additional_charges = additional_charges;
        }

        public BookTulModel.ResponseBean.AdditionalPriceBean getAdditional_price() {
            return additional_price;
        }

        public void setAdditional_price(BookTulModel.ResponseBean.AdditionalPriceBean additional_price) {
            this.additional_price = additional_price;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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
            dest.writeInt(this.tool_id);
            dest.writeInt(this.user_id);
            dest.writeInt(this.borrower_id);
            dest.writeString(this.owner);
            dest.writeString(this.category_name);
            dest.writeString(this.first_name);
            dest.writeString(this.last_name);
            dest.writeString(this.owner_pic);
            dest.writeString(this.phone_number);
            dest.writeString(this.country_code);
            dest.writeString(this.address);
            dest.writeInt(this.lender_status);
            dest.writeInt(this.borrower_status);
            dest.writeString(this.borrower);
            dest.writeString(this.borrower_pic);
            dest.writeString(this.delivery_cost);
            dest.writeString(this.price);
            dest.writeString(this.base_price);
            dest.writeString(this.discount);
            dest.writeString(this.discount_percentage);
            dest.writeString(this.currency);
            dest.writeString(this.delivery_date);
            dest.writeString(this.return_date);
            dest.writeString(this.selected_date);
            dest.writeString(this.booked_at);
            dest.writeString(this.handover_at);
            dest.writeString(this.lander_received_at);
            dest.writeString(this.borrower_received_at);
            dest.writeString(this.borrower_returned_at);
            dest.writeString(this.cancelled_at);
            dest.writeInt(this.booking_id);
            dest.writeString(this.title);
            dest.writeParcelable(this.additional_charges, flags);
            dest.writeParcelable(this.additional_price, flags);
            dest.writeString(this.total_amount);
            dest.writeInt(this.user_type);
            dest.writeInt(this.quantity);
            dest.writeInt(this.refund_status);
            dest.writeInt(this.rating);
            dest.writeString(this.cancel_percentage);
            dest.writeString(this.transaction_fee);
            dest.writeString(this.primary_currency);
            dest.writeInt(this.extra_charges);
            dest.writeString(this.transaction_percentage);
            dest.writeInt(this.type);
            dest.writeTypedList(this.attachment);
        }

        public static class AdditionalChargesBean implements Parcelable {
            public static final Creator<AdditionalChargesBean> CREATOR = new Creator<AdditionalChargesBean>() {
                @Override
                public AdditionalChargesBean createFromParcel(Parcel source) {
                    return new AdditionalChargesBean(source);
                }

                @Override
                public AdditionalChargesBean[] newArray(int size) {
                    return new AdditionalChargesBean[size];
                }
            };
            /**
             * security_charges : 2
             * fee : 1
             */

            private String security_charges;
            private String fee;

            public AdditionalChargesBean() {
            }

            protected AdditionalChargesBean(Parcel in) {
                this.security_charges = in.readString();
                this.fee = in.readString();
            }

            public String getSecurity_charges() {
                return security_charges;
            }

            public void setSecurity_charges(String security_charges) {
                this.security_charges = security_charges;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.security_charges);
                dest.writeString(this.fee);
            }
        }

        public static class AdditionalPriceBean implements Parcelable {
            public static final Creator<AdditionalPriceBean> CREATOR = new Creator<AdditionalPriceBean>() {
                @Override
                public AdditionalPriceBean createFromParcel(Parcel source) {
                    return new AdditionalPriceBean(source);
                }

                @Override
                public AdditionalPriceBean[] newArray(int size) {
                    return new AdditionalPriceBean[size];
                }
            };
            /**
             * security_charges : 2
             * fee : 1
             */

            private String security_charges;
            private String fee;
            private String add_currency;

            public AdditionalPriceBean() {
            }

            protected AdditionalPriceBean(Parcel in) {
                this.security_charges = in.readString();
                this.fee = in.readString();
                this.add_currency = in.readString();
            }

            public String getAdd_currency() {
                return add_currency;
            }

            public void setAdd_currency(String add_currency) {
                this.add_currency = add_currency;
            }

            public String getSecurity_charges() {
                return security_charges;
            }

            public void setSecurity_charges(String security_charges) {
                this.security_charges = security_charges;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.security_charges);
                dest.writeString(this.fee);
                dest.writeString(this.add_currency);
            }
        }
    }

}
