package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.AuthenticationDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.CivilMeasurementDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.ConsumerDetFragmentNew;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.ContractorDetFragmentNew;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.MaterialDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.MeterProtectionDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.NewMeterDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.OldMeterDetFragment;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;

public class MMGScreenDemo extends AppCompatActivity {
    private ViewPager viewPager;
    TabLayout tabLayout;
    ViewGroup vg;
    private Context mCon;

    ViewGroup viewGroupConsumer, viewGroupOldMeter, viewGroupNewMeter, viewGroupMaterial, viewGroupMeterProtection, viewGroupCivilMeasurement, viewGroupAuthentication, viewGroupContractor;

    ConsumerDetFragmentNew consumerDetFragement = new ConsumerDetFragmentNew();
    OldMeterDetFragment oldMeterDetFragment = new OldMeterDetFragment();
    NewMeterDetFragment newMeterDetFragment = new NewMeterDetFragment();
//    ContractorDetFragment contractorDetFragment = new ContractorDetFragment();
    ContractorDetFragmentNew contractorDetFragmentnew = new ContractorDetFragmentNew();

    MaterialDetFragment materialDetFragment = new MaterialDetFragment();
    CivilMeasurementDetFragment civilMeasurementDetFragment = new CivilMeasurementDetFragment();
    MeterProtectionDetFragment meterProtectionDetFragment = new MeterProtectionDetFragment();
    AuthenticationDetFragment authenticationDetFragment = new AuthenticationDetFragment();

    private final Fragment[] PAGES = new Fragment[]{
            consumerDetFragement,
            oldMeterDetFragment,
            newMeterDetFragment,
            contractorDetFragmentnew,
            materialDetFragment,
            civilMeasurementDetFragment,
            meterProtectionDetFragment,
            authenticationDetFragment
    };

    String meterID = "",serachById="",consumerNoStr="",refNoStr="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main_mmg_demo);
        mCon = this;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        //ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Intent intent = getIntent();
        meterID = intent.getStringExtra("meterID");
        serachById = intent.getStringExtra("serachById");
        consumerNoStr = intent.getStringExtra("consumerNoStr");
        refNoStr = intent.getStringExtra("refNoStr");


        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
       /* fragmentCompanyDetails.setCompanyDetailsSaved(this);
        fragmentBankDetails.setBankDetailsSaved(this);
        fragmentRouteForm.setRouteDetails(this);*/
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();
                if (manager != null) {
                    if (manager.getBackStackEntryCount() >= 1) {
                        String topOnStack = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1).getName();
                        Log.i("TOP ON BACK STACK", topOnStack);
                    }
                }
            }
        });

        vg = (ViewGroup) tabLayout.getChildAt(0);

        if (meterID.equalsIgnoreCase("3")) {

            viewGroupConsumer = (ViewGroup) vg.getChildAt(0);

            viewGroupOldMeter = (ViewGroup) vg.getChildAt(1);
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            if (tab != null) {
                tabLayout.removeTab(tab);
            }

            viewGroupOldMeter.setEnabled(false);
            viewGroupOldMeter.setAlpha((float) 0.60);

            viewGroupNewMeter = (ViewGroup) vg.getChildAt(2);

            viewGroupMaterial = (ViewGroup) vg.getChildAt(3);
            viewGroupMeterProtection = (ViewGroup) vg.getChildAt(4);

            viewGroupCivilMeasurement = (ViewGroup) vg.getChildAt(5);

            viewGroupAuthentication = (ViewGroup) vg.getChildAt(6);
            viewGroupContractor = (ViewGroup) vg.getChildAt(7);


        }

        if (meterID.equalsIgnoreCase("2")) {

            viewGroupConsumer = (ViewGroup) vg.getChildAt(0);
            viewGroupOldMeter = (ViewGroup) vg.getChildAt(1);
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            if (tab != null) {
                tabLayout.removeTab(tab);
            }

            viewGroupOldMeter.setEnabled(false);
            viewGroupOldMeter.setAlpha((float) 0.60);

            viewGroupNewMeter = (ViewGroup) vg.getChildAt(2);

            viewGroupMaterial= (ViewGroup) vg.getChildAt(3);
            viewGroupMeterProtection= (ViewGroup) vg.getChildAt(4);

            viewGroupCivilMeasurement = (ViewGroup) vg.getChildAt(5);

            viewGroupAuthentication = (ViewGroup) vg.getChildAt(6);
            viewGroupContractor = (ViewGroup) vg.getChildAt(7);

        }
        if (meterID.equalsIgnoreCase("4")) {
            viewGroupConsumer = (ViewGroup) vg.getChildAt(0);
            viewGroupOldMeter = (ViewGroup) vg.getChildAt(1);


            viewGroupNewMeter = (ViewGroup) vg.getChildAt(2);

            viewGroupMaterial  = (ViewGroup) vg.getChildAt(3);
            TabLayout.Tab materialTab = tabLayout.getTabAt(3);
            if (materialTab != null) {
                tabLayout.removeTab(materialTab);
            }
            viewGroupMaterial.setEnabled(false);
            viewGroupMaterial.setAlpha((float) 0.60);


            viewGroupMeterProtection  = (ViewGroup) vg.getChildAt(5);

            viewGroupCivilMeasurement = (ViewGroup) vg.getChildAt(4);
            TabLayout.Tab civilTab = tabLayout.getTabAt(4);
            if (civilTab != null) {
                tabLayout.removeTab(civilTab);
            }
            viewGroupCivilMeasurement.setEnabled(false);
            viewGroupCivilMeasurement.setAlpha((float) 0.60);

            viewGroupAuthentication = (ViewGroup) vg.getChildAt(6);
            viewGroupContractor = (ViewGroup) vg.getChildAt(7);

        }
    }

    private void setSelectedPage() {
        viewGroupOldMeter.setEnabled(false);
    }


    /* PagerAdapter for supplying the ViewPager with the pages (fragments) to display. */
    public class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] PAGE_TITLES = new String[]{
                getResources().getString(R.string.consumer),
                getResources().getString(R.string.old_meter),
                getResources().getString(R.string.new_meter),
                getResources().getString(R.string.contractor),
                getResources().getString(R.string.material),
                getResources().getString(R.string.civil_measurement),
                getResources().getString(R.string.meter_protection),
                getResources().getString(R.string.authentication)
        };

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startBackActivity();
    }

    private void startBackActivity() {
        Intent i = new Intent(this, MMGMainActivity.class);
        startActivity(i);
        finish();
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
   /* @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }*/
}
