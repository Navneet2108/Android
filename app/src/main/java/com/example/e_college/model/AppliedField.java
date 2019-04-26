package com.example.e_college.model;

public class AppliedField {
    public String docid;
   public String colname;
   public String colcity;
   public String colstate;

    public AppliedField() {
    }

    @Override
    public String toString() {
        return "AppliedField{" +
                "colname='" + colname + '\'' +
                ", colcity='" + colcity + '\'' +
                ", colstate='" + colstate + '\'' +
                '}';
    }

    public AppliedField(String colname, String colcity, String colstate) {
        this.colname = colname;
        this.colcity = colcity;
        this.colstate = colstate;
    }

    public AppliedField(String docid, String colname, String colcity, String colstate) {
        this.docid = docid;
        this.colname = colname;
        this.colcity = colcity;
        this.colstate = colstate;
    }
}
