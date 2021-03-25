package com.example.kingsgdl.kings.tablelayout;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.KingFull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

public class FragmentTwo extends Fragment {
    private View view;
    private TextView txtkingLongHist;
    private KingFull king;


    public FragmentTwo(KingFull king) {
        this.king = king;
    }

    public FragmentTwo() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, container, false);
        txtkingLongHist=view.findViewById(R.id.txtkingLongHist);
        String htmlString =king.getKingLongHist();
        txtkingLongHist.setLinksClickable(true);
        SpannableString ss = new SpannableString(HtmlCompat.fromHtml(htmlString,HtmlCompat.FROM_HTML_MODE_COMPACT));
//        Spanned spanned = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT);
//        txtkingLongHist.setText(spanned);

        txtkingLongHist.setText(ss);
        txtkingLongHist.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}
