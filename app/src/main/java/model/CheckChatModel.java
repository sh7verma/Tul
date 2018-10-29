package model;

/**
 * Created by dev on 7/3/18.
 */

public class CheckChatModel extends BaseModel {

    /**
     * response : {"is_chat_block":0,"user_block_status":0,"owner_block_status":0,"admin_pause_status":0}
     * code : 111
     */

    private ResponseBean response;
    private int code;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResponseBean {
        /**
         * is_chat_block : 0
         * user_block_status : 0
         * owner_block_status : 0
         * admin_pause_status : 0
         */

        private int is_chat_block;
        private int user_block_status;
        private int owner_block_status;
        private int admin_pause_status;

        public int getIs_chat_block() {
            return is_chat_block;
        }

        public void setIs_chat_block(int is_chat_block) {
            this.is_chat_block = is_chat_block;
        }

        public int getUser_block_status() {
            return user_block_status;
        }

        public void setUser_block_status(int user_block_status) {
            this.user_block_status = user_block_status;
        }

        public int getOwner_block_status() {
            return owner_block_status;
        }

        public void setOwner_block_status(int owner_block_status) {
            this.owner_block_status = owner_block_status;
        }

        public int getAdmin_pause_status() {
            return admin_pause_status;
        }

        public void setAdmin_pause_status(int admin_pause_status) {
            this.admin_pause_status = admin_pause_status;
        }
    }
}
