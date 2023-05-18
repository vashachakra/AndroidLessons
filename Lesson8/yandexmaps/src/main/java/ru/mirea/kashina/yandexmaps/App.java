package ru.mirea.kashina.yandexmaps;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class App  extends Application {
    private final String MAPKIT_API_KEY = "bf69f1aa-c134-4d5d-bc3e-2a450ef4ef02";
    @Override
    public void onCreate() {
        super.onCreate();
// Set the api key before calling initialize on MapKitFactory.
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}
