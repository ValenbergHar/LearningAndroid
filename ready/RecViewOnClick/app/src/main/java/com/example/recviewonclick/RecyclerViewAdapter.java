package com.example.recviewonclick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<ExampleItem> exampleItemList;
    private Context context;

    // clickRecycle
    private OnItemClickListener mListener;

    // clickRecycle 1
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDelete(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public RecyclerViewAdapter(List<ExampleItem> exampleItemList, Context context) {
        this.exampleItemList = exampleItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt1.setText(exampleItemList.get(position).getTxt1());
        holder.txt2.setText(exampleItemList.get(position).getTxt2());
        holder.imageView.setImageResource(exampleItemList.get(position).getImageResource());
    }


    @Override
    public int getItemCount() {
        return exampleItemList.size();
    }

    // clickRecycle static 2
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt1;
        private TextView txt2;
        private ImageView imageView;
        private  ImageView imageViewDel;

        // clickRecycle static 3
        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            imageView = itemView.findViewById(R.id.imageView);
            imageViewDel = itemView.findViewById(R.id.image_delete);

            // clickRecycle 4
            imageViewDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDelete(position);
                        }
                    }

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }
}
