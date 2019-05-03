package com.example.e_college.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.e_college.R;
import com.example.e_college.model.College;
import com.example.e_college.model.CollegeInfo;
import com.example.e_college.model.Student;
import com.example.e_college.model.Util;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore db;
    AppliedColleges appliedColleges;

    ProgressDialog progressDialog;

    CollegeInfo collegeInfo;
    TextView etxtinfo;
    TextView etxtaddress,etxtwebsite,etxtphone;
    TextView txtnewdeadline,txttransferdeadline;
    TextView newStudent,transerStudent;
    Button btnsave,btnback,btnnewdate,btntransferdate;
    DatePickerDialog datePickerDialog;
    int status = 0;

    void initviews(){
        txtnewdeadline=findViewById(R.id.NewDeadline);
        txttransferdeadline=findViewById(R.id.transferDeadline);
        etxtinfo = findViewById(R.id.editTextinfo);
        etxtaddress = findViewById(R.id.editTextAddress);
        etxtwebsite=findViewById(R.id.editTextWebsite);
        etxtphone=findViewById(R.id.editTextphone);
        newStudent = findViewById(R.id.newStudent);
        transerStudent=findViewById(R.id.transferStudent);
        btnsave = findViewById(R.id.buttonSave);
        btnback = findViewById(R.id.buttonback);
        btnnewdate = findViewById(R.id.newdate);
        btntransferdate = findViewById(R.id.transferdate);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        collegeInfo = new CollegeInfo();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        btnnewdate.setVisibility(View.INVISIBLE);
        btntransferdate.setVisibility(View.INVISIBLE);

       /* btnnewdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 1;
                showdatepickerDialog();
            }
        });
        btntransferdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 2;
                showdatepickerDialog();
                //txttransferdeadline.setText(date);
            }
        });*/

        //btnsave.setOnClickListener(clickListener);
        btnsave.setText("Apply");
        btnback.setOnClickListener(clickListener);

    }
    void saveDetails(){
        progressDialog.show();
        db.collection("students").document(firebaseUser.getUid()).collection("AppliedColleges").add(appliedColleges)
                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isComplete()){
                            Toast.makeText(InfoActivity.this, "Details Saved Successfully", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch (id){
            case R.id.buttonSave:
                   /* collegeInfo.info=etxtinfo.getText().toString();
                    collegeInfo.newDeadline=txtnewdeadline.getText().toString();
                    collegeInfo.transferdeadline=txttransferdeadline.getText().toString();
                    collegeInfo.newstufee=newStudent.getText().toString();
                    collegeInfo.transferstufee=transerStudent.getText().toString();
                    collegeInfo.phone=etxtphone.getText().toString();
                    collegeInfo.address=etxtaddress.getText().toString();
                    collegeInfo.website=etxtwebsite.getText().toString();
                    if(Util.isInternetConnected(InfoActivity.this)) {
                        saveDetails();
                    }else{
                        Toast.makeText(InfoActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
                    }*/
                    break;
                case R.id.buttonback:
                    Intent intent=new Intent(InfoActivity.this,AllCollegeActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };
    /*void showdatepickerDialog(){
        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);
        datePickerDialog = new DatePickerDialog(this, onDateSetListener, yy, mm, dd);
        datePickerDialog.show();
    }
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = year+"/"+(month+1)+"/"+dayOfMonth;
            Toast.makeText(InfoActivity.this,"some error",Toast.LENGTH_LONG).show();

            if(status==1) {
                txtnewdeadline.setText(date);
            }else {
                txttransferdeadline.setText(date);
            }
        }
    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("E-College");
        setContentView(R.layout.activity_info);
        initviews();
        fetchCollegeDetailsFromCloud();
    }
    void fetchCollegeDetailsFromCloud() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent rcv = getIntent();
        String id = rcv.getStringExtra("keyid");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("College").child(id);

        DatabaseReference cid = uidRef.child("CollegeDetails").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String info = dataSnapshot.child("info").getValue().toString();
                String newDeadline = dataSnapshot.child("newDeadline").getValue().toString();
                String transferDeadline = dataSnapshot.child("transferDeadline").getValue().toString();
                String newstufee = dataSnapshot.child("newstufee").getValue().toString();
                String transferstufee = dataSnapshot.child("transferstufee").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String website = dataSnapshot.child("website").getValue().toString();


                etxtinfo.setText(info);
                txtnewdeadline.setText(newDeadline);
                txttransferdeadline.setText(transferDeadline);
                newStudent.setText(newstufee);
                transerStudent.setText(transferstufee);
                etxtphone.setText(phone);
                etxtaddress.setText(address);
                etxtwebsite.setText(website);

                //Log.d(TAG, userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);
       /* Intent rcv = getIntent();
        String id = rcv.getStringExtra("keyid");
        Toast.makeText(this,"get id" +id,Toast.LENGTH_LONG).show();
        db.collection("User").document(firebaseUser.getUid()).collection("College").document(id).collection("CollegeDetails").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {
                            QuerySnapshot querySnapshot = task.getResult();

                            List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                            for (DocumentSnapshot snapshot : documentSnapshots) {
                                String docId = snapshot.getId();
                                CollegeInfo collegeInfo = snapshot.toObject(CollegeInfo.class);
                                collegeInfo.docid = docId;
                                etxtinfo.setText(collegeInfo.info);
                                txtnewdeadline.setText(collegeInfo.newDeadline);
                                txttransferdeadline.setText(collegeInfo.transferdeadline);
                                newStudent.setText(collegeInfo.newstufee);
                                transerStudent.setText(collegeInfo.transferstufee);
                                etxtphone.setText(collegeInfo.phone);
                                etxtaddress.setText(collegeInfo.address);
                                etxtwebsite.setText(collegeInfo.website);
                            }

                        }
                    }

                });*/
    }


}
