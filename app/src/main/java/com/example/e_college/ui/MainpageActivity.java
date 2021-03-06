package com.example.e_college.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.e_college.R;
import com.example.e_college.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainpageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    FirebaseAuth auth;
    TextView txtUsername,txtEmail;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    Button btnCourses,btnlocation,btncolleges;
SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPreferences" ;
    public static final String collegeKey= "collegeKey";
   public static final String collegeId = "IDKey";
    String Name;
    String cid;
    void initViews(){
        btnCourses=findViewById(R.id.FindCourses);
       // btnlocation=findViewById(R.id.Location);
        btncolleges=findViewById(R.id.FindColleges);
       // btncolleges.setText("FIND COLLEGES");
        btnCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btncolleges.getText().toString().trim().length()!=0){
                    String name=btncolleges.getText().toString();
                    Intent course = new Intent(MainpageActivity.this, CoursesActivity.class);
                    course.putExtra("keycollege",name);
                    startActivity(course);

                }else {
                    Toast.makeText(MainpageActivity.this, "Please select college", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncolleges.setOnClickListener(this);

        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Name=sharedPreferences.getString(collegeKey,"");
        cid=sharedPreferences.getString(collegeId,"");
        btncolleges.setText(Name);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("E-College");
        initViews();
        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);


        txtUsername = (TextView) headerView.findViewById(R.id.textUsername);
        txtEmail = (TextView) headerView.findViewById(R.id.textEmail);
        auth = FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        getCurrentStudentDetail();

    }

    void getCurrentStudentDetail(){

        String uid = auth.getCurrentUser().getUid();
        //Toast.makeText(MainpageActivity.this, "Uid is there "+uid, Toast.LENGTH_LONG).show();

        firebaseFirestore.collection("students").document(uid).get()
                .addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if(task.isComplete()){
                  DocumentSnapshot documentSnapshot=task.getResult();
                  Student student = documentSnapshot.toObject(Student.class);
                  //String docid=snapshot.getId();
                  //String uid = auth.getCurrentUser().getUid();
                  txtUsername.setText(student.Name);
                  txtEmail.setText(student.Email);


              }
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainpage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
            //return true;
        //}else{
            auth.signOut();// Clear Logged In Users Data from Shared Preferences
            Intent intent=new Intent(MainpageActivity.this, LoginActivity.class);
            startActivity(intent);

        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
           Intent intent=new Intent(MainpageActivity.this,MainpageActivity.class);
           startActivity(intent);


        } else if (id == R.id.nav_colleges) {
            Intent intent=new Intent(MainpageActivity.this,AllUserActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_login) {
            Intent intent=new Intent(MainpageActivity.this,PersonalInfoActivity.class);
            startActivity(intent);


        }// else if (id == R.id.nav_search) {}

         else if (id == R.id.nav_payment) {
            Intent intent=new Intent(MainpageActivity.this,PaymentCardActivity.class);
            startActivity(intent);

        }// else if (id == R.id.nav_help) {
            //Intent intent=new Intent(MainpageActivity.this,AllStudentActivity.class);
            //startActivity(intent);

        //}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
       return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == 202) {
            String name = data.getStringExtra("keycollegename");
            btncolleges.setText(name);
        }
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();

        switch(id){
            case R.id.FindColleges:
                Intent intent = new Intent(MainpageActivity.this, AllUserActivity.class);
                startActivityForResult(intent, 101);
                break;

            case R.id.FindCourses:

            if(btncolleges.getText().toString().trim().length()!=0){
                Intent intent1 = new Intent(MainpageActivity.this, CoursesActivity.class);
                intent1.putExtra(collegeId,cid);
               startActivity(intent1);

            }else{
                Toast.makeText(MainpageActivity.this, "Please choose college", Toast.LENGTH_SHORT).show();
            }
                break;
        }
    }
}
