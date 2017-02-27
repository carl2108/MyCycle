package app.mycycle;

import android.location.Location;

import com.google.gson.JsonElement;

/**
 * Created by carloconnor on 23/02/17.
 */

public class MyLocation extends Location {
    private final static String DEFAULT_LOCATION_PROVIDER = "gps";

    public MyLocation() {
        super(DEFAULT_LOCATION_PROVIDER);
    }

    public MyLocation(Location location) {
        super(location.getProvider());
        this.setLatitude(location.getLatitude());
        this.setLongitude(location.getLongitude());
    }

    public MyLocation(JsonElement json) {
        super(json.getAsJsonObject().get("provider").toString());
        this.setLatitude(json.getAsJsonObject().get("mLatitude").getAsDouble());
        this.setLongitude(json.getAsJsonObject().get("mLongitude").getAsDouble());
    }

}
