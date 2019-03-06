package com.stassinopoulos.ari.bathroomapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import com.backendless.Backendless;

public class HostActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private RelativeLayout mRootView;
    private HostTabType mHostTabType;

    public enum HostTabType {
        HOME_TAB,
        REPORT_TAB,
        REQUEST_TAB
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                Defaults.APPLICATION_ID,
                Defaults.API_KEY );

        setContentView(R.layout.activity_host);
        mTabLayout = findViewById(R.id.host_tab_layout);
        mRootView = findViewById(R.id.host_relative_layout);

        mTabLayout.addTab(mTabLayout.newTab().setText("Home").setTag(HostTabType.HOME_TAB));
        mTabLayout.addTab(mTabLayout.newTab().setText("Report").setTag(HostTabType.REPORT_TAB));
        mTabLayout.addTab(mTabLayout.newTab().setText("Request").setTag(HostTabType.REQUEST_TAB));

        getSupportActionBar().setElevation(0.0f);

        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colorText), ContextCompat.getColor(this, R.color.colorText));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(!(tab.getTag() instanceof HostTabType))
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

    private void switchTab(HostTabType hostTabType) {
        if(mHostTabType != hostTabType) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            switch(hostTabType) {
                case HOME_TAB:
                    ft.replace(R.id.host_relative_layout, new HomeFragment());
                    break;
                case REPORT_TAB:
                    ft.replace(R.id.host_relative_layout, new ReportFragment());
                    break;
                case REQUEST_TAB:
                    ft.replace(R.id.host_relative_layout, new RequestFragment());
                    break;
            }

            ft.commit();

            mHostTabType = hostTabType;
        }
    }



}
