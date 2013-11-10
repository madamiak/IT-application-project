package pl.travelscheduler.mobile.views;

import java.util.List;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.adapters.TravelAdapter;
import pl.travelscheduler.mobile.helpers.ServicesHelper;
import pl.travelscheduler.mobile.listeners.LocalTravelOnItemClickListener;
import pl.travelscheduler.mobile.model.DataContainer;
import pl.travelscheduler.mobile.model.DataContainer.SOURCE;
import pl.travelscheduler.mobile.model.Travel;
import pl.travelscheduler.mobile.tasks.LoadOnlineTravelsTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RankingFragment extends Fragment
{
	private TextView txtVLocalLabel;
	private ListView tripsList;
	private TextView txtVOnlineLabel;
	private ListView tripsOnlineList;
	private TextView txtVNoLocalTrips;
	
    public RankingFragment() 
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
    {
    	setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_ranking, container,
				false);
		txtVLocalLabel = (TextView) rootView.findViewById(R.id.ranking_local_label);
		tripsList = (ListView) rootView
				.findViewById(R.id.ranking_list);
		txtVOnlineLabel = (TextView) rootView.findViewById(R.id.ranking_online_label);
		tripsOnlineList = (ListView) rootView
				.findViewById(R.id.ranking_to_download);
		txtVNoLocalTrips = (TextView) rootView.findViewById(R.id.ranking_no_local_trips);
		
		displayLocalTravels();		
		
		displayOnlineTravels();
		
		return rootView;
    }
    
    private void displayLocalTravels()
	{
		List<Travel> localTravels = DataContainer.getLocalTravels();
		if(localTravels != null && localTravels.size() > 0)
		{
			tripsList.setVisibility(View.VISIBLE);
			txtVNoLocalTrips.setVisibility(View.GONE);
			TravelAdapter tripAdapter = new TravelAdapter(getActivity(), localTravels);
			tripsList.setAdapter(tripAdapter);
			tripsList.setOnItemClickListener(new LocalTravelOnItemClickListener(getActivity(), SOURCE.MY_TRAVELS));
		}
		else
		{
			txtVNoLocalTrips.setVisibility(View.VISIBLE);
			tripsList.setVisibility(View.GONE);
		}
	}

	private void displayOnlineTravels()
	{
		List<Travel> onlineTravels = DataContainer.getRankingOnlineTravels();		
		if(onlineTravels != null && onlineTravels.size() > 0)
		{
			showOnlineTravels(true);		
			TravelAdapter onlineTripsAdapter = new TravelAdapter(getActivity(), onlineTravels);
			tripsOnlineList.setAdapter(onlineTripsAdapter);
		}
		else
		{
			showOnlineTravels(false);
		}
	}
	
	private void showOnlineTravels(boolean show)
	{
		int visibility;
		if(show)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.GONE;
		}
		txtVLocalLabel.setVisibility(visibility);
		txtVOnlineLabel.setVisibility(visibility);
		tripsOnlineList.setVisibility(visibility);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		inflater.inflate(R.menu.ranking_actions, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case R.id.ranking_action_refresh:
		        if(ServicesHelper.isInternetEnabled(getActivity()))
		        {
		        	LoadOnlineTravelsTask task = new LoadOnlineTravelsTask(getActivity(), SOURCE.RANKING);
		        	task.execute((String)null);
		        }
		        else
		        {
		        	Toast.makeText(getActivity(), "No Internet connection...", Toast.LENGTH_SHORT).show();
		        }
				displayOnlineTravels();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
