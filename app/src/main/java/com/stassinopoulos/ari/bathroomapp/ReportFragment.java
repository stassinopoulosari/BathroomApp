package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class ReportFragment extends Fragment {

    private final int fragmentResource = R.layout.fragment_report;
    private HostActivity mHostActivity;
    private BathroomForm mBathroomForm;
    private int floorIndex = 0;
    private int buildingIndex = 0;
    private int genderIndex = 0;
    private int statusIndex = 0;

    public ReportFragment setHostActivity(HostActivity hostActivity) {
        this.mHostActivity = hostActivity;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentInflatedView = inflater.inflate(fragmentResource, container, false);

        FragmentTransaction formFragmentTransaction = getFragmentManager().beginTransaction();

        mBathroomForm = new BathroomForm().setHasStatusField(true).setBathroomFormListener(new BathroomForm.BathroomFormListener() {
            @Override
            public void receiveRequest(Bathroom bathroom) {
                effectRequest(bathroom);
            }
        }).setOnLoad(new Runnable() {
            @Override
            public void run() {
                mBathroomForm.getStatusSpinner().setSelection(statusIndex);
                mBathroomForm.getReportBuildingSpinner().setSelection(buildingIndex);
                mBathroomForm.getGenderSpinner().setSelection(genderIndex);
                mBathroomForm.getFloorSpinner().setSelection(floorIndex);
            }
        });


        formFragmentTransaction.replace(R.id.report_form_fragment_frame, mBathroomForm);

        formFragmentTransaction.commit();

        return fragmentInflatedView;
    }

    private void effectRequest(Bathroom bathroom) {

        Log.e("Starting effect", bathroom.getBuilding().toString());
        final Bathroom usableBathroom = bathroom;


        getView().post(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getInstance().getReference("reports");
                        String key = ref.push().getKey();
                        ref.child(key).setValue(new FirebaseBathroomReport(usableBathroom, authResult.getUser().getUid()).toMap())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), "Report succeeded", Toast.LENGTH_SHORT).show();
                                        mHostActivity.switchTab(HostActivity.HostTabType.HOME_TAB);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Report failed " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public BathroomForm getBathroomForm() {
        return mBathroomForm;
    }

    public ReportFragment prefillForm(Bathroom bathroom) {
        List<Bathroom.Building> buildingValues = Arrays.asList(Bathroom.Building.values());
        List<Bathroom.Gender> genderValues = Arrays.asList(Bathroom.Gender.values());
        List<Bathroom.Status> statuses = Arrays.asList(Bathroom.Status.values());

        floorIndex = bathroom.getFloor() - 1;
        genderIndex = genderValues.indexOf(bathroom.getGender());
        buildingIndex = buildingValues.indexOf(bathroom.getBuilding()) + 1;

        if (bathroom.getStatus() != null) {
            statusIndex = -1 * statuses.indexOf(bathroom.getStatus()) + 2;
        }
        return this;
    }
}
