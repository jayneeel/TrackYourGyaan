package com.example.trackyourgyan.objects;

public class QuizResult {
    String status, subject, uid, timestamp;
    int score;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public QuizResult() {
    }

    public QuizResult(String status, String subject, String uid, String timestamp, int score) {
        this.status = status;
        this.subject = subject;
        this.uid = uid;
        this.timestamp = timestamp;
        this.score = score;
    }
}
