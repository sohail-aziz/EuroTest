package aziz.sohail.eurotest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainFragment extends Fragment {


    private static final String TAG = "MainFragment";

    private AutoCompleteTextView autoCompleteTextViewStartLocation, autoCompleteTextViewEndLocation;
    private ImageButton imageButtonSelectDate;
    private TextView textViewDate;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //populate spinners
        //initialize textViewDate to default date


    }

    private View.OnClickListener dateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


}
