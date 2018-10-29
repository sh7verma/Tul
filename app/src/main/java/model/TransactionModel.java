package model;

/**
 * Created by applify on 1/31/2018.
 */

public class TransactionModel extends BaseModel{


    /**
     * response : 7
     * code : 111
     */

    private String response;
    private int code;
    private int is_email_skip;
    private int email_verify;
    private int block_status;

    public int getBlock_status() {
        return block_status;
    }

    public void setBlock_status(int block_status) {
        this.block_status = block_status;
    }

    public int getIs_email_skip() {
        return is_email_skip;
    }

    public void setIs_email_skip(int is_email_skip) {
        this.is_email_skip = is_email_skip;
    }

    public int getEmail_verify() {
        return email_verify;
    }

    public void setEmail_verify(int email_verify) {
        this.email_verify = email_verify;
    }



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
