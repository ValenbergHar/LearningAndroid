package com.example.sqldemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<Person> personList;
    Context context;

    public RecyclerViewAdapter(List<Person> personList, Context context) {
        this.personList = personList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_one_activity, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_id.setText(String.valueOf(personList.get(position).getId()));
        holder.txt_name.setText(personList.get(position).getName());
        holder.txt_age.setText(String.valueOf(personList.get(position).getAge()));
        holder.isActive.setText(personList.get(position).isActive() == 1 ? "true" : "false");
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_id;
        private TextView txt_name;
        private TextView txt_age;
        private TextView isActive;
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id = itemView.findViewById(R.id.txt_id);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_age = itemView.findViewById(R.id.txt_age);
            isActive = itemView.findViewById(R.id.txt_isActive);
            constraintLayout = itemView.findViewById(R.id.onePersonLayout);

        }
    }
}
