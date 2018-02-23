package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/22/2018.
 */

public class AttendanceDataResponse {
    @SerializedName("attendanceID")
    @Expose
    private String attendanceID;
    @SerializedName("studentID")
    @Expose
    private String studentID;
    @SerializedName("classesID")
    @Expose
    private String classesID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("usertype")
    @Expose
    private String usertype;
    @SerializedName("monthyear")
    @Expose
    private String monthyear;
    @SerializedName("start_day")
    @Expose
    private String startDay;
    @SerializedName("attendance")
    @Expose
    private List<AttendanceValuesResponse> attendance = null;

    public String getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(String attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getClassesID() {
        return classesID;
    }

    public void setClassesID(String classesID) {
        this.classesID = classesID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getMonthyear() {
        return monthyear;
    }

    public void setMonthyear(String monthyear) {
        this.monthyear = monthyear;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public List<AttendanceValuesResponse> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<AttendanceValuesResponse> attendance) {
        this.attendance = attendance;
    }

}
