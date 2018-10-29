package model;

/**
 * Created by applify on 1/1/2018.
 */

public class ChatNotificationModel {

    private String user_id;
    private String name;
    private String push_token;
    private String profile_pic;
    private String badge_count;
    private String chat_dialog_id;
    private String message;
    private String participant_ids;
    private String platform_status;
    private String mute;
    private String messages_notification;

    public String getMessages_notification() {
        return messages_notification;
    }

    public void setMessages_notification(String messages_notification) {
        this.messages_notification = messages_notification;
    }

    public String getMute() {
        return mute;
    }

    public void setMute(String mute) {
        this.mute = mute;
    }

    public ChatNotificationModel() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPush_token() {
        return push_token;
    }

    public void setPush_token(String push_token) {
        this.push_token = push_token;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getBadge_count() {
        return badge_count;
    }

    public void setBadge_count(String badge_count) {
        this.badge_count = badge_count;
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

    public String getParticipant_ids() {
        return participant_ids;
    }

    public void setParticipant_ids(String participant_ids) {
        this.participant_ids = participant_ids;
    }

    public String getPlatform_status() {
        return platform_status;
    }

    public void setPlatform_status(String platform_status) {
        this.platform_status = platform_status;
    }

}
