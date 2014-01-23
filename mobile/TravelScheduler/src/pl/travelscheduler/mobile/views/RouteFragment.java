package pl.travelscheduler.mobile.views;

import java.util.List;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.adapters.PointAdapter;
import pl.travelscheduler.mobile.helpers.ServicesHelper;
import pl.travelscheduler.mobile.listeners.LocationUpdatesListener;
import pl.travelscheduler.mobile.model.Point;
import pl.travelscheduler.mobile.model.Travel;
import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RouteFragment extends Fragment {
	private TextView txtRouteLabel;
	private ListView poiList;
	private TextView txtRouteNoRoute;
	private SupportMapFragment fragment;
	private GoogleMap map;
	private LocationManager locationManager;
	private LocationUpdatesListener locationListener;
	public static Travel travel;

	private final int SHOW_ON_MAP = 100;

	public RouteFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_route, container,
				false);
		txtRouteLabel = (TextView) rootView.findViewById(R.id.route_label);
		poiList = (ListView) rootView.findViewById(R.id.poi_list);
		txtRouteNoRoute = (TextView) rootView.findViewById(R.id.route_no_route);

		registerForContextMenu(poiList);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentManager fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_route);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.map_route, fragment).commit();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (map == null) {
			map = fragment.getMap();
			map.setMyLocationEnabled(true);
		}
		Activity activity = getActivity();
		checkInternet(activity);
		displayRoute();
	}

	private void checkInternet(Activity activity) {
		if (activity != null && !ServicesHelper.isInternetEnabled(activity)) {
			Toast.makeText(activity,
					"No Internet connection, working offline...",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (locationManager != null && locationListener != null) {
			locationManager.removeUpdates(locationListener);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		Point selectedPoint = travel.getPoints().get(info.position);
		menu.setHeaderTitle(selectedPoint.getName());
		menu.add(Menu.NONE, SHOW_ON_MAP, 0, R.string.poi_show_on_map);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		travel.getPoints().get(info.position);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" +travel.getPoints().get(info.position).getLatitude()+","+travel.getPoints().get(info.position).getLongitude()));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);		
		return true;
	}

	private void displayRoute() {
		if (travel != null) {
			poiList.setVisibility(View.VISIBLE);
			txtRouteNoRoute.setVisibility(View.GONE);
			List<Point> points = travel.getPoints();
			PointAdapter pointAdapter = new PointAdapter(getActivity(), points);
			poiList.setAdapter(pointAdapter);
			if(points != null && map != null)
			{
				for(Point p : points)
				{
					LatLng coordinate = new LatLng(p.getLatitude(), p.getLongitude());
	                map.addMarker(new MarkerOptions()
	                		.position(coordinate)
	                        .icon(BitmapDescriptorFactory
	                                .fromResource(R.drawable.ic_action_place))
	                        .title(p.getName()));
				}
			}
		} else {
			txtRouteNoRoute.setVisibility(View.VISIBLE);
			poiList.setVisibility(View.GONE);
		}
	}
}
