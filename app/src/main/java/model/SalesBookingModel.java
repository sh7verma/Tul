package model;

import java.util.List;

/**
 * Created by dev on 17/9/18.
 */

public class SalesBookingModel extends BaseModel {


    /**
     * response : {"id":168,"user_id":308,"tool_id":51,"delivery":0,"delivery_type":0,"delivery_cost":"","converted_delivery_cost":"0.0","primary_currency":"GBP","price":"50.0","total_amount":"50.0","payment_mode":1,"address":"","latitude":"","longitude":"","quantity":"1","payment_status":"0","order_status":"1","converted_amount":"50.0","order_id":"YFHRFD0CXL","created_at":"2018-09-17 07:21","updated_at":"2018-09-17 07:21","transaction_fee":"5.0","seller_rating":0,"buyer_rating":0,"converted_transaction_fee":"5.0","currency":"£","title":"Availablr","attachment":[{"id":77,"tool_id":51,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15363129540.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1536312954_thumb0.jpg","type":"image"}],"owner_id":300,"owner":"Rocky Roxxe","first_name":"Rocky","last_name":"Roxxe","owner_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536297387.jpg","buyer_id":308,"buyer":"Sks Pvt ","buyer_first_name":"Sks Pvt","buyer_last_name":"","buyer_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536579263.jpg"}
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
         * id : 168
         * user_id : 308
         * tool_id : 51
         * delivery : 0
         * delivery_type : 0
         * delivery_cost :
         * converted_delivery_cost : 0.0
         * primary_currency : GBP
         * price : 50.0
         * total_amount : 50.0
         * payment_mode : 1
         * address :
         * latitude :
         * longitude :
         * quantity : 1
         * payment_status : 0
         * order_status : 1
         * converted_amount : 50.0
         * order_id : YFHRFD0CXL
         * created_at : 2018-09-17 07:21
         * updated_at : 2018-09-17 07:21
         * transaction_fee : 5.0
         * seller_rating : 0
         * buyer_rating : 0
         * converted_transaction_fee : 5.0
         * currency : £
         * title : Availablr
         * attachment : [{"id":77,"tool_id":51,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15363129540.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1536312954_thumb0.jpg","type":"image"}]
         * owner_id : 300
         * owner : Rocky Roxxe
         * first_name : Rocky
         * last_name : Roxxe
         * owner_pic : https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536297387.jpg
         * buyer_id : 308
         * buyer : Sks Pvt
         * buyer_first_name : Sks Pvt
         * buyer_last_name :
         * buyer_pic : https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1536579263.jpg
         */

        private int id;
        private int user_id;
        private int tool_id;
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
        private String converted_amount;
        private String order_id;
        private String created_at;
        private String updated_at;
        private String transaction_fee;
        private int seller_rating;
        private int buyer_rating;
        private String converted_transaction_fee;
        private String currency;
        private String title;
        private int owner_id;
        private String owner;
        private String first_name;
        private String last_name;
        private String owner_pic;
        private int buyer_id;
        private String buyer;
        private String buyer_first_name;
        private String buyer_last_name;
        private String buyer_pic;
        private List<AttachmentBean> attachment;

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

        public int getTool_id() {
            return tool_id;
        }

        public void setTool_id(int tool_id) {
            this.tool_id = tool_id;
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

        public String getConverted_amount() {
            return converted_amount;
        }

        public void setConverted_amount(String converted_amount) {
            this.converted_amount = converted_amount;
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

        public int getSeller_rating() {
            return seller_rating;
        }

        public void setSeller_rating(int seller_rating) {
            this.seller_rating = seller_rating;
        }

        public int getBuyer_rating() {
            return buyer_rating;
        }

        public void setBuyer_rating(int buyer_rating) {
            this.buyer_rating = buyer_rating;
        }

        public String getConverted_transaction_fee() {
            return converted_transaction_fee;
        }

        public void setConverted_transaction_fee(String converted_transaction_fee) {
            this.converted_transaction_fee = converted_transaction_fee;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(int owner_id) {
            this.owner_id = owner_id;
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

        public int getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(int buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getBuyer() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer = buyer;
        }

        public String getBuyer_first_name() {
            return buyer_first_name;
        }

        public void setBuyer_first_name(String buyer_first_name) {
            this.buyer_first_name = buyer_first_name;
        }

        public String getBuyer_last_name() {
            return buyer_last_name;
        }

        public void setBuyer_last_name(String buyer_last_name) {
            this.buyer_last_name = buyer_last_name;
        }

        public String getBuyer_pic() {
            return buyer_pic;
        }

        public void setBuyer_pic(String buyer_pic) {
            this.buyer_pic = buyer_pic;
        }

        public List<AttachmentBean> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<AttachmentBean> attachment) {
            this.attachment = attachment;
        }

        public static class AttachmentBean {
            /**
             * id : 77
             * tool_id : 51
             * attachment : https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15363129540.jpg
             * thumbnail : https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1536312954_thumb0.jpg
             * type : image
             */

            private int id;
            private int tool_id;
            private String attachment;
            private String thumbnail;
            private String type;

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
        }
    }
}
