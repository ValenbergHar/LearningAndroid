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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Created by oleg on 23.8.16
 */
public class Dialog_Nadsan_Pravila extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) getActivity().setTheme(R.style.AppCompatDark);
        else getActivity().setTheme(R.style.AppTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(getResources().getString(R.string.peryiady).toUpperCase());
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        ScrollView scrollView = new ScrollView(getActivity());
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.nadsan_periody);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            inputStream.close();
            textView.setText(MainActivity.fromHtml(stringBuilder.toString()));
        } catch (Throwable ignored) {
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
        if (dzenNoch) textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
        scrollView.addView(textView);
        linearLayout.addView(scrollView);
        builder.setView(linearLayout);
        builder.setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }
}
