package com.example.e_college.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_college.R;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.College;
import com.example.e_college.ui.AppliedColleges;

import java.util.ArrayList;

public class AppliedAdapter extends RecyclerView.Adapter<AppliedAdapter.ViewHolder> {
    Context context;
    int resources;
    ArrayList<College> objects;



    OnRecyclerItemClickListener recyclerItemClickListener;

    public AppliedAdapter(AppliedColleges appliedColleges, int list_item, ArrayList<College> colleges) {
        this.context=appliedColleges;
        this.resources=list_item;
        this.objects=colleges;
    }


    @Override
    public AppliedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resources, parent, false);
        final AppliedAdapter.ViewHolder holder = new AppliedAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        College colleges = objects.get(position);
        holder.txtTitle.setText(colleges.name);
        holder.txtCity.setText(colleges.city);
        holder.txtState.setText(colleges.state);
    }



    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.recyclerItemClickListener=onRecyclerItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtCity;
        TextView txtState;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.textViewTitle);
            txtCity = itemView.findViewById(R.id.textViewCity);
            txtState = itemView.findViewById(R.id.textViewState);
        }
    }
}
