package elink.suezShimla.water.crm.InternalNetworkAudit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.Complaint;
import elink.suezShimla.water.crm.InternalNetworkAudit.fragment.INAConsumerDetailsFragment;
import elink.suezShimla.water.crm.InternalNetworkAudit.fragment.INAMeterDetailsFragment;
import elink.suezShimla.water.crm.InternalNetworkAudit.fragment.INAMeterTestingResultFragment;
import elink.suezShimla.water.crm.InternalNetworkAudit.fragment.INASiteObservationFragment;
import elink.suezShimla.water.crm.InternalNetworkAudit.fragment.INAStorageTypeFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.CustomViewPager;
import elink.suezShimla.water.crm.R;

public class InternalNetworkAuditActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener, TabLayout.BaseOnTabSelectedListener {

    CoordinatorLayout activity_mainLayout;
    ViewPagerAdapter adapter;
    CustomViewPager viewPager;
    TabLayout tabLayout;

    static ImageButton rightArrow, leftArrow;
    static Animation aniFade;
    Fragment fragment = null;

    private String consumerNo = "", complaintNo = "";
    Complaint complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_network_audit2);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        if (getIntent() != null) {
            complaint = (Complaint) getIntent().getSerializableExtra("complaint");
            consumerNo = getIntent().getStringExtra("consumerNo");
            complaintNo = getIntent().getStringExtra("complaintNo");
        }

        init();
    }

    public static void animationOnArrow() {
        rightArrow.startAnimation(aniFade);
    }


    private void init() {
        activity_mainLayout = findViewById(R.id.activity_mainLayout);
        viewPager = findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        rightArrow = findViewById(R.id.rightArrow);
        leftArrow = findViewById(R.id.leftArrow);

        aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);

        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        onPageSelected(1);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("complaint", complaint);
        bundle.putString("consumerNo", consumerNo);
        bundle.putString("complaintNo", complaintNo);

        switch (position) {
            case 0:
                fragment = new INAConsumerDetailsFragment();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new INAMeterDetailsFragment();
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new INAMeterTestingResultFragment();
                fragment.setArguments(bundle);
                break;
            case 3:
                fragment = new INASiteObservationFragment();
                fragment.setArguments(bundle);
                break;
            case 4:
                fragment = new INAStorageTypeFragment();
                fragment.setArguments(bundle);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            Bundle bundle = new Bundle();
            bundle.putSerializable("complaint", complaint);
            bundle.putString("consumerNo", consumerNo);
            bundle.putString("complaintNo", complaintNo);

            switch (position) {
                case 0:
                    fragment = new INAConsumerDetailsFragment();
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new INAMeterDetailsFragment();
                    fragment.setArguments(bundle);
                    break;
                case 2:
                    fragment = new INAMeterTestingResultFragment();
                    fragment.setArguments(bundle);
                    break;
                case 3:
                    fragment = new INASiteObservationFragment();
                    fragment.setArguments(bundle);
                    break;
                case 4:
                    fragment = new INAStorageTypeFragment();
                    fragment.setArguments(bundle);
                    break;

                default:
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "Consumer Details";
            } else if (position == 1) {
                title = "Meter Details";
            } else if (position == 2) {
                title = "Meter Testing Results";
            } else if (position == 3) {
                title = "Site Observation";
            } else if (position == 4) {
                title = "Storage Type";
            }
            return title;
        }

    }

    public void onClickNext() {
        int currentViewpagerPosition = viewPager.getCurrentItem();

        changeViewPagerPosition(currentViewpagerPosition + 1);

    }

    public void onClickPrev() {
        int currentViewpagerPosition = viewPager.getCurrentItem();

        changeViewPagerPosition(currentViewpagerPosition - 1);

    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == 1) {
            viewPager.setCurrentItem(0);
        } else if (viewPager.getCurrentItem() == 2) {
            viewPager.setCurrentItem(1);
        } else if (viewPager.getCurrentItem() == 3) {
            viewPager.setCurrentItem(2);
        } else if (viewPager.getCurrentItem() == 4) {
            App.backPressMaterialFragment = "Y";
            viewPager.setCurrentItem(3);
        } else if (viewPager.getCurrentItem() == 5) {
            viewPager.setCurrentItem(4);
        } else if (viewPager.getCurrentItem() == 6) {
            viewPager.setCurrentItem(5);
        } else if (viewPager.getCurrentItem() == 7) {
            viewPager.setCurrentItem(6);
        }  else {
            super.onBackPressed();
            return;

        }
    }

    private void changeViewPagerPosition(int position) {
        int totalCount = viewPager.getAdapter().getCount();
        if (position < 0 || position >= totalCount) {
            return;
        }
        viewPager.setCurrentItem(position);
    }
}