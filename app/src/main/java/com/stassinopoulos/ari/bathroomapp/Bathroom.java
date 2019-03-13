package com.stassinopoulos.ari.bathroomapp;

public class Bathroom {

    private String mIDString;
    private Building mBuilding;
    private String mReadableName;
    private Status mStatus;
    private int mFloor;
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

    public int getFloor() {
        return mFloor;
    }

    public void setFloor(int floor) {
        if (floor == 2 && this.mBuilding != null && this.mBuilding.getNumberOfFloors() == 2)
            mFloor = floor;
        if (floor == 1) mFloor = floor;
    }

    public enum Building implements Adaptable {
        A("A"), B("B"), E("E"), G("G"), J("J"), M("M"), N("N"), Q("Q");

        private String mLetter;

        Building(String letter) {
            mLetter = letter;
        }

        public String getTextValue() {
            return mLetter;
        }

        public int getNumberOfFloors() {
            switch (this) {
                case A:
                case B:
                case E:
                case G:
                case N:
                case Q:
                    return 1;
                default:
                    return 2;
            }
        }


    }

    public enum Status implements Adaptable {
        OPEN("Open"), CLOSED("Closed");

        private String mStatus;

        Status(String status) {
            mStatus = status;
        }

        public String getTextValue() {
            return mStatus;
        }
    }

    public enum Gender implements Adaptable {
        MALE("Male"), FEMALE("Female");

        private String mReadableValue;

        Gender(String readableValue) {
            mReadableValue = readableValue;
        }

        public String getTextValue() {
            return mReadableValue;
        }
    }

    public interface Adaptable {
        String getTextValue();
    }
}
