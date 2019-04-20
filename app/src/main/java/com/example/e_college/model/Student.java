package com.example.e_college.model;

public class Student {
    public String docID;
  public String Name;
  public String Email;
  public String Password;
  public String StudentType;

    public Student() {
    }

    public Student(String name, String email, String password, String studentType) {
        Name = name;
        Email = email;
        Password = password;
        StudentType = studentType;
    }

    public Student(String docID, String name, String email, String password, String studentType) {
        this.docID = docID;
        Name = name;
        Email = email;
        Password = password;
        StudentType = studentType;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", StudentType='" + StudentType + '\'' +
                '}';
    }
}
