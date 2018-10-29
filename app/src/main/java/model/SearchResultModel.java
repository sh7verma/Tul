package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.List;

/**
 * Created by applify on 12/19/2017.
 */

public class SearchResultModel extends BaseModel {
    /**
     * response : [{"id":1,"title":"iOS Tul","latitude":"30.657797735213","longitude":"76.7327738833397","rating":5,"attachment":[{"id":1,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513242731_thumb0.jpeg","tool_id":1,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132427310.jpeg","type":"image"}]},{"id":6,"title":"Jj Hfh","latitude":"30.6578093682907","longitude":"76.7327374102679","rating":1,"attachment":[{"id":6,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513252792_thumb0.jpeg","tool_id":6,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132527920.jpeg","type":"image"}]},{"id":8,"title":"Uxfyivovig Bkb K","latitude":"30.657784","longitude":"76.7327231","rating":0,"attachment":[{"id":8,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513253947_thumb0.jpg","tool_id":8,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132539470.jpg","type":"image"}]},{"id":7,"title":"Aish Tul","latitude":"30.6577836","longitude":"76.7327229","rating":0,"attachment":[{"id":7,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513253668_thumb0.jpg","tool_id":7,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132536680.jpg","type":"image"}]},{"id":18,"title":"Ifiyfyi","latitude":"30.6577627","longitude":"76.7327461","rating":0,"attachment":[{"id":22,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513587928_thumb0.jpg","tool_id":18,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15135879280.jpg","type":"image"}]},{"id":3,"title":"Android Tul","latitude":"30.6577981","longitude":"76.7327016","rating":5,"attachment":[{"id":3,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513242840_thumb0.jpg","tool_id":3,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132428400.jpg","type":"image"}]},{"id":24,"title":"Abc3","latitude":"30.6577713","longitude":"76.7327238","rating":0,"attachment":[{"id":28,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513666489_thumb0.jpg","tool_id":24,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15136664890.jpg","type":"image"}]},{"id":10,"title":"Xjgxgjjcg","latitude":"30.6577531015786","longitude":"76.7327338178425","rating":5,"attachment":[{"id":10,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513257457_thumb0.jpeg","tool_id":10,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132574570.jpeg","type":"image"}]},{"id":5,"title":"So Anna Ananan","latitude":"30.6577430432948","longitude":"76.7326994520395","rating":0,"attachment":[{"id":5,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513250054_thumb0.jpeg","tool_id":5,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132500540.jpeg","type":"image"}]},{"id":11,"title":"Tshshhdbdjhd","latitude":"30.6577018462407","longitude":"76.7326619849324","rating":0,"attachment":[{"id":11,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513258371_thumb0.jpeg","tool_id":11,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132583710.jpeg","type":"image"}]},{"id":4,"title":"Bsbbsbdnndndn","latitude":"30.6574998423743","longitude":"76.7326880526512","rating":0,"attachment":[{"id":4,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513247874_thumb0.jpeg","tool_id":4,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132478740.jpeg","type":"image"}]},{"id":19,"title":"Admin Testing","latitude":"30.6892166137695","longitude":"76.7311096191406","rating":0,"attachment":[{"id":23,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513589628_thumb0.jpeg","tool_id":19,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15135896280.jpeg","type":"image"}]},{"id":21,"title":"Adminnn Testing ����","latitude":"30.7788797","longitude":"76.723687","rating":3,"attachment":[{"id":25,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513592999_thumb0.jpeg","tool_id":21,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15135929990.jpeg","type":"image"}]},{"id":13,"title":"Sander ","latitude":"25.7849668003375","longitude":"-80.1300092786551","rating":0,"attachment":[{"id":13,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513265609_thumb0.jpeg","tool_id":13,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132656090.jpeg","type":"image"}]},{"id":22,"title":"Chciicgickcgicg","latitude":"15.8676","longitude":"77.744","rating":0,"attachment":[{"id":26,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513599579_thumb0.jpeg","tool_id":22,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15135995790.jpeg","type":"image"}]},{"id":12,"title":"Drill","latitude":"52.0455070073536","longitude":"-0.758840851485729","rating":0,"attachment":[{"id":12,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513265210_thumb0.jpeg","tool_id":12,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132652100.jpeg","type":"image"}]}]
     * code : 403
     */

    private int code;
    private List<ResponseBean> response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean implements Parcelable, ClusterItem {
        /**
         * id : 1
         * title : iOS Tul
         * latitude : 30.657797735213
         * longitude : 76.7327738833397
         * rating : 5
         * attachment : [{"id":1,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools_thumbnail/1513242731_thumb0.jpeg","tool_id":1,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15132427310.jpeg","type":"image"}]
         */

        private int id;
        private int user_id;
        private String title;
        private String latitude;
        private String longitude;
        private String price;
        private String owner;
        private String owner_pic;
        private String currency;
        private int rating;
        private List<AttachmentModel> attachment;
        public ResponseBean() {
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public LatLng getPosition() {
            return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        @Override
        public String getSnippet() {
            return null;
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

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getOwnerPic() {
            return owner_pic;
        }

        public void setOwnerPic(String ownerPic) {
            this.owner_pic = ownerPic;
        }

        public List<AttachmentModel> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<AttachmentModel> attachment) {
            this.attachment = attachment;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.user_id);
            dest.writeString(this.title);
            dest.writeString(this.latitude);
            dest.writeString(this.longitude);
            dest.writeString(this.price);
            dest.writeString(this.owner);
            dest.writeString(this.owner_pic);
            dest.writeString(this.currency);
            dest.writeInt(this.rating);
            dest.writeTypedList(this.attachment);
        }

        protected ResponseBean(Parcel in) {
            this.id = in.readInt();
            this.user_id = in.readInt();
            this.title = in.readString();
            this.latitude = in.readString();
            this.longitude = in.readString();
            this.price = in.readString();
            this.owner = in.readString();
            this.owner_pic = in.readString();
            this.currency = in.readString();
            this.rating = in.readInt();
            this.attachment = in.createTypedArrayList(AttachmentModel.CREATOR);
        }

        public static final Creator<ResponseBean> CREATOR = new Creator<ResponseBean>() {
            @Override
            public ResponseBean createFromParcel(Parcel source) {
                return new ResponseBean(source);
            }

            @Override
            public ResponseBean[] newArray(int size) {
                return new ResponseBean[size];
            }
        };
    }
}
