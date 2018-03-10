package com.example.osamakhalid.schoolsystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HAMI on 07/03/2018.
 */

public class GalleryData_Model  implements Parcelable{

    @SerializedName("mediaID")
    @Expose
    private String mediaID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("usertype")
    @Expose
    private String usertype;
    @SerializedName("mcategoryID")
    @Expose
    private String mcategoryID;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("file_name_display")
    @Expose
    private String fileNameDisplay;

    protected GalleryData_Model(Parcel in) {
        mediaID = in.readString();
        userID = in.readString();
        usertype = in.readString();
        mcategoryID = in.readString();
        fileName = in.readString();
        fileNameDisplay = in.readString();
    }

    public static final Creator<GalleryData_Model> CREATOR = new Creator<GalleryData_Model>() {
        @Override
        public GalleryData_Model createFromParcel(Parcel in) {
            return new GalleryData_Model(in);
        }

        @Override
        public GalleryData_Model[] newArray(int size) {
            return new GalleryData_Model[size];
        }
    };

    public String getMediaID() {
        return mediaID;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getMcategoryID() {
        return mcategoryID;
    }

    public void setMcategoryID(String mcategoryID) {
        this.mcategoryID = mcategoryID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameDisplay() {
        return fileNameDisplay;
    }

    public void setFileNameDisplay(String fileNameDisplay) {
        this.fileNameDisplay = fileNameDisplay;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mediaID);
        parcel.writeString(userID);
        parcel.writeString(usertype);
        parcel.writeString(mcategoryID);
        parcel.writeString(fileName);
        parcel.writeString(fileNameDisplay);
    }
}
