package app.mycycle;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;

/**
 * Created by carloconnor on 16/02/17.
 */

public class CustomLocationProvider extends GpsMyLocationProvider {
    CustomLocationListener customLocationListener;

    public CustomLocationProvider(Context context, Activity activity) {
        super(context);
        try {
            customLocationListener = (CustomLocationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CustomLocationListener");
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        super.onLocationChanged(location);
        customLocationListener.updateLocation(location);

    }

    public interface CustomLocationListener {
        void updateLocation(Location location);
    }

}
