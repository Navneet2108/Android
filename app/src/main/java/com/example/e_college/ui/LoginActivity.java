package com.example.e_college.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_college.R;

import com.example.e_college.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
  ImageView imageView;
  EditText editEmail,editPassword;
  CheckBox checkBox;
  Button btnLogin,btnSignUp;
  TextView textForgotPassword;


    ProgressDialog progressDialog;
    Student student;
    FirebaseAuth auth;

  public void initViews(){
      imageView = findViewById(R.id.imageView);
      editEmail = findViewById(R.id.editTextEmail);
      editPassword=findViewById(R.id.editTextPassword);
      checkBox=findViewById(R.id.checkBox);
      btnLogin =findViewById(R.id.buttonLogin);
      btnSignUp =findViewById(R.id.buttonSign);
      textForgotPassword =findViewById(R.id.textForgotPassword);

     student=new Student();

      progressDialog = new ProgressDialog(this);
      progressDialog.setMessage("Please Wait..");
      progressDialog.setCancelable(false);



      auth=FirebaseAuth.getInstance();
      if (auth.getCurrentUser() != null) {
          startActivity(new Intent(LoginActivity.this, MainpageActivity.class));

      }


      btnLogin.setOnClickListener(this);
      btnSignUp.setOnClickListener(this);
      textForgotPassword.setOnClickListener(this);


  }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initViews();
    }

    @Override
    public void onClick(View v) {
      int id=v.getId();

      switch (id){
          case R.id.buttonLogin:
              student.Email=editEmail.getText().toString();
              student.Password=editPassword.getText().toString();


              if (TextUtils.isEmpty(student.Email)) {
                  Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                  return;
              }

              if (TextUtils.isEmpty(student.Password)) {
                  Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                  return;
              }


              LoginUser();
              break;

          case R.id.buttonSign:
              Intent intent=new Intent(LoginActivity.this, RegistrationActivity.class);
              startActivity(intent);
              finish();
              break;

          case R.id.textForgotPassword:
             /* AlertDialog.Builder mbuilder=new AlertDialog.Builder(LoginActivity.this);
              View mView=getLayoutInflater().inflate(R.layout.activity_forgot_password,null);*/

              Intent intent1=new Intent(LoginActivity.this,ForgotPassword.class);
              startActivity(intent1);
              finish();
              break;
      }

    }
    void LoginUser(){

        progressDialog.show();
        auth.signInWithEmailAndPassword(student.Email,student.Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    // there was an error
                    if (student.Password.length() < 6) {
                        editPassword.setError(getString(R.string.minimum_password));
                        progressDialog.dismiss();
                    } else {

                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                        clearFields();
                        progressDialog.dismiss();
                    }
                } else {

                    Toast.makeText(LoginActivity.this, " Login Successful", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainpageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });

    }
    void clearFields(){
        editEmail.setText("");
        editPassword.setText("");
        checkBox.setChecked(false);
    }
}
