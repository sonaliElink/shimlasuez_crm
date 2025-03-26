package elink.suezShimla.water.crm.MeterManagementSystem.MMGReports;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.Adapter.MeterManagementGroupReportsAdapter;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;

public class MeterManagementGroupReportsActivity extends AppCompatActivity {
    TabLayout tabLayout;
    MeterManagementGroupReportsAdapter meterManagementGroupReportsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_meter_management_group_reports);

                tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                tabLayout.addTab(tabLayout.newTab().setText("Meter Installation"));
                tabLayout.addTab(tabLayout.newTab().setText("Meter Removal"));
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                final ViewPager viewPager =(ViewPager)findViewById(R.id.view_pager);
                meterManagementGroupReportsAdapter = new MeterManagementGroupReportsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(meterManagementGroupReportsAdapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }
                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
}

    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App) this.getApplication();
        if (myApp.wasInBackground) {
            finish();
            startActivity(new Intent(this, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((App) this.getApplication()).startActivityTransitionTimer();
    }

}
