package model;

import java.util.List;

/**
 * Created by applify on 11/16/2017.
 */
public class AvailbiltyModel extends BaseModel{

    /**
     * response : ["17-11-2017","18-11-2017","19-11-2017","20-11-2017"]
     * code : 201
     */

    private int code;
    private List<String> response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }
}
