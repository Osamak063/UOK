package com.example.osamakhalid.schoolsystem.Model;

/**
 * Created by Osama Khalid on 2/4/2018.
 */

public class Book_Model {
    String name;
    String course;
    public Book_Model(){}
    public Book_Model(String name,String course){
        this.name=name;
        this.course=course;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
