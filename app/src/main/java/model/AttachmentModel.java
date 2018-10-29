package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by applify on 11/2/2017.
 */

public class AttachmentModel implements Parcelable {
    private int id;
    private int tool_id;
    private String attachment;
    private String thumbnail;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTool_id() {
        return tool_id;
    }

    public void setTool_id(int tool_id) {
        this.tool_id = tool_id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.tool_id);
        dest.writeString(this.attachment);
        dest.writeString(this.thumbnail);
        dest.writeString(this.type);
    }

    public AttachmentModel() {
    }

    protected AttachmentModel(Parcel in) {
        this.id = in.readInt();
        this.tool_id = in.readInt();
        this.attachment = in.readString();
        this.thumbnail = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<AttachmentModel> CREATOR = new Parcelable.Creator<AttachmentModel>() {
        @Override
        public AttachmentModel createFromParcel(Parcel source) {
            return new AttachmentModel(source);
        }

        @Override
        public AttachmentModel[] newArray(int size) {
            return new AttachmentModel[size];
        }
    };
}
