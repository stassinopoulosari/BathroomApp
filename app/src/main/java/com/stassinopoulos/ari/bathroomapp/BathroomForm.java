package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Space;

public class BathroomForm extends Fragment {

    private View mBathroomFormView;
    private AdapterView mReportBuildingSpinner;
    private AppCompatSpinner mFloorSpinner;
    private AppCompatSpinner mGenderSpinner;
    private AppCompatButton mSubmitButton;

    private AppCompatSpinner mStatusSpinner;
    private AppCompatTextView mStatusHeader;
    private Space mStatusSpace;

    private boolean mHasStatusField = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBathroomFormView = inflater.inflate(R.layout.component_bathroom_form, container, false);
        setUp();
        return mBathroomFormView;
    }

    private void setUp() {
        mReportBuildingSpinner = mBathroomFormView.findViewById(R.id.b_form_building_spinner);
        mFloorSpinner = mBathroomFormView.findViewById(R.id.b_form_floor_spinner);
        mGenderSpinner = mBathroomFormView.findViewById(R.id.b_form_gender_spinner);
        mSubmitButton = mBathroomFormView.findViewById(R.id.b_form_submit_button);

        mStatusSpinner = mBathroomFormView.findViewById(R.id.b_form_status_spinner);
        mStatusHeader = mBathroomFormView.findViewById(R.id.b_form_status_header);
        mStatusSpace = mBathroomFormView.findViewById(R.id.b_form_status_space);

        if (!mHasStatusField) {
            View[] statusElements = {mStatusSpinner, mStatusHeader, mStatusSpace};
            for (View statusElement : statusElements) {
                statusElement.setVisibility(View.GONE);
            }
        }

        final Bathroom.Building[] buildingChoices = Bathroom.Building.values();
        Bathroom.Gender[] genderChoices = Bathroom.Gender.values();
        Integer[] floorChoices = {1, 2};

        mReportBuildingSpinner.setAdapter(new AdaptableArrayAdapter(getContext(), android.R.layout.simple_spinner_item).setAdaptables(buildingChoices));
        mFloorSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, floorChoices));
        mGenderSpinner.setAdapter(new AdaptableArrayAdapter(getContext(), android.R.layout.simple_spinner_item, false).setAdaptables(genderChoices));

        if (mHasStatusField) {
            Bathroom.Status[] statusChoices = Bathroom.Status.values();
            mStatusSpinner.setAdapter(new AdaptableArrayAdapter(getContext(), android.R.layout.simple_spinner_item).setAdaptables(statusChoices));
        }

        mReportBuildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Bathroom.Building selectedItem = (Bathroom.Building) mReportBuildingSpinner.getSelectedItem();
                if (selectedItem == null) return;
                if (selectedItem.getNumberOfFloors() == 2) {
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


                Log.d("BUILDING", building.getTextValue());
                Log.d("FLOOR", String.valueOf(floor));
                Log.d("GENDER", gender.getTextValue());
                if (mHasStatusField) {
                    Bathroom.Status status = (Bathroom.Status) mStatusSpinner.getSelectedItem();
                    Log.d("STATUS", status.getTextValue());
                }
            }

        });
    }

    public BathroomForm setHasStatusField(boolean hasStatusField) {
        mHasStatusField = hasStatusField;
        return this;
    }
}