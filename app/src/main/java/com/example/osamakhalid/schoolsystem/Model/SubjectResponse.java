package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 2/21/2018.
 */

public class SubjectResponse {
    @SerializedName("subjectID")
    @Expose
    private String subjectID;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("subject_author")
    @Expose
    private String subjectAuthor;
    @SerializedName("subject_code")
    @Expose
    private String subjectCode;
    @SerializedName("teacher")
    @Expose
    private String teacher;

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectAuthor() {
        return subjectAuthor;
    }

    public void setSubjectAuthor(String subjectAuthor) {
        this.subjectAuthor = subjectAuthor;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
