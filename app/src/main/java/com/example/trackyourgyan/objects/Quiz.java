package com.example.trackyourgyan.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz implements Serializable {
    public String id, title;
    public int time;
    public ArrayList<Question> quizQuestions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<Question> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(ArrayList<Question> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public Quiz(String id, String title, int time, ArrayList<Question> quizQuestions) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.quizQuestions = quizQuestions;
    }
}
