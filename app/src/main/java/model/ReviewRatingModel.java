package model;

import java.util.List;

/**
 * Created by dev on 27/12/17.
 */

public class ReviewRatingModel extends BaseModel {


    /**
     * response : [{"id":2,"date":"21-12-2017","rating":5,"review":"","username":"Jasjeet Singh","user_pic":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1513752726.jpg"},{"id":4,"date":"22-12-2017","rating":5,"review":"","username":"Jasjeet Singh","user_pic":"https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1513752726.jpg"}]
     * count : 2
     * avg_rating : 5
     * code : 111
     */

    private int count;
    private int avg_rating;
    private int code;
    private List<ResponseBean> response;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(int avg_rating) {
        this.avg_rating = avg_rating;
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
         * id : 2
         * date : 21-12-2017
         * rating : 5
         * review :
         * username : Jasjeet Singh
         * user_pic : https://s3.ap-south-1.amazonaws.com/kittydev/tul/profile_pic/1513752726.jpg
         */

        private int id;
        private String date;
        private int rating;
        private String review;
        private String username;
        private String user_pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }
    }
}
