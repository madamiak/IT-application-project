package pl.travelscheduler.mobile.adapters;

import java.util.List;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.helpers.CalendarHelper;
import pl.travelscheduler.mobile.model.Rating;
import pl.travelscheduler.mobile.model.TransportType;
import pl.travelscheduler.mobile.model.Travel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelAdapter extends ArrayAdapter<Travel>
{
	Activity activity;
	
    public TravelAdapter(Activity activity, List<Travel> objects)
    {
        super(activity, R.layout.travel_item_template, objects);
    	this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	Travel currentItem = this.getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.travel_item_template, parent,
                false);
        TextView tVSource = (TextView) rowView
                .findViewById(R.id.travelItemTxtSource);
        TextView tVDestination = (TextView) rowView
                .findViewById(R.id.travelItemTxtDestination);
        tVSource.setText(currentItem.getSource());
        tVDestination.setText(currentItem.getDestination());
        
        ImageView imgVRating = (ImageView) rowView.findViewById(R.id.travelItemImgRating);
        if(currentItem.getRating() == Rating.NONE)
        {
        	imgVRating.setVisibility(View.INVISIBLE);
        }
        else if(currentItem.getRating() == Rating.LOW)
        {
        	imgVRating.setVisibility(View.VISIBLE);
        	imgVRating.setImageResource(R.drawable.ic_action_not_important);
        }
        else if(currentItem.getRating() == Rating.MEDIUM)
        {
        	imgVRating.setVisibility(View.VISIBLE);
        	imgVRating.setImageResource(R.drawable.ic_action_half_important);
        }
        else if(currentItem.getRating() == Rating.HIGH)
        {
        	imgVRating.setVisibility(View.VISIBLE);
        	imgVRating.setImageResource(R.drawable.ic_action_important);
        }
        
        ImageView imgVTransportType = (ImageView) rowView.findViewById(R.id.travelItemImgTransportType);
        if(currentItem.getTransportType() == TransportType.CAR)
        {
        	imgVTransportType.setImageResource(R.drawable.ic_transport_type_car);
        }
        
        TextView txtVPoi = (TextView) rowView.findViewById(R.id.travelItemPoiInfo);
        if(currentItem.hasPois())
        {
        	txtVPoi.setVisibility(View.VISIBLE);
        }
        else
        {
        	txtVPoi.setVisibility(View.INVISIBLE);
        }
        
        TextView txtVDistance = (TextView) rowView.findViewById(R.id.travelItemDistance);
        txtVDistance.setText(currentItem.getDistance() + " km");
        
        TextView txtVTime = (TextView) rowView.findViewById(R.id.travelItemTime);
        txtVTime.setText(CalendarHelper.ToString(currentItem.getStartingTime()));
        
        return rowView;
    }
}
