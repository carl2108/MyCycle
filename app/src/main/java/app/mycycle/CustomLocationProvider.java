package app.mycycle;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import org.osmdroid.api.IMapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;

/**
 * Created by carloconnor on 16/02/17.
 */

public class CustomLocationProvider extends GpsMyLocationProvider {

    public CustomLocationProvider(Context context) {
        super(context);
    }

    @Override
    public void onLocationChanged(final Location location) {
        super.onLocationChanged(location);

    }

}
