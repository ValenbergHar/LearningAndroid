package by.carkva_gazeta.malitounik;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by oleg on 20.3.17
 */

public class Widget_mun extends AppWidgetProvider {

    private RemoteViews updateViews;
    private final String UPDATE_ALL_WIDGETS = "update_all_widgets";
    private final String ACTION_PLUS = "mun_plus";
    private final String ACTION_MINUS = "mun_minus";
    private final String RESET = "reset";
    private ArrayList<ArrayList<String>> data;

    @Override
    public void onUpdate(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager, @NonNull int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        if (updateViews == null)
            updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_mun);
        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }
        // Обновляем виджет
        appWidgetManager.updateAppWidget(appWidgetIds, updateViews);
        //appWidgetManager.updateAppWidget(new ComponentName(context.getPackageName(), Widget_mun.class.getName()), updateViews);
    }

    private void updateWidget(Context context, @NonNull AppWidgetManager appWidgetManager, int[] widgetIDs) {
        if (updateViews == null)
            updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_mun);
        SharedPreferences chin = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        String[] MonthName = {"СТУДЗЕНЬ", "ЛЮТЫ", "САКАВІК", "КРАСАВІК", "ТРАВЕНЬ", "ЧЭРВЕНЬ",
                "ЛІПЕНЬ", "ЖНІВЕНЬ", "ВЕРАСЕНЬ", "КАСТРЫЧНІК", "ЛІСТАПАД", "СЬНЕЖАНЬ"};
        for (int i : widgetIDs) {
            int tecmun = chin.getInt("WIDGET" + i, c.get(Calendar.MONTH));
            updateViews.setTextViewText(R.id.Mun_widget, MonthName[tecmun]);
            Intent updateIntent = new Intent(context, Widget_mun.class);
            updateIntent.setAction(ACTION_PLUS);
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, i);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, i, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.imageButton2, pIntent);

            Intent countIntent = new Intent(context, Widget_mun.class);
            countIntent.setAction(ACTION_MINUS);
            countIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, i);
            pIntent = PendingIntent.getBroadcast(context, i, countIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.imageButton, pIntent);
            mun(context, i);
        }
        // Обновляем виджет
        appWidgetManager.updateAppWidget(widgetIDs, updateViews);
        //appWidgetManager.updateAppWidget(new ComponentName(context.getPackageName(), Widget_mun.class.getName()), updateViews);
    }

    private void updateWidget(Context context, @NonNull AppWidgetManager appWidgetManager, int widgetID) {
        SharedPreferences chin = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        int Cyear = SettingsActivity.GET_CALIANDAR_YEAR_MAX; //c.get(Calendar.YEAR);
        int tecmun = chin.getInt("WIDGET" + widgetID, c.get(Calendar.MONTH));
        int tecyear = chin.getInt("WIDGETYEAR" + widgetID, SettingsActivity.GET_CALIANDAR_YEAR_MAX);
        String[] MonthName = {"СТУДЗЕНЬ", "ЛЮТЫ", "САКАВІК", "КРАСАВІК", "ТРАВЕНЬ", "ЧЭРВЕНЬ",
                "ЛІПЕНЬ", "ЖНІВЕНЬ", "ВЕРАСЕНЬ", "КАСТРЫЧНІК", "ЛІСТАПАД", "СЬНЕЖАНЬ"};
        if (updateViews == null)
            updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_mun);
        if (tecyear == c.get(Calendar.YEAR))
            updateViews.setTextViewText(R.id.Mun_widget, MonthName[tecmun]);
        else updateViews.setTextViewText(R.id.Mun_widget, MonthName[tecmun] + ", " + tecyear);
        if (Cyear == tecyear && tecmun == 11)
            updateViews.setViewVisibility(R.id.imageButton2, View.INVISIBLE);
        else updateViews.setViewVisibility(R.id.imageButton2, View.VISIBLE);
        if (SettingsActivity.GET_CALIANDAR_YEAR_MIN == tecyear && tecmun == 0)
            updateViews.setViewVisibility(R.id.imageButton, View.INVISIBLE);
        else updateViews.setViewVisibility(R.id.imageButton, View.VISIBLE);

        Intent updateIntent = new Intent(context, Widget_mun.class);
        updateIntent.setAction(ACTION_PLUS);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, widgetID, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.imageButton2, pIntent);

        Intent countIntent = new Intent(context, Widget_mun.class);
        countIntent.setAction(ACTION_MINUS);
        countIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        pIntent = PendingIntent.getBroadcast(context, widgetID, countIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.imageButton, pIntent);
        mun(context, widgetID);
        // Обновляем виджет
        appWidgetManager.updateAppWidget(widgetID, updateViews);
        //appWidgetManager.updateAppWidget(new ComponentName(context.getPackageName(), Widget_mun.class.getName()), updateViews);
    }

    @Override
    public void onEnabled(@NonNull Context context) {
        super.onEnabled(context);
        SharedPreferences chin = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        chin.edit().putBoolean("WIDGET_MUN_ENABLED", true).apply();
        Intent intent = new Intent(context, Widget_mun.class);
        intent.setAction(UPDATE_ALL_WIDGETS);
        PendingIntent pIntentBoot = PendingIntent.getBroadcast(context, 53, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 52, intent, 0);
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        //if (alarmManager != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Objects.requireNonNull(alarmManager).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 300000, pIntentBoot);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(alarmManager).setExact(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 300000, pIntentBoot);
        } else {
            Objects.requireNonNull(alarmManager).set(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 300000, pIntentBoot);
        }
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), 86400000, pIntent);
        //}
    }

    @Override
    public void onDisabled(@NonNull Context context) {
        super.onDisabled(context);
        SharedPreferences chin = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        chin.edit().putBoolean("WIDGET_MUN_ENABLED", false).apply();
        Intent intent = new Intent(context, Widget_mun.class);
        intent.setAction(UPDATE_ALL_WIDGETS);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 52, intent, 0);
        PendingIntent pIntentBoot = PendingIntent.getBroadcast(context, 53, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent reset = new Intent(context, Widget_mun.class);
        reset.setAction(RESET);
        PendingIntent pReset = PendingIntent.getBroadcast(context, 257, reset, 0);
        //if (alarmManager != null) {
        Objects.requireNonNull(alarmManager).cancel(pIntent);
        alarmManager.cancel(pIntentBoot);
        alarmManager.cancel(pReset);
        //}
    }

    @Override
    public void onDeleted(@NonNull Context context, @NonNull int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        // Удаляем Preferences
        SharedPreferences.Editor editor = context.getSharedPreferences("biblia", Context.MODE_PRIVATE).edit();
        for (int widgetID : appWidgetIds) {
            editor.remove("WIDGET" + widgetID);
            editor.remove("WIDGETYEAR" + widgetID);
        }
        editor.apply();
    }

    private long mkTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        SharedPreferences chin = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase(UPDATE_ALL_WIDGETS)) {
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] ids = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int i : ids) {
                chin.edit().putInt("WIDGET" + i, c.get(Calendar.MONTH)).apply();
                chin.edit().putInt("WIDGETYEAR" + i, c.get(Calendar.YEAR)).apply();
            }
            onUpdate(context, appWidgetManager, ids);
            Intent intentUpdate = new Intent(context, Widget_mun.class);
            intentUpdate.setAction(UPDATE_ALL_WIDGETS);
            c.add(Calendar.DATE, 1);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 51, intentUpdate, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Objects.requireNonNull(alarmManager).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(alarmManager).setExact(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
            } else {
                Objects.requireNonNull(alarmManager).set(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
            }
        }

        c = (GregorianCalendar) Calendar.getInstance();
        String RESET_MAIN = "reset_main";
        if (intent.getAction().equalsIgnoreCase(RESET_MAIN)) {
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] ids = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int i : ids) {
                chin.edit().putInt("WIDGET" + i, c.get(Calendar.MONTH)).apply();
                chin.edit().putInt("WIDGETYEAR" + i, c.get(Calendar.YEAR)).apply();
            }
            updateWidget(context, AppWidgetManager.getInstance(context), ids);
        }

        if (intent.getAction().equalsIgnoreCase(RESET)) {
            // извлекаем ID экземпляра
            int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;//AppWidgetManager.INVALID_APPWIDGET_ID = 0
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            }
            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                chin.edit().putInt("WIDGET" + mAppWidgetId, c.get(Calendar.MONTH)).apply();
                chin.edit().putInt("WIDGETYEAR" + mAppWidgetId, c.get(Calendar.YEAR)).apply();
                updateWidget(context, AppWidgetManager.getInstance(context), mAppWidgetId);
            }
        }

        if (intent.getAction().equalsIgnoreCase(ACTION_PLUS) || intent.getAction().equalsIgnoreCase(ACTION_MINUS)) {
            // извлекаем ID экземпляра
            int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;//AppWidgetManager.INVALID_APPWIDGET_ID = 0
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            }
            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                int tekmun = chin.getInt("WIDGET" + mAppWidgetId, c.get(Calendar.MONTH));
                int tekyear = chin.getInt("WIDGETYEAR" + mAppWidgetId, c.get(Calendar.YEAR));
                if (intent.getAction().equalsIgnoreCase(ACTION_PLUS)) {
                    if (tekmun < 11) chin.edit().putInt("WIDGET" + mAppWidgetId, ++tekmun).apply();
                    else {
                        chin.edit().putInt("WIDGET" + mAppWidgetId, 0).apply();
                        chin.edit().putInt("WIDGETYEAR" + mAppWidgetId, ++tekyear).apply();
                    }
                } else {
                    if (tekmun > 0) chin.edit().putInt("WIDGET" + mAppWidgetId, --tekmun).apply();
                    else {
                        chin.edit().putInt("WIDGET" + mAppWidgetId, 11).apply();
                        chin.edit().putInt("WIDGETYEAR" + mAppWidgetId, --tekyear).apply();
                    }
                }
                Intent reset = new Intent(context, Widget_mun.class);
                reset.setAction(RESET);
                reset.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                PendingIntent pReset = PendingIntent.getBroadcast(context, 257, reset, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                //if (alarmManager != null) {
                Objects.requireNonNull(alarmManager).cancel(pReset);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120000, pReset);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120000, pReset);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120000, pReset);
                }
                //}
                // Обновляем виджет
                updateWidget(context, AppWidgetManager.getInstance(context), mAppWidgetId);
            }
        }
    }

    private int getmun(int mun, int year) {
        GregorianCalendar g = new GregorianCalendar(year, mun, 1); //(GregorianCalendar) Calendar.getInstance();
        int position = (year - SettingsActivity.GET_CALIANDAR_YEAR_MIN) * 12 + g.get(Calendar.MONTH);
        int count = (SettingsActivity.GET_CALIANDAR_YEAR_MAX - SettingsActivity.GET_CALIANDAR_YEAR_MIN + 1) * 12;
        for (int i = 0; i < count; i++) {
            if (position == i) {
                return position;
            }
        }
        return position;
    }

    @SuppressWarnings("ConstantConditions")
    private void mun(Context context, int widgetID) {
        updateViews.setViewVisibility(R.id.nedel5, View.VISIBLE);
        updateViews.setViewVisibility(R.id.nedel6, View.VISIBLE);

        SharedPreferences chin = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);

        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();

        GregorianCalendar calendar_post;

        //Month = 10;
        int Month = chin.getInt("WIDGET" + widgetID, c.get(Calendar.MONTH));
        //year = 2016
        int year = chin.getInt("WIDGETYEAR" + widgetID, c.get(Calendar.YEAR));

        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(MainActivity.caliandar(context, getmun(Month, year)));
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            isr.close();
        } catch (IOException ignored) {
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
        }.getType();
        data = new ArrayList<>(gson.fromJson(builder.toString(), type));

        GregorianCalendar calendar_full = new GregorianCalendar(year, Month, 1);

        boolean munTudey = false;
        if (Month == c.get(Calendar.MONTH) && year == c.get(Calendar.YEAR)) munTudey = true;


        int wik = calendar_full.get(Calendar.DAY_OF_WEEK);
        int munAll = calendar_full.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mun_actual = c.get(Calendar.DAY_OF_MONTH);
        calendar_full.add(Calendar.MONTH, -1);
        int old_mun_aktual = calendar_full.getActualMaximum(Calendar.DAY_OF_MONTH);
        int Mouth_old = calendar_full.get(Calendar.MONTH);
        int year_old = calendar_full.get(Calendar.YEAR);
        int old_day = old_mun_aktual - wik + 1;
        calendar_full.add(Calendar.MONTH, 2);
        int Mouth_new = calendar_full.get(Calendar.MONTH);
        int year_new = calendar_full.get(Calendar.YEAR);
        String day;
        int i = 0;
        int new_day = 0;
        boolean nopost = false, post = false, strogiPost = false; //, do_day, img;
        for (int e = 1; e <= 42; e++) {
            int denNedeli;
            if (e < wik) {
                ++old_day;
                day = "start";
            } else if (e < munAll + wik) {
                i++;
                day = String.valueOf(i);
                calendar_post = new GregorianCalendar(year, Month, i);
                denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                nopost = data.get(i - 1).get(7).contains("1");
                post = data.get(i - 1).get(7).contains("2");
                strogiPost = data.get(i - 1).get(7).contains("3");
                if (denNedeli == 1) nopost = false;
            } else {
                ++new_day;
                day = "end";
            }
            if (42 - (munAll + wik) >= 6) {
                updateViews.setViewVisibility(R.id.nedel6, View.GONE);
            }
            if (munAll + wik == 29) {
                updateViews.setViewVisibility(R.id.nedel5, View.GONE);
            }
            calendar_post = new GregorianCalendar(year, Month, i);
            String WIDGETMUN = "widget_mun";
            if (e == 1) {
                updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                if (day.equals("start")) {
                    calendar_post = new GregorianCalendar(year_old, Mouth_old, old_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_old + "" + old_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button1a, pIntent);
                    updateViews.setTextViewText(R.id.button1a, String.valueOf(old_day));
                    updateViews.setTextColor(R.id.button1a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                } else {
                    updateViews.setTextColor(R.id.button1a, ContextCompat.getColor(context, R.color.colorPrimary));
                    updateViews.setTextViewText(R.id.button1a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button1a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button1a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button1a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button1a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button1a, pIntent);
                }
            }
            if (e == 2) {
                updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("start")) {
                    calendar_post = new GregorianCalendar(year_old, Mouth_old, old_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_old + "" + old_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button2a, pIntent);
                    updateViews.setTextViewText(R.id.button2a, String.valueOf(old_day));
                    updateViews.setTextColor(R.id.button2a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                } else {
                    updateViews.setTextColor(R.id.button2a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button2a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button2a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button2a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button2a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button2a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button2a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button2a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button2a, pIntent);
                }
            }
            if (e == 3) {
                updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("start")) {
                    calendar_post = new GregorianCalendar(year_old, Mouth_old, old_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_old + "" + old_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button3a, pIntent);
                    updateViews.setTextViewText(R.id.button3a, String.valueOf(old_day));
                    updateViews.setTextColor(R.id.button3a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                } else {
                    updateViews.setTextColor(R.id.button3a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button3a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button3a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button3a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button3a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button3a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button3a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button3a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button3a, pIntent);
                }
            }
            if (e == 4) {
                updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("start")) {
                    calendar_post = new GregorianCalendar(year_old, Mouth_old, old_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_old + "" + old_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button4a, pIntent);
                    updateViews.setTextViewText(R.id.button4a, String.valueOf(old_day));
                    updateViews.setTextColor(R.id.button4a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                } else {
                    updateViews.setTextColor(R.id.button4a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button4a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button4a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button4a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button4a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button4a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {

                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {

                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button4a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button4a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button4a, pIntent);
                }
            }
            if (e == 5) {
                updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("start")) {
                    calendar_post = new GregorianCalendar(year_old, Mouth_old, old_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_old + "" + old_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button5a, pIntent);
                    updateViews.setTextViewText(R.id.button5a, String.valueOf(old_day));
                    updateViews.setTextColor(R.id.button5a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                } else {
                    updateViews.setTextColor(R.id.button5a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button5a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button5a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button5a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button5a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button5a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {

                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button5a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button5a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button5a, pIntent);
                }
            }
            if (e == 6) {
                updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("start")) {
                    calendar_post = new GregorianCalendar(year_old, Mouth_old, old_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_old + "" + old_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button6a, pIntent);
                    updateViews.setTextViewText(R.id.button6a, String.valueOf(old_day));
                    updateViews.setTextColor(R.id.button6a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                } else {
                    updateViews.setTextColor(R.id.button6a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button6a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button6a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button6a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button6a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button6a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button6a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button6a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button6a, pIntent);
                }
            }
            if (e == 7) {
                updateViews.setTextColor(R.id.button7a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button7a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button7a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button7a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button7a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button7a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button7a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button7a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button7a, pIntent);
            }
            if (e == 8) {
                updateViews.setTextColor(R.id.button8a, ContextCompat.getColor(context, R.color.colorPrimary));
                updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                updateViews.setTextViewText(R.id.button8a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button8a, ContextCompat.getColor(context, R.color.colorIcons));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button8a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button8a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button8a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button8a, pIntent);
            }
            if (e == 9) {
                updateViews.setTextColor(R.id.button9a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button9a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button9a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button9a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button9a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button9a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button9a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button9a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button9a, pIntent);
            }
            if (e == 10) {
                updateViews.setTextColor(R.id.button10a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button10a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button10a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button10a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button10a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button10a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button10a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button10a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button10a, pIntent);
            }
            if (e == 11) {
                updateViews.setTextColor(R.id.button11a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button11a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button11a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button11a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button11a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button11a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button11a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button11a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button11a, pIntent);
            }
            if (e == 12) {
                updateViews.setTextColor(R.id.button12a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button12a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button12a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button12a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button12a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button12a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button12a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button12a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button12a, pIntent);
            }
            if (e == 13) {
                updateViews.setTextColor(R.id.button13a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button13a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button13a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button13a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button13a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button13a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button13a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button13a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button13a, pIntent);
            }
            if (e == 14) {
                updateViews.setTextColor(R.id.button14a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button14a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button14a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button14a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button14a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button14a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button14a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button14a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button14a, pIntent);
            }
            if (e == 15) {
                updateViews.setTextColor(R.id.button15a, ContextCompat.getColor(context, R.color.colorPrimary));
                updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                updateViews.setTextViewText(R.id.button15a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button15a, ContextCompat.getColor(context, R.color.colorIcons));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button15a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button15a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button15a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button15a, pIntent);
            }
            if (e == 16) {
                updateViews.setTextColor(R.id.button16a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button16a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button16a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button16a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button16a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button16a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {

                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button16a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button16a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button16a, pIntent);
            }
            if (e == 17) {
                updateViews.setTextColor(R.id.button17a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button17a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button17a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button17a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button17a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button17a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button17a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button17a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button17a, pIntent);
            }
            if (e == 18) {
                updateViews.setTextColor(R.id.button18a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button18a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button18a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button18a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button18a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button18a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button18a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button18a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button18a, pIntent);
            }
            if (e == 19) {
                updateViews.setTextColor(R.id.button19a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button19a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button19a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button19a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button19a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button19a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button19a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button19a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button19a, pIntent);
            }
            if (e == 20) {
                updateViews.setTextColor(R.id.button20a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button20a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button20a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button20a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button20a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button20a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button20a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button20a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button20a, pIntent);
            }
            if (e == 21) {
                updateViews.setTextColor(R.id.button21a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button21a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button21a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button21a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button21a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button21a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button21a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button21a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button21a, pIntent);
            }
            if (e == 22) {
                updateViews.setTextColor(R.id.button22a, ContextCompat.getColor(context, R.color.colorPrimary));
                updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                updateViews.setTextViewText(R.id.button22a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button22a, ContextCompat.getColor(context, R.color.colorIcons));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button22a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button22a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button22a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button22a, pIntent);
            }
            if (e == 23) {
                updateViews.setTextColor(R.id.button23a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button23a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button23a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button23a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button23a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button23a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button23a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button23a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button23a, pIntent);
            }
            if (e == 24) {
                updateViews.setTextColor(R.id.button24a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button24a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button24a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button24a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button24a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button24a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button24a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button24a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button24a, pIntent);
            }
            if (e == 25) {
                updateViews.setTextColor(R.id.button25a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button25a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button25a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button25a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button25a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button25a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button25a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button25a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button25a, pIntent);
            }
            if (e == 26) {
                updateViews.setTextColor(R.id.button26a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button26a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button26a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button26a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button26a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button26a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button26a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button26a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button26a, pIntent);
            }
            if (e == 27) {
                updateViews.setTextColor(R.id.button27a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button27a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button27a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button27a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button27a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button27a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button27a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button27a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button27a, pIntent);
            }
            if (e == 28) {
                updateViews.setTextColor(R.id.button28a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_day);
                updateViews.setTextViewText(R.id.button28a, String.valueOf(i));
                if (SviatyDvunadesiatya(i)) {
                    if (mun_actual == i && munTudey)
                        updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_red_today);
                    else
                        updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_red);
                    updateViews.setTextColor(R.id.button28a, ContextCompat.getColor(context, R.color.colorIcons));
                    updateViews.setTextViewText(R.id.button28a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                } else if (SviatyVialikia(i)) {
                    if (mun_actual == i && munTudey) {
                        updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_red_today);
                    } else {
                        updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_red);
                    }
                    updateViews.setTextColor(R.id.button28a, ContextCompat.getColor(context, R.color.colorIcons));
                } else {
                    if (nopost) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                        else
                            updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                    }
                    if (post) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_post_today);
                        else
                            updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_post);
                    }
                    if (strogiPost) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                        } else {
                            updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                        }
                        updateViews.setTextColor(R.id.button28a, ContextCompat.getColor(context, R.color.colorIcons));
                    }
                    if (!nopost && !post && !strogiPost) {
                        denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                        if (denNedeli == 1) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        } else {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_day_today);
                            else
                                updateViews.setInt(R.id.button28a, "setBackgroundResource", R.drawable.calendar_day);
                        }
                    }
                }
                if (Prorok(i))
                    updateViews.setTextViewText(R.id.button28a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                calendar_post = new GregorianCalendar(year, Month, i);
                Intent dayIntent = new Intent(context, SplashActivity.class);
                dayIntent.putExtra(WIDGETMUN, true);
                dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String code = year + "" + Month + "" + i;
                PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                updateViews.setOnClickPendingIntent(R.id.button28a, pIntent);
            }
            if (e == 29) {
                updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button29a, pIntent);
                    updateViews.setTextColor(R.id.button29a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setTextViewText(R.id.button29a, String.valueOf(new_day));
                } else {
                    updateViews.setTextColor(R.id.button29a, ContextCompat.getColor(context, R.color.colorPrimary));
                    updateViews.setTextViewText(R.id.button29a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button29a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button29a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button29a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button29a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button29a, pIntent);
                }
            }
            if (e == 30) {
                updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button30a, pIntent);
                    updateViews.setTextColor(R.id.button30a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setTextViewText(R.id.button30a, String.valueOf(new_day));
                } else {
                    updateViews.setTextColor(R.id.button30a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button30a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button30a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button30a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button30a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button30a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button30a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button30a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button30a, pIntent);
                }
            }
            if (e == 31) {
                updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button31a, pIntent);
                    updateViews.setTextColor(R.id.button31a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setTextViewText(R.id.button31a, String.valueOf(new_day));
                } else {
                    updateViews.setTextColor(R.id.button31a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button31a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button31a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button31a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button31a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button31a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button31a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button31a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button31a, pIntent);
                }
            }
            if (e == 32) {
                updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button32a, pIntent);
                    updateViews.setTextColor(R.id.button32a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setTextViewText(R.id.button32a, String.valueOf(new_day));
                } else {
                    updateViews.setTextColor(R.id.button32a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button32a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button32a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button32a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button32a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button32a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button32a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button32a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button32a, pIntent);
                }
            }
            if (e == 33) {
                updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button33a, pIntent);
                    updateViews.setTextColor(R.id.button33a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setTextViewText(R.id.button33a, String.valueOf(new_day));
                } else {
                    updateViews.setTextColor(R.id.button33a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button33a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button33a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button33a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button33a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button33a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button33a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button33a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button33a, pIntent);
                }
            }
            if (e == 34) {
                updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button34a, pIntent);
                    updateViews.setTextColor(R.id.button34a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setTextViewText(R.id.button34a, String.valueOf(new_day));
                } else {
                    updateViews.setTextColor(R.id.button34a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button34a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button34a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button34a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button34a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button34a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button34a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button34a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button34a, pIntent);
                }
            }
            if (e == 35) {
                updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button35a, pIntent);
                    updateViews.setTextColor(R.id.button35a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setTextViewText(R.id.button35a, String.valueOf(new_day));
                } else {
                    updateViews.setTextColor(R.id.button35a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button35a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button35a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button35a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button35a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button35a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button35a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button35a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button35a, pIntent);
                }
            }
            if (e == 36) {
                updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button36a, pIntent);
                    updateViews.setTextColor(R.id.button36a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setTextViewText(R.id.button36a, String.valueOf(new_day));
                } else {
                    updateViews.setTextColor(R.id.button36a, ContextCompat.getColor(context, R.color.colorPrimary));
                    updateViews.setTextViewText(R.id.button36a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button36a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button36a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button36a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button36a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button36a, pIntent);
                }
            }
            if (e == 37) {
                updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_day);
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button37a, pIntent);
                    updateViews.setTextColor(R.id.button37a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setTextViewText(R.id.button37a, String.valueOf(new_day));
                } else {
                    updateViews.setTextColor(R.id.button37a, ContextCompat.getColor(context, R.color.colorPrimary_text));
                    updateViews.setTextViewText(R.id.button37a, String.valueOf(i));
                    if (SviatyDvunadesiatya(i)) {
                        if (mun_actual == i && munTudey)
                            updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_red_today);
                        else
                            updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_red);
                        updateViews.setTextColor(R.id.button37a, ContextCompat.getColor(context, R.color.colorIcons));
                        updateViews.setTextViewText(R.id.button37a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    } else if (SviatyVialikia(i)) {
                        if (mun_actual == i && munTudey) {
                            updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_red_today);
                        } else {
                            updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_red);
                        }
                        updateViews.setTextColor(R.id.button37a, ContextCompat.getColor(context, R.color.colorIcons));
                    } else {
                        if (nopost) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                            else
                                updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                        }
                        if (post) {
                            if (mun_actual == i && munTudey)
                                updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_post_today);
                            else
                                updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_post);
                        }
                        if (strogiPost) {
                            if (mun_actual == i && munTudey) {
                                updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_strogi_post_today);
                            } else {
                                updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_strogi_post);
                            }
                            updateViews.setTextColor(R.id.button37a, ContextCompat.getColor(context, R.color.colorIcons));
                        }
                        if (!nopost && !post && !strogiPost) {
                            denNedeli = calendar_post.get(Calendar.DAY_OF_WEEK);
                            if (denNedeli == 1) {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_bez_posta_today);
                                else
                                    updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_bez_posta);
                            } else {
                                if (mun_actual == i && munTudey)
                                    updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_day_today);
                                else
                                    updateViews.setInt(R.id.button37a, "setBackgroundResource", R.drawable.calendar_day);
                            }
                        }
                    }
                    if (Prorok(i))
                        updateViews.setTextViewText(R.id.button37a, MainActivity.fromHtml("<strong>" + i + "</strong>"));
                    calendar_post = new GregorianCalendar(year, Month, i);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Month + "" + i;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button37a, pIntent);
                }
            }
            if (e == 38) {
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button38a, pIntent);
                    updateViews.setTextColor(R.id.button38a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setInt(R.id.button38a, "setBackgroundResource", R.drawable.calendar_day);
                    updateViews.setTextViewText(R.id.button38a, String.valueOf(new_day));
                }
            }
            if (e == 39) {
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button39a, pIntent);
                    updateViews.setTextColor(R.id.button39a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setInt(R.id.button39a, "setBackgroundResource", R.drawable.calendar_day);
                    updateViews.setTextViewText(R.id.button39a, String.valueOf(new_day));
                }
            }
            if (e == 40) {
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button40a, pIntent);
                    updateViews.setTextColor(R.id.button40a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setInt(R.id.button40a, "setBackgroundResource", R.drawable.calendar_day);
                    updateViews.setTextViewText(R.id.button40a, String.valueOf(new_day));
                }
            }
            if (e == 41) {
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button41a, pIntent);
                    updateViews.setTextColor(R.id.button41a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setInt(R.id.button41a, "setBackgroundResource", R.drawable.calendar_day);
                    updateViews.setTextViewText(R.id.button41a, String.valueOf(new_day));
                }
            }
            if (e == 42) {
                if (day.equals("end")) {
                    calendar_post = new GregorianCalendar(year_new, Mouth_new, new_day);
                    Intent dayIntent = new Intent(context, SplashActivity.class);
                    dayIntent.putExtra(WIDGETMUN, true);
                    dayIntent.putExtra("DayYear", calendar_post.get(Calendar.DAY_OF_YEAR));
                    dayIntent.putExtra("Year", calendar_post.get(Calendar.YEAR));
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    String code = year + "" + Mouth_new + "" + new_day;
                    PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt(code), dayIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    updateViews.setOnClickPendingIntent(R.id.button42a, pIntent);
                    updateViews.setTextColor(R.id.button42a, ContextCompat.getColor(context, R.color.colorSecondary_text));
                    updateViews.setInt(R.id.button42a, "setBackgroundResource", R.drawable.calendar_day);
                    updateViews.setTextViewText(R.id.button42a, String.valueOf(new_day));
                }
            }
        }
    }

    private boolean SviatyVialikia(int day) {
        // когда выпадают ВЯЛІКІЯ СЬВЯТЫ относительно Пасхі
        return data.get(day - 1).get(5).contains("2");
    }

    private boolean SviatyDvunadesiatya(int day) {
        // когда выпадают двунадесятые праздники
        return data.get(day - 1).get(5).contains("1");
    }

    private boolean Prorok(int day) {
        // когда выпадают Прарокі
        return !data.get(day - 1).get(4).contains("no_sviatyia") && data.get(day - 1).get(4).contains("#d00505");
    }
}
