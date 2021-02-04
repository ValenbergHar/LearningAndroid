package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

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

import java.util.Objects;

import by.carkva_gazeta.malitounik.Dialog_Help_Fullscreen;
import by.carkva_gazeta.malitounik.Dialog_brightness;
import by.carkva_gazeta.malitounik.Dialog_font_size;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.SmartFragmentStatePagerAdapter;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

public class nadsanContentActivity extends AppCompatActivity implements Dialog_font_size.Dialog_font_size_Listener, Dialog_bible_razdel.Dialog_bible_razdel_Listener, nadsanContentPage.ListPosition {

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
    //private boolean paralel = false;
    private TextView_Roboto_Condensed bibleInfo;
    private ViewPager vpPager;
    private int fullglav = 0;
    private int glava;
    private SharedPreferences k;
    private boolean dzenNoch;
    private boolean dialog = true;
    //private ScrollView scrollView;
    //private LinearLayout linearLayout;
    private ConstraintLayout linearLayoutTitle;
    //private String cytanneSours;
    //private String cytanneParalelnye;
    private TextView_Roboto_Condensed title_toolbar;
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

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor prefEditors = k.edit();
        ArrayMap<String, Integer> set = new ArrayMap<>();
        set.put("glava", vpPager.getCurrentItem());
        set.put("stix", fierstPosition);
        Gson gson = new Gson();
        prefEditors.putString("psalter_time_psalter_nadsan", gson.toJson(set));
        prefEditors.apply();
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("kafizma")) {
            glava = setKafizma(getIntent().getExtras().getInt("kafizma", 1));
        } else {
            glava = getIntent().getExtras().getInt("glava", 0);
        }
        if (getIntent().getExtras().containsKey("stix")) {
            fierstPosition = getIntent().getExtras().getInt("stix", 0);
            trak = true;
        }

        linearLayoutTitle = findViewById(R.id.linealLayoutTitle);
        //linearLayout = findViewById(R.id.conteiner);
        //scrollView = findViewById(R.id.scroll);
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
        bibleInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
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
        bibleInfo.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary));
        if (dzenNoch) {
            getWindow().setBackgroundDrawableResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
            bibleInfo.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
            vpTabStrip.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            bibleInfo.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
            vpTabStrip.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
        }
        bibleInfo.setText(getString(by.carkva_gazeta.malitounik.R.string.kafizma) + " " + getKafizma(glava));
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (glava != position)
                    fierstPosition = 0;
                bibleInfo.setText(getString(by.carkva_gazeta.malitounik.R.string.kafizma2) + " " + getKafizma(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        fullglav = 151;
        title_toolbar = findViewById(R.id.title_toolbar);

        if (savedInstanceState != null) {
            dialog = savedInstanceState.getBoolean("dialog");
            //paralel = savedInstanceState.getBoolean("paralel");
            //cytanneSours = savedInstanceState.getString("cytanneSours");
            //cytanneParalelnye = savedInstanceState.getString("cytanneParalelnye");
            fullscreenPage = savedInstanceState.getBoolean("fullscreen");
            checkSetDzenNoch = savedInstanceState.getBoolean("checkSetDzenNoch");
            //if (paralel) {
            //    setOnClic(cytanneParalelnye, cytanneSours);
            //}
        }
        vpPager.setCurrentItem(glava);

        if (k.getBoolean("orientation", false)) {
            setRequestedOrientation(getOrientation());
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    private void setTollbarTheme() {
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        title_toolbar.setText(getResources().getText(by.carkva_gazeta.malitounik.R.string.psalter));
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
            title_toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
            title_toolbar.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
        }
    }

    private int getKafizma(int psalter) {
        psalter++;
        int kafizma = 1;
        if (psalter >= 9 && psalter <= 16)
            kafizma = 2;
        if (psalter >= 17 && psalter <= 23)
            kafizma = 3;
        if (psalter >= 24 && psalter <= 31)
            kafizma = 4;
        if (psalter >= 32 && psalter <= 36)
            kafizma = 5;
        if (psalter >= 37 && psalter <= 45)
            kafizma = 6;
        if (psalter >= 46 && psalter <= 54)
            kafizma = 7;
        if (psalter >= 55 && psalter <= 63)
            kafizma = 8;
        if (psalter >= 64 && psalter <= 69)
            kafizma = 9;
        if (psalter >= 70 && psalter <= 76)
            kafizma = 10;
        if (psalter >= 77 && psalter <= 84)
            kafizma = 11;
        if (psalter >= 85 && psalter <= 90)
            kafizma = 12;
        if (psalter >= 91 && psalter <= 100)
            kafizma = 13;
        if (psalter >= 101 && psalter <= 104)
            kafizma = 14;
        if (psalter >= 105 && psalter <= 108)
            kafizma = 15;
        if (psalter >= 109 && psalter <= 117)
            kafizma = 16;
        if (psalter == 118)
            kafizma = 17;
        if (psalter >= 119 && psalter <= 133)
            kafizma = 18;
        if (psalter >= 134 && psalter <= 142)
            kafizma = 19;
        if (psalter >= 143 && psalter <= 151)
            kafizma = 20;
        return kafizma;
    }

    private int setKafizma(int kafizma) {
        int glava = 1;
        switch (kafizma) {
            case 2:
                glava = 9;
                break;
            case 3:
                glava = 17;
                break;
            case 4:
                glava = 24;
                break;
            case 5:
                glava = 32;
                break;
            case 6:
                glava = 37;
                break;
            case 7:
                glava = 46;
                break;
            case 8:
                glava = 55;
                break;
            case 9:
                glava = 64;
                break;
            case 10:
                glava = 70;
                break;
            case 11:
                glava = 77;
                break;
            case 12:
                glava = 85;
                break;
            case 13:
                glava = 91;
                break;
            case 14:
                glava = 101;
                break;
            case 15:
                glava = 105;
                break;
            case 16:
                glava = 109;
                break;
            case 17:
                glava = 118;
                break;
            case 18:
                glava = 119;
                break;
            case 19:
                glava = 134;
                break;
            case 20:
                glava = 143;
                break;
        }
        glava--;
        return glava;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("dialog", dialog);
        //outState.putBoolean("paralel", paralel);
        //outState.putString("cytanneSours", cytanneSours);
        //outState.putString("cytanneParalelnye", cytanneParalelnye);
        outState.putBoolean("fullscreen", fullscreenPage);
        outState.putBoolean("checkSetDzenNoch", checkSetDzenNoch);
    }

    @Override
    public void onBackPressed() {
        /*if (paralel) {
            scrollView.setVisibility(View.GONE);
            bibleInfo.setVisibility(View.VISIBLE);
            vpPager.setVisibility(View.VISIBLE);
            title_toolbar.setText(getResources().getText(by.carkva_gazeta.malitounik.R.string.psalter));
            paralel = false;
            supportInvalidateOptionsMenu();
        } else*/
        if (fullscreenPage) {
            fullscreenPage = false;
            show();
        }
        if (checkSetDzenNoch)
            onSupportNavigateUp();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        /*if (paralel) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_glava).setVisible(false);
        } else {*/
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_glava).setVisible(true);
        //}
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

    /*private void setOnClic(String cytanneParalelnye, String cytanneSours) {
        paralel = true;
        this.cytanneParalelnye = cytanneParalelnye;
        this.cytanneSours = cytanneSours;
        Paralelnye_mesta pm = new Paralelnye_mesta();
        linearLayout.removeAllViewsInLayout();
        ArrayList<TextView_Roboto_Condensed> arrayList = pm.paralel(nadsanContentActivity.this, cytanneSours, cytanneParalelnye, true);
        for (int i = 0; i < arrayList.size(); i++) {
            linearLayout.addView(arrayList.get(i));
        }
        scrollView.setVisibility(View.VISIBLE);
        bibleInfo.setVisibility(View.GONE);
        vpPager.setVisibility(View.GONE);
        title_toolbar.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.paralel_smoll, cytanneSours));
        supportInvalidateOptionsMenu();
    }*/

    class MyPagerAdapter extends SmartFragmentStatePagerAdapter {

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 151;
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
                    return nadsanContentPage.newInstance(i, pazicia);
                }
            }
            return nadsanContentPage.newInstance(0, 1);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(by.carkva_gazeta.malitounik.R.string.psalom2) + " " + (position + 1);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
