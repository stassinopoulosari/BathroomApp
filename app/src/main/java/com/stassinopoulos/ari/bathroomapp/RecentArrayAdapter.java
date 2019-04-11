package com.stassinopoulos.ari.bathroomapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecentArrayAdapter extends ArrayAdapter<ReceivedBathroom> {

    List<ReceivedBathroom> mUnifiedList;
    int mResource;
    ButtonReceiver mButtonReceiver;

    public RecentArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.mUnifiedList = new ArrayList<>();
        this.mResource = resource;
    }

    public void setUnifiedList(List<ReceivedBathroom> unifiedList) {
        mUnifiedList = unifiedList;
        this.notifyDataSetChanged();
    }

    public void setButtonReceiver(ButtonReceiver buttonReceiver) {
        mButtonReceiver = buttonReceiver;
    }

    @Override
    public int getCount() {
        return mUnifiedList.size();
    }

    @Override
    public ReceivedBathroom getItem(int i) {
        return mUnifiedList.get(i);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        System.out.println(i);
        View viewToReturn;
        if (convertView != null) {
            viewToReturn = convertView;
        } else {
            viewToReturn = View.inflate(getContext(), mResource, null);
        }

        final ReceivedBathroom bathroom = mUnifiedList.get(i);

        if (bathroom == null) return null;

        AppCompatTextView bathroomNameTextView = viewToReturn.findViewById(R.id.unified_result_item_name);
        AppCompatTextView bathroomStatusTextView = viewToReturn.findViewById(R.id.unified_result_item_status_text);
        AppCompatButton bathroomStatusDisputeResolveButton = viewToReturn.findViewById(R.id.unified_result_item_dispute);
        AppCompatTextView countTextView = viewToReturn.findViewById(R.id.unified_result_item_count);

        boolean isReport = bathroom.getStatus() != null;

        Log.d("G", bathroom.getGender().toString());

        bathroomNameTextView.setText(bathroom.getBuilding().getTextValue() + " " +
                (bathroom.getBuilding().getNumberOfFloors() == 2 ? (bathroom.getFloor() == 1 ? "first" : "second") +
                        " floor " : "") +
                (bathroom.getGender().equals(Bathroom.Gender.MALE) ? "men's room" : "women's room"));

        bathroomStatusTextView.setText((isReport ? bathroom.getStatus().getTextValue() : "Requested"));
        bathroomStatusTextView.setVisibility(View.VISIBLE);
        Log.d("Home", String.valueOf(bathroomStatusTextView.getText()));
        bathroomStatusTextView.setTextColor(getContext().getColor(isReport ? (bathroom.getStatus() == Bathroom.Status.OPEN ? R.color.colorGreen : R.color.colorRed) : R.color.colorPrimary));

        bathroomStatusDisputeResolveButton.setText((isReport ? "DISPUTE" : "REPORT"));
        bathroomStatusDisputeResolveButton.setVisibility(View.VISIBLE);
        Log.d("Home", String.valueOf(bathroomStatusDisputeResolveButton.getText()));
        bathroomStatusDisputeResolveButton.setTextColor(getContext().getColor(isReport ? R.color.colorRed : R.color.colorGreen));

        bathroomStatusDisputeResolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonReceiver.onReceive(bathroom);
            }
        });

        countTextView.setText("x" + bathroom.getStatisticCount());

        return viewToReturn;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    public interface ButtonReceiver {
        void onReceive(ReceivedBathroom toDisputeOrResolve);
    }
}