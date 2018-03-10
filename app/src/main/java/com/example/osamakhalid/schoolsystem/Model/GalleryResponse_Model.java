package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 07/03/2018.
 */

public class GalleryResponse_Model {

    @SerializedName("media_data")
    @Expose
    private List<GalleryImage_Data> mediaData = null;

    public List<GalleryImage_Data> getMediaData() {
        return mediaData;
    }

    public void setMediaData(List<GalleryImage_Data> mediaData) {
        this.mediaData = mediaData;
    }

}
