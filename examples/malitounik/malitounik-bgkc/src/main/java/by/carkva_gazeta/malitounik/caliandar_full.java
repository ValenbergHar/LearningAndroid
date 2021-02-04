package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Objects;

public class caliandar_full extends Fragment implements View.OnClickListener {

    private int dayYear;
    private int day;
    private int year;
    private boolean dzenNoch;
    private int R_color_colorPrimary = R.color.colorPrimary;
    private final ArrayList<ArrayList<String>> data = new ArrayList<>();
    private LinearLayout padzei;
    private ScrollView scrollView;
    private final Gson gson = new Gson();
    private String sabytieTitle;
    private int position;
    private long mLastClickTime = 0;

    @NonNull
    public static caliandar_full newInstance(int position, int day, int year, int dayYear) {
        caliandar_full fragment = new caliandar_full();
        Bundle args = new Bundle();
        args.putInt("dayYear", dayYear);
        args.putInt("year", year);
        args.putInt("day", day);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    private String getData(int mun) {
        if (Menu_caliandar.munKal == mun && !Menu_caliandar.dataJson.equals("")) {
            return Menu_caliandar.dataJson;
        } else {
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
            Menu_caliandar.dataJson = builder.toString();
            Menu_caliandar.munKal = mun;
            return builder.toString();
        }
    }

    private int getmun(int position) {
        int count2 = 0;
        GregorianCalendar g = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, 1);
        int Mun = g.get(Calendar.MONTH);
        for (int i = 0; i < getCount(); i++) {
            g.add(Calendar.DATE, 1);
            if (position == i) {
                return count2;
            }
            if (g.get(Calendar.MONTH) != Mun) {
                count2++;
                Mun = g.get(Calendar.MONTH);
            }
        }
        return count2;
    }

    private int getCount() {
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        int dayyear = 0;
        for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i <= SettingsActivity.GET_CALIANDAR_YEAR_MAX; i++) {
            if (c.isLeapYear(i)) dayyear = 366 + dayyear;
            else dayyear = 365 + dayyear;
        }
        return dayyear;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dayYear = Objects.requireNonNull(getArguments()).getInt("dayYear");
        year = getArguments().getInt("year");
        day = getArguments().getInt("day");
        position = getArguments().getInt("position");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
        }.getType();
        data.addAll(gson.fromJson(getData(getmun(position)), type));

        String[] NedelName = {"", "нядзеля", "панядзелак", "аўторак", "серада", "чацьвер", "пятніца", "субота"};
        String[] MonthName = getResources().getStringArray(R.array.mun_array);
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        View rootView = inflater.inflate(R.layout.calaindar, container, false);
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch)
            R_color_colorPrimary = R.color.colorPrimary_black;
        BitmapDrawable TileMe = new BitmapDrawable(getActivity().getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.calendar_fon));
        TileMe.setTileModeX(Shader.TileMode.REPEAT);

        padzei = rootView.findViewById(R.id.padzei);
        TextView_Roboto_Condensed textTitleChyt = rootView.findViewById(R.id.textTitleChyt);
        TextView_Roboto_Condensed textChytanne = rootView.findViewById(R.id.textChytanne);
        TextView_Roboto_Condensed textChytanneSviatyia = rootView.findViewById(R.id.textChytanneSviatyia);
        TextView_Roboto_Condensed textPost = rootView.findViewById(R.id.textPost);
        scrollView = rootView.findViewById(R.id.scroll);
        if (!data.get(day).get(20).equals("") && Integer.parseInt(data.get(day).get(0)) == 1) {
            String ton = data.get(day).get(20);
            textTitleChyt.setText(ton + "\n\n" + textTitleChyt.getText());
            textTitleChyt.setOnClickListener(this);
        } else {
            textTitleChyt.setEnabled(false);
        }
        ImageView PostFish = rootView.findViewById(R.id.PostFish);
        textChytanne.setOnClickListener(this);
        textChytanneSviatyia.setOnClickListener(this);
        TextView_Roboto_Condensed textChytanneSviatyiaDop = rootView.findViewById(R.id.textChytanneSviatyiaDop);
        textChytanneSviatyiaDop.setOnClickListener(this);
        TextView_Roboto_Condensed textPameplyia = rootView.findViewById(R.id.textPamerlyia);

        int maranata = k.getInt("maranata", 0);
        TextView_Roboto_Condensed maranata1 = rootView.findViewById(R.id.maranata);
        TextView_Roboto_Condensed textTitleMaranata = rootView.findViewById(R.id.textTitleMaranata);
        if (maranata == 1) {
            maranata1.setOnClickListener(this);
            if (dzenNoch) {
                maranata1.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_dark_maranata));
                maranata1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                textTitleMaranata.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_dark_maranata));
                textTitleMaranata.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            }
            maranata1.setVisibility(View.VISIBLE);
            textTitleMaranata.setVisibility(View.VISIBLE);
            String dataMaranAta = data.get(day).get(13);
            if (k.getBoolean("belarus", false))
                dataMaranAta = MainActivity.translateToBelarus(dataMaranAta);
            maranata1.setText(dataMaranAta);
        }

        TextView_Roboto_Condensed textMesiac = rootView.findViewById(R.id.textMesiac);
        TextView_Roboto_Condensed textChislo = rootView.findViewById(R.id.textChislo);
        TextView_Roboto_Condensed textDenNedeli = rootView.findViewById(R.id.textDenNedeli);

        View polosa1 = rootView.findViewById(R.id.polosa1);
        View polosa2 = rootView.findViewById(R.id.polosa2);
        TextView_Roboto_Condensed textPredsviaty = rootView.findViewById(R.id.textPredsviaty);
        TextView_Roboto_Condensed textSviatyia = rootView.findViewById(R.id.textSviatyia);
        TextView_Roboto_Condensed textCviatyGlavnyia = rootView.findViewById(R.id.textCviatyGlavnyia);
        ImageView imageView3 = rootView.findViewById(R.id.znakTipicona);
        imageView3.setOnClickListener(this);
        if (dzenNoch) {
            textSviatyia.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textSviatyia.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_dark));
            textPost.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textCviatyGlavnyia.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
            textCviatyGlavnyia.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_dark));
            textPredsviaty.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        }
        textDenNedeli.setText(NedelName[Integer.parseInt(data.get(day).get(0))]);
        textChislo.setText(data.get(day).get(1));
        if (Integer.parseInt(data.get(day).get(3)) != c.get(Calendar.YEAR))
            textMesiac.setText(MonthName[Integer.parseInt(data.get(day).get(2))] + ", " + data.get(day).get(3));
        else
            textMesiac.setText(MonthName[Integer.parseInt(data.get(day).get(2))]);
        if (!data.get(day).get(4).contains("no_sviatyia")) {
            String dataSviatyia = data.get(day).get(4);
            if (dzenNoch) dataSviatyia = dataSviatyia.replace("#d00505", "#f44336");
            textSviatyia.setText(MainActivity.fromHtml(dataSviatyia));
        } else {
            polosa1.setVisibility(View.GONE);
            polosa2.setVisibility(View.GONE);
            textSviatyia.setVisibility(View.GONE);
        }
        textSviatyia.setOnClickListener(this);
        if (!data.get(day).get(6).contains("no_sviaty")) {
            textCviatyGlavnyia.setText(data.get(day).get(6));
            textCviatyGlavnyia.setVisibility(View.VISIBLE);
            if (data.get(day).get(6).contains("Пачатак") || data.get(day).get(6).contains("Вялікі") || data.get(day).get(6).contains("Вялікая") || data.get(day).get(6).contains("ВЕЧАР") || data.get(day).get(6).contains("Палова")) {
                if (dzenNoch)
                    textCviatyGlavnyia.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                else
                    textCviatyGlavnyia.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                textCviatyGlavnyia.setTypeface(null, Typeface.NORMAL);
                textCviatyGlavnyia.setEnabled(false);
            } else {
                if (data.get(day).get(6).toLowerCase().contains("нядзел") || data.get(day).get(6).toLowerCase().contains("дзень"))
                    textCviatyGlavnyia.setEnabled(false);
                else
                    textCviatyGlavnyia.setOnClickListener(this);
            }
        }
        switch (Integer.parseInt(data.get(day).get(7))) {
            case 1:
                textDenNedeli.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                textChislo.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                textMesiac.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                textDenNedeli.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_bez_posta));
                textChislo.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_bez_posta));
                textMesiac.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_bez_posta));
                textTitleChyt.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_bez_posta));
                textChytanne.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_bez_posta));
                textChytanneSviatyiaDop.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_bez_posta));
                textChytanneSviatyia.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_bez_posta));
                textPameplyia.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_bez_posta));
                if (data.get(day).get(0).contains("6")) {
                    textPost.setVisibility(View.VISIBLE);
                    textPost.setText(getResources().getString(R.string.No_post));
                }
                break;
            case 2:
                textTitleChyt.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_post));
                textChytanne.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_post));
                textChytanneSviatyiaDop.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_post));
                textChytanneSviatyia.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_post));
                textDenNedeli.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_post));
                textChislo.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_post));
                textMesiac.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_post));
                textPameplyia.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_post));
                if (data.get(day).get(0).contains("6")) {
                    PostFish.setVisibility(View.VISIBLE);
                    textPost.setVisibility(View.VISIBLE);
                    if (dzenNoch) {
                        PostFish.setImageResource(R.drawable.fishe_whate);
                    }
                }
                break;
            case 3:
                textDenNedeli.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_strogi_post));
                textDenNedeli.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                textChislo.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_strogi_post));
                textChislo.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                textMesiac.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_strogi_post));
                textMesiac.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                textTitleChyt.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_strogi_post));
                textTitleChyt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                textChytanne.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_strogi_post));
                textChytanne.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                textChytanneSviatyiaDop.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_strogi_post));
                textChytanneSviatyiaDop.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                textChytanneSviatyia.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_strogi_post));
                textChytanneSviatyia.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                textPost.setText(getResources().getString(R.string.Strogi_post));
                textPameplyia.setText(getResources().getString(R.string.Strogi_post));
                textPameplyia.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                textPost.setVisibility(View.VISIBLE);
                PostFish.setVisibility(View.VISIBLE);
                if (dzenNoch)
                    PostFish.setImageResource(R.drawable.fishe_red_black);
                else
                    PostFish.setImageResource(R.drawable.fishe_red);
                break;
        }
        if (data.get(day).get(5).contains("1") || data.get(day).get(5).contains("2") || data.get(day).get(5).contains("3")) {
            textDenNedeli.setBackgroundResource(R_color_colorPrimary);
            textChislo.setBackgroundResource(R_color_colorPrimary);
            textMesiac.setBackgroundResource(R_color_colorPrimary);
            textDenNedeli.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textChislo.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
            textMesiac.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        }
        if (data.get(day).get(5).contains("2")) {
            textCviatyGlavnyia.setTypeface(null, Typeface.NORMAL);
        }
        if (!data.get(day).get(8).equals("")) {
            textPredsviaty.setText(MainActivity.fromHtml(data.get(day).get(8)));
            textPredsviaty.setVisibility(View.VISIBLE);
        }
        textChytanne.setText(data.get(day).get(9));
        if (data.get(day).get(9).contains("Прабачьце, няма дадзеных") || !data.get(day).get(9).contains(";"))
            textChytanne.setEnabled(false);
        if (data.get(day).get(9).equals(""))
            textChytanne.setVisibility(View.GONE);
        if (!data.get(day).get(10).equals("")) {
            textChytanneSviatyia.setText(data.get(day).get(10));
            textChytanneSviatyia.setVisibility(View.VISIBLE);
        }
        if (!data.get(day).get(11).equals("")) {
            textChytanneSviatyiaDop.setText(data.get(day).get(11));
            textChytanneSviatyiaDop.setVisibility(View.VISIBLE);
        }

        switch (Integer.parseInt(data.get(day).get(12))) {
            case 1:
                if (dzenNoch)
                    imageView3.setImageResource(R.drawable.znaki_krest_black);
                else
                    imageView3.setImageResource(R.drawable.znaki_krest);
                imageView3.setVisibility(View.VISIBLE);
                break;
            case 2:
                int R_drawable_znaki_krest_v_kruge = R.drawable.znaki_krest_v_kruge;
                if (dzenNoch)
                    R_drawable_znaki_krest_v_kruge = R.drawable.znaki_krest_v_kruge_black;
                imageView3.setImageResource(R_drawable_znaki_krest_v_kruge);
                imageView3.setVisibility(View.VISIBLE);
                break;
            case 3:
                if (dzenNoch)
                    imageView3.setImageResource(R.drawable.znaki_krest_v_polukruge_black);
                else
                    imageView3.setImageResource(R.drawable.znaki_krest_v_polukruge);
                imageView3.setVisibility(View.VISIBLE);
                break;
            case 4:
                if (dzenNoch)
                    imageView3.setImageResource(R.drawable.znaki_ttk_black_black);
                else
                    imageView3.setImageResource(R.drawable.znaki_ttk);
                imageView3.setVisibility(View.VISIBLE);
                break;
            case 5:
                imageView3.setVisibility(View.VISIBLE);
                if (dzenNoch) {
                    imageView3.setImageResource(R.drawable.znaki_ttk_whate);
                } else {
                    imageView3.setImageResource(R.drawable.znaki_ttk_black);
                }
                break;
        }

        if (k.getInt("pravas", 0) == 1) {
            if (!data.get(day).get(14).equals("")) {
                TextView_Roboto_Condensed pravaslavie = rootView.findViewById(R.id.pravaslavie);
                pravaslavie.setVisibility(View.VISIBLE);
                pravaslavie.setText(data.get(day).get(14));
            }
        }
        if (k.getInt("pkc", 0) == 1) {
            if (!data.get(day).get(19).equals("")) {
                TextView_Roboto_Condensed pkc = rootView.findViewById(R.id.RKC);
                pkc.setVisibility(View.VISIBLE);
                pkc.setText(data.get(day).get(19));
            }
        }
        if (k.getInt("gosud", 0) == 1) {
            TextView_Roboto_Condensed gosudarstvo = rootView.findViewById(R.id.gosudarstvo);
            if (!data.get(day).get(16).equals("")) {
                gosudarstvo.setVisibility(View.VISIBLE);
                gosudarstvo.setText(data.get(day).get(16));
            }
            if (!data.get(day).get(15).equals("")) {
                gosudarstvo.setVisibility(View.VISIBLE);
                gosudarstvo.setText(data.get(day).get(15));
                gosudarstvo.setTextColor(ContextCompat.getColor(getActivity(), R_color_colorPrimary));
            }
        }
        if (k.getInt("pafesii", 0) == 1) {
            if (!data.get(day).get(17).equals("")) {
                TextView_Roboto_Condensed prafesional = rootView.findViewById(R.id.prafesional);
                prafesional.setVisibility(View.VISIBLE);
                prafesional.setText(data.get(day).get(17));
            }
        }
        if (data.get(day).get(18).contains("1")) {
            textPameplyia.setVisibility(View.VISIBLE);
        }

        if (MainActivity.padzeia.size() > 0) {
            Bundle extras = Objects.requireNonNull(getActivity()).getIntent().getExtras();
            if (extras != null && extras.getBoolean("sabytieView", false)) {
                sabytieTitle = extras.getString("sabytieTitle", "");
            }
            getActivity().runOnUiThread(() -> SabytieView(sabytieTitle));
        }
        return rootView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        int dayyear = 0;
        for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < year; i++) {
            if (c.isLeapYear(i)) dayyear = 366 + dayyear;
            else dayyear = 365 + dayyear;
        }
        MainActivity.setDataCalendar = dayyear + dayYear;
        switch (v.getId()) {
            case R.id.textCviatyGlavnyia:
                if (MainActivity.checkModule_resources(getActivity())) {
                    try {
                        Intent i = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.opisanie"));
                        i.putExtra("glavnyia", true);
                        i.putExtra("svity", data.get(day).get(6));
                        i.putExtra("mun", Integer.parseInt(data.get(day).get(2)));
                        i.putExtra("day", Integer.parseInt(data.get(day).get(1)));
                        startActivity(i);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                    dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                }
                break;
            case R.id.textSviatyia:
                if (MainActivity.checkModule_resources(getActivity())) {
                    try {
                        Intent i = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.opisanie"));
                        i.putExtra("mun", Integer.parseInt(data.get(day).get(2)));
                        i.putExtra("day", Integer.parseInt(data.get(day).get(1)));
                        startActivity(i);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                    dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                }
                break;
            case R.id.textChytanneSviatyia:
                if (MainActivity.checkModule_resources(getActivity())) {
                    try {
                        Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.chytanne"));
                        intent.putExtra("cytanne", data.get(day).get(10));
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                    dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                }
                break;
            case R.id.textChytanne:
                if (MainActivity.checkModule_resources(getActivity())) {
                    try {
                        Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.chytanne"));
                        intent.putExtra("cytanne", data.get(day).get(9));
                        intent.putExtra("nedelia", Integer.parseInt(data.get(day).get(5)));
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                    dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                }
                break;
            case R.id.textChytanneSviatyiaDop:
                if (MainActivity.checkModule_resources(getActivity())) {
                    try {
                        Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.chytanne"));
                        intent.putExtra("cytanne", data.get(day).get(11));
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                    dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                }
                break;
            case R.id.maranata:
                if (MainActivity.checkModule_resources(getActivity())) {
                    try {
                        Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.MaranAta"));
                        intent.putExtra("cytanneMaranaty", data.get(day).get(13));
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                    dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                }
                break;
            case R.id.textTitleChyt:
                String ton = data.get(day).get(20);
                if (MainActivity.checkModule_resources(getActivity())) {
                    try {
                        Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.Ton"));
                        if (ton.contains("Тон 1")) intent.putExtra("ton", 1);
                        if (ton.contains("Тон 2")) intent.putExtra("ton", 2);
                        if (ton.contains("Тон 3")) intent.putExtra("ton", 3);
                        if (ton.contains("Тон 4")) intent.putExtra("ton", 4);
                        if (ton.contains("Тон 5")) intent.putExtra("ton", 5);
                        if (ton.contains("Тон 6")) intent.putExtra("ton", 6);
                        if (ton.contains("Тон 7")) intent.putExtra("ton", 7);
                        if (ton.contains("Тон 8")) intent.putExtra("ton", 8);
                        intent.putExtra("ton_naidzelny", true);
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                    dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                }
                break;
            case R.id.znakTipicona:
                int tipiconNumber = Integer.parseInt(data.get(day).get(12));
                Dialog_tipicon tipicon = Dialog_tipicon.getInstance(tipiconNumber);
                tipicon.show(Objects.requireNonNull(getFragmentManager()), "tipicon");
                break;

        }
    }

    private void SabytieView(String sabytieTitle) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        String title;
        ArrayList<TextView_Roboto_Condensed> sabytieList = new ArrayList<>();
        Collections.sort(MainActivity.padzeia);
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
                if (gc.get(Calendar.DAY_OF_YEAR) - 1 == dayYear && gc.get(Calendar.YEAR) == year) {
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
                    float density = getResources().getDisplayMetrics().density;
                    int realpadding = (int) (5 * density);
                    TextView_Roboto_Condensed textViewT = new TextView_Roboto_Condensed(getActivity());
                    textViewT.setText(title);
                    textViewT.setPadding(realpadding, realpadding, realpadding, realpadding);
                    textViewT.setTypeface(null, Typeface.BOLD);
                    textViewT.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
                    textViewT.setTypeface(null, Typeface.BOLD);
                    textViewT.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorIcons));
                    textViewT.setBackgroundColor(Color.parseColor(sabytie.getColors(getActivity())[p.color]));
                    sabytieList.add(textViewT);
                    TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                    textView.setPadding(realpadding, realpadding, realpadding, realpadding);
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
                    textViewT.setLayoutParams(llp);
                    textView.setVisibility(View.GONE);
                    textViewT.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
                    textViewT.setOnClickListener((v) -> {
                        if (textView.getVisibility() == View.GONE) {
                            textViewT.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
                            textView.setVisibility(View.VISIBLE);
                            LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            llp2.setMargins(0, 0, 0, 0);
                            textViewT.setLayoutParams(llp2);
                            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                        } else {
                            textViewT.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
                            textViewT.setLayoutParams(llp);
                            textView.setVisibility(View.GONE);
                        }
                    });
                    if (title.equals(sabytieTitle)) {
                        textViewT.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
                        textView.setVisibility(View.VISIBLE);
                        LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        llp2.setMargins(0, 0, 0, 0);
                        textViewT.setLayoutParams(llp2);
                        scrollView.post(() -> {
                            getActivity().getIntent().removeExtra("sabytieView");
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            Animation shakeanimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                            textViewT.startAnimation(shakeanimation);
                            textView.startAnimation(shakeanimation);
                        });
                    }
                }
                gc.add(Calendar.DATE, 1);
            }
        }
        for (int i = 0; i < sabytieList.size(); i++) {
            padzei.addView(sabytieList.get(i));
        }
    }
}
