package by.carkva_gazeta.biblijateka;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

import by.carkva_gazeta.malitounik.InteractiveScrollView;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 23.3.18
 */

public class Dialog_bibliateka extends DialogFragment {

    private String listPosition;
    private String title;
    private String listStr;
    private String size;
    private Dialog_bibliateka_Listener mListener;

    static Dialog_bibliateka getInstance(String listPosition, String listStr, String title, String size) {
        Dialog_bibliateka Instance = new Dialog_bibliateka();
        Bundle args = new Bundle();
        args.putString("listPosition", listPosition);
        args.putString("listStr", listStr);
        args.putString("title", title);
        args.putString("size", size);
        Instance.setArguments(args);
        return Instance;
    }

    interface Dialog_bibliateka_Listener {
        void onDialogbibliatekaPositiveClick(String listPosition, String title);
        //void onDialogbibliatekaNeutralClick(String listPosition, String title);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPosition = Objects.requireNonNull(getArguments()).getString("listPosition");
        listStr = getArguments().getString("listStr");
        title = getArguments().getString("title");
        size = getArguments().getString("size");
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (Dialog_bibliateka_Listener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Dialog_bibliateka_Listener");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (dzenNoch) getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        else getActivity().setTheme(by.carkva_gazeta.malitounik.R.style.AppTheme);
        LinearLayout linearLayout2 = new LinearLayout(getActivity());
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        builder.setView(linearLayout2);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.addView(linearLayout);
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary));
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        File file = new File(getActivity().getFilesDir() + "/Biblijateka/" + listPosition);
        if (file.exists()) {
            textViewZaglavie.setText("АПІСАНЬНЕ");
        } else {
            textViewZaglavie.setText("СПАМПАВАЦЬ ФАЙЛ?");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                new Thread(() -> {
                    try {
                        String format;
                        StorageManager storageManager = (StorageManager) getActivity().getSystemService(Context.STORAGE_SERVICE);
                        long bates = Objects.requireNonNull(storageManager).getAllocatableBytes(storageManager.getUuidForPath(getActivity().getFilesDir()));
                        float bat = (float) bates / 1024;
                        if (bat < 10000f)
                            format = ": ДАСТУПНА " + formatFigureTwoPlaces(new BigDecimal(bat).setScale(2, RoundingMode.HALF_EVEN).floatValue()) + " КБ";
                        else if (bates < 1000L)
                            format = ": ДАСТУПНА " + bates + " БАЙТ";
                        else
                            format = "";
                        getActivity().runOnUiThread(() -> textViewZaglavie.setText(textViewZaglavie.getText().toString() + format));
                    } catch (IOException ignored) {
                    }
                }).start();
            }
        }
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linearLayout.addView(textViewZaglavie);
        InteractiveScrollView isv = new InteractiveScrollView(getActivity());
        isv.setVerticalScrollBarEnabled(false);
        linearLayout.addView(isv);
        TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
        textView.setText(MainActivity.fromHtml(listStr));
        textView.setPadding(realpadding, realpadding, realpadding, realpadding);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, k.getInt("font_malitounik", 18));
        if (dzenNoch) textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        else textView.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
        isv.addView(textView);
        int dirCount = Integer.parseInt(size);
        String izm;
        if (dirCount / 1024 > 1000) {
            izm = formatFigureTwoPlaces(BigDecimal.valueOf((float) dirCount / 1024 / 1024).setScale(2, RoundingMode.HALF_UP).floatValue()) + " Мб";
        } else {
            izm = formatFigureTwoPlaces(BigDecimal.valueOf((float) dirCount / 1024).setScale(2, RoundingMode.HALF_UP).floatValue()) + " Кб";
        }
        if (file.exists()) {
            builder.setPositiveButton(getResources().getString(by.carkva_gazeta.malitounik.R.string.ok), (dialog, which) -> dialog.cancel());
        } else {
            if (MainActivity.isIntNetworkAvailable(getActivity()) != 0) {
                builder.setPositiveButton("Спампаваць " + izm, (dialog, which) -> {
                    mListener.onDialogbibliatekaPositiveClick(listPosition, title);
                    dialog.cancel();
                });
                builder.setNegativeButton(by.carkva_gazeta.malitounik.R.string.CANCEL, ((dialog, which) -> dialog.cancel()));
            } else {
                builder.setPositiveButton("НЯМА ІНТЭРНЭТ-ЗЛУЧЭНЬНЯ", (dialog, which) -> dialog.cancel());
            }
        }
        AlertDialog alert = builder.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            Button btnNegative = alert.getButton(Dialog.BUTTON_NEGATIVE);
            btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        return alert;
    }

    @NonNull
    private String formatFigureTwoPlaces(float value) {
        DecimalFormat myFormatter = new DecimalFormat("##0.00");
        return myFormatter.format(value);
    }
}
