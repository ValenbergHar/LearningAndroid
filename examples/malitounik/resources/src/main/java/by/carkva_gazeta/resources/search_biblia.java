package by.carkva_gazeta.resources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import by.carkva_gazeta.malitounik.EditText_Roboto_Condensed;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 5.10.16
 */

@SuppressWarnings({"unchecked"})
public class search_biblia extends AppCompatActivity implements View.OnClickListener, Dialog_bible_searsh_settings.diallog_bible_searsh_listiner {

    private TextView_Roboto_Condensed akafist;
    private ListView listView;
    private EditText_Roboto_Condensed editText;
    private EditText_Roboto_Condensed editText2;
    private ArrayList<String> seash;
    private search_biblia_ListAdaprer adapter;
    private SharedPreferences.Editor prefEditors;
    private static int zavet = 1;
    private boolean dzenNoch;
    private static WeakReference<ArrayAdapter<String>> adapterReference;
    private static ArrayMap<String, Integer> setSinodalBible;
    private static ArrayMap<String, Integer> setSemuxaBible;
    private static boolean searche = false;
    private long mLastClickTime = 0;

    @Override
    protected void onPause() {
        super.onPause();
        prefEditors.putString("search_string_filter", Objects.requireNonNull(editText2.getText()).toString());
        prefEditors.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    /*@Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //savedInstanceState.putSerializable("seash", seash);
    }*/

    @Override
    public void OnSetSettings(String edit) {
        if (edit.length() >= 3) {
            Poshuk poshuk = new Poshuk(this);
            poshuk.execute(edit);
        }
    }

    private static void setBibleSinodal() throws IllegalAccessException {
        setSinodalBible = new ArrayMap<>();
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            if (field.getName().contains("sinaidal")) {
                setSinodalBible.put(field.getName(), field.getInt(null));
            }
        }
    }

    private static void setBibleSemuxa() throws IllegalAccessException {
        setSemuxaBible = new ArrayMap<>();
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            if (field.getName().contains("biblia")) {
                setSemuxaBible.put(field.getName(), field.getInt(null));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        SharedPreferences chin = getSharedPreferences("biblia", MODE_PRIVATE);
        prefEditors = chin.edit();
        dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(by.carkva_gazeta.malitounik.R.layout.search_biblia);
        akafist = findViewById(by.carkva_gazeta.malitounik.R.id.TextView);
        listView = findViewById(by.carkva_gazeta.malitounik.R.id.ListView);
        editText = findViewById(by.carkva_gazeta.malitounik.R.id.editText);
        ImageView buttonx = findViewById(by.carkva_gazeta.malitounik.R.id.buttonx);
        ImageView buttonx2 = findViewById(by.carkva_gazeta.malitounik.R.id.buttonx2);
        buttonx2.setVisibility(View.VISIBLE);
        buttonx.setOnClickListener(this);
        buttonx2.setOnClickListener(this);
        if (dzenNoch) {
            buttonx.setImageResource(by.carkva_gazeta.malitounik.R.drawable.cancel);
            buttonx2.setImageResource(by.carkva_gazeta.malitounik.R.drawable.cancel);
        }
        editText.addTextChangedListener(new MyTextWatcher(editText, false));
        editText.setSelection(Objects.requireNonNull(editText.getText()).toString().length());
        editText2 = findViewById(by.carkva_gazeta.malitounik.R.id.editText2);
        editText2.setVisibility(View.VISIBLE);
        editText2.addTextChangedListener(new MyTextWatcher(editText2, true));
        akafist.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        //if (savedInstanceState != null) {
        //seash = (ArrayList<String>) savedInstanceState.getSerializable("seash");
        //} else {
        //seash = new ArrayList<>();
        //}
        seash = new ArrayList<>();
        if (getIntent() != null && getIntent().getIntExtra("zavet", 1) != zavet) {
            prefEditors.putString("search_string", "");
            prefEditors.putString("search_string_filter", "");
            prefEditors.apply();
        }
        if (!Objects.requireNonNull(chin.getString("search_string", "")).equals("")) {
            //if (savedInstanceState == null) {
            Gson gson = new Gson();
            String json = chin.getString("search_array", "");
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            seash.addAll(Objects.requireNonNull(gson.fromJson(json, type)));
            akafist.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, seash.size()));
            //}
        }
        if (getIntent() != null)
            zavet = getIntent().getIntExtra("zavet", 1);
        String title = "";
        if (zavet == 1)
            title = getResources().getString(by.carkva_gazeta.malitounik.R.string.poshuk_semuxa);
        if (zavet == 2)
            title = getResources().getString(by.carkva_gazeta.malitounik.R.string.poshuk_sinoidal);
        if (zavet == 3)
            title = getResources().getString(by.carkva_gazeta.malitounik.R.string.poshuk_nadsan);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String edit = editText.getText().toString();
                if (edit.length() >= 3) {
                    Poshuk poshuk = new Poshuk(this);
                    poshuk.execute(edit);
                } else {
                    LinearLayout layout = new LinearLayout(this);
                    if (dzenNoch)
                        layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary_black);
                    else
                        layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
                    float density = getResources().getDisplayMetrics().density;
                    int realpadding = (int) (10 * density);
                    TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(this);
                    toast.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                    toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                    toast.setText(getString(by.carkva_gazeta.malitounik.R.string.seashmin));
                    toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                    layout.addView(toast);
                    Toast mes = new Toast(this);
                    mes.setDuration(Toast.LENGTH_SHORT);
                    mes.setView(layout);
                    mes.show();
                }
                editText.setSelection(edit.length());
                return true;
            }
            return false;
        });
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        akafist.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, seash.size()));
        adapter = new search_biblia_ListAdaprer(this);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == 1) {
                    // Скрываем клавиатуру
                    InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm1).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });
        editText.setText(chin.getString("search_string", ""));
        editText2.setText(chin.getString("search_string_filter", ""));
        adapterReference = new WeakReference<>(adapter);
        if (dzenNoch)
            akafist.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            String strText = adapterView.getAdapter().getItem(position).toString();
            int nazva = 0, nazvaS = -1;
            if (zavet == 2) {
                if (strText.contains("Бытие")) nazvaS = 0;
                if (strText.contains("Исход")) nazvaS = 1;
                if (strText.contains("Левит")) nazvaS = 2;
                if (strText.contains("Числа")) nazvaS = 3;
                if (strText.contains("Второзаконие")) nazvaS = 4;
                if (strText.contains("Иисуса Навина")) nazvaS = 5;
                if (strText.contains("Судей израилевых")) nazvaS = 6;
                if (strText.contains("Руфи")) nazvaS = 7;
                if (strText.contains("1-я Царств")) nazvaS = 8;
                if (strText.contains("2-я Царств")) nazvaS = 9;
                if (strText.contains("3-я Царств")) nazvaS = 10;
                if (strText.contains("4-я Царств")) nazvaS = 11;
                if (strText.contains("1-я Паралипоменон")) nazvaS = 12;
                if (strText.contains("2-я Паралипоменон")) nazvaS = 13;
                if (strText.contains("1-я Ездры")) nazvaS = 14;
                if (strText.contains("Неемии")) nazvaS = 15;
                if (strText.contains("2-я Ездры")) nazvaS = 16;
                if (strText.contains("Товита")) nazvaS = 17;
                if (strText.contains("Иудифи")) nazvaS = 18;
                if (strText.contains("Есфири")) nazvaS = 19;
                if (strText.contains("Иова")) nazvaS = 20;
                if (strText.contains("Псалтирь")) nazvaS = 21;
                if (strText.contains("Притчи Соломона")) nazvaS = 22;
                if (strText.contains("Екклезиаста")) nazvaS = 23;
                if (strText.contains("Песнь песней Соломона")) nazvaS = 24;
                if (strText.contains("Премудрости Соломона")) nazvaS = 25;
                if (strText.contains("Премудрости Иисуса, сына Сирахова")) nazvaS = 26;
                if (strText.contains("Исаии")) nazvaS = 27;
                if (strText.contains("Иеремии")) nazvaS = 28;
                if (strText.contains("Плач Иеремии")) nazvaS = 29;
                if (strText.contains("Послание Иеремии")) nazvaS = 30;
                if (strText.contains("Варуха")) nazvaS = 31;
                if (strText.contains("Иезекииля")) nazvaS = 32;
                if (strText.contains("Даниила")) nazvaS = 33;
                if (strText.contains("Осии")) nazvaS = 34;
                if (strText.contains("Иоиля")) nazvaS = 35;
                if (strText.contains("Амоса")) nazvaS = 36;
                if (strText.contains("Авдия")) nazvaS = 37;
                if (strText.contains("Ионы")) nazvaS = 38;
                if (strText.contains("Михея")) nazvaS = 39;
                if (strText.contains("Наума")) nazvaS = 40;
                if (strText.contains("Аввакума")) nazvaS = 41;
                if (strText.contains("Сафонии")) nazvaS = 42;
                if (strText.contains("Аггея")) nazvaS = 43;
                if (strText.contains("Захарии")) nazvaS = 44;
                if (strText.contains("Малахии")) nazvaS = 45;
                if (strText.contains("1-я Маккавейская")) nazvaS = 46;
                if (strText.contains("2-я Маккавейская")) nazvaS = 47;
                if (strText.contains("3-я Маккавейская")) nazvaS = 48;
                if (strText.contains("3-я Ездры")) nazvaS = 49;
                //if (strText.contains("От Матфея")) nazva = 0;
                if (strText.contains("От Марка")) nazva = 1;
                if (strText.contains("От Луки")) nazva = 2;
                if (strText.contains("От Иоанна")) nazva = 3;
                if (strText.contains("Деяния святых апостолов")) nazva = 4;
                if (strText.contains("Иакова")) nazva = 5;
                if (strText.contains("1-е Петра")) nazva = 6;
                if (strText.contains("2-е Петра")) nazva = 7;
                if (strText.contains("1-е Иоанна")) nazva = 8;
                if (strText.contains("2-е Иоанна")) nazva = 9;
                if (strText.contains("3-е Иоанна")) nazva = 10;
                if (strText.contains("Иуды")) nazva = 11;
                if (strText.contains("Римлянам")) nazva = 12;
                if (strText.contains("1-е Коринфянам")) nazva = 13;
                if (strText.contains("2-е Коринфянам")) nazva = 14;
                if (strText.contains("Галатам")) nazva = 15;
                if (strText.contains("Эфэсянам")) nazva = 16;
                if (strText.contains("Филиппийцам")) nazva = 17;
                if (strText.contains("Колоссянам")) nazva = 18;
                if (strText.contains("1-е Фессалоникийцам (Солунянам)")) nazva = 19;
                if (strText.contains("2-е Фессалоникийцам (Солунянам)")) nazva = 20;
                if (strText.contains("1-е Тимофею")) nazva = 21;
                if (strText.contains("2-е Тимофею")) nazva = 22;
                if (strText.contains("Титу")) nazva = 23;
                if (strText.contains("Филимону")) nazva = 24;
                if (strText.contains("Евреям")) nazva = 25;
                if (strText.contains("Откровение (Апокалипсис)")) nazva = 26;
            }
            if (zavet == 1) {
                if (strText.contains("Быцьцё")) nazvaS = 0;
                if (strText.contains("Выхад")) nazvaS = 1;
                if (strText.contains("Лявіт")) nazvaS = 2;
                if (strText.contains("Лікі")) nazvaS = 3;
                if (strText.contains("Другі Закон")) nazvaS = 4;
                if (strText.contains("Ісуса сына Нава")) nazvaS = 5;
                if (strText.contains("Судзьдзяў")) nazvaS = 6;
                if (strText.contains("Рут")) nazvaS = 7;
                if (strText.contains("1-я Царстваў")) nazvaS = 8;
                if (strText.contains("2-я Царстваў")) nazvaS = 9;
                if (strText.contains("3-я Царстваў")) nazvaS = 10;
                if (strText.contains("4-я Царстваў")) nazvaS = 11;
                if (strText.contains("1-я Летапісаў")) nazvaS = 12;
                if (strText.contains("2-я Летапісаў")) nazvaS = 13;
                if (strText.contains("Эздры")) nazvaS = 14;
                if (strText.contains("Нээміі")) nazvaS = 15;
                if (strText.contains("Эстэр")) nazvaS = 16;
                if (strText.contains("Ёва")) nazvaS = 17;
                if (strText.contains("Псалтыр")) nazvaS = 18;
                if (strText.contains("Выслоўяў Саламонавых")) nazvaS = 19;
                if (strText.contains("Эклезіяста")) nazvaS = 20;
                if (strText.contains("Найвышэйшая Песьня Саламонава")) nazvaS = 21;
                if (strText.contains("Ісаі")) nazvaS = 22;
                if (strText.contains("Ераміі")) nazvaS = 23;
                if (strText.contains("Ераміін Плач")) nazvaS = 24;
                if (strText.contains("Езэкііля")) nazvaS = 25;
                if (strText.contains("Данііла")) nazvaS = 26;
                if (strText.contains("Асіі")) nazvaS = 27;
                if (strText.contains("Ёіля")) nazvaS = 28;
                if (strText.contains("Амоса")) nazvaS = 29;
                if (strText.contains("Аўдзея")) nazvaS = 30;
                if (strText.contains("Ёны")) nazvaS = 31;
                if (strText.contains("Міхея")) nazvaS = 32;
                if (strText.contains("Навума")) nazvaS = 33;
                if (strText.contains("Абакума")) nazvaS = 34;
                if (strText.contains("Сафона")) nazvaS = 35;
                if (strText.contains("Агея")) nazvaS = 36;
                if (strText.contains("Захарыі")) nazvaS = 37;
                if (strText.contains("Малахіі")) nazvaS = 38;
                //if (strText.contains("Паводле Мацьвея")) nazva = 0;
                if (strText.contains("Паводле Марка")) nazva = 1;
                if (strText.contains("Паводле Лукаша")) nazva = 2;
                if (strText.contains("Паводле Яна")) nazva = 3;
                if (strText.contains("Дзеі Апосталаў")) nazva = 4;
                if (strText.contains("Якава")) nazva = 5;
                if (strText.contains("1-е Пятра")) nazva = 6;
                if (strText.contains("2-е Пятра")) nazva = 7;
                if (strText.contains("1-е Яна Багаслова")) nazva = 8;
                if (strText.contains("2-е Яна Багаслова")) nazva = 9;
                if (strText.contains("3-е Яна Багаслова")) nazva = 10;
                if (strText.contains("Юды")) nazva = 11;
                if (strText.contains("Да Рымлянаў")) nazva = 12;
                if (strText.contains("1-е да Карынфянаў")) nazva = 13;
                if (strText.contains("2-е да Карынфянаў")) nazva = 14;
                if (strText.contains("Да Галятаў")) nazva = 15;
                if (strText.contains("Да Эфэсянаў")) nazva = 16;
                if (strText.contains("Да Піліпянаў")) nazva = 17;
                if (strText.contains("Да Каласянаў")) nazva = 18;
                if (strText.contains("1-е да Фесаланікійцаў")) nazva = 19;
                if (strText.contains("2-е да Фесаланікійцаў")) nazva = 20;
                if (strText.contains("1-е да Цімафея")) nazva = 21;
                if (strText.contains("2-е да Цімафея")) nazva = 22;
                if (strText.contains("Да Ціта")) nazva = 23;
                if (strText.contains("Да Філімона")) nazva = 24;
                if (strText.contains("Да Габрэяў")) nazva = 25;
                if (strText.contains("Адкрыцьцё (Апакаліпсіс)")) nazva = 26;
            }
            int str1;
            if (zavet == 3) {
                str1 = strText.indexOf("Пс. ");
            } else {
                str1 = strText.indexOf("Гл. ");
            }
            int str2 = strText.indexOf("</strong>");
            int str3 = strText.indexOf("<!--stix.");
            int str4 = strText.indexOf("-->");
            int glava = Integer.parseInt(strText.substring(str1 + 4, str2));
            int stix = Integer.parseInt(strText.substring(str3 + 9, str4));
            if (zavet == 3) {
                Intent intent = new Intent(search_biblia.this, nadsanContentActivity.class);
                intent.putExtra("glava", glava - 1);
                intent.putExtra("stix", stix - 1);
                prefEditors.putInt("search_position", listView.getFirstVisiblePosition());
                prefEditors.apply();
                startActivity(intent);
            } else {
                if (nazvaS != -1) {
                    if (zavet == 1) {
                        Intent intent = new Intent(search_biblia.this, stary_zapaviet3.class);
                        intent.putExtra("kniga", nazvaS);
                        intent.putExtra("glava", glava - 1);
                        intent.putExtra("stix", stix - 1);
                        prefEditors.putBoolean("novyzavet", false);
                        prefEditors.putInt("search_position", listView.getFirstVisiblePosition());
                        prefEditors.apply();
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(search_biblia.this, stary_zapaviet_sinaidal3.class);
                        intent.putExtra("kniga", nazvaS);
                        intent.putExtra("glava", glava - 1);
                        intent.putExtra("stix", stix - 1);
                        prefEditors.putBoolean("novyzavet", false);
                        prefEditors.putInt("search_position", listView.getFirstVisiblePosition());
                        prefEditors.apply();
                        startActivity(intent);
                    }
                } else {
                    if (zavet == 1) {
                        Intent intent = new Intent(search_biblia.this, novy_zapaviet3.class);
                        intent.putExtra("kniga", nazva);
                        intent.putExtra("glava", glava - 1);
                        intent.putExtra("stix", stix - 1);
                        prefEditors.putBoolean("novyzavet", true);
                        prefEditors.putInt("search_position", listView.getFirstVisiblePosition());
                        prefEditors.apply();
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(search_biblia.this, novy_zapaviet_sinaidal3.class);
                        intent.putExtra("kniga", nazva);
                        intent.putExtra("glava", glava - 1);
                        intent.putExtra("stix", stix - 1);
                        prefEditors.putBoolean("novyzavet", true);
                        prefEditors.putInt("search_position", listView.getFirstVisiblePosition());
                        prefEditors.apply();
                        startActivity(intent);
                    }
                }
            }
        });

        try {
            setBibleSinodal();
            setBibleSemuxa();
        } catch (IllegalAccessException ignored) {
        }

        setTollbarTheme(title);
    }

    private void setTollbarTheme(String title) {
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
        title_toolbar.setText(title);
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.search_biblia, menu);
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
        int id = item.getItemId();
        if (id == by.carkva_gazeta.malitounik.R.id.action_settings) {
            Dialog_bible_searsh_settings dialog_bible_searsh_settings = Dialog_bible_searsh_settings.getInstance(Objects.requireNonNull(editText.getText()).toString());
            dialog_bible_searsh_settings.show(getSupportFragmentManager(), "bible_searsh_settings");
        }
        return super.onOptionsItemSelected(item);
    }

    private static String zamena(String replase) {
        replase = replase.replace("ё", "е");
        replase = replase.replace("и", "і");
        replase = replase.replace("щ", "ў");
        replase = replase.replace("ъ", "'");
        replase = replase.replace("све", "сьве");
        replase = replase.replace("сві", "сьві");
        replase = replase.replace("свя", "сьвя");
        replase = replase.replace("зве", "зьве");
        replase = replase.replace("зві", "зьві");
        replase = replase.replace("звя", "зьвя");
        replase = replase.replace("зме", "зьме");
        replase = replase.replace("змі", "зьмі");
        replase = replase.replace("змя", "зьмя");
        replase = replase.replace("зня", "зьня");
        replase = replase.replace("сле", "сьле");
        replase = replase.replace("слі", "сьлі");
        replase = replase.replace("сль", "сьль");
        replase = replase.replace("слю", "сьлю");
        replase = replase.replace("сля", "сьля");
        replase = replase.replace("сне", "сьне");
        replase = replase.replace("сні", "сьні");
        replase = replase.replace("сню", "сьню");
        replase = replase.replace("сня", "сьня");
        replase = replase.replace("спе", "сьпе");
        replase = replase.replace("спі", "сьпі");
        replase = replase.replace("спя", "сьпя");
        replase = replase.replace("сце", "сьце");
        replase = replase.replace("сці", "сьці");
        replase = replase.replace("сць", "сьць");
        replase = replase.replace("сцю", "сьцю");
        replase = replase.replace("сця", "сьця");
        replase = replase.replace("цце", "цьце");
        replase = replase.replace("цці", "цьці");
        replase = replase.replace("ццю", "цьцю");
        replase = replase.replace("ззе", "зьзе");
        replase = replase.replace("ззі", "зьзі");
        replase = replase.replace("ззю", "зьзю");
        replase = replase.replace("ззя", "зьзя");
        replase = replase.replace("зле", "зьле");
        replase = replase.replace("злі", "зьлі");
        replase = replase.replace("злю", "зьлю");
        replase = replase.replace("зля", "зьля");
        replase = replase.replace("збе", "зьбе");
        replase = replase.replace("збі", "зьбі");
        replase = replase.replace("збя", "зьбя");
        replase = replase.replace("нне", "ньне");
        replase = replase.replace("нні", "ньні");
        replase = replase.replace("нню", "ньню");
        replase = replase.replace("ння", "ньня");
        replase = replase.replace("лле", "льле");
        replase = replase.replace("ллі", "льлі");
        replase = replase.replace("ллю", "льлю");
        replase = replase.replace("лля", "льля");
        replase = replase.replace("дск", "дзк");
        replase = replase.replace("дств", "дзтв");
        replase = replase.replace("з’е", "зье");
        replase = replase.replace("з’я", "зья");
        return replase;
    }

    private static ArrayList<String> Semuxa(Context context, String poshuk) {
        SharedPreferences chin = context.getSharedPreferences("biblia", MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        ArrayList<String> seashpost = new ArrayList<>();
        if (!poshuk.equals("")) {
            poshuk = zamena(poshuk);
            if (chin.getInt("pegistr", 0) == 0) poshuk = poshuk.toLowerCase();
            //String[] poshukchar = poshuk.split(" ");

            if (chin.getInt("slovocalkam", 0) == 0) {
                char[] m = {'у', 'е', 'а', 'о', 'э', 'я', 'и', 'ю', 'ь', 'ы'};
                /*if (chin.getInt("kohnoeslovo", 0) == 1) {
                    for (int e = 0; e < poshukchar.length; e++) {
                        if (poshukchar[e].length() > 2) {
                            for (char aM : m) {
                                int r = poshukchar[e].length() - 1;
                                if (poshukchar[e].length() >= 3) {
                                    if (poshukchar[e].charAt(r) == aM) {
                                        poshukchar[e] = poshukchar[e].replace(poshukchar[e], poshukchar[e].substring(0, r));
                                    }
                                }
                            }
                        }
                    }
                } else {*/
                for (char aM : m) {
                    int r = poshuk.length() - 1;
                    if (poshuk.length() >= 3) {
                        if (poshuk.charAt(r) == aM) {
                            poshuk = poshuk.replace(poshuk, poshuk.substring(0, r));
                        }
                    }
                }
                //}
            } else {
                poshuk = " " + poshuk + " ";
            }
            String color = "<font color=#d00505>";
            if (dzenNoch)
                color = "<font color=#f44336>";
            for (int i = 0; i < setSemuxaBible.size(); i++) {
                String biblia = "biblia";
                if (chin.getInt("biblia_seash", 0) == 1) biblia = "biblian";
                if (chin.getInt("biblia_seash", 0) == 2) biblia = "biblias";
                if (setSemuxaBible.keyAt(i).contains(biblia)) {
                    String nazva = "";
                    if (setSemuxaBible.keyAt(i).contains("biblias1")) nazva = "Быцьцё";
                    if (setSemuxaBible.keyAt(i).contains("biblias2")) nazva = "Выхад";
                    if (setSemuxaBible.keyAt(i).contains("biblias3")) nazva = "Лявіт";
                    if (setSemuxaBible.keyAt(i).contains("biblias4")) nazva = "Лікі";
                    if (setSemuxaBible.keyAt(i).contains("biblias5"))
                        nazva = "Другі Закон";
                    if (setSemuxaBible.keyAt(i).contains("biblias6"))
                        nazva = "Ісуса сына Нава";
                    if (setSemuxaBible.keyAt(i).contains("biblias7"))
                        nazva = "Судзьдзяў";
                    if (setSemuxaBible.keyAt(i).contains("biblias8")) nazva = "Рут";
                    if (setSemuxaBible.keyAt(i).contains("biblias9"))
                        nazva = "1-я Царстваў";
                    if (setSemuxaBible.keyAt(i).contains("biblias10"))
                        nazva = "2-я Царстваў";
                    if (setSemuxaBible.keyAt(i).contains("biblias11"))
                        nazva = "3-я Царстваў";
                    if (setSemuxaBible.keyAt(i).contains("biblias12"))
                        nazva = "4-я Царстваў";
                    if (setSemuxaBible.keyAt(i).contains("biblias13"))
                        nazva = "1-я Летапісаў";
                    if (setSemuxaBible.keyAt(i).contains("biblias14"))
                        nazva = "2-я Летапісаў";
                    if (setSemuxaBible.keyAt(i).contains("biblias15")) nazva = "Эздры";
                    if (setSemuxaBible.keyAt(i).contains("biblias16"))
                        nazva = "Нээміі";
                    if (setSemuxaBible.keyAt(i).contains("biblias17")) nazva = "Эстэр";
                    if (setSemuxaBible.keyAt(i).contains("biblias18")) nazva = "Ёва";
                    if (setSemuxaBible.keyAt(i).contains("biblias19"))
                        nazva = "Псалтыр";
                    if (setSemuxaBible.keyAt(i).contains("biblias20"))
                        nazva = "Выслоўяў Саламонавых";
                    if (setSemuxaBible.keyAt(i).contains("biblias21"))
                        nazva = "Эклезіяста";
                    if (setSemuxaBible.keyAt(i).contains("biblias22"))
                        nazva = "Найвышэйшая Песьня Саламонава";
                    if (setSemuxaBible.keyAt(i).contains("biblias23")) nazva = "Ісаі";
                    if (setSemuxaBible.keyAt(i).contains("biblias24"))
                        nazva = "Ераміі";
                    if (setSemuxaBible.keyAt(i).contains("biblias25"))
                        nazva = "Ераміін Плач";
                    if (setSemuxaBible.keyAt(i).contains("biblias26"))
                        nazva = "Езэкііля";
                    if (setSemuxaBible.keyAt(i).contains("biblias27"))
                        nazva = "Данііла";
                    if (setSemuxaBible.keyAt(i).contains("biblias28")) nazva = "Асіі";
                    if (setSemuxaBible.keyAt(i).contains("biblias29")) nazva = "Ёіля";
                    if (setSemuxaBible.keyAt(i).contains("biblias30")) nazva = "Амоса";
                    if (setSemuxaBible.keyAt(i).contains("biblias31"))
                        nazva = "Аўдзея";
                    if (setSemuxaBible.keyAt(i).contains("biblias32")) nazva = "Ёны";
                    if (setSemuxaBible.keyAt(i).contains("biblias33")) nazva = "Міхея";
                    if (setSemuxaBible.keyAt(i).contains("biblias34"))
                        nazva = "Навума";
                    if (setSemuxaBible.keyAt(i).contains("biblias35"))
                        nazva = "Абакума";
                    if (setSemuxaBible.keyAt(i).contains("biblias36"))
                        nazva = "Сафона";
                    if (setSemuxaBible.keyAt(i).contains("biblias37")) nazva = "Агея";
                    if (setSemuxaBible.keyAt(i).contains("biblias38"))
                        nazva = "Захарыі";
                    if (setSemuxaBible.keyAt(i).contains("biblias39"))
                        nazva = "Малахіі";
                    if (setSemuxaBible.keyAt(i).contains("biblian1"))
                        nazva = "Паводле Мацьвея";
                    if (setSemuxaBible.keyAt(i).contains("biblian2"))
                        nazva = "Паводле Марка";
                    if (setSemuxaBible.keyAt(i).contains("biblian3"))
                        nazva = "Паводле Лукаша";
                    if (setSemuxaBible.keyAt(i).contains("biblian4"))
                        nazva = "Паводле Яна";
                    if (setSemuxaBible.keyAt(i).contains("biblian5"))
                        nazva = "Дзеі Апосталаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian6")) nazva = "Якава";
                    if (setSemuxaBible.keyAt(i).contains("biblian7"))
                        nazva = "1-е Пятра";
                    if (setSemuxaBible.keyAt(i).contains("biblian8"))
                        nazva = "2-е Пятра";
                    if (setSemuxaBible.keyAt(i).contains("biblian9"))
                        nazva = "1-е Яна Багаслова";
                    if (setSemuxaBible.keyAt(i).contains("biblian10"))
                        nazva = "2-е Яна Багаслова";
                    if (setSemuxaBible.keyAt(i).contains("biblian11"))
                        nazva = "3-е Яна Багаслова";
                    if (setSemuxaBible.keyAt(i).contains("biblian12")) nazva = "Юды";
                    if (setSemuxaBible.keyAt(i).contains("biblian13"))
                        nazva = "Да Рымлянаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian14"))
                        nazva = "1-е да Карынфянаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian15"))
                        nazva = "2-е да Карынфянаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian16"))
                        nazva = "Да Галятаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian17"))
                        nazva = "Да Эфэсянаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian18"))
                        nazva = "Да Піліпянаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian19"))
                        nazva = "Да Каласянаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian20"))
                        nazva = "1-е да Фесаланікійцаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian21"))
                        nazva = "2-е да Фесаланікійцаў";
                    if (setSemuxaBible.keyAt(i).contains("biblian22"))
                        nazva = "1-е да Цімафея";
                    if (setSemuxaBible.keyAt(i).contains("biblian23"))
                        nazva = "2-е да Цімафея";
                    if (setSemuxaBible.keyAt(i).contains("biblian24"))
                        nazva = "Да Ціта";
                    if (setSemuxaBible.keyAt(i).contains("biblian25"))
                        nazva = "Да Філімона";
                    if (setSemuxaBible.keyAt(i).contains("biblian26"))
                        nazva = "Да Габрэяў";
                    if (setSemuxaBible.keyAt(i).contains("biblian27"))
                        nazva = "Адкрыцьцё (Апакаліпсіс)";
                    try {
                        InputStream inputStream = context.getResources().openRawResource(setSemuxaBible.valueAt(i));
                        InputStreamReader isr = new InputStreamReader(inputStream);
                        BufferedReader reader = new BufferedReader(isr);
                        String line;
                        StringBuilder builder = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            builder.append(line).append("\n");
                        }
                        inputStream.close();
                        int glava = 0;
                        String[] split = builder.toString().split("===");
                        for (int e = 1; e < split.length; e++) {
                            glava++;
                            String[] bibleline = split[e].split("\n");
                            int stix = 0;
                            for (int r = 1; r < bibleline.length; r++) {
                                String prepinanie = bibleline[r];
                                if (prepinanie.contains("//")) {
                                    int t1 = prepinanie.indexOf("//");
                                    if (t1 == 0)
                                        continue;
                                    else
                                        prepinanie = prepinanie.substring(0, t1).trim();
                                }
                                stix++;
                                if (chin.getInt("slovocalkam", 0) == 1)
                                    prepinanie = " " + bibleline[r] + " ";
                                if (chin.getInt("pegistr", 0) == 0)
                                    prepinanie = prepinanie.toLowerCase();
                                int t1a = prepinanie.indexOf(poshuk);
                                prepinanie = prepinanie.replace(",", "");
                                prepinanie = prepinanie.replace(".", "");
                                prepinanie = prepinanie.replace(";", "");
                                prepinanie = prepinanie.replace(":", "");
                                prepinanie = prepinanie.replace("-", "");
                                prepinanie = prepinanie.replace("\"", "");
                                int count = 0;
                                if (t1a != -1)
                                    count = t1a - prepinanie.indexOf(poshuk);
                                prepinanie = prepinanie.replace("ё", "е");
                                prepinanie = prepinanie.replace("<em>", "    ");
                                prepinanie = prepinanie.replace("</em>", "     ");
                                prepinanie = prepinanie.replace("<br>", "    ");
                                prepinanie = prepinanie.replace("<strong>", "        ");
                                prepinanie = prepinanie.replace("</strong>", "         ");
                                if (chin.getInt("slovocalkam", 0) == 0) {
                                    /*if (chin.getInt("kohnoeslovo", 0) == 1) {
                                        boolean add = true;
                                        for (String aPoshukchar : poshukchar) {
                                            if (aPoshukchar.length() > 2) {
                                                if ((prepinanie.contains(aPoshukchar))) {
                                                    if (add) {
                                                        String aSviatyia = bibleline[r];
                                                        if (chin.getInt("pegistr", 0) == 0)
                                                            aPoshukchar = aPoshukchar.toLowerCase();
                                                        int t1 = prepinanie.indexOf(aPoshukchar);
                                                        t1 = t1 + count;
                                                        int t2 = aPoshukchar.length();
                                                        aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                                                        seashpost.add("<!--stix." + stix + "--><strong>" + nazva + " Гл. " + glava + "</strong><br>" + aSviatyia);
                                                        add = false;
                                                    }
                                                }
                                            }
                                        }
                                    } else {*/
                                    if (prepinanie.contains(poshuk)) {
                                        String aSviatyia = bibleline[r];
                                        int t1 = prepinanie.indexOf(poshuk);
                                        t1 = t1 + count;
                                        int t2 = poshuk.length();
                                        aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                                        seashpost.add("<!--stix." + stix + "--><strong>" + nazva + " Гл. " + glava + "</strong><br>" + aSviatyia);
                                    }
                                    //}
                                } else {
                                    if (prepinanie.contains(poshuk)) {
                                        String aSviatyia = bibleline[r];
                                        int t1 = prepinanie.indexOf(poshuk);
                                        t1 = t1 + count;
                                        int t2 = poshuk.length();
                                        aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                                        seashpost.add("<!--stix." + stix + "--><strong>" + nazva + " Гл. " + glava + "</strong><br>" + aSviatyia);
                                    }
                                }
                            }
                        }
                    } catch (Throwable ignored) {
                    }
                }
            }
        }
        return seashpost;
    }

    @NonNull
    private static ArrayList<String> Sinoidal(@NonNull Context context, @NonNull String poshuk) {
        SharedPreferences chin = context.getSharedPreferences("biblia", MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        ArrayList<String> seashpost = new ArrayList<>();
        if (!poshuk.equals("")) {
            poshuk = poshuk.replace("ё", "е");
            if (chin.getInt("pegistr", 0) == 0) poshuk = poshuk.toLowerCase();
            //String[] poshukchar = poshuk.split(" ");
            if (chin.getInt("slovocalkam", 0) == 0) {
                char[] m = {'у', 'е', 'а', 'о', 'э', 'я', 'и', 'ю', 'ь', 'ы'};
                /*if (chin.getInt("kohnoeslovo", 0) == 1) {
                    for (int e = 0; e < poshukchar.length; e++) {
                        if (poshukchar[e].length() > 2) {
                            for (char aM : m) {
                                int r = poshukchar[e].length() - 1;
                                if (poshukchar[e].length() >= 0) {
                                    if (poshukchar[e].charAt(r) == aM) {
                                        poshukchar[e] = poshukchar[e].replace(poshukchar[e], poshukchar[e].substring(0, r));
                                    }
                                }
                            }
                        }
                    }
                } else {*/
                for (char aM : m) {
                    int r = poshuk.length() - 1;
                    if (poshuk.charAt(r) == aM) {
                        poshuk = poshuk.replace(poshuk, poshuk.substring(0, r));
                    }
                }
                //}
            } else {
                poshuk = " " + poshuk + " ";
            }
            String color = "<font color=#d00505>";
            if (dzenNoch)
                color = "<font color=#f44336>";
            for (int i = 0; i < setSinodalBible.size(); i++) {
                String biblia = "sinaidal";
                if (chin.getInt("biblia_seash", 0) == 1) biblia = "sinaidaln";
                if (chin.getInt("biblia_seash", 0) == 2) biblia = "sinaidals";
                if (setSinodalBible.keyAt(i).contains(biblia)) {
                    String nazva = "";
                    if (setSinodalBible.keyAt(i).contains("sinaidals1"))
                        nazva = "Бытие";
                    if (setSinodalBible.keyAt(i).contains("sinaidals2"))
                        nazva = "Исход";
                    if (setSinodalBible.keyAt(i).contains("sinaidals3"))
                        nazva = "Левит";
                    if (setSinodalBible.keyAt(i).contains("sinaidals4"))
                        nazva = "Числа";
                    if (setSinodalBible.keyAt(i).contains("sinaidals5"))
                        nazva = "Второзаконие";
                    if (setSinodalBible.keyAt(i).contains("sinaidals6"))
                        nazva = "Иисуса Навина";
                    if (setSinodalBible.keyAt(i).contains("sinaidals7"))
                        nazva = "Судей израилевых";
                    if (setSinodalBible.keyAt(i).contains("sinaidals8"))
                        nazva = "Руфи";
                    if (setSinodalBible.keyAt(i).contains("sinaidals9"))
                        nazva = "1-я Царств";
                    if (setSinodalBible.keyAt(i).contains("sinaidals10"))
                        nazva = "2-я Царств";
                    if (setSinodalBible.keyAt(i).contains("sinaidals11"))
                        nazva = "3-я Царств";
                    if (setSinodalBible.keyAt(i).contains("sinaidals12"))
                        nazva = "4-я Царств";
                    if (setSinodalBible.keyAt(i).contains("sinaidals13"))
                        nazva = "1-я Паралипоменон";
                    if (setSinodalBible.keyAt(i).contains("sinaidals14"))
                        nazva = "2-я Паралипоменон";
                    if (setSinodalBible.keyAt(i).contains("sinaidals15"))
                        nazva = "1-я Ездры";
                    if (setSinodalBible.keyAt(i).contains("sinaidals16"))
                        nazva = "Неемии";
                    if (setSinodalBible.keyAt(i).contains("sinaidals17"))
                        nazva = "2-я Ездры";
                    if (setSinodalBible.keyAt(i).contains("sinaidals18"))
                        nazva = "Товита";
                    if (setSinodalBible.keyAt(i).contains("sinaidals19"))
                        nazva = "Иудифи";
                    if (setSinodalBible.keyAt(i).contains("sinaidals20"))
                        nazva = "Есфири";
                    if (setSinodalBible.keyAt(i).contains("sinaidals21"))
                        nazva = "Иова";
                    if (setSinodalBible.keyAt(i).contains("sinaidals22"))
                        nazva = "Псалтирь";
                    if (setSinodalBible.keyAt(i).contains("sinaidals23"))
                        nazva = "Притчи Соломона";
                    if (setSinodalBible.keyAt(i).contains("sinaidals24"))
                        nazva = "Екклезиаста";
                    if (setSinodalBible.keyAt(i).contains("sinaidals25"))
                        nazva = "Песнь песней Соломона";
                    if (setSinodalBible.keyAt(i).contains("sinaidals26"))
                        nazva = "Премудрости Соломона";
                    if (setSinodalBible.keyAt(i).contains("sinaidals27"))
                        nazva = "Премудрости Иисуса, сына Сирахова";
                    if (setSinodalBible.keyAt(i).contains("sinaidals28"))
                        nazva = "Исаии";
                    if (setSinodalBible.keyAt(i).contains("sinaidals29"))
                        nazva = "Иеремии";
                    if (setSinodalBible.keyAt(i).contains("sinaidals30"))
                        nazva = "Плач Иеремии";
                    if (setSinodalBible.keyAt(i).contains("sinaidals31"))
                        nazva = "Послание Иеремии";
                    if (setSinodalBible.keyAt(i).contains("sinaidals32"))
                        nazva = "Варуха";
                    if (setSinodalBible.keyAt(i).contains("sinaidals33"))
                        nazva = "Иезекииля";
                    if (setSinodalBible.keyAt(i).contains("sinaidals34"))
                        nazva = "Даниила";
                    if (setSinodalBible.keyAt(i).contains("sinaidals35"))
                        nazva = "Осии";
                    if (setSinodalBible.keyAt(i).contains("sinaidals36"))
                        nazva = "Иоиля";
                    if (setSinodalBible.keyAt(i).contains("sinaidals37"))
                        nazva = "Амоса";
                    if (setSinodalBible.keyAt(i).contains("sinaidals38"))
                        nazva = "Авдия";
                    if (setSinodalBible.keyAt(i).contains("sinaidals39"))
                        nazva = "Ионы";
                    if (setSinodalBible.keyAt(i).contains("sinaidals40"))
                        nazva = "Михея";
                    if (setSinodalBible.keyAt(i).contains("sinaidals41"))
                        nazva = "Наума";
                    if (setSinodalBible.keyAt(i).contains("sinaidals42"))
                        nazva = "Аввакума";
                    if (setSinodalBible.keyAt(i).contains("sinaidals43"))
                        nazva = "Сафонии";
                    if (setSinodalBible.keyAt(i).contains("sinaidals44"))
                        nazva = "Аггея";
                    if (setSinodalBible.keyAt(i).contains("sinaidals45"))
                        nazva = "Захарии";
                    if (setSinodalBible.keyAt(i).contains("sinaidals46"))
                        nazva = "Малахии";
                    if (setSinodalBible.keyAt(i).contains("sinaidals47"))
                        nazva = "1-я Маккавейская";
                    if (setSinodalBible.keyAt(i).contains("sinaidals48"))
                        nazva = "2-я Маккавейская";
                    if (setSinodalBible.keyAt(i).contains("sinaidals49"))
                        nazva = "3-я Маккавейская";
                    if (setSinodalBible.keyAt(i).contains("sinaidals50"))
                        nazva = "3-я Ездры";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln1"))
                        nazva = "От Матфея";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln2"))
                        nazva = "От Марка";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln3"))
                        nazva = "От Луки";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln4"))
                        nazva = "От Иоанна";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln5"))
                        nazva = "Деяния святых апостолов";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln6"))
                        nazva = "Иакова";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln7"))
                        nazva = "1-е Петра";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln8"))
                        nazva = "2-е Петра";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln9"))
                        nazva = "1-е Иоанна";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln10"))
                        nazva = "2-е Иоанна";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln11"))
                        nazva = "3-е Иоанна";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln12"))
                        nazva = "Иуды";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln13"))
                        nazva = "Римлянам";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln14"))
                        nazva = "1-е Коринфянам";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln15"))
                        nazva = "2-е Коринфянам";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln16"))
                        nazva = "Галатам";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln17"))
                        nazva = "Эфэсянам";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln18"))
                        nazva = "Филиппийцам";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln19"))
                        nazva = "Колоссянам";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln20"))
                        nazva = "1-е Фессалоникийцам (Солунянам)";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln21"))
                        nazva = "2-е Фессалоникийцам (Солунянам)";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln22"))
                        nazva = "1-е Тимофею";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln23"))
                        nazva = "2-е Тимофею";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln24"))
                        nazva = "Титу";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln25"))
                        nazva = "Филимону";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln26"))
                        nazva = "Евреям";
                    if (setSinodalBible.keyAt(i).contains("sinaidaln27"))
                        nazva = "Откровение (Апокалипсис)";
                    try {
                        InputStream inputStream = context.getResources().openRawResource(setSinodalBible.valueAt(i));
                        InputStreamReader isr = new InputStreamReader(inputStream);
                        BufferedReader reader = new BufferedReader(isr);
                        String line;
                        StringBuilder builder = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            builder.append(line).append("\n");
                        }
                        inputStream.close();
                        int glava = 0;
                        String[] split = builder.toString().split("===");
                        for (int e = 1; e < split.length; e++) {
                            glava++;
                            String[] bibleline = split[e].split("\n");
                            int stix = 0;
                            for (int r = 1; r < bibleline.length; r++) {
                                stix++;
                                String prepinanie = bibleline[r];
                                if (chin.getInt("slovocalkam", 0) == 1)
                                    prepinanie = " " + bibleline[r] + " ";
                                if (chin.getInt("pegistr", 0) == 0)
                                    prepinanie = prepinanie.toLowerCase();
                                int t1a = prepinanie.indexOf(poshuk);
                                prepinanie = prepinanie.replace(",", "");
                                prepinanie = prepinanie.replace(".", "");
                                prepinanie = prepinanie.replace(";", "");
                                prepinanie = prepinanie.replace(":", "");
                                prepinanie = prepinanie.replace("[", "");
                                prepinanie = prepinanie.replace("]", "");
                                prepinanie = prepinanie.replace("-", "");
                                prepinanie = prepinanie.replace("\"", "");
                                int count = 0;
                                if (t1a != -1)
                                    count = t1a - prepinanie.indexOf(poshuk);
                                prepinanie = prepinanie.replace("ё", "е");
                                if (chin.getInt("slovocalkam", 0) == 0) {
                                    /*if (chin.getInt("kohnoeslovo", 0) == 1) {
                                        boolean add = true;
                                        for (String aPoshukchar : poshukchar) {
                                            if (aPoshukchar.length() > 2) {
                                                if (prepinanie.contains(aPoshukchar)) {
                                                    if (add) {
                                                        String aSviatyia = bibleline[r];
                                                        if (chin.getInt("pegistr", 0) == 0)
                                                            aPoshukchar = aPoshukchar.toLowerCase();
                                                        int t1 = prepinanie.indexOf(aPoshukchar);
                                                        t1 = t1 + count;
                                                        int t2 = aPoshukchar.length();
                                                        aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                                                        seashpost.add("<!--stix." + stix + "--><strong>" + nazva + " Гл. " + glava + "</strong><br>" + aSviatyia);
                                                        add = false;
                                                    }
                                                }
                                            }
                                        }
                                    } else {*/
                                    if (prepinanie.contains(poshuk)) {
                                        String aSviatyia = bibleline[r];
                                        int t1 = prepinanie.indexOf(poshuk);
                                        t1 = t1 + count;
                                        int t2 = poshuk.length();
                                        aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                                        seashpost.add("<!--stix." + stix + "--><strong>" + nazva + " Гл. " + glava + "</strong><br>" + aSviatyia);
                                    }
                                    //}
                                } else {
                                    if (prepinanie.contains(poshuk)) {
                                        String aSviatyia = bibleline[r];
                                        int t1 = prepinanie.indexOf(poshuk);
                                        t1 = t1 + count;
                                        int t2 = poshuk.length();
                                        aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                                        seashpost.add("<!--stix." + stix + "--><strong>" + nazva + " Гл. " + glava + "</strong><br>" + aSviatyia);
                                    }
                                }
                            }
                        }
                    } catch (Throwable ignored) {
                    }
                }
            }
        }
        return seashpost;
    }

    private static ArrayList<String> Nadsan(Context context, String poshuk) {
        SharedPreferences chin = context.getSharedPreferences("biblia", MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        ArrayList<String> seashpost = new ArrayList<>();
        if (!poshuk.equals("")) {
            poshuk = zamena(poshuk);
            if (chin.getInt("pegistr", 0) == 0) poshuk = poshuk.toLowerCase();
            //String[] poshukchar = poshuk.split(" ");
            if (chin.getInt("slovocalkam", 0) == 0) {
                char[] m = {'у', 'е', 'а', 'о', 'э', 'я', 'и', 'ю', 'ь', 'ы'};
                /*if (chin.getInt("kohnoeslovo", 0) == 1) {
                    for (int e = 0; e < poshukchar.length; e++) {
                        if (poshukchar[e].length() >= 3) {
                            for (char aM : m) {
                                int r = poshukchar[e].length() - 1;
                                if (poshukchar[e].length() >= 3) {
                                    if (poshukchar[e].charAt(r) == aM) {
                                        poshukchar[e] = poshukchar[e].replace(poshukchar[e], poshukchar[e].substring(0, r));
                                    }
                                }
                            }
                        }
                    }
                } else {*/
                for (char aM : m) {
                    int r = poshuk.length() - 1;
                    if (poshuk.length() >= 3) {
                        if (poshuk.charAt(r) == aM) {
                            poshuk = poshuk.replace(poshuk, poshuk.substring(0, r));
                        }
                    }
                }
                //}
            } else {
                poshuk = " " + poshuk + " ";
            }
            String color = "<font color=#d00505>";
            if (dzenNoch)
                color = "<font color=#f44336>";
            String nazva = "Псалтыр";
            try {
                InputStream inputStream = context.getResources().openRawResource(R.raw.nadsan_psaltyr);
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                inputStream.close();
                int glava = 0;
                String[] split = builder.toString().split("===");
                for (int e = 1; e < split.length; e++) {
                    glava++;
                    String[] bibleline = split[e].split("\n");
                    int stix = 0;
                    for (int r = 1; r < bibleline.length; r++) {
                        stix++;
                        String prepinanie = bibleline[r];
                        if (chin.getInt("pegistr", 0) == 0)
                            prepinanie = prepinanie.toLowerCase();
                        int t1a = prepinanie.indexOf(poshuk);
                        prepinanie = prepinanie.replace(",", "");
                        prepinanie = prepinanie.replace(".", "");
                        prepinanie = prepinanie.replace(";", "");
                        prepinanie = prepinanie.replace(":", "");
                        prepinanie = prepinanie.replace("-", "");
                        prepinanie = prepinanie.replace("\"", "");
                        int count = 0;
                        if (t1a != -1)
                            count = t1a - prepinanie.indexOf(poshuk);
                        prepinanie = prepinanie.replace("ё", "е");
                        prepinanie = prepinanie.replace("<em>", "    ");
                        prepinanie = prepinanie.replace("</em>", "     ");
                        prepinanie = prepinanie.replace("<br>", "    ");
                        prepinanie = prepinanie.replace("<strong>", "        ");
                        prepinanie = prepinanie.replace("</strong>", "         ");
                        if (chin.getInt("slovocalkam", 0) == 0) {
                            /*if (chin.getInt("kohnoeslovo", 0) == 1) {
                                boolean add = true;
                                for (String aPoshukchar : poshukchar) {
                                    if (aPoshukchar.length() >= 3) {
                                        if (prepinanie.contains(aPoshukchar)) {
                                            if (add) {
                                                String aSviatyia = bibleline[r];
                                                if (chin.getInt("pegistr", 0) == 0)
                                                    aPoshukchar = aPoshukchar.toLowerCase();
                                                int t1 = prepinanie.indexOf(aPoshukchar);
                                                t1 = t1 + count;
                                                int t2 = aPoshukchar.length();
                                                aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                                                seashpost.add("<!--stix." + stix + "--><strong>" + nazva + " Пс. " + glava + "</strong><br>" + aSviatyia);
                                                add = false;
                                            }
                                        }
                                    }
                                }
                            } else {*/
                            if (prepinanie.contains(poshuk)) {
                                String aSviatyia = bibleline[r];
                                int t1 = prepinanie.indexOf(poshuk);
                                t1 = t1 + count;
                                int t2 = poshuk.length();
                                aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                                seashpost.add("<!--stix." + stix + "--><strong>" + nazva + " Пс. " + glava + "</strong><br>" + aSviatyia);
                            }
                            //}
                        } else {
                            if (prepinanie.contains(poshuk)) {
                                String aSviatyia = bibleline[r];
                                int t1 = prepinanie.indexOf(poshuk);
                                t1 = t1 + count;
                                int t2 = poshuk.length();
                                aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                                seashpost.add("<!--stix." + stix + "--><strong>" + nazva + " Пс. " + glava + "</strong><br>" + aSviatyia);
                            }
                        }
                    }
                }
            } catch (Throwable ignored) {
            }
        }
        return seashpost;
    }

    @Override
    public void onClick(View view) {
        int idSelect = view.getId();
        if (idSelect == by.carkva_gazeta.malitounik.R.id.buttonx) {
            searche = true;
            adapter.clear();
            editText.setText("");
            editText.requestFocus();
            prefEditors.putString("search_string", "");
            prefEditors.apply();
            akafist.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, 0));
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        if (idSelect == by.carkva_gazeta.malitounik.R.id.buttonx2) {
            editText2.setText("");
        }
    }

    static class Poshuk extends AsyncTask<String, Void, ArrayList<String>> {

        private final WeakReference<Activity> activityReference;
        private final SharedPreferences chin;
        private SharedPreferences.Editor prefEditors;

        Poshuk(Activity context) {
            activityReference = new WeakReference<>(context);
            chin = context.getSharedPreferences("biblia", MODE_PRIVATE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            searche = true;
            prefEditors = chin.edit();
            ProgressBar progressBar = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.progressBar);
            TextView_Roboto_Condensed akafist = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.TextView);
            ListView listView = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.ListView);
            EditText_Roboto_Condensed editText = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.editText);
            ImageView buttonx = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.buttonx);
            buttonx.setOnClickListener(null);
            adapterReference.get().clear();
            akafist.setText(activityReference.get().getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, 0));
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            String edit = Objects.requireNonNull(editText.getText()).toString();
            if (!edit.equals("")) {
                edit = edit.trim();
                editText.setText(edit);
                prefEditors.putString("search_string", edit);
                prefEditors.apply();
                InputMethodManager imm = (InputMethodManager) activityReference.get().getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            if (zavet == 1) {
                return Semuxa(activityReference.get(), params[0]);
            }
            if (zavet == 2) {
                return Sinoidal(activityReference.get(), params[0]);
            }
            if (zavet == 3) {
                return Nadsan(activityReference.get(), params[0]);
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            ProgressBar progressBar = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.progressBar);
            TextView_Roboto_Condensed akafist = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.TextView);
            ListView listView = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.ListView);
            ImageView buttonx = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.buttonx);
            EditText_Roboto_Condensed editText2 = activityReference.get().findViewById(by.carkva_gazeta.malitounik.R.id.editText2);
            adapterReference.get().addAll(result);
            adapterReference.get().getFilter().filter(Objects.requireNonNull(editText2.getText()).toString());
            akafist.setText(activityReference.get().getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, adapterReference.get().getCount()));
            if (!Objects.requireNonNull(chin.getString("search_string", "")).equals("")) {
                listView.clearFocus();
                listView.post(() -> listView.setSelection(chin.getInt("search_position", 0)));
            }
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            buttonx.setOnClickListener((search_biblia) activityReference.get());
            Gson gson = new Gson();
            String json = gson.toJson(result);
            prefEditors.putString("search_array", json);
            prefEditors.apply();
            searche = false;
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private int editPosition;
        private int check = 0;
        private boolean editch = true;
        private final EditText editText;
        private final boolean filtep;

        MyTextWatcher(EditText editText, boolean filtep) {
            this.editText = editText;
            this.filtep = filtep;
        }

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
                if (zavet == 1 || zavet == 3) {
                    edit = edit.replace("и", "і");
                    edit = edit.replace("щ", "ў");
                    edit = edit.replace("ъ", "'");
                    edit = edit.replace("И", "І");
                    edit = edit.replace("Щ", "Ў");
                    edit = edit.replace("Ъ", "'");
                }
                if (check != 0) {
                    editText.removeTextChangedListener(this);
                    editText.setText(edit);
                    editText.setSelection(editPosition);
                    editText.addTextChangedListener(this);
                }
            }
            if (filtep) adapter.getFilter().filter(edit);
        }
    }

    class search_biblia_ListAdaprer extends ArrayAdapter<String> {

        private final SharedPreferences k;
        private final ArrayList<String> origData;
        private final Activity activity;

        search_biblia_ListAdaprer(@NonNull Activity context) {
            super(context, by.carkva_gazeta.malitounik.R.layout.simple_list_item_2, by.carkva_gazeta.malitounik.R.id.label, seash);
            k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            origData = new ArrayList<>(seash);
            activity = context;
        }

        @Override
        public void addAll(@NonNull Collection<? extends String> collection) {
            super.addAll(collection);
            origData.addAll(collection);
        }

        @Override
        public void clear() {
            super.clear();
            if (searche) origData.clear();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = activity.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_2, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(by.carkva_gazeta.malitounik.R.id.label);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }

            boolean dzenNoch = k.getBoolean("dzen_noch", false);

            viewHolder.text.setText(MainActivity.fromHtml(seash.get(position)));
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

            if (dzenNoch) {
                viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.selector_dark));
                viewHolder.text.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(by.carkva_gazeta.malitounik.R.drawable.stiker_black, 0, 0, 0);
            }
            return mView;
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    constraint = constraint.toString().toLowerCase();
                    FilterResults result = new FilterResults();

                    if (constraint.toString().length() > 0) {
                        ArrayList<String> founded = new ArrayList<>();
                        for (String item : origData) {
                            if (item.toLowerCase().contains(constraint)) {
                                founded.add(item);
                            }
                        }

                        result.values = founded;
                        result.count = founded.size();
                    } else {
                        result.values = origData;
                        result.count = origData.size();
                    }
                    return result;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    clear();
                    String color = "<font color=#d00505>";
                    if (dzenNoch)
                        color = "<font color=#f44336>";
                    for (String item : (ArrayList<String>) results.values) {
                        int itm = item.toLowerCase().indexOf(constraint.toString().toLowerCase());
                        int itmcount = constraint.toString().length();
                        add(item.substring(0, itm) + color + item.substring(itm, itm + itmcount) + "</font>" + item.substring(itm + itmcount));
                    }
                    akafist.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, results.count));
                    notifyDataSetChanged();
                }
            };
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
