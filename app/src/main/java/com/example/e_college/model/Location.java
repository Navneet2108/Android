package com.example.e_college.model;

public class Location {
    public String docid;
   public String state;

    public Location() {
    }

    public Location(String docid, String state) {
        this.docid = docid;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Location{" +
                "docid='" + docid + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
