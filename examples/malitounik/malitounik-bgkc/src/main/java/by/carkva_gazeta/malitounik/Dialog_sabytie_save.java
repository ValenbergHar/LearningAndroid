package by.carkva_gazeta.malitounik;

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
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * Created by oleg on 25.10.17
 */

public class Dialog_sabytie_save extends DialogFragment {

    private Dialog_sabytie_save_Listener mListener;

    interface Dialog_sabytie_save_Listener {
        void onDialogPositiveClick();

        void onDialogNegativeClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            try {
                mListener = (Dialog_sabytie_save_Listener) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(Objects.requireNonNull(getActivity()).toString() + " must implement Dialog_sabytie_save_Listener");
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText("ПАДЗЕЯ ЗЬМЕНЕНА");
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        textView.setText("Захаваць зьмены?");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch) textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
        linearLayout.addView(textView);
        ad.setView(linearLayout);
        ad.setNegativeButton("Не", (dialogInterface, i) -> mListener.onDialogNegativeClick());
        ad.setPositiveButton("Так", (dialogInterface, i) -> mListener.onDialogPositiveClick());
        ad.setNeutralButton("Адмена", (dialogInterface, i) -> dialogInterface.cancel());
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
