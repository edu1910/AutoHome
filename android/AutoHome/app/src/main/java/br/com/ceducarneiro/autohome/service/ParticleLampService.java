package br.com.ceducarneiro.autohome.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import br.com.ceducarneiro.autohome.AutoHomeApp;
import br.com.ceducarneiro.autohome.R;
import br.com.ceducarneiro.autohome.controller.WeatherController;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;

public class ParticleLampService extends IntentService {

    private static boolean running = false;
    private static ParticleDevice device = null;

    public ParticleLampService() {
        super("ParticleLampService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        running = true;

        Log.d(AutoHomeApp.TAG, "ParticleLampService - running");

        try {
            if (!ParticleCloudSDK.getCloud().isLoggedIn())
                ParticleCloudSDK.getCloud().logIn(getString(R.string.PARTICLE_USER),
                        getString(R.string.PARTICLE_PASS));

            if (device == null)
                device = ParticleCloudSDK.getCloud().getDevice(getString(R.string.PARTICLE_DEVICE));

            WeatherController.getInstance().setLampOn(device.callFunction("lampToggle") == 1);
            WeatherController.getInstance().updateWidgets(this);
        } catch (Exception ex) {
            device = null;
            Log.e(AutoHomeApp.TAG, "ParticleLampService", ex);
        }

        Log.d(AutoHomeApp.TAG, "ParticleLampService - finished");

        running = false;
    }

    public static synchronized boolean isRunning() {
        return running;
    }

}
