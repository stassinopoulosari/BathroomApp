package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportFragment extends Fragment {

    private final int fragmentResource = R.layout.fragment_report;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentInflatedView = inflater.inflate(fragmentResource, container, false);

        FragmentTransaction formFragmentTransaction = getFragmentManager().beginTransaction();
        formFragmentTransaction.replace(R.id.report_form_fragment_frame, new BathroomForm().setHasStatusField(true));
        formFragmentTransaction.commit();

        return fragmentInflatedView;
    }
}
