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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
	
	private final int DELETE_ITEM = 100;
	private final int DOWNLOAD_ITEM = 200;
	
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

		registerForContextMenu(tripsList);
		registerForContextMenu(tripsOnlineList);
		
		displayLocalTravels();		
		
		displayOnlineTravels();
		
		return rootView;
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		if(v == tripsList)
		{
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			Travel selectedTravel = DataContainer.getRankingLocalTravel(info.position);
		    menu.setHeaderTitle(selectedTravel.getName());
		    menu.add(Menu.NONE, DELETE_ITEM, 0, R.string.trip_local_context_item_delete);
		}
		if(v == tripsOnlineList)
		{
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			Travel selectedTravel = DataContainer.getRankingOnlineTravel(info.position);
			menu.setHeaderTitle(selectedTravel.getName());
		    menu.add(Menu.NONE, DOWNLOAD_ITEM, 0, R.string.trip_online_context_item_download);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		if(menuItemIndex == DELETE_ITEM)
		{
			Travel selectedTravel = DataContainer.getRankingLocalTravel(info.position);
			DataContainer.removeRankingTravel(selectedTravel, getActivity());
			displayLocalTravels();
			return true;
		}
		else if(menuItemIndex == DOWNLOAD_ITEM)
		{
			Travel selectedTravel = DataContainer.getRankingOnlineTravel(info.position);
			DataContainer.addRankingTravelToLocal(selectedTravel, getActivity());
			displayLocalTravels();
			displayOnlineTravels();
			return true;
		}
		return false;
	}
    
    private void displayLocalTravels()
	{
		List<Travel> localTravels = DataContainer.getRankingLocalTravels();
		if(localTravels != null && localTravels.size() > 0)
		{
			tripsList.setVisibility(View.VISIBLE);
			txtVNoLocalTrips.setVisibility(View.GONE);
			TravelAdapter tripAdapter = new TravelAdapter(getActivity(), localTravels);
			tripsList.setAdapter(tripAdapter);
			tripsList.setOnItemClickListener(new LocalTravelOnItemClickListener(getActivity(), SOURCE.RANKING));
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
			tripsOnlineList.setOnItemClickListener(new LocalTravelOnItemClickListener(getActivity(), SOURCE.RANKING));
			
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
		        	LoadOnlineTravelsTask task = new LoadOnlineTravelsTask(getActivity(), SOURCE.RANKING, true, null);
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
