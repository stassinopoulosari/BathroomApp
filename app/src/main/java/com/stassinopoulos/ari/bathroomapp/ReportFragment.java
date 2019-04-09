package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportFragment extends Fragment {

    private final int fragmentResource = R.layout.fragment_report;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentInflatedView = inflater.inflate(fragmentResource, container, false);

        FragmentTransaction formFragmentTransaction = getFragmentManager().beginTransaction();
        formFragmentTransaction.replace(R.id.report_form_fragment_frame, new BathroomForm().setHasStatusField(true).setBathroomFormListener(new BathroomForm.BathroomFormListener() {
            @Override
            public void receiveRequest(Bathroom bathroom) {
                effectRequest(bathroom);
            }
        }));

        formFragmentTransaction.commit();

        return fragmentInflatedView;
    }

    private void effectRequest(Bathroom bathroom) {
        final Bathroom usableBathroom = bathroom;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("reports");
                String key = ref.push().getKey();
                ref.child(key).setValue(usableBathroom);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
