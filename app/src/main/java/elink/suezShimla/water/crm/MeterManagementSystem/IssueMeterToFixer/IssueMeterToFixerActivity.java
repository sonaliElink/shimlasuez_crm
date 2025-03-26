package elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRequestTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Adapter.IssueMeterToFixerAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Model.IssueMeterFixerModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class IssueMeterToFixerActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Context mCon;
    private SwipeRefreshLayout swipeRefreshLayout;

    //BottomSheet Dialog
    private BottomSheetDialog sheetDialog;
    private TextInputLayout dateTimeTextInputLayout, fromDateInputLayout, toDateInputLayout;
    private TextInputEditText dateTimeEditText, fromDateEditText, toDateEditText,consumerNoEditText;
    private AppCompatSpinner requestTypeSpinner, sourceTypeSpinner, observationSpinner,zoneSpinner,subzoneSpinner;
    private AppCompatImageView closeImageView;
    RealmOperations realmOperations;
    private String issueDateStr="", fromDateStr="", toDateStr="", requestTypeStr="",zoneIdValueStr="",subzoneIdValueStr="",sourceTypeStr="",sourceTypevalueStr=""
            ,obserStr="",obserValStr="",consumerStr="";
    int zoneId=0,subZoneId=0,requestId=0;
    String zoneName="",subZoneName="",requstType="";
    private ArrayAdapter zoneAdapter, subZoneAdapter,requestTypeAdapter;
    String consumerNo="",consumerType="",reqNo="",serviceType="",phase="",oldMtrNo="",oldMtrTyp="",oldMtrReading="",newMtrNo="",newMtrTyp="",newMtrReading="",mtrIndication="",
            installReplaceDt="",mtrInsttledReplcd="",mtrIrReasn="",fixrName="",contactNo="",nameAdd="",observ="",reqSubType="",risingMains="",fwdmmgDt="",rejRes="",zone="",subZone="";
   String  sourceTypeVal="",sourceTypeValStr="",buStr="",pcStr="",OCRM_ID="",SOURCETYPE="";
    //Recycler View
    private RecyclerView issueMeterFixerRecyclerView;
    private List<IssueMeterFixerModel> issueMeterFixerModelList = new ArrayList<>();
    private IssueMeterToFixerAdapter issueMeterToFixerAdapter;
    private MMGZoneModel MMGZoneModel;

    private Calendar fromDateCalendar, toDateCalendar, issueCalendar;
    private MaterialDialog progress;
    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    String requestTypeValueStr="",requestTypeValueIdStr="";

    List<String> ZoneDataList=new ArrayList<>();
    List<String> SubZoneDataList=new ArrayList<>();
    List<String> RequestTypeList=new ArrayList<>();

    private List<ZoneModel> zoneModelList;
    private ArrayList<String> subzoneList = new ArrayList<>();

    private ZoneModel zoneModel;

    private MMGSubZoneModel subZoneModel;
    private MMGRequestTypeModel mmgRequestTypeModel;
    private String subZone_id = "";

    private List<MMGRequestTypeModel> mmgRequestTypeModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_meter_to_fixer);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        realmOperations = new RealmOperations(mCon);



        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        sheetDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));


        issueCalendar = Calendar.getInstance();
        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();



        issueMeterFixerRecyclerView = findViewById(R.id.issueMeterFixerRecyclerView);

        issueMeterToFixerAdapter = new IssueMeterToFixerAdapter(mCon);

        issueMeterFixerRecyclerView.setHasFixedSize(true);
        issueMeterFixerRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));
        issueMeterBottomSheetDialog();

        //initially issue date is system date
        String date_issue = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        dateTimeEditText.setText(date_issue);


       // ZoneDataList= realmOperations.fetchIssueToMeterZone() ;
        //SubZoneDataList = realmOperations.fetchIssueToMeterSubZone();
       // RequestTypeList= realmOperations.fetchIssueToMeterFixrRequestType();

        Log.e("ZoneList",""+ ZoneDataList.toString());
        Log.e("SubzoneList",""+ SubZoneDataList.toString());
        Log.e("RequestType",""+ RequestTypeList.toString());

       /* zoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, ZoneDataList);
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneSpinner.setAdapter(zoneAdapter);
*/

       /* subZoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, SubZoneDataList);
        subZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subzoneSpinner.setAdapter(subZoneAdapter);

        requestTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, RequestTypeList);
        requestTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestTypeSpinner.setAdapter(requestTypeAdapter);
*/
        setDate();

    }


    private void setDate() {
        try {
            Date dateInstance = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(dateInstance);
            cal.add(Calendar.DATE, -1);
            Date dateBefore30Days = cal.getTime();

            @SuppressLint("SimpleDateFormat") SimpleDateFormat fromDf = new SimpleDateFormat("dd-MMM-yyyy");
            String FromDateFormat = fromDf.format(dateBefore30Days);
            fromDateEditText.setText(FromDateFormat);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat toDf = new SimpleDateFormat("dd-MMM-yyyy");
            String toDateFormat = toDf.format(Calendar.getInstance().getTime());
            toDateEditText.setText(toDateFormat);
        } catch (Exception e) {
            Log.d("check", e.getMessage());
        }
    }


    private void issueMeterBottomSheetDialog() {

        @SuppressLint("InflateParams")
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_issue_meter_fixer, null);
        sheetDialog.setContentView(sheetView);
       /// BottomSheetBehavior.from(Objects.requireNonNull(sheetView)).setState(BottomSheetBehavior.STATE_EXPANDED);
        sheetDialog.show();
        sheetDialog.setCancelable(false);

        MaterialButton showButton = sheetView.findViewById(R.id.showButton);
        showButton.setOnClickListener(this);

        dateTimeTextInputLayout = sheetView.findViewById(R.id.dateTimeTextInputLayout);
        fromDateInputLayout = sheetView.findViewById(R.id.fromDateInputLayout);
        toDateInputLayout = sheetView.findViewById(R.id.toDateInputLayout);

        dateTimeEditText = sheetView.findViewById(R.id.dateTimeEditText);
        fromDateEditText = sheetView.findViewById(R.id.fromDateEditText);
        toDateEditText = sheetView.findViewById(R.id.toDateEditText);
        consumerNoEditText = sheetView.findViewById(R.id.consumerNoEditText);

        requestTypeSpinner = sheetView.findViewById(R.id.requestTypeSpinner);
        requestTypeSpinner.setOnItemSelectedListener(this);
        sourceTypeSpinner = sheetView.findViewById(R.id.sourceTypeSpinner);
        observationSpinner = sheetView.findViewById(R.id.observationSpinner);
        zoneSpinner = sheetView.findViewById(R.id.zoneSpinner);
        zoneSpinner.setOnItemSelectedListener(this);

        subzoneSpinner = sheetView.findViewById(R.id.subZoneSpinner);

        closeImageView = sheetView.findViewById(R.id.employeeCloseImageView);


        setZoneDropDown();
        requestType();


       /* requestTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sourceTypeSpinner.setVisibility(View.GONE);
                    observationSpinner.setVisibility(View.GONE);
                } else if (position == 1) {
                    sourceTypeSpinner.setVisibility(View.VISIBLE);
                    observationSpinner.setVisibility(View.GONE);
                } else if (position == 2) {
                    sourceTypeSpinner.setVisibility(View.GONE);
                    observationSpinner.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    sourceTypeSpinner.setVisibility(View.GONE);
                    observationSpinner.setVisibility(View.GONE);
                } else if (position == 4) {
                    sourceTypeSpinner.setVisibility(View.GONE);
                    observationSpinner.setVisibility(View.GONE);
                }


                String requestType = requestTypeSpinner.getSelectedItem().toString();
                MMGRequestTypeModel MMGRequestTypeModel = realmOperations.fetchIssueToMeterFixrRequestTypeByName(requestType);
                try {
                    if (requestType.equals("All")) {
                        requestIdValueStr ="A" ;
                    }   else {
                        requestIdValueStr = String.valueOf(MMGRequestTypeModel.getAllVal());
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
*/




  /*  subzoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String subZone = subzoneSpinner.getSelectedItem().toString();
        MMGSubZoneModel MMGSubZoneModel = realmOperations.fetchIssueToMeterSubZoneByName(subZone);
        try {
            if (zoneName.equals("ALL")) {
                subZoneId = -1;
            }else{
                subZoneId = MMGSubZoneModel.getPCM_PC_ID();
            }
            subzoneIdValueStr = String.valueOf(subZoneId);
            Log.e("subzoneIdValueStr",subzoneIdValueStr);
        }catch (Exception e){
            Log.e("spinnerValue",e.toString());
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});*/

        final DatePickerDialog.OnDateSetListener fromCalendarDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                fromDateCalendar.set(Calendar.YEAR, year);
                fromDateCalendar.set(Calendar.MONTH, monthOfYear);
                fromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFromDateCalendar();
            }
        };

        final DatePickerDialog.OnDateSetListener issueCalendarDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                issueCalendar.set(Calendar.YEAR, year);
                issueCalendar.set(Calendar.MONTH, monthOfYear);
                issueCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateCalendar();
            }
        };

        final DatePickerDialog.OnDateSetListener toCalendarDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                toDateCalendar.set(Calendar.YEAR, year);
                toDateCalendar.set(Calendar.MONTH, monthOfYear);
                toDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateToDateCalendar();
            }
        };

        dateTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, issueCalendarDate, issueCalendar
                        .get(Calendar.YEAR), issueCalendar.get(Calendar.MONTH),
                        issueCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        fromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromCalendarDate, fromDateCalendar
                        .get(Calendar.YEAR), fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DAY_OF_MONTH));
                //datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        toDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toCalendarDate, toDateCalendar
                        .get(Calendar.YEAR), toDateCalendar.get(Calendar.MONTH),
                        toDateCalendar.get(Calendar.DAY_OF_MONTH));
                //datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

      /*  showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sourceTypeSpinner.getVisibility()==View.VISIBLE) {

                    sourceTypeVal=sourceTypeSpinner.getSelectedItem().toString();
                    if(sourceTypeVal.equals("All")){
                        sourceTypeValStr="A";
                    }else if(sourceTypeVal.equals("Call Center")){
                        sourceTypeValStr="C";
                    }else if(sourceTypeVal.equals("Camp")){
                        sourceTypeValStr="M";
                    }else{
                        sourceTypeValStr="NA";
                    }

                }

                if(observationSpinner.getVisibility()==View.VISIBLE) {

                    obserStr = observationSpinner.getSelectedItem().toString();
                    if (obserStr.equals("All")) {
                        obserValStr = "A";
                    } else {
                        obserValStr = "NA";
                    }
                }


                issueDateStr = dateTimeEditText.getText().toString().trim();
                fromDateStr = fromDateEditText.getText().toString().trim();
                toDateStr = toDateEditText.getText().toString().trim();
                consumerStr= consumerNoEditText.getText().toString();
                requestTypeStr = requestTypeSpinner.getSelectedItem().toString().trim();
                //sourceTypeStr=sourceTypeSpinner.getSelectedItem().toString();
                //obserValStr=observationSpinner.getSelectedItem().toString();

                validate();
            }
        });*/

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(mCon, "Plz enter details to proceed", Toast.LENGTH_SHORT).show();
            }
        });

        sheetDialog.setCanceledOnTouchOutside(false);
        sheetDialog.setCancelable(false);
        sheetDialog.show();
    }

    private void setZoneDropDown() {

        String zoneIdLists = PreferenceUtil.getZone();
        List<String> zoneIdList = Arrays.asList(zoneIdLists.split(","));
        // Log.d("check", String.valueOf(zoneIdList));
        ArrayList<String> zoneName = new ArrayList<>();

        for (int i = 0; i < zoneIdList.size(); i++) {
            int id = Integer.parseInt(zoneIdList.get(i));
            zoneModelList = realmOperations.fetchZoneById(id);


            // Log.d("check List", String.valueOf(zoneModelList));

            for (ZoneModel zoneModel : zoneModelList) {

                zoneName.add(zoneModel.getBU_NAME());
                //Log.d("check", String.valueOf(zoneName));

            }
            ArrayList<String>  zoneList = new ArrayList<>();
            zoneList.add("All");
            zoneList.addAll(zoneName);
            zoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, zoneList);
            zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            zoneSpinner.setAdapter(zoneAdapter);

            zoneSpinner.setOnItemSelectedListener(this);
        }
    }




    private void requestType(){
        mmgRequestTypeModelList = realmOperations.fetchReuqestTypeList();

        ArrayList<String> requestType = new ArrayList<>();

        for (MMGRequestTypeModel mmgRequestTypeModel : mmgRequestTypeModelList) {
            requestType.add(mmgRequestTypeModel.getSelectVal());
        }
        ArrayList<String> requestTypeList = new ArrayList<>();
        requestTypeList.add("Select");
        requestTypeList.addAll(requestType);
        requestTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, requestTypeList);
        requestTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestTypeSpinner.setAdapter(requestTypeAdapter);
        requestTypeSpinner.setOnItemSelectedListener(this);
    }

    private void dateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateTimeEditText.setText(sdf.format(issueCalendar.getTime()));
    }

    // From Date Picker
    private void updateFromDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fromDateEditText.setText(sdf.format(fromDateCalendar.getTime()));
    }




    // To Date Picker
    private void updateToDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        toDateEditText.setText(sdf.format(toDateCalendar.getTime()));
    }

    private void validate() {

        boolean isvalidDate = false, isvalidFromDate = false, isvalidToDate = false, isvalidRequestType = false;

        if (TextUtils.isEmpty(issueDateStr)) {
            dateTimeTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isvalidDate = true;
            dateTimeTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(fromDateStr)) {
            fromDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isvalidFromDate = true;
            fromDateInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(toDateStr)) {
            toDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isvalidToDate = true;
            toDateInputLayout.setError(null);
        }

        if (requestTypeStr.equalsIgnoreCase("Request Type *")) {
            TextView view = (TextView) requestTypeSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isvalidRequestType = true;
            TextView view = (TextView) requestTypeSpinner.getSelectedView();
            view.setError(null);
        }

        if (isvalidDate && isvalidFromDate && isvalidRequestType && isvalidToDate) {
            sheetDialog.cancel();
            getIssuetoMeterCardviewData();
            //getMeterData();
        }
    }

   /* private void getMeterData() {
        for (int i = 0; i < 5; i++) {
            IssueMeterFixerModel issueMeterFixerModel = new IssueMeterFixerModel("Mr. Pranay Das\nMehra compound, Sakinaka, Andheri(East), Mumbai :- 400059",
                    "8787878787",
                    "101093226",
                    "19/03/1/89");
            issueMeterFixerModelList.add(issueMeterFixerModel);
        }
        issueMeterToFixerAdapter.addList(issueMeterFixerModelList);
        issueMeterFixerRecyclerView.setAdapter(issueMeterToFixerAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }*/

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(false);

        // getMeterData();
    }

   public void getIssuetoMeterCardviewData(){
       String params[] = new String[15];


       params[0] = PreferenceUtil.getRights();
       params[1] = fromDateStr;
       params[2] = toDateStr ;
       params[3] = "" ;
       params[4] = sourceTypevalueStr ;
       params[5] = "C";
       params[6] = zoneIdValueStr ;
       params[7] = subZone_id;
       params[8] =  "0" ;
       params[9] =  "0";
       params[10] = "0" ;
       params[11] = "1" ;
       params[12] = consumerStr;
       params[13] = "629,644" ;
       params[14] = "0" ;





       if (connection.isConnectingToInternet()) {
           IssueToMeterFixerAsyncTask  issueToMeterFixerAsyncTask = new IssueToMeterFixerAsyncTask();
           issueToMeterFixerAsyncTask.execute(params);
       } else {
           Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
       }
   }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()){
                case R.id.zoneSpinner: {
                    zoneName = zoneSpinner.getSelectedItem().toString();
                    if (zoneName.equalsIgnoreCase("All")) {

                        ArrayList<String> subAll = new ArrayList<>();
                        subAll.add("ALL");

                        subZoneAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, subAll);
                        subZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subzoneSpinner.setAdapter(subZoneAdapter);

                        zoneId = 0;
                        zoneIdValueStr = "0";
                    } else {
                        zoneModel = realmOperations.fetchZoneByName(zoneName);
                        zoneId = zoneModel.getBUM_BU_ID();
                        zoneIdValueStr = String.valueOf(zoneId);


                        subzoneList = realmOperations.fetchSubZoneList(Integer.parseInt(zoneIdValueStr));

                        ArrayList<String> zone_sub_zone_list = new ArrayList<>();
                        zone_sub_zone_list.add("ALL");
                        zone_sub_zone_list.addAll(subzoneList);

                        subZoneAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, zone_sub_zone_list);
                        subZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subzoneSpinner.setAdapter(subZoneAdapter);
                    }
                }
                break;
                case R.id.requestTypeSpinner: {
                    String requestTypeValue = requestTypeSpinner.getSelectedItem().toString();
                    if (requestTypeValue.equalsIgnoreCase("Select")) {
                        requestTypeValueIdStr ="0";
                    } else {
                        mmgRequestTypeModel = realmOperations.fetchRequestTypeId(requestTypeValue);
                        requestTypeValueIdStr = mmgRequestTypeModel.getAllVal();
                        UtilitySharedPreferences.clearPrefKey(mCon,AppConstant.REQUESTTYPEID);
                        UtilitySharedPreferences.setPrefs(mCon,AppConstant.REQUESTTYPEID,requestTypeValueIdStr);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showButton:
                if(checkValidation()){
                    sheetDialog.cancel();
                    getIssuetoMeterCardviewData();
                }
                break;

            default:
                break;
        }
    }

    private boolean checkValidation() {

        requestTypeValueStr = requestTypeSpinner.getSelectedItem().toString();
        fromDateStr = fromDateEditText.getText().toString().trim();
        toDateStr = toDateEditText.getText().toString().trim();

        if (requestTypeValueStr.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, R.string.please_select_request_type, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (fromDateStr.equalsIgnoreCase("")) {
            fromDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(mCon, R.string.please_select_fromdate, Toast.LENGTH_SHORT).show();

            return false;
        }else {
            fromDateInputLayout.setError(null);

        }
        if (toDateStr.equalsIgnoreCase("")) {
            toDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(mCon, R.string.please_select_todate,Toast.LENGTH_SHORT).show();

            return false;
        } else {
            toDateInputLayout.setError(null);

        }

        return true;

    }


    @SuppressLint("StaticFieldLeak")
    private class IssueToMeterFixerAsyncTask extends AsyncTask<String, Void, Void> {

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
                String paraNames[] = new String[15];

                paraNames[0] = "Rights";
                paraNames[1] = "FromDate";
                paraNames[2] = "ToDate";
                paraNames[3] = "ComplaintType";
                paraNames[4] = "SourceType";
                paraNames[5] = "M_ServiceType";
                paraNames[6] = "M_BU";
                paraNames[7] = "M_PC";
                paraNames[8] = "M_MRC";
                paraNames[9] =  "M_Lot";
                paraNames[10] = "M_Purpose";
                paraNames[11] = "M_MeterIndication";
                paraNames[12] = "M_MeterFixer";
                paraNames[13] = "M_ProcessCode";
                paraNames[14] = "W_Phase";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_IssueMeterToFixerShow, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                issueMeterFixerModelList=new ArrayList<>();
                IssueMeterFixerModel[] enums = gson.fromJson(jsonResponse, IssueMeterFixerModel[].class);
                if (enums != null && enums.length > 0) {

                    for (IssueMeterFixerModel model : enums) {
                        zone=model.getBU();
                        subZone=model.getPCNAME();
                        contactNo=model.getSRM_S_MOBILE_NO();
                        consumerNo=model.getSERVICENO();
                        nameAdd=model.getNAME_ADDRESS();
                        reqNo=model.getREF_NO();

                        oldMtrNo=model.getOLDMETERNO();
                        reqSubType=model.getSUBTYPE();
                        fwdmmgDt=model.getMRT_REQUESTDATE();
                        rejRes=model.getREJ_RES();
                        observ=model.getOBS();
                        buStr=model.getBUM_BU_ID();
                        pcStr=model.getPC();
                        OCRM_ID=model.getOCRM_ID();
                        SOURCETYPE=model.getSOURCETYPE();

                        IssueMeterFixerModel data = new IssueMeterFixerModel(zone,subZone,nameAdd,contactNo,consumerNo,reqNo,oldMtrNo,reqSubType,fwdmmgDt,rejRes,observ,SOURCETYPE,buStr,pcStr,OCRM_ID);
                        issueMeterFixerModelList.add(data);

                    }
                    issueMeterToFixerAdapter.addList(issueMeterFixerModelList);
                    issueMeterFixerRecyclerView.setAdapter(issueMeterToFixerAdapter);
                    issueMeterToFixerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mCon, "No Data Available", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("IssuemeterToFixerExpn",e.toString());

                String error = e.toString();
                ErrorClass.errorData(mCon, "IssueMeterToFixerActivity", "show button to get Cardview Data", error);
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
