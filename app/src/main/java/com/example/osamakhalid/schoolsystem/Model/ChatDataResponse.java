package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 3/1/2018.
 */

public class ChatDataResponse {
    @SerializedName("message_content")
    @Expose
    private String messageContent;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("image")
    @Expose
    private String image;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
