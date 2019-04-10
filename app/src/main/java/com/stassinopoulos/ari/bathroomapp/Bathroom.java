package com.stassinopoulos.ari.bathroomapp;

import android.support.annotation.Nullable;

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
        A("A", "Admin"), B("B", "Hub"), E("E", "Student Union"), G("G", "Old Gym"), J("J", "J/K Building"), M("M", "L/M Building"), N("N", "N Building"), Q("Q", "Portables");

        private String mLetter;
        private String mReadableText;

        Building(String letter, String readableText) {
            mLetter = letter;
            mReadableText = readableText;
        }

        public static Building fromLetter(String letter) {
            for(Building building : Building.values()) {
                if(building.getLetter().equals(letter)) return building;
            }
            return Building.A;
        }

        public String getTextValue() {
            return mReadableText;
        }

        public String getLetter() {
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
        MALE("M", "Male"), FEMALE("F", "Female");

        private String mReadableValue;
        private String mLetter;

        Gender(String letter, String readableValue) {
            mLetter = letter; mReadableValue = readableValue;
        }

        public String toString() {
            return mLetter;
        }

        public static Gender fromLetter(String mLetter) {
            switch(mLetter) {
                case "M":
                    return Gender.MALE;
                case "F":
                default:
                    return Gender.FEMALE;
            }
        }

        public String getTextValue() {
            return mReadableValue;
        }

        public String getLetter() {
            return mLetter;
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
        output.put("building", getBuilding().getLetter());
        output.put("gender", getGender().getLetter());
        output.put("floor", getFloor());
        if(mStatus != null) {
            output.put("status", getStatus().toString());
        }
        return output;
    }

    @Nullable
    public static Bathroom fromMap(Map<String, Object> map) {
        if(!(map.get("building") instanceof String && map.get("gender") instanceof String && map.get("floor") instanceof Double)) return null;
        String buildingString = (String) map.get("building");
        String genderString = (String) map.get("gender");
        int floor = (Integer) map.get("floor");
        Building building = Building.fromLetter(buildingString);
        Gender gender = Gender.fromLetter(genderString);
        Bathroom toReturn = new Bathroom(building, floor, gender);
        if(map.get("status") instanceof String) {
            Status status = Status.valueOf((String) map.get("status"));
            toReturn = new Bathroom(building, floor, gender, status);
        }
        return toReturn;
    }
}
