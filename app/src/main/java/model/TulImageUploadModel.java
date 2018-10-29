package model;

import java.io.File;

import okhttp3.MultipartBody;

/**
 * Created by applify on 10/17/2017.
 */

public class TulImageUploadModel {

    private MultipartBody.Part file;
    private MultipartBody.Part thumb;
    private  String Type;

    public MultipartBody.Part getFile() {
        return file;
    }

    public void setFile(MultipartBody.Part file) {
        this.file = file;
    }

    public MultipartBody.Part getThumb() {
        return thumb;
    }

    public void setThumb(MultipartBody.Part thumb) {
        this.thumb = thumb;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
