package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 2/22/2018.
 */

public class AttendanceValuesResponse {
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("day_name")
    @Expose
    private String dayName;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("attendance_status")
    @Expose
    private String attendanceStatus;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
