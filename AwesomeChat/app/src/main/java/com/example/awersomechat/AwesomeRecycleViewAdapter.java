package com.example.awersomechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AwesomeRecycleViewAdapter extends RecyclerView.Adapter<AwesomeRecycleViewAdapter.ViewHolder> {
    private List<AwesomeMessage> messages;
    private Context context;

    public AwesomeRecycleViewAdapter(List<AwesomeMessage> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(messages.get(position).getText());
        holder.textTextView.setText(messages.get(position).getName());
        Glide.with(holder.photoImageView.getContext()).load(messages.get(position).getImageUrl()).into(holder.photoImageView);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTextView;
        private TextView nameTextView;
        private ImageView photoImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textTextView = itemView.findViewById(R.id.textTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
             photoImageView = itemView.findViewById(R.id.photoImageView);

        }
    }
}
