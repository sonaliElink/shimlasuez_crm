package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCvlMeasurementResponseModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Adapter.CivilMeasurementDetailsAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.AuthenticationDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.CivilMeasurementFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.ConsumerDetFragmentNew;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.ContractorDetFragmentNew;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.MaterialDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.MeterProtectionDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.NewMeterDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.OldMeterDetFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGCustDetModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGGetDetailsResponseModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMaterialResponseModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.MeterInstallationContractorDetails;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class MMGScreenActivity extends AppCompatActivity implements ConsumerDetFragmentNew.SendMessage, NewMeterDetFragment.SendMtrSizeMM
        , ContractorDetFragmentNew.SendCntrctDetails, NewMeterDetFragment.SendNewMeterDetails, CivilMeasurementFragment.SendCvlMeasurementDetails, MeterProtectionDetFragment.SendMeterProtectionDetails
        , MaterialDetFragment.SendMaterialDetails, OldMeterDetFragment.SendOldMeterDetails, ViewPager.OnPageChangeListener, View.OnClickListener, TabLayout.BaseOnTabSelectedListener {
    CoordinatorLayout activity_mainLayout;
    ViewPagerAdapter adapter;
    CustomViewPager viewPager;
    int pos;
    public static List<MMGMaterialResponseModel> materialDemoList;
    public static List<MMGCvlMeasurementResponseModel> cvlMeasurementResponseModelList;
    private Context mCon;
    String meterID = "", serachById = "CNO", consumerNoStr = "", refNoStr = "", contactorId = "", commisioned_noncommisioned = "", jsonResponse = "",
            pagename = "", contList = "", radioButtonVal = "";
    TabLayout tabLayout;

    private ConnectionDetector connection;

    private Invoke invServices;
    private Gson gson;

    MaterialDialog mmgMasterProgress;

    ArrayList<MMGCustDetModel> customerDetailList = new ArrayList<>();
    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = new ArrayList<>();
    ArrayList<InstallDetails> installDetails = new ArrayList<>();
    ArrayList<MMGValidateDetails> mmgvalidateDetailList = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    static ImageButton rightArrow, leftArrow;
    static Animation aniFade;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmgscreen);
    //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        activity_mainLayout = (CoordinatorLayout) findViewById(R.id.activity_mainLayout);
        mCon = this;

        gson = new Gson();
        connection = new ConnectionDetector(this);
        invServices = new Invoke();
        Intent intent = getIntent();
        meterID = intent.getStringExtra("meterID");
        serachById = intent.getStringExtra("serachById");
        consumerNoStr = intent.getStringExtra("consumerNoStr");
        refNoStr = intent.getStringExtra("refNoStr");
        commisioned_noncommisioned = intent.getStringExtra("commisioned_noncommisioned");
        pagename = intent.getStringExtra("pagename");
        contList = intent.getStringExtra("contList");
        radioButtonVal = intent.getStringExtra("radioButtonVal");

        getCustomerDetails();

        //  init();

//                rightArrow.setVisibility(View.VISIBLE);
//                leftArrow.setVisibility(View.GONE);
        //  fragment.setArguments(bundle);
    }

    private void init() {
        rightArrow = (ImageButton) findViewById(R.id.rightArrow);
        leftArrow = (ImageButton) findViewById(R.id.leftArrow);
        viewPager = (CustomViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

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

        //viewPager.setOffscreenPageLimit(10); //pinky need to test with this line 08/03/2022
        viewPager.addOnPageChangeListener(this);
        // rightArrow.setVisibility(View.VISIBLE);

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(+1), true); //getItem(+1) for next
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
            }
        });

        aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);

        //   if()

        fragment = new ConsumerDetFragmentNew();
       /* if(fragment.isAdded()){
            return;
        }*/
    }

    public static void animationOnArrow() {
        rightArrow.startAnimation(aniFade);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
//    public void sendData(String makerCodeId, String serialNoStr, String installDtStr, String meterSizeStr, String sealNoStr,String pastMeterReadingStr) {
    public void sendData(String makerCodeId, String oldmeterno, String sealNoStr, String installDtStr,
                         String coonectionLoad, String pastMeterReadingStr,
                         String submitStatus, String radiobuttonValStr, String consumerNoStr, String zoneStr,
                         String groupStr, String refTypeStr, String connSizeStr, String property_assessmnt,
                         String fromNodeStr, String toNodeStr, String primaryMobStr, String alternateMobStr,
                         String gis_bidStr, String dmaId, String srId, String msrId, String commisioned_noncommisioned,
                         String employee_code, String mac_address, String contList, boolean isSubmitted) {
        String tag = "android:switcher:" + R.id.pager + ":" + 1;

        OldMeterDetFragment oldMeterDetFragment = (OldMeterDetFragment) getSupportFragmentManager().findFragmentByTag(tag);
//        oldMeterDetFragment.displayReceivedData(makerCodeId,serialNoStr,installDtStr,meterSizeStr,sealNoStr,pastMeterReadingStr);

        oldMeterDetFragment.displayReceivedData(makerCodeId, oldmeterno, sealNoStr, installDtStr, coonectionLoad,
                pastMeterReadingStr, submitStatus, radiobuttonValStr, zoneStr, groupStr,
                refTypeStr, connSizeStr, property_assessmnt, fromNodeStr, toNodeStr, primaryMobStr, alternateMobStr,
                gis_bidStr, String.valueOf(dmaId), String.valueOf(srId), String.valueOf(msrId), commisioned_noncommisioned,
                employee_code, mac_address, contList, isSubmitted);

        NewMeterDetFragment newMeterDetFragment = new NewMeterDetFragment();
        newMeterDetFragment.displayReceivedData(makerCodeId, oldmeterno, sealNoStr, installDtStr, coonectionLoad,
                pastMeterReadingStr, submitStatus, radiobuttonValStr, consumerNoStr, zoneStr, groupStr,
                refTypeStr, connSizeStr, property_assessmnt, fromNodeStr, toNodeStr, primaryMobStr, alternateMobStr,
                gis_bidStr, String.valueOf(dmaId), String.valueOf(srId), String.valueOf(msrId), commisioned_noncommisioned,
                employee_code, mac_address, contList, isSubmitted);

        ContractorDetFragmentNew contractorDetFragmentNew = new ContractorDetFragmentNew();
        contractorDetFragmentNew.displayReceivedData(makerCodeId, oldmeterno, sealNoStr, installDtStr, coonectionLoad,
                pastMeterReadingStr, submitStatus, radiobuttonValStr, consumerNoStr, zoneStr, groupStr,
                refTypeStr, connSizeStr, property_assessmnt, fromNodeStr, toNodeStr, primaryMobStr, alternateMobStr,
                gis_bidStr, String.valueOf(dmaId), String.valueOf(srId), String.valueOf(msrId), commisioned_noncommisioned,
                employee_code, mac_address, contList, isSubmitted);

        MaterialDetFragment materialDetFragment = new MaterialDetFragment();
        materialDetFragment.displayReceivedData(makerCodeId, oldmeterno, sealNoStr, installDtStr, coonectionLoad,
                pastMeterReadingStr, submitStatus, radiobuttonValStr, consumerNoStr, zoneStr, groupStr,
                refTypeStr, connSizeStr, property_assessmnt, fromNodeStr, toNodeStr, primaryMobStr, alternateMobStr,
                gis_bidStr, String.valueOf(dmaId), String.valueOf(srId), String.valueOf(msrId), commisioned_noncommisioned,
                employee_code, mac_address, contList, isSubmitted);

        CivilMeasurementFragment civilMeasurementFragment = new CivilMeasurementFragment();
        civilMeasurementFragment.displayReceivedData(makerCodeId, oldmeterno, sealNoStr, installDtStr, coonectionLoad,
                pastMeterReadingStr, submitStatus, radiobuttonValStr, consumerNoStr, zoneStr, groupStr,
                refTypeStr, connSizeStr, property_assessmnt, fromNodeStr, toNodeStr, primaryMobStr, alternateMobStr,
                gis_bidStr, String.valueOf(dmaId), String.valueOf(srId), String.valueOf(msrId), commisioned_noncommisioned,
                employee_code, mac_address, contList, isSubmitted);

        MeterProtectionDetFragment meterProtectionDetFragment = new MeterProtectionDetFragment();
        meterProtectionDetFragment.displayReceivedData(makerCodeId, oldmeterno, sealNoStr, installDtStr, coonectionLoad,
                pastMeterReadingStr, submitStatus, radiobuttonValStr, consumerNoStr, zoneStr, groupStr,
                refTypeStr, connSizeStr, property_assessmnt, fromNodeStr, toNodeStr, primaryMobStr, alternateMobStr,
                gis_bidStr, String.valueOf(dmaId), String.valueOf(srId), String.valueOf(msrId), commisioned_noncommisioned,
                employee_code, mac_address, contList, isSubmitted);

        AuthenticationDetFragment authenticationDetFragment = new AuthenticationDetFragment();
        authenticationDetFragment.displayReceivedData(makerCodeId, oldmeterno, sealNoStr, installDtStr, coonectionLoad,
                pastMeterReadingStr, submitStatus, radiobuttonValStr, consumerNoStr, zoneStr, groupStr,
                refTypeStr, connSizeStr, property_assessmnt, fromNodeStr, toNodeStr, primaryMobStr, alternateMobStr,
                gis_bidStr, String.valueOf(dmaId), String.valueOf(srId), String.valueOf(msrId), commisioned_noncommisioned,
                employee_code, mac_address, contList, isSubmitted);

    }

    @Override
    public void sendmeterSize(String sizeStr, String sizeId) {
        MaterialDetFragment materialDetFragment = new MaterialDetFragment();
        materialDetFragment.displaymeterSizeData(sizeStr, sizeId);
    }

    @Override
    public void sndContracterDet(String c_id, String c_emp_id, String contractorNameStr, String vendorCodeStr, String contractorEmployeeStr,
                                 String removedHandverIdStr, String otherContractor, String otherContractorEmp,
                                 String other_code_id, String contList, String meterSizeId, boolean isContractorSubmitted) {

        ContractorDetFragmentNew contractorDetFragment = new ContractorDetFragmentNew();
        contractorDetFragment.displayContractorDet(contList);

        MaterialDetFragment materialDetFragment = new MaterialDetFragment();
        materialDetFragment.displayMaterialDet(contList);

        materialDetFragment.displayContracterDet(c_id, c_emp_id, contractorNameStr, vendorCodeStr, contractorEmployeeStr,
                removedHandverIdStr, otherContractor, otherContractorEmp, other_code_id, meterSizeId, isContractorSubmitted);

        CivilMeasurementFragment civilMeasurementFragment = new CivilMeasurementFragment();
        civilMeasurementFragment.displayContracterDet(c_id, c_emp_id, contractorNameStr, vendorCodeStr, contractorEmployeeStr,
                removedHandverIdStr, otherContractor, otherContractorEmp, other_code_id, isContractorSubmitted);


        AuthenticationDetFragment authenticationDetFragment = new AuthenticationDetFragment();
        authenticationDetFragment.displayContracterDet(c_id, c_emp_id, contractorNameStr, vendorCodeStr, contractorEmployeeStr,
                removedHandverIdStr, otherContractor, otherContractorEmp, other_code_id, isContractorSubmitted);

        MeterProtectionDetFragment meterProtectionDetFragment = new MeterProtectionDetFragment();
        meterProtectionDetFragment.displayContracterDet(c_id, c_emp_id, contractorNameStr, vendorCodeStr, contractorEmployeeStr,
                removedHandverIdStr, otherContractor, otherContractorEmp, other_code_id, isContractorSubmitted);
    }

    @Override
    public void sndMateriaLDet(List materiallist, String contList, boolean isMaterialSubmitted) {

        String tag = "android:switcher:" + R.id.pager + ":" + 3;


        MeterProtectionDetFragment meterProtectionDetFragment = new MeterProtectionDetFragment();
        meterProtectionDetFragment.displayMeterProt(contList);

        CivilMeasurementFragment civilMeasurementFragment = new CivilMeasurementFragment();
        materialDemoList = materiallist;
        civilMeasurementFragment.displayMaterialDet(materialDemoList, isMaterialSubmitted);

        meterProtectionDetFragment.displayMaterialDet(materialDemoList, isMaterialSubmitted);

        AuthenticationDetFragment authenticationDetFragment = new AuthenticationDetFragment();
        materialDemoList = materiallist;
        authenticationDetFragment.displayMaterialDet(materialDemoList, isMaterialSubmitted);
    }

    @Override
    public void sndMtrPrtctnDet(String pccLengthStr, String pccWidthStr, String pccDepthStr,
                                String pccTotalStr, String roadCuttingIdStr, String rdLengthStr,
                                String rdWidthStr, String rdDepthStr, String rdTotalStr, boolean isSubmitted) {
        AuthenticationDetFragment authenticationDetFragment = new AuthenticationDetFragment();
        authenticationDetFragment.displayMeterProtectionDet(pccLengthStr, pccWidthStr, pccDepthStr, pccTotalStr,
                roadCuttingIdStr, rdLengthStr, rdWidthStr, rdDepthStr, rdTotalStr, isSubmitted);
    }

    @Override
    public void sndOldMtrDet(String oldmakerCodeId, String oldmeterNoStr, String oldinstallDtStr,
                             String oldmeterSizeStr, String oldsealNoStr, String pastReadingStr, String oldMtrStsId,
                             String oldmeterTypeId, String finalReadingStr, String finalStatusStr, String meterObservationId,
                             String meterReasonId, String contList, boolean isOldSubmitted) {

        String tag = "android:switcher:" + R.id.pager + ":" + 2;

        NewMeterDetFragment newMeterDetFragment = new NewMeterDetFragment();
        newMeterDetFragment.displayNewMeterDet(contList);

        MaterialDetFragment materialDetFragment = new MaterialDetFragment();
        materialDetFragment.displayOldMeterDetails(oldmakerCodeId, oldmeterNoStr, oldinstallDtStr,
                oldmeterSizeStr, oldsealNoStr, pastReadingStr, oldMtrStsId, oldmeterTypeId, finalReadingStr,
                finalStatusStr, meterObservationId, meterReasonId, isOldSubmitted);

        newMeterDetFragment.displayOldMeterDetails(oldmakerCodeId, oldmeterNoStr, oldinstallDtStr,
                oldmeterSizeStr, oldsealNoStr, pastReadingStr, oldMtrStsId, oldmeterTypeId, finalReadingStr,
                finalStatusStr, meterObservationId, meterReasonId, isOldSubmitted);

        ContractorDetFragmentNew contractorDetFragmentNew = new ContractorDetFragmentNew();
        contractorDetFragmentNew.displayOldMeterDetails(oldmakerCodeId, oldmeterNoStr, oldinstallDtStr,
                oldmeterSizeStr, oldsealNoStr, pastReadingStr, oldMtrStsId, oldmeterTypeId, finalReadingStr,
                finalStatusStr, meterObservationId, meterReasonId, isOldSubmitted);

        CivilMeasurementFragment civilMeasurementFragment = new CivilMeasurementFragment();
        civilMeasurementFragment.displayOldMeterDetails(oldmakerCodeId, oldmeterNoStr, oldinstallDtStr,
                oldmeterSizeStr, oldsealNoStr, pastReadingStr, oldMtrStsId, oldmeterTypeId, finalReadingStr,
                finalStatusStr, meterObservationId, meterReasonId, isOldSubmitted);

        MeterProtectionDetFragment meterProtectionDetFragment = new MeterProtectionDetFragment();
        meterProtectionDetFragment.displayOldMeterDetails(oldmakerCodeId, oldmeterNoStr, oldinstallDtStr,
                oldmeterSizeStr, oldsealNoStr, pastReadingStr, oldMtrStsId, oldmeterTypeId, finalReadingStr,
                finalStatusStr, meterObservationId, meterReasonId, isOldSubmitted);

        AuthenticationDetFragment authenticationDetFragment = new AuthenticationDetFragment();
        authenticationDetFragment.displayOldMeterDetails(oldmakerCodeId, oldmeterNoStr, oldinstallDtStr,
                oldmeterSizeStr, oldsealNoStr, pastReadingStr, oldMtrStsId, oldmeterTypeId, finalReadingStr,
                finalStatusStr, meterObservationId, meterReasonId, isOldSubmitted);

    }

    @Override
    public void sndCvlMeasurementDet(List cvlMeasurementList, boolean is_civil_submitted) {
        AuthenticationDetFragment authenticationDetFragment = new AuthenticationDetFragment();
        cvlMeasurementResponseModelList = CivilMeasurementDetailsAdapter.civilUpdatedDemoList;
        authenticationDetFragment.displayCvlMeasurementDet(cvlMeasurementResponseModelList, is_civil_submitted);

        MeterProtectionDetFragment meterProtectionDetFragment = new MeterProtectionDetFragment();
        meterProtectionDetFragment.displayCvlMeasurementDet(cvlMeasurementResponseModelList, is_civil_submitted);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Bundle bundle = new Bundle();
        bundle.putString("meterID", meterID);
        bundle.putString("serachById", serachById);
        bundle.putString("consumerNoStr", consumerNoStr);
        bundle.putString("refNoStr", refNoStr);
        bundle.putString("commisioned_noncommisioned", commisioned_noncommisioned);
        bundle.putString("cont_id", contactorId);
        bundle.putString("pagename", pagename);
        bundle.putString("contList", contList);
        bundle.putString("radioButtonVal", radioButtonVal);

        //if not valid return
        // get current fragment data
        // validate data
        //else continue

        switch (position) {
            case 0:
                fragment = new ConsumerDetFragmentNew();
//                rightArrow.setVisibility(View.VISIBLE);
//                leftArrow.setVisibility(View.GONE);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new OldMeterDetFragment();
//                rightArrow.setVisibility(View.VISIBLE);
//                leftArrow.setVisibility(View.VISIBLE);
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new NewMeterDetFragment();
//                rightArrow.setVisibility(View.VISIBLE);
//                leftArrow.setVisibility(View.VISIBLE);
                fragment.setArguments(bundle);
                break;
            case 3:
                fragment = new ContractorDetFragmentNew();
//                rightArrow.setVisibility(View.VISIBLE);
//                leftArrow.setVisibility(View.VISIBLE);
                fragment.setArguments(bundle);
                break;
            case 4:
                fragment = new MaterialDetFragment();
//                rightArrow.setVisibility(View.VISIBLE);
//                leftArrow.setVisibility(View.VISIBLE);
                fragment.setArguments(bundle);
                break;
            case 5:
                fragment = new CivilMeasurementFragment();
//                rightArrow.setVisibility(View.VISIBLE);
//                leftArrow.setVisibility(View.VISIBLE);
                fragment.setArguments(bundle);

                break;
            case 6:
                fragment = new MeterProtectionDetFragment();
//                rightArrow.setVisibility(View.VISIBLE);
//                leftArrow.setVisibility(View.VISIBLE);
                fragment.setArguments(bundle);

                break;
            case 7:
                fragment = new AuthenticationDetFragment();
//                rightArrow.setVisibility(View.GONE);
//                leftArrow.setVisibility(View.VISIBLE);
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
    public void onClick(View v) {
        onPageSelected(1);
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

    @Override
    public void sndNewMtrDet(String makerCodeId, String meterNoStr, String installDtStr, String meterSizeId,
                             String sealNoStr, String initialReadingStr, String meterTypeId,
                             String meterLocationId, String protectedBoxIdStr, String taxNoStr,
                             String meterHandoverIdStr, String contList, String dial_digit, boolean isNewMeterSubmitted,String avrageConsumtion) {

        ContractorDetFragmentNew contractorDetFragment = new ContractorDetFragmentNew();
        contractorDetFragment.displayContractorDet(contList);

        contractorDetFragment.displayNewMeterDet(makerCodeId, meterNoStr, installDtStr,
                meterSizeId, sealNoStr, initialReadingStr, meterTypeId, meterLocationId, protectedBoxIdStr,
                taxNoStr, meterHandoverIdStr, contList, dial_digit, isNewMeterSubmitted);

        MaterialDetFragment materialDetFragment = new MaterialDetFragment();
        materialDetFragment.displayNewMeterDet(makerCodeId, meterNoStr, installDtStr,
                meterSizeId, sealNoStr, initialReadingStr, meterTypeId, meterLocationId, protectedBoxIdStr,
                taxNoStr, meterHandoverIdStr, contList, dial_digit, isNewMeterSubmitted);

        CivilMeasurementFragment civilMeasurementFragment = new CivilMeasurementFragment();
        civilMeasurementFragment.displayNewMeterDet(makerCodeId, meterNoStr, installDtStr,
                meterSizeId, sealNoStr, initialReadingStr, meterTypeId, meterLocationId, protectedBoxIdStr,
                taxNoStr, meterHandoverIdStr, contList, dial_digit, isNewMeterSubmitted);

        MeterProtectionDetFragment meterProtectionDetFragment = new MeterProtectionDetFragment();
        meterProtectionDetFragment.displayNewMeterDet(makerCodeId, meterNoStr, installDtStr,
                meterSizeId, sealNoStr, initialReadingStr, meterTypeId, meterLocationId, protectedBoxIdStr,
                taxNoStr, meterHandoverIdStr, contList, dial_digit, isNewMeterSubmitted);

        AuthenticationDetFragment authenticationDetFragment = new AuthenticationDetFragment();

        authenticationDetFragment.displayNewMeterDet(makerCodeId, meterNoStr, installDtStr,
                meterSizeId, sealNoStr, initialReadingStr, meterTypeId, meterLocationId, protectedBoxIdStr,
                taxNoStr, meterHandoverIdStr, contList, dial_digit, isNewMeterSubmitted);

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            Bundle bundle = new Bundle();
            bundle.putString("meterID", meterID);
            bundle.putString("serachById", serachById);
            bundle.putString("consumerNoStr", consumerNoStr);
            bundle.putString("refNoStr", refNoStr);
            bundle.putString("commisioned_noncommisioned", commisioned_noncommisioned);
            bundle.putString("cont_id", contactorId);
            bundle.putString("pagename", pagename);
            bundle.putString("contList", contList);

            switch (position) {
                case 0:
                    fragment = new ConsumerDetFragmentNew();
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new OldMeterDetFragment();
                    fragment.setArguments(bundle);
                    break;
                case 2:
                    fragment = new NewMeterDetFragment();
                    fragment.setArguments(bundle);
                    break;
                case 3:
                    fragment = new ContractorDetFragmentNew();
                    fragment.setArguments(bundle);
                    break;
                case 4:
                    fragment = new MaterialDetFragment();
                    fragment.setArguments(bundle);
                    break;
                case 5:
                    fragment = new CivilMeasurementFragment();
                    fragment.setArguments(bundle);
                    break;
                case 6:
                    fragment = new MeterProtectionDetFragment();
                    fragment.setArguments(bundle);
                    break;
                case 7:
                    fragment = new AuthenticationDetFragment();
                    fragment.setArguments(bundle);
                    break;

                default:
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "Consumer";
            } else if (position == 1) {
                title = "Old Meter";
            } else if (position == 2) {
                title = "New Meter";

            } else if (position == 3) {
                title = "Plumber";
            } else if (position == 4) {
                title = "Material";
            } else if (position == 5) {
                title = "Civil Measurement";
            } else if (position == 6) {
                title = "Meter Protection";
            } else if (position == 7) {
                title = "Upload Documents";
            }
            return title;
        }
    }

    private void startBackActivity() {
        Intent intent = new Intent(this, MeterInstallationContractorDetails.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }


    public void onClickNext(int currPos) {
        //Log.e("currPos", "currPos: "+currPos);
        int currentViewpagerPosition = viewPager.getCurrentItem();
        changeViewPagerPosition(currPos + 1);

    }

    public void onClickPrev() {
        int currentViewpagerPosition = viewPager.getCurrentItem();

        changeViewPagerPosition(currentViewpagerPosition - 1);

    }

    public void onClickAuthenticate() {
        int currentViewpagerPosition = viewPager.getCurrentItem();

        changeViewPagerPosition(7);

    }

    public void skipOldMeterDetails() {
        int currentViewpagerPosition = viewPager.getCurrentItem();

        changeViewPagerPosition(2);

    }

    public void skipNewMeterDetails() {
        int currentViewpagerPosition = viewPager.getCurrentItem();

        changeViewPagerPosition(3);

    }

    private void changeViewPagerPosition(int position) {
        int totalCount = viewPager.getAdapter().getCount();
        if (position < 0 || position >= totalCount) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    boolean doubleBackToExitPressedOnce = false;

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
        } /*else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 500);
        } */else {
            super.onBackPressed();
            return;

        }
    }

    //New BackPressed Method
//    @Override
//    public void onBackPressed() {
//        this.doubleBackToExitPressedOnce = true;
//
//        if (doubleBackToExitPressedOnce) {
//            if (viewPager.getCurrentItem() == 1) {
//                // Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
//                viewPager.setCurrentItem(0);
//            } else if (viewPager.getCurrentItem() == 2) {
//                // Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
//
//                viewPager.setCurrentItem(1);
//            } else if (viewPager.getCurrentItem() == 3) {
//                // Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
//
//                viewPager.setCurrentItem(2);
//            } else if (viewPager.getCurrentItem() == 4) {
//                App.backPressMaterialFragment = "Y";
//                //   Toast.makeText(this, "3"+App.backPressMaterialFragment, Toast.LENGTH_SHORT).show();
//
//                viewPager.setCurrentItem(3);
//            } else if (viewPager.getCurrentItem() == 5) {
//                // Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
//
//                viewPager.setCurrentItem(4);
//            } else if (viewPager.getCurrentItem() == 6) {
//                //   Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
//
//                viewPager.setCurrentItem(5);
//            } else if (viewPager.getCurrentItem() == 7) {
//                viewPager.setCurrentItem(6);
//            }
//            super.onBackPressed();
//            return;
//        }
//
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 500);
//        new MaterialDialog.Builder(this)
//                .title("Exit")
//                .content("Are you sure, you want to exit to Homescreen?")
//                .positiveText("Yes")
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        finish();
//                    }
//                })
//                .negativeText("Cancel")
//                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//    }

    private void getCustomerDetails() {
        String params[] = new String[4];
        params[0] = "CNO";
        params[1] = consumerNoStr;
        params[2] = refNoStr;
        try {
            params[3] =  new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

        //Log.e("Params", Arrays.toString(params));
        if (connection.isConnectingToInternet()) {
            Get_CustomerDetailsTask get_customerDetailsTask = new Get_CustomerDetailsTask();
            get_customerDetailsTask.execute(params);
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class Get_CustomerDetailsTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            mmgMasterProgress = new MaterialDialog.Builder(MMGScreenActivity.this)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[4];
                paraName[0] = "SearchType";
                paraName[1] = "SearchStr";
                paraName[2] = "RefNo";
                paraName[3] = "EmpCode";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetConsumerAndMeterDetails, params, paraName);

                Log.e("MMGScreenResponse",jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MMGGetDetailsResponseModel mmgGetDetailsResponseModel = gson.fromJson(jsonResponse, MMGGetDetailsResponseModel.class);
                if (mmgGetDetailsResponseModel != null) {

                    customerDetailList = (ArrayList<MMGCustDetModel>) mmgGetDetailsResponseModel.getMMGCustomerDetails();
                    meterConnectionList = (ArrayList<MMGMeterConnectedDetailsModel>) mmgGetDetailsResponseModel.getMMGMeterConnectionDetails();
                    installDetails = (ArrayList<InstallDetails>) mmgGetDetailsResponseModel.getInstallDetailsList();
                    mmgvalidateDetailList = (ArrayList<MMGValidateDetails>) mmgGetDetailsResponseModel.getMmgValidateDetails();

                }

                mmgMasterProgress.dismiss();
                init();


            } catch (Exception e) {
                Log.e("Get_CustomerDetails As", e.toString());
                mmgMasterProgress.dismiss();
                String error = e.toString();
                ErrorClass.errorData(MMGScreenActivity.this, "Consumer Details Fragment", "Get Customer details task", error);
            }
        }


    }

    public ArrayList<MMGCustDetModel> getMMGCustomerDetailsData() {
        return customerDetailList;
    }

    public ArrayList<MMGMeterConnectedDetailsModel> getMMGMeterConnectionDetailsData() {
        return meterConnectionList;
    }

    public ArrayList<InstallDetails> getMMGInstallDetailsData() {
        return installDetails;
    }

    public ArrayList<MMGValidateDetails> getMmgvalidateDetailList() {
        return mmgvalidateDetailList;
    }


    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App) this.getApplication();
        if (myApp.wasInBackground) {
            finish();
            startActivity(new Intent(mCon, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((App) this.getApplication()).startActivityTransitionTimer();
    }
  /*  @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }*/
}
