package pl.travelscheduler.mobile.activities;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.helpers.ServicesHelper;
import pl.travelscheduler.mobile.helpers.SessionHelper;
import pl.travelscheduler.mobile.model.DataContainer;
import pl.travelscheduler.mobile.model.DataContainer.SOURCE;
import pl.travelscheduler.mobile.tasks.LoadOnlineTravelsTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity
{
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        
        DataContainer.LoadTravels(this);
        DataContainer.LoadRankingTravels(this);
        if(ServicesHelper.isInternetEnabled(this))
        {            
            SessionHelper.firstLogIn(this);
            SessionHelper.loadUserNames(this);
        	
        	DataContainer.LoadRankingOnlineTravels();
        	if(SessionHelper.isUserLoggedIn())
        	{
        		LoadOnlineTravelsTask task = new LoadOnlineTravelsTask(this, SOURCE.MY_TRAVELS, false, null);
				task.execute();
        	}
        }
        
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
