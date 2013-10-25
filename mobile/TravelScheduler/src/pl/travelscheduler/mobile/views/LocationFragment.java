package pl.travelscheduler.mobile.views;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.helpers.ServicesHelper;
import pl.travelscheduler.mobile.listeners.LocationUpdatesListener;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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
    private LocationManager locationManager;
    private Location location;
    private String provider;
    private LocationUpdatesListener locationListener;
    private Marker myMarker;

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
            // map.setMyLocationEnabled(true);
        }
        Activity activity = getActivity();
        checkInternet(activity);
        handleLocation(activity);
    }

    private void handleLocation(Activity activity)
    {
        if (activity != null)
        {
            if (ServicesHelper.isGpsEnabled(activity))
            {
                locationManager = (LocationManager) activity
                        .getSystemService(Context.LOCATION_SERVICE);
                getLocation();
                locationListener = new LocationUpdatesListener(myMarker);
            }
            else
            {
                Toast.makeText(
                        activity,
                        "Location services not enabled, cannot find your coordinates...",
                        Toast.LENGTH_SHORT).show();
            }
        }
        if (locationManager != null && provider != null
                && locationListener != null)
        {
            locationManager.requestLocationUpdates(provider, 500, 2,
                    locationListener);
        }
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

    private void getLocation()
    {
        if (locationManager != null)
        {
            Criteria crta = new Criteria();
            crta.setAccuracy(Criteria.ACCURACY_FINE);
            crta.setAltitudeRequired(false);
            crta.setBearingRequired(false);
            crta.setCostAllowed(true);
            crta.setPowerRequirement(Criteria.NO_REQUIREMENT);
            provider = locationManager.getBestProvider(crta, false);
            location = locationManager.getLastKnownLocation(provider);
            if (location != null && map != null)
            {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                LatLng coordinate = new LatLng(lat, lng);
                myMarker = map.addMarker(new MarkerOptions().position(
                        coordinate).icon(
                        BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_action_place)));
                
                CameraUpdate center = CameraUpdateFactory.newLatLng(coordinate);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                map.moveCamera(center);
                map.animateCamera(zoom);
            }
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (locationManager != null && locationListener != null)
        {
            locationManager.removeUpdates(locationListener);
        }
    }
}
