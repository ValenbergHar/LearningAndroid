package com.example.kingsgdl.kings.tablelayout;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.KingFull;

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
    private KingFull kingFull;
    private TextView nameReign;

    public FragmentOne(KingFull king) {
        this.kingFull = king;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        dateReign = view.findViewById(R.id.reign);
        nameReign= view.findViewById(R.id.name_reign);

        if (kingFull.getKingDateReign() == null) {
            dateReign.setVisibility(View.INVISIBLE);
            nameReign.setVisibility(View.INVISIBLE);
        } else {
            dateReign.setText(kingFull.getKingDateReign());
        }

        textBirthDeath = view.findViewById(R.id.textBirthDeath);


        textBirthDeath.setText(kingFull.getKingDateLife());

//        txtkingLongHist.setLinksClickable(true);
//        SpannableString ss = new SpannableString(HtmlCompat.fromHtml(htmlString,HtmlCompat.FROM_HTML_MODE_COMPACT));

        String htmlString = kingFull.getKingShortHist();
        txtKingShortHist = view.findViewById(R.id.txtKingShortHist);
        txtKingShortHist.setLinksClickable(true);
        SpannableString spannableString = new SpannableString(HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT));
        txtKingShortHist.setText(spannableString);
        txtKingShortHist.setMovementMethod(LinkMovementMethod.getInstance());

//        txtKingShortHist.setText(king.getKingShortHist());

        imageViewFragOne = view.findViewById(R.id.imageViewFragOne);
        Glide.with(getActivity()).load(kingFull.getKingPhotos().get(0)).into(imageViewFragOne);

        return view;
    }
}
