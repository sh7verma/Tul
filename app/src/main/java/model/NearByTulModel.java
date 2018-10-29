package model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.List;

/**
 * Created by applify on 11/1/2017.
 */
public class NearByTulModel extends BaseModel {


    /**
     * response : [{"id":4,"latitude":"30.6579061","longitude":"76.7304161","category_id":1,"category_name":"HAND TULS"},{"id":10,"latitude":"30.6577903","longitude":"76.732739","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":12,"latitude":"30.6577903","longitude":"76.732739","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":19,"latitude":"30.6577903","longitude":"76.732739","category_id":2,"category_name":"POWER TULS"},{"id":25,"latitude":"30.6577705","longitude":"76.7327616","category_id":2,"category_name":"POWER TULS"},{"id":26,"latitude":"30.6577705","longitude":"76.7327616","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":32,"latitude":"30.6577218","longitude":"76.7327165","category_id":2,"category_name":"POWER TULS"},{"id":33,"latitude":"30.6577705","longitude":"76.7327616","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":34,"latitude":"30.65777050783961","longitude":"76.73276156187057","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":39,"latitude":"30.6577","longitude":"76.7327","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":40,"latitude":"30.6577705","longitude":"76.7327616","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":41,"latitude":"30.6580179","longitude":"76.7329383","category_id":2,"category_name":"POWER TULS"},{"id":42,"latitude":"30.6577705","longitude":"76.7327616","category_id":2,"category_name":"POWER TULS"},{"id":43,"latitude":"30.690889","longitude":"76.737531","category_id":1,"category_name":"HAND TULS"},{"id":44,"latitude":"30.6578178557727","longitude":"76.7327272395435","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":45,"latitude":"30.6578098093841","longitude":"76.7327214413136","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":46,"latitude":"30.6347","longitude":"76.7057","category_id":2,"category_name":"POWER TULS"},{"id":47,"latitude":"30.6578","longitude":"76.7326","category_id":1,"category_name":"HAND TULS"},{"id":48,"latitude":"30.6577978","longitude":"76.7327052","category_id":1,"category_name":"HAND TULS"},{"id":49,"latitude":"30.6578433746757","longitude":"76.7327755597203","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":50,"latitude":"30.65781","longitude":"76.7327165","category_id":1,"category_name":"HAND TULS"},{"id":52,"latitude":"30.6577705","longitude":"76.7327616","category_id":1,"category_name":"HAND TULS"},{"id":53,"latitude":"30.6577705","longitude":"76.7327616","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":54,"latitude":"30.6577705","longitude":"76.7327616","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":55,"latitude":"30.65777050783961","longitude":"76.73276156187057","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":56,"latitude":"30.6578077263796","longitude":"76.7327200442383","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":57,"latitude":"30.6578747649031","longitude":"76.7327881325751","category_id":2,"category_name":"POWER TULS"},{"id":58,"latitude":"30.6578535710425","longitude":"76.7326703667641","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":60,"latitude":"30.6577705","longitude":"76.7327616","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":62,"latitude":"30.6578150019335","longitude":"76.7327607237517","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":63,"latitude":"30.6577705","longitude":"76.7327616","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":64,"latitude":"30.6577705","longitude":"76.7327616","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":65,"latitude":"30.6577698","longitude":"76.7327549","category_id":2,"category_name":"POWER TULS"},{"id":66,"latitude":"30.6577838212537","longitude":"76.7325375974893","category_id":2,"category_name":"POWER TULS"},{"id":67,"latitude":"43.7046691496332","longitude":"7.22532399930733","category_id":2,"category_name":"POWER TULS"},{"id":68,"latitude":"30.6578404829192","longitude":"76.7327037268102","category_id":3,"category_name":"ENGINE POWERED TULS"},{"id":70,"latitude":"30.6578","longitude":"76.7329","category_id":2,"category_name":"POWER TULS"},{"id":71,"latitude":"30.657866261247772","longitude":"76.73272769898176","category_id":2,"category_name":"POWER TULS"},{"id":72,"latitude":"30.6577705","longitude":"76.7327616","category_id":3,"category_name":"ENGINE POWERED TULS"}]
     * code : 403
     */

    private int code;
    private List<ResponseBean> response;
    private CountBean count;

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
        this.count = count;
    }

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

    public static class ResponseBean implements ClusterItem {
        /**
         * id : 4
         * latitude : 30.6579061
         * longitude : 76.7304161
         * category_id : 1
         * category_name : HAND TULS
         */

        private int id;
        private String latitude;
        private String longitude;
        private int category_id;
        private String category_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        @Override
        public LatLng getPosition() {
            return position();
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public String getSnippet() {
            return null;
        }


        private LatLng position() {
            return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        }

    }

    public static class CountBean {
        /**
         * my_bookings_count : 1
         * tool_lent_count : 2
         */

        private int my_bookings_count;
        private int tool_lent_count;
        private int rating;
        private int unread_count;

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getUnread_count() {
            return unread_count;
        }

        public void setUnread_count(int unread_count) {
            this.unread_count = unread_count;
        }

        public int getMy_bookings_count() {
            return my_bookings_count;
        }

        public void setMy_bookings_count(int my_bookings_count) {
            this.my_bookings_count = my_bookings_count;
        }

        public int getTool_lent_count() {
            return tool_lent_count;
        }

        public void setTool_lent_count(int tool_lent_count) {
            this.tool_lent_count = tool_lent_count;
        }
    }



}
