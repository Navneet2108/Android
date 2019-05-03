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
import com.example.e_college.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    int resources;
    ArrayList<User> objects;

    OnRecyclerItemClickListener recyclerItemClickListener;
    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public UserAdapter(Context context, int resources, ArrayList<User> objects) {
        this.context = context;
        this.resources = resources;
        this.objects = objects;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resources, parent, false);
        final UserAdapter.ViewHolder holder = new UserAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
        return  holder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User user = objects.get(position);
        holder.txtusertitle.setText(user.Name);
        holder.txtuseremail.setText(user.Email);

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtusertitle;
        TextView txtuseremail;
        public ViewHolder(View itemView) {
            super(itemView);
            txtusertitle=itemView.findViewById(R.id.usertitle);
            txtuseremail=itemView.findViewById(R.id.useremail);
        }
    }
}
