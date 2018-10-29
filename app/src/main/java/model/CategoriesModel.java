package model;

import java.util.List;



public class CategoriesModel extends BaseModel{

    private List<ResponseBean> response;

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 1
         * category_name : HAND TULS
         * image : https://s3.ap-south-1.amazonaws.com/kittydev/tul/category/handtool.png
         */

        private int id;
        private String category_name;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
