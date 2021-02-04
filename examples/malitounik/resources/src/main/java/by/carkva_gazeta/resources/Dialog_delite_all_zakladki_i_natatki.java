package by.carkva_gazeta.resources;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

import java.util.Objects;

import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 4.4.19
 */

public class Dialog_delite_all_zakladki_i_natatki extends DialogFragment {

    private Dialog_delite_all_zakladki_i_natatki_Listener mListener;
    private String zakladka_albo_natatka;
    private int semuxa;
    
    @NonNull
    public static Dialog_delite_all_zakladki_i_natatki getInstance(String zakladka_albo_natatka, int semuxa) {
        Dialog_delite_all_zakladki_i_natatki dialog_delite = new Dialog_delite_all_zakladki_i_natatki();
        Bundle bundle = new Bundle();
        bundle.putString("zakladka_albo_natatka", zakladka_albo_natatka);
        bundle.putInt("semuxa", semuxa);
        dialog_delite.setArguments(bundle);
        return dialog_delite;
    }

    interface Dialog_delite_all_zakladki_i_natatki_Listener {
        void file_all_natatki_albo_zakladki(int semuxa);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (Dialog_delite_all_zakladki_i_natatki_Listener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Dialog_delite_all_zakladki_i_natatki_Listener");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zakladka_albo_natatka = Objects.requireNonNull(getArguments()).getString("zakladka_albo_natatka");
        semuxa = getArguments().getInt("semuxa");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        else getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.Trash).toUpperCase());
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        textView.setText(getString(by.carkva_gazeta.malitounik.R.string.delite_all_natatki_i_zakladki, zakladka_albo_natatka));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch)
            textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
        linearLayout.addView(textView);
        builder.setPositiveButton(getResources().getText(by.carkva_gazeta.malitounik.R.string.ok), (dialog, which) -> mListener.file_all_natatki_albo_zakladki(semuxa));
        builder.setNegativeButton(getResources().getString(by.carkva_gazeta.malitounik.R.string.CANCEL), (dialog, arg1) -> dialog.cancel());
        builder.setView(linearLayout);
        AlertDialog alert = builder.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            Button btnNegative = alert.getButton(Dialog.BUTTON_NEGATIVE);
            btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }
}
