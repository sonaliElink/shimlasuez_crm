package elink.suezShimla.water.crm.ConnectionManagement.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ConnectionManagement.fragment.NCCommercialFeasibilityDetailsFragment;
import elink.suezShimla.water.crm.ConnectionManagement.fragment.NCConnectionDetailsFragment;
import elink.suezShimla.water.crm.ConnectionManagement.fragment.NCConsumerDetailsFragment;
import elink.suezShimla.water.crm.ConnectionManagement.fragment.NCTechnicalFeasibilityDetailsFragment;
import elink.suezShimla.water.crm.ConnectionManagement.fragment.NCUploadDocDetailsFragment;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;

public class SiteVisitListActivityDetails extends AppCompatActivity implements NCConsumerDetailsFragment.SendConsumerData, NCConnectionDetailsFragment.ConnectionData, NCCommercialFeasibilityDetailsFragment.NCCommercialFeasibility, NCTechnicalFeasibilityDetailsFragment.NCTechnicalFeasibility/*,NCConsumerIndexDetailsFragment.ConsumerIndex*/ {
    private Context mCon;
    private final String LOG_TAG = SiteVisitListActivityDetails.class.getSimpleName();
    NCConsumerDetailsFragment ncConsumerDetails = new NCConsumerDetailsFragment();
    NCConnectionDetailsFragment ncConnectionDetails = new NCConnectionDetailsFragment();
    NCCommercialFeasibilityDetailsFragment ncCommercialFeasibilityDetails = new NCCommercialFeasibilityDetailsFragment();
    NCTechnicalFeasibilityDetailsFragment ncTechnicalFeasibilityDetails = new NCTechnicalFeasibilityDetailsFragment();
    // NCConsumerIndexDetailsFragment ncConsumerIndexDetails = new NCConsumerIndexDetailsFragment();
    NCUploadDocDetailsFragment ncUploadDocDetails = new NCUploadDocDetailsFragment();
    // Titles of the individual pages (displayed in tabs)
    String latitude="",longitude="";
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    // The fragments that are used as the individual pages
    private final Fragment[] PAGES = new Fragment[]{
            ncConsumerDetails,
            ncConnectionDetails,
            ncCommercialFeasibilityDetails,
            ncTechnicalFeasibilityDetails,
            //  ncConsumerIndexDetails,
            ncUploadDocDetails
            //     new FragmentBranch()
    };

    // The ViewPager is responsible for sliding pages (fragments) in and out upon user input
    private ViewPager view_pager;
    ViewGroup vg;
    TabLayout tabLayout;

    TextView tv_toolbar;

    private SiteVisitModel model, customerModel, connectionModel, commercialFeasibilityModel, technicalFeasibilityModel, consumerIndex;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_site_visit_list_details);

        mCon=this;
        init();
        getLocations();
    }

    private void getLocations() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager =(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            Criteria c=new Criteria();
            //if we pass false than
            //it will check first satellite location than Internet and than Sim Network
            String   provider=locationManager.getBestProvider(c, false);
            Location locationGPS = locationManager.getLastKnownLocation(provider);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                //Toast.makeText(this, "location captured", Toast.LENGTH_SHORT).show();


            } else {
               // Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        tv_toolbar = findViewById(R.id.tv_toolbar);
        tv_toolbar.setText(getResources().getString(R.string.site_visit_list));

        model = getIntent().getParcelableExtra("siteVisitEntity");


        customerModel = new SiteVisitModel();
        connectionModel = new SiteVisitModel();
        commercialFeasibilityModel = new SiteVisitModel();
        technicalFeasibilityModel = new SiteVisitModel();
        // consumerIndex = new SiteVisitModel();


        view_pager = findViewById(R.id.viewpager);
        view_pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        view_pager.setEnabled(false);
        // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
        // both directions, i.e. when activity_select_language new tab is selected, the ViewPager switches to this page,
        // and when the ViewPager switches to activity_select_language new page, the corresponding tab is selected)
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(view_pager);


        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
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
        view_pager.setOffscreenPageLimit(6);

    }

    private void changeViewPagerPosition(int position) {
        int totalCount = view_pager.getAdapter().getCount();
        if (position < 0 || position >= totalCount) {
            return;
        }
        view_pager.setCurrentItem(position);
    }


    public void onClickNext() {
        int currentViewpagerPosition = view_pager.getCurrentItem();

        changeViewPagerPosition(currentViewpagerPosition + 1);

    }

    public void onClickDoubleNext() {
        int currentViewpagerPosition = view_pager.getCurrentItem();

        changeViewPagerPosition(currentViewpagerPosition + 2);

    }


    public SiteVisitModel getSiteVisitDataData() {
        return model;
    }

    public SiteVisitModel getCustomerList() {
        return customerModel;
    }

    public SiteVisitModel getConnectionList() {
        return connectionModel;
    }

    public SiteVisitModel
    getComercialFeasibilityList() {
        return commercialFeasibilityModel;
    }

    public SiteVisitModel getTechnicalFeasibilityList() {
        return technicalFeasibilityModel;
    }

    public SiteVisitModel getConsumerIndexData() {
        return consumerIndex;
    }

    @Override
    public void sendData(SiteVisitModel siteVisitModel) {
        customerModel = siteVisitModel;
        //    Log.d("Click",siteVisitModel.getAM_APP_NMTITLE()); 
    }

    @Override
    public void sendConnectionData(SiteVisitModel siteVisitModel) {
        connectionModel = siteVisitModel;

    }

    @Override
    public void sendCommercialFeasibility(SiteVisitModel siteVisitModel) {
        commercialFeasibilityModel = siteVisitModel;
    }

    @Override
    public void sendTechnicalFeasibility(SiteVisitModel siteVisitModel) {
        technicalFeasibilityModel = siteVisitModel;

    }


    public void onClickPrev() {
        int currentViewpagerPosition = view_pager.getCurrentItem();

        changeViewPagerPosition(currentViewpagerPosition - 1);

    }

    public void onClickDoublePrev() {
        int currentViewpagerPosition = view_pager.getCurrentItem();

        changeViewPagerPosition(currentViewpagerPosition - 2);

    }

    /* @Override
     public void consumerIndexData(SiteVisitModel siteVisitModel) {
         consumerIndex=siteVisitModel;
     }
 */
    public class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] PAGE_TITLES = new String[]{
                getResources().getString(R.string.consumer),
                getResources().getString(R.string.connection),
                getResources().getString(R.string.commercial_feasibility),
                getResources().getString(R.string.technical_feasibility),
                //  getResources().getString(R.string.consumer_index),
                getResources().getString(R.string.upload_documents)

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
        startBackActivity();
        super.onBackPressed();
    }

    private void startBackActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Tag", "3");
        startActivity(intent);
        //nitin
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

 /*   @Override
    public void onPause() {
        super.onPause();
        ((App) this.getApplication()).startActivityTransitionTimer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }*/
}
