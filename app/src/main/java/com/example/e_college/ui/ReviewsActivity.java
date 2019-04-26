package com.example.e_college.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_college.R;
import com.example.e_college.model.Rating;
import com.example.e_college.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReviewsActivity extends AppCompatActivity implements View.OnClickListener{
    TextView etxtstudentname,etxtplacement,etxtfaculty,etxtreputation,etxtcampus;
    Button submit,choosecollege,viewratings;
    RatingBar eratingplacement,eratingfaculty,eratingreputation,eratingcampus;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;
    Student student;

    Rating rating;

    void initViews(){
        eratingplacement = (RatingBar) findViewById(R.id.placement_rating_bar);
        eratingfaculty = (RatingBar) findViewById(R.id.faculty_rating_bar);
        eratingreputation = (RatingBar) findViewById(R.id.reputation_rating_bar);
        eratingcampus = (RatingBar) findViewById(R.id.campus_rating_bar);

        submit = (Button) findViewById(R.id.submit_button);
        choosecollege = (Button) findViewById(R.id.choosecollege);
        viewratings = (Button) findViewById(R.id.Viewratings);

        etxtstudentname =  findViewById(R.id.etxtStudent);
        etxtplacement= findViewById(R.id.placement);
        etxtfaculty= findViewById(R.id.faculty);
        etxtreputation= findViewById(R.id.Reputation);
        etxtcampus= findViewById(R.id.campus);

        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        user=auth.getCurrentUser();

        student=new Student();

        rating=new Rating();

        eratingplacement.setOnClickListener(this);
        eratingfaculty.setOnClickListener(this);
        eratingreputation.setOnClickListener(this);
        eratingcampus.setOnClickListener(this);

        submit.setOnClickListener(this);
        choosecollege.setOnClickListener(this);
        viewratings.setOnClickListener(this);


       // String tempholder=getIntent().getStringExtra("Listviewclickvalue");
       // choosecollege.setText(tempholder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        getSupportActionBar().hide();
        initViews();
        fetchnamefromcloud();

    }
   void fetchnamefromcloud(){
       String uid = auth.getCurrentUser().getUid();
       Toast.makeText(ReviewsActivity.this, "Uid is there "+uid, Toast.LENGTH_LONG).show();

       db.collection("students").document(uid).get()
               .addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isComplete()) {
                           DocumentSnapshot documentSnapshot = task.getResult();
                           Student student = documentSnapshot.toObject(Student.class);
                           //String docid=snapshot.getId();
                           //String uid = auth.getCurrentUser().getUid();
                           etxtstudentname.setText(student.Name);

                       }
                   }
               });
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == 201) {
            String name = data.getStringExtra("keycollegename");
            choosecollege.setText(name);
        }
    }



            @Override
    public void onClick(View v) {
        int id=v.getId();

        switch (id){
            case R.id.Viewratings:
                if(choosecollege.getText().toString().trim().length() == 0) {
                    Toast.makeText(ReviewsActivity.this, "Please Choose the college", Toast.LENGTH_SHORT).show();
                }else {
                    etxtplacement.setVisibility(View.VISIBLE);
                    etxtplacement.setText("Your rating is: " + eratingplacement.getRating());
                    etxtfaculty.setVisibility(View.VISIBLE);
                    etxtfaculty.setText("Your rating is: " + eratingfaculty.getRating());
                    etxtreputation.setVisibility(View.VISIBLE);
                    etxtreputation.setText("Your rating is: " + eratingreputation.getRating());
                    etxtcampus.setVisibility(View.VISIBLE);
                    etxtcampus.setText("Your rating is: " + eratingcampus.getRating());
                }
                break;

            case R.id.choosecollege:
                Intent intent = new Intent(ReviewsActivity.this, AllCollegeActivity.class);
                startActivityForResult(intent, 101);
                //finish();
                break;
            case R.id.btn_submit:
                rating.placementrating=etxtplacement.getText().toString();
                rating.facultyrating=etxtfaculty.getText().toString();
                rating.reputationrating=etxtreputation.getText().toString();
                rating.campusrating=etxtcampus.getText().toString();
                saveRatingInCloud();
                break;
        }

    }

    private void saveRatingInCloud() {
        db.collection("Colleges").document(user.getUid())
                .collection("Reviews").add(rating)
                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isComplete()) {
                            Toast.makeText(ReviewsActivity.this, "Reviews are successfully added", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(ReviewsActivity.this,MainpageActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }
}
