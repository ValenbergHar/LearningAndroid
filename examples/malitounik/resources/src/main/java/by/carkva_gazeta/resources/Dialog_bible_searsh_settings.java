package by.carkva_gazeta.resources;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 22.7.17
 */

public class Dialog_bible_searsh_settings extends DialogFragment {

    private SharedPreferences.Editor prefEditors;
    private diallog_bible_searsh_listiner mListener;

    public static Dialog_bible_searsh_settings getInstance(String edit) {
        Dialog_bible_searsh_settings Instance = new Dialog_bible_searsh_settings();
        Bundle args = new Bundle();
        args.putString("edit", edit);
        Instance.setArguments(args);
        return Instance;
    }

    interface diallog_bible_searsh_listiner {
        void OnSetSettings(String edit);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (diallog_bible_searsh_listiner) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement diallog_bible_searsh_listiner");
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
        prefEditors = chin.edit();
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
        textViewZaglavie.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.settings_poshuk));
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linear.addView(textViewZaglavie);
        ScrollView scrollView = new ScrollView(getActivity());
        linear.addView(scrollView);
        scrollView.setPadding(10, 10, 10, 0);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
        CheckBox checkBox = new CheckBox(getActivity());
        checkBox.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        if (chin.getInt("pegistr", 0) == 1) checkBox.setChecked(true);
        checkBox.setText("Улічваць рэгістр");
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditors.putInt("pegistr", 1);
            } else {
                prefEditors.putInt("pegistr", 0);
            }
            prefEditors.apply();
        });
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        CheckBox checkBox1 = new CheckBox(getActivity());
        checkBox1.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        if (chin.getInt("slovocalkam", 0) == 1) checkBox1.setChecked(true);
        checkBox1.setText("Дакладнае супадзеньне");
        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditors.putInt("slovocalkam", 1);
            } else {
                prefEditors.putInt("slovocalkam", 0);
            }
            prefEditors.apply();
        });
        checkBox1.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        /*RadioGroup radioGroup = new RadioGroup(getActivity());
        final RadioButton_Roboto_Condensed radioButton1 = new RadioButton_Roboto_Condensed(getActivity());
        final RadioButton_Roboto_Condensed radioButton2 = new RadioButton_Roboto_Condensed(getActivity());
        radioGroup.addView(radioButton1);
        radioGroup.addView(radioButton2);
        if (dzenNoch) {
            checkBox.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
            checkBox1.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
            radioButton1.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
            radioButton2.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        }
        radioButton1.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        radioButton2.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        radioButton1.setText("Усе словы");
        radioButton2.setText("Любое з слоў");
        if (chin.getInt("kohnoeslovo", 0) == 1) {
            radioButton1.setChecked(false);
            radioButton2.setChecked(true);
        } else {
            radioButton1.setChecked(true);
            radioButton2.setChecked(false);
        }
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == radioButton1.getId()) {
                prefEditors.putInt("kohnoeslovo", 0);
            }
            if (checkedId == radioButton2.getId()) {
                prefEditors.putInt("kohnoeslovo", 1);
            }
            prefEditors.apply();
        });*/
        String[] data = {"УСЯ БІБЛІЯ", "НОВЫ ЗАПАВЕТ", "СТАРЫ ЗАПАВЕТ"};
        Spinner spinner = new Spinner(getActivity());
        Dialog_Bible_Adapter arrayAdapter = new Dialog_Bible_Adapter(getActivity(), data);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(chin.getInt("biblia_seash", 0));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefEditors.putInt("biblia_seash", position);
                prefEditors.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        linearLayout.addView(checkBox);
        //linearLayout.addView(radioGroup);
        linearLayout.addView(checkBox1);
        linearLayout.addView(spinner);
        builder.setView(linear);
        builder.setPositiveButton(getString(by.carkva_gazeta.malitounik.R.string.ok), (dialog, whichButton) -> {
            dialog.cancel();
            mListener.OnSetSettings(Objects.requireNonNull(getArguments()).getString("edit"));
        });
        AlertDialog alert = builder.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }

    static class Dialog_Bible_Adapter extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] name;

        Dialog_Bible_Adapter(@NonNull Activity context, @NonNull String[] objects) {
            super(context, by.carkva_gazeta.malitounik.R.layout.simple_list_item_1, objects);
            this.context = context;
            name = objects;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            SharedPreferences k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            View v = super.getDropDownView(position, convertView, parent);
            TextView_Roboto_Condensed textView = (TextView_Roboto_Condensed) v;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, k.getInt("font_malitounik", 18));
            return v;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            SharedPreferences k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            if (convertView == null) {
                convertView = context.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_4, parent, false);
                viewHolder = new ViewHolder();
                convertView.setTag(viewHolder);
                viewHolder.text = convertView.findViewById(by.carkva_gazeta.malitounik.R.id.text1);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, k.getInt("font_malitounik", 18));
            viewHolder.text.setGravity(Gravity.START);
            viewHolder.text.setTypeface(null, Typeface.NORMAL);
            viewHolder.text.setText(String.valueOf(name[position]));
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
