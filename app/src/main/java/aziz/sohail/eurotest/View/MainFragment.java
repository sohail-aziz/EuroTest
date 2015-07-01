package aziz.sohail.eurotest.View;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aziz.sohail.eurotest.Dao.WebManagerFactory;
import aziz.sohail.eurotest.JSON.LocationResponse;
import aziz.sohail.eurotest.R;
import aziz.sohail.eurotest.Utils;
import de.greenrobot.event.EventBus;


public class MainFragment extends Fragment {


    private static final String TAG = "MainFragment";

    private AutoCompleteTextView autoCompleteTextViewStartLocation, autoCompleteTextViewEndLocation;
    private ImageButton imageButtonSelectDate;
    private TextView textViewDate;

    private ArrayAdapter<String> startLocationAdapter, endLocationAdapter;

    @NotNull
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        autoCompleteTextViewStartLocation = (AutoCompleteTextView) root.findViewById(R.id.autocompletetextview_start_location_fragment_main);
        autoCompleteTextViewEndLocation = (AutoCompleteTextView) root.findViewById(R.id.autocompletetextview_end_location_fragment_main);
        imageButtonSelectDate = (ImageButton) root.findViewById(R.id.imagebutton_date_fragment_main);
        textViewDate = (TextView) root.findViewById(R.id.textview_date_fragment_main);

        imageButtonSelectDate.setOnClickListener(dateClickListener);
        root.findViewById(R.id.button_search_fragment_main).setOnClickListener(searchClickListener);

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

    @NotNull
    private View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), R.string.search_message_fragment_main, Toast.LENGTH_SHORT).show();
        }
    };
    @NotNull
    private View.OnClickListener dateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Calendar calendar = Calendar.getInstance();
            final int startYear = calendar.get(Calendar.YEAR);
            final int startMonth = (calendar.get(Calendar.MONTH));
            final int startDay = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                    //month start at 0
                    DateTime selectedDate = new DateTime().withDate(year, (monthOfYear + 1), dayOfMonth);
                    textViewDate.setText(Utils.getFormattedDate(selectedDate));


                }
            }, startYear, startMonth, startDay);

            datePickerDialog.show();
        }
    };

    @NotNull
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(@NotNull CharSequence s, int start, int before, int count) {

            if (s != "" && s.length() >= 2) {
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

    /**
     * Subscribe for EventOnLocationSearch [on main thread]
     *
     * @param event
     */
    public void onEventMainThread(@NotNull EventOnLocationSearch event) {
        Log.d(TAG, "EventOnLocationSearch");
        if (event.exception != null) {
            Log.e(TAG, "EventOnLocationSearch: e=" + event.exception.getMessage());
            Toast.makeText(getActivity(), "Error fetching locations, please check your connectivity", Toast.LENGTH_SHORT).show();
            return;
        }

        //update adapter to auto textview
        Log.d(TAG, "locations size=" + event.locationResponseList.size());

        //set the adapter to focused auto complete
        if (autoCompleteTextViewStartLocation.isFocused()) {

            startLocationAdapter.clear();
            startLocationAdapter.addAll(event.locationNames);
            startLocationAdapter.getFilter().filter(autoCompleteTextViewStartLocation.getText(), autoCompleteTextViewStartLocation);

        } else if (autoCompleteTextViewEndLocation.isFocused()) {

            endLocationAdapter.clear();
            endLocationAdapter.addAll(event.locationNames);
            endLocationAdapter.getFilter().filter(autoCompleteTextViewEndLocation.getText(), autoCompleteTextViewEndLocation);

        }

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
