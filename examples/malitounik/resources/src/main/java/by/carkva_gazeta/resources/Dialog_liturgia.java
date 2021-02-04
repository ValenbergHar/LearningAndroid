package by.carkva_gazeta.resources;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 30.11.17
 */

public class Dialog_liturgia extends DialogFragment {

    private InputStream inputStream;
    private String chast;
    
    public static Dialog_liturgia getInstance(String chast) {
        Dialog_liturgia Instance = new Dialog_liturgia();
        Bundle args = new Bundle();
        args.putString("chast", chast);
        Instance.setArguments(args);
        return Instance;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        MainActivity.dialogVisable = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chast = Objects.requireNonNull(getArguments()).getString("chast");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MainActivity.dialogVisable = true;
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        else getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppTheme);
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        StringBuilder builder = new StringBuilder();
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        try {
            Resources r = getActivity().getResources();
            int bogashlugbovya = Integer.parseInt(chast);
            switch (bogashlugbovya) {
                case 1:
                    inputStream = r.openRawResource(R.raw.bogashlugbovya1_1);
                    textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.ps_102);
                    break;
                case 2:
                    inputStream = r.openRawResource(R.raw.bogashlugbovya1_2);
                    textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.ps_91);
                    break;
                case 3:
                    inputStream = r.openRawResource(R.raw.bogashlugbovya1_3);
                    textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.ps_145);
                    break;
                case 4:
                    inputStream = r.openRawResource(R.raw.bogashlugbovya1_4);
                    textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.ps_92);
                    break;
                case 5:
                    inputStream = r.openRawResource(R.raw.bogashlugbovya1_5);
                    textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.mc_5_3_12);
                    break;
                case 6:
                    inputStream = r.openRawResource(R.raw.bogashlugbovya1_6);
                    textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.malitva_za_pamerlyx);
                    break;
                case 7:
                    inputStream = r.openRawResource(R.raw.bogashlugbovya1_7);
                    textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.malitva_za_paclicanyx);
                    break;
                case 8:
                    textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.CZYTANNE_DIALOG);
                    zmenyiaChastki zch = new zmenyiaChastki(getActivity());
                    builder.append(zch.sviatyiaView(1));
                    break;
                case 9:
                    textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.CZYTANNE_DIALOG);
                    zch = new zmenyiaChastki(getActivity());
                    builder.append(zch.sviatyiaView(0));
                    break;
            }
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (dzenNoch)
                        line = line.replace("#d00505", "#f44336");
                    builder.append(line).append("\n");
                }
                inputStream.close();
            }
        } catch (Throwable ignored) {
        }
        ScrollView scrollView = new ScrollView(getActivity());
        linearLayout.addView(scrollView);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setPadding(realpadding, realpadding, realpadding, realpadding);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, k.getInt("font_malitounik", 18));
        if (dzenNoch) textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
        textView.setText(MainActivity.fromHtml(builder.toString()));
        scrollView.addView(textView);
        ab.setView(linearLayout);
        ab.setPositiveButton(getActivity().getResources().getString(by.carkva_gazeta.malitounik.R.string.ok), (dialog, which) -> dialog.cancel());
        AlertDialog alert = ab.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }
}
