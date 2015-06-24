package aziz.sohail.eurotest;

import android.location.Location;

/**
 * Created by Sohail Aziz on 6/25/2015.
 */
public class LocationProvider {

    private static final LocationProvider instance = new LocationProvider();
    private Location currentLocation;


    private LocationProvider() {

    }

    public static LocationProvider getInstance() {
        return instance;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }


}
