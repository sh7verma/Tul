package model;

/**
 * Created by dev on 7/9/18.
 */

public class SalesBookTulModel extends BaseModel{


    /**
     * response : {"id":23,"user_id":293,"sale_tool_id":31,"delivery":1,"delivery_type":0,"delivery_cost":"20740.05","converted_delivery_cost":"20740.05","primary_currency":"GBP","price":"1098.00","total_amount":"21838.05","payment_mode":1,"address":"Kharoudi, Haryana 136035, India,  Kaithal, Haryana, India.","latitude":"30.01322970477612","longitude":"76.30566403269769","quantity":"9","payment_status":"0","order_status":"0","stripe_response":null,"stripe_balance_response":null,"converted_ammount":"21838.05","order_id":"Y9YUSB5Y5Y","created_at":"2018-09-07T10:54:43.000Z","updated_at":"2018-09-07T10:54:43.000Z","transaction_fee":"436.76099999999997","converted_transaction_fee":"436.76099999999997"}
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
         * id : 23
         * user_id : 293
         * sale_tool_id : 31
         * delivery : 1
         * delivery_type : 0
         * delivery_cost : 20740.05
         * converted_delivery_cost : 20740.05
         * primary_currency : GBP
         * price : 1098.00
         * total_amount : 21838.05
         * payment_mode : 1
         * address : Kharoudi, Haryana 136035, India,  Kaithal, Haryana, India.
         * latitude : 30.01322970477612
         * longitude : 76.30566403269769
         * quantity : 9
         * payment_status : 0
         * order_status : 0
         * stripe_response : null
         * stripe_balance_response : null
         * converted_ammount : 21838.05
         * order_id : Y9YUSB5Y5Y
         * created_at : 2018-09-07T10:54:43.000Z
         * updated_at : 2018-09-07T10:54:43.000Z
         * transaction_fee : 436.76099999999997
         * converted_transaction_fee : 436.76099999999997
         */

        private int id;
        private int user_id;
        private int sale_tool_id;
        private int delivery;
        private int delivery_type;
        private String delivery_cost;
        private String converted_delivery_cost;
        private String primary_currency;
        private String price;
        private String total_amount;
        private int payment_mode;
        private String address;
        private String latitude;
        private String longitude;
        private String quantity;
        private String payment_status;
        private String order_status;
        private Object stripe_response;
        private Object stripe_balance_response;
        private String converted_ammount;
        private String order_id;
        private String created_at;
        private String updated_at;
        private String transaction_fee;
        private String converted_transaction_fee;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getSale_tool_id() {
            return sale_tool_id;
        }

        public void setSale_tool_id(int sale_tool_id) {
            this.sale_tool_id = sale_tool_id;
        }

        public int getDelivery() {
            return delivery;
        }

        public void setDelivery(int delivery) {
            this.delivery = delivery;
        }

        public int getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(int delivery_type) {
            this.delivery_type = delivery_type;
        }

        public String getDelivery_cost() {
            return delivery_cost;
        }

        public void setDelivery_cost(String delivery_cost) {
            this.delivery_cost = delivery_cost;
        }

        public String getConverted_delivery_cost() {
            return converted_delivery_cost;
        }

        public void setConverted_delivery_cost(String converted_delivery_cost) {
            this.converted_delivery_cost = converted_delivery_cost;
        }

        public String getPrimary_currency() {
            return primary_currency;
        }

        public void setPrimary_currency(String primary_currency) {
            this.primary_currency = primary_currency;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public int getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(int payment_mode) {
            this.payment_mode = payment_mode;
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

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public Object getStripe_response() {
            return stripe_response;
        }

        public void setStripe_response(Object stripe_response) {
            this.stripe_response = stripe_response;
        }

        public Object getStripe_balance_response() {
            return stripe_balance_response;
        }

        public void setStripe_balance_response(Object stripe_balance_response) {
            this.stripe_balance_response = stripe_balance_response;
        }

        public String getConverted_ammount() {
            return converted_ammount;
        }

        public void setConverted_ammount(String converted_ammount) {
            this.converted_ammount = converted_ammount;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getTransaction_fee() {
            return transaction_fee;
        }

        public void setTransaction_fee(String transaction_fee) {
            this.transaction_fee = transaction_fee;
        }

        public String getConverted_transaction_fee() {
            return converted_transaction_fee;
        }

        public void setConverted_transaction_fee(String converted_transaction_fee) {
            this.converted_transaction_fee = converted_transaction_fee;
        }
    }
}
