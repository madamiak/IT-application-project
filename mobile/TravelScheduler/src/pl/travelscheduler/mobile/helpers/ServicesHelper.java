package pl.travelscheduler.mobile.helpers;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ServicesHelper
{
    public static boolean isInternetEnabled(Activity activity)
    {
        ConnectivityManager cm = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

    public static boolean isGpsEnabled(Activity activity)
    {
        LocationManager locationManager = (LocationManager) activity
                .getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            return false;
        }
        return true;
    }
}