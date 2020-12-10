package com.example.gdlkingslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import javax.microedition.khronos.opengles.GL;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_king, parent, false);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_king_picture = itemView.findViewById(R.id.iv_king_picture);
            txt_king_name = itemView.findViewById(R.id.txt_king_name);
            txt_king_date = itemView.findViewById(R.id.txt_date);

        }
    }
}
