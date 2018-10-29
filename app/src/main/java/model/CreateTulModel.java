package model;


import java.util.List;

public class CreateTulModel extends BaseModel{

    /**
     * response : {"id":16,"title":"33","user_id":4,"owner":"Rajat","category_id":2,"description":"Sdd","quantity":88,"rented":0,"price":"888","currency":"£","additional_price":{"security_charges":"88","fee":"88"},"address":"Unnamed Road, Sector 82, JLPL Industrial Area, Punjab 140308, India,JLPL Industrial Area , Sahibzada Ajit Singh Nagar,Punjab,India","latitude":"30.6578101","longitude":"76.7327749","rules":["Vv"],"preferences":{"available":"Only Weekends","start_time":"4:41 PM","end_time":"4:41 PM","tull_delivery":"0","delivery_charges":"","delivery_start_time":"","delivery_end_time":""},"attachment":[{"id":27,"tool_id":16,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15087576010.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508757601_thumb0.jpg","type":"image"}],"pause_status":"0","bank_detail":{"id":14,"user_id":4,"tool_id":16,"bank_name":"STRIPE TEST BANK","first_name":"F Fctc","last_name":"Yh6j7j","dob":"23-10-2004","strip_account":"acct_1BG3y5EWF6QMSb37","account_number":"00012345","country_code":"GB","currency":"GBP","address":"Tbth6h6","city":"6gyh6g","state":"8j7j7j","postal_code":"Ca103","sort_code":"108800"},"rating":0,"total_earnings":"0"}
     * code : 403
     */

    private ResponseBean response;
    private int code;

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

    public static class ResponseBean {
        /**
         * id : 16
         * title : 33
         * user_id : 4
         * owner : Rajat
         * category_id : 2
         * description : Sdd
         * quantity : 88
         * rented : 0
         * price : 888
         * currency : £
         * additional_price : {"security_charges":"88","fee":"88"}
         * address : Unnamed Road, Sector 82, JLPL Industrial Area, Punjab 140308, India,JLPL Industrial Area , Sahibzada Ajit Singh Nagar,Punjab,India
         * latitude : 30.6578101
         * longitude : 76.7327749
         * rules : ["Vv"]
         * preferences : {"available":"Only Weekends","start_time":"4:41 PM","end_time":"4:41 PM","tull_delivery":"0","delivery_charges":"","delivery_start_time":"","delivery_end_time":""}
         * attachment : [{"id":27,"tool_id":16,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15087576010.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508757601_thumb0.jpg","type":"image"}]
         * pause_status : 0
         * bank_detail : {"id":14,"user_id":4,"tool_id":16,"bank_name":"STRIPE TEST BANK","first_name":"F Fctc","last_name":"Yh6j7j","dob":"23-10-2004","strip_account":"acct_1BG3y5EWF6QMSb37","account_number":"00012345","country_code":"GB","currency":"GBP","address":"Tbth6h6","city":"6gyh6g","state":"8j7j7j","postal_code":"Ca103","sort_code":"108800"}
         * rating : 0
         * total_earnings : 0
         */

        private int id;
        private String title;
        private int user_id;
        private String owner;
        private int category_id;
        private String description;
        private int quantity;
        private int rented;
        private String price;
        private String transaction_percentage;
        private String currency;
        private AdditionalPriceBean additional_price;
        private String address;
        private String latitude;
        private String longitude;
        private PreferencesBean preferences;
        private String pause_status;
        private BankDetailBean bank_detail;
        private int rating;

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        private String condition;
        private String total_earnings;
        private List<String> rules;
        private List<AttachmentBean> attachment;

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

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
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

        public String getTransaction_percentage() {
            return transaction_percentage;
        }

        public void setTransaction_percentage(String transaction_percentage) {
            this.transaction_percentage = transaction_percentage;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
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

        public List<String> getRules() {
            return rules;
        }

        public void setRules(List<String> rules) {
            this.rules = rules;
        }

        public List<AttachmentBean> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<AttachmentBean> attachment) {
            this.attachment = attachment;
        }



        public static class AdditionalPriceBean {
            /**
             * security_charges : 88
             * fee : 88
             */

            private String security_charges;
            private String fee;

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
        }

        public static class PreferencesBean {
            /**
             * available : Only Weekends
             * start_time : 4:41 PM
             * end_time : 4:41 PM
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
        }

        public static class BankDetailBean {
            /**
             * id : 14
             * user_id : 4
             * tool_id : 16
             * bank_name : STRIPE TEST BANK
             * first_name : F Fctc
             * last_name : Yh6j7j
             * dob : 23-10-2004
             * strip_account : acct_1BG3y5EWF6QMSb37
             * account_number : 00012345
             * country_code : GB
             * currency : GBP
             * address : Tbth6h6
             * city : 6gyh6g
             * state : 8j7j7j
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
        }

        public static class AttachmentBean {
            /**
             * id : 27
             * tool_id : 16
             * attachment : https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15087576010.jpg
             * thumbnail : https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508757601_thumb0.jpg
             * type : image
             */

            private int id;
            private int tool_id;
            private String attachment;
            private String thumbnail;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTool_id() {
                return tool_id;
            }

            public void setTool_id(int tool_id) {
                this.tool_id = tool_id;
            }

            public String getAttachment() {
                return attachment;
            }

            public void setAttachment(String attachment) {
                this.attachment = attachment;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
