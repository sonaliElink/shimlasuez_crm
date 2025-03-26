package elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.Fragment.MeterInstallationFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.Fragment.MeterRemovalFragment;

public class MeterManagementGroupReportsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MeterManagementGroupReportsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                    MeterInstallationFragment meterInstallationFragment=new MeterInstallationFragment();
                    return meterInstallationFragment;
            case 1:
                    MeterRemovalFragment meterRemovalFragment=new MeterRemovalFragment();
                    return meterRemovalFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
