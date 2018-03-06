package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Libray_Model {


    @SerializedName("library_id")
    @Expose
    private String libraryId;
    @SerializedName("library_fee")
    @Expose
    private String libraryFee;
    @SerializedName("joining_date")
    @Expose
    private String joiningDate;
    @SerializedName("books")
    @Expose
    private List<LibraryData_Model> books = null;

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryFee() {
        return libraryFee;
    }

    public void setLibraryFee(String libraryFee) {
        this.libraryFee = libraryFee;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public List<LibraryData_Model> getBooks() {
        return books;
    }

    public void setBooks(List<LibraryData_Model> books) {
        this.books = books;
    }

}
