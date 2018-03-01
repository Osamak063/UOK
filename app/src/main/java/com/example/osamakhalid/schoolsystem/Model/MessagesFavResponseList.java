package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/28/2018.
 */

public class MessagesFavResponseList {

    @SerializedName("message_data")
    @Expose
    private List<MessagesFavResponse> messageData = null;

    public List<MessagesFavResponse> getMessageData() {
        return messageData;
    }

    public void setMessageData(List<MessagesFavResponse> messageData) {
        this.messageData = messageData;
    }
}
