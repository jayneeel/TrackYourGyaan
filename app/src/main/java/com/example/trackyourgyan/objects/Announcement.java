package com.example.trackyourgyan.objects;

import java.util.Date;

public class Announcement {
    String title, message, year;

    public Announcement() {
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Announcement(String title, String message, String year) {
        this.title = title;
        this.message = message;
        this.year = year;
    }
}
