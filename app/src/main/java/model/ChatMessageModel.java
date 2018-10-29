package model;

/**
 * Created by applify on 8/3/2017.
 */

public class ChatMessageModel {

    private String chat_dialog_id;
    private String message;
    private String message_id;
    private String message_time;
    private String sender_id;
    private int message_status;
    private boolean is_header;
    private String show_HeaderText;

    public String getShow_message_datetime() {
        return show_message_datetime;
    }

    public void setShow_message_datetime(String show_message_datetime) {
        this.show_message_datetime = show_message_datetime;
    }

    public String getShow_HeaderText() {
        return show_HeaderText;
    }

    public void setShow_HeaderText(String show_HeaderText) {
        this.show_HeaderText = show_HeaderText;
    }


    private String show_message_datetime;

    public ChatMessageModel() {

    }

    public String getChat_dialog_id() {
        return chat_dialog_id;
    }

    public void setChat_dialog_id(String chat_dialog_id) {
        this.chat_dialog_id = chat_dialog_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public Integer getMessage_status() {
        return message_status;
    }

    public void setMessage_status(Integer message_status) {
        this.message_status = message_status;
    }

    public boolean is_header() {
        return is_header;
    }

    public void setIs_header(boolean is_header) {
        this.is_header = is_header;
    }
}
