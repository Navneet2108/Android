package com.example.e_college.ui;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.e_college.R;
import com.example.e_college.adapter.AppliedAdapter;
import com.example.e_college.adapter.CollegeAdapter;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.College;
import com.example.e_college.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AppliedColleges extends AppCompatActivity implements OnRecyclerItemClickListener {
    RecyclerView recyclerView;
    ArrayList<College> colleges;
    int position;
    AppliedAdapter appliedAdapter;
    College college;

    ProgressDialog progressDialog;

Student student;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

    void initViews() {


        recyclerView = findViewById(R.id.AppliedcollegesRecyclerView);
        recyclerView.setAdapter(appliedAdapter);


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        college = new College();
        student=new Student();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_colleges);
        initViews();
        fetchCollegesFromCloudDb();
    }

  void fetchCollegesFromCloudDb() {
      String uid = firebaseAuth.getCurrentUser().getUid();
      //Toast.makeText(MainpageActivity.this, "Uid is there "+uid, Toast.LENGTH_LONG).show();

      db.collection("students").document(uid).get()
              .addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if(task.isComplete()){
                          colleges = new ArrayList<>();
                          DocumentSnapshot documentSnapshot=task.getResult();
                          Student student = documentSnapshot.toObject(Student.class);
                          //String docid=snapshot.getId();
                          //String uid = auth.getCurrentUser().getUid();
                          String docId = documentSnapshot.getId();

                          student.docID = docId;
                          colleges.add(college);
                          //       Courses courses = Snapshot.toObject(Colleges.class);
                          Log.i("size", Integer.toString(colleges.size()));


                      getSupportActionBar().setTitle("Total Colleges: " + colleges.size());
                      appliedAdapter = new AppliedAdapter(AppliedColleges.this, R.id.list_item,colleges);
                      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppliedColleges.this);
                      recyclerView.setAdapter(appliedAdapter);
                          appliedAdapter.setOnRecyclerItemClickListener((OnRecyclerItemClickListener) AppliedColleges.this);

                      recyclerView.setLayoutManager(linearLayoutManager);

                  } else {
                      Toast.makeText(AppliedColleges.this, "Some Error", Toast.LENGTH_LONG).show();
                  }



                      }

              });
    }

    @Override
    public void onItemClick(int position) {
        this.position = position;
        college = colleges.get(position);
        Toast.makeText(this, "You Clicked on Position:" + position, Toast.LENGTH_LONG).show();

    }
}
