package model;

/**
 * Created by dev on 6/11/17.
 */

public class DeleteCardModel extends BaseModel{

    /**
     * response : Card successfully deleted
     * code : 302
     */

    private String response;
    private int code;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
