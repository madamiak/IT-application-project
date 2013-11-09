package pl.travelscheduler.mobile.views;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.adapters.TravelAdapter;
import pl.travelscheduler.mobile.model.Point;
import pl.travelscheduler.mobile.model.Rating;
import pl.travelscheduler.mobile.model.TransportType;
import pl.travelscheduler.mobile.model.Travel;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class MyTripsFragment extends Fragment 
{
	public MyTripsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_my_trips, container,
				false);
		ListView tripsList = (ListView) rootView
				.findViewById(R.id.my_trips_list);
		List<Travel> travels = new ArrayList<Travel>();
		Point wroclaw = new Point("Wroc³aw", 17.038333, 51.107778);
		Point katowice = new Point("Katowice", 19.0, 50.25);
		Point krakow = new Point("Kraków", 19.938333, 50.061389);
		Point poznan = new Point("Poznañ", 16.934278, 52.4085);
		Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
		Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);
		travels.add(new Travel(wroclaw, krakow, new Point[] { katowice },
				Rating.MEDIUM, firstStartingDate, TransportType.CAR, 280));
		travels.add(new Travel(wroclaw, poznan, null, Rating.NONE,
				secondStartingDate, TransportType.CAR, 180));
		TravelAdapter tripAdapter = new TravelAdapter(getActivity(), travels);
		tripsList.setAdapter(tripAdapter);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		inflater.inflate(R.menu.my_trips_actions, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case R.id.my_trips_action_refresh:
				Toast.makeText(getActivity(), "Refreshing...", Toast.LENGTH_LONG)
						.show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
