package model;

/**
 * Created by dev on 6/11/17.
 */

public class CardLocalModel extends BaseModel {

    /**
     * response : {"id":1,"created_at":"2017-11-02T08:43:30.000Z","updated_at":"2017-11-02T08:43:30.000Z","user_id":"21","card_number":"4242424242424242","name_on_card":"shivam jindal","expiry_month":1,"expiry_year":2018}
     * code : 303
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
         * id : 1
         * created_at : 2017-11-02T08:43:30.000Z
         * updated_at : 2017-11-02T08:43:30.000Z
         * user_id : 21
         * card_number : 4242424242424242
         * name_on_card : shivam jindal
         * expiry_month : 1
         * expiry_year : 2018
         */

        private int id;
        private String created_at;
        private String updated_at;
        private String user_id;
        private String card_number;
        private String name_on_card;
        private int expiry_month;
        private int expiry_year;
        private boolean is_selected;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCard_number() {
            if(card_number==null){
                return "";
            }
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }

        public String getName_on_card() {
            if(name_on_card==null){
                return "";
            }
            return name_on_card;
        }

        public void setName_on_card(String name_on_card) {
            this.name_on_card = name_on_card;
        }

        public int getExpiry_month() {

            return expiry_month;
        }

        public void setExpiry_month(int expiry_month) {
            this.expiry_month = expiry_month;
        }

        public int getExpiry_year() {
            return expiry_year;
        }

        public void setExpiry_year(int expiry_year) {
            this.expiry_year = expiry_year;
        }

        public boolean is_selected() {
            return is_selected;
        }

        public void setIs_selected(boolean is_selected) {
            this.is_selected = is_selected;
        }
    }
}
