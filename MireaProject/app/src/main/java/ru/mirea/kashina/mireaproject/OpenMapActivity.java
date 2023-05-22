package ru.mirea.kashina.mireaproject;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ru.mirea.kashina.mireaproject.databinding.ActivityOpenMap2Binding;

public class OpenMapActivity extends AppCompatActivity {

    private ActivityOpenMap2Binding binding;
    private final String MAPKIT_API_KEY = "bf69f1aa-c134-4d5d-bc3e-2a450ef4ef02";
    private final Point ROUTE_START_LOCATION = new Point(55.584777, 37.903695);
    private final Point ROUTE_END_LOCATION = new Point(55.769008, 37.644612);
    private final Point SCREEN_CENTER = new Point(
            (ROUTE_START_LOCATION.getLatitude() + ROUTE_END_LOCATION.getLatitude()) / 2,
            (ROUTE_START_LOCATION.getLongitude() + ROUTE_END_LOCATION.getLongitude()) /
                    2);
    private MapView mapView;
    private int[] colors = {0xFFFF0000, 0xFF00FF00, 0x00FFBBBB, 0xFF0000FF};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_map2);
        mapView = findViewById(R.id.mapview);
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
}