package com.stassinopoulos.ari.bathroomapp;
import android.util.Log;

import com.google.firebase.database.ServerValue;
import com.stassinopoulos.ari.bathroomapp.Bathroom;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirebaseBathroomReport {

    private Bathroom mBathroom;
    private String mUserID;

    public FirebaseBathroomReport(Bathroom bathroom, String userID) {
        this.mBathroom = bathroom;
        this.mUserID = userID;
    }

    public Map toMap() {
        Map output = new HashMap();
        output.put("userID", mUserID);
        output.put("bathroom", mBathroom);
        output.put("timestamp", ServerValue.TIMESTAMP);
        return output;
    }

    public Bathroom getBathroom() {
        return mBathroom;
    }

    public void setBathroom(Bathroom bathroom) {
        mBathroom = bathroom;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }
}