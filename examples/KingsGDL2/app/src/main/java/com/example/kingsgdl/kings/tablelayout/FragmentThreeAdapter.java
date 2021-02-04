package com.example.kingsgdl.kings.tablelayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kingsgdl.kings.King;

import java.util.ArrayList;
import java.util.List;

public class FragmentThreeAdapter extends RecyclerView.Adapter<FragmentThreeAdapter.FragmentThreeViewHolder> {

    private List<String> kingsPhotoList;
    private List<String> kingsPhotoNameList;
    private Context context;

    public FragmentThreeAdapter(List<String> kingsPhotoList, List<String> kingsPhotoNameList, Context context) {
        this.kingsPhotoList = kingsPhotoList;
        this.kingsPhotoNameList = kingsPhotoNameList;
        this.context = context;
    }


    @NonNull
    @Override
    public FragmentThreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentThreeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FragmentThreeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewImageKing;
        TextView txtNameImageKing;

        public FragmentThreeViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
