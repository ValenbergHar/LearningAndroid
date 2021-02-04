package by.carkva_gazeta.malitounik;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by oleg on 21.11.16.
 */

public class ReceiverBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && (Objects.requireNonNull(intent.getAction()).equals("android.intent.action.BOOT_COMPLETED") || intent.getAction().equals("android.intent.action.QUICKBOOT_POWERON") || intent.getAction().equals("com.htc.intent.action.QUICKBOOT_POWERON"))) {
            GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
            Intent i = new Intent(context, ReceiverUpdate.class);
            i.setAction("UPDATE");
            PendingIntent pServise = PendingIntent.getBroadcast(context, 10, i, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (c.getTimeInMillis() > mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)))
                c.add(Calendar.DATE, 1);
            Objects.requireNonNull(am).setRepeating(AlarmManager.RTC_WAKEUP, mkTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)), 86400000L, pServise);
            context.sendBroadcast(i);
        }
    }

    private long mkTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 10, 0, 0);
        return calendar.getTimeInMillis();
    }
}
