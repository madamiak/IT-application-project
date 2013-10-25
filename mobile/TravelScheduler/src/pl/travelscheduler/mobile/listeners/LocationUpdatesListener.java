package pl.travelscheduler.mobile.listeners;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationUpdatesListener implements LocationListener
{
    private Marker currentMarker;

    public LocationUpdatesListener(Marker myMarker)
    {
        if(myMarker != null)
        {
            currentMarker = myMarker;            
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if (currentMarker != null)
        {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            LatLng coordinate = new LatLng(lat, lng);
            currentMarker.setPosition(coordinate);
        }
    }

    @Override
    public void onProviderDisabled(String provider)
    {
    }

    @Override
    public void onProviderEnabled(String provider)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }

}
