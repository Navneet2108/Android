package com.example.e_college.model;

public class User {
    public String docid;
    public String Name;
    public String Email;
    public String Password;

    public User() {
    }

    public User(String docid, String name, String email, String password) {
        this.docid = docid;
        Name = name;
        Email = email;
        Password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "docid='" + docid + '\'' +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
