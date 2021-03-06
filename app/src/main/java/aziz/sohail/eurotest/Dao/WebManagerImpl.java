package aziz.sohail.eurotest.Dao;

import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aziz.sohail.eurotest.JSON.GSON;
import aziz.sohail.eurotest.JSON.GeoPosition;
import aziz.sohail.eurotest.JSON.LocationResponse;
import aziz.sohail.eurotest.LocationProvider;
import aziz.sohail.eurotest.Utils;
import aziz.sohail.eurotest.View.MainFragment;
import de.greenrobot.event.EventBus;

/**
 * Created by Sohail Aziz on 6/23/2015.
 */
public class WebManagerImpl implements WebManager {

    private static final String TAG = "WebManagerImpl";
    final String BASE_URL = "http://api.goeuro.com/api/v2/position/suggest/de/";

    @Override
    public void getLocations(@NonNull final String locationName) {
        Log.d(TAG, "getLocations");

        if (locationName == null) {
            throw new IllegalArgumentException("getLocations: invalid argument");
        }

        final String searchUrl = BASE_URL + locationName;
        Log.d(TAG, "searchUr=" + searchUrl);
        final AsyncHttpClient client = new AsyncHttpClient();

        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, @Nullable JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                Log.d(TAG, "onSuccess: response=" + response);

                if (response != null) {

                    List<LocationResponse> locations = new ArrayList<>(response.length());


                    final Location myLocation = LocationProvider.getInstance().getCurrentLocation();
                    final Location targetLocation = new Location("");
                    Gson gson = GSON.getGson();
                    try {

                        for (int i = 0, len = response.length(); i < len; i++) {

                            LocationResponse locationResponse = gson.fromJson(response.getJSONObject(i).toString(), LocationResponse.class);

                            GeoPosition targetGeoPosition = locationResponse.getGeoPosition();

                            Log.d(TAG, "targetGeoPostion:" + targetGeoPosition);
                            if (targetGeoPosition != null && myLocation != null) {

                                targetLocation.setLatitude((double) targetGeoPosition.getLatitude());
                                targetLocation.setLongitude((double) targetGeoPosition.getLongitude());

                                float distance = Utils.getDistance(myLocation, targetLocation);
                                locationResponse.setDistance(distance);

                            } else {
                                Log.e(TAG, "target or current location is null");
                            }

                            locations.add(locationResponse);

                        }

                        //sort arraylist
                        Collections.sort(locations);
                        List<String> locationNameList = getLocationNames(locations);

                        //posting success event
                        EventBus.getDefault().post(new MainFragment.EventOnLocationSearch(locations, locationNameList, null));


                    } catch (JSONException e) {
                        e.printStackTrace();
                        EventBus.getDefault().post(new MainFragment.EventOnLocationSearch(null, null, e));
                    }

                } else {

                    //posting failure event
                    EventBus.getDefault().post(new MainFragment.EventOnLocationSearch(null, null, new Exception("error")));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure: error=" + errorResponse);

                //posting failure event
                EventBus.getDefault().post(new MainFragment.EventOnLocationSearch(null, null, new Exception(throwable)));

            }
        });


    }

    /**
     * Utility method which returns list location names from List  LocationResponse
     *
     * @param locationResponseList
     * @return
     */
    @NotNull
    private List<String> getLocationNames(@NonNull final List<LocationResponse> locationResponseList) {
        if (locationResponseList == null) {
            throw new IllegalArgumentException("getLocationNames: argument cannot be null");
        }

        List<String> namesList = new ArrayList<>(locationResponseList.size());
        for (LocationResponse lr : locationResponseList) {
            namesList.add(lr.getFullName());
        }

        return namesList;
    }
}
