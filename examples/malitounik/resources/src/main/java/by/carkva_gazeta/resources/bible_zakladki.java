package by.carkva_gazeta.resources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import by.carkva_gazeta.malitounik.Dialog_context_menu_vybranoe;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.MaranAta_Global_List;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class bible_zakladki extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, Dialog_zakladka_delite.ZakladkaDeliteListiner, Dialog_delite_all_zakladki_i_natatki.Dialog_delite_all_zakladki_i_natatki_Listener, Dialog_context_menu_vybranoe.Dialog_context_menu_vybranoe_Listener {

    private bible_zakladki_ListAdaprer adapter;
    private int semuxa = 1;
    private boolean dzenNoch;
    private ListView listView;
    private TextView_Roboto_Condensed help;
    private ArrayList<String> data;
    private long mLastClickTime = 0;

    @Override
    public void file_all_natatki_albo_zakladki(int semuxa) {
        if (semuxa == 1) {
            MaranAta_Global_List.getZakladkiSemuxa().removeAll(MaranAta_Global_List.getZakladkiSemuxa());
            adapter.notifyDataSetChanged();
            File fileZakladki = new File(getFilesDir() + "/BibliaSemuxaZakladki.json");
            if (fileZakladki.exists()) {
                fileZakladki.delete();
            }
        }
        if (semuxa == 2) {
            MaranAta_Global_List.getZakladkiSinodal().removeAll(MaranAta_Global_List.getZakladkiSinodal());
            adapter.notifyDataSetChanged();
            File fileZakladki = new File(getFilesDir() + "/BibliaSinodalZakladki.json");
            if (fileZakladki.exists()) {
                fileZakladki.delete();
            }
        }
        /*if (semuxa == 3) {
            MaranAta_Global_List.getZakladkiPsalterNadsana().removeAll(MaranAta_Global_List.getZakladkiPsalterNadsana());
            adapter.notifyDataSetChanged();
            File fileZakladki = new File(getFilesDir() + "/PsalterNadsanZakladki.json");
            if (fileZakladki.exists()) {
                fileZakladki.delete();
            }
        }*/
        help.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        invalidateOptionsMenu();
    }

    @Override
    public void onDialogDeliteVybranoeClick(int position, String name) {
        Dialog_zakladka_delite delite = Dialog_zakladka_delite.getInstance(position, name, semuxa, true);
        delite.show(getSupportFragmentManager(), "zakladka_delite");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        SharedPreferences k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        setContentView(by.carkva_gazeta.malitounik.R.layout.akafist_list);
        listView = findViewById(by.carkva_gazeta.malitounik.R.id.ListView);
        if (getIntent() != null) {
            semuxa = getIntent().getIntExtra("semuxa", 1);
        }
        if (semuxa == 1)
            data = MaranAta_Global_List.getZakladkiSemuxa();
        if (semuxa == 2)
            data = MaranAta_Global_List.getZakladkiSinodal();
        //if (semuxa == 3)
        //data = MaranAta_Global_List.getZakladkiPsalterNadsana();
        adapter = new bible_zakladki_ListAdaprer(this, data);
        help = findViewById(by.carkva_gazeta.malitounik.R.id.help);
        if (data.size() == 0) {
            help.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        if (dzenNoch)
            help.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
        listView.setAdapter(adapter);
        listView.setVerticalScrollBarEnabled(false);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
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
        title_toolbar.setText(by.carkva_gazeta.malitounik.R.string.zakladki_bible);
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (data.size() == 0) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.trash).setVisible(false);
        } else {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.trash).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.zakladki_i_natatki, menu);
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
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == by.carkva_gazeta.malitounik.R.id.trash) {
            Dialog_delite_all_zakladki_i_natatki natatki = Dialog_delite_all_zakladki_i_natatki.getInstance(getResources().getString(by.carkva_gazeta.malitounik.R.string.zakladki_bible).toLowerCase(), semuxa);
            natatki.show(getSupportFragmentManager(), "delite_all_zakladki_i_natatki");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTollbarTheme();
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    @Override
    public void natatkidiliteItem(int position, int semuxa) {
    }

    @Override
    public void zakladkadiliteItem(int position, int semuxa) {
        if (semuxa == 1) {
            MaranAta_Global_List.getZakladkiSemuxa().remove(position);
            adapter.notifyDataSetChanged();
            File fileZakladki = new File(getFilesDir() + "/BibliaSemuxaZakladki.json");
            if (MaranAta_Global_List.getZakladkiSemuxa().size() == 0) {
                if (fileZakladki.exists()) {
                    fileZakladki.delete();
                }
                help.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                Gson gson = new Gson();
                try {
                    FileWriter outputStream = new FileWriter(fileZakladki);
                    outputStream.write(gson.toJson(MaranAta_Global_List.getZakladkiSemuxa()));
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
        if (semuxa == 2) {
            MaranAta_Global_List.getZakladkiSinodal().remove(position);
            adapter.notifyDataSetChanged();
            File fileZakladki = new File(getFilesDir() + "/BibliaSinodalZakladki.json");
            if (MaranAta_Global_List.getZakladkiSinodal().size() == 0) {
                if (fileZakladki.exists()) {
                    fileZakladki.delete();
                }
                help.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                Gson gson = new Gson();
                try {
                    FileWriter outputStream = new FileWriter(fileZakladki);
                    outputStream.write(gson.toJson(MaranAta_Global_List.getZakladkiSinodal()));
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
        /*if (semuxa == 3) {
            MaranAta_Global_List.getZakladkiPsalterNadsana().remove(position);
            adapter.notifyDataSetChanged();
            File fileZakladki = new File(getFilesDir() + "/PsalterNadsanZakladki.json");
            if (MaranAta_Global_List.getZakladkiPsalterNadsana().size() == 0) {
                if (fileZakladki.exists()) {
                    fileZakladki.delete();
                }
                help.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                Gson gson = new Gson();
                try {
                    FileWriter outputStream = new FileWriter(fileZakladki);
                    outputStream.write(gson.toJson(MaranAta_Global_List.getZakladkiPsalterNadsana()));
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }*/
        invalidateOptionsMenu();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        String knigaName = String.valueOf(parent.getItemAtPosition(position));
        int kniga = -1;
        int knigaS = -1;
        int t1, t2 = 0, t3 = 0, glava = 0;
        if (semuxa == 1) {
            if (knigaName.contains("Паводле Мацьвея"))
                kniga = 0;
            if (knigaName.contains("Паводле Марка"))
                kniga = 1;
            if (knigaName.contains("Паводле Лукаша"))
                kniga = 2;
            if (knigaName.contains("Паводле Яна"))
                kniga = 3;
            if (knigaName.contains("Дзеі Апосталаў"))
                kniga = 4;
            if (knigaName.contains("Якава"))
                kniga = 5;
            if (knigaName.contains("1-е Пятра"))
                kniga = 6;
            if (knigaName.contains("2-е Пятра"))
                kniga = 7;
            if (knigaName.contains("1-е Яна Багаслова"))
                kniga = 8;
            if (knigaName.contains("2-е Яна Багаслова"))
                kniga = 9;
            if (knigaName.contains("3-е Яна Багаслова"))
                kniga = 10;
            if (knigaName.contains("Юды"))
                kniga = 11;
            if (knigaName.contains("Да Рымлянаў"))
                kniga = 12;
            if (knigaName.contains("1-е да Карынфянаў"))
                kniga = 13;
            if (knigaName.contains("2-е да Карынфянаў"))
                kniga = 14;
            if (knigaName.contains("Да Галятаў"))
                kniga = 15;
            if (knigaName.contains("Да Эфэсянаў"))
                kniga = 16;
            if (knigaName.contains("Да Піліпянаў"))
                kniga = 17;
            if (knigaName.contains("Да Каласянаў"))
                kniga = 18;
            if (knigaName.contains("1-е да Фесаланікійцаў"))
                kniga = 19;
            if (knigaName.contains("2-е да Фесаланікійцаў"))
                kniga = 20;
            if (knigaName.contains("1-е да Цімафея"))
                kniga = 21;
            if (knigaName.contains("2-е да Цімафея"))
                kniga = 22;
            if (knigaName.contains("Да Ціта"))
                kniga = 23;
            if (knigaName.contains("Да Філімона"))
                kniga = 24;
            if (knigaName.contains("Да Габрэяў"))
                kniga = 25;
            if (knigaName.contains("Адкрыцьцё (Апакаліпсіс)"))
                kniga = 26;


            if (knigaName.contains("Быцьцё"))
                knigaS = 0;
            if (knigaName.contains("Выхад"))
                knigaS = 1;
            if (knigaName.contains("Лявіт"))
                knigaS = 2;
            if (knigaName.contains("Лікі"))
                knigaS = 3;
            if (knigaName.contains("Другі Закон"))
                knigaS = 4;
            if (knigaName.contains("Ісуса сына Нава"))
                knigaS = 5;
            if (knigaName.contains("Судзьдзяў"))
                knigaS = 6;
            if (knigaName.contains("Рут"))
                knigaS = 7;
            if (knigaName.contains("1-я Царстваў"))
                knigaS = 8;
            if (knigaName.contains("2-я Царстваў"))
                knigaS = 9;
            if (knigaName.contains("3-я Царстваў"))
                knigaS = 10;
            if (knigaName.contains("4-я Царстваў"))
                knigaS = 11;
            if (knigaName.contains("1-я Летапісаў"))
                knigaS = 12;
            if (knigaName.contains("2-я Летапісаў"))
                knigaS = 13;
            if (knigaName.contains("Эздры"))
                knigaS = 14;
            if (knigaName.contains("Нээміі"))
                knigaS = 15;
            if (knigaName.contains("Эстэр"))
                knigaS = 16;
            if (knigaName.contains("Ёва"))
                knigaS = 17;
            if (knigaName.contains("Псалтыр"))
                knigaS = 18;
            if (knigaName.contains("Выслоўяў Саламонавых"))
                knigaS = 19;
            if (knigaName.contains("Эклезіяста"))
                knigaS = 20;
            if (knigaName.contains("Найвышэйшая Песьня Саламонава"))
                knigaS = 21;
            if (knigaName.contains("Ісаі"))
                knigaS = 22;
            if (knigaName.contains("Ераміі"))
                knigaS = 23;
            if (knigaName.contains("Ераміін Плач"))
                knigaS = 24;
            if (knigaName.contains("Езэкііля"))
                knigaS = 25;
            if (knigaName.contains("Данііла"))
                knigaS = 26;
            if (knigaName.contains("Асіі"))
                knigaS = 27;
            if (knigaName.contains("Ёіля"))
                knigaS = 28;
            if (knigaName.contains("Амоса"))
                knigaS = 29;
            if (knigaName.contains("Аўдзея"))
                knigaS = 30;
            if (knigaName.contains("Ёны"))
                knigaS = 31;
            if (knigaName.contains("Міхея"))
                knigaS = 32;
            if (knigaName.contains("Навума"))
                knigaS = 33;
            if (knigaName.contains("Абакума"))
                knigaS = 34;
            if (knigaName.contains("Сафона"))
                knigaS = 35;
            if (knigaName.contains("Агея"))
                knigaS = 36;
            if (knigaName.contains("Захарыі"))
                knigaS = 37;
            if (knigaName.contains("Малахіі"))
                knigaS = 38;
            t1 = knigaName.indexOf("Разьдзел ");
            t2 = knigaName.indexOf("/", t1);
            t3 = knigaName.indexOf("\n\n");
            glava = Integer.parseInt(knigaName.substring(t1 + 9, t2));
        }
        if (semuxa == 2) {
            if (knigaName.contains("От Матфея"))
                kniga = 0;
            if (knigaName.contains("От Марка"))
                kniga = 1;
            if (knigaName.contains("От Луки"))
                kniga = 2;
            if (knigaName.contains("От Иоанна"))
                kniga = 3;
            if (knigaName.contains("Деяния святых апостолов"))
                kniga = 4;
            if (knigaName.contains("Иакова"))
                kniga = 5;
            if (knigaName.contains("1-е Петра"))
                kniga = 6;
            if (knigaName.contains("2-е Петра"))
                kniga = 7;
            if (knigaName.contains("1-е Иоанна"))
                kniga = 8;
            if (knigaName.contains("2-е Иоанна"))
                kniga = 9;
            if (knigaName.contains("3-е Иоанна"))
                kniga = 10;
            if (knigaName.contains("Иуды"))
                kniga = 11;
            if (knigaName.contains("Римлянам"))
                kniga = 12;
            if (knigaName.contains("1-е Коринфянам"))
                kniga = 13;
            if (knigaName.contains("2-е Коринфянам"))
                kniga = 14;
            if (knigaName.contains("Галатам"))
                kniga = 15;
            if (knigaName.contains("Ефесянам"))
                kniga = 16;
            if (knigaName.contains("Филиппийцам"))
                kniga = 17;
            if (knigaName.contains("Колоссянам"))
                kniga = 18;
            if (knigaName.contains("1-е Фессалоникийцам (Солунянам)"))
                kniga = 19;
            if (knigaName.contains("2-е Фессалоникийцам (Солунянам)"))
                kniga = 20;
            if (knigaName.contains("1-е Тимофею"))
                kniga = 21;
            if (knigaName.contains("2-е Тимофею"))
                kniga = 22;
            if (knigaName.contains("Титу"))
                kniga = 23;
            if (knigaName.contains("Филимону"))
                kniga = 24;
            if (knigaName.contains("Евреям"))
                kniga = 25;
            if (knigaName.contains("Откровение (Апокалипсис)"))
                kniga = 26;

            if (knigaName.contains("Бытие"))
                knigaS = 0;
            if (knigaName.contains("Исход"))
                knigaS = 1;
            if (knigaName.contains("Левит"))
                knigaS = 2;
            if (knigaName.contains("Числа"))
                knigaS = 3;
            if (knigaName.contains("Второзаконие"))
                knigaS = 4;
            if (knigaName.contains("Иисуса Навина"))
                knigaS = 5;
            if (knigaName.contains("Судей израилевых"))
                knigaS = 6;
            if (knigaName.contains("Руфи"))
                knigaS = 7;
            if (knigaName.contains("1-я Царств"))
                knigaS = 8;
            if (knigaName.contains("2-я Царств"))
                knigaS = 9;
            if (knigaName.contains("3-я Царств"))
                knigaS = 10;
            if (knigaName.contains("4-я Царств"))
                knigaS = 11;
            if (knigaName.contains("1-я Паралипоменон"))
                knigaS = 12;
            if (knigaName.contains("2-я Паралипоменон"))
                knigaS = 13;
            if (knigaName.contains("1-я Ездры"))
                knigaS = 14;
            if (knigaName.contains("Неемии"))
                knigaS = 15;
            if (knigaName.contains("2-я Ездры"))
                knigaS = 16;
            if (knigaName.contains("Товита"))
                knigaS = 17;
            if (knigaName.contains("Иудифи"))
                knigaS = 18;
            if (knigaName.contains("Есфири"))
                knigaS = 19;
            if (knigaName.contains("Иова"))
                knigaS = 20;
            if (knigaName.contains("Псалтирь"))
                knigaS = 21;
            if (knigaName.contains("Притчи Соломона"))
                knigaS = 22;
            if (knigaName.contains("Екклезиаста"))
                knigaS = 23;
            if (knigaName.contains("Песнь песней Соломона"))
                knigaS = 24;
            if (knigaName.contains("Премудрости Соломона"))
                knigaS = 25;
            if (knigaName.contains("Премудрости Иисуса, сына Сирахова"))
                knigaS = 26;
            if (knigaName.contains("Исаии"))
                knigaS = 27;
            if (knigaName.contains("Иеремии"))
                knigaS = 28;
            if (knigaName.contains("Плач Иеремии"))
                knigaS = 29;
            if (knigaName.contains("Послание Иеремии"))
                knigaS = 30;
            if (knigaName.contains("Варуха"))
                knigaS = 31;
            if (knigaName.contains("Иезекииля"))
                knigaS = 32;
            if (knigaName.contains("Даниила"))
                knigaS = 33;
            if (knigaName.contains("Осии"))
                knigaS = 34;
            if (knigaName.contains("Иоиля"))
                knigaS = 35;
            if (knigaName.contains("Амоса"))
                knigaS = 36;
            if (knigaName.contains("Авдия"))
                knigaS = 37;
            if (knigaName.contains("Ионы"))
                knigaS = 38;
            if (knigaName.contains("Михея"))
                knigaS = 39;
            if (knigaName.contains("Наума"))
                knigaS = 40;
            if (knigaName.contains("Аввакума"))
                knigaS = 41;
            if (knigaName.contains("Сафонии"))
                knigaS = 42;
            if (knigaName.contains("Аггея"))
                knigaS = 43;
            if (knigaName.contains("Захарии"))
                knigaS = 44;
            if (knigaName.contains("Малахии"))
                knigaS = 45;
            if (knigaName.contains("1-я Маккавейская"))
                knigaS = 46;
            if (knigaName.contains("2-я Маккавейская"))
                knigaS = 47;
            if (knigaName.contains("3-я Маккавейская"))
                knigaS = 48;
            if (knigaName.contains("3-я Ездры"))
                knigaS = 49;
            t1 = knigaName.indexOf("Глава ");
            t2 = knigaName.indexOf("/", t1);
            t3 = knigaName.indexOf("\n\n", t2);
            glava = Integer.parseInt(knigaName.substring(t1 + 6, t2));
        }
        /*if (semuxa == 3) {
            knigaS = 18;
            t1 = knigaName.indexOf("Разьдзел ");
            t2 = knigaName.indexOf("/", t1);
            t3 = knigaName.indexOf("\n\n");
            glava = Integer.parseInt(knigaName.substring(t1 + 9, t2));
        }*/
        int stix = Integer.parseInt(knigaName.substring(t2 + 6, t3));
        Intent intent = null;
        /*if (semuxa == 3) {
            intent = new Intent(this, nadsanContentActivity.class);
        } else {*/
        if (kniga != -1) {
            if (semuxa == 1) {
                intent = new Intent(this, novy_zapaviet3.class);
            }
            if (semuxa == 2) {
                intent = new Intent(this, novy_zapaviet_sinaidal3.class);
            }
            Objects.requireNonNull(intent).putExtra("kniga", kniga);
        }
        if (knigaS != -1) {
            if (semuxa == 1) {
                intent = new Intent(this, stary_zapaviet3.class);
            }
            if (semuxa == 2) {
                intent = new Intent(this, stary_zapaviet_sinaidal3.class);
            }
            Objects.requireNonNull(intent).putExtra("kniga", knigaS);
        }
        //}
        Objects.requireNonNull(intent).putExtra("glava", glava - 1);
        intent.putExtra("stix", stix - 1);
        startActivityForResult(intent, 500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        int t1 = data.get(position).indexOf("\n\n");
        int t2;
        if (semuxa == 1)
            t2 = data.get(position).indexOf(". ", t1);
        else
            t2 = data.get(position).indexOf(" ", t1);
        Dialog_context_menu_vybranoe vybranoe = Dialog_context_menu_vybranoe.getInstance(position, data.get(position).substring(0, t1) + getString(by.carkva_gazeta.malitounik.R.string.stix_by) + " " + data.get(position).substring(t1 + 2, t2));
        vybranoe.show(getSupportFragmentManager(), "context_menu_vybranoe");
        return true;
    }

    private class bible_zakladki_ListAdaprer extends ArrayAdapter<String> {

        private final Activity mContext;
        private final ArrayList<String> itemsL;
        private final SharedPreferences k;

        bible_zakladki_ListAdaprer(@NonNull Activity context, ArrayList<String> arrayList) {
            super(context, by.carkva_gazeta.malitounik.R.layout.simple_list_item_3, by.carkva_gazeta.malitounik.R.id.label, arrayList);
            mContext = context;
            itemsL = arrayList;
            k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        }

        @Override
        public void add(@Nullable String string) {
            super.add(string);
            itemsL.add(string);
        }

        @Override
        public void remove(@Nullable String string) {
            super.remove(string);
            itemsL.remove(string);
        }

        @Override
        public void clear() {
            super.clear();
            itemsL.clear();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = mContext.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_3, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(by.carkva_gazeta.malitounik.R.id.label);
                viewHolder.button_popup = mView.findViewById(by.carkva_gazeta.malitounik.R.id.button_popup);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }

            boolean dzenNoch = k.getBoolean("dzen_noch", false);

            viewHolder.button_popup.setOnClickListener((v -> showPopupMenu(viewHolder.button_popup, position, itemsL.get(position))));

            viewHolder.text.setText(itemsL.get(position));
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

            if (dzenNoch) {
                viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(mContext, by.carkva_gazeta.malitounik.R.drawable.selector_dark));
                viewHolder.text.setTextColor(ContextCompat.getColor(mContext, by.carkva_gazeta.malitounik.R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(by.carkva_gazeta.malitounik.R.drawable.stiker_black, 0, 0, 0);
            }
            return mView;
        }

        private void showPopupMenu(View view, int position, String name) {
            PopupMenu popup = new PopupMenu(mContext, view);
            MenuInflater infl = popup.getMenuInflater();
            infl.inflate(by.carkva_gazeta.malitounik.R.menu.popup, popup.getMenu());
            popup.getMenu().getItem(0).setVisible(false);
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
                    case by.carkva_gazeta.malitounik.R.id.menu_redoktor:
                        return true;
                    case by.carkva_gazeta.malitounik.R.id.menu_remove:
                        int t1 = name.indexOf("\n\n");
                        int t2;
                        if (semuxa == 1)
                            t2 = name.indexOf(". ", t1);
                        else
                            t2 = name.indexOf(" ", t1);
                        Dialog_zakladka_delite delite = Dialog_zakladka_delite.getInstance(position, name.substring(0, t1) + getString(by.carkva_gazeta.malitounik.R.string.stix_by) + " " + name.substring(t1 + 2, t2), semuxa, true);
                        delite.show(getSupportFragmentManager(), "zakladka_delite");
                        return true;
                }
                return false;
            });
            popup.show();
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
        ImageView button_popup;
    }
}
