package com.example.e_college.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.e_college.R;
import com.example.e_college.listener.OnRecyclerItemClickListener;
import com.example.e_college.model.Guidelines;


import java.util.ArrayList;

public class GuidelinesAdapter extends RecyclerView.Adapter<GuidelinesAdapter.ViewHolder> {
    Context context;
    int resources;
    ArrayList<Guidelines> objects;

    OnRecyclerItemClickListener recyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener recycleritemclicklistener) {
        this.recyclerItemClickListener =  recycleritemclicklistener;

    }


    public  GuidelinesAdapter(Context context, int resources, ArrayList<Guidelines> objects) {
        this.context = context;
        this.resources = resources;
        this.objects = objects;
    }

    @Override
    public GuidelinesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resources, parent, false);
        final GuidelinesAdapter.ViewHolder holder = new GuidelinesAdapter.ViewHolder(view);
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
        Guidelines guidelines= objects.get(position);
        holder.txtTitle.setText(guidelines.guideln);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.textViewTitle);
        }
    }
}



