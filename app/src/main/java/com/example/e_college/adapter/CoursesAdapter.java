package com.example.e_college.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.e_college.R;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.Courses;
import com.example.e_college.ui.CoursesActivity;


import java.util.ArrayList;

public class CoursesAdapter  extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    Context context;
    int resources;
    ArrayList<Courses> objects;

    OnRecyclerItemClickListener recyclerItemClickListener;

    public CoursesAdapter(CoursesActivity coursesActivity, int course, ArrayList<Courses> coursesArrayList) {
        this.context=coursesActivity;
        this.resources=course;
        this.objects=coursesArrayList;
    }


    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }



    @NonNull
    @Override
    public CoursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resources, parent, false);
        final CoursesAdapter.ViewHolder holder = new CoursesAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesAdapter.ViewHolder viewHolder, int position) {
        Courses courses = objects.get(position);
        viewHolder.txtTitle.setText(courses.Name);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.textViewTitleCourse);
        }
    }
}
