package com.example.osamakhalid.schoolsystem.Model;

/**
 * Created by Osama Khalid on 2/4/2018.
 */

public class Chat_Model {
    String message;
    String date;
    public Chat_Model(){}
    public Chat_Model(String message,String date){
        this.message=message;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
