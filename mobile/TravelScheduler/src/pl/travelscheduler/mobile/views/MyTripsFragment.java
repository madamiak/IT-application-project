package pl.travelscheduler.mobile.views;

import java.util.ArrayList;
import java.util.List;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.adapters.TravelAdapter;
import pl.travelscheduler.mobile.model.Travel;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class MyTripsFragment extends Fragment
{
    public MyTripsFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_my_trips, container,
                false);
        ListView tripsList = (ListView) rootView
                .findViewById(R.id.my_trips_list);
        List<Travel> travels = new ArrayList<Travel>();
        travels.add(new Travel("Wroc³aw", "Kraków"));
        travels.add(new Travel("Wroc³aw", "Gdañsk"));
        travels.add(new Travel("Wroc³aw", "Poznañ"));
        travels.add(new Travel("Wroc³aw", "Warszawa"));
        travels.add(new Travel("Wroc³aw", "Suwa³ki"));
        travels.add(new Travel("Wroc³aw", "£ódŸ"));
        travels.add(new Travel("Wroc³aw", "Katowice"));
        travels.add(new Travel("Wroc³aw", "Lublin"));
        travels.add(new Travel("Wroc³aw", "Bia³ystok"));
        travels.add(new Travel("Wroc³aw", "Toruñ"));
        travels.add(new Travel("Wroc³aw", "Szczecin"));
        TravelAdapter tripAdapter = new TravelAdapter(getActivity(), travels);
        tripsList.setAdapter(tripAdapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.my_trips_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.my_trips_action_refresh:
            Toast.makeText(getActivity(), "Refreshing...", Toast.LENGTH_LONG)
                    .show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
