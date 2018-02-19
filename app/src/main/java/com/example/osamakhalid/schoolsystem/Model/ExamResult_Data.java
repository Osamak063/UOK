package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HAMI on 17/02/2018.
 */

public class ExamResult_Data {
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("highest_mark")
    @Expose
    private String highestMark;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHighestMark() {
        return highestMark;
    }

    public void setHighestMark(String highestMark) {
        this.highestMark = highestMark;
    }


}
