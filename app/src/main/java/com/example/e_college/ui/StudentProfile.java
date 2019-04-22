package com.example.e_college.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_college.R;
import com.example.e_college.model.Student;
import com.example.e_college.model.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentProfile extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.student_profile_Name)
    TextView txtname;

    @BindView(R.id.student_profile_email)
    TextView txtemail;

    @BindView(R.id.student_profile_contact)
    TextView txtcontact;

    @BindView(R.id.student_profile_DOB)
    TextView txtDOB;

    @BindView(R.id.student_profile_gender)
    TextView txtgender;

    @BindView(R.id.student_profile_father_name)
    TextView txtfathername;

    @BindView(R.id.student_profile_mothername)
    TextView txtmothername;

    @BindView(R.id.student_profile_religion)
    TextView txtreligion;

    @BindView(R.id.student_profile_nationality)
    TextView txtnationality;

    @BindView(R.id.student_profile_city)
    TextView txtcity;

    @BindView(R.id.student_profile_state)
    TextView txtstate;

    @BindView(R.id.student_profile_guardian_name)
    TextView txtguardianname;

    @BindView(R.id.student_profile_guardian_contact)
    TextView txtguardiancontact;

    @BindView(R.id.student_profile_collegename)
    TextView txtcollegename;

    @BindView(R.id.student_profile_course_name)
    TextView txtcoursename;

    @BindView(R.id.student_profile_matricpercent)
    TextView txtmatricpercent;

    @BindView(R.id.student_profile_MatricYOP)
    TextView txtmatricYOP;

    @BindView(R.id.student_profile_Twelthpercent)
    TextView txtTwelthpercent;

    @BindView(R.id.student_profile_TwelthYOP)
    TextView txtTwelthYOP;

    @BindView(R.id.student_profile_MatricBoard)
    TextView txtmatricboard;

    @BindView(R.id.student_profile_TwelthBoard)
    TextView txtTwelthboard;

    @BindView(R.id.student_profile_Qualification)
    TextView txtqual;

    @BindView(R.id.student_profile_permanent_address)
    TextView txtaddress;

    @BindView(R.id.student_profile_Otherpercent)
    TextView txtOtherpercent;

    @BindView(R.id.student_profile_pin_code)
    TextView txtpincode;

    @BindView(R.id.student_profile_studentType)
    TextView txtstudenttype;

    @BindView(R.id.student_profile_Batchyear)
    TextView txtbatchyear;

    @BindView(R.id.btn_submit)
    Button btnsubmit;

    @BindView(R.id.btn_cancel)
    Button btncancel;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

    Student student;

    ProgressDialog progressDialog;

    boolean updatemode;

    void initViews() {
        ButterKnife.bind(this);

        btnsubmit.setOnClickListener(this);
        btncancel.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        student=new Student();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        initViews();

    }


    void saveStudentDetailsInCloud(){
            progressDialog.show();
            firebaseUser=firebaseAuth.getCurrentUser();
            db.collection("students").document(firebaseUser.getUid())
                    .set(student).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isComplete()){
                        progressDialog.dismiss();
                        Toast.makeText(StudentProfile.this,"Details are successfully saved",Toast.LENGTH_LONG).show();

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(StudentProfile.this,"some error",Toast.LENGTH_LONG).show();
                    }
                }
            });



        }




    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_submit:
                student.Name = txtname.getText().toString();
                student.Email = txtemail.getText().toString();
                student.Contact = txtcontact.getText().toString();
                student.City = txtcity.getText().toString();
                student.CollegeName = txtcollegename.getText().toString();
                student.CourseName = txtcoursename.getText().toString();
                student.GuardianContact = txtguardiancontact.getText().toString();
                student.BatchYear = txtbatchyear.getText().toString();
                student.MatricBoard = txtmatricboard.getText().toString();
                student.Matricpercent = txtmatricpercent.getText().toString();
                student.OtherPercent = txtOtherpercent.getText().toString();
                student.Pincode = txtpincode.getText().toString();
                student.TwelthPercent = txtTwelthpercent.getText().toString();
                student.FatherName = txtfathername.getText().toString();
                student.DateOfBirth = txtDOB.getText().toString();
                student.MotherName = txtmothername.getText().toString();
                student.PermanentAddress = txtaddress.getText().toString();
                student.Nationality = txtnationality.getText().toString();
                student.Gender = txtgender.getText().toString();
                student.GuardianName = txtguardianname.getText().toString();
                student.Religion = txtreligion.getText().toString();
                student.TwelthBoard = txtTwelthboard.getText().toString();
                student.OtherQual = txtqual.getText().toString();
                student.State = txtstate.getText().toString();
                student.MatricYOP = txtmatricYOP.getText().toString();
                student.TwelthYOP = txtTwelthYOP.getText().toString();
                student.StudentType = txtstudenttype.getText().toString();

                if(txtname.getText().toString().trim().length()==0) {
                    Toast.makeText(StudentProfile.this, "Please enter your details", Toast.LENGTH_SHORT).show();
                }else {
                    if (Util.isInternetConnected(StudentProfile.this)) {
                        saveStudentDetailsInCloud();
                    } else {
                        Toast.makeText(StudentProfile.this, "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
                    }
                }












                break;

            case R.id.btn_cancel:
                Intent cancel = new Intent(StudentProfile.this, MainpageActivity.class);
                startActivity(cancel);

                break;

        }
    }
}
