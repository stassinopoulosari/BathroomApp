package com.stassinopoulos.ari.bathroomapp;

class BathroomReport {

    private Bathroom mBathroom;
    private Bathroom.Status mStatusChange;

    public BathroomReport(Bathroom bathroon, Bathroom.Status status) {
        this.mBathroom = bathroon;
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
