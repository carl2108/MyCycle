package app.mycycle;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private final static String LOG = "LOG";
    private final static int CURRENT_LOCATION_OVERLAY_INDEX = 3;
    private final static int COMPASS_OVERLAY_INDEX = 2;
    private final static int CURRENT_ROUTE_OVERLAY_INDEX = 1;
    private final static int PREVIOUS_ROUTE_OVERLAY_INDEX = 0;

    MapView map;
    View view;
    Context context;
    MyPolyline currentRouteOverlay;
    MyPolyline previousRouteOverlay;

    MyLocationNewOverlay myLocationOverlay;
    CompassOverlay compassOverlay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        context = view.getContext();


        //TODO important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        map = (MapView) view.findViewById(R.id.map);
        //map.setTileSource(TileSourceFactory.MAPNIK);

        //map.setBuiltInZoomControls(true);                                                           //zoom buttons and multitouch zoom
        //map.setMultiTouchControls(true);

        IMapController mapController = map.getController();                                         //set default view point
        mapController.setZoom(20);
        GeoPoint startPoint = new GeoPoint(-6.330, 53.53);
        mapController.setCenter(startPoint);

        GpsMyLocationProvider gpsMyLocationProvider = new CustomLocationProvider(context, getActivity());              //my location overlay
        myLocationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider, map);
        myLocationOverlay.enableMyLocation();
        //GeoPoint myLocation = myLocationOverlay.getMyLocation();                                                      //todo - use to initialise stopwatch fragment location?
        //Log.v("LOG", "Longitude: " + myLocation.getLongitude() + " Latitude: " + myLocation.getLatitude());
        myLocationOverlay.enableFollowLocation();
        //map.getOverlayManager().add(CURRENT_LOCATION_OVERLAY_INDEX, myLocationOverlay);

        compassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), map);          //compass overlay
        compassOverlay.enableCompass();
        //map.getOverlayManager().add(COMPASS_OVERLAY_INDEX, compassOverlay);

        currentRouteOverlay = new MyPolyline();
        previousRouteOverlay = new MyPolyline();
        //map.getOverlayManager().add(CURRENT_ROUTE_OVERLAY_INDEX, currentRouteLine);

        addOverlays();

        return view;
    }

    public void drawRoute(MyPolyline polyline) {
        map.getOverlayManager().set(CURRENT_ROUTE_OVERLAY_INDEX, polyline);
    }

    public void addOverlays() {
        map.getOverlayManager().add(PREVIOUS_ROUTE_OVERLAY_INDEX, previousRouteOverlay);
        map.getOverlayManager().add(CURRENT_ROUTE_OVERLAY_INDEX, currentRouteOverlay);
        map.getOverlayManager().add(COMPASS_OVERLAY_INDEX, compassOverlay);
        map.getOverlayManager().add(CURRENT_LOCATION_OVERLAY_INDEX, myLocationOverlay);
    }

    public void clearRoute() {
        currentRouteOverlay.clearPath();
    }

}
