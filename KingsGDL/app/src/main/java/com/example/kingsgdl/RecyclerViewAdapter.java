package com.example.kingsgdl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<King> kingList;
    Context context;

    public RecyclerViewAdapter(List<King> kingList, Context context) {
        this.kingList = kingList;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.king_one, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_king_name.setText(kingList.get(position).getName());
        holder.txt_king_date.setText(String.valueOf(kingList.get(position).getDateOfElection()));
        Glide.with(context).load(kingList.get(position).getImageUrl()).into(holder.iv_king_picture);
    }

    @Override
    public int getItemCount() {
        return kingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_king_picture;
        TextView txt_king_name;
        TextView txt_king_date;
        ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_king_picture = itemView.findViewById(R.id.iv_king_picture);
            txt_king_name = itemView.findViewById(R.id.txt_king_name);
            txt_king_date = itemView.findViewById(R.id.txt_date);
            constraintLayout = itemView.findViewById(R.id.oneLineKingLayout);



        }
    }
}
