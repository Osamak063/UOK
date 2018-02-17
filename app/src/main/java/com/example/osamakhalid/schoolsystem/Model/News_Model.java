package com.example.osamakhalid.schoolsystem.Model;

/**
 * Created by Osama Khalid on 2/2/2018.
 */

public class News_Model {
    String news;
    String date;
    public News_Model(){}
    public News_Model(String news,String date){
        this.news=news;
        this.date=date;
    }
    public String getDate() {
        return date;
    }

    public String getNews() {
        return news;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNews(String news) {
        this.news = news;
    }
}
