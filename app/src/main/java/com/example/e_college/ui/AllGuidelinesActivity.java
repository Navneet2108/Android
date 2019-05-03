package com.example.e_college.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.e_college.R;
import com.example.e_college.adapter.GuidelinesAdapter;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.Guidelines;
import com.example.e_college.model.User;
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

public class AllGuidelinesActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    RecyclerView recyclerView;
    ArrayList<Guidelines> guidelinesArrayList;
    int position;
    GuidelinesAdapter guidelinesAdapter;
    Guidelines guidelines;
    User user;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

    void initViews() {
        recyclerView = findViewById(R.id.GuidelinesRecyclerview);
        recyclerView.setAdapter(guidelinesAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        guidelines = new Guidelines();
        user=new User();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_guidelines);
        initViews();
        if (Util.isInternetConnected(this)) {
            fetchGuidelinesFromCloudDb();
        } else {
            Toast.makeText(AllGuidelinesActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }
    }
    void fetchGuidelinesFromCloudDb() {
        db.collection("User").document(firebaseUser.getUid()).collection("College").document(firebaseUser.getUid()).collection("AdmissionGuidelines").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {
                            guidelinesArrayList = new ArrayList<>();
                            QuerySnapshot querySnapshot = task.getResult();

                            List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                            for (DocumentSnapshot snapshot : documentSnapshots) {
                                String docId = snapshot.getId();
                                Guidelines guideln = snapshot.toObject(Guidelines.class);
                                guideln.docid = docId;
                                guidelinesArrayList.add(guideln);
                                Log.i("size", Integer.toString(guidelinesArrayList.size()));

                            }
                            getSupportActionBar().setTitle("Guidelines: " );
                            guidelinesAdapter = new GuidelinesAdapter(AllGuidelinesActivity.this,R.layout.guidelines_item, guidelinesArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllGuidelinesActivity.this);
                            recyclerView.setAdapter(guidelinesAdapter);
                           guidelinesAdapter.setOnRecyclerItemClickListener((OnRecyclerItemClickListener) AllGuidelinesActivity.this);

                            recyclerView.setLayoutManager(linearLayoutManager);

                        } else {
                            Toast.makeText(AllGuidelinesActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }
    void showGuidelinesDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details:");
        builder.setMessage(guidelines.toString());
        builder.setPositiveButton("Done", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    void deleteCollegesFromCloudDB(){
        db.collection("User").document(firebaseUser.getUid()).collection("College").document(firebaseUser.getUid()).collection("AdmissionGuidelines").document(guidelines.docid)
                .delete()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isComplete()){
                            Toast.makeText(AllGuidelinesActivity.this,"Deletion Finished",Toast.LENGTH_LONG).show();
                            guidelinesArrayList.remove(position);
                            guidelinesAdapter.notifyDataSetChanged(); // Refresh Your RecyclerView
                        }else{
                            Toast.makeText(AllGuidelinesActivity.this,"Deletion Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    void askForDeletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are You Sure ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCollegesFromCloudDB();
            }
        });
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void showOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"View","Update", "Delete","Cancel"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        showGuidelinesDetails();
                        break;
                    case 1:
                        /*Intent intent= new Intent(AllGuidelinesActivity.this, GuidelinesActivity.class);
                        intent.putExtra("keyGuideln",guidelines );
                        startActivity(intent);
                        break;*/


                    case 2:
                        askForDeletion();
                        break;



                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onItemClick(int position) {
        this.position = position;
        guidelines = guidelinesArrayList.get(position);
        Toast.makeText(this, "You Clicked on Position:" + position, Toast.LENGTH_LONG).show();
        showOptions();
    }
}
