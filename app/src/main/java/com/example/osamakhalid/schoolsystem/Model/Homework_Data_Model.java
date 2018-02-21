package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HAMI on 21/02/2018.
 */

public class Homework_Data_Model {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("desc")
    @Expose
    private String desc;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
