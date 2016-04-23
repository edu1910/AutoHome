package br.com.ceducarneiro.autohome.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import br.com.ceducarneiro.autohome.AutoHomeApp;
import br.com.ceducarneiro.autohome.R;
import br.com.ceducarneiro.autohome.controller.WeatherController;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;

public class ParticleWeatherService extends IntentService {

    private static boolean running = false;
    private static ParticleDevice device = null;

    public ParticleWeatherService() {
        super("ParticleWeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        running = true;

        Log.d(AutoHomeApp.TAG, "ParticleWeatherService - running");

        try {
            if (!ParticleCloudSDK.getCloud().isLoggedIn())
                ParticleCloudSDK.getCloud().logIn(getString(R.string.PARTICLE_USER),
                        getString(R.string.PARTICLE_PASS));

            if (device == null)
                device = ParticleCloudSDK.getCloud().getDevice(getString(R.string.PARTICLE_DEVICE));

            WeatherController.getInstance().setLastHumitidy((float) device.getDoubleVariable("humi"));
            WeatherController.getInstance().setLastTemperature((float) device.getDoubleVariable("temp"));
            WeatherController.getInstance().setLampOn(device.getIntVariable("lamp") == 1);

            WeatherController.getInstance().updateWidgets(this);
        } catch (Exception ex) {
            device = null;
            Log.e(AutoHomeApp.TAG, "ParticleWeatherService", ex);
        }

        Log.d(AutoHomeApp.TAG, "ParticleWeatherService - finished");

        running = false;
    }

    public static synchronized boolean isRunning() {
        return running;
    }

}
