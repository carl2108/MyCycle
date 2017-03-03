package app.mycycle;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by carloconnor on 15/02/17.
 */

public class MainActivity extends AppCompatActivity implements CustomLocationProvider.CustomLocationListener, StopwatchFragment.MapFragmentListener {
    private final static String LOG = "LOG";

    StopwatchFragment stopwatchFragment;
    MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatchFragment = new StopwatchFragment();
        SpeedGraphFragment speedGraphFragment = new SpeedGraphFragment();
        mapFragment = new MapFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        /*fragmentTransaction.add(R.id.activity_main_1, stopwatchFragment, "stopwatchFragment");
        fragmentTransaction.add(R.id.activity_main_2, mapFragment, "mapFragment");
        fragmentTransaction.add(R.id.activity_main_3, speedGraphFragment, "speedGraphFragment");*/

        fragmentTransaction.commit();

        Log.e(LOG, "Done");
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
