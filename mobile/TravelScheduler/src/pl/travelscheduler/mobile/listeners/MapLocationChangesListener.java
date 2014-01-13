package pl.travelscheduler.mobile.listeners;

import java.util.ArrayList;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.model.DataContainer;
import pl.travelscheduler.mobile.model.Point;
import pl.travelscheduler.mobile.tasks.LoadPoisInRadius;
import android.app.Activity;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapLocationChangesListener implements OnMyLocationChangeListener
{
	private GoogleMap map;
	private Location previousLocation;
	private boolean locationWasNull;
	private Activity parentActivity;
	
	private final float POIS_UPDATE_DISTANCE = 3000;
	
	public MapLocationChangesListener(GoogleMap gMap, Activity activity)
	{
		map = gMap;
		map.clear();
		ArrayList<Point> pois = DataContainer.getSavedPoints(activity);
		for(Point p : pois)
		{
			LatLng coordinate = new LatLng(p.getLatitude(), p.getLongitude());
            map.addMarker(new MarkerOptions()
            		.position(coordinate)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_action_place))
                    .title(p.getName()));
		}
		locationWasNull = false;
		parentActivity = activity;
	}

	@Override
	public void onMyLocationChange(Location newLocation)
	{
		if(newLocation != null)
		{
			if(previousLocation == null)
			{
				previousLocation = newLocation;
				locationWasNull = true;
			}
			if(previousLocation.distanceTo(newLocation) > POIS_UPDATE_DISTANCE || locationWasNull)
			{
				previousLocation = newLocation;
				locationWasNull = false;
				LoadPoisInRadius task = new LoadPoisInRadius(newLocation, parentActivity, map);
				task.execute((String)null);
			}
		}
	}
}