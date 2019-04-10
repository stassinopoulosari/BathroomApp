package com.stassinopoulos.ari.bathroomapp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BathroomReport {

    private Bathroom mBathroom;
    private Bathroom.Status mStatusChange;

    public BathroomReport(Bathroom bathroom, Bathroom.Status status) {
        this.mBathroom = bathroom;
        this.mStatusChange = status;
    }

    public Bathroom getBathroom() {
        return mBathroom;
    }

    public void setBathroom(Bathroom bathroom) {
        mBathroom = bathroom;
    }

    public Bathroom.Status getNewStatus() {
        return mStatusChange;
    }

    public void setStatus(Bathroom.Status status) {
        mStatusChange = status;
    }

}
