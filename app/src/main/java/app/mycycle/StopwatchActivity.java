package app.mycycle;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.text.DecimalFormat;

public class StopwatchActivity extends AppCompatActivity {

    private static final String LOG = "LOG";
    private static final float GPS_REQUEST_DISTANCE_INTVERVAL = 5;                                  //in meters
    private static final long GPS_REQUEST_TIME_INTVERVAL = 1000;                                    //in milliseconds

    private LocationManager locationManager;
    private LocationListener locationListener;
    private DistanceCalculator distanceCalculator;
    private Chronometer timer;

    private Button startButton, stopButton;

    private TextView textViewTotalDistance, textViewCurrentSpeed, textViewAverageSpeed;
    private double currLongitude, currLatitude, prevLongitude, prevLatitude, tmpDistance, totalDistance, currTime, prevTime;

    boolean firstPoint = true;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);                          //lock screen to portrait mode for now

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener();
        distanceCalculator = new HaversineDistanceCalculator();

        timer = new Chronometer(getBaseContext());
        timer = (Chronometer) findViewById(R.id.chronometer);

        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timer.stop();
            }
        });

        textViewAverageSpeed = (TextView) findViewById(R.id.average_speed);
        textViewCurrentSpeed = (TextView) findViewById(R.id.current_speed);
        textViewTotalDistance = (TextView) findViewById(R.id.total_distance);
        

        if(gpsAvailable()) {                                                                        //check is gps service available, do we have permission and request periodic location updates
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e(LOG, "No permission to access GPS");
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_REQUEST_TIME_INTVERVAL, GPS_REQUEST_DISTANCE_INTVERVAL, locationListener);
        }

    }

    /* method to check if GPS is enable */
    private Boolean gpsAvailable() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        if(gpsStatus) {
            return true;
        } else {
            return false;
        }
    }

    /* class to handle gps location service requests */
    private class LocationListener implements android.location.LocationListener {
        private static final String LOG = "LOG";

        @Override
        public void onLocationChanged(Location location) {
            if(firstPoint == true) {
                currLongitude = location.getLongitude();
                currLatitude = location.getLatitude();
                firstPoint = false;
            } else {
                prevLatitude = currLatitude;
                prevLongitude = currLongitude;
                currLongitude = location.getLongitude();
                currLatitude = location.getLatitude();
                //Log.v(LOG, "Longitude: " + currLongitude);
                //Log.v(LOG, "Latitude: " + currLatitude);

                tmpDistance = distanceCalculator.calculateDistance(prevLatitude, prevLongitude, currLatitude, currLongitude);
                Log.v(LOG, "Distance: " + tmpDistance);

                totalDistance += tmpDistance;
                textViewTotalDistance.setText(decimalFormat.format(totalDistance));
                //Log.v(LOG, "Total Distance: " + totalDistance);


                prevTime = currTime;
                currTime = (System.currentTimeMillis() - timer.getBase()) / 1000;

                double timeDiff = currTime - prevTime;
                Log.v(LOG, "Time difference = " + timeDiff);


                double currSpeed = tmpDistance / timeDiff;
                textViewCurrentSpeed.setText(decimalFormat.format(currSpeed));

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
