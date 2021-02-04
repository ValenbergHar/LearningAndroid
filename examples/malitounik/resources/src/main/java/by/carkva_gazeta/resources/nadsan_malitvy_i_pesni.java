package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import by.carkva_gazeta.malitounik.Dialog_Help_Fullscreen;
import by.carkva_gazeta.malitounik.Dialog_brightness;
import by.carkva_gazeta.malitounik.Dialog_font_size;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

public class nadsan_malitvy_i_pesni extends AppCompatActivity implements Dialog_font_size.Dialog_font_size_Listener {

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            scrollview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
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
    private boolean dzenNoch;
    private int fontBiblia;
    private ScrollView scrollview;
    private TextView_Roboto_Condensed malitvy_i_pesny;

    @Override
    public void onDialogFontSizePositiveClick() {
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        malitvy_i_pesny.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(by.carkva_gazeta.malitounik.R.layout.nadsan_malitvy_i_pesny);
        scrollview = findViewById(by.carkva_gazeta.malitounik.R.id.scrollview);
        malitvy_i_pesny = findViewById(by.carkva_gazeta.malitounik.R.id.malitvy_i_pesny);
        if (getIntent().getExtras() != null) {
            try {
                int pedsny = R.raw.nadsan_pered;
                switch (getIntent().getExtras().getInt("malitva", 0)) {
                    case 0:
                        pedsny = R.raw.nadsan_pered;
                        break;
                    case 1:
                        pedsny = R.raw.nadsan_posle;
                        break;
                    case 2:
                        pedsny = R.raw.nadsan_pesni;
                        break;
                }
                InputStream inputStream = getResources().openRawResource(pedsny);
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
                malitvy_i_pesny.setText(MainActivity.fromHtml(builder.toString()));
            } catch (Throwable ignored) {
            }
        }
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
            malitvy_i_pesny.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
        }
        malitvy_i_pesny.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        if (k.getBoolean("orientation", false)) {
            setRequestedOrientation(getOrientation());
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        setTollbarTheme();
    }

    private void setTollbarTheme() {
        Toolbar toolbar = findViewById(by.carkva_gazeta.malitounik.R.id.toolbar);
        TextView_Roboto_Condensed title_toolbar = findViewById(by.carkva_gazeta.malitounik.R.id.title_toolbar);
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
        title_toolbar.setText(Objects.requireNonNull(getIntent().getExtras()).getString("malitva_title"));
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
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
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_plus).setVisible(false);
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_minus).setVisible(false);
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setVisible(false);
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_orientation).setChecked(k.getBoolean("orientation", false));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences.Editor prefEditor;
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        prefEditor = k.edit();
        int id = item.getItemId();

        if (id == by.carkva_gazeta.malitounik.R.id.action_orientation) {
            checkSetDzenNoch = true;
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                setRequestedOrientation(getOrientation());
                prefEditor.putBoolean("orientation", true);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                prefEditor.putBoolean("orientation", false);
            }
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_font) {
            Dialog_font_size dialog_font_size = new Dialog_font_size();
            dialog_font_size.show(getSupportFragmentManager(), "font_size");
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
                onSupportNavigateUp();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fullscreenPage) hide();
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
        scrollview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
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
