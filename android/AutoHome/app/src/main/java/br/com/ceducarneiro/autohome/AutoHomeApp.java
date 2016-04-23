package br.com.ceducarneiro.autohome;

import android.app.Application;

import com.onesignal.OneSignal;

import io.particle.android.sdk.cloud.ParticleCloudSDK;

public class AutoHomeApp extends Application {

    public static final String TAG = "AHOME";

    private static AutoHomeApp instance;

    public AutoHomeApp() {
        instance = this;
    }

    public synchronized static AutoHomeApp getInstance() {
        if (instance == null) {
            new AutoHomeApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ParticleCloudSDK.init(this);
        OneSignal.startInit(this).init();
    }
}
