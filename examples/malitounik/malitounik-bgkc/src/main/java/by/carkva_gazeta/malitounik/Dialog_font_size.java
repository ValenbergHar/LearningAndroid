package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * Created by oleg on 20.7.17
 */

public class Dialog_font_size extends DialogFragment {

    private SharedPreferences k;
    private AppCompatSeekBar input;
    private Dialog_font_size_Listener mListener;

    public interface Dialog_font_size_Listener {
        void onDialogFontSizePositiveClick();
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (Dialog_font_size_Listener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Dialog_font_size_Listener");
            }
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        MainActivity.dialogVisable = false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seekbar", input.getProgress());
    }

    private int setProgress(int fontBiblia) {
        int progress = 1;
        switch (fontBiblia) {
            case 14:
                progress = 0;
                break;
            case 18:
                progress = 1;
                break;
            case 22:
                progress = 2;
                break;
            case 26:
                progress = 3;
                break;
            case 30:
                progress = 4;
                break;
            case 34:
                progress = 5;
                break;
            case 38:
                progress = 6;
                break;
            case 42:
                progress = 7;
                break;
            case 46:
                progress = 8;
                break;
            case 50:
                progress = 9;
                break;
            case 54:
                progress = 10;
                break;
        }
        return progress;
    }

    private int getFont(int progress) {
        int font = SettingsActivity.GET_DEFAULT_FONT_SIZE;
        switch (progress) {
            case 0:
                font = 14;
                break;
            case 1:
                font = 18;
                break;
            case 2:
                font = 22;
                break;
            case 3:
                font = 26;
                break;
            case 4:
                font = 30;
                break;
            case 5:
                font = 34;
                break;
            case 6:
                font = 38;
                break;
            case 7:
                font = 42;
                break;
            case 8:
                font = 46;
                break;
            case 9:
                font = 50;
                break;
            case 10:
                font = 54;
                break;
        }
        return font;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MainActivity.dialogVisable = true;
        k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        int fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        if (dzenNoch) getActivity().setTheme(R.style.AppCompatDark);
        else getActivity().setTheme(R.style.AppTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(getResources().getString(R.string.FONT_SIZE_APP));
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        input = new AppCompatSeekBar(getActivity());
        input.setPadding(realpadding, realpadding, realpadding, realpadding);
        input.setMax(10);
        if (savedInstanceState != null) {
            int seekbar = savedInstanceState.getInt("seekbar");
            input.setProgress(seekbar);
        } else {
            input.setProgress(setProgress(fontBiblia));
        }
        linearLayout.addView(input);
        input.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor prefEditors = k.edit();
                prefEditors.putInt("font_malitounik", getFont(progress));
                prefEditors.apply();
                mListener.onDialogFontSizePositiveClick();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        builder.setPositiveButton(getResources().getText(R.string.ok), (dialog, which) -> {
            int progress = input.getProgress();
            SharedPreferences.Editor prefEditors = k.edit();
            prefEditors.putInt("font_malitounik", getFont(progress));
            prefEditors.apply();
            mListener.onDialogFontSizePositiveClick();
            dialog.cancel();
        });
        builder.setNegativeButton(getResources().getText(R.string.CANCEL), (dialog, which) -> {
            SharedPreferences.Editor prefEditors = k.edit();
            prefEditors.putInt("font_malitounik", fontBiblia);
            prefEditors.apply();
            mListener.onDialogFontSizePositiveClick();
            dialog.cancel();
        });
        builder.setNeutralButton(getResources().getText(R.string.default_font), (dialog, which) -> {
            SharedPreferences.Editor prefEditors = k.edit();
            prefEditors.putInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
            prefEditors.apply();
            mListener.onDialogFontSizePositiveClick();
            dialog.cancel();
        });
        builder.setView(linearLayout);
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
