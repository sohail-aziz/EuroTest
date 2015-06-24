package aziz.sohail.eurotest;

import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Sohail Aziz on 6/25/2015.
 */
public class Utils {

    private static final String TAG = "Utils";

    public static float getDistance(@NonNull Location locationOne, @NonNull Location locationTwo) {

        Log.d(TAG, "getDistance; locationOne:" + locationOne.getLatitude() + ":" + locationOne.getLongitude());
        Log.d(TAG, "getDistance: locationTwo:" + locationTwo.getLongitude() + ":" + locationTwo.getLongitude());

        float distance = locationOne.distanceTo(locationTwo);
        Log.d(TAG, "getDistance: distance:" + distance);

        return distance;
    }
}
