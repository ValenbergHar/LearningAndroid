package by.carkva_gazeta.resources;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import by.carkva_gazeta.malitounik.EditText_Roboto_Condensed;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 21.7.17
 */

public class Dialog_bible_razdel extends DialogFragment {

    private int full_glav;
    private EditText_Roboto_Condensed input;
    
    public static Dialog_bible_razdel getInstance(int full_glav) {
        Dialog_bible_razdel Instance = new Dialog_bible_razdel();
        Bundle args = new Bundle();
        args.putInt("full_glav", full_glav);
        Instance.setArguments(args);
        return Instance;
    }

    interface Dialog_bible_razdel_Listener {
        void onComplete(int glava);
    }

    private Dialog_bible_razdel_Listener mListener;

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (Dialog_bible_razdel_Listener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Dialog_bible_razdel_Listener");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        full_glav = Objects.requireNonNull(getArguments()).getInt("full_glav");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("glava", Objects.requireNonNull(input.getText()).toString());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        else getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout linearLayout2 = new LinearLayout(getActivity());
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        builder.setView(linearLayout2);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.addView(linearLayout);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        else
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.DATA_SEARCH, full_glav));
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        input = new EditText_Roboto_Condensed(getActivity());
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (savedInstanceState != null) {
            input.setText(savedInstanceState.getString("glava"));
        } else {
            input.setText("");
        }
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (dzenNoch) {
            input.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
            input.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark_ligte);
        } else {
            input.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
            input.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorIcons);
        }
        input.setPadding(realpadding, realpadding, realpadding, realpadding);//10, 0, 0, 0
        input.requestFocus();
        // Показываем клавиатуру
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        linearLayout.addView(input);
        builder.setNegativeButton(getResources().getString(by.carkva_gazeta.malitounik.R.string.CANCEL), (dialog, which) -> {
            InputMethodManager imm12 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm12).hideSoftInputFromWindow(input.getWindowToken(), 0);
            dialog.cancel();
        });
        builder.setPositiveButton(getResources().getString(by.carkva_gazeta.malitounik.R.string.ok), (dialog, which) -> {
            // Скрываем клавиатуру
            InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm1).hideSoftInputFromWindow(input.getWindowToken(), 0);
            if (Objects.requireNonNull(input.getText()).toString().equals("")) {
                LinearLayout layout = new LinearLayout(getActivity());
                if (dzenNoch) layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary_black);
                else layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
                TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(getActivity());
                toast.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
                toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                toast.setText(getString(by.carkva_gazeta.malitounik.R.string.error));
                toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                layout.addView(toast);
                Toast mes = new Toast(getActivity());
                mes.setDuration(Toast.LENGTH_SHORT);
                mes.setView(layout);
                mes.show();
            } else {
                int value;
                try {
                    value = Integer.parseInt(input.getText().toString()) - 1;
                } catch (NumberFormatException e) {
                    value = -1;
                }
                if (value >= 0 && value < full_glav) {
                    mListener.onComplete(value);
                } else {
                    LinearLayout layout = new LinearLayout(getActivity());
                    if (dzenNoch) layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary_black);
                    else layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
                    TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(getActivity());
                    toast.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
                    toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                    toast.setText(getString(by.carkva_gazeta.malitounik.R.string.error));
                    toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                    layout.addView(toast);
                    Toast mes = new Toast(getActivity());
                    mes.setDuration(Toast.LENGTH_SHORT);
                    mes.setView(layout);
                    mes.show();
                }
            }
        });
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
