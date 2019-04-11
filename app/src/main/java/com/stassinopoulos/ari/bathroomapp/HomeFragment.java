package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private final int fragmentResource = R.layout.fragment_home;
    private ListView mRecentReportList;

    private List<ReceivedBathroom> mReports;
    private List<ReceivedBathroom> mRequests;
    private List<ReceivedBathroom> mUnifiedList;
    private HostActivity mHostActivity;

    public HomeFragment setHostActivity(HostActivity hostActivity) {
        this.mHostActivity = hostActivity;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(fragmentResource, container, false);
        mRecentReportList = fragmentView.findViewById(R.id.home_recent_reports_list);


        //TODO Get list of bathroom reports
        mReports = new ArrayList<>();
        mRequests = new ArrayList<>();
        mUnifiedList = new ArrayList<>();

        final RecentArrayAdapter arrayAdapter = new RecentArrayAdapter(getContext(), R.layout.component_unified_result_item);
        arrayAdapter.setUnifiedList(mUnifiedList);
        arrayAdapter.setButtonReceiver(new RecentArrayAdapter.ButtonReceiver() {
            @Override
            public void onReceive(ReceivedBathroom toDisputeOrResolve) {
                ReportFragment fragmentToSwitch = new ReportFragment().setHostActivity(mHostActivity).prefillForm(toDisputeOrResolve);

                mHostActivity.switchTab(HostActivity.HostTabType.REPORT_TAB, fragmentToSwitch);

            }
        });
        mRecentReportList.setAdapter(arrayAdapter);

        //mRecentReportList.setAdapter(new RecentActivityAdapter(getContext(), 0, new ArrayList<BathroomReport>()));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query reportsRef = database.getReference("reports").orderByChild("timestamp").limitToFirst(50);
        Query requestsRef = database.getReference("requests").orderByChild("timestamp").limitToFirst(50);

        ValueEventListener unifiedListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Object value = dataSnapshot.getValue();
                if (value == null) return;

                try {
                    switch (dataSnapshot.getKey()) {
                        case "reports":
                            mReports = new ArrayList<>();
                            if (!(value instanceof Map)) break;
                            Map<String, Object> newReports = (HashMap<String, Object>) value;
                            outerloop:
                            for (Object report : newReports.values()) {
                                if (!(report instanceof Map)) continue;

                                Map<String, Object> reportMap = (Map<String, Object>) report;

                                long receivedTimestamp = (Long) reportMap.get("timestamp");

                                if (reportMap.get("bathroom") == null || !(reportMap.get("bathroom") instanceof Map))
                                    break;

                                ReceivedBathroom reportBathroom = ReceivedBathroom.fromMap((Map<String, Object>) reportMap.get("bathroom"), receivedTimestamp);

                                for (ReceivedBathroom checkReport : mReports) {
                                    if (checkReport.matches(reportBathroom)) {
                                        checkReport.addCase(reportBathroom);
                                        continue outerloop;
                                    }
                                }

                                mReports.add(reportBathroom);
                            }
                            break;
                        case "requests":
                            mRequests = new ArrayList<>();
                            if (!(value instanceof Map)) break;
                            Map<String, Object> newRequests = (Map<String, Object>) value;
                            outerloop:
                            for (Object request : newRequests.values()) {
                                if (!(request instanceof Map)) continue;

                                Map<String, Object> requestMap = (Map<String, Object>) request;

                                long receivedTimestamp = (Long) requestMap.get("timestamp");

                                if (requestMap.get("bathroom") == null || !(requestMap.get("bathroom") instanceof Map))
                                    break;

                                ReceivedBathroom requestBathroom = ReceivedBathroom.fromMap((Map<String, Object>) requestMap.get("bathroom"), receivedTimestamp);

                                for (ReceivedBathroom checkRequest : mRequests) {
                                    if (checkRequest.matches(requestBathroom)) {
                                        checkRequest.addCase(requestBathroom);
                                        continue outerloop;
                                    }
                                }

                                mRequests.add(requestBathroom);

                            }
                            break;
                        default:
                            break;
                    }
                    List<ReceivedBathroom> tempList = new ArrayList<>();
                    Iterator<ReceivedBathroom> reqIter = mRequests.iterator();

                    while (reqIter.hasNext()) {
                        ReceivedBathroom rb = reqIter.next();
                        for (ReceivedBathroom report : mReports) {
                            if (report.matches(rb) && report.getReceivedTimestamp() > rb.getReceivedTimestamp()) {
                                reqIter.remove();
                            }
                        }
                    }

                    tempList.addAll(mRequests);
                    tempList.addAll(mReports);

                    tempList.sort(new Comparator<ReceivedBathroom>() {
                        @Override
                        public int compare(ReceivedBathroom t0, ReceivedBathroom t1) {
                            return (int) (t1.mReceivedTimestamp - t0.mReceivedTimestamp);
                        }
                    });
                    mUnifiedList = tempList;
                    System.out.println(mUnifiedList.size());
                    arrayAdapter.setUnifiedList(mUnifiedList);
                } catch (Exception e) {
                    e.printStackTrace();
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
