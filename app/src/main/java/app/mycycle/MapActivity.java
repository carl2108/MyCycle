package app.mycycle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.lang.reflect.AccessibleObject;

/**
 * Created by carloconnor on 15/02/17.
 */

public class MapActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();


        TestVariable testVariable = new TestVariable();
        testVariable.setListener(new TestVariable.ChangeListener() {
            @Override
            public void onChange() {
                Log.e("TEST", "onChange listener called");
            }
        });


        //TODO important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_map);

        MapView map = (MapView) findViewById(R.id.map);
        //map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);                                                           //zoom buttons and multitouch zoom
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();                                         //set default view point
        mapController.setZoom(20);
        GeoPoint startPoint = new GeoPoint(-6.330, 53.53);
        mapController.setCenter(startPoint);



        GpsMyLocationProvider gpsMyLocationProvider = new GpsMyLocationProvider(this);              //my location overlay
        MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider, map);
        myLocationOverlay.enableMyLocation();
        //GeoPoint myLocation = myLocationOverlay.getMyLocation();
        //Log.v("LOG", "Longitude: " + myLocation.getLongitude() + " Latitude: " + myLocation.getLatitude());
        myLocationOverlay.enableFollowLocation();
        map.getOverlays().add(myLocationOverlay);



        CompassOverlay compassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), map);          //compass overlay
        compassOverlay.enableCompass();
        map.getOverlays().add(compassOverlay);


    }

    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }
}
