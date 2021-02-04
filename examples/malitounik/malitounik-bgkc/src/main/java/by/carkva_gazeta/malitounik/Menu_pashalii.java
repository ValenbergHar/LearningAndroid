package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by oleg on 31.5.16
 */
public class Menu_pashalii extends Fragment {

    private int yearG;
    private int yearG2;
    private int seache = 0;
    private long mLastClickTime = 0;

    @NonNull
    public static Menu_pashalii newInstance() {
        return new Menu_pashalii();
    }

    public static Menu_pashalii newInstance(int year) {
        Menu_pashalii fragmentFirst = new Menu_pashalii();
        Bundle args = new Bundle();
        args.putInt("Year", year);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Calendar c = Calendar.getInstance();
        if (getArguments() != null) {
            yearG = getArguments().getInt("Year", c.get(Calendar.YEAR));
            seache = yearG;
            yearG2 = yearG + 10;
        } else {
            yearG = c.get(Calendar.YEAR) - 3;
            yearG2 = yearG + 20;
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pashalii, container, false);
        SharedPreferences chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        Calendar c = Calendar.getInstance();
        int yearfull = c.get(Calendar.YEAR);
        TextView_Roboto_Condensed textViewL = rootView.findViewById(R.id.title);
        TextView_Roboto_Condensed textViewG = rootView.findViewById(R.id.gri);
        TextView_Roboto_Condensed textViewU = rootView.findViewById(R.id.ula);
        LinearLayout linearLayout = rootView.findViewById(R.id.pasha);
        textViewL.setText(MainActivity.fromHtml("<u>" + getResources().getString(R.string.pascha_kaliandar_bel) + "</u>"));
        if (dzenNoch) {
            textViewG.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textViewL.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        }
        textViewL.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
        textViewG.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
        textViewU.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
        textViewL.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            Intent intent = new Intent(getActivity(), pasxa.class);
            startActivity(intent);
        });

        int data_p, month_p, data_prav, month_prav;
        String[] MonthName = {"студзеня", "лютага", "сакавіка", "красавіка", "траўня", "чэрвеня",
                "ліпеня", "жніўня", "верасьня", "кастрычніка", "лістапада", "сьнежня"};

        for (int i = yearG; i <= yearG2; i++) {
            int a = i % 19;
            int b = i % 4;
            int cx = i % 7;
            int k = i / 100;
            int p = (13 + 8 * k) / 25;
            int q = (k / 4);
            int m = (15 - p + k - q) % 30;
            int n = (4 + k - q) % 7;
            int d = (19 * a + m) % 30;
            int ex = (2 * b + 4 * cx + 6 * d + n) % 7;
            if (d + ex <= 9) {
                data_p = d + ex + 22;
                month_p = 3;
            } else {
                data_p = d + ex - 9;
                if (d == 29 && ex == 6) data_p = 19;
                if (d == 28 && ex == 6) data_p = 18;
                month_p = 4;
            }
            int a2 = (19 * (i % 19) + 15) % 30;
            int b2 = (2 * (i % 4) + 4 * (i % 7) + 6 * a2 + 6) % 7;
            if (a2 + b2 > 9) {
                data_prav = a2 + b2 - 9;
                month_prav = 4;
            } else {
                data_prav = 22 + a2 + b2;
                month_prav = 3;
            }
            GregorianCalendar pravas = new GregorianCalendar(i, month_prav - 1, data_prav);
            GregorianCalendar katolic = new GregorianCalendar(i, month_p - 1, data_p);
            String vek = String.valueOf(yearG).substring(0, 2);
            if (vek.equals("15") || vek.equals("16"))
                pravas.add(Calendar.DATE, 10);
            if (vek.equals("17")) pravas.add(Calendar.DATE, 11);
            if (vek.equals("18")) pravas.add(Calendar.DATE, 12);
            if (vek.equals("19") || vek.equals("20"))
                pravas.add(Calendar.DATE, 13);
            TextView_Roboto_Condensed textView1 = new TextView_Roboto_Condensed(getActivity());
            textView1.setFocusable(false);
            linearLayout.addView(textView1);
            textView1.setText(data_p + " " + MonthName[month_p - 1] + " " + i);
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
            if (yearfull == i) {
                textView1.setTypeface(null, Typeface.BOLD);
                if (dzenNoch)
                    textView1.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorPrimary_black));
                else textView1.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorPrimary));
            } else if (seache == i) {
                textView1.setTypeface(null, Typeface.BOLD);
                if (dzenNoch) textView1.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorIcons));
                else textView1.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorPrimary_text));
            } else {
                textView1.setTypeface(null, Typeface.NORMAL);
                if (dzenNoch) textView1.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorIcons));
                else textView1.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorPrimary_text));
            }
            textView1.setPadding(0, 10, 0, 0);
            TextView_Roboto_Condensed textView2 = new TextView_Roboto_Condensed(getActivity());
            textView2.setFocusable(false);
            if (pravas.get(Calendar.DAY_OF_YEAR) != katolic.get(Calendar.DAY_OF_YEAR)) {
                linearLayout.addView(textView2);
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
                textView2.setText(pravas.get(Calendar.DATE) + " " + MonthName[pravas.get(Calendar.MONTH)]);
                textView2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
            }
            View view = new View(getActivity());
            LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            linLayoutParam.height = 1;
            view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
            linearLayout.addView(view, linLayoutParam);
        }
        return rootView;
    }
}