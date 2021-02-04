package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import by.carkva_gazeta.malitounik.Dialog_Help_Fullscreen;
import by.carkva_gazeta.malitounik.Dialog_brightness;
import by.carkva_gazeta.malitounik.Dialog_font_size;
import by.carkva_gazeta.malitounik.InteractiveScrollView;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;
import by.carkva_gazeta.malitounik.help_text;

public class paslia_prychascia extends AppCompatActivity implements View.OnTouchListener, Dialog_font_size.Dialog_font_size_Listener {

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
    private boolean checkSetDzenNoch = false;
    private SharedPreferences k;
    private TextView_Roboto_Condensed akafist;
    private TextView_Roboto_Condensed prog;
    private InteractiveScrollView scrollView;
    private int fontBiblia;
    private boolean dzenNoch;
    private int n;
    private InputStream inputStream;
    private String resurs;
    private Boolean men = false;
    private String title = "";
    private ConstraintLayout constraintLayout;
    private boolean levo = false, pravo = false;

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
    protected void onResume() {
        super.onResume();
        setTollbarTheme();
        if (fullscreenPage) hide();
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    @Override
    public void onDialogFontSizePositiveClick() {
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        akafist.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akafist_under);
        akafist = findViewById(R.id.TextView);
        scrollView = findViewById(R.id.scrollView2);
        constraintLayout = findViewById(R.id.constraint);
        constraintLayout.setOnTouchListener(this);
        prog = findViewById(R.id.progress);
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        if (savedInstanceState != null) {
            fullscreenPage = savedInstanceState.getBoolean("fullscreen");
            checkSetDzenNoch = savedInstanceState.getBoolean("checkSetDzenNoch");
        }
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
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
            akafist.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            prog.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        }
        akafist.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        try {
            Resources r = paslia_prychascia.this.getResources();
            int prynagodnyia = Objects.requireNonNull(getIntent().getExtras()).getInt("paslia_prychascia", 1);
            switch (prynagodnyia) {
                case 1:
                    inputStream = r.openRawResource(R.raw.paslia_prychascia1);
                    resurs = "paslia_prychascia1";
                    title = "Малітва падзякі";
                    break;
                case 2:
                    inputStream = r.openRawResource(R.raw.paslia_prychascia2);
                    resurs = "paslia_prychascia2";
                    title = "Малітва сьв. Васіля Вялікага";
                    break;
                case 3:
                    inputStream = r.openRawResource(R.raw.paslia_prychascia3);
                    resurs = "paslia_prychascia3";
                    title = "Малітва Сымона Мэтафраста";
                    break;
                case 4:
                    inputStream = r.openRawResource(R.raw.paslia_prychascia4);
                    resurs = "paslia_prychascia4";
                    title = "Iншая малітва";
                    break;
                case 5:
                    inputStream = r.openRawResource(R.raw.paslia_prychascia5);
                    resurs = "paslia_prychascia5";
                    title = "Малітва да Найсьвяцейшай Багародзіцы";
                    break;
            }
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    if (dzenNoch)
                        line = line.replace("#d00505", "#f44336");
                    builder.append(line).append("\n");
                }
                inputStream.close();
                //CaseInsensitiveResourcesFontLoader FontLoader = new CaseInsensitiveResourcesFontLoader();
                akafist.setText(MainActivity.fromHtml(builder.toString()));
            }
        } catch (Throwable ignored) {
        }

        men = vybranoe_view.checkVybranoe(this, resurs);

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
            title_toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
                            akafist.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
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
                            akafist.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                            String max = "";
                            if (fontBiblia == SettingsActivity.GET_FONT_SIZE_MAX)
                                max = " (макс)";
                            prog.setText(fontBiblia + " sp" + max);
                            prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
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
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_plus).setVisible(false);
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_minus).setVisible(false);
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setVisible(false);
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
            checkSetDzenNoch = true;
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
        if (id == by.carkva_gazeta.malitounik.R.id.action_vybranoe) {
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
        if (fullscreenPage) {
            fullscreenPage = false;
            show();
        } else {
            if (checkSetDzenNoch) {
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                super.onBackPressed();
            }
        }
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
        outState.putBoolean("checkSetDzenNoch", checkSetDzenNoch);
    }
}

