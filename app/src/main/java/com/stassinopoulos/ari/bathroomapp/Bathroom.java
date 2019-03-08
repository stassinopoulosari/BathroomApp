package com.stassinopoulos.ari.bathroomapp;

public class Bathroom {

    public enum Building {
        A,B,E,G,J,M,N,Q
    }

    public enum Status {
        OPEN, CLOSED, UNKNOWN
    }

    private String mIDString;
    private Building mBuilding;
    private String mReadableName;
    private Status mStatus;

    private String mRoomNumber;

    public Bathroom(String idString, Building building, String readableName, Status status) {
        mIDString = idString;
        mBuilding = building;
        mReadableName = readableName;
        mStatus = status;
    }

    public String getIDString() {
        return mIDString;
    }

    public void setIDString(String IDString) {
        mIDString = IDString;
    }

    public Building getBuilding() {
        return mBuilding;
    }

    public void setBuilding(Building building) {
        mBuilding = building;
    }

    public String getReadableName() {
        return mReadableName;
    }

    public void setReadableName(String readableName) {
        mReadableName = readableName;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public String getRoomNumber() {
        return mRoomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        mRoomNumber = roomNumber;
    }

}
