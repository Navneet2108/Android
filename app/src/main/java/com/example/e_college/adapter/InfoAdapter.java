package com.example.e_college.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_college.R;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.CollegeInfo;
import com.example.e_college.model.Guidelines;
import com.example.e_college.ui.PersonalInfoActivity;
import com.example.e_college.ui.StudentProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    Context context;
    int resources;
    ArrayList<CollegeInfo> objects;

    OnRecyclerItemClickListener recyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener recycleritemclicklistener) {
        this.recyclerItemClickListener =  recycleritemclicklistener;

    }


    public  InfoAdapter(Context context, int resources, ArrayList<CollegeInfo> objects) {
        this.context = context;
        this.resources = resources;
        this.objects = objects;
    }


    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resources, parent, false);
        final InfoAdapter.ViewHolder holder = new InfoAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final InfoAdapter.ViewHolder holder, int position) {
      CollegeInfo collegeInfo=objects.get(position);
      holder.etxtinfo.setText(collegeInfo.info);
        holder.txtnewdeadline.setText(collegeInfo.newDeadline);
        holder.txttransferdeadline.setText(collegeInfo.transferdeadline);
        holder.newStudent.setText(collegeInfo.newstufee);
        holder.transerStudent.setText(collegeInfo.transferstufee);
        holder.etxtaddress.setText(collegeInfo.address);
        holder.etxtwebsite.setText(collegeInfo.website);
        holder.etxtphone.setText(collegeInfo.phone);



    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView etxtinfo;
        TextView etxtaddress,etxtwebsite,etxtphone;
        TextView txtnewdeadline,txttransferdeadline;
        TextView newStudent,transerStudent;


        public ViewHolder(View itemView) {
            super(itemView);
            txtnewdeadline=itemView.findViewById(R.id.NewDeadline);
            txttransferdeadline=itemView.findViewById(R.id.transferDeadline);
            etxtinfo = itemView.findViewById(R.id.editTextinfo);
            etxtaddress =itemView.findViewById(R.id.editTextAddress);
            etxtwebsite=itemView.findViewById(R.id.editTextWebsite);
            etxtphone=itemView.findViewById(R.id.editTextphone);
            newStudent =itemView.findViewById(R.id.newStudent);
            transerStudent=itemView.findViewById(R.id.transferStudent);



        }

    }
}
