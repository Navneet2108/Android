package com.example.e_college.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.e_college.R;
import com.example.e_college.adapter.GuidelinesAdapter;
import com.example.e_college.adapter.InfoAdapter;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.College;
import com.example.e_college.model.CollegeInfo;
import com.example.e_college.model.Guidelines;
import com.example.e_college.model.Student;
import com.example.e_college.model.User;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    RecyclerView recyclerView;
    ArrayList<CollegeInfo> collegeInfos;
    int position;
    InfoAdapter infoAdapter;
    CollegeInfo collegeInfo;
    User user;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

    void initViews() {
        recyclerView = findViewById(R.id.InforecyclerView);
        recyclerView.setAdapter(infoAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        collegeInfo = new CollegeInfo();
        user=new User();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("E-College");
        setContentView(R.layout.activity_info);
        initViews();
        if (Util.isInternetConnected(this)) {
            fetchCollegeInfoFromCloudDb();
        } else {
            Toast.makeText(InfoActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }
    }
    void fetchCollegeInfoFromCloudDb() {
        Intent rcv=getIntent();
        String id=rcv.getStringExtra("keyid");
        Toast.makeText(InfoActivity.this,"id is here "+id,Toast.LENGTH_LONG).show();
        db.collection("User").document(firebaseUser.getUid()).collection("College").document(id).collection("CollegeDetails").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {
                            collegeInfos = new ArrayList<>();
                            QuerySnapshot querySnapshot = task.getResult();

                            List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                            for (DocumentSnapshot snapshot : documentSnapshots) {
                                String docId = snapshot.getId();
                                CollegeInfo collegeInfo = snapshot.toObject(CollegeInfo.class);
                                collegeInfo.docid = docId;
                                collegeInfos.add(collegeInfo);
                                Log.i("size", Integer.toString(collegeInfos.size()));

                            }
                            getSupportActionBar().setTitle("College Details" );
                            infoAdapter = new InfoAdapter(InfoActivity.this,R.layout.info, collegeInfos);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InfoActivity.this);
                            recyclerView.setAdapter(infoAdapter);
                            infoAdapter.setOnRecyclerItemClickListener((OnRecyclerItemClickListener) InfoActivity.this);

                            recyclerView.setLayoutManager(linearLayoutManager);

                        } else {
                            Toast.makeText(InfoActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    @Override
    public void onItemClick(int position) {
        this.position = position;
        collegeInfo = collegeInfos.get(position);
        //Toast.makeText(this, "You Clicked on Position:" + position, Toast.LENGTH_LONG).show();
        //showOptions();
    }
}
