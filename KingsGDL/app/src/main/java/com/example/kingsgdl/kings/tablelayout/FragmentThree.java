package com.example.kingsgdl.kings.tablelayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.King;
import com.example.kingsgdl.kings.RecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FragmentThree extends Fragment {
    private View view;
    private int id;

    private List<String> kingsPhotoList;
    private List<String> kingsPhotoNameList;


    private RecyclerView recyclerView;
    private FragmentThreeAdapter mFTAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public FragmentThree(int id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_three, container, false);



        String[] date = getResources().getStringArray((R.array.date));

        for (int i = 0; i < reign.length; i++) {
            if (id == i) {
                textBirthDeath.setText(date[i]);
                dateReign.setText(reign[i]);
                break;
            }
        }

        return view;
    }
}
