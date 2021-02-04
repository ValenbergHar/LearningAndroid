package by.carkva_gazeta.biblijateka;

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

public class Dialog_set_page_biblioteka extends DialogFragment {

    private int page;
    private int pageCount;
    private Dialog_set_page_biblioteka_Listener mListener;

    static Dialog_set_page_biblioteka getInstance(int page, int pageCount) {
        Dialog_set_page_biblioteka Instance = new Dialog_set_page_biblioteka();
        Bundle args = new Bundle();
        args.putInt("page", page + 1);
        args.putInt("pageCount", pageCount);
        Instance.setArguments(args);
        return Instance;
    }

    interface Dialog_set_page_biblioteka_Listener {
        void onDialogSetPage(int page);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = Objects.requireNonNull(getArguments()).getInt("page");
        pageCount = getArguments().getInt("pageCount");
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (Dialog_set_page_biblioteka_Listener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Dialog_set_page_biblioteka_Listener");
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        SharedPreferences chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout linear = new LinearLayout(getActivity());
        linear.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(String.format("Увядзіце нумар старонкі. Усяго: %s", pageCount));
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linear.addView(textViewZaglavie);
        final EditText_Roboto_Condensed input = new EditText_Roboto_Condensed(getActivity());
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        linear.addView(input);
        input.setText(String.valueOf(page));
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.requestFocus();
        // Показываем клавиатуру
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        builder.setView(linear);

        builder.setPositiveButton(getString(by.carkva_gazeta.malitounik.R.string.ok), (dialog, whichButton) -> {
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
                    value = Integer.parseInt(input.getText().toString());
                } catch (NumberFormatException e) {
                    value = 1;
                }
                if (value >= 1 && value <= pageCount) {
                    mListener.onDialogSetPage(value);
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

        builder.setNegativeButton(getString(by.carkva_gazeta.malitounik.R.string.CANCEL), (dialog, which) -> {
            InputMethodManager imm12 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm12).hideSoftInputFromWindow(input.getWindowToken(), 0);
        });
        builder.setNeutralButton("На пачатак", (dialog, which) -> {
            mListener.onDialogSetPage(1);
            InputMethodManager imm12 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm12).hideSoftInputFromWindow(input.getWindowToken(), 0);
        });
        AlertDialog alert = builder.create();
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
