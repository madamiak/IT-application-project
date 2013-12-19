package pl.travelscheduler.mobile.activities;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.model.Travel;
import pl.travelscheduler.mobile.views.RouteFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DetailsActivity extends FragmentActivity
{
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details);
    	Travel selected = (Travel) this.getIntent().getSerializableExtra("Route");
    	RouteFragment.travel=selected;
    	initFragment(new RouteFragment(), selected);
    }

    protected void initFragment(RouteFragment routeFragment, Travel t) {
    	android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    	android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    	fragmentTransaction.replace(R.id.det, routeFragment);
    	fragmentTransaction.commit();
    	
    }
}
