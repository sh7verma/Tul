package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 26/12/17.
 */

public class NotificationModel extends BaseModel implements Parcelable {

    private List<ResponseBean> response;

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 26
         * notification_type : 7
         * booking_id : 6
         * tool_id : 1
         * message : JJ Singh has marked updated on 21-12-2017 as Returned
         * read_status : 0
         * username : JJ Singh
         * first_name : JJ
         * last_name : Singh
         * user_pic : https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1513766436.jpg
         */

        private int id;
        private int notification_type;
        private int booking_id;
        private int tool_id;
        private String message;
        private int read_status;
        private String username;
        private String first_name;
        private String last_name;
        private String user_pic;
        private String created;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNotification_type() {
            return notification_type;
        }

        public void setNotification_type(int notification_type) {
            this.notification_type = notification_type;
        }

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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getRead_status() {
            return read_status;
        }

        public void setRead_status(int read_status) {
            this.read_status = read_status;
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

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getCreated_at() {
            return created;
        }

        public void setCreated_at(String created_at) {
            this.created = created_at;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.response);
    }

    public NotificationModel() {
    }

    protected NotificationModel(Parcel in) {
        this.response = new ArrayList<ResponseBean>();
        in.readList(this.response, ResponseBean.class.getClassLoader());
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel source) {
            return new NotificationModel(source);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };
}
