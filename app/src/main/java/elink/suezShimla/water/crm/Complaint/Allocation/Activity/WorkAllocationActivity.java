package elink.suezShimla.water.crm.Complaint.Allocation.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.Allocation.Adapter.WorkAllocationAdapter;
import elink.suezShimla.water.crm.Complaint.Allocation.Model.AllocationModel;
import elink.suezShimla.water.crm.Complaint.Allocation.Model.AutoAllocatedWorkResponseModel;
import elink.suezShimla.water.crm.Complaint.Allocation.Model.GetWorkAllocatedDataModel;
import elink.suezShimla.water.crm.Complaint.MapScreen.Model.LatLongModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.DMAModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.SRModel;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadCustomerType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSourceType;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import elink.suezShimla.water.crm.map.MapsActivity;

public class WorkAllocationActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Context mCon;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView allocationRecyclerView;
    private List<AllocationModel> allocationModelList;
    private WorkAllocationAdapter workAllocationAdapter;
    private BottomSheetDialog sheetBehavior;
    private MaterialDialog progress;
    private LinearLayout errorLinear;

    private AppCompatSpinner zoneSpinner, complaintTypeSpinner, complaintSubTypeSpinner, srSpinner, dmaSpinner, customerTypeSpinner, sourceTypeSpinner,serviceTypeSpinner,complaintOriginSpinner;
    private RealmOperations realmOperations;
    private ZoneModel zoneModel;
    private SubZoneModel subZoneModel;
    private ComplaintTypeModel complaintTypeModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private ArrayList<String> zoneList, subZoneList, complaintTypeList, complaintSubTypeList, sourceTypeList;
    private List<ZoneModel> zoneModelList;
    private ArrayAdapter zoneAdapter, subZoneAdapter, complaintTypeAdapter, complaintSubTypeAdapter, customerTypeArrayAdapter, sourceTypeAdapter;
    private int zoneId, subZoneId, complaintTypeId, complaintSubTypeId;

    private ArrayAdapter srArrayAdapter, dmaArrayAdapter;
    SRModel model_sr;
    DMAModel model_dma;
    private DownloadSourceType complaintSourceModel;
    private DownloadCustomerType customerTypeModel;

    private String jsonResponse = "", sourceTypeIdStr="", originId="", customerTypeId="",serviceTypeStr="",originStr="",departmentID="";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;

    private Calendar fromDateCalendar, toDateCalendar;

    private MaterialButton showButton, autoAllocateButton, clearButton;
    private AppCompatImageView closeImageView;
    private TextInputLayout fromDateInputLayout, toDateInputLayout, consumerNoInputLayout;
    private TextInputEditText fromDateEditText, toDateEditText, consumerNoEditText;
    private String fromDateStr, toDateStr, mainComplaintID="", mainComplaintCode="",fromDate="",toDate="",strClick="";

    private String complaintBy,complaintNoStr, consumerNoStr, complaintDateTimeStr, complaintTypeStr, complaintSubTypeStr, consumerNameStr, mobileNoStr, addressStr,
            workAllocationDateTimeStr, tariffStr, statusStr, zoneStr, subZoneStr, disputeBillMonthYearStr, zoneIdValueStr, subzoneIdValueStr,
            complaintTypeIdValueStr, complaintSubTypeIdStr, wSecStr, userTagStr, conLat, conLong, consumerNo,vipName, sla_status="", aging="", repeat_calls="", com_seq="", complaintCode="", priorityStr="",srId="";
    private ImageButton allocatedFilter;
    private int dmaId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_allocation);
    //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        wSecStr = "0";
        userTagStr = "0";

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);
        realmOperations.deleteLatLongTable();

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

        errorLinear = findViewById(R.id.errorLinear);
        allocatedFilter = findViewById(R.id.allocatedFilter);
        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        allocationRecyclerView = findViewById(R.id.allocationRecyclerView);
        allocationRecyclerView.setHasFixedSize(true);
        allocationRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        workAllocationAdapter = new WorkAllocationAdapter(mCon);

        realmOperations.setEngineerUnCheck();
        departmentID = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEPARTMENTID);

        try {
            departmentID=  new AesAlgorithm().decrypt(departmentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        complaintTypeList = realmOperations.fetchComplaintTypeByDept(departmentID);

        String cc="";
        for(int i=0;i<complaintTypeList.size();i++){
            cc = complaintTypeList.get(i);
        }
        Log.d("cc",cc);
        complaintTypeModel = realmOperations.fetchComplaintTypeByName(cc);
        mainComplaintID = String.valueOf(complaintTypeModel.getCMTM_ID());
        mainComplaintCode = complaintTypeModel.getCMTM_CODE();
        Log.d("cc", String.valueOf(complaintTypeId));

        fromDate= getIntent().getStringExtra("fromDate");
        toDate= getIntent().getStringExtra("toDate");
        strClick= getIntent().getStringExtra("strClick");

        getAllocatedDataWOFilter();
        setDate();
        allocatedFilter.setOnClickListener(this);
    }

    // Bottom Dialog Filter
    public void workAllocationBottomFilterDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_work_allocation_filter, null));
        sheetBehavior.setContentView(sheetView);
        FrameLayout bottomSheet = sheetBehavior.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(Objects.requireNonNull(bottomSheet)).setState(BottomSheetBehavior.STATE_EXPANDED);
        sheetBehavior.show();
        sheetBehavior.setCancelable(false);

        closeImageView = sheetView.findViewById(R.id.closeImageView);
        showButton = sheetView.findViewById(R.id.showButton);
        clearButton = sheetView.findViewById(R.id.clearButton);
        autoAllocateButton = sheetView.findViewById(R.id.autoAllocateButton);
        fromDateInputLayout = sheetView.findViewById(R.id.fromDateInputLayout);
        toDateInputLayout = sheetView.findViewById(R.id.toDateInputLayout);
        consumerNoInputLayout = sheetView.findViewById(R.id.consumerNoInputLayout);

        fromDateEditText = sheetView.findViewById(R.id.fromDateEditText);
        toDateEditText = sheetView.findViewById(R.id.toDateEditText);
        consumerNoEditText = sheetView.findViewById(R.id.consumerNoEditText);

        zoneSpinner = sheetView.findViewById(R.id.zoneSpinner);
//        subzoneSpinner = sheetView.findViewById(R.id.subzoneSpinner);
        complaintTypeSpinner = sheetView.findViewById(R.id.complaintTypeSpinner);
        complaintSubTypeSpinner = sheetView.findViewById(R.id.complaintSubTypeSpinner);
        sourceTypeSpinner = sheetView.findViewById(R.id.sourceTypeSpinner);
        srSpinner = sheetView.findViewById(R.id.srSpinner);
        dmaSpinner = sheetView.findViewById(R.id.dmaSpinner);
        customerTypeSpinner = sheetView.findViewById(R.id.customerTypeSpinner);
        serviceTypeSpinner = sheetView.findViewById(R.id.serviceTypeSpinner);
        serviceTypeSpinner.setOnItemSelectedListener(this);

        complaintOriginSpinner = sheetView.findViewById(R.id.complaintOriginSpinner);
        complaintOriginSpinner.setOnItemSelectedListener(this);

        // setDate();

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
        }

//        if(PreferenceUtil.getSiteEng().equals("SiteEng")){
//            complaintTypeList = realmOperations.fetchComplaintTypeWC();
//        }else{
//            complaintTypeList = realmOperations.fetchComplaintType();
//        }
        setComplaintTypeDropdown();

        setSRDropDown();
        setCustomerTypeDropDown();
        setSourceTypeDropDown("2");


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumerNoEditText.setText(null);
                subZoneModel = realmOperations.fetchSubZoneByName("All");
                complaintTypeModel = realmOperations.fetchComplaintTypeByName("All");
          //      complaintTypeModel = realmOperations.fetchComplaintTypeByName("All");

//                subzoneSpinner.setSelection(0);
                complaintTypeSpinner.setSelection(0);
                complaintSubTypeSpinner.setSelection(0);

                setDate();
            }
        });

        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String zoneName = zoneSpinner.getSelectedItem().toString();
                if(zoneName.equalsIgnoreCase("All")){
                    zoneId = 0;
                    zoneIdValueStr = "0";
                }else {
                    zoneModel = realmOperations.fetchZoneByName(zoneName);
                    zoneId = zoneModel.getBUM_BU_ID();
                    zoneIdValueStr = String.valueOf(zoneId);
               }
//                subZoneList = realmOperations.fetchSubZoneById(zoneId);
//                ArrayList<String> subZone = new ArrayList<>();
//                subZone.add("All");
//                subZone.addAll(subZoneList);
//
//                subZoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, subZone);
//                subZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                subzoneSpinner.setAdapter(subZoneAdapter);
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

        sourceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String sourceTypeStr = sourceTypeSpinner.getSelectedItem().toString();
               if(sourceTypeStr.equalsIgnoreCase("All")){
                   sourceTypeIdStr = "0";
               }else {
                   complaintSourceModel = realmOperations.fetchSourceByName(sourceTypeStr);
                   String sourceTypeId = complaintSourceModel.getSOURCECODE();
                   sourceTypeIdStr = sourceTypeId;
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                if (allocationModelList != null) {
                    sheetBehavior.cancel();
                } else {
                    finish();
                }
            }
        });

        autoAllocateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCon, "single click", Toast.LENGTH_SHORT).show();
               /* fromDateStr = fromDateEditText.getText().toString().trim();
                toDateStr = toDateEditText.getText().toString().trim();

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

                    String[] params = new String[8];

                    params[0] = fromDateStr;
                    params[1] = toDateStr;
                    params[2] = complaintTypeIdValueStr;
                    params[3] = zoneIdValueStr;
                    params[4] = subzoneIdValueStr;
                    params[5] = "0";
                    params[6] = "S001";
                    params[7] = PreferenceUtil.getMac();

                    //  Log.d("check", "params " + params);

                    if (connection.isConnectingToInternet()) {
                        GetAutoAllocatedWork getAutoAllocatedWork = new GetAutoAllocatedWork();
                        getAutoAllocatedWork.execute(params);
                    } else {
                        Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });
    }

    private void setSRDropDown() {
        ArrayList<String> srName = new ArrayList<>();
        srName = realmOperations.fetchSRList();
        ArrayList<String>  srList = new ArrayList<>();
        srList.add("All");
        srList.addAll(srName);

        srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, srList);
        srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        srSpinner.setAdapter(srArrayAdapter);
        srSpinner.setOnItemSelectedListener(this);
    }

    private void setDMADropdown() {
        try {
            ArrayList<String> meterDMAName = new ArrayList<>();
            meterDMAName = realmOperations.fetchDMAList();
            String SRName = srSpinner.getSelectedItem().toString();
            model_sr = realmOperations.fetchSRNameByMSRName(SRName);
            String srID = String.valueOf(model_sr.getTRM_ID());

            model_dma = realmOperations.fetchDMABySRName(srID);
            String srId = String.valueOf(model_dma.getSRID());
            ArrayList<String> fetchedDMAList = new ArrayList<>();
            fetchedDMAList = realmOperations.fetchDMAListAll(srId);

            dmaArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, fetchedDMAList);
            dmaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dmaSpinner.setAdapter(dmaArrayAdapter);
            dmaSpinner.setOnItemSelectedListener(this);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCustomerTypeDropDown() {
        ArrayList<String> srName = new ArrayList<>();
        srName = realmOperations.fetchCustomerTypeName();
        ArrayList<String>  srList = new ArrayList<>();
        srList.add("All");
        srList.addAll(srName);

        customerTypeArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, srList);
        customerTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerTypeSpinner.setAdapter(customerTypeArrayAdapter);
        customerTypeSpinner.setOnItemSelectedListener(this);
    }

    private void setSourceTypeDropDown(String orginId) {
        this.originId = orginId;

        sourceTypeList = realmOperations.fetchSourceTypeName(orginId);
        ArrayList<String>  sourceType = new ArrayList<>();
        sourceType.add("All");
        sourceType.addAll(sourceTypeList);

        sourceTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, sourceType);
        sourceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceTypeSpinner.setAdapter(sourceTypeAdapter);
    }

    private void setComplaintTypeDropdown(){
        String deptId = null;
        try {
            deptId = new AesAlgorithm().decrypt(PreferenceUtil.getDepartmentId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        complaintTypeList = realmOperations.fetchComplaintTypeByDept(deptId);

        ArrayList<String> complaintType = new ArrayList<>();
        complaintType.addAll(complaintTypeList);

        complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintType);
        complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintTypeSpinner.setAdapter(complaintTypeAdapter);
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
            getAllocatedData();
            //clear();
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

       /* if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Employee")) {
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
            startActivity(new Intent(mCon, MapsActivity.class));

            finish();
        } else if (id == R.id.action_filter) {
            sheetBehavior.show();
            realmOperations.deleteLatLongTable();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getAllocatedDataWOFilter();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void getAllocatedData() {
        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String params[] = new String[16];

        params[0] = fromDateStr;   // test-data (02-Apr-2019)
        params[1] = toDateStr;  // test-data (06-May-2019)
        params[2] = zoneIdValueStr;
        params[3] = subzoneIdValueStr;
        params[4] = wSecStr;  // constant (0)
        params[5] = consumerNoStr;   // test-data (111)
        params[6] = userTagStr;   // constant (0)
        params[7] = complaintTypeIdValueStr;
        params[8] = complaintSubTypeIdStr;

        params[9] = String.valueOf(srId);
        params[10] = String.valueOf(dmaId);
        params[11] = sourceTypeIdStr;
        params[12] = customerTypeId;
        params[13] = empcode;
        params[14] = originStr;
        params[15] = serviceTypeStr;

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
            case R.id.allocatedFilter:
                workAllocationBottomFilterDialog();
                setDate();
                break;

            default:
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.srSpinner: {
                String srValue = srSpinner.getSelectedItem().toString();
                if (srValue.equalsIgnoreCase("All")) {
                    ArrayList<String> dmalist = new ArrayList<>();
                    dmalist.add("All");

                    dmaArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, dmalist);
                    dmaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dmaSpinner.setAdapter(dmaArrayAdapter);
                    srId = "0";
                } else {
                    setDMADropdown();

                    model_sr = realmOperations.getSrId(srValue);
                    String srIDStr = model_sr.getTRM_ID();
                    srId = srIDStr;
                }
            }
            break;

            case R.id.dmaSpinner:{
                String dmaSelectedValue = dmaSpinner.getSelectedItem().toString();
                if(dmaSelectedValue.equalsIgnoreCase("All")){
                    dmaId = 0;
                }else {
                    model_dma = realmOperations.getDMAId(dmaSelectedValue);
                    String dmaIDStr = model_dma.getPM_ID();
                    dmaId = Integer.parseInt(dmaIDStr);
                    Log.d("dmaId", ""+dmaId);
                }
            }
            break;

            case R.id.customerTypeSpinner:{
                String selectedValue = customerTypeSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("All")){
                    customerTypeId = "0";
                }else {
                    customerTypeModel = realmOperations.getCustomerTypeId(selectedValue);
                    String custIDStr = customerTypeModel.getCUSTTYPEID();
                    if (custIDStr.contains(".")) {
                        customerTypeId = custIDStr.substring(0, custIDStr.indexOf('.'));
                    } else {
                        customerTypeId = custIDStr;
                    }
                    Log.d("customerTypeId", ""+customerTypeId);
                }
            }
            break;

            case R.id.serviceTypeSpinner:{
                String selectedValue = serviceTypeSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("All")){
                    serviceTypeStr = "0";
                }else if(selectedValue.equalsIgnoreCase("Complaint")){
                    serviceTypeStr = "C";
                }else if(selectedValue.equalsIgnoreCase("Request")){
                    serviceTypeStr = "R";
                }else if(selectedValue.equalsIgnoreCase("Query")){
                    serviceTypeStr = "Q";
                }


            }
            break;
            case R.id.complaintOriginSpinner:{
                String selectedValue = complaintOriginSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("External")){
                    originStr = "2";
                }else if(selectedValue.equalsIgnoreCase("Internal")){
                    originStr = "1";
                }


            }
            break;
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                paraNames[4] = "w_consumer";
                paraNames[5] = "w_fixer";
                paraNames[6] = "comp_type";
                paraNames[7] = "comp_sub_type";
                paraNames[8] = "w_User_type";
                paraNames[9] = "w_user_code";

                paraNames[10] = "SR";                //0
                paraNames[11] = "DMA";              //0
                paraNames[12] = "Source";           //0
                paraNames[13] = "CustomerType";     //0
                paraNames[14] = "Origin";
                paraNames[15] = "ServType";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Complaint_GetWorkCompletionData, params, paraNames);
                Log.e("WorkAllocated",jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                allocationModelList = new ArrayList<>();
                GetWorkAllocatedDataModel[] enums = gson.fromJson(jsonResponse, GetWorkAllocatedDataModel[].class);

                if (enums != null && enums.length > 0) {
                    for (GetWorkAllocatedDataModel model : enums) {
                        complaintNoStr = model.getCOMNO();
                        consumerNo = model.getCOM_SERVICE_NO();
                        complaintDateTimeStr = model.getCOM_COMPDATE();
//                        DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

                        complaintTypeStr = model.getCMTM_NAME();
                        complaintSubTypeStr = model.getOCRM_NAME();
                        consumerNameStr = model.getCONSUMER_NAME();
                        complaintBy = model.getNAME();
                        mobileNoStr = model.getMOBILE();
                        addressStr = model.getADDRESS_NO();
                        //workAllocationDateTimeStr = current date
                        tariffStr = model.getTARIFF();
                        statusStr = model.getSTATUS();
                        zoneStr = model.getBUM_BU_NAME();
                        subZoneStr = model.getGROUP_NAME();
                        disputeBillMonthYearStr = model.getCOM_YEAR_BILL();
                        conLat = model.getSRM_LATITUDE();
                        conLong = model.getSRM_LONGITUDE();
                        vipName = model.getVIP();
                        sla_status = model.getSLA();
                        aging = model.getAGINGOFCOMPLAINT();
                        repeat_calls = model.getCOM_CALLS();
                        com_seq = model.getCOM_SEQ();
                        complaintCode = model.getCOM_NO_TYPE();
                        priorityStr = model.getPRIORITY();

                        AllocationModel data = new AllocationModel(complaintNoStr, zoneStr, subZoneStr, complaintDateTimeStr, consumerNameStr, addressStr,
                                mobileNoStr, complaintTypeStr, complaintSubTypeStr, consumerNo, tariffStr, statusStr, disputeBillMonthYearStr,
                                conLat, conLong,vipName, sla_status, aging, repeat_calls,com_seq,complaintCode,priorityStr,complaintBy);

                        allocationModelList.add(data);

                        if (model.getSRM_LATITUDE() != null && model.getSRM_LONGITUDE() != null) {
                            LatLongModel latLongModelExist = realmOperations.getLatLongExist();
                            if (latLongModelExist == null) {
                                LatLongModel latLongModel = new LatLongModel(model.getSRM_LATITUDE(), model.getSRM_LONGITUDE(), model.getNAME());
                                realmOperations.addLatLong(latLongModel);
                            } else {
                                if (!latLongModelExist.getTitle().equals(model.getNAME())) {
                                    LatLongModel latLongModel = new LatLongModel(model.getSRM_LATITUDE(), model.getSRM_LONGITUDE(), model.getNAME());
                                    realmOperations.addLatLong(latLongModel);
                                }
                            }
                        }
                    }
                    sheetBehavior.cancel();
                    errorLinear.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    allocationRecyclerView.setVisibility(View.VISIBLE);
                    workAllocationAdapter.addList(allocationModelList);
                    allocationRecyclerView.setAdapter(workAllocationAdapter);
                    workAllocationAdapter.notifyDataSetChanged();


                } else {
                    errorLinear.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    allocationRecyclerView.setVisibility(View.GONE);
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "Complaint_GetWorkAllocationData", "Show or Swipe to refresh", error);
            }
            progress.dismiss();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetAutoAllocatedWork extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
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
                String paraNames[] = new String[8];

                paraNames[0] = "FromDate";
                paraNames[1] = "ToDate";
                paraNames[2] = "ComplaintType";
                paraNames[3] = "BU";
                paraNames[4] = "PC";
                paraNames[5] = "Section";
                paraNames[6] = "Emp_Code";
                paraNames[7] = "IP";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.ComplaintAutoAllocate, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                AutoAllocatedWorkResponseModel enums = gson.fromJson(jsonResponse, AutoAllocatedWorkResponseModel.class);
                //   Log.d("check", "enum " + enums);
                if (!enums.getDiv_Cmsg().equals(" ")) {
                    Toast.makeText(mCon, enums.getDiv_CError(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mCon, enums.getDiv_Cmsg(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "WorkAllocationActivity", "Complaint_AutoAllocate", error);

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
        }
    }

    private void getAllocatedDataWOFilter() {
        Date today = new Date();
       // SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
      //  String dateToStr = format.format(today);

        // Decrypt EmpCode
        String empcode = null;
        try {
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[16];

        params[0] = fromDate;   // test-data (02-Apr-2019)
        params[1] = toDate;  // test-data (06-May-2019)
        params[2] = "0"; //0
        params[3] = "0"; //0
        params[4] = "";
        params[5] = "";
        params[6] = mainComplaintID;
        params[7] = "0";//"0";// mainComplaintID;
        params[8] = strClick;// Allocated //ReAllocated //Completed // All
        params[9] = empcode;
        params[10] = "0";
        params[11] = "0";
        params[12] = "0";
        params[13] = "0";
        params[14] = "0";
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
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }
}
