package ru.mirea.kashina.yandexdriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.directions.driving.VehicleOptions;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.kashina.yandexdriver.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements DrivingSession.DrivingRouteListener {

        private ActivityMainBinding binding;
        private final String MAPKIT_API_KEY = "bf69f1aa-c134-4d5d-bc3e-2a450ef4ef02";
        private final Point ROUTE_START_LOCATION = new Point(55.584777, 37.903695);
        private final Point ROUTE_END_LOCATION = new Point(55.769008, 37.644612);
        private final Point SCREEN_CENTER = new Point(
                (ROUTE_START_LOCATION.getLatitude() + ROUTE_END_LOCATION.getLatitude()) / 2,
                (ROUTE_START_LOCATION.getLongitude() + ROUTE_END_LOCATION.getLongitude()) /
                        2);
        private MapView mapView;
        private MapObjectCollection mapObjects;
        private DrivingRouter drivingRouter;
        private DrivingSession drivingSession;
        private int[] colors = {0xFFFF0000, 0xFF00FF00, 0x00FFBBBB, 0xFF0000FF};
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                MapKitFactory.setApiKey(MAPKIT_API_KEY);
                MapKitFactory.initialize(this);
                SearchFactory.initialize(this);
                DirectionsFactory.initialize(this);
                binding = ActivityMainBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());
                mapView = (MapView) findViewById(R.id.mapview);
                mapView.getMap().setRotateGesturesEnabled(false);
                mapView.getMap().move(new CameraPosition(
                        SCREEN_CENTER, 10, 0, 0));
                drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
                mapObjects = mapView.getMap().getMapObjects().addCollection();
                submitRequest();
                PlacemarkMapObject marker = mapView.getMap().getMapObjects().addPlacemark(new
                        Point(55.751574, 37.573856), ImageProvider.fromResource(this, R.drawable.icon_foreground));
                marker.addTapListener(new MapObjectTapListener() {
                        @Override
                        public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point
                                point) {
                                Toast.makeText(getApplication(),"Marker click",
                                        Toast.LENGTH_SHORT).show();
                                return false;
                        }
                });
        }
        private void submitRequest() {
                DrivingOptions drivingOptions = new DrivingOptions();
                VehicleOptions vehicleOptions = new VehicleOptions();
                drivingOptions.setRoutesCount(4);
                ArrayList<RequestPoint> requestPoints = new ArrayList<>();
                requestPoints.add(new RequestPoint(ROUTE_START_LOCATION,
                        RequestPointType.WAYPOINT,
                        null));
                requestPoints.add(new RequestPoint(ROUTE_END_LOCATION,
                        RequestPointType.WAYPOINT,
                        null));
                drivingSession = drivingRouter.requestRoutes(requestPoints, drivingOptions,
                        vehicleOptions, this);
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
        @Override
        public void onDrivingRoutes(@NonNull List<DrivingRoute> list) {
                int color;
                for (int i = 0; i < list.size(); i++) {
                        color = colors[i];
                        mapObjects.addPolyline(list.get(i).getGeometry()).setStrokeColor(color);
                }
        }
        @Override
        public void onDrivingRoutesError(@NonNull Error error) {
                String errorMessage = getString(R.string.unknown_error_message);
                if (error instanceof RemoteError) {
                        errorMessage = getString(R.string.remote_error_message);
                } else if (error instanceof NetworkError) {
                        errorMessage = getString(R.string.network_error_message);
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
}