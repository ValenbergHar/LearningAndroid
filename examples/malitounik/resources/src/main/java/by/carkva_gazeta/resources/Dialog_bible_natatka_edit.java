package by.carkva_gazeta.resources;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import by.carkva_gazeta.malitounik.EditText_Roboto_Condensed;
import by.carkva_gazeta.malitounik.MaranAta_Global_List;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 29.3.19
 */

public class Dialog_bible_natatka_edit extends DialogFragment {

    private bible_natatka_edit_listiner edit;
    private int semuxa;
    private int position;
    
    public static Dialog_bible_natatka_edit getInstance(int semuxa, int position) {
        Dialog_bible_natatka_edit zametka = new Dialog_bible_natatka_edit();
        Bundle bundle = new Bundle();
        bundle.putInt("semuxa", semuxa);
        bundle.putInt("position", position);
        zametka.setArguments(bundle);
        return zametka;
    }

    interface bible_natatka_edit_listiner {
        void setEdit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            try {
                edit = (bible_natatka_edit_listiner) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement bible_natatka_edit_listiner");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        semuxa = Objects.requireNonNull(getArguments()).getInt("semuxa");
        position = getArguments().getInt("position");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        else getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppTheme);
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        String editText = "";
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.natatka_bersha_biblii);
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        if (semuxa == 1) {
            editText = MaranAta_Global_List.getNatatkiSemuxa().get(position).get(5);
        }
        if (semuxa == 2) {
            editText = MaranAta_Global_List.getNatatkiSinodal().get(position).get(5);
        }
        /*if (semuxa == 3) {
            editText = MaranAta_Global_List.getNatatkiPsalterNadsana().get(position).get(3);
        }*/
        EditText_Roboto_Condensed editTextView = new EditText_Roboto_Condensed(getActivity());
        editTextView.setPadding(realpadding, realpadding, realpadding, realpadding);
        editTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch) {
            editTextView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
            editTextView.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark_ligte);
        } else {
            editTextView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
            editTextView.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorIcons);
        }
        editTextView.setText(editText);
        editTextView.requestFocus();
        // Показываем клавиатуру
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        linearLayout.addView(editTextView);
        ad.setView(linearLayout);
        ad.setPositiveButton(getResources().getString(by.carkva_gazeta.malitounik.R.string.ok), (dialog, which) -> {
            if (semuxa == 1) {
                if (Objects.requireNonNull(editTextView.getText()).toString().equals(""))
                    MaranAta_Global_List.getNatatkiSemuxa().remove(position);
                else
                    MaranAta_Global_List.getNatatkiSemuxa().get(position).set(5, editTextView.getText().toString());
            }
            if (semuxa == 2) {
                if (Objects.requireNonNull(editTextView.getText()).toString().equals(""))
                    MaranAta_Global_List.getNatatkiSinodal().remove(position);
                else
                    MaranAta_Global_List.getNatatkiSinodal().get(position).set(5, editTextView.getText().toString());
            }
            /*if (semuxa == 3) {
                if (editTextView.getText().toString().equals(""))
                    MaranAta_Global_List.getNatatkiPsalterNadsana().remove(position);
                else
                    MaranAta_Global_List.getNatatkiPsalterNadsana().get(position).set(3, editTextView.getText().toString());
            }*/
            InputMethodManager imm12 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm12).hideSoftInputFromWindow(editTextView.getWindowToken(), 0);
            dialog.cancel();
            edit.setEdit();
        });
        ad.setNeutralButton(getString(by.carkva_gazeta.malitounik.R.string.bible_natatka), (dialog, which) -> {
            if (semuxa == 1 && MaranAta_Global_List.getNatatkiSemuxa().size() > 0)
                MaranAta_Global_List.getNatatkiSemuxa().remove(position);
            if (semuxa == 2 && MaranAta_Global_List.getNatatkiSinodal().size() > 0)
                MaranAta_Global_List.getNatatkiSinodal().remove(position);
            //if (semuxa == 3 && MaranAta_Global_List.getNatatkiPsalterNadsana().size() > 0)
            //    MaranAta_Global_List.getNatatkiPsalterNadsana().remove(position);
            InputMethodManager imm12 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm12).hideSoftInputFromWindow(editTextView.getWindowToken(), 0);
            dialog.cancel();
            edit.setEdit();
        });
        ad.setNegativeButton(by.carkva_gazeta.malitounik.R.string.CANCEL, (dialog, which) -> {
            InputMethodManager imm12 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm12).hideSoftInputFromWindow(editTextView.getWindowToken(), 0);
            dialog.cancel();
        });
        AlertDialog alert = ad.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            Button btnNegative = alert.getButton(Dialog.BUTTON_NEGATIVE);
            btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            Button btnNeutral = alert.getButton(Dialog.BUTTON_NEUTRAL);
            btnNeutral.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }
}
