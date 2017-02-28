package app.mycycle;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

/**
 * Created by carloconnor on 27/02/17.
 */

public class MyPolyline extends Polyline {
    public void addPoint(GeoPoint aPoint) {
        super.addPoint(aPoint);
    }

    public void clearPath() {
        super.clearPath();
    }
}
