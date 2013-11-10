package pl.travelscheduler.mobile.listeners;

import pl.travelscheduler.mobile.model.DataContainer;
import pl.travelscheduler.mobile.model.DataContainer.SOURCE;
import pl.travelscheduler.mobile.model.Travel;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class LocalTravelOnItemClickListener implements OnItemClickListener
{	
	private Activity activity;
	private SOURCE travelSource;
	
	public LocalTravelOnItemClickListener(Activity activity, SOURCE source)
	{
		this.activity = activity;  
		travelSource = source;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int index, long id)
	{
		Travel clickedTravel = null;
		if(travelSource == SOURCE.MY_TRAVELS)
		{
			clickedTravel = DataContainer.getLocalTravel(index);
		}
		else
		{
			clickedTravel = DataContainer.getRankingLocalTravel(index);
		}
		
		//TODO: run details activity
		
		Toast.makeText(activity, "Selected travel: " + clickedTravel.getSource() +
				" - " + clickedTravel.getDestination(), Toast.LENGTH_SHORT).show();
	}

}
