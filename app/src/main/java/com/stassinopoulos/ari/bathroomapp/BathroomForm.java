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
import android.widget.Toast;

public class BathroomForm extends Fragment {

    private View mBathroomFormView;
    private Runnable mOnloadRunnable;
    private AdapterView mReportBuildingSpinner;
    private AppCompatSpinner mFloorSpinner;
    private AppCompatSpinner mGenderSpinner;
    private AppCompatButton mSubmitButton;
    private AppCompatSpinner mStatusSpinner;
    private AppCompatTextView mStatusHeader;
    private Space mStatusSpace;
    private boolean mHasStatusField = true;
    private BathroomFormListener mBathroomFormListener;

    public AdapterView getReportBuildingSpinner() {
        return mReportBuildingSpinner;
    }

    public AppCompatSpinner getFloorSpinner() {
        return mFloorSpinner;
    }

    public AppCompatSpinner getGenderSpinner() {
        return mGenderSpinner;
    }

    public AppCompatSpinner getStatusSpinner() {
        return mStatusSpinner;
    }

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
                Bathroom.Adaptable[] adaptablesWOStatus = {(Bathroom.Adaptable) mReportBuildingSpinner.getSelectedItem(), (Bathroom.Adaptable) mGenderSpinner.getSelectedItem()};

                for (Bathroom.Adaptable adaptable : (mHasStatusField ? adaptables : adaptablesWOStatus)) {
                    if (adaptable == null) {
                        if (mReportBuildingSpinner.getSelectedItem() == null) {
                            Toast.makeText(getContext(), "Please select a building", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (mStatusSpinner.getSelectedItem() == null && mHasStatusField) {
                            Toast.makeText(getContext(), "Please select a status", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getContext(), "Something has gone wrong with the form submission.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Bathroom.Building building = (Bathroom.Building) mReportBuildingSpinner.getSelectedItem();
                int floor = (Integer) mFloorSpinner.getSelectedItem();
                Bathroom.Gender gender = (Bathroom.Gender) mGenderSpinner.getSelectedItem();
                Bathroom.Status status = null;

                Bathroom bathroom = new Bathroom(building, floor, gender);
                Log.d("BUILDING", building.getTextValue());
                Log.d("FLOOR", String.valueOf(floor));
                Log.d("GENDER", gender.getTextValue());

                if (mHasStatusField) {
                    status = (Bathroom.Status) mStatusSpinner.getSelectedItem();
                    bathroom = new Bathroom(building, floor, gender, status);
                    Log.d("STATUS", status.getTextValue());
                }

                mBathroomFormListener.receiveRequest(bathroom);

            }

        });

        if (mOnloadRunnable != null) {
            mOnloadRunnable.run();
        }
    }

    public BathroomForm setHasStatusField(boolean hasStatusField) {
        mHasStatusField = hasStatusField;
        return this;
    }

    public BathroomForm setBathroomFormListener(BathroomFormListener bathroomFormListener) {
        mBathroomFormListener = bathroomFormListener;
        return this;
    }

    public BathroomForm setOnLoad(Runnable r) {
        mOnloadRunnable = r;
        return this;
    }

    public interface BathroomFormListener {
        void receiveRequest(Bathroom bathroom);
    }

}
