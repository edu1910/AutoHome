package br.com.ceducarneiro.autohome.preference;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.ceducarneiro.autohome.AutoHomeApp;

public class AppPreferencesManager {

    public static final float INVALID_VALUE = -999;

    private final String PREFERENCES_NODE = "preferences_app";

    private final String PREFERENCE_TEMPERATURE = "preference_temp";
    private final String PREFERENCE_HUMIDITY = "preference_humi";
    private final String PREFERENCE_LAMP_STATUS = "preference_lamp";
    private final String PREFERENCE_LAST_LAMP_TIME = "preference_last_lamp";

    private static AppPreferencesManager instance;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private AppPreferencesManager() {
        prefs = AutoHomeApp.getInstance().getSharedPreferences(PREFERENCES_NODE, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public static synchronized AppPreferencesManager getInstance() {
        if (instance == null) {
            instance = new AppPreferencesManager();
        }
        return instance;
    }

    public float getLastTemperature() {
        return prefs.getFloat(PREFERENCE_TEMPERATURE, INVALID_VALUE);
    }

    public void setLastTemperature(float value) {
        editor.putFloat(PREFERENCE_TEMPERATURE, value);
        editor.commit();
    }

    public float getLastHumidity() {
        return prefs.getFloat(PREFERENCE_HUMIDITY, INVALID_VALUE);
    }

    public void setLastHumidity(float value) {
        editor.putFloat(PREFERENCE_HUMIDITY, value);
        editor.commit();
    }

    public boolean getLampStatus() {
        return prefs.getBoolean(PREFERENCE_LAMP_STATUS, false);
    }

    public void setLampStatus(boolean value) {
        editor.putBoolean(PREFERENCE_LAMP_STATUS, value);
        editor.commit();
    }

    public long getLastLampTime() {
        return prefs.getLong(PREFERENCE_LAST_LAMP_TIME, 0l);
    }

    public void setLastLampTime(long value) {
        editor.putLong(PREFERENCE_LAST_LAMP_TIME, value);
        editor.commit();
    }

    public void clear() {
        editor.remove(PREFERENCE_TEMPERATURE);
        editor.remove(PREFERENCE_HUMIDITY);
        editor.remove(PREFERENCE_LAMP_STATUS);
        editor.remove(PREFERENCE_LAST_LAMP_TIME);
        editor.commit();
    }
}
