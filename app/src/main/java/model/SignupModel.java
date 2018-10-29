package model;

/**
 * Created by applify on 9/27/2017.
 */

public class SignupModel extends BaseModel {

    /**
     * response : {"id":13,"status":0,"platform_status":2,"user_type":1,"access_token":"9bae7d7d9c261c2fc82f62183e1f98f0","device_token":"123","email":"rajatarora+3@applify.co","username":"","phone_number":"","country_code":"","user_pic":"","facebook_id":null,"otp":1111}
     * code : 400
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
         * id : 13
         * status : 0
         * platform_status : 2
         * user_type : 1
         * access_token : 9bae7d7d9c261c2fc82f62183e1f98f0
         * device_token : 123
         * email : rajatarora+3@applify.co
         * username :
         * phone_number :
         * country_code :
         * user_pic :
         * facebook_id : null
         * otp : 1111
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
        private String facebook_id;
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
        private String vat;
        private String primary_currency;
        private String currency_selected;
        private String unverified_email;

        public String getUnverified_email() {
            return unverified_email;
        }

        public void setUnverified_email(String unverified_email) {
            this.unverified_email = unverified_email;
        }

        public String getCurrency_selected() {
            return currency_selected;
        }

        public void setCurrency_selected(String currency_selected) {
            this.currency_selected = currency_selected;
        }

        public String getPrimary_currency() {
            return primary_currency;
        }

        public void setPrimary_currency(String primary_currency) {
            this.primary_currency = primary_currency;
        }

        public String getVat() {
            return vat;
        }

        public void setVat(String vat) {
            this.vat = vat;
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

        public String getFacebook_id() {
            return facebook_id;
        }

        public void setFacebook_id(String facebook_id) {
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

        public int getEmail_verify() {
            return email_verify;
        }

        public void setEmail_verify(int email_verify) {
            this.email_verify = email_verify;
        }
    }


}
