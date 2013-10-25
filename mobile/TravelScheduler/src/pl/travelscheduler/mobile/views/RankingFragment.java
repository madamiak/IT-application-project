package pl.travelscheduler.mobile.views;

import java.util.ArrayList;
import java.util.List;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.adapters.TravelAdapter;
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
        rankingList.setAdapter(tripAdapter);
        return rootView;
    }
}
