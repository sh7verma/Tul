package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dev on 22/11/17.
 */

public class PendingTaskModel extends BaseModel {


    /**
     * response : [{"booking_id":27,"tool_id":82,"user_id":57,"owner":"Rajat Arora","first_name":"Rajat","last_name":"Arora","owner_pic":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1511152376.jpg","country_code":"+44","phone_number":"9888454536","address":"Delhi, India","title":"Checking For Lent","category_id":2,"category_name":"POWER TULS","delivery_date":"2017-11-18 00:00","return_date":"2017-11-19 00:00","type":1,"attachment":[{"id":91,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1511242390_thumb0.jpg","tool_id":82,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15112423900.jpg","type":"image"}]},{"booking_id":28,"tool_id":82,"user_id":57,"owner":"Rajat Arora","first_name":"Rajat","last_name":"Arora","owner_pic":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1511152376.jpg","country_code":"+44","phone_number":"9888454536","address":"Delhi, India","title":"Checking For Lent","category_id":2,"category_name":"POWER TULS","delivery_date":"2017-11-18 00:00","return_date":"2017-11-19 00:00","type":1,"attachment":[{"id":91,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1511242390_thumb0.jpg","tool_id":82,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15112423900.jpg","type":"image"}]}]
     * code : 111
     */

    private int code;
    private List<BookTulModel.ResponseBean> response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BookTulModel.ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<BookTulModel.ResponseBean> response) {
        this.response = response;
    }

/*
    public static class ResponseBean implements Parcelable {
        */
/**
         * booking_id : 27
         * tool_id : 82
         * user_id : 57
         * owner : Rajat Arora
         * first_name : Rajat
         * last_name : Arora
         * owner_pic : https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1511152376.jpg
         * country_code : +44
         * phone_number : 9888454536
         * address : Delhi, India
         * title : Checking For Lent
         * category_id : 2
         * category_name : POWER TULS
         * delivery_date : 2017-11-18 00:00
         * return_date : 2017-11-19 00:00
         * type : 1
         * attachment : [{"id":91,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1511242390_thumb0.jpg","tool_id":82,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15112423900.jpg","type":"image"}]
         *//*


        private int booking_id;
        private int tool_id;
        private int user_id;
        private String owner;
        private String first_name;
        private String last_name;
        private String owner_pic;
        private String country_code;
        private String phone_number;
        private String address;
        private String title;
        private int category_id;
        private String category_name;
        private String delivery_date;
        private String return_date;
        private int type;
        private int total_price;
        private List<AttachmentModel> attachment;

        public int getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(int booking_id) {
            this.booking_id = booking_id;
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

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTotal_amount() {
            return total_price;
        }

        public void setTotal_amount(int total_price) {
            this.total_price = total_price;
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
            dest.writeInt(this.booking_id);
            dest.writeInt(this.tool_id);
            dest.writeInt(this.user_id);
            dest.writeString(this.owner);
            dest.writeString(this.first_name);
            dest.writeString(this.last_name);
            dest.writeString(this.owner_pic);
            dest.writeString(this.country_code);
            dest.writeString(this.phone_number);
            dest.writeString(this.address);
            dest.writeString(this.title);
            dest.writeInt(this.category_id);
            dest.writeString(this.category_name);
            dest.writeString(this.delivery_date);
            dest.writeString(this.return_date);
            dest.writeInt(this.type);
            dest.writeTypedList(this.attachment);
        }

        public ResponseBean() {
        }

        protected ResponseBean(Parcel in) {
            this.booking_id = in.readInt();
            this.tool_id = in.readInt();
            this.user_id = in.readInt();
            this.owner = in.readString();
            this.first_name = in.readString();
            this.last_name = in.readString();
            this.owner_pic = in.readString();
            this.country_code = in.readString();
            this.phone_number = in.readString();
            this.address = in.readString();
            this.title = in.readString();
            this.category_id = in.readInt();
            this.category_name = in.readString();
            this.delivery_date = in.readString();
            this.return_date = in.readString();
            this.type = in.readInt();
            this.attachment = in.createTypedArrayList(AttachmentModel.CREATOR);
        }

        public static final Parcelable.Creator<ResponseBean> CREATOR = new Parcelable.Creator<ResponseBean>() {
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
*/
}
