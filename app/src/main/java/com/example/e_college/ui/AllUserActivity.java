package com.example.e_college.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.e_college.R;
import com.example.e_college.adapter.GuidelinesAdapter;
import com.example.e_college.adapter.UserAdapter;
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

public class AllUserActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    int position;
    UserAdapter userAdapter;
    User user;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

    void initViews() {
        recyclerView = findViewById(R.id.UserRecyclerView);
        recyclerView.setAdapter(userAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        user=new User();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);
        initViews();
        if (Util.isInternetConnected(this)) {
            fetchUserFromCloudDb();
        } else {
            Toast.makeText(AllUserActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchUserFromCloudDb() {
        db.collection("User").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {
                            userArrayList = new ArrayList<>();
                            QuerySnapshot querySnapshot = task.getResult();

                            List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                            for (DocumentSnapshot snapshot : documentSnapshots) {
                                String docId = snapshot.getId();
                                User user = snapshot.toObject(User.class);
                                user.docid = docId;
                                userArrayList.add(user);
                                Log.i("size", Integer.toString(userArrayList.size()));

                            }
                            getSupportActionBar().setTitle("Total Users: " +userArrayList.size() );
                            userAdapter = new UserAdapter(AllUserActivity.this,R.layout.user, userArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllUserActivity.this);
                            recyclerView.setAdapter(userAdapter);
                            userAdapter.setOnRecyclerItemClickListener((OnRecyclerItemClickListener) AllUserActivity.this);

                            recyclerView.setLayoutManager(linearLayoutManager);

                        } else {
                            Toast.makeText(AllUserActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    @Override
    public void onItemClick(int position) {
        this.position = position;
        user = userArrayList.get(position);
        String id=user.docid;
        Toast.makeText(this, "You Clicked on Position:" + position, Toast.LENGTH_LONG).show();
        Intent intent=new Intent(AllUserActivity.this, AllCollegeActivity.class);
        intent.putExtra("keyid",id);
        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
    }


}
