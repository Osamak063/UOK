package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 3/1/2018.
 */

public class ChatResponse {
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("attachment_url")
    @Expose
    private String attachmentUrl;
    @SerializedName("attachment_dir")
    @Expose
    private String attachmentDir;
    @SerializedName("attachment_name")
    @Expose
    private String attachmentName;
    @SerializedName("messageid")
    @Expose
    private String messageid;
    @SerializedName("messages_data")
    @Expose
    private List<ChatDataResponse> messagesData = null;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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

    public String getAttachmentDir() {
        return attachmentDir;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentDir(String attachmentDir) {
        this.attachmentDir = attachmentDir;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public List<ChatDataResponse> getMessagesData() {
        return messagesData;
    }

    public void setMessagesData(List<ChatDataResponse> messagesData) {
        this.messagesData = messagesData;
    }

}
