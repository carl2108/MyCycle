package app.mycycle;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by carloconnor on 15/02/17.
 */

public class MainActivity extends AppCompatActivity implements CustomLocationProvider.CustomLocationListener, StopwatchFragment.MapFragmentListener {
    private final static String LOG = "LOG";

    StopwatchFragment stopwatchFragment;
    MapFragment mapFragment;
    TabbedFragment tabbedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatchFragment = new StopwatchFragment();
        //SpeedGraphFragment speedGraphFragment = new SpeedGraphFragment();
        tabbedFragment = new TabbedFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_1, stopwatchFragment, "stopwatchFragment");
        fragmentTransaction.add(R.id.activity_main_2, tabbedFragment, "tabbedFragment");
        fragmentTransaction.commit();

        Log.i(LOG, "Done");
    }

    @Override
    public void updateLocation(Location location) {
        Log.e("GPS", "Location received! Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
        stopwatchFragment.updateStopwatch(location);
    }

    @Override
    public void updateMap(MyPolyline polyline) {
        if(mapFragment == null)
            assignMapFragment();
        mapFragment.drawRoute(polyline);
    }

    @Override
    public void clearRoute() {
        if(mapFragment == null)
            assignMapFragment();
        mapFragment.clearRoute();
    }

    protected void assignMapFragment() {
        try{
            mapFragment = (MapFragment) getSupportFragmentManager().getFragments().get(2);
        } catch (Exception e) {
            throw new RuntimeException("Could not find mapFragment");
        }
    }
}
