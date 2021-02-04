package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Dialog_prazdnik extends DialogFragment {

    private int setid = 10;
    private ArrayList<Integer> arrayList;
    private Dialog_prazdnik_Listener mListener;

    public interface Dialog_prazdnik_Listener {
        void setPrazdnik(int year);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            try {
                mListener = (Dialog_prazdnik_Listener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement Dialog_pasxa_Listener");
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList("arrayList", arrayList);
        outState.putInt("setid", setid);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout linear = new LinearLayout(getActivity());
        linear.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText(getResources().getString(R.string.CARKVA_SVIATY));
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        linear.addView(textViewZaglavie);
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        if (savedInstanceState != null) {
            setid = savedInstanceState.getInt("setid");
            arrayList = savedInstanceState.getIntegerArrayList("arrayList");
        } else {
            arrayList = new ArrayList<>();
            for (int i = c.get(Calendar.YEAR) + 10; i >= SettingsActivity.GET_CALIANDAR_YEAR_MIN; i--) {
                arrayList.add(i);
            }
        }
        ListAdapter arrayAdapter = new ListAdapter(getActivity(), arrayList);
        Spinner spinner = new Spinner(getActivity());
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(setid);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) == c.get(Calendar.YEAR)) {
                setid = i;
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setid = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        linear.addView(spinner);
        builder.setView(linear);
        builder.setNegativeButton(getString(R.string.CANCEL), (dialog, whichButton) -> dialog.cancel());
        builder.setPositiveButton(getString(R.string.ok), (dialog, whichButton) -> mListener.setPrazdnik(arrayList.get(setid)));
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
