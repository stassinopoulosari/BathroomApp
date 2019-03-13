package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RequestFragment extends Fragment {

    private final int fragmentResource = R.layout.fragment_request;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentInflatedView = inflater.inflate(fragmentResource, container, false);

        FragmentTransaction formFragmentTransaction = getFragmentManager().beginTransaction();
        formFragmentTransaction.replace(R.id.request_bathroom_form_frame, new BathroomForm().setHasStatusField(false));
        formFragmentTransaction.commit();

        return fragmentInflatedView;
    }
}
