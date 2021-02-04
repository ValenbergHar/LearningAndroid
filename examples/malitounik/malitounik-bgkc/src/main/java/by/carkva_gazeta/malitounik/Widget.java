package by.carkva_gazeta.malitounik;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.view.View;
import android.widget.RemoteViews;

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
 * Created by oleg on 17.11.16
 */

public class Widget extends AppWidgetProvider {

    private final String UPDATE_ALL_WIDGETS = "update_all_widgets";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        for (int widgetID : appWidgetIds) {
            kaliandar(context, appWidgetManager, widgetID);
        }
        // Обновляем виджет
        //appWidgetManager.updateAppWidget(appWidgetIds, updateViews);
    }

    private static void Prazdnik(Context context, RemoteViews updateViews, int R_color_colorPrimary) {
        updateViews.setInt(R.id.textChislo, "setBackgroundColor", ContextCompat.getColor(context, R_color_colorPrimary));
        updateViews.setTextColor(R.id.textChislo, ContextCompat.getColor(context, R.color.colorIcons));
        updateViews.setInt(R.id.textDenNedeli, "setBackgroundColor", ContextCompat.getColor(context, R_color_colorPrimary));
        updateViews.setInt(R.id.textMesiac, "setBackgroundColor", ContextCompat.getColor(context, R_color_colorPrimary));
        updateViews.setTextColor(R.id.textDenNedeli, ContextCompat.getColor(context, R.color.colorIcons));
        updateViews.setTextColor(R.id.textMesiac, ContextCompat.getColor(context, R.color.colorIcons));
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        SharedPreferences sp = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        sp.edit().putBoolean("WIDGET_ENABLED", true).apply();
        Intent intent = new Intent(context, Widget.class);
        intent.setAction(UPDATE_ALL_WIDGETS);
        PendingIntent pIntentBoot = PendingIntent.getBroadcast(context, 51, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 50, intent, 0);
        GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        //if (alarmManager != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Objects.requireNonNull(alarmManager).setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 300000, pIntentBoot);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(alarmManager).setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 300000, pIntentBoot);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
        } else {
            Objects.requireNonNull(alarmManager).set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 300000, pIntentBoot);
            alarmManager.set(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
        }
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), 86400000, pIntent);
        //}
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        SharedPreferences.Editor editor = context.getSharedPreferences("biblia", Context.MODE_PRIVATE).edit();
        for (int widgetID : appWidgetIds) {
            editor.remove("dzen_noch_widget_day" + widgetID);
        }
        editor.apply();
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        SharedPreferences sp = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        sp.edit().putBoolean("WIDGET_ENABLED", false).apply();
        Intent intent = new Intent(context, Widget.class);
        intent.setAction(UPDATE_ALL_WIDGETS);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 50, intent, 0);
        PendingIntent pIntentBoot = PendingIntent.getBroadcast(context, 51, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //if (alarmManager != null) {
        Objects.requireNonNull(alarmManager).cancel(pIntent);
        alarmManager.cancel(pIntentBoot);
        //}
    }

    private long mkTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase(UPDATE_ALL_WIDGETS)) {
            ComponentName thisAppWidget = new ComponentName(
                    context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            int[] ids = appWidgetManager.getAppWidgetIds(thisAppWidget);
            onUpdate(context, appWidgetManager, ids);
            Intent intentUpdate = new Intent(context, Widget.class);
            intentUpdate.setAction(UPDATE_ALL_WIDGETS);

            GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
            c.add(Calendar.DATE, 1);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 50, intentUpdate, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Objects.requireNonNull(alarmManager).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(alarmManager).setExact(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
            } else {
                Objects.requireNonNull(alarmManager).set(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
            }
        }
    }

    private static int getmun() {
        GregorianCalendar g = (GregorianCalendar) Calendar.getInstance();
        int position = (g.get(Calendar.YEAR) - SettingsActivity.GET_CALIANDAR_YEAR_MIN) * 12 + g.get(Calendar.MONTH);
        int count = (SettingsActivity.GET_CALIANDAR_YEAR_MAX - SettingsActivity.GET_CALIANDAR_YEAR_MIN + 1) * 12;
        for (int i = 0; i < count; i++) {
            if (position == i) {
                return position;
            }
        }
        return position;
    }

    public static void kaliandar(Context context, AppWidgetManager appWidgetManager, int widgetID) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        BitmapDrawable TileMe = new BitmapDrawable(context.getResources(), BitmapFactory.decodeResource(context.getResources(), R.drawable.calendar_fon));
        TileMe.setTileModeX(Shader.TileMode.REPEAT);

        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(MainActivity.caliandar(context, getmun()));
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
        ArrayList<ArrayList<String>> data = new ArrayList<>(gson.fromJson(builder.toString(), type));
        GregorianCalendar g = (GregorianCalendar) Calendar.getInstance();
        int day = g.get(Calendar.DATE) - 1;
        GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(data.get(day).get(3)), Integer.parseInt(data.get(day).get(2)), Integer.parseInt(data.get(day).get(1)));

        boolean dzenNoch = context.getSharedPreferences("biblia", Context.MODE_PRIVATE).getBoolean("dzen_noch_widget_day" + widgetID, false);
        int r_color_colorPrimary_text;
        int R_color_colorPrimary;
        if (dzenNoch) {
            R_color_colorPrimary = R.color.colorPrimary_black;
            r_color_colorPrimary_text = R.color.colorIcons;
        } else {
            R_color_colorPrimary = R.color.colorPrimary;
            r_color_colorPrimary_text = R.color.colorPrimary_text;
        }
        int month = calendar.get(Calendar.MONTH);
        int dayofmounth = calendar.get(Calendar.DAY_OF_MONTH);
        int Nedel = calendar.get(Calendar.DAY_OF_WEEK);

        Intent intent = new Intent(context, SplashActivity.class);
        String WIDGETDAY = "widget_day";
        intent.putExtra(WIDGETDAY, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(context, 500, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.fullCaliandar, pIntent);

        Intent settings = new Intent(context, Widget_config.class);
        settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        settings.setAction("android.appwidget.action.APPWIDGET_CONFIGURE");
        settings.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        PendingIntent Psettings = PendingIntent.getActivity(context, 1000 + widgetID, settings, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.settings, Psettings);

        if (dzenNoch) {
            updateViews.setInt(R.id.Layout, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorbackground_material_dark));
            updateViews.setTextColor(R.id.textSviatyia, ContextCompat.getColor(context, R.color.colorIcons));
            updateViews.setImageViewResource(R.id.imageView7, R.drawable.settings);
        } else {
            updateViews.setInt(R.id.Layout, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorIcons));
            updateViews.setTextColor(R.id.textSviatyia, ContextCompat.getColor(context, R.color.colorPrimary_text));
            updateViews.setImageViewResource(R.id.imageView7, R.drawable.settings_black);
        }
        updateViews.setTextViewText(R.id.textPost, "Пост");
        updateViews.setViewVisibility(R.id.textPost, View.GONE);
        updateViews.setTextColor(R.id.textPost, ContextCompat.getColor(context, R.color.colorPrimary_text));
        if (dzenNoch)
            updateViews.setImageViewResource(R.id.imageView4, R.drawable.fishe_whate);
        else
            updateViews.setImageViewResource(R.id.imageView4, R.drawable.fishe);
        updateViews.setViewVisibility(R.id.imageView4, View.GONE);
        updateViews.setViewVisibility(R.id.znakTipicona, View.GONE);
        updateViews.setViewVisibility(R.id.textCviatyGlavnyia, View.GONE);
        updateViews.setInt(R.id.textDenNedeli, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorDivider));
        updateViews.setInt(R.id.textChislo, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorDivider));
        updateViews.setInt(R.id.textMesiac, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorDivider));
        updateViews.setTextColor(R.id.textChislo, ContextCompat.getColor(context, R.color.colorPrimary_text));
        updateViews.setTextColor(R.id.textDenNedeli, ContextCompat.getColor(context, R.color.colorPrimary_text));
        updateViews.setTextColor(R.id.textMesiac, ContextCompat.getColor(context, R.color.colorPrimary_text));
        updateViews.setTextColor(R.id.textCviatyGlavnyia, ContextCompat.getColor(context, R_color_colorPrimary));
        updateViews.setViewVisibility(R.id.textSviatyia, View.VISIBLE);
        updateViews.setTextViewText(R.id.textChislo, Integer.toString(dayofmounth));

        if (Integer.parseInt(data.get(day).get(7)) == 1) {
            updateViews.setTextColor(R.id.textDenNedeli, ContextCompat.getColor(context, R.color.colorPrimary_text));
            updateViews.setTextColor(R.id.textChislo, ContextCompat.getColor(context, R.color.colorPrimary_text));
            updateViews.setTextColor(R.id.textMesiac, ContextCompat.getColor(context, R.color.colorPrimary_text));
            updateViews.setInt(R.id.textDenNedeli, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorBezPosta));
            updateViews.setInt(R.id.textChislo, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorBezPosta));
            updateViews.setInt(R.id.textMesiac, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorBezPosta));
        }
        if (!(Nedel == 1 || Nedel == 7)) {
            if (Integer.parseInt(data.get(day).get(7)) == 1) {
                updateViews.setInt(R.id.textDenNedeli, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorBezPosta));
                updateViews.setInt(R.id.textChislo, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorBezPosta));
                updateViews.setInt(R.id.textMesiac, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorBezPosta));
                if (Nedel == 6) {
                    updateViews.setTextViewText(R.id.textPost, "Посту няма");
                    updateViews.setInt(R.id.textPost, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorBezPosta));
                    updateViews.setViewVisibility(R.id.textPost, View.VISIBLE);
                }
            }
        }
        if (!(Nedel == 1 || Nedel == 7)) {
            if (Integer.parseInt(data.get(day).get(7)) == 2) {
                updateViews.setInt(R.id.textDenNedeli, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorPost));
                updateViews.setInt(R.id.textChislo, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorPost));
                updateViews.setInt(R.id.textMesiac, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorPost));
            }
        }
        if (!data.get(day).get(6).contains("no_sviaty")) {
            if (data.get(day).get(5).contains("1"))
                updateViews.setTextViewText(R.id.textCviatyGlavnyia, MainActivity.fromHtml("<strong>" + data.get(day).get(6) + "</strong>"));
            else
                updateViews.setTextViewText(R.id.textCviatyGlavnyia, MainActivity.fromHtml(data.get(day).get(6)));
            updateViews.setViewVisibility(R.id.textCviatyGlavnyia, View.VISIBLE);
        }

        if (data.get(day).get(6).contains("Пачатак") || data.get(day).get(6).contains("Вялікі") || data.get(day).get(6).contains("Вялікая") || data.get(day).get(6).contains("убот") || data.get(day).get(6).contains("ВЕЧАР") || data.get(day).get(6).contains("Палова")) {
            updateViews.setTextColor(R.id.textCviatyGlavnyia, ContextCompat.getColor(context, r_color_colorPrimary_text));
            updateViews.setTextViewText(R.id.textCviatyGlavnyia, MainActivity.fromHtml(data.get(day).get(6)));
            updateViews.setViewVisibility(R.id.textCviatyGlavnyia, View.VISIBLE);
        }
        String dataSviatyia = "";
        if (!data.get(day).get(4).contains("no_sviatyia")) {
            dataSviatyia = data.get(day).get(4);
            if (dzenNoch) dataSviatyia = dataSviatyia.replace("#d00505", "#f44336");
            updateViews.setTextViewText(R.id.textSviatyia, MainActivity.fromHtml(dataSviatyia));
        } else {
            updateViews.setViewVisibility(R.id.textSviatyia, View.GONE);
        }
        if (!data.get(day).get(8).equals("")) {
            updateViews.setTextViewText(R.id.textSviatyia, MainActivity.fromHtml(data.get(day).get(8) + ";<br>" + dataSviatyia));
            updateViews.setViewVisibility(R.id.textSviatyia, View.VISIBLE);
        }
        if (data.get(day).get(7).contains("2")) {
            updateViews.setInt(R.id.textDenNedeli, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorPost));
            updateViews.setInt(R.id.textChislo, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorPost));
            updateViews.setInt(R.id.textMesiac, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorPost));
            updateViews.setInt(R.id.textPost, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorPost));
            if (Nedel == 6) {
                updateViews.setViewVisibility(R.id.textPost, View.VISIBLE);
                updateViews.setViewVisibility(R.id.imageView4, View.VISIBLE);
            }
        } else if (data.get(day).get(7).contains("3")) {
            updateViews.setInt(R.id.textDenNedeli, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorStrogiPost));
            updateViews.setTextColor(R.id.textDenNedeli, ContextCompat.getColor(context, R.color.colorIcons));
            updateViews.setInt(R.id.textChislo, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorStrogiPost));
            updateViews.setTextColor(R.id.textChislo, ContextCompat.getColor(context, R.color.colorIcons));
            updateViews.setInt(R.id.textMesiac, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorStrogiPost));
            updateViews.setTextColor(R.id.textMesiac, ContextCompat.getColor(context, R.color.colorIcons));
            updateViews.setTextViewText(R.id.textPost, "Строгі пост");
            updateViews.setInt(R.id.textPost, "setBackgroundColor", ContextCompat.getColor(context, R.color.colorStrogiPost));
            updateViews.setTextColor(R.id.textPost, ContextCompat.getColor(context, R.color.colorIcons));
            updateViews.setViewVisibility(R.id.textPost, View.VISIBLE);
            updateViews.setViewVisibility(R.id.imageView4, View.VISIBLE);
            if (dzenNoch)
                updateViews.setImageViewResource(R.id.imageView4, R.drawable.fishe_red_black);
            else
                updateViews.setImageViewResource(R.id.imageView4, R.drawable.fishe_red);
        }
        if (data.get(day).get(5).contains("1") || data.get(day).get(5).contains("2") || data.get(day).get(5).contains("3")) {
            updateViews.setTextColor(R.id.textCviatyGlavnyia, ContextCompat.getColor(context, R_color_colorPrimary));
            Prazdnik(context, updateViews, R_color_colorPrimary);
        }

        switch (Integer.parseInt(data.get(day).get(12))) {
            case 1:
                if (dzenNoch)
                    updateViews.setImageViewResource(R.id.znakTipicona, R.drawable.znaki_krest_black);
                else
                    updateViews.setImageViewResource(R.id.znakTipicona, R.drawable.znaki_krest);
                updateViews.setViewVisibility(R.id.znakTipicona, View.VISIBLE);
                break;
            case 2:
                int R_drawable_znaki_krest_v_kruge = R.drawable.znaki_krest_v_kruge;
                if (dzenNoch)
                    R_drawable_znaki_krest_v_kruge = R.drawable.znaki_krest_v_kruge_black;
                updateViews.setImageViewResource(R.id.znakTipicona, R_drawable_znaki_krest_v_kruge);
                updateViews.setViewVisibility(R.id.znakTipicona, View.VISIBLE);
                break;
            case 3:
                if (dzenNoch)
                    updateViews.setImageViewResource(R.id.znakTipicona, R.drawable.znaki_krest_v_polukruge_black);
                else
                    updateViews.setImageViewResource(R.id.znakTipicona, R.drawable.znaki_krest_v_polukruge);
                updateViews.setViewVisibility(R.id.znakTipicona, View.VISIBLE);
                break;
            case 4:
                if (dzenNoch)
                    updateViews.setImageViewResource(R.id.znakTipicona, R.drawable.znaki_ttk_black_black);
                else
                    updateViews.setImageViewResource(R.id.znakTipicona, R.drawable.znaki_ttk);
                updateViews.setViewVisibility(R.id.znakTipicona, View.VISIBLE);
                break;
            case 5:
                if (dzenNoch)
                    updateViews.setImageViewResource(R.id.znakTipicona, R.drawable.znaki_ttk_whate);
                else
                    updateViews.setImageViewResource(R.id.znakTipicona, R.drawable.znaki_ttk_black);
                updateViews.setViewVisibility(R.id.znakTipicona, View.VISIBLE);
                break;
        }

        String[] NedelName = {"", "нядзеля", "панядзелак", "аўторак", "серада", "чацьвер", "пятніца", "субота"};
        updateViews.setTextViewText(R.id.textDenNedeli, NedelName[Nedel]);
        if (Nedel == 1) Prazdnik(context, updateViews, R_color_colorPrimary);

        String[] MonthName = {"СТУДЗЕНЯ", "ЛЮТАГА", "САКАВІКА", "КРАСАВІКА", "ТРАЎНЯ", "ЧЭРВЕНЯ",
                "ЛІПЕНЯ", "ЖНІЎНЯ", "ВЕРАСЬНЯ", "КАСТРЫЧНІКА", "ЛІСТАПАДА", "СЬНЕЖНЯ"};
        updateViews.setTextViewText(R.id.textMesiac, MonthName[month]);

        appWidgetManager.updateAppWidget(widgetID, updateViews);
        //appWidgetManager.updateAppWidget(new ComponentName(context.getPackageName(), Widget.class.getName()), updateViews);
    }
}
