package com.example.kingsgdl.kings.tablelayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.King;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentTwo extends Fragment {
    private View view;
    private TextView txtkingLongHist;
    private King king;


    public FragmentTwo(King king) {
        this.king = king;
    }

    public FragmentTwo() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, container, false);
        txtkingLongHist=view.findViewById(R.id.txtkingLongHist);
        txtkingLongHist.setText(king.getKingLongHist());

        return view;
    }
}
