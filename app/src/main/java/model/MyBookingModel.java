package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by applify on 11/20/2017.
 */

public class MyBookingModel extends BaseModel {

    /**
     * response : [{"tool_id":20,"user_id":20,"owner":"Naina Lalla","first_name":"Naina","last_name":"Lalla","owner_pic":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1510143091.jpeg","booking_id":20,"lender_status":1,"borrower_status":0,"title":"Ababa Sbs sbs S Jab. Sjsn Sbs. Sjsn s ","additional_charges":{"security_charges":"94","fee":"94"},"total_amount":"128","delivery_cost":0,"price":"64","quantity":1,"currency":"£","delivery_date":"2017-11-18 00:00","return_date":"2017-11-19 00:00","selected_date":"18-11-2017,19-11-2017","handover_at":"2017-11-20 00:00","lander_received_at":"2017-11-20 00:00","borrower_received_at":"2017-11-20 00:00","borrower_returned_at":"2017-11-20 00:00","canceled_at":"","booked_at":"2017-11-20 04:50","attachment":[{"id":24,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1510203733_thumb0.jpeg","tool_id":20,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15102037330.jpeg","type":"image"}],"user_type":1}]
     * count : {"my_bookings_count":1,"tool_lent_count":2}
     */

    private int code;
    private List<BookTulModel.ResponseBean> response;
    private CountBean count;


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

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
        this.count = count;
    }



    public static class CountBean {
        /**
         * my_bookings_count : 1
         * tool_lent_count : 2
         */

        private int my_bookings_count;
        private int tool_lent_count;

        public int getMy_bookings_count() {
            return my_bookings_count;
        }

        public void setMy_bookings_count(int my_bookings_count) {
            this.my_bookings_count = my_bookings_count;
        }

        public int getTool_lent_count() {
            return tool_lent_count;
        }

        public void setTool_lent_count(int tool_lent_count) {
            this.tool_lent_count = tool_lent_count;
        }
    }

}
