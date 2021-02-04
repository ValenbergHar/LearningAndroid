package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class caliandar_nedzel extends ListFragment {
    
    private int year;
    private int mun;
    private int date;
    private int dateInt;
    private int position;
    private int count;
    private ArrayList<ArrayList<String>> strings, strings2;
    static boolean setDenNedeli = false;

    public static caliandar_nedzel newInstance(int year, int mun, int date, int position) {
        caliandar_nedzel fragment = new caliandar_nedzel();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("year", year);
        args.putInt("mun", mun);
        args.putInt("date", date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        year = Objects.requireNonNull(getArguments()).getInt("year");
        mun = getArguments().getInt("mun");
        dateInt = getArguments().getInt("date");
        position = getArguments().getInt("position");
        count = count();
        date = getMun();
        strings = new ArrayList<>();
        strings2 = new ArrayList<>();
    }

    private int getMun() {
        GregorianCalendar gS = new GregorianCalendar(year, mun, dateInt);
        GregorianCalendar g = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, 1);
        for (int i = 0; i < count; i++) {
            if (g.get(Calendar.MONTH) == gS.get(Calendar.MONTH) && g.get(Calendar.YEAR) == gS.get(Calendar.YEAR)) {
                return i;
            }
            g.add(Calendar.MONTH, 1);
        }
        return 0;
    }

    private int count() {
        return (SettingsActivity.GET_CALIANDAR_YEAR_MAX - SettingsActivity.GET_CALIANDAR_YEAR_MIN + 1) * 12;
    }

    private void getJsonFile() throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStream inputStream = getResources().openRawResource(MainActivity.caliandar(Objects.requireNonNull(getActivity()), date));
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        isr.close();
        String out = builder.toString();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<String>>>(){}.getType();
        strings.addAll(gson.fromJson(out, type));
        if (date != count - 1) {
            for (int i = 0; i < strings.size(); i++) {
                if (26 >= Integer.parseInt(strings.get(i).get(1))) {
                    int t1 = builder.toString().lastIndexOf("]");
                    StringBuilder builderN = new StringBuilder();
                    inputStream = getResources().openRawResource(MainActivity.caliandar(getActivity(),date + 1));
                    isr = new InputStreamReader(inputStream);
                    reader = new BufferedReader(isr);
                    while ((line = reader.readLine()) != null) {
                        builderN.append(line);
                    }
                    isr.close();
                    int t2 = builderN.toString().indexOf("[");
                    String out1 = out.substring(0, t1);
                    String out2 = builderN.toString().substring(t2 + 1);
                    out = out1 + "," + out2;
                    strings.clear();
                    break;
                }
            }
        }
        strings.addAll(gson.fromJson(out, type));
        for (int i = 0; i < strings.size(); i++) {
            if (Integer.parseInt(strings.get(i).get(1)) == dateInt) {
                for (int e = 0; e < 7; e++) {
                    strings2.add(strings.get(i));
                    i++;
                }
                break;
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            getJsonFile();
        } catch (IOException ignored) {
        }
        String[] str = new String[7];
        caliandar_nedzel_list_adapter adapter = new caliandar_nedzel_list_adapter(Objects.requireNonNull(getActivity()), strings2, str);
        setListAdapter(adapter);
        if (setDenNedeli) {
            GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
            if (getNedzel(c.get(Calendar.WEEK_OF_YEAR) - 1) == position) {
                getListView().setSelection(c.get(Calendar.DAY_OF_WEEK) - 1);
                setDenNedeli = false;
            }
        }
        getListView().setVerticalScrollBarEnabled(false);
    }

    private int getNedzel(int WeekOfYear) {
        GregorianCalendar calendar = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MAX, 11, 31);
        int dayyear = 0;
        for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < SettingsActivity.GET_CALIANDAR_YEAR_MAX - 1; i++) {
            if (calendar.isLeapYear(i)) dayyear = 366 + dayyear;
            else dayyear = 365 + dayyear;
        }
        return dayyear / 7 + WeekOfYear;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        GregorianCalendar g = new GregorianCalendar(Integer.parseInt(strings2.get(position).get(3)), Integer.parseInt(strings2.get(position).get(2)), Integer.parseInt(strings2.get(position).get(1)));
        Intent intent = new Intent();
        intent.putExtra("data", g.get(Calendar.DAY_OF_YEAR) - 1);
        intent.putExtra("year", Integer.parseInt(strings2.get(position).get(3)));
        Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
