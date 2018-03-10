package com.example.osamakhalid.schoolsystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HAMI on 10/03/2018.
 */

public class TimeTable_Data_Model implements Parcelable {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("room")
    @Expose
    private String room;

    protected TimeTable_Data_Model(Parcel in) {
        time = in.readString();
        subject = in.readString();
        room = in.readString();
    }

    public static final Creator<TimeTable_Data_Model> CREATOR = new Creator<TimeTable_Data_Model>() {
        @Override
        public TimeTable_Data_Model createFromParcel(Parcel in) {
            return new TimeTable_Data_Model(in);
        }

        @Override
        public TimeTable_Data_Model[] newArray(int size) {
            return new TimeTable_Data_Model[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeString(subject);
        parcel.writeString(room);
    }
}
