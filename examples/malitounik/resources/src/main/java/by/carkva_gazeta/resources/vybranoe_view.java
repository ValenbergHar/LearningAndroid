package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import by.carkva_gazeta.malitounik.Dialog_Help_Fullscreen;
import by.carkva_gazeta.malitounik.Dialog_brightness;
import by.carkva_gazeta.malitounik.Dialog_font_size;
import by.carkva_gazeta.malitounik.EditText_Roboto_Condensed;
import by.carkva_gazeta.malitounik.InteractiveScrollView;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.Malitvy_paslia_prychascia;
import by.carkva_gazeta.malitounik.Menu_vybranoe;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;
import by.carkva_gazeta.malitounik.Vybranoe_data;
import by.carkva_gazeta.malitounik.WebViewCustom;
import by.carkva_gazeta.malitounik.help_text;

public class vybranoe_view extends AppCompatActivity implements View.OnTouchListener, Dialog_font_size.Dialog_font_size_Listener, WebViewCustom.OnScrollChangedCallback, WebViewCustom.OnBottomListener, InteractiveScrollView.OnScrollChangedCallback, MyWebViewClient.OnLinkListenner {

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            scrollView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
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
    private SharedPreferences k;
    private TextView_Roboto_Condensed textView;
    private WebViewCustom akafist;
    private TextView_Roboto_Condensed prog;
    private int fontBiblia;
    private boolean dzenNoch;
    private boolean autoscroll = false;
    private InteractiveScrollView scrollView;
    private int n;
    private int yS;
    private int spid = 60;
    private static String resurs;
    private Boolean men = true;
    private Timer scrollTimer = null, procentTimer = null, resetTimer = null;
    private TimerTask scrollerSchedule, procentSchedule;
    private GregorianCalendar g;
    private ConstraintLayout constraintLayout;
    private boolean levo = false, pravo = false, niz = false;
    private int positionY = 0;
    private EditText_Roboto_Condensed editText;
    private ImageView imageView;
    private ImageView imageViewUp;
    private TextView_Roboto_Condensed textViewCount;
    private String title;
    private boolean editVybranoe = false;
    private boolean mActionDown;

    public static boolean setVybranoe(@NonNull Context context, String resurs, String title) {
        Gson gson = new Gson();
        File file = new File(context.getFilesDir() + "/Vybranoe.json");
        String sb = "";
        if (file.exists()) {
            try {
                FileReader inputStream = new FileReader(file);
                BufferedReader reader = new BufferedReader(inputStream);
                String line;
                while ((line = reader.readLine()) != null) {
                    sb = line;
                }
                inputStream.close();
            } catch (IOException ignored) {
            }
            Type type = new TypeToken<ArrayList<Vybranoe_data>>() {
            }.getType();
            Menu_vybranoe.vybranoe = gson.fromJson(sb, type);
        } else if (Menu_vybranoe.vybranoe == null) {
            Menu_vybranoe.vybranoe = new ArrayList<>();
        }
        boolean check = true;
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            if (field.getName().intern().equals(resurs)) {
                for (int i = 0; i < Menu_vybranoe.vybranoe.size(); i++) {
                    if (Menu_vybranoe.vybranoe.get(i).resurs.intern().equals(resurs)) {
                        Menu_vybranoe.vybranoe.remove(i);
                        check = false;
                        break;
                    }
                }
                break;
            }
        }
        Field[] fields2 = by.carkva_gazeta.malitounik.R.raw.class.getFields();
        for (Field field : fields2) {
            if (field.getName().intern().equals(resurs)) {
                for (int i = 0; i < Menu_vybranoe.vybranoe.size(); i++) {
                    if (Menu_vybranoe.vybranoe.get(i).resurs.intern().equals(resurs)) {
                        Menu_vybranoe.vybranoe.remove(i);
                        check = false;
                        break;
                    }
                }
                break;
            }
        }

        if (check) {
            Menu_vybranoe.vybranoe.add(new Vybranoe_data(resurs, title));
        }
        try {
            FileWriter outputStream = new FileWriter(file);
            outputStream.write(gson.toJson(Menu_vybranoe.vybranoe));
            outputStream.close();
        } catch (IOException ignored) {
        }
        return check;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean checkVybranoe(@NonNull Context context, String resurs) {
        boolean check = false;
        Gson gson = new Gson();
        File file = new File(context.getFilesDir() + "/Vybranoe.json");
        String sb = "";
        if (file.exists()) {
            try {
                FileReader inputStream = new FileReader(file);
                BufferedReader reader = new BufferedReader(inputStream);
                String line;
                while ((line = reader.readLine()) != null) {
                    sb = line;
                }
                inputStream.close();
                Type type = new TypeToken<ArrayList<Vybranoe_data>>() {
                }.getType();
                Menu_vybranoe.vybranoe = gson.fromJson(sb, type);
            } catch (Exception ignored) {
                file.delete();
                return false;
            }
        } else {
            return false;
        }
        if (Menu_vybranoe.vybranoe == null) {
            Menu_vybranoe.vybranoe = new ArrayList<>();
            return false;
        }
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            if (field.getName().intern().equals(resurs)) {
                for (int i = 0; i < Menu_vybranoe.vybranoe.size(); i++) {
                    if (Menu_vybranoe.vybranoe.get(i).resurs.intern().equals(resurs)) {
                        //Menu_vybranoe.vybranoe.remove(i);
                        check = true;
                        break;
                    }
                }
                break;
            }
        }
        Field[] fields2 = by.carkva_gazeta.malitounik.R.raw.class.getFields();
        for (Field field : fields2) {
            if (field.getName().intern().equals(resurs)) {
                for (int i = 0; i < Menu_vybranoe.vybranoe.size(); i++) {
                    if (Menu_vybranoe.vybranoe.get(i).resurs.intern().equals(resurs)) {
                        //Menu_vybranoe.vybranoe.remove(i);
                        check = true;
                        break;
                    }
                }
                break;
            }
        }
        return check;
    }

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
        if (textView.getVisibility() == View.VISIBLE) {
            textView.setTextSize(fontBiblia);
        } else {
            WebSettings webSettings = akafist.getSettings();
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setAppCacheEnabled(false);
            webSettings.setBlockNetworkImage(true);
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setGeolocationEnabled(false);
            webSettings.setNeedInitialFocus(false);
            webSettings.setDefaultFontSize(fontBiblia);
        }
    }

    @Override
    public void onScroll(int t) {
        positionY = t;
    }

    @Override
    public void onBottom() {
        stopAutoScroll();
        SharedPreferences.Editor prefEditors = k.edit();
        prefEditors.putBoolean("autoscroll", false);
        prefEditors.apply();
        supportInvalidateOptionsMenu();
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "ClickableViewAccessibility"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        dzenNoch = k.getBoolean("dzen_noch", false);
        super.onCreate(savedInstanceState);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        setContentView(R.layout.bogasluzbovya);
        autoscroll = k.getBoolean("autoscroll", false);
        spid = k.getInt("autoscrollSpid", 60);
        akafist = findViewById(R.id.WebView);
        akafist.setOnTouchListener(this);
        akafist.setOnLongClickListener((view) -> scrollTimer != null);
        MyWebViewClient client = new MyWebViewClient();
        client.setOnLinkListenner(this);
        akafist.setWebViewClient(client);
        scrollView = findViewById(R.id.scrollView2);
        scrollView.setOnTouchListener(this);
        textViewCount = findViewById(R.id.textCount);
        imageView = findViewById(R.id.imageView5);
        imageViewUp = findViewById(R.id.imageView6);
        editText = findViewById(R.id.textSearch);
        scrollView.setOnScrollChangedCallback(this);
        scrollView.setOnLongClickListener((view) -> scrollTimer != null);
        constraintLayout = findViewById(R.id.constraint);
        constraintLayout.setOnTouchListener(this);
        prog = findViewById(R.id.progress);
        autoscroll = k.getBoolean("autoscroll", false);
        scrollView.setOnBottomReachedListener(() -> {
                    stopAutoScroll();
                    SharedPreferences.Editor prefEditors = k.edit();
                    prefEditors.putBoolean("autoscroll", false);
                    prefEditors.apply();
                    supportInvalidateOptionsMenu();
                }
        );
        if (savedInstanceState != null) {
            fullscreenPage = savedInstanceState.getBoolean("fullscreen");
            editVybranoe = savedInstanceState.getBoolean("editVybranoe");
            if (savedInstanceState.getBoolean("seach")) {
                editText.setVisibility(View.VISIBLE);
                textViewCount.setVisibility(View.VISIBLE);
                imageViewUp.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }
        }
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        textView = findViewById(R.id.TextView);
        textView.setTextSize(fontBiblia);
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
            textView.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            prog.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        }
        resurs = getIntent().getStringExtra("resurs");
        if (Objects.requireNonNull(resurs).contains("pesny"))
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        title = getIntent().getStringExtra("title");
        WebSettings webSettings = akafist.getSettings();
        webSettings.setStandardFontFamily("sans-serif-condensed");
        webSettings.setDefaultFontSize(fontBiblia);
        webSettings.setJavaScriptEnabled(true);
        //akafist.addJavascriptInterface(new WebAppInterface(this, getSupportFragmentManager()), "Android");

        positionY = (int) (k.getInt(resurs + "Scroll", 0) / getResources().getDisplayMetrics().density);
        akafist.setOnScrollChangedCallback(this);
        akafist.setOnBottomListener(this);

        if (resurs.intern().contains("bogashlugbovya") || resurs.intern().contains("akafist") || resurs.intern().contains("malitvy") || resurs.intern().contains("ruzanec")) {
            textView.setVisibility(View.GONE);
            akafist.setVisibility(View.VISIBLE);
            akafist.loadDataWithBaseURL("malitounikApp-app//carkva-gazeta.by/", loadData(), "text/html", "utf-8", null);
        } else {
            akafist.setVisibility(View.GONE);
            textView.setText(MainActivity.fromHtml(loadData()));
            positionY = k.getInt(resurs + "Scroll", 0);
            scrollView.post(() -> scrollView.scrollBy(0, positionY));
        }

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
        title_toolbar.setText(title);
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    private StringBuilder scrollWebView() {
        StringBuilder script = new StringBuilder();
        script.append("<script language=\"javascript\" type=\"text/javascript\">");
        script.append("\n");
        script.append("    function toY(){");
        script.append("\n");
        script.append("        window.scrollTo(0, ").append(positionY).append(");");
        script.append("\n");
        script.append("    }");
        script.append("\n");
        script.append("</script>");
        script.append("\n");
        return script;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    private String loadData() {
        zmenyiaChastki zmenyiaChastki = new zmenyiaChastki(this);
        GregorianCalendar gregorian = (GregorianCalendar) Calendar.getInstance();
        int day_of_week = gregorian.get(Calendar.DAY_OF_WEEK);
        StringBuilder builder = new StringBuilder();
        try {
            int id = R.raw.bogashlugbovya1;
            Field[] fields = R.raw.class.getFields();
            for (Field field : fields) {
                if (field.getName().intern().equals(resurs)) {
                    id = field.getInt(null);
                    break;
                }
            }
            Field[] fields2 = by.carkva_gazeta.malitounik.R.raw.class.getFields();
            for (Field field : fields2) {
                if (field.getName().intern().equals(resurs)) {
                    id = field.getInt(null);
                    break;
                }
            }
            InputStream inputStream = getResources().openRawResource(id);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line, color;
            if (dzenNoch) color = "<font color=\"#f44336\">";
            else color = "<font color=\"#d00505\">";
            while ((line = reader.readLine()) != null) {
                line = line.replace("<head>", "<head>" + scrollWebView());
                line = line.replace("<body>", "<body onload='toY()'>");
                if (dzenNoch)
                    line = line.replace("#d00505", "#f44336");
                if (dzenNoch)
                    line = line.replace("<html><head>", "<html><head><style type=\"text/css\">::selection {background: #eb9b9a;} body{-webkit-tap-highlight-color: rgba(244,67,54,0.2); color: #fff; background-color: #303030; margin: 0; padding: 0}</style>");
                else
                    line = line.replace("<html><head>", "<html><head><style type=\"text/css\">::selection {background: #eb9b9a;} body{-webkit-tap-highlight-color: rgba(208,5,5,0.1); margin: 0; padding: 0}</style>");
                if (resurs.contains("bogashlugbovya")) {
                    if (line.intern().contains("<KANDAK></KANDAK>")) {
                        line = line.replace("<KANDAK></KANDAK>", "");
                        builder.append(line).append("\n");
                        try {
                            if (day_of_week == 1) {
                                builder.append(zmenyiaChastki.trapary_i_kandaki_niadzelnyia(1));
                            } else {
                                builder.append(zmenyiaChastki.trapary_i_kandaki_na_kogny_dzen(day_of_week, 1));
                            }
                        } catch (Throwable t) {
                            builder.append(this.getResources().getString(by.carkva_gazeta.malitounik.R.string.chteniaErr)).append("<br>\n");
                        }
                    }
                    if (line.intern().contains("<PRAKIMEN></PRAKIMEN>")) {
                        line = line.replace("<PRAKIMEN></PRAKIMEN>", "");
                        builder.append(line).append("\n");
                        try {
                            if (day_of_week == 1) {
                                builder.append(zmenyiaChastki.trapary_i_kandaki_niadzelnyia(2));
                            } else {
                                builder.append(zmenyiaChastki.trapary_i_kandaki_na_kogny_dzen(day_of_week, 2));
                            }
                        } catch (Throwable t) {
                            builder.append(this.getResources().getString(by.carkva_gazeta.malitounik.R.string.chteniaErr)).append("<br>\n");
                        }
                    }
                    if (line.intern().contains("<ALILUIA></ALILUIA>")) {
                        line = line.replace("<ALILUIA></ALILUIA>", "");
                        builder.append(line).append("\n");
                        try {
                            if (day_of_week == 1) {
                                builder.append(zmenyiaChastki.trapary_i_kandaki_niadzelnyia(3));
                            } else {
                                builder.append(zmenyiaChastki.trapary_i_kandaki_na_kogny_dzen(day_of_week, 3));
                            }
                        } catch (Throwable t) {
                            builder.append(this.getResources().getString(by.carkva_gazeta.malitounik.R.string.chteniaErr)).append("<br>\n");
                        }
                    }
                    if (line.intern().contains("<PRICHASNIK></PRICHASNIK>")) {
                        line = line.replace("<PRICHASNIK></PRICHASNIK>", "");
                        builder.append(line).append("\n");
                        try {
                            if (day_of_week == 1) {
                                builder.append(zmenyiaChastki.trapary_i_kandaki_niadzelnyia(4));
                            } else {
                                builder.append(zmenyiaChastki.trapary_i_kandaki_na_kogny_dzen(day_of_week, 4));
                            }
                        } catch (Throwable t) {
                            builder.append(this.getResources().getString(by.carkva_gazeta.malitounik.R.string.chteniaErr)).append("<br>\n");
                        }
                    }
                    switch (line.intern()) {
                        case "<APCH></APCH>": {
                            line = line.replace("<APCH></APCH>", "");
                            String sv = zmenyiaChastki.sviatyia();
                            if (!sv.equals("")) {
                                String[] s1 = sv.split(":");
                                String[] s2 = s1[1].split(";");
                                sv = s1[0] + ":" + s2[0];
                                builder.append("<a href=# onClick=\"javascript:Android.androidAlert('8');\">").append(color).append(sv).append("</font></a>").append("<br><br>\n");
                            } else builder.append(line).append("\n");
                            try {
                                builder.append(zmenyiaChastki.zmenya(1));
                            } catch (Throwable t) {
                                builder.append(this.getResources().getString(by.carkva_gazeta.malitounik.R.string.chteniaErr)).append("<br>\n");
                            }
                            break;
                        }
                        case "<EVCH></EVCH>": {
                            line = line.replace("<EVCH></EVCH>", "");
                            String sv = zmenyiaChastki.sviatyia();
                            if (!sv.equals("")) {
                                String[] s1 = sv.split(":");
                                String[] s2 = s1[1].split(";");
                                sv = s1[0] + ":" + s2[1];
                                builder.append("<a href=# onClick=\"javascript:Android.androidAlert('9');\">").append(color).append(sv).append("</font></a>").append("<br><br>\n");
                            } else builder.append(line).append("\n");
                            try {
                                builder.append(zmenyiaChastki.zmenya(0));
                            } catch (Throwable t) {
                                builder.append(this.getResources().getString(by.carkva_gazeta.malitounik.R.string.chteniaErr)).append("<br>\n");
                            }
                            break;
                        }
                        default:
                            builder.append(line).append("\n");
                            break;
                    }
                } else {
                    builder.append(line).append("\n");
                }
            }
            inputStream.close();
        } catch (Throwable ignored) {
        }
        // API >= 16
        if (akafist.getVisibility() == View.VISIBLE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            akafist.setFindListener((activeMatchOrdinal, numberOfMatches, isDoneCounting) -> {
                if (numberOfMatches == 0)
                    textViewCount.setText(by.carkva_gazeta.malitounik.R.string.niama);
                else
                    textViewCount.setText((activeMatchOrdinal + 1) + " (" + numberOfMatches + ")");
            });
            if (dzenNoch)
                imageViewUp.setImageResource(by.carkva_gazeta.malitounik.R.drawable.up_black);
            imageViewUp.setOnClickListener((v -> akafist.findNext(false)));
            editText.addTextChangedListener(new TextWatcher() {

                private int editPosition;
                private int check = 0;
                private boolean editch = true;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    editch = count != after;
                    check = after;
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    editPosition = start + count;
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String edit = s.toString();
                    if (editch) {
                        edit = edit.replace("и", "і");
                        edit = edit.replace("щ", "ў");
                        edit = edit.replace("ъ", "'");
                        edit = edit.replace("И", "І");
                        edit = edit.replace("Щ", "Ў");
                        edit = edit.replace("Ъ", "'");
                        if (check != 0) {
                            editText.removeTextChangedListener(this);
                            editText.setText(edit);
                            editText.setSelection(editPosition);
                            editText.addTextChangedListener(this);
                        }
                    }
                    akafist.findAllAsync(edit);
                }
            });
            if (dzenNoch)
                imageView.setImageResource(by.carkva_gazeta.malitounik.R.drawable.niz_back);
            imageView.setOnClickListener((v -> akafist.findNext(true)));
        }
        return builder.toString();
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
        if (resetTimer == null) {
            resetTimer = new Timer();
            TimerTask resetSchedule = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
                }
            };
            resetTimer.schedule(resetSchedule, 60000);
        }
    }

    private void startAutoScroll() {
        if (resetTimer != null) {
            resetTimer.cancel();
            resetTimer = null;
        }
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
                            if (akafist.getVisibility() == View.VISIBLE) akafist.scrollBy(0, 2);
                            else scrollView.scrollBy(0, 2);
                        }
                    });
                }
            };
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            scrollTimer.schedule(scrollerSchedule, spid, spid);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onTouch(@NonNull View v, @NonNull MotionEvent event) {
        int heightConstraintLayout = constraintLayout.getHeight();
        int widthConstraintLayout = constraintLayout.getWidth();
        int otstup = (int) (10 * getResources().getDisplayMetrics().density);
        int y = (int) event.getY();
        int x = (int) event.getX();
        SharedPreferences.Editor prefEditor;
        prefEditor = k.edit();
        if (v.getId() == R.id.WebView || v.getId() == by.carkva_gazeta.malitounik.R.id.scrollView2) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mActionDown = true;
                    break;
                case MotionEvent.ACTION_UP:
                    mActionDown = false;
                    break;
            }
            return false;
        }
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
                            WebSettings webSettings = akafist.getSettings();
                            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                            webSettings.setAppCacheEnabled(false);
                            webSettings.setBlockNetworkImage(true);
                            webSettings.setLoadsImagesAutomatically(true);
                            webSettings.setGeolocationEnabled(false);
                            webSettings.setNeedInitialFocus(false);
                            webSettings.setDefaultFontSize(fontBiblia);
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
                            WebSettings webSettings = akafist.getSettings();
                            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                            webSettings.setAppCacheEnabled(false);
                            webSettings.setBlockNetworkImage(true);
                            webSettings.setLoadsImagesAutomatically(true);
                            webSettings.setGeolocationEnabled(false);
                            webSettings.setNeedInitialFocus(false);
                            webSettings.setDefaultFontSize(fontBiblia);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        autoscroll = k.getBoolean("autoscroll", false);

        if (resurs.contains("bogashlugbovya") || resurs.intern().contains("akafist") || resurs.intern().contains("malitvy") || resurs.intern().contains("ruzanec")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_find).setVisible(true);
        }
        if (autoscroll) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_plus).setVisible(true);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_minus).setVisible(true);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setTitle(getResources().getString(by.carkva_gazeta.malitounik.R.string.autoScrolloff));
        } else {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_plus).setVisible(false);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_minus).setVisible(false);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setTitle(getResources().getString(by.carkva_gazeta.malitounik.R.string.autoScrollon));
        }
        if (men) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_vybranoe).setIcon(ContextCompat.getDrawable(this, by.carkva_gazeta.malitounik.R.drawable.star_big_on));
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_vybranoe).setTitle(getResources().getString(by.carkva_gazeta.malitounik.R.string.vybranoe_del));
        } else {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_vybranoe).setIcon(ContextCompat.getDrawable(this, by.carkva_gazeta.malitounik.R.drawable.star_big_off));
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_vybranoe).setTitle(getResources().getString(by.carkva_gazeta.malitounik.R.string.vybranoe));
        }
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_orientation).setChecked(k.getBoolean("orientation", false));
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_dzen_noch).setChecked(k.getBoolean("dzen_noch", false));
        MenuItem item = menu.findItem(by.carkva_gazeta.malitounik.R.id.action_vybranoe);
        SpannableString spanString = new SpannableString(menu.findItem(by.carkva_gazeta.malitounik.R.id.action_vybranoe).getTitle().toString());
        int end = spanString.length();
        spanString.setSpan(new AbsoluteSizeSpan(SettingsActivity.GET_FONT_SIZE_MIN, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        item.setTitle(spanString);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.akafist, menu);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        SharedPreferences.Editor prefEditor;
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        prefEditor = k.edit();
        int id = item.getItemId();

        if (id == by.carkva_gazeta.malitounik.R.id.action_dzen_noch) {
            editVybranoe = true;
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                prefEditor.putBoolean("dzen_noch", true);
            } else {
                prefEditor.putBoolean("dzen_noch", false);
            }
            prefEditor.apply();
            recreate();
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_find) {
            editText.setVisibility(View.VISIBLE);
            textViewCount.setVisibility(View.VISIBLE);
            imageViewUp.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
        if (id == by.carkva_gazeta.malitounik.R.id.action_vybranoe) {
            editVybranoe = true;
            men = vybranoe_view.setVybranoe(this, resurs, title);
            if (men) {
                LinearLayout layout = new LinearLayout(this);
                layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
                float density = getResources().getDisplayMetrics().density;
                int realpadding = (int) (10 * density);
                TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(this);
                toast.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                toast.setText(getString(by.carkva_gazeta.malitounik.R.string.addVybranoe));
                toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                layout.addView(toast);
                Toast mes = new Toast(this);
                mes.setDuration(Toast.LENGTH_SHORT);
                mes.setView(layout);
                mes.show();
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

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor prefEditor = k.edit();
        prefEditor.putInt(resurs + "Scroll", positionY);
        prefEditor.apply();
        if (fullscreenPage) {
            fullscreenPage = false;
            show();
        } else if (editText.getVisibility() == View.VISIBLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                editText.setVisibility(View.GONE);
                textViewCount.setVisibility(View.GONE);
                imageViewUp.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                akafist.findAllAsync("");
                editText.setText("");
                textViewCount.setText(by.carkva_gazeta.malitounik.R.string.niama);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        } else {
            if (editVybranoe)
                onSupportNavigateUp();
            else
                super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor prefEditor = k.edit();
        prefEditor.putInt(resurs + "Scroll", positionY);
        prefEditor.apply();
        stopAutoScroll();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        scrollerSchedule = null;
        scrollTimer = null;
        if (resetTimer != null) {
            resetTimer.cancel();
            resetTimer = null;
        }
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

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        scrollView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fullscreen", fullscreenPage);
        outState.putBoolean("editVybranoe", editVybranoe);
        if (editText.getVisibility() == View.VISIBLE)
            outState.putBoolean("seach", true);
        else
            outState.putBoolean("seach", false);
    }

    @Override
    public void onActivityStart() {
        Intent intent = new Intent(this, Malitvy_paslia_prychascia.class);
        startActivity(intent);
        positionY = 0;
    }

    @Override
    public void onDialogStart(String message) {
        Dialog_liturgia dialog_liturgia = Dialog_liturgia.getInstance(message);
        dialog_liturgia.show(getSupportFragmentManager(), "dialog_liturgia");
    }
}
