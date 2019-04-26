package com.example.e_college.model;

public class Rating {
    public String docid;
    public String placementrating;
    public String facultyrating;
    public String reputationrating;
    public String campusrating;


    public Rating() {
    }

    public Rating(String docid, String placementrating, String facultyrating, String reputationrating, String campusrating) {
        this.docid = docid;
        this.placementrating = placementrating;
        this.facultyrating = facultyrating;
        this.reputationrating = reputationrating;
        this.campusrating = campusrating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "docid='" + docid + '\'' +
                ", placementrating='" + placementrating + '\'' +
                ", facultyrating='" + facultyrating + '\'' +
                ", reputationrating='" + reputationrating + '\'' +
                ", campusrating='" + campusrating + '\'' +
                '}';
    }
}
