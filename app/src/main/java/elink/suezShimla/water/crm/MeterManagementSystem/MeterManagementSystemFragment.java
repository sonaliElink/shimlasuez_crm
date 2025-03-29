package elink.suezShimla.water.crm.MeterManagementSystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.ZoneAndWard.Adapter.ZoneWardAdapter;
import elink.suezShimla.water.crm.Complaint.ZoneAndWard.Model.ZoneWardModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
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
import elink.suezShimla.water.crm.MasterData.MasterCountModel;
import elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.AcceptMMGDeptActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.IssueMeterToFixerActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGMainActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.MeterManagementGroupReportsActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MassMeterInstallatin.MassMeterInstallation;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.MeterInstallationActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.MeterInstallationSentAutheticationActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.MeterInstallationReplacementEntryShow;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.ShowMeterDataModel;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.StoreManagementActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.MeterInstallationContractorDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.Model.MMGGetAllMtrInstallByContModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class MeterManagementSystemFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private Context mCon;
    private ZoneWardAdapter zoneWardAdapter;
    private List<ZoneWardModel> zoneWardModelList;
    private RelativeLayout viewAllRelativeLayout;
    private MaterialButton storeManagementButton, acceptMMGDept, massMeterInstallationButton,
            meterInstllReplaceButton;
    BarChart barChart;

    private String jsonResponse = "", jsonMasterResponse = "", jsonResponse1 = "",sessionid="", empMasterDataCode = "", JsonMMGMasterData = "", currentDate = "", Tag = "M";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private MaterialDialog progress, installCountProgress;
    LinearLayout ll_hsc_replacement, ll_issue_meter_to_fixer, ll_allocation_work, ll_save_details_by_fixer, ll_completed_work, ll_authenticated_work;

    List<ShowMeterDataModel> showMtrList = new ArrayList<>();
    List<ShowMeterDataModel> showMtrAuthList = new ArrayList<>();
    List<ShowMeterDataModel> showMtrAuthenticatedList = new ArrayList<>();
    ShowMeterDataModel showMeterDataModel;
    private TextView workAllocatedTV, savedDetailsTV, tv_issue_meter_to_fixer, workCompletedTV, workAuthenticatedCompletedTV;

    MaterialDialog mmgMasterProgress;

    int allocationCount, savedFixerDetailCount, workCompletionCount, workAuthenticatedCount;
    String allocationCountStr, savedFixerDetailCountStr, workCompletionCountStr, workAuthenticatedCountStr;

    String fixerAllocationRights = "MEMMIR208", hscReplacementRight = "MIRD001357";

    private RealmOperations realmOperations;


    String formattedDate = "", startDateStr = "", endDateStr = "", startYearDateStr = "", endYearDateStr = "", fromDate = "", toDate = "";


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

    AppCompatRadioButton rbLeft, rbRight, rbCenter;
    boolean today = true, month = false, year = false;

    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";

    private SwipeRefreshLayout summarySwipeRefresher;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    public MeterManagementSystemFragment() {
        // Required empty public constructor
    }

    public static MeterManagementSystemFragment newInstance() {
        return new MeterManagementSystemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
      //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meter_management_system, container, false);

        mCon = getActivity();
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

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

        viewAllRelativeLayout = view.findViewById(R.id.viewAllRelativeLayout);
        barChart = (BarChart) view.findViewById(R.id.barchart);
        workAllocatedTV = view.findViewById(R.id.workAllocatedTV);
        workCompletedTV = view.findViewById(R.id.workCompletedTV);
        workAuthenticatedCompletedTV = view.findViewById(R.id.workAuthenticatedCompletedTV);
        savedDetailsTV = view.findViewById(R.id.savedDetailsTV);

        // tv_issue_meter_to_fixer = view.findViewById(R.id.tv_issue_meter_to_fixer);
        ll_issue_meter_to_fixer = view.findViewById(R.id.ll_issue_meter_to_fixer);
        ll_hsc_replacement = view.findViewById(R.id.ll_hsc_replacement);
        ll_allocation_work = view.findViewById(R.id.ll_allocation_work);
        ll_save_details_by_fixer = view.findViewById(R.id.ll_save_details_by_fixer);
        ll_completed_work = view.findViewById(R.id.ll_completed_work);
        ll_authenticated_work = view.findViewById(R.id.ll_authenticated_work);

        summarySwipeRefresher = view.findViewById(R.id.summarySwipeRefresher);
        summarySwipeRefresher.setOnRefreshListener(this);
        summarySwipeRefresher.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

        ll_issue_meter_to_fixer.setOnClickListener(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy ");
        currentDate = simpleDateFormat.format(new Date());

        realmOperations = new RealmOperations(mCon);

        empMasterDataCode = UtilitySharedPreferences.getPrefs(getActivity(), AppConstant.EMPCODE);
        try {
            empMasterDataCode = aes.decrypt( empMasterDataCode);
            sessionid=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.SID));

        } catch (Exception e) {
            e.printStackTrace();
        }


        rbLeft = view.findViewById(R.id.rbLeft);
        rbRight = view.findViewById(R.id.rbRight);
        rbCenter = view.findViewById(R.id.rbCenter);
        rbLeft.setOnClickListener(this);
        rbRight.setOnClickListener(this);
        rbCenter.setOnClickListener(this);
        STARTTIME = UtilitySharedPreferences.getPrefs(mCon, AppConstant.STARTTIME);
        ENDTIME = UtilitySharedPreferences.getPrefs(mCon, AppConstant.ENDTIME);
        Calendar c = Calendar.getInstance();

        try {
            final SimpleDateFormat sdff = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdff.parse(STARTTIME);
            final Date dateObji = sdff.parse(ENDTIME);
            ALERTSTARTTIME = new SimpleDateFormat("hh:mm aa").format(dateObj);
            ALERTENDTIME = new SimpleDateFormat("hh:mm aa").format(dateObji);
        } catch (final ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        time = df.format(c.getTime());

        checkTimes(dateParsing(STARTTIME), dateParsing(time), dateParsing(ENDTIME));

        currentDate();

        viewAllRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCon, MeterManagementGroupReportsActivity.class);
                startActivity(intent);
            }
        });

        storeManagementButton = view.findViewById(R.id.storeManagementButton);
        massMeterInstallationButton = view.findViewById(R.id.massMeterInstallationButton);
        //   meterDetailsEntryButton = view.findViewById(R.id.meterDetailsEntryButton);
        //   viewMeterDetailsButton = view.findViewById(R.id.viewMeterDetailsButton);
        // meterInstllReplaceButton = view.findViewById(R.id.meterInstllReplaceButton);
        //    meterInstallButton = view.findViewById(R.id.meterInstallButton);
        acceptMMGDept = view.findViewById(R.id.acceptMMGDept);
        // meterRemoveButton = view.findViewById(R.id.meterRemoveButton);


        storeManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, StoreManagementActivity.class));
            }
        });

        acceptMMGDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, AcceptMMGDeptActivity.class));
            }
        });

        massMeterInstallationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, MassMeterInstallation.class));
            }
        });

        ll_hsc_replacement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  startActivity(new Intent(mCon, MMGScreenActivity.class));
                startActivity(new Intent(mCon, MMGMainActivity.class));
            }
        });

      /*  meterInstllReplaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, MeterInstallationReplacementPrint.class));
            }
        });*/

        ll_allocation_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (today) {
                    fromDate = formattedDate;
                    toDate = formattedDate;
                } else if (month) {
                    fromDate = startDateStr;
                    toDate = endDateStr;
                } else if (year) {
                    fromDate = "";// startYearDateStr;
                    toDate = ""; //endYearDateStr;
                }

                Intent intent = new Intent(mCon, MeterInstallationActivity.class);
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate", toDate);
                startActivity(intent);
            }
        });

        ll_completed_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (today) {
                    fromDate = formattedDate;
                    toDate = formattedDate;
                } else if (month) {
                    fromDate = startDateStr;
                    toDate = endDateStr;
                } else if (year) {
                    fromDate = "";// startYearDateStr;
                    toDate = ""; //endYearDateStr;
                }

                Intent intent = new Intent(mCon, MeterInstallationSentAutheticationActivity.class);
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate", toDate);
                // intent.putExtra("strClick",clickStr);

                //nitin changes 19-02-2021
                //getlattest Ajit 19-02-20211

                startActivity(intent);

                //startActivity(new Intent(mCon, MeterInstallationSentAutheticationActivity.class));
            }
        });
        ll_authenticated_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (today) {
                    fromDate = formattedDate;
                    toDate = formattedDate;
                } else if (month) {
                    fromDate = startDateStr;
                    toDate = endDateStr;
                } else if (year) {
                    fromDate = ""; // startYearDateStr;
                    toDate = "";  //endYearDateStr;
                }
//change here
                Intent intent = new Intent(mCon, MeterInstallationContractorDetails.class);
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate", toDate);
                intent.putExtra("fromPage", "savedDetails");
                // intent.putExtra("strClick",clickStr);

                startActivity(intent);


                // startActivity(new Intent(mCon, MeterInstallationAutheticatedActivity.class));
            }
        });

      /*  meterRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, RemoveMeterActivity.class));
            }
        });*/

        ll_save_details_by_fixer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(mCon, ViewDetailsByContractor.class));

                if (today) {
                    fromDate = formattedDate;
                    toDate = formattedDate;
                } else if (month) {
                    fromDate = startDateStr;
                    toDate = endDateStr;
                } else if (year) {
                    fromDate = "";// startYearDateStr;
                    toDate = ""; //endYearDateStr;
                }

                Intent intent = new Intent(mCon, MeterInstallationContractorDetails.class);
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate", toDate);
                intent.putExtra("fromPage", "workreallocate");
                // intent.putExtra("strClick",clickStr);

                startActivity(intent);
            /*    Intent intent = new Intent(mCon, MeterInstallationContractorDetails.class);
                startActivity(intent);*/
            }
        });

        // getMeterInstallationList();
        //showInstalledMeterList();

        getMasterDataDownload();

        return view;
    }
    private void checkTimes(Date startime, Date current_time, Date endtime) {

        boolean isInBetween = false;
        if (endtime.after(startime)) {
            if (startime.before(current_time) && endtime.after(current_time)) {
                isInBetween = true;
            }
        } else if (current_time.after(startime) || current_time.before(endtime)) {
            isInBetween = true;
        }
        if (isInBetween) {
            submitData = true;
        } /*else {

            timeoutAlertBox();
        }*/

    }
    private Date dateParsing(String dtStart) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    private void timeoutAlertBox() {
        MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                .title(R.string.alert)
                .titleColorRes(R.color.red_500)
                .content(rtimem + " " + ALERTSTARTTIME + " " + "to" + " " + ALERTENDTIME)
                .contentColor(this.getResources().getColor(R.color.colorPrimary))
                .canceledOnTouchOutside(false)
                .positiveText(R.string.ok)

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        Intent intent = new Intent(mCon, SplashScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                        dialog.dismiss();
                    }
                }).show();
    }

    private void currentDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = calendar.getTime();
        // Create first day of year
        Calendar firstDayOfCurrentYear = Calendar.getInstance();
        firstDayOfCurrentYear.set(Calendar.DATE, 1);
        firstDayOfCurrentYear.set(Calendar.MONTH, 0);
        // Create last day of year

        Calendar lastDayOfCurrentYear = Calendar.getInstance();
        lastDayOfCurrentYear.set(Calendar.DATE, 31);
        lastDayOfCurrentYear.set(Calendar.MONTH, 11);
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        startDateStr = df.format(monthFirstDay);
        endDateStr = df.format(monthLastDay);
        startYearDateStr = df.format(firstDayOfCurrentYear.getTime());
        endYearDateStr = df.format(lastDayOfCurrentYear.getTime());

    }

    private void getMasterDataCount(String Filter) {
        String[] params = new String[4];

        params[0] = empMasterDataCode;
        params[1] = Tag;
        params[2] = Filter;
        params[3] = sessionid;

        if (connection.isConnectingToInternet()) {
            MasterCount masterCount = new MasterCount();
            masterCount.execute(params);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }


    }

    private class MasterCount extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.please_wait)
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
                username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.EMPCODE));
                password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));


                String paraName[] = new String[4];
                paraName[0] = "LoginUser";
                paraName[1] = "Tag";
                paraName[2] = "Filter";
                paraName[3] = "SessionToken";
                Log.e("MMGDashParams",Arrays.toString(params));

                jsonMasterResponse = invServices.getOtherData(Constants.URL, Constants.NameSpace, Constants.MobileDashboard,username,password, params, paraName);
                //Log.e("jsonMasterResponse",jsonMasterResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MasterCountModel[] enums = gson.fromJson(jsonMasterResponse, MasterCountModel[].class);

                if (enums.length > 0) {

               /*   allocationCount = Integer.parseInt(enums[0].getWORK_ALLOCATION());
                    savedFixerDetailCount = Integer.parseInt(enums[0].getWORK_SAVED());
                    workCompletionCount = Integer.parseInt(enums[0].getWORK_DONE());
                    workAuthenticatedCount = Integer.parseInt(enums[0].getTODAY_WORK());*/

                    allocationCountStr = enums[0].getWORK_ALLOCATED();
                    savedFixerDetailCountStr = enums[0].getSAVED_DETAILS_BY_FIXER();
                    workCompletionCountStr = enums[0].getWORK_DONE();
                    workAuthenticatedCountStr = enums[0].getWORK_RE_ALLOCATED();
                    //  Toast.makeText(mCon, ""+allocationCountStr+" "+savedFixerDetailCountStr, Toast.LENGTH_SHORT).show();

                    setDataCount();
                }

            } catch (Exception e) {
                Log.d("check", e.getMessage());
                //  Toast.makeText(mCon, e.getMessage()+"nitihniigin", Toast.LENGTH_SHORT).show();
                // error = e.toString();
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "Click Login Button", error);
            }
            progress.dismiss();
        }
    }

    private void setDataCount() {
        workAllocatedTV.setText(allocationCountStr);
        savedDetailsTV.setText(workAuthenticatedCountStr);
        //  Log.e("workAuthenticatedCountStr",workAuthenticatedCountStr);
        workCompletedTV.setText(workCompletionCountStr);
        workAuthenticatedCompletedTV.setText(savedFixerDetailCountStr);
        barChart();
    }

    private void barChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Float allocatedCount = Float.valueOf(allocationCountStr);
        Float savedCount = Float.valueOf(workAuthenticatedCountStr);
        Float workCount = Float.valueOf(workCompletionCountStr);
        Float authenticatedCount = Float.valueOf(savedFixerDetailCountStr);


        entries.add(new BarEntry(0, allocatedCount));
        entries.add(new BarEntry(1, savedCount));
        entries.add(new BarEntry(2, workCount));
        entries.add(new BarEntry(3, authenticatedCount));
        //   entries.add(new BarEntry(savedCount, 2));

        BarDataSet bardataset = new BarDataSet(entries, " ");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Allocated Work");
        //labels.add("HSC/Meter");
        labels.add("Saved Details");
        labels.add("Pending");
        labels.add("Authenticated");
        BarDataSet set = new BarDataSet(entries, getResources().getString(R.string.MMG_Reports));
        set.setValueFormatter(new LargeValueFormatter());

        set.setColors(new int[]{R.color.red_700, R.color.colorPrimary, R.color.yellow_700, R.color.green_700}, mCon);

        BarData data = new BarData(set);
        //BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        // barChart.setDescription("Set Bar Chart Description Here");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(2000);
    }

    private void getMeterInstallationList() {

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[9];
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String dateToStr = format.format(today);

        String bu = PreferenceUtil.getFirstZoneAvailable();

        params[0] = "C";
        params[1] = empcode;
        params[2] = "0";
        params[3] = "";
        params[4] = "01-Jan-2020";
        params[5] = dateToStr;
        params[6] = "0";
        params[7] = "-1";
        params[8] = "N";

        if (connection.isConnectingToInternet()) {
            MeterinstallShowAsyncTask meterinstallShowAsyncTask = new MeterinstallShowAsyncTask();
            meterinstallShowAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rbLeft:
                if (rbLeft.isChecked()) {
                    rbLeft.setTextColor(getActivity().getResources().getColor(R.color.white));
                    rbRight.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    // getMasterDataCount();
                    today = true;
                    year = false;
                    month = false;

                    getMasterDataCount("T");
                    //   tvNSCVisitPending.setText(String.valueOf(visitPendingStr));
                    //     tvNSCVisitCompleted.setText(String.valueOf(visitCompleted));

                }
                break;
            case R.id.rbCenter:
                if (rbCenter.isChecked()) {
                    rbLeft.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

                    rbRight.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getActivity().getResources().getColor(R.color.white));
                    // getMasterDataCount();
                    month = true;
                    today = false;
                    year = false;
                    getMasterDataCount("M");
                    //    tvNSCVisitPending.setText(String.valueOf(visitPendingMonthStr));
                    //   tvNSCVisitCompleted.setText(String.valueOf(visitMonthCompleted));
                    // month=false;

                }
                break;
            case R.id.rbRight:
                if (rbRight.isChecked()) {
                    rbRight.setTextColor(getActivity().getResources().getColor(R.color.white));
                    rbLeft.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    //  getMasterDataCount();
                    year = true;
                    today = false;
                    month = false;
                    getMasterDataCount("Y");
                    //      tvNSCVisitPending.setText(String.valueOf(visitPendingYearStr));
                    //     tvNSCVisitCompleted.setText(String.valueOf(visitYearCompleted));
                    // year=false;


                }
                break;
            case R.id.ll_issue_meter_to_fixer:
                Intent intent = new Intent(mCon, IssueMeterToFixerActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        //   getMeterInstallationList();
        //    showInstalledMeterList();
        //pinky added below three line 02/03/2022
        today = true;
        year = false;
        month = false;
        getMasterDataCount("T");
        rbLeft.setChecked(true);
        rbLeft.setTextColor(getActivity().getResources().getColor(R.color.white));
        rbRight.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        rbCenter.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        summarySwipeRefresher.setRefreshing(false);

    }

    @SuppressLint("StaticFieldLeak")
    private class MeterinstallShowAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            installCountProgress = new MaterialDialog.Builder(mCon)
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
                String paraNames[] = new String[9];
                paraNames[0] = "SourceType";
                paraNames[1] = "EmpCode";
                paraNames[2] = "ComplaintSubType";
                paraNames[3] = "ConsumerNo";
                paraNames[4] = "FromIssueDate";
                paraNames[5] = "ToIssueDate";
                paraNames[6] = "BU";
                paraNames[7] = "PC";
                paraNames[8] = "Tag";

                jsonResponse1 = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_MeterInstallationReplacementEntryShow_1, params, paraNames);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MeterInstallationReplacementEntryShow meterInstallationModel = gson.fromJson(jsonResponse1, MeterInstallationReplacementEntryShow.class);
                List<ShowMeterDataModel> downloadShowMeterData = meterInstallationModel.getDownloadMeterData();

                showMtrList.clear();
                showMtrAuthList.clear();
                showMtrAuthenticatedList.clear();
                for (ShowMeterDataModel meterDetails : meterInstallationModel.getDownloadMeterData()) {

                    if (downloadShowMeterData.size() > 0) {
                        if (meterDetails.getSTATUS().equalsIgnoreCase("New")) {
                            showMeterDataModel = new ShowMeterDataModel(meterDetails.getMFX_NEW_SERIAL_NO(), meterDetails.getMFX_ISSUE_DATE(), meterDetails.getTOD_NAME(),
                                    meterDetails.getMDT_MPHASE(), meterDetails.getMFX_REFNO(), meterDetails.getDIGIT(), meterDetails.getMF(), meterDetails.getMFX_SERVICENO(), meterDetails.getMETER_SEQ_CODE(),
                                    meterDetails.getMFX_OLD_METER_NO(), meterDetails.getMETER_INDICATION(), meterDetails.getMETER_INDICATION_ID(), meterDetails.getMFX_SERVICE_TYPE(),
                                    meterDetails.getSERVICE_TOD_FLAG(), meterDetails.getOLD_METER_TOD_FLAG(), meterDetails.getBU(), meterDetails.getPC(), meterDetails.getFIXERCD(), meterDetails.getMFX_FIXERCODE(),
                                    meterDetails.getCONTACTNO(), meterDetails.getNEWMFGCODE(), meterDetails.getOLDMFGCODE(), meterDetails.getCONSUMERNAME(), meterDetails.getNAME_ADDRESS(),
                                    meterDetails.getPROPERTY_ASSESSMENT_NO(), meterDetails.getSRM_LATITUDE(), meterDetails.getSRM_LONGITUDE(), meterDetails.getSRM_S_GIS_ID(),
                                    meterDetails.getTFM_TARIFF_NAME(), meterDetails.getBU_NAME(), meterDetails.getPC_NAME(), meterDetails.getCONTRACTORFIXER(), meterDetails.getDTC(),
                                    meterDetails.getPOLE(), meterDetails.getCOMPLAINT_SUBTYPE(), meterDetails.getOBSERVATION(), meterDetails.getOBS_ID(), meterDetails.getPRIM_SEC(),
                                    meterDetails.getCOM_SOURCECD(), meterDetails.getMRT_OLDMETER_STATUS(), meterDetails.getMRT_OLDMETER_READING(), meterDetails.getMRT_OLDMETER_OBSERVATION(),
                                    meterDetails.getMRT_TRANSACTION_ID(), meterDetails.getMFX_VENDOR_ID(), meterDetails.getMFX_PRINT_COUNT(), meterDetails.getWALKROUTE(), meterDetails.getSEQUENCE_NO(),
                                    meterDetails.getMCT_CONNTYPE_NAME(), "", meterDetails.getSTATUS(), meterDetails.getSUBMITION_DATE(), meterDetails.getALLOCATIONDATE()
                                    , meterDetails.getMRT_PROCESS_DATE(), meterDetails.getMI_ISCOMMISSIONED(), meterDetails.getMI_ACTION(), meterDetails.getMICPATH());
                            showMtrList.add(showMeterDataModel);
                            allocationCount = showMtrList.size();
                            ;
                            workAllocatedTV.setText(String.valueOf(allocationCount));

                        }
                        if (meterDetails.getSTATUS().equalsIgnoreCase("Sent To Authentication")) {
                            showMeterDataModel = new ShowMeterDataModel(meterDetails.getMFX_NEW_SERIAL_NO(), meterDetails.getMFX_ISSUE_DATE(), meterDetails.getTOD_NAME(),
                                    meterDetails.getMDT_MPHASE(), meterDetails.getMFX_REFNO(), meterDetails.getDIGIT(), meterDetails.getMF(), meterDetails.getMFX_SERVICENO(), meterDetails.getMETER_SEQ_CODE(),
                                    meterDetails.getMFX_OLD_METER_NO(), meterDetails.getMETER_INDICATION(), meterDetails.getMETER_INDICATION_ID(), meterDetails.getMFX_SERVICE_TYPE(),
                                    meterDetails.getSERVICE_TOD_FLAG(), meterDetails.getOLD_METER_TOD_FLAG(), meterDetails.getBU(), meterDetails.getPC(), meterDetails.getFIXERCD(), meterDetails.getMFX_FIXERCODE(),
                                    meterDetails.getCONTACTNO(), meterDetails.getNEWMFGCODE(), meterDetails.getOLDMFGCODE(), meterDetails.getCONSUMERNAME(), meterDetails.getNAME_ADDRESS(),
                                    meterDetails.getPROPERTY_ASSESSMENT_NO(), meterDetails.getSRM_LATITUDE(), meterDetails.getSRM_LONGITUDE(), meterDetails.getSRM_S_GIS_ID(),
                                    meterDetails.getTFM_TARIFF_NAME(), meterDetails.getBU_NAME(), meterDetails.getPC_NAME(), meterDetails.getCONTRACTORFIXER(), meterDetails.getDTC(),
                                    meterDetails.getPOLE(), meterDetails.getCOMPLAINT_SUBTYPE(), meterDetails.getOBSERVATION(), meterDetails.getOBS_ID(), meterDetails.getPRIM_SEC(),
                                    meterDetails.getCOM_SOURCECD(), meterDetails.getMRT_OLDMETER_STATUS(), meterDetails.getMRT_OLDMETER_READING(), meterDetails.getMRT_OLDMETER_OBSERVATION(),
                                    meterDetails.getMRT_TRANSACTION_ID(), meterDetails.getMFX_VENDOR_ID(), meterDetails.getMFX_PRINT_COUNT(), meterDetails.getWALKROUTE(), meterDetails.getSEQUENCE_NO(),
                                    meterDetails.getMCT_CONNTYPE_NAME(), "", meterDetails.getSTATUS(), meterDetails.getSUBMITION_DATE(), meterDetails.getALLOCATIONDATE()
                                    , meterDetails.getMRT_PROCESS_DATE(), meterDetails.getMI_ISCOMMISSIONED(), meterDetails.getMI_ACTION(), meterDetails.getMICPATH());

                            showMtrAuthList.add(showMeterDataModel);
                            workCompletionCount = showMtrAuthList.size();
                            ;
                            workCompletedTV.setText(String.valueOf(workCompletionCount));

                        }
                        if (meterDetails.getSTATUS().equalsIgnoreCase("Authenticated")) {
                            showMeterDataModel = new ShowMeterDataModel(meterDetails.getMFX_NEW_SERIAL_NO(), meterDetails.getMFX_ISSUE_DATE(), meterDetails.getTOD_NAME(),
                                    meterDetails.getMDT_MPHASE(), meterDetails.getMFX_REFNO(), meterDetails.getDIGIT(), meterDetails.getMF(), meterDetails.getMFX_SERVICENO(), meterDetails.getMETER_SEQ_CODE(),
                                    meterDetails.getMFX_OLD_METER_NO(), meterDetails.getMETER_INDICATION(), meterDetails.getMETER_INDICATION_ID(), meterDetails.getMFX_SERVICE_TYPE(),
                                    meterDetails.getSERVICE_TOD_FLAG(), meterDetails.getOLD_METER_TOD_FLAG(), meterDetails.getBU(), meterDetails.getPC(), meterDetails.getFIXERCD(), meterDetails.getMFX_FIXERCODE(),
                                    meterDetails.getCONTACTNO(), meterDetails.getNEWMFGCODE(), meterDetails.getOLDMFGCODE(), meterDetails.getCONSUMERNAME(), meterDetails.getNAME_ADDRESS(),
                                    meterDetails.getPROPERTY_ASSESSMENT_NO(), meterDetails.getSRM_LATITUDE(), meterDetails.getSRM_LONGITUDE(), meterDetails.getSRM_S_GIS_ID(),
                                    meterDetails.getTFM_TARIFF_NAME(), meterDetails.getBU_NAME(), meterDetails.getPC_NAME(), meterDetails.getCONTRACTORFIXER(), meterDetails.getDTC(),
                                    meterDetails.getPOLE(), meterDetails.getCOMPLAINT_SUBTYPE(), meterDetails.getOBSERVATION(), meterDetails.getOBS_ID(), meterDetails.getPRIM_SEC(),
                                    meterDetails.getCOM_SOURCECD(), meterDetails.getMRT_OLDMETER_STATUS(), meterDetails.getMRT_OLDMETER_READING(), meterDetails.getMRT_OLDMETER_OBSERVATION(),
                                    meterDetails.getMRT_TRANSACTION_ID(), meterDetails.getMFX_VENDOR_ID(), meterDetails.getMFX_PRINT_COUNT(), meterDetails.getWALKROUTE(), meterDetails.getSEQUENCE_NO(),
                                    meterDetails.getMCT_CONNTYPE_NAME(), "", meterDetails.getSTATUS(), meterDetails.getSUBMITION_DATE(), meterDetails.getALLOCATIONDATE(),
                                    meterDetails.getMRT_PROCESS_DATE(), meterDetails.getMI_ISCOMMISSIONED(), meterDetails.getMI_ACTION(), meterDetails.getMICPATH());

                            showMtrAuthenticatedList.add(showMeterDataModel);
                            workAuthenticatedCount = showMtrAuthenticatedList.size();
                            ;
                            workAuthenticatedCompletedTV.setText(String.valueOf(workAuthenticatedCount));


                        }
                    }
                }

                installCountProgress.dismiss();


            } catch (Exception e) {
                System.out.println(e);
                installCountProgress.dismiss();
            }
            installCountProgress.dismiss();

        }
    }

    private void showInstalledMeterList() {

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String bu = PreferenceUtil.getFirstZoneAvailable();

        String mmgFixer = PreferenceUtil.getMMGFixer();
        String params[] = new String[7];
        params[0] = "0";
        params[1] = "0";
        params[2] = "ALL";
        params[3] = "01-Jun-2020";
        params[4] = currentDate;
        params[5] = "0";
        params[6] = "0";

      /*    params[0] = "C";
        params[1] = PreferenceUtil.getEmployeeCode();
        params[2] = "0";
        params[3] = "";
        params[4] = "01-Jan-2020";
        params[5] = dateToStr;
        params[6] = "0";
        params[7] = "-1";
        params[8] = "N";


       params[0] = "0";
        params[1] = "0";
        params[2] = "ALL";
        params[3] = "01-Jan-2020";
        params[4] = currentDate;
        params[5] = "0";
        params[6] = "0";


      params[0] = "0";
        params[1] = "0";
        params[2] = "ALL";
        params[3] = "";
        params[4] = "";
        params[5] = "0";
        params[6] = mmgFixer;*/

        if (connection.isConnectingToInternet()) {
            MMGGetAllMtrInstallByCont mtrInstallByCont = new MMGGetAllMtrInstallByCont();
            mtrInstallByCont.execute(params);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class MMGGetAllMtrInstallByCont extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[7];
                paraName[0] = "TypeOfWork";
                paraName[1] = "TypeOfArea";
                paraName[2] = "RequestType";
                paraName[3] = "Fdate";
                paraName[4] = "Tdate";
                paraName[5] = "Zone";
                paraName[6] = "Contractor";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMGGetAllMtrInstallByCont, params, paraName);

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MMGGetAllMtrInstallByContModel[] enums = gson.fromJson(jsonResponse, MMGGetAllMtrInstallByContModel[].class);
                if (enums.length > 0) {
                    savedFixerDetailCount = enums.length;
                    savedDetailsTV.setText(String.valueOf(savedFixerDetailCount));
                    //  Log.e("savedFixerDetailCount", String.valueOf(savedFixerDetailCount));
                    //Log.d("enums", ""+enums.length);

                }

                barChart();
            } catch (Exception e) {
                Log.d("Count", " " + e.getMessage());
            }
        }


    }


    private void getMasterDataDownload() {
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
        } else {
            getMasterDataCount("T");

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
                    for (MMGSubZoneModel MMGSubZoneModel : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterSubZones()) {
                        int id = (MMGSubZoneModel.getPCM_PC_ID());
                        int buId = (MMGSubZoneModel.getPCM_BU_ID());
                        MMGSubZoneModel MMGSubZoneModelData = new MMGSubZoneModel(id, MMGSubZoneModel.getPCM_PC_NAME(), buId);
                        realmOperations.addIssueToMeterSubZone(MMGSubZoneModelData);

                    }

                    // Insert Request Type  in RequestTypeModel Table


                    realmOperations.deleteIssueToMeterFixrRequestTypeTable();
                    for (MMGRequestTypeModel MMGRequestTypeModel1 : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterRequstType()) {

                        if (!MMGRequestTypeModel1.getSelectVal().equalsIgnoreCase("Select")) {
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
                mmgMasterProgress.dismiss();
                getMasterDataCount("T");
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid_MMG", error);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(mCon);
        mCon = context.getApplicationContext();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCon = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App)mCon.getApplicationContext();
        if (myApp.wasInBackground) {
            getActivity().finish();
            startActivity(new Intent(mCon, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        realmOperations.close();
        ((App) mCon.getApplicationContext()).startActivityTransitionTimer();
    }

}
