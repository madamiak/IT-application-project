package pl.travelscheduler.mobile.adapters;

import java.util.List;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.model.Point;
import pl.travelscheduler.mobile.model.PointType;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PointAdapter extends ArrayAdapter<Point>
{
	Activity activity;
	
    public PointAdapter(Activity activity, List<Point> objects)
    {
        super(activity, R.layout.point_item_template, objects);
    	this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	Point currentItem = this.getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.point_item_template, parent,
                false);
        
        showName(rowView, currentItem);
        showLong(rowView, currentItem);
        showLat(rowView, currentItem);        
        showType(rowView, currentItem);
        showImage(rowView, currentItem);   
        
        return rowView;
    }

	private void showType(View rowView, Point currentItem) {
		ImageView imgVPointType = (ImageView) rowView.findViewById(R.id.pointItemImgPointType);
	    if(currentItem.getType() == PointType.CITY)
	    {
	    	imgVPointType.setImageResource(R.drawable.ic_point_type_city);
	    }
	}

	private void showLat(View rowView, Point currentItem) {
		TextView txtVLatitude = (TextView) rowView.findViewById(R.id.pointItemLatitude);
		if(currentItem.getLatitude()>=0)
			txtVLatitude.setText(String.valueOf(currentItem.getLatitude())+" N");
		else
			txtVLatitude.setText(String.valueOf(currentItem.getLatitude())+" S");
	}

	private void showLong(View rowView, Point currentItem)
	{
		TextView txtVLong = (TextView) rowView.findViewById(R.id.pointItemLongitude);
		if(currentItem.getLongitude()>=0)
			txtVLong.setText(String.valueOf(currentItem.getLongitude())+" E");
		else
			txtVLong.setText(String.valueOf(currentItem.getLongitude())+" W");
	}

	private void showName(View rowView, Point currentItem)
	{
        TextView tVName = (TextView) rowView.findViewById(R.id.pointItemTxtName);
        tVName.setText(currentItem.getName());
	}
	
	private void showImage(View rowView, Point currentItem)
	{
        ImageView imgVImage = (ImageView) rowView.findViewById(R.id.pointItemImg);
        if(!currentItem.getImages().isEmpty())
        {
        	//TODO obsluga URL'i z pierwszym zdjeciem
        	imgVImage.setVisibility(View.VISIBLE);
        	imgVImage.setImageResource(R.drawable.ic_action_important);
        }
	}

}
