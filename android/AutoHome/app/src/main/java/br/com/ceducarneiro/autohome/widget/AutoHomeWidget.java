package br.com.ceducarneiro.autohome.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import br.com.ceducarneiro.autohome.controller.WeatherController;
import br.com.ceducarneiro.autohome.preference.AppPreferencesManager;
import br.com.ceducarneiro.autohome.service.ParticleLampService;
import br.com.ceducarneiro.autohome.service.ParticleWeatherAlarm;

public class AutoHomeWidget extends AppWidgetProvider {

    public static final String LAMP_CLICK_ACTION = "br.com.ceducarneiro.autohome.LAMP_CLICK_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(LAMP_CLICK_ACTION)) {
            if (!ParticleLampService.isRunning()) {
                Intent it = new Intent(context, ParticleLampService.class);
                context.startService(it);
            }
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        AppPreferencesManager.getInstance().clear();
        ParticleWeatherAlarm.scheduleAlarm(context);
    }

    @Override
    public void onDisabled(Context context) {
        AppPreferencesManager.getInstance().clear();
        ParticleWeatherAlarm.cancelAlarm(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        int minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int minHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);

        appWidgetManager.updateAppWidget(appWidgetId,
                WeatherController.getInstance().getRemoteViews(context, minWidth, minHeight));
    }

}

