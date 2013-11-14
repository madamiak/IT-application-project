package pl.travelscheduler.mobile.tasks;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import pl.travelscheduler.mobile.AppParameters;
import pl.travelscheduler.mobile.helpers.HttpHelper;
import pl.travelscheduler.mobile.helpers.SessionHelper;
import android.os.AsyncTask;

public class LoginTask extends AsyncTask<String, Void, Integer>
{

	@Override
	protected Integer doInBackground(String... args)
	{
		try
		{
			String parameters = "?login="+args[0]+"&password="+args[1];
			HttpResponse response = HttpHelper.post(AppParameters.SERVER_URL + 
					AppParameters.AUTHENTICATE_SERVICE +  parameters, null);
			if(response != null)
			{
				String respStr = HttpHelper.getStringFromResponse(response);
				JSONObject authResp = new JSONObject(respStr);
				String code = authResp.getString("code");
				JSONObject data = new JSONObject(authResp.getString("data"));
				
				if(code.equals("OK"))
				{
					String userId = data.getString("userId");
					SessionHelper.setUserId(userId);
					return 1;
				}
				else
				{
					return 0;
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
}
