package com.example.currency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Data> dataList;
    private Context context;

    public RecyclerViewAdapter(List<Data> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.unit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.data1.setText(dataList.get(position).getData1());
        holder.data2.setText(dataList.get(position).getData2());
        holder.data3.setText(dataList.get(position).getData3());
        holder.data4.setText(dataList.get(position).getData4());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView data1;
        private TextView data2;
        private TextView data3;
        private TextView data4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            data1 = itemView.findViewById(R.id.data1);
            data2 = itemView.findViewById(R.id.data2);
            data3 = itemView.findViewById(R.id.data3);
            data4 = itemView.findViewById(R.id.data4);
        }
    }
}
