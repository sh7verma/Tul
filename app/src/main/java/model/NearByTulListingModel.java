package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by applify on 11/1/2017.
 */

public class NearByTulListingModel extends BaseModel {


    /**
     * response : [{"id":58,"owner":"Sonal Dhdhd Dhdhd Djdjd Djdjd ","title":"Dhdh","user_id":10,"price":"84","attachment":[{"id":106,"tool_id":58,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15093636840.jpeg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509363684_thumb0.jpeg","type":"image"},{"id":107,"tool_id":58,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15093636841.jpeg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509363684_thumb1.jpeg","type":"image"}],"rating":4},{"id":68,"owner":"Babzbzbbzbz","title":"Nzbnznsnnzbzbzbbznznnznznnznznnsnxnnxnxnxnxnndnnsnsnsndnndnzbzbbzbxbbxnznzbbsbxnxnbsbsbsbbsbsbsbbxbz","user_id":18,"price":"998.5","attachment":[{"id":119,"tool_id":68,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15094261710.jpeg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509426171_thumb0.jpeg","type":"image"}],"rating":4},{"id":71,"owner":"Abcde","title":"Fgfyv the Fgfyv the F Yyfy Uguu Uuguuug Had Ughug Ugug Uuuiggii Uug Uug Ooj Jgfmbgu Gug Tdd Jguu Iih","user_id":12,"price":"85.4","attachment":[{"id":122,"tool_id":71,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15094411450.jpg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509441145_thumb0.jpg","type":"image"}],"rating":4}]
     * code : 403
     */

    private int code;
    private List<ResponseBean> response;

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
         * id : 58
         * owner : Sonal Dhdhd Dhdhd Djdjd Djdjd
         * title : Dhdh
         * user_id : 10
         * price : 84
         * attachment : [{"id":106,"tool_id":58,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15093636840.jpeg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509363684_thumb0.jpeg","type":"image"},{"id":107,"tool_id":58,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15093636841.jpeg","thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509363684_thumb1.jpeg","type":"image"}]
         * rating : 4
         */

        private int id;
        private String owner;
        private String title;
        private int user_id;
        private String price;
        private int rating;
        private String owner_pic;
        private String access_token;
        private String device_token;
        private String platform_status;
        private String condition;
        private String currency;
        private List<AttachmentModel> attachment;
        public ResponseBean() {
        }

        protected ResponseBean(Parcel in) {
            this.id = in.readInt();
            this.owner = in.readString();
            this.title = in.readString();
            this.user_id = in.readInt();
            this.price = in.readString();
            this.rating = in.readInt();
            this.owner_pic = in.readString();
            this.access_token = in.readString();
            this.device_token = in.readString();
            this.platform_status = in.readString();
            this.condition = in.readString();
            this.currency = in.readString();
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

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
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

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
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

        public List<AttachmentModel> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<AttachmentModel> attachment) {
            this.attachment = attachment;
        }

        public String getOwner_pic() {
            return owner_pic;
        }

        public void setOwner_pic(String owner_pic) {
            this.owner_pic = owner_pic;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.owner);
            dest.writeString(this.title);
            dest.writeInt(this.user_id);
            dest.writeString(this.price);
            dest.writeInt(this.rating);
            dest.writeString(this.owner_pic);
            dest.writeString(this.access_token);
            dest.writeString(this.device_token);
            dest.writeString(this.platform_status);
            dest.writeString(this.condition);
            dest.writeString(this.currency);
            dest.writeTypedList(this.attachment);
        }

        public static class AttachmentBean implements Parcelable {
            public static final Creator<AttachmentBean> CREATOR = new Creator<AttachmentBean>() {
                @Override
                public AttachmentBean createFromParcel(Parcel source) {
                    return new AttachmentBean(source);
                }

                @Override
                public AttachmentBean[] newArray(int size) {
                    return new AttachmentBean[size];
                }
            };
            /**
             * id : 106
             * tool_id : 58
             * attachment : https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15093636840.jpeg
             * thumbnail : https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1509363684_thumb0.jpeg
             * type : image
             */

            private int id;
            private int tool_id;
            private String attachment;
            private String thumbnail;
            private String type;

            public AttachmentBean() {
            }

            protected AttachmentBean(Parcel in) {
                this.id = in.readInt();
                this.tool_id = in.readInt();
                this.attachment = in.readString();
                this.thumbnail = in.readString();
                this.type = in.readString();
            }

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeInt(this.tool_id);
                dest.writeString(this.attachment);
                dest.writeString(this.thumbnail);
                dest.writeString(this.type);
            }
        }
    }
}
