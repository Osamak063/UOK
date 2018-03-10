package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HAMI on 22/02/2018.
 */

public class TeacherData_Model {

    @SerializedName("teacher_id")
    @Expose
    private String teacherId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("teacher_username")
    @Expose
    private String teacherUsername;

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
