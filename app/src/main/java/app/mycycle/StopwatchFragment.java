package app.mycycle;

import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import org.osmdroid.util.GeoPoint;

import java.text.DecimalFormat;

public class StopwatchFragment extends Fragment implements View.OnClickListener {

    private final static String LOG = "LOG";
    private final static int SECONDS_IN_HOUR = 60*60;
    private final static int MILLISECONDS_IN_SECOND = 1000;
    private final static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private final static String TIMER_INIT = "00:00";

    private DistanceCalculator distanceCalculator;
    private Chronometer timer;
    private Button startButton, stopButton, resetButton;

    private TextView textViewTotalDistance, textViewCurrentSpeed, textViewAverageSpeed;

    private int sectionCounter;
    private double currLongitude, currLatitude, prevLongitude, prevLatitude, currDistance, totalDistance ,
                    currTime, prevTime, timeDiff, currSpeed, averageSpeed, averageSpeedRunningTotal, totalTime;

    private RouteDAO routeDAO;
    private Route newRoute;
    View view;

    boolean running = false;

    MyPolyline routeLine = new MyPolyline();
    MapFragmentListener mapFragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stopwatch_fragment, container, false);

        mapFragmentListener = (MapFragmentListener) getActivity();
        distanceCalculator = new HaversineDistanceCalculator();

        timer = new Chronometer(getActivity().getBaseContext());
        timer = (Chronometer) view.findViewById(R.id.chronometer);

        startButton = (Button) view.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        resetButton = (Button) view.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        resetButton.setEnabled(false);
        resetButton.setVisibility(View.INVISIBLE);
        stopButton = (Button) view.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        stopButton.setEnabled(false);

        textViewAverageSpeed = (TextView) view.findViewById(R.id.average_speed);
        textViewCurrentSpeed = (TextView) view.findViewById(R.id.current_speed);
        textViewTotalDistance = (TextView) view.findViewById(R.id.total_distance);

        routeDAO = new RouteDAO(getActivity().getApplicationContext());
        initRoute();

        return view;
    }

    public void updateStopwatch(Location location) {
        if(!running || currLongitude == -999) {                                                                 //handle the first received gps update
            currLongitude = location.getLongitude();
            currLatitude = location.getLatitude();
            return;
        }

        prevTime = currTime;
        currTime = (System.currentTimeMillis() - timer.getBase()) / MILLISECONDS_IN_SECOND;         //total cycle time so far
        if(prevTime != 0)
            timeDiff = currTime - prevTime;                                                          //time for previous section
        totalTime += timeDiff;

        prevLatitude = currLatitude;
        prevLongitude = currLongitude;
        currLongitude = location.getLongitude();
        currLatitude = location.getLatitude();

        currDistance = distanceCalculator.calculateDistance(prevLatitude, prevLongitude,            //distance for the previous section
                                                            currLatitude, currLongitude);

        totalDistance += currDistance;                                                              //total distance textview

        if(timeDiff != 0)
            currSpeed = currDistance*SECONDS_IN_HOUR/timeDiff;                                      //current speed textview

        /*averageSpeedRunningTotal += currSpeed;
        averageSpeedSections++;
        averageSpeed = averageSpeedRunningTotal/averageSpeedSections;*/                             //average speed - calculated by rolling average

        if(totalTime != 0)
            averageSpeed = totalDistance*SECONDS_IN_HOUR/totalTime;                                 //average speed - calculated by total distance/time

        textViewAverageSpeed.setText(decimalFormat.format(averageSpeed));
        textViewCurrentSpeed.setText(decimalFormat.format(currSpeed));
        textViewTotalDistance.setText(decimalFormat.format(totalDistance));

        Log.i(LOG, "TimeDiff: " + timeDiff);
        Log.i(LOG, "TotalTime: " + totalTime);
        Log.i(LOG, "CurrDistance: " + currDistance);
        Log.i(LOG, "TotalDistance: " + totalDistance);
        Log.i(LOG, "CurrSpeed: " + currSpeed);
        Log.i(LOG, "AverageSpeed: " + averageSpeed);

        newRoute.addSection(new RouteSection(sectionCounter++, new MyLocation(location), currTime, currDistance, currSpeed));
        GeoPoint currPoint = new GeoPoint(location);
        routeLine.addPoint(currPoint);
        mapFragmentListener.updateMap(routeLine);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
                prevTime = timer.getBase();
                running = true;
                startButton.setEnabled(false);
                startButton.setVisibility(View.INVISIBLE);
                stopButton.setEnabled(true);
                break;
            case R.id.stop_button:
                timer.stop();
                endRoute();
                running = false;
                resetButton.setEnabled(true);
                resetButton.setVisibility(View.VISIBLE);
                stopButton.setEnabled(false);
                stopButton.setVisibility(View.INVISIBLE);
                startButton.setEnabled(false);
                startButton.setVisibility(View.VISIBLE);
                break;
            case R.id.reset_button:
                startButton.setEnabled(true);
                startButton.setVisibility(View.VISIBLE);
                stopButton.setEnabled(false);
                stopButton.setVisibility(View.VISIBLE);
                resetButton.setEnabled(false);
                resetButton.setVisibility(View.INVISIBLE);
                resetRoute();
                break;
        }
    }

    public void endRoute() {
        newRoute.setTotalDistance(totalDistance);
        newRoute.setTotalTime(totalTime);
        newRoute.setAverageSpeed(averageSpeed);
        routeDAO.save(newRoute);
        Log.v(LOG, "Route " + newRoute.getID() + " saved!");
    }

    public void initRoute() {
        newRoute = new Route();
        newRoute.setID(routeDAO.getNextRouteID());
        initRouteVariables();
        resetTextViews();
        routeLine.clearPath();
    }                                                                   //initialise a new route

    public void resetRoute() {                                                                      //initialise a new route and clear the map fragment
        initRoute();
        mapFragmentListener.clearRoute();
    }

    public void initRouteVariables() {
        sectionCounter = 0;
        currLongitude = -999;
        currLatitude = -999;
        prevLongitude = -999;
        prevLatitude = -999;
        currDistance = 0;
        totalDistance = 0;
        currTime = 0;
        prevTime = 0;
        timeDiff = 0;
        currSpeed = 0;
        averageSpeed = 0;
        averageSpeedRunningTotal = 0;
        totalTime = 0;
    }

    public void resetTextViews() {
        textViewAverageSpeed.setText(decimalFormat.format(averageSpeed));
        textViewCurrentSpeed.setText(decimalFormat.format(currSpeed));
        textViewTotalDistance.setText(decimalFormat.format(totalDistance));
        timer = new Chronometer(getActivity().getBaseContext());
        timer = (Chronometer) view.findViewById(R.id.chronometer);
        timer.setText(TIMER_INIT);
    }

    public interface MapFragmentListener {
        void updateMap(MyPolyline polyline);
        void clearRoute();
    }

}
