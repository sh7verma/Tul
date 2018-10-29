package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by applify on 10/12/2017.
 */

public class TulImageModel implements Parcelable {

    private int id;
    private String type;
    private String thumbnails;
    private String path;
    private boolean isEdit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public TulImageModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeString(this.thumbnails);
        dest.writeString(this.path);
        dest.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
    }

    protected TulImageModel(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.thumbnails = in.readString();
        this.path = in.readString();
        this.isEdit = in.readByte() != 0;
    }

    public static final Creator<TulImageModel> CREATOR = new Creator<TulImageModel>() {
        @Override
        public TulImageModel createFromParcel(Parcel source) {
            return new TulImageModel(source);
        }

        @Override
        public TulImageModel[] newArray(int size) {
            return new TulImageModel[size];
        }
    };
}
