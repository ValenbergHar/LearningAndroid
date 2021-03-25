package com.example.kingsgdl.kings.tablelayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.KingFull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentThree extends Fragment {
    private View view;
    private RecyclerView photoRecView;
    private PhotoAdapter adapter;
    private KingFull king;

    public FragmentThree(KingFull king) {
        this.king = king;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_three, container, false);

        adapter= new PhotoAdapter(this, king.getKingPhotos(), king.getKingPhotosDesc());
        photoRecView = view.findViewById(R.id.photoRecView);
        photoRecView.setAdapter(adapter);
        photoRecView.setLayoutManager(new GridLayoutManager(getContext(),2));


     return view;
    }
}
