package com.example.kingsgdl.kings.tablelayout;

import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.King;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

public class FragmentOne extends Fragment {
    private View view;
    private TextView dateReign;
    private ImageView imageViewFragOne;
    private TextView textBirthDeath;
    private TextView txtKingShortHist;
    private King king;

    public FragmentOne(King king) {
        this.king = king;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        dateReign = view.findViewById(R.id.reign);
        dateReign.setText(king.getKingDateReign());

        textBirthDeath = view.findViewById(R.id.textBirthDeath);
        textBirthDeath.setText(king.getKingDateLife());


        String htmlString = king.getKingShortHist();
        Spanned spanned = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT);
        txtKingShortHist = view.findViewById(R.id.txtKingShortHist);
        txtKingShortHist.setText(spanned);

//        txtKingShortHist.setText(king.getKingShortHist());

        imageViewFragOne = view.findViewById(R.id.imageViewFragOne);
        Glide.with(getActivity()).load(king.getKingPhotos().get(0)).into(imageViewFragOne);

        return view;
    }
}
