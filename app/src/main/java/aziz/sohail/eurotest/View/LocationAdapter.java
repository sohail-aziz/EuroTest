package aziz.sohail.eurotest.View;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import aziz.sohail.eurotest.JSON.LocationResponse;
import aziz.sohail.eurotest.R;

/**
 * Created by Sohail Aziz on 6/24/2015.
 */

public class LocationAdapter extends BaseAdapter {

    private Context context;
    private List<LocationResponse> locations;

    public LocationAdapter(@NonNull Activity activity, @NonNull List<LocationResponse> locationResponseList) {
        if (activity == null || locationResponseList == null) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.context = activity;
        this.locations = locationResponseList;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Object getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_location_adapter, null, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewLocationName = (TextView) convertView.findViewById(R.id.textview_location_name_row_location_adapter);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String locationName = locations.get(position).getName();

        if (locationName != null) {
            viewHolder.textViewLocationName.setText(locationName);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textViewLocationName;
    }
}
