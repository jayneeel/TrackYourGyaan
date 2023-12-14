package com.example.trackyourgyan.objects;

import java.io.Serializable;

public class Student implements Serializable {
    String firstName, email, year, dept;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", year='" + year + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Student(String firstName, String email, String year, String dept) {
        this.firstName = firstName;
        this.email = email;
        this.year = year;
        this.dept = dept;
    }
}
