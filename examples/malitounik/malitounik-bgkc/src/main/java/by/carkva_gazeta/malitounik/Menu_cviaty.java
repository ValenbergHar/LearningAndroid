package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

/**
 * Created by oleg on 31.5.16
 */
public class Menu_cviaty extends ListFragment {

    static ArrayList<String> opisanie;
    private static ArrayList<Integer> data;
    private int yearG;
    private carkva_carkva_Listener mListener;
    private long mLastClickTime = 0;

    public static Menu_cviaty newInstance(int year) {
        Menu_cviaty fragmentFirst = new Menu_cviaty();
        Bundle args = new Bundle();
        args.putInt("Year", year);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Calendar c = Calendar.getInstance();
        if (getArguments() != null) {
            yearG = getArguments().getInt("Year", c.get(Calendar.YEAR));
        } else {
            yearG = c.get(Calendar.YEAR);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = getListView();
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        ArrayAdapter<String> adapter = new MyArrayAdapter(Objects.requireNonNull(getActivity()), getPrazdnik(getActivity(), yearG));
        setListAdapter(adapter);
        int pad = (int) (10 * getResources().getDisplayMetrics().density);
        getListView().setPadding(pad,pad,pad,pad);
    }

    static ArrayList<String> getPrazdnik(Context context, int yearG) {
        ArrayList<String> builder = new ArrayList<>();
        data = new ArrayList<>();
        opisanie = new ArrayList<>();
        GregorianCalendar c;
        int a = yearG % 19;
        int b = yearG % 4;
        int cx = yearG % 7;
        int ks = (yearG / 100);
        int p = (13 + 8 * ks) / 25;
        int q = (ks / 4);
        int m = (15 - p + ks - q) % 30;
        int n = (4 + ks - q) % 7;
        int d = (19 * a + m) % 30;
        int ex = (2 * b + 4 * cx + 6 * d + n) % 7;
        int month_p;
        int data_p;
        if (d + ex <= 9) {
            data_p = d + ex + 22;
            month_p = 3;
        } else {
            data_p = d + ex - 9;
            if (d == 29 && ex == 6) data_p = 19;
            if (d == 28 && ex == 6) data_p = 18;
            month_p = 4;
        }
        String[] MonthName = {"студзеня", "лютага", "сакавіка", "красавіка", "траўня", "чэрвеня",
                "ліпеня", "жніўня", "верасьня", "кастрычніка", "лістапада", "сьнежня"};
        String[] NedelName = {"", "нядзеля", "панядзелак", "аўторак", "серада", "чацьвер", "пятніца", "субота"};

        SharedPreferences k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        String color;
        if (dzenNoch) color = "<font color=\"#f44336\">";
        else color = "<font color=\"#d00505\">";

        prazdniki[] prazdnik = new prazdniki[12];
        c = new GregorianCalendar(yearG, 0, 6);
        prazdnik[0] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->" + color + "Богазьяўленьне (Вадохрышча)</font>", "<br><strong><em>6 студзеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + " </strong></em>");
        c = new GregorianCalendar(yearG, 1, 2);
        prazdnik[1] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->" + color + "Сустрэча Госпада нашага Ісуса Хрыста (Грамніцы)</font>", "<br><strong><em>2 лютага, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 2, 25);
        prazdnik[2] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->" + color + "Дабравешчаньне</font>", "<br><strong><em>25 сакавіка, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        GregorianCalendar calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        calendar.add(Calendar.DATE, -7);
        prazdnik[3] = new prazdniki(calendar.get(Calendar.DAY_OF_YEAR), "<!--" + calendar.get(Calendar.DATE) + ":" + calendar.get(Calendar.MONTH) + "--><!--1-->" + color + "Уваход Гасподні ў Ерусалім (Вербніца)</font>", "<br><strong><em>" + calendar.get(Calendar.DAY_OF_MONTH) + " " + MonthName[calendar.get(Calendar.MONTH)] + ", " + NedelName[calendar.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        calendar.add(Calendar.DATE, 46);
        prazdnik[4] = new prazdniki(calendar.get(Calendar.DAY_OF_YEAR), "<!--" + calendar.get(Calendar.DATE) + ":" + calendar.get(Calendar.MONTH) + "--><!--1-->" + color + "Узьнясеньне Гасподняе (Ушэсьце)</font>", "<br><strong><em>" + calendar.get(Calendar.DAY_OF_MONTH) + " " + MonthName[calendar.get(Calendar.MONTH)] + ", " + NedelName[calendar.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        calendar.add(Calendar.DATE, 10);
        prazdnik[5] = new prazdniki(calendar.get(Calendar.DAY_OF_YEAR), "<!--" + calendar.get(Calendar.DATE) + ":" + calendar.get(Calendar.MONTH) + "--><!--1-->" + color + "Зыход Сьвятога Духа (Тройца)</font>", "<br><strong><em>" + calendar.get(Calendar.DAY_OF_MONTH) + " " + MonthName[calendar.get(Calendar.MONTH)] + ", " + NedelName[calendar.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 7, 6);
        prazdnik[6] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->" + color + "Перамяненьне Гасподняе (Спас)</font>", "<br><strong><em>6 жніўня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 7, 15);
        prazdnik[7] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->" + color + "Усьпеньне Найсьвяцейшай Багародзіцы</font>", "<br><strong><em>15 жніўня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 8, 8);
        prazdnik[8] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->" + color + "Нараджэньне Найсьвяцейшай Багародзіцы</font>", "<br><strong><em>8 верасьня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 8, 14);
        prazdnik[9] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->" + color + "Крыжаўзвышэньне</font>", "<br><strong><em>14 верасьня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 10, 21);
        prazdnik[10] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->" + color + "Уваход у Храм Найсьвяцейшай Багародзіцы</font>", "<br><strong><em>21 лістапада, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 11, 25);
        prazdnik[11] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->" + color + "Нараджэньне Хрыстова (Каляды)</font>", "<br><strong><em>25 сьнежня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        Arrays.sort(prazdnik);

        prazdniki[] prazdnikV = new prazdniki[5];
        c = new GregorianCalendar(yearG, 0, 1);
        prazdnikV[0] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--2-->" + color + "Абрэзаньне Гасподняе</font>", "<br><strong><em>1 студзеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 5, 24);
        prazdnikV[1] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--2-->" + color + "Нараджэньне сьв. Яна Прадвесьніка і Хрысьціцеля</font>", "<br><strong><em>24 чэрвеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 5, 29);
        prazdnikV[2] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--2-->" + color + "Сьвятых вярхоўных апосталаў Пятра і Паўла</font>", "<br><strong><em>29 чэрвеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 7, 29);
        prazdnikV[3] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--2-->" + color + "Адсячэньне галавы сьв. Яна Прадвесьніка і Хрысьціцеля</font>", "<br><strong><em>29 жніўня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 9, 1);
        prazdnikV[4] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--2-->" + color + "Покрыва Найсьвяцейшай Багародзіцы</font>", "<br><strong><em>1 кастрычніка, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        //c = new GregorianCalendar(yearG, 11, 9);
        //prazdnikV[5] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--2-->" + color + "Беззаганнае Зачацьце Найсьвяцейшай Багародзіцы</font>", "<br><strong><em>9 сьнежня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        Arrays.sort(prazdnikV);

        prazdniki[] prazdnikPamer = new prazdniki[8];
        c = new GregorianCalendar(yearG, month_p - 1, data_p);
        c.add(Calendar.DATE, -57);
        prazdnikPamer[0] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Мясапусная бацькоўская субота", "<br><strong><em>" + c.get(Calendar.DATE) + " " + MonthName[c.get(Calendar.MONTH)] + ", " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, month_p - 1, data_p);
        c.add(Calendar.DATE, -50);
        prazdnikPamer[1] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Успамін усіх сьвятых айцоў, манахаў і посьнікаў", "<br><strong><em>" + c.get(Calendar.DATE) + " " + MonthName[c.get(Calendar.MONTH)] + ", " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, month_p - 1, data_p);
        c.add(Calendar.DATE, -29);
        prazdnikPamer[2] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Субота 3-га тыдня Вялікага посту", "<br><strong><em>" + c.get(Calendar.DATE) + " " + MonthName[c.get(Calendar.MONTH)] + ", " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, month_p - 1, data_p);
        c.add(Calendar.DATE, -22);
        prazdnikPamer[3] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Субота 4-га тыдня Вялікага посту", "<br><strong><em>" + c.get(Calendar.DATE) + " " + MonthName[c.get(Calendar.MONTH)] + ", " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, month_p - 1, data_p);
        c.add(Calendar.DATE, 9);
        prazdnikPamer[4] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Радаўніца", "<br><strong><em>" + c.get(Calendar.DATE) + " " + MonthName[c.get(Calendar.MONTH)] + ", " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, month_p - 1, data_p);
        c.add(Calendar.DATE, 48);
        prazdnikPamer[5] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Траецкая бацькоўская субота", "<br><strong><em>" + c.get(Calendar.DATE) + " " + MonthName[c.get(Calendar.MONTH)] + ", " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        for (int i = 19; i <= 25; i++) {
            c = new GregorianCalendar(yearG, 9, i);
            int dayofweek = c.get(Calendar.DAY_OF_WEEK);
            if (7 == dayofweek) {
                prazdnikPamer[6] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Зьмітраўская бацькоўская субота", "<br><strong><em>" + c.get(Calendar.DATE) + " " + MonthName[c.get(Calendar.MONTH)] + ", " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
            }
        }
        c = new GregorianCalendar(yearG, 10, 2);
        prazdnikPamer[7] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Дзяды, дзень успаміну памёрлых", "<br><strong><em>2 лістапада, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        Arrays.sort(prazdnikPamer);

        prazdniki[] prazdnikU = new prazdniki[3];
        c = new GregorianCalendar(yearG, 6, 11);
        prazdnikU[0] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Успамін мучаніцкай сьмерці ў катэдры сьв. Сафіі ў Полацку 5 манахаў-базыльянаў", "<br><strong><em>11 ліпеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 8, 15);
        prazdnikU[1] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Успамін Бабровіцкай трагедыі (зьнішчэньне ў 1942 г. жыхароў уніяцкай парафіі в. Бабровічы Івацэвіцкага р-ну)", "<br><strong><em>15 верасьня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 9, 8);
        prazdnikU[2] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--3-->Успамін Берасьцейскай царкоўнай Уніі 1596 году", "<br><strong><em>8(18) кастрычніка, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        Arrays.sort(prazdnikU);

        prazdniki[] prazdnikP = new prazdniki[25];
        c = new GregorianCalendar(yearG, 0, 30);
        prazdnikP[0] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Гомель: Трох Сьвяціцеляў</font>", "<br><strong><em>30 студзеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        prazdnikP[1] = new prazdniki(calendar.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Антвэрпан: Уваскрасеньня Хрыстовага</font>", "<br><strong><em>" + calendar.get(Calendar.DAY_OF_MONTH) + " " + MonthName[calendar.get(Calendar.MONTH)] + ", " + NedelName[calendar.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        prazdnikP[2] = new prazdniki(calendar.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Віцебск: Уваскрасеньня Хрыстовага</font>", "<br><strong><em>" + calendar.get(Calendar.DAY_OF_MONTH) + " " + MonthName[calendar.get(Calendar.MONTH)] + ", " + NedelName[calendar.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 3, 28);
        prazdnikP[3] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Пінск: сьвятога Кірылы Тураўскага</font>", "<br><strong><em>28 красавіка, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 4, 1);
        prazdnikP[4] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Маладэчна: Хрыста Чалавекалюбцы</font>", "<br><strong><em>1 траўня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 4, 7);
        prazdnikP[5] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Івацэвічы: Маці Божай Жыровіцкай</font>", "<br><strong><em>7 траўня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 4, 11);
        prazdnikP[6] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Баранавічы: сьвятых роўнаапостальных Кірылы і Мятода</font>", "<br><strong><em>11 траўня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 4, 13);
        prazdnikP[7] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Горадня: Маці Божай Фацімскай</font>", "<br><strong><em>13 траўня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        calendar.add(Calendar.DATE, 56);
        prazdnikP[8] = new prazdniki(calendar.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Слонім: Сьвятой Тройцы</font>", "<br><strong><em>" + calendar.get(Calendar.DAY_OF_MONTH) + " " + MonthName[calendar.get(Calendar.MONTH)] + ", " + NedelName[calendar.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        calendar.add(Calendar.DATE, 1);
        prazdnikP[9] = new prazdniki(calendar.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Менск: Сьвятога Духа</font>", "<br><strong><em>" + calendar.get(Calendar.DAY_OF_MONTH) + " " + MonthName[calendar.get(Calendar.MONTH)] + ", " + NedelName[calendar.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 5, 27);
        prazdnikP[10] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Менск: Маці Божай Нястомнай Дапамогі</font>", "<br><strong><em>27 чэрвеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 5, 29);
        prazdnikP[11] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Лондан: сьвятых апосталаў Пятра і Паўла</font>", "<br><strong><em>29 чэрвеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        prazdnikP[12] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Жодзіна: сьвятых апосталаў Пятра і Паўла</font>", "<br><strong><em>29 чэрвеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        // Вылічым калі выпадает парафіяльнае свята ў Брэсте
        int[] chisla = {24, 25, 26, 27, 28, 29, 30};
        int brest = 24;
        for (int aChisla : chisla) {
            GregorianCalendar cal = new GregorianCalendar(c.get(Calendar.YEAR), 5, aChisla);
            int deyNed = cal.get(Calendar.DAY_OF_WEEK);
            if (deyNed == 7) brest = aChisla;
        }
        c = new GregorianCalendar(yearG, 5, brest);
        prazdnikP[13] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Берасьце: сьвятых братоў-апосталаў Пятра і Андрэя</font>", "<br><strong><em>" + c.get(Calendar.DAY_OF_MONTH) + " " + MonthName[c.get(Calendar.MONTH)] + ", " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 6, 24);
        prazdnikP[14] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Наваградак: сьв. Барыса і Глеба</font>", "<br><strong><em>24 ліпеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        prazdnikP[15] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Полацак: манастыр сьв. Барыса і Глеба</font>", "<br><strong><em>24 ліпеня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 7, 6);
        prazdnikP[16] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Заслаўе: Перамяненьня Гасподняга</font>", "<br><strong><em>6 жніўня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 8, 8);
        prazdnikP[17] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Магілёў: Бялыніцкай іконы Маці Божай</font>", "<br><strong><em>8 верасьня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 8, 16);
        prazdnikP[18] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Ліда: сьвятамучаніка Язафата Полацкага</font>", "<br><strong><em>16 верасьня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 9, 1);
        prazdnikP[19] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Ворша: Покрыва Найсьвяцейшай Багародзіцы</font>", "<br><strong><em>1 кастрычніка, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        prazdnikP[20] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Мар’іна Горка: Покрыва Найсьвяцейшай Багародзіцы</font>", "<br><strong><em>1 кастрычніка, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 10, 8);
        prazdnikP[21] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Барысаў: сьвятога Арханёла Міхаіла</font>", "<br><strong><em>8 лістапада, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 10, 12);
        prazdnikP[22] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Полацак: сьвятамучаніка Язафата</font>", "<br><strong><em>12 лістапада, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 11, 6);
        prazdnikP[23] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Менск: сьвятога Мікалая Цудатворцы</font>", "<br><strong><em>6 сьнежня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        c = new GregorianCalendar(yearG, 11, 27);
        prazdnikP[24] = new prazdniki(c.get(Calendar.DAY_OF_YEAR), "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "-->" + color + "Менск: праведнага Язэпа</font>", "<br><strong><em>27 сьнежня, " + NedelName[c.get(Calendar.DAY_OF_WEEK)] + "</strong></em>");
        Arrays.sort(prazdnikP);

        c = new GregorianCalendar(yearG, month_p - 1, data_p);
        String pasha = color + "<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><strong>ПАСХА ХРЫСТОВА (ВЯЛІКДЗЕНЬ)</strong></font><br><strong><em>" + data_p + " " + MonthName[month_p - 1] + " " + yearG + " году, " + NedelName[1] + "</strong></em>";
        builder.add(pasha);
        data.add(c.get(Calendar.DAY_OF_YEAR));
        opisanie.add("<!--" + c.get(Calendar.DATE) + ":" + c.get(Calendar.MONTH) + "--><!--1-->ПАСХА ХРЫСТОВА (ВЯЛІКДЗЕНЬ)");
        pasha = color + "<strong>ДВУНАДЗЯСЯТЫЯ СЬВЯТЫ</strong></font><br><br>";
        int one = 0;
        for (prazdniki aPrazdnik : prazdnik) {
            if (one == 0) builder.add(pasha + aPrazdnik.opisanie + aPrazdnik.opisanieData);
            else builder.add(aPrazdnik.opisanie + aPrazdnik.opisanieData);
            data.add(aPrazdnik.data);
            opisanie.add(aPrazdnik.opisanie);
            one++;
        }
        pasha = color + "<strong>ВЯЛІКІЯ СЬВЯТЫ</strong></font><br><br>";
        one = 0;
        for (prazdniki aPrazdnikV : prazdnikV) {
            if (one == 0) builder.add(pasha + aPrazdnikV.opisanie + aPrazdnikV.opisanieData);
            else builder.add(aPrazdnikV.opisanie + aPrazdnikV.opisanieData);
            data.add(aPrazdnikV.data);
            opisanie.add(aPrazdnikV.opisanie);
            one++;
        }
        pasha = color + "<strong>ДНІ ЎСПАМІНУ ПАМЁРЛЫХ</strong></font><br><br>";
        one = 0;
        for (prazdniki pPrazdnik : prazdnikPamer) {
            if (one == 0) builder.add(pasha + pPrazdnik.opisanie + pPrazdnik.opisanieData);
            else builder.add(pPrazdnik.opisanie + pPrazdnik.opisanieData);
            data.add(pPrazdnik.data);
            opisanie.add(pPrazdnik.opisanie);
            one++;
        }
        pasha = color + "<strong>ЦАРКОЎНЫЯ ПАМЯТНЫЯ ДАТЫ</strong></font><br><br>";
        one = 0;
        for (prazdniki aPrazdnikU : prazdnikU) {
            if (one == 0) builder.add(pasha + aPrazdnikU.opisanie + aPrazdnikU.opisanieData);
            else builder.add(aPrazdnikU.opisanie + aPrazdnikU.opisanieData);
            data.add(aPrazdnikU.data);
            opisanie.add(aPrazdnikU.opisanie);
            one++;
        }
        pasha = color + "<strong>ПАРАФІЯЛЬНЫЯ СЬВЯТЫ</strong></font><br><br>";
        one = 0;
        for (prazdniki aPrazdnikP : prazdnikP) {
            if (one == 0) builder.add(pasha + aPrazdnikP.opisanie + aPrazdnikP.opisanieData);
            else builder.add(aPrazdnikP.opisanie + aPrazdnikP.opisanieData);
            data.add(aPrazdnikP.data);
            opisanie.add(aPrazdnikP.opisanie);
            one++;
        }
        return builder;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        if (SettingsActivity.GET_CALIANDAR_YEAR_MAX >= yearG)
            mListener.setDataCalendar(data.get(position), yearG);
    }

    interface carkva_carkva_Listener {
        void setDataCalendar(int day_of_year, int year);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            try {
                mListener = (carkva_carkva_Listener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement carkva_carkva_Listener");
            }
        }
    }

    private static class MyArrayAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final List<String> list;

        MyArrayAdapter(@NonNull Activity context, @NonNull List<String> objects) {
            super(context, R.layout.simple_list_item_sviaty, objects);
            this.context = context;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            SharedPreferences k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            ViewHolder ea;
            if (convertView == null) {
                ea = new ViewHolder();
                convertView = context.getLayoutInflater().inflate(R.layout.simple_list_item_sviaty, parent, false);
                ea.textView = convertView.findViewById(R.id.label);
                ea.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE));
                convertView.setTag(ea);
            } else {
                ea = (ViewHolder) convertView.getTag();
            }
            //CaseInsensitiveResourcesFontLoader fontLoader = new CaseInsensitiveResourcesFontLoader();
            ea.textView.setText(MainActivity.fromHtml(list.get(position)));
            if (k.getBoolean("dzen_noch", false)) {
                ea.textView.setTextColor(ContextCompat.getColor(context, R.color.colorIcons));
            }
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed textView;
    }
}
