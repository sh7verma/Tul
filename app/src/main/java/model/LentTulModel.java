package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dev on 20/11/17.
 */

public class LentTulModel extends BaseModel{


    private int code;
    private List<BookTulModel.ResponseBean> response;

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

}
