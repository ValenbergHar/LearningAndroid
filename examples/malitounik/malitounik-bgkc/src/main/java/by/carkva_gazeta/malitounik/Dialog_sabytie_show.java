package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by oleg on 29.9.17
 */

public class Dialog_sabytie_show extends DialogFragment {
    
    private String title;
    private String data;
    private String time;
    private String dataK;
    private String timeK;
    private String res;

    public static Dialog_sabytie_show getInstance(String title, String data, String time, String dataK, String timeK, String res) {
        Dialog_sabytie_show dialog_show_sabytie = new Dialog_sabytie_show();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("data", data);
        bundle.putString("time", time);
        bundle.putString("dataK", dataK);
        bundle.putString("timeK", timeK);
        bundle.putString("res", res);
        dialog_show_sabytie.setArguments(bundle);
        return dialog_show_sabytie;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = Objects.requireNonNull(getArguments()).getString("title");
        data = getArguments().getString("data");
        time = getArguments().getString("time");
        dataK = getArguments().getString("dataK");
        timeK = getArguments().getString("timeK");
        res = getArguments().getString("res");
    }

    @NonNull
    @SuppressLint("SetTextI18n")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        final boolean dzenNoch = k.getBoolean("dzen_noch", false);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        TextView_Roboto_Condensed textViewT = new TextView_Roboto_Condensed(getActivity());
        textViewT.setText(title.toUpperCase());
        textViewT.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        textViewT.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewT.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewT.setTypeface(null, Typeface.BOLD);
        if (dzenNoch)
            textViewT.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        else textViewT.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        linearLayout.addView(textViewT);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        if (data.equals(dataK) && time.equals(timeK)) {
            textView.setText("Калі: " + data + " " + time + "\n" + res);
        } else {
            textView.setText("Пачатак: " + data + " " + time + "\nКанец: " + dataK + " " + timeK + "\n" + res);
        }
        if (dzenNoch) {
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        } else {
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        linearLayout.addView(textView);
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setView(linearLayout);
        ad.setPositiveButton(getResources().getString(R.string.ok), (dialog, arg1) -> dialog.cancel());
        String[] ts = data.split("\\.");
        GregorianCalendar g = new GregorianCalendar(Integer.parseInt(ts[2]), Integer.parseInt(ts[1]) - 1, Integer.parseInt(ts[0]));
        if (g.get(Calendar.YEAR) <= SettingsActivity.GET_CALIANDAR_YEAR_MAX) {
            ad.setNeutralButton(getString(R.string.sabytie_kaliandar), (dialog, arg1) -> {
                Intent intent = new Intent();
                intent.putExtra("data", g.get(Calendar.DAY_OF_YEAR) - 1);
                intent.putExtra("year", g.get(Calendar.YEAR));
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
                dialog.cancel();
            });
        }
        AlertDialog alert = ad.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            Button btnNeutral = alert.getButton(Dialog.BUTTON_NEUTRAL);
            btnNeutral .setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }
}
