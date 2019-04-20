package com.example.e_college.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.e_college.R;
import com.example.e_college.model.College;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class CollegeActivity extends AppCompatActivity {
    EditText eTxtName,eTxtPhone,eTxtEmail;
    Button btnSubmit;
    College colleges;

    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore db;

    void initViews() {
        eTxtName = findViewById(R.id.editTextName);
        eTxtPhone = findViewById(R.id.editTextPhone);
        eTxtEmail = findViewById(R.id.editTextEmail);
        btnSubmit = findViewById(R.id.buttonAdd);
        colleges = new College();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colleges.name = eTxtName.getText().toString();
                colleges.password= eTxtPhone.getText().toString();
                colleges.email=eTxtEmail.getText().toString();

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college);
        initViews();

    }
}
