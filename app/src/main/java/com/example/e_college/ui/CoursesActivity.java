package com.example.e_college.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.example.e_college.model.User;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CoursesActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    Courses courses;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPreferences" ;
    public static final String collegeId = "IDKey";
    public static final String collegeKey= "collegeKey";
    String Name;
    String cid;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseFirestore db;
    User user;

    RecyclerView recyclerView;
    ArrayList<Courses> coursesArrayList;
    int position;
    CoursesAdapter coursesAdapter;
    ProgressDialog progressDialog;

    //String uid;
    public void initViews(){
       
         courses = new Courses();

        user=new User();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();

        recyclerView = findViewById(R.id.CourseRecyclerView);
        recyclerView.setAdapter(coursesAdapter);
        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Name=sharedPreferences.getString(collegeKey,"");
        cid=sharedPreferences.getString(collegeId,"");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        initViews();

        fetchCourseFromCloud();
    }

    private void fetchCourseFromCloud() {
        Toast.makeText(CoursesActivity.this, "id is here "+cid, Toast.LENGTH_SHORT).show();
        db.collection("User").document(firebaseUser.getUid()).collection("College").document(cid).collection("Courses").get()
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

    @Override
    public void onItemClick(int position) {
        this.position = position;
        courses = coursesArrayList.get(position);
        Toast.makeText(this,"You Clicked on Position:"+position,Toast.LENGTH_LONG).show();
        //showOptions();

    }
   /* void deleteCoursesFromCloudDB(){

        db.collection("User").document(firebaseUser.getUid()).collection("College").document(firebaseUser.getUid()).collection("Courses").document(courses.doc_Id)
                .delete()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isComplete()){
                            Toast.makeText(CoursesActivity.this,"Deletion Finished",Toast.LENGTH_LONG).show();

                            coursesArrayList.remove(position);
                            coursesAdapter.notifyDataSetChanged(); // Refresh Your RecyclerView
                        }else{
                            Toast.makeText(CoursesActivity.this,"Deletion Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    void askForDeletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+courses.Name);
        builder.setMessage("Are You Sure ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCoursesFromCloudDB();
            }
        });
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/
    void showOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"Update " + courses.Name, "Delete " + courses.Name,"Cancel"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        /*Intent intent = new Intent(CoursesActivity.this, AddCoursesActivity.class);
                        intent.putExtra("keyCourse", courses);
                        startActivity(intent);*/
                        break;

                    case 1:
                       // askForDeletion();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


}
