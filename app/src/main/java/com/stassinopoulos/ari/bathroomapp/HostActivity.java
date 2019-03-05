package com.stassinopoulos.ari.bathroomapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

public class HostActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private RelativeLayout mRootView;

    public enum HostTabType {
        HOME_TAB,
        REPORT_TAB,
        REQUEST_TAB
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_host);
        mTabLayout = findViewById(R.id.host_tab_layout);
        mToolbar = findViewById(R.id.host_toolbar);
        mRootView = findViewById(R.id.host_relative_layout);

        mTabLayout.addTab(mTabLayout.newTab().setText("Home").setTag(HostTabType.HOME_TAB));
        mTabLayout.addTab(mTabLayout.newTab().setText("Report").setTag(HostTabType.REPORT_TAB));
        mTabLayout.addTab(mTabLayout.newTab().setText("Request").setTag(HostTabType.REQUEST_TAB));

        mToolbar.setTitle("Home");
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(!(tab.getTag() instanceof HostTabType))
                    return;

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch((HostTabType) tab.getTag()) {

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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



}
