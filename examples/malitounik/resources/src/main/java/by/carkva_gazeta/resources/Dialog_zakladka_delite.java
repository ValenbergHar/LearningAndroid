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

public class Dialog_zakladka_delite extends DialogFragment {

    private ZakladkaDeliteListiner delite;
    private int position;
    private String name;
    private int semuxa;
    private boolean zakladka;
    
    @NonNull
    public static Dialog_zakladka_delite getInstance(int position, String name, int semuxa, boolean zakladka) {
        Dialog_zakladka_delite dialog_delite = new Dialog_zakladka_delite();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("name", name);
        bundle.putInt("semuxa", semuxa);
        bundle.putBoolean("zakladka", zakladka);
        dialog_delite.setArguments(bundle);
        return dialog_delite;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = Objects.requireNonNull(getArguments()).getInt("position");
        name = getArguments().getString("name");
        semuxa = getArguments().getInt("semuxa");
        zakladka = getArguments().getBoolean("zakladka");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            try {
                delite = (ZakladkaDeliteListiner) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement ZakladkaDeliteListiner");
            }
        }
    }

    interface ZakladkaDeliteListiner {
        void zakladkadiliteItem(int position, int semuxa);

        void natatkidiliteItem(int position, int semuxa);
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
        textViewZaglavie.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.remove));
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        if (zakladka)
            textView.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.delite_natatki_i_zakladki, getString(by.carkva_gazeta.malitounik.R.string.zakladki_bible2), name));
        else
            textView.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.delite_natatki_i_zakladki, getString(by.carkva_gazeta.malitounik.R.string.natatki_biblii2), name));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch)
            textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
        linearLayout.addView(textView);
        if (zakladka) {
            builder.setPositiveButton(getResources().getText(by.carkva_gazeta.malitounik.R.string.ok), (dialog, which) -> delite.zakladkadiliteItem(position, semuxa));
        } else {
            builder.setPositiveButton(getResources().getText(by.carkva_gazeta.malitounik.R.string.ok), (dialog, which) -> delite.natatkidiliteItem(position, semuxa));
        }
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
