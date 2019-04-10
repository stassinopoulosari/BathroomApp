package com.stassinopoulos.ari.bathroomapp;

import android.support.annotation.Nullable;

import java.util.Map;

public class ReceivedBathroom extends Bathroom {

    long mReceivedTimestamp = 0L;

    public ReceivedBathroom(Building building, int floor, Gender gender, Status status) {
        super(building, floor, gender, status);
    }

    public ReceivedBathroom(Building building, int floor, Gender gender) {
        super(building, floor, gender);
    }

    public ReceivedBathroom(Building building, int floor, Gender gender, long receivedTimestamp) {
        super(building, floor, gender);
        this.mReceivedTimestamp = receivedTimestamp;
    }

    public ReceivedBathroom(Building building, int floor, Gender gender, Status status, long receivedTimestamp) {
        super(building, floor, gender, status);
        this.mReceivedTimestamp = receivedTimestamp;
    }

    public long getReceivedTimestamp() {
        return mReceivedTimestamp;
    }

    public void setReceivedTimestamp(long receivedTimestamp) {
        mReceivedTimestamp = receivedTimestamp;
    }

    @Nullable
    public static ReceivedBathroom fromBathroom(Bathroom bathroom, long receivedTimestamp) {
        return new ReceivedBathroom(bathroom.getBuilding(), bathroom.getFloor(), bathroom.getGender(), bathroom.getStatus(), receivedTimestamp);
    }

    @Nullable
    public static ReceivedBathroom fromMap(Map<String, Object> map, long receivedTimestamp) {
        Bathroom bathroom = Bathroom.fromMap(map);
        if(bathroom == null) return null;
        return ReceivedBathroom.fromBathroom(bathroom, receivedTimestamp);
    }
}
