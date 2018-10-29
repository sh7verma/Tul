package model;


/**
 * Created by applify on 6/6/2017.
 */

public abstract class BaseModel  {
    public Error error;

    public class Error {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
