package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dev on 22/8/18.
 */

public class SalesListingTulModel implements Parcelable {
    /**
     * response : [{"id":2,"title":"dummy","user_id":190,"owner":" ","first_name":"","last_name":"","owner_pic":"","category_id":"3","edit_count":0,"category_name":"ENGINE POWERED TULs","description":"dummy","quantity":10,"sold":0,"currency":"£","price":"100","address":"asfasfas","latitude":"","longitude":"","condition":"new","attachment":[{"id":5,"tool_id":2,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":6,"tool_id":2,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":7,"tool_id":2,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":8,"tool_id":2,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"}],"rating":0,"user_rating":null,"created_at":null,"is_wishlisted":0},{"tool_id":1,"title":"dummy","id":187,"owner":"Vbzbzb Hsjsj","first_name":"Vbzbzb","last_name":"Hsjsj","owner_pic":"https://s3.eu-west-2.amazonaws.com/tul-uploads/profile_pics/1534766016.jpeg","category_id":"3","edit_count":0,"category_name":"ENGINE POWERED TULs","description":"dummy","quantity":10,"sold":0,"currency":"£","price":"100","address":"asfasfas","latitude":"","longitude":"","condition":"new","attachment":[{"id":1,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":2,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":3,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"},{"id":4,"tool_id":1,"attachment":"https://s3.eu-west-2.amazonaws.com/tul-uploads/tools/15168651700.jpg","thumbnail":"https://s3.eu-west-2.amazonaws.com/tul-uploads/thumbnail/1516368280_thumb0.jpg","type":"image"}],"rating":0,"user_rating":null,"created_at":null,"is_wishlisted":0}]
     * code : 111
     */

    private int code;
    private List<SalesTulDetailModel.ResponseBean> response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SalesTulDetailModel.ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<SalesTulDetailModel.ResponseBean> response) {
        this.response = response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeTypedList(this.response);
    }

    public SalesListingTulModel() {
    }

    protected SalesListingTulModel(Parcel in) {
        this.code = in.readInt();
        this.response = in.createTypedArrayList(SalesTulDetailModel.ResponseBean.CREATOR);
    }

    public static final Parcelable.Creator<SalesListingTulModel> CREATOR = new Parcelable.Creator<SalesListingTulModel>() {
        @Override
        public SalesListingTulModel createFromParcel(Parcel source) {
            return new SalesListingTulModel(source);
        }

        @Override
        public SalesListingTulModel[] newArray(int size) {
            return new SalesListingTulModel[size];
        }
    };
}
