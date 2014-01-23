package pl.travelscheduler.mobile.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.travelscheduler.mobile.AppParameters;
import pl.travelscheduler.mobile.dialogs.ProgressDialog;
import pl.travelscheduler.mobile.helpers.HttpHelper;
import pl.travelscheduler.mobile.helpers.SessionHelper;
import pl.travelscheduler.mobile.model.DataContainer;
import pl.travelscheduler.mobile.model.Point;
import pl.travelscheduler.mobile.model.PointType;
import pl.travelscheduler.mobile.model.Rating;
import pl.travelscheduler.mobile.model.TransportType;
import pl.travelscheduler.mobile.model.DataContainer.SOURCE;
import pl.travelscheduler.mobile.model.Travel;
import pl.travelscheduler.mobile.views.MyTripsFragment;
import android.app.Activity;
import android.os.AsyncTask;

public class LoadOnlineTravelsTask extends AsyncTask<String, String, String>
{
	private ProgressDialog progressDialog;
	private SOURCE travelsSource;
	private List<Travel> travels;
	private MyTripsFragment fragment;

	public LoadOnlineTravelsTask(Activity activity, SOURCE source, boolean openDialog, MyTripsFragment myTripsFragment)
	{
		travelsSource = source;
		String messageToDisplay = null;
		fragment = myTripsFragment;
		travels = new ArrayList<Travel>();
		if(travelsSource == SOURCE.MY_TRAVELS)
		{
			messageToDisplay = "Loading Travels from the Internet...";
		}
		else if(travelsSource == SOURCE.RANKING)
		{
			messageToDisplay = "Loading Ranking from the Internet...";
		}
		if(openDialog)
		{
			progressDialog = new ProgressDialog(activity, messageToDisplay);
		}
	}
	
	@Override
	protected void onPreExecute()
	{
		if(progressDialog != null)
		{
			progressDialog.run();
		}
	}

	@Override
	protected String doInBackground(String... arg0)
	{
		if(travelsSource == SOURCE.MY_TRAVELS)
		{
			//DataContainer.LoadOnlineTravels();
			loadTravels();
		}
		else if(travelsSource == SOURCE.RANKING)
		{
			DataContainer.LoadRankingOnlineTravels();
		}
		return null;
	}

	private List<Travel> loadTravels()
	{
		try
		{
			String parameters = "/user/"+SessionHelper.getUserId()+"/trips";
			HttpResponse response = HttpHelper.get(AppParameters.SERVER_URL + parameters);
			if(response != null)
			{
				String respStr = HttpHelper.getStringFromResponse(response);
				JSONObject routesResp = new JSONObject(respStr);
				JSONArray routes = new JSONArray(routesResp.getString("routes"));
				if(routes != null && routes.length() > 0)
				{
					for(int i = 0; i < routes.length(); i++)
					{
						JSONObject route = routes.getJSONObject(i);
						
						//points
						JSONArray points = route.getJSONArray("points");
						if(points == null || points.length() < 2)
						{
							continue;
						}
						List<Point> pointsList = new ArrayList<Point>();
						for(int j = 0; j < points.length(); j++)
						{
							JSONObject point = points.getJSONObject(j);
							
							int id = point.getInt("id");
							String type = point.getString("type");
							String name = point.getString("name");
							double lat = point.getDouble("lat");
							double lng = point.getDouble("lng");
							
							PointType pointType = PointType.CITY;
							if(type.equals("Hotel"))
							{
								pointType = PointType.HOTEL;
							}
							
							Point p = new Point(id, name, lng, lat, j, pointType, null);
							pointsList.add(p);
						}
						
						//summary
						JSONObject summary = route.getJSONObject("summary");
						JSONObject distance = summary.getJSONObject("distance");
						//String distanceStr = distance.getString("text");
						int distanceInt = distance.getInt("value");
						double distanceDblKm = (double)(distanceInt / 1000);
						//JSONObject duration = summary.getJSONObject("duration");
						//String durationStr = duration.getString("text");
						//int durationInt = duration.getInt("value");
						
						//prefferences
						JSONObject prefferences = route.getJSONObject("prefferences");
						double budgetDbl = prefferences.getDouble("budget");
						String startDateStr = prefferences.getString("startDate");
						Calendar startCalendar = null;
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				        try
				        {
				        	Date startDate = simpleDateFormat.parse(startDateStr);
				            startCalendar = new GregorianCalendar();
				            startCalendar.setTime(startDate);
				        }
				        catch (ParseException ex)
				        {
				            ex.printStackTrace();
				        }
						
						String name = "Trasa " + i;
						if(pointsList.size() > 1)
						{
							name = pointsList.get(0).getName() + " - " + pointsList.get(pointsList.size() - 1).getName();
						}
						
						Travel t = new Travel(-1, pointsList, Rating.MEDIUM, startCalendar, 
								TransportType.CAR, distanceDblKm, budgetDbl, name);
						travels.add(t);
					}
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
	protected void onPostExecute(String result)
	{
		if(progressDialog != null)
		{
			progressDialog.stop();
		}
		for(Travel t : travels)
		{
			DataContainer.addOnlineTravel(t);
		}
		if(fragment != null)
		{
			fragment.displayOnlineTravels();
		}
	}
}
