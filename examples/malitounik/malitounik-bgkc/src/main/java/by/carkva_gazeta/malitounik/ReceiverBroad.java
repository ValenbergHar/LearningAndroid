package by.carkva_gazeta.malitounik;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by oleg on 7.6.16
 */
public class ReceiverBroad extends BroadcastReceiver {

    private String channelId = "2000";
    private int id = 205;
    private boolean sabytieSet = false;

    @Override
    public void onReceive(Context ctx, Intent intent) {
        GregorianCalendar g = (GregorianCalendar) Calendar.getInstance();
        int dayofyear = g.get(Calendar.DAY_OF_YEAR);
        int year = g.get(Calendar.YEAR);
        if (intent != null) {
            boolean sabytie = intent.getBooleanExtra("sabytieSet", false);
            if (sabytie) {
                channelId = "3000";
                id = Integer.parseInt(Objects.requireNonNull(intent.getExtras()).getString("dataString", dayofyear + String.valueOf(g.get(Calendar.MONTH)) + g.get(Calendar.HOUR_OF_DAY) + g.get(Calendar.MINUTE)));
                sabytieSet = true;
            }
        }
        sendNotif(ctx, Objects.requireNonNull(intent).getAction(), intent.getStringExtra("extra"), intent.getIntExtra("dayofyear", dayofyear), intent.getIntExtra("year", year));
    }

    private void sendNotif(Context context, String Sviata, String Name, int dayofyear, int year) {
        Intent notificationIntent = new Intent(context, SplashActivity.class);
        //notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //notificationIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("data", dayofyear);
        notificationIntent.putExtra("year", year);
        notificationIntent.putExtra("sabytie", true);
        if (sabytieSet) {
            notificationIntent.putExtra("sabytieView", true);
            notificationIntent.putExtra("sabytieTitle", Sviata);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 15, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        SharedPreferences chin = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        Uri uri;
        int bigIcon = R.drawable.calendar_full;
        String name = context.getResources().getString(R.string.sabytie);
        if (!sabytieSet) {
            bigIcon = R.drawable.krest;
            name = context.getResources().getString(R.string.SVIATY);
        }
        long[] vibrate = new long[]{0, 1000, 700, 1000, 700, 1000};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.setDescription(name);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setLightColor(ContextCompat.getColor(context, R.color.colorPrimary));
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att);
            channel.enableVibration(true);
            channel.setVibrationPattern(vibrate);
            channel.enableLights(true);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            Notification.Builder builder = new Notification.Builder(context, channelId);
            builder.setContentIntent(contentIntent)
                    .setWhen(System.currentTimeMillis())
                    .setShowWhen(true)
                    .setSmallIcon(R.drawable.krest)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), bigIcon))
                    .setAutoCancel(true)
                    .setContentTitle(Name)
                    .setContentText(Sviata);
            Notification notification = builder.build();
            notificationManager.notify(id, notification);
            notificationManager.deleteNotificationChannel("by.carkva-gazeta");
        } else {
            int sound = chin.getInt("soundnotification", 0);
            if (!sabytieSet) sound = 0;
            switch (sound) {
                case 1:
                    uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                    break;
                case 2:
                    uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    break;
                case 3:
                    uri = Uri.parse(chin.getString("soundURI", ""));
                    break;
                default:
                    uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    break;
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Сьвяты і Падзеі");
            builder.setContentIntent(contentIntent)
                    .setWhen(System.currentTimeMillis())
                    .setShowWhen(true)
                    .setSmallIcon(R.drawable.krest)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), bigIcon))
                    .setAutoCancel(true)
                    .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                    .setLights(ContextCompat.getColor(context, R.color.colorPrimary), 1000, 1000)
                    .setContentTitle(Name)
                    .setContentText(Sviata);
            if (chin.getInt("guk", 1) == 1) builder.setSound(uri);
            if (chin.getInt("vibra", 1) == 1) builder.setVibrate(vibrate);
            Notification notification = builder.build();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(id, notification);
        }
    }
}
