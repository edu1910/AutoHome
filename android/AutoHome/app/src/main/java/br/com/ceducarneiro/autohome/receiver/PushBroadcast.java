package br.com.ceducarneiro.autohome.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import br.com.ceducarneiro.autohome.AutoHomeApp;
import br.com.ceducarneiro.autohome.controller.WeatherController;
import br.com.ceducarneiro.autohome.preference.AppPreferencesManager;

public class PushBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle dataBundle = intent.getBundleExtra("data");

        Log.d(AutoHomeApp.TAG, "PushBroadcast - running");

        try {
            JSONObject customJSON = new JSONObject(dataBundle.getString("custom"));
            if (customJSON.has("a")) {
                JSONObject additionalData = customJSON.getJSONObject("a");
                if (additionalData.has("lamp") && additionalData.has("time")) {
                    int lamp = additionalData.getInt("lamp");
                    long time = additionalData.getLong("time");

                    AppPreferencesManager appPreferencesManager = AppPreferencesManager.getInstance();

                    if (appPreferencesManager.getLastLampTime() < time) {
                        appPreferencesManager.setLampStatus(lamp == 1);
                        appPreferencesManager.setLastLampTime(time);
                        WeatherController.getInstance().updateWidgets(context);
                    }
                }
            }
        } catch (Throwable ex) {
            Log.e(AutoHomeApp.TAG, "PushBroadcast", ex);
        }

        Log.d(AutoHomeApp.TAG, "PushBroadcast - finished");
    }
}
