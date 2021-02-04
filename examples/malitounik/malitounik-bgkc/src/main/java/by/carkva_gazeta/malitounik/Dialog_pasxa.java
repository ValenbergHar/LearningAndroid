package by.carkva_gazeta.malitounik;

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

public class Dialog_pasxa extends DialogFragment {

    private int value = -1;
    private EditText_Roboto_Condensed input;
    private Dialog_pasxa_Listener mListener;
    private int realpadding;
    private boolean dzenNoch;

    public interface Dialog_pasxa_Listener {
        void setPasxa(int year);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            try {
                mListener = (Dialog_pasxa_Listener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement Dialog_pasxa_Listener");
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("value", Objects.requireNonNull(input.getText()).toString());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout linear = new LinearLayout(getActivity());
        linear.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(getResources().getString(R.string.DATA_SEARCH2));
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        linear.addView(textViewZaglavie);
        input = new EditText_Roboto_Condensed(getActivity());
        if (dzenNoch) {
            input.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            input.setBackgroundResource(R.color.colorbackground_material_dark_ligte);
        } else {
            input.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            input.setBackgroundResource(R.color.colorIcons);
        }
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        linear.addView(input);
        if (savedInstanceState != null) {
            String sValue = savedInstanceState.getString("value", "");
            input.setText(sValue);
        }
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.requestFocus();
        // Показываем клавиатуру
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        builder.setView(linear);

        builder.setPositiveButton(getString(R.string.ok), (dialog, whichButton) -> {
            // Скрываем клавиатуру
            InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm1).hideSoftInputFromWindow(input.getWindowToken(), 0);
            if (Objects.requireNonNull(input.getText()).toString().equals("")) {
                error();
            } else {
                value = Integer.parseInt(input.getText().toString());
                if (value >= 1583 && value <= 2089) {
                    mListener.setPasxa(value);
                } else {
                    error();
                }
            }
        });

        builder.setNegativeButton(getString(R.string.CANCEL), (dialog, which) -> {
            InputMethodManager imm12 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm12).hideSoftInputFromWindow(input.getWindowToken(), 0);
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

    private void error() {
        LinearLayout layout = new LinearLayout(getActivity());
        if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
        else layout.setBackgroundResource(R.color.colorPrimary);
        TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(getActivity());
        toast.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorIcons));
        toast.setPadding(realpadding, realpadding, realpadding, realpadding);
        toast.setText(getString(R.string.error));
        toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        layout.addView(toast);
        Toast mes = new Toast(getActivity());
        mes.setDuration(Toast.LENGTH_SHORT);
        mes.setView(layout);
        mes.show();
    }
}
