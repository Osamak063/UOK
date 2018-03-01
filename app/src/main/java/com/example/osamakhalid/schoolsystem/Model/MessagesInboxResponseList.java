package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/26/2018.
 */

public class MessagesInboxResponseList {
    @SerializedName("message_data")
    @Expose
    private List<MessagesInboxResponse> messageData = null;

    public List<MessagesInboxResponse> getMessageData() {
        return messageData;
    }

    public void setMessageData(List<MessagesInboxResponse> messageData) {
        this.messageData = messageData;
    }
}
