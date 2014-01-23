package pl.travelscheduler.mobile.listeners;

import pl.travelscheduler.mobile.activities.DetailsActivity;
import pl.travelscheduler.mobile.model.DataContainer;
import pl.travelscheduler.mobile.model.DataContainer.SOURCE;
import pl.travelscheduler.mobile.model.Travel;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

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
		else if (travelSource == SOURCE.RANKING)
		{
			if (!DataContainer.getRankingLocalTravels().isEmpty())
			{
				clickedTravel = DataContainer.getRankingLocalTravel(index);
			}
			else
			{
				clickedTravel = DataContainer.getRankingOnlineTravel(index);
			}
		}
		
		Intent intent = new Intent(activity, DetailsActivity.class);
		intent.putExtra("Route", clickedTravel);
		activity.startActivity(intent);
	}

}
