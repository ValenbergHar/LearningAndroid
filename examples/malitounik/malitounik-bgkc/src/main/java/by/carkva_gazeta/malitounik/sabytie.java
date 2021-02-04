package by.carkva_gazeta.malitounik;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Objects;

import static by.carkva_gazeta.malitounik.MainActivity.padzeia;

/**
 * Created by oleg on 8.5.17
 */

public class sabytie extends AppCompatActivity implements Dialog_sabytie_save.Dialog_sabytie_save_Listener, Dialog_context_menu_sabytie.Dialog_context_menu_sabytie_Listener, Dialog_delite.Dialog_delite_Listener {

    private SharedPreferences k;
    private boolean dzenNoch;
    private boolean konec = false;
    private boolean back = false;
    private boolean home = false;
    private boolean redak = false;
    private boolean save = false;
    private SabytieAdapter adapter;
    private final ArrayList<String> sabytie2 = new ArrayList<>();
    private EditText_Roboto_Condensed editText;
    private EditText_Roboto_Condensed editText2;
    private EditText_Roboto_Condensed editText4;
    private TextView_Roboto_Condensed textViewDate;
    private TextView_Roboto_Condensed textViewTime;
    private TextView_Roboto_Condensed textViewDateK;
    private TextView_Roboto_Condensed textViewTimeK;
    private TextView_Roboto_Condensed labelbutton12;
    private Spinner spinner;
    private Spinner spinner4;
    private Spinner spinner5;
    private GregorianCalendar c;
    private int timeH;
    private int timeM;
    private int posit = 0;
    private int nomer = 0;
    private int YearG;
    private int MunG;
    private int idMenu = 1;
    private int repitL = 0;
    private int radio = 1;
    private int color = 0;
    private int vybtimeSave;
    private int repitSave;
    private int colorSave;
    private String ta;
    private String da;
    private String taK;
    private String daK;
    private String filename;
    private String time = "0";
    private String editSave;
    private String edit2Save;
    private String daSave;
    private String taSave;
    private String daKSave;
    private String taKSave;
    private String editText4Save;
    private String labelbutton12Save;
    private int radioSave;
    private long result;
    private AlarmManager am;
    private ScrollView titleLoy;
    private LinearLayout listLoy;
    private LinearLayout radioButton2a;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private Menu menu;
    private long mLastClickTime = 0;
    private static final String[] colors = {"#D00505", "#800080", "#C71585", "#FF00FF", "#F4A460", "#D2691E", "#A52A2A", "#1E90FF", "#6A5ACD", "#228B22", "#9ACD32", "#20B2AA"};

    public static String[] getColors(@NonNull Context context) {
        SharedPreferences k = context.getSharedPreferences("biblia", MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) {
            colors[0] = "#f44336";
        } else {
            colors[0] = "#D00505";
        }
        return colors;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) {
            setTheme(R.style.AppCompatDark);
            colors[0] = "#f44336";
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sabytie);
        if (savedInstanceState != null) {
            redak = savedInstanceState.getBoolean("redak");
            back = savedInstanceState.getBoolean("back");
        }
        titleLoy = findViewById(R.id.titleLayout);
        listLoy = findViewById(R.id.listLayout);
        editText = findViewById(R.id.editText);
        textViewDate = findViewById(R.id.label1);
        textViewTime = findViewById(R.id.label2);
        textViewDateK = findViewById(R.id.label12);
        textViewTimeK = findViewById(R.id.label22);
        editText2 = findViewById(R.id.editText2);
        spinner = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton2a = findViewById(R.id.radioButton2a);
        labelbutton12 = findViewById(R.id.labelbutton12);
        labelbutton12.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            String[] w = labelbutton12.getText().toString().split("[.]");
            GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(w[2]), Integer.parseInt(w[1]) - 1, Integer.parseInt(w[0]));
            YearG = gc.get(Calendar.YEAR);
            MunG = gc.get(Calendar.MONTH);
            Intent i = new Intent(sabytie.this, caliandar_mun.class);
            i.putExtra("day", gc.get(Calendar.DATE));
            i.putExtra("year", YearG);
            i.putExtra("mun", MunG);
            i.putExtra("sabytie", true);
            startActivityForResult(i, 1093);
        });
        editText4 = findViewById(R.id.editText4);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButton1:
                    radio = 1;
                    radioButton2a.setVisibility(View.GONE);
                    labelbutton12.setVisibility(View.GONE);
                    break;
                case R.id.radioButton2:
                    radio = 2;
                    radioButton2a.setVisibility(View.VISIBLE);
                    labelbutton12.setVisibility(View.GONE);
                    break;
                case R.id.radioButton3:
                    if (idMenu != 3) {
                        String[] w = labelbutton12.getText().toString().split("[.]");
                        GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(w[2]), Integer.parseInt(w[1]) - 1, Integer.parseInt(w[0]));
                        YearG = gc.get(Calendar.YEAR);
                        MunG = gc.get(Calendar.MONTH);
                        Intent i = new Intent(sabytie.this, caliandar_mun.class);
                        i.putExtra("day", gc.get(Calendar.DATE));
                        i.putExtra("year", YearG);
                        i.putExtra("mun", MunG);
                        i.putExtra("sabytie", true);
                        startActivityForResult(i, 1093);
                    }
                    radio = 3;
                    radioButton2a.setVisibility(View.GONE);
                    labelbutton12.setVisibility(View.VISIBLE);
                    break;
            }
        });
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        c = (GregorianCalendar) Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        String nol1 = "", nol2 = "";
        if (c.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
        if (c.get(Calendar.MONTH) < 9) nol2 = "0";
        labelbutton12.setText(getResources().getString(R.string.Sabytie, nol1, c.get(Calendar.DAY_OF_MONTH), nol2, (c.get(Calendar.MONTH) + 1), c.get(Calendar.YEAR)));
        c.add(Calendar.DATE, -1);
        result = c.getTimeInMillis();
        c.add(Calendar.HOUR_OF_DAY, 1);
        timeH = c.get(Calendar.HOUR_OF_DAY);
        timeM = 0;
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), timeH, timeM, 0);
        nol1 = "";
        nol2 = "";
        if (c.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
        if (c.get(Calendar.MONTH) < 9) nol2 = "0";
        YearG = c.get(Calendar.YEAR);
        MunG = c.get(Calendar.MONTH);
        da = nol1 + c.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR);
        ta = timeH + ":00";
        spinner5.setSelection(0);
        color = 0;
        textViewDate.setText(da);

        String[] notifi = {"хвілінаў", "часоў", "дзён", "тыдняў"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                R.layout.simple_list_item_1, notifi);
        spinner.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posit = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        String[] repit = {"Няма", "Кожны дзень", "Па будных днях", "Два дні праз два", "Кожны тыдзень", "Кожныя два тыдні", "Кожныя чатыры тыдні", "Кожны месяц", "Раз на год"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                R.layout.simple_list_item_1, repit);
        spinner4.setAdapter(adapter3);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(spinner4.getWindowToken(), 0);
                repitL = position;
                if (repitL == 7) {
                    radioButton3.setClickable(false);
                    radioButton3.setTextColor(ContextCompat.getColor(sabytie.this, R.color.colorSecondary_text));
                } else {
                    radioButton3.setClickable(true);
                    if (dzenNoch)
                        radioButton3.setTextColor(ContextCompat.getColor(sabytie.this, R.color.colorIcons));
                    else
                        radioButton3.setTextColor(ContextCompat.getColor(sabytie.this, R.color.colorPrimary_text));
                }
                if (repitL == 0) radioButton1.setChecked(true);
                if (repitL > 0) {
                    radioGroup.setVisibility(View.VISIBLE);
                } else {
                    radioGroup.setVisibility(View.GONE);
                    radioButton2a.setVisibility(View.GONE);
                    labelbutton12.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> adapter5 = new ColorAdapter(this);
        spinner5.setAdapter(adapter5);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color = position;
                spinner5.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        textViewDate.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            String[] w = textViewDate.getText().toString().split("[.]");
            GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(w[2]), Integer.parseInt(w[1]) - 1, Integer.parseInt(w[0]));
            YearG = gc.get(Calendar.YEAR);
            MunG = gc.get(Calendar.MONTH);
            Intent i = new Intent(sabytie.this, caliandar_mun.class);
            i.putExtra("day", gc.get(Calendar.DATE));
            i.putExtra("year", YearG);
            i.putExtra("mun", MunG);
            i.putExtra("sabytie", true);
            startActivityForResult(i, 109);
        });
        textViewTime.setText(ta);
        textViewTime.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            AlertDialog.Builder ad = new AlertDialog.Builder(sabytie.this);
            LinearLayout linearLayout = new LinearLayout(sabytie.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            ad.setView(linearLayout);
            final TimePicker timePicker = new TimePicker(sabytie.this);
            timePicker.setIs24HourView(true);
            String[] settime = textViewTime.getText().toString().split("[:]");
            GregorianCalendar gc = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), Integer.parseInt(settime[0]), Integer.parseInt(settime[1]), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(gc.get(Calendar.HOUR_OF_DAY));
                timePicker.setMinute(gc.get(Calendar.MINUTE));
            } else {
                timePicker.setCurrentHour(gc.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(gc.get(Calendar.MINUTE));
            }
            linearLayout.addView(timePicker);
            ad.setTitle("Выберыце час");
            ad.setPositiveButton(getResources().getString(R.string.ok), (dialog, arg1) -> {
                da = textViewDate.getText().toString();
                daK = textViewDateK.getText().toString();
                taK = textViewTimeK.getText().toString();
                c = (GregorianCalendar) Calendar.getInstance();
                c.setTimeInMillis(result);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timeH = timePicker.getHour();
                    timeM = timePicker.getMinute();
                } else {
                    timeH = timePicker.getCurrentHour();
                    timeM = timePicker.getCurrentMinute();
                }
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), timeH, timeM, 0);
                result = c.getTimeInMillis();
                String tr = "";
                if (timeM < 10) tr = "0";
                ta = timeH + ":" + tr + timeM;
                String[] date = da.split("[.]");
                GregorianCalendar gc12 = new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0]), 0, 0, 0);
                String[] dateK = daK.split("[.]");
                String[] timeK = taK.split("[:]");
                GregorianCalendar gcK = new GregorianCalendar(Integer.parseInt(dateK[2]), Integer.parseInt(dateK[1]) - 1, Integer.parseInt(dateK[0]), 0, 0, 0);
                if (gc12.getTimeInMillis() == gcK.getTimeInMillis()) {
                    gc12 = new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0]), timeH, timeM, 0);
                    gcK = new GregorianCalendar(Integer.parseInt(dateK[2]), Integer.parseInt(dateK[1]) - 1, Integer.parseInt(dateK[0]), Integer.parseInt(timeK[0]), Integer.parseInt(timeK[1]), 0);
                    if (gc12.getTimeInMillis() > gcK.getTimeInMillis()) {
                        textViewTimeK.setText(ta);
                    }
                }
                textViewTime.setText(ta);
            });
            ad.setNegativeButton("Адмена", (dialog, arg1) -> dialog.cancel());
            AlertDialog alert = ad.create();
            alert.setOnShowListener(dialog -> {
                Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                Button btnNegative = alert.getButton(Dialog.BUTTON_NEGATIVE);
                btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            });
            alert.show();
        });
        textViewDateK.setText(da);
        textViewDateK.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            String[] w = textViewDateK.getText().toString().split("[.]");
            GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(w[2]), Integer.parseInt(w[1]) - 1, Integer.parseInt(w[0]));
            YearG = gc.get(Calendar.YEAR);
            MunG = gc.get(Calendar.MONTH);
            Intent i = new Intent(sabytie.this, caliandar_mun.class);
            i.putExtra("day", gc.get(Calendar.DATE));
            i.putExtra("year", YearG);
            i.putExtra("mun", MunG);
            i.putExtra("sabytie", true);
            startActivityForResult(i, 1092);
        });
        textViewTimeK.setText(ta);
        textViewTimeK.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            AlertDialog.Builder ad = new AlertDialog.Builder(sabytie.this);
            LinearLayout linearLayout = new LinearLayout(sabytie.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            ad.setView(linearLayout);
            final TimePicker timePicker = new TimePicker(sabytie.this);
            timePicker.setIs24HourView(true);
            String[] settime = textViewTimeK.getText().toString().split("[:]");
            GregorianCalendar gc = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), Integer.parseInt(settime[0]), Integer.parseInt(settime[1]), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(gc.get(Calendar.HOUR_OF_DAY));
                timePicker.setMinute(gc.get(Calendar.MINUTE));
            } else {
                timePicker.setCurrentHour(gc.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(gc.get(Calendar.MINUTE));
            }
            linearLayout.addView(timePicker);
            ad.setTitle("Выберыце час");
            ad.setPositiveButton(getResources().getString(R.string.ok), (dialog, arg1) -> {
                int timeHK;
                int timeMK;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timeHK = timePicker.getHour();
                    timeMK = timePicker.getMinute();
                } else {
                    timeHK = timePicker.getCurrentHour();
                    timeMK = timePicker.getCurrentMinute();
                }
                String tr = "";
                if (timeMK < 10) tr = "0";
                taK = timeHK + ":" + tr + timeMK;
                textViewTimeK.setText(taK);
                konec = true;
                String[] days = textViewDate.getText().toString().split("[.]");
                GregorianCalendar gc1 = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), 0, 0, 0);
                String[] days2 = textViewDateK.getText().toString().split("[.]");
                GregorianCalendar gc2 = new GregorianCalendar(Integer.parseInt(days2[2]), Integer.parseInt(days2[1]) - 1, Integer.parseInt(days2[0]), 0, 0, 0);
                int kon = gc2.get(Calendar.DAY_OF_YEAR);
                int res = gc1.get(Calendar.DAY_OF_YEAR);
                if (kon - res == 0) {
                    String[] times = textViewTime.getText().toString().split("[:]");
                    String[] times2 = textViewTimeK.getText().toString().split("[:]");
                    GregorianCalendar gc3 = new GregorianCalendar(gc2.get(Calendar.YEAR), gc2.get(Calendar.MONTH), gc2.get(Calendar.DAY_OF_MONTH), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                    GregorianCalendar gc4 = new GregorianCalendar(gc2.get(Calendar.YEAR), gc2.get(Calendar.MONTH), gc2.get(Calendar.DAY_OF_MONTH), Integer.parseInt(times2[0]), Integer.parseInt(times2[1]), 0);
                    if (gc4.getTimeInMillis() - gc3.getTimeInMillis() < 1000) {
                        gc2.add(Calendar.DATE, 1);
                        String nol112 = "", nol212 = "";
                        if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol112 = "0";
                        if (gc2.get(Calendar.MONTH) < 9) nol212 = "0";
                        String da1 = nol112 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol212 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR);
                        textViewDateK.setText(da1);
                    }
                }
            });
            ad.setNegativeButton("Адмена", (dialog, arg1) -> dialog.cancel());
            AlertDialog alert = ad.create();
            alert.setOnShowListener(dialog -> {
                Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                Button btnNegative = alert.getButton(Dialog.BUTTON_NEGATIVE);
                btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            });
            alert.show();
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        TextView_Roboto_Condensed title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setOnClickListener((v) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
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
        title_toolbar.setText(getResources().getString(R.string.sabytie));
        if (dzenNoch) {
            toolbar.setPopupTheme(R.style.AppCompatDark);
            toolbar.setBackgroundResource(R.color.colorprimary_material_dark);
        }
        ListView listView = findViewById(R.id.ListView);

        Collections.sort(padzeia);
        for (Padzeia p : padzeia) {
            sabytie2.add(p.dat + " " + p.padz.replace("_", " "));
        }
        adapter = new SabytieAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String name = parent.getItemAtPosition(position).toString();
            Dialog_context_menu_sabytie context_menu_sabytie = Dialog_context_menu_sabytie.getInstance(position, name);
            context_menu_sabytie.show(getSupportFragmentManager(), "context_menu_sabytie");
            return true;
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            String title = "", data = "", time = "", dataK = "", timeK = "";
            long paz = 0;
            for (int i = 0; i < padzeia.size(); i++) {
                if (i == position) {
                    Padzeia p = padzeia.get(i);
                    title = p.padz;
                    data = p.dat;
                    time = p.tim;
                    paz = p.paznic;
                    dataK = p.datK;
                    timeK = p.timK;
                }
            }
            String res = "Паведаміць: Ніколі";
            if (paz != 0) {
                GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
                gc.setTimeInMillis(paz);
                String nol11 = "", nol21 = "", nol3 = "";
                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol11 = "0";
                if (gc.get(Calendar.MONTH) < 9) nol21 = "0";
                if (gc.get(Calendar.MINUTE) < 10) nol3 = "0";
                res = "Паведаміць: " + nol11 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol21 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR) + " у " + gc.get(Calendar.HOUR_OF_DAY) + ":" + nol3 + gc.get(Calendar.MINUTE);
            }
            Dialog_sabytie_show dialog_show_sabytie = Dialog_sabytie_show.getInstance(title, data, time, dataK, timeK, res);
            dialog_show_sabytie.show(getSupportFragmentManager(), "sabytie");
        });
        editSave = Objects.requireNonNull(editText.getText()).toString();
        edit2Save = Objects.requireNonNull(editText2.getText()).toString();
        daSave = textViewDate.getText().toString();
        taSave = textViewTime.getText().toString();
        daKSave = textViewDateK.getText().toString();
        taKSave = textViewTimeK.getText().toString();
        labelbutton12Save = labelbutton12.getText().toString();
        editText4Save = Objects.requireNonNull(editText4.getText()).toString();
        colorSave = spinner5.getSelectedItemPosition();
        radioSave = radio;
    }

    @Override
    public void onDialogEditClick(int position) {
        save = true;
        back = true;
        Padzeia p = padzeia.get(position);
        editText.setText(p.padz);
        textViewDate.setText(p.dat);
        textViewTime.setText(p.tim);
        textViewDateK.setText(p.datK);
        textViewTimeK.setText(p.timK);
        if (p.sec.equals("-1")) editText2.setText("");
        else editText2.setText(p.sec);
        spinner.setSelection(p.vybtime);
        spinner4.setSelection(p.repit);
        spinner5.setSelection(p.color);
        labelbutton12Save = labelbutton12.getText().toString();
        editText4Save = Objects.requireNonNull(editText4.getText()).toString();
        radioSave = radio;
        vybtimeSave = p.vybtime;
        repitSave = p.repit;
        colorSave = p.color;
        color = p.color;
        if (p.repit > 0) radioGroup.setVisibility(View.VISIBLE);
        else radioGroup.setVisibility(View.GONE);
        nomer = position;
        titleLoy.setVisibility(View.VISIBLE);
        listLoy.setVisibility(View.GONE);
        idMenu = 3;
        filename = p.file;
        time = p.count;
        String[] count = time.split("[.]");
        if (time.equals("0")) radioButton1.setChecked(true);
        else if (count.length == 1) {
            radioButton2.setChecked(true);
            editText4.setText(time);
        } else {
            radioButton3.setChecked(true);
            labelbutton12.setText(time);
        }
        repitL = p.repit;
        editSave = Objects.requireNonNull(editText.getText()).toString();
        edit2Save = Objects.requireNonNull(editText2.getText()).toString();
        daSave = textViewDate.getText().toString();
        taSave = textViewTime.getText().toString();
        daKSave = textViewDateK.getText().toString();
        taKSave = textViewTimeK.getText().toString();
        supportInvalidateOptionsMenu();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void file_delite(int position, String filename) {
        redak = true;
        final Padzeia sab = padzeia.get(position);
        final String filen = sab.file;
        final ArrayList<Padzeia> del = new ArrayList<>();
        for (Padzeia p : padzeia) {
            if (p.file.contains(filen)) {
                del.add(p);
            }
        }
        File file = new File(getFilesDir() + "/Sabytie/" + sab.file);
        if (file.exists()) {
            file.delete();
        }
        padzeia.removeAll(del);
        Collections.sort(padzeia);
        sabytie2.clear();
        for (Padzeia p : padzeia) {
            sabytie2.add(p.dat + " " + p.padz.replace("_", " "));
        }
        adapter.notifyDataSetChanged();
        Thread thread = new Thread(() -> {
            if (sab.count.equals("0")) {
                if (sab.repit == 1 || sab.repit == 4 || sab.repit == 5 || sab.repit == 6) {
                    if (!sab.sec.equals("-1")) {
                        Intent intent = createIntent(sab.padz.replace("_", " "), "Падзея" + " " + sab.dat + " у " + sab.tim, sab.dat, sab.tim);
                        long londs3 = sab.paznic / 100000L;
                        PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                        am.cancel(pIntent);
                        pIntent.cancel();
                    }
                } else {
                    for (Padzeia p : del) {
                        if (p.file.contains(filen)) {
                            if (!p.sec.equals("-1")) {
                                Intent intent = createIntent(p.padz.replace("_", " "), "Падзея" + " " + p.dat + " у " + p.tim, p.dat, p.tim);
                                long londs3 = p.paznic / 100000L;
                                PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                am.cancel(pIntent);
                                pIntent.cancel();
                            }
                        }
                    }
                }
            } else {
                for (Padzeia p : del) {
                    if (!p.sec.equals("-1")) {
                        Intent intent = createIntent(p.padz.replace("_", " "), "Падзея" + " " + p.dat + " у " + p.tim, p.dat, p.tim);
                        long londs3 = p.paznic / 100000L;
                        PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                        am.cancel(pIntent);
                        pIntent.cancel();
                    }
                }
            }
            //MyBackupAgent.requestBackup(sabytie.this);
        });
        thread.start();
        LinearLayout layout = new LinearLayout(sabytie.this);
        if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
        else layout.setBackgroundResource(R.color.colorPrimary);
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(sabytie.this);
        toast.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
        toast.setPadding(realpadding, realpadding, realpadding, realpadding);
        toast.setText("Выдалена");
        toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        layout.addView(toast);
        Toast mes = new Toast(sabytie.this);
        mes.setDuration(Toast.LENGTH_SHORT);
        mes.setView(layout);
        mes.show();
    }


    @Override
    public void onDialogDeliteClick(int position) {
        Dialog_delite dd = Dialog_delite.getInstance(position, "", "з падзей", sabytie2.get(position));
        dd.show(getSupportFragmentManager(), "dialig_delite");
    }

    private void onPopupRedaktor(int pos) {
        String timeC;
        save = true;
        back = true;
        Padzeia p = padzeia.get(pos);
        editText.setText(p.padz);
        textViewDate.setText(p.dat);
        textViewTime.setText(p.tim);
        textViewDateK.setText(p.datK);
        textViewTimeK.setText(p.timK);
        if (p.sec.equals("-1")) editText2.setText("");
        else editText2.setText(p.sec);
        spinner.setSelection(p.vybtime);
        spinner4.setSelection(p.repit);
        spinner5.setSelection(p.color);
        labelbutton12Save = labelbutton12.getText().toString();
        editText4Save = Objects.requireNonNull(editText4.getText()).toString();
        radioSave = radio;
        vybtimeSave = p.vybtime;
        repitSave = p.repit;
        colorSave = p.color;
        color = p.color;
        if (p.repit > 0) radioGroup.setVisibility(View.VISIBLE);
        else radioGroup.setVisibility(View.GONE);
        nomer = pos;
        titleLoy.setVisibility(View.VISIBLE);
        listLoy.setVisibility(View.GONE);
        idMenu = 3;
        filename = p.file;
        timeC = p.count;
        String[] count = timeC.split("[.]");
        if (timeC.equals("0")) radioButton1.setChecked(true);
        else if (count.length == 1) {
            radioButton2.setChecked(true);
            editText4.setText(timeC);
        } else {
            radioButton3.setChecked(true);
            labelbutton12.setText(timeC);
        }
        repitL = p.repit;
        sabytie.this.time = timeC;
        editSave = Objects.requireNonNull(editText.getText()).toString();
        edit2Save = Objects.requireNonNull(editText2.getText()).toString();
        daSave = textViewDate.getText().toString();
        taSave = textViewTime.getText().toString();
        daKSave = textViewDateK.getText().toString();
        taKSave = textViewTimeK.getText().toString();
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        String editSaveN = Objects.requireNonNull(editText.getText()).toString();
        String edit2SaveN = Objects.requireNonNull(editText2.getText()).toString();
        String edit4SaveN = Objects.requireNonNull(editText4.getText()).toString();
        String daSaveN = textViewDate.getText().toString();
        String taSaveN = textViewTime.getText().toString();
        String daKSaveN = textViewDateK.getText().toString();
        String taKSaveN = textViewTimeK.getText().toString();
        if (!(edit2SaveN.equals(edit2Save) && editSaveN.equals(editSave) && daSaveN.equals(daSave) && daKSaveN.equals(daKSave) && taSaveN.equals(taSave) && taKSaveN.equals(taKSave) && spinner.getSelectedItemPosition() == vybtimeSave && spinner4.getSelectedItemPosition() == repitSave && spinner5.getSelectedItemPosition() == colorSave && labelbutton12Save.equals(labelbutton12.getText().toString()) && editText4Save.equals(edit4SaveN) && radioSave == radio) && listLoy.getVisibility() == View.GONE) {
            Dialog_sabytie_save dialog_sabytie_save = new Dialog_sabytie_save();
            dialog_sabytie_save.show(getSupportFragmentManager(), "sabytie_save");
        } else if (back) {
            home = true;
            MenuItem item = menu.findItem(R.id.action_cansel);
            onOptionsItemSelected(item);
        } else {
            if (redak) {
                MainActivity.setListPadzeia(this);
                onSupportNavigateUp();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onDialogPositiveClick() {
        MenuItem item;
        if (save) item = menu.findItem(R.id.action_save_redak);
        else item = menu.findItem(R.id.action_save);
        onOptionsItemSelected(item);
    }

    @Override
    public void onDialogNegativeClick() {
        MenuItem item = menu.findItem(R.id.action_cansel);
        onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 109 || requestCode == 1092 || requestCode == 1093) {
            if (data != null) {
                long dayyear = 0;
                long day = data.getIntExtra("data", 0);
                int year = data.getIntExtra("year", c.get(Calendar.YEAR));
                for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < year; i++) {
                    if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                    else dayyear = 365 + dayyear;
                }
                long mills = (dayyear + day) * 86400000L;
                GregorianCalendar setCal = (GregorianCalendar) Calendar.getInstance();
                setCal.set(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, 1, 0, 0, 0);
                long timeold = setCal.getTimeInMillis();
                result = mills + timeold;
                setCal.setTimeInMillis(result);
                String nol1 = "", nol2 = "";
                if (setCal.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                if (setCal.get(Calendar.MONTH) < 9) nol2 = "0";
                da = nol1 + setCal.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (setCal.get(Calendar.MONTH) + 1) + "." + setCal.get(Calendar.YEAR);
                YearG = setCal.get(Calendar.YEAR);
                MunG = setCal.get(Calendar.MONTH);
                if (requestCode == 109) {
                    String[] days = textViewDate.getText().toString().split("[.]");
                    GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), 0, 0, 0);
                    String[] days2 = textViewDateK.getText().toString().split("[.]");
                    GregorianCalendar gc2 = new GregorianCalendar(Integer.parseInt(days2[2]), Integer.parseInt(days2[1]) - 1, Integer.parseInt(days2[0]), 0, 0, 0);
                    int kon = gc2.get(Calendar.DAY_OF_YEAR);
                    int res = gc.get(Calendar.DAY_OF_YEAR);
                    if (kon - res >= 0) {
                        String da1;
                        setCal.add(Calendar.DATE, kon - res);
                        if (setCal.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                        else nol1 = "";
                        if (setCal.get(Calendar.MONTH) < 9) nol2 = "0";
                        else nol2 = "";
                        da1 = nol1 + setCal.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (setCal.get(Calendar.MONTH) + 1) + "." + setCal.get(Calendar.YEAR);
                        textViewDateK.setText(da1);
                        if (gc2.get(Calendar.YEAR) > gc.get(Calendar.YEAR)) {
                            int leapYear = 365;
                            if (gc.isLeapYear(gc.get(Calendar.YEAR))) leapYear = 366;
                            setCal.add(Calendar.DATE, -(kon - res));
                            setCal.add(Calendar.DATE, leapYear - res + kon);
                            if (setCal.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                            else nol1 = "";
                            if (setCal.get(Calendar.MONTH) < 9) nol2 = "0";
                            else nol2 = "";
                            da1 = nol1 + setCal.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (setCal.get(Calendar.MONTH) + 1) + "." + setCal.get(Calendar.YEAR);
                            textViewDateK.setText(da1);
                        }
                    }
                    textViewDate.setText(da);
                    nol1 = "";
                    nol2 = "";
                    setCal.add(Calendar.DATE, 1);
                    if (setCal.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                    if (setCal.get(Calendar.MONTH) < 9) nol2 = "0";
                    String[] days3 = labelbutton12.getText().toString().split("[.]");
                    GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(days3[2]), Integer.parseInt(days3[1]) - 1, Integer.parseInt(days3[0]), 0, 0, 0);
                    String[] days4 = textViewDate.getText().toString().split("[.]");
                    GregorianCalendar gc4 = new GregorianCalendar(Integer.parseInt(days4[2]), Integer.parseInt(days4[1]) - 1, Integer.parseInt(days4[0]), 0, 0, 0);
                    long kon2 = gc3.getTimeInMillis();
                    long resul = gc4.getTimeInMillis();
                    if (kon2 - resul < 0)
                        labelbutton12.setText(getResources().getString(R.string.Sabytie, nol1, setCal.get(Calendar.DAY_OF_MONTH), nol2, (setCal.get(Calendar.MONTH) + 1), setCal.get(Calendar.YEAR)));
                }
                if (requestCode == 1092) {
                    textViewDateK.setText(da);
                    konec = true;
                    String[] days = textViewDate.getText().toString().split("[.]");
                    GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), 0, 0, 0);
                    String[] days2 = textViewDateK.getText().toString().split("[.]");
                    GregorianCalendar gc2 = new GregorianCalendar(Integer.parseInt(days2[2]), Integer.parseInt(days2[1]) - 1, Integer.parseInt(days2[0]), 0, 0, 0);
                    long kon = gc2.getTimeInMillis();
                    result = gc.getTimeInMillis();
                    if (kon - result < 0) {
                        LinearLayout layout = new LinearLayout(sabytie.this);
                        if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
                        else layout.setBackgroundResource(R.color.colorPrimary);
                        float density = getResources().getDisplayMetrics().density;
                        int realpadding = (int) (10 * density);
                        TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(sabytie.this);
                        toast.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                        toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                        toast.setText("Дата заканчэньня павінна быць пазней, чым дата пачатку");
                        toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                        layout.addView(toast);
                        Toast mes = new Toast(sabytie.this);
                        mes.setDuration(Toast.LENGTH_LONG);
                        mes.setView(layout);
                        mes.show();
                        da = textViewDate.getText().toString();
                        textViewDateK.setText(da);
                    }
                }
                if (requestCode == 1093) {
                    labelbutton12.setText(da);
                    String[] days = textViewDate.getText().toString().split("[.]");
                    GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), 0, 0, 0);
                    gc.add(Calendar.DATE, 1);
                    String[] days2 = labelbutton12.getText().toString().split("[.]");
                    GregorianCalendar gc2 = new GregorianCalendar(Integer.parseInt(days2[2]), Integer.parseInt(days2[1]) - 1, Integer.parseInt(days2[0]), 0, 0, 0);
                    long kon = gc2.getTimeInMillis();
                    long resul = gc.getTimeInMillis();
                    if (kon - resul < 0) {
                        LinearLayout layout = new LinearLayout(sabytie.this);
                        if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
                        else layout.setBackgroundResource(R.color.colorPrimary);
                        float density = getResources().getDisplayMetrics().density;
                        int realpadding = (int) (10 * density);
                        TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(sabytie.this);
                        toast.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                        toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                        toast.setText("Дата паўтору павінна быць пазней, чым дата пачатку");
                        toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                        layout.addView(toast);
                        Toast mes = new Toast(sabytie.this);
                        mes.setDuration(Toast.LENGTH_LONG);
                        mes.setView(layout);
                        mes.show();
                        nol1 = "";
                        nol2 = "";
                        if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                        if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                        labelbutton12.setText(getResources().getString(R.string.Sabytie, nol1, gc.get(Calendar.DAY_OF_MONTH), nol2, (gc.get(Calendar.MONTH) + 1), gc.get(Calendar.YEAR)));
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(R.menu.sabytie, menu);
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
        menu.findItem(R.id.action_add).setVisible(false);
        menu.findItem(R.id.action_delite).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_save_redak).setVisible(false);
        menu.findItem(R.id.action_cansel).setVisible(false);
        switch (idMenu) {
            case 1:
                menu.findItem(R.id.action_add).setVisible(true);
                menu.findItem(R.id.action_delite).setVisible(true);
                break;
            case 2:
                menu.findItem(R.id.action_save).setVisible(true);
                menu.findItem(R.id.action_cansel).setVisible(true);
                break;
            case 3:
                menu.findItem(R.id.action_save_redak).setVisible(true);
                menu.findItem(R.id.action_cansel).setVisible(true);
                break;
        }
        return true;
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (!home) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return true;
            }
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        int id = item.getItemId();
        c = (GregorianCalendar) Calendar.getInstance();
        Animation shakeanimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.action_settings) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, "3000");
                startActivity(intent);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.deleteNotificationChannel("by.carkva-gazeta");
            } else {
                Dialog_sabytie_settings settings = new Dialog_sabytie_settings();
                settings.show(getSupportFragmentManager(), "settings");
            }
        }
        if (id == R.id.action_save) {
            redak = true;
            back = false;
            try {
                String edit = editText.getText().toString();
                String edit2 = editText2.getText().toString();
                da = textViewDate.getText().toString();
                ta = textViewTime.getText().toString();
                daK = textViewDateK.getText().toString();
                taK = textViewTimeK.getText().toString();
                if (!edit.equals("")) {
                    long londs = 0, londs2 = 0;
                    String[] days = textViewDate.getText().toString().split("[.]");
                    String[] times = textViewTime.getText().toString().split("[:]");
                    GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                    result = gc.getTimeInMillis();
                    if (!konec) {
                        daK = da;
                        taK = ta;
                    }
                    if (!edit2.equals("")) {
                        londs = Long.parseLong(edit2);
                        switch (posit) {
                            case 0:
                                londs = londs * 60000L;
                                break;
                            case 1:
                                londs = londs * 3600000L;
                                break;
                            case 2:
                                londs = londs * 86400000L;
                                break;
                            case 3:
                                londs = londs * 604800000L;
                                break;
                        }
                    } else {
                        edit2 = "-1";
                    }

                    FileWriter outputStream = new FileWriter(getFilesDir() + "/Sabytie/" + c.getTimeInMillis() + ".dat");
                    switch (repitL) {
                        case 0:
                            time = "0";
                            if (!edit2.equals("-1")) {
                                londs2 = result - londs;
                                long londs3 = londs2 / 100000L;
                                if (result > c.getTimeInMillis()) {
                                    Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                    PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                    } else {
                                        am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                    }
                                }
                            }
                            padzeia.add(new Padzeia(edit, da, ta, londs2, posit, edit2, daK, taK, repitL, time, c.getTimeInMillis() + ".dat", color));
                            outputStream.write(edit.replace(" ", "_") + " " + da + " " + ta + " " + londs2 + " " + posit + " " + edit2 + " " + daK + " " + taK + " " + repitL + " " + time + " " + c.getTimeInMillis() + ".dat" + " " + color + "\n");
                            break;
                        case 1:
                            time = "0";
                            String[] rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            String[] rdat2 = daK.split("[.]");
                            GregorianCalendar gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            StringBuilder builder = new StringBuilder();
                            int dayof = gc.get(Calendar.DAY_OF_YEAR);
                            int leapYear = 365 - dayof + 365 + 1;
                            if (gc.isLeapYear(gc.get(Calendar.YEAR)))
                                leapYear = 365 - dayof + 366 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;
                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            if (radio == 1) {
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    for (int i = 0; i < 731; i++) {
                                        if (londs2 > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            am.setRepeating(AlarmManager.RTC_WAKEUP, londs2, 86400000L, pIntent);
                                            break;
                                        }
                                        londs2 = londs2 + 86400000L;
                                    }
                                }
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (radio != 1) {
                                        if (result > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, c.getTimeInMillis() + ".dat", color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(c.getTimeInMillis()).append(".dat").append(" ").append(color).append("\n");
                                gc.add(Calendar.DATE, 1);
                                gc2.add(Calendar.DATE, 1);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 2:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.DAY_OF_YEAR);
                            leapYear = 365 - dayof + 365 + 1;
                            if (gc.isLeapYear(gc.get(Calendar.YEAR)))
                                leapYear = 365 - dayof + 366 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;
                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (gc.get(Calendar.DAY_OF_WEEK) > 1 && gc.get(Calendar.DAY_OF_WEEK) < 7) {
                                    if (!edit2.equals("-1")) {
                                        londs2 = result - londs;
                                        long londs3 = londs2 / 100000L;
                                        if (result > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                    String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                    if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                    if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                    if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                    if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                    padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, c.getTimeInMillis() + ".dat", color));
                                    builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(c.getTimeInMillis()).append(".dat").append(" ").append(color).append("\n");
                                }
                                gc.add(Calendar.DATE, 1);
                                gc2.add(Calendar.DATE, 1);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 3:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.DAY_OF_YEAR);
                            leapYear = 365 - dayof + 365 + 1;
                            if (gc.isLeapYear(gc.get(Calendar.YEAR)))
                                leapYear = 365 - dayof + 366 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;

                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            int schet = 0;
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (schet < 2) {
                                    if (!edit2.equals("-1")) {
                                        londs2 = result - londs;
                                        long londs3 = londs2 / 100000L;
                                        if (result > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                    String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                    if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                    if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                    if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                    if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                    padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, c.getTimeInMillis() + ".dat", color));
                                    builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(c.getTimeInMillis()).append(".dat").append(" ").append(color).append("\n");
                                }
                                schet++;
                                gc.add(Calendar.DATE, 1);
                                gc2.add(Calendar.DATE, 1);
                                if (schet == 4) schet = 0;
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 4:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.WEEK_OF_YEAR);
                            leapYear = 52 - dayof + 52 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.WEEK_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;
                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            if (radio == 1) {
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    for (int i = 0; i < 731; i++) {
                                        if (londs2 > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            am.setRepeating(AlarmManager.RTC_WAKEUP, londs2, 604800000L, pIntent);
                                            break;
                                        }
                                        londs2 = londs2 + 604800000L;
                                    }
                                }
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (radio != 1) {
                                        if (result > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, c.getTimeInMillis() + ".dat", color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(c.getTimeInMillis()).append(".dat").append(" ").append(color).append("\n");
                                gc.add(Calendar.DATE, 7);
                                gc2.add(Calendar.DATE, 7);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 5:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.WEEK_OF_YEAR);
                            leapYear = 26 - (dayof / 2) + 26 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;

                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            if (radio == 1) {
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    for (int i = 0; i < 731; i++) {
                                        if (londs2 > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            am.setRepeating(AlarmManager.RTC_WAKEUP, londs2, 14 * 86400000L, pIntent);
                                            break;
                                        }
                                        londs2 = londs2 + (14 * 86400000L);
                                    }
                                }
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (radio != 1) {
                                        if (result > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, c.getTimeInMillis() + ".dat", color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(c.getTimeInMillis()).append(".dat").append(" ").append(color).append("\n");
                                gc.add(Calendar.DATE, 14);
                                gc2.add(Calendar.DATE, 14);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 6:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.WEEK_OF_YEAR);
                            leapYear = 13 - (dayof / 4) + 13;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;

                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            if (radio == 1) {
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    for (int i = 0; i < 731; i++) {
                                        if (londs2 > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            am.setRepeating(AlarmManager.RTC_WAKEUP, londs2, 2419200000L, pIntent);
                                            break;
                                        }
                                        londs2 = londs2 + 2419200000L;
                                    }
                                }
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (radio != 1) {
                                        if (result > c.getTimeInMillis()) {
                                            Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, c.getTimeInMillis() + ".dat", color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(c.getTimeInMillis()).append(".dat").append(" ").append(color).append("\n");
                                gc.add(Calendar.DATE, 28);
                                gc2.add(Calendar.DATE, 28);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 7:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.MONTH) + 1;
                            leapYear = 12 - dayof + 12 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;
                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (result > c.getTimeInMillis()) {
                                        Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                        PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        } else {
                                            am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, c.getTimeInMillis() + ".dat", color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(c.getTimeInMillis()).append(".dat").append(" ").append(color).append("\n");
                                gc.add(Calendar.MONTH, 1);
                                gc2.add(Calendar.MONTH, 1);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 8:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            leapYear = 10;
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (result > c.getTimeInMillis()) {
                                        Intent intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                        PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        } else {
                                            am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, c.getTimeInMillis() + ".dat", color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(c.getTimeInMillis()).append(".dat").append(" ").append(color).append("\n");
                                gc.add(Calendar.YEAR, 1);
                                gc2.add(Calendar.YEAR, 1);
                            }
                            outputStream.write(builder.toString());
                            break;
                    }
                    outputStream.close();
                    Collections.sort(padzeia);
                    sabytie2.clear();
                    for (Padzeia p : padzeia) {
                        sabytie2.add(p.dat + " " + p.padz.replace("_", " "));
                    }
                    if (!editText2.getText().toString().equals("")) {
                        if (k.getBoolean("check_notifi", true) && Build.MANUFACTURER.toLowerCase().contains("huawei")) {
                            Dialog_help_notification notifi = new Dialog_help_notification();
                            notifi.show(getSupportFragmentManager(), "help_notification");
                        }
                    }
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                    editText2.setText("");
                    LinearLayout layout = new LinearLayout(sabytie.this);
                    if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
                    else layout.setBackgroundResource(R.color.colorPrimary);
                    float density = getResources().getDisplayMetrics().density;
                    int realpadding = (int) (10 * density);
                    TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(sabytie.this);
                    toast.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                    toast.setText("Захавана");
                    toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                    layout.addView(toast);
                    Toast mes = new Toast(sabytie.this);
                    mes.setDuration(Toast.LENGTH_SHORT);
                    mes.setView(layout);
                    mes.show();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    titleLoy.setVisibility(View.GONE);
                    listLoy.setVisibility(View.VISIBLE);
                    idMenu = 1;
                    supportInvalidateOptionsMenu();
                } else {
                    editText.startAnimation(shakeanimation);
                }
            } catch (Exception ignored) {
            }
            //MyBackupAgent.requestBackup(this);
        }
        if (id == R.id.action_save_redak) {
            redak = true;
            back = false;
            try {
                Padzeia p = padzeia.get(nomer);
                String edit = editText.getText().toString();
                String edit2 = editText2.getText().toString();
                da = textViewDate.getText().toString();
                ta = textViewTime.getText().toString();
                daK = textViewDateK.getText().toString();
                taK = textViewTimeK.getText().toString();
                if (!edit.equals("")) {
                    Intent intent;
                    PendingIntent pIntent;
                    try {
                        File file = new File(getFilesDir() + "/Sabytie/" + p.file);
                        FileReader inputStream = new FileReader(file);
                        BufferedReader reader = new BufferedReader(inputStream);
                        String line;
                        while ((line = reader.readLine()) != null && !line.equals("")) {
                            String[] t1 = line.split(" ");
                            intent = createIntent(t1[0].replace("_", " "), "Падзея" + " " + t1[1] + " у " + t1[2], t1[1], t1[2]);
                            long londs3 = Long.parseLong(t1[3]) / 100000L;
                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                            am.cancel(pIntent);
                            pIntent.cancel();
                        }
                    } catch (Exception ignored) {
                    }
                    long londs = 0, londs2 = 0;
                    String[] days = textViewDate.getText().toString().split("[.]");
                    String[] times = textViewTime.getText().toString().split("[:]");
                    String[] times2 = textViewTimeK.getText().toString().split("[:]");
                    GregorianCalendar gc4 = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), Integer.parseInt(times2[0]), Integer.parseInt(times2[1]), 0);
                    long result2 = gc4.getTimeInMillis();
                    GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                    result = gc.getTimeInMillis();
                    if (result2 != result) konec = true;
                    if (!konec) {
                        daK = da;
                        taK = ta;
                    }
                    if (!edit2.equals("")) {
                        londs = Long.parseLong(edit2);
                        switch (posit) {
                            case 0:
                                londs = londs * 60000L;
                                break;
                            case 1:
                                londs = londs * 3600000L;
                                break;
                            case 2:
                                londs = londs * 86400000L;
                                break;
                            case 3:
                                londs = londs * 604800000L;
                                break;
                        }
                    } else {
                        edit2 = "-1";
                    }
                    padzeia.clear();
                    FileWriter outputStream = new FileWriter(getFilesDir() + "/Sabytie/" + filename);
                    switch (repitL) {
                        case 0:
                            time = "0";
                            if (!edit2.equals("-1")) {
                                londs2 = result - londs;
                                long londs3 = londs2 / 100000L;
                                if (result > c.getTimeInMillis()) {
                                    intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                    pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                    } else {
                                        am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                    }
                                }
                            }
                            padzeia.add(new Padzeia(edit, da, ta, londs2, posit, edit2, daK, taK, repitL, time, filename, color));
                            outputStream.write(edit.replace(" ", "_") + " " + da + " " + ta + " " + londs2 + " " + posit + " " + edit2 + " " + daK + " " + taK + " " + repitL + " " + time + " " + filename + " " + color + "\n");
                            break;
                        case 1:
                            time = "0";
                            String[] rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            String[] rdat2 = daK.split("[.]");
                            GregorianCalendar gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            StringBuilder builder = new StringBuilder();
                            int dayof = gc.get(Calendar.DAY_OF_YEAR);
                            int leapYear = 365 - dayof + 365 + 1;
                            if (gc.isLeapYear(gc.get(Calendar.YEAR)))
                                leapYear = 365 - dayof + 366 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;
                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            if (radio == 1) {
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    for (int i = 0; i < 731; i++) {
                                        if (londs2 > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            am.setRepeating(AlarmManager.RTC_WAKEUP, londs2, 86400000L, pIntent);
                                            break;
                                        }
                                        londs2 = londs2 + 86400000L;
                                    }
                                }
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (radio != 1) {
                                        if (result > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, filename, color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(filename).append(" ").append(color).append("\n");
                                gc.add(Calendar.DATE, 1);
                                gc2.add(Calendar.DATE, 1);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 2:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.DAY_OF_YEAR);
                            leapYear = 365 - dayof + 365 + 1;
                            if (gc.isLeapYear(gc.get(Calendar.YEAR)))
                                leapYear = 365 - dayof + 366 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;
                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (gc.get(Calendar.DAY_OF_WEEK) > 1 && gc.get(Calendar.DAY_OF_WEEK) < 7) {
                                    if (!edit2.equals("-1")) {
                                        londs2 = result - londs;
                                        long londs3 = londs2 / 100000L;
                                        if (result > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                    String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                    if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                    if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                    if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                    if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                    padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, filename, color));
                                    builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(filename).append(" ").append(color).append("\n");
                                }
                                gc.add(Calendar.DATE, 1);
                                gc2.add(Calendar.DATE, 1);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 3:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.DAY_OF_YEAR);
                            leapYear = 365 - dayof + 365 + 1;
                            if (gc.isLeapYear(gc.get(Calendar.YEAR)))
                                leapYear = 365 - dayof + 366 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;

                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            int schet = 0;
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (schet < 2) {
                                    if (!edit2.equals("-1")) {
                                        londs2 = result - londs;
                                        long londs3 = londs2 / 100000L;
                                        if (result > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                    String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                    if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                    if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                    if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                    if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                    padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, filename, color));
                                    builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(filename).append(" ").append(color).append("\n");
                                }
                                schet++;
                                gc.add(Calendar.DATE, 1);
                                gc2.add(Calendar.DATE, 1);
                                if (schet == 4) schet = 0;
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 4:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.WEEK_OF_YEAR);
                            leapYear = 52 - dayof + 52 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.WEEK_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;
                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            if (radio == 1) {
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    for (int i = 0; i < 731; i++) {
                                        if (londs2 > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            am.setRepeating(AlarmManager.RTC_WAKEUP, londs2, 604800000L, pIntent);
                                            break;
                                        }
                                        londs2 = londs2 + 604800000L;
                                    }
                                }
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (radio != 1) {
                                        if (result > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, filename, color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(filename).append(" ").append(color).append("\n");
                                gc.add(Calendar.DATE, 7);
                                gc2.add(Calendar.DATE, 7);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 5:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.WEEK_OF_YEAR);
                            leapYear = 26 - (dayof / 2) + 26 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;

                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            if (radio == 1) {
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    for (int i = 0; i < 731; i++) {
                                        if (londs2 > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            am.setRepeating(AlarmManager.RTC_WAKEUP, londs2, 1209600000L, pIntent);
                                            break;
                                        }
                                        londs2 = londs2 + 1209600000L;
                                    }
                                }
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (radio != 1) {
                                        if (result > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, filename, color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(filename).append(" ").append(color).append("\n");
                                gc.add(Calendar.DATE, 14);
                                gc2.add(Calendar.DATE, 14);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 6:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.WEEK_OF_YEAR);
                            leapYear = 13 - (dayof / 4) + 13;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;

                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            if (radio == 1) {
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    for (int i = 0; i < 731; i++) {
                                        if (londs2 > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            am.setRepeating(AlarmManager.RTC_WAKEUP, londs2, 2419200000L, pIntent);
                                            break;
                                        }
                                        londs2 = londs2 + (2419200000L);
                                    }
                                }
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (radio != 1) {
                                        if (result > c.getTimeInMillis()) {
                                            intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                            pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            } else {
                                                am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                            }
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, filename, color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(filename).append(" ").append(color).append("\n");
                                gc.add(Calendar.DATE, 28);
                                gc2.add(Calendar.DATE, 28);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 7:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            dayof = gc.get(Calendar.MONTH) + 1;
                            leapYear = 12 - dayof + 12 + 1;

                            if (radio == 3) {
                                time = labelbutton12.getText().toString();
                                String[] tim = time.split("[.]");
                                GregorianCalendar gc3 = new GregorianCalendar(Integer.parseInt(tim[2]), Integer.parseInt(tim[1]) - 1, Integer.parseInt(tim[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                                int resd = gc3.get(Calendar.DAY_OF_YEAR) - dayof;
                                if (gc.get(Calendar.YEAR) < gc3.get(Calendar.YEAR)) {
                                    int yeav = 365;
                                    if (gc.isLeapYear(gc.get(Calendar.YEAR))) yeav = 366;
                                    resd = yeav - dayof + gc3.get(Calendar.DAY_OF_YEAR);
                                }
                                leapYear = resd + 1;
                            }
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (result > c.getTimeInMillis()) {
                                        intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                        pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        } else {
                                            am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, filename, color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(filename).append(" ").append(color).append("\n");
                                gc.add(Calendar.MONTH, 1);
                                gc2.add(Calendar.MONTH, 1);
                            }
                            outputStream.write(builder.toString());
                            break;
                        case 8:
                            time = "0";
                            rdat = da.split("[.]");
                            gc.set(Integer.parseInt(rdat[2]), Integer.parseInt(rdat[1]) - 1, Integer.parseInt(rdat[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            rdat2 = daK.split("[.]");
                            gc2 = new GregorianCalendar(Integer.parseInt(rdat2[2]), Integer.parseInt(rdat2[1]) - 1, Integer.parseInt(rdat2[0]), Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0);
                            builder = new StringBuilder();
                            leapYear = 10;
                            if (radio == 2) {
                                time = editText4.getText().toString();
                                if (time.equals("")) time = "1";
                                leapYear = Integer.parseInt(time);
                            }
                            for (int i = 0; i < leapYear; i++) {
                                result = gc.getTimeInMillis();
                                if (!edit2.equals("-1")) {
                                    londs2 = result - londs;
                                    long londs3 = londs2 / 100000L;
                                    if (result > c.getTimeInMillis()) {
                                        intent = createIntent(edit, "Падзея" + " " + da + " у " + ta, da, ta);
                                        pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            am.setExact(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        } else {
                                            am.set(AlarmManager.RTC_WAKEUP, londs2, pIntent);
                                        }
                                    }
                                }
                                String nol1 = "", nol2 = "", nol3 = "", nol4 = "";
                                if (gc.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                                if (gc.get(Calendar.MONTH) < 9) nol2 = "0";
                                if (gc2.get(Calendar.DAY_OF_MONTH) < 10) nol3 = "0";
                                if (gc2.get(Calendar.MONTH) < 9) nol4 = "0";
                                padzeia.add(new Padzeia(edit, nol1 + gc.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR), ta, londs2, posit, edit2, nol3 + gc2.get(Calendar.DAY_OF_MONTH) + "." + nol4 + (gc2.get(Calendar.MONTH) + 1) + "." + gc2.get(Calendar.YEAR), taK, repitL, time, filename, color));
                                builder.append(edit.replace(" ", "_")).append(" ").append(nol1).append(gc.get(Calendar.DAY_OF_MONTH)).append(".").append(nol2).append(gc.get(Calendar.MONTH) + 1).append(".").append(gc.get(Calendar.YEAR)).append(" ").append(ta).append(" ").append(londs2).append(" ").append(posit).append(" ").append(edit2).append(" ").append(nol3).append(gc2.get(Calendar.DAY_OF_MONTH)).append(".").append(nol4).append(gc2.get(Calendar.MONTH) + 1).append(".").append(gc2.get(Calendar.YEAR)).append(" ").append(taK).append(" ").append(repitL).append(" ").append(time).append(" ").append(filename).append(" ").append(color).append("\n");
                                gc.add(Calendar.YEAR, 1);
                                gc2.add(Calendar.YEAR, 1);
                            }
                            outputStream.write(builder.toString());
                            break;
                    }
                    outputStream.close();
                    File dir = new File(getFilesDir() + "/Sabytie");
                    for (String s : dir.list()) {
                        File file = new File(getFilesDir() + "/Sabytie/" + s);
                        if (!s.contains(filename)) {
                            FileReader inputStream = new FileReader(file);
                            BufferedReader reader = new BufferedReader(inputStream);
                            String line;
                            while ((line = reader.readLine()) != null && !line.equals("")) {
                                String[] t1 = line.split(" ");
                                if (t1.length == 11)
                                    padzeia.add(new Padzeia(t1[0].replace("_", " "), t1[1], t1[2], Long.parseLong(t1[3]), Integer.parseInt(t1[4]), t1[5], t1[6], t1[7], Integer.parseInt(t1[8]), t1[9], t1[10], color));
                                else
                                    padzeia.add(new Padzeia(t1[0].replace("_", " "), t1[1], t1[2], Long.parseLong(t1[3]), Integer.parseInt(t1[4]), t1[5], t1[6], t1[7], Integer.parseInt(t1[8]), t1[9], t1[10], Integer.parseInt(t1[11])));
                            }
                            inputStream.close();
                        }
                    }

                    Collections.sort(padzeia);
                    sabytie2.clear();
                    for (Padzeia p2 : padzeia) {
                        sabytie2.add(p2.dat + " " + p2.padz.replace("_", " "));
                    }
                    if (!editText2.getText().toString().equals("")) {
                        if (k.getBoolean("check_notifi", true) && Build.MANUFACTURER.toLowerCase().contains("huawei")) {
                            Dialog_help_notification notifi = new Dialog_help_notification();
                            notifi.show(getSupportFragmentManager(), "help_notification");
                        }
                    }
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                    editText2.setText("");
                    spinner.setSelection(0);
                    spinner4.setSelection(0);
                    spinner5.setSelection(0);
                    radioGroup.setVisibility(View.GONE);
                    String nol1 = "", nol2 = "";
                    c.add(Calendar.HOUR_OF_DAY, 1);
                    if (c.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
                    if (c.get(Calendar.MONTH) < 9) nol2 = "0";
                    da = nol1 + c.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR);
                    ta = timeH + ":00";
                    textViewDate.setText(da);
                    textViewTime.setText(ta);
                    textViewDateK.setText(da);
                    textViewTimeK.setText(ta);
                    LinearLayout layout = new LinearLayout(sabytie.this);
                    if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
                    else layout.setBackgroundResource(R.color.colorPrimary);
                    float density = getResources().getDisplayMetrics().density;
                    int realpadding = (int) (10 * density);
                    TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(sabytie.this);
                    toast.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                    toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                    toast.setText("Захавана");
                    toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                    layout.addView(toast);
                    Toast mes = new Toast(sabytie.this);
                    mes.setDuration(Toast.LENGTH_SHORT);
                    mes.setView(layout);
                    mes.show();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    titleLoy.setVisibility(View.GONE);
                    listLoy.setVisibility(View.VISIBLE);
                    idMenu = 1;
                    supportInvalidateOptionsMenu();
                } else {
                    editText.startAnimation(shakeanimation);
                }
            } catch (Exception ignored) {
            }
            //MyBackupAgent.requestBackup(this);
        }
        if (id == R.id.action_cansel) {
            back = false;
            home = false;
            c.add(Calendar.HOUR_OF_DAY, 1);
            String nol1 = "", nol2 = "";
            if (c.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
            if (c.get(Calendar.MONTH) < 9) nol2 = "0";
            editText.setText("");
            editText2.setText("");
            spinner.setSelection(0);
            spinner4.setSelection(0);
            spinner5.setSelection(0);
            radioGroup.setVisibility(View.GONE);
            da = nol1 + c.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR);
            ta = timeH + ":00";
            textViewDate.setText(da);
            textViewTime.setText(ta);
            textViewDateK.setText(da);
            textViewTimeK.setText(ta);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            titleLoy.setVisibility(View.GONE);
            listLoy.setVisibility(View.VISIBLE);
            idMenu = 1;
            supportInvalidateOptionsMenu();
            //return true;
        }
        if (id == R.id.action_delite) {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(this);
            if (dzenNoch)
                textViewZaglavie.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            else
                textViewZaglavie.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            float density = getResources().getDisplayMetrics().density;
            int realpadding = (int) (10 * density);
            textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
            textViewZaglavie.setText(getResources().getString(R.string.remove));
            textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
            textViewZaglavie.setTypeface(null, Typeface.BOLD);
            textViewZaglavie.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            linearLayout.addView(textViewZaglavie);
            TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(this);
            textView.setPadding(realpadding, realpadding, realpadding, realpadding);
            textView.setText("Якія падзеі Вы жадаеце выдаліць?");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
            if (dzenNoch) textView.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            else textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
            linearLayout.addView(textView);
            ad.setView(linearLayout);
            ad.setPositiveButton("Усё", (dialog, arg1) -> {
                redak = true;
                new Thread(() -> {
                    for (Padzeia p : padzeia) {
                        if (!p.sec.equals("-1")) {
                            Intent intent = createIntent(p.padz, "Падзея" + " " + p.dat + " у " + p.tim, p.dat, p.tim);
                            long londs3 = p.paznic / 100000L;
                            PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                            am.cancel(pIntent);
                            pIntent.cancel();
                        }
                    }
                    File dir = new File(getFilesDir() + "/Sabytie");
                    for (String s : dir.list()) {
                        File file = new File(getFilesDir() + "/Sabytie/" + s);
                        file.delete();
                    }
                    padzeia.clear();
                    //MyBackupAgent.requestBackup(sabytie.this);
                }).start();
                adapter.clear();
                adapter.notifyDataSetChanged();
                LinearLayout layout = new LinearLayout(sabytie.this);
                if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
                else layout.setBackgroundResource(R.color.colorPrimary);
                TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(sabytie.this);
                toast.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
                toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                toast.setText("Выдалена");
                toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                layout.addView(toast);
                Toast mes = new Toast(sabytie.this);
                mes.setDuration(Toast.LENGTH_SHORT);
                mes.setView(layout);
                mes.show();
            });
            ad.setNeutralButton("Старыя", (dialog, which) -> {
                c = (GregorianCalendar) Calendar.getInstance();
                GregorianCalendar c2 = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), 0);
                ArrayList<Padzeia> del = new ArrayList<>();
                for (Padzeia p : padzeia) {
                    if (p.repit == 0) {
                        String[] days = p.datK.split("[.]");
                        String[] time = p.timK.split(":");
                        GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]), 0);
                        if (c2.getTimeInMillis() >= gc.getTimeInMillis()) {
                            if (!p.sec.equals("-1")) {
                                Intent intent = createIntent(p.padz, "Падзея" + " " + p.dat + " у " + p.tim, p.dat, p.tim);
                                long londs3 = p.paznic / 100000L;
                                PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                am.cancel(pIntent);
                                pIntent.cancel();
                            }
                            del.add(p);
                        }
                    } else {
                        String[] days = p.dat.split("[.]");
                        String[] time = p.timK.split(":");
                        GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]), 0);
                        if (c2.getTimeInMillis() >= gc.getTimeInMillis()) {
                            if (!p.sec.equals("-1")) {
                                Intent intent = createIntent(p.padz, "Падзея" + " " + p.dat + " у " + p.tim, p.dat, p.tim);
                                long londs3 = p.paznic / 100000L;
                                PendingIntent pIntent = PendingIntent.getBroadcast(sabytie.this, (int) londs3, intent, 0);
                                am.cancel(pIntent);
                                pIntent.cancel();
                            }
                            del.add(p);
                        }
                    }
                }
                if (del.size() != 0) {
                    redak = true;
                    File dir = new File(getFilesDir() + "/Sabytie/");
                    String[] list = dir.list();
                    for (String aList : list) {
                        File file = new File(getFilesDir() + "/Sabytie/" + aList);
                        StringBuilder sb = new StringBuilder();
                        try {
                            FileReader inputStream = new FileReader(file);
                            BufferedReader reader = new BufferedReader(inputStream);
                            String line;
                            while ((line = reader.readLine()) != null && !line.equals("")) {
                                String[] t1 = line.split(" ");
                                String[] days = t1[1].split("[.]");
                                String[] time = t1[7].split(":");
                                GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]), 0);
                                if (!(c2.getTimeInMillis() > gc.getTimeInMillis())) {
                                    sb.append(line).append("\n");
                                }
                            }
                            inputStream.close();
                            if (sb.length() != 0) {
                                FileWriter outputStream = new FileWriter(getFilesDir() + "/Sabytie/" + aList);
                                outputStream.write(sb.toString());
                                outputStream.close();
                            } else {
                                new File(getFilesDir() + "/Sabytie/" + aList).delete();
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    padzeia.removeAll(del);
                    Collections.sort(padzeia);
                    for (Padzeia p : del) {
                        if (p.repit == 0) {
                            File file = new File(getFilesDir() + "/Sabytie/" + p.file);
                            file.delete();
                        }
                    }
                    sabytie2.clear();
                    for (Padzeia p2 : padzeia) {
                        sabytie2.add(p2.dat + " " + p2.padz.replace("_", " "));
                    }
                    adapter.notifyDataSetChanged();
                }
                //MyBackupAgent.requestBackup(this);
            });
            ad.setNegativeButton("Адмена", (dialog, arg1) -> dialog.cancel());
            AlertDialog alert = ad.create();
            alert.setOnShowListener(dialog -> {
                Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                Button btnNegative = alert.getButton(Dialog.BUTTON_NEGATIVE);
                btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                Button btnNeutral = alert.getButton(Dialog.BUTTON_NEUTRAL);
                btnNeutral.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                if (dzenNoch) {
                    btnPositive.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
                    btnNegative.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
                    btnNeutral.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
                }
            });
            alert.show();
        }
        if (id == R.id.action_add) {
            save = false;
            back = true;
            String nol1 = "", nol2 = "";
            if (c.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
            if (c.get(Calendar.MONTH) < 9) nol2 = "0";
            da = nol1 + c.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR);
            c.add(Calendar.HOUR_OF_DAY, 1);
            timeH = c.get(Calendar.HOUR_OF_DAY);
            ta = timeH + ":00";
            nol1 = "";
            nol2 = "";
            if (c.get(Calendar.DAY_OF_MONTH) < 10) nol1 = "0";
            if (c.get(Calendar.MONTH) < 9) nol2 = "0";
            daK = nol1 + c.get(Calendar.DAY_OF_MONTH) + "." + nol2 + (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.YEAR);
            taK = ta;
            titleLoy.setVisibility(View.VISIBLE);
            listLoy.setVisibility(View.GONE);
            textViewDate.setText(da);
            textViewTime.setText(ta);
            textViewDateK.setText(daK);
            textViewTimeK.setText(taK);
            konec = false;
            idMenu = 2;
            spinner4.setSelection(0);
            spinner5.setSelection(0);
            color = 0;
            editSave = editText.getText().toString();
            edit2Save = editText2.getText().toString();
            daSave = textViewDate.getText().toString();
            taSave = textViewTime.getText().toString();
            daKSave = textViewDateK.getText().toString();
            taKSave = textViewTimeK.getText().toString();
            supportInvalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    private Intent createIntent(String action, String extra, @NonNull String data, @NonNull String time) {
        Intent i = new Intent(this, ReceiverBroad.class);
        i.setAction(action);
        i.putExtra("sabytieSet", true);
        i.putExtra("extra", extra);
        String[] dateN = data.split("[.]");
        String[] timeN = time.split(":");
        GregorianCalendar g = new GregorianCalendar(Integer.parseInt(dateN[2]), Integer.parseInt(dateN[1]) - 1, Integer.parseInt(dateN[0]), 0, 0, 0);
        i.putExtra("dataString", dateN[0] + dateN[1] + timeN[0] + timeN[1]);
        i.putExtra("year", g.get(Calendar.YEAR));
        return i;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("redak", redak);
        outState.putBoolean("back", back);
    }

    class SabytieAdapter extends ArrayAdapter<String> {

        SabytieAdapter(Context context) {
            super(context, R.layout.simple_list_item_3, R.id.label, sabytie2);
            k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            String[] sab = sabytie2.get(position).split(" ");
            String[] data = sab[0].split("[.]");
            GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(data[2]), Integer.parseInt(data[1]) - 1, Integer.parseInt(data[0]));
            GregorianCalendar day = (GregorianCalendar) Calendar.getInstance();
            if (convertView == null) {
                convertView = sabytie.this.getLayoutInflater().inflate(R.layout.simple_list_item_3, parent, false);
                viewHolder = new ViewHolder();
                convertView.setTag(viewHolder);
                viewHolder.text = convertView.findViewById(R.id.label);
                viewHolder.button_popup = convertView.findViewById(R.id.button_popup);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (gc.get(Calendar.DAY_OF_YEAR) == day.get(Calendar.DAY_OF_YEAR) && gc.get(Calendar.YEAR) == day.get(Calendar.YEAR)) {
                viewHolder.text.setTypeface(null, Typeface.BOLD);
            } else {
                viewHolder.text.setTypeface(null, Typeface.NORMAL);
            }

            viewHolder.button_popup.setOnClickListener((v -> showPopupMenu(viewHolder.button_popup, position)));

            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
            viewHolder.text.setText(sabytie2.get(position));
            if (dzenNoch)
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            return convertView;
        }

        @SuppressWarnings("SameReturnValue")
        private void showPopupMenu(View view, int position) {
            PopupMenu popup = new PopupMenu(sabytie.this, view);
            MenuInflater infl = popup.getMenuInflater();
            infl.inflate(R.menu.popup, popup.getMenu());
            for (int i = 0; i < popup.getMenu().size(); i++) {
                MenuItem item = popup.getMenu().getItem(i);
                SpannableString spanString = new SpannableString(popup.getMenu().getItem(i).getTitle().toString());
                int end = spanString.length();
                spanString.setSpan(new AbsoluteSizeSpan(SettingsActivity.GET_FONT_SIZE_MIN, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                item.setTitle(spanString);
            }
            popup.setOnMenuItemClickListener(menuItem -> {
                popup.dismiss();
                switch (menuItem.getItemId()) {
                    case R.id.menu_redoktor:
                        onPopupRedaktor(position);
                        break;
                    case R.id.menu_remove:
                        onDialogDeliteClick(position);
                        break;
                }
                return true;
            });
            popup.show();
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
        ImageView button_popup;
    }

    class ColorAdapter extends ArrayAdapter<String> {

        ColorAdapter(@NonNull Context context) {
            super(context, R.layout.simple_list_item_color, R.id.label, colors);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolderColor viewHolder;
            if (convertView == null) {
                convertView = sabytie.this.getLayoutInflater().inflate(R.layout.simple_list_item_color, parent, false);
                viewHolder = new ViewHolderColor();
                convertView.setTag(viewHolder);
                viewHolder.text = convertView.findViewById(R.id.label);
            } else {
                viewHolder = (ViewHolderColor) convertView.getTag();
            }
            viewHolder.text.setBackgroundColor(Color.parseColor(colors[position]));
            viewHolder.text.setText("Назва падзеі");
            viewHolder.text.setTextSize(SettingsActivity.GET_FONT_SIZE_MIN);
            viewHolder.text.setTextColor(ContextCompat.getColor(sabytie.this, R.color.colorIcons));
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView text = view.findViewById(R.id.label);
            text.setBackgroundColor(Color.parseColor(colors[position]));
            text.setText("Назва падзеі");
            text.setTextSize(SettingsActivity.GET_FONT_SIZE_MIN);
            text.setTextColor(ContextCompat.getColor(sabytie.this, R.color.colorIcons));
            return view;
        }
    }

    private static class ViewHolderColor {
        TextView_Roboto_Condensed text;
    }
}
