package app.mycycle;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * Created by carloconnor on 16/02/17.
 */

public class MapFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        Context context = view.getContext();

        TestVariable testVariable = new TestVariable();
        testVariable.setListener(new TestVariable.ChangeListener() {
            @Override
            public void onChange() {
                Log.e("TEST", "onChange listener called");
            }
        });


        //TODO important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        MapView map = (MapView) view.findViewById(R.id.map);
        //map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);                                                           //zoom buttons and multitouch zoom
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();                                         //set default view point
        mapController.setZoom(20);
        GeoPoint startPoint = new GeoPoint(-6.330, 53.53);
        mapController.setCenter(startPoint);



        GpsMyLocationProvider gpsMyLocationProvider = new GpsMyLocationProvider(context);              //my location overlay
        MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider, map);
        myLocationOverlay.enableMyLocation();
        //GeoPoint myLocation = myLocationOverlay.getMyLocation();
        //Log.v("LOG", "Longitude: " + myLocation.getLongitude() + " Latitude: " + myLocation.getLatitude());
        myLocationOverlay.enableFollowLocation();
        map.getOverlays().add(myLocationOverlay);



        CompassOverlay compassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), map);          //compass overlay
        compassOverlay.enableCompass();
        map.getOverlays().add(compassOverlay);

        return view;

    }
}
