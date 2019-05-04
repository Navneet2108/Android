package com.example.e_college.ui;

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
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_college.R;
import com.example.e_college.adapter.CollegeAdapter;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.College;
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

public class  AllCollegeActivity extends AppCompatActivity  implements OnRecyclerItemClickListener {
    RecyclerView recyclerView;
    ArrayList<College> colleges;
    int position;
    CollegeAdapter collegeAdapter;
    College college;
    String id;
    String name;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    int status=0;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPreferences" ;
    public static final String collegeKey= "collegeKey";
    public static final String collegeid= "collegeid";
    String cid;

    void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(collegeAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        college = new College();

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_college);
        initViews();
        if (Util.isInternetConnected(this)) {
            fetchCollegesFromCloudDb();
        } else {
            Toast.makeText(AllCollegeActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }

    }

    void fetchCollegesFromCloudDb() {
        Intent rcv=getIntent();
        String id=rcv.getStringExtra("keyid");
        db.collection("User").document(id).collection("College").get()
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
                                Log.i("size", Integer.toString(colleges.size()));

                            }
                            getSupportActionBar().setTitle("Total Colleges: " + colleges.size());
                            collegeAdapter = new CollegeAdapter(AllCollegeActivity.this, R.layout.list_item, colleges);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllCollegeActivity.this);
                            recyclerView.setAdapter(collegeAdapter);
                            collegeAdapter.setOnRecyclerItemClickListener(AllCollegeActivity.this);

                            recyclerView.setLayoutManager(linearLayoutManager);

                        } else {
                            Toast.makeText(AllCollegeActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }


    @Override
    public void onItemClick(int position) {
        this.position = position;
        college = colleges.get(position);
        //String UserInfo = colleges.get(position).toString();
         //userId = UserInfo.substring(0, UserInfo .indexOf(" "));
         name=college.name;
         cid=college.docID;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(collegeKey, name);
        editor.putString(collegeid,cid);
        editor.apply();
        Toast.makeText(this, "Thanks", Toast.LENGTH_LONG).show();
        showAlert();


    }

    void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"Show Details", "Choose college For Reviews","Choose college for Courses","Cancel"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent=new Intent(AllCollegeActivity.this, InfoActivity.class);
                        intent.putExtra("keyid",id);
                        startActivity(intent);
                        break;

                    case 1:
                        Intent data1 = new Intent(AllCollegeActivity.this, ReviewsActivity.class);
                       data1.putExtra(collegeKey,name);
                       data1.putExtra(collegeid,cid);
                        //setResult(201,data1);
                       startActivity(data1);
                        finish();
                        break;

                    case 2:

                        Intent data = new Intent(AllCollegeActivity.this, MainpageActivity.class);
                        data.putExtra(collegeKey,name);
                        startActivity(data);
                        finish();
                        break;

                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
