package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dev on 30/8/18.
 */

public class SellerProfileModel {


    /**
     * response : {"id":267,"status":1,"platform_status":1,"user_type":1,"access_token":"1278d86e77383ee6914130ffe18326e4","device_token":"dsgsdgsdg","email":"jasjeet.s+111@applify.co","username":"jasjeet Singh","first_name":"jasjeet","last_name":"Singh","phone_number":"9041433279","country_code":"+91","user_pic":"","facebook_id":null,"otp":1111,"lender":0,"email_verify":0,"block_status":0,"is_guest":0,"is_email_skip":0,"is_seller":1,"login_type":1,"is_company":0,"latitude":"49.8575831","longitude":"6.0076709","address":"Ettelbruck, Luxembourg"}
     * code : 400
     */

    private ResponseBean response;
    private int code;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
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

    public static class ResponseBean {
        /**
         * id : 267
         * status : 1
         * platform_status : 1
         * user_type : 1
         * access_token : 1278d86e77383ee6914130ffe18326e4
         * device_token : dsgsdgsdg
         * email : jasjeet.s+111@applify.co
         * username : jasjeet Singh
         * first_name : jasjeet
         * last_name : Singh
         * phone_number : 9041433279
         * country_code : +91
         * user_pic :
         * facebook_id : null
         * otp : 1111
         * lender : 0
         * email_verify : 0
         * block_status : 0
         * is_guest : 0
         * is_email_skip : 0
         * is_seller : 1
         * login_type : 1
         * is_company : 0
         * latitude : 49.8575831
         * longitude : 6.0076709
         * address : Ettelbruck, Luxembourg
         */

        private int id;
        private int status;
        private int platform_status;
        private int user_type;
        private String access_token;
        private String device_token;
        private String email;
        private String username;
        private String first_name;
        private String last_name;
        private String phone_number;
        private String country_code;
        private String user_pic;
        private Object facebook_id;
        private int otp;
        private int lender;
        private int email_verify;
        private int block_status;
        private int is_guest;
        private int is_email_skip;
        private int is_seller;
        private int login_type;
        private int is_company;
        private String latitude;
        private String longitude;
        private String address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPlatform_status() {
            return platform_status;
        }

        public void setPlatform_status(int platform_status) {
            this.platform_status = platform_status;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public Object getFacebook_id() {
            return facebook_id;
        }

        public void setFacebook_id(Object facebook_id) {
            this.facebook_id = facebook_id;
        }

        public int getOtp() {
            return otp;
        }

        public void setOtp(int otp) {
            this.otp = otp;
        }

        public int getLender() {
            return lender;
        }

        public void setLender(int lender) {
            this.lender = lender;
        }

        public int getEmail_verify() {
            return email_verify;
        }

        public void setEmail_verify(int email_verify) {
            this.email_verify = email_verify;
        }

        public int getBlock_status() {
            return block_status;
        }

        public void setBlock_status(int block_status) {
            this.block_status = block_status;
        }

        public int getIs_guest() {
            return is_guest;
        }

        public void setIs_guest(int is_guest) {
            this.is_guest = is_guest;
        }

        public int getIs_email_skip() {
            return is_email_skip;
        }

        public void setIs_email_skip(int is_email_skip) {
            this.is_email_skip = is_email_skip;
        }

        public int getIs_seller() {
            return is_seller;
        }

        public void setIs_seller(int is_seller) {
            this.is_seller = is_seller;
        }

        public int getLogin_type() {
            return login_type;
        }

        public void setLogin_type(int login_type) {
            this.login_type = login_type;
        }

        public int getIs_company() {
            return is_company;
        }

        public void setIs_company(int is_company) {
            this.is_company = is_company;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


    public static class ErrorBean {
        /**
         * message : This tool has been deleted by the Owner
         * code : 1111
         */

        private String message;
        @SerializedName("code")
        private int codeX;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCodeX() {
            return codeX;
        }

        public void setCodeX(int codeX) {
            this.codeX = codeX;
        }
    }
}
