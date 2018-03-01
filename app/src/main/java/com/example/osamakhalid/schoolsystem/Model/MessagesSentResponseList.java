package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/28/2018.
 */

public class MessagesSentResponseList {
    @SerializedName("message_data")
    @Expose
    private List<MessagesSentResponse> messageData = null;

    public List<MessagesSentResponse> getMessageData() {
        return messageData;
    }

    public void setMessageData(List<MessagesSentResponse> messageData) {
        this.messageData = messageData;
    }

}
