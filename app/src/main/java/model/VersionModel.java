package model;

/**
 * Created by applify on 1/19/2018.
 */

public class VersionModel extends BaseModel{


    /**
     * response : {"version":"1.0","update":""}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * version : 1.0
         * update :
         */

        private String version;
        private String update;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }
    }
}
