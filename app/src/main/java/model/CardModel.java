package model;

import java.util.List;

/**
 * Created by dev on 31/10/17.
 */

public class CardModel extends BaseModel {


    /**
     * response : [{"id":13,"user_id":9,"card_number":"4242 4242 4242 4247","name_on_card":"Shubam","expiry_month":11,"expiry_year":2022,"created_at":"2017-11-02T07:21:15.000Z","updated_at":"2017-11-02T07:21:15.000Z"}]
     * code : 302
     */

    private int code;
    private List<CardLocalModel.ResponseBean> response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CardLocalModel.ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<CardLocalModel.ResponseBean> response) {
        this.response = response;
    }

}
