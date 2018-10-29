package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by applify on 10/18/2017.
 */

public class ProfileModel extends BaseModel {

    /**
     * response : {"id":9,"status":2,"platform_status":2,"user_type":2,"access_token":"ab8831d2b24969376e747116e8a66c44","device_token":"123","email":"rajatarora028@gmail.com","username":"Rajat Arora","phone_number":"9888454536","country_code":"+44","user_pic":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1509000380.jpg","facebook_id":"1640305702699571","otp":1111,"lender":1,"tools":[{"id":80,"title":"Weekend","user_id":9,"price":"100","category_name":"POWER TULS","attachment":[{"id":128,"tool_id":80,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15097034820.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509703482_thumb0.jpg","type":"image"}],"is_wishlisted":0},{"id":79,"title":"Weekdays","user_id":9,"price":"100","category_name":"HAND TULS","attachment":[{"id":126,"tool_id":79,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15097033760.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509703376_thumb0.jpg","type":"image"},{"id":127,"tool_id":79,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15097033771.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509703377_thumb1.jpg","type":"image"}],"is_wishlisted":0},{"id":76,"title":"Nov 2","user_id":9,"price":"10","category_name":"POWER TULS","attachment":[{"id":124,"tool_id":76,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15095999360.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509599936_thumb0.jpg","type":"image"},{"id":125,"tool_id":76,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15095999371.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509599937_thumb1.jpg","type":"image"}],"is_wishlisted":0},{"id":64,"title":"Test Video","user_id":9,"price":"68","category_name":"ENGINE POWERED TULS","attachment":[{"id":114,"tool_id":64,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1509367053.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509367053_thumb.jpg","type":"video"}],"is_wishlisted":0},{"id":63,"title":"Hhd","user_id":9,"price":"646","category_name":"ENGINE POWERED TULS","attachment":[{"id":113,"tool_id":63,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1509366678.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509366678_thumb.jpg","type":"video"}],"is_wishlisted":0},{"id":54,"title":"Hhh","user_id":9,"price":"66","category_name":"ENGINE POWERED TULS","attachment":[{"id":95,"tool_id":54,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1509362703.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509362703_thumb.jpg","type":"video"}],"is_wishlisted":0},{"id":40,"title":"Test","user_id":9,"price":"23","category_name":"ENGINE POWERED TULS","attachment":[{"id":64,"tool_id":40,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1509000725.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509000725_thumb.jpg","type":"video"}],"is_wishlisted":0}],"wishlist":[{"id":10,"owner":"Rajat","title":"Unun","user_id":4,"price":"66","category_name":"ENGINE POWERED TULS","attachment":[{"id":17,"tool_id":10,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15087489610.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508748961_thumb0.jpg","type":"image"},{"id":18,"tool_id":10,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15087489621.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508748962_thumb1.jpg","type":"image"}]}],"tools_count":7,"wishlist_count":1}
     * code : 422
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
         * id : 9
         * status : 2
         * platform_status : 2
         * user_type : 2
         * access_token : ab8831d2b24969376e747116e8a66c44
         * device_token : 123
         * email : rajatarora028@gmail.com
         * username : Rajat Arora
         * phone_number : 9888454536
         * country_code : +44
         * user_pic : https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1509000380.jpg
         * facebook_id : 1640305702699571
         * otp : 1111
         * lender : 1
         * tools : [{"id":80,"title":"Weekend","user_id":9,"price":"100","category_name":"POWER TULS","attachment":[{"id":128,"tool_id":80,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15097034820.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509703482_thumb0.jpg","type":"image"}],"is_wishlisted":0},{"id":79,"title":"Weekdays","user_id":9,"price":"100","category_name":"HAND TULS","attachment":[{"id":126,"tool_id":79,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15097033760.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509703376_thumb0.jpg","type":"image"},{"id":127,"tool_id":79,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15097033771.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509703377_thumb1.jpg","type":"image"}],"is_wishlisted":0},{"id":76,"title":"Nov 2","user_id":9,"price":"10","category_name":"POWER TULS","attachment":[{"id":124,"tool_id":76,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15095999360.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509599936_thumb0.jpg","type":"image"},{"id":125,"tool_id":76,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15095999371.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509599937_thumb1.jpg","type":"image"}],"is_wishlisted":0},{"id":64,"title":"Test Video","user_id":9,"price":"68","category_name":"ENGINE POWERED TULS","attachment":[{"id":114,"tool_id":64,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1509367053.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509367053_thumb.jpg","type":"video"}],"is_wishlisted":0},{"id":63,"title":"Hhd","user_id":9,"price":"646","category_name":"ENGINE POWERED TULS","attachment":[{"id":113,"tool_id":63,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1509366678.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509366678_thumb.jpg","type":"video"}],"is_wishlisted":0},{"id":54,"title":"Hhh","user_id":9,"price":"66","category_name":"ENGINE POWERED TULS","attachment":[{"id":95,"tool_id":54,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1509362703.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509362703_thumb.jpg","type":"video"}],"is_wishlisted":0},{"id":40,"title":"Test","user_id":9,"price":"23","category_name":"ENGINE POWERED TULS","attachment":[{"id":64,"tool_id":40,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1509000725.mp4","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509000725_thumb.jpg","type":"video"}],"is_wishlisted":0}]
         * wishlist : [{"id":10,"owner":"Rajat","title":"Unun","user_id":4,"price":"66","category_name":"ENGINE POWERED TULS","attachment":[{"id":17,"tool_id":10,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15087489610.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508748961_thumb0.jpg","type":"image"},{"id":18,"tool_id":10,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15087489621.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1508748962_thumb1.jpg","type":"image"}]}]
         * tools_count : 7
         * wishlist_count : 1
         */

        private int id;
        private int status;
        private int platform_status;
        private int user_type;
        private String access_token;
        private String device_token;
        private String email;
        private String username;
        private String phone_number;
        private String country_code;
        private String user_pic;
        private String facebook_id;
        private int otp;
        private int lender;
        private int tools_count;
        private int wishlist_count;
        private List<ToolsBean> tools;
        private List<NearByTulListingModel.ResponseBean> wishlist;
        private int block_status;
        private int is_guest;
        private int is_email_skip;
        private int email_verify;

        public ResponseBean() {
        }


        protected ResponseBean(Parcel in) {
            this.id = in.readInt();
            this.status = in.readInt();
            this.platform_status = in.readInt();
            this.user_type = in.readInt();
            this.access_token = in.readString();
            this.device_token = in.readString();
            this.email = in.readString();
            this.username = in.readString();
            this.phone_number = in.readString();
            this.country_code = in.readString();
            this.user_pic = in.readString();
            this.facebook_id = in.readString();
            this.otp = in.readInt();
            this.lender = in.readInt();
            this.tools_count = in.readInt();
            this.wishlist_count = in.readInt();
            this.tools = in.createTypedArrayList(ToolsBean.CREATOR);
            this.wishlist = in.createTypedArrayList(NearByTulListingModel.ResponseBean.CREATOR);
            this.block_status = in.readInt();
            this.is_guest = in.readInt();
            this.is_email_skip = in.readInt();
            this.email_verify = in.readInt();
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

        public int getTools_count() {
            return tools_count;
        }

        public void setTools_count(int tools_count) {
            this.tools_count = tools_count;
        }

        public int getWishlist_count() {
            return wishlist_count;
        }

        public void setWishlist_count(int wishlist_count) {
            this.wishlist_count = wishlist_count;
        }

        public List<ToolsBean> getTools() {
            return tools;
        }

        public void setTools(List<ToolsBean> tools) {
            this.tools = tools;
        }

        public List<NearByTulListingModel.ResponseBean> getWishlist() {
            return wishlist;
        }

        public void setWishlist(List<NearByTulListingModel.ResponseBean> wishlist) {
            this.wishlist = wishlist;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.status);
            dest.writeInt(this.platform_status);
            dest.writeInt(this.user_type);
            dest.writeString(this.access_token);
            dest.writeString(this.device_token);
            dest.writeString(this.email);
            dest.writeString(this.username);
            dest.writeString(this.phone_number);
            dest.writeString(this.country_code);
            dest.writeString(this.user_pic);
            dest.writeString(this.facebook_id);
            dest.writeInt(this.otp);
            dest.writeInt(this.lender);
            dest.writeInt(this.tools_count);
            dest.writeInt(this.wishlist_count);
            dest.writeTypedList(this.tools);
            dest.writeTypedList(this.wishlist);
            dest.writeInt(this.block_status);
            dest.writeInt(this.is_guest);
            dest.writeInt(this.is_email_skip);
            dest.writeInt(this.email_verify);
        }

        public static class ToolsBean implements Parcelable {
            public static final Creator<ToolsBean> CREATOR = new Creator<ToolsBean>() {
                @Override
                public ToolsBean createFromParcel(Parcel source) {
                    return new ToolsBean(source);
                }

                @Override
                public ToolsBean[] newArray(int size) {
                    return new ToolsBean[size];
                }
            };
            /**
             * id : 80
             * title : Weekend
             * user_id : 9
             * price : 100
             * category_name : POWER TULS
             * attachment : [{"id":128,"tool_id":80,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15097034820.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509703482_thumb0.jpg","type":"image"}]
             * is_wishlisted : 0
             */

            private int id;
            private String title;
            private int user_id;
            private String price;
            private String category_name;
            private String transaction_percentage;
            private String condition;
            private String currency;
            private int is_wishlisted;
            private List<AttachmentModel> attachment;
            public ToolsBean() {
            }

            protected ToolsBean(Parcel in) {
                this.id = in.readInt();
                this.title = in.readString();
                this.user_id = in.readInt();
                this.price = in.readString();
                this.category_name = in.readString();
                this.transaction_percentage = in.readString();
                this.condition = in.readString();
                this.currency = in.readString();
                this.is_wishlisted = in.readInt();
                this.attachment = in.createTypedArrayList(AttachmentModel.CREATOR);
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getTransaction_percentage() {
                return transaction_percentage;
            }

            public void setTransaction_percentage(String transaction_percentage) {
                this.transaction_percentage = transaction_percentage;
            }

            public int getIs_wishlisted() {
                return is_wishlisted;
            }

            public void setIs_wishlisted(int is_wishlisted) {
                this.is_wishlisted = is_wishlisted;
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
                dest.writeString(this.price);
                dest.writeString(this.category_name);
                dest.writeString(this.transaction_percentage);
                dest.writeString(this.condition);
                dest.writeString(this.currency);
                dest.writeInt(this.is_wishlisted);
                dest.writeTypedList(this.attachment);
            }
        }
    }
}
