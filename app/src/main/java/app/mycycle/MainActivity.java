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

public class MainActivity extends AppCompatActivity implements CustomLocationProvider.CustomLocationListener {

    TestFragment testFragment;
    StopwatchFragment stopwatchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        getSupportActionBar().hide();

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        stopwatchFragment = new StopwatchFragment();
        SpeedGraphFragment speedGraphFragment = new SpeedGraphFragment();
        MapFragment mapFragment = new MapFragment();
        testFragment = new TestFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_1, stopwatchFragment, "stopwatchFragment");
        fragmentTransaction.add(R.id.activity_main_2, mapFragment, "mapFragment");
        //fragmentTransaction.add(R.id.activity_main_3, speedGraphFragment, "speedGraphFragment");
        fragmentTransaction.add(R.id.activity_main_3, testFragment, "testFragment");
        fragmentTransaction.commit();


    }

    @Override
    public void updateLocation(Location location) {
        Log.e("GPS", "Location received! Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
        stopwatchFragment.updateStopwatch(location);
    }

}
