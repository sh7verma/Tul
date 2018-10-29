package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by applify on 8/3/2017.
 */

public class ChatDialogModel implements Parcelable {

    private Map<String, String> access_token;
    private String chat_dialog_id;
    private String last_message;
    private String last_message_id;
    private String last_message_sender_id;
    private String last_message_time;
    private String participant_ids;
    private String dialog_created_time;

    public String getDialog_created_time() {
        return dialog_created_time;
    }

    public void setDialog_created_time(String dialog_created_time) {
        this.dialog_created_time = dialog_created_time;
    }

    private Map<String, String> delete_dialog_time;
    private Map<String, String> platform_status;
    private Map<String, String> profile_pic;
    private Map<String, String> push_token;
    private Map<String, String> unread_count;
    private Map<String, String> name;
    private Map<String, String> mute;
    private Map<String, String> delete_outer_dialog;

    public Map<String, String> getBlock_status() {
        return block_status;
    }

    public void setBlock_status(Map<String, String> block_status) {
        this.block_status = block_status;
    }

    private Map<String, String> block_status;

    public Map<String, String> getMute() {
        return mute;
    }

    public void setMute(Map<String, String> mute) {
        this.mute = mute;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public Map<String, String> getDelete_outer_dialog() {
        return delete_outer_dialog;
    }

    public void setDelete_outer_dialog(Map<String, String> delete_outer_dialog) {
        this.delete_outer_dialog = delete_outer_dialog;
    }

    public ChatDialogModel() {

    }

    public Map<String, String> getAccess_token() {
        return access_token;
    }

    public void setAccess_token(Map<String, String> access_token) {
        this.access_token = access_token;
    }

    public String getChat_dialog_id() {
        return chat_dialog_id;
    }

    public void setChat_dialog_id(String chat_dialog_id) {
        this.chat_dialog_id = chat_dialog_id;
    }


    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_message_id() {
        return last_message_id;
    }

    public void setLast_message_id(String last_message_id) {
        this.last_message_id = last_message_id;
    }

    public String getLast_message_sender_id() {
        return last_message_sender_id;
    }

    public void setLast_message_sender_id(String last_message_sender_id) {
        this.last_message_sender_id = last_message_sender_id;
    }

    public String getLast_message_time() {
        return last_message_time;
    }

    public void setLast_message_time(String last_message_time) {
        this.last_message_time = last_message_time;
    }

    public String getParticipant_ids() {
        return participant_ids;
    }

    public void setParticipant_ids(String participant_ids) {
        this.participant_ids = participant_ids;
    }

    public Map<String, String> getDelete_dialog_time() {
        return delete_dialog_time;
    }

    public void setDelete_dialog_time(Map<String, String> delete_dialog_time) {
        this.delete_dialog_time = delete_dialog_time;
    }

    public Map<String, String> getPlatform_status() {
        return platform_status;
    }

    public void setPlatform_status(Map<String, String> platform_status) {
        this.platform_status = platform_status;
    }

    public Map<String, String> getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(Map<String, String> profile_pic) {
        this.profile_pic = profile_pic;
    }

    public Map<String, String> getPush_token() {
        return push_token;
    }

    public void setPush_token(Map<String, String> push_token) {
        this.push_token = push_token;
    }

    public Map<String, String> getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(Map<String, String> unread_count) {
        this.unread_count = unread_count;
    }

    public static ChatDialogModel parseChat(DataSnapshot dataSnapshot) {
        dataSnapshot.getValue();
        ChatDialogModel msg = new ChatDialogModel();
        msg.setChat_dialog_id(dataSnapshot.child("chat_dialog_id").getValue(String.class));
        return msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.access_token.size());
        for (Map.Entry<String, String> entry : this.access_token.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(this.chat_dialog_id);
        dest.writeString(this.last_message);
        dest.writeString(this.last_message_id);
        dest.writeString(this.last_message_sender_id);
        dest.writeString(this.last_message_time);
        dest.writeString(this.participant_ids);
        dest.writeString(this.dialog_created_time);
        dest.writeInt(this.delete_dialog_time.size());
        for (Map.Entry<String, String> entry : this.delete_dialog_time.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.platform_status.size());
        for (Map.Entry<String, String> entry : this.platform_status.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.profile_pic.size());
        for (Map.Entry<String, String> entry : this.profile_pic.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.push_token.size());
        for (Map.Entry<String, String> entry : this.push_token.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.unread_count.size());
        for (Map.Entry<String, String> entry : this.unread_count.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.name.size());
        for (Map.Entry<String, String> entry : this.name.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.mute.size());
        for (Map.Entry<String, String> entry : this.mute.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.delete_outer_dialog.size());
        for (Map.Entry<String, String> entry : this.delete_outer_dialog.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.block_status.size());
        for (Map.Entry<String, String> entry : this.block_status.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    protected ChatDialogModel(Parcel in) {
        int access_tokenSize = in.readInt();
        this.access_token = new HashMap<String, String>(access_tokenSize);
        for (int i = 0; i < access_tokenSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.access_token.put(key, value);
        }
        this.chat_dialog_id = in.readString();
        this.last_message = in.readString();
        this.last_message_id = in.readString();
        this.last_message_sender_id = in.readString();
        this.last_message_time = in.readString();
        this.participant_ids = in.readString();
        this.dialog_created_time = in.readString();
        int delete_dialog_timeSize = in.readInt();
        this.delete_dialog_time = new HashMap<String, String>(delete_dialog_timeSize);
        for (int i = 0; i < delete_dialog_timeSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.delete_dialog_time.put(key, value);
        }
        int platform_statusSize = in.readInt();
        this.platform_status = new HashMap<String, String>(platform_statusSize);
        for (int i = 0; i < platform_statusSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.platform_status.put(key, value);
        }
        int profile_picSize = in.readInt();
        this.profile_pic = new HashMap<String, String>(profile_picSize);
        for (int i = 0; i < profile_picSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.profile_pic.put(key, value);
        }
        int push_tokenSize = in.readInt();
        this.push_token = new HashMap<String, String>(push_tokenSize);
        for (int i = 0; i < push_tokenSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.push_token.put(key, value);
        }
        int unread_countSize = in.readInt();
        this.unread_count = new HashMap<String, String>(unread_countSize);
        for (int i = 0; i < unread_countSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.unread_count.put(key, value);
        }
        int nameSize = in.readInt();
        this.name = new HashMap<String, String>(nameSize);
        for (int i = 0; i < nameSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.name.put(key, value);
        }
        int muteSize = in.readInt();
        this.mute = new HashMap<String, String>(muteSize);
        for (int i = 0; i < muteSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.mute.put(key, value);
        }
        int delete_outer_dialogSize = in.readInt();
        this.delete_outer_dialog = new HashMap<String, String>(delete_outer_dialogSize);
        for (int i = 0; i < delete_outer_dialogSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.delete_outer_dialog.put(key, value);
        }
        int block_statusSize = in.readInt();
        this.block_status = new HashMap<String, String>(block_statusSize);
        for (int i = 0; i < block_statusSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.block_status.put(key, value);
        }
    }

    public static final Creator<ChatDialogModel> CREATOR = new Creator<ChatDialogModel>() {
        @Override
        public ChatDialogModel createFromParcel(Parcel source) {
            return new ChatDialogModel(source);
        }

        @Override
        public ChatDialogModel[] newArray(int size) {
            return new ChatDialogModel[size];
        }
    };
}
