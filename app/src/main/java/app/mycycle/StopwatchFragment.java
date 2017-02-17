package app.mycycle;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.text.DecimalFormat;

public class StopwatchFragment extends Fragment {

    private DistanceCalculator distanceCalculator;
    private Chronometer timer;

    private Button startButton, stopButton;

    private TextView textViewTotalDistance, textViewCurrentSpeed, textViewAverageSpeed;

    private double currLongitude = -999, currLatitude = -999, prevLongitude, prevLatitude, currDistance, totalDistance,
                    currTime, prevTime, timeDiff, currSpeed, averageSpeed, averageSpeedRunningTotal, averageSpeedSections = 0;

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stopwatch_fragment, container, false);

        distanceCalculator = new HaversineDistanceCalculator();

        timer = new Chronometer(getActivity().getBaseContext());
        timer = (Chronometer) view.findViewById(R.id.chronometer);

        startButton = (Button) view.findViewById(R.id.start_button);
        stopButton = (Button) view.findViewById(R.id.stop_button);

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {                                                      //use switch statement
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

        textViewAverageSpeed = (TextView) view.findViewById(R.id.average_speed);
        textViewCurrentSpeed = (TextView) view.findViewById(R.id.current_speed);
        textViewTotalDistance = (TextView) view.findViewById(R.id.total_distance);

        return view;
    }

    public void updateStopwatch(Location location) {
        if(currLongitude == -999) {                                                                 //handle the first received gps update
            currLongitude = location.getLongitude();
            currLatitude = location.getLatitude();
            return;
        }

        prevTime = currTime;
        currTime = (System.currentTimeMillis() - timer.getBase()) / 1000;                           //total cycle time so far
        timeDiff = currTime - prevTime;                                                             //time for previous section

        prevLatitude = currLatitude;
        prevLongitude = currLongitude;
        currLongitude = location.getLongitude();
        currLatitude = location.getLatitude();

        currDistance = distanceCalculator.calculateDistance(prevLatitude, prevLongitude,            //distance for the previous section
                                                            currLatitude, currLongitude);

        totalDistance += currDistance;                                                              //total distance textview

        currSpeed = currDistance/timeDiff;                                                          //current speed textview

        /*averageSpeedRunningTotal += currSpeed;
        averageSpeedSections++;
        averageSpeed = averageSpeedRunningTotal/averageSpeedSections;*/                               //average speed - calculated by rolling average

        averageSpeed = totalDistance/currTime;                                                      //average speed - calculated by total distance/time

        textViewAverageSpeed.setText(decimalFormat.format(averageSpeed));
        textViewCurrentSpeed.setText(decimalFormat.format(currSpeed));
        //textViewTotalDistance.setText(decimalFormat.format(totalDistance));
        textViewTotalDistance.setText(String.valueOf(1));

    }

}
