package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dev on 4/12/17.
 */

public class DashboardDatesModel extends BaseModel {


    /**
     * response : [{"booking_id":138,"date":"07-12-2017","time":"12:00 AM","title":"Edward Android First Tul Djdje Ejej����������������������","status":1},{"booking_id":147,"date":"15-12-2017","time":"12:00 AM","title":"Edward Android First Tul Djdje Ejej����������������������","status":1},{"booking_id":148,"date":"04-12-2017","time":"12:00 AM","title":"Edward Android First Tul Djdje Ejej����������������������","status":1},{"booking_id":150,"date":"20-12-2017","time":"12:00 AM","title":"Edward Android First Tul Djdje Ejej����������������������","status":1},{"booking_id":159,"date":"10-12-2017","time":"6:12 PM","title":"Andy Two","status":1},{"booking_id":161,"date":"10-12-2017","time":"6:12 PM","title":"Andy Two","status":1},{"booking_id":138,"title":"Edward Android First Tul Djdje Ejej����������������������","date":"07-12-2017","time":"12:00 AM (Next Day)","status":2},{"booking_id":147,"title":"Edward Android First Tul Djdje Ejej����������������������","date":"15-12-2017","time":"12:00 AM (Next Day)","status":2},{"booking_id":148,"title":"Edward Android First Tul Djdje Ejej����������������������","date":"04-12-2017","time":"12:00 AM (Next Day)","status":2},{"booking_id":150,"title":"Edward Android First Tul Djdje Ejej����������������������","date":"20-12-2017","time":"12:00 AM (Next Day)","status":2},{"booking_id":159,"title":"Andy Two","date":"11-12-2017","time":"6:12 PM (Next Day)","status":2},{"booking_id":161,"title":"Andy Two","date":"11-12-2017","time":"6:12 PM (Next Day)","status":2}]
     * code : 111
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
        /**
         * booking_id : 138
         * date : 07-12-2017
         * time : 12:00 AM
         * title : Edward Android First Tul Djdje Ejej����������������������
         * status : 1
         */

        private int booking_id;
        private String date;
        private String time;
        private String title;
        private String delivery_date;
        private String return_date;

        private int status;
        private int tool_id;

        public int getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(int booking_id) {
            this.booking_id = booking_id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTool_id() {
            return tool_id;
        }

        public void setTool_id(int tool_id) {
            this.tool_id = tool_id;
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

        public ResponseBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.booking_id);
            dest.writeString(this.date);
            dest.writeString(this.time);
            dest.writeString(this.title);
            dest.writeString(this.delivery_date);
            dest.writeString(this.return_date);
            dest.writeInt(this.status);
            dest.writeInt(this.tool_id);
        }

        protected ResponseBean(Parcel in) {
            this.booking_id = in.readInt();
            this.date = in.readString();
            this.time = in.readString();
            this.title = in.readString();
            this.delivery_date = in.readString();
            this.return_date = in.readString();
            this.status = in.readInt();
            this.tool_id = in.readInt();
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
