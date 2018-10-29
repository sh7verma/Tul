package model;

import java.util.List;

/**
 * Created by applify on 11/8/2017.
 */

public class AccountModel extends BaseModel{

    /**
     * response : [{"account_id":19,"account_number":"00012345","account":"acct_1BLkmhJhQ3Qr3O6X","first_name":"Rajat","last_name":"Arora","bank_name":"STRIPE TEST BANK","currency":"gbp","state":"Dhdh","sort_code":"108800","postal_code":"Ca103","dob":"08-11-2004","country_code":"GB","city":"Dggd","address":"Test","is_primary":0,"amount":0,"created_at":"2017-11-08T04:03:50.000Z","updated_at":"2017-11-08T04:03:50.000Z"},{"account_id":4,"account_number":"00012345","account":"acct_1BLUnSHe282bUrXk","first_name":"Rajat","last_name":"Arora","bank_name":"STRIPE TEST BANK","currency":"gbp","state":"Dggd","sort_code":"108800","postal_code":"Ca103","dob":"07-11-2004","country_code":"GB","city":"Bxbd","address":"Dhsh","is_primary":1,"amount":0,"created_at":"2017-11-07T10:59:32.000Z","updated_at":"2017-11-07T10:59:32.000Z"}]
     * code : 421
     */

    private int code;
    private List<CreateStripeAccountModel.ResponseBean> response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CreateStripeAccountModel.ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<CreateStripeAccountModel.ResponseBean> response) {
        this.response = response;
    }

}
