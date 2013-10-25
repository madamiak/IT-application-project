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
        travels.add(new Travel("Wroc�aw", "Krak�w"));
        travels.add(new Travel("Wroc�aw", "Gda�sk"));
        travels.add(new Travel("Wroc�aw", "Pozna�"));
        travels.add(new Travel("Wroc�aw", "Warszawa"));
        travels.add(new Travel("Wroc�aw", "Suwa�ki"));
        travels.add(new Travel("Wroc�aw", "��d�"));
        travels.add(new Travel("Wroc�aw", "Katowice"));
        travels.add(new Travel("Wroc�aw", "Lublin"));
        travels.add(new Travel("Wroc�aw", "Bia�ystok"));
        travels.add(new Travel("Wroc�aw", "Toru�"));
        travels.add(new Travel("Wroc�aw", "Szczecin"));
        TravelAdapter tripAdapter = new TravelAdapter(getActivity(), travels);
        rankingList.setAdapter(tripAdapter);
        return rootView;
    }
}
