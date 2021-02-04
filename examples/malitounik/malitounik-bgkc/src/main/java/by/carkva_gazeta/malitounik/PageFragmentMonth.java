package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class PageFragmentMonth extends Fragment implements View.OnClickListener {
    
    private int wik;
    private int date;
    private int mun;
    private int year;
    private int pageNumberFull;
    private boolean dzenNoch;
    private final ArrayList<ArrayList<String>> data = new ArrayList<>();
    private final ArrayList<Padzeia> padzei = new ArrayList<>();
    private LinearLayout linearLayout;
    private int viewId;
    private long mLastClickTime = 0;

    public static PageFragmentMonth newInstance(int date, int mun, int year, int position) {
        PageFragmentMonth fragmentFirst = new PageFragmentMonth();
        Bundle args = new Bundle();
        args.putInt("date", date);
        args.putInt("mun", mun);
        args.putInt("year", year);
        args.putInt("position", position);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    private void sabytieOn() {
        if (!caliandar_mun.SabytieOnView) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            GregorianCalendar c = new GregorianCalendar(year, mun, date);
            SabytieView(c.get(Calendar.DAY_OF_YEAR) - 1);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = Objects.requireNonNull(getArguments()).getInt("date");
        mun = getArguments().getInt("mun");
        year = getArguments().getInt("year");
        int position = getArguments().getInt("position");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
        }.getType();
        data.addAll(gson.fromJson(getData(position), type));
        for (Padzeia p : MainActivity.padzeia) {
            String[] r1 = p.dat.split("[.]");
            int munL = Integer.parseInt(r1[1]) - 1;
            int yaer = Integer.parseInt(r1[2]);
            if (munL == mun && yaer == year) {
                padzei.add(p);
            }
        }
    }

    @NonNull
    private String getData(int mun) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(MainActivity.caliandar(Objects.requireNonNull(getActivity()), mun));
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            isr.close();
        } catch (IOException ignored) {
        }
        return builder.toString();
    }

    private boolean SabytieCheck(int day) {
        boolean sabytie = false;
        for (Padzeia p : padzei) {
            String[] r1 = p.dat.split("[.]");
            int date = Integer.parseInt(r1[0]);
            if (date == day) {
                sabytie = true;
                break;
            }
        }
        return sabytie;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.calendar_mun, container, false);
        SharedPreferences chin = getActivity().getSharedPreferences("biblia", Context.MODE_PRIVATE);

        linearLayout = rootview.findViewById(R.id.linearLayout);

        sabytieOn();

        TextView_Roboto_Condensed textView2 = rootview.findViewById(R.id.textView2);
        TextView_Roboto_Condensed battom1 = rootview.findViewById(R.id.button1);
        TextView_Roboto_Condensed battom2 = rootview.findViewById(R.id.button2);
        TextView_Roboto_Condensed battom3 = rootview.findViewById(R.id.button3);
        TextView_Roboto_Condensed battom4 = rootview.findViewById(R.id.button4);
        TextView_Roboto_Condensed battom5 = rootview.findViewById(R.id.button5);
        TextView_Roboto_Condensed battom6 = rootview.findViewById(R.id.button6);
        TextView_Roboto_Condensed battom7 = rootview.findViewById(R.id.button7);
        TextView_Roboto_Condensed battom8 = rootview.findViewById(R.id.button8);
        TextView_Roboto_Condensed battom9 = rootview.findViewById(R.id.button9);
        TextView_Roboto_Condensed battom10 = rootview.findViewById(R.id.button10);
        TextView_Roboto_Condensed battom11 = rootview.findViewById(R.id.button11);
        TextView_Roboto_Condensed battom12 = rootview.findViewById(R.id.button12);
        TextView_Roboto_Condensed battom13 = rootview.findViewById(R.id.button13);
        TextView_Roboto_Condensed battom14 = rootview.findViewById(R.id.button14);
        TextView_Roboto_Condensed battom15 = rootview.findViewById(R.id.button15);
        TextView_Roboto_Condensed battom16 = rootview.findViewById(R.id.button16);
        TextView_Roboto_Condensed battom17 = rootview.findViewById(R.id.button17);
        TextView_Roboto_Condensed battom18 = rootview.findViewById(R.id.button18);
        TextView_Roboto_Condensed battom19 = rootview.findViewById(R.id.button19);
        TextView_Roboto_Condensed battom20 = rootview.findViewById(R.id.button20);
        TextView_Roboto_Condensed battom21 = rootview.findViewById(R.id.button21);
        TextView_Roboto_Condensed battom22 = rootview.findViewById(R.id.button22);
        TextView_Roboto_Condensed battom23 = rootview.findViewById(R.id.button23);
        TextView_Roboto_Condensed battom24 = rootview.findViewById(R.id.button24);
        TextView_Roboto_Condensed battom25 = rootview.findViewById(R.id.button25);
        TextView_Roboto_Condensed battom26 = rootview.findViewById(R.id.button26);
        TextView_Roboto_Condensed battom27 = rootview.findViewById(R.id.button27);
        TextView_Roboto_Condensed battom28 = rootview.findViewById(R.id.button28);
        TextView_Roboto_Condensed battom29 = rootview.findViewById(R.id.button29);
        TextView_Roboto_Condensed battom30 = rootview.findViewById(R.id.button30);
        TextView_Roboto_Condensed battom31 = rootview.findViewById(R.id.button31);
        TextView_Roboto_Condensed battom32 = rootview.findViewById(R.id.button32);
        TextView_Roboto_Condensed battom33 = rootview.findViewById(R.id.button33);
        TextView_Roboto_Condensed battom34 = rootview.findViewById(R.id.button34);
        TextView_Roboto_Condensed battom35 = rootview.findViewById(R.id.button35);
        TextView_Roboto_Condensed battom36 = rootview.findViewById(R.id.button36);
        TextView_Roboto_Condensed battom37 = rootview.findViewById(R.id.button37);
        TextView_Roboto_Condensed battom38 = rootview.findViewById(R.id.button38);
        TextView_Roboto_Condensed battom39 = rootview.findViewById(R.id.button39);
        TextView_Roboto_Condensed battom40 = rootview.findViewById(R.id.button40);
        TextView_Roboto_Condensed battom41 = rootview.findViewById(R.id.button41);
        TextView_Roboto_Condensed battom42 = rootview.findViewById(R.id.button42);
        LinearLayout tableRow = rootview.findViewById(R.id.TableRow);
        LinearLayout tablerowpre = rootview.findViewById(R.id.TableRowPre);
        dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) {
            textView2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
            battom1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
            battom8.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
            battom15.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
            battom22.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
            battom29.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
            battom36.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
        }
        battom1.setOnClickListener(this);
        battom2.setOnClickListener(this);
        battom3.setOnClickListener(this);
        battom4.setOnClickListener(this);
        battom5.setOnClickListener(this);
        battom6.setOnClickListener(this);
        battom7.setOnClickListener(this);
        battom8.setOnClickListener(this);
        battom9.setOnClickListener(this);
        battom10.setOnClickListener(this);
        battom11.setOnClickListener(this);
        battom12.setOnClickListener(this);
        battom13.setOnClickListener(this);
        battom14.setOnClickListener(this);
        battom15.setOnClickListener(this);
        battom16.setOnClickListener(this);
        battom17.setOnClickListener(this);
        battom18.setOnClickListener(this);
        battom19.setOnClickListener(this);
        battom20.setOnClickListener(this);
        battom21.setOnClickListener(this);
        battom22.setOnClickListener(this);
        battom23.setOnClickListener(this);
        battom24.setOnClickListener(this);
        battom25.setOnClickListener(this);
        battom26.setOnClickListener(this);
        battom27.setOnClickListener(this);
        battom28.setOnClickListener(this);
        battom29.setOnClickListener(this);
        battom30.setOnClickListener(this);
        battom31.setOnClickListener(this);
        battom32.setOnClickListener(this);
        battom33.setOnClickListener(this);
        battom34.setOnClickListener(this);
        battom35.setOnClickListener(this);
        battom36.setOnClickListener(this);
        battom37.setOnClickListener(this);
        battom38.setOnClickListener(this);
        battom39.setOnClickListener(this);
        battom40.setOnClickListener(this);
        battom41.setOnClickListener(this);
        battom42.setOnClickListener(this);

        if (caliandar_mun.SabytieOnView) {
            linearLayout.setVisibility(View.VISIBLE);
            if (savedInstanceState != null) {
                TextView_Roboto_Condensed textView = rootview.findViewById(savedInstanceState.getInt("viewId"));
                if (textView != null) {
                    pageNumberFull = savedInstanceState.getInt("pageNumberFull");
                    wik = savedInstanceState.getInt("wik");
                    onClick(textView);
                }
            }
        }

        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();

        //GregorianCalendar calendar = new GregorianCalendar(year, mun, 1);
        //int Month = calendar.get(Calendar.MONTH);

        boolean munTudey = false;
        if (mun == c.get(Calendar.MONTH) && year == c.get(Calendar.YEAR)) munTudey = true;

        GregorianCalendar calendar_full = new GregorianCalendar(year, mun, 1);
        wik = calendar_full.get(Calendar.DAY_OF_WEEK);
        int munAll = calendar_full.getActualMaximum(Calendar.DAY_OF_MONTH);
        pageNumberFull = calendar_full.get(Calendar.DAY_OF_YEAR);
        calendar_full.add(Calendar.MONTH, -1);
        int old_mun_aktual = calendar_full.getActualMaximum(Calendar.DAY_OF_MONTH);
        int old_day = old_mun_aktual - wik + 1;
        String day;
        int i = 0;
        int new_day = 0;
        boolean nopost = false, post = false, strogiPost = false;
        for (int e = 1; e <= 42; e++) {
            GregorianCalendar calendar_post;
            int denNedeli;
            if (e < wik) {
                ++old_day;
                day = "start";
            } else if (e < munAll + wik) {
                i++;
                day = String.valueOf(i);

                nopost = Integer.parseInt(data.get(i - 1).get(7)) == 1;
                post = Integer.parseInt(data.get(i - 1).get(7)) == 2;
                strogiPost = Integer.parseInt(data.get(i - 1).get(7)) == 3;

                if (Integer.parseInt(data.get(i - 1).get(5)) == 1 || Integer.parseInt(data.get(i - 1).get(5)) == 2)
                    nopost = false;
            } else {
                ++new_day;
                day = "end";
            }
            if (42 - (munAll + wik) >= 6) tableRow.setVisibility(View.GONE);
            if (munAll + wik == 29) tablerowpre.setVisibility(View.GONE);
            calendar_post = new GregorianCalendar(year, mun, i);
            if (e == 1) {
                if (day.equals("start")) {
                    battom1.setText(String.valueOf(old_day));
                    battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                    battom1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom1.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom1.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom1.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom1.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 2) {
                if (day.equals("start")) {
                    battom2.setText(String.valueOf(old_day));
                    battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom2.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom2.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom2.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom2.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom2.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 3) {
                if (day.equals("start")) {
                    battom3.setText(String.valueOf(old_day));
                    battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom3.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom3.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom3.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom3.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom3.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 4) {
                if (day.equals("start")) {
                    battom4.setText(String.valueOf(old_day));
                    battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom4.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom4.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom4.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom4.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom4.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom4.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom4.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom4.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom4.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 5) {
                if (day.equals("start")) {
                    battom5.setText(String.valueOf(old_day));
                    battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom5.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom5.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom5.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom5.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom5.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom5.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom5.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom5.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom5.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 6) {
                if (day.equals("start")) {
                    battom6.setText(String.valueOf(old_day));
                    battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom6.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom6.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom6.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom6.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom6.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom6.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom6.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom6.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom6.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 7) {
                boolean sab = SabytieCheck(i);
                battom7.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom7.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom7.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom7.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom7.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom7.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom7.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom7.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 8) {
                boolean sab = SabytieCheck(i);
                battom8.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom8.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom8.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom8.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom8.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom8.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom8.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom8.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 9) {
                boolean sab = SabytieCheck(i);
                battom9.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom9.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom9.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom9.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom9.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom9.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom9.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom9.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 10) {
                boolean sab = SabytieCheck(i);
                battom10.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom10.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom10.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom10.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom10.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom10.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom10.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom10.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 11) {
                boolean sab = SabytieCheck(i);
                battom11.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom11.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom11.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom11.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom11.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom11.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom11.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom11.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 12) {
                boolean sab = SabytieCheck(i);
                battom12.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom12.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom12.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom12.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom12.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom12.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom12.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom12.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 13) {
                boolean sab = SabytieCheck(i);
                battom13.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom13.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom13.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom13.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom13.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom13.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom13.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom13.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 14) {
                boolean sab = SabytieCheck(i);
                battom14.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom14.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom14.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom14.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom14.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom14.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom14.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom14.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 15) {
                boolean sab = SabytieCheck(i);
                battom15.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom15.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom15.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom15.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom15.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom15.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom15.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom15.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 16) {
                boolean sab = SabytieCheck(i);
                battom16.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom16.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom16.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom16.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom16.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom16.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom16.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom16.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 17) {
                boolean sab = SabytieCheck(i);
                battom17.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom17.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom17.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom17.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom17.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom17.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom17.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom17.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 18) {
                boolean sab = SabytieCheck(i);
                battom18.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom18.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom18.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom18.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom18.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom18.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom18.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom18.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 19) {
                boolean sab = SabytieCheck(i);
                battom19.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom19.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom19.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom19.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom19.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom19.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom19.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom19.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 20) {
                boolean sab = SabytieCheck(i);
                battom20.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom20.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom20.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom20.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom20.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom20.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom20.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom20.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 21) {
                boolean sab = SabytieCheck(i);
                battom21.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom21.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom21.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom21.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom21.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom21.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom21.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom21.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 22) {
                boolean sab = SabytieCheck(i);
                battom22.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom22.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom22.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom22.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom22.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom22.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom22.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom22.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 23) {
                boolean sab = SabytieCheck(i);
                battom23.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom23.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom23.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom23.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom23.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom23.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom23.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom23.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 24) {
                boolean sab = SabytieCheck(i);
                battom24.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom24.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom24.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom24.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom24.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom24.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom24.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom24.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 25) {
                boolean sab = SabytieCheck(i);
                battom25.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom25.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom25.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom25.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom25.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom25.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom25.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom25.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 26) {
                boolean sab = SabytieCheck(i);
                battom26.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom26.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom26.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom26.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom26.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom26.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom26.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom26.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 27) {
                boolean sab = SabytieCheck(i);
                battom27.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom27.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom27.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom27.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom27.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom27.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom27.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom27.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 28) {
                boolean sab = SabytieCheck(i);
                battom28.setText(day);
                if (data.get(i - 1).get(4).contains("#d00505"))
                    battom28.setTypeface(null, Typeface.BOLD);
                switch (Integer.parseInt(data.get(i - 1).get(5))) {
                    case 1:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom28.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom28.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                            if (dzenNoch) {
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                            } else {
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                            }
                        } else {
                            if (dzenNoch) {
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                            } else {
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                            }
                        }
                        battom28.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        battom28.setTypeface(null, Typeface.NORMAL);
                        break;
                    default:
                        if (nopost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                            else if (sab)
                                battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                            else
                                battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                        }
                        if (post) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                            else if (sab)
                                battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                            else
                                battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                        }
                        if (strogiPost) {
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                            else if (sab)
                                battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                            else
                                battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                            battom28.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            } else {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                    else
                                        battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                else if (sab)
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                else
                                    battom28.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                            }
                        }
                        break;
                }
            }
            if (e == 29) {
                if (day.equals("end")) {
                    battom29.setText(String.valueOf(new_day));
                    battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                    battom29.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom29.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom29.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom29.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom29.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom29.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom29.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom29.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom29.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 30) {
                if (day.equals("end")) {
                    battom30.setText(String.valueOf(new_day));
                    battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom30.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom30.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom30.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom30.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom30.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom30.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom30.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom30.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom30.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 31) {
                if (day.equals("end")) {
                    battom31.setText(String.valueOf(new_day));
                    battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom31.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom31.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom31.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom31.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom31.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom31.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom31.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom31.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom31.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 32) {
                if (day.equals("end")) {
                    battom32.setText(String.valueOf(new_day));
                    battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom32.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom32.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom32.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom32.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom32.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom32.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom32.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom32.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom32.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 33) {
                if (day.equals("end")) {
                    battom33.setText(String.valueOf(new_day));
                    battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom33.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom33.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom33.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom33.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom33.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom33.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom33.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom33.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom33.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 34) {
                if (day.equals("end")) {
                    battom34.setText(String.valueOf(new_day));
                    battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom34.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom34.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom34.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom34.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom34.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom34.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom34.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom34.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom34.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 35) {
                if (day.equals("end")) {
                    battom35.setText(String.valueOf(new_day));
                    battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom35.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom35.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom35.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom35.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom35.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom35.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom35.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom35.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom35.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 36) {
                if (day.equals("end")) {
                    battom36.setText(String.valueOf(new_day));
                    battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                    battom36.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom36.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom36.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom36.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom36.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom36.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom36.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom36.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom36.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 37) {
                if (day.equals("end")) {
                    battom37.setText(String.valueOf(new_day));
                    battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom37.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                } else {
                    boolean sab = SabytieCheck(i);
                    battom37.setText(day);
                    if (data.get(i - 1).get(4).contains("#d00505"))
                        battom37.setTypeface(null, Typeface.BOLD);
                    switch (Integer.parseInt(data.get(i - 1).get(5))) {
                        case 1:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom37.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom37.setTypeface(null, Typeface.BOLD);
                            break;
                        case 2:
                            if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey) {
                                if (dzenNoch) {
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_sabytie_black));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today_black));
                                } else {
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_today));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_today));
                                }
                            } else {
                                if (dzenNoch) {
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie_black));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_black));
                                } else {
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red_sabytie));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_red));
                                }
                            }
                            battom37.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            battom37.setTypeface(null, Typeface.NORMAL);
                            break;
                        default:
                            if (nopost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                else if (sab)
                                    battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                else
                                    battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                            }
                            if (post) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie_today));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_today));
                                else if (sab)
                                    battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post_sabytie));
                                else
                                    battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_post));
                            }
                            if (strogiPost) {
                                if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                    if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie_today));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_today));
                                else if (sab)
                                    battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post_sabytie));
                                else
                                    battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_strogi_post));
                                battom37.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                            }
                            if (!nopost && !post && !strogiPost) {
                                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                                if (denNedeli == 1) {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie_today));
                                        else
                                            battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_today));
                                    else if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta_sabytie));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_bez_posta));
                                } else {
                                    if (c.get(Calendar.DAY_OF_MONTH) == i && munTudey)
                                        if (sab)
                                            battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie_today));
                                        else
                                            battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_today));
                                    else if (sab)
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day_sabytie));
                                    else
                                        battom37.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                                }
                            }
                            break;
                    }
                }
            }
            if (e == 38) {
                if (day.equals("end")) {
                    battom38.setText(String.valueOf(new_day));
                    battom38.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom38.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                }
            }
            if (e == 39) {
                if (day.equals("end")) {
                    battom39.setText(String.valueOf(new_day));
                    battom39.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom39.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                }
            }
            if (e == 40) {
                if (day.equals("end")) {
                    battom40.setText(String.valueOf(new_day));
                    battom40.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom40.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                }
            }
            if (e == 41) {
                if (day.equals("end")) {
                    battom41.setText(String.valueOf(new_day));
                    battom41.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom41.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                }
            }
            if (e == 42) {
                if (day.equals("end")) {
                    battom42.setText(String.valueOf(new_day));
                    battom42.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.calendar_day));
                    battom42.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSecondary_text));
                }
            }
        }
        return rootview;
    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        if (linearLayout.getVisibility() == View.GONE) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.button1:
                    intent.putExtra("data", pageNumberFull - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button2:
                    intent.putExtra("data", pageNumberFull + 1 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button3:
                    intent.putExtra("data", pageNumberFull + 2 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button4:
                    intent.putExtra("data", pageNumberFull + 3 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button5:
                    intent.putExtra("data", pageNumberFull + 4 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button6:
                    intent.putExtra("data", pageNumberFull + 5 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button7:
                    intent.putExtra("data", pageNumberFull + 6 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button8:
                    intent.putExtra("data", pageNumberFull + 7 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button9:
                    intent.putExtra("data", pageNumberFull + 8 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button10:
                    intent.putExtra("data", pageNumberFull + 9 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button11:
                    intent.putExtra("data", pageNumberFull + 10 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button12:
                    intent.putExtra("data", pageNumberFull + 11 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button13:
                    intent.putExtra("data", pageNumberFull + 12 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button14:
                    intent.putExtra("data", pageNumberFull + 13 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button15:
                    intent.putExtra("data", pageNumberFull + 14 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button16:
                    intent.putExtra("data", pageNumberFull + 15 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button17:
                    intent.putExtra("data", pageNumberFull + 16 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button18:
                    intent.putExtra("data", pageNumberFull + 17 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button19:
                    intent.putExtra("data", pageNumberFull + 18 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button20:
                    intent.putExtra("data", pageNumberFull + 19 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button21:
                    intent.putExtra("data", pageNumberFull + 20 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button22:
                    intent.putExtra("data", pageNumberFull + 21 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button23:
                    intent.putExtra("data", pageNumberFull + 22 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button24:
                    intent.putExtra("data", pageNumberFull + 23 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button25:
                    intent.putExtra("data", pageNumberFull + 24 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button26:
                    intent.putExtra("data", pageNumberFull + 25 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button27:
                    intent.putExtra("data", pageNumberFull + 26 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button28:
                    intent.putExtra("data", pageNumberFull + 27 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button29:
                    intent.putExtra("data", pageNumberFull + 28 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button30:
                    intent.putExtra("data", pageNumberFull + 29 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button31:
                    intent.putExtra("data", pageNumberFull + 30 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button32:
                    intent.putExtra("data", pageNumberFull + 31 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button33:
                    intent.putExtra("data", pageNumberFull + 32 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button34:
                    intent.putExtra("data", pageNumberFull + 33 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button35:
                    intent.putExtra("data", pageNumberFull + 34 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button36:
                    intent.putExtra("data", pageNumberFull + 35 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button37:
                    intent.putExtra("data", pageNumberFull + 36 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button38:
                    intent.putExtra("data", pageNumberFull + 37 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button39:
                    intent.putExtra("data", pageNumberFull + 38 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button40:
                    intent.putExtra("data", pageNumberFull + 39 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button41:
                    intent.putExtra("data", pageNumberFull + 40 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
                case R.id.button42:
                    intent.putExtra("data", pageNumberFull + 41 - wik);
                    intent.putExtra("year", year);
                    Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                    break;
            }
        } else {
            viewId = v.getId();
            switch (viewId) {
                case R.id.button1:
                    SabytieView(pageNumberFull - wik);
                    break;
                case R.id.button2:
                    SabytieView(pageNumberFull + 1 - wik);
                    break;
                case R.id.button3:
                    SabytieView(pageNumberFull + 2 - wik);
                    break;
                case R.id.button4:
                    SabytieView(pageNumberFull + 3 - wik);
                    break;
                case R.id.button5:
                    SabytieView(pageNumberFull + 4 - wik);
                    break;
                case R.id.button6:
                    SabytieView(pageNumberFull + 5 - wik);
                    break;
                case R.id.button7:
                    SabytieView(pageNumberFull + 6 - wik);
                    break;
                case R.id.button8:
                    SabytieView(pageNumberFull + 7 - wik);
                    break;
                case R.id.button9:
                    SabytieView(pageNumberFull + 8 - wik);
                    break;
                case R.id.button10:
                    SabytieView(pageNumberFull + 9 - wik);
                    break;
                case R.id.button11:
                    SabytieView(pageNumberFull + 10 - wik);
                    break;
                case R.id.button12:
                    SabytieView(pageNumberFull + 11 - wik);
                    break;
                case R.id.button13:
                    SabytieView(pageNumberFull + 12 - wik);
                    break;
                case R.id.button14:
                    SabytieView(pageNumberFull + 13 - wik);
                    break;
                case R.id.button15:
                    SabytieView(pageNumberFull + 14 - wik);
                    break;
                case R.id.button16:
                    SabytieView(pageNumberFull + 15 - wik);
                    break;
                case R.id.button17:
                    SabytieView(pageNumberFull + 16 - wik);
                    break;
                case R.id.button18:
                    SabytieView(pageNumberFull + 17 - wik);
                    break;
                case R.id.button19:
                    SabytieView(pageNumberFull + 18 - wik);
                    break;
                case R.id.button20:
                    SabytieView(pageNumberFull + 19 - wik);
                    break;
                case R.id.button21:
                    SabytieView(pageNumberFull + 20 - wik);
                    break;
                case R.id.button22:
                    SabytieView(pageNumberFull + 21 - wik);
                    break;
                case R.id.button23:
                    SabytieView(pageNumberFull + 22 - wik);
                    break;
                case R.id.button24:
                    SabytieView(pageNumberFull + 23 - wik);
                    break;
                case R.id.button25:
                    SabytieView(pageNumberFull + 24 - wik);
                    break;
                case R.id.button26:
                    SabytieView(pageNumberFull + 25 - wik);
                    break;
                case R.id.button27:
                    SabytieView(pageNumberFull + 26 - wik);
                    break;
                case R.id.button28:
                    SabytieView(pageNumberFull + 27 - wik);
                    break;
                case R.id.button29:
                    SabytieView(pageNumberFull + 28 - wik);
                    break;
                case R.id.button30:
                    SabytieView(pageNumberFull + 29 - wik);
                    break;
                case R.id.button31:
                    SabytieView(pageNumberFull + 30 - wik);
                    break;
                case R.id.button32:
                    SabytieView(pageNumberFull + 31 - wik);
                    break;
                case R.id.button33:
                    SabytieView(pageNumberFull + 32 - wik);
                    break;
                case R.id.button34:
                    SabytieView(pageNumberFull + 33 - wik);
                    break;
                case R.id.button35:
                    SabytieView(pageNumberFull + 34 - wik);
                    break;
                case R.id.button36:
                    SabytieView(pageNumberFull + 35 - wik);
                    break;
                case R.id.button37:
                    SabytieView(pageNumberFull + 36 - wik);
                    break;
                case R.id.button38:
                    SabytieView(pageNumberFull + 37 - wik);
                    break;
                case R.id.button39:
                    SabytieView(pageNumberFull + 38 - wik);
                    break;
                case R.id.button40:
                    SabytieView(pageNumberFull + 39 - wik);
                    break;
                case R.id.button41:
                    SabytieView(pageNumberFull + 40 - wik);
                    break;
                case R.id.button42:
                    SabytieView(pageNumberFull + 41 - wik);
                    break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("viewId", viewId);
        outState.putInt("pageNumberFull", pageNumberFull);
        outState.putInt("wik", wik);
    }

    private void SabytieView(int DayYear) {
        linearLayout.removeAllViewsInLayout();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        String title;
        ArrayList<TextView_Roboto_Condensed> sabytieList = new ArrayList<>();
        for (Padzeia p : MainActivity.padzeia) {
            String[] r1 = p.dat.split("[.]");
            String[] r2 = p.datK.split("[.]");
            gc.set(Integer.parseInt(r1[2]), Integer.parseInt(r1[1]) - 1, Integer.parseInt(r1[0]));
            int NaY = gc.get(Calendar.YEAR);
            int Na = gc.get(Calendar.DAY_OF_YEAR);
            gc.set(Integer.parseInt(r2[2]), Integer.parseInt(r2[1]) - 1, Integer.parseInt(r2[0]));
            int yaerw = gc.get(Calendar.YEAR);
            int Kon = gc.get(Calendar.DAY_OF_YEAR);
            int rezkK = Kon - Na + 1;
            if (yaerw > NaY) {
                int leapYear = 365;
                if (gc.isLeapYear(NaY)) leapYear = 366;
                rezkK = leapYear - Na + Kon;
            }
            gc.set(Integer.parseInt(r1[2]), Integer.parseInt(r1[1]) - 1, Integer.parseInt(r1[0]));
            for (int i = 0; i < rezkK; i++) {
                if (gc.get(Calendar.DAY_OF_YEAR) - 1 == DayYear && gc.get(Calendar.YEAR) == year) {
                    title = p.padz;
                    String data = p.dat;
                    String time = p.tim;
                    String dataK = p.datK;
                    String timeK = p.timK;
                    long paz = p.paznic;
                    String res = "Паведаміць: Ніколі";
                    if (paz != 0) {
                        gc.setTimeInMillis(paz);
                        String nol1 = "", nol2 = "", nol3 = "";
                        if (gc.get(Calendar.DATE) < 10) nol1 = "0";
                        if (gc.get(Calendar.MONTH) < 10) nol2 = "0";
                        if (gc.get(Calendar.MINUTE) < 10) nol3 = "0";
                        res = "Паведаміць: " + nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR) + " у " + gc.get(Calendar.HOUR_OF_DAY) + ":" + nol3 + gc.get(Calendar.MINUTE);
                    }
                    TextView_Roboto_Condensed textViewT = new TextView_Roboto_Condensed(getActivity());
                    textViewT.setText(title);
                    textViewT.setPadding(20, 10, 10, 10);
                    textViewT.setTypeface(null, Typeface.BOLD);
                    textViewT.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
                    textViewT.setTypeface(null, Typeface.BOLD);
                    textViewT.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorIcons));
                    textViewT.setBackgroundColor(Color.parseColor(sabytie.getColors(getActivity())[p.color]));
                    sabytieList.add(textViewT);
                    TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                    textView.setPadding(20, 0, 10, 10);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
                    textView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorDivider));
                    if (dzenNoch) {
                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                        textView.setBackgroundResource(R.color.colorprimary_material_dark);
                    }
                    if (data.equals(dataK) && time.equals(timeK)) {
                        textView.setText(getResources().getString(R.string.sabytieKali, data, time, res));
                    } else {
                        textView.setText(getResources().getString(R.string.sabytieDoKuda, data, time, dataK, timeK, res));
                    }
                    sabytieList.add(textView);
                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    llp.setMargins(0, 0, 0, 10);
                    textView.setLayoutParams(llp);
                }
                gc.add(Calendar.DATE, 1);
            }
        }
        if (sabytieList.size() > 0) {
            for (int i = 0; i < sabytieList.size(); i++) {
                linearLayout.addView(sabytieList.get(i));
            }
        }
    }
}



