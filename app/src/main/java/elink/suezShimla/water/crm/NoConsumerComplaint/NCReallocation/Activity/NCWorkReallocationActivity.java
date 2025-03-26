package elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
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
import elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Adapter.NCReallocationAdapter;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Model.NCGetWorkReAllocateDataModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Model.NCReallocationModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class NCWorkReallocationActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private Context mCon;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView reallocationRecyclerView;
    private List<NCReallocationModel> ncreallocationModelList;
    private NCReallocationAdapter ncreallocationAdapter;
    private BottomSheetDialog sheetBehavior;
    private MaterialDialog progress;
    private LinearLayout errorLinear;

    private Calendar fromDateCalendar, toDateCalendar;

    private AppCompatSpinner zoneSpinner, subzoneSpinner, complaintTypeSpinner, complaintSubTypeSpinner;
    private ZoneModel zoneModel;
    private SubZoneModel subZoneModel;
    private ComplaintTypeModel complaintTypeModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private List<ZoneModel> zoneModelList;
    private ArrayList<String> zoneList, subZoneList, complaintTypeList, complaintSubTypeList;
    private ArrayAdapter zoneAdapter, subZoneAdapter, complaintTypeAdapter, complaintSubTypeAdapter;
    private int zoneId, subZoneId, complaintTypeId, complaintSubTypeId;

    private MaterialButton showButton, clearButton;
    private AppCompatImageView closeImageView;
    private TextInputLayout fromDateInputLayout, toDateInputLayout, consumerNoInputLayout;
    private TextInputEditText fromDateEditText, toDateEditText, consumerNoEditText;
    private String fromDateStr, toDateStr,srm_bill_amount;

    private String complaintNoStr, consumerNoStr, complaintDateTimeStr, complaintTypeStr, complaintSubTypeStr, consumerNameStr, mobileNoStr, addressStr,
            workAllocationDateTimeStr, tariffStr, statusStr, zoneStr, subZoneStr, disputeBillMonthYearStr, zoneIdValueStr, subzoneIdValueStr,
            complaintTypeIdValueStr, complaintSubTypeIdStr, priorityStr, sourceTypeStr, assignedCodeStr, assignedNameStr, wSecStr, userTagStr, consumerNo,vipName;

    private String jsonResponse = "", imei, mac, employeeID;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncwork_reallocation);
        mCon = this;

        wSecStr = "0";
        userTagStr = "0";

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

        errorLinear = findViewById(R.id.errorLinear);
        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        reallocationRecyclerView = findViewById(R.id.reallocationRecyclerView);
        reallocationRecyclerView.setHasFixedSize(true);
        reallocationRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        ncreallocationAdapter = new NCReallocationAdapter(mCon);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        workAllocationBottomFilterDialog();

        realmOperations.setEngineerUnCheck();

        setDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        realmOperations = new RealmOperations(mCon);
        realmOperations.setEngineerUnCheck();

        if (ncreallocationModelList != null) {
            getWorkReallocatedData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        realmOperations.close();
    }

    public void workAllocationBottomFilterDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_work_re_allocation_filter, null));
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

        zoneSpinner = sheetView.findViewById(R.id.zoneSpinner);
        subzoneSpinner = sheetView.findViewById(R.id.subzoneSpinner);
        complaintTypeSpinner = sheetView.findViewById(R.id.complaintTypeSpinner);
        complaintSubTypeSpinner = sheetView.findViewById(R.id.complaintSubTypeSpinner);

        // setDate();

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
       /* for (int i = 0; i < zoneIdList.size(); i++) {
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

        String siteEng = null;
        try {
            siteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                String subzone = subzoneSpinner.getSelectedItem().toString();
                subZoneModel = realmOperations.fetchSubZoneByName(subzone);
                try {
                    if (subzone.equals("All")) {
                        subZoneId = -1;
                    }else{
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

                validateBottomDialog();
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ncreallocationModelList != null) {
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
        boolean isValidFromDate = false, isValidToDate = false;

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
            getWorkReallocatedData();
            sheetBehavior.cancel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            sheetBehavior.show();
            // setDate();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getWorkReallocatedData();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void getWorkReallocatedData() {
        String params[] = new String[6];

        params[0] = fromDateStr;   // test-data (02-Apr-2019)
        params[1] = toDateStr;  // test-data (06-May-2019)
        params[2] = zoneIdValueStr;
        params[3] = "";
        params[4] = "N";  // constant (0)
        params[5] = "";   // test-data (111)
          // constant (0)

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
                String paraNames[] = new String[6];
                paraNames[0] = "W_FDate";
                paraNames[1] = "W_Tdate";
                paraNames[2] = "W_BU";
                paraNames[3] = "W_PC";
                paraNames[4] = "W_tag";
                paraNames[5] = "w_ConsNo";
                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.NComplaint_WorkReAllocationGetData, params, paraNames);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                ncreallocationModelList = new ArrayList<>();
                NCGetWorkReAllocateDataModel[] enums = gson.fromJson(jsonResponse, NCGetWorkReAllocateDataModel[].class);
                if (enums != null && enums.length > 0) {
                    for (NCGetWorkReAllocateDataModel model : enums) {
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

                        if(  model.getPRIORITY()==null){
                            priorityStr="N.A";
                        }else{
                            priorityStr= model.getPRIORITY();

                        } if(  model.getASSIGNEDCODE()==null){
                            assignedCodeStr="N.A";
                        }else{
                            assignedCodeStr= model.getASSIGNEDCODE();
                        }
                        if(  model.getASSIGNEDNAME()==null){
                            assignedNameStr="N.A";
                        }else{
                            assignedNameStr= model.getASSIGNEDNAME();
                        }
                        String cnctNo=  mobileNoStr.substring(0, mobileNoStr.indexOf('.'));
                        NCReallocationModel data = new NCReallocationModel(complaintNoStr, zoneStr, subZoneStr, complaintDateTimeStr, consumerNameStr, addressStr,
                                cnctNo  , complaintTypeStr, complaintSubTypeStr, consumerNo, tariffStr, statusStr, srm_bill_amount, "", "",vipName,priorityStr,assignedCodeStr,assignedNameStr);
                        ncreallocationModelList.add(data);
                    }
                    sheetBehavior.cancel();
                    errorLinear.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    ncreallocationAdapter.addList(ncreallocationModelList);
                    reallocationRecyclerView.setAdapter(ncreallocationAdapter);
                    ncreallocationAdapter.notifyDataSetChanged();
                } else {
                    errorLinear.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCWorkReallocationActivity", "showButton of work reallocated data", error);
            }
            progress.dismiss();
        }
    }

    private void clear() {
        fromDateEditText.setText("");
        toDateEditText.setText("");
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
}
