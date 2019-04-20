package com.example.e_college.model;

public class Courses {
    public  String id;
    public  String doc_Id;
    public String Name;

    public Courses() {
    }

    public Courses(String id, String name,String doc_Id) {
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
