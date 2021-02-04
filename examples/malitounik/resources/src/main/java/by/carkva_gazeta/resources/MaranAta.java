package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.MaranAta_Global_List;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;
import by.carkva_gazeta.malitounik.help_text;

/**
 * Created by oleg on 18.10.16
 */

@SuppressWarnings("ConstantConditions")
public class MaranAta extends AppCompatActivity implements View.OnTouchListener, Dialog_font_size.Dialog_font_size_Listener, ListView.OnItemClickListener, ListView.OnItemLongClickListener {

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            listView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
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
    private boolean change = false;
    private InputStream inputStream;
    private TextView_Roboto_Condensed prog;
    private String cytanne;
    private SharedPreferences k;
    private int fontBiblia;
    private boolean dzenNoch;
    private boolean autoscroll = false;
    private ListView listView;
    private maran_ata_ListAdaprer adapter;
    private final ArrayList<String> maranAta = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> Vydelenie;
    private int n;
    private int yS;
    private int spid = 60;
    private boolean belarus = false;
    private Timer scrollTimer = null, procentTimer = null, resetTimer = null;
    private TimerTask scrollerSchedule, procentSchedule;
    private GregorianCalendar g;
    private ConstraintLayout constraintLayout;
    private boolean levo = false, pravo = false, niz = false;
    private boolean mActionDown;
    private LinearLayout linearLayout2;
    private boolean addBold_Underline = false;
    private boolean setFont = false;
    private ScrollView scrollView;
    private LinearLayout conteiner;
    private boolean paralel = false;
    private boolean onsave = false;
    private int paralelPosition;
    private TextView_Roboto_Condensed title_toolbar;
    private String tollBarText = "";
    private int mPosition;
    private int mOffset;

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
        setFont = true;
        adapter.notifyDataSetChanged();
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
                        forceScroll();
                        if (!mActionDown && !MainActivity.dialogVisable) {
                            int firstPosition = listView.getFirstVisiblePosition();
                            if (firstPosition == ListView.INVALID_POSITION) {
                                return;
                            }
                            View firstView = listView.getChildAt(0);
                            if (firstView == null) {
                                return;
                            }
                            int newTop = firstView.getTop() - 2;
                            listView.setSelectionFromTop(firstPosition, newTop);
                        }
                    });
                }
            };
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            scrollTimer.schedule(scrollerSchedule, spid, spid);
        }
    }

    private void forceScroll() {
        MotionEvent event = MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(), MotionEvent.ACTION_MOVE, listView.getX(), -1, 0);
        onTouch(listView, event);
    }

    private int checkPosition(int position) {
        for (int i = 0; i < Vydelenie.size(); i++) {
            if (Vydelenie.get(i).get(0) == position) {
                return i;
            }
        }
        return -1;
    }

    private void clearEmptyPosition() {
        ArrayList<ArrayList<Integer>> remove = new ArrayList<>();
        for (int i = 0; i < Vydelenie.size(); i++) {
            boolean posrem = true;
            for (int e = 1; e < Vydelenie.get(i).size(); e++) {
                if (Vydelenie.get(i).get(e) == 1) {
                    posrem = false;
                    break;
                }
            }
            if (posrem) {
                remove.add(Vydelenie.get(i));
            }
        }
        Vydelenie.removeAll(remove);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        belarus = k.getBoolean("belarus", false);
        super.onCreate(savedInstanceState);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        setContentView(R.layout.akafist_maran_ata);
        setTollbarTheme();
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        autoscroll = k.getBoolean("autoscroll", false);
        spid = k.getInt("autoscrollSpid", 60);

        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        scrollView = findViewById(R.id.scroll);
        conteiner = findViewById(R.id.conteiner);
        listView = findViewById(R.id.ListView);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        listView.setOnTouchListener(this);
        adapter = new maran_ata_ListAdaprer(this);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        cytanne = Objects.requireNonNull(getIntent().getExtras()).getString("cytanneMaranaty");
        setMaranata(Objects.requireNonNull(cytanne));
        if (savedInstanceState != null) {
            onsave = true;
            fullscreenPage = savedInstanceState.getBoolean("fullscreen");
            change = savedInstanceState.getBoolean("change");
            tollBarText = savedInstanceState.getString("tollBarText");
            title_toolbar.setText(getString(by.carkva_gazeta.malitounik.R.string.maranata_tollbar, tollBarText));
            paralel = savedInstanceState.getBoolean("paralel", paralel);
            if (paralel) {
                paralelPosition = savedInstanceState.getInt("paralelPosition");
                ParralelMestaView(paralelPosition);
            }
        }
        listView.post(() -> listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView list, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (list.getAdapter() == null || list.getChildAt(0) == null)
                    return;
                if (list.getLastVisiblePosition() == list.getAdapter().getCount() - 1 && list.getChildAt(list.getChildCount() - 1).getBottom() <= list.getHeight()) {
                    autoscroll = false;
                    stopAutoScroll();
                    SharedPreferences.Editor prefEditors = k.edit();
                    prefEditors.putBoolean("autoscroll", false);
                    prefEditors.apply();
                    supportInvalidateOptionsMenu();
                }
                setFont = false;
                int position = list.getFirstVisiblePosition();
                int offset = list.getChildAt(0).getTop();
                if (mPosition < position) {
                    mOffset = 0;
                }
                int scroll;
                if (mPosition == position && mOffset == offset) {
                    // прокрутка стоит
                    scroll = 0;
                } else if (mPosition > position && mOffset > offset) {
                    // прокрутка идет вверх
                    scroll = 1; //-1;
                } else if (mPosition == position && mOffset < offset) {
                    // прокрутка идет вверх
                    scroll = 1;//-1;
                } else {
                    // прокрутка идет вниз
                    scroll = 1;
                }
                if (!onsave) {
                    String nazva = "";
                    /*if (scroll == -1) {
                        nazva = maranAta.get(list.getLastVisiblePosition() - 5);
                    }*/
                    if (scroll == 1) {
                        if (list.getLastVisiblePosition() - 5 >= 0)
                            nazva = maranAta.get(list.getLastVisiblePosition() - 5);
                        else
                            nazva = maranAta.get(list.getLastVisiblePosition());
                    }
                    String oldtollBarText = title_toolbar.getText().toString();
                    if (oldtollBarText.equals("")) {
                        nazva = maranAta.get(list.getFirstVisiblePosition() + 2);
                        if (nazva.contains("nazva+++")) {
                            int t1 = nazva.indexOf("nazva+++");
                            int t2 = nazva.indexOf("-->", t1 + 8);
                            tollBarText = nazva.substring(t1 + 8, t2);
                            title_toolbar.setText(getString(by.carkva_gazeta.malitounik.R.string.maranata_tollbar, tollBarText));
                        }
                    }
                    if (!nazva.contains(tollBarText) && scroll != 0) {
                        if (nazva.contains("nazva+++")) {
                            int t1 = nazva.indexOf("nazva+++");
                            int t2 = nazva.indexOf("-->", t1 + 8);
                            tollBarText = nazva.substring(t1 + 8, t2);
                            title_toolbar.setText(getString(by.carkva_gazeta.malitounik.R.string.maranata_tollbar, tollBarText));
                        }
                    }
                    mPosition = position;
                    mOffset = offset;
                }
                onsave = false;
            }
        }));

        constraintLayout = findViewById(R.id.constraint);
        constraintLayout.setOnTouchListener(this);
        prog = findViewById(R.id.progress);
        if (dzenNoch)
            prog.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        linearLayout2 = findViewById(R.id.linearLayout2);
        ImageView imageView1 = findViewById(R.id.textView1);
        ImageView imageView2 = findViewById(R.id.textView2);
        ImageView imageView3 = findViewById(R.id.textView3);
        ImageView imageView4 = findViewById(R.id.textView4);
        imageView1.setOnClickListener((v) -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", MainActivity.fromHtml(maranAta.get(MaranAta_Global_List.getListPosition())).toString());
            Objects.requireNonNull(clipboard).setPrimaryClip(clip);
            linearLayout2.setVisibility(View.GONE);
            LinearLayout layout = new LinearLayout(this);
            if (dzenNoch)
                layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary_black);
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
            mes.setDuration(Toast.LENGTH_SHORT);
            mes.setView(layout);
            mes.show();
            supportInvalidateOptionsMenu();
        });
        imageView2.setOnClickListener((v) -> {
            int i = checkPosition(MaranAta_Global_List.getListPosition());
            if (i != -1) {
                if (Vydelenie.get(i).get(2) == 0) {
                    Vydelenie.get(i).set(2, 1);
                } else {
                    Vydelenie.get(i).set(2, 0);
                }
            } else {
                ArrayList<Integer> setVydelenie = new ArrayList<>();
                setVydelenie.add(MaranAta_Global_List.getListPosition());
                setVydelenie.add(0);
                setVydelenie.add(1);
                setVydelenie.add(0);
                Vydelenie.add(setVydelenie);
            }
            linearLayout2.setVisibility(View.GONE);
            addBold_Underline = true;
            supportInvalidateOptionsMenu();
        });
        imageView3.setOnClickListener((v) -> {
            int i = checkPosition(MaranAta_Global_List.getListPosition());
            if (i != -1) {
                if (Vydelenie.get(i).get(3) == 0) {
                    Vydelenie.get(i).set(3, 1);
                } else {
                    Vydelenie.get(i).set(3, 0);
                }
            } else {
                ArrayList<Integer> setVydelenie = new ArrayList<>();
                setVydelenie.add(MaranAta_Global_List.getListPosition());
                setVydelenie.add(0);
                setVydelenie.add(0);
                setVydelenie.add(1);
                Vydelenie.add(setVydelenie);
            }
            linearLayout2.setVisibility(View.GONE);
            addBold_Underline = true;
            supportInvalidateOptionsMenu();
        });
        imageView4.setOnClickListener((v) -> {
            int i = checkPosition(MaranAta_Global_List.getListPosition());
            if (i != -1) {
                if (Vydelenie.get(i).get(1) == 0) {
                    Vydelenie.get(i).set(1, 1);
                } else {
                    Vydelenie.get(i).set(1, 0);
                }
            } else {
                ArrayList<Integer> setVydelenie = new ArrayList<>();
                setVydelenie.add(MaranAta_Global_List.getListPosition());
                setVydelenie.add(1);
                setVydelenie.add(0);
                setVydelenie.add(0);
                Vydelenie.add(setVydelenie);
            }
            linearLayout2.setVisibility(View.GONE);
            addBold_Underline = true;
            supportInvalidateOptionsMenu();
        });
        if (dzenNoch) {
            linearLayout2.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
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
        File file;
        if (belarus)
            file = new File(getFilesDir() + "/MaranAtaBel/" + cytanne + ".json");
        else
            file = new File(getFilesDir() + "/MaranAta/" + cytanne + ".json");
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
                Vydelenie = gson.fromJson(sb, type);
            } catch (IOException ignored) {
            }
        } else {
            Vydelenie = new ArrayList<>();
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
        title_toolbar = findViewById(R.id.title_toolbar);
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
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (linearLayout2.getVisibility() == View.VISIBLE) {
            return false;
        }
        int heightConstraintLayout = constraintLayout.getHeight();
        int widthConstraintLayout = constraintLayout.getWidth();
        int otstup = (int) (10 * getResources().getDisplayMetrics().density);
        int y = (int) event.getY();
        int x = (int) event.getX();
        SharedPreferences.Editor prefEditor;
        prefEditor = k.edit();
        if (v.getId() == R.id.ListView) {
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
            linearLayout2.setVisibility(View.GONE);
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
                            prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                            String min = "";
                            if (fontBiblia == SettingsActivity.GET_FONT_SIZE_MIN)
                                min = " (мін)";
                            prog.setText(fontBiblia + " sp" + min);
                            prefEditor.putInt("font_malitounik", fontBiblia);
                            prefEditor.apply();
                            setFont = true;
                            adapter.notifyDataSetChanged();
                        }
                    }
                    if (x > widthConstraintLayout - otstup && y < n && y % 26 == 0) {
                        if (fontBiblia < SettingsActivity.GET_FONT_SIZE_MAX) {
                            fontBiblia = fontBiblia + 4;
                            prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                            String max = "";
                            if (fontBiblia == SettingsActivity.GET_FONT_SIZE_MAX)
                                max = " (макс)";
                            prog.setText(fontBiblia + " sp" + max);
                            prefEditor.putInt("font_malitounik", fontBiblia);
                            prefEditor.apply();
                            setFont = true;
                            adapter.notifyDataSetChanged();
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
                        /*prefEditor.putInt("font_malitounik", fontBiblia);
                        setFont = true;
                        adapter.notifyDataSetChanged();
                        prefEditor.apply();*/
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

    @SuppressLint("SetTextI18n")
    private void setMaranata(@NonNull String cytanne) {
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        String[] chten = cytanne.split(";");
        for (int i = 0; i < chten.length; i++) {
            String fit = chten[i].trim();
            try {
                int nachalo, konec, stixn = -1, stixk = -1;
                String[] bible = Paralelnye_mesta.biblia(fit);
                Paralelnye_mesta.kniga = bible[0];
                Paralelnye_mesta.nazva = bible[1];
                Paralelnye_mesta.nazvaBel = bible[2];
                Paralelnye_mesta.nomer = Integer.parseInt(bible[3]);
                //int s1 = kniga.length();
                int s2 = fit.lastIndexOf(" ");
                int s5 = -1;
                if (s2 == -1) {
                    nachalo = 1;
                    konec = 1;
                } else {
                    int s3 = fit.indexOf(".", s2 + 1);
                    if (s3 != -1) {
                        int s4 = fit.indexOf("-");
                        s5 = fit.indexOf(".", s3 + 1);
                        nachalo = Integer.parseInt(fit.substring(s2 + 1, s3));
                        stixn = Integer.parseInt(fit.substring(s3 + 1, s4));
                        if (s5 != -1) {
                            konec = Integer.parseInt(fit.substring(s4 + 1, s5));
                            stixk = Integer.parseInt(fit.substring(s5 + 1));
                        } else {
                            konec = nachalo;
                            stixk = Integer.parseInt(fit.substring(s4 + 1));
                        }
                    } else {
                        int s4 = fit.indexOf("-", s2 + 1);
                        if (s4 != -1) {
                            nachalo = Integer.parseInt(fit.substring(s2 + 1, s4));
                            konec = Integer.parseInt(fit.substring(s4 + 1));
                        } else {
                            nachalo = Integer.parseInt(fit.substring(s2 + 1));
                            konec = nachalo;
                        }
                    }
                }
                Resources r = MaranAta.this.getResources();
                if (belarus) {
                    if (Paralelnye_mesta.nomer == 1) inputStream = r.openRawResource(R.raw.biblias1);
                    if (Paralelnye_mesta.nomer == 2) inputStream = r.openRawResource(R.raw.biblias2);
                    if (Paralelnye_mesta.nomer == 3) inputStream = r.openRawResource(R.raw.biblias3);
                    if (Paralelnye_mesta.nomer == 4) inputStream = r.openRawResource(R.raw.biblias4);
                    if (Paralelnye_mesta.nomer == 5) inputStream = r.openRawResource(R.raw.biblias5);
                    if (Paralelnye_mesta.nomer == 6) inputStream = r.openRawResource(R.raw.biblias6);
                    if (Paralelnye_mesta.nomer == 7) inputStream = r.openRawResource(R.raw.biblias7);
                    if (Paralelnye_mesta.nomer == 8) inputStream = r.openRawResource(R.raw.biblias8);
                    if (Paralelnye_mesta.nomer == 9) inputStream = r.openRawResource(R.raw.biblias9);
                    if (Paralelnye_mesta.nomer == 10) inputStream = r.openRawResource(R.raw.biblias10);
                    if (Paralelnye_mesta.nomer == 11) inputStream = r.openRawResource(R.raw.biblias11);
                    if (Paralelnye_mesta.nomer == 12) inputStream = r.openRawResource(R.raw.biblias12);
                    if (Paralelnye_mesta.nomer == 13) inputStream = r.openRawResource(R.raw.biblias13);
                    if (Paralelnye_mesta.nomer == 14) inputStream = r.openRawResource(R.raw.biblias14);
                    if (Paralelnye_mesta.nomer == 15) inputStream = r.openRawResource(R.raw.biblias15);
                    if (Paralelnye_mesta.nomer == 16) inputStream = r.openRawResource(R.raw.biblias16);
                    if (Paralelnye_mesta.nomer == 20) inputStream = r.openRawResource(R.raw.biblias17);
                    if (Paralelnye_mesta.nomer == 21) inputStream = r.openRawResource(R.raw.biblias18);
                    if (Paralelnye_mesta.nomer == 22) inputStream = r.openRawResource(R.raw.biblias19);
                    if (Paralelnye_mesta.nomer == 23) inputStream = r.openRawResource(R.raw.biblias20);
                    if (Paralelnye_mesta.nomer == 24) inputStream = r.openRawResource(R.raw.biblias21);
                    if (Paralelnye_mesta.nomer == 25) inputStream = r.openRawResource(R.raw.biblias22);
                    if (Paralelnye_mesta.nomer == 28) inputStream = r.openRawResource(R.raw.biblias23);
                    if (Paralelnye_mesta.nomer == 29) inputStream = r.openRawResource(R.raw.biblias24);
                    if (Paralelnye_mesta.nomer == 30) inputStream = r.openRawResource(R.raw.biblias25);
                    if (Paralelnye_mesta.nomer == 33) inputStream = r.openRawResource(R.raw.biblias26);
                    if (Paralelnye_mesta.nomer == 34) inputStream = r.openRawResource(R.raw.biblias27);
                    if (Paralelnye_mesta.nomer == 35) inputStream = r.openRawResource(R.raw.biblias28);
                    if (Paralelnye_mesta.nomer == 36) inputStream = r.openRawResource(R.raw.biblias29);
                    if (Paralelnye_mesta.nomer == 37) inputStream = r.openRawResource(R.raw.biblias30);
                    if (Paralelnye_mesta.nomer == 38) inputStream = r.openRawResource(R.raw.biblias31);
                    if (Paralelnye_mesta.nomer == 39) inputStream = r.openRawResource(R.raw.biblias32);
                    if (Paralelnye_mesta.nomer == 40) inputStream = r.openRawResource(R.raw.biblias33);
                    if (Paralelnye_mesta.nomer == 41) inputStream = r.openRawResource(R.raw.biblias34);
                    if (Paralelnye_mesta.nomer == 42) inputStream = r.openRawResource(R.raw.biblias35);
                    if (Paralelnye_mesta.nomer == 43) inputStream = r.openRawResource(R.raw.biblias36);
                    if (Paralelnye_mesta.nomer == 44) inputStream = r.openRawResource(R.raw.biblias37);
                    if (Paralelnye_mesta.nomer == 45) inputStream = r.openRawResource(R.raw.biblias38);
                    if (Paralelnye_mesta.nomer == 46) inputStream = r.openRawResource(R.raw.biblias39);
                    if (Paralelnye_mesta.nomer == 51) inputStream = r.openRawResource(R.raw.biblian1);
                    if (Paralelnye_mesta.nomer == 52) inputStream = r.openRawResource(R.raw.biblian2);
                    if (Paralelnye_mesta.nomer == 53) inputStream = r.openRawResource(R.raw.biblian3);
                    if (Paralelnye_mesta.nomer == 54) inputStream = r.openRawResource(R.raw.biblian4);
                    if (Paralelnye_mesta.nomer == 55) inputStream = r.openRawResource(R.raw.biblian5);
                    if (Paralelnye_mesta.nomer == 56) inputStream = r.openRawResource(R.raw.biblian6);
                    if (Paralelnye_mesta.nomer == 57) inputStream = r.openRawResource(R.raw.biblian7);
                    if (Paralelnye_mesta.nomer == 58) inputStream = r.openRawResource(R.raw.biblian8);
                    if (Paralelnye_mesta.nomer == 59) inputStream = r.openRawResource(R.raw.biblian9);
                    if (Paralelnye_mesta.nomer == 60) inputStream = r.openRawResource(R.raw.biblian10);
                    if (Paralelnye_mesta.nomer == 61) inputStream = r.openRawResource(R.raw.biblian11);
                    if (Paralelnye_mesta.nomer == 62) inputStream = r.openRawResource(R.raw.biblian12);
                    if (Paralelnye_mesta.nomer == 63) inputStream = r.openRawResource(R.raw.biblian13);
                    if (Paralelnye_mesta.nomer == 64) inputStream = r.openRawResource(R.raw.biblian14);
                    if (Paralelnye_mesta.nomer == 65) inputStream = r.openRawResource(R.raw.biblian15);
                    if (Paralelnye_mesta.nomer == 66) inputStream = r.openRawResource(R.raw.biblian16);
                    if (Paralelnye_mesta.nomer == 67) inputStream = r.openRawResource(R.raw.biblian17);
                    if (Paralelnye_mesta.nomer == 68) inputStream = r.openRawResource(R.raw.biblian18);
                    if (Paralelnye_mesta.nomer == 69) inputStream = r.openRawResource(R.raw.biblian19);
                    if (Paralelnye_mesta.nomer == 70) inputStream = r.openRawResource(R.raw.biblian20);
                    if (Paralelnye_mesta.nomer == 71) inputStream = r.openRawResource(R.raw.biblian21);
                    if (Paralelnye_mesta.nomer == 72) inputStream = r.openRawResource(R.raw.biblian22);
                    if (Paralelnye_mesta.nomer == 73) inputStream = r.openRawResource(R.raw.biblian23);
                    if (Paralelnye_mesta.nomer == 74) inputStream = r.openRawResource(R.raw.biblian24);
                    if (Paralelnye_mesta.nomer == 75) inputStream = r.openRawResource(R.raw.biblian25);
                    if (Paralelnye_mesta.nomer == 76) inputStream = r.openRawResource(R.raw.biblian26);
                    if (Paralelnye_mesta.nomer == 77) inputStream = r.openRawResource(R.raw.biblian27);
                } else {
                    if (Paralelnye_mesta.nomer == 1) inputStream = r.openRawResource(R.raw.sinaidals1);
                    if (Paralelnye_mesta.nomer == 2) inputStream = r.openRawResource(R.raw.sinaidals2);
                    if (Paralelnye_mesta.nomer == 3) inputStream = r.openRawResource(R.raw.sinaidals3);
                    if (Paralelnye_mesta.nomer == 4) inputStream = r.openRawResource(R.raw.sinaidals4);
                    if (Paralelnye_mesta.nomer == 5) inputStream = r.openRawResource(R.raw.sinaidals5);
                    if (Paralelnye_mesta.nomer == 6) inputStream = r.openRawResource(R.raw.sinaidals6);
                    if (Paralelnye_mesta.nomer == 7) inputStream = r.openRawResource(R.raw.sinaidals7);
                    if (Paralelnye_mesta.nomer == 8) inputStream = r.openRawResource(R.raw.sinaidals8);
                    if (Paralelnye_mesta.nomer == 9) inputStream = r.openRawResource(R.raw.sinaidals9);
                    if (Paralelnye_mesta.nomer == 10) inputStream = r.openRawResource(R.raw.sinaidals10);
                    if (Paralelnye_mesta.nomer == 11) inputStream = r.openRawResource(R.raw.sinaidals11);
                    if (Paralelnye_mesta.nomer == 12) inputStream = r.openRawResource(R.raw.sinaidals12);
                    if (Paralelnye_mesta.nomer == 13) inputStream = r.openRawResource(R.raw.sinaidals13);
                    if (Paralelnye_mesta.nomer == 14) inputStream = r.openRawResource(R.raw.sinaidals14);
                    if (Paralelnye_mesta.nomer == 15) inputStream = r.openRawResource(R.raw.sinaidals15);
                    if (Paralelnye_mesta.nomer == 16) inputStream = r.openRawResource(R.raw.sinaidals16);
                    if (Paralelnye_mesta.nomer == 17) inputStream = r.openRawResource(R.raw.sinaidals17);
                    if (Paralelnye_mesta.nomer == 18) inputStream = r.openRawResource(R.raw.sinaidals18);
                    if (Paralelnye_mesta.nomer == 19) inputStream = r.openRawResource(R.raw.sinaidals19);
                    if (Paralelnye_mesta.nomer == 20) inputStream = r.openRawResource(R.raw.sinaidals20);
                    if (Paralelnye_mesta.nomer == 21) inputStream = r.openRawResource(R.raw.sinaidals21);
                    if (Paralelnye_mesta.nomer == 22) inputStream = r.openRawResource(R.raw.sinaidals22);
                    if (Paralelnye_mesta.nomer == 23) inputStream = r.openRawResource(R.raw.sinaidals23);
                    if (Paralelnye_mesta.nomer == 24) inputStream = r.openRawResource(R.raw.sinaidals24);
                    if (Paralelnye_mesta.nomer == 25) inputStream = r.openRawResource(R.raw.sinaidals25);
                    if (Paralelnye_mesta.nomer == 26) inputStream = r.openRawResource(R.raw.sinaidals26);
                    if (Paralelnye_mesta.nomer == 27) inputStream = r.openRawResource(R.raw.sinaidals27);
                    if (Paralelnye_mesta.nomer == 28) inputStream = r.openRawResource(R.raw.sinaidals28);
                    if (Paralelnye_mesta.nomer == 29) inputStream = r.openRawResource(R.raw.sinaidals29);
                    if (Paralelnye_mesta.nomer == 30) inputStream = r.openRawResource(R.raw.sinaidals30);
                    if (Paralelnye_mesta.nomer == 31) inputStream = r.openRawResource(R.raw.sinaidals31);
                    if (Paralelnye_mesta.nomer == 32) inputStream = r.openRawResource(R.raw.sinaidals32);
                    if (Paralelnye_mesta.nomer == 33) inputStream = r.openRawResource(R.raw.sinaidals33);
                    if (Paralelnye_mesta.nomer == 34) inputStream = r.openRawResource(R.raw.sinaidals34);
                    if (Paralelnye_mesta.nomer == 35) inputStream = r.openRawResource(R.raw.sinaidals35);
                    if (Paralelnye_mesta.nomer == 36) inputStream = r.openRawResource(R.raw.sinaidals36);
                    if (Paralelnye_mesta.nomer == 37) inputStream = r.openRawResource(R.raw.sinaidals37);
                    if (Paralelnye_mesta.nomer == 38) inputStream = r.openRawResource(R.raw.sinaidals38);
                    if (Paralelnye_mesta.nomer == 39) inputStream = r.openRawResource(R.raw.sinaidals39);
                    if (Paralelnye_mesta.nomer == 40) inputStream = r.openRawResource(R.raw.sinaidals40);
                    if (Paralelnye_mesta.nomer == 41) inputStream = r.openRawResource(R.raw.sinaidals41);
                    if (Paralelnye_mesta.nomer == 42) inputStream = r.openRawResource(R.raw.sinaidals42);
                    if (Paralelnye_mesta.nomer == 43) inputStream = r.openRawResource(R.raw.sinaidals43);
                    if (Paralelnye_mesta.nomer == 44) inputStream = r.openRawResource(R.raw.sinaidals44);
                    if (Paralelnye_mesta.nomer == 45) inputStream = r.openRawResource(R.raw.sinaidals45);
                    if (Paralelnye_mesta.nomer == 46) inputStream = r.openRawResource(R.raw.sinaidals46);
                    if (Paralelnye_mesta.nomer == 47) inputStream = r.openRawResource(R.raw.sinaidals47);
                    if (Paralelnye_mesta.nomer == 48) inputStream = r.openRawResource(R.raw.sinaidals48);
                    if (Paralelnye_mesta.nomer == 49) inputStream = r.openRawResource(R.raw.sinaidals49);
                    if (Paralelnye_mesta.nomer == 50) inputStream = r.openRawResource(R.raw.sinaidals50);
                    if (Paralelnye_mesta.nomer == 51) inputStream = r.openRawResource(R.raw.sinaidaln1);
                    if (Paralelnye_mesta.nomer == 52) inputStream = r.openRawResource(R.raw.sinaidaln2);
                    if (Paralelnye_mesta.nomer == 53) inputStream = r.openRawResource(R.raw.sinaidaln3);
                    if (Paralelnye_mesta.nomer == 54) inputStream = r.openRawResource(R.raw.sinaidaln4);
                    if (Paralelnye_mesta.nomer == 55) inputStream = r.openRawResource(R.raw.sinaidaln5);
                    if (Paralelnye_mesta.nomer == 56) inputStream = r.openRawResource(R.raw.sinaidaln6);
                    if (Paralelnye_mesta.nomer == 57) inputStream = r.openRawResource(R.raw.sinaidaln7);
                    if (Paralelnye_mesta.nomer == 58) inputStream = r.openRawResource(R.raw.sinaidaln8);
                    if (Paralelnye_mesta.nomer == 59) inputStream = r.openRawResource(R.raw.sinaidaln9);
                    if (Paralelnye_mesta.nomer == 60) inputStream = r.openRawResource(R.raw.sinaidaln10);
                    if (Paralelnye_mesta.nomer == 61) inputStream = r.openRawResource(R.raw.sinaidaln11);
                    if (Paralelnye_mesta.nomer == 62) inputStream = r.openRawResource(R.raw.sinaidaln12);
                    if (Paralelnye_mesta.nomer == 63) inputStream = r.openRawResource(R.raw.sinaidaln13);
                    if (Paralelnye_mesta.nomer == 64) inputStream = r.openRawResource(R.raw.sinaidaln14);
                    if (Paralelnye_mesta.nomer == 65) inputStream = r.openRawResource(R.raw.sinaidaln15);
                    if (Paralelnye_mesta.nomer == 66) inputStream = r.openRawResource(R.raw.sinaidaln16);
                    if (Paralelnye_mesta.nomer == 67) inputStream = r.openRawResource(R.raw.sinaidaln17);
                    if (Paralelnye_mesta.nomer == 68) inputStream = r.openRawResource(R.raw.sinaidaln18);
                    if (Paralelnye_mesta.nomer == 69) inputStream = r.openRawResource(R.raw.sinaidaln19);
                    if (Paralelnye_mesta.nomer == 70) inputStream = r.openRawResource(R.raw.sinaidaln20);
                    if (Paralelnye_mesta.nomer == 71) inputStream = r.openRawResource(R.raw.sinaidaln21);
                    if (Paralelnye_mesta.nomer == 72) inputStream = r.openRawResource(R.raw.sinaidaln22);
                    if (Paralelnye_mesta.nomer == 73) inputStream = r.openRawResource(R.raw.sinaidaln23);
                    if (Paralelnye_mesta.nomer == 74) inputStream = r.openRawResource(R.raw.sinaidaln24);
                    if (Paralelnye_mesta.nomer == 75) inputStream = r.openRawResource(R.raw.sinaidaln25);
                    if (Paralelnye_mesta.nomer == 76) inputStream = r.openRawResource(R.raw.sinaidaln26);
                    if (Paralelnye_mesta.nomer == 77) inputStream = r.openRawResource(R.raw.sinaidaln27);
                }
                if (inputStream != null) {
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        if (belarus) {
                            if (line.contains("//")) {
                                int t1 = line.indexOf("//");
                                line = line.substring(0, t1).trim();
                                if (!line.equals(""))
                                    builder.append(line).append("\n");
                                continue;
                            }
                        }
                        builder.append(line).append("\n");
                    }
                    inputStream.close();
                    inputStream = null;
                    if (chten.length == 6 && i == 3) {
                        if (belarus) {
                            maranAta.add("<br><em><!--no-->" + getResources().getString(by.carkva_gazeta.malitounik.R.string.end_fabreary_be) + "</em><br>\n");
                        } else {
                            maranAta.add("<br><em><!--no-->" + getResources().getString(by.carkva_gazeta.malitounik.R.string.end_fabreary_ru) + "</em><br>\n");
                        }
                    }
                    String[] split2 = builder.toString().split("===");
                    if (konec == split2.length) konec = konec - 1;
                    int vN, vK;
                    StringBuilder r1 = new StringBuilder();
                    String r2 = "";

                    for (int e = nachalo; e <= konec; e++) {
                        if (stixn != -1) {
                            if (s5 != -1) {
                                if (e == nachalo) {
                                    if (belarus) vN = split2[e].indexOf(stixn + ". ");
                                    else vN = split2[e].indexOf(stixn + " ");
                                    r1.append(split2[e].substring(vN).trim());
                                }
                                if (e != nachalo && e != konec) {
                                    r1.append("\n").append(split2[e].trim());
                                }
                                if (e == konec) {
                                    int vK1;
                                    if (belarus) vK1 = split2[e].indexOf(stixk + ". ");
                                    else vK1 = split2[e].indexOf(stixk + " ");
                                    vK = split2[e].indexOf("\n", vK1);
                                    r2 = split2[e].substring(0, vK);
                                }
                            } else {
                                int vK1;
                                if (belarus) {
                                    vN = split2[e].indexOf(stixn + ". ");
                                    vK1 = split2[e].indexOf(stixk + ". ");
                                } else {
                                    vN = split2[e].indexOf(stixn + " ");
                                    vK1 = split2[e].indexOf(stixk + " ");
                                }
                                vK = split2[e].indexOf("\n", vK1);
                                r1.append(split2[e].substring(vN, vK));
                            }
                        } else {
                            if (belarus) {
                                maranAta.add("<!--no--><!--nazva+++" + Paralelnye_mesta.nazvaBel + " " + e + "--><br><strong>" + Paralelnye_mesta.nazvaBel + " " + e + "</strong><br>\n");
                            } else {
                                maranAta.add("<!--no--><!--nazva+++" + Paralelnye_mesta.nazva + " " + e + "--><br><strong>" + Paralelnye_mesta.nazva + " " + e + "</strong><br>\n");
                            }
                            String[] splitline = split2[e].trim().split("\n");
                            int i3;
                            for (int i2 = 0; i2 < splitline.length; i2++) {
                                if (Paralelnye_mesta.kniga.contains("Сир") && e == 1 && i2 >= 8)
                                    i3 = i2 - 7;
                                else
                                    i3 = i2 + 1;
                                if (belarus)
                                    maranAta.add("<!--" + Paralelnye_mesta.kniga + "." + e + "." + i3 + "--><!--nazva+++" + Paralelnye_mesta.nazvaBel + " " + e + "-->" + splitline[i2] + getParallel(Paralelnye_mesta.nomer, e, i2) + "\n");
                                else
                                    maranAta.add("<!--" + Paralelnye_mesta.kniga + "." + e + "." + i3 + "--><!--nazva+++" + Paralelnye_mesta.nazva + " " + e + "-->" + splitline[i2] + getParallel(Paralelnye_mesta.nomer, e, i2) + "\n");
                            }
                        }
                    }
                    if (stixn != -1) {
                        int t1 = fit.indexOf(".");
                        if (belarus) {
                            maranAta.add("<!--no--><!--nazva+++" + Paralelnye_mesta.nazvaBel + " " + fit.substring(s2 + 1, t1) + "--><br><strong>" + Paralelnye_mesta.nazvaBel + " " + fit.substring(s2 + 1) + "</strong><br>\n");
                        } else {
                            maranAta.add("<!--no--><!--nazva+++" + Paralelnye_mesta.nazva + " " + fit.substring(s2 + 1, t1) + "--><br><strong>" + Paralelnye_mesta.nazva + " " + fit.substring(s2 + 1) + "</strong><br>\n");
                        }
                        String[] res1 = r1.toString().trim().split("\n");
                        for (int i2 = 0, i3 = stixn; i2 < res1.length; i2++, i3++) {
                            if (belarus)
                                maranAta.add("<!--" + Paralelnye_mesta.kniga + "." + nachalo + "." + i3 + "--><!--nazva+++" + Paralelnye_mesta.nazvaBel + " " + fit.substring(s2 + 1, t1) + "-->" + res1[i2] + getParallel(Paralelnye_mesta.nomer, nachalo, i3 - 1) + "\n");
                            else
                                maranAta.add("<!--" + Paralelnye_mesta.kniga + "." + nachalo + "." + i3 + "--><!--nazva+++" + Paralelnye_mesta.nazva + " " + fit.substring(s2 + 1, t1) + "-->" + res1[i2] + getParallel(Paralelnye_mesta.nomer, nachalo, i3 - 1) + "\n");
                        }
                        if (konec - nachalo != 0) {
                            String[] res2 = r2.trim().split("\n");
                            for (int i2 = 0; i2 < res2.length; i2++) {
                                if (belarus)
                                    maranAta.add("<!--" + Paralelnye_mesta.kniga + "." + konec + "." + (i2 + 1) + "--><!--nazva+++" + Paralelnye_mesta.nazvaBel + " " + konec + "-->" + res2[i2] + getParallel(Paralelnye_mesta.nomer, konec, i2) + "\n");
                                else
                                    maranAta.add("<!--" + Paralelnye_mesta.kniga + "." + konec + "." + (i2 + 1) + "--><!--nazva+++" + Paralelnye_mesta.nazva + " " + konec + "-->" + res2[i2] + getParallel(Paralelnye_mesta.nomer, konec, i2) + "\n");
                            }
                        }
                    }
                } else {
                    maranAta.add("<!--no--><!--nazva+++" + Paralelnye_mesta.nazvaBel + "--><br><strong>" + Paralelnye_mesta.nazvaBel + "</strong><br>\n");
                    maranAta.add("<!--no-->" + getString(by.carkva_gazeta.malitounik.R.string.semuxa_maran_ata_error));
                }
            } catch (Throwable t) {
                if (belarus) {
                    maranAta.add("<!--no--><br><strong>" + Paralelnye_mesta.nazvaBel + " " + fit + "</strong><br>\n");
                } else {
                    maranAta.add("<!--no--><br><strong>" + Paralelnye_mesta.nazva + " " + fit + "</strong><br>\n");
                }
                maranAta.add("<!--no-->" + getResources().getString(by.carkva_gazeta.malitounik.R.string.error_ch) + "\n");
            }
        }
        adapter.notifyDataSetChanged();
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
    public void onBackPressed() {
        if (paralel) {
            scrollView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            title_toolbar.setText(getString(by.carkva_gazeta.malitounik.R.string.maranata_tollbar, tollBarText));
            paralel = false;
            supportInvalidateOptionsMenu();
        } else if (fullscreenPage) {
            fullscreenPage = false;
            show();
        } else if (linearLayout2.getVisibility() == View.VISIBLE) {
            linearLayout2.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();
        } else {
            if (change) {
                onSupportNavigateUp();
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        scrollerSchedule = null;
        scrollTimer = null;
        if (addBold_Underline) {
            clearEmptyPosition();
            File file;
            if (belarus) {
                file = new File(getFilesDir() + "/MaranAtaBel/" + cytanne + ".json");
            } else {
                file = new File(getFilesDir() + "/MaranAta/" + cytanne + ".json");
            }
            if (Vydelenie.size() == 0) {
                if (file.exists()) {
                    file.delete();
                }
            } else {
                try {
                    Gson gson = new Gson();
                    FileWriter outputStream = new FileWriter(file);
                    outputStream.write(gson.toJson(Vydelenie));
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
            //MyBackupAgent.requestBackup(this);
        }
        if (resetTimer != null) {
            resetTimer.cancel();
            resetTimer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fullscreenPage) hide();
        autoscroll = k.getBoolean("autoscroll", false);
        spid = k.getInt("autoscrollSpid", 60);
        if (autoscroll) {
            startAutoScroll();
        }
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        autoscroll = k.getBoolean("autoscroll", false);
        if (paralel) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setVisible(false);
        } else {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setVisible(true);
        }
        if (linearLayout2.getVisibility() == View.GONE) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setVisible(true);
            mActionDown = false;
        } else {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setVisible(false);
        }
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
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_paralel).setChecked(k.getBoolean("paralel_maranata", true));
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_paralel).setVisible(true);
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_dzen_noch).setChecked(k.getBoolean("dzen_noch", false));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        linearLayout2.setVisibility(View.GONE);

        SharedPreferences.Editor prefEditor;
        dzenNoch = k.getBoolean("dzen_noch", false);
        prefEditor = k.edit();
        if (id == android.R.id.home) {
            if (paralel) {
                onBackPressed();
                return true;
            }
        }
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
        if (id == by.carkva_gazeta.malitounik.R.id.action_paralel) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                prefEditor.putBoolean("paralel_maranata", true);
            } else {
                prefEditor.putBoolean("paralel_maranata", false);
            }
            prefEditor.apply();
            adapter.notifyDataSetChanged();
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
        listView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fullscreen", fullscreenPage);
        outState.putBoolean("change", change);
        outState.putString("tollBarText", tollBarText);
        outState.putBoolean("paralel", paralel);
        outState.putInt("paralelPosition", paralelPosition);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (linearLayout2.getVisibility() == View.VISIBLE) {
            linearLayout2.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();
        } else {
            ParralelMestaView(position);
            paralelPosition = position;
        }
    }

    private void ParralelMestaView(int position) {
        if (k.getBoolean("paralel_maranata", true)) {
            if (!autoscroll) {
                int t1 = maranAta.get(position).indexOf("$");
                if (t1 != -1) {
                    paralel = true;
                    Paralelnye_mesta pm = new Paralelnye_mesta();
                    int t2 = maranAta.get(position).indexOf("-->");
                    int t3 = maranAta.get(position).indexOf("<!--");
                    String ch = maranAta.get(position).substring(t3 + 4, t2);
                    String[] biblia = ch.split("\\.");
                    conteiner.removeAllViewsInLayout();
                    ArrayList<TextView_Roboto_Condensed> arrayList = pm.paralel(this, biblia[0] + " " + (biblia[1]) + "." + (biblia[2]), maranAta.get(position).substring(t1 + 1).trim(), belarus);
                    for (int i = 0; i < arrayList.size(); i++) {
                        conteiner.addView(arrayList.get(i));
                    }
                    scrollView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    title_toolbar.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.paralel_smoll, biblia[0] + " " + (biblia[1]) + "." + (biblia[2])));
                    supportInvalidateOptionsMenu();
                }
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (!autoscroll) {
            if (!maranAta.get(position).contains("<!--no-->") && !maranAta.get(position).trim().equals("")) {
                if (linearLayout2.getVisibility() == View.GONE) {
                    linearLayout2.setVisibility(View.VISIBLE);
                    MaranAta_Global_List.setListPosition(position);
                } else {
                    linearLayout2.setVisibility(View.GONE);
                }
                supportInvalidateOptionsMenu();
            }
        }
        return true;
    }

    private String getParallel(int kniga, int glava, int position) {
        Biblia_parallel_chtenia parallel = new Biblia_parallel_chtenia();
        String res = "";
        if (kniga == 1) {
            res = parallel.kniga1(glava, position + 1);
        }
        if (kniga == 2) {
            res = parallel.kniga2(glava, position + 1);
        }
        if (kniga == 3) {
            res = parallel.kniga3(glava, position + 1);
        }
        if (kniga == 4) {
            res = parallel.kniga4(glava, position + 1);
        }
        if (kniga == 5) {
            res = parallel.kniga5(glava, position + 1);
        }
        if (kniga == 6) {
            res = parallel.kniga6(glava, position + 1);
        }
        if (kniga == 7) {
            res = parallel.kniga7(glava, position + 1);
        }
        if (kniga == 8) {
            res = parallel.kniga8(glava, position + 1);
        }
        if (kniga == 9) {
            res = parallel.kniga9(glava, position + 1);
        }
        if (kniga == 10) {
            res = parallel.kniga10(glava, position + 1);
        }
        if (kniga == 11) {
            res = parallel.kniga11(glava, position + 1);
        }
        if (kniga == 12) {
            res = parallel.kniga12(glava, position + 1);
        }
        if (kniga == 13) {
            res = parallel.kniga13(glava, position + 1);
        }
        if (kniga == 14) {
            res = parallel.kniga14(glava, position + 1);
        }
        if (kniga == 15) {
            res = parallel.kniga15(glava, position + 1);
        }
        if (kniga == 16) {
            res = parallel.kniga16(glava, position + 1);
        }
        if (kniga == 17) {
            res = parallel.kniga17(glava, position + 1);
        }
        if (kniga == 18) {
            res = parallel.kniga18(glava, position + 1);
        }
        if (kniga == 19) {
            res = parallel.kniga19(glava, position + 1);
        }
        if (kniga == 20) {
            res = parallel.kniga20(glava, position + 1);
        }
        if (kniga == 21) {
            res = parallel.kniga21(glava, position + 1);
        }
        if (kniga == 22) {
            res = parallel.kniga22(glava, position + 1);
        }
        if (kniga == 23) {
            res = parallel.kniga23(glava, position + 1);
        }
        if (kniga == 24) {
            res = parallel.kniga24(glava, position + 1);
        }
        if (kniga == 25) {
            res = parallel.kniga25(glava, position + 1);
        }
        if (kniga == 26) {
            res = parallel.kniga26(glava, position + 1);
        }
        if (kniga == 27) {
            res = parallel.kniga27(glava, position + 1);
        }
        if (kniga == 28) {
            res = parallel.kniga28(glava, position + 1);
        }
        if (kniga == 29) {
            res = parallel.kniga29(glava, position + 1);
        }
        if (kniga == 30) {
            res = parallel.kniga30(glava, position + 1);
        }
        if (kniga == 31) {
            res = parallel.kniga31(glava, position + 1);
        }
        if (kniga == 32) {
            res = parallel.kniga32(glava, position + 1);
        }
        if (kniga == 33) {
            res = parallel.kniga33(glava, position + 1);
        }
        if (kniga == 34) {
            res = parallel.kniga34(glava, position + 1);
        }
        if (kniga == 35) {
            res = parallel.kniga35(glava, position + 1);
        }
        if (kniga == 36) {
            res = parallel.kniga36(glava, position + 1);
        }
        if (kniga == 37) {
            res = parallel.kniga37(glava, position + 1);
        }
        if (kniga == 38) {
            res = parallel.kniga38(glava, position + 1);
        }
        if (kniga == 39) {
            res = parallel.kniga39(glava, position + 1);
        }
        if (kniga == 40) {
            res = parallel.kniga40(glava, position + 1);
        }
        if (kniga == 41) {
            res = parallel.kniga41(glava, position + 1);
        }
        if (kniga == 42) {
            res = parallel.kniga42(glava, position + 1);
        }
        if (kniga == 43) {
            res = parallel.kniga43(glava, position + 1);
        }
        if (kniga == 44) {
            res = parallel.kniga44(glava, position + 1);
        }
        if (kniga == 45) {
            res = parallel.kniga45(glava, position + 1);
        }
        if (kniga == 46) {
            res = parallel.kniga46(glava, position + 1);
        }
        if (kniga == 47) {
            res = parallel.kniga47(glava, position + 1);
        }
        if (kniga == 48) {
            res = parallel.kniga48(glava, position + 1);
        }
        if (kniga == 49) {
            res = parallel.kniga49(glava, position + 1);
        }
        if (kniga == 50) {
            res = parallel.kniga50(glava, position + 1);
        }
        if (kniga == 51) {
            res = parallel.kniga51(glava, position + 1);
        }
        if (kniga == 52) {
            res = parallel.kniga52(glava, position + 1);
        }
        if (kniga == 53) {
            res = parallel.kniga53(glava, position + 1);
        }
        if (kniga == 54) {
            res = parallel.kniga54(glava, position + 1);
        }
        if (kniga == 55) {
            res = parallel.kniga55(glava, position + 1);
        }
        if (kniga == 56) {
            res = parallel.kniga56(glava, position + 1);
        }
        if (kniga == 57) {
            res = parallel.kniga57(glava, position + 1);
        }
        if (kniga == 58) {
            res = parallel.kniga58(glava, position + 1);
        }
        if (kniga == 59) {
            res = parallel.kniga59(glava, position + 1);
        }
        if (kniga == 60) {
            res = parallel.kniga60(glava, position + 1);
        }
        if (kniga == 61) {
            res = parallel.kniga61(glava, position + 1);
        }
        if (kniga == 62) {
            res = parallel.kniga62(glava, position + 1);
        }
        if (kniga == 63) {
            res = parallel.kniga63(glava, position + 1);
        }
        if (kniga == 64) {
            res = parallel.kniga64(glava, position + 1);
        }
        if (kniga == 65) {
            res = parallel.kniga65(glava, position + 1);
        }
        if (kniga == 66) {
            res = parallel.kniga66(glava, position + 1);
        }
        if (kniga == 67) {
            res = parallel.kniga67(glava, position + 1);
        }
        if (kniga == 68) {
            res = parallel.kniga68(glava, position + 1);
        }
        if (kniga == 69) {
            res = parallel.kniga69(glava, position + 1);
        }
        if (kniga == 70) {
            res = parallel.kniga70(glava, position + 1);
        }
        if (kniga == 71) {
            res = parallel.kniga71(glava, position + 1);
        }
        if (kniga == 72) {
            res = parallel.kniga72(glava, position + 1);
        }
        if (kniga == 73) {
            res = parallel.kniga73(glava, position + 1);
        }
        if (kniga == 74) {
            res = parallel.kniga74(glava, position + 1);
        }
        if (kniga == 75) {
            res = parallel.kniga75(glava, position + 1);
        }
        if (kniga == 76) {
            res = parallel.kniga76(glava, position + 1);
        }
        if (kniga == 77) {
            res = parallel.kniga77(glava, position + 1);
        }
        if (!res.contains("+-+")) {
            if (belarus)
                res = MainActivity.translateToBelarus(res);
            res = "$" + res;
        }
        return res;
    }

    class maran_ata_ListAdaprer extends ArrayAdapter<String> {

        private final Activity activity;

        maran_ata_ListAdaprer(Activity activity) {
            super(activity, by.carkva_gazeta.malitounik.R.layout.simple_list_item_maranata, maranAta);
            this.activity = activity;
        }

        @Override
        public boolean isEnabled(int position) {
            if (maranAta.get(position).contains("<!--no-->"))
                return false;
            else if (scrollerSchedule == null)
                return super.isEnabled(position);
            else
                return false;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null || setFont) {
                mView = activity.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_maranata, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(by.carkva_gazeta.malitounik.R.id.label);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }
            String textView = maranAta.get(position);
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
            textView = textView.replace("+-+", "");
            int t1 = textView.indexOf("$");
            SpannableStringBuilder ssb;
            int end;
            if (t1 != -1) {
                int t2 = textView.indexOf("-->");
                if (t2 != -1) {
                    textView = textView.substring(t2 + 3);
                    t1 = textView.indexOf("$");
                }
                int paralelLeg = textView.substring(t1 + 1).length();
                textView = textView.replace("$", "<br>");
                Spanned spanned = MainActivity.fromHtml(textView.trim());
                end = spanned.length();
                t1 = end - paralelLeg;
                ssb = new SpannableStringBuilder(spanned);
                if (k.getBoolean("paralel_maranata", true)) {
                    ssb.setSpan(new RelativeSizeSpan(0.7f), t1, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorSecondary_text)), t1, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    ssb.delete(t1, end);
                    end = t1;
                }
                int pos = checkPosition(position);
                if (pos != -1) {
                    if (Vydelenie.get(pos).get(1) == 1) {
                        if (dzenNoch)
                            ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorPrimary_text)), 0, t1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorYelloy)), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (Vydelenie.get(pos).get(2) == 1)
                        ssb.setSpan(new UnderlineSpan(), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if (Vydelenie.get(pos).get(3) == 1)
                        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                Spanned spanned = MainActivity.fromHtml(textView);
                end = spanned.length();
                ssb = new SpannableStringBuilder(spanned);
                int pos = checkPosition(position);
                if (pos != -1) {
                    if (Vydelenie.get(pos).get(1) == 1) {
                        if (dzenNoch)
                            ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorPrimary_text)), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorYelloy)), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (Vydelenie.get(pos).get(2) == 1)
                        ssb.setSpan(new UnderlineSpan(), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if (Vydelenie.get(pos).get(3) == 1)
                        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            viewHolder.text.setText(ssb);
            if (position == MaranAta_Global_List.getListPosition() && linearLayout2.getVisibility() == View.VISIBLE) {
                if (dzenNoch) {
                    viewHolder.text.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark2);
                    viewHolder.text.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
                } else {
                    viewHolder.text.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorDivider);
                }
            } else {
                if (dzenNoch) {
                    viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.selector_dark));
                    viewHolder.text.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
                } else {
                    viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.selector_default));
                }
            }
            return mView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
