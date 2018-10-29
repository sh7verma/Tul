package model;

import java.util.List;

/**
 * Created by dev on 23/8/18.
 */

public class SellerDetailsModel {

    /**
     * response : {"id":187,"status":2,"platform_status":1,"user_type":1,"access_token":"670530b9c5426c52e46b1c3bdf1e718f","device_token":"cc98Zdv4-ag:APA91bGAj2LLK0EzpBl6JGhZTExvDSXs2eGLP0GCSlcWzHfMz8iagbzOWXeW89OYJokn0fzrUzqJaAF2z0sqtcZUm63PiVEaR4FTsQAKn1S5DSecz5wZoq9BRu0r6WfYLEyGZgxmE_pBKZiPfNhB3oDYhRgFDAe7Jw","email":"bsbs@gshs.gs","username":"Vbzbzb Hsjsj","first_name":"Vbzbzb","last_name":"Hsjsj","phone_number":"7508843163","country_code":"+91","user_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1534766016.jpeg","facebook_id":null,"otp":1111,"lender":0,"email_verify":0,"block_status":0,"is_guest":0,"is_email_skip":1,"is_seller":null,"login_type":1,"is_company":0,"tools":[{"tool_id":1,"title":"dummy","id":187,"owner":"Vbzbzb Hsjsj","first_name":"Vbzbzb","last_name":"Hsjsj","owner_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1534766016.jpeg","category_id":"3","edit_count":0,"category_name":"ENGINE POWERED TULs","description":"dummy","quantity":10,"sold":0,"currency":"£","price":"100","address":"asfasfas","latitude":"","longitude":"","condition":"new","attachment":[{"id":1,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":2,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":3,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":4,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"}],"rating":0,"user_rating":0,"created_at":null,"is_wishlisted":0}]}
     * code : 111
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
         * id : 187
         * status : 2
         * platform_status : 1
         * user_type : 1
         * access_token : 670530b9c5426c52e46b1c3bdf1e718f
         * device_token : cc98Zdv4-ag:APA91bGAj2LLK0EzpBl6JGhZTExvDSXs2eGLP0GCSlcWzHfMz8iagbzOWXeW89OYJokn0fzrUzqJaAF2z0sqtcZUm63PiVEaR4FTsQAKn1S5DSecz5wZoq9BRu0r6WfYLEyGZgxmE_pBKZiPfNhB3oDYhRgFDAe7Jw
         * email : bsbs@gshs.gs
         * username : Vbzbzb Hsjsj
         * first_name : Vbzbzb
         * last_name : Hsjsj
         * phone_number : 7508843163
         * country_code : +91
         * user_pic : https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1534766016.jpeg
         * facebook_id : null
         * otp : 1111
         * lender : 0
         * email_verify : 0
         * block_status : 0
         * is_guest : 0
         * is_email_skip : 1
         * is_seller : null
         * login_type : 1
         * is_company : 0
         * tools : [{"tool_id":1,"title":"dummy","id":187,"owner":"Vbzbzb Hsjsj","first_name":"Vbzbzb","last_name":"Hsjsj","owner_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1534766016.jpeg","category_id":"3","edit_count":0,"category_name":"ENGINE POWERED TULs","description":"dummy","quantity":10,"sold":0,"currency":"£","price":"100","address":"asfasfas","latitude":"","longitude":"","condition":"new","attachment":[{"id":1,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":2,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":3,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":4,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"}],"rating":0,"user_rating":0,"created_at":null,"is_wishlisted":0}]
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
        private Object is_seller;
        private int login_type;
        private int is_company;
        private List<SalesTulDetailModel.ResponseBean> tools;

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

        public Object getIs_seller() {
            return is_seller;
        }

        public void setIs_seller(Object is_seller) {
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

        public List<SalesTulDetailModel.ResponseBean> getTools() {
            return tools;
        }

        public void setTools(List<SalesTulDetailModel.ResponseBean> tools) {
            this.tools = tools;
        }

    }
}
