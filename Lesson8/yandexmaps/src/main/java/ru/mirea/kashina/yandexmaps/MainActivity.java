package ru.mirea.kashina.yandexmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.SearchManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.widget.EditText;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import ru.mirea.kashina.yandexmaps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener {
    private final String MAPKIT_API_KEY = "bf69f1aa-c134-4d5d-bc3e-2a450ef4ef02";

    private final Point TARGET_LOCATION = new Point(59.945933, 97.820);
    private MapView mapView;
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 10;
    private UserLocationLayer userLocationLayer;
    private final int RESULT_NUMBER_LIMIT = 5;
    private EditText searchEdit;
    private SearchManager searchManager;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        SearchFactory.initialize(this);
        DirectionsFactory.initialize(this);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.getMap().move(new CameraPosition(TARGET_LOCATION, 0.0f, 0.0f, 0.0f), new Animation(Animation.Type.SMOOTH, 0), null);
        mapView.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f,
                        0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        loadUserLocationLayer();
        requestLocationPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float) (mapView.getWidth() * 0.5),
                        (float) (mapView.getHeight() * 0.5)),
                new PointF((float) (mapView.getWidth() * 0.5),
                        (float) (mapView.getHeight() * 0.83)));
// При определении направления движения устанавливается следующая иконка
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, android.R.drawable.arrow_up_float));
// При получении координат местоположения устанавливается следующая иконка
        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();
        pinIcon.setIcon(
                "pin",
                ImageProvider.fromResource(this, R.drawable.search),
                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
                        .setRotationType(RotationType.ROTATE)

                        .setZIndex(1f)
                        .setScale(0.5f)

        );
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {
    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView,

                                @NonNull ObjectEvent objectEvent) {

    }

    private void loadUserLocationLayer() {
        MapKit mapKit = MapKitFactory.getInstance();
        mapKit.resetLocationManagerToDefault();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                "android.permission.ACCESS_FINE_LOCATION")
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{"android.permission.ACCESS_FINE_LOCATION"},
                    PERMISSIONS_REQUEST_FINE_LOCATION);
        }
    }
}
/*public class MainActivity extends AppCompatActivity {
     /**
      * Замените "your_api_key" валидным API-ключом.
      * Ключ можно получить на сайте https://developer.tech.yandex.ru/
      */
/*
     private final String MAPKIT_API_KEY = "bf69f1aa-c134-4d5d-bc3e-2a450ef4ef02";
     private final Point TARGET_LOCATION = new Point(59.945933, 30.320045);

     private MapView mapView;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         */
/**
          * Задайте API-ключ перед инициализацией MapKitFactory.
          * Рекомендуется устанавливать ключ в методе Application.onCreate(),
          * но в данном примере он устанавливается в Activity.
          *//*

         MapKitFactory.setApiKey(MAPKIT_API_KEY);
         */
/**
          * Инициализация библиотеки для загрузки необходимых нативных библиотек.
          * Рекомендуется инициализировать библиотеку MapKit в методе Activity.onCreate()
          * Инициализация в методе Application.onCreate() может привести к лишним вызовам и увеличенному использованию батареи.
          *//*

         MapKitFactory.initialize(this);
         // Создание MapView.
         setContentView(R.layout.activity_main);
         super.onCreate(savedInstanceState);
         mapView = (MapView)findViewById(R.id.mapview);

         // Перемещение камеры в центр Санкт-Петербурга.
         mapView.getMap().move(
                 new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                 new Animation(Animation.Type.SMOOTH, 5),
                 null);
     }

     @Override
     protected void onStop() {
         // Вызов onStop нужно передавать инстансам MapView и MapKit.
         mapView.onStop();
         MapKitFactory.getInstance().onStop();
         super.onStop();
     }

     @Override
     protected void onStart() {
         // Вызов onStart нужно передавать инстансам MapView и MapKit.
         super.onStart();
         MapKitFactory.getInstance().onStart();
         mapView.onStart();
     }
 }*/
