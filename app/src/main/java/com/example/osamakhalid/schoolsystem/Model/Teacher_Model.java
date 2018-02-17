package com.example.osamakhalid.schoolsystem.Model;

/**
 * Created by Osama Khalid on 2/3/2018.
 */

public class Teacher_Model {
    String name;
    String courses;
    public Teacher_Model(){}
    public Teacher_Model(String name,String courses){
        this.name=name;
        this.courses=courses;
    }

    public String getName() {
        return name;
    }

    public String getCourses() {
        return courses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }
}
