package pl.travelscheduler.mobile.views;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.adapters.TravelAdapter;
import pl.travelscheduler.mobile.model.Point;
import pl.travelscheduler.mobile.model.Rating;
import pl.travelscheduler.mobile.model.TransportType;
import pl.travelscheduler.mobile.model.Travel;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RankingFragment extends Fragment
{
    public RankingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
        ListView rankingList = (ListView)rootView.findViewById(R.id.ranking_list);
        List<Travel> travels = new ArrayList<Travel>();
        Point wroclaw = new Point("Wroc³aw", 17.038333, 51.107778);
        Point krakow = new Point("Kraków", 19.938333, 50.061389);
        Point poznan = new Point("Poznañ", 16.934278, 52.4085);
        Calendar firstStartingDate = new GregorianCalendar(2013, 11, 11, 12, 00);
        Calendar secondStartingDate = new GregorianCalendar(2013, 10, 1, 17, 00);
        travels.add(new Travel(wroclaw, krakow, null, Rating.HIGH, firstStartingDate, TransportType.CAR, 180));
        travels.add(new Travel(wroclaw, poznan, null, Rating.LOW, secondStartingDate, TransportType.CAR, 273));
        TravelAdapter tripAdapter = new TravelAdapter(getActivity(), travels);
        rankingList.setAdapter(tripAdapter);
        return rootView;
    }
}
