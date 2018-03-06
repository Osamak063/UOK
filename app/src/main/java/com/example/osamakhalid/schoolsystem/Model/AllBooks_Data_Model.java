package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class AllBooks_Data_Model {

    @SerializedName("bookID")
    @Expose
    private String bookID;
    @SerializedName("book")
    @Expose
    private String book;
    @SerializedName("subject_code")
    @Expose
    private String subjectCode;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("due_quantity")
    @Expose
    private String dueQuantity;
    @SerializedName("rack")
    @Expose
    private String rack;

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDueQuantity() {
        return dueQuantity;
    }

    public void setDueQuantity(String dueQuantity) {
        this.dueQuantity = dueQuantity;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }
}
