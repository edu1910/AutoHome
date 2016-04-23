package br.com.ceducarneiro.autohome.controller;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RemoteViews;

import br.com.ceducarneiro.autohome.R;
import br.com.ceducarneiro.autohome.widget.AutoHomeWidget;
import br.com.ceducarneiro.autohome.preference.AppPreferencesManager;

public class WeatherController {

    private static WeatherController instance;

    private WeatherController() {}

    public static synchronized WeatherController getInstance() {
        if (instance == null)
            instance = new WeatherController();

        return instance;
    }

    public Float getLastTemperature() {
        float temp = AppPreferencesManager.getInstance().getLastTemperature();
        return temp != AppPreferencesManager.getInstance().INVALID_VALUE ? temp : null;
    }

    public void setLastTemperature(float lastTemp) {
        AppPreferencesManager.getInstance().setLastTemperature(lastTemp);
    }

    public Float getLastHumitidy() {
        float humi = AppPreferencesManager.getInstance().getLastHumidity();
        return humi != AppPreferencesManager.getInstance().INVALID_VALUE ? humi : null;
    }

    public void setLastHumitidy(float lastHumitidy) {
        AppPreferencesManager.getInstance().setLastHumidity(lastHumitidy);
    }

    public Boolean getLampOn() {
        return AppPreferencesManager.getInstance().getLampStatus();
    }

    public void setLampOn(Boolean lampOn) {
        AppPreferencesManager.getInstance().setLampStatus(lampOn);
    }

    public RemoteViews getRemoteViews(Context context, int minWidth, int minHeight) {
        RemoteViews views = getLastTemperature() == null ? new RemoteViews(context.getPackageName(),
                R.layout.wheather_widget_placeholder) : null;

        if (views == null) {
            views = new RemoteViews(context.getPackageName(), R.layout.wheather_widget);
            int columns = getCellsForSize(minWidth);
            views.setViewVisibility(R.id.layout_extra, columns >= 3 ? View.VISIBLE : View.GONE);

            views.setTextViewText(R.id.appwidget_temp, Html.fromHtml(String.format("%.1fºC", getLastTemperature() != null ? getLastTemperature() : 0)));
            views.setTextViewText(R.id.appwidget_humi, Html.fromHtml(String.format("%.1f%%", getLastHumitidy() != null ? getLastHumitidy() : 0)));
            views.setTextViewCompoundDrawables(R.id.appwidget_lamp, 0, getLampOn() != null && getLampOn() ? R.drawable.ic_lightbulb_white : R.drawable.ic_lightbulb_outline_white, 0, 0);
            views.setTextViewText(R.id.appwidget_lamp, getLampOn() != null && getLampOn() ? "ON" : "OFF");

            Intent intent = new Intent(context, AutoHomeWidget.class);
            intent.setAction(AutoHomeWidget.LAMP_CLICK_ACTION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget_lamp, pendingIntent);
        }

        return views;
    }

    public void updateWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        ComponentName widget = new ComponentName(context, AutoHomeWidget.class);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(widget);

        if (widgetIds != null) {
            for (int widgetId : widgetIds) {
                Bundle options = appWidgetManager.getAppWidgetOptions(widgetId);

                int minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
                int minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);

                appWidgetManager.updateAppWidget(widgetId, getRemoteViews(context, minWidth, minHeight));
            }
        }
    }

    private int getCellsForSize(int size) {
        int n = 2;
        while (70 * n - 30 < size) {
            ++n;
        }
        return n - 1;
    }

}
