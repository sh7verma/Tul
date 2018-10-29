package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dev on 4/9/18.
 */

public class SalesPriceFee {


    /**
     * response : [{"id":1,"min_price":"0","max_price":"500","transaction_percentage":"10"},{"id":2,"min_price":"501","max_price":"1000","transaction_percentage":"8"},{"id":3,"min_price":"1001","max_price":"5000","transaction_percentage":"6"},{"id":4,"min_price":"5001","max_price":"10000","transaction_percentage":"4"},{"id":5,"min_price":"10000","max_price":"50000","transaction_percentage":"2"}]
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
         * id : 1
         * min_price : 0
         * max_price : 500
         * transaction_percentage : 10
         */

        private int id;
        private String min_price;
        private String max_price;
        private String transaction_percentage;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        private String currency;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMin_price() {
            return min_price;
        }

        public void setMin_price(String min_price) {
            this.min_price = min_price;
        }

        public String getMax_price() {
            return max_price;
        }

        public void setMax_price(String max_price) {
            this.max_price = max_price;
        }

        public String getTransaction_percentage() {
            return transaction_percentage;
        }

        public void setTransaction_percentage(String transaction_percentage) {
            this.transaction_percentage = transaction_percentage;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.min_price);
            dest.writeString(this.max_price);
            dest.writeString(this.transaction_percentage);
            dest.writeString(this.currency);
        }

        public ResponseBean() {
        }

        protected ResponseBean(Parcel in) {
            this.id = in.readInt();
            this.min_price = in.readString();
            this.max_price = in.readString();
            this.transaction_percentage = in.readString();
            this.currency = in.readString();
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
}
