package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.play.core.splitinstall.SplitInstallException;
import com.google.android.play.core.splitinstall.SplitInstallHelper;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class MainActivity extends AppCompatActivity implements View.OnClickListener, Dialog_context_menu.Dialog_context_menu_Listener, Dialog_context_menu_vybranoe.Dialog_context_menu_vybranoe_Listener, Menu_cviaty.carkva_carkva_Listener, Dialog_delite.Dialog_delite_Listener, Menu_caliandar.Menu_caliandarPageListinner, Dialog_font_size.Dialog_font_size_Listener, Dialog_pasxa.Dialog_pasxa_Listener, Dialog_prazdnik.Dialog_prazdnik_Listener {

    private static boolean setAlarm = true;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private GregorianCalendar c;
    private SharedPreferences k;
    private SharedPreferences.Editor prefEditors;
    private int idSelect;
    private int idOld = -1;
    //private int value = 0;
    private TextView_Roboto_Condensed label1;
    private TextView_Roboto_Condensed label2;
    private TextView_Roboto_Condensed label3;
    private TextView_Roboto_Condensed label4;
    private TextView_Roboto_Condensed label5;
    private TextView_Roboto_Condensed label6;
    private TextView_Roboto_Condensed label7;
    private TextView_Roboto_Condensed label8;
    private TextView_Roboto_Condensed label91;
    private TextView_Roboto_Condensed label92;
    private TextView_Roboto_Condensed label93;
    private TextView_Roboto_Condensed label94;
    private TextView_Roboto_Condensed label95;
    private TextView_Roboto_Condensed label101;
    private TextView_Roboto_Condensed label102;
    private TextView_Roboto_Condensed label103;
    private TextView_Roboto_Condensed label104;
    private TextView_Roboto_Condensed label105;
    private TextView_Roboto_Condensed label11;
    private TextView_Roboto_Condensed label12;
    private TextView_Roboto_Condensed label13;
    private ImageView imageView2;
    private ImageView imageView3;
    private TextView_Roboto_Condensed textView;
    private boolean dzenNoch;
    private ScrollView scrollView;
    //private int setidYear;
    private static long back_pressed;
    private Menu_natatki Menu_natatki;
    private Menu_vybranoe vybranoe;
    private Menu_cviaty carkva;
    private Menu_pamiatka menu_pamiatka;
    private Menu_padryxtouka_da_spovedzi padryxtouka;
    public final static ArrayList<Padzeia> padzeia = new ArrayList<>();
    public static int setDataCalendar = -1;
    public static boolean checkBrightness = true;
    private static boolean setPadzeia = true;
    private String tolbarTitle;
    private static int SessionId;
    private boolean shortcuts = false;
    private static boolean onStart = true;
    public static int brightness = 15;
    public static boolean dialogVisable = false;

    @Override
    public void setDataCalendar(int day_of_year, int year) {
        c = (GregorianCalendar) Calendar.getInstance();
        idSelect = R.id.label1;
        int dayyear = 0;
        for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < year; i++) {
            if (c.isLeapYear(i)) dayyear = 366 + dayyear;
            else dayyear = 365 + dayyear;
        }
        setDataCalendar = dayyear + day_of_year - 1;
        idOld = -1;
        onClick(label1);
    }

    @Override
    public void onDialogFontSizePositiveClick() {
        if (padryxtouka != null)
            padryxtouka.onDialogFontSizePositiveClick();
        if (menu_pamiatka != null)
            menu_pamiatka.onDialogFontSizePositiveClick();
    }

    @Override
    public void setPage(int page) {
        setDataCalendar = page;
    }

    @Override
    public void file_delite(int position, String file) {
        if (vybranoe != null)
            vybranoe.file_delite(position);
        if (Menu_natatki != null)
            Menu_natatki.file_delite(position);
    }

    @Override
    public void onDialogEditClick(int position) {
        Menu_natatki.onDialogEditClick(position);
    }

    @Override
    public void onDialogDeliteClick(int position, String name) {
        Menu_natatki.onDialogDeliteClick(position, name);
    }

    @Override
    public void onDialogDeliteVybranoeClick(int position, String name) {
        vybranoe.onDialogDeliteVybranoeClick(position, name);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id", idSelect);
        outState.putInt("idOld", idOld);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkBrightness) {
            try {
                brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS) * 100 / 255;
            } catch (Settings.SettingNotFoundException e) {
                brightness = 15;
            }
        } else {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) brightness / 100;
            getWindow().setAttributes(lp);
        }
        k = getSharedPreferences("biblia", MODE_PRIVATE);

        float density = getResources().getDisplayMetrics().density;

        ImageView logosite = findViewById(R.id.imageView);
        logosite.post(() -> {
            BitmapDrawable bd = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.logotip);
            float imageHeight = Objects.requireNonNull(bd).getBitmap().getHeight() / density;
            float imageWidth = bd.getBitmap().getWidth() / density;
            float widthDp = logosite.getWidth() / density;
            float kooficient = widthDp / imageWidth;
            float hidch = imageHeight * kooficient;
            ViewGroup.LayoutParams layoutParams = logosite.getLayoutParams();
            layoutParams.height = (int) (hidch * density);
            logosite.setLayoutParams(layoutParams);
        });
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }

    private void ajustCompoundDrawableSizeWithText(@NonNull final TextView_Roboto_Condensed textView, final Drawable leftDrawable) {
        textView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (leftDrawable != null) {
                    leftDrawable.setBounds(0, 0, (int) textView.getTextSize(), (int) textView.getTextSize());
                }
                textView.setCompoundDrawables(leftDrawable, null, null, null);
                textView.removeOnLayoutChangeListener(this);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mkDir();
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Удаление старой константы размера шрифта
        if (k.contains("font_biblia")) {
            prefEditors = k.edit();
            prefEditors.putInt("font_malitounik", (int) k.getFloat("font_biblia", 18F));
            prefEditors.remove("font_biblia");
            prefEditors.apply();
        }

        if (savedInstanceState != null) {
            idSelect = savedInstanceState.getInt("id");
            idOld = savedInstanceState.getInt("idOld");
        }
        /*InputStream inputStream2 = getResources().openRawResource(R.raw.nadsan_psaltyr);
        String[] split;
        try {
            InputStreamReader isr = new InputStreamReader(inputStream2);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                /*if (line.contains("//")) {
                    int t1 = line.indexOf("//");
                    line = line.substring(0, t1).trim();
                    if (!line.equals(""))
                        builder.append(line).append("\n");
                    continue;
                }*/
                /*builder.append(line).append("\n");
            }
            inputStream2.close();
           split = builder.toString().split("===");
           StringBuilder builder1 = new StringBuilder();
           for (int e = 1; e < split.length; e++) {
               builder1.append("// Псалом ").append(e).append("\n").append("===").append(split[e]);
           }
           File file = new File(this.getFilesDir() + "/caliandar_code.txt");
           FileWriter outputStream = new FileWriter(file);
           outputStream.write(builder1.toString());
           outputStream.close();
        } catch (Throwable ignored) {
        }
        */

        // Удаление старой версии Избраного
        File dirV = new File(getFilesDir() + "/Vybranoe");
        if (dirV.exists()) {
            String[] dirContents = dirV.list();
            for (String dirContent : Objects.requireNonNull(dirContents)) {
                File file = new File(getFilesDir() + "/Vybranoe/" + dirContent);
                if (file.exists()) {
                    file.delete();
                }
            }
            dirV.delete();
        }
        // Конец

        Toolbar toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.title_toolbar);
        textView.setOnClickListener((v) -> {
            textView.setHorizontallyScrolling(true);
            textView.setFreezesText(true);
            textView.setMarqueeRepeatLimit(-1);
            if (textView.isSelected()) {
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setSelected(false);
            } else {
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setSelected(true);
            }
        });
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN + 4);
        setSupportActionBar(toolbar);
        // Скрываем клавиатуру
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        c = (GregorianCalendar) Calendar.getInstance();

        //setidYear = c.get(Calendar.YEAR);

        idSelect = k.getInt("id", R.id.label1);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        linearLayout2 = findViewById(R.id.title9);
        linearLayout3 = findViewById(R.id.title10);
        label1 = findViewById(R.id.label1);
        label1.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label2 = findViewById(R.id.label2);
        label2.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label3 = findViewById(R.id.label3);
        label3.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label4 = findViewById(R.id.label4);
        label4.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label5 = findViewById(R.id.label5);
        label5.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label6 = findViewById(R.id.label6);
        label6.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label7 = findViewById(R.id.label7);
        label7.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label8 = findViewById(R.id.label8);
        label8.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        TextView_Roboto_Condensed label9 = findViewById(R.id.label9);
        label9.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label91 = findViewById(R.id.label91);
        label91.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label92 = findViewById(R.id.label92);
        label92.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label93 = findViewById(R.id.label93);
        label93.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label94 = findViewById(R.id.label94);
        label94.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label95 = findViewById(R.id.label95);
        label95.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        TextView_Roboto_Condensed label10 = findViewById(R.id.label10);
        label10.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label101 = findViewById(R.id.label101);
        label101.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label102 = findViewById(R.id.label102);
        label102.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label103 = findViewById(R.id.label103);
        label103.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label104 = findViewById(R.id.label104);
        label104.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label105 = findViewById(R.id.label105);
        label105.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label11 = findViewById(R.id.label11);
        label11.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label12 = findViewById(R.id.label12);
        label12.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label13 = findViewById(R.id.label13);
        label13.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

        TextView_Roboto_Condensed carkva_link = findViewById(R.id.textView);
        carkva_link.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        ImageView logosite = findViewById(R.id.imageView);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.krest);
        if (dzenNoch) {
            drawable = ContextCompat.getDrawable(this, R.drawable.krest_black);
            label91.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            label92.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            label93.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            label94.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            label95.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            label101.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            label102.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            label103.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            label105.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            label104.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        }
        ajustCompoundDrawableSizeWithText(label1, drawable);
        ajustCompoundDrawableSizeWithText(label2, drawable);
        ajustCompoundDrawableSizeWithText(label3, drawable);
        ajustCompoundDrawableSizeWithText(label4, drawable);
        ajustCompoundDrawableSizeWithText(label5, drawable);
        ajustCompoundDrawableSizeWithText(label6, drawable);
        ajustCompoundDrawableSizeWithText(label7, drawable);
        ajustCompoundDrawableSizeWithText(label8, drawable);
        ajustCompoundDrawableSizeWithText(label9, drawable);
        ajustCompoundDrawableSizeWithText(label10, drawable);
        ajustCompoundDrawableSizeWithText(label11, drawable);
        ajustCompoundDrawableSizeWithText(label12, drawable);
        ajustCompoundDrawableSizeWithText(label13, drawable);

        if (dzenNoch) {
            toolbar.setPopupTheme(R.style.AppCompatDark);
            drawable = ContextCompat.getDrawable(this, R.drawable.logotip_whate);
            logosite.setImageDrawable(drawable);
        }
        LinearLayout label9a = findViewById(R.id.label9a);
        LinearLayout label10a = findViewById(R.id.label10a);
        if (k.getInt("sinoidal", 0) == 1) {
            label11.setVisibility(View.VISIBLE);
        }
        scrollView = findViewById(R.id.ScrollView);

        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        label1.setOnClickListener(this);
        label2.setOnClickListener(this);
        label3.setOnClickListener(this);
        label4.setOnClickListener(this);
        label5.setOnClickListener(this);
        label6.setOnClickListener(this);
        label7.setOnClickListener(this);
        label8.setOnClickListener(this);
        label91.setOnClickListener(this);
        label92.setOnClickListener(this);
        label93.setOnClickListener(this);
        label94.setOnClickListener(this);
        label95.setOnClickListener(this);
        label101.setOnClickListener(this);
        label102.setOnClickListener(this);
        label103.setOnClickListener(this);
        label104.setOnClickListener(this);
        label105.setOnClickListener(this);
        label11.setOnClickListener(this);
        label12.setOnClickListener(this);
        label13.setOnClickListener(this);
        label9a.setOnClickListener(this);
        label10a.setOnClickListener(this);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);

        Uri data = getIntent().getData();
        if (data != null) {
            if (data.toString().contains("shortcuts=1")) {
                idSelect = R.id.label12;
                onClick(label12);
            } else if (data.toString().contains("shortcuts=3")) {
                idSelect = R.id.label7;
                onClick(label7);
            } else if (data.toString().contains("shortcuts=2")) {
                idSelect = R.id.label2;
                shortcuts = true;
                onClick(label2);
            } else if (data.toString().contains("caliandar")) {
                idSelect = R.id.label1;
                onClick(label1);
            } else if (data.toString().contains("biblija")) {
                idSelect = R.id.label8;
                onClick(label8);
            } else if (!data.toString().contains("https://")) {
                idSelect = R.id.label2;
                shortcuts = true;
                onClick(label2);
            }
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String WIDGETDAY = "widget_day";
            String WIDGETMUN = "widget_mun";

            if (extras.getBoolean(WIDGETMUN, false) && savedInstanceState == null) {
                idSelect = R.id.label1;
                int dayyear = 0;
                for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < extras.getInt("Year", c.get(Calendar.YEAR)); i++) {
                    if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                    else dayyear = 365 + dayyear;
                }
                setDataCalendar = dayyear + extras.getInt("DayYear", c.get(Calendar.DAY_OF_YEAR)) - 1;
            }
            if (extras.getBoolean(WIDGETDAY, false) && savedInstanceState == null) {
                idSelect = R.id.label1;
                int chyt = c.get(Calendar.DAY_OF_YEAR) - 1;
                int dayyear = 0;
                int chytanneYear = c.get(Calendar.YEAR);
                for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < chytanneYear; i++) {
                    if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                    else dayyear = 365 + dayyear;
                }
                setDataCalendar = dayyear + chyt;
            }

            if (extras.getBoolean("sabytie", false)) {
                idSelect = R.id.label1;
                int chyt = extras.getInt("data") - 1;
                int dayyear = 0;
                int chytanneYear = extras.getInt("year");
                if (chytanneYear == -1) chytanneYear = c.get(Calendar.YEAR);
                for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < chytanneYear; i++) {
                    if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                    else dayyear = 365 + dayyear;
                }
                setDataCalendar = dayyear + chyt;
            }
        }

        boolean scroll = false;
        // Выбор пункта
        switch (idSelect) {
            case R.id.label1:
                onClick(label1);
                break;
            case R.id.label2:
                onClick(label2);
                break;
            case R.id.label3:
                onClick(label3);
                break;
            case R.id.label4:
                if (!label4.isShown())
                    scroll = true;
                onClick(label4);
                break;
            case R.id.label5:
                if (!label5.isShown())
                    scroll = true;
                onClick(label5);
                break;
            case R.id.label6:
                if (!label6.isShown())
                    scroll = true;
                onClick(label6);
                break;
            case R.id.label7:
                if (!label7.isShown())
                    scroll = true;
                onClick(label7);
                break;
            case R.id.label8:
                if (!label8.isShown())
                    scroll = true;
                onClick(label8);
                break;
            case R.id.label91:
                if (!label91.isShown())
                    scroll = true;
                onClick(label91);
                break;
            case R.id.label92:
                if (!label92.isShown())
                    scroll = true;
                onClick(label92);
                break;
            case R.id.label93:
                if (!label93.isShown())
                    scroll = true;
                onClick(label93);
                break;
            case R.id.label94:
                if (!label94.isShown())
                    scroll = true;
                onClick(label94);
                break;
            case R.id.label95:
                if (!label95.isShown())
                    scroll = true;
                onClick(label95);
                break;
            case R.id.label101:
                if (!label101.isShown())
                    scroll = true;
                onClick(label101);
                break;
            case R.id.label102:
                if (!label102.isShown())
                    scroll = true;
                onClick(label102);
                break;
            case R.id.label103:
                if (!label103.isShown())
                    scroll = true;
                onClick(label103);
                break;
            case R.id.label104:
                if (!label104.isShown())
                    scroll = true;
                onClick(label104);
                break;
            case R.id.label105:
                if (!label105.isShown())
                    scroll = true;
                onClick(label105);
                break;
            case R.id.label11:
                if (!label11.isShown())
                    scroll = true;
                onClick(label11);
                break;
            case R.id.label12:
                if (!label12.isShown())
                    scroll = true;
                onClick(label12);
                break;
            case R.id.label13:
                if (!label13.isShown())
                    scroll = true;
                onClick(label13);
                break;
            default:
                idSelect = R.id.label1;
                onClick(label1);
                break;
        }

        if (setAlarm) {
            Intent i = new Intent(this, ReceiverUpdate.class);
            i.setAction("UPDATE");
            sendBroadcast(i);
            GregorianCalendar c2 = (GregorianCalendar) Calendar.getInstance();
            PendingIntent pServise = PendingIntent.getBroadcast(this, 10, i, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (c2.getTimeInMillis() > mkTime(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH)))
                c2.add(Calendar.DATE, 1);
            Objects.requireNonNull(am).setRepeating(AlarmManager.RTC_WAKEUP, mkTime(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH)), 86400000L, pServise);
            setAlarm = false;
        }
        if (MainActivity.setPadzeia) {
            MainActivity.setPadzeia = false;
            setListPadzeia(this);
        }
        if (scroll)
            scrollView.post(() -> scrollView.smoothScrollBy(0, scrollView.getHeight()));
    }

    public static void setListPadzeia(Activity activity) {
        new Thread(() -> {
            MainActivity.padzeia.clear();
            try {
                File dir = new File(activity.getFilesDir() + "/Sabytie");
                for (String s : Objects.requireNonNull(dir.list())) {
                    File fileS = new File(activity.getFilesDir() + "/Sabytie/" + s);
                    if (fileS.exists()) {
                        FileReader inputStream = new FileReader(fileS);
                        BufferedReader reader = new BufferedReader(inputStream);
                        String line;
                        while ((line = reader.readLine()) != null && !line.equals("")) {
                            String[] t1 = line.split(" ");
                            try {
                                if (t1.length == 11)
                                    MainActivity.padzeia.add(new Padzeia(t1[0].replace("_", " "), t1[1], t1[2], Long.parseLong(t1[3]), Integer.parseInt(t1[4]), t1[5], t1[6], t1[7], Integer.parseInt(t1[8]), t1[9], t1[10], 0));
                                else
                                    MainActivity.padzeia.add(new Padzeia(t1[0].replace("_", " "), t1[1], t1[2], Long.parseLong(t1[3]), Integer.parseInt(t1[4]), t1[5], t1[6], t1[7], Integer.parseInt(t1[8]), t1[9], t1[10], Integer.parseInt(t1[11])));
                            } catch (Exception ignored) {
                                fileS.delete();
                            }
                        }
                        inputStream.close();
                    }
                }
            } catch (Exception ignored) {
            }
        }).start();
    }

    private void mkDir() {
        File dir = new File(getFilesDir() + "/Sabytie");
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(getFilesDir() + "/MaranAtaBel");
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(getFilesDir() + "/MaranAta");
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(getFilesDir() + "/Malitva");
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(getFilesDir() + "/BibliaSemuxaNovyZavet");
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(getFilesDir() + "/BibliaSinodalNovyZavet");
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(getFilesDir() + "/BibliaSemuxaStaryZavet");
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(getFilesDir() + "/BibliaSinodalStaryZavet");
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(getFilesDir() + "/Site");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                moveTaskToBack(true);
                prefEditors = k.edit();
                Map<String, ?> allEntries = k.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    if (entry.getKey().contains("Scroll")) {
                        prefEditors.putInt(entry.getKey(), 0);
                    }
                }
                //prefEditors.putInt("id", R.id.label1);
                prefEditors.putString("search_svityx_string", "");
                prefEditors.putString("search_string", "");
                prefEditors.putInt("search_position", 0);
                prefEditors.putInt("pegistr", 0);
                prefEditors.putInt("slovocalkam", 0);
                //prefEditors.putInt("kohnoeslovo", 0);
                prefEditors.putInt("biblia_seash", 0);
                prefEditors.apply();
                setPadzeia = true;
                setDataCalendar = -1;
                checkBrightness = true;
                super.onBackPressed();
            } else {
                back_pressed = System.currentTimeMillis();
                LinearLayout layout = new LinearLayout(this);
                if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
                else layout.setBackgroundResource(R.color.colorPrimary);
                float density = getResources().getDisplayMetrics().density;
                int realpadding = (int) (10 * density);
                TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(this);
                toast.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                toast.setText(getString(R.string.exit));
                toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                layout.addView(toast);
                Toast mes = new Toast(this);
                mes.setDuration(Toast.LENGTH_SHORT);
                mes.setView(layout);
                mes.show();
            }
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void setPasxa(int year) {
        FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
        ftrans.setCustomAnimations(R.anim.alphainfragment, R.anim.alphaoutfragment);
        Menu_pashalii Pasha = Menu_pashalii.newInstance(year);
        ftrans.replace(R.id.conteiner, Pasha);
        ftrans.commit();
    }

    @Override
    public void setPrazdnik(int year) {
        FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
        ftrans.setCustomAnimations(R.anim.alphainfragment, R.anim.alphaoutfragment);
        carkva = Menu_cviaty.newInstance(year);
        //setidYear = year;
        String str = getString(R.string.CARKVA_SVIATY);
        if (year != c.get(Calendar.YEAR)) str = str + " на " + year + " год";
        textView.setText(str);
        ftrans.replace(R.id.conteiner, carkva);
        ftrans.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_glava) {
            int dayyear = 0;
            for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < c.get(Calendar.YEAR); i++) {
                if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                else dayyear = 365 + dayyear;
            }
            setDataCalendar = dayyear + c.get(Calendar.DAY_OF_YEAR) - 1;
            idOld = -1;
            onClick(label1);
        }
        if (id == R.id.settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        if (id == R.id.onas) {
            Intent i = new Intent(this, onas.class);
            startActivity(i);
        }
        if (id == R.id.help) {
            Intent i = new Intent(this, Help.class);
            startActivity(i);
        }
        if (id == R.id.pasxa) {
            Dialog_pasxa pasxa = new Dialog_pasxa();
            pasxa.show(getSupportFragmentManager(), "pasxa");
        }
        if (id == R.id.prazdnik) {
            Dialog_prazdnik prazdnik = new Dialog_prazdnik();
            prazdnik.show(getSupportFragmentManager(), "prazdnik");
        }
        if (id == R.id.tipicon) {
            Dialog_tipicon tipicon = Dialog_tipicon.getInstance(0);
            tipicon.show(getSupportFragmentManager(), "tipicon");
        }
        if (id == R.id.search) {
            if (MainActivity.checkModule_resources(this)) {
                try {
                    Intent intent = new Intent(this, Class.forName("by.carkva_gazeta.resources.search_pesny"));
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(getSupportFragmentManager(), "dadatak");
            }
        }
        if (id == R.id.sabytie) {
            Intent i = new Intent(this, sabytie.class);
            startActivityForResult(i, 105);
        }
        if (id == R.id.search_sviatyia) {
            Intent i = new Intent(this, search_sviatyia.class);
            startActivityForResult(i, 140);
        }
        if (id == R.id.action_mun) {
            if (onStart) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, 1);
                for (int i = 0; i < setDataCalendar; i++) {
                    gregorianCalendar.add(Calendar.DATE, 1);
                }
                Intent i = new Intent(this, caliandar_mun.class);
                i.putExtra("mun", gregorianCalendar.get(Calendar.MONTH));
                i.putExtra("day", gregorianCalendar.get(Calendar.DATE));
                i.putExtra("year", gregorianCalendar.get(Calendar.YEAR));
                startActivityForResult(i, 105);
                onStart = false;
            }
        }
        if (id == R.id.search_nadsan) {
            if (MainActivity.checkModule_resources(this)) {
                try {
                    Intent intent = new Intent(this, Class.forName("by.carkva_gazeta.resources.search_biblia"));
                    intent.putExtra("zavet", 3);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(getSupportFragmentManager(), "dadatak");
            }
        }
        if (id == R.id.sortdate) {
            prefEditors = k.edit();
            if (item.isChecked())
                prefEditors.putInt("natatki_sort", 0);
            else
                prefEditors.putInt("natatki_sort", 1);
            prefEditors.apply();
            Menu_natatki.sortAlfavit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 150 && resultCode == RESULT_OK) {
            downloadDynamicModule(this);
        }
        if (requestCode == 105) {
            if (data != null) {
                int dayyear = 0;
                int day = data.getIntExtra("data", 0);
                int year = data.getIntExtra("year", c.get(Calendar.YEAR));
                idSelect = R.id.label1;
                for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < year; i++) {
                    if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                    else dayyear = 365 + dayyear;
                }
                if (setDataCalendar != dayyear + day) {
                    setDataCalendar = dayyear + day;
                    idOld = -1;
                    onClick(label1);
                }
            }
            onStart = true;
        }
        if (requestCode == 140) {
            if (data != null) {
                int dayyear = 0;
                int day = data.getIntExtra("data", 0);
                for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < c.get(Calendar.YEAR); i++) {
                    if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                    else dayyear = 365 + dayyear;
                }
                if (setDataCalendar != dayyear + day) {
                    setDataCalendar = dayyear + day;
                    idOld = -1;
                    onClick(label1);
                }
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (!(idSelect == R.id.label9a || idSelect == R.id.label10a)) {
            menu.findItem(R.id.action_add).setVisible(false);
            menu.findItem(R.id.action_mun).setVisible(false);
            menu.findItem(R.id.action_glava).setVisible(false);
            menu.findItem(R.id.tipicon).setVisible(false);
            menu.findItem(R.id.pasxa).setVisible(false);
            menu.findItem(R.id.search).setVisible(false);
            menu.findItem(R.id.trash).setVisible(false);
            menu.findItem(R.id.sabytie).setVisible(false);
            menu.findItem(R.id.prazdnik).setVisible(false);
            menu.findItem(R.id.search_sviatyia).setVisible(false);
            menu.findItem(R.id.search_nadsan).setVisible(false);
            menu.findItem(R.id.sortdate).setVisible(false);
            menu.findItem(R.id.action_font).setVisible(false);
            menu.findItem(R.id.action_bright).setVisible(false);
            menu.findItem(R.id.action_dzen_noch).setVisible(false);
            switch (idSelect) {
                case R.id.label101:
                case R.id.label102:
                    menu.findItem(R.id.action_font).setVisible(true);
                    menu.findItem(R.id.action_bright).setVisible(true);
                    menu.findItem(R.id.action_dzen_noch).setVisible(true);
                    break;
                case R.id.label103:
                    menu.findItem(R.id.prazdnik).setVisible(true);
                    break;
                case R.id.label104:
                    menu.findItem(R.id.pasxa).setVisible(true);
                    break;
                case R.id.label7:
                    menu.findItem(R.id.action_add).setVisible(true);
                    menu.findItem(R.id.sortdate).setVisible(true);
                    int sort = k.getInt("natatki_sort", 0);
                    if (sort == 1)
                        menu.findItem(R.id.sortdate).setChecked(true);
                    else
                        menu.findItem(R.id.sortdate).setChecked(false);
                    break;
                case R.id.label12:
                    menu.findItem(R.id.trash).setVisible(true);
                    break;
                case R.id.label13:
                    menu.findItem(R.id.search_nadsan).setVisible(true);
                    break;
            }
            if (idSelect == R.id.label91 || idSelect == R.id.label92 || idSelect == R.id.label93 || idSelect == R.id.label94 || idSelect == R.id.label95) {
                menu.findItem(R.id.search).setVisible(true);
            }
            if (dzenNoch) {
                menu.findItem(R.id.action_mun).setIcon(R.drawable.calendar_black_full);
                menu.findItem(R.id.action_glava).setIcon(R.drawable.calendar_black);
            }
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_dzen_noch).setChecked(k.getBoolean("dzen_noch", false));
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(R.menu.main, menu);
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
    public void onClick(View view) {
        idSelect = view.getId();
        if (!(idSelect == R.id.label9a || idSelect == R.id.label10a)) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (!(idSelect == R.id.label9a || idSelect == R.id.label10a)) {
            if (dzenNoch) {
                label1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label4.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label5.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label6.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label7.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label8.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label91.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label92.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label93.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label94.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label95.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label101.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label102.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label103.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label104.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label105.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label11.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label12.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
                label13.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbackground_material_dark));
            } else {
                label1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label4.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label5.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label6.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label7.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label8.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label91.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label92.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label93.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label94.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label95.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label101.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label102.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label103.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label104.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label105.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label11.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label12.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
                label13.setBackgroundColor(ContextCompat.getColor(this, R.color.colorIcons));
            }
        }

        prefEditors = k.edit();
        if (idSelect == R.id.label91 || idSelect == R.id.label92 || idSelect == R.id.label93 || idSelect == R.id.label94 || idSelect == R.id.label95) {
            linearLayout2.setVisibility(View.VISIBLE);
            imageView2.setImageResource(android.R.drawable.arrow_up_float);
        }
        if (idSelect == R.id.label101 || idSelect == R.id.label102 || idSelect == R.id.label103 || idSelect == R.id.label104 || idSelect == R.id.label105) {
            linearLayout3.setVisibility(View.VISIBLE);
            imageView3.setImageResource(android.R.drawable.arrow_up_float);
        }

        if (idSelect == R.id.label9a) {
            if (linearLayout2.getVisibility() == View.VISIBLE) {
                linearLayout2.setVisibility(View.GONE);
                imageView2.setImageResource(android.R.drawable.arrow_down_float);
            } else {
                linearLayout2.setVisibility(View.VISIBLE);
                imageView2.setImageResource(android.R.drawable.arrow_up_float);
                scrollView.post(() -> scrollView.smoothScrollBy(0, linearLayout2.getHeight()));
            }
        }
        if (idSelect == R.id.label10a) {
            if (linearLayout3.getVisibility() == View.VISIBLE) {
                linearLayout3.setVisibility(View.GONE);
                imageView3.setImageResource(android.R.drawable.arrow_down_float);
            } else {
                linearLayout3.setVisibility(View.VISIBLE);
                imageView3.setImageResource(android.R.drawable.arrow_up_float);
                scrollView.post(() -> scrollView.smoothScrollBy(0, linearLayout3.getHeight()));
            }
        }
        switch (idSelect) {
            case R.id.label1:
                tolbarTitle = getString(R.string.kaliandar2);
                if (dzenNoch)
                    label1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label2:
                tolbarTitle = getString(R.string.SAJT);
                if (dzenNoch)
                    label2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label3:
                tolbarTitle = getString(R.string.LITURGIKON);
                if (dzenNoch)
                    label3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label4:
                tolbarTitle = getString(R.string.malitvy);
                if (dzenNoch)
                    label4.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label4.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label5:
                tolbarTitle = getString(R.string.akafisty);
                if (dzenNoch)
                    label5.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label5.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label6:
                tolbarTitle = getString(R.string.ruzanec);
                if (dzenNoch)
                    label6.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label6.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label7:
                tolbarTitle = getString(R.string.MAJE_MALITVY);
                if (dzenNoch)
                    label7.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label7.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label8:
                tolbarTitle = getString(R.string.title_biblia);
                if (dzenNoch)
                    label8.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label8.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label13:
                tolbarTitle = getString(R.string.title_psalter);
                if (dzenNoch)
                    label13.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label13.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label91:
                tolbarTitle = getString(R.string.pesny1);
                if (dzenNoch)
                    label91.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label91.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label92:
                tolbarTitle = getString(R.string.pesny2);
                if (dzenNoch)
                    label92.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label92.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label93:
                tolbarTitle = getString(R.string.pesny3);
                if (dzenNoch)
                    label93.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label93.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label94:
                tolbarTitle = getString(R.string.pesny4);
                if (dzenNoch)
                    label94.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label94.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label95:
                tolbarTitle = getString(R.string.pesny5);
                if (dzenNoch)
                    label95.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label95.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label103:
                tolbarTitle = getString(R.string.CARKVA_SVIATY);
                if (dzenNoch)
                    label103.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else
                    label103.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label104:
                tolbarTitle = getString(R.string.KALIANDAR_BEL);
                if (dzenNoch)
                    label104.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else
                    label104.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label105:
                tolbarTitle = getString(R.string.parafii);
                if (dzenNoch)
                    label105.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else
                    label105.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label102:
                tolbarTitle = getString(R.string.pamiatka);
                if (dzenNoch)
                    label102.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else
                    label102.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label101:
                tolbarTitle = getString(R.string.spovedz);
                if (dzenNoch)
                    label101.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else
                    label101.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label11:
                tolbarTitle = getString(R.string.bsinaidal);
                if (dzenNoch)
                    label11.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label11.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
            case R.id.label12:
                tolbarTitle = getString(R.string.MenuVybranoe);
                if (dzenNoch)
                    label12.setBackgroundColor(ContextCompat.getColor(this, R.color.colorprimary_material_dark));
                else label12.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
                break;
        }
        textView.setText(tolbarTitle);

        if (idOld != idSelect) {
            FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
            ftrans.setCustomAnimations(R.anim.alphainfragment, R.anim.alphaoutfragment);

            c = (GregorianCalendar) Calendar.getInstance();
            LinearLayout linear = findViewById(R.id.linear);
            if (idSelect != R.id.label2 && linear.getVisibility() == View.VISIBLE)
                linear.setVisibility(View.GONE);
            if (idSelect != R.id.label7)
                Menu_natatki = null;
            if (idSelect != R.id.label12)
                vybranoe = null;
            if (idSelect != R.id.label101)
                padryxtouka = null;
            if (idSelect != R.id.label102)
                menu_pamiatka = null;
            if (idSelect != R.id.label103)
                carkva = null;
            switch (idSelect) {
                case R.id.label1:
                    int dayyear = 0;
                    for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < c.get(Calendar.YEAR); i++) {
                        if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                        else dayyear = 365 + dayyear;
                    }
                    if (setDataCalendar == -1)
                        setDataCalendar = dayyear + c.get(Calendar.DAY_OF_YEAR) - 1;
                    Menu_caliandar caliandar = Menu_caliandar.newInstance(setDataCalendar);
                    ftrans.replace(R.id.conteiner, caliandar);
                    prefEditors.putInt("id", idSelect);
                    break;
                case R.id.label2:
                    prefEditors.putInt("id", idSelect);
                    Menu_glavnoe menu_glavnoe = Menu_glavnoe.newInstance(shortcuts);
                    ftrans.replace(R.id.conteiner, menu_glavnoe);
                    break;
                case R.id.label3:
                    prefEditors.putInt("id", idSelect);
                    Menu_bogashlugbovya bogaslus = new Menu_bogashlugbovya();
                    ftrans.replace(R.id.conteiner, bogaslus);
                    break;
                case R.id.label4:
                    prefEditors.putInt("id", idSelect);
                    Menu_malitvy Malitvy = new Menu_malitvy();
                    ftrans.replace(R.id.conteiner, Malitvy);
                    break;
                case R.id.label5:
                    prefEditors.putInt("id", idSelect);
                    Menu_akafisty Akafisty = new Menu_akafisty(this);
                    ftrans.replace(R.id.conteiner, Akafisty);
                    break;
                case R.id.label6:
                    prefEditors.putInt("id", idSelect);
                    Menu_ruzanec ruzanec = new Menu_ruzanec();
                    ftrans.replace(R.id.conteiner, ruzanec);
                    break;
                case R.id.label7:
                    prefEditors.putInt("id", idSelect);
                    Menu_natatki = new Menu_natatki();
                    ftrans.replace(R.id.conteiner, Menu_natatki);
                    break;
                case R.id.label8:
                    if (MaranAta_Global_List.getNatatkiSemuxa() == null) {
                        File fileNatatki = new File(getFilesDir() + "/BibliaSemuxaNatatki.json");
                        if (fileNatatki.exists()) {
                            try {
                                String sb = "";
                                FileReader inputStream = new FileReader(fileNatatki);
                                BufferedReader reader = new BufferedReader(inputStream);
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb = line;
                                }
                                inputStream.close();
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
                                }.getType();
                                MaranAta_Global_List.setNatatkiSemuxa(gson.fromJson(sb, type));
                            } catch (IOException ignored) {
                                MaranAta_Global_List.setNatatkiSemuxa(new ArrayList<>());
                            }
                        } else {
                            MaranAta_Global_List.setNatatkiSemuxa(new ArrayList<>());
                        }
                    }
                    if (MaranAta_Global_List.getZakladkiSemuxa() == null) {
                        File fileZakladki = new File(getFilesDir() + "/BibliaSemuxaZakladki.json");
                        if (fileZakladki.exists()) {
                            try {
                                String sb = "";
                                FileReader inputStream = new FileReader(fileZakladki);
                                BufferedReader reader = new BufferedReader(inputStream);
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb = line;
                                }
                                inputStream.close();
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<String>>() {
                                }.getType();
                                MaranAta_Global_List.setZakladkiSemuxa(gson.fromJson(sb, type));
                            } catch (IOException ignored) {
                                MaranAta_Global_List.setZakladkiSemuxa(new ArrayList<>());
                            }
                        } else {
                            MaranAta_Global_List.setZakladkiSemuxa(new ArrayList<>());
                        }
                    }
                    prefEditors.putInt("id", idSelect);
                    prefEditors.putBoolean("novyzavet", false);
                    Menu_bible_semuxa semuxa = new Menu_bible_semuxa();
                    ftrans.replace(R.id.conteiner, semuxa);
                    break;
                case R.id.label13:
                    prefEditors.putInt("id", idSelect);
                    prefEditors.putBoolean("novyzavet", false);
                    Menu_psalter_nadsana nadsana = new Menu_psalter_nadsana();
                    ftrans.replace(R.id.conteiner, nadsana);
                    break;
                case R.id.label91:
                    prefEditors.putInt("id", idSelect);
                    Menu_pesny_prasl menu_pesny_prasl = new Menu_pesny_prasl();
                    ftrans.replace(R.id.conteiner, menu_pesny_prasl);
                    break;
                case R.id.label92:
                    prefEditors.putInt("id", idSelect);
                    Menu_pesny_bel menu_pesny_bel = new Menu_pesny_bel();
                    ftrans.replace(R.id.conteiner, menu_pesny_bel);
                    break;
                case R.id.label93:
                    prefEditors.putInt("id", idSelect);
                    Menu_pesny_bag menu_pesny_bag = new Menu_pesny_bag();
                    ftrans.replace(R.id.conteiner, menu_pesny_bag);
                    break;
                case R.id.label94:
                    prefEditors.putInt("id", idSelect);
                    Menu_pesny_kal menu_pesny_kal = new Menu_pesny_kal();
                    ftrans.replace(R.id.conteiner, menu_pesny_kal);
                    break;
                case R.id.label95:
                    prefEditors.putInt("id", idSelect);
                    Menu_pesny_taize menu_pesny_taize = new Menu_pesny_taize();
                    ftrans.replace(R.id.conteiner, menu_pesny_taize);
                    break;
                case R.id.label103:
                    prefEditors.putInt("id", idSelect);
                    carkva = Menu_cviaty.newInstance(c.get(Calendar.YEAR));
                    ftrans.replace(R.id.conteiner, carkva);
                    break;
                case R.id.label104:
                    prefEditors.putInt("id", idSelect);
                    Menu_pashalii menu_pashalii = Menu_pashalii.newInstance();
                    ftrans.replace(R.id.conteiner, menu_pashalii);
                    break;
                case R.id.label105:
                    prefEditors.putInt("id", idSelect);
                    Menu_parafii_bgkc parafii_bgkc = new Menu_parafii_bgkc();
                    ftrans.replace(R.id.conteiner, parafii_bgkc);
                    break;
                case R.id.label102:
                    prefEditors.putInt("id", idSelect);
                    menu_pamiatka = new Menu_pamiatka();
                    ftrans.replace(R.id.conteiner, menu_pamiatka);
                    break;
                case R.id.label101:
                    prefEditors.putInt("id", idSelect);
                    padryxtouka = new Menu_padryxtouka_da_spovedzi();
                    ftrans.replace(R.id.conteiner, padryxtouka);
                    break;
                case R.id.label11:
                    if (MaranAta_Global_List.getNatatkiSinodal() == null) {
                        File fileNatatki = new File(getFilesDir() + "/BibliaSinodalNatatki.json");
                        if (fileNatatki.exists()) {
                            try {
                                String sb = "";
                                FileReader inputStream = new FileReader(fileNatatki);
                                BufferedReader reader = new BufferedReader(inputStream);
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb = line;
                                }
                                inputStream.close();
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
                                }.getType();
                                MaranAta_Global_List.setNatatkiSinodal(gson.fromJson(sb, type));
                            } catch (IOException ignored) {
                                MaranAta_Global_List.setNatatkiSinodal(new ArrayList<>());
                            }
                        } else {
                            MaranAta_Global_List.setNatatkiSinodal(new ArrayList<>());
                        }
                    }
                    if (MaranAta_Global_List.getZakladkiSinodal() == null) {
                        File fileZakladkiS = new File(getFilesDir() + "/BibliaSinodalZakladki.json");
                        if (fileZakladkiS.exists()) {
                            try {
                                String sb = "";
                                FileReader inputStream = new FileReader(fileZakladkiS);
                                BufferedReader reader = new BufferedReader(inputStream);
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb = line;
                                }
                                inputStream.close();
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<String>>() {
                                }.getType();
                                MaranAta_Global_List.setZakladkiSinodal(gson.fromJson(sb, type));
                            } catch (IOException ignored) {
                                MaranAta_Global_List.setZakladkiSinodal(new ArrayList<>());
                            }
                        } else {
                            MaranAta_Global_List.setZakladkiSinodal(new ArrayList<>());
                        }
                    }
                    prefEditors.putInt("id", idSelect);
                    prefEditors.putBoolean("novyzavet", false);
                    Menu_bible_sinoidal sinoidal = new Menu_bible_sinoidal();
                    ftrans.replace(R.id.conteiner, sinoidal);
                    break;
                case R.id.label12:
                    prefEditors.putInt("id", idSelect);
                    vybranoe = new Menu_vybranoe();
                    ftrans.replace(R.id.conteiner, vybranoe);
                    break;
                default:
                    idSelect = idOld;
                    prefEditors.putInt("id", idSelect);
                    break;
            }
            textView.postDelayed(ftrans::commitAllowingStateLoss, 300);
            prefEditors.apply();
        }
        idOld = idSelect;
    }

    private long mkTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 10, 0, 0);
        return calendar.getTimeInMillis();
    }

    private static int getOrientation(@NonNull Activity context) {
        int rotation = context.getWindowManager().getDefaultDisplay().getRotation();
        int displayOrientation = context.getResources().getConfiguration().orientation;

        if (displayOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_180)
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;

            return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        } else if (rotation == Surface.ROTATION_180 || rotation == Surface.ROTATION_90)
            return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;

        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @SuppressLint("SetTextI18n")
    public static void downloadDynamicModule(Activity context) {
        ProgressBar progressBarModule = context.findViewById(R.id.progressBarModule);
        LinearLayout layout_dialod = context.findViewById(R.id.linear);
        LinearLayout layout_dialod2 = context.findViewById(R.id.linear2);
        TextView_Roboto_Condensed text = context.findViewById(R.id.textProgress);
        SharedPreferences k = context.getSharedPreferences("biblia", MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) {
            layout_dialod2.setBackgroundResource(R.color.colorbackground_material_dark);
            context.findViewById(R.id.module_download).setBackgroundResource(R.color.colorPrimary_black);
        }
        SplitInstallManager splitInstallManager = SplitInstallManagerFactory.create(context);

        SplitInstallRequest request = SplitInstallRequest
                .newBuilder()
                .addModule("biblijateka")
                .build();

        SplitInstallStateUpdatedListener listener = state -> {
            if (state.status() == SplitInstallSessionStatus.FAILED) {
                downloadDynamicModule(context);
                return;
            }
            if (state.status() == SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION) {
                try {
                    splitInstallManager.startConfirmationDialogForResult(state, context, 150);
                } catch (IntentSender.SendIntentException ignored) {
                }
            }
            if (state.sessionId() == SessionId) {
                double bytesDownload = Math.round(state.bytesDownloaded() / 1024.0 / 1024.0 * 100.0) / 100.0;
                double total = Math.round(state.totalBytesToDownload() / 1024.0 / 1024.0 * 100.0) / 100.0;
                switch (state.status()) {
                    case SplitInstallSessionStatus.PENDING:
                        context.setRequestedOrientation(getOrientation(context));
                        layout_dialod.setVisibility(View.VISIBLE);
                        text.setText(bytesDownload + "Мб з " + total + "Мб");
                        break;
                    case SplitInstallSessionStatus.DOWNLOADED:
                        layout_dialod.setVisibility(View.GONE);
                        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                        break;
                    case SplitInstallSessionStatus.DOWNLOADING:
                        context.setRequestedOrientation(getOrientation(context));
                        layout_dialod.setVisibility(View.VISIBLE);
                        progressBarModule.setMax((int) state.totalBytesToDownload());
                        progressBarModule.setProgress((int) state.bytesDownloaded());
                        text.setText(bytesDownload + "Мб з " + total + "Мб");
                        break;
                    case SplitInstallSessionStatus.INSTALLED:
                        layout_dialod.setVisibility(View.GONE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            SplitInstallHelper.updateAppInfo(context);
                            new Handler().post(() -> {
                                try {
                                    Intent intent = new Intent(context, Class.forName("by.carkva_gazeta.biblijateka.bibliotekaView"));
                                    if (context.getIntent().getData() != null)
                                        intent.setData(context.getIntent().getData());
                                    intent.putExtra("site", true);
                                    context.startActivity(intent);
                                } catch (ClassNotFoundException ignored) {
                                }
                            });
                        } else {
                            try {
                                Context newContext = context.createPackageContext(context.getPackageName(), 0);
                                try {
                                    Intent intent = new Intent(newContext, Class.forName("by.carkva_gazeta.biblijateka.bibliotekaView"));
                                    if (context.getIntent().getData() != null)
                                        intent.setData(context.getIntent().getData());
                                    intent.putExtra("site", true);
                                    context.startActivity(intent);
                                } catch (ClassNotFoundException ignored) {
                                }
                            } catch (PackageManager.NameNotFoundException ignored) {
                            }
                        }
                        break;
                    case SplitInstallSessionStatus.CANCELED:
                    case SplitInstallSessionStatus.CANCELING:
                    case SplitInstallSessionStatus.FAILED:
                    case SplitInstallSessionStatus.INSTALLING:
                    case SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION:
                    case SplitInstallSessionStatus.UNKNOWN:
                        break;
                }
            }
        };

        splitInstallManager.registerListener(listener);

        splitInstallManager.startInstall(request)
                .addOnFailureListener(e -> {
                    if (((SplitInstallException) e).getErrorCode() == SplitInstallErrorCode.NETWORK_ERROR) {
                        LinearLayout layout = new LinearLayout(context);
                        if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
                        else layout.setBackgroundResource(R.color.colorPrimary);
                        float density = context.getResources().getDisplayMetrics().density;
                        int realpadding = (int) (10 * density);
                        TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(context);
                        toast.setTextColor(ContextCompat.getColor(context, R.color.colorIcons));
                        toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                        toast.setText(context.getString(R.string.no_internet));
                        toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                        layout.addView(toast);
                        Toast mes = new Toast(context);
                        mes.setDuration(Toast.LENGTH_LONG);
                        mes.setView(layout);
                        mes.show();
                    }
                })
                .addOnSuccessListener(sessionId -> SessionId = sessionId);
    }

    public static boolean checkModules_biblijateka(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        Set<String> muduls = SplitInstallManagerFactory.create(context).getInstalledModules();
        for (String mod : muduls) {
            if (mod.equals("biblijateka")) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkModule_resources(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        Set<String> muduls = SplitInstallManagerFactory.create(context).getInstalledModules();
        for (String mod : muduls) {
            if (mod.equals("resources")) {
                return true;
            }
        }
        return false;
    }

    public static int caliandar(@NonNull Context context, int mun) {
        String filename = "caliandar" + mun;
        return context.getResources().getIdentifier(filename, "raw", context.getPackageName());
    }

    public static String removeZnakiAndSlovy(String cytanne) {
        cytanne = cytanne.replace("\n", " ");
        cytanne = cytanne.replace("[", "");
        cytanne = cytanne.replace("?", "");
        cytanne = cytanne.replace("!", "");
        cytanne = cytanne.replace("(", "");
        cytanne = cytanne.replace(")", "");
        cytanne = cytanne.replace("#", "");
        cytanne = cytanne.replace("\"", "");
        cytanne = cytanne.replace(":", "");
        cytanne = cytanne.replace("|", "");
        cytanne = cytanne.replace("]", "");
        cytanne = cytanne.replace("Тон 1.", "");
        cytanne = cytanne.replace("Тон 2.", "");
        cytanne = cytanne.replace("Тон 3.", "");
        cytanne = cytanne.replace("Тон 4.", "");
        cytanne = cytanne.replace("Тон 5.", "");
        cytanne = cytanne.replace("Тон 6.", "");
        cytanne = cytanne.replace("Тон 7.", "");
        cytanne = cytanne.replace("Тон 8.", "");
        cytanne = cytanne.replace("Вялікія гадзіны", "");
        cytanne = cytanne.replace("На асьвячэньне вады", "");
        cytanne = cytanne.replace("Багародзіцы", "");
        cytanne = cytanne.replace("Дабравешчаньне", "");
        cytanne = cytanne.replace("Сустрэчы", "");
        cytanne = cytanne.replace("Літургіі няма", "");
        cytanne = cytanne.replace("На вячэрні", "");
        cytanne = cytanne.replace("Строгі пост", "");
        cytanne = cytanne.replace("Вялікі", "");
        cytanne = cytanne.replace("канон", "");
        cytanne = cytanne.replace("сьв.", "");
        cytanne = cytanne.replace("Чын", "");
        cytanne = cytanne.replace("паднясеньня", "");
        cytanne = cytanne.replace("Пачэснага", "");
        cytanne = cytanne.replace("Крыжа", "");
        cytanne = cytanne.replace("Андрэя", "");
        cytanne = cytanne.replace("Крыцкага", "");
        cytanne = cytanne.replace("Літургія", "");
        cytanne = cytanne.replace("раней", "");
        cytanne = cytanne.replace("асьвячаных", "");
        cytanne = cytanne.replace("дароў", "");
        cytanne = cytanne.replace("Яна", "");
        cytanne = cytanne.replace("Залатавуснага", "");
        cytanne = cytanne.replace("сьвятога", "");
        cytanne = cytanne.replace("Васіля", "");
        cytanne = cytanne.replace("Вялікага", "");
        cytanne = cytanne.replace("Блаславеньне", "");
        cytanne = cytanne.replace("вербаў", "");
        cytanne = cytanne.replace("з вячэрняй", "");
        cytanne = cytanne.replace("На ютрані", "");
        cytanne = cytanne.replace("Посту няма", "");
        cytanne = cytanne.replace("Пам.", "");
        cytanne = cytanne.replace("Сьв.", "");
        cytanne = cytanne.replace("Вялеб.", "");
        cytanne = cytanne.replace("Пакл.", "");
        cytanne = cytanne.replace("Багар.", "");
        cytanne = cytanne.replace("Вялікамуч.", "");
        cytanne = cytanne.replace("Ап.", "");
        cytanne = cytanne.replace("Айцам.", "");
        cytanne = cytanne.replace("Прар.", "");
        cytanne = cytanne.replace("Муч.", "");
        cytanne = cytanne.replace("Крыжу", "");
        cytanne = cytanne.replace("Вобр.", "");
        cytanne = cytanne.replace("Нов_году.", "");
        cytanne = cytanne.replace("Вял.", "");
        cytanne = cytanne.replace("Арх.", "");
        cytanne = cytanne.replace("Абнаўл.", "");
        cytanne = cytanne.replace("Сьвятамуч.", "");
        cytanne = cytanne.replace("Саб.", "");
        cytanne = cytanne.replace("Першамуч.", "");
        cytanne = cytanne.replace("Суб.", "");
        cytanne = cytanne.replace("Нядз.", "");
        cytanne = cytanne.replace("Ганне", "");
        cytanne = cytanne.trim();
        return cytanne;
    }

    @NonNull
    public static String translateToBelarus(String paralel) {
        paralel = paralel.replace("Быт", "Быц");
        paralel = paralel.replace("Исх", "Вых");
        paralel = paralel.replace("Лев", "Ляв");
        paralel = paralel.replace("Чис", "Лікі");
        paralel = paralel.replace("Втор", "Дрг");
        //paralel = paralel.replace("Нав", "Нав");
        //paralel = paralel.replace("Суд", "Суд");
        paralel = paralel.replace("Руфь", "Рут");
        //paralel = paralel.replace("1 Цар", "1 Цар");
        //paralel = paralel.replace("2 Цар", "2 Цар");
        //paralel = paralel.replace("3 Цар", "3 Цар");
        //paralel = paralel.replace("4 Цар", "4 Цар");
        paralel = paralel.replace("1 Пар", "1 Лет");
        paralel = paralel.replace("2 Пар", "2 Лет");
        paralel = paralel.replace("1 Езд", "1 Эзд");
        paralel = paralel.replace("Неем", "Нээм");
        paralel = paralel.replace("2 Езд", "2 Эзд");
        paralel = paralel.replace("Тов", "Тав");
        paralel = paralel.replace("Иудифь", "Юдт");
        paralel = paralel.replace("Есф", "Эст");
        paralel = paralel.replace("Иов", "Ёва");
        //paralel = paralel.replace("Пс", "Пс");
        paralel = paralel.replace("Притч", "Высл");
        paralel = paralel.replace("Еккл", "Экл");
        paralel = paralel.replace("Песн", "Псн");
        paralel = paralel.replace("Прем", "Мдр");
        paralel = paralel.replace("Сир", "Сір");
        paralel = paralel.replace("Ис", "Іс");
        paralel = paralel.replace("Иер", "Ер");
        //paralel = paralel.replace("Плач", "Плач");
        paralel = paralel.replace("Посл Иер", "Пасл Ер");
        //paralel = paralel.replace("Вар", "Бар");
        paralel = paralel.replace("Иез", "Езк");
        //paralel = paralel.replace("Дан", "Дан");
        paralel = paralel.replace("Ос", "Ас");
        paralel = paralel.replace("Иоил", "Ёіл");
        //paralel = paralel.replace("Ам", "Ам");
        paralel = paralel.replace("Авд", "Аўдз");
        paralel = paralel.replace("Иона", "Ёны");
        paralel = paralel.replace("Мих", "Міх");
        paralel = paralel.replace("Наум", "Нвм");
        paralel = paralel.replace("Авв", "Абк");
        paralel = paralel.replace("Соф", "Саф");
        paralel = paralel.replace("Агг", "Аг");
        //paralel = paralel.replace("Зах", "Зах");
        //paralel = paralel.replace("Мал", "Мал");
        //paralel = paralel.replace("1 Мак", "1 Мак");
        //paralel = paralel.replace("2 Мак", "2 Мак");
        //paralel = paralel.replace("3 Мак", "3 Мак");
        paralel = paralel.replace("3 Езд", "3 Эзд");
        paralel = paralel.replace("Мф", "Мц");
        //paralel = paralel.replace("Мк", "Мк");
        //paralel = paralel.replace("Лк", "Лк");
        paralel = paralel.replace("Ин", "Ян");
        paralel = paralel.replace("Деян", "Дз");
        paralel = paralel.replace("Иак", "Як");
        paralel = paralel.replace("1 Пет", "1 Пт");
        paralel = paralel.replace("2 Пет", "2 Пт");
        paralel = paralel.replace("1 Ин", "1 Ян");
        paralel = paralel.replace("2 Ин", "2 Ян");
        paralel = paralel.replace("3 Ин", "3 Ян");
        paralel = paralel.replace("Иуд", "Юд");
        paralel = paralel.replace("Рим", "Рым");
        paralel = paralel.replace("1 Кор", "1 Кар");
        paralel = paralel.replace("2 Кор", "2 Кар");
        //paralel = paralel.replace("Гал", "Гал");
        paralel = paralel.replace("Еф", "Эф");
        paralel = paralel.replace("Флп", "Плп");
        paralel = paralel.replace("Кол", "Клс");
        //paralel = paralel.replace("1 Фес", "1 Фес");
        //paralel = paralel.replace("2 Фес", "2 Фес");
        paralel = paralel.replace("1 Тим", "1 Цім");
        paralel = paralel.replace("2 Тим", "2 Цім");
        paralel = paralel.replace("Тит", "Ціт");
        //paralel = paralel.replace("Флм", "Флм");
        paralel = paralel.replace("Евр", "Гбр");
        paralel = paralel.replace("Откр", "Адкр");
        return paralel;
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        if (html == null) {
            return new SpannableString("");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nm = Objects.requireNonNull(connectivityManager).getActiveNetwork();
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nm);
            if (Objects.requireNonNull(actNw).hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return true;
            return actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        } else {
            NetworkInfo nwInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
            return Objects.requireNonNull(nwInfo).isConnectedOrConnecting();
        }
    }

    public static int isIntNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nm = Objects.requireNonNull(connectivityManager).getActiveNetwork();
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nm);
            if (Objects.requireNonNull(actNw).hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return 1;
            if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return 2;
            else return 0;
        } else {
            NetworkInfo activeNetwork = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
            if (Objects.requireNonNull(activeNetwork).isConnectedOrConnecting()) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return 1;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's by.carkva_gazeta.malitounikApp.data plan
                    return 2;
                } else return 0;
            } else {
                // not connected to the internet
                return 0;
            }
        }
    }
}
