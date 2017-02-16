package app.mycycle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by carloconnor on 15/02/17.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StopwatchFragment stopwatchFragment = new StopwatchFragment();
        SpeedGraphFragment speedGraphFragment = new SpeedGraphFragment();
        MapFragment mapFragment = new MapFragment();
        TestFragment testFragment = new TestFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_1, stopwatchFragment, "stopwatchFragment");
        fragmentTransaction.add(R.id.activity_main_2, mapFragment, "mapFragment");
        //fragmentTransaction.add(R.id.activity_main_3, speedGraphFragment, "speedGraphFragment");
        fragmentTransaction.add(R.id.activity_main_3, testFragment, "testFragment");
        fragmentTransaction.commit();




    }

}
