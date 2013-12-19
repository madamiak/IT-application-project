package pl.travelscheduler.mobile.views;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.adapters.PointAdapter;
import pl.travelscheduler.mobile.dialogs.LoginDialog;
import pl.travelscheduler.mobile.helpers.ServicesHelper;
import pl.travelscheduler.mobile.helpers.SessionHelper;
import pl.travelscheduler.mobile.listeners.LocationUpdatesListener;
import pl.travelscheduler.mobile.model.DataContainer;
import pl.travelscheduler.mobile.model.DataContainer.SOURCE;
import pl.travelscheduler.mobile.model.Point;
import pl.travelscheduler.mobile.model.Travel;
import pl.travelscheduler.mobile.tasks.LoadOnlineTravelsTask;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RouteFragment extends Fragment {
	private TextView txtRouteLabel;
	private ListView poiList;
	private TextView txtRouteNoRoute;
	private SupportMapFragment fragment;
	private GoogleMap map;
	private LocationManager locationManager;
	private Location location;
	private String provider;
	private LocationUpdatesListener locationListener;
	private Marker myMarker;
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

		displayRoute();

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
			// map.setMyLocationEnabled(true);
		}
		Activity activity = getActivity();
		checkInternet(activity);
		handleLocation(activity);
	}

	private void handleLocation(Activity activity) {
		if (activity != null) {
			if (ServicesHelper.isGpsEnabled(activity)) {
				locationManager = (LocationManager) activity
						.getSystemService(Context.LOCATION_SERVICE);
				getLocation();
				locationListener = new LocationUpdatesListener(myMarker);
			} else {
				Toast.makeText(
						activity,
						"Location services not enabled, cannot find your coordinates...",
						Toast.LENGTH_SHORT).show();
			}
		}
		if (locationManager != null && provider != null
				&& locationListener != null) {
			locationManager.requestLocationUpdates(provider, 500, 2,
					locationListener);
		}
	}

	private void checkInternet(Activity activity) {
		if (activity != null && !ServicesHelper.isInternetEnabled(activity)) {
			Toast.makeText(activity,
					"No Internet connection, working offline...",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void getLocation() {
		if (locationManager != null) {
			Criteria crta = new Criteria();
			crta.setAccuracy(Criteria.ACCURACY_FINE);
			crta.setAltitudeRequired(false);
			crta.setBearingRequired(false);
			crta.setCostAllowed(true);
			crta.setPowerRequirement(Criteria.NO_REQUIREMENT);
			provider = locationManager.getBestProvider(crta, false);
			location = locationManager.getLastKnownLocation(provider);
			if (location != null && map != null) {
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
		Point selectedPoint = travel.getPoints().get(info.position);
		return true;
	}

	private void displayRoute() {
		if (travel != null) {
			poiList.setVisibility(View.VISIBLE);
			txtRouteNoRoute.setVisibility(View.GONE);
			PointAdapter pointAdapter = new PointAdapter(getActivity(),
					travel.getPoints());
			poiList.setAdapter(pointAdapter);
		} else {
			txtRouteNoRoute.setVisibility(View.VISIBLE);
			poiList.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		if (SessionHelper.isUserLoggedIn()) {
			menu.add(Menu.NONE, R.id.my_trips_action_refresh, 0,
					R.string.my_trips_actions_refresh)
					.setIcon(R.drawable.ic_action_refresh)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add(Menu.NONE, R.id.my_trips_action_logout, 1,
					R.string.my_trips_actions_logout)
					.setIcon(R.drawable.ic_action_logout)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		} else {
			menu.add(Menu.NONE, R.id.my_trips_action_login, 1,
					R.string.my_trips_actions_login)
					.setIcon(R.drawable.ic_action_person)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.my_trips_action_refresh:
			if (ServicesHelper.isInternetEnabled(getActivity())) {
				if (SessionHelper.isUserLoggedIn()) {
					LoadOnlineTravelsTask task = new LoadOnlineTravelsTask(
							getActivity(), SOURCE.MY_TRAVELS);
					task.execute((String) null);
				} else {
					Toast.makeText(getActivity(),
							"You have to log in first...", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(getActivity(), "No Internet connection...",
						Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.my_trips_action_login:
			if (ServicesHelper.isInternetEnabled(getActivity())) {
				if (!SessionHelper.isUserLoggedIn()) {
					LoginDialog dlg = new LoginDialog(getActivity());
					dlg.show();
				}
			} else {
				Toast.makeText(getActivity(), "No Internet connection...",
						Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.my_trips_action_logout:
			SessionHelper.logOut();
			DataContainer.clearOnlineTravels();
			getActivity().invalidateOptionsMenu();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
