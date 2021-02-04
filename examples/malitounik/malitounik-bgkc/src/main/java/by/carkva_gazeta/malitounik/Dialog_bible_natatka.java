package by.carkva_gazeta.malitounik;

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

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by oleg on 29.3.19
 */

public class Dialog_bible_natatka extends DialogFragment {

    private boolean redaktor = false;
    private int position = 0;
    private int semuxa;
    private String novyzavet;
    private int kniga;
    private int glava;
    private int stix;
    private String bibletext;

    public static Dialog_bible_natatka getInstance(int semuxa, String novyzavet, int kniga, int glava, int stix, String bibletext) {
        Dialog_bible_natatka zametka = new Dialog_bible_natatka();
        Bundle bundle = new Bundle();
        bundle.putInt("semuxa", semuxa);
        bundle.putString("novyzavet", novyzavet);
        bundle.putInt("kniga", kniga);
        bundle.putInt("glava", glava);
        bundle.putInt("stix", stix);
        bundle.putString("bibletext", bibletext);
        zametka.setArguments(bundle);
        return zametka;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        semuxa = Objects.requireNonNull(getArguments()).getInt("semuxa");
        novyzavet = getArguments().getString("novyzavet");
        kniga = getArguments().getInt("kniga");
        glava = getArguments().getInt("glava");
        stix = getArguments().getInt("stix");
        bibletext = getArguments().getString("bibletext");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) getActivity().setTheme(R.style.AppCompatDark);
        else getActivity().setTheme(R.style.AppTheme);
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        String editText = "";
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(R.string.natatka_bersha_biblii);
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        if (semuxa == 1) {
            for (int i = 0; i < MaranAta_Global_List.getNatatkiSemuxa().size(); i++) {
                if (MaranAta_Global_List.getNatatkiSemuxa().get(i).get(0).contains(novyzavet) && Integer.parseInt(MaranAta_Global_List.getNatatkiSemuxa().get(i).get(1)) == kniga && Integer.parseInt(MaranAta_Global_List.getNatatkiSemuxa().get(i).get(2)) == glava && Integer.parseInt(MaranAta_Global_List.getNatatkiSemuxa().get(i).get(3)) == stix) {
                    redaktor = true;
                    editText = MaranAta_Global_List.getNatatkiSemuxa().get(i).get(5);
                    position = i;
                    break;
                }
            }
        }
        if (semuxa == 2) {
            for (int i = 0; i < MaranAta_Global_List.getNatatkiSinodal().size(); i++) {
                if (MaranAta_Global_List.getNatatkiSinodal().get(i).get(0).contains(novyzavet) && Integer.parseInt(MaranAta_Global_List.getNatatkiSinodal().get(i).get(1)) == kniga && Integer.parseInt(MaranAta_Global_List.getNatatkiSinodal().get(i).get(2)) == glava && Integer.parseInt(MaranAta_Global_List.getNatatkiSinodal().get(i).get(3)) == stix) {
                    redaktor = true;
                    editText = MaranAta_Global_List.getNatatkiSinodal().get(i).get(5);
                    position = i;
                    break;
                }
            }
        }
        EditText_Roboto_Condensed editTextView = new EditText_Roboto_Condensed(getActivity());
        editTextView.setPadding(realpadding, realpadding, realpadding, realpadding);
        editTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch) {
            editTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            editTextView.setBackgroundResource(R.color.colorbackground_material_dark_ligte);
        } else {
            editTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            editTextView.setBackgroundResource(R.color.colorIcons);
        }
        editTextView.setText(editText);
        editTextView.requestFocus();
        // Показываем клавиатуру
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        linearLayout.addView(editTextView);
        ad.setView(linearLayout);
        ad.setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> {
            if (semuxa == 1) {
                if (redaktor && MaranAta_Global_List.getNatatkiSemuxa().size() > 0) {
                    if (Objects.requireNonNull(editTextView.getText()).toString().equals(""))
                        MaranAta_Global_List.getNatatkiSemuxa().remove(position);
                    else
                        MaranAta_Global_List.getNatatkiSemuxa().get(position).set(5, editTextView.getText().toString());
                } else {
                    if (!Objects.requireNonNull(editTextView.getText()).toString().equals("")) {
                        ArrayList<String> temp = new ArrayList<>();
                        temp.add(novyzavet);
                        temp.add(String.valueOf(kniga));
                        temp.add(String.valueOf(glava));
                        temp.add(String.valueOf(stix));
                        temp.add(bibletext);
                        temp.add(editTextView.getText().toString());
                        MaranAta_Global_List.getNatatkiSemuxa().add(0, temp);
                    }
                }
            }
            if (semuxa == 2) {
                if (redaktor && MaranAta_Global_List.getNatatkiSinodal().size() > 0) {
                    if (Objects.requireNonNull(editTextView.getText()).toString().equals(""))
                        MaranAta_Global_List.getNatatkiSinodal().remove(position);
                    else
                        MaranAta_Global_List.getNatatkiSinodal().get(position).set(5, editTextView.getText().toString());
                } else {
                    if (!Objects.requireNonNull(editTextView.getText()).toString().equals("")) {
                        ArrayList<String> temp = new ArrayList<>();
                        temp.add(novyzavet);
                        temp.add(String.valueOf(kniga));
                        temp.add(String.valueOf(glava));
                        temp.add(String.valueOf(stix));
                        temp.add(bibletext);
                        temp.add(editTextView.getText().toString());
                        MaranAta_Global_List.getNatatkiSinodal().add(0, temp);
                    }
                }
            }
            InputMethodManager imm12 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm12).hideSoftInputFromWindow(editTextView.getWindowToken(), 0);
            dialog.cancel();
        });
        ad.setNeutralButton(getString(R.string.bible_natatka), (dialog, which) -> {
            if (semuxa == 1 && MaranAta_Global_List.getNatatkiSemuxa().size() > 0)
                MaranAta_Global_List.getNatatkiSemuxa().remove(position);
            if (semuxa == 2 && MaranAta_Global_List.getNatatkiSinodal().size() > 0)
                MaranAta_Global_List.getNatatkiSinodal().remove(position);
            //if (semuxa == 3 && MaranAta_Global_List.getNatatkiPsalterNadsana().size() > 0)
            //    MaranAta_Global_List.getNatatkiPsalterNadsana().remove(position);
            InputMethodManager imm12 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm12).hideSoftInputFromWindow(editTextView.getWindowToken(), 0);
            dialog.cancel();
        });
        ad.setNegativeButton(R.string.CANCEL, (dialog, which) -> {
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
