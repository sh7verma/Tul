package model;

import java.util.Map;

/**
 * Created by system009 on 18/12/17.
 */
public class ChatProfileModel {

    private String access_token;
    private Map<String, String> chat_dialog_ids;
    private String name;
    private String first_name;
    private String last_name;
    private String profile_pic;
    private String push_token;
    private String block_status;

    private String user_id;
    private String online_status;
    private String badge_count;

    private String platform_status;



    public String getMessages_notification() {
        return messages_notification;
    }

    public void setMessages_notification(String messages_notification) {
        this.messages_notification = messages_notification;
    }

    private String messages_notification;


    public ChatProfileModel() {

    }

    public String getBlock_status() {
        return block_status;
    }

    public void setBlock_status(String block_status) {
        this.block_status = block_status;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<String, String> getChat_dialog_ids() {
        return chat_dialog_ids;
    }

    public void setChat_dialog_ids(Map<String, String> chat_dialog_ids) {
        this.chat_dialog_ids = chat_dialog_ids;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getPush_token() {
        return push_token;
    }

    public void setPush_token(String push_token) {
        this.push_token = push_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBadge_count() {
        return badge_count;
    }

    public void setBadge_count(String badge_count) {
        this.badge_count = badge_count;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }



    public String getPlatform_status() {
        return platform_status;
    }

    public void setPlatform_status(String platform_status) {
        this.platform_status = platform_status;
    }
}
