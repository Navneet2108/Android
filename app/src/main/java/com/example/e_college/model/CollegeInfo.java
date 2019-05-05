package com.example.e_college.model;

import java.io.Serializable;

public class CollegeInfo implements Serializable {
    public  String docid;
   public String info;
    public String newstufee;
    public  String transferstufee;
    public String newDeadline;
    public String transferdeadline;
    public String address;
    public String website;
    public String  phone;


    public CollegeInfo() {
    }


    public CollegeInfo(String docid, String info, String newstufee, String transferstufee, String newDeadline, String transferdeadline, String address, String website, String phone) {
        this.docid = docid;
        this.info = info;
        this.newstufee = newstufee;
        this.transferstufee = transferstufee;
        this.newDeadline = newDeadline;
        this.transferdeadline = transferdeadline;
        this.address = address;
        this.website = website;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CollegeInfo{" +
                "docid='" + docid + '\'' +
                ", info='" + info + '\'' +
                ", newstufee='" + newstufee + '\'' +
                ", transferstufee='" + transferstufee + '\'' +
                ", newDeadline='" + newDeadline + '\'' +
                ", transferdeadline='" + transferdeadline + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
