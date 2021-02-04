package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by oleg on 29.3.16
 */
public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences k;
    private SharedPreferences.Editor prefEditor;
    private TextView_Roboto_Condensed textView;
    private TextView_Roboto_Condensed settingsNotifiSviata;
    private RadioButton_Roboto_Condensed radioButtonOnly;
    private RadioButton_Roboto_Condensed radioButtonFull;
    private RadioButton_Roboto_Condensed radioButtonNon;
    private RadioButton_Roboto_Condensed maranataRus;
    private RadioButton_Roboto_Condensed maranataBel;
    private SwitchCompat checkBoxV;
    private SwitchCompat checkBoxG;
    private SwitchCompat sinoidalcheckBoxG;
    private SwitchCompat maranatacheckBoxG;
    private SwitchCompat checkBoxT;
    private SwitchCompat pravas;
    private SwitchCompat pkc;
    private SwitchCompat dziar;
    private SwitchCompat prafes;
    private SwitchCompat checkBox5;
    private Spinner spinnerTime;
    private boolean dzenNoch;
    private long mLastClickTime = 0;
    private int itemDefault = 0;
    public final static int GET_DEFAULT_FONT_SIZE = 18;
    public final static int GET_FONT_SIZE_MIN = 14;
    public final static int GET_FONT_SIZE_MAX = 54;
    public final static int GET_CALIANDAR_YEAR_MIN = 2017;
    public final static int GET_CALIANDAR_YEAR_MAX = 2021;

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }

    @NonNull
    private String formatFigureTwoPlaces(float value) {
        DecimalFormat myFormatter = new DecimalFormat("##0.00");
        return myFormatter.format(value);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        super.onCreate(savedInstanceState);
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        int notification = k.getInt("notification", 2);
        if (dzenNoch)
            setTheme(R.style.AppCompatDark);
        setContentView(R.layout.settings_activity);
        prefEditor = k.edit();

        int vibr = k.getInt("vibra", 1);
        checkBoxV = findViewById(R.id.vibro);
        if (dzenNoch)
            checkBoxV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        checkBoxV.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        if (vibr == 0) checkBoxV.setChecked(false);

        int guk = k.getInt("guk", 1);
        checkBoxG = findViewById(R.id.guk);
        if (dzenNoch)
            checkBoxG.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        checkBoxG.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        if (guk == 0) checkBoxG.setChecked(false);

        spinnerTime = findViewById(R.id.spinnerTime);
        if (k.getInt("notification", 2) == 0)
            spinnerTime.setVisibility(View.GONE);
        ArrayList<DataTime> dataTimes = new ArrayList<>();
        for (int i = 6; i < 18; i++) {
            dataTimes.add(new DataTime("Паведамляць а " + i + "-й гадзіне", i));
        }
        for (DataTime time : dataTimes) {
            if (time.data == k.getInt("timeNotification", 8))
                break;
            itemDefault++;
        }
        spinnerTime.setAdapter(new timeAdapter(this, dataTimes));
        spinnerTime.setSelection(itemDefault);
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (itemDefault != i) {
                    prefEditor.putInt("timeNotification", dataTimes.get(i).data);
                    prefEditor.apply();
                    itemDefault = i;
                    spinnerTime.setEnabled(false);
                    new Thread(() -> {
                        setNotifications(SettingsActivity.this, notification);
                        SettingsActivity.this.runOnUiThread(() -> spinnerTime.setEnabled(true));
                    }).start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        settingsNotifiSviata = findViewById(R.id.notifiSvizta);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkBoxV.setVisibility(View.GONE);
            checkBoxG.setVisibility(View.GONE);
            long[] vibrate = new long[]{0, 1000, 700, 1000, 700, 1000};
            NotificationChannel channel = new NotificationChannel("3000", getResources().getString(R.string.sabytie), NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.setDescription(getResources().getString(R.string.sabytie));
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setLightColor(ContextCompat.getColor(this, R.color.colorPrimary));
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att);
            channel.enableVibration(true);
            channel.setVibrationPattern(vibrate);
            channel.enableLights(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            NotificationChannel channel2 = new NotificationChannel("2000", getResources().getString(R.string.SVIATY), NotificationManager.IMPORTANCE_HIGH);
            channel2.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel2.setDescription(getResources().getString(R.string.SVIATY));
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel2.setLightColor(ContextCompat.getColor(this, R.color.colorPrimary));
            channel2.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att);
            channel2.enableVibration(true);
            channel2.setVibrationPattern(vibrate);
            channel2.enableLights(true);
            notificationManager.createNotificationChannel(channel2);

            if (k.getInt("notification", 2) > 0) settingsNotifiSviata.setVisibility(View.VISIBLE);
            settingsNotifiSviata.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
            settingsNotifiSviata.setOnClickListener(view -> {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, "2000");
                startActivity(intent);
            });
            notificationManager.deleteNotificationChannel("by.carkva-gazeta");
        }
        TextView_Roboto_Condensed textView14 = findViewById(R.id.textView14);
        textView14.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        TextView_Roboto_Condensed textView15 = findViewById(R.id.textView15);
        textView15.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        TextView_Roboto_Condensed notificationView = findViewById(R.id.notificationView);
        notificationView.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        TextView_Roboto_Condensed textView57 = findViewById(R.id.textView57);
        textView57.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        TextView_Roboto_Condensed textsecret = findViewById(R.id.secret);
        View line = findViewById(R.id.line);
        View line1 = findViewById(R.id.line1);
        View line2 = findViewById(R.id.line2);
        View line3 = findViewById(R.id.line3);
        View line4 = findViewById(R.id.line4);

        if (dzenNoch) {
            textView14.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            textView15.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            notificationView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            textView57.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            textsecret.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            line.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            line1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            line2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            line3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            line4.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
        }

        File dir = new File(getFilesDir() + "/Site");
        long dirCount = 0;
        File[] dirContents = dir.listFiles();
        for (File dirContent : Objects.requireNonNull(dirContents)) {
            dirCount = dirCount + dirContent.length();
        }
        /*File dir2 = new File(getFilesDir() + "/image_temp");
        if (!dir2.exists()) {
            dir2.mkdir();
        }
        File[] dirContents2 = dir2.listFiles();
        for (File aDirContents2 : dirContents2) {
            dirCount = dirCount + aDirContents2.length();
        }*/

        pravas = findViewById(R.id.prav);
        if (dzenNoch)
            pravas.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        pravas.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);

        textsecret.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        pravas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putInt("pravas", 1);
            } else {
                prefEditor.putInt("pravas", 0);
            }
            prefEditor.apply();
        });
        pkc = findViewById(R.id.pkc);
        if (dzenNoch)
            pkc.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        pkc.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);

        pkc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putInt("pkc", 1);
            } else {
                prefEditor.putInt("pkc", 0);
            }
            prefEditor.apply();
        });
        dziar = findViewById(R.id.dzair);
        if (dzenNoch)
            dziar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        dziar.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        dziar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putInt("gosud", 1);
            } else {
                prefEditor.putInt("gosud", 0);
            }
            prefEditor.apply();
        });
        prafes = findViewById(R.id.praf);
        if (dzenNoch)
            prafes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        prafes.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        prafes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putInt("pafesii", 1);
            } else {
                prefEditor.putInt("pafesii", 0);
            }
            prefEditor.apply();
        });

        if (dzenNoch) {
            pravas.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            dziar.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            prafes.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            pkc.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
        }
        if (k.getInt("pkc", 0) == 1) pkc.setChecked(true);
        if (k.getInt("pravas", 0) == 1) pravas.setChecked(true);
        if (k.getInt("gosud", 0) == 1) dziar.setChecked(true);
        if (k.getInt("pafesii", 0) == 1) prafes.setChecked(true);
        TextView_Roboto_Condensed maranataOpis = findViewById(R.id.maranataOpis);
        maranataOpis.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        TextView_Roboto_Condensed button = findViewById(R.id.button);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        radioButtonOnly = findViewById(R.id.notificationOnly);
        radioButtonFull = findViewById(R.id.notificationFull);
        radioButtonNon = findViewById(R.id.notificationNon);
        radioButtonOnly.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        radioButtonFull.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        radioButtonNon.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        textView = findViewById(R.id.textView58);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        if (dirCount / 1024 > 1000) {
            textView.setText(getResources().getString(R.string.QUOTA_M, formatFigureTwoPlaces(BigDecimal.valueOf((float) dirCount / 1024 / 1024).setScale(2, RoundingMode.HALF_UP).floatValue())));
        } else {
            textView.setText(getResources().getString(R.string.QUOTA, formatFigureTwoPlaces(BigDecimal.valueOf((float) dirCount / 1024).setScale(2, RoundingMode.HALF_UP).floatValue())));
        }

        if (Build.MANUFACTURER.toLowerCase().contains("huawei")) {
            TextView_Roboto_Condensed notifi_help = findViewById(R.id.help_notifi);
            notifi_help.setVisibility(View.VISIBLE);
            notifi_help.setTextSize(GET_FONT_SIZE_MIN);
            notifi_help.setOnClickListener((v -> {
                Dialog_help_notification notifi = new Dialog_help_notification();
                notifi.show(getSupportFragmentManager(), "help_notification");
            }));
        }

        RadioGroup radiogroup2 = findViewById(R.id.notificationGrup);

        RadioGroup radiogroupMaranata = findViewById(R.id.maranataGrup);
        maranataBel = findViewById(R.id.maranataBel);
        maranataRus = findViewById(R.id.maranataRus);
        maranataBel.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        maranataRus.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        boolean belarus = k.getBoolean("belarus", false);
        if (belarus) {
            maranataBel.setChecked(true);
            maranataRus.setChecked(false);
        } else {
            maranataRus.setChecked(true);
            maranataBel.setChecked(false);
        }

        if (notification == 1) radioButtonOnly.setChecked(true);
        else radioButtonOnly.setChecked(false);
        if (notification == 2) radioButtonFull.setChecked(true);
        else radioButtonFull.setChecked(false);
        if (notification == 0) radioButtonNon.setChecked(true);
        else radioButtonNon.setChecked(false);

        int sinoidal = k.getInt("sinoidal", 0);
        sinoidalcheckBoxG = findViewById(R.id.sinoidal);
        if (dzenNoch)
            sinoidalcheckBoxG.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        sinoidalcheckBoxG.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        if (sinoidal == 1) sinoidalcheckBoxG.setChecked(true);

        int maranata = k.getInt("maranata", 0);
        maranatacheckBoxG = findViewById(R.id.maranata);
        if (dzenNoch)
            maranatacheckBoxG.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        maranatacheckBoxG.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        if (maranata == 1) {
            maranatacheckBoxG.setChecked(true);
        } else {
            maranataBel.setClickable(false);
            maranataRus.setClickable(false);
            maranataBel.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
            maranataRus.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
            maranataOpis.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
        }

        boolean dzen_noch_settings = k.getBoolean("dzen_noch", false);
        checkBox5 = findViewById(R.id.checkBox5);
        if (dzenNoch)
            checkBox5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        checkBox5.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        if (dzen_noch_settings) checkBox5.setChecked(true);

        int trafik = k.getInt("trafic", 0);
        checkBoxT = findViewById(R.id.checkBox2);
        if (dzenNoch)
            checkBoxT.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        checkBoxT.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        if (trafik == 1) checkBoxT.setChecked(true);
        TextView_Roboto_Condensed reset = findViewById(R.id.reset);
        if (dzenNoch)
            reset.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.knopka_red_black));
        reset.setTextSize(TypedValue.COMPLEX_UNIT_SP, GET_FONT_SIZE_MIN);
        reset.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            LinearLayout layout = new LinearLayout(this);
            if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
            else layout.setBackgroundResource(R.color.colorPrimary);
            float density = getResources().getDisplayMetrics().density;
            int realpadding = (int) (10 * density);
            TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(this);
            toast.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            toast.setPadding(realpadding, realpadding, realpadding, realpadding);
            toast.setText("Захавана");
            toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            layout.addView(toast);
            Toast mes = new Toast(this);
            mes.setDuration(Toast.LENGTH_SHORT);
            mes.setView(layout);
            mes.show();
            prefEditor.putBoolean("help_str", true);
            prefEditor.putInt("font_malitounik", GET_DEFAULT_FONT_SIZE);
            prefEditor.putBoolean("dzen_noch", false);
            prefEditor.putBoolean("FullscreenHelp", true);
            prefEditor.putInt("pravas", 0);
            prefEditor.putInt("pkc", 0);
            prefEditor.putInt("nedelia", 0);
            prefEditor.putInt("gosud", 0);
            prefEditor.putInt("pafesii", 0);
            prefEditor.putBoolean("belarus", false);
            prefEditor.putInt("notification", 2);
            prefEditor.putInt("power", 1);
            prefEditor.putInt("vibra", 1);
            prefEditor.putInt("guk", 1);
            prefEditor.putInt("sinoidal", 0);
            prefEditor.putInt("maranata", 0);
            prefEditor.putInt("soundnotification", 0);
            prefEditor.putInt("timeNotification", 8);
            maranataBel.setClickable(false);
            maranataRus.setClickable(false);
            maranataBel.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
            maranataRus.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
            maranataOpis.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
            prefEditor.putInt("trafic", 0);
            prefEditor.apply();
            checkBoxV.setClickable(true);
            checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
            checkBoxG.setClickable(true);
            checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
            checkBox5.setChecked(false);
            maranatacheckBoxG.setChecked(false);
            maranataRus.setChecked(true);
            maranataBel.setChecked(false);
            sinoidalcheckBoxG.setChecked(false);
            radioButtonOnly.setChecked(false);
            radioButtonFull.setChecked(true);
            radioButtonNon.setChecked(false);
            checkBoxV.setChecked(true);
            checkBoxG.setChecked(true);
            checkBoxT.setChecked(false);
            spinnerTime.setSelection(2);
            pkc.setChecked(false);
            pravas.setChecked(false);
            dziar.setChecked(false);
            prafes.setChecked(false);
            recreate();
        });

        ScrollView scrollView = findViewById(R.id.scrollView);

        if (dzenNoch) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
            }
            scrollView.setBackgroundResource(R.color.colorbackground_material_dark);
            radioButtonOnly.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            radioButtonFull.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            radioButtonNon.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            checkBox5.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            sinoidalcheckBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            maranatacheckBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            if (maranata != 0) {
                maranataBel.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                maranataRus.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                maranataOpis.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            }
            checkBoxT.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
        }

        radiogroupMaranata.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.maranataBel:
                    prefEditor.putBoolean("belarus", true);
                    Dialog_semuxa_no_knigi semuxa_no_knigi = new Dialog_semuxa_no_knigi();
                    semuxa_no_knigi.show(getSupportFragmentManager(), "semuxa_no_knigi");
                    break;
                case R.id.maranataRus:
                    prefEditor.putBoolean("belarus", false);
                    break;
                default:
                    break;
            }
            prefEditor.apply();
        });

        radiogroup2.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.notificationOnly:
                    settingsNotifiSviata.setVisibility(View.VISIBLE);
                    spinnerTime.setVisibility(View.VISIBLE);
                    if (k.getBoolean("check_notifi", true) && Build.MANUFACTURER.toLowerCase().contains("huawei")) {
                        Dialog_help_notification notifi = new Dialog_help_notification();
                        notifi.show(getSupportFragmentManager(), "help_notification");
                    }
                    prefEditor.putInt("notification", 1);
                    if (dzenNoch)
                        checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    else checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                    checkBoxG.setClickable(true);
                    if (dzenNoch)
                        checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    else checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                    new Thread(() -> {
                        SettingsActivity.this.runOnUiThread(() -> {
                            if (dzenNoch) {
                                checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                                checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                            } else {
                                checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                                checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                            }
                            radioButtonNon.setClickable(false);
                            radioButtonFull.setClickable(false);
                            radioButtonNon.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
                            radioButtonFull.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
                        });
                        setNotifications(SettingsActivity.this, 1);
                        SettingsActivity.this.runOnUiThread(() -> {
                            radioButtonNon.setClickable(true);
                            radioButtonFull.setClickable(true);
                            if (dzenNoch) {
                                radioButtonNon.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                                radioButtonFull.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                            } else {
                                radioButtonNon.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                                radioButtonFull.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                            }
                        });
                    }).start();
                    break;
                case R.id.notificationFull:
                    settingsNotifiSviata.setVisibility(View.VISIBLE);
                    spinnerTime.setVisibility(View.VISIBLE);
                    if (k.getBoolean("check_notifi", true) && Build.MANUFACTURER.toLowerCase().contains("huawei")) {
                        Dialog_help_notification notifi = new Dialog_help_notification();
                        notifi.show(getSupportFragmentManager(), "help_notification");
                    }
                    prefEditor.putInt("notification", 2);
                    if (dzenNoch)
                        checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    else checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                    checkBoxG.setClickable(true);
                    if (dzenNoch)
                        checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    else checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                    new Thread(() -> {
                        SettingsActivity.this.runOnUiThread(() -> {
                            if (dzenNoch) {
                                checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                                checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                            } else {
                                checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                                checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                            }
                            radioButtonOnly.setClickable(false);
                            radioButtonOnly.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
                            radioButtonNon.setClickable(false);
                            radioButtonNon.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
                        });
                        setNotifications(SettingsActivity.this, 2);
                        SettingsActivity.this.runOnUiThread(() -> {
                            radioButtonOnly.setClickable(true);
                            radioButtonNon.setClickable(true);
                            if (dzenNoch) {
                                radioButtonOnly.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                                radioButtonNon.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                            } else {
                                radioButtonOnly.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                                radioButtonNon.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                            }
                        });
                    }).start();
                    break;
                case R.id.notificationNon:
                    settingsNotifiSviata.setVisibility(View.GONE);
                    spinnerTime.setVisibility(View.GONE);
                    prefEditor.putInt("notification", 0);
                    if (dzenNoch)
                        checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    else checkBoxV.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                    checkBoxG.setClickable(true);
                    if (dzenNoch)
                        checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    else checkBoxG.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                    new Thread(() -> {
                        SettingsActivity.this.runOnUiThread(() -> {
                            radioButtonOnly.setClickable(false);
                            radioButtonOnly.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
                            radioButtonFull.setClickable(false);
                            radioButtonFull.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
                        });
                        setNotifications(SettingsActivity.this, 0);
                        SettingsActivity.this.runOnUiThread(() -> {
                            radioButtonOnly.setClickable(true);
                            radioButtonFull.setClickable(true);
                            if (dzenNoch) {
                                radioButtonOnly.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                                radioButtonFull.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                            } else {
                                radioButtonOnly.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                                radioButtonFull.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                            }
                        });
                    }).start();
                    break;
            }
            prefEditor.apply();
        });

        checkBoxV.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putInt("vibra", 1);
            } else {
                prefEditor.putInt("vibra", 0);
            }
            prefEditor.apply();
        });

        sinoidalcheckBoxG.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putInt("sinoidal", 1);
            } else {
                prefEditor.putInt("sinoidal", 0);
            }
            prefEditor.apply();
        });

        maranatacheckBoxG.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putInt("maranata", 1);
                maranataBel.setClickable(true);
                maranataRus.setClickable(true);
                if (dzenNoch) {
                    maranataBel.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    maranataRus.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    maranataOpis.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                } else {
                    maranataBel.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                    maranataRus.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                    maranataOpis.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                }
            } else {
                prefEditor.putInt("maranata", 0);
                maranataBel.setClickable(false);
                maranataRus.setClickable(false);
                maranataBel.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
                maranataRus.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
                maranataOpis.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary_text));
            }
            prefEditor.apply();
        });

        checkBoxG.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putInt("guk", 1);
            } else {
                prefEditor.putInt("guk", 0);
            }
            prefEditor.apply();
        });

        checkBoxT.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putInt("trafic", 1);
            } else {
                prefEditor.putInt("trafic", 0);
            }
            prefEditor.apply();
        });

        checkBox5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefEditor.putBoolean("dzen_noch", true);
            } else {
                prefEditor.putBoolean("dzen_noch", false);
            }
            prefEditor.apply();
            recreate();
        });

        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            for (File aDirContents1 : dirContents) {
                aDirContents1.delete();
            }
            /*for (File aDirContents2 : dirContents2) {
                aDirContents2.delete();
            }*/
            textView.setText(getResources().getString(R.string.QUOTA, formatFigureTwoPlaces(BigDecimal.valueOf((float) 0).setScale(2, RoundingMode.HALF_UP).floatValue())));
        });
        checkBoxV.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        checkBoxG.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        sinoidalcheckBoxG.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        maranatacheckBoxG.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        checkBoxT.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        pravas.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        pkc.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        dziar.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        prafes.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        checkBox5.setTypeface(TextView_Roboto_Condensed.createFont(Typeface.NORMAL));
        if (savedInstanceState == null && (notification == 1 || notification == 2)) {
            if (k.getBoolean("check_notifi", true) && Build.MANUFACTURER.toLowerCase().contains("huawei")) {
                Dialog_help_notification notifi = new Dialog_help_notification();
                notifi.show(getSupportFragmentManager(), "help_notification");
            }
        }

        setTollbarTheme();
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
        title_toolbar.setText(getResources().getText(R.string.tools_item));
        if (dzenNoch) {
            toolbar.setPopupTheme(R.style.AppCompatDark);
            toolbar.setBackgroundResource(R.color.colorprimary_material_dark);
        }
    }

    private static long mkTime(int year, int month, int day, int hour) {
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        calendar.set(year, month, day, hour, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private static int mkTimeDayOfYear(int year, int month, int day) {
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        calendar.set(year, month, day, 19, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    private static int mkTimeYear(int year, int month, int day) {
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        calendar.set(year, month, day, 19, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.get(Calendar.YEAR);
    }

    private static Intent createIntent(Context context, String action, String extra) {
        Intent intent = new Intent(context, ReceiverBroad.class);
        intent.setAction(action);
        intent.putExtra("extra", extra);
        return intent;
    }

    private static Intent createIntent(Context context, String action, String extra, int dayofyear, int year) {
        Intent intent = new Intent(context, ReceiverBroad.class);
        intent.setAction(action);
        intent.putExtra("extra", extra);
        intent.putExtra("dayofyear", dayofyear);
        intent.putExtra("year", year);
        return intent;
    }

    public static void setNotifications(@NonNull Context context, int notifications) {
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        Intent intent;
        PendingIntent pIntent;
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        int year = c.get(Calendar.YEAR), data_p, month_p;
        SharedPreferences chin = context.getSharedPreferences("biblia", MODE_PRIVATE);
        int timeNotification = chin.getInt("timeNotification", 8);
        for (int i = 0; i < 2; i++) {
            year = year + i;
            int a = year % 19;
            int b = year % 4;
            int cx = year % 7;
            int k = (year / 100);
            int p = (13 + 8 * k) / 25;
            int q = (k / 4);
            int m = (15 - p + k - q) % 30;
            int n = (4 + k - q) % 7;
            int d = (19 * a + m) % 30;
            int ex = (2 * b + 4 * cx + 6 * d + n) % 7;
            if (d + ex <= 9) {
                data_p = d + ex + 22;
                month_p = 3;
            } else {
                data_p = d + ex - 9;
                if (d == 29 && ex == 6) data_p = 19;
                if (d == 28 && ex == 6) data_p = 18;
                month_p = 4;
            }
            if (notifications != 0) {
                if (c.getTimeInMillis() < mkTime(year, month_p - 1, data_p - 1, 19)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S1), context.getResources().getString(R.string.Sv3), mkTimeDayOfYear(year, month_p - 1, data_p), mkTimeYear(year, month_p, data_p - 1));  // Абавязковае
                    String code = "1" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, month_p - 1, data_p - 1, 19), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, month_p - 1, data_p - 1, 19), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, month_p - 1, data_p - 1, 19), pIntent);
                    }
                }
                if (c.getTimeInMillis() < mkTime(year, month_p - 1, data_p, timeNotification)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S1), context.getResources().getString(R.string.Sv4));  // Абавязковае
                    String code = "2" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, month_p - 1, data_p, timeNotification), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, month_p - 1, data_p, timeNotification), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, month_p - 1, data_p, timeNotification), pIntent);
                    }
                }

                if (c.getTimeInMillis() < mkTime(year, 0, 5, 19)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S2), context.getResources().getString(R.string.Sv3), mkTimeDayOfYear(year, 0, 6), mkTimeYear(year, 0, 6));  // Абавязковае
                    String code = "3" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 0, 5, 19), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 0, 5, 19), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 0, 5, 19), pIntent);
                    }
                }
                if (c.getTimeInMillis() < mkTime(year, 0, 6, timeNotification)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S2), context.getResources().getString(R.string.Sv4));  // Абавязковае
                    String code = "4" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 0, 6, timeNotification), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 0, 6, timeNotification), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 0, 6, timeNotification), pIntent);
                    }
                }

                Calendar cet = Calendar.getInstance();
                cet.set(year, month_p - 1, data_p - 1);
                cet.add(Calendar.DATE, -7);
                if (c.getTimeInMillis() < mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S5), context.getResources().getString(R.string.Sv3), mkTimeDayOfYear(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH) + 1), mkTimeYear(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH) + 1));  // Абавязковае
                    String code = "5" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19), pIntent);
                    }
                }
                cet.add(Calendar.DATE, 1);
                if (c.getTimeInMillis() < mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S5), context.getResources().getString(R.string.Sv4));  // Абавязковае
                    String code = "6" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification), pIntent);
                    }
                }

                cet.set(year, month_p - 1, data_p - 1);
                cet.add(Calendar.DATE, +39);
                if (c.getTimeInMillis() < mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S6), context.getResources().getString(R.string.Sv3), mkTimeDayOfYear(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH) + 1), mkTimeYear(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH) + 1));  // Абавязковае
                    String code = "7" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19), pIntent);
                    }
                }
                cet.add(Calendar.DATE, 1);
                if (c.getTimeInMillis() < mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S6), context.getResources().getString(R.string.Sv4));  // Абавязковае
                    String code = "8" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification), pIntent);
                    }
                }

                cet.set(year, month_p - 1, data_p - 1);
                cet.add(Calendar.DATE, +49);
                if (c.getTimeInMillis() < mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S7), context.getResources().getString(R.string.Sv3), mkTimeDayOfYear(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH) + 1), mkTimeYear(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH) + 1));  // Абавязковае
                    String code = "9" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), 19), pIntent);
                    }
                }
                cet.add(Calendar.DATE, 1);
                if (c.getTimeInMillis() < mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S7), context.getResources().getString(R.string.Sv4));  // Абавязковае
                    String code = "10" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, cet.get(Calendar.MONTH), cet.get(Calendar.DAY_OF_MONTH), timeNotification), pIntent);
                    }
                }
                if (c.getTimeInMillis() < mkTime(year, 2, 24, 19)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S4), context.getResources().getString(R.string.Sv3), mkTimeDayOfYear(year, 2, 25), mkTimeYear(year, 2, 25));  // Абавязковае
                    String code = "11" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 2, 24, 19), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 2, 24, 19), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 2, 24, 19), pIntent);
                    }
                }
                if (c.getTimeInMillis() < mkTime(year, 2, 25, timeNotification)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S4), context.getResources().getString(R.string.Sv4));  // Абавязковае
                    String code = "12" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 2, 25, timeNotification), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 2, 25, timeNotification), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 2, 25, timeNotification), pIntent);
                    }
                }

                if (c.getTimeInMillis() < mkTime(year, 7, 14, 19)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S9), context.getResources().getString(R.string.Sv3), mkTimeDayOfYear(year, 7, 15), mkTimeYear(year, 7, 15));  // Абавязковае
                    String code = "13" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 14, 19), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 14, 19), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 14, 19), pIntent);
                    }
                }
                if (c.getTimeInMillis() < mkTime(year, 7, 15, timeNotification)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S9), context.getResources().getString(R.string.Sv4));  // Абавязковае
                    String code = "14" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 15, timeNotification), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 15, timeNotification), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 15, timeNotification), pIntent);
                    }
                }

                if (c.getTimeInMillis() < mkTime(year, 11, 24, 19)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S13), context.getResources().getString(R.string.Sv3), mkTimeDayOfYear(year, 11, 25), mkTimeYear(year, 11, 25));  // Абавязковае
                    String code = "15" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 11, 24, 19), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 11, 24, 19), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 11, 24, 19), pIntent);
                    }
                }
                if (c.getTimeInMillis() < mkTime(year, 11, 25, timeNotification)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S13), context.getResources().getString(R.string.Sv4));  // Абавязковае
                    String code = "16" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 11, 25, timeNotification), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 11, 25, timeNotification), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 11, 25, timeNotification), pIntent);
                    }
                }

                if (c.getTimeInMillis() < mkTime(year, 5, 28, 19)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S16), context.getResources().getString(R.string.Sv3), mkTimeDayOfYear(year, 5, 29), mkTimeYear(year, 5, 29));  // Абавязковае
                    String code = "17" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 28, 19), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 28, 19), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 28, 19), pIntent);
                    }
                }
                if (c.getTimeInMillis() < mkTime(year, 5, 29, timeNotification)) {
                    intent = createIntent(context, context.getResources().getString(R.string.S16), context.getResources().getString(R.string.Sv4));  // Абавязковае
                    String code = "18" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 29, timeNotification), pIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 29, timeNotification), pIntent);
                    } else {
                        Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 29, timeNotification), pIntent);
                    }
                }
                if (notifications == 2) {
                    if (c.getTimeInMillis() < mkTime(year, 1, 1, 19)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S3), context.getResources().getString(R.string.Sv1), mkTimeDayOfYear(year, 1, 2), mkTimeYear(year, 1, 2));
                        String code = "19" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 1, 1, 19), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 1, 1, 19), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 1, 1, 19), pIntent);
                        }
                    }
                    if (c.getTimeInMillis() < mkTime(year, 1, 2, timeNotification)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S3), context.getResources().getString(R.string.Sv2));
                        String code = "20" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 1, 2, timeNotification), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 1, 2, timeNotification), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 1, 2, timeNotification), pIntent);
                        }
                    }

                    if (c.getTimeInMillis() < mkTime(year, 7, 5, 19)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S8), context.getResources().getString(R.string.Sv1), mkTimeDayOfYear(year, 7, 6), mkTimeYear(year, 7, 6));
                        String code = "21" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 5, 19), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 5, 19), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 5, 19), pIntent);
                        }
                    }
                    if (c.getTimeInMillis() < mkTime(year, 7, 6, timeNotification)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S8), context.getResources().getString(R.string.Sv2));
                        String code = "22" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 6, timeNotification), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 6, timeNotification), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 6, timeNotification), pIntent);
                        }
                    }

                    if (c.getTimeInMillis() < mkTime(year, 8, 7, 19)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S10), context.getResources().getString(R.string.Sv1), mkTimeDayOfYear(year, 8, 8), mkTimeYear(year, 8, 8));
                        String code = "23" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 7, 19), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 7, 19), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 7, 19), pIntent);
                        }
                    }
                    if (c.getTimeInMillis() < mkTime(year, 8, 8, timeNotification)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S10), context.getResources().getString(R.string.Sv2));
                        String code = "24" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 8, timeNotification), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 8, timeNotification), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 8, timeNotification), pIntent);
                        }
                    }

                    if (c.getTimeInMillis() < mkTime(year, 8, 13, 19)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S11), context.getResources().getString(R.string.Sv1), mkTimeDayOfYear(year, 8, 14), mkTimeYear(year, 8, 14));
                        String code = "25" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 13, 19), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 13, 19), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 13, 19), pIntent);
                        }
                    }
                    if (c.getTimeInMillis() < mkTime(year, 8, 14, timeNotification)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S11), context.getResources().getString(R.string.Sv2));
                        String code = "26" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 14, timeNotification), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 14, timeNotification), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 14, timeNotification), pIntent);
                        }
                    }

                    if (c.getTimeInMillis() < mkTime(year, 10, 20, 19)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S12), context.getResources().getString(R.string.Sv1), mkTimeDayOfYear(year, 10, 21), mkTimeYear(year, 10, 21));
                        String code = "27" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 10, 20, 19), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 10, 20, 19), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 10, 20, 19), pIntent);
                        }
                    }
                    if (c.getTimeInMillis() < mkTime(year, 10, 21, timeNotification)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S12), context.getResources().getString(R.string.Sv2));
                        String code = "28" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 10, 21, timeNotification), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 10, 21, timeNotification), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 10, 21, timeNotification), pIntent);
                        }
                    }

                    if (c.getTimeInMillis() < mkTime(year, 11, 31, 19)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S14), context.getResources().getString(R.string.Sv1), mkTimeDayOfYear(year + 1, 0, 1), mkTimeYear(year + 1, 0, 1));
                        String code = "29" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 11, 31, 19), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 11, 31, 19), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 11, 31, 19), pIntent);
                        }
                    }
                    if (c.getTimeInMillis() < mkTime(year, 0, 1, timeNotification)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S14), context.getResources().getString(R.string.Sv2));
                        String code = "30" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 0, 1, timeNotification), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 0, 1, timeNotification), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 0, 1, timeNotification), pIntent);
                        }
                    }

                    if (c.getTimeInMillis() < mkTime(year, 5, 23, 19)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S15), context.getResources().getString(R.string.Sv1), mkTimeDayOfYear(year, 5, 24), mkTimeYear(year, 5, 24));
                        String code = "31" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 23, 19), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 23, 19), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 23, 19), pIntent);
                        }
                    }
                    if (c.getTimeInMillis() < mkTime(year, 5, 24, timeNotification)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S15), context.getResources().getString(R.string.Sv2));
                        String code = "32" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 24, timeNotification), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 24, timeNotification), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 5, 24, timeNotification), pIntent);
                        }
                    }

                    if (c.getTimeInMillis() < mkTime(year, 7, 28, 19)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S17), context.getResources().getString(R.string.Sv1), mkTimeDayOfYear(year, 7, 29), mkTimeYear(year, 7, 29));
                        String code = "33" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 28, 19), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 28, 19), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 28, 19), pIntent);
                        }
                    }
                    if (c.getTimeInMillis() < mkTime(year, 7, 29, timeNotification)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S17), context.getResources().getString(R.string.Sv2));
                        String code = "34" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 29, timeNotification), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 29, timeNotification), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 7, 29, timeNotification), pIntent);
                        }
                    }

                    if (c.getTimeInMillis() < mkTime(year, 8, 30, 19)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S18), context.getResources().getString(R.string.Sv1), mkTimeDayOfYear(year, 9, 1), mkTimeYear(year, 9, 1));
                        String code = "35" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 30, 19), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 30, 19), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 8, 30, 19), pIntent);
                        }
                    }
                    if (c.getTimeInMillis() < mkTime(year, 9, 1, timeNotification)) {
                        intent = createIntent(context, context.getResources().getString(R.string.S18), context.getResources().getString(R.string.Sv2));
                        String code = "36" + year;
                        pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(year, 9, 1, timeNotification), pIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(year, 9, 1, timeNotification), pIntent);
                        } else {
                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(year, 9, 1, timeNotification), pIntent);
                        }
                    }
                }
            }
            if (notifications == 1 || notifications == 0) {
                String code;
                if (notifications != 1) {
                    intent = createIntent(context, context.getResources().getString(R.string.S1), context.getResources().getString(R.string.Sv1));  // Абавязковае
                    code = "1" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    Objects.requireNonNull(am).cancel(pIntent);
                    intent = createIntent(context, context.getResources().getString(R.string.S1), context.getResources().getString(R.string.Sv2));  // Абавязковае
                    code = "2" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);

                    intent = createIntent(context, context.getResources().getString(R.string.S2), context.getResources().getString(R.string.Sv1));  // Абавязковае
                    code = "3" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);
                    intent = createIntent(context, context.getResources().getString(R.string.S2), context.getResources().getString(R.string.Sv2));  // Абавязковае
                    code = "4" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);

                    intent = createIntent(context, context.getResources().getString(R.string.S5), context.getResources().getString(R.string.Sv1));  // Абавязковае
                    code = "5" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);
                    intent = createIntent(context, context.getResources().getString(R.string.S5), context.getResources().getString(R.string.Sv2));  // Абавязковае
                    code = "6" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);

                    intent = createIntent(context, context.getResources().getString(R.string.S6), context.getResources().getString(R.string.Sv1));  // Абавязковае
                    code = "7" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);
                    intent = createIntent(context, context.getResources().getString(R.string.S6), context.getResources().getString(R.string.Sv2));  // Абавязковае
                    code = "8" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);

                    intent = createIntent(context, context.getResources().getString(R.string.S7), context.getResources().getString(R.string.Sv1));  // Абавязковае
                    code = "9" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);
                    intent = createIntent(context, context.getResources().getString(R.string.S7), context.getResources().getString(R.string.Sv2));  // Абавязковае
                    code = "10" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);

                    intent = createIntent(context, context.getResources().getString(R.string.S4), context.getResources().getString(R.string.Sv1));  // Абавязковае
                    code = "11" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);
                    intent = createIntent(context, context.getResources().getString(R.string.S4), context.getResources().getString(R.string.Sv2));  // Абавязковае
                    code = "12" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);

                    intent = createIntent(context, context.getResources().getString(R.string.S9), context.getResources().getString(R.string.Sv1));  // Абавязковае
                    code = "13" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);
                    intent = createIntent(context, context.getResources().getString(R.string.S9), context.getResources().getString(R.string.Sv2));  // Абавязковае
                    code = "14" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);

                    intent = createIntent(context, context.getResources().getString(R.string.S13), context.getResources().getString(R.string.Sv1));  // Абавязковае
                    code = "15" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);
                    intent = createIntent(context, context.getResources().getString(R.string.S13), context.getResources().getString(R.string.Sv2));  // Абавязковае
                    code = "16" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);

                    intent = createIntent(context, context.getResources().getString(R.string.S16), context.getResources().getString(R.string.Sv1));  // Абавязковае
                    code = "17" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);
                    intent = createIntent(context, context.getResources().getString(R.string.S16), context.getResources().getString(R.string.Sv2));  // Абавязковае
                    code = "18" + year;
                    pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                    am.cancel(pIntent);
                }

                intent = createIntent(context, context.getResources().getString(R.string.S3), context.getResources().getString(R.string.Sv1));
                code = "19" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                Objects.requireNonNull(am).cancel(pIntent);
                intent = createIntent(context, context.getResources().getString(R.string.S3), context.getResources().getString(R.string.Sv2));
                code = "20" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);

                intent = createIntent(context, context.getResources().getString(R.string.S8), context.getResources().getString(R.string.Sv1));
                code = "21" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);
                intent = createIntent(context, context.getResources().getString(R.string.S8), context.getResources().getString(R.string.Sv2));
                code = "22" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);

                intent = createIntent(context, context.getResources().getString(R.string.S10), context.getResources().getString(R.string.Sv1));
                code = "23" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);
                intent = createIntent(context, context.getResources().getString(R.string.S10), context.getResources().getString(R.string.Sv2));
                code = "24" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);

                intent = createIntent(context, context.getResources().getString(R.string.S11), context.getResources().getString(R.string.Sv1));
                code = "25" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);
                intent = createIntent(context, context.getResources().getString(R.string.S11), context.getResources().getString(R.string.Sv2));
                code = "26" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);

                intent = createIntent(context, context.getResources().getString(R.string.S12), context.getResources().getString(R.string.Sv1));
                code = "27" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);
                intent = createIntent(context, context.getResources().getString(R.string.S12), context.getResources().getString(R.string.Sv2));
                code = "28" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);

                intent = createIntent(context, context.getResources().getString(R.string.S14), context.getResources().getString(R.string.Sv1));
                code = "29" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);
                intent = createIntent(context, context.getResources().getString(R.string.S14), context.getResources().getString(R.string.Sv2));
                code = "30" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);

                intent = createIntent(context, context.getResources().getString(R.string.S15), context.getResources().getString(R.string.Sv1));
                code = "31" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);
                intent = createIntent(context, context.getResources().getString(R.string.S15), context.getResources().getString(R.string.Sv2));
                code = "32" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);

                intent = createIntent(context, context.getResources().getString(R.string.S17), context.getResources().getString(R.string.Sv1));
                code = "33" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);
                intent = createIntent(context, context.getResources().getString(R.string.S17), context.getResources().getString(R.string.Sv2));
                code = "34" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);

                intent = createIntent(context, context.getResources().getString(R.string.S18), context.getResources().getString(R.string.Sv1));
                code = "35" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);
                intent = createIntent(context, context.getResources().getString(R.string.S18), context.getResources().getString(R.string.Sv2));
                code = "36" + year;
                pIntent = PendingIntent.getBroadcast(context, Integer.parseInt(code), intent, 0);
                am.cancel(pIntent);
            }
        }
    }

    private static class timeAdapter extends ArrayAdapter<DataTime> {

        private final Activity activity;
        private final ArrayList<DataTime> dataTimes;

        timeAdapter(@NonNull Activity context, ArrayList<DataTime> dataTime) {
            super(context, R.layout.simple_list_item_1, dataTime);
            activity = context;
            dataTimes = dataTime;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = super.getDropDownView(position, convertView, parent);
            TextView_Roboto_Condensed textView = (TextView_Roboto_Condensed) v;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
            textView.setText(dataTimes.get(position).string);
            return v;
        }

        @Override
        public int getCount() {
            return dataTimes.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = activity.getLayoutInflater().inflate(R.layout.simple_list_item_1, parent, false);
                viewHolder = new ViewHolder();
                convertView.setTag(viewHolder);
                viewHolder.text = convertView.findViewById(R.id.text1);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
            viewHolder.text.setText(dataTimes.get(position).string);
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }

    private static class DataTime {
        private final String string;
        private final int data;

        DataTime(String string, int data) {
            this.string = string;
            this.data = data;
        }
    }
}

