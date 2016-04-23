package br.com.ceducarneiro.autohome.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ParticleWeatherAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        runService(context);
    }

    public static void runService(Context context) {
        if (!ParticleWeatherService.isRunning()) {
            Intent service = new Intent(context, ParticleWeatherService.class);
            context.startService(service);
        }
    }

    public static void scheduleAlarm(Context context) {
        Intent intent = new Intent(context, ParticleWeatherAlarm.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long firstMillis = 0;
        int intervalMillis = 5 * 60 * 1000;
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);
    }

    public static void cancelAlarm(Context context) {
        Intent intent = new Intent(context, ParticleWeatherAlarm.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
}
