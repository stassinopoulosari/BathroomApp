package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ReportFragment extends Fragment {

    private final int fragmentResource = R.layout.fragment_report;
    private Spinner mReportBuildingSpinner;
    private Spinner mFloorSpinner;
    private Spinner mGenderSpinner;
    private Spinner mStatusSpinner;
    private Button mSubmitButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentInflatedView = inflater.inflate(fragmentResource, container, false);

        mReportBuildingSpinner = fragmentInflatedView.findViewById(R.id.report_building_spinner);
        mFloorSpinner = fragmentInflatedView.findViewById(R.id.report_floor_spinner);
        mGenderSpinner = fragmentInflatedView.findViewById(R.id.report_gender_spinner);
        mStatusSpinner = fragmentInflatedView.findViewById(R.id.report_status_spinner);
        mSubmitButton = fragmentInflatedView.findViewById(R.id.report_submit_button);

        final Bathroom.Building[] buildingChoices = Bathroom.Building.values();
        Bathroom.Gender[] genderChoices = Bathroom.Gender.values();
        Integer[] floorChoices = {1, 2};
        Bathroom.Status[] statusChoices = Bathroom.Status.values();

        mReportBuildingSpinner.setAdapter(new AdaptableArrayAdapter(getContext(), android.R.layout.simple_spinner_item).setAdaptables(buildingChoices));
        mFloorSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, floorChoices));
        mGenderSpinner.setAdapter(new AdaptableArrayAdapter(getContext(), android.R.layout.simple_spinner_item, false).setAdaptables(genderChoices));
        mStatusSpinner.setAdapter(new AdaptableArrayAdapter(getContext(), android.R.layout.simple_spinner_item).setAdaptables(statusChoices));

        mReportBuildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (buildingChoices[i] == null) return;
                if (buildingChoices[i].getNumberOfFloors() == 2) {
                    mFloorSpinner.setEnabled(true);
                } else {
                    mFloorSpinner.setEnabled(false);
                    mFloorSpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        mFloorSpinner.setEnabled(false);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Bathroom.Adaptable[] adaptables = {(Bathroom.Adaptable) mReportBuildingSpinner.getSelectedItem(), (Bathroom.Adaptable) mGenderSpinner.getSelectedItem(), (Bathroom.Adaptable) mStatusSpinner.getSelectedItem()};
                for (Bathroom.Adaptable adaptable : adaptables) {
                    if (adaptable == null) return;
                }

                Bathroom.Building building = (Bathroom.Building) mReportBuildingSpinner.getSelectedItem();
                int floor = (Integer) mFloorSpinner.getSelectedItem();
                Bathroom.Gender gender = (Bathroom.Gender) mGenderSpinner.getSelectedItem();
                Bathroom.Status status = (Bathroom.Status) mStatusSpinner.getSelectedItem();

                Log.d("BUILDING", building.getTextValue());
                Log.d("FLOOR", String.valueOf(floor));
                Log.d("GENDER", gender.getTextValue());
                Log.d("STATUS", status.getTextValue());
            }

        });

        return fragmentInflatedView;
    }
}
