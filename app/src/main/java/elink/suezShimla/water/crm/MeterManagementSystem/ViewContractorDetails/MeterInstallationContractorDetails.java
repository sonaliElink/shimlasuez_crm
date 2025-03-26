package elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.MapScreen.Model.LatLongModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRequestTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGVendorDetModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGZoneModel;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.MeterInstallationReplacementEntryShow;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.Other;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.ShowMeterDataModel;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.Adapter.MeterInstallationAdapterDemo;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.Model.MMGGetAllMtrInstallByContModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class MeterInstallationContractorDetails extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rv_consumer_details;
    private MeterInstallationAdapterDemo meterInstallationContractorDetAdapter;
    ImageButton allocatedFilter;
    private BottomSheetDialog sheetDialog;
    private Context mCon;

    private int workId, areaId, zoneId, contractorId, zone_bum_bu_id;
    private String request_type_str="ALL", fromDate="", toDate="", jsonResponse="",currentDate="",wrokStr="";
    private EditText fromDateInputEditText, toDateInputEditText;
    private Calendar fromDateCalendar, toDateCalendar;
    private ArrayAdapter zoneAdapter, contractorAdapter, reqAdapter;
    List<String> zoneDataList = new ArrayList<>();
    List<String> reqDataList = new ArrayList<>();
    private Invoke invServices;
    private Gson gson;
    private ConnectionDetector connection;
    private MaterialDialog progress;
    private SearchView searchView;
    private List<MMGGetAllMtrInstallByContModel> mmgGetAllMtrInstallByContModelArrayList = new ArrayList<>();
    List<ShowMeterDataModel> showMtrList=new ArrayList<>();

    ShowMeterDataModel showMeterDataModel;

    private AppCompatSpinner requestTypeSpinner, areaTypeSpinner, workTypeSpinner, zoneSpinner, fixerSpinner;
    private AppCompatImageView closeImageView;
    private MaterialButton showButton, clearButton;
    RealmOperations realmOperations;
    private ArrayList<String> contractorName = new ArrayList<>();
    private String requestIdValueStr="", req_id_value="",  mmgFixer="", contractor_id,fromDateStr="",toDateStr="",
            processCode="", fromPage="", tag="";
    MMGZoneModel mmgZoneModel;
    MMGVendorDetModel mmgVendorDetModel;
    MMGRequestTypeModel mmgRequestTypeModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    MeterinstallShowAsyncTask meterinstallShowAsyncTask;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_meter_installation_contractor_details);
        init();

    }

    private void init() {
        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(this);
        invServices = new Invoke();
        rv_consumer_details = findViewById(R.id.rv_consumer_details);
        allocatedFilter = findViewById(R.id.allocatedFilter);
        realmOperations = new RealmOperations(mCon);
        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

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

        allocatedFilter.setOnClickListener(this);

       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy ");
        currentDate = simpleDateFormat.format(new Date());
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        //contactorId = UtilitySharedPreferences.getPrefs(this, AppConstant.MMGFIXER);
      //  contactorEmpId = UtilitySharedPreferences.getPrefs(this, Constants.CONTACTOREMP);
      //  showInstalledMeterList();
        fromDateStr= getIntent().getStringExtra("fromDate");
        toDateStr= getIntent().getStringExtra("toDate");
        fromPage = getIntent().getStringExtra("fromPage");


        if (fromPage != null) {
            if (fromPage.equalsIgnoreCase("workreallocate")) {
                processCode = "607";
                setTitle("Work ReAllocated");
                tag = "WRS";
            } else if (fromPage.equalsIgnoreCase("savedDetails")) {
                processCode = "603";
                setTitle("Saved Details");
                tag = "S";
            }
        }
        meterInstallationContractorDetAdapter = new MeterInstallationAdapterDemo(this, fromDateStr, toDateStr, tag);
        getMeterInstallationList();

        try {


            searchView = findViewById(R.id.searchView);
            searchView.clearFocus();
/*
    searchView.setMaxWidth(Integer.MAX_VALUE);
    searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
*/

        }catch (Exception e){
            e.printStackTrace();
        }

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



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.allocatedFilter:
                openSheetDialog();

                break;
            case R.id.showButton:
                validate();
                break;

            case R.id.clearButton:
                clearFields();

                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

        getMeterInstallationList();
        swipeRefreshLayout.setRefreshing(false);

    }






    private void openSheetDialog() {
        sheetDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_fixer_data_filter, null));

//        fromDate = sheetView.findViewById(R.id.fromDate);
//        toDate = sheetView.findViewById(R.id.toDate);

        fromDateInputEditText = sheetView.findViewById(R.id.fromDateInputEditText);
        toDateInputEditText = sheetView.findViewById(R.id.toDateInputEditText);
        requestTypeSpinner = sheetView.findViewById(R.id.requestTypeSpinner);
        areaTypeSpinner = sheetView.findViewById(R.id.areaTypeSpinner);
        workTypeSpinner = sheetView.findViewById(R.id.workTypeSpinner);
        zoneSpinner = sheetView.findViewById(R.id.zoneSpinner);
        fixerSpinner = sheetView.findViewById(R.id.fixerSpinner);
        closeImageView = sheetView.findViewById(R.id.closeImageView);
        showButton = sheetView.findViewById(R.id.showButton);
        clearButton = sheetView.findViewById(R.id.clearButton);
        sheetDialog.setContentView(sheetView);
        sheetDialog.show();
        sheetDialog.setCanceledOnTouchOutside(false);

        workTypeSpinner.setOnItemSelectedListener(this);
        areaTypeSpinner.setOnItemSelectedListener(this);
        requestTypeSpinner.setOnItemSelectedListener(this);
        zoneSpinner.setOnItemSelectedListener(this);
        fixerSpinner.setOnItemSelectedListener(this);

        final DatePickerDialog.OnDateSetListener fromDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fromDateCalendar.set(Calendar.YEAR, year);
                fromDateCalendar.set(Calendar.MONTH, month);
                fromDateCalendar.set(Calendar.DATE, dayOfMonth);
                updateFromDate();
            }
        };

        final DatePickerDialog.OnDateSetListener toDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                toDateCalendar.set(Calendar.YEAR, year);
                toDateCalendar.set(Calendar.MONTH, month);
                toDateCalendar.set(Calendar.DATE, dayOfMonth);
                updateToDate();
            }
        };

        fromDateInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromDate, fromDateCalendar.get(Calendar.YEAR),
                        fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DATE));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        toDateInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toDate, toDateCalendar.get(Calendar.YEAR),
                        toDateCalendar.get(Calendar.MONTH),
                        toDateCalendar.get(Calendar.DATE));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sheetDialog.dismiss();
               // Toast.makeText(mCon, "Plz enter details to view Meter Details", Toast.LENGTH_SHORT).show();
            }
        });

        contractorName = realmOperations.fetchVendorDetails();

        ArrayList<String> contractorList = new ArrayList<>();
        contractorList.add("--Select--");
        contractorList.addAll(contractorName);

        contractorAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, contractorList);
        contractorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fixerSpinner.setAdapter(contractorAdapter);

       // String mmgFixer = PreferenceUtil.getMMGFixer();
        mmgFixer = UtilitySharedPreferences.getPrefs(mCon, AppConstant.MMGFIXER);
//        mmgFixer = aes.decrypt( mmgFixer.getBytes(), secretKey, IV);

        if(mmgFixer.equalsIgnoreCase("-98")){
            fixerSpinner.setEnabled(true);
        }else {
            fixerSpinner.setEnabled(false);
            String empName;
            mmgVendorDetModel = realmOperations.fetchContEmpName(mmgFixer);
            if(mmgVendorDetModel!=null) {
                 empName = mmgVendorDetModel.getNAME();
                ArrayList<String> contractorList11 = new ArrayList<>();
                contractorList11.add(empName);

                contractorAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, contractorList11);
                contractorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fixerSpinner.setAdapter(contractorAdapter);
            }else {

            }

        }

        clearButton.setOnClickListener(this);
        showButton.setOnClickListener(this);

        zoneDataList = realmOperations.fetchIssueToMeterZone() ;
        ArrayList<String> zone = new ArrayList<>();
        zone.add("All");
        zone.addAll(zoneDataList);

        zoneAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, zone);
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneSpinner.setAdapter(zoneAdapter);

        requestTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String requestType = requestTypeSpinner.getSelectedItem().toString();
                MMGRequestTypeModel MMGRequestTypeModel = realmOperations.fetchIssueToMeterFixrRequestTypeByName(requestType);
                try {
                    if (requestType.equals("All")) {
                        requestIdValueStr ="ALL" ;
                    }   else {
                        if(requestType.equalsIgnoreCase("New Connection")){
                            requestIdValueStr="A";
                        }else if(requestType.equals("Complaint")){
                            requestIdValueStr="C";
                        }
                        else if(requestType.equals("Exisiting Application")){
                            requestIdValueStr="EA";
                        }else if(requestType.equals("Temp.Connection")){
                            requestIdValueStr="TR";
                        }
                    }

                    Log.e("requestIdValueStr", String.valueOf(requestIdValueStr));
                }catch (Exception e){
                    Log.e("spinnerValue",e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void updateFromDate() {
        String dateFormat = "dd-MMM-yyyy"; //set date format
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        fromDateInputEditText.setText(sdf.format(fromDateCalendar.getTime()));
    }

    private void updateToDate() {
        String dateFormat = "dd-MMM-yyyy"; //set date format
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        toDateInputEditText.setText(sdf.format(toDateCalendar.getTime()));
    }

    private void validate() {
        fromDate = fromDateInputEditText.getText().toString();
        toDate = toDateInputEditText.getText().toString();

        startNextActivity();
    }

    private void clearFields() {
        try {
            workTypeSpinner.setSelection(0);
            areaTypeSpinner.setSelection(0);
            requestTypeSpinner.setSelection(0);
            zoneSpinner.setSelection(0);
            fixerSpinner.setSelection(0);
            fromDateInputEditText.setText("");
            toDateInputEditText.setText("");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void filter(String input) {
        List<ShowMeterDataModel> list = new ArrayList<>();

        for (ShowMeterDataModel item : showMtrList) {
            if (item.getMFX_SERVICENO().toLowerCase().contains(input.toLowerCase()) || item.getCONSUMERNAME().toLowerCase().contains(input.toLowerCase()) || item.getMFX_REFNO().toLowerCase().contains(input.toLowerCase())) {
                list.add(item);
            }
        }
        meterInstallationContractorDetAdapter.filterList(list);
    }

    private void startNextActivity() {
      if(fromDate.equalsIgnoreCase("")){
                MessageWindow.msgWindow(mCon, getResources().getString(R.string.please_select_fromdate));
                return;
            } else if(toDate.equalsIgnoreCase("")){
                MessageWindow.msgWindow(mCon, getResources().getString(R.string.please_select_todate));
            }

        else {
          //showFilteredList();
          getMeterInstallationList();

      }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.workTypeSpinner: {
                workId = workTypeSpinner.getSelectedItemPosition();
                wrokStr= workTypeSpinner.getSelectedItem().toString();
            }
            break;
            case R.id.areaTypeSpinner: {
                areaId = areaTypeSpinner.getSelectedItemPosition();
            }
            break;
            case R.id.requestTypeSpinner: {
                request_type_str = requestTypeSpinner.getSelectedItem().toString();
                String select = requestTypeSpinner.getSelectedItem().toString();
                if(!select.equalsIgnoreCase("All")){
                    mmgRequestTypeModel = realmOperations.fetchReqTypeById(request_type_str);
                    String name = mmgRequestTypeModel.getSelectVal();
                    req_id_value = mmgRequestTypeModel.getAllVal();
                }
            }
            break;
            case R.id.zoneSpinner: {
                zoneId = zoneSpinner.getSelectedItemPosition();
                String select = zoneSpinner.getSelectedItem().toString();
                if(!select.equalsIgnoreCase("All")){
                    mmgZoneModel = realmOperations.fetchZoneId(zoneId);
                    zone_bum_bu_id = mmgZoneModel.getBUM_BU_ID();
                }
            }
            break;
            case R.id.fixerSpinner: {
                contractorId = fixerSpinner.getSelectedItemPosition();
                String select = fixerSpinner.getSelectedItem().toString();
                if (!select.equalsIgnoreCase("All")) {
                    mmgVendorDetModel = realmOperations.fetchVendorEmpByName(select);
                    contractor_id = mmgVendorDetModel.getEM_EMP_CODE();
                }
            }
            break;

            default:
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getMeterInstallationList(){

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[8];
        String bu = PreferenceUtil.getFirstZoneAvailable();

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String dateToStr = format.format(today);

        params[0] = "0";
        params[1] =  empcode; //"1310";
        params[2] = "";
        params[3] = fromDateStr;
        params[4] = toDateStr;
        params[5] = "0";
        params[6] = "-1";
        params[7] = processCode;


        if (connection.isConnectingToInternet()) {
            meterinstallShowAsyncTask  = new MeterinstallShowAsyncTask();
            meterinstallShowAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

        //Log.e("workReallocationParams", Arrays.toString(params));
    }

/*    private void getFilteredMeterInstallation(){
        String[] params = new String[9];
        params[0] = requestIdValueStr;
        params[1] = PreferenceUtil.getEmployeeCode();
        params[2] = "0";
        params[3] = cons_numStr;
        params[4] = fromDateStr;
        params[5] = toDateStr;
        params[6] = zoneIdStr;
        params[7] = String.valueOf(subZone_id);
        params[8] = "N";

        if (connection.isConnectingToInternet()) {
            meterinstallShowAsyncTask  = new MeterinstallShowAsyncTask();
            meterinstallShowAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }*/


    @SuppressLint("StaticFieldLeak")
    private class MeterinstallShowAsyncTask extends AsyncTask<String, Void, Void> {
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
                String paraNames[] = new String[8];
                paraNames[0] = "SourceType";
                paraNames[1] = "EmpCode";
                paraNames[2] = "ConsumerNo";
                paraNames[3] = "FromIssueDate";
                paraNames[4] = "ToIssueDate";
                paraNames[5] = "BU";
                paraNames[6] = "PC";
                paraNames[7] = "ProcessCode";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_MeterInstallationReplacementEntryShow_1, params, paraNames);
                Log.e("reallocationResp",jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MeterInstallationReplacementEntryShow meterInstallationModel = gson.fromJson(jsonResponse, MeterInstallationReplacementEntryShow.class);
                List<ShowMeterDataModel> downloadShowMeterData = meterInstallationModel.getData();
                List<Other> downloadOther = meterInstallationModel.getDownloadOther();
                if(downloadShowMeterData.size() == 0){
                    MessageWindow.errorWindow(mCon, "No data found");

                 //   errorLinear.setVisibility(View.VISIBLE);
                    allocatedFilter.setEnabled(false);
                    allocatedFilter.setAlpha((float) 0.3);

                }else {
               /* int allocatedWorkList = downloadShowMeterData.size();
                UtilitySharedPreferences.setPrefs(mCon, Constants.ALLOCATED_WORK_LIST, String.valueOf(allocatedWorkList));*//**/
                    showMtrList.clear();
                    allocatedFilter.setEnabled(true);

                    for (ShowMeterDataModel meterDetails : meterInstallationModel.getData()) {
                        if (downloadShowMeterData.size() > 0) {

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
                                    meterDetails.getMCT_CONNTYPE_NAME(), requestIdValueStr, meterDetails.getSTATUS(),meterDetails.getSUBMITION_DATE(),meterDetails.getALLOCATIONDATE()
                                    ,meterDetails.getMRT_PROCESS_DATE(),meterDetails.getMI_ISCOMMISSIONED(), meterDetails.getMI_ACTION(), meterDetails.getMICPATH());

                            showMtrList.add(showMeterDataModel);
                            Log.d("showMtrList", ""+showMtrList);
                        }

                        realmOperations.deleteLatLongTable();
                        if (meterDetails.getSRM_LATITUDE() != null && meterDetails.getSRM_LONGITUDE() != null) {
                            LatLongModel LatLongModelExist = realmOperations.getLatLongExist();
                            if (LatLongModelExist == null) {
                                LatLongModel LatLongModel = new LatLongModel(meterDetails.getSRM_LATITUDE(), meterDetails.getSRM_LONGITUDE(), meterDetails.getCONSUMERNAME());
                                realmOperations.addLatLong(LatLongModel);
                            } else {
                                if (!LatLongModelExist.getTitle().equals(meterDetails.getCONSUMERNAME())) {
                                    LatLongModel LatLongModel = new LatLongModel(meterDetails.getSRM_LATITUDE(), meterDetails.getSRM_LONGITUDE(), meterDetails.getCONSUMERNAME());
                                    realmOperations.addLatLong(LatLongModel);
                                }
                            }
                        }
                    }

                    meterInstallationContractorDetAdapter.addList(showMtrList);
                    rv_consumer_details.setAdapter(meterInstallationContractorDetAdapter);
                    meterInstallationContractorDetAdapter.notifyDataSetChanged();

                }
                //  realmOperations.deleteLatLongTable();
            } catch (Exception e) {
                Log.e("MIActExcpn",e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "MeterInstallationActivity", "Meter Installation Button on home page", error);
            }
            progress.dismiss();
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

}
