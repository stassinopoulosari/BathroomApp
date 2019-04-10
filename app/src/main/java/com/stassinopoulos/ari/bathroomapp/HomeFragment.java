package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private final int fragmentResource = R.layout.fragment_home;
    private ListView mRecentReportList;

    private List<ReceivedBathroom> mReports;
    private List<ReceivedBathroom> mRequests;
    private List<ReceivedBathroom> unifiedList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(fragmentResource, container, false);
        mRecentReportList = fragmentView.findViewById(R.id.home_recent_reports_list);

        //TODO Get list of bathroom reports
        mReports = new ArrayList<>();
        mRequests = new ArrayList<>();
        
        //mRecentReportList.setAdapter(new RecentActivityAdapter(getContext(), 0, new ArrayList<BathroomReport>()));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query reportsRef = database.getReference("reports").orderByChild("timestamp").limitToFirst(5);
        Query requestsRef = database.getReference("mRequests").orderByChild("timestamp").limitToFirst(5);

        ValueEventListener unifiedListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Object value = dataSnapshot.getValue();
                if(value == null) return;

                try {
                    switch (dataSnapshot.getKey()) {
                        case "reports":
                            mReports = new ArrayList<>();
                            if (!(value instanceof Map)) break;
                            Map<String, Object> newReports = (HashMap<String, Object>) value;
                            for (Object report : newReports.values()) {
                                if (!(report instanceof Map)) continue;

                                Map<String, Object> reportMap = (Map<String, Object>) report;

                                long receivedTimestamp = (Long) reportMap.get("timestamp");

                                if (reportMap.get("bathroom") == null || !(reportMap.get("bathroom") instanceof Map))
                                    break;

                                ReceivedBathroom reportBathroom = ReceivedBathroom.fromMap((Map<String, Object>) reportMap.get("bathroom"), receivedTimestamp);

                                mReports.add(reportBathroom);
                            }
                            break;
                        case "mRequests":
                            mRequests = new ArrayList<>();
                            if (!(value instanceof Map)) break;
                            Map<String, Object> newRequests = (Map<String, Object>) value;
                            for (Object request : newRequests.values()) {
                                if (!(request instanceof Map)) continue;

                                Map<String, Object> requestMap = (Map<String, Object>) request;

                                long receivedTimestamp = (Long) requestMap.get("timestamp");

                                if (requestMap.get("bathroom") == null || !(requestMap.get("bathroom") instanceof Map))
                                    break;

                                ReceivedBathroom requestBathroom = ReceivedBathroom.fromMap((Map<String, Object>) requestMap.get("bathroom"), receivedTimestamp);

                                mRequests.add(requestBathroom);

                                unifiedList = new ArrayList<>();
                                unifiedList.addAll(mRequests);
                                unifiedList.addAll(mReports);
                                unifiedList.sort(new Comparator<ReceivedBathroom>() {
                                    @Override
                                    public int compare(ReceivedBathroom t0, ReceivedBathroom t1) {
                                        return (int) (t0.mReceivedTimestamp - t1.mReceivedTimestamp);
                                    }
                                });
                            }
                            break;
                        default:
                            break;
                    }
                } catch(Exception e) {
                    Log.e("Home", e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        reportsRef.addValueEventListener(unifiedListener);
        requestsRef.addValueEventListener(unifiedListener);

        return fragmentView;

    }
}
