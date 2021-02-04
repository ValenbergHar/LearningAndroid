package by.carkva_gazeta.biblijateka;

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
 * Created by oleg on 18.7.17
 */

public class Dialog_biblioteka_WI_FI extends DialogFragment {

    private String listPosition;
    private Dialog_biblioteka_WI_FI_Listener mListener;
    
    static Dialog_biblioteka_WI_FI getInstance(String listPosition) {
        Dialog_biblioteka_WI_FI Instance = new Dialog_biblioteka_WI_FI();
        Bundle args = new Bundle();
        args.putString("listPosition", listPosition);
        Instance.setArguments(args);
        return Instance;
    }

    interface Dialog_biblioteka_WI_FI_Listener {
        void onDialogPositiveClick(String listPosition);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPosition = Objects.requireNonNull(getArguments()).getString("listPosition");
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (Dialog_biblioteka_WI_FI_Listener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Dialog_biblioteka_WI_FI_Listener");
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
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
        textViewZaglavie.setText(by.carkva_gazeta.malitounik.R.string.wifi_error);
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        textView.setText(by.carkva_gazeta.malitounik.R.string.download_bibliateka);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch) textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
        linearLayout.addView(textView);
        builder.setView(linearLayout);
        builder.setPositiveButton(getString(by.carkva_gazeta.malitounik.R.string.dazvolic), (dialog, whichButton) -> mListener.onDialogPositiveClick(listPosition));
        builder.setNegativeButton(getResources().getString(by.carkva_gazeta.malitounik.R.string.CANCEL), (dialog, which) -> dialog.cancel());
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
