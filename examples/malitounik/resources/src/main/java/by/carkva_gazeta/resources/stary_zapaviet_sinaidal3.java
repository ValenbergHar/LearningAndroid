package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArrayMap;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import by.carkva_gazeta.malitounik.Dialog_Help_Fullscreen;
import by.carkva_gazeta.malitounik.Dialog_bible_natatka;
import by.carkva_gazeta.malitounik.Dialog_brightness;
import by.carkva_gazeta.malitounik.Dialog_font_size;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.MaranAta_Global_List;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.SmartFragmentStatePagerAdapter;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

public class stary_zapaviet_sinaidal3 extends AppCompatActivity implements Dialog_font_size.Dialog_font_size_Listener, Dialog_bible_razdel.Dialog_bible_razdel_Listener, PageFragmentStaryZapavietSinaidal.ClicParalelListiner, PageFragmentStaryZapavietSinaidal.ListPosition, PageFragmentStaryZapavietSinaidal.LongClicListiner {

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            linearLayoutTitle.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = () -> {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    };

    private boolean fullscreenPage = false;
    private boolean trak = false;
    private boolean paralel = false;
    private TextView_Roboto_Condensed bibleInfo;
    private ViewPager vpPager;
    private int fullglav = 0;
    private int kniga;
    private int glava;
    private SharedPreferences k;
    private boolean dzenNoch;
    private boolean dialog = true;
    private TextView_Roboto_Condensed title_toolbar;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;
    private ConstraintLayout linearLayoutTitle;
    private String cytanneSours;
    private String cytanneParalelnye;
    private boolean setedit = false;
    private boolean checkSetDzenNoch = false;
    public static int fierstPosition = 0;

    private int getOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int displayOrientation = getResources().getConfiguration().orientation;

        if (displayOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_180)
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;

            return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        } else if (rotation == Surface.ROTATION_180 || rotation == Surface.ROTATION_90)
            return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;

        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    private void clearEmptyPosition() {
        ArrayList<ArrayList<Integer>> remove = new ArrayList<>();
        for (int i = 0; i < MaranAta_Global_List.getVydelenie().size(); i++) {
            boolean posrem = true;
            for (int e = 1; e < MaranAta_Global_List.getVydelenie().get(i).size(); e++) {
                if (MaranAta_Global_List.getVydelenie().get(i).get(e) == 1) {
                    posrem = false;
                    break;
                }
            }
            if (posrem) {
                remove.add(MaranAta_Global_List.getVydelenie().get(i));
            }
        }
        MaranAta_Global_List.getVydelenie().removeAll(remove);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor prefEditors = k.edit();
        // Формат: Завет(1-Новый, 0-Старый) : Книга : Глава : Стих
        ArrayMap<String, Integer> set = new ArrayMap<>();
        set.put("zavet", 0);
        set.put("kniga", kniga);
        set.put("glava", vpPager.getCurrentItem());
        set.put("stix", fierstPosition);
        Gson gson = new Gson();
        prefEditors.putString("bible_time_sinodal", gson.toJson(set));
        prefEditors.apply();
        clearEmptyPosition();
        File file = new File(getFilesDir() + "/BibliaSinodalStaryZavet/" + kniga + ".json");
        if (MaranAta_Global_List.getVydelenie().size() == 0) {
            if (file.exists()) {
                file.delete();
            }
        } else {
            try {
                FileWriter outputStream = new FileWriter(file);
                outputStream.write(gson.toJson(MaranAta_Global_List.getVydelenie()));
                outputStream.close();
            } catch (IOException ignored) {
            }
        }
        File fileZakladki = new File(getFilesDir() + "/BibliaSinodalZakladki.json");
        if (MaranAta_Global_List.getZakladkiSinodal().size() == 0) {
            if (fileZakladki.exists()) {
                fileZakladki.delete();
            }
        } else {
            try {
                FileWriter outputStream = new FileWriter(fileZakladki);
                outputStream.write(gson.toJson(MaranAta_Global_List.getZakladkiSinodal()));
                outputStream.close();
            } catch (IOException ignored) {
            }
        }
        File fileNatatki = new File(getFilesDir() + "/BibliaSinodalNatatki.json");
        if (MaranAta_Global_List.getNatatkiSinodal().size() == 0) {
            if (fileNatatki.exists()) {
                fileNatatki.delete();
            }
        } else {
            try {
                FileWriter outputStream = new FileWriter(fileNatatki);
                outputStream.write(gson.toJson(MaranAta_Global_List.getNatatkiSinodal()));
                outputStream.close();
            } catch (IOException ignored) {
            }
        }
        //MyBackupAgent.requestBackup(this);
    }

    @Override
    public void onDialogFontSizePositiveClick() {
        Objects.requireNonNull(vpPager.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onComplete(int glava) {
        vpPager.setCurrentItem(glava);
    }

    @Override
    public void getListPosition(int position) {
        fierstPosition = position;
    }

    @Override
    public void onLongClick() {
        if (linearLayout2.getVisibility() == View.VISIBLE) {
            linearLayout2.setVisibility(View.GONE);
            MaranAta_Global_List.setmPedakVisable(false);
        } else {
            linearLayout2.setVisibility(View.VISIBLE);
            MaranAta_Global_List.setmPedakVisable(true);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        if (savedInstanceState != null) {
            checkSetDzenNoch = savedInstanceState.getBoolean("checkSetDzenNoch");
        }
        dzenNoch = k.getBoolean("dzen_noch", false);
        super.onCreate(savedInstanceState);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        setContentView(R.layout.activity_bible);
        kniga = Objects.requireNonNull(getIntent().getExtras()).getInt("kniga", 0);
        glava = getIntent().getExtras().getInt("glava", 0);
        if (getIntent().getExtras().containsKey("stix")) {
            fierstPosition = getIntent().getExtras().getInt("stix", 0);
            trak = true;
        }
        MaranAta_Global_List.setmListGlava(0);

        linearLayoutTitle = findViewById(R.id.linealLayoutTitle);
        linearLayout = findViewById(R.id.conteiner);
        linearLayout2 = findViewById(R.id.linearLayout4);
        if (dzenNoch) {
            linearLayout2.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
        ImageView imageView1 = findViewById(R.id.copy);
        imageView1.setOnClickListener((v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", MainActivity.fromHtml(MaranAta_Global_List.getBible().get(MaranAta_Global_List.getListPosition())).toString());
            Objects.requireNonNull(clipboard).setPrimaryClip(clip);
            LinearLayout layout = new LinearLayout(this);
            SharedPreferences chin = getSharedPreferences("biblia", Context.MODE_PRIVATE);
            boolean dzenNoch = chin.getBoolean("dzen_noch", false);
            if (dzenNoch) layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary_black);
            else layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
            float density = getResources().getDisplayMetrics().density;
            int realpadding = (int) (10 * density);
            TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(this);
            toast.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            toast.setPadding(realpadding, realpadding, realpadding, realpadding);
            toast.setText(by.carkva_gazeta.malitounik.R.string.copy);
            toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            layout.addView(toast);
            Toast mes = new Toast(this);
            mes.setDuration(Toast.LENGTH_LONG);
            mes.setView(layout);
            mes.show();
            linearLayout2.setVisibility(View.GONE);
            MaranAta_Global_List.setmPedakVisable(false);
        }));
        ImageView imageView2 = findViewById(R.id.yelloy);
        imageView2.setOnClickListener((v -> {
            int i = MaranAta_Global_List.checkPosition(MaranAta_Global_List.getmListGlava(), MaranAta_Global_List.getListPosition());
            if (i != -1) {
                if (MaranAta_Global_List.getVydelenie().get(i).get(2) == 0) {
                    MaranAta_Global_List.getVydelenie().get(i).set(2, 1);
                } else {
                    MaranAta_Global_List.getVydelenie().get(i).set(2, 0);
                }
            } else {
                ArrayList<Integer> setVydelenie = new ArrayList<>();
                setVydelenie.add(MaranAta_Global_List.getmListGlava());
                setVydelenie.add(MaranAta_Global_List.getListPosition());
                setVydelenie.add(1);
                setVydelenie.add(0);
                setVydelenie.add(0);
                MaranAta_Global_List.getVydelenie().add(setVydelenie);
            }
            linearLayout2.setVisibility(View.GONE);
            MaranAta_Global_List.setmPedakVisable(false);
        }));
        ImageView imageView3 = findViewById(R.id.underline);
        imageView3.setOnClickListener((v) -> {
            int i = MaranAta_Global_List.checkPosition(MaranAta_Global_List.getmListGlava(), MaranAta_Global_List.getListPosition());
            if (i != -1) {
                if (MaranAta_Global_List.getVydelenie().get(i).get(3) == 0) {
                    MaranAta_Global_List.getVydelenie().get(i).set(3, 1);
                } else {
                    MaranAta_Global_List.getVydelenie().get(i).set(3, 0);
                }
            } else {
                ArrayList<Integer> setVydelenie = new ArrayList<>();
                setVydelenie.add(MaranAta_Global_List.getmListGlava());
                setVydelenie.add(MaranAta_Global_List.getListPosition());
                setVydelenie.add(0);
                setVydelenie.add(1);
                setVydelenie.add(0);
                MaranAta_Global_List.getVydelenie().add(setVydelenie);
            }
            linearLayout2.setVisibility(View.GONE);
            MaranAta_Global_List.setmPedakVisable(false);
        });
        ImageView imageView4 = findViewById(R.id.bold);
        imageView4.setOnClickListener((v) -> {
            int i = MaranAta_Global_List.checkPosition(MaranAta_Global_List.getmListGlava(), MaranAta_Global_List.getListPosition());
            if (i != -1) {
                if (MaranAta_Global_List.getVydelenie().get(i).get(4) == 0) {
                    MaranAta_Global_List.getVydelenie().get(i).set(4, 1);
                } else {
                    MaranAta_Global_List.getVydelenie().get(i).set(4, 0);
                }
            } else {
                ArrayList<Integer> setVydelenie = new ArrayList<>();
                setVydelenie.add(MaranAta_Global_List.getmListGlava());
                setVydelenie.add(MaranAta_Global_List.getListPosition());
                setVydelenie.add(0);
                setVydelenie.add(0);
                setVydelenie.add(1);
                MaranAta_Global_List.getVydelenie().add(setVydelenie);
            }
            linearLayout2.setVisibility(View.GONE);
            MaranAta_Global_List.setmPedakVisable(false);
        });
        ImageView imageView5 = findViewById(R.id.zakladka);
        imageView5.setOnClickListener((v) -> {
            boolean check = false;
            for (int i = 0; i < MaranAta_Global_List.getZakladkiSinodal().size(); i++) {
                if (MaranAta_Global_List.getZakladkiSinodal().get(i).contains(MainActivity.fromHtml(MaranAta_Global_List.getBible().get(MaranAta_Global_List.getListPosition())).toString())) {
                    check = true;
                    break;
                }
            }
            if (!check)
                MaranAta_Global_List.getZakladkiSinodal().add(0, getKnigaBible() + "/" + getResources().getString(by.carkva_gazeta.malitounik.R.string.rsinaidal) + " " + (MaranAta_Global_List.getmListGlava() + 1) + getString(by.carkva_gazeta.malitounik.R.string.stix_ru) + " " + (MaranAta_Global_List.getListPosition() + 1) + "\n\n" + MainActivity.fromHtml(MaranAta_Global_List.getBible().get(MaranAta_Global_List.getListPosition())).toString());
            LinearLayout layout = new LinearLayout(stary_zapaviet_sinaidal3.this);
            boolean dzenNoch = k.getBoolean("dzen_noch", false);
            if (dzenNoch) layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary_black);
            else layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
            float density = getResources().getDisplayMetrics().density;
            int realpadding = (int) (10 * density);
            TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(stary_zapaviet_sinaidal3.this);
            toast.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            toast.setPadding(realpadding, realpadding, realpadding, realpadding);
            if (!check) toast.setText("Дабаўлена у закладкі");
            else toast.setText("Закладка існуе");
            toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            layout.addView(toast);
            Toast mes = new Toast(stary_zapaviet_sinaidal3.this);
            mes.setDuration(Toast.LENGTH_LONG);
            mes.setView(layout);
            mes.show();
            linearLayout2.setVisibility(View.GONE);
            MaranAta_Global_List.setmPedakVisable(false);
            setedit = true;
        });
        ImageView imageView6 = findViewById(R.id.zametka);
        imageView6.setOnClickListener((v) -> {
            String knigaName = getKnigaBible() + "/" + getResources().getString(by.carkva_gazeta.malitounik.R.string.rsinaidal) + " " + (MaranAta_Global_List.getmListGlava() + 1) + getString(by.carkva_gazeta.malitounik.R.string.stix_ru) + " " + (MaranAta_Global_List.getListPosition() + 1);
            Dialog_bible_natatka zametka = Dialog_bible_natatka.getInstance(2,"0", kniga, MaranAta_Global_List.getmListGlava(), MaranAta_Global_List.getListPosition(), knigaName);
            zametka.show(getSupportFragmentManager(), "bible_zametka");
            linearLayout2.setVisibility(View.GONE);
            MaranAta_Global_List.setmPedakVisable(false);
            setedit = true;
        });
        scrollView = findViewById(R.id.scroll);
        vpPager = findViewById(R.id.pager);
        PagerTabStrip vpTabStrip = findViewById(R.id.pagerTabStrip);
        vpTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        for (int i = 0; i < vpTabStrip.getChildCount(); ++i) {
            View nextChild = vpTabStrip.getChildAt(i);
            if (nextChild instanceof TextView) {
                TextView textViewToConvert = (TextView) nextChild;
                textViewToConvert.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
            }
        }
        SmartFragmentStatePagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        bibleInfo = findViewById(R.id.bible);
        bibleInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (dzenNoch) {
                window.setStatusBarColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                window.setNavigationBarColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
            } else {
                window.setStatusBarColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimaryDark));
                window.setNavigationBarColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimaryDark));
            }
        }
        if (dzenNoch) {
            getWindow().setBackgroundDrawableResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
            bibleInfo.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            vpTabStrip.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            bibleInfo.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
            vpTabStrip.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
        }
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                MaranAta_Global_List.setmListGlava(position);
                MaranAta_Global_List.setmPedakVisable(false);
                linearLayout2.setVisibility(View.GONE);
                if (glava != position)
                    fierstPosition = 0;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        switch (kniga) {
            case 0:
                bibleInfo.setText("Бытие");
                fullglav = 50;
                break;
            case 1:
                bibleInfo.setText("Исход");
                fullglav = 40;
                break;
            case 2:
                bibleInfo.setText("Левит");
                fullglav = 27;
                break;
            case 3:
                bibleInfo.setText("Числа");
                fullglav = 36;
                break;
            case 4:
                bibleInfo.setText("Второзаконие");
                fullglav = 34;
                break;
            case 5:
                bibleInfo.setText("Иисуса Навина");
                fullglav = 24;
                break;
            case 6:
                bibleInfo.setText("Судей израилевых");
                fullglav = 21;
                break;
            case 7:
                bibleInfo.setText("Руфи");
                fullglav = 4;
                break;
            case 8:
                bibleInfo.setText("1-я Царств");
                fullglav = 31;
                break;
            case 9:
                bibleInfo.setText("2-я Царств");
                fullglav = 24;
                break;
            case 10:
                bibleInfo.setText("3-я Царств");
                fullglav = 22;
                break;
            case 11:
                bibleInfo.setText("4-я Царств");
                fullglav = 25;
                break;
            case 12:
                bibleInfo.setText("1-я Паралипоменон");
                fullglav = 29;
                break;
            case 13:
                bibleInfo.setText("2-я Паралипоменон");
                fullglav = 37;
                break;
            case 14:
                bibleInfo.setText("1-я Ездры");
                fullglav = 10;
                break;
            case 15:
                bibleInfo.setText("Неемии");
                fullglav = 13;
                break;
            case 16:
                bibleInfo.setText("2-я Ездры");
                fullglav = 9;
                break;
            case 17:
                bibleInfo.setText("Товита");
                fullglav = 14;
                break;
            case 18:
                bibleInfo.setText("Иудифи");
                fullglav = 16;
                break;
            case 19:
                bibleInfo.setText("Есфири");
                fullglav = 10;
                break;
            case 20:
                bibleInfo.setText("Иова");
                fullglav = 42;
                break;
            case 21:
                bibleInfo.setText("Псалтирь");
                fullglav = 151;
                break;
            case 22:
                bibleInfo.setText("Притчи Соломона");
                fullglav = 31;
                break;
            case 23:
                bibleInfo.setText("Екклезиаста");
                fullglav = 12;
                break;
            case 24:
                bibleInfo.setText("Песнь песней Соломона");
                fullglav = 8;
                break;
            case 25:
                bibleInfo.setText("Премудрости Соломона");
                fullglav = 19;
                break;
            case 26:
                bibleInfo.setText("Премудрости Иисуса, сына Сирахова");
                fullglav = 51;
                break;
            case 27:
                bibleInfo.setText("Исаии");
                fullglav = 66;
                break;
            case 28:
                bibleInfo.setText("Иеремии");
                fullglav = 52;
                break;
            case 29:
                bibleInfo.setText("Плач Иеремии");
                fullglav = 5;
                break;
            case 30:
                bibleInfo.setText("Послание Иеремии");
                fullglav = 1;
                break;
            case 31:
                bibleInfo.setText("Варуха");
                fullglav = 5;
                break;
            case 32:
                bibleInfo.setText("Иезекииля");
                fullglav = 48;
                break;
            case 33:
                bibleInfo.setText("Даниила");
                fullglav = 14;
                break;
            case 34:
                bibleInfo.setText("Осии");
                fullglav = 14;
                break;
            case 35:
                bibleInfo.setText("Иоиля");
                fullglav = 3;
                break;
            case 36:
                bibleInfo.setText("Амоса");
                fullglav = 9;
                break;
            case 37:
                bibleInfo.setText("Авдия");
                fullglav = 1;
                break;
            case 38:
                bibleInfo.setText("Ионы");
                fullglav = 4;
                break;
            case 39:
                bibleInfo.setText("Михея");
                fullglav = 7;
                break;
            case 40:
                bibleInfo.setText("Наума");
                fullglav = 3;
                break;
            case 41:
                bibleInfo.setText("Аввакума");
                fullglav = 3;
                break;
            case 42:
                bibleInfo.setText("Сафонии");
                fullglav = 3;
                break;
            case 43:
                bibleInfo.setText("Аггея");
                fullglav = 2;
                break;
            case 44:
                bibleInfo.setText("Захарии");
                fullglav = 14;
                break;
            case 45:
                bibleInfo.setText("Малахии");
                fullglav = 4;
                break;
            case 46:
                bibleInfo.setText("1-я Маккавейская");
                fullglav = 16;
                break;
            case 47:
                bibleInfo.setText("2-я Маккавейская");
                fullglav = 15;
                break;
            case 48:
                bibleInfo.setText("3-я Маккавейская");
                fullglav = 7;
                break;
            case 49:
                bibleInfo.setText("3-я Ездры");
                fullglav = 16;
                break;
        }
        title_toolbar = findViewById(R.id.title_toolbar);

        if (savedInstanceState != null) {
            dialog = savedInstanceState.getBoolean("dialog");
            paralel = savedInstanceState.getBoolean("paralel");
            cytanneSours = savedInstanceState.getString("cytanneSours");
            cytanneParalelnye = savedInstanceState.getString("cytanneParalelnye");
            fullscreenPage = savedInstanceState.getBoolean("fullscreen");
            if (paralel) {
                setOnClic(cytanneParalelnye, cytanneSours);
            }
        }
        vpPager.setCurrentItem(glava);

        if (k.getBoolean("orientation", false)) {
            setRequestedOrientation(getOrientation());
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        File file = new File(getFilesDir() + "/BibliaSinodalStaryZavet/" + kniga + ".json");
        if (file.exists()) {
            try {
                String sb = "";
                FileReader inputStream = new FileReader(file);
                BufferedReader reader = new BufferedReader(inputStream);
                String line;
                while ((line = reader.readLine()) != null) {
                    sb = line;
                }
                inputStream.close();
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<ArrayList<Integer>>>() {
                }.getType();
                MaranAta_Global_List.setVydelenie(gson.fromJson(sb, type));
            } catch (IOException ignored) {
            }
        } else {
            MaranAta_Global_List.setVydelenie(new ArrayList<>());
        }
    }

    private void setTollbarTheme() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView_Roboto_Condensed title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setOnClickListener((v) -> {
            title_toolbar.setHorizontallyScrolling(true);
            title_toolbar.setFreezesText(true);
            title_toolbar.setMarqueeRepeatLimit(-1);
            if (title_toolbar.isSelected()) {
                title_toolbar.setEllipsize(TextUtils.TruncateAt.END);
                title_toolbar.setSelected(false);
            } else {
                title_toolbar.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                title_toolbar.setSelected(true);
            }
        });
        title_toolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN + 4);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        title_toolbar.setText(by.carkva_gazeta.malitounik.R.string.stary_zapaviet);
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
            title_toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
            title_toolbar.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
        }
    }

    private String getKnigaBible() {
        String knigaName = "";
        switch (kniga) {
            case 0:
                knigaName = "Бытие";
                break;
            case 1:
                knigaName = "Исход";
                break;
            case 2:
                knigaName = "Левит";
                break;
            case 3:
                knigaName = "Числа";
                break;
            case 4:
                knigaName = "Второзаконие";
                break;
            case 5:
                knigaName = "Иисуса Навина";
                break;
            case 6:
                knigaName = "Судей израилевых";
                break;
            case 7:
                knigaName = "Руфи";
                break;
            case 8:
                knigaName = "1-я Царств";
                break;
            case 9:
                knigaName = "2-я Царств";
                break;
            case 10:
                knigaName = "3-я Царств";
                break;
            case 11:
                knigaName = "4-я Царств";
                break;
            case 12:
                knigaName = "1-я Паралипоменон";
                break;
            case 13:
                knigaName = "2-я Паралипоменон";
                break;
            case 14:
                knigaName = "1-я Ездры";
                break;
            case 15:
                knigaName = "Неемии";
                break;
            case 16:
                knigaName = "2-я Ездры";
                break;
            case 17:
                knigaName = "Товита";
                break;
            case 18:
                knigaName = "Иудифи";
                break;
            case 19:
                knigaName = "Есфири";
                break;
            case 20:
                knigaName = "Иова";
                break;
            case 21:
                knigaName = "Псалтирь";
                break;
            case 22:
                knigaName = "Притчи Соломона";
                break;
            case 23:
                knigaName = "Екклезиаста";
                break;
            case 24:
                knigaName = "Песнь песней Соломона";
                break;
            case 25:
                knigaName = "Премудрости Соломона";
                break;
            case 26:
                knigaName = "Премудрости Иисуса, сына Сирахова";
                break;
            case 27:
                knigaName = "Исаии";
                break;
            case 28:
                knigaName = "Иеремии";
                break;
            case 29:
                knigaName = "Плач Иеремии";
                break;
            case 30:
                knigaName = "Послание Иеремии";
                break;
            case 31:
                knigaName = "Варуха";
                break;
            case 32:
                knigaName = "Иезекииля";
                break;
            case 33:
                knigaName = "Даниила";
                break;
            case 34:
                knigaName = "Осии";
                break;
            case 35:
                knigaName = "Иоиля";
                break;
            case 36:
                knigaName = "Амоса";
                break;
            case 37:
                knigaName = "Авдия";
                break;
            case 38:
                knigaName = "Ионы";
                break;
            case 39:
                knigaName = "Михея";
                break;
            case 40:
                knigaName = "Наума";
                break;
            case 41:
                knigaName = "Аввакума";
                break;
            case 42:
                knigaName = "Сафонии";
                break;
            case 43:
                knigaName = "Аггея";
                break;
            case 44:
                knigaName = "Захарии";
                break;
            case 45:
                knigaName = "Малахии";
                break;
            case 46:
                knigaName = "1-я Маккавейская";
                break;
            case 47:
                knigaName = "2-я Маккавейская";
                break;
            case 48:
                knigaName = "3-я Маккавейская";
                break;
            case 49:
                knigaName = "3-я Ездры";
                break;
        }
        return knigaName;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("dialog", dialog);
        outState.putBoolean("paralel", paralel);
        outState.putString("cytanneSours", cytanneSours);
        outState.putString("cytanneParalelnye", cytanneParalelnye);
        outState.putBoolean("fullscreen", fullscreenPage);
        outState.putBoolean("checkSetDzenNoch", checkSetDzenNoch);
    }

    @Override
    public void onBackPressed() {
        if (paralel) {
            scrollView.setVisibility(View.GONE);
            bibleInfo.setVisibility(View.VISIBLE);
            vpPager.setVisibility(View.VISIBLE);
            title_toolbar.setText(getResources().getText(by.carkva_gazeta.malitounik.R.string.novy_zapaviet));
            paralel = false;
            supportInvalidateOptionsMenu();
        } else if (fullscreenPage) {
            fullscreenPage = false;
            show();
        } else if (linearLayout2.getVisibility() == View.VISIBLE) {
            MaranAta_Global_List.setmPedakVisable(false);
            linearLayout2.setVisibility(View.GONE);
        } else {
            if (setedit) {
                setResult(500);
                finish();
            } else {
                if (checkSetDzenNoch)
                    onSupportNavigateUp();
                else
                    super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (paralel) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_glava).setVisible(false);
        } else {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_glava).setVisible(true);
        }
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_orientation).setChecked(k.getBoolean("orientation", false));
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_dzen_noch).setChecked(k.getBoolean("dzen_noch", false));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        SharedPreferences.Editor prefEditors = k.edit();

        dzenNoch = k.getBoolean("dzen_noch", false);
        if (id == by.carkva_gazeta.malitounik.R.id.action_dzen_noch) {
            checkSetDzenNoch = true;
            SharedPreferences.Editor prefEditor = k.edit();
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                prefEditor.putBoolean("dzen_noch", true);
            } else {
                prefEditor.putBoolean("dzen_noch", false);
            }
            prefEditor.apply();
            recreate();
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_orientation) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                setRequestedOrientation(getOrientation());
                prefEditors.putBoolean("orientation", true);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                prefEditors.putBoolean("orientation", false);
            }
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_glava) {
            Dialog_bible_razdel dialog_bible_razdel = Dialog_bible_razdel.getInstance(fullglav);
            dialog_bible_razdel.show(getSupportFragmentManager(), "full_glav");
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_font) {
            Dialog_font_size dialog_font_size = new Dialog_font_size();
            dialog_font_size.show(getSupportFragmentManager(), "font");
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_bright) {
            Dialog_brightness dialog_brightness = new Dialog_brightness();
            dialog_brightness.show(getSupportFragmentManager(), "brightness");
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_fullscreen) {
            if (k.getBoolean("FullscreenHelp", true)) {
                Dialog_Help_Fullscreen dialog_help_fullscreen = new Dialog_Help_Fullscreen();
                dialog_help_fullscreen.show(getSupportFragmentManager(), "FullscreenHelp");
            }
            fullscreenPage = true;
            hide();
        }
        prefEditors.apply();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTollbarTheme();
        if (fullscreenPage) hide();
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.biblia, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            int end = spanString.length();
            spanString.setSpan(new AbsoluteSizeSpan(SettingsActivity.GET_FONT_SIZE_MIN, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(spanString);
        }
        return true;
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        linearLayoutTitle.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override
    public void setOnClic(String cytanneParalelnye, String cytanneSours) {
        paralel = true;
        this.cytanneParalelnye = cytanneParalelnye;
        this.cytanneSours = cytanneSours;
        Paralelnye_mesta pm = new Paralelnye_mesta();
        linearLayout.removeAllViewsInLayout();
        ArrayList<TextView_Roboto_Condensed> arrayList = pm.paralel(stary_zapaviet_sinaidal3.this, cytanneSours, cytanneParalelnye, false);
        for (TextView_Roboto_Condensed textView : arrayList) {
            linearLayout.addView(textView);
        }
        scrollView.setVisibility(View.VISIBLE);
        bibleInfo.setVisibility(View.GONE);
        vpPager.setVisibility(View.GONE);
        title_toolbar.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.paralel_smoll, cytanneSours));
        supportInvalidateOptionsMenu();
    }

    class MyPagerAdapter extends SmartFragmentStatePagerAdapter {

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            int fullglav = 1;
            switch (kniga) {
                case 0:
                    fullglav = 50;
                    break;
                case 1:
                    fullglav = 40;
                    break;
                case 2:
                    fullglav = 27;
                    break;
                case 3:
                    fullglav = 36;
                    break;
                case 4:
                    fullglav = 34;
                    break;
                case 5:
                    fullglav = 24;
                    break;
                case 6:
                    fullglav = 21;
                    break;
                case 7:
                    fullglav = 4;
                    break;
                case 8:
                    fullglav = 31;
                    break;
                case 9:
                    fullglav = 24;
                    break;
                case 10:
                    fullglav = 22;
                    break;
                case 11:
                    fullglav = 25;
                    break;
                case 12:
                    fullglav = 29;
                    break;
                case 13:
                    fullglav = 37;
                    break;
                case 14:
                    fullglav = 10;
                    break;
                case 15:
                    fullglav = 13;
                    break;
                case 16:
                    fullglav = 9;
                    break;
                case 17:
                    fullglav = 14;
                    break;
                case 18:
                    fullglav = 16;
                    break;
                case 19:
                    fullglav = 10;
                    break;
                case 20:
                    fullglav = 42;
                    break;
                case 21:
                    fullglav = 151;
                    break;
                case 22:
                    fullglav = 31;
                    break;
                case 23:
                    fullglav = 12;
                    break;
                case 24:
                    fullglav = 8;
                    break;
                case 25:
                    fullglav = 19;
                    break;
                case 26:
                    fullglav = 51;
                    break;
                case 27:
                    fullglav = 66;
                    break;
                case 28:
                    fullglav = 52;
                    break;
                case 29:
                    fullglav = 5;
                    break;
                case 30:
                case 37:
                    break;
                case 31:
                    fullglav = 5;
                    break;
                case 32:
                    fullglav = 48;
                    break;
                case 33:
                    fullglav = 14;
                    break;
                case 34:
                    fullglav = 14;
                    break;
                case 35:
                    fullglav = 3;
                    break;
                case 36:
                    fullglav = 9;
                    break;
                case 38:
                    fullglav = 4;
                    break;
                case 39:
                    fullglav = 7;
                    break;
                case 40:
                    fullglav = 3;
                    break;
                case 41:
                    fullglav = 3;
                    break;
                case 42:
                    fullglav = 3;
                    break;
                case 43:
                    fullglav = 2;
                    break;
                case 44:
                    fullglav = 14;
                    break;
                case 45:
                    fullglav = 4;
                    break;
                case 46:
                    fullglav = 16;
                    break;
                case 47:
                    fullglav = 15;
                    break;
                case 48:
                    fullglav = 7;
                    break;
                case 49:
                    fullglav = 16;
                    break;
            }
            return fullglav;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            for (int i = 0; i < getCount(); i++) {
                if (position == i) {
                    int pazicia;
                    if (trak) {
                        if (glava != i) pazicia = 0;
                        else pazicia = fierstPosition;
                    } else pazicia = 0;
                    return PageFragmentStaryZapavietSinaidal.newInstance(i, kniga, pazicia);
                }
            }
            return PageFragmentStaryZapavietSinaidal.newInstance(0, kniga, 1);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (kniga == 21)
                return getResources().getString(by.carkva_gazeta.malitounik.R.string.psinaidal) + " " + (position + 1);
            else return getResources().getString(by.carkva_gazeta.malitounik.R.string.rsinaidal) + " " + (position + 1);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
