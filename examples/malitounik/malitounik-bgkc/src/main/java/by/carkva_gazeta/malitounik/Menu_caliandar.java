package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by oleg on 14.6.19
 */
public class Menu_caliandar extends Fragment {

    private ViewPager pager;
    private Menu_caliandarPageListinner listinner;
    static String dataJson = "";
    static int munKal = 0;
    private int page;

    public static Menu_caliandar newInstance(int page) {
        Menu_caliandar caliandar = new Menu_caliandar();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        caliandar.setArguments(bundle);
        return caliandar;
    }

    interface Menu_caliandarPageListinner {
        void setPage(int page);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                listinner = (Menu_caliandarPageListinner) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Menu_caliandarPageListinner");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_caliandar, container, false);
        pager = rootView.findViewById(R.id.pager);
        MyCalendarAdapter frag = new MyCalendarAdapter(getFragmentManager());
        pager.setAdapter(frag);
        pager.setCurrentItem(page);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                listinner.setPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        page = Objects.requireNonNull(getArguments()).getInt("page");
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        int dayyear = 0;
        for (int i = SettingsActivity.GET_CALIANDAR_YEAR_MIN; i < c.get(Calendar.YEAR); i++) {
            if (c.isLeapYear(i)) dayyear = 366 + dayyear;
            else dayyear = 365 + dayyear;
        }
        if (dayyear + c.get(Calendar.DAY_OF_YEAR) - 1 == pager.getCurrentItem())
            menu.findItem(R.id.action_glava).setVisible(false);
        else menu.findItem(R.id.action_glava).setVisible(true);
        menu.findItem(R.id.action_mun).setVisible(true);
        menu.findItem(R.id.tipicon).setVisible(true);
        menu.findItem(R.id.sabytie).setVisible(true);
        menu.findItem(R.id.search_sviatyia).setVisible(true);
    }

    private static class MyCalendarAdapter extends SmartFragmentStatePagerAdapter {

        private Fragment mCurrentFragment;
        private int mun = Calendar.JANUARY;
        private int year = SettingsActivity.GET_CALIANDAR_YEAR_MIN;
        private final GregorianCalendar c;

        MyCalendarAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            c = (GregorianCalendar) Calendar.getInstance();
        }

        Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            GregorianCalendar g = new GregorianCalendar(SettingsActivity.GET_CALIANDAR_YEAR_MIN, 0, 1);
            for (int i2 = 0; i2 < getCount(); i2++) {
                if (position == i2) {
                    if (mun != g.get(Calendar.MONTH) || year != g.get(Calendar.YEAR)) {
                        mun = g.get(Calendar.MONTH);
                        year = g.get(Calendar.YEAR);
                    }
                    int dayofyear = g.get(Calendar.DAY_OF_YEAR) - 1;
                    int year = g.get(Calendar.YEAR);
                    int day = g.get(Calendar.DATE) - 1;
                    return caliandar_full.newInstance(position, day, year, dayofyear);
                }
                g.add(Calendar.DATE, 1);
            }
            return caliandar_full.newInstance(0, 1, SettingsActivity.GET_CALIANDAR_YEAR_MIN, 1);
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
            return dayyear;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }
    }
}
