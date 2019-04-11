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

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RequestFragment extends Fragment {

    private final int fragmentResource = R.layout.fragment_request;
    private HostActivity mHostActivity;
    private BathroomForm mBathroomForm;

    public RequestFragment setHostActivity(HostActivity hostActivity) {
        this.mHostActivity = hostActivity;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentInflatedView = inflater.inflate(fragmentResource, container, false);

        FragmentTransaction formFragmentTransaction = getFragmentManager().beginTransaction();

        mBathroomForm = new BathroomForm().setHasStatusField(false).setBathroomFormListener(new BathroomForm.BathroomFormListener() {
            @Override
            public void receiveRequest(Bathroom bathroom) {
                Log.d("RF", "Received request");
                effectRequest(bathroom);
            }
        });
        formFragmentTransaction.replace(R.id.request_bathroom_form_frame, mBathroomForm);

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
                        DatabaseReference ref = database.getInstance().getReference("requests");
                        String key = ref.push().getKey();
                        ref.child(key).setValue(new FirebaseBathroomReport(usableBathroom, authResult.getUser().getUid()).toMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Request succeeded", Toast.LENGTH_SHORT).show();
                                mHostActivity.switchTab(HostActivity.HostTabType.HOME_TAB);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Request failed " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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


}
