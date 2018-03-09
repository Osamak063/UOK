package com.example.osamakhalid.schoolsystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HAMI on 08/03/2018.
 */

public class SyllabusMultiple_Subject_Model implements Parcelable {

    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("desc")
    @Expose
    private String desc;

    protected SyllabusMultiple_Subject_Model(Parcel in) {
        subject = in.readString();
        desc = in.readString();
    }

    public static final Creator<SyllabusMultiple_Subject_Model> CREATOR = new Creator<SyllabusMultiple_Subject_Model>() {
        @Override
        public SyllabusMultiple_Subject_Model createFromParcel(Parcel in) {
            return new SyllabusMultiple_Subject_Model(in);
        }

        @Override
        public SyllabusMultiple_Subject_Model[] newArray(int size) {
            return new SyllabusMultiple_Subject_Model[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subject);
        parcel.writeString(desc);
    }
}
