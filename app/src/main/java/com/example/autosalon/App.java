package com.example.autosalon;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        com.yandex.mapkit.MapKitFactory.setApiKey("28d1e702-ebb4-4d88-9e01-f5785c3e9907");
        com.yandex.mapkit.MapKitFactory.initialize(this);
    }
}
