package model;

import java.util.List;

/**
 * Created by dev on 22/8/18.
 */

public class SalesSellerModel {


    /**
     * response : [{"id":190,"username":" ","first_name":"","last_name":"","latitude":"30.6983052","longitude":"76.6273402"},{"id":187,"username":"Vbzbzb Hsjsj","first_name":"Vbzbzb","last_name":"Hsjsj","latitude":"30.7350626","longitude":"76.6933172"}]
     * code : 111
     */

    private int code;
    private List<ResponseBean> response;
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public static class ResponseBean {
        /**
         * id : 190
         * username :
         * first_name :
         * last_name :
         * latitude : 30.6983052
         * longitude : 76.6273402
         */

        private int id;
        private String username;
        private String first_name;
        private String last_name;
        private String latitude;
        private String longitude;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
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
    }
}
