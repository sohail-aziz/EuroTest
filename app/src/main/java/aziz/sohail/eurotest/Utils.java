package aziz.sohail.eurotest;

import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Sohail Aziz on 6/25/2015.
 */
public class Utils {

    private static final String TAG = "Utils";

    /**
     * Returns distance between locationOne and locationTwo
     *
     * @param locationOne
     * @param locationTwo
     * @return
     */
    public static float getDistance(@NonNull Location locationOne, @NonNull Location locationTwo) {

        Log.d(TAG, "getDistance; locationOne:" + locationOne.getLatitude() + ":" + locationOne.getLongitude());
        Log.d(TAG, "getDistance: locationTwo:" + locationTwo.getLongitude() + ":" + locationTwo.getLongitude());

        float distance = locationOne.distanceTo(locationTwo);
        Log.d(TAG, "getDistance: distance:" + distance);

        return distance;
    }

    /**
     * @param date
     * @return String date in MM.dd.yyyy format
     */
    public static String getFormattedDate(@NonNull final DateTime date) {

        if (date == null) {
            throw new IllegalArgumentException("getFormattedDate: date cannot be null");
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM.dd.yyyy");
        return formatter.print(date);

    }

}
