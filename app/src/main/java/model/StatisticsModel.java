package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 4/12/17.
 */

public class StatisticsModel extends BaseModel implements Parcelable {


    /**
     * response : [{"total_earnings":"40","rating":5}]
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

    public static class ResponseBean {
        /**
         * total_earnings : 40
         * rating : 5
         */

        private String total_earnings;
        private int rating;

        public String getTotal_earnings() {
            return total_earnings;
        }

        public void setTotal_earnings(String total_earnings) {
            this.total_earnings = total_earnings;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeList(this.response);
    }

    public StatisticsModel() {
    }

    protected StatisticsModel(Parcel in) {
        this.code = in.readInt();
        this.response = new ArrayList<ResponseBean>();
        in.readList(this.response, ResponseBean.class.getClassLoader());
    }

    public static final Creator<StatisticsModel> CREATOR = new Creator<StatisticsModel>() {
        @Override
        public StatisticsModel createFromParcel(Parcel source) {
            return new StatisticsModel(source);
        }

        @Override
        public StatisticsModel[] newArray(int size) {
            return new StatisticsModel[size];
        }
    };
}
