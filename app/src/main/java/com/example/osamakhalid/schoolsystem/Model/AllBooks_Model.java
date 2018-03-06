package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class AllBooks_Model {


    @SerializedName("books_data")
    @Expose
    private List<AllBooks_Data_Model> booksData = null;

    public List<AllBooks_Data_Model> getBooksData() {
        return booksData;
    }

    public void setBooksData(List<AllBooks_Data_Model> booksData) {
        this.booksData = booksData;}

}
