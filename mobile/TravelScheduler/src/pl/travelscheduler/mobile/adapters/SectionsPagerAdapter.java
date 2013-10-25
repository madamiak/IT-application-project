package pl.travelscheduler.mobile.adapters;

import java.util.Locale;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.views.LocationFragment;
import pl.travelscheduler.mobile.views.MyTripsFragment;
import pl.travelscheduler.mobile.views.RankingFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SectionsPagerAdapter(FragmentManager fm,
            Context main)
    {
        super(fm);
        mContext = main;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DummySectionFragment (defined as a static inner class
        // below) with the page number as its lone argument.
        if(position == 0)
        {
            Fragment fragment = new MyTripsFragment();
            return fragment;
        }
        else if(position == 1)
        {
            Fragment fragment = new RankingFragment();
            return fragment;
        }
        else if (position == 2)
        {
            Fragment fragment = new LocationFragment();
            return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_my_trips).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.tab_ranking).toUpperCase(l);
            case 2:
                return mContext.getString(R.string.tab_location).toUpperCase(l);
        }
        return null;
    }
}
