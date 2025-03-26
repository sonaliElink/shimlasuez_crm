package elink.suezShimla.water.crm.Complaint;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
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
import java.util.Locale;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.Allocation.Activity.WorkAllocationCompletionActivity;
import elink.suezShimla.water.crm.Complaint.Allocation.Model.GetWorkAllocatedDataModel;
import elink.suezShimla.water.crm.Complaint.Reallocation.Activity.WorkReAllocationCompletionActivity;
import elink.suezShimla.water.crm.Complaint.Reallocation.Model.ReallocationResponseDataModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Activity.ComplaintHistoryActivity;
import elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.Model.TodayWorkCompletionResponseModel;
import elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.TodayWorkCompletedActivity;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Activity.WorkCompletedActivity;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.WorkCompletionResponseModel;
import elink.suezShimla.water.crm.Complaint.ZoneAndWard.Adapter.ZoneWardAdapter;
import elink.suezShimla.water.crm.Complaint.ZoneAndWard.Model.ZoneWardModel;
import elink.suezShimla.water.crm.Complaint.ZoneAndWard.ZoneAndWardDetails.ZoneAndWardDetailsActivity;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSourceModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.FinishActionModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SiteEngineerModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.MasterData.DownloadMasterData;
import elink.suezShimla.water.crm.MasterData.MasterCountModel;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class ComplaintFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private Context mCon;
    private RecyclerView zoneWardRecyclerView;
    private SearchView searchView;
    private ZoneWardAdapter zoneWardAdapter;
    private List<ZoneWardModel> zoneWardModelList;
    private List<ZoneModel> zoneModelList;
    private List<SubZoneModel> subZoneModelList;
    private RelativeLayout viewAllRelativeLayout, dashBoardRelativeLayout, historyRegistrationRelativeLayout;
         /*   workAllocationRelativeLayout, workReallocationRelativeLayout,
            workCompletionRelativeLayout, todayCompletedRelativeLayout;*/

    AppCompatRadioButton rbLeft,rbRight,rbCenter;
    boolean today=true,month=false,year=false,common=false;


    private RealmOperations realmOperations;
    BarChart barChart;

    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private MaterialDialog progress, progressReallocation, progressCompletion,progressToday;
    private String jsonResponseAllocated="",jsonResponse="", jsonResponse1 = "", jsonResponse2 = "",departmentID="", mainComplaintID="0",strCommon="Today";
    private int registrationCount = 0,workAlocatedCount = 0, reallocationCount = 0, workCompletionCount = 0, totalWork = 0;
    private MaterialButton RegAndHistoryButton;
   // allocationButton, reallocationButton, completionButton, locationButton, todayCompleteWorkButton;
    private TextView completionTv,disconectTv, reallocationTv, allocationTv,todayCompleteWorkTv;
    String workAlocatedCountStr, reallocationCountStr, workCompletionCountStr, totalWorkStr;
    private ComplaintTypeModel complaintTypeModel;
    private ArrayList<String> complaintTypeList;
    LinearLayout ll_allocation_work,ll_reallocation_work,ll_work_completion,ll_today_work_completion,ll_main;
        private SwipeRefreshLayout summarySwipeRefresher;


    String sessionid="",systemAdmin = "SYSADM001", sytemSubAdmin = "SADMIN423", complaintRights = "CMOC00021", allocationRights = "OCWA00312", reAllocationRights = "OCWR00315",
            jsonMasterResponse = "", empMasterDataCode="",rights="",Tag="C",Filter="Y" ;
    String formattedDate="",startDateStr="",endDateStr="",startYearDateStr="",endYearDateStr="",fromDate="",toDate="";
    Intent intent;
    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    public ComplaintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
      //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);

        mCon = getActivity();
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

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
        try {
            sessionid=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.SID));
        } catch (Exception e) {
            e.printStackTrace();
        }

        zoneWardRecyclerView = view.findViewById(R.id.zoneWardRecyclerView);
        barChart = (BarChart) view.findViewById(R.id.barchart);

        completionTv = view.findViewById(R.id.completionTv);
        reallocationTv = view.findViewById(R.id.reallocationTv);
        allocationTv = view.findViewById(R.id.allocationTv);
        todayCompleteWorkTv = view.findViewById(R.id.todayCompleteWorkTv);

        searchView = view.findViewById(R.id.searchView);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        summarySwipeRefresher = view.findViewById(R.id.summarySwipeRefresher);
        summarySwipeRefresher.setOnRefreshListener(this);
        summarySwipeRefresher.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

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

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                // hideSoftKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                filter(query);
                return false;
            }
        });

        dashBoardRelativeLayout = view.findViewById(R.id.dashBoardRelativeLayout);
        historyRegistrationRelativeLayout = view.findViewById(R.id.historyRegistrationRelativeLayout);

        ll_allocation_work = view.findViewById(R.id.ll_allocation_work);
        ll_reallocation_work = view.findViewById(R.id.ll_reallocation_work);
        ll_work_completion = view.findViewById(R.id.ll_work_completion);
        ll_today_work_completion = view.findViewById(R.id.ll_today_work_completion);
        ll_main = view.findViewById(R.id.ll_main);
        /*ll_allocation_work = view.findViewById(R.id.workAllocationRelativeLayout);
        workReallocationRelativeLayout = view.findViewById(R.id.workReallocationRelativeLayout);
        workCompletionRelativeLayout = view.findViewById(R.id.workCompletionRelativeLayout);
        todayCompletedRelativeLayout = view.findViewById(R.id.todayCompletedRelativeLayout);*/
        viewAllRelativeLayout = view.findViewById(R.id.viewAllRelativeLayout);


        rbLeft =view.findViewById(R.id.rbLeft);
        rbRight= view.findViewById(R.id.rbRight);
        rbCenter= view.findViewById(R.id.rbCenter);
        rbLeft.setOnClickListener(this);
        rbRight.setOnClickListener(this);
        rbCenter.setOnClickListener(this);


        ll_allocation_work.setOnClickListener(this);

        ll_reallocation_work.setOnClickListener(this);
        ll_work_completion.setOnClickListener(this);
        ll_today_work_completion.setOnClickListener(this);


       // setVisibility(false, false, false, false, false, false);

        zoneWardAdapter = new ZoneWardAdapter(mCon);
        zoneWardRecyclerView.setHasFixedSize(true);
        zoneWardRecyclerView.setLayoutManager(new LinearLayoutManager(mCon, LinearLayoutManager.HORIZONTAL, false));
        zoneWardRecyclerView.setItemAnimator(new DefaultItemAnimator());

        zoneWardModelList = new ArrayList<>();

        departmentID = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEPARTMENTID);

        try {
            departmentID=  new AesAlgorithm().decrypt(departmentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if((departmentID.equalsIgnoreCase("1"))||(departmentID.equalsIgnoreCase("7"))||
                (departmentID.equalsIgnoreCase("10"))||(departmentID.equalsIgnoreCase("11"))||
                (departmentID.equalsIgnoreCase("12"))||(departmentID.equalsIgnoreCase("17"))||
                (departmentID.equalsIgnoreCase("18"))){
        //    ll_main.setVisibility(View.GONE);
        }*/
        complaintTypeList = realmOperations.fetchComplaintTypeByDept(departmentID);
        String cc="";
        for(int i=0;i<complaintTypeList.size();i++){
            cc = complaintTypeList.get(i);
        }
        Log.d("cc",cc);
        complaintTypeModel = realmOperations.fetchComplaintTypeByName(cc);
        if(complaintTypeModel==null){
            mainComplaintID="0";
        }else {
            mainComplaintID = String.valueOf(complaintTypeModel.getCMTM_ID());
        }


        viewAllRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCon, ZoneAndWardDetailsActivity.class);
                i.putExtra("GETWARD", "All");
                i.putExtra("GETWARD_ID", "-1");
                i.putExtra("GETZONE_ID", "-1");
                startActivity(i);
            }
        });

        RegAndHistoryButton = view.findViewById(R.id.RegAndHistoryButton);
     /*   allocationButton = view.findViewById(R.id.allocationButton);
        reallocationButton = view.findViewById(R.id.reallocationButton);
        completionButton = view.findViewById(R.id.completionButton);
        todayCompleteWorkButton = view.findViewById(R.id.todayCompleteWorkButton);
        locationButton = view.findViewById(R.id.locationButton);*/

        ll_allocation_work = view.findViewById(R.id.ll_allocation_work);
        ll_reallocation_work = view.findViewById(R.id.ll_reallocation_work);
        ll_work_completion = view.findViewById(R.id.ll_work_completion);
        ll_today_work_completion = view.findViewById(R.id.ll_today_work_completion);

        ll_allocation_work.setOnClickListener(this);

        String isSiteEng=null;
        try {
            isSiteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

         rights = PreferenceUtil.getRights();



        if (MessageWindow.userAccess(mCon,rights, systemAdmin, sytemSubAdmin)) {
            // hideItem(R.id.nav_complaint, true);
        } else {
            // hideItem(R.id.nav_complaint, false);
        }


      /*  if (rights.contains("SYSADM001") || rights.contains("SADMIN423")) {
            setVisibility(true, true, true, true, true, true);
        } else {

            CMOC00021 - Complaint Registration
            OCWA00312  -  Work Allocation
            OCWR00315  -  Work Re-allocation
            //viewVisibility(dashBoardRelativeLayout, rights, "MICM00779");
            viewVisibility(historyRegistrationRelativeLayout, rights, "CCCIOG159");
            viewVisibility(ll_allocation_work, rights, "OCWA00312");
            viewVisibility(ll_reallocation_work, rights, "OCWR00315");
           *//* viewVisibility(ll_work_completion, rights, "OCWC00318");
            viewVisibility(ll_today_work_completion, rights, "OCWC00318");*//*
        }*/
        if(isSiteEng.equals("SiteEng")){
            ll_today_work_completion.setVisibility(View.VISIBLE);
        }else{
//            todayCompletedRelativeLayout.setVisibility(View.GONE);
            ll_today_work_completion.setVisibility(View.VISIBLE);
        }
        RegAndHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if (MessageWindow.userAccess(mCon,rights, complaintRights)) {
                    historyRegistrationRelativeLayout.setVisibility(View.VISIBLE);
                    checkMasterDataBase(ComplaintHistoryActivity.class,"");

                } else {
                    MessageWindow.errorWindow(mCon, "Access Denied");

                }
            }
        });

        empMasterDataCode = UtilitySharedPreferences.getPrefs(getActivity(), AppConstant.EMPCODE);
        try {
            empMasterDataCode = aes.decrypt( empMasterDataCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* getAllocatedDataCount();
        getWorkReallocatedCount();
        getWorkCompletionCount();
        getTodayCompletedWork();
*/
        currentDate();

        getMasterDataCount("T");
        loadRecyclerData();

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
        } else {

           timeoutAlertBox();
        }

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

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);



        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        startDateStr = df.format(monthFirstDay);
        endDateStr = df.format(monthLastDay);
        startYearDateStr=df.format(firstDayOfCurrentYear.getTime());
        endYearDateStr=df.format(lastDayOfCurrentYear.getTime());

    }

    private void loadRecyclerData() {
        try {
            String zoneIdStr = PreferenceUtil.getZone();

            List<String> elephantList = Arrays.asList(zoneIdStr.split(","));

            for (int i = 0; i < elephantList.size(); i++) {
                int id = Integer.parseInt(elephantList.get(i));
                zoneModelList = realmOperations.fetchZoneById(id);

                for (ZoneModel zoneModel : zoneModelList) {

                    subZoneModelList = realmOperations.fetchSubZoneByZoneId(zoneModel.getBUM_BU_ID());

                    for (SubZoneModel subZoneModel : subZoneModelList) {

                        String id_name = zoneModel.getBUM_BU_ID() + "-" + zoneModel.getBU_NAME();

                        ZoneWardModel zoneWardModel = new ZoneWardModel(zoneModel.getBUM_BU_ID(), subZoneModel.getPCM_PC_ID(), id_name, subZoneModel.getPCM_PC_NAME());
                        zoneWardModelList.add(zoneWardModel);
                        zoneWardAdapter.addList(zoneWardModelList);
                        zoneWardRecyclerView.setAdapter(zoneWardAdapter);
                    }
                }
            }
        } catch (Exception e) {
            Log.d("check", e.getMessage());
        }
    }

    public void filter(String input) {
        List<ZoneWardModel> list = new ArrayList<>();

        for (ZoneWardModel item : zoneWardModelList) {
            if (item.getZone().toLowerCase().contains(input.toLowerCase()) || item.getWard().toLowerCase().contains(input.toLowerCase())) {
                list.add(item);
            }
        }
        zoneWardAdapter.filterList(list);
    }

    private void checkMasterDataBase(Class aClass,String clickStr) {
        ComplaintTypeModel complaintTypeModelExist = realmOperations.getComplaintTypeExist();
        ComplaintSubTypeModel complaintSubTypeModelExist = realmOperations.getComplaintSubTypeExist();
        ComplaintSourceModel complaintSourceModelExist = realmOperations.getComplaintSourceExist();
        ZoneModel zoneModelExist = realmOperations.getZoneExist();
        SubZoneModel subZoneModelExist = realmOperations.getSubZoneExist();
        SiteEngineerModel siteEngineerModelExist = realmOperations.getSiteEngineerExist();
        FinishActionModel finishActionModelExist = realmOperations.getFinishActionExist();
        if (complaintTypeModelExist != null && complaintSubTypeModelExist != null && complaintSourceModelExist != null && zoneModelExist != null
                && subZoneModelExist != null && siteEngineerModelExist != null && finishActionModelExist != null) {

            if(today){
                fromDate= formattedDate;
                toDate= formattedDate;
            }else if(month){
                fromDate= startDateStr;
                toDate= endDateStr;
            }else if(year){
                fromDate= "";// startYearDateStr;
                toDate=""; //endYearDateStr;
            }

            intent = new Intent(mCon, aClass);
            intent.putExtra("fromDate",fromDate);
            intent.putExtra("toDate",toDate);
            intent.putExtra("strClick",clickStr);

            startActivity(intent);
           // startActivity(new Intent(mCon, aClass));
        } else {
            DownloadMasterData downloadMasterData = new DownloadMasterData();
            downloadMasterData.downloadData(mCon);
        }
    }

    private void getAllocatedDataCount() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String dateToStr = format.format(today);

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[16];

        params[0] = "";   // test-data (02-Apr-2019)
        params[1] = dateToStr;  // test-data (06-May-2019)
        params[2] = "0";
        params[3] = "0";
        params[4] = "0";
        params[5] = "";
        params[6] = "0";
        params[7] = mainComplaintID;
        params[8] = "0";
        params[9] = "0";
        params[10] = "0";
        params[11] = "0";
        params[12] = "0";
        params[13] = empcode;
        params[14] = "2";
        params[15] = "0";


        // Log.d("check", "" + Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            GetAllocatedWork getAllocatedWork = new GetAllocatedWork();
            getAllocatedWork.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rbLeft:
                if(rbLeft.isChecked()){
                    rbLeft.setTextColor(getActivity().getResources().getColor(R.color.white));
                    rbRight.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    // getMasterDataCount();
                    today=true;
                    year=false;
                    month=false;

                    common= true;
                    strCommon="Today";

                    getMasterDataCount("T");
                 //   tvNSCVisitPending.setText(String.valueOf(visitPendingStr));
               //     tvNSCVisitCompleted.setText(String.valueOf(visitCompleted));

                }
                break;
            case R.id.rbCenter:
                if(rbCenter.isChecked()){
                    rbLeft.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

                    rbRight.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getActivity().getResources().getColor(R.color.white));
                    // getMasterDataCount();
                    month=true;
                    today=false;
                    year=false;
                    common= true;
                    strCommon="Monthly";


                    getMasterDataCount("M");
                //    tvNSCVisitPending.setText(String.valueOf(visitPendingMonthStr));
                 //   tvNSCVisitCompleted.setText(String.valueOf(visitMonthCompleted));
                    // month=false;

                }
                break;
            case R.id.rbRight:
                if(rbRight.isChecked()){
                    rbRight.setTextColor(getActivity().getResources().getColor(R.color.white));
                    rbLeft.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    //  getMasterDataCount();
                    year=true;
                    today=false;
                    month=false;
                    common= true;
                    strCommon="All";

                    getMasterDataCount("Y");
              //      tvNSCVisitPending.setText(String.valueOf(visitPendingYearStr));
               //     tvNSCVisitCompleted.setText(String.valueOf(visitYearCompleted));
                    // year=false;


                }
                break;


            case R.id.ll_allocation_work:
                checkMasterDataBase(WorkAllocationCompletionActivity.class,"AndroidAllocated");
//                if (MessageWindow.userAccess(mCon,rights, systemAdmin, sytemSubAdmin)) {
//                    ll_allocation_work.setVisibility(View.VISIBLE);
//                  //  checkMasterDataBase(WorkAllocationActivity.class,"Allocated");
//                    checkMasterDataBase(WorkAllocationCompletionActivity.class,"AndroidAllocated");
//                //    Toast.makeText(mCon, ""+strCommon, Toast.LENGTH_SHORT).show();
//
//            } else {
//                    if (MessageWindow.userAccess(mCon,rights, allocationRights)) {
//                        ll_allocation_work.setVisibility(View.VISIBLE);
//                      //  checkMasterDataBase(WorkAllocationActivity.class,"Allocated");
//                        checkMasterDataBase(WorkAllocationCompletionActivity.class,"AndroidAllocated");
//                      //  Toast.makeText(mCon, ""+strCommon, Toast.LENGTH_SHORT).show();
//
//
//                    } else {
//                        MessageWindow.errorWindow(mCon, "Access Denied");
//
//                    }
//
//                }


                break;

              case R.id.ll_reallocation_work:
                  checkMasterDataBase(WorkReAllocationCompletionActivity.class,"ReAllocated");
//                  if (MessageWindow.userAccess(mCon,rights, systemAdmin, sytemSubAdmin)) {
//                      ll_reallocation_work.setVisibility(View.VISIBLE);
//
//
//                //      checkMasterDataBase(WorkReallocationActivity.class,"ReAllocated");
//                      checkMasterDataBase(WorkReAllocationCompletionActivity.class,"ReAllocated");
//
//                  } else {
//                      if (MessageWindow.userAccess(mCon,rights, reAllocationRights)) {
//                          ll_reallocation_work.setVisibility(View.VISIBLE);
//                          checkMasterDataBase(WorkReAllocationCompletionActivity.class,"ReAllocated");
//
//                      } else {
//                          MessageWindow.errorWindow(mCon, "Access Denied");
//                      }
//
//                  }

                break;

              case R.id.ll_work_completion:
              //    checkMasterDataBase(WorkCompletionActivity.class,"Completed");
                  checkMasterDataBase(WorkCompletedActivity.class,"AndroidCompleted");

                  break;

              case R.id.ll_today_work_completion:
                  checkMasterDataBase(TodayWorkCompletedActivity.class,"All");
                break;





            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadRecyclerData();
        //pinky added below three line 02/03/2022
        today=true;
        year=false;
        month=false;

        getMasterDataCount("T");

        common= true;
        strCommon="Today";

        rbLeft.setChecked(true);
        rbLeft.setTextColor(getActivity().getResources().getColor(R.color.white));
        rbRight.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        rbCenter.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        // getMasterDataCount();
/*        today=true;
        year=false;
        month=false;*/

        //getMasterDataCount("T");
        summarySwipeRefresher.setRefreshing(false);

    }

    @SuppressLint("StaticFieldLeak")
    private class GetAllocatedWork extends AsyncTask<String, Void, Void> {
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
                String paraNames[] = new String[16];
                paraNames[0] = "W_FromDate";
                paraNames[1] = "W_ToDate";
                paraNames[2] = "W_BU";
                paraNames[3] = "W_PC";
                paraNames[4] = "W_Sec";
                paraNames[5] = "w_consumer";
                paraNames[6] = "w_usertag";
                paraNames[7] = "comp_type";
                paraNames[8] = "comp_sub_type";
                paraNames[9] = "SR";                //0
                paraNames[10] = "DMA";              //0
                paraNames[11] = "Source";           //0
                paraNames[12] = "CustomerType";     //0
                paraNames[13] = "LoginEmpCode";
                paraNames[14] = "Origin";
                paraNames[15] = "ServType";
                jsonResponseAllocated = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetWorkAllocationData, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                if (!jsonResponse1.equals("[]")) {
                    GetWorkAllocatedDataModel[] enums = gson.fromJson(jsonResponseAllocated, GetWorkAllocatedDataModel[].class);

                    if (enums != null && enums.length > 0) {
                        workAlocatedCount = enums.length;
                        allocationTv.setText(String.valueOf(workAlocatedCount));

                        ValueAnimator animator = ValueAnimator.ofInt(0, workAlocatedCount);
                        animator.setDuration(3000);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                allocationTv.setText(animation.getAnimatedValue().toString());
                            }
                        });
                        animator.start();
                    }
                    progress.dismiss();
                }
            } catch (Exception e) {

                }


            progress.dismiss();
        }
    }

    private void getWorkReallocatedCount() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String dateToStr = format.format(today);

        String empCode = null;
        try {
            // Decrypt EmpCode
            empCode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[16];

        params[0] = "01-Jan-2011";
        params[1] = dateToStr;
        params[2] = "0";
        params[3] = "-1";
        params[4] = "0";
        params[5] = "";
        params[6] = "0";
        params[7] = mainComplaintID;
        params[8] = "0";

        params[9] = "0";
        params[10] = "0";
        params[11] = "0";
        params[12] = "0";
        params[13] = empCode;
        params[14] = "2";
        params[15] = "0";

        Log.d("check", "" + Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            WorkReallocation workReallocation = new WorkReallocation();
            workReallocation.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class WorkReallocation extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressReallocation = new MaterialDialog.Builder(mCon)
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
                String paraNames[] = new String[16];
                paraNames[0] = "W_FromDate";
                paraNames[1] = "W_ToDate";
                paraNames[2] = "W_BU";
                paraNames[3] = "W_PC";
                paraNames[4] = "W_Sec";
                paraNames[5] = "w_consumer";
                paraNames[6] = "w_usertag";
                paraNames[7] = "comp_type";
                paraNames[8] = "comp_sub_type";

                paraNames[9] = "SR";                //0
                paraNames[10] = "DMA";              //0
                paraNames[11] = "Source";           //0
                paraNames[12] = "CustomerType";     //0
                paraNames[13] = "LoginUser";
                paraNames[14] = "Origin";
                paraNames[15] = "ServType";

                jsonResponse1 = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetWorkReallocation, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                if (!jsonResponse1.equals("[]")) {
                    ReallocationResponseDataModel[] enums = gson.fromJson(jsonResponse1, ReallocationResponseDataModel[].class);
                    if (enums != null && enums.length > 0) {
                        reallocationCount = enums.length;
                        reallocationTv.setText(String.valueOf(reallocationCount));

                        ValueAnimator animator = ValueAnimator.ofInt(0, reallocationCount);
                        animator.setDuration(3000);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                reallocationTv.setText(animation.getAnimatedValue().toString());
                            }
                        });
                        animator.start();

                    }
                    progressReallocation.dismiss();
                }
            }catch (Exception e) {  e.printStackTrace();  }
            progressReallocation.dismiss();
        }
    }

    private void getWorkCompletionCount() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String dateToStr = format.format(today);
        String loggedInUserStr =null;

        try {
            // Decrypt EmpCode
            loggedInUserStr = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String param[] = new String[17];

        param[0] = "0";   // test-data (0)
        param[1] = "";
        param[2] = dateToStr;
        param[3] = "";
        param[4] = "0";   //test-data (0)
        param[5] = "0";   //test-data (0)
        param[6] = "0";  //  test-data (1l) zoneid
        param[7] = "0";    // test-data (1-East) zonetext
        param[8] = "-1"; //   test-data (1)
        param[9] = "0";    // test-data (0)
        param[10] = "Web";  // test-data (Web) // from login
        param[11] = loggedInUserStr;

        param[12] = "0";
        param[13] = "0";
        param[14] = "0";
        param[15] = "0";
        param[16] = loggedInUserStr;

//        ("0", NULL, todays date, '',0, 0, 0, 0, -1, "0", "Web", LoginEmpCode,
//
//Newly added parameters SR=0,DMA= 0,Source= 0,CustomerType = 0, LoginEmpCode);

        if (connection.isConnectingToInternet()) {
            WorkCompletion workCompletion = new WorkCompletion();
            workCompletion.execute(param);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class WorkCompletion extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressCompletion = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                String paraNames[] = new String[17];

                paraNames[0] = "SearchFor";
                paraNames[1] = "FromDate";
                paraNames[2] = "ToDate";
                paraNames[3] = "ConsumerNo";
                paraNames[4] = "CompType";
                paraNames[5] = "CompSubType";
                paraNames[6] = "ZoneId";
                paraNames[7] = "ZoneText";
                paraNames[8] = "SubZoneId";
                paraNames[9] = "w_usertag";
                paraNames[10] = "IsAndroid";
                paraNames[11] = "LoggedInUser";
                paraNames[12] = "SR";
                paraNames[13] = "DMA";
                paraNames[14] = "Source";
                paraNames[15] = "CustomerType";
                paraNames[16] = "LoginUser";

                jsonResponse2 = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetWorkCompletionData, params, paraNames);
                //  Log.d("check ", jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                if(!jsonResponse2.equals("[]")) {
                    WorkCompletionResponseModel[] enums = gson.fromJson(jsonResponse2, WorkCompletionResponseModel[].class);

                    if (enums != null && enums.length > 0) {
                        workCompletionCount = enums.length;
                        completionTv.setText(String.valueOf(workCompletionCount));

                        ValueAnimator animator = ValueAnimator.ofInt(0, workCompletionCount);
                        animator.setDuration(2000);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                completionTv.setText(animation.getAnimatedValue().toString());
                            }
                        });
                        animator.start();

                    }

                }
            //    barChart();
                progressCompletion.dismiss();
            } catch (Exception e) { e.printStackTrace(); }
            progressCompletion.dismiss();
        }
    }


    private void getTodayCompletedWork() {
        String params[] = new String[1];

        try {
            // Decrypt EmpCode
            params[0] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (connection.isConnectingToInternet()) {
            TodayWorkCompletion todayWorkCompletion = new TodayWorkCompletion();
            todayWorkCompletion.execute(params);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class TodayWorkCompletion extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressToday = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paramNames[] = new String[1];

                paramNames[0] = "w_EmpCode";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.TodayWorkCompletionData, params, paramNames);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try {
                // todayCompleteWorkModels = new ArrayList<>();

                if(!jsonResponse.equals("[]")) {
                    TodayWorkCompletionResponseModel[] enums = gson.fromJson(jsonResponse, TodayWorkCompletionResponseModel[].class);

                    if (enums != null && enums.length > 0) {
                        totalWork = enums.length;
                        todayCompleteWorkTv.setText(String.valueOf(totalWork));


                    }
                    //barChart();


                    progressToday.dismiss();
                }
                progressToday.dismiss();
                //barChartDemo();
            }catch (Exception e){
                String error = e.toString();
                ErrorClass.errorData(mCon, "TodaysCompletedWorkActivity", "TodayWorkCompletion", error);
                progressToday.dismiss();

            }
        }
    }

/*    private void barChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        //  Float registration = (float) registrationCount;
        Float allocatedCount = (float) workAlocatedCount;
        Float reallocatedCount = (float) reallocationCount;
        Float completionCount = (float) workCompletionCount;
        Float todaysCompletedWork = (float) todaysCompletedWorkCount;


        //  entries.add(new BarEntry(registration, 0));
        entries.add(new BarEntry(allocatedCount, 0));
        entries.add(new BarEntry(reallocatedCount, 1));
        entries.add(new BarEntry(completionCount, 2));
        entries.add(new BarEntry(todaysCompletedWork, 3));

        BarDataSet bardataset = new BarDataSet(entries, " ");

        ArrayList<String> labels = new ArrayList<String>();
        //     labels.add("Complaint Registration");
        labels.add("Work Allocation");
        labels.add("Work Reallocation");
        labels.add("Work Completion");
        labels.add("Today's Work");

        barChart.getAxisLeft().setTextColor(ContextCompat.getColor(mCon, R.color.white)); // left y-axis
        barChart.getAxisRight().setTextColor(ContextCompat.getColor(mCon, R.color.white)); // left y-axis
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.setDrawGridBackground(false);// this is a must

        bardataset.setValueTextColor(Color.WHITE);

        barChart.getDescription().setEnabled(false);

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        // barChart.setDescription("Set Bar Chart Description Here");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(2000);
    }*/

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
                //Log.e("ComplaintDashParams",Arrays.toString(params));
                jsonMasterResponse = invServices.getOtherData(Constants.URL, Constants.NameSpace, Constants.MobileDashboard,username,password, params, paraName);
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

//                    workAlocatedCount = Integer.parseInt(enums[0].getWORK_ALLOCATED());
//                    reallocationCount = Integer.parseInt(enums[0].getWORK_RE_ALLOCATED());
//                    workCompletionCount = Integer.parseInt(enums[0].getWORK_DONE());
//                    totalWork = Integer.parseInt(enums[0].getTOTAL_WORK());

                    workAlocatedCountStr = enums[0].getWORK_ALLOCATED();
                    reallocationCountStr = enums[0].getWORK_RE_ALLOCATED();
                    workCompletionCountStr = enums[0].getWORK_DONE();
                    totalWorkStr = enums[0].getTOTAL_WORK();

                    setDataCount();
                }

            } catch (Exception e) {
                Log.d("check", e.getMessage());
              //  Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                // error = e.toString();
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "Click Login Button", error);
            }
            progress.dismiss();
        }
    }

    private void setDataCount() {
        allocationTv.setText(String.valueOf(workAlocatedCountStr));
        reallocationTv.setText(String.valueOf(reallocationCountStr));
        completionTv.setText(String.valueOf(workCompletionCountStr));
        todayCompleteWorkTv.setText(String.valueOf(totalWorkStr));
        barChartNew();
    }

    private void barChartNew() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Float workAlocatedCount = Float.valueOf(workAlocatedCountStr);
        Float reallocationCount = Float.valueOf(reallocationCountStr);
        Float workCompletionCount = Float.valueOf(workCompletionCountStr);
        Float totalWork = Float.valueOf(totalWorkStr);

        entries.add(new BarEntry(0, workAlocatedCount));
        entries.add(new BarEntry(1, reallocationCount));
        entries.add(new BarEntry(2, workCompletionCount));
        entries.add(new BarEntry(3, totalWork));

        BarDataSet bardataset = new BarDataSet(entries, " ");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add(getResources().getString(R.string.work_allocation));
        labels.add(getResources().getString(R.string.work_reallocation));
        labels.add(getResources().getString(R.string.work_completion));
        labels.add(getResources().getString(R.string.today_s_completed_work));

        BarDataSet set = new BarDataSet(entries, getResources().getString(R.string.work_status));
        set.setValueFormatter(new LargeValueFormatter());

        set.setColors(new int[]{  R.color.red_700,R.color.yellow_700,R.color.green_700, R.color.four_700}, mCon);

        barChart.setPinchZoom(true);
        barChart.setScaleEnabled(true);

        BarData data = new BarData(set);

        barChart.setData(data); // set the data and list of labels into chart

        barChart.getAxisLeft().setTextColor(ContextCompat.getColor(mCon, R.color.white)); // left y-axis
        barChart.getAxisRight().setTextColor(ContextCompat.getColor(mCon, R.color.white)); // left y-axis
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.setDrawGridBackground(false);// this is a must

        set.setValueTextColor(Color.WHITE);

        barChart.getDescription().setEnabled(false);
        barChart.animateY(2000);
    }

    private void barChartDemo(){

        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextSize(0);
        xAxis.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        xAxis.setEnabled(true);

        barChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });
        barChart.getAxisRight().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });

        List<BarEntry> entries = new ArrayList();
        entries.add(new BarEntry(0, workAlocatedCount));
        entries.add(new BarEntry(1, reallocationCount));
        entries.add(new BarEntry(2, workCompletionCount));
        entries.add(new BarEntry(3, totalWork));

        ArrayList name = new ArrayList();
        name.add(getResources().getString(R.string.work_allocation));
        name.add(getResources().getString(R.string.work_reallocation));
        name.add(getResources().getString(R.string.work_completion));
        name.add(getResources().getString(R.string.today_s_completed_work));

        BarDataSet set = new BarDataSet(entries, getResources().getString(R.string.work_status));
        set.setValueFormatter(new LargeValueFormatter());

        set.setColors(new int[]{  R.color.red_700,R.color.yellow_700,R.color.green_700, R.color.four_700}, mCon);

        BarData data = new BarData(set);
        XAxis xxAxis = barChart.getXAxis();

        xxAxis.setDrawGridLines(true);
        xxAxis.setTextSize(8f);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawAxisLine(true);


        barChart.animateXY(1500, 1500);
        barChart.setPinchZoom(true);
        barChart.setScaleEnabled(true);//disbale all zoom

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.5f; // x2 dataset

        data.setBarWidth(barWidth); // set custom bar width

        barChart.setData(data);
        barChart.invalidate();

        barChart.getAxisLeft().setTextColor(ContextCompat.getColor(mCon, R.color.white)); // left y-axis
        barChart.getAxisRight().setTextColor(ContextCompat.getColor(mCon, R.color.white)); // left y-axis
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.setDrawGridBackground(false);// this is a must

        set.setValueTextColor(Color.WHITE);

        barChart.getDescription().setEnabled(false);




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
        ((App) mCon.getApplicationContext()).startActivityTransitionTimer();
    }

}
