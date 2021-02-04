package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * Created by oleg on 18.7.17
 */

public class Dialog_context_menu extends DialogFragment {
    
    private int position;
    private String name;
    private Dialog_context_menu_Listener mListener;

    public static Dialog_context_menu getInstance(int position, String name) {
        Dialog_context_menu Instance = new Dialog_context_menu();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("name", name);
        Instance.setArguments(args);
        return Instance;
    }

    public interface Dialog_context_menu_Listener {
        void onDialogEditClick(int position);

        void onDialogDeliteClick(int position, String name);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = Objects.requireNonNull(getArguments()).getInt("position");
        name = getArguments().getString("name");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            try {
                mListener = (Dialog_context_menu_Listener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement Dialog_context_menu_Listener");
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
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
        textViewZaglavie.setText(name);
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        textView.setText("Рэдагаваць");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch) textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
        linearLayout.addView(textView);
        TextView_Roboto_Condensed textView2 = new TextView_Roboto_Condensed(getActivity());
        textView2.setPadding(realpadding, realpadding, realpadding, realpadding);
        textView2.setText("Выдаліць");
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        if (dzenNoch) textView2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        else textView2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));

        linearLayout.addView(textView2);
        builder.setView(linearLayout);
        AlertDialog dialog = builder.create();
        textView.setOnClickListener((v) -> {
            dialog.cancel();
            mListener.onDialogEditClick(position);
        });
        textView2.setOnClickListener((v) -> {
            dialog.cancel();
            mListener.onDialogDeliteClick(position, name);
        });
        return dialog;
    }
}
