package app.mycycle;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by carloconnor on 01/03/17.
 */

public class MainFragment1 extends Fragment implements CustomLocationProvider.CustomLocationListener, StopwatchFragment.MapFragmentListener {
    private final static String LOG = "LOG";

    StopwatchFragment stopwatchFragment;
    MapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        stopwatchFragment = new StopwatchFragment();
        SpeedGraphFragment speedGraphFragment = new SpeedGraphFragment();
        mapFragment = new MapFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_1, stopwatchFragment, "stopwatchFragment");
        fragmentTransaction.add(R.id.activity_main_2, mapFragment, "mapFragment");
        fragmentTransaction.add(R.id.activity_main_3, speedGraphFragment, "speedGraphFragment");
        fragmentTransaction.commit();

        return view;
    }

    @Override
    public void updateLocation(Location location) {
        Log.e("GPS", "Location received! Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
        stopwatchFragment.updateStopwatch(location);
    }

    @Override
    public void updateMap(MyPolyline polyline) {
        mapFragment.drawRoute(polyline);
    }

    @Override
    public void clearRoute() {
        mapFragment.clearRoute();
    }
}
