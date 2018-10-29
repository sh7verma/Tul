package model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dev on 21/8/18.
 */

public class SalesTulDetailModel extends BaseModel implements Parcelable {

    public static final Creator<SalesTulDetailModel> CREATOR = new Creator<SalesTulDetailModel>() {
        @Override
        public SalesTulDetailModel createFromParcel(Parcel source) {
            return new SalesTulDetailModel(source);
        }

        @Override
        public SalesTulDetailModel[] newArray(int size) {
            return new SalesTulDetailModel[size];
        }
    };
    /**
     * response : {"id":1,"title":"dummy","user_id":187,"owner":"Vbzbzb Hsjsj","first_name":"Vbzbzb","last_name":"Hsjsj","owner_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1534766016.jpeg","category_id":"3","edit_count":0,"category_name":"ENGINE POWERED TULs","description":"dummy","quantity":10,"sold":0,"currency":"£","price":"100","address":"asfasfas","latitude":"","longitude":"","attachment":[{"id":1,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/asfasf.png","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/asfasfas.png","type":"image"},{"id":2,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/asfasf.png","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/asfasfas.png","type":"image"},{"id":3,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/asfasf.png","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/asfasfas.png","type":"image"},{"id":4,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/asfasf.png","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/asfasfas.png","type":"image"}],"rating":0,"created_at":null}
     * code : 111
     */

    private ResponseBean response;
    private int code;
    /**
     * error : {"message":"This tool has been deleted by the Owner","code":1111}
     */


    protected SalesTulDetailModel(Parcel in) {
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
         * id : 1
         * title : dummy
         * user_id : 187
         * owner : Vbzbzb Hsjsj
         * first_name : Vbzbzb
         * last_name : Hsjsj
         * owner_pic : https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1534766016.jpeg
         * category_id : 3
         * edit_count : 0
         * category_name : ENGINE POWERED TULs
         * description : dummy
         * quantity : 10
         * sold : 0
         * currency : £
         * price : 100
         * address : asfasfas
         * latitude :
         * longitude :
         * attachment : [{"id":1,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/asfasf.png","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/asfasfas.png","type":"image"},{"id":2,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/asfasf.png","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/asfasfas.png","type":"image"},{"id":3,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/asfasf.png","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/asfasfas.png","type":"image"},{"id":4,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/asfasf.png","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/asfasfas.png","type":"image"}]
         * rating : 0
         * created_at : null
         * primary_currency": "GBP",
         * delivery_type": 1,
         * delivery_charges_local": "9",
         * delivery_charges_int": "8",
         */

        private int id;
        private String title;
        private int user_id;
        private String owner;
        private String first_name;
        private String last_name;
        private String owner_pic;
        private String category_id;
        private int edit_count;
        private String category_name;
        private String description;
        private int quantity;
        private int sold;
        private String currency;
        private String price;
        private String address;
        private String latitude;
        private String longitude;
        private int rating;
        private String created_at;
        private String condition;
        private String primary_currency;
        private List<AttachmentModel> attachment;
        private int is_wishlisted;
        private String delivery_charges_local;
        private String delivery_charges_int;
        private int delivery_type;

        public ResponseBean() {
        }

        protected ResponseBean(Parcel in) {
            this.id = in.readInt();
            this.title = in.readString();
            this.user_id = in.readInt();
            this.owner = in.readString();
            this.first_name = in.readString();
            this.last_name = in.readString();
            this.owner_pic = in.readString();
            this.category_id = in.readString();
            this.edit_count = in.readInt();
            this.category_name = in.readString();
            this.description = in.readString();
            this.quantity = in.readInt();
            this.sold = in.readInt();
            this.currency = in.readString();
            this.price = in.readString();
            this.address = in.readString();
            this.latitude = in.readString();
            this.longitude = in.readString();
            this.rating = in.readInt();
            this.created_at = in.readString();
            this.condition = in.readString();
            this.primary_currency = in.readString();
            this.attachment = in.createTypedArrayList(AttachmentModel.CREATOR);
            this.is_wishlisted = in.readInt();
            this.delivery_charges_local = in.readString();
            this.delivery_charges_int = in.readString();
            this.delivery_type = in.readInt();
        }

        public String getDelivery_charges_local() {
            if (TextUtils.isEmpty(delivery_charges_int)) {
                return "0";
            }

            return delivery_charges_local;
        }

        public void setDelivery_charges_local(String delivery_charges_local) {
            this.delivery_charges_local = delivery_charges_local;
        }

        public String getDelivery_charges_int() {
            if (TextUtils.isEmpty(delivery_charges_int)) {
                return "0";
            }

            return delivery_charges_int;
        }

        public void setDelivery_charges_int(String delivery_charges_int) {
            this.delivery_charges_int = delivery_charges_int;
        }

        public int getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(int delivery_type) {
            this.delivery_type = delivery_type;
        }

        public String getPrimary_currency() {
            return primary_currency;
        }

        public void setPrimary_currency(String primary_currency) {
            this.primary_currency = primary_currency;
        }

        public int getIs_wishlisted() {
            return is_wishlisted;
        }

        public void setIs_wishlisted(int is_wishlisted) {
            this.is_wishlisted = is_wishlisted;
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

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public int getEdit_count() {
            return edit_count;
        }

        public void setEdit_count(int edit_count) {
            this.edit_count = edit_count;
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

        public int getSold() {
            return sold;
        }

        public void setSold(int sold) {
            this.sold = sold;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

//        public static class AttachmentBean {
//            /**
//             * id : 1
//             * tool_id : 1
//             * attachment : https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/asfasf.png
//             * thumbnail : https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/asfasfas.png
//             * type : image
//             */
//
//            private int id;
//            private int tool_id;
//            private String attachment;
//            private String thumbnail;
//            private String type;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public int getTool_id() {
//                return tool_id;
//            }
//
//            public void setTool_id(int tool_id) {
//                this.tool_id = tool_id;
//            }
//
//            public String getAttachment() {
//                return attachment;
//            }
//
//            public void setAttachment(String attachment) {
//                this.attachment = attachment;
//            }
//
//            public String getThumbnail() {
//                return thumbnail;
//            }
//
//            public void setThumbnail(String thumbnail) {
//                this.thumbnail = thumbnail;
//            }
//
//            public String getType() {
//                return type;
//            }
//
//            public void setType(String type) {
//                this.type = type;
//            }
//        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
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
            dest.writeString(this.first_name);
            dest.writeString(this.last_name);
            dest.writeString(this.owner_pic);
            dest.writeString(this.category_id);
            dest.writeInt(this.edit_count);
            dest.writeString(this.category_name);
            dest.writeString(this.description);
            dest.writeInt(this.quantity);
            dest.writeInt(this.sold);
            dest.writeString(this.currency);
            dest.writeString(this.price);
            dest.writeString(this.address);
            dest.writeString(this.latitude);
            dest.writeString(this.longitude);
            dest.writeInt(this.rating);
            dest.writeString(this.created_at);
            dest.writeString(this.condition);
            dest.writeString(this.primary_currency);
            dest.writeTypedList(this.attachment);
            dest.writeInt(this.is_wishlisted);
            dest.writeString(this.delivery_charges_local);
            dest.writeString(this.delivery_charges_int);
            dest.writeInt(this.delivery_type);
        }
    }


}
