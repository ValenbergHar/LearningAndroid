package by.carkva_gazeta.resources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import by.carkva_gazeta.malitounik.EditText_Roboto_Condensed;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.Menu_list_data;
import by.carkva_gazeta.malitounik.Menu_list_data_sort;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 5.10.16
 */

public class search_pesny extends AppCompatActivity {

    private TextView_Roboto_Condensed akafist;
    private EditText_Roboto_Condensed editText;
    private search_ListAdaprer adapter;
    private boolean dzenNoch;
    private Timer PosukPesenTimer = null;
    private TimerTask PosukPesenSchedule;
    private ArrayList<Menu_list_data> menu_list_data;
    private long mLastClickTime = 0;

    public static ArrayList<Menu_list_data> get_Menu_list_data() {
        ArrayList<Menu_list_data> menu_list_data = new ArrayList<>();
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_0, "Анёл ад Бога Габрыэль"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_1, "З далёкай Фацімы"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_2, "Люрдаўская песьня"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_3, "Матачка Божая"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_4, "Маці Божая Будслаўская"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_5, "Маці Божая ў Жыровіцах"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_6, "Маці з Фацімы"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_7, "Маці мая Божая"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_8, "Мне аднойчы"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_9, "О Марыя, Маці Бога (1)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_10, "О Марыя, Маці Бога (2)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_11, "Памаліся, Марыя"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_12, "Песьня да Маці Божай Нястомнай Дапамогі"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_13, "Радуйся, Марыя!"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_14, "Табе, Марыя, давяраю я"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bag_15, "Ціхая, пакорная"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_0, "Ave Maria"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_1, "Божа, што калісь народы"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_2, "Божа, я малюся за Беларусь"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_3, "Вечна жывi, мая Беларусь"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_4, "К табе, Беларусь"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_5, "Магутны Божа"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_6, "Малюся за цябе, Беларусь"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_7, "Малітва"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_8, "Мая краіна"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_bel_9, "Мы хочам Бога"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_0, "Ave Maria (Зорка зазьзяла)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_1, "А што гэта за сьпевы"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_2, "А ў сьвеце нам навіна была"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_3, "А ўчора з вячора"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_4, "Вясёлых калядных сьвятаў"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_5, "Зазьзяла зорачка над Бэтлеемам"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_6, "Звон зьвініць"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_7, "На шляху ў Бэтлеем"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_8, "Неба і зямля"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_9, "Нова радасьць стала"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_10, "Ночка цiхая, зарыста"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_11, "Ноччу сьвятой"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_12, "Паказалась з неба яснасьць"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_13, "Прыйдзіце да Збаўцы"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_14, "Радасная вестка"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_15, "У начную ціш"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_16, "Учора зьвячора — засьвяціла зора"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_17, "Ціхая ноч (пер. Н. Арсеньневай)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_18, "Ціхая ноч-2"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_19, "Ціхая ноч-3"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_20, "Прыйдзі, прыйдзі, Эмануэль (ХІХ ст.)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_kal_21, "Прыйдзі, прыйдзі, Эмануэль (XII–ХVIII стст.)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_0, "Ён паўсюль"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_1, "Ісус вызваліў мяне"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_2, "Ісус нам дае збаўленьне"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_3, "Айцец наш і наш Валадар"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_4, "Алілуя!"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_5, "Бог блаславіў гэты дзень"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_6, "Бог ёсьць любоў"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_7, "Богу сьпявай, уся зямля!"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_8, "Божа мой"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_9, "Браце мой"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_10, "Весяліся і пляскай у далоні"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_11, "Вольная воля"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_12, "Вось маё сэрца"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_13, "Вядзі мяне, Божа"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_14, "Вялікім і цудоўным"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_15, "Госпад мой заўсёды па маёй правіцы"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_16, "Госпаду дзякуйце, бо добры Ён"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_17, "Дай Духа любові"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_18, "Дай уславіць Цябе"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_19, "Дай, добры Божа"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_20, "Дакраніся да маіх вачэй"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_21, "Дзякуй за ўсё, што Ты стварыў"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_22, "Дзякуй!"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_23, "З намі — Пятро і Андрэй"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_24, "Знайдзі мяне"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_25, "Зоркі далёка"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_26, "Кадош (Сьвяты)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_27, "Клічаш ты"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_28, "Любоў Твая"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_29, "Любіць — гэта ахвяраваць"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_30, "Майго жыцьця — мой Бог крыніца"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_31, "Маё сэрца"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_32, "Маё шчасьце ў Iсуса"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_33, "На псалтыры і на арфе"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_34, "Настане дзень"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_35, "Невычэрпныя ласкі ў Бога"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_36, "О, калі б ты паслухаў Мяне"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_37, "Ойча мой, к Табе іду"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_38, "Ойча, мяне Ты любіш"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_39, "Пакліканьне (Човен)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_40, "Пачуй мой кліч, чулы Ойча"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_41, "Песьню славы засьпявайма"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_42, "Песьня Давіда"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_43, "Песьня вячэрняя"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_44, "Песьня пілігрыма"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_45, "Песьня ранішняя"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_46, "Пяцёра пакутнікаў"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_47, "Пілігрым"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_48, "Руах"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_49, "Сьвятло жыцьця"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_50, "Сьпявайма добраму Богу"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_51, "Сьпявайце Цару"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_52, "Так, як імкнецца сарна"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_53, "Твая любоў"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_54, "Твая прысутнасьць"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_55, "Толькі Ісус"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_56, "Толькі Бог, толькі ты"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_57, "Толькі Бог"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_58, "Ты ведаеш сэрца маё"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_59, "Ты ведаеш..."));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_60, "Ты — Госпад мой"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_61, "Хвала Табе, вялікі Бог"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_62, "Хвалім Цябе, Божа!"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_63, "Хрыстос уваскрос! (Resucito)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_64, "Ці ты быў на Галгофе"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_65, "Шалом алэхем (Мір вам)"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_66, "Я люблю Цябе, Ойча міласэрны"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_67, "Я ўстану рана, каб сьпяваць"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_68, "Як гэта хораша й міла"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_69, "Яму за ўсё слава"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_70, "Цябе, Бога, хвалім"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_prasl_71, "Мой Госпад, мой Збаўца"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_0, "Magnifikat"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_1, "Ostende nobis"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_2, "Ubi caritas"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_3, "Блаславёны Бог"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_4, "Бог мой, Iсус, сьвяцi нам у цемры"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_5, "Будзь са Мной"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_6, "Дай нам, Божа, моц ласкi Сваёй"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_7, "Дзякуем Табе, Божа наш"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_8, "Дзякуем Табе, Хрысьце"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_9, "Кожны дзень Бог дае мне сiлы"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_10, "Мая душа ў Богу мае спакой"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_11, "О, Iсусе"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_12, "О, Госпадзе мой"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_13, "Прыйдзi, Дух Сьвяты"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_14, "У цемры iдзём"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_15, "У цемры нашых дзён"));
        menu_list_data.add(new Menu_list_data(R.raw.pesny_taize_16, "Хай тваё сэрца больш не журыцца"));
        // так же нужно добавить имена песен в by.carkva_gazeta.malitounik.Menu_pesny_*
        return menu_list_data;
    }

    public static int getPesniaID(String name) {
        ArrayList<Menu_list_data> menu_list_data = get_Menu_list_data();
        for (Menu_list_data list_data : menu_list_data) {
            if (list_data.data.equals(name))
                return list_data.id;
        }
        return -1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences chin = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(by.carkva_gazeta.malitounik.R.layout.search_biblia);
        akafist = findViewById(by.carkva_gazeta.malitounik.R.id.TextView);
        ListView listView = findViewById(by.carkva_gazeta.malitounik.R.id.ListView);
        editText = findViewById(by.carkva_gazeta.malitounik.R.id.editText);
        ImageView buttonx = findViewById(by.carkva_gazeta.malitounik.R.id.buttonx);
        if (dzenNoch) {
            buttonx.setImageResource(by.carkva_gazeta.malitounik.R.drawable.cancel);
        }
        buttonx.setOnClickListener(v -> {
            editText.setText("");
            adapter.clear();
            akafist.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, 0));
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        });
        akafist.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        adapter = new search_ListAdaprer(this, new ArrayList<>());
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
        akafist.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, adapter.getCount()));
        if (dzenNoch) {
            akafist.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
        }
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            LinearLayout ll = (LinearLayout) view;
            TextView_Roboto_Condensed textView = ll.findViewById(by.carkva_gazeta.malitounik.R.id.label);
            String strText = textView.getText().toString();
            Intent intent = new Intent(search_pesny.this, search_pesny_view_result.class);
            intent.putExtra("resultat", strText);
            startActivity(intent);
        });

        editText.addTextChangedListener(new MyTextWatcher());

        menu_list_data = get_Menu_list_data();

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
        title_toolbar.setText(getResources().getText(by.carkva_gazeta.malitounik.R.string.search));
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
            title_toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
            title_toolbar.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
        } else {
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
            title_toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
        }
    }

    private void stopPosukPesen() {
        if (PosukPesenTimer != null) {
            PosukPesenTimer.cancel();
            PosukPesenTimer = null;
        }
        PosukPesenSchedule = null;
    }

    private void startPosukPesen(String poshuk) {
        if (PosukPesenTimer == null) {
            PosukPesenTimer = new Timer();
            if (PosukPesenSchedule != null) {
                PosukPesenSchedule.cancel();
                PosukPesenSchedule = null;
            }
            PosukPesenSchedule = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> RawAsset(poshuk));
                }
            };
            PosukPesenTimer.schedule(PosukPesenSchedule, 0);
        }
    }

    private void RawAsset(String poshuk) {
        boolean setClear = true;
        if (!poshuk.equals("")) {
            poshuk = poshuk.toLowerCase();
            poshuk = poshuk.replace("ё", "е");
            poshuk = poshuk.replace("све", "сьве");
            poshuk = poshuk.replace("сві", "сьві");
            poshuk = poshuk.replace("свя", "сьвя");
            poshuk = poshuk.replace("зве", "зьве");
            poshuk = poshuk.replace("зві", "зьві");
            poshuk = poshuk.replace("звя", "зьвя");
            poshuk = poshuk.replace("зме", "зьме");
            poshuk = poshuk.replace("змі", "зьмі");
            poshuk = poshuk.replace("змя", "зьмя");
            poshuk = poshuk.replace("зня", "зьня");
            poshuk = poshuk.replace("сле", "сьле");
            poshuk = poshuk.replace("слі", "сьлі");
            poshuk = poshuk.replace("сль", "сьль");
            poshuk = poshuk.replace("слю", "сьлю");
            poshuk = poshuk.replace("сля", "сьля");
            poshuk = poshuk.replace("сне", "сьне");
            poshuk = poshuk.replace("сні", "сьні");
            poshuk = poshuk.replace("сню", "сьню");
            poshuk = poshuk.replace("сня", "сьня");
            poshuk = poshuk.replace("спе", "сьпе");
            poshuk = poshuk.replace("спі", "сьпі");
            poshuk = poshuk.replace("спя", "сьпя");
            poshuk = poshuk.replace("сце", "сьце");
            poshuk = poshuk.replace("сці", "сьці");
            poshuk = poshuk.replace("сць", "сьць");
            poshuk = poshuk.replace("сцю", "сьцю");
            poshuk = poshuk.replace("сця", "сьця");
            poshuk = poshuk.replace("цце", "цьце");
            poshuk = poshuk.replace("цці", "цьці");
            poshuk = poshuk.replace("ццю", "цьцю");
            poshuk = poshuk.replace("ззе", "зьзе");
            poshuk = poshuk.replace("ззі", "зьзі");
            poshuk = poshuk.replace("ззю", "зьзю");
            poshuk = poshuk.replace("ззя", "зьзя");
            poshuk = poshuk.replace("зле", "зьле");
            poshuk = poshuk.replace("злі", "зьлі");
            poshuk = poshuk.replace("злю", "зьлю");
            poshuk = poshuk.replace("зля", "зьля");
            poshuk = poshuk.replace("збе", "зьбе");
            poshuk = poshuk.replace("збі", "зьбі");
            poshuk = poshuk.replace("збя", "зьбя");
            poshuk = poshuk.replace("нне", "ньне");
            poshuk = poshuk.replace("нні", "ньні");
            poshuk = poshuk.replace("нню", "ньню");
            poshuk = poshuk.replace("ння", "ньня");
            poshuk = poshuk.replace("лле", "льле");
            poshuk = poshuk.replace("ллі", "льлі");
            poshuk = poshuk.replace("ллю", "льлю");
            poshuk = poshuk.replace("лля", "льля");
            poshuk = poshuk.replace("дск", "дзк");
            poshuk = poshuk.replace("дств", "дзтв");
            poshuk = poshuk.replace("з’е", "зье");
            poshuk = poshuk.replace("з’я", "зья");

            char[] m = {'у', 'е', 'а', 'о', 'э', 'я', 'і', 'ю', 'ў', 'ь', 'ы'};
            for (char aM : m) {
                int r = poshuk.length() - 1;
                if (r >= 3) {
                    if (poshuk.charAt(r) == aM) {
                        poshuk = poshuk.replace(poshuk, poshuk.substring(0, r));
                    }
                }
            }
            Collections.sort(menu_list_data, new Menu_list_data_sort());
            for (int i = 0; i < menu_list_data.size(); i++) {
                try {
                    InputStream inputStream = getResources().openRawResource(menu_list_data.get(i).id);
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        line = line.replace(",", "");
                        line = line.replace(" — ", " ");
                        line = line.replace("(", "");
                        line = line.replace(")", "");
                        line = line.replace(".", "");
                        line = line.replace("!", "");
                        builder.append(line).append("\n");
                    }
                    inputStream.close();
                    if (builder.toString().toLowerCase().replace("ё", "е").contains(poshuk.toLowerCase())) {
                        if (setClear) {
                            adapter.clear();
                            setClear = false;
                        }
                        adapter.add(menu_list_data.get(i).data);
                    }
                } catch (Throwable ignored) {
                }
            }
            if (setClear) {
                adapter.clear();
            }
        }
        akafist.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, adapter.getCount()));
    }

    private class MyTextWatcher implements TextWatcher {

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
            if (editch) {
                String edit = s.toString();
                edit = edit.replace("и", "і");
                edit = edit.replace("щ", "ў");
                edit = edit.replace("ъ", "'");
                if (edit.length() >= 3) {
                    stopPosukPesen();
                    startPosukPesen(edit);
                } else {
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    akafist.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.seash, 0));
                }
                if (check != 0) {
                    editText.removeTextChangedListener(this);
                    editText.setText(edit);
                    editText.setSelection(editPosition);
                    editText.addTextChangedListener(this);
                }
            }
        }
    }

    private static class search_ListAdaprer extends ArrayAdapter<String> {

        private final Activity mContext;
        private final SharedPreferences k;
        private final ArrayList<String> adapterList;

        search_ListAdaprer(@NonNull Activity context, ArrayList<String> adapterList) {
            super(context, by.carkva_gazeta.malitounik.R.layout.simple_list_item_2, by.carkva_gazeta.malitounik.R.id.label, adapterList);
            this.adapterList = adapterList;
            mContext = context;
            k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = mContext.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_2, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(by.carkva_gazeta.malitounik.R.id.label);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }
            boolean dzenNoch = k.getBoolean("dzen_noch", false);

            viewHolder.text.setText(adapterList.get(position));
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

            if (dzenNoch) {
                viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(mContext, by.carkva_gazeta.malitounik.R.drawable.selector_dark));
                viewHolder.text.setTextColor(ContextCompat.getColor(mContext, by.carkva_gazeta.malitounik.R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(by.carkva_gazeta.malitounik.R.drawable.stiker_black, 0, 0, 0);
            }
            return mView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
