package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;


public class HostActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private RelativeLayout mRootView;
    private HostTabType mHostTabType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_host);
        mTabLayout = findViewById(R.id.host_tab_layout);
        mRootView = findViewById(R.id.host_relative_layout);

        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.home_header).setTag(HostTabType.HOME_TAB));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.report_header).setTag(HostTabType.REPORT_TAB));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.request_header).setTag(HostTabType.REQUEST_TAB));

        getSupportActionBar().setElevation(0.0f);

        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colorText), ContextCompat.getColor(this, R.color.colorText));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!(tab.getTag() instanceof HostTabType))
                    return;

                switchTab((HostTabType) tab.getTag());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        switchTab(HostTabType.HOME_TAB);
    }

    public void switchTab(final HostTabType hostTabType) {
        if (mHostTabType != hostTabType) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if(mHostTabType != null) {
                if (mHostTabType.getInt() > hostTabType.getInt()) {
                    ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                } else {
                    ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                }
            }

            switch (hostTabType) {
                case HOME_TAB:
                    ft.replace(R.id.host_relative_layout, new HomeFragment());
                    break;
                case REPORT_TAB:
                    ft.replace(R.id.host_relative_layout, new ReportFragment().setHostActivity(this));
                    break;
                case REQUEST_TAB:
                    ft.replace(R.id.host_relative_layout, new RequestFragment().setHostActivity(this));
                    break;
            }

            ft.runOnCommit(new Runnable() {
                @Override
                public void run() {
                    mTabLayout.getTabAt(hostTabType.getInt()).select();
                }
            });

            ft.commit();



            mHostTabType = hostTabType;
        }
    }

    public enum HostTabType {
        HOME_TAB,
        REPORT_TAB,
        REQUEST_TAB;

        public int getInt() {
            switch(this){
                case HOME_TAB:
                    return 0;
                case REPORT_TAB:
                    return 1;
                default:
                    return 2;
            }
        }
    }


}
