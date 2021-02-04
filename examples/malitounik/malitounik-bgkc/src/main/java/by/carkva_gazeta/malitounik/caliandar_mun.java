package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by oleg on 3.7.16
 */
public class caliandar_mun extends AppCompatActivity {

    private ViewPager vpPager, vpPagerNedel;
    private SmartFragmentStatePagerAdapter adapterViewPager, adapterViewPagerNedel;
    private int yearG;
    private int posMun;
    private int day;
    private GregorianCalendar c;
    private Spinner spinner;
    private Spinner spinner2;
    private String[] names;
    private TextView_Roboto_Condensed nedelName;
    private boolean dzenNoch;
    private SharedPreferences chin;
    private boolean sabytue = false;
    public static boolean SabytieOnView = false;

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SabytieOnView = false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        chin = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        TextView_Roboto_Condensed title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN + 4);
        sabytue = getIntent().getBooleanExtra("sabytie", false);
        if (sabytue) title_toolbar.setText(R.string.get_date);
        else title_toolbar.setText(R.string.kaliandar);
        if (dzenNoch) {
            toolbar.setPopupTheme(R.style.AppCompatDark);
            toolbar.setBackgroundResource(R.color.colorprimary_material_dark);
        }
        c = (GregorianCalendar) Calendar.getInstance();
        posMun = Objects.requireNonNull(getIntent().getExtras()).getInt("mun", c.get(Calendar.MONTH));
        yearG = getIntent().getExtras().getInt("year", c.get(Calendar.YEAR));
        if (yearG > SettingsActivity.GET_CALIANDAR_YEAR_MAX)
            yearG = SettingsActivity.GET_CALIANDAR_YEAR_MAX;
        day = getIntent().getExtras().getInt("day", c.get(Calendar.DATE));
        vpPager = findViewById(R.id.pager);
        vpPagerNedel = findViewById(R.id.pagerNedel);
        RelativeLayout nedel = findViewById(R.id.nedel);
        ImageView imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener((v) -> vpPagerNedel.setCurrentItem(vpPagerNedel.getCurrentItem() - 1));
        ImageView imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener((v) -> vpPagerNedel.setCurrentItem(vpPagerNedel.getCurrentItem() + 1));
        nedelName = findViewById(R.id.nedelName);
        if (dzenNoch) {
            imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.levo_catedra));
            imageButton2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pravo_catedra));
            nedelName.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
        }
        caliandar_nedzel.setDenNedeli = true;

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Месяц");
        tabSpec.setContent(R.id.tvTab1);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Тыдзень");
        tabSpec.setContent(R.id.tvTab2);
        tabHost.addTab(tabSpec);
        tabHost.setOnTabChangedListener(tabId -> {
            SharedPreferences.Editor editor = chin.edit();
            if (tabId.contains("tag1"))
                editor.putInt("nedelia", 0);
            else
                editor.putInt("nedelia", 1);
            editor.apply();
            invalidateOptionsMenu();
        });

        ArrayList<String> data2 = new ArrayList<>();
        for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i <= SettingsActivity.GET_CALIANDAR_YEAR_MAX; i++) {
            data2.add(String.valueOf(i));
        }

        names = getResources().getStringArray(R.array.mun_array2);
        caliandarMunAdapter adapter = new caliandarMunAdapter(this, names);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner2 = findViewById(R.id.spinner2);
        caliandarMunAdapter adapter2 = new caliandarMunAdapter(this, data2);
        spinner2.setAdapter(adapter2);

        int pos = chin.getInt("nedelia", 0);
        nedel.setVerticalGravity(View.VISIBLE);
        if (pos == 1) {
            tabHost.setCurrentTabByTag("tag2");
        } else {
            tabHost.setCurrentTabByTag("tag1");
        }

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        c = (GregorianCalendar) Calendar.getInstance();
        adapterViewPagerNedel = new MyCalendarNedelAdapter(getSupportFragmentManager());
        vpPagerNedel.setAdapter(adapterViewPagerNedel);
        int dayyear = 0;
        for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < yearG; i++) {
            if (c.isLeapYear(i)) dayyear = 366 + dayyear;
            else dayyear = 365 + dayyear;
        }
        int count2 = 0;
        int dayyear2 = 0;
        for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i <= SettingsActivity.GET_CALIANDAR_YEAR_MAX; i++) {
            if (c.isLeapYear(i)) dayyear2 = 366 + dayyear2;
            else dayyear2 = 365 + dayyear2;
        }
        GregorianCalendar calendarStart = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, 1);
        calendarStart.setFirstDayOfWeek(Calendar.SUNDAY);
        int ost = calendarStart.get(Calendar.DAY_OF_WEEK);
        if (ost == 1) ost = 8;
        for (int i = 0; i < 7; i++) {
            if (calendarStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) break;
            calendarStart.add(Calendar.DATE, 1);
        }
        GregorianCalendar c2 = new GregorianCalendar(yearG, posMun, day);
        c2.setFirstDayOfWeek(Calendar.SUNDAY);
        GregorianCalendar calendarEnd = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MAX, 11, 31);
        calendarEnd.setFirstDayOfWeek(Calendar.SUNDAY);
        for (int i = 7; i > 0; i--) {
            if (calendarEnd.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) break;
            calendarEnd.add(Calendar.DATE, -1);
        }
        if (calendarEnd.getTimeInMillis() <= c2.getTimeInMillis()) {
            for (int e = 0; e < dayyear2; e++) {
                int dayFull = dayyear + calendarEnd.get(Calendar.DAY_OF_YEAR) - 7 - (8 - ost);
                if (dayFull < 0) dayFull = 0;
                if (e == dayFull) {
                    vpPagerNedel.setCurrentItem(count2);
                    nedelName.setText(calendarEnd.get(Calendar.WEEK_OF_YEAR) + " тыдзень");
                }
                if (e % 7 == 0) {
                    count2++;
                }
            }
        } else if (calendarStart.getTimeInMillis() <= c2.getTimeInMillis()) {
            for (int e = 0; e < dayyear2; e++) {
                int dayFull = dayyear + c2.get(Calendar.DAY_OF_YEAR) - 7 - (8 - ost);
                if (dayFull < 0) dayFull = 0;
                if (e == dayFull) {
                    vpPagerNedel.setCurrentItem(count2);
                    nedelName.setText(c2.get(Calendar.WEEK_OF_YEAR) + " тыдзень");
                }
                if (e % 7 == 0) {
                    count2++;
                }
            }
        } else {
            for (int e = 0; e < dayyear2; e++) {
                int dayFull = dayyear + calendarStart.get(Calendar.DAY_OF_YEAR) - 7 - (8 - ost);
                if (dayFull < 0) dayFull = 0;
                if (e == dayFull) {
                    vpPagerNedel.setCurrentItem(count2);
                    nedelName.setText(calendarStart.get(Calendar.WEEK_OF_YEAR) + " тыдзень");
                }
                if (e % 7 == 0) {
                    count2++;
                }
            }
        }

        int son = (yearG - SettingsActivity.GET_CALIANDAR_YEAR_MIN) * 12 + posMun;
        int pagepos = vpPager.getCurrentItem();
        if (pagepos != son) {
            vpPager.setCurrentItem(son);
        }

        if (adapterViewPagerNedel.getCount() - 1 == vpPagerNedel.getCurrentItem())
            imageButton2.setVisibility(View.GONE);
        if (vpPagerNedel.getCurrentItem() == 0)
            imageButton.setVisibility(View.GONE);

        spinner.setSelection(posMun);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int son = (yearG - SettingsActivity.GET_CALIANDAR_YEAR_MIN) * 12 + position;
                posMun = position;
                int pagepos = vpPager.getCurrentItem();
                if (pagepos != son) {
                    vpPager.setCurrentItem(son);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinner2.setSelection(yearG - SettingsActivity.GET_CALIANDAR_YEAR_MIN);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearG = Integer.parseInt((String) parent.getSelectedItem());
                int son = (yearG - SettingsActivity.GET_CALIANDAR_YEAR_MIN) * 12 + posMun;
                int pagepos = vpPager.getCurrentItem();
                if (pagepos != son) {
                    vpPager.setCurrentItem(son);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < adapterViewPager.getCount(); i++) {
                    if (position == i) {
                        int r = SettingsActivity.GET_CALIANDAR_YEAR_MIN, t = 0;
                        for (int s = 0; s <= c.get(Calendar.YEAR) - SettingsActivity.GET_CALIANDAR_YEAR_MIN + 2; s++) {
                            for (int w = 0; w < 12; w++) {
                                if (i == t) {
                                    yearG = r;
                                    posMun = w;
                                }
                                t++;
                            }
                            r++;
                        }
                        spinner.setSelection(posMun);
                        spinner2.setSelection(yearG - SettingsActivity.GET_CALIANDAR_YEAR_MIN);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        vpPagerNedel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                GregorianCalendar calendarStart = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, 1);
                calendarStart.setFirstDayOfWeek(Calendar.SUNDAY);
                for (int i = 0; i < 7; i++) {
                    if (calendarStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) break;
                    calendarStart.add(Calendar.DATE, 1);
                }
                int ned = calendarStart.get(Calendar.WEEK_OF_YEAR);
                for (int i = 0; i < adapterViewPagerNedel.getCount(); i++) {
                    if (position == i) {
                        nedelName.setText(ned + " тыдзень");
                    }
                    calendarStart.add(Calendar.DATE, 7);
                    ned = calendarStart.get(Calendar.WEEK_OF_YEAR);
                }
                if (adapterViewPagerNedel.getCount() - 1 == position)
                    imageButton2.setVisibility(View.GONE);
                else imageButton2.setVisibility(View.VISIBLE);
                if (position == 0)
                    imageButton.setVisibility(View.GONE);
                else imageButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.caliandar_mun, menu);
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
        int pos = chin.getInt("nedelia", 0);
        if (MainActivity.padzeia.size() > 0 && pos == 0 && !sabytue) {
            menu.findItem(R.id.action_padzeia).setVisible(true);
            if (SabytieOnView) {
                if (dzenNoch)
                    menu.findItem(R.id.action_padzeia).setIcon(R.drawable.calendar_padzea_black_on);
                else
                    menu.findItem(R.id.action_padzeia).setIcon(R.drawable.calendar_padzea_on);
            } else {
                if (dzenNoch)
                    menu.findItem(R.id.action_padzeia).setIcon(R.drawable.calendar_padzea_black_off);
                else
                    menu.findItem(R.id.action_padzeia).setIcon(R.drawable.calendar_padzea_off);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.action_padzeia) {
            caliandar_mun.SabytieOnView = !caliandar_mun.SabytieOnView;
            String messege;
            if (!caliandar_mun.SabytieOnView) {
                messege = getResources().getString(R.string.sabytie_disable_mun);
            } else {
                messege = getResources().getString(R.string.sabytie_enable_mun);
            }
            LinearLayout layout = new LinearLayout(this);
            if (dzenNoch) layout.setBackgroundResource(R.color.colorPrimary_black);
            else layout.setBackgroundResource(R.color.colorPrimary);
            float density = getResources().getDisplayMetrics().density;
            int realpadding = (int) (10 * density);
            TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(this);
            toast.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
            toast.setPadding(realpadding, realpadding, realpadding, realpadding);
            toast.setText(messege);
            toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
            layout.addView(toast);
            Toast mes = new Toast(this);
            mes.setDuration(Toast.LENGTH_SHORT);
            mes.setView(layout);
            mes.show();
            adapterViewPager.notifyDataSetChanged();
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    class caliandarMunAdapter extends ArrayAdapter<String> {

        private ArrayList<String> arrayList;

        caliandarMunAdapter(@NonNull Context context, @NonNull String[] strings) {
            super(context, R.layout.simple_list_item_4, strings);
        }

        caliandarMunAdapter(@NonNull Context context, @NonNull ArrayList<String> objects) {
            super(context, R.layout.simple_list_item_4, objects);
            arrayList = objects;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            GregorianCalendar day = (GregorianCalendar) Calendar.getInstance();
            View v = super.getDropDownView(position, convertView, parent);
            TextView_Roboto_Condensed textView = (TextView_Roboto_Condensed) v;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
            if (arrayList == null) {
                if (day.get(Calendar.MONTH) == position) {
                    textView.setTypeface(null, Typeface.BOLD);
                } else {
                    textView.setTypeface(null, Typeface.NORMAL);
                }
            } else {
                if (day.get(Calendar.YEAR) == position + SettingsActivity.GET_CALIANDAR_YEAR_MIN) {
                    textView.setTypeface(null, Typeface.BOLD);
                } else {
                    textView.setTypeface(null, Typeface.NORMAL);
                }
            }
            return v;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            GregorianCalendar day = (GregorianCalendar) Calendar.getInstance();
            if (convertView == null) {
                convertView = caliandar_mun.this.getLayoutInflater().inflate(R.layout.simple_list_item_4, parent, false);
                viewHolder = new ViewHolder();
                convertView.setTag(viewHolder);
                viewHolder.text = convertView.findViewById(R.id.text1);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_DEFAULT_FONT_SIZE);
            if (arrayList == null) {
                if (day.get(Calendar.MONTH) == position) {
                    viewHolder.text.setTypeface(null, Typeface.BOLD);
                } else {
                    viewHolder.text.setTypeface(null, Typeface.NORMAL);
                }
                viewHolder.text.setText(names[position]);
            } else {
                if (day.get(Calendar.YEAR) == position + SettingsActivity.GET_CALIANDAR_YEAR_MIN) {
                    viewHolder.text.setTypeface(null, Typeface.BOLD);
                } else {
                    viewHolder.text.setTypeface(null, Typeface.NORMAL);
                }
                viewHolder.text.setText(arrayList.get(position));
            }
            return convertView;
        }
    }

    class MyCalendarNedelAdapter extends SmartFragmentStatePagerAdapter {

        private Fragment mCurrentFragment;
        private int cor = 1;

        MyCalendarNedelAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            GregorianCalendar calendarStart = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, 1);
            if (calendarStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                cor = 0;
        }

        Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            GregorianCalendar calendarStart = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, 1);
            for (int i = 0; i < 7; i++) {
                if (calendarStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) break;
                calendarStart.add(Calendar.DATE, 1);
            }
            int count = 0;
            int dayyear = 0;
            for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i <= SettingsActivity.GET_CALIANDAR_YEAR_MAX; i++) {
                if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                else dayyear = 365 + dayyear;
            }
            for (int e = 1; e <= dayyear; e++) {
                if (e % 7 == 0) {
                    if (count == position) {
                        return caliandar_nedzel.newInstance(calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE), position);
                    }
                    count++;
                    calendarStart.add(Calendar.DATE, 7);
                }
            }
            return caliandar_nedzel.newInstance(calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE), 0);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            int dayyear = 0;
            for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i <= SettingsActivity.GET_CALIANDAR_YEAR_MAX; i++) {
                if (c.isLeapYear(i)) dayyear = 366 + dayyear;
                else dayyear = 365 + dayyear;
            }
            return dayyear / 7 - cor;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }
    }

    class MyPagerAdapter extends SmartFragmentStatePagerAdapter {

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return (SettingsActivity.GET_CALIANDAR_YEAR_MAX - SettingsActivity.GET_CALIANDAR_YEAR_MIN + 1) * 12;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            GregorianCalendar g = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, day);
            for (int i = 0; i < getCount(); i++) {
                if (position == i) {
                    return PageFragmentMonth.newInstance(g.get(Calendar.DATE), g.get(Calendar.MONTH), g.get(Calendar.YEAR), position);
                }
                g.add(Calendar.MONTH, 1);
            }
            return PageFragmentMonth.newInstance(g.get(Calendar.DATE), g.get(Calendar.MONTH), g.get(Calendar.YEAR), 0);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
