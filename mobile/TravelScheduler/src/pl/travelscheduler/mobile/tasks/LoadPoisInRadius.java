package pl.travelscheduler.mobile.tasks;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.travelscheduler.mobile.AppParameters;
import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.helpers.HttpHelper;
import pl.travelscheduler.mobile.model.DataContainer;
import pl.travelscheduler.mobile.model.Point;
import pl.travelscheduler.mobile.model.PointType;
import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;

public class LoadPoisInRadius extends AsyncTask<String, Void, Void>
{
	private Location location;
	private Activity activity;
	private GoogleMap map;
	private ArrayList<Point> addedPoints;

	public LoadPoisInRadius(Location currentLocation, Activity parentActivity, GoogleMap gMap)
	{
		location = currentLocation;
		activity = parentActivity;
		map = gMap;
	}

	@Override
	protected Void doInBackground(String... args)
	{
		try
		{
			String parameters = "?long="+location.getLongitude()+
								"&latt="+location.getLatitude()+
								"&radius="+AppParameters.POIS_RADIUS;
			HttpResponse response = HttpHelper.get(AppParameters.SERVER_URL + 
					AppParameters.POIS_SERVICE +  parameters);
			if(response != null)
			{
				String respStr = HttpHelper.getStringFromResponse(response);
				JSONObject poisResp = new JSONObject(respStr);
				JSONArray poisArray = new JSONArray(poisResp.getString("pois"));
				if(poisArray != null && poisArray.length() > 0)
				{
					for(int i = 0; i < poisArray.length(); i++)
					{			
						JSONObject poi = poisArray.getJSONObject(i);
						String id = poi.getString("id");
						if(!DataContainer.containsPoiWithId(Integer.parseInt(id)))
						{
							Point p = loadPoi(id);
							if(p != null)
							{
								if(DataContainer.addPoi(p))
								{
									if(addedPoints == null)
									{
										addedPoints = new ArrayList<Point>();
									}
									addedPoints.add(p);
								}
							}
						}
					}
					DataContainer.savePois(activity);
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result)
	{
		super.onPostExecute(result);
		if(addedPoints != null)
		{
			for(Point p : addedPoints)
			{
				LatLng coordinate = new LatLng(p.getLatitude(), p.getLongitude());
                map.addMarker(new MarkerOptions()
                		.position(coordinate)
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_action_place))
                        .title(p.getName()));
			}
		}
	}
	
	private Point loadPoi(String id) throws JSONException
	{
		String parameters = "/"+id;
		HttpResponse response = HttpHelper.get(AppParameters.SERVER_URL + 
				AppParameters.POI_DATA_SERVICE +  parameters);
		if(response != null)
		{
			String respStr = HttpHelper.getStringFromResponse(response);
			JSONObject poiResp = new JSONObject(respStr);
			Point poiPoint = new Point(poiResp.getInt("id"),
									   poiResp.getString("value"),
									   poiResp.optDouble("longn"),
									   poiResp.optDouble("latt"),
									   -1, PointType.POI, null);
			return poiPoint;
		}
		return null;
	}
}
