package com.example.e_college.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentProfile extends AppCompatActivity implements View.OnClickListener {

    DatePickerDialog datePickerDialog;

    @BindView(R.id.student_profile_Name)
    EditText txtname;

    @BindView(R.id.student_profile_email)
    EditText txtemail;

    @BindView(R.id.student_profile_contact)
    EditText txtcontact;

    @BindView(R.id.student_profile_DOB)
    EditText txtDOB;

    @BindView(R.id.student_profile_gender)
    EditText txtgender;

    @BindView(R.id.student_profile_father_name)
    EditText txtfathername;

    @BindView(R.id.student_profile_mothername)
    EditText txtmothername;

    @BindView(R.id.student_profile_religion)
    EditText txtreligion;

    @BindView(R.id.student_profile_nationality)
    EditText txtnationality;

    @BindView(R.id.student_profile_city)
    EditText txtcity;

    @BindView(R.id.student_profile_state)
    EditText txtstate;

    @BindView(R.id.student_profile_guardian_name)
    EditText txtguardianname;

    @BindView(R.id.student_profile_guardian_contact)
    EditText txtguardiancontact;

    @BindView(R.id.student_profile_collegename)
    EditText txtcollegename;

    @BindView(R.id.student_profile_course_name)
    EditText txtcoursename;

    @BindView(R.id.student_profile_matricpercent)
    EditText txtmatricpercent;

    @BindView(R.id.student_profile_MatricYOP)
    EditText txtmatricYOP;

    @BindView(R.id.student_profile_Twelthpercent)
    EditText txtTwelthpercent;

    @BindView(R.id.student_profile_TwelthYOP)
    EditText txtTwelthYOP;

    @BindView(R.id.student_profile_MatricBoard)
    EditText txtmatricboard;

    @BindView(R.id.student_profile_TwelthBoard)
    EditText txtTwelthboard;

    @BindView(R.id.student_profile_Qualification)
    EditText txtqual;

    @BindView(R.id.student_profile_permanent_address)
    EditText txtaddress;

    @BindView(R.id.student_profile_Otherpercent)
    EditText txtOtherpercent;

    @BindView(R.id.student_profile_pin_code)
    EditText txtpincode;

    @BindView(R.id.student_profile_studentType)
    EditText txtstudenttype;

    @BindView(R.id.student_profile_Batchyear)
    EditText txtbatchyear;

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
        txtDOB.setOnClickListener(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        initViews();
        fetchStudentDetailsFromCloud();
    }
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = year+"/"+(month+1)+"/"+dayOfMonth;
            txtDOB.setText(date);
        }
    };

    void showDatePickerDialog(){

        // get todays date
        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);

        // show the same date on dialog
        datePickerDialog = new DatePickerDialog(this, onDateSetListener, yy, mm, dd);
        datePickerDialog.show();
    }
    void fetchStudentDetailsFromCloud(){

        String uid = firebaseAuth.getCurrentUser().getUid();
        db.collection("students").document(uid).get()
                .addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isComplete()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            Student student = documentSnapshot.toObject(Student.class);
                            txtname.setText(student.Name);
                            txtemail.setText(student.Email);
                            txtcontact.setText(student.Contact);
                            txtaddress.setText(student.PermanentAddress);
                            txtbatchyear.setText(student.BatchYear);
                            txtTwelthboard.setText(student.TwelthBoard);
                            txtcity.setText(student.City);
                            txtstate.setText(student.State);
                            txtstudenttype.setText(student.StudentType);
                            txtcollegename.setText(student.CollegeName);
                            txtcoursename.setText(student.CourseName);
                            txtmatricYOP.setText(student.MatricYOP);
                            txtTwelthYOP.setText(student.TwelthYOP);
                            txtreligion.setText(student.Religion);
                            txtqual.setText(student.OtherQual);
                            txtpincode.setText(student.Pincode);
                            txtmatricpercent.setText(student.Matricpercent);
                            txtTwelthpercent.setText(student.TwelthPercent);
                            txtnationality.setText(student.Nationality);
                            txtmothername.setText(student.MotherName);
                            txtOtherpercent.setText(student.OtherPercent);
                            txtmatricboard.setText(student.MatricBoard);
                            txtgender.setText(student.Gender);
                            txtguardianname.setText(student.GuardianName);
                            txtguardiancontact.setText(student.GuardianContact);
                            txtDOB.setText(student.DateOfBirth);
                            txtfathername.setText(student.FatherName);
                        }
                    }
                });

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
                        Intent intent=new Intent(StudentProfile.this,PersonalInfoActivity.class);
                        startActivity(intent);

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
                    txtname.setError("Please enter field");
                    txtname.requestFocus();
                }
                if (txtemail.getText().toString().trim().length() == 0) {
                    txtemail.setError("Please enter field");
                    txtemail.requestFocus();
                }
                if (txtcontact.getText().toString().trim().length() == 0) {
                    txtcontact.setError("Please enter field");
                    txtcontact.requestFocus();
                }
                if (txtstudenttype.getText().toString().trim().length() == 0) {
                    txtstudenttype.setError("Please enter field");
                    txtstudenttype.requestFocus();
                }
                if (txtmatricpercent.getText().toString().trim().length() == 0) {
                    txtmatricpercent.setError("Please enter field");
                    txtmatricpercent.requestFocus();
                }
                if (txtTwelthpercent.getText().toString().trim().length() == 0) {
                txtTwelthpercent.setError("Please enter field");
                txtTwelthpercent.requestFocus();
                }
                if (txtmatricYOP.getText().toString().trim().length() == 0) {
                    txtmatricYOP.setError("Please enter field");
                    txtmatricYOP.requestFocus();
                }
                if (txtTwelthYOP.getText().toString().trim().length() == 0) {
                txtTwelthYOP.setError("Please enter field");
                txtTwelthYOP.requestFocus();
                }
                if (txtstate.getText().toString().trim().length() == 0) {
                    txtstate.setError("Please enter field");
                    txtstate.requestFocus();
                }
                if (txtbatchyear.getText().toString().trim().length() == 0) {
                    txtbatchyear.setError("Please enter field");
                    txtbatchyear.requestFocus();
                }
                if (txtreligion.getText().toString().trim().length() == 0) {
                txtreligion.setError("Please enter field");
                txtreligion.requestFocus();
                }
                if (txtcity.getText().toString().trim().length() == 0) {
                    txtcity.setError("Please enter field");
                    txtcity.requestFocus();
                }
                if (txtcollegename.getText().toString().trim().length() == 0) {
                txtcollegename.setError("Please enter field");
                txtcollegename.requestFocus();
                }
                if (txtcoursename.getText().toString().trim().length() == 0) {
                    txtcoursename.setError("Please enter field");
                    txtcoursename.requestFocus();
                }

                if (txtmatricboard.getText().toString().trim().length() == 0) {
                    txtmatricboard.setError("Please enter field");
                    txtmatricboard.requestFocus();
                }

                if (txtpincode.getText().toString().trim().length() == 0) {
                        txtpincode.setError("Please enter field");
                    txtpincode.requestFocus();
                }
                if (txtfathername.getText().toString().trim().length() == 0) {
                    txtfathername.setError("Please enter field");
                    txtfathername.requestFocus();
                }
                if (txtDOB.getText().toString().trim().length() == 0) {
                    txtDOB.setError("Please enter field");
                    txtDOB.requestFocus();
                }
                if (txtmothername.getText().toString().trim().length() == 0) {
                    txtmothername.setError("Please enter field");
                    txtmothername.requestFocus();
                }
                if (txtaddress.getText().toString().trim().length() == 0) {
                    txtaddress.setError("Please enter field");
                    txtaddress.requestFocus();
                }
                if (txtnationality.getText().toString().trim().length() == 0) {
                    txtnationality.setError("Please enter field");
                    txtnationality.requestFocus();
                }
                if (txtgender.getText().toString().trim().length() == 0) {
                    txtgender.setError("Please enter field");
                    txtgender.requestFocus();
                }

                if (txtTwelthboard.getText().toString().trim().length() == 0) {
                    txtTwelthboard.setError("Please enter field");
                    txtTwelthboard.requestFocus();
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
            case R.id.student_profile_DOB:
                showDatePickerDialog();
                break;


        }
    }
}
