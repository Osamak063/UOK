package com.example.osamakhalid.schoolsystem.Model;

/**
 * Created by HAMI on 16/02/2018.
 */

public class Homework_Model  {

    private String course,assign,date;

    public Homework_Model(){

    }

    public Homework_Model(String Course,String assign,String date){

        this.course = Course;
        this.assign = assign;
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAssign() {
        return assign;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
