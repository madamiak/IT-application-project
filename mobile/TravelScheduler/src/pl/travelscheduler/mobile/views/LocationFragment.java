package pl.travelscheduler.mobile.views;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.helpers.ServicesHelper;
import pl.travelscheduler.mobile.listeners.MapLocationChangesListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class LocationFragment extends Fragment
{

    private SupportMapFragment fragment;
    private GoogleMap map;
    
    public LocationFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_location, container,
                false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null)
        {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (map == null)
        {
            map = fragment.getMap();
            map.setMyLocationEnabled(true);
            map.setOnMyLocationChangeListener(new MapLocationChangesListener(map, getActivity()));
        }
        checkInternet(getActivity());
    }

    private void checkInternet(Activity activity)
    {
        if (activity != null && !ServicesHelper.isInternetEnabled(activity))
        {
            Toast.makeText(activity,
                    "No Internet connection, working offline...",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
