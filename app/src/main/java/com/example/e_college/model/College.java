package com.example.e_college.model;

import java.io.Serializable;

public class College implements Serializable {
    public String docID;
    public String name;
    public String email;
    public String password;
    public String city;
    public String state;

    public College() {
    }

    public College(String docID, String name, String email, String password, String city, String state) {
        this.docID = docID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return "College{" +
                "docID='" + docID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
