package pl.travelscheduler.mobile.tasks;

import pl.travelscheduler.mobile.dialogs.ProgressDialog;
import pl.travelscheduler.mobile.model.DataContainer;
import pl.travelscheduler.mobile.model.DataContainer.SOURCE;
import android.app.Activity;
import android.os.AsyncTask;

public class LoadOnlineTravelsTask extends AsyncTask<String, String, String>
{
	private ProgressDialog progressDialog;
	private SOURCE travelsSource;

	public LoadOnlineTravelsTask(Activity activity, SOURCE source)
	{
		travelsSource = source;
		String messageToDisplay = null;
		if(travelsSource == SOURCE.MY_TRAVELS)
		{
			messageToDisplay = "Loading Travels from the Internet...";
		}
		else
		{
			messageToDisplay = "Loading Ranking from the Internet...";
		}
		progressDialog = new ProgressDialog(activity, messageToDisplay);
	}
	
	@Override
	protected void onPreExecute()
	{
		progressDialog.run();
	}

	@Override
	protected String doInBackground(String... arg0)
	{
		if(travelsSource == SOURCE.MY_TRAVELS)
		{
			DataContainer.LoadOnlineTravels();
		}
		else
		{
			DataContainer.LoadRankingOnlineTravels();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result)
	{
		progressDialog.stop();
	}
}
