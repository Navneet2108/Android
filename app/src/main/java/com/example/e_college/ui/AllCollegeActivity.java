package com.example.e_college.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;



import com.example.e_college.R;
import com.example.e_college.adapter.CollegeAdapter;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.College;


import com.example.e_college.model.Student;
import com.example.e_college.model.Util;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.opencensus.stats.View;

public class AllCollegeActivity extends AppCompatActivity  implements OnRecyclerItemClickListener {
    RecyclerView recyclerView;
    ArrayList<College> colleges;
    int position;
    CollegeAdapter collegeAdapter;
    College college;

ProgressDialog progressDialog;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;


    TextView txtViewTitle;

    TextView txtViewState;

    TextView txtViewCity;

    Student student;
    void initViews() {
        txtViewTitle=findViewById(R.id.textViewTitle);
        txtViewState=findViewById(R.id.textViewState);
        txtViewCity=findViewById(R.id.textViewCity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(collegeAdapter);
        //recyclerView.setAdapter(coursesAdapter);

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

  /* void saveCollegedetails(){
       progressDialog.show();
       firebaseUser=firebaseAuth.getCurrentUser();
       db.collection("students").document(firebaseUser.getUid()).set(student)
               .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       Toast.makeText(AllCollegeActivity.this, student.Name + " Applied Successful", Toast.LENGTH_LONG).show();
                       progressDialog.dismiss();
                       Intent intent = new Intent(AllCollegeActivity.this, MainpageActivity.class);
                       startActivity(intent);
                       finish();

                   }
               });
   }*/



    @Override
    public void onItemClick(int position) {
        this.position = position;
        college = colleges.get(position);
        Toast.makeText(this,"You Clicked on Position:"+position,Toast.LENGTH_LONG).show();
        //Intent intent=new Intent(AllCollegeActivity.this,AddCoursesActivity.class);
       // startActivity(intent);

       /* student.CollegeName=txtViewTitle.getText().toString();
        student.State=txtViewState.getText().toString();
        student.City=txtViewCity.getText().toString();
        if (Util.isInternetConnected(this)) {
            saveCollegedetails();
        } else {
            Toast.makeText(AllCollegeActivity.this, "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
        }*/
        }


}


