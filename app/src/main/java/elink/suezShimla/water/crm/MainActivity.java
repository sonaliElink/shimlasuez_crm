package elink.suezShimla.water.crm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.File;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Bill.BillFragment;
import elink.suezShimla.water.crm.ChangePassword.ChangePasswordFragment;
import elink.suezShimla.water.crm.Complaint.Allocation.Activity.WorkAllocationActivity;
import elink.suezShimla.water.crm.Complaint.ComplaintFragment;
import elink.suezShimla.water.crm.Complaint.Reallocation.Activity.WorkReallocationActivity;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Activity.ComplaintHistoryActivity;
import elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.TodayWorkCompletedActivity;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Activity.WorkCompletionActivity;
import elink.suezShimla.water.crm.ConnectionManagement.ConnectionManagementFragment;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_IssuetoMeterFixer_MasterDataModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MDialDigitModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.DMAModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCgRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGContEmpModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCvlMeasurementResponseModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGFcRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMakerCodeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMaterialDetailsModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterLocationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterSizeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterStatusModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGObersvationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRampAndRRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRequestTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSaddleAndPitExcavModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGTypeOfRoadcuttingModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGVendorDetModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGWallBoringModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGZoneModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MSRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MStatusObservationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.SRModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSourceModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.FinishActionModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SiteEngineerModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.MasterData.ActivityDownloadMaster;
import elink.suezShimla.water.crm.MasterData.DownloadMasterData;
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.IssueMeterToFixerActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGMainActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.MeterInstallationActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterManagementSystemFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.StoreManagementActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.MeterInstallationContractorDetails;
import elink.suezShimla.water.crm.NoConsumerComplaint.NoConsumerFragment;
import elink.suezShimla.water.crm.Notifications.Activity.NotificationActivity;
import elink.suezShimla.water.crm.Shantanu.Collection.CollectionDashboardFragment;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import io.realm.Realm;

public class MainActivity extends SessionActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MaterialDialog progress;
    private Context mCon;
    private Fragment fragment;
    private Toolbar toolbar;
    private String imei, mac, emp_code = "",sessionid="", versionName = "", tag = "", empMasterDataCode = "", JsonMMGMasterData = "", jsonResponseAndroidHSC = "", pdfLink = "";
    private NavigationView navigationView;
    Boolean goToConnectionM=false;
    RealmOperations realmOperations;
    RelativeLayout relative_layout1;
    TextView badge_notification_3, user_id;
    String notificatioCcount = "", deviceAuthorization = "", appIsLogged = "";
    private boolean doubleBackToExitPressedOnce;

    MaterialDialog mmgMasterProgress, hscMasterProgress;
    private ConnectionDetector connection;
    private Invoke invServices;

    private Gson gson;

    MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel;
    MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel;
    MMGMeterSizeModel mmgMeterSizeModel;
    MMGMakerCodeModel mmgMakerCodeModel;
    MMGRampAndRRModel mmgRampAndRRModel;
    MMGMeterTypeModel mmgMeterTypeModel;
    MMGWallBoringModel mmgWallBoringModel;
    MMGCgRestroModel mmgCgRestroModel;
    MMGFcRestroModel mmgFcRestroModel;
    MMGMaterialDetailsModel mmgMaterialDetailsModel;
    MMGZoneModel IssueToMeter_ZoneExist;
    MMGSubZoneModel MMGSubZoneModelExist;
    MMGRequestTypeModel MMGRequestTypeModel;
    MMGObersvationModel mmgObersvationModel;
    MStatusObservationModel mStatusObservationModel;
    MMGMeterLocationModel mmgMeterLocationModel;
    MMGMeterStatusModel mmgMeterStatusModel;
    MMGVendorDetModel mmgVendorDetModel;
    MSRModel msrModel;
    MMGContEmpModel MMGContEmpModel;
    SRModel srModelExist;
    DMAModel dmaModelExist;
    MDialDigitModel mDialDigitModel;
    MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel;

    String jsonValidateUser = "",systemAdmin = "SYSADM001", sytemSubAdmin = "SADMIN423", complaintRights = "CMOC00021", allocationRights = "OCWA00312",
            reAllocationRights = "OCWR00315", fixerAllocationRights = "MEMMIR208", hscReplacementRight = "MIRD001357";
    private String EXPORT_REALM_FILE_NAME = "crm_backup.realm";
    private Realm realm;

    private static String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    String depright="";
    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;
    public static String IpAddCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCon = this;
        realmOperations = new RealmOperations(mCon);

        realm = Realm.getDefaultInstance();

        Intent startingIntent = getIntent();
        imei = startingIntent.getStringExtra("IMEINumber");
        mac = startingIntent.getStringExtra("MACAddress");
        versionName = startingIntent.getStringExtra("versionName");
        tag = startingIntent.getStringExtra("Tag");

        tag = startingIntent.getStringExtra("goToConnectionM");
        if(goToConnectionM){
            fragment = new ConnectionManagementFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.connection_managment));        }





        // shantanu's code
        WifiManager wm = (WifiManager) mCon.getSystemService(Context.WIFI_SERVICE);
        IpAddCode= Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            sessionid=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.SID));

        } catch (Exception e) {
            e.printStackTrace();
        }

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.complaint));
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        badge_notification_3 = findViewById(R.id.badge_notification_3);

        relative_layout1 = findViewById(R.id.relative_layout1);
        deviceAuthorization = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEVICEAUTHORIZATION);
        appIsLogged = UtilitySharedPreferences.getPrefs(mCon, AppConstant.APP_ISLOGGED);

        if (deviceAuthorization.equalsIgnoreCase("0")) {
            deviceAuthorizedDialog();
        }else {
            if(appIsLogged.equalsIgnoreCase("0")){
              //  adminLogout();
            }
        }

        try {
            // AES Algorithm for encryption / decryption

            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            random = new SecureRandom();
            random.nextBytes(IV);

            aes=new AesAlgorithm();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        hideItem(R.id.nav_complaint, true);
        //   hideItem(R.id.nav_connection_request, false);
        hideItem(R.id.nav_meter_management_system, false);
        hideItem(R.id.nav_noConsumercomplaint, false);
        hideItem(R.id.nav_connection_management, false);
        hideItem(R.id.nav_main_collection_managementt, false);  // shantanu code for collection payment
        fragment = new ComplaintFragment();
        SetFragment(fragment);
        if (App.backPressMMGFragment.equalsIgnoreCase("Y")) {
            fragment = new MeterManagementSystemFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.meter_management_system));

            App.backPressMMGFragment = "N";
        }

        if (App.backPressNSCFragment.equalsIgnoreCase("Y")) {
            fragment = new ConnectionManagementFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.connection_managment));

            App.backPressNSCFragment = "N";
        }


        String rights = PreferenceUtil.getRights();



      /*  SYSADM001 - All
        SADMIN423 - All

        CMOC00021 - Complaint Registration
        OCWA00312  -  Work Allocation
        OCWR00315  -  Work Re-allocation*/

       /* if (!rights.contains("SYSADM001") && !rights.contains("SADMIN423") && !rights.contains("CM0000015") &&
                !rights.contains("NC0000002") && !rights.contains("ME0000201") && !rights.contains("MEMMAR203") &&
                !rights.contains("NCON0906")) {
            MessageWindow.throwOutFromWindow(mCon, getResources().getString(R.string.you_have_no_role_rights), "Login Error", LoginActivity.class);
        } else {
            if (rights.contains("SYSADM001") || rights.contains("SADMIN423")) {
                hideItem(R.id.nav_complaint, true);
                //  hideItem(R.id.nav_connection_request, true);
                hideItem(R.id.nav_meter_management_system, true);
             //   hideItem(R.id.mmg_reports, true);

            } else {
                if (rights.contains("CM0000015")) {
                    hideItem(R.id.nav_complaint, true);
                } else {
                    hideItem(R.id.nav_complaint, true);
                }

                if (rights.contains("NC0000002")) {
                        hideItem(R.id.nav_meter_management_system, false);
                } else {
                         hideItem(R.id.nav_meter_management_system, true);
                }
                //For meter management
                if (rights.contains("ME0000201")) {
                  //  hideItem(R.id.mmg_reports, true);
                    hideItem(R.id.nav_meter_management_system, true);
                } else {
                    hideItem(R.id.nav_meter_management_system, false);
                }

                //For store management
                if (rights.contains("MEMMAR203")) {
                    hideItem(R.id.nav_store_management_system, false);
                } else {
                    hideItem(R.id.nav_store_management_system, false);
                }
                if (rights.contains("NCON0906")) {
                    hideItem(R.id.nav_noConsumercomplaint, false);
                } else {
                    hideItem(R.id.nav_noConsumercomplaint, false);
                }
            }
        }*/
// false means visibility gone
        String dept = null;
        try {
            dept = new AesAlgorithm().decrypt(PreferenceUtil.getDepartmentId());
        } catch (Exception e) {
            e.printStackTrace();

        }
        SharedPreferences prfs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
         depright = prfs.getString("deptright", "");
         if(depright.contains("3,4,5,9,6,2,8,10")) {
            hideItem(R.id.nav_connection_management, true);
            fragment = new ConnectionManagementFragment();
            SetFragment(fragment);
            navigationView.setCheckedItem(R.id.nav_connection_management);
            toolbar.setTitle(getResources().getString(R.string.connection_managment));
             hideItem(R.id.nav_main_collection_managementt, true);
             navigateFragment(new CollectionDashboardFragment(),R.id.nav_main_collection_managementt,R.string.collection);



             hideItem(R.id.nav_meter_management_system, true);
            fragment = new MeterManagementSystemFragment();
            SetFragment(fragment);
            navigationView.setCheckedItem(R.id.nav_meter_management_system);
            toolbar.setTitle(getResources().getString(R.string.meter_management_system));
        }
        else if (depright.contains("4,5")) { // shantanu code for collection
            hideItem(R.id.nav_connection_management, true);
            hideItem(R.id.nav_main_collection_managementt, true);
            navigateFragment(new ConnectionManagementFragment(),R.id.nav_connection_management,R.string.connection_managment);
            navigateFragment(new CollectionDashboardFragment(),R.id.nav_main_collection_managementt,R.string.collection);
        }else if (depright.contains("4,6")) { // shantanu code for collection
            hideItem(R.id.nav_main_collection_managementt, true);
            hideItem(R.id.nav_meter_management_system, true);
            navigateFragment(new CollectionDashboardFragment(),R.id.nav_main_collection_managementt,R.string.collection);
            navigateFragment(new MeterManagementSystemFragment(),R.id.nav_meter_management_system,R.string.meter_management_system);
        } else if (depright.contains("5,6")) {
            hideItem(R.id.nav_connection_management, true);
            hideItem(R.id.nav_meter_management_system, true);
//            fragment = new ConnectionManagementFragment();
//            SetFragment(fragment);
//            navigationView.setCheckedItem(R.id.nav_connection_management);
//            toolbar.setTitle(getResources().getString(R.string.connection_managment));
            navigateFragment(new ConnectionManagementFragment(),R.id.nav_connection_management,R.string.connection_managment);

//            fragment = new MeterManagementSystemFragment();
//            SetFragment(fragment);
//            navigationView.setCheckedItem(R.id.nav_meter_management_system);
//            toolbar.setTitle(getResources().getString(R.string.meter_management_system));
            navigateFragment(new MeterManagementSystemFragment(),R.id.nav_meter_management_system,R.string.meter_management_system);
        }else if(depright.contains("5")){
            hideItem(R.id.nav_connection_management, true);
            hideItem(R.id.nav_meter_management_system, false);
            navigateFragment(new ConnectionManagementFragment(),R.id.nav_connection_management,R.string.connection_managment);
        }else if(depright.contains("6")) {
            hideItem(R.id.nav_connection_management, false);
            hideItem(R.id.nav_meter_management_system, true);
            navigateFragment(new MeterManagementSystemFragment(),R.id.nav_meter_management_system,R.string.meter_management_system);
        }else if(depright.contains("4")) {  // shantanu code for collection payment (4,10)
            hideItem(R.id.nav_connection_management, false);
            hideItem(R.id.nav_main_collection_managementt, true);
            navigateFragment(new CollectionDashboardFragment(),R.id.nav_main_collection_managementt,R.string.collection);
        }

        //Log.e("DEpartment", dept);

       /* if (userAccess(rights, systemAdmin, sytemSubAdmin)) {
            // hideItem(R.id.nav_complaint, true);
            hideItem(R.id.complaint_reg_history, true);
            hideItem(R.id.complaint_work_allocation, false);
            hideItem(R.id.complaint_work_reallocation, false);
            hideItem(R.id.fixerAllocation, false);
            hideItem(R.id.hsc_meter_replace, false);
            //     hideItem(R.id.nav_connection_management, true);
            hideItem(R.id.nav_meter_management_system, true);

            if (depright.contains("5,6")) {// meter managment
                if (tag == null) {
                    tag = "2";
                } else {
                    if (tag.equalsIgnoreCase("3")) {
                        tag = "3";
                    } else if (tag.equalsIgnoreCase("4")) {
                        tag = "4";
                    } else {
                        tag = "2";
                    }
                }
            }
            if (dept.contains("5")) {// nsc meter
                tag = "3";
            }

        } else {

            if (dept.contains("6")) {// meter managment
                hideItem(R.id.nav_connection_management, false);
                hideItem(R.id.nav_meter_management_system, true);
               // hideItem(R.id.nav_internal_audit, true);

                if (tag == null) {
                    tag = "2";
                } else {
                    if (tag.equalsIgnoreCase("4")) {
                        tag = "4";
                    } else {
                        tag = "2";
                    }
                }
            } else if (dept.contains("5")) {
                //  hideItem(R.id.mmg_reports, false);
                hideItem(R.id.nav_connection_management, true);
                hideItem(R.id.nav_meter_management_system, false);
                //hideItem(R.id.nav_internal_audit, false);
                tag = "3";
            } else if (tag.equalsIgnoreCase("4")) {
                tag = "4";
            } else {
                hideItem(R.id.nav_meter_management_system, false);
                hideItem(R.id.nav_connection_management, false);
               // hideItem(R.id.nav_internal_audit, false);
            }

            if (userAccess(rights, complaintRights)) {
                hideItem(R.id.complaint_reg_history, true);
            } else {
                hideItem(R.id.complaint_reg_history, false);
            }
            if (userAccess(rights, allocationRights)) {
                hideItem(R.id.complaint_work_allocation, false);

            } else {
                hideItem(R.id.complaint_work_allocation, false);

            }
            if (userAccess(rights, reAllocationRights)) {
                hideItem(R.id.complaint_work_reallocation, false);
            } else {
                hideItem(R.id.complaint_work_reallocation, false);
            }
            if (userAccess(rights, fixerAllocationRights)) {

                hideItem(R.id.fixerAllocation, false);
            } else {
                hideItem(R.id.fixerAllocation, false);
            }
            if (userAccess(rights, hscReplacementRight)) {

                hideItem(R.id.hsc_meter_replace, false);
            } else {
                hideItem(R.id.hsc_meter_replace, false);
            }
            // hideItem(R.id.nav_complaint, false);
        }


      *//*  if (rights.contains("CM0000015")) {
            hideItem(R.id.nav_complaint, true);
            hideItem(R.id.nav_noConsumercomplaint, false);
        } else {
            hideItem(R.id.nav_complaint, true);
        }*//*

//
     *//*   notificatioCcount=PreferenceUtil.getNotiCount();
        badge_notification_3.setText(notificatioCcount); || rights.contains("CM0000015")*//*
        if (rights.contains("SYSADM001") || rights.contains("SADMIN423") || dept.equalsIgnoreCase("6")) {
            if (tag != null && tag.equalsIgnoreCase("2")) {
                fragment = new MeterManagementSystemFragment();
                SetFragment(fragment);
                toolbar.setTitle(getResources().getString(R.string.meter_management_system));
            } else if (tag != null && tag.equalsIgnoreCase("3")) {
                fragment = new ConnectionManagementFragment();
                SetFragment(fragment);
                navigationView.setCheckedItem(R.id.nav_connection_management);
            } else {
                fragment = new ComplaintFragment();
                SetFragment(fragment);
                navigationView.setCheckedItem(R.id.nav_complaint);
            }


//            fragment = new MeterManagementSystemFragment();
//            SetFragment();
//            navigationView.setCheckedItem(R.id.nav_meter_management_system);
        }


        if (tag != null && tag.equalsIgnoreCase("3")) {
            fragment = new ConnectionManagementFragment();
            SetFragment(fragment);
            navigationView.setCheckedItem(R.id.nav_connection_management);
            toolbar.setTitle(getResources().getString(R.string.connection_managment));
        }*/

        Menu menu = navigationView.getMenu();
        MenuItem app_version = menu.findItem(R.id.app_version);
        app_version.setTitle(getResources().getString(R.string.version) + "" + PreferenceUtil.getVersionName());
        String username = null;
        try {
            username = new AesAlgorithm().decrypt(PreferenceUtil.getEmpName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        user_id = navigationView.getHeaderView(0).findViewById(R.id.user_id);
        user_id.setSelected(true);
        user_id.setText(username + " " + "[" + emp_code + "]");

        realmOperations = new RealmOperations(mCon);

        if (connection.isConnectingToInternet()) {
            getMasterDataDownload();

        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_LONG).show();

        }

    }

    // shantanu created method for navigate frag
    void navigateFragment(Fragment navFragment, int navItem, int navString){
        SetFragment(navFragment);
        navigationView.setCheckedItem(navItem);
        toolbar.setTitle(getResources().getString(navString));
    }

    private boolean userAccess(String rights, String systemAdmin, String sytemSubAdmin) {

        boolean tmp = false;
        String[] rid = rights.split(String.valueOf(','));
        for (int i = 0; i < rid.length; i++) {
            if (rights.equals(rid[i]) || systemAdmin.equals(rid[i]) || sytemSubAdmin.equals(rid[i])) {
                tmp = true;
                break;
            }
        }
        return tmp;

    }

    private boolean userAccess(String rights, String systemAdmin) {

        boolean tmp = false;
        String[] rid = rights.split(String.valueOf(','));
        for (int i = 0; i < rid.length; i++) {
            if (rights.equals(rid[i]) || systemAdmin.equals(rid[i])) {
                tmp = true;
                break;
            }
        }
        return tmp;

    }

    private void getMasterDataDownload() {

        empMasterDataCode = UtilitySharedPreferences.getPrefs(this, AppConstant.EMPCODE);
        try {
            empMasterDataCode = aes.decrypt( empMasterDataCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[1];
        params[0] = empMasterDataCode;

        mmgTypeOfRoadcuttingModel = realmOperations.getRoadcuttingExist();
        mmgSaddleAndPitExcavModel = realmOperations.getExcavationExist();
        mmgMakerCodeModel = realmOperations.getMakerCodeExist();
        mmgMeterSizeModel = realmOperations.getMeterSizeExist();

        mmgRampAndRRModel = realmOperations.getRAMPRRExist();
        mmgMeterTypeModel = realmOperations.getMeterTypeExist();
        mmgWallBoringModel = realmOperations.getWallBoringExist();
        mmgCgRestroModel = realmOperations.getCGRExist();

        mmgFcRestroModel = realmOperations.getFCRExist();
        mmgMaterialDetailsModel = realmOperations.getMaterialDetailsExist();
        IssueToMeter_ZoneExist = realmOperations.getIssueToMeter_ZoneExist();
        MMGSubZoneModelExist = realmOperations.getIssueToMeterSubZoneExist();

        MMGRequestTypeModel = realmOperations.getIssueToMeterFixrRequestTypeExist();
        mmgObersvationModel = realmOperations.getObservationExist();
        mStatusObservationModel = realmOperations.getMStatusObservationExist();
        mmgMeterLocationModel = realmOperations.getMtrLocationExist();

        mmgMeterStatusModel = realmOperations.getMeterStatusExist();
        mmgVendorDetModel = realmOperations.getVendorDetExist();
        msrModel = realmOperations.getMSRExist();
        srModelExist = realmOperations.getSRModelExist();

        dmaModelExist = realmOperations.getDMAModelExist();
        MMGContEmpModel = realmOperations.getContEmpExist();
        mDialDigitModel = realmOperations.getDialDigitExists();
        mmgCvlMeasurementResponseModel = realmOperations.getCvlMesurementExist();


        if (mmgTypeOfRoadcuttingModel == null && mmgSaddleAndPitExcavModel == null && mmgMakerCodeModel == null && mmgMeterSizeModel == null &&
                mmgRampAndRRModel == null && mmgMeterTypeModel == null && mmgWallBoringModel == null && mmgCgRestroModel == null &&
                mmgFcRestroModel == null && mmgMaterialDetailsModel == null && mmgWallBoringModel == null && mmgCgRestroModel == null &&
                MMGRequestTypeModel == null && mmgObersvationModel == null && mStatusObservationModel == null && mmgMeterLocationModel == null &&
                mmgMeterStatusModel == null && mmgVendorDetModel == null && msrModel == null && srModelExist == null &&
                dmaModelExist == null && MMGContEmpModel == null && mDialDigitModel == null && mmgCvlMeasurementResponseModel == null) {
            GetMasterData_IssueToMeterFixer getMasterData_issueToMeterFixer = new GetMasterData_IssueToMeterFixer();
            getMasterData_issueToMeterFixer.execute(params);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetMasterData_IssueToMeterFixer extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            mmgMasterProgress = new MaterialDialog.Builder(mCon)
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
//                JsonMMGMasterData = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.GetMasterDataForAndroid_MMG);
                String paraNames[] = new String[1];
                paraNames[0] = "EmpCode";

                JsonMMGMasterData = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetMasterDataForAndroid_MMG, params, paraNames);

                //Log.e("JsonMMGMasterData",JsonMMGMasterData);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MMG_IssuetoMeterFixer_MasterDataModel mmg_issuetoMeterFixer_masterDataModel = gson.fromJson(JsonMMGMasterData, MMG_IssuetoMeterFixer_MasterDataModel.class);
                if (mmg_issuetoMeterFixer_masterDataModel != null) {

                    realmOperations.deleteRoadcuttingTable();
                    for (MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel1 : mmg_issuetoMeterFixer_masterDataModel.getRoadcutting()) {
                        MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel2 = new MMGTypeOfRoadcuttingModel(mmgTypeOfRoadcuttingModel1.getRC_ID(), mmgTypeOfRoadcuttingModel1.getRC_DESC());
                        realmOperations.addRoadcutting(mmgTypeOfRoadcuttingModel2);
                    }

                    realmOperations.deleteExcavationTable();
                    for (MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel1 : mmg_issuetoMeterFixer_masterDataModel.getExcavation()) {
                        MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel2 = new MMGSaddleAndPitExcavModel(mmgSaddleAndPitExcavModel1.getEC_ID(), mmgSaddleAndPitExcavModel1.getEC_DESC());
                        realmOperations.addExcavation(mmgSaddleAndPitExcavModel2);
                    }
                    realmOperations.deleteMakerCodeTable();
                    for (MMGMakerCodeModel mmgMakerCodeModel1 : mmg_issuetoMeterFixer_masterDataModel.getMakerCode()) {
                        MMGMakerCodeModel mmgMakerCodeModel2 = new MMGMakerCodeModel(mmgMakerCodeModel1.getMMFG_MFGCODE(), mmgMakerCodeModel1.getMMFG_MFGNAME(), mmgMakerCodeModel1.getMMFG_MATERIAL_TYPE());
                        realmOperations.addMakerCode(mmgMakerCodeModel2);
                    }
                    realmOperations.deleteMeterSizeTable();
                    for (MMGMeterSizeModel mmgMeterSizeModel1 : mmg_issuetoMeterFixer_masterDataModel.getMeterSizeModel()) {
                        MMGMeterSizeModel mmgMeterSizeModel2 = new MMGMeterSizeModel(mmgMeterSizeModel1.getMCS_ID(), mmgMeterSizeModel1.getCONNSIZEMM());
                        realmOperations.addMeterSize(mmgMeterSizeModel2);
                    }

                    realmOperations.deleteRAMPRRTable();
                    for (MMGRampAndRRModel mmgRampAndRRModel1 : mmg_issuetoMeterFixer_masterDataModel.getRAMPRR()) {
                        MMGRampAndRRModel mmgRampAndRRModel2 = new MMGRampAndRRModel(mmgRampAndRRModel1.getRRR_ID(), mmgRampAndRRModel1.getRRR_DESC());
                        realmOperations.addRAMPRR(mmgRampAndRRModel2);
                    }

                    realmOperations.deleteMeterTypeTable();
                    for (MMGMeterTypeModel mmgMeterTypeModel1 : mmg_issuetoMeterFixer_masterDataModel.getMeterType()) {
                        MMGMeterTypeModel mmgMeterTypeModel2 = new MMGMeterTypeModel(mmgMeterTypeModel1.getMTC_METERTYPE_CODE(), mmgMeterTypeModel1.getMTC_TYPEDESC());
                        realmOperations.addMeterType(mmgMeterTypeModel2);

                    }

                    realmOperations.deleteWallBoringTable();
                    for (MMGWallBoringModel mmgWallBoringModel1 : mmg_issuetoMeterFixer_masterDataModel.getWallBoring()) {
                        MMGWallBoringModel mmgWallBoringModel2 = new MMGWallBoringModel(mmgWallBoringModel1.getWB_ID(), mmgWallBoringModel1.getWB_DESC());
                        realmOperations.addWallBoring(mmgWallBoringModel2);
                    }


                    realmOperations.deleteCGRTable();
                    for (MMGCgRestroModel mmgCgRestroModel1 : mmg_issuetoMeterFixer_masterDataModel.getCGR()) {
                        MMGCgRestroModel mmgCgRestroModel2 = new MMGCgRestroModel(mmgCgRestroModel1.getCGR_ID(), mmgCgRestroModel1.getCGR_DESC());
                        realmOperations.addCGR(mmgCgRestroModel2);

                    }


                    realmOperations.deleteFCRTable();
                    for (MMGFcRestroModel mmgFcRestroModel1 : mmg_issuetoMeterFixer_masterDataModel.getFCR()) {
                        MMGFcRestroModel mmgFcRestroModel2 = new MMGFcRestroModel(mmgFcRestroModel1.getFCR_ID(), mmgFcRestroModel1.getFCR_DESC());
                        realmOperations.addFCR(mmgFcRestroModel2);

                    }


                    realmOperations.deleteMaterialDetailsTable();
                    for (MMGMaterialDetailsModel mmgMaterialDetailsModel1 : mmg_issuetoMeterFixer_masterDataModel.getMaterialDetails()) {
                        MMGMaterialDetailsModel mmgMaterialDetailsModel2 = new MMGMaterialDetailsModel(mmgMaterialDetailsModel1.getMM_ID(), mmgMaterialDetailsModel1.getMM_NAME(), mmgMaterialDetailsModel1.getM_UNIT(), mmgMaterialDetailsModel1.getMM_DEF_QTY(), mmgMaterialDetailsModel1.getSIZEID());
                        realmOperations.addMaterialDetails(mmgMaterialDetailsModel2);

                    }


                    realmOperations.deleteIssueToMeterFixrZoneTable();
                    for (MMGZoneModel MMGZoneModel : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterZones()) {
                        int id = MMGZoneModel.getBUM_BU_ID();
                        MMGZoneModel MMGZoneModelData = new MMGZoneModel(MMGZoneModel.getBU_NAME(), id);
                        realmOperations.addIssueToMeterFixrZone(MMGZoneModelData);

                    }


                    realmOperations.deleteIssueToMeterSubZoneTable();
                    for (elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel MMGSubZoneModel : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterSubZones()) {
                        int id = (MMGSubZoneModel.getPCM_PC_ID());
                        int buId = (MMGSubZoneModel.getPCM_BU_ID());
                        MMGSubZoneModel MMGSubZoneModelData = new MMGSubZoneModel(id, MMGSubZoneModel.getPCM_PC_NAME(), buId);
                        realmOperations.addIssueToMeterSubZone(MMGSubZoneModelData);

                    }

                    // Insert Request Type  in RequestTypeModel Table


                    realmOperations.deleteIssueToMeterFixrRequestTypeTable();
                    for (MMGRequestTypeModel MMGRequestTypeModel1 : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterRequstType()) {

                        if (MMGRequestTypeModel1.getAllVal()!=null && !MMGRequestTypeModel1.getSelectVal().equalsIgnoreCase("Select")) {
                            MMGRequestTypeModel MMGRequestTypeModel2 = new MMGRequestTypeModel(MMGRequestTypeModel1.getSelectVal(), MMGRequestTypeModel1.getAllVal());
                            realmOperations.addIssueToMeterFixrRequestType(MMGRequestTypeModel2);
                        }
                    }


                    realmOperations.deleteObservationTable();
                    for (MMGObersvationModel mmgObersvationModel1 : mmg_issuetoMeterFixer_masterDataModel.getObersvation()) {
                        MMGObersvationModel mmgMeterPrefixModel2 = new MMGObersvationModel(mmgObersvationModel1.getOCRM_ID(), mmgObersvationModel1.getOCRM_NAME());
                        realmOperations.addObservation(mmgMeterPrefixModel2);
                    }


                    realmOperations.deleteMStatusObservationTable();
                    for (MStatusObservationModel mStatusObservationModel1 : mmg_issuetoMeterFixer_masterDataModel.getMStatusObservation()) {
                        MStatusObservationModel mmgMeterPrefixModel22 = new MStatusObservationModel(mStatusObservationModel1.getMSNM_MSTATUS_ID(), mStatusObservationModel1.getMSNM_MSUBSTATUS_ID(), mStatusObservationModel1.getMSNM_MSUBSTATUS_NAME());
                        realmOperations.addMStatusObservation(mmgMeterPrefixModel22);
                    }


                    realmOperations.deleteMtrLocationTable();
                    for (MMGMeterLocationModel mmgMeterLocationModel37 : mmg_issuetoMeterFixer_masterDataModel.getMeterLocation()) {
                        MMGMeterLocationModel mmgMeterLocationModel2 = new MMGMeterLocationModel(mmgMeterLocationModel37.getML_ID(), mmgMeterLocationModel37.getML_DESC());
                        realmOperations.addMtrLocation(mmgMeterLocationModel2);
                    }


                    realmOperations.deleteMeterStatusTable();
                    for (MMGMeterStatusModel mmgMeterStatusModel1 : mmg_issuetoMeterFixer_masterDataModel.getMeterStatus()) {
                        MMGMeterStatusModel mmgMeterStatusModel2 = new MMGMeterStatusModel(mmgMeterStatusModel1.getMSM_METERSTATUS_ID(), mmgMeterStatusModel1.getMSM_METERSTATUS_NAME(), mmgMeterStatusModel1.getFINALMETERSTATUSTAG());
                        realmOperations.addMeterStatusDetails(mmgMeterStatusModel2);
                    }


                    realmOperations.deletVendorTable();
                    for (MMGVendorDetModel mmgVendorDetModel1 : mmg_issuetoMeterFixer_masterDataModel.getVendor()) {
                        MMGVendorDetModel mmgVendorDetModel2 = new MMGVendorDetModel(mmgVendorDetModel1.getEM_EMP_CODE(), mmgVendorDetModel1.getNAME(), mmgVendorDetModel1.getEM_EMAIL(), mmgVendorDetModel1.getEM_PHONEM(), mmgVendorDetModel1.getEM_DESIGNATION(), mmgVendorDetModel1.getDGM_DES_NAME());
                        realmOperations.addVendorDet(mmgVendorDetModel2);
                    }


                    realmOperations.deleteMSRTable();
                    for (MSRModel mMsrModel1 : mmg_issuetoMeterFixer_masterDataModel.getMSR()) {
                        MSRModel msrModel22 = new MSRModel(mMsrModel1.getSBM_ID(), mMsrModel1.getSBM_NAME());
                        realmOperations.addMSR(msrModel22);
                    }


                    realmOperations.deleteSRTable();
                    for (SRModel srModel1 : mmg_issuetoMeterFixer_masterDataModel.getSR()) {
                        SRModel srModel22 = new SRModel(srModel1.getTRM_ID(), srModel1.getTRM_NAME(), srModel1.getMSRID());
                        realmOperations.addSR(srModel22);
                    }

                    realmOperations.deleteDMATable();
                    for (DMAModel dmaModel1 : mmg_issuetoMeterFixer_masterDataModel.getDMA()) {
                        DMAModel dmaModel2 = new DMAModel(dmaModel1.getPM_ID(), dmaModel1.getPM_NAME(), dmaModel1.getSRID());
                        realmOperations.addDMA(dmaModel2);
                    }


                    realmOperations.deletContEmpTable();
                    for (MMGContEmpModel mmgContEmpModel : mmg_issuetoMeterFixer_masterDataModel.getContEmp()) {
                        MMGContEmpModel mmgVendorDetModel2 = new MMGContEmpModel(mmgContEmpModel.getEM_EMP_CODE(), mmgContEmpModel.getNAME(), mmgContEmpModel.getEM_EMAIL(), mmgContEmpModel.getEM_PHONEM(), mmgContEmpModel.getEM_DESIGNATION(), mmgContEmpModel.getDGM_DES_NAME(), mmgContEmpModel.getEM_VENDOR_ID());
                        realmOperations.addContEmpDet(mmgVendorDetModel2);
                    }


                    realmOperations.deletCvlMesurementTable();
                    for (MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel1 : mmg_issuetoMeterFixer_masterDataModel.getCivilDetails()) {
                        MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel2 = new MMGCvlMeasurementResponseModel(mmgCvlMeasurementResponseModel1.getSLNO(), mmgCvlMeasurementResponseModel1.getMCD_MATERIAL_ID(), mmgCvlMeasurementResponseModel1.getMCD_MATERIAL_NAME(), mmgCvlMeasurementResponseModel1.getMCD_ISDROPDOWN(), mmgCvlMeasurementResponseModel1.getMCD_ISQUANTITY(), mmgCvlMeasurementResponseModel1.getDDLID(), mmgCvlMeasurementResponseModel1.getQUANTITY(), mmgCvlMeasurementResponseModel1.getLENGTH(), mmgCvlMeasurementResponseModel1.getWIDTH(), mmgCvlMeasurementResponseModel1.getDEPTH());
                        realmOperations.addCvlMesurementDet(mmgCvlMeasurementResponseModel2);
                    }


                    realmOperations.deletDialDigitTable();
                    for (MDialDigitModel mDialDigitModel1 : mmg_issuetoMeterFixer_masterDataModel.getDialDigit()) {
                        MDialDigitModel mDialDigitModel2 = new MDialDigitModel(mDialDigitModel1.getDIGITTEXT(), mDialDigitModel1.getDIGITID());
                        realmOperations.addDialDigit(mDialDigitModel2);
                    }

                    // ComplaintByModel mComplaintModel = realmOperations.getDialDigitExists();
//                realmOperations.deletDialDigitTable();
//                        for (ComplaintByModel complaintByModel : mmg_issuetoMeterFixer_masterDataModel.getDialDigit()) {
//                            ComplaintByModel complaintByModel1   = new ComplaintByModel(complaintByModel.getDIGITTEXT(),complaintByModel.getDIGITID());
//                            realmOperations.addDialDigit(complaintByModel1);
//                        }

                    // Insert Finish Action in FinishActionModel Table
                    mmgMasterProgress.dismiss();
                }

            } catch (Exception e) {
                Log.e("Exception", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid_MMG", error);
            }


        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.please_click_back_again_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
//            new MaterialDialog.Builder(mCon)
//                    .title("Exit")
//                    .content("Are you sure, you want to exit")
//                    .positiveText("Yes")
//                    .onPositive(new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                            dialog.dismiss();
//                            finish();
//                        }
//                    })
//                    .negativeText("Cancel")
//                    .onNegative(new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        /*MenuItem item1 = menu.findItem(R.id.actionbar_item);
        MenuItemCompat.setActionView(item1, R.layout.notification_update_count_layout);

        relative_layout1 = (RelativeLayout) MenuItemCompat.getActionView(item1);*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_master_data) {
            Intent intent = new Intent(MainActivity.this, ActivityDownloadMaster.class);
           /* intent.putExtra("IMEINumber", imei);
            intent.putExtra("MACAddress", mac);
            intent.putExtra("employeeID", employeeID);
*/
            startActivity(intent);


            return true;
        }
        if (id == R.id.action_notification) {

            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);


            //   Toast.makeText(mCon, "Notification", Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_complaint) {
            fragment = new ComplaintFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.complaint));

        }else if(id == R.id.nav_accept_payment){   // Shantanu Code for Payment Collection module
            fragment = new CollectionDashboardFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.collection));
        } else if(id == R.id.bill_print){
            fragment = new BillFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.bprint));
        }
        else if (id == R.id.nav_meter_management_system) {
            fragment = new MeterManagementSystemFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.meter_management_system));
        } else if (id == R.id.mmg_home) {
            fragment = new MeterManagementSystemFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.meter_management_system));
        } else if (id == R.id.nav_store_management_system) {
            Intent intent = new Intent(this, StoreManagementActivity.class);
            startActivity(intent);
            // finish();
            toolbar.setTitle(getResources().getString(R.string.store_management));
        } else if (id == R.id.nav_noConsumercomplaint) {
            fragment = new NoConsumerFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.noConsumer));

        } else if (id == R.id.complaint_reg_history) {
            checkMasterDataBase(ComplaintHistoryActivity.class);
        } else if (id == R.id.complaint_todays_work) {
            checkMasterDataBase(TodayWorkCompletedActivity.class);
        } else if (id == R.id.nav_change_password) {
            fragment = new ChangePasswordFragment();
            SetFragment(fragment);
            toolbar.setTitle(R.string.change_mpin);
        } else if (id == R.id.complaint_home) {
            fragment = new ComplaintFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.complaint));
        } else if (id == R.id.complaint_work_allocation) {
            checkMasterDataBase(WorkAllocationActivity.class);
        } else if (id == R.id.complaint_work_reallocation) {
            checkMasterDataBase(WorkReallocationActivity.class);
        } else if (id == R.id.complaint_work_completion) {
            checkMasterDataBase(WorkCompletionActivity.class);
        } else if (id == R.id.work_allocated) {
            startActivity(new Intent(mCon, MeterInstallationActivity.class));
        } else if (id == R.id.saved_details_by_fixer) {
            Intent intent = new Intent(mCon, MeterInstallationContractorDetails.class);
            startActivity(intent);
        } else if (id == R.id.hsc_meter_replace) {
            Intent it = new Intent(mCon, MMGMainActivity.class);
            it.putExtra("NavigationPage", "FromNavigation");
            startActivity(it);
        } else if (id == R.id.fixerAllocation) {
          /*  Intent intent = new Intent(mCon, IssueMeterToFixerActivity.class);
            startActivity(intent);*/
            startActivity(new Intent(mCon, IssueMeterToFixerActivity.class));
        }
        /*else if (id == R.id.nav_internal_audit) {
            fragment = new InternalNetworkAuditFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.internal_audit_report));
        }*/  /*else if (id == R.id.mmg_reports) {
            Intent intent = new Intent(mCon, MeterManagementGroupReportsActivity.class);
            startActivity(intent);
        } */ else if (id == R.id.nav_connection_management) {
            fragment = new ConnectionManagementFragment();
            SetFragment(fragment);
            toolbar.setTitle(getResources().getString(R.string.connection_managment));
        } else if (id == R.id.nav_back_up) {
            MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                    .content(getResources().getString(R.string.backup_data))
                    .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                    .canceledOnTouchOutside(false)
                    .positiveText(getResources().getString(R.string.yes))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            //dialog.dismiss();
                            backupData();
                        }
                    })
                    .negativeText(getResources().getString(R.string.cancel))
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (id == R.id.nav_logout) {
            MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                    .title(getResources().getString(R.string.logout))
                    .content(getResources().getString(R.string.are_you_sure_you_want_to_logout))
                    .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                    .canceledOnTouchOutside(false)
                    .positiveText(getResources().getString(R.string.yes))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            //realmOperations.deleteRow();
                            String params[] = new String[2];

                            params[0] = emp_code;
                            params[1] = sessionid;

                            LogoutUser logout = new LogoutUser();
                            logout.execute(params);

                        }
                    })
                    .negativeText(getResources().getString(R.string.cancel))
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class LogoutUser extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                String username=null,password=null;
               // username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.EMPCODE));
               // password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                String paraName[] = new String[2];
                paraName[0] = "EmpCode";
                paraName[1] = "SessionToken";
                jsonValidateUser = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "SessionTermination1",params, paraName);
            } catch (Exception e) {
                Log.d("Catch", e.toString());

            }
            return null;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(Void result) {
            try {

               if(jsonValidateUser.equals("success")){
                   Intent i = new Intent(mCon, SplashScreen.class);
                   i.putExtra("IMEINumber", imei);
                   i.putExtra("MACAddress", mac);
                   i.putExtra("versionName", versionName);
                         /*   realmOperations.deleteComplaintTypeTable();
                            realmOperations.deleteSiteEngineerTable();*/
                   startActivity(i);
                   finish();
               }else {
                   progress.dismiss();

                   Toast.makeText(mCon, "Failed to logout", Toast.LENGTH_LONG).show();
               }
            } catch (Exception ex) {
                Log.e("Exception", ex.toString());
                String error = ex.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "ValidateUser", error);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    public void SetFragment(Fragment fragment1) {
        if (fragment1 != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment1).commit();
        }
    }

    private void hideItem(int id, boolean check) {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(id).setVisible(check);
    }

    private void checkMasterDataBase(Class aClass) {
        ComplaintTypeModel complaintTypeModelExist = realmOperations.getComplaintTypeExist();
        ComplaintSubTypeModel complaintSubTypeModelExist = realmOperations.getComplaintSubTypeExist();
        ComplaintSourceModel complaintSourceModelExist = realmOperations.getComplaintSourceExist();
        ZoneModel zoneModelExist = realmOperations.getZoneExist();
        SubZoneModel subZoneModelExist = realmOperations.getSubZoneExist();
        SiteEngineerModel siteEngineerModelExist = realmOperations.getSiteEngineerExist();
        FinishActionModel finishActionModelExist = realmOperations.getFinishActionExist();
        if (complaintTypeModelExist != null && complaintSubTypeModelExist != null && complaintSourceModelExist != null && zoneModelExist != null
                && subZoneModelExist != null && siteEngineerModelExist != null && finishActionModelExist != null) {
            startActivity(new Intent(mCon, aClass));
        } else {
            DownloadMasterData downloadMasterData = new DownloadMasterData();
            downloadMasterData.downloadData(mCon);
        }
    }

    public void setBackFrag() {
        Fragment fragment = new MeterManagementSystemFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();
        }
        toolbar.setTitle(getResources().getString(R.string.meter_management_system));
    }


    public void backupData() {
        checkStoragePermission();

        File exportRealmFile;
        File EXPORT_REALM_PATH = new File(mCon.getExternalFilesDir(null), "Back up");

        // Log.d(TAG, "Realm path : " + realm.getPath());

        try {

            if (!EXPORT_REALM_PATH.exists()) {
                EXPORT_REALM_PATH.mkdirs();
            }
            exportRealmFile = new File(EXPORT_REALM_PATH, EXPORT_REALM_FILE_NAME);

            if (exportRealmFile.exists()) {
                exportRealmFile.delete();
            }

            realm.writeCopyTo(exportRealmFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String msg = getResources().getString(R.string.file_exported_to) + EXPORT_REALM_PATH + "/" + EXPORT_REALM_FILE_NAME;
        Toast.makeText(mCon, msg, Toast.LENGTH_SHORT).show();
        // Log.d(TAG, msg);
    }

    private void checkStoragePermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
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
        realmOperations.close();
        ((App) this.getApplication()).startActivityTransitionTimer();
    }
/*
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }*/
}
