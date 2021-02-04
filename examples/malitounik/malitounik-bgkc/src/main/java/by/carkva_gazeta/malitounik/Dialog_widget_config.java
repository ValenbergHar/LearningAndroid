package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * Created by oleg on 20.7.17
 */

public class Dialog_widget_config extends DialogFragment {

    private int widgetID;
    private Dialog_widget_config_Listener mListener;

    public static Dialog_widget_config getInstance(int widgetID) {
        Dialog_widget_config dialog_widget_config = new Dialog_widget_config();
        Bundle bundle = new Bundle();
        bundle.putInt("widgetID", widgetID);
        dialog_widget_config.setArguments(bundle);
        return dialog_widget_config;
    }

    interface Dialog_widget_config_Listener {
        void onDialogWidgetConfigPositiveClick();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        widgetID = Objects.requireNonNull(getArguments()).getInt("widgetID");
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (Dialog_widget_config_Listener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Dialog_widget_config_Listener");
            }
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Objects.requireNonNull(getActivity()).finish();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = k.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View linearLayout = View.inflate(getActivity(), R.layout.dialog_widget_config, null);

        SwitchCompat checkBox20 = linearLayout.findViewById(R.id.checkBox20);
        checkBox20.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        if (k.getBoolean("dzen_noch_widget_day" + widgetID, false))
            checkBox20.setChecked(true);
        else
            checkBox20.setChecked(false);

        checkBox20.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putBoolean("dzen_noch_widget_day" + widgetID, true);
            } else {
                prefEditor.putBoolean("dzen_noch_widget_day" + widgetID, false);
            }
        });

        builder.setPositiveButton(getResources().getText(R.string.ok), (dialog, which) -> {
            prefEditor.apply();
            mListener.onDialogWidgetConfigPositiveClick();
            dialog.cancel();
        });
        builder.setView(linearLayout);
        AlertDialog alert = builder.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }
}
