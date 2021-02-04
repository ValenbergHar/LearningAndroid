package by.carkva_gazeta.malitounik;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Created by oleg on 21.7.17
 */

public class Dialog_Alesya_Semukha extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) getActivity().setTheme(R.style.AppCompatDark);
        else getActivity().setTheme(R.style.AppTheme);
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ScrollView scrollView = new ScrollView(getActivity());
        scrollView.setVerticalScrollBarEnabled(false);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(R.string.alesyaSemukha);
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.all_rights_reserved);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException ignored) {
        }
        textView.setText(builder.toString());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch) textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
        scrollView.addView(textView);
        linearLayout.addView(scrollView);
        ad.setView(linearLayout);
        ad.setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> dialog.cancel());
        AlertDialog alert = ad.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }
}
