package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 07/03/2018.
 */

public class GalleryImage_Data {


    @SerializedName("folder_name")
    @Expose
    private String folderName;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("images")
    @Expose
    private List<GalleryData_Model> images = null;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<GalleryData_Model> getImages() {
        return images;
    }

    public void setImages(List<GalleryData_Model> images) {
        this.images = images;
    }

}
