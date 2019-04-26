package com.example.e_college.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_college.R;
import com.example.e_college.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {

   @BindView(R.id.Studentname)
    TextView txtStudentname;

   @BindView(R.id.studentemail)
   TextView txtStudentemail;

   @BindView(R.id.btnedit)
    Button btnedit;

   @BindView(R.id.admissionGuidelines)
   TextView txtadmissionGuidelines;

   @BindView(R.id.writeReviews)
    TextView txtwriteReviews;

    @BindView(R.id.ViewName)
    TextView txtViewname;

    @BindView(R.id.viewemail)
    TextView txtviewemail;

    @BindView(R.id.viewcontact)
    TextView txtviewcontact;

    @BindView(R.id.viewDOB)
    TextView txtDOB;

    @BindView(R.id.viewgender)
    TextView txtgender;

    @BindView(R.id.viewfathername)
    TextView txtfathername;

    @BindView(R.id.viewmothername)
    TextView txtmothername;

    @BindView(R.id.viewreligion)
    TextView txtreligion;

    @BindView(R.id.viewnationality)
    TextView txtnationality;

    @BindView(R.id.viewcity)
    TextView txtcity;

    @BindView(R.id.viewstate)
    TextView txtstate;

    @BindView(R.id.viewguardian_name)
    TextView txtguardianname;

    @BindView(R.id.viewguardian_contact)
    TextView txtguardiancontact;

    @BindView(R.id.viewcollegename)
    TextView txtcollegename;

    @BindView(R.id.view_coursename)
    TextView txtcoursename;

    @BindView(R.id.view_matricpercent)
    TextView txtmatricpercent;

    @BindView(R.id.view_matricYOP)
    TextView txtmatricYOP;

    @BindView(R.id.view_twelthPercent)
    TextView txttwelthpercent;

    @BindView(R.id.view_twelthYOP)
    TextView txttwelthYOP;

    @BindView(R.id.view_MatricBoard)
    TextView txtmatricboard;

    @BindView(R.id.view_twelthBoard)
    TextView txttwelthboard;

    @BindView(R.id.view_Qualification)
    TextView txtqual;

    @BindView(R.id.view_Address)
    TextView txtaddress;

    @BindView(R.id.view_otherpercent)
    TextView txtotherpercent;

    @BindView(R.id.view_pin_code)
    TextView txtpincode;

    @BindView(R.id.view_Studenttype)
    TextView txtstudenttype;

    @BindView(R.id.view_Batchyear)
    TextView txtbatchyear;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

void initViews(){
    ButterKnife.bind(this);

    btnedit.setOnClickListener(this);
    txtadmissionGuidelines.setOnClickListener(this);
    txtwriteReviews.setOnClickListener(this);

    firebaseAuth = FirebaseAuth.getInstance();
    db = FirebaseFirestore.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();


}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        initViews();
        fetchStudentDetailsFromCloud();
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
                            txtStudentname.setText(student.Name);
                            txtStudentemail.setText(student.Email);
                            txtViewname.setText(student.Name);
                            txtviewemail.setText(student.Email);
                            txtviewcontact.setText(student.Contact);
                            txtaddress.setText(student.PermanentAddress);
                            txtbatchyear.setText(student.BatchYear);
                            txttwelthboard.setText(student.TwelthBoard);
                            txtcity.setText(student.City);
                            txtstate.setText(student.State);
                            txtstudenttype.setText(student.StudentType);
                            txtcollegename.setText(student.CollegeName);
                            txtcoursename.setText(student.CourseName);
                            txtmatricYOP.setText(student.MatricYOP);
                            txttwelthYOP.setText(student.TwelthYOP);
                            txtreligion.setText(student.Religion);
                            txtqual.setText(student.OtherQual);
                            txtpincode.setText(student.Pincode);
                            txtmatricpercent.setText(student.Matricpercent);
                            txttwelthpercent.setText(student.TwelthPercent);
                            txtnationality.setText(student.Nationality);
                            txtmothername.setText(student.MotherName);
                            txtotherpercent.setText(student.OtherPercent);
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

    @Override
    public void onClick(View v) {
    int id=v.getId();

    switch (id){
        case R.id.btnedit:
            Intent edit=new Intent(PersonalInfoActivity.this,StudentProfile.class);
            startActivity(edit);

            break;

        case R.id.admissionGuidelines:
            Intent guidelines=new Intent(PersonalInfoActivity.this,AdmissionGuidelines.class);
            startActivity(guidelines);

            break;

        case R.id.writeReviews:
            Intent reviews=new Intent(PersonalInfoActivity.this,ReviewsActivity.class);
            startActivity(reviews);

            break;
    }




    }
}
