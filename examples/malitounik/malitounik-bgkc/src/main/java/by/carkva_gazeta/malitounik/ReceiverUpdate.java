package by.carkva_gazeta.malitounik;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class ReceiverUpdate extends BroadcastReceiver {

    private final String UPDATE_ALL_WIDGETS = "update_all_widgets";
    private final String RESET_MAIN = "reset_main";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Task();
    }

    private void Task() {
        new Thread(() -> {
            SharedPreferences chin = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            // Установка будильников
            GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (chin.getBoolean("WIDGET_MUN_ENABLED", false)) {
                int mun_ak = c.get(Calendar.MONTH);
                int year_ak = c.get(Calendar.YEAR);
                boolean reset_wid = false;
                Intent intent = new Intent(context, Widget_mun.class);
                intent.setAction(UPDATE_ALL_WIDGETS);
                PendingIntent pIntent = PendingIntent.getBroadcast(context, 51, intent, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
                } else {
                    Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
                }
                ComponentName thisAppWidget = new ComponentName(context.getPackageName(), context.getPackageName() + ".Widget_mun");
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] ids = appWidgetManager.getAppWidgetIds(thisAppWidget);
                for (int i : ids) {
                    int munS = chin.getInt("WIDGET" + i, mun_ak);
                    int yearS = chin.getInt("WIDGETYEAR" + i, year_ak);
                    if (!(munS == mun_ak && yearS == year_ak)) reset_wid = true;
                }
                if (reset_wid) {
                    Intent reset = new Intent(context, Widget_mun.class);
                    reset.setAction(RESET_MAIN);
                    PendingIntent pReset = PendingIntent.getBroadcast(context, 258, reset, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120000L, pReset);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120000L, pReset);
                    } else {
                        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120000L, pReset);
                    }
                }
            }
            if (chin.getBoolean("WIDGET_ENABLED", false)) {
                Intent intent = new Intent(context, Widget.class);
                intent.setAction(UPDATE_ALL_WIDGETS);
                PendingIntent pIntent = PendingIntent.getBroadcast(context, 50, intent, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
                } else {
                    Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), pIntent);
                }
            }
            PendingIntent pIntent;
            try {
                File dir = new File(context.getFilesDir() + "/Sabytie");
                for (String s : Objects.requireNonNull(dir.list())) {
                    File file = new File(context.getFilesDir() + "/Sabytie/" + s);
                    if (file.exists()) {
                        FileReader inputStream = new FileReader(file);
                        BufferedReader reader = new BufferedReader(inputStream);
                        String line;
                        while ((line = reader.readLine()) != null && !line.equals("")) {
                            String[] t1 = line.split(" ");
                            if (!t1[5].equals("-1")) {
                                if (t1[10].equals("0")) {
                                    switch (Integer.parseInt(t1[9])) {
                                        case 1:
                                            long timerepit = Long.parseLong(t1[3]);
                                            while (true) {
                                                if (timerepit > c.getTimeInMillis()) {
                                                    Intent intent = createIntentSabytie(t1[0].replace("_", " ") + " " + t1[1] + " у " + t1[2], t1[1]);
                                                    pIntent = PendingIntent.getBroadcast(context, (int) timerepit, intent, 0);
                                                    Objects.requireNonNull(am).setRepeating(AlarmManager.RTC_WAKEUP, timerepit, 86400000L, pIntent);
                                                    break;
                                                }
                                                timerepit = timerepit + (86400000L);
                                            }
                                            break;
                                        case 4:
                                            timerepit = Long.parseLong(t1[3]);
                                            while (true) {
                                                if (timerepit > c.getTimeInMillis()) {
                                                    Intent intent = createIntentSabytie(t1[0].replace("_", " ") + " " + t1[1] + " у " + t1[2], t1[1]);
                                                    pIntent = PendingIntent.getBroadcast(context, (int) timerepit, intent, 0);
                                                    Objects.requireNonNull(am).setRepeating(AlarmManager.RTC_WAKEUP, timerepit, 604800000L, pIntent);
                                                    break;
                                                }
                                                timerepit = timerepit + 604800000L;
                                            }
                                            break;
                                        case 5:
                                            timerepit = Long.parseLong(t1[3]);
                                            while (true) {
                                                if (timerepit > c.getTimeInMillis()) {
                                                    Intent intent = createIntentSabytie(t1[0].replace("_", " ") + " " + t1[1] + " у " + t1[2], t1[1]);
                                                    pIntent = PendingIntent.getBroadcast(context, (int) timerepit, intent, 0);
                                                    Objects.requireNonNull(am).setRepeating(AlarmManager.RTC_WAKEUP, timerepit, 1209600000L, pIntent);
                                                    break;
                                                }
                                                timerepit = timerepit + 1209600000L;
                                            }
                                            break;
                                        case 6:
                                            timerepit = Long.parseLong(t1[3]);
                                            while (true) {
                                                if (timerepit > c.getTimeInMillis()) {
                                                    Intent intent = createIntentSabytie(t1[0].replace("_", " ") + " " + t1[1] + " у " + t1[2], t1[1]);
                                                    pIntent = PendingIntent.getBroadcast(context, (int) timerepit, intent, 0);
                                                    Objects.requireNonNull(am).setRepeating(AlarmManager.RTC_WAKEUP, timerepit, 2419200000L, pIntent);
                                                    break;
                                                }
                                                timerepit = timerepit + 2419200000L;
                                            }
                                            break;
                                        default:
                                            if (Long.parseLong(t1[3]) > c.getTimeInMillis()) {
                                                Intent intent = createIntentSabytie(t1[0].replace("_", " ") + " " + t1[1] + " у " + t1[2], t1[1]);
                                                pIntent = PendingIntent.getBroadcast(context, (int) Long.valueOf(t1[3]).longValue(), intent, 0);
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, Long.parseLong(t1[3]), pIntent);
                                                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                    Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, Long.parseLong(t1[3]), pIntent);
                                                } else {
                                                    Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, Long.parseLong(t1[3]), pIntent);
                                                }
                                            }
                                            break;
                                    }
                                } else {
                                    if (Long.parseLong(t1[3]) > c.getTimeInMillis()) {
                                        Intent intent = createIntentSabytie(t1[0].replace("_", " ") + " " + t1[1] + " у " + t1[2], t1[1]);
                                        pIntent = PendingIntent.getBroadcast(context, (int) Long.valueOf(t1[3]).longValue(), intent, 0);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            Objects.requireNonNull(am).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, Long.parseLong(t1[3]), pIntent);
                                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            Objects.requireNonNull(am).setExact(AlarmManager.RTC_WAKEUP, Long.parseLong(t1[3]), pIntent);
                                        } else {
                                            Objects.requireNonNull(am).set(AlarmManager.RTC_WAKEUP, Long.parseLong(t1[3]), pIntent);
                                        }
                                    }
                                }
                            }
                        }
                        inputStream.close();
                    }
                }
            } catch (Exception ignored) {
            }
            int notify = chin.getInt("notification", 2);
            SettingsActivity.setNotifications(context, notify);
        }).start();
    }

    private long mkTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    private Intent createIntentSabytie(String action, String data) {
        Intent intent = new Intent(context, ReceiverBroad.class);
        intent.setAction(action);
        intent.putExtra("sabytieSet", true);
        intent.putExtra("extra", "Падзея");
        String[] dateN = data.split("[.]");
        GregorianCalendar g = new GregorianCalendar(Integer.parseInt(dateN[2]), Integer.parseInt(dateN[1]) - 1, Integer.parseInt(dateN[0]), 0, 0, 0);
        intent.putExtra("data", g.get(Calendar.DAY_OF_YEAR));
        intent.putExtra("year", g.get(Calendar.YEAR));
        return intent;
    }
}
