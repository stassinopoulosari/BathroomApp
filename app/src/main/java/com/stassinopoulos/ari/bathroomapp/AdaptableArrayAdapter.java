package com.stassinopoulos.ari.bathroomapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptableArrayAdapter extends ArrayAdapter<Bathroom.Adaptable> {

    private int mResource;
    private boolean mEmptyFirstItem;

    private Bathroom.Adaptable[] mAdaptables;

    public AdaptableArrayAdapter(@NonNull Context context, int resource) {
        this(context, resource, true);
    }

    public AdaptableArrayAdapter(@NonNull Context context, int resource, boolean emptyFirstItem) {
        super(context, resource);
        mEmptyFirstItem = emptyFirstItem;
        mResource = resource;
    }

    @Override
    public int getCount() {
        if (mAdaptables == null) return (mEmptyFirstItem ? 1 : 0);
        return mAdaptables.length + (mEmptyFirstItem ? 1 : 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = (convertView != null ? convertView : View.inflate(getContext(), mResource, null));
        TextView textView = view.findViewById(android.R.id.text1);
        if (mEmptyFirstItem && position == 0) {
            textView.setText("");
            return view;
        }

        textView.setText(mAdaptables[position - (mEmptyFirstItem ? 1 : 0)].getTextValue());

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = (convertView != null ? convertView : View.inflate(getContext(), mResource, null));
        TextView textView = view.findViewById(android.R.id.text1);
        if (mEmptyFirstItem && position == 0) {
            textView.setText("");
            return view;
        }

        textView.setText(mAdaptables[position - (mEmptyFirstItem ? 1 : 0)].getTextValue());

        return view;
    }

    public AdaptableArrayAdapter setAdaptables(Bathroom.Adaptable[] adaptables) {
        mAdaptables = adaptables;
        notifyDataSetChanged();
        return this;
    }

    @Nullable
    @Override
    public Bathroom.Adaptable getItem(int position) {
        if (position == 0 && mEmptyFirstItem) {
            return null;
        }
        return mAdaptables[position - (mEmptyFirstItem ? 1 : 0)];
    }
}
