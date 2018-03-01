package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 2/28/2018.
 */

public class MessagesSentResponse {
    @SerializedName("message_id")
    @Expose
    private String messageId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("time")
    @Expose
    private String time;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
