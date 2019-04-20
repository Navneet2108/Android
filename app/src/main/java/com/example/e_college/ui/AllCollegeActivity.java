package com.example.e_college.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;



import com.example.e_college.R;
import com.example.e_college.adapter.CollegeAdapter;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.College;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import io.opencensus.stats.View;

public class AllCollegeActivity extends AppCompatActivity  implements OnRecyclerItemClickListener {
    RecyclerView recyclerView;
    ArrayList<College> colleges;
    int position;
    CollegeAdapter collegeAdapter;
    College college;




    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(collegeAdapter);
        //recyclerView.setAdapter(coursesAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        college = new College();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_college);
        initViews();
        fetchCollegesFromCloudDb();

    }

    void fetchCollegesFromCloudDb() {
        db.collection("Colleges").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {
                            colleges = new ArrayList<>();

                            QuerySnapshot querySnapshot = task.getResult();

                            List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                            for (DocumentSnapshot snapshot : documentSnapshots) {
                                String docId = snapshot.getId();
                                College college = snapshot.toObject(College.class);
                                college.docID = docId;
                                colleges.add(college);
                                //       Courses courses = Snapshot.toObject(Colleges.class);
                                Log.i("size", Integer.toString(colleges.size()));

                            }
                            getSupportActionBar().setTitle("Total Colleges: " + colleges.size());
                            collegeAdapter = new CollegeAdapter(AllCollegeActivity.this,R.layout.list_item,colleges);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllCollegeActivity.this);
                            recyclerView.setAdapter(collegeAdapter);
                            collegeAdapter.setOnRecyclerItemClickListener((OnRecyclerItemClickListener) AllCollegeActivity.this);

                            recyclerView.setLayoutManager(linearLayoutManager);

                        } else {
                            Toast.makeText(AllCollegeActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }
   /* void showCollegeDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(college.name + "Details:");
        builder.setMessage(college.toString());
        builder.setPositiveButton("Done", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/

    @Override
    public void onItemClick(int position) {
        this.position = position;
        college = colleges.get(position);
        Toast.makeText(this,"You Clicked on Position:"+position,Toast.LENGTH_LONG).show();
        Intent intent=new Intent(AllCollegeActivity.this,AddCoursesActivity.class);
        startActivity(intent);




    }


}


