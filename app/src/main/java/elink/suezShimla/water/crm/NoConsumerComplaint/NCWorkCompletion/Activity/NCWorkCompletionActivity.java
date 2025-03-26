package elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCMapScreen.NCMapActivity;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Adapter.NCWorkCompletionAdapter;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Model.NCWorkCompletionModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Model.NCWorkCompletionResponseModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class NCWorkCompletionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private Context mCon;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView workCompletionRecyclerView;
    private List<NCWorkCompletionModel> workCompletionModelList;

    private NCWorkCompletionAdapter ncworkCompletionAdapter;
    private BottomSheetDialog sheetBehavior;
    private MaterialDialog progress;
    private LinearLayout errorLinear;
    private RadioGroup radioGroup;
    private RadioButton registerComplaintRadio, rejectedComplaints;

    private Calendar fromDateCalendar, toDateCalendar;

    private String jsonResponse = "", imei, mac, employeeID;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;

    private MaterialButton showButton, clearButton;
    private AppCompatImageView closeImageView;
    private TextInputLayout fromDateInputLayout, toDateInputLayout, consumerNoInputLayout;
    private TextInputEditText fromDateEditText, toDateEditText, consumerNoEditText;
    private String fromDateStr, toDateStr, OTP,vipName;

    private AppCompatSpinner zoneSpinner, subzoneSpinner, complaintTypeSpinner, complaintSubTypeSpinner;
    private ZoneModel zoneModel;
    private SubZoneModel subZoneModel;
    private ComplaintTypeModel complaintTypeModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private List<ZoneModel> zoneModelList;
    private ArrayList<String> zoneList, subZoneList, complaintTypeList, complaintSubTypeList;
    private ArrayAdapter zoneAdapter, subZoneAdapter, complaintTypeAdapter, complaintSubTypeAdapter;
    private int zoneId, subZoneId, complaintTypeId, complaintSubTypeId;

    String complaintNoStr, consumerNoStr, complaintWorkAllocationDateStr, complaintDateTimeStr, complaintSubTypeStr, consumerNameStr, contactNoStr,
            addressStr, tariffStr, statusStr, priorityStr, zoneStr, zoneValueStr, subzone, subZoneStr, disputeBillMonthYrStr, zoneIdValueStr,
            subzoneIdValueStr, complaintTypeIdValueStr, complaintSubTypeIdStr, meterNoStr, address1Str, address2Str, address3Str, pincodeStr, complaintTypeStr,
            meterTransIdStr, searchForStr, latStr, longStr, userTagStr, isAndroidStr, loggedInUserStr, consumerNo,mobileNoStr,srm_bill_amount,allocated_Date,app_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncwork_completion);
        mCon = this;

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        userTagStr = "0";
        isAndroidStr = "Android";
        loggedInUserStr =empcode;
        // loggedInUserStr = "SE002";

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);
        realmOperations.deleteLatLongTable();

        errorLinear = findViewById(R.id.errorLinear);
        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        workCompletionRecyclerView = findViewById(R.id.workCompletionRecyclerView);
        workCompletionRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        workAllocationBottomFilterDialog();

        /*try {
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
        }*/
    }

    // Bottom Dialog Filter
    public void workAllocationBottomFilterDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_ncwork_completion_filter, null));
        sheetBehavior.setContentView(sheetView);
        FrameLayout bottomSheet = sheetBehavior.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(Objects.requireNonNull(bottomSheet)).setState(BottomSheetBehavior.STATE_EXPANDED);
        sheetBehavior.show();
        sheetBehavior.setCancelable(false);

        closeImageView = sheetView.findViewById(R.id.closeImageView);
        clearButton = sheetView.findViewById(R.id.clearButton);
        showButton = sheetView.findViewById(R.id.showButton);
        fromDateInputLayout = sheetView.findViewById(R.id.fromDateInputLayout);
        toDateInputLayout = sheetView.findViewById(R.id.toDateInputLayout);
        consumerNoInputLayout = sheetView.findViewById(R.id.consumerNoInputLayout);
        fromDateEditText = sheetView.findViewById(R.id.fromDateEditText);
        toDateEditText = sheetView.findViewById(R.id.toDateEditText);
        consumerNoEditText = sheetView.findViewById(R.id.consumerNoEditText);
        radioGroup = sheetView.findViewById(R.id.radioGroup);
        registerComplaintRadio = sheetView.findViewById(R.id.registerComplaintRadio);
        rejectedComplaints = sheetView.findViewById(R.id.rejectedComplaints);

        setDate();

        String siteEng = null;
        try {
            siteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (siteEng != null) {
            if (siteEng.equals("SiteEng")) {
                rejectedComplaints.setVisibility(View.GONE);
            } else {
                rejectedComplaints.setVisibility(View.VISIBLE);
            }
        }

        zoneSpinner = sheetView.findViewById(R.id.zoneSpinner);
        subzoneSpinner = sheetView.findViewById(R.id.subzoneSpinner);
        complaintTypeSpinner = sheetView.findViewById(R.id.complaintTypeSpinner);
        complaintSubTypeSpinner = sheetView.findViewById(R.id.complaintSubTypeSpinner);

        String zoneIdLists = PreferenceUtil.getZone();
        List<String> zoneIdList = Arrays.asList(zoneIdLists.split(","));
        // Log.d("check", String.valueOf(zoneIdList));
        ArrayList<String> zoneName = new ArrayList<>();
        ArrayList<String> zoneNameList = new ArrayList<>();
        zoneNameList.add("All");
        zoneName=realmOperations.fetchZone();
        zoneNameList.addAll(zoneName);

        zoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, zoneNameList);
        //Log.d("check", String.valueOf(zoneList));
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneSpinner.setAdapter(zoneAdapter);
      /*  for (int i = 0; i < zoneIdList.size(); i++) {
            int id = Integer.parseInt(zoneIdList.get(i));
            zoneModelList = realmOperations.fetchZoneById(id);
            // Log.d("check List", String.valueOf(zoneModelList));

            for (ZoneModel zoneModel : zoneModelList) {

                zoneName.add(zoneModel.getBU_NAME());
                //Log.d("check", String.valueOf(zoneName));

            }
            zoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, zoneName);
            //Log.d("check", String.valueOf(zoneList));
            zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            zoneSpinner.setAdapter(zoneAdapter);
        }
*/


        if(siteEng.equals("SiteEng")){
            complaintTypeList = realmOperations.fetchComplaintTypeBySE();
        }else{
            complaintTypeList = realmOperations.fetchComplaintType();
        }


        ArrayList<String> complaintType = new ArrayList<>();
        complaintType.add("All");
        complaintType.addAll(complaintTypeList);

        complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintType);
        complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintTypeSpinner.setAdapter(complaintTypeAdapter);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumerNoEditText.setText(null);
                subZoneModel = realmOperations.fetchSubZoneByName("All");
                complaintTypeModel = realmOperations.fetchComplaintTypeByName("All");
                complaintTypeModel = realmOperations.fetchComplaintTypeByName("All");

                subzoneSpinner.setSelection(0);
                complaintTypeSpinner.setSelection(0);
                complaintSubTypeSpinner.setSelection(0);

                setDate();
            }
        });

        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String zoneName = zoneSpinner.getSelectedItem().toString();
                if (zoneName.equals("All")) {
                    zoneId =0;
                } else {
                    zoneModel = realmOperations.fetchZoneByName(zoneName);
                    zoneId= zoneModel.getBUM_BU_ID();
                }


                zoneIdValueStr=String.valueOf(zoneId);

                Log.e("zoneId", String.valueOf(zoneId));

                subZoneList = realmOperations.fetchSubZoneById(zoneId);
                ArrayList<String> subZone = new ArrayList<>();
                subZone.add("All");
                subZone.addAll(subZoneList);

                subZoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, subZone);
                subZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subzoneSpinner.setAdapter(subZoneAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subzoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subzone = subzoneSpinner.getSelectedItem().toString();
                subZoneModel = realmOperations.fetchSubZoneByName(subzone);
                try {
                    if (subzone.equals("All")) {
                        subZoneId = -1;
                    } else {
                        subZoneId = subZoneModel.getPCM_PC_ID();
                    }

                    subzoneIdValueStr = String.valueOf(subZoneId);
                }catch (Exception e){
                    Log.e("spinnerValue",e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        complaintTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String complaintTypeName = complaintTypeSpinner.getSelectedItem().toString();
                complaintTypeModel = realmOperations.fetchComplaintTypeByName(complaintTypeName);

                if (complaintTypeName.equals("All")) {
                    complaintTypeId = 0;
                    complaintTypeIdValueStr = String.valueOf(complaintTypeId);
                    ArrayList<String> subAll = new ArrayList<>();
                    subAll.add("All");
                    complaintSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, subAll);
                    complaintSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    complaintSubTypeSpinner.setAdapter(complaintSubTypeAdapter);
                } else {
                    complaintTypeId = complaintTypeModel.getCMTM_ID();
                    complaintTypeIdValueStr = String.valueOf(complaintTypeId);
                    complaintSubTypeList = realmOperations.fetchComplaintSubType(complaintTypeId);

                    ArrayList<String> complaintSubType = new ArrayList<>();

                    complaintSubType.add("All");
                    complaintSubType.addAll(complaintSubTypeList);

                    complaintSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintSubType);
                    complaintSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    complaintSubTypeSpinner.setAdapter(complaintSubTypeAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        complaintSubTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String complaintSubTypeName = complaintSubTypeSpinner.getSelectedItem().toString();

                if (complaintSubTypeName.equals("All")) {
                    complaintSubTypeId = 0;
                } else {
                    complaintSubTypeModel = realmOperations.fetchComplaintSubTypeByName(complaintSubTypeName);
                    complaintSubTypeId = complaintSubTypeModel.getCOMPLAINTSUBTYPEID();
                }
                complaintSubTypeIdStr = String.valueOf(complaintSubTypeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        fromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromCalendarDate, fromDateCalendar
                        .get(Calendar.YEAR), fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DAY_OF_MONTH));
                //datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        toDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toCalendarDate, toDateCalendar
                        .get(Calendar.YEAR), toDateCalendar.get(Calendar.MONTH),
                        toDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDateStr = fromDateEditText.getText().toString().trim();
                toDateStr = toDateEditText.getText().toString().trim();
                consumerNoStr = consumerNoEditText.getText().toString().trim();

                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == registerComplaintRadio.getId()) {
                    searchForStr = "0";
                } else if (selectedId == rejectedComplaints.getId()) {
                    searchForStr = "1";
                }

                // Log.d("check", searchForStr);

                validateBottomDialog();
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workCompletionModelList != null) {
                    sheetBehavior.cancel();
                } else {
                    finish();
                }
            }
        });
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

    // Validate Bottom Dialog Filter
    private void validateBottomDialog() {
        boolean isValidFromDate = false, isValidToDate = false, isValidConsumerNo = false;

        if (TextUtils.isEmpty(fromDateStr)) {
            fromDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            fromDateInputLayout.setError(null);
            isValidFromDate = true;
        }

        if (TextUtils.isEmpty(toDateStr)) {
            toDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            toDateInputLayout.setError(null);
            isValidToDate = true;
        }

        if (isValidFromDate && isValidToDate) {
            getWorkCompletionData();
            sheetBehavior.cancel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_location_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        /*if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Employee")) {
            menu.findItem(R.id.locButton).setVisible(true);
        } else if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Admin")) {
            menu.findItem(R.id.locButton).setVisible(false);
        }*/

        return super.onPrepareOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.locButton) {
            startActivity(new Intent(mCon, NCMapActivity.class));
        } else if (id == R.id.action_filter) {
            sheetBehavior.show();
            realmOperations.deleteLatLongTable();
            setDate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getWorkCompletionData();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (workCompletionModelList != null) {
            getWorkCompletionData();
        }
    }

    private void getWorkCompletionData() {
       /* Log.d("check", searchForStr + " " + fromDateStr + " " + toDateStr + " " + consumerNoStr + " " + complaintTypeIdValueStr + " " +
                complaintSubTypeIdStr + " " + zoneIdValueStr + " " + zoneValueStr + " " + subzoneIdValueStr + " " + userTagStr + " "
                + isAndroidStr + " " + loggedInUserStr);*/

        String siteEng = null;
        try {
            siteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[9];
           params[0] = fromDateStr;
           params[1] = toDateStr;
           params[2] = zoneIdValueStr;
           params[3] = "";
           params[4] = "0";
           params[5] = "W";
           params[6] = "";
           params[7] = empcode;
        if(siteEng.equals("SiteEng")){
                params[8] = "Mobile";
        } else if (siteEng.equals("ZonalManager")) {
            if (searchForStr.equals("0")) {
                params[8] = "Web";
            } else {
                params[8] = "Reject";
            }
        }
           /* Log.d("check", "params " + fromDateStr + " " + toDateStr + " " + consumerNoStr + " " + complaintTypeIdValueStr + " " +
                    complaintSubTypeIdStr + " " + zoneIdValueStr + " " + zoneValueStr + " " + subzoneIdValueStr);*/

        //  Log.d("check", "" + Arrays.toString(param));

        if (connection.isConnectingToInternet()) {
            WorkCompletion workCompletion = new WorkCompletion();
            workCompletion.execute(params);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class WorkCompletion extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
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
                String paraNames[] = new String[9];

                paraNames[0] = "W_FDate";
                paraNames[1] = "W_Tdate";
                paraNames[2] = "W_BU";
                paraNames[3] = "W_PC";
                paraNames[4] = "W_Fcode";
                paraNames[5] = "W_tag";
                paraNames[6] = "w_ConsNo";
                paraNames[7] = "w_SiteEng";
                paraNames[8] = "w_UserType";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.NComplaint_WorkAllocationGetData, params, paraNames);
                //  Log.d("check ", jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                workCompletionModelList = new ArrayList<>();
                NCWorkCompletionResponseModel[] enums = gson.fromJson(jsonResponse, NCWorkCompletionResponseModel[].class);
                //Log.d("check", "data" + enums);
                if (enums != null && enums.length > 0) {
                    for (NCWorkCompletionResponseModel model : enums) {

                        String zone=  String.valueOf(model.getSRM_BU_ID());
                        String zoneaftrsubstrng=zone.substring(0, zone.indexOf('.'));
                        List<ZoneModel> zoneModelList = realmOperations.fetchZoneById(Integer.parseInt(zoneaftrsubstrng));
                        String zoneName= zoneaftrsubstrng+"-"+zoneModelList.get(0).getBU_NAME();
                        ArrayList<String>subZonelist=realmOperations.fetchSubZoneByPCId(Integer.parseInt(model.getSRM_PC_ID()));
                        String subZoneName=subZonelist.get(0);

                        if(model.getFCOMNO()==null){
                            complaintNoStr="N.A";
                        }else{
                            complaintNoStr = model.getFCOMNO();
                        }
                        if( model.getFLM_SERVICENUMBER()==null){
                            consumerNo="N.A";
                        }else{
                            consumerNo = model.getFLM_SERVICENUMBER();
                        }
                        if(  model.getFLM_APP_DATE()==null){
                            complaintDateTimeStr="N.A";
                        }else{
                            complaintDateTimeStr = model.getFLM_APP_DATE();
                        }
                        if(  model.getFLM_COMPTYPE()==null){
                            complaintTypeStr="N.A";
                        }else{
                            complaintTypeStr = model.getFLM_COMPTYPE();
                        }

                        if(   model.getFRM_REASONNAME()==null){
                            complaintSubTypeStr="N.A";
                        }else{
                            complaintSubTypeStr = model.getFRM_REASONNAME();
                        }

                        if(  model.getCONSUMER_NAME()==null){
                            consumerNameStr="N.A";
                        }else{
                            consumerNameStr = model.getCONSUMER_NAME();
                        }

                        if(  model.getFLM_PHONE()==null){
                            mobileNoStr="N.A";
                        }else{
                            mobileNoStr = model.getFLM_PHONE();
                        }
                        if(   model.getSRM_S_ADDRESS()==null){
                            addressStr="N.A";
                        }else{
                            addressStr = model.getSRM_S_ADDRESS();
                        }
                        if(   model.getTARIFF()==null){
                            tariffStr="N.A";
                        }else{
                            tariffStr = model.getTARIFF();
                        }

                        if(    model.getSTATUS()==null){
                            statusStr="N.A";
                        }else{
                            statusStr = model.getSTATUS();
                        }

                        if( model.getBUM_BU_NAME()==null){
                            zoneStr="N.A";
                        }else{
                            zoneStr=zoneName;
                        }

                        if(  model.getCIRCLE_NAME()==null){
                            subZoneStr="N.A";
                        }else{
                            subZoneStr = subZoneName;
                        }
                        if(  model.getVIP()==null){
                            vipName="N.A";
                        }else{
                            vipName=model.getVIP();
                        }

                        if(  model.getSRM_BILL_AMOUNT()==null){
                            srm_bill_amount="N.A";
                        }else{
                            srm_bill_amount= model.getSRM_BILL_AMOUNT();
                        }
                        if(  model.getFLM_APP_DATE()==null){
                            app_Date="N.A";
                        }else{
                            app_Date= model.getFLM_APP_DATE();
                        }
                        if(  model.getFLM_ALLOCDATE()==null){
                            allocated_Date="N.A";
                        }else{
                            allocated_Date= model.getFLM_ALLOCDATE();
                        }
                        if(  model.getPRIORITY()==null){
                            priorityStr="N.A";
                        }else{
                            priorityStr= model.getPRIORITY();
                        }
                        String cnctNo=  mobileNoStr.substring(0, mobileNoStr.indexOf('.'));
                        NCWorkCompletionModel data = new NCWorkCompletionModel(complaintNoStr, zoneStr, subZoneStr, complaintDateTimeStr, consumerNameStr, addressStr,
                                cnctNo  , complaintTypeStr, complaintSubTypeStr, consumerNo, tariffStr, statusStr, srm_bill_amount, "", "",vipName,app_Date,allocated_Date,priorityStr);
                        workCompletionModelList.add(data);

                    }
                    sheetBehavior.cancel();
                    errorLinear.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    ncworkCompletionAdapter = new NCWorkCompletionAdapter(mCon, searchForStr);
                    ncworkCompletionAdapter.addList(workCompletionModelList);
                    workCompletionRecyclerView.setAdapter(ncworkCompletionAdapter);
                    ncworkCompletionAdapter.notifyDataSetChanged();
                } else {
                    errorLinear.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    Toast.makeText(mCon, R.string.no_complaints, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCWorkCompletionActivity", "Show Button of work completion", error);
            }
            progress.dismiss();
        }
    }

    private void clear() {
        fromDateEditText.setText("");
        toDateEditText.setText("");
        consumerNoEditText.setText("");
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
            Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
