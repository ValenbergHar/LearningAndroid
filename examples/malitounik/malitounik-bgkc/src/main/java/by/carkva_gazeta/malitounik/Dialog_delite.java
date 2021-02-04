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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * Created by oleg on 8.3.18
 */

public class Dialog_delite extends DialogFragment {

    private Dialog_delite_Listener mListener;
    private int position;
    private String filename;
    private String title;
    private String massege;
    
    @NonNull
    public static Dialog_delite getInstance(int position, String filename, String title, String massege) {
        Dialog_delite dialog_delite = new Dialog_delite();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("file", filename);
        bundle.putString("title", title);
        bundle.putString("massege", massege);
        dialog_delite.setArguments(bundle);
        return dialog_delite;
    }

    public interface Dialog_delite_Listener {
        void file_delite(int position, String file);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = Objects.requireNonNull(getArguments()).getInt("position");
        filename = getArguments().getString("file");
        title = getArguments().getString("title");
        massege = getArguments().getString("massege");
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (Dialog_delite_Listener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Dialog_delite_Listener");
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
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
        textViewZaglavie.setText(getResources().getString(R.string.remove));
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        textView.setText(getString(R.string.delite_full, title, massege));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch)
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
        linearLayout.addView(textView);
        builder.setPositiveButton(getResources().getText(R.string.ok), (dialog, which) -> mListener.file_delite(position, filename));
        builder.setNegativeButton(getResources().getString(R.string.CANCEL), (dialog, arg1) -> dialog.cancel());
        builder.setView(linearLayout);
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
