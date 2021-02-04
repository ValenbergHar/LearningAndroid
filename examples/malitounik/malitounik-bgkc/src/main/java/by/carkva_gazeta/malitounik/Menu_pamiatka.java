package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by oleg on 9.6.16
 */
public class Menu_pamiatka extends ListFragment {

    private ArrayAdapter<String> adapter;
    private SharedPreferences k;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    void onDialogFontSizePositiveClick() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_font) {
            Dialog_font_size dialog_font_size = new Dialog_font_size();
            dialog_font_size.show(Objects.requireNonNull(getFragmentManager()), "font");
        }
        if (id == R.id.action_bright) {
            Dialog_brightness dialog_brightness = new Dialog_brightness();
            dialog_brightness.show(Objects.requireNonNull(getFragmentManager()), "brightness");
        }
        if (id == R.id.action_dzen_noch) {
            item.setChecked(!item.isChecked());
            SharedPreferences.Editor prefEditor = k.edit();
            if (item.isChecked()) {
                prefEditor.putBoolean("dzen_noch", true);
            } else {
                prefEditor.putBoolean("dzen_noch", false);
            }
            prefEditor.apply();
            Objects.requireNonNull(getActivity()).recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        ListView listView = getListView();
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        ArrayList<String> data = new ArrayList<>();

        Calendar c = Calendar.getInstance();

        int data_p, month_p;

        int year = c.get(Calendar.YEAR);
        int a = year % 19;
        int b = year % 4;
        int cx = year % 7;
        int ks = (year / 100);
        int p = (13 + 8 * ks) / 25;
        int q = (ks / 4);
        int m = (15 - p + ks - q) % 30;
        int n = (4 + ks - q) % 7;
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

        String[] MonthName = {"студзеня", "лютага", "сакавіка", "красавіка", "траўня", "чэрвеня",
                "ліпеня", "жніўня", "верасьня", "кастрычніка", "лістапада", "сьнежня"};

        GregorianCalendar calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        int pashaD = calendar.get(Calendar.DAY_OF_MONTH);
        int pashaM = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.DATE, -7);
        int erusalimD = calendar.get(Calendar.DAY_OF_MONTH);
        int erusalimM = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.DATE, +46);
        int uznasenneD = calendar.get(Calendar.DAY_OF_MONTH);
        int uznasenneM = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.DATE, +10);
        int troicaD = calendar.get(Calendar.DAY_OF_MONTH);
        int troicaM = calendar.get(Calendar.MONTH);
        calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        calendar.add(Calendar.DATE, -70);
        int postMytnikND = calendar.get(Calendar.DAY_OF_MONTH);
        int postMytnikNM = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.DATE, 7);
        int postMytnikKD = calendar.get(Calendar.DAY_OF_MONTH);
        int postMytnikKM = calendar.get(Calendar.MONTH);
        calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        calendar.add(Calendar.DATE, 7);
        int nopostSvetluKD = calendar.get(Calendar.DAY_OF_MONTH);
        int nopostSvetluKM = calendar.get(Calendar.MONTH);
        calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        calendar.add(Calendar.DATE, 49);
        int nopostTroicaND = calendar.get(Calendar.DAY_OF_MONTH);
        int nopostTroicaNM = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.DATE, 7);
        int nopostTroicaKD = calendar.get(Calendar.DAY_OF_MONTH);
        int nopostTroicaKM = calendar.get(Calendar.MONTH);
        calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        calendar.add(Calendar.DATE, 57);
        int postPetrKD = calendar.get(Calendar.DAY_OF_MONTH);
        int postPetrKM = calendar.get(Calendar.MONTH);
        calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        calendar.add(Calendar.DATE, -48);
        int postVialikiND = calendar.get(Calendar.DAY_OF_MONTH);
        int postVialikiNM = calendar.get(Calendar.MONTH);
        calendar = new GregorianCalendar(c.get(Calendar.YEAR), month_p - 1, data_p);
        calendar.add(Calendar.DATE, -1);
        int postVialikiKD = calendar.get(Calendar.DAY_OF_MONTH);
        int postVialikiKM = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.DATE, -1);
        int postVialikaiPiatKD = calendar.get(Calendar.DAY_OF_MONTH);
        int postVialikaiPiatKM = calendar.get(Calendar.MONTH);

        if (dzenNoch)
            data.add("<strong><font>ЕЎХАРЫСТЫЧНЫ ПОСТ ПЕРАД СЬВЯТЫМ ПРЫЧАСЬЦЕМ</font></strong>");
        else
            data.add("<font color=\"#d00505\"><strong>ЕЎХАРЫСТЫЧНЫ ПОСТ ПЕРАД СЬВЯТЫМ ПРЫЧАСЬЦЕМ</strong></font>");
        data.add("<em>Ня менш за 1 гадзiну перад пачаткам Боскай Літургіі трэба ўстрымацца ад ежы i напояў.</em>");
        data.add("Чыстая вада, а таксама прыём прыпісаных лекаў не забараняецца.");
        if (dzenNoch)
            data.add("<strong>АДЗНАЧЭНЬНЕ СЬВЯТАЎ</strong>");
        else
            data.add("<font color=\"#d00505\"><strong>АДЗНАЧЭНЬНЕ СЬВЯТАЎ</strong></font>");
        data.add("Згодна з кан. 880 Кодэксу Канонаў Усходніх Цэркваў вернікі Беларускай Грэка-Каталіцкай Царквы абавязаны сьвяткаваць, акрамя <strong>кожнай нядзелі</strong>, наступныя царкоўныя сьвяты:");
        data.add("<strong>1.</strong> <em>Сьвяты, якія заўсёды ў нядзелю:</em>");
        data.add("<strong>- Уваход Гасподні ў Ерусалім (Вербніца) " + erusalimD + " " + MonthName[erusalimM] + ".</strong>");
        data.add("<strong>- Уваскрасеньне Хрыстова (Вялiкдзень) " + pashaD + " " + MonthName[pashaM] + ".</strong>");
        data.add("<strong>- Зыход Сьвятога Духа (Тройца) – " + troicaD + " " + MonthName[troicaM] + ".</strong>");
        data.add("<strong>2. Богазьяўленьне (Вадохрышча) – 6 студзеня.</strong>");
        data.add("<strong>3. Дабравешчаньне Найсьвяцейшай Багародзiцы – 25 сакавіка.</strong>");
        data.add("<strong>4. Узьнясеньне Гасподняе (Ушэсьце) – " + uznasenneD + " " + MonthName[uznasenneM] + ".</strong>");
        data.add("<strong>5. Сьвята Вярхоўных Апосталаў Пятра і Паўла – 29 чэрвеня.</strong>");
        data.add("<strong>6. Усьпеньне Найсьвяцейшай Багародзiцы – 15 жніўня.</strong>");
        data.add("<strong>7. Нараджэньне Хрыстова (Раство) – 25 сьнежня.</strong>");
        data.add("У гэтыя царкоўныя сьвяты і ў нядзелі вернiкi <strong>абавязаны браць удзел у сьв. Лiтургii</strong> i ўстрымлівацца ад цяжкай фiзiчнай працы. ");
        data.add("Ва ўсе iншыя сьвяты сьвятары адпраўляюць сьв. Лiтургiю для тых, якiя змогуць i пажадаюць сьвяткаваць. Аднак Царква таксама асабліва заахвочвае вернікаў браць удзел у набажэнствах наступных сьвятаў: <br><strong>• Абрэзаньне Гасподняе</strong>, <em>1 студзеня</em>;<br><strong>• Перамяненьне Гасподняе</strong>, <em>6 жніўня</em>; <br><strong>• Нараджэньне Найсьвяцейшай Багародзіцы</strong>, <em>8 верасьня</em>;<br><strong>• Узвышэньне Пачэснага Крыжа Гасподняга (Крыжаўзвышэньне)</strong>, <em>14 верасьня</em>.");
        if (dzenNoch)
            data.add("<strong>АБАВЯЗКОВЫЯ ПАСТЫ</strong>");
        else
            data.add("<font color=\"#d00505\"><strong>АБАВЯЗКОВЫЯ ПАСТЫ</strong></font>");
        data.add("• <em><strong>Усе пятнiцы</strong> на працягу году вернікі БГКЦ абавязаны ўстрымлiвацца ад мясных страваў.");
        data.add("Посту ў пятніцу няма ў сьвяты Гасподнiя i Багародзiчныя, а таксама ў перыяд:</em>");
        data.add("- ад Раства (25 сьнежня) да Богазьяўленьня (6 студзеня);");
        data.add("- ад Нядзелi мытнiка i фарысея (" + postMytnikND + " " + MonthName[postMytnikNM] + ") да Нядзелi блуднага сына (" + postMytnikKD + " " + MonthName[postMytnikKM] + ");");
        data.add("- ад Вялiкадня (" + pashaD + " " + MonthName[pashaM] + ") да Нядзелi Тамаша (" + nopostSvetluKD + " " + MonthName[nopostSvetluKM] + ");");
        data.add("- ад Тройцы (" + nopostTroicaND + " " + MonthName[nopostTroicaNM] + ") да Нядзелi ўсiх сьвятых (" + nopostTroicaKD + " " + MonthName[nopostTroicaKM] + ").");
        data.add("<em>• Асаблівы час, калі вернікі прыкладаюць узмоцненыя намаганьні для свайго духоўнага росту, абмяжоўваюць сябе ў ежы, а таксама ўстрымліваюцца ад арганiзацыi публiчных забаў з музыкай i танцамi:</em>");
        data.add("<strong>- Вялiкi пост і Вялікі тыдзень:</strong><em>" + postVialikiND + " " + MonthName[postVialikiNM] + " – " + postVialikiKD + " " + MonthName[postVialikiKM] + ";</em>");
        data.add("<strong>- Пятроўскі пост:</strong><em>" + postPetrKD + " " + MonthName[postPetrKM] + " - 28 чэрвеня;</em>");
        data.add("<strong>- Усьпенскі пост:</strong><em> 1 жніўня - 14 жніўня;</em>");
        data.add("<strong>- Калядны пост (Пiлiпаўка):</strong><em> 15 лістапада – 24 сьнежня.</em>");
        data.add("<strong>Грэка-католiкi абавязаны:</strong>");
        data.add("• <em>Устрымлiвацца ад мясных i малочных страваў:</em>");
        data.add("- у першы дзень Вялiкага посту (" + postVialikiND + " " + MonthName[postVialikiNM] + ");");
        data.add("- у Вялiкую пятнiцу (" + postVialikaiPiatKD + " " + MonthName[postVialikaiPiatKM] + ").");
        data.add("• <em>Устрымлiвацца ад мясных страваў i абмяжоўвацца адным пасілкам у дзень:</em>");
        data.add("- у Сьвяты вечар перад Раством (24 сьнежня);");
        data.add("- у Сьвяты вечар перад Богазьяўленьнем (5 студзеня);");
        data.add("- на Ўзвышэньне сьв. Крыжа (14 верасьня);");
        data.add("- на Адсячэньне галавы сьв. Яна Хрысьцiцеля (29 жніўня).");
        if (dzenNoch)
            data.add("<strong><font>АД ПОСТУ ЗВОЛЬНЕНЫЯ:</font></strong>");
        else
            data.add("<font color=\"#d00505\"><strong>АД ПОСТУ ЗВОЛЬНЕНЫЯ:</strong></font>");
        data.add("- дзецi да 14 гадоў i тыя, чый узрост больш за 60 гадоў;");
        data.add("- хворыя фiзiчна i душэўна, цяжарныя жанчыны, а таксама тыя, што кормяць грудзьмi;");
        data.add("- тыя, што выздараўлiваюць пасьля цяжкай хваробы;");
        data.add("- тыя, што не распараджаюцца сабой у поўнай меры (напрыклад, тыя, што жывуць у чужых; зьнябожаныя; тыя, што жывуць з ахвяраваньня i г. д.)");
        data.add("Таксама бiскуп i парахi могуць звольнiць верніка ад посту дзеля нейкiх важкiх прычынаў. Спаведнiк можа гэта зрабiць у спавядальнi.");
        adapter = new MyArrayAdapter(getActivity(), data);
        setListAdapter(adapter);
        getListView().setDivider(null);
        int pad = (int) (10 * getResources().getDisplayMetrics().density);
        getListView().setPadding(pad, pad, pad, pad);
    }

    private static class MyArrayAdapter extends ArrayAdapter<String> {

        private final ArrayList<String> list;
        private final Activity activity;

        MyArrayAdapter(Activity activity, @NonNull ArrayList<String> objects) {
            super(activity, R.layout.simple_list_item_maranata, objects);
            list = objects;
            this.activity = activity;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            SharedPreferences k = activity.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            ExpArrayAdapterParallelItems ea;
            if (convertView == null) {
                ea = new ExpArrayAdapterParallelItems();
                convertView = activity.getLayoutInflater().inflate(R.layout.simple_list_item_maranata, parent, false);
                ea.textView = convertView.findViewById(R.id.label);
                convertView.setTag(ea);
            } else {
                ea = (ExpArrayAdapterParallelItems) convertView.getTag();
            }
            ea.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE));
            ea.textView.setText(MainActivity.fromHtml(list.get(position)));
            if (k.getBoolean("dzen_noch", false)) {
                ea.textView.setTextColor(ContextCompat.getColor(activity, R.color.colorIcons));
            }
            return convertView;
        }
    }

    private static class ExpArrayAdapterParallelItems {
        TextView_Roboto_Condensed textView;
    }
}
