package com.stassinopoulos.ari.bathroomapp;

import java.util.HashMap;
import java.util.Map;

public class Bathroom {

    private Building mBuilding;
    private Gender mGender;
    private Status mStatus;
    private int mFloor;
    
    public Bathroom(Building building, int floor, Gender gender, Status status) {
        mBuilding = building;
        mStatus = status;
        mGender = gender;
        mFloor = floor;
    }

    public Bathroom(Building building, int floor, Gender gender) {
        mBuilding = building;
        mFloor = floor;
        mGender = gender;
    }

    public Building getBuilding() {
        return mBuilding;
    }

    public void setBuilding(Building building) {
        mBuilding = building;
    }


    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
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

    public Gender getGender() {
        return mGender;
    }

    public void setGender(Gender gender) {
        mGender = gender;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> output = new HashMap<>();
        output.put("building", getBuilding().toString());
        output.put("gender", getGender().toString());
        output.put("floor", getFloor());
        if(mStatus != null) {
            output.put("status", getStatus().toString());
        }
        return output;
    }
}
