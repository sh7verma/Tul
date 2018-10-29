package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by applify on 10/17/2017.
 */

public class CreateStripeAccountModel extends BaseModel {

    /**
     * response : {"id":"ba_1BL9ooAWLMVDrZBZEE4MRt9P","object":"bank_account","account":"acct_1BL9oiAWLMVDrZBZ","account_holder_name":"Jasjeet Singh","account_holder_type":"individual","bank_name":"STRIPE TEST BANK","country":"GB","currency":"GBP","default_for_currency":true,"fingerprint":"avzUs8UwPkvuEsz0","last4":"2345","metadata":{},"routing_number":"10-88-00","status":"new","account_number":"00012345","first_name":"Jasjeet","sort_code":"108800","country_code":"GB","last_name":"Singh","address":"698, Sector 82","city":"Mohali","state":"Punjab","postal_code":"CA103","dob":"30-11-1993"}
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

    public static class ResponseBean implements Parcelable {
        /**
         * id : ba_1BL9ooAWLMVDrZBZEE4MRt9P
         * object : bank_account
         * account : acct_1BL9oiAWLMVDrZBZ
         * account_holder_name : Jasjeet Singh
         * account_holder_type : individual
         * bank_name : STRIPE TEST BANK
         * country : GB
         * currency : GBP
         * default_for_currency : true
         * fingerprint : avzUs8UwPkvuEsz0
         * last4 : 2345
         * metadata : {}
         * routing_number : 10-88-00
         * status : new
         * account_number : 00012345
         * first_name : Jasjeet
         * sort_code : 108800
         * country_code : GB
         * last_name : Singh
         * address : 698, Sector 82
         * city : Mohali
         * state : Punjab
         * postal_code : CA103
         * dob : 30-11-1993
         */

        private String id;
        private int account_id;
        private String object;
        private String account;
        private String account_holder_name;
        private String account_holder_type;
        private String bank_name;
        private String country;
        private String currency;
        private boolean default_for_currency;
        private String fingerprint;
        private String last4;
        private String routing_number;
        private String status;
        private String account_number;
        private String first_name;
        private String sort_code;
        private String country_code;
        private String last_name;
        private String address;
        private String city;
        private String state;
        private String postal_code;
        private String dob;
        private String amount;
        private String swift;
        private String account_type;
        private int is_primary;
        public ResponseBean() {
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

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getIs_primary() {
            return is_primary;
        }

        public void setIs_primary(int is_primary) {
            this.is_primary = is_primary;
        }

        public int getAccountId() {
            return account_id;
        }

        public void setAccountId(int account_id) {
            this.account_id = account_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAccount_holder_name() {
            return account_holder_name;
        }

        public void setAccount_holder_name(String account_holder_name) {
            this.account_holder_name = account_holder_name;
        }

        public String getAccount_holder_type() {
            return account_holder_type;
        }

        public void setAccount_holder_type(String account_holder_type) {
            this.account_holder_type = account_holder_type;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public boolean isDefault_for_currency() {
            return default_for_currency;
        }

        public void setDefault_for_currency(boolean default_for_currency) {
            this.default_for_currency = default_for_currency;
        }

        public String getFingerprint() {
            return fingerprint;
        }

        public void setFingerprint(String fingerprint) {
            this.fingerprint = fingerprint;
        }

        public String getLast4() {
            return last4;
        }

        public void setLast4(String last4) {
            this.last4 = last4;
        }

        public String getRouting_number() {
            return routing_number;
        }

        public void setRouting_number(String routing_number) {
            this.routing_number = routing_number;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getSort_code() {
            return sort_code;
        }

        public void setSort_code(String sort_code) {
            this.sort_code = sort_code;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
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

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public int getIsPrimary() {
            return is_primary;
        }

        public void setIsPrimary(int isPrimary) {
            this.is_primary = isPrimary;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeInt(this.account_id);
            dest.writeString(this.object);
            dest.writeString(this.account);
            dest.writeString(this.account_holder_name);
            dest.writeString(this.account_holder_type);
            dest.writeString(this.bank_name);
            dest.writeString(this.country);
            dest.writeString(this.currency);
            dest.writeByte(this.default_for_currency ? (byte) 1 : (byte) 0);
            dest.writeString(this.fingerprint);
            dest.writeString(this.last4);
            dest.writeString(this.routing_number);
            dest.writeString(this.status);
            dest.writeString(this.account_number);
            dest.writeString(this.first_name);
            dest.writeString(this.sort_code);
            dest.writeString(this.country_code);
            dest.writeString(this.last_name);
            dest.writeString(this.address);
            dest.writeString(this.city);
            dest.writeString(this.state);
            dest.writeString(this.postal_code);
            dest.writeString(this.dob);
            dest.writeString(this.amount);
            dest.writeString(this.swift);
            dest.writeString(this.account_type);
            dest.writeInt(this.is_primary);
        }

        protected ResponseBean(Parcel in) {
            this.id = in.readString();
            this.account_id = in.readInt();
            this.object = in.readString();
            this.account = in.readString();
            this.account_holder_name = in.readString();
            this.account_holder_type = in.readString();
            this.bank_name = in.readString();
            this.country = in.readString();
            this.currency = in.readString();
            this.default_for_currency = in.readByte() != 0;
            this.fingerprint = in.readString();
            this.last4 = in.readString();
            this.routing_number = in.readString();
            this.status = in.readString();
            this.account_number = in.readString();
            this.first_name = in.readString();
            this.sort_code = in.readString();
            this.country_code = in.readString();
            this.last_name = in.readString();
            this.address = in.readString();
            this.city = in.readString();
            this.state = in.readString();
            this.postal_code = in.readString();
            this.dob = in.readString();
            this.amount = in.readString();
            this.swift = in.readString();
            this.account_type = in.readString();
            this.is_primary = in.readInt();
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
