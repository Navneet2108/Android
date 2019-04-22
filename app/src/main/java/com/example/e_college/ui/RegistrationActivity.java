package com.example.e_college.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_college.R;
import com.example.e_college.model.Student;
import com.example.e_college.model.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtName, txtEmail, txtPassword;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView  textLogin;
    Button btnRegister;

    Student student;

    ProgressDialog progressDialog;

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    //SpeechRecognizer speechRecognizer;
    TextToSpeech tts;

    void initViews() {
        txtName = findViewById(R.id.editTextName);
        txtEmail = findViewById(R.id.editTextEmail);
        txtPassword = findViewById(R.id.editTextPassword);

        radioGroup = findViewById(R.id.studentType);

        textLogin = findViewById(R.id.textLogin);
        btnRegister = findViewById(R.id.buttonRegister);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.ERROR) {
                    Toast.makeText(RegistrationActivity.this, "Please change TTS settings in mobile phone", Toast.LENGTH_LONG).show();
                }
            }
        });
        student = new Student();

        auth = FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        btnRegister.setOnClickListener(this);
        textLogin.setOnClickListener(this);

    }

    public void radioBtnClick(View v) {
        int radioBtnID = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioBtnID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonRegister) {
            student.Name = txtName.getText().toString();
            student.Email = txtEmail.getText().toString();
            student.Password = txtPassword.getText().toString();
            student.StudentType=radioButton.getText().toString();


            if (Util.isInternetConnected(this)) {
                registerUser();
            } else {
                Toast.makeText(RegistrationActivity.this, "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
            }
        } else {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    void registerUser() {

        progressDialog.show();

        auth.createUserWithEmailAndPassword(student.Email, student.Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {

                    /*Toast.makeText(RegistrationActivity.this, student.Name + " Registered Successful", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(RegistrationActivity.this, MainpageActivity.class);
                    startActivity(intent);
                    finish();*/

                     saveUserInCloud();
                }
            }
        });
    }

    void saveUserInCloud() {
       /* firestore.collection("students").add(student)
                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isComplete()) {

                            Toast.makeText(RegistrationActivity.this, student.Name+ " Registered Successful", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(RegistrationActivity.this, MainpageActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }

                });*/

       firebaseUser=auth.getCurrentUser();
        firestore.collection("students").document(firebaseUser.getUid()).set(student)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RegistrationActivity.this, student.Name + " Registered Successful", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(RegistrationActivity.this, MainpageActivity.class);
                        startActivity(intent);
                      finish();
                    }
                });


    }
}

