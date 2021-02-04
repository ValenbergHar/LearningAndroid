package by.carkva_gazeta.malitounik;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class Dialog_tipicon extends DialogFragment {

    public static Dialog_tipicon getInstance(int tipicon) {
        Dialog_tipicon dialog_tipicon = new Dialog_tipicon();
        Bundle bundle = new Bundle();
        bundle.putInt("tipicon", tipicon);
        dialog_tipicon.setArguments(bundle);
        return dialog_tipicon;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int tipicon = Objects.requireNonNull(getArguments()).getInt("tipicon");
        SharedPreferences chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = View.inflate(getActivity(), R.layout.tipicon, null);
        builder.setView(dialogView);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        TableRow tableRow1 = dialogView.findViewById(R.id.t1);
        tableRow1.setVisibility(View.GONE);
        TableRow tableRow2 = dialogView.findViewById(R.id.t2);
        tableRow2.setVisibility(View.GONE);
        TableRow tableRow3 = dialogView.findViewById(R.id.t3);
        tableRow3.setVisibility(View.GONE);
        TableRow tableRow10 = dialogView.findViewById(R.id.t10);
        tableRow10.setVisibility(View.GONE);
        TableRow tableRow11 = dialogView.findViewById(R.id.t11);
        tableRow11.setVisibility(View.GONE);
        View polosa = dialogView.findViewById(R.id.polosa);
        polosa.setVisibility(View.GONE);

        TableRow tableRow7 = dialogView.findViewById(R.id.t7);
        if (tipicon == 1) tableRow7.setVisibility(View.VISIBLE);
        else tableRow7.setVisibility(View.GONE);
        TableRow tableRow5 = dialogView.findViewById(R.id.t5);
        if (tipicon == 2) tableRow5.setVisibility(View.VISIBLE);
        else tableRow5.setVisibility(View.GONE);
        TableRow tableRow6 = dialogView.findViewById(R.id.t6);
        if (tipicon == 3) tableRow6.setVisibility(View.VISIBLE);
        else tableRow6.setVisibility(View.GONE);
        TableRow tableRow8 = dialogView.findViewById(R.id.t8);
        if (tipicon == 4) tableRow8.setVisibility(View.VISIBLE);
        else tableRow8.setVisibility(View.GONE);
        TableRow tableRow9 = dialogView.findViewById(R.id.t9);
        if (tipicon == 5) tableRow9.setVisibility(View.VISIBLE);
        else tableRow9.setVisibility(View.GONE);

        TextView_Roboto_Condensed textView1 = dialogView.findViewById(R.id.textView1);
        textView1.setVisibility(View.GONE);

        if (tipicon == 0) {
            tableRow1.setVisibility(View.VISIBLE);
            tableRow2.setVisibility(View.VISIBLE);
            tableRow3.setVisibility(View.VISIBLE);
            tableRow10.setVisibility(View.VISIBLE);
            tableRow11.setVisibility(View.VISIBLE);
            polosa.setVisibility(View.VISIBLE);
            tableRow5.setVisibility(View.VISIBLE);
            tableRow6.setVisibility(View.VISIBLE);
            tableRow7.setVisibility(View.VISIBLE);
            tableRow8.setVisibility(View.VISIBLE);
            tableRow9.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.VISIBLE);
        }

        TextView_Roboto_Condensed textView2 = dialogView.findViewById(R.id.textView2);
        TextView_Roboto_Condensed textView3 = dialogView.findViewById(R.id.textView3);
        TextView_Roboto_Condensed textView4 = dialogView.findViewById(R.id.textView4);
        TextView_Roboto_Condensed textView5 = dialogView.findViewById(R.id.textView5);
        TextView_Roboto_Condensed textView6 = dialogView.findViewById(R.id.textView6);
        TextView_Roboto_Condensed textView7 = dialogView.findViewById(R.id.textView7);
        textView7.setPadding(0, 0, 0, 0);
        TextView_Roboto_Condensed textView8 = dialogView.findViewById(R.id.textView8);
        textView8.setText(MainActivity.fromHtml("<strong>Двунадзясятыя</strong><br> і вялікія сьвяты"));
        TextView_Roboto_Condensed textView9 = dialogView.findViewById(R.id.textView9);
        TextView_Roboto_Condensed textView10 = dialogView.findViewById(R.id.textView10);
        TextView_Roboto_Condensed textView11 = dialogView.findViewById(R.id.textView11);
        TextView_Roboto_Condensed textView12 = dialogView.findViewById(R.id.textView12);
        TextView_Roboto_Condensed textView13 = dialogView.findViewById(R.id.textView13);
        View line2 = dialogView.findViewById(R.id.line2);
        ImageView image1 = dialogView.findViewById(R.id.image1);
        ImageView image2 = dialogView.findViewById(R.id.image2);
        ImageView image3 = dialogView.findViewById(R.id.image3);
        ImageView image4 = dialogView.findViewById(R.id.image4);
        ImageView image5 = dialogView.findViewById(R.id.image5);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView9.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView10.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView11.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView12.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textView13.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch) {
            ImageView imageView = dialogView.findViewById(R.id.imageView14);
            imageView.setImageResource(R.drawable.znaki_ttk_whate);
            textView1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView4.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView5.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView6.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView7.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView8.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView9.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView10.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView11.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView12.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textView13.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
            line2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
            image1.setImageResource(R.drawable.znaki_krest_v_kruge_black);
            image2.setImageResource(R.drawable.znaki_krest_v_polukruge_black);
            image3.setImageResource(R.drawable.znaki_krest_black);
            image4.setImageResource(R.drawable.znaki_ttk_black_black);
            image5.setImageResource(R.drawable.znaki_red_kub_black);
        }
        builder.setPositiveButton(getActivity().getString(R.string.ok), (dialog, whichButton) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }
}
