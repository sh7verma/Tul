package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 4/12/17.
 */

public class DashboardMyTulModel extends BaseModel implements Parcelable {


    /**
     * response : [{"tool_id":106,"title":"Tul Tul Tul","total_bookings":4,"total_earning":1210,"price":"10","currency":"£","views":0,"wishlist_count":0,"attachment":[{"id":121,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1511860582_thumb0.jpg","tool_id":106,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15118605820.jpg","type":"image"}]},{"tool_id":108,"title":"Tul 2","total_bookings":15,"total_earning":110,"price":"10","currency":"£","views":0,"wishlist_count":1,"attachment":[{"id":123,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1511861842_thumb0.jpg","tool_id":108,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15118618420.jpg","type":"image"}]},{"tool_id":121,"title":"Del","total_bookings":1,"total_earning":5,"price":"5","currency":"£","views":0,"wishlist_count":0,"attachment":[{"id":137,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1511965099_thumb0.jpg","tool_id":121,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15119650990.jpg","type":"image"}]}]
     * code : 111
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

    public static class ResponseBean {
        /**
         * tool_id : 106
         * title : Tul Tul Tul
         * total_bookings : 4
         * total_earning : 1210
         * price : 10
         * currency : £
         * views : 0
         * wishlist_count : 0
         * attachment : [{"id":121,"thumbnail":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1511860582_thumb0.jpg","tool_id":106,"attachment":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15118605820.jpg","type":"image"}]
         */

        private int tool_id;
        private String title;
        private int total_bookings;
        private float total_earning;
        private String price;
        private String currency;
        private int views;
        private int wishlist_count;
        private List<AttachmentBean> attachment;

        public int getTool_id() {
            return tool_id;
        }

        public void setTool_id(int tool_id) {
            this.tool_id = tool_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTotal_bookings() {
            return total_bookings;
        }

        public void setTotal_bookings(int total_bookings) {
            this.total_bookings = total_bookings;
        }

        public float getTotal_earning() {
            return total_earning;
        }

        public void setTotal_earning(int total_earning) {
            this.total_earning = total_earning;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getWishlist_count() {
            return wishlist_count;
        }

        public void setWishlist_count(int wishlist_count) {
            this.wishlist_count = wishlist_count;
        }

        public List<AttachmentBean> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<AttachmentBean> attachment) {
            this.attachment = attachment;
        }

        public static class AttachmentBean {
            /**
             * id : 121
             * thumbnail : https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/1511860582_thumb0.jpg
             * tool_id : 106
             * attachment : https://s3.ap-south-1.amazonaws.com/kittydev/tul/tools/15118605820.jpg
             * type : image
             */

            private int id;
            private String thumbnail;
            private int tool_id;
            private String attachment;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeList(this.response);
    }

    public DashboardMyTulModel() {
    }

    protected DashboardMyTulModel(Parcel in) {
        this.code = in.readInt();
        this.response = new ArrayList<ResponseBean>();
        in.readList(this.response, ResponseBean.class.getClassLoader());
    }

    public static final Creator<DashboardMyTulModel> CREATOR = new Creator<DashboardMyTulModel>() {
        @Override
        public DashboardMyTulModel createFromParcel(Parcel source) {
            return new DashboardMyTulModel(source);
        }

        @Override
        public DashboardMyTulModel[] newArray(int size) {
            return new DashboardMyTulModel[size];
        }
    };
}
