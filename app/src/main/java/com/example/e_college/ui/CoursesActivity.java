package com.example.e_college.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.e_college.R;
import com.example.e_college.adapter.CoursesAdapter;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.Courses;
import com.example.e_college.model.Student;
import com.example.e_college.model.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoursesActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    Courses courses;

    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore db;

    RecyclerView recyclerView;
    ArrayList<Courses> coursesArrayList;
    int position;
    CoursesAdapter coursesAdapter;
    ProgressDialog progressDialog;

    Student student;


    TextView txtViewTitleCourse;
    
    public void initViews(){
       txtViewTitleCourse=findViewById(R.id.textViewTitleCourse);
       
       courses = new Courses();

       auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        recyclerView = findViewById(R.id.CourseRecyclerView);
        recyclerView.setAdapter(coursesAdapter);



        student=new Student();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        Intent rcv = getIntent();
        String name = rcv.getStringExtra("keycollege");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        initViews();
        fetchCourseFromCloud();
    }

    private void fetchCourseFromCloud() {
        db.collection("Colleges").document(firebaseUser.getUid()).collection("Courses").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                            coursesArrayList=new ArrayList<>();
                            QuerySnapshot querySnapshot=task.getResult();
                            List<DocumentSnapshot> documentSnapshots=querySnapshot.getDocuments();

                            for (DocumentSnapshot snapshot:documentSnapshots){
                                String docid=snapshot.getId();
                                Courses courses1=snapshot.toObject(Courses.class);
                                courses1.doc_Id=docid;
                                coursesArrayList.add(courses1);
                            }

                            getSupportActionBar().setTitle("Total Courses: "+coursesArrayList.size());

                            coursesAdapter=new CoursesAdapter(CoursesActivity.this,R.layout.course,coursesArrayList);
                            LinearLayoutManager layoutManager=new LinearLayoutManager(CoursesActivity.this);
                            recyclerView.setAdapter(coursesAdapter);
                            coursesAdapter.setOnRecyclerItemClickListener((OnRecyclerItemClickListener) CoursesActivity.this);
                            recyclerView.setLayoutManager(layoutManager);

                        }else {
                            Toast.makeText(CoursesActivity.this,"Some Error",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
    void saveCoursedetails(){
        progressDialog.show();
        firebaseUser=auth.getCurrentUser();
        db.collection("students").document(firebaseUser.getUid()).set(student)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CoursesActivity.this, student.Name + " Applied Successful", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(CoursesActivity.this, MainpageActivity.class);
                        startActivity(intent);

                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        this.position = position;
        courses = coursesArrayList.get(position);
        Toast.makeText(this,"You Clicked on Position:"+position,Toast.LENGTH_LONG).show();
       // student.CourseName=txtViewTitleCourse.getText().toString();

       /* if (Util.isInternetConnected(this)) {
            saveCoursedetails();
        } else {
            Toast.makeText(CoursesActivity.this, "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
        }*/





    }

}
