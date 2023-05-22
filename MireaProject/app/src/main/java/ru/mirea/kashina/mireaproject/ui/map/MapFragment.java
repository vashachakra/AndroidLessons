package ru.mirea.kashina.mireaproject.ui.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

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
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.kashina.mireaproject.OpenMapActivity;
import ru.mirea.kashina.mireaproject.R;
import ru.mirea.kashina.mireaproject.databinding.FragmentMapBinding;

public class MapFragment extends Fragment implements View.OnClickListener{
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
        private String mParam1;
        private String mParam2;
        public MapFragment() {
            // Required empty public constructor
        }

        public static MapFragment newInstance(String param1, String param2) {
            MapFragment fragment = new MapFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }
        @Override
        public void onStop(){
            super.onStop();
        }
        @Override
        public void onStart(){
            super.onStart();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_map, container, false);
            Button openMapButton = root.findViewById(R.id.button2);
            openMapButton.setOnClickListener(this);
            return root;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), OpenMapActivity.class);
            startActivity(intent);
        }
    }


/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
        }

        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(requireContext());
        DirectionsFactory.initialize(requireContext());

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = rootView.findViewById(R.id.mapview);

        mapView.getMap().move(new CameraPosition(SCREEN_CENTER, 10, 0, 0));
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        mapObjects = mapView.getMap().getMapObjects().addCollection();
        submitRequest();

        mapView.getMap().addTapListener(this);
        mapView.getMap().addInputListener(this);
        myMark();
        return rootView;
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
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    public void onStart() {
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
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onObjectTap(GeoObjectTapEvent geoObjectTapEvent) {
        final GeoObjectSelectionMetadata selectionMetadata = geoObjectTapEvent.getGeoObject()
                .getMetadataContainer()
                .getItem(GeoObjectSelectionMetadata.class);


        String text = geoObjectTapEvent.getGeoObject().getName();
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show();
        if (selectionMetadata != null) {
            mapView.getMap().selectGeoObject(selectionMetadata.getId(), selectionMetadata.getLayerId());
        }
        return selectionMetadata != null;
    }
    @Override
    public void onMapTap(@NonNull Map map, @NonNull Point point) {
        mapView.getMap().deselectGeoObject();
    }

    @Override
    public void onMapLongTap(@NonNull Map map, @NonNull Point point) {

    }
    private void myMark() {
        newMark("Парк Горького", "Классный парк", "г. Москва, м. Парк Культуры", new Point(55.729219, 37.600891));
        newMark("ВДНХ", "Здесь тоже здорово", "г. Москва, м. ВДНХ", new Point(55.831388, 37.629277));
        newMark("Эльбрус", "Сюда очень хочется поехать этим летом", "гора Эльбрус, Кабардино-Балкарская Республика", new Point(43.345068, 42.465897));
    }

    public void newMark(String name, String description, String address, Point place){
        PlacemarkMapObject mark = mapObjects.addPlacemark(place, ImageProvider.fromResource(requireContext(), R.drawable.ic_launcher_map_foreground));
        mark.addTapListener((mapObject, point) -> {
            Toast.makeText(requireContext(), (mapObject.getUserData().toString()), Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}*/
