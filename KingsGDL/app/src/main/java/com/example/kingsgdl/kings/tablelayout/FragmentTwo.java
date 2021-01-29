package com.example.kingsgdl.kings.tablelayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kingsgdl.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentTwo extends Fragment {
    private View view;
    private int id;
    private TextView txtKingStory;


    public FragmentTwo(int id) {
        this.id = id;
    }

    public FragmentTwo() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, container, false);
        txtKingStory=view.findViewById(R.id.txtKingStory);
        String[] arrKingStory = getResources().getStringArray((R.array.story));

        for (int i = 0; i < arrKingStory.length; i++) {
            if (id == i) {
                txtKingStory.setText(arrKingStory[i]);
                  break;
            }
        }

        return view;
    }
}
