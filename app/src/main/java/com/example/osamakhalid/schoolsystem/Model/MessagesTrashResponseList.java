package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/28/2018.
 */

public class MessagesTrashResponseList {
    @SerializedName("message_data")
    @Expose
    private List<MessagesTrashResponse> messageData = null;

    public List<MessagesTrashResponse> getMessageData() {
        return messageData;
    }

    public void setMessageData(List<MessagesTrashResponse> messageData) {
        this.messageData = messageData;
    }
}
