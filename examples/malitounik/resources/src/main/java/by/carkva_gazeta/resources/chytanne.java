package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import by.carkva_gazeta.malitounik.Dialog_Help_Fullscreen;
import by.carkva_gazeta.malitounik.Dialog_brightness;
import by.carkva_gazeta.malitounik.Dialog_font_size;
import by.carkva_gazeta.malitounik.InteractiveScrollView;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;
import by.carkva_gazeta.malitounik.help_text;

/**
 * Created by oleg on 25.5.16
 */

public class chytanne extends AppCompatActivity implements View.OnTouchListener, Dialog_font_size.Dialog_font_size_Listener {

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            linearLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
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
    private TextView_Roboto_Condensed prog;
    private SharedPreferences k;
    private int fontBiblia;
    private boolean dzenNoch;
    private boolean autoscroll = false;
    private int n;
    private int yS;
    private int spid = 60;
    private Timer scrollTimer = null, procentTimer = null;
    private TimerTask scrollerSchedule, procentSchedule;
    private GregorianCalendar g;
    private InputStream inputStream;
    private ConstraintLayout constraintLayout;
    private boolean levo = false, pravo = false, niz = false;
    private boolean mActionDown;
    private boolean change = false;
    private ArrayList<TextView_Roboto_Condensed> cytannelist;
    private LinearLayout linearLayout;
    private InteractiveScrollView scrollView;
    private int nedelia = -1;
    private int toTwoList = 0;

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

    @Override
    public void onDialogFontSizePositiveClick() {
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        for (TextView_Roboto_Condensed text : cytannelist) {
            text.setTextSize(fontBiblia);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akafist_chytanne);
        if (savedInstanceState != null) {
            fullscreenPage = savedInstanceState.getBoolean("fullscreen");
            change = savedInstanceState.getBoolean("change");
        }
        cytannelist = new ArrayList<>();
        nedelia = Objects.requireNonNull(getIntent().getExtras()).getInt("nedelia", -1);
        linearLayout = findViewById(R.id.LinearButtom);
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        constraintLayout = findViewById(R.id.constraint);
        constraintLayout.setOnTouchListener(this);
        scrollView = findViewById(R.id.InteractiveScroll);
        scrollView.setOnBottomReachedListener(() -> {
            autoscroll = false;
            stopAutoScroll();
            SharedPreferences.Editor prefEditors = k.edit();
            prefEditors.putBoolean("autoscroll", false);
            prefEditors.apply();
            supportInvalidateOptionsMenu();
        });
        scrollView.setOnNestedTouchListener(action -> mActionDown = action);
        prog = findViewById(R.id.progress);
        if (dzenNoch)
            prog.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        autoscroll = k.getBoolean("autoscroll", false);
        spid = k.getInt("autoscrollSpid", 60);
        autoscroll = k.getBoolean("autoscroll", false);
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
        setChtenia(savedInstanceState);

        if (k.getBoolean("help_str", true)) {
            startActivity(new Intent(this, help_text.class));
            SharedPreferences.Editor prefEditor;
            prefEditor = k.edit();
            prefEditor.putBoolean("help_str", false);
            prefEditor.apply();
        }

        if (k.getBoolean("orientation", false)) {
            setRequestedOrientation(getOrientation());
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
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
        title_toolbar.setText(getResources().getText(by.carkva_gazeta.malitounik.R.string.CZYTANNE));
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int heightConstraintLayout = constraintLayout.getHeight();
        int widthConstraintLayout = constraintLayout.getWidth();
        int otstup = (int) (10 * getResources().getDisplayMetrics().density);
        int y = (int) event.getY();
        int x = (int) event.getX();
        SharedPreferences.Editor prefEditor;
        prefEditor = k.edit();
        if (v.getId() == R.id.constraint) {
            if (MainActivity.checkBrightness) {
                try {
                    MainActivity.brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS) * 100 / 255;
                } catch (Settings.SettingNotFoundException ignored) {
                }
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    n = (int) event.getY();
                    yS = (int) event.getX();
                    int proc;
                    if (x < otstup) {
                        levo = true;
                        prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                        prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, MainActivity.brightness));
                        prog.setVisibility(View.VISIBLE);
                    }
                    if (x > widthConstraintLayout - otstup) {
                        pravo = true;
                        String minmax = "";
                        if (fontBiblia == SettingsActivity.GET_FONT_SIZE_MIN)
                            minmax = " (мін)";
                        if (fontBiblia == SettingsActivity.GET_FONT_SIZE_MAX)
                            minmax = " (макс)";
                        prog.setText(fontBiblia + " sp" + minmax);
                        prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                        prog.setVisibility(View.VISIBLE);
                    }
                    if (y > heightConstraintLayout - otstup) {
                        niz = true;
                        spid = k.getInt("autoscrollSpid", 60);
                        proc = 100 - (spid - 15) * 100 / 215;
                        prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                        prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, proc));
                        prog.setVisibility(View.VISIBLE);
                        startProcent();
                        autoscroll = k.getBoolean("autoscroll", false);
                        if (!autoscroll) {
                            startAutoScroll();
                            prefEditor.putBoolean("autoscroll", true);
                            prefEditor.apply();
                            supportInvalidateOptionsMenu();
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (x < otstup && y > n && y % 15 == 0) {
                        if (MainActivity.brightness > 0) {
                            MainActivity.brightness = MainActivity.brightness - 1;
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.screenBrightness = (float) MainActivity.brightness / 100;
                            getWindow().setAttributes(lp);
                            prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, MainActivity.brightness));
                            MainActivity.checkBrightness = false;
                        }
                    }
                    if (x < otstup && y < n && y % 15 == 0) {
                        if (MainActivity.brightness < 100) {
                            MainActivity.brightness = MainActivity.brightness + 1;
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.screenBrightness = (float) MainActivity.brightness / 100;
                            getWindow().setAttributes(lp);
                            prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, MainActivity.brightness));
                            MainActivity.checkBrightness = false;
                        }
                    }
                    if (x > widthConstraintLayout - otstup && y > n && y % 26 == 0) {
                        if (fontBiblia > SettingsActivity.GET_FONT_SIZE_MIN) {
                            fontBiblia = fontBiblia - 4;
                            prefEditor.putInt("font_malitounik", fontBiblia);
                            prefEditor.apply();
                            for (TextView_Roboto_Condensed text : cytannelist) {
                                text.setTextSize(fontBiblia);
                            }
                            String min = "";
                            if (fontBiblia == SettingsActivity.GET_FONT_SIZE_MIN)
                                min = " (мін)";
                            prog.setText(fontBiblia + " sp" + min);
                            prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                        }
                    }
                    if (x > widthConstraintLayout - otstup && y < n && y % 26 == 0) {
                        if (fontBiblia < SettingsActivity.GET_FONT_SIZE_MAX) {
                            fontBiblia = fontBiblia + 4;
                            prefEditor.putInt("font_malitounik", fontBiblia);
                            prefEditor.apply();
                            for (TextView_Roboto_Condensed text : cytannelist) {
                                text.setTextSize(fontBiblia);
                            }
                            String max = "";
                            if (fontBiblia == SettingsActivity.GET_FONT_SIZE_MAX)
                                max = " (макс)";
                            prog.setText(fontBiblia + " sp" + max);
                            prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                        }
                    }
                    if (y > heightConstraintLayout - otstup && x > yS && x % 25 == 0) {
                        if (spid <= 235 && spid >= 20) {
                            spid = spid - 5;
                            proc = 100 - (spid - 15) * 100 / 215;
                            prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                            prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, proc));
                            prog.setVisibility(View.VISIBLE);
                            startProcent();
                            stopAutoScroll();
                            startAutoScroll();
                        }
                    }
                    if (y > heightConstraintLayout - otstup && x < yS && x % 25 == 0) {
                        if (spid <= 225 && spid >= 10) {
                            spid = spid + 5;
                            proc = 100 - (spid - 15) * 100 / 215;
                            prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                            prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, proc));
                            prog.setVisibility(View.VISIBLE);
                            startProcent();
                            stopAutoScroll();
                            startAutoScroll();
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP: // отпускание
                    v.performClick();
                case MotionEvent.ACTION_CANCEL:
                    if (levo) {
                        levo = false;
                        prog.setVisibility(View.GONE);
                    }
                    if (pravo) {
                        pravo = false;
                        prog.setVisibility(View.GONE);
                    }
                    if (niz) {
                        niz = false;
                        prefEditor.putInt("autoscrollSpid", spid);
                        prefEditor.apply();
                    }
                    break;
            }
        }
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    private void setChtenia(Bundle savedInstanceState) {
        try {
            fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
            String w = getIntent().getExtras().getString("cytanne");
            w = MainActivity.removeZnakiAndSlovy(w);

            String[] split = w.split(";");
            //String[] split = {"Гал 1.1-10, 20-2.5"};
            int knigaN, knigaK = 0, zaglnum = 0;
            // Мц 1.1-10, 20-2.5, 10-20, 1.21-2.4, 11;
            for (int i = 0; i < split.length; i++) {
                String[] zaglavie = split[i].split(",");
                String zagl = "";
                String zaglavieName = "";
                for (int e = 0; e < zaglavie.length; e++) {
                    String zaglav = zaglavie[e].trim();
                    int zag = zaglav.indexOf(" ", 2);
                    int zag1 = zaglav.indexOf(".");
                    int zag2 = zaglav.indexOf("-");
                    int zag3 = zaglav.indexOf(".", zag1 + 1);
                    String zagS;
                    boolean supadzenneNachKonc = false;
                    if (zag2 != -1) {
                        zagS = zaglav.substring(0, zag2);
                        if (zag3 != -1) {
                            int glava1 = Integer.valueOf(zaglav.substring(zag1 + 1, zag2));
                            int glava2 = Integer.valueOf(zaglav.substring(zag3 + 1));
                            if (glava1 == glava2) supadzenneNachKonc = true;
                        }
                    } else {
                        zagS = zaglav;
                    }
                    boolean glav = false;
                    if (zag1 > zag2 && zag == -1) {
                        glav = true;
                    } else if (zag != -1) {
                        zagl = zaglav.substring(0, zag); // Название книги
                        String zaglavieName1 = split[i].trim();
                        zaglavieName = " " + zaglavieName1.substring(zag + 1);
                        zaglnum = Integer.parseInt(zaglav.substring(zag + 1, zag1)); // Номер главы
                    } else if (zag1 != -1) {
                        zaglnum = Integer.parseInt(zaglav.substring(0, zag1)); // Номер главы
                    }
                    if (glav) {
                        int zagS1 = zagS.indexOf(".");
                        if (zagS1 == -1) {
                            knigaN = Integer.parseInt(zagS); // Начало чтения
                        } else {
                            zaglnum = Integer.parseInt(zagS.substring(0, zagS1)); // Номер главы
                            knigaN = Integer.parseInt(zagS.substring(zagS1 + 1)); // Начало чтения
                        }
                    } else if (zag2 == -1) {
                        // Конец чтения
                        if (zag != -1) {
                            int zagS1 = zagS.indexOf(".");
                            zaglnum = Integer.parseInt(zagS.substring(zag + 1, zagS1)); // Номер главы
                            knigaN = Integer.parseInt(zagS.substring(zagS1 + 1)); // Начало чтения
                        } else {
                            knigaN = Integer.parseInt(zaglav); // Начало чтения
                        }
                        knigaK = knigaN; // Конец чтения
                    } else {
                        knigaN = Integer.parseInt(zaglav.substring(zag1 + 1, zag2)); // Начало чтения
                    }
                    if (glav) {
                        knigaK = Integer.parseInt(zaglav.substring(zag1 + 1)); // Конец чтения
                    } else if (zag2 != -1) {
                        if (zag3 == -1) {
                            knigaK = Integer.parseInt(zaglav.substring(zag2 + 1)); // Конец чтения
                        } else {
                            knigaK = Integer.parseInt(zaglav.substring(zag3 + 1)); // Конец чтения
                        }
                    }
                    String spln = "";
                    if (i > 0) spln = "\n";
                    int kniga = 0;
                    if (zagl.equals("Мц")) kniga = 0;
                    if (zagl.equals("Мк")) kniga = 1;
                    if (zagl.equals("Лк")) kniga = 2;
                    if (zagl.equals("Ян")) kniga = 3;
                    if (zagl.equals("Дз")) kniga = 4;
                    if (zagl.equals("Як")) kniga = 5;
                    if (zagl.equals("1 Пт")) kniga = 6;
                    if (zagl.equals("2 Пт")) kniga = 7;
                    if (zagl.equals("1 Ян")) kniga = 8;
                    if (zagl.equals("2 Ян")) kniga = 9;
                    if (zagl.equals("3 Ян")) kniga = 10;
                    if (zagl.equals("Юд")) kniga = 11;
                    if (zagl.equals("Рым")) kniga = 12;
                    if (zagl.equals("1 Кар")) kniga = 13;
                    if (zagl.equals("2 Кар")) kniga = 14;
                    if (zagl.equals("Гал")) kniga = 15;
                    if (zagl.equals("Эф")) kniga = 16;
                    if (zagl.equals("Флп")) kniga = 17;
                    if (zagl.equals("Клс")) kniga = 18;
                    if (zagl.equals("1 Фес")) kniga = 19;
                    if (zagl.equals("2 Фес")) kniga = 20;
                    if (zagl.equals("1 Цім")) kniga = 21;
                    if (zagl.equals("2 Цім")) kniga = 22;
                    if (zagl.equals("Ціт")) kniga = 23;
                    if (zagl.equals("Піл")) kniga = 24;
                    if (zagl.equals("Гбр")) kniga = 25;
                    if (zagl.equals("Быц")) kniga = 26;
                    if (zagl.equals("Высл")) kniga = 27;
                    if (zagl.equals("Езк")) kniga = 28;
                    if (zagl.equals("Вых")) kniga = 29;
                    if (zagl.equals("Ёў")) kniga = 30;
                    if (zagl.equals("Зах")) kniga = 31;
                    if (zagl.equals("Ёіл")) kniga = 32;
                    if (zagl.equals("Саф")) kniga = 33;
                    if (zagl.equals("Іс")) kniga = 34;
                    if (zagl.equals("Ер")) kniga = 35;
                    if (zagl.equals("Дан")) kniga = 36;
                    if (zagl.equals("Лікі")) kniga = 37;
                    if (zagl.equals("Міх")) kniga = 38;
                    //Быц 1.1-13; Лікі 24.2-3, 5-9, 17-18; Міх 4.6-7, 5.1-4; Іс 11.1-10; Ярэм 3.35-4.4; Дан 2.31-36, 44-45; Іс 9.5-6, 7.10-16, 8.1-4, 9-10
                    Resources r = chytanne.this.getResources();
                    SpannableStringBuilder ssbTitle = null;
                    switch (kniga) {
                        case 0:
                            inputStream = r.openRawResource(R.raw.biblian1);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_0, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 1:
                            inputStream = r.openRawResource(R.raw.biblian2);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_1, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 2:
                            inputStream = r.openRawResource(R.raw.biblian3);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_2, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 3:
                            inputStream = r.openRawResource(R.raw.biblian4);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_3, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 4:
                            inputStream = r.openRawResource(R.raw.biblian5);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_4, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 5:
                            inputStream = r.openRawResource(R.raw.biblian6);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_5, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 6:
                            inputStream = r.openRawResource(R.raw.biblian7);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_6, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 7:
                            inputStream = r.openRawResource(R.raw.biblian8);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_7, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 8:
                            inputStream = r.openRawResource(R.raw.biblian9);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_8, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 9:
                            inputStream = r.openRawResource(R.raw.biblian10);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_9, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 10:
                            inputStream = r.openRawResource(R.raw.biblian11);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_10, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 11:
                            inputStream = r.openRawResource(R.raw.biblian12);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_11, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 12:
                            inputStream = r.openRawResource(R.raw.biblian13);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_12, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 13:
                            inputStream = r.openRawResource(R.raw.biblian14);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_13, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 14:
                            inputStream = r.openRawResource(R.raw.biblian15);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_14, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 15:
                            inputStream = r.openRawResource(R.raw.biblian16);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_15, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 16:
                            inputStream = r.openRawResource(R.raw.biblian17);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_16, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 17:
                            inputStream = r.openRawResource(R.raw.biblian18);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_17, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 18:
                            inputStream = r.openRawResource(R.raw.biblian19);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_18, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 19:
                            inputStream = r.openRawResource(R.raw.biblian20);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_19, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 20:
                            inputStream = r.openRawResource(R.raw.biblian21);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_20, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 21:
                            inputStream = r.openRawResource(R.raw.biblian22);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_21, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 22:
                            inputStream = r.openRawResource(R.raw.biblian23);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_22, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 23:
                            inputStream = r.openRawResource(R.raw.biblian24);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_23, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 24:
                            inputStream = r.openRawResource(R.raw.biblian25);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_24, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 25:
                            inputStream = r.openRawResource(R.raw.biblian26);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_25, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 26:
                            inputStream = r.openRawResource(R.raw.biblias1);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_26, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 27:
                            inputStream = r.openRawResource(R.raw.biblias20);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_27, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 28:
                            inputStream = r.openRawResource(R.raw.biblias26);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_28, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 29:
                            inputStream = r.openRawResource(R.raw.biblias2);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_29, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 30:
                            inputStream = r.openRawResource(R.raw.biblias18);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_30, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 31:
                            inputStream = r.openRawResource(R.raw.biblias38);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_31, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 32:
                            inputStream = r.openRawResource(R.raw.biblias29);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_32, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 33:
                            inputStream = r.openRawResource(R.raw.biblias36);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_33, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 34:
                            inputStream = r.openRawResource(R.raw.biblias23);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_34, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 35:
                            inputStream = r.openRawResource(R.raw.biblias24);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_35, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 36:
                            inputStream = r.openRawResource(R.raw.biblias27);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_36, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 37:
                            inputStream = r.openRawResource(R.raw.biblias4);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_37, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                        case 38:
                            inputStream = r.openRawResource(R.raw.biblias33);
                            if (e == 0) {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_38, spln, zaglavieName));
                            } else {
                                ssbTitle = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.chtinia_zag, spln.trim()));
                            }
                            break;
                    }
                    if (e == 0)
                        ssbTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, ssbTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    TextView_Roboto_Condensed textView1 = new TextView_Roboto_Condensed(this);
                    textView1.setFocusable(false);
                    textView1.setText(ssbTitle);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                    if (dzenNoch)
                        textView1.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                    else
                        textView1.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                    textView1.setPadding(0, 10, 0, 0);
                    linearLayout.addView(textView1);
                    cytannelist.add(textView1);
                    try {
                        if (inputStream != null) {
                            InputStreamReader isr = new InputStreamReader(inputStream);
                            BufferedReader reader = new BufferedReader(isr);
                            String line;
                            StringBuilder builder = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                if (line.contains("//")) {
                                    int t1 = line.indexOf("//");
                                    line = line.substring(0, t1).trim();
                                    if (!line.equals(""))
                                        builder.append(line).append("\n");
                                    continue;
                                }
                                builder.append(line).append("\n");
                            }
                            inputStream.close();
                            String[] split2 = builder.toString().split("===");
                            String spl;
                            int desK1, desN;
                            spl = split2[zaglnum].trim();
                            desN = spl.indexOf(knigaN + ".");

                            if (knigaN == knigaK && !supadzenneNachKonc) {
                                desK1 = desN;
                            } else {
                                desK1 = spl.indexOf(knigaK + ".");
                                if (zag3 != -1 || glav) {
                                    String spl1 = split2[zaglnum].trim();
                                    String spl2 = split2[zaglnum + 1].trim();
                                    int des1 = spl1.length();
                                    desN = spl1.indexOf(knigaN + ".");
                                    desK1 = spl2.indexOf(knigaK + ".");
                                    int desN1 = spl2.indexOf(knigaK + 1 + ".", desK1);
                                    if (desN1 == -1) {
                                        desN1 = spl1.length();
                                    }
                                    desK1 = desN1 + des1;
                                    spl = spl1 + "\n" + spl2;
                                    zaglnum = zaglnum + 1;
                                }
                            }
                            int desK = spl.indexOf("\n", desK1);
                            if (desK == -1) {
                                TextView_Roboto_Condensed textView2 = new TextView_Roboto_Condensed(this);
                                textView2.setFocusable(false);
                                textView2.setText(new SpannableStringBuilder(spl.substring(desN)));
                                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                                if (dzenNoch)
                                    textView2.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                                else
                                    textView2.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                                textView2.setPadding(0, 10, 0, 0);
                                cytannelist.add(textView2);
                                linearLayout.addView(textView2);
                            } else {
                                TextView_Roboto_Condensed textView2 = new TextView_Roboto_Condensed(this);
                                textView2.setFocusable(false);
                                textView2.setText(new SpannableStringBuilder(spl.substring(desN, desK)));
                                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                                if (dzenNoch)
                                    textView2.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                                else
                                    textView2.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                                textView2.setPadding(0, 10, 0, 0);
                                cytannelist.add(textView2);
                                linearLayout.addView(textView2);
                            }
                        }
                    } catch (Throwable ignored) {
                    }
                }
                if (i == 0) toTwoList = cytannelist.size();
            }
            if (k.getBoolean("utran", true) && (nedelia == 1 || nedelia == 2 || nedelia == 3) && split.length > 2 && savedInstanceState == null) {
                scrollView.postDelayed(() -> {
                    float y = linearLayout.getY() + linearLayout.getChildAt(toTwoList).getY();
                    scrollView.smoothScrollTo(0, (int) y);
                }, 700);
            }
        } catch (Throwable ignored) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(getResources().getString(by.carkva_gazeta.malitounik.R.string.error_ch));
            ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, getResources().getString(by.carkva_gazeta.malitounik.R.string.error_ch).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            TextView_Roboto_Condensed textView2 = new TextView_Roboto_Condensed(this);
            textView2.setFocusable(false);
            textView2.setText(ssb);
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
            if (dzenNoch)
                textView2.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            else
                textView2.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
            textView2.setPadding(0, 10, 0, 0);
            cytannelist.add(textView2);
            linearLayout.addView(textView2);
        }
    }

    private void stopProcent() {
        if (procentTimer != null) {
            procentTimer.cancel();
            procentTimer = null;
        }
        procentSchedule = null;
    }

    private void startProcent() {
        g = (GregorianCalendar) Calendar.getInstance();
        if (procentTimer == null) {
            procentTimer = new Timer();
            if (procentSchedule != null) {
                procentSchedule.cancel();
                procentSchedule = null;
            }
            procentSchedule = new TimerTask() {
                @Override
                public void run() {
                    GregorianCalendar g2 = (GregorianCalendar) Calendar.getInstance();
                    if (g.getTimeInMillis() + 1000 <= g2.getTimeInMillis()) {
                        runOnUiThread(() -> {
                            prog.setVisibility(View.GONE);
                            stopProcent();
                        });
                    }
                }
            };
            procentTimer.schedule(procentSchedule, 20, 20);
        }
    }

    private void stopAutoScroll() {
        if (scrollTimer != null) {
            scrollTimer.cancel();
            scrollTimer = null;
        }
        scrollerSchedule = null;
    }

    private void startAutoScroll() {
        if (scrollTimer == null) {
            scrollTimer = new Timer();
            if (scrollerSchedule != null) {
                scrollerSchedule.cancel();
                scrollerSchedule = null;
            }
            scrollerSchedule = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        if (!mActionDown && !MainActivity.dialogVisable) {
                            scrollView.smoothScrollBy(0, 2);
                        }
                    });
                }
            };
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            scrollTimer.schedule(scrollerSchedule, spid, spid);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.chtenia, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            int end = spanString.length();
            spanString.setSpan(new AbsoluteSizeSpan(SettingsActivity.GET_FONT_SIZE_MIN, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(spanString);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        autoscroll = k.getBoolean("autoscroll", false);
        if (autoscroll) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_plus).setVisible(true);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_minus).setVisible(true);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setTitle(getResources().getString(by.carkva_gazeta.malitounik.R.string.autoScrolloff));
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_fullscreen).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_plus).setVisible(false);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_minus).setVisible(false);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setTitle(getResources().getString(by.carkva_gazeta.malitounik.R.string.autoScrollon));
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_fullscreen).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_orientation).setChecked(k.getBoolean("orientation", false));
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_dzen_noch).setChecked(k.getBoolean("dzen_noch", false));
        if (nedelia != -1) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_utran).setChecked(k.getBoolean("utran", true));
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_utran).setVisible(true);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (fullscreenPage) {
            fullscreenPage = false;
            show();
        } else {
            if (change) {
                onSupportNavigateUp();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        scrollerSchedule = null;
        scrollTimer = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTollbarTheme();
        if (fullscreenPage) hide();
        autoscroll = k.getBoolean("autoscroll", false);
        spid = k.getInt("autoscrollSpid", 60);
        if (autoscroll) {
            startAutoScroll();
        }
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        SharedPreferences.Editor prefEditor;
        dzenNoch = k.getBoolean("dzen_noch", false);
        prefEditor = k.edit();
        if (id == by.carkva_gazeta.malitounik.R.id.action_dzen_noch) {
            change = true;
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                prefEditor.putBoolean("dzen_noch", true);
            } else {
                prefEditor.putBoolean("dzen_noch", false);
            }
            prefEditor.apply();
            recreate();
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_utran) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                prefEditor.putBoolean("utran", true);
            } else {
                prefEditor.putBoolean("utran", false);
            }
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_orientation) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                setRequestedOrientation(getOrientation());
                prefEditor.putBoolean("orientation", true);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                prefEditor.putBoolean("orientation", false);
            }
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_plus) {
            if (spid <= 235 && spid >= 20) {
                spid = spid - 5;
                int proc = 100 - (spid - 15) * 100 / 215;
                prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, proc));
                prog.setVisibility(View.VISIBLE);
                startProcent();
                stopAutoScroll();
                startAutoScroll();
                SharedPreferences.Editor prefEditors = k.edit();
                prefEditors.putInt("autoscrollSpid", spid);
                prefEditors.apply();
            }
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_minus) {
            if (spid <= 225 && spid >= 10) {
                spid = spid + 5;
                int proc = 100 - (spid - 15) * 100 / 215;
                prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, proc));
                prog.setVisibility(View.VISIBLE);
                startProcent();
                stopAutoScroll();
                startAutoScroll();
                SharedPreferences.Editor prefEditors = k.edit();
                prefEditors.putInt("autoscrollSpid", spid);
                prefEditors.apply();
            }
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_auto) {
            autoscroll = k.getBoolean("autoscroll", false);
            if (autoscroll) {
                stopAutoScroll();
                prefEditor.putBoolean("autoscroll", false);
            } else {
                startAutoScroll();
                prefEditor.putBoolean("autoscroll", true);
            }
            supportInvalidateOptionsMenu();
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
        prefEditor.apply();
        return super.onOptionsItemSelected(item);
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
        linearLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fullscreen", fullscreenPage);
        outState.putBoolean("change", change);
    }
}
