package com.example.e_college.model;

import java.io.Serializable;

public class Courses implements Serializable {
    public  String id;
    public  String doc_Id;
    public String Name;
    public String coursefees;

    public Courses() {
    }

    public Courses(String id, String doc_Id, String name, String coursefees) {
        this.id = id;
        this.doc_Id = doc_Id;
        Name = name;
        this.coursefees = coursefees;
    }

    public Courses(String id, String name, String doc_Id) {
        this.id = id;
        this.Name = name;
        this.doc_Id=doc_Id;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id='" + id + '\'' +
                ", doc_Id='" + doc_Id + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
