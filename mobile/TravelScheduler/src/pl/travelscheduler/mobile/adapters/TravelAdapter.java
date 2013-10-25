package pl.travelscheduler.mobile.adapters;

import java.util.List;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.model.Travel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TravelAdapter extends ArrayAdapter<Travel>
{
    public TravelAdapter(Context context, List<Travel> objects)
    {
        super(context, R.layout.travel_item_template, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.travel_item_template, parent,
                false);
        TextView tVSource = (TextView) rowView
                .findViewById(R.id.travelItemTxtSource);
        TextView tVDestination = (TextView) rowView
                .findViewById(R.id.travelItemTxtDestination);
        tVSource.setText(this.getItem(position).getSource());
        tVDestination.setText(this.getItem(position).getDestination());
        return rowView;
    }
}
