package com.stassinopoulos.ari.bathroomapp;

class BathroomReport {

    private Bathroom mBathroom;
    private Bathroom.Status mStatus;
    private String mIDString;

    public Bathroom getBathroom() {
        return mBathroom;
    }

    public void setBathroom(Bathroom bathroom) {
        mBathroom = bathroom;
    }

    public Bathroom.Status getStatus() {
        return mStatus;
    }

    public void setStatus(Bathroom.Status status) {
        mStatus = status;
    }

    public String getIDString() {
        return mIDString;
    }

    public void setIDString(String IDString) {
        mIDString = IDString;
    }
}
