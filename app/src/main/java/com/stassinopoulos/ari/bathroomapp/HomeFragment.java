package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final int fragmentResource = R.layout.fragment_home;
    private ListView mRecentReportList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(fragmentResource, container, false);
        mRecentReportList = fragmentView.findViewById(R.id.home_recent_reports_list);

        //TODO Get list of bathroom reports
        mRecentReportList.setAdapter(new RecentActivityAdapter(getContext(), 0, new ArrayList<BathroomReport>()));

        return fragmentView;

    }
}
