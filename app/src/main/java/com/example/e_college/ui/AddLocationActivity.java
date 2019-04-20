package com.example.e_college.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_college.R;
import com.example.e_college.model.Courses;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddLocationActivity extends AppCompatActivity {
    EditText etxtstate;
    Button btnSubmit,btnView;

    Courses courses;

    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
    }
}
