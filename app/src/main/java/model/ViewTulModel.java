package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by applify on 10/18/2017.
 */

public class ViewTulModel extends BaseModel implements Parcelable {


    public static final Parcelable.Creator<ViewTulModel> CREATOR = new Parcelable.Creator<ViewTulModel>() {
        @Override
        public ViewTulModel createFromParcel(Parcel source) {
            return new ViewTulModel(source);
        }

        @Override
        public ViewTulModel[] newArray(int size) {
            return new ViewTulModel[size];
        }
    };
    /**
     * response : {"id":78,"title":"Dhhdh","user_id":141,"owner":"Rajat Arora","category_id":3,"description":"Bxbxnxdjjxnxn","quantity":686,"rented":0,"price":"799","add_currency":"£","additional_price":{"security_charges":"869","fee":"869"},"address":"Unnamed Road,JLPL Industrial Area , Sahibzada Ajit Singh Nagar,Punjab,India","latitude":"30.6577903","longitude":"76.732739","rules":["Test"],"preferences":{"available":"Only Weekends","start_time":"10:33 AM","end_time":"10:33 AM","tull_delivery":"0","delivery_charges":"","delivery_start_time":"","delivery_end_time":""},"attachment":[{"id":55,"tool_id":78,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/15083031560.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508303156_thumb0.jpg","type":"image"},{"id":56,"tool_id":78,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/15083031571.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508303157_thumb1.jpg","type":"image"},{"id":57,"tool_id":78,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508303157.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508303157_thumb.jpg","type":"video"}],"pause_status":"0","bank_detail":{"id":42,"user_id":141,"tool_id":78,"bank_name":"STRIPE TEST BANK","account_holder_name":"Zggxhz Zhzhhzusyzg","dob":"18-10-2004","strip_account":"acct_1BE9jyLMRqYmMKu3","account_number":"00012345","country_code":"GB","add_currency":"GBP","address":"Ztgzgs","city":"Sgzggsg","state":"Shhzhs","postal_code":"Ca103","sort_code":"108800"},"rating":0}
     * code : 401
     */

    private ResponseBean response;
    private int code;

    public ViewTulModel() {
    }

    protected ViewTulModel(Parcel in) {
        this.response = in.readParcelable(ResponseBean.class.getClassLoader());
        this.code = in.readInt();
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.response, flags);
        dest.writeInt(this.code);
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
         * id : 78
         * title : Dhhdh
         * user_id : 141
         * owner : Rajat Arora
         * category_id : 3
         * description : Bxbxnxdjjxnxn
         * quantity : 686
         * rented : 0
         * price : 799
         * add_currency : £
         * additional_price : {"security_charges":"869","fee":"869"}
         * address : Unnamed Road,JLPL Industrial Area , Sahibzada Ajit Singh Nagar,Punjab,India
         * latitude : 30.6577903
         * longitude : 76.732739
         * rules : ["Test"]
         * preferences : {"available":"Only Weekends","start_time":"10:33 AM","end_time":"10:33 AM","tull_delivery":"0","delivery_charges":"","delivery_start_time":"","delivery_end_time":""}
         * attachment : [{"id":55,"tool_id":78,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/15083031560.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508303156_thumb0.jpg","type":"image"},{"id":56,"tool_id":78,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/15083031571.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508303157_thumb1.jpg","type":"image"},{"id":57,"tool_id":78,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508303157.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508303157_thumb.jpg","type":"video"}]
         * pause_status : 0
         * bank_detail : {"id":42,"user_id":141,"tool_id":78,"bank_name":"STRIPE TEST BANK","account_holder_name":"Zggxhz Zhzhhzusyzg","dob":"18-10-2004","strip_account":"acct_1BE9jyLMRqYmMKu3","account_number":"00012345","country_code":"GB","add_currency":"GBP","address":"Ztgzgs","city":"Sgzggsg","state":"Shhzhs","postal_code":"Ca103","sort_code":"108800"}
         * rating : 0
         */
        private int id;
        private String title;
        private int user_id;
        private String owner;
        private String currency;
        private String owner_pic;
        private int category_id;
        private String category_name;
        private String description;
        private int quantity;
        private int rented;
        private String discount;
        private String discount_percentage;
        private int discount_days;
        private AdditionalPriceBean additional_price;
        private String price;
        private String base_price;
        private String address;
        private String booked_address;
        private String booked_latitude;
        private String booked_longitude;
        private String latitude;
        private String longitude;
        private PreferencesBean preferences;
        private String pause_status;
        private String borrower_pic;
        private String borrower;
        private BankDetailBean bank_detail;
        private int rating;
        private int is_wishlisted;
        private String total_earnings;
        private int total_booking;
        private int active_bookings;
        private int today_booking;
        private String delivery_date;
        private int delivery_type;
        private String return_date;
        private String country_code;
        private String phone_number;
        private String borrower_country_code;
        private String borrower_phone_number;
        private String access_token;
        private String device_token;
        private String platform_status;
        private String cancel_percentage;
        private String transaction_percentage;
        private List<String> rules;
        private List<AttachmentModel> attachment;
        private int admin_pause_status;
        private int borrower_id;
        private String borrower_platform_status;
        private String borrower_access_token;
        private String borrower_device_token;

        public ResponseBean() {
        }

        protected ResponseBean(Parcel in) {
            this.id = in.readInt();
            this.title = in.readString();
            this.user_id = in.readInt();
            this.owner = in.readString();
            this.owner_pic = in.readString();
            this.category_id = in.readInt();
            this.category_name = in.readString();
            this.description = in.readString();
            this.quantity = in.readInt();
            this.rented = in.readInt();
            this.discount = in.readString();
            this.discount_percentage = in.readString();
            this.discount_days = in.readInt();
            this.price = in.readString();
            this.base_price = in.readString();
            this.currency = in.readString();
            this.additional_price = in.readParcelable(AdditionalPriceBean.class.getClassLoader());
            this.address = in.readString();
            this.booked_address = in.readString();
            this.booked_latitude = in.readString();
            this.booked_longitude = in.readString();
            this.latitude = in.readString();
            this.longitude = in.readString();
            this.preferences = in.readParcelable(PreferencesBean.class.getClassLoader());
            this.pause_status = in.readString();
            this.borrower_pic = in.readString();
            this.borrower = in.readString();
            this.bank_detail = in.readParcelable(BankDetailBean.class.getClassLoader());
            this.rating = in.readInt();
            this.is_wishlisted = in.readInt();
            this.total_earnings = in.readString();
            this.total_booking = in.readInt();
            this.active_bookings = in.readInt();
            this.today_booking = in.readInt();
            this.delivery_date = in.readString();
            this.delivery_type = in.readInt();
            this.return_date = in.readString();
            this.country_code = in.readString();
            this.phone_number = in.readString();
            this.borrower_country_code = in.readString();
            this.borrower_phone_number = in.readString();
            this.access_token = in.readString();
            this.device_token = in.readString();
            this.platform_status = in.readString();
            this.cancel_percentage = in.readString();
            this.transaction_percentage = in.readString();
            this.rules = in.createStringArrayList();
            this.attachment = in.createTypedArrayList(AttachmentModel.CREATOR);
            this.admin_pause_status = in.readInt();
            this.borrower_id = in.readInt();
            this.borrower_platform_status = in.readString();
            this.borrower_access_token = in.readString();
            this.borrower_device_token = in.readString();
        }

        public int getIs_wishlisted() {
            return is_wishlisted;
        }

        public void setIs_wishlisted(int is_wishlisted) {
            this.is_wishlisted = is_wishlisted;
        }

        public int getAdmin_pause_status() {
            return admin_pause_status;
        }

        public void setAdmin_pause_status(int admin_pause_status) {
            this.admin_pause_status = admin_pause_status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getOwner_pic() {
            return owner_pic;
        }

        public void setOwner_pic(String owner_pic) {
            this.owner_pic = owner_pic;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getRented() {
            return rented;
        }

        public void setRented(int rented) {
            this.rented = rented;
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

        public int getDiscount_days() {
            return discount_days;
        }

        public void setDiscount_days(int discount_days) {
            this.discount_days = discount_days;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;

        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public int getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(int delivery_type) {
            this.delivery_type = delivery_type;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public AdditionalPriceBean getAdditional_price() {
            return additional_price;
        }

        public void setAdditional_price(AdditionalPriceBean additional_price) {
            this.additional_price = additional_price;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBooked_address() {
            return booked_address;
        }

        public void setBooked_address(String booked_address) {
            this.booked_address = booked_address;
        }

        public String getBooked_latitude() {
            return booked_latitude;
        }

        public void setBooked_latitude(String booked_latitude) {
            this.booked_latitude = booked_latitude;
        }

        public String getBooked_longitude() {
            return booked_longitude;
        }

        public void setBooked_longitude(String booked_longitude) {
            this.booked_longitude = booked_longitude;
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

        public PreferencesBean getPreferences() {
            return preferences;
        }

        public void setPreferences(PreferencesBean preferences) {
            this.preferences = preferences;
        }

        public String getPause_status() {
            return pause_status;
        }

        public void setPause_status(String pause_status) {
            this.pause_status = pause_status;
        }

        public BankDetailBean getBank_detail() {
            return bank_detail;
        }

        public void setBank_detail(BankDetailBean bank_detail) {
            this.bank_detail = bank_detail;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getTotal_earnings() {
            return total_earnings;
        }

        public void setTotal_earnings(String total_earnings) {
            this.total_earnings = total_earnings;
        }

        public int getTotal_booking() {
            return total_booking;
        }

        public void setTotal_booking(int total_booking) {
            this.total_booking = total_booking;
        }

        public int getToday_booking() {
            return today_booking;
        }

        public void setToday_booking(int today_booking) {
            this.today_booking = today_booking;
        }

        public int is_wishlisted() {
            return is_wishlisted;
        }

        public List<String> getRules() {
            return rules;
        }

        public void setRules(List<String> rules) {
            this.rules = rules;
        }

        public String getBorrower_pic() {
            return borrower_pic;
        }

        public void setBorrower_pic(String borrower_pic) {
            this.borrower_pic = borrower_pic;
        }

        public String getBorrower() {
            return borrower;
        }

        public void setBorrower(String borrower) {
            this.borrower = borrower;
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

        public String getBorrower_country_code() {
            return borrower_country_code;
        }

        public void setBorrower_country_code(String borrower_country_code) {
            this.borrower_country_code = borrower_country_code;
        }

        public int getActive_bookings() {
            return active_bookings;
        }

        public void setActive_bookings(int active_bookings) {
            this.active_bookings = active_bookings;
        }

        public String getBorrower_phone_number() {
            return borrower_phone_number;
        }

        public void setBorrower_phone_number(String borrower_phone_number) {
            this.borrower_phone_number = borrower_phone_number;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getPlatform_status() {
            return platform_status;
        }

        public void setPlatform_status(String platform_status) {
            this.platform_status = platform_status;
        }

        public int getBorrower_id() {
            return borrower_id;
        }

        public void setBorrower_id(int borrower_id) {
            this.borrower_id = borrower_id;
        }

        public String getBorrower_platform_status() {
            return borrower_platform_status;
        }

        public void setBorrower_platform_status(String borrower_platform_status) {
            this.borrower_platform_status = borrower_platform_status;
        }

        public String getBorrower_access_token() {
            return borrower_access_token;
        }

        public void setBorrower_access_token(String borrower_access_token) {
            this.borrower_access_token = borrower_access_token;
        }

        public String getBorrower_device_token() {
            return borrower_device_token;
        }

        public void setBorrower_device_token(String borrower_device_token) {
            this.borrower_device_token = borrower_device_token;
        }

        public String getCancel_percentage() {
            return cancel_percentage;
        }

        public void setCancel_percentage(String cancel_percentage) {
            this.cancel_percentage = cancel_percentage;
        }

        public String getTransaction_percentage() {
            return transaction_percentage;
        }

        public void setTransaction_percentage(String transaction_percentage) {
            this.transaction_percentage = transaction_percentage;
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
            dest.writeString(this.title);
            dest.writeInt(this.user_id);
            dest.writeString(this.owner);
            dest.writeString(this.owner_pic);
            dest.writeInt(this.category_id);
            dest.writeString(this.category_name);
            dest.writeString(this.description);
            dest.writeInt(this.quantity);
            dest.writeInt(this.rented);
            dest.writeString(this.discount);
            dest.writeString(this.discount_percentage);
            dest.writeInt(this.discount_days);
            dest.writeString(this.price);
            dest.writeString(this.base_price);
            dest.writeString(this.currency);
            dest.writeParcelable(this.additional_price, flags);
            dest.writeString(this.address);
            dest.writeString(this.booked_address);
            dest.writeString(this.booked_latitude);
            dest.writeString(this.booked_longitude);
            dest.writeString(this.latitude);
            dest.writeString(this.longitude);
            dest.writeParcelable(this.preferences, flags);
            dest.writeString(this.pause_status);
            dest.writeString(this.borrower_pic);
            dest.writeString(this.borrower);
            dest.writeParcelable(this.bank_detail, flags);
            dest.writeInt(this.rating);
            dest.writeInt(this.is_wishlisted);
            dest.writeString(this.total_earnings);
            dest.writeInt(this.total_booking);
            dest.writeInt(this.active_bookings);
            dest.writeInt(this.today_booking);
            dest.writeString(this.delivery_date);
            dest.writeInt(this.delivery_type);
            dest.writeString(this.return_date);
            dest.writeString(this.country_code);
            dest.writeString(this.phone_number);
            dest.writeString(this.borrower_country_code);
            dest.writeString(this.borrower_phone_number);
            dest.writeString(this.access_token);
            dest.writeString(this.device_token);
            dest.writeString(this.platform_status);
            dest.writeString(this.cancel_percentage);
            dest.writeString(this.transaction_percentage);
            dest.writeStringList(this.rules);
            dest.writeTypedList(this.attachment);
            dest.writeInt(this.admin_pause_status);
            dest.writeInt(this.borrower_id);
            dest.writeString(this.borrower_platform_status);
            dest.writeString(this.borrower_access_token);
            dest.writeString(this.borrower_device_token);
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
             * security_charges : 869
             * fee : 869
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

        public static class PreferencesBean implements Parcelable {
            public static final Creator<PreferencesBean> CREATOR = new Creator<PreferencesBean>() {
                @Override
                public PreferencesBean createFromParcel(Parcel source) {
                    return new PreferencesBean(source);
                }

                @Override
                public PreferencesBean[] newArray(int size) {
                    return new PreferencesBean[size];
                }
            };
            /**
             * available : Only Weekends
             * start_time : 10:33 AM
             * end_time : 10:33 AM
             * tull_delivery : 0
             * delivery_charges :
             * delivery_start_time :
             * delivery_end_time :
             */

            private String available;
            private String start_time;
            private String end_time;
            private String tull_delivery;
            private String delivery_charges;
            private String delivery_start_time;
            private String delivery_end_time;
            private String currency;

            public PreferencesBean() {
            }

            protected PreferencesBean(Parcel in) {
                this.available = in.readString();
                this.start_time = in.readString();
                this.end_time = in.readString();
                this.tull_delivery = in.readString();
                this.delivery_charges = in.readString();
                this.delivery_start_time = in.readString();
                this.delivery_end_time = in.readString();
                this.currency = in.readString();
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getAvailable() {
                return available;
            }

            public void setAvailable(String available) {
                this.available = available;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getTull_delivery() {
                return tull_delivery;
            }

            public void setTull_delivery(String tull_delivery) {
                this.tull_delivery = tull_delivery;
            }

            public String getDelivery_charges() {
                return delivery_charges;
            }

            public void setDelivery_charges(String delivery_charges) {
                this.delivery_charges = delivery_charges;
            }

            public String getDelivery_start_time() {
                return delivery_start_time;
            }

            public void setDelivery_start_time(String delivery_start_time) {
                this.delivery_start_time = delivery_start_time;
            }

            public String getDelivery_end_time() {
                return delivery_end_time;
            }

            public void setDelivery_end_time(String delivery_end_time) {
                this.delivery_end_time = delivery_end_time;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.available);
                dest.writeString(this.start_time);
                dest.writeString(this.end_time);
                dest.writeString(this.tull_delivery);
                dest.writeString(this.delivery_charges);
                dest.writeString(this.delivery_start_time);
                dest.writeString(this.delivery_end_time);
                dest.writeString(this.currency);
            }
        }

        public static class BankDetailBean implements Parcelable {
            public static final Creator<BankDetailBean> CREATOR = new Creator<BankDetailBean>() {
                @Override
                public BankDetailBean createFromParcel(Parcel source) {
                    return new BankDetailBean(source);
                }

                @Override
                public BankDetailBean[] newArray(int size) {
                    return new BankDetailBean[size];
                }
            };
            /**
             * id : 42
             * user_id : 141
             * tool_id : 78
             * bank_name : STRIPE TEST BANK
             * account_holder_name : Zggxhz Zhzhhzusyzg
             * dob : 18-10-2004
             * strip_account : acct_1BE9jyLMRqYmMKu3
             * account_number : 00012345
             * country_code : GB
             * add_currency : GBP
             * address : Ztgzgs
             * city : Sgzggsg
             * state : Shhzhs
             * postal_code : Ca103
             * sort_code : 108800
             */

            private int id;
            private int user_id;
            private int tool_id;
            private String bank_name;
            private String first_name;
            private String last_name;
            private String dob;
            private String strip_account;
            private String account_number;
            private String country_code;
            private String currency;
            private String address;
            private String city;
            private String state;
            private String postal_code;
            private String sort_code;
            private String swift;
            private String account_type;

            public BankDetailBean() {
            }

            protected BankDetailBean(Parcel in) {
                this.id = in.readInt();
                this.user_id = in.readInt();
                this.tool_id = in.readInt();
                this.bank_name = in.readString();
                this.first_name = in.readString();
                this.last_name = in.readString();
                this.dob = in.readString();
                this.strip_account = in.readString();
                this.account_number = in.readString();
                this.country_code = in.readString();
                this.currency = in.readString();
                this.address = in.readString();
                this.city = in.readString();
                this.state = in.readString();
                this.postal_code = in.readString();
                this.sort_code = in.readString();
                this.swift = in.readString();
                this.account_type = in.readString();
            }

            public String getAccount_type() {
                return account_type;
            }

            public void setAccount_type(String account_type) {
                this.account_type = account_type;
            }

            public String getSwift() {
                return swift;
            }

            public void setSwift(String swift) {
                this.swift = swift;
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

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
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

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public String getStrip_account() {
                return strip_account;
            }

            public void setStrip_account(String strip_account) {
                this.strip_account = strip_account;
            }

            public String getAccount_number() {
                return account_number;
            }

            public void setAccount_number(String account_number) {
                this.account_number = account_number;
            }

            public String getCountry_code() {
                return country_code;
            }

            public void setCountry_code(String country_code) {
                this.country_code = country_code;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getPostal_code() {
                return postal_code;
            }

            public void setPostal_code(String postal_code) {
                this.postal_code = postal_code;
            }

            public String getSort_code() {
                return sort_code;
            }

            public void setSort_code(String sort_code) {
                this.sort_code = sort_code;
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
                dest.writeString(this.bank_name);
                dest.writeString(this.first_name);
                dest.writeString(this.last_name);
                dest.writeString(this.dob);
                dest.writeString(this.strip_account);
                dest.writeString(this.account_number);
                dest.writeString(this.country_code);
                dest.writeString(this.currency);
                dest.writeString(this.address);
                dest.writeString(this.city);
                dest.writeString(this.state);
                dest.writeString(this.postal_code);
                dest.writeString(this.sort_code);
                dest.writeString(this.swift);
                dest.writeString(this.account_type);
            }
        }
    }
}
