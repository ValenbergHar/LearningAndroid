package com.example.kingsgdl.kings.tablelayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kingsgdl.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentOne extends Fragment {
    private TextView dateReign;
    private ImageView imageViewFragOne;
    private View view;
    private int id;
    private String urlImage;
    private TextView textBirthDeath;


    public FragmentOne(int id, String urlImage) {
        this.id = id;
        this.urlImage = urlImage;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        dateReign = view.findViewById(R.id.reign);
        imageViewFragOne = view.findViewById(R.id.imageViewFragOne);
        textBirthDeath = view.findViewById(R.id.textBirthDeath);
        Glide.with(getActivity()).load(urlImage).into(imageViewFragOne);

        String[] reign = getResources().getStringArray((R.array.reign));
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
