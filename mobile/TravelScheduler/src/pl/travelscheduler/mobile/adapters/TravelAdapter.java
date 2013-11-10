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
        
        showSource(rowView, currentItem);
        showDestination(rowView, currentItem);
        showRating(rowView, currentItem);        
        showTransportType(rowView, currentItem);        
        showPoiInformation(rowView, currentItem);        
        showDistance(rowView, currentItem);        
        showTime(rowView, currentItem);
        
        return rowView;
    }

	private void showTime(View rowView, Travel currentItem)
	{
		TextView txtVTime = (TextView) rowView.findViewById(R.id.travelItemTime);
        txtVTime.setText(CalendarHelper.ToString(currentItem.getStartingTime()));
	}

	private void showDistance(View rowView, Travel currentItem)
	{
		TextView txtVDistance = (TextView) rowView.findViewById(R.id.travelItemDistance);
		txtVDistance.setText(currentItem.getDistance() + " km");
	}

	private void showPoiInformation(View rowView, Travel currentItem)
	{
		TextView txtVPoi = (TextView) rowView.findViewById(R.id.travelItemPoiInfo);
	    if(currentItem.hasPois())
	    {
	    	txtVPoi.setVisibility(View.VISIBLE);
	    }
	    else
	    {
	    	txtVPoi.setVisibility(View.INVISIBLE);
	    }
	}

	private void showTransportType(View rowView, Travel currentItem)
	{
		ImageView imgVTransportType = (ImageView) rowView.findViewById(R.id.travelItemImgTransportType);
	    if(currentItem.getTransportType() == TransportType.CAR)
	    {
	    	imgVTransportType.setImageResource(R.drawable.ic_transport_type_car);
	    }
	}

	private void showDestination(View rowView, Travel currentItem)
	{
        TextView tVDestination = (TextView) rowView
                .findViewById(R.id.travelItemTxtDestination);
        tVDestination.setText(currentItem.getDestination());
	}

	private void showSource(View rowView, Travel currentItem)
	{
        TextView tVSource = (TextView) rowView
                .findViewById(R.id.travelItemTxtSource);
        tVSource.setText(currentItem.getSource());
	}

	private void showRating(View rowView, Travel currentItem)
	{
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
	}
}
