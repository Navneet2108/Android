package com.example.e_college.model;

import java.io.Serializable;
import java.security.PublicKey;

public class Guidelines implements Serializable {
    public String docid;
    public  String guideln;

    public Guidelines() {
    }

    public Guidelines(String docid, String guideln) {
        this.docid = docid;
        this.guideln = guideln;
    }

    public Guidelines(String guideln) {
        this.guideln = guideln;
    }

    @Override
    public String toString() {
        return "Guidelines{" +
                "guideln='" + guideln + '\'' +
                '}';
    }
}
