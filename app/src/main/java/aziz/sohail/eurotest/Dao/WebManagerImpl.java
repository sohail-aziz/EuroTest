package aziz.sohail.eurotest.Dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import aziz.sohail.eurotest.JSON.GSON;
import aziz.sohail.eurotest.JSON.LocationResponse;
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
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                Log.d(TAG, "onSuccess: response=" + response);

                if (statusCode == HttpStatus.SC_OK && response != null) {

                    List<LocationResponse> locations = new ArrayList<>(response.length());
                    List<String> locationNames = new ArrayList<String>(response.length());

                    Gson gson = GSON.getGson();
                    try {

                        for (int i = 0, len = response.length(); i < len; i++) {
                            final LocationResponse locationResponse = gson.fromJson(response.getJSONObject(i).toString(), LocationResponse.class);
                            locations.add(locationResponse);
                            locationNames.add(locationResponse.getName());
                        }

                        EventBus.getDefault().post(new MainFragment.EventOnLocationSearch(locations, locationNames, null));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        EventBus.getDefault().post(new MainFragment.EventOnLocationSearch(null, null, e));
                    }

                } else {
                    EventBus.getDefault().post(new MainFragment.EventOnLocationSearch(null, null, new Exception("error")));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure: error=" + errorResponse);

                EventBus.getDefault().post(new MainFragment.EventOnLocationSearch(null, null, new Exception(throwable)));

            }
        });


    }
}
