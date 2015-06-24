package aziz.sohail.eurotest.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aziz.sohail.eurotest.Dao.WebManagerFactory;
import aziz.sohail.eurotest.JSON.LocationResponse;
import aziz.sohail.eurotest.R;
import de.greenrobot.event.EventBus;


public class MainFragment extends Fragment {


    private static final String TAG = "MainFragment";

    private AutoCompleteTextView autoCompleteTextViewStartLocation, autoCompleteTextViewEndLocation;
    private ImageButton imageButtonSelectDate;
    private TextView textViewDate;

    private ArrayAdapter<String> startLocationAdapter, endLocationAdapter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        autoCompleteTextViewStartLocation = (AutoCompleteTextView) root.findViewById(R.id.autocompletetextview_start_location_fragment_main);
        autoCompleteTextViewEndLocation = (AutoCompleteTextView) root.findViewById(R.id.autocompletetextview_end_location_fragment_main);
        imageButtonSelectDate = (ImageButton) root.findViewById(R.id.imagebutton_date_fragment_main);
        textViewDate = (TextView) root.findViewById(R.id.textview_date_fragment_main);

        imageButtonSelectDate.setOnClickListener(dateClickListener);

        autoCompleteTextViewStartLocation.setThreshold(2);
        autoCompleteTextViewEndLocation.setThreshold(2);
        autoCompleteTextViewStartLocation.addTextChangedListener(textWatcher);
        autoCompleteTextViewEndLocation.addTextChangedListener(textWatcher);


        startLocationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<String>());
        startLocationAdapter.setNotifyOnChange(true);
        autoCompleteTextViewStartLocation.setAdapter(startLocationAdapter);


        endLocationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<String>());
        endLocationAdapter.setNotifyOnChange(true);
        autoCompleteTextViewEndLocation.setAdapter(endLocationAdapter);


        return root;
    }

    private View.OnClickListener dateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >= 2) {
                Log.d(TAG, "onTextChanged: s.len=" + s.length());
                WebManagerFactory.getWebManager().getLocations(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(EventOnLocationSearch event) {
        Log.d(TAG, "EventOnLocationSearch");
        if (event.exception != null) {
            Log.e(TAG, "EventOnLocationSearch: e=" + event.exception.getMessage());
            Toast.makeText(getActivity(), "Error fetching locations, please check your connectivity", Toast.LENGTH_SHORT).show();
            return;
        }

        //update adapter to auto textview
        Log.d(TAG, "locations size=" + event.locationResponseList.size());

        //Log.i(TAG, "unsorted list:");
        //printList(event.locationResponseList);

        //sort by distance
        Collections.sort(event.locationResponseList);

        //Log.i(TAG, "sorted list:");
        //printList(event.locationResponseList);

        //set the adapter to focused auto complete
        if (autoCompleteTextViewStartLocation.isFocused()) {

            startLocationAdapter.clear();
            startLocationAdapter.addAll(getLocationNames(event.locationResponseList));
            startLocationAdapter.getFilter().filter(autoCompleteTextViewStartLocation.getText(), autoCompleteTextViewStartLocation);

        } else if (autoCompleteTextViewEndLocation.isFocused()) {

            endLocationAdapter.clear();
            endLocationAdapter.addAll(getLocationNames(event.locationResponseList));
            endLocationAdapter.getFilter().filter(autoCompleteTextViewEndLocation.getText(), autoCompleteTextViewEndLocation);

        }

    }

    /**
     * Utility method to print list with distance for debugging
     *
     * @param locationResponseList
     */
    private void printList(@NonNull List<LocationResponse> locationResponseList) {

        if (locationResponseList == null) {
            throw new IllegalArgumentException("invalid argument");
        }


        for (LocationResponse lr : locationResponseList) {
            Log.d(TAG, lr.getName() + ":" + lr.getDistance());
        }
    }

    /**
     * Utility method which returns list location names from List  LocationResponse
     *
     * @param locationResponseList
     * @return
     */
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

    /**
     * Class for Location Search Events
     */
    public static class EventOnLocationSearch {


        public final List<LocationResponse> locationResponseList;
        public final List<String> locationNames;
        public final Exception exception;

        public EventOnLocationSearch(List<LocationResponse> locationResponseList, List<String> names, Exception exception) {
            this.locationResponseList = locationResponseList;
            this.locationNames = names;
            this.exception = exception;
        }
    }

}
