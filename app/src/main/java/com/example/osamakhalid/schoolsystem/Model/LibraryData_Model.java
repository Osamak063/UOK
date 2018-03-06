package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class LibraryData_Model {

    @SerializedName("book")
    @Expose
    private String book;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("serial_no")
    @Expose
    private String serialNo;
    @SerializedName("issue_date")
    @Expose
    private String issueDate;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("return_date")
    @Expose
    private String returnDate;
    @SerializedName("fine")
    @Expose
    private String fine;

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }
}
