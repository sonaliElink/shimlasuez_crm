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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
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
import elink.suezShimla.water.crm.Complaint.Allocation.Adapter.WorkAllocationCompletionAdapter;
import elink.suezShimla.water.crm.Complaint.MapScreen.Model.LatLongModel;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.WorkCompletionResponseModel;
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

public class WorkAllocationCompletionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Context mCon;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView workCompletionRecyclerView;
    private List<WorkCompletionResponseModel> workCompletionModelList=new ArrayList<>();

    private WorkAllocationCompletionAdapter workCompletionAdapter;
    private BottomSheetDialog sheetBehavior;
    private MaterialDialog progress;
    private LinearLayout errorLinear;
    private RadioGroup radioGroup;
    private RadioButton registerComplaintRadio, rejectedComplaints,forwardFrmMmg;

    private Calendar fromDateCalendar, toDateCalendar;

    private String jsonResponse = "", imei, mac, employeeID;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;

    private MaterialButton showButton,clearButton;
    private AppCompatImageView closeImageView;
    private TextInputLayout fromDateInputLayout, toDateInputLayout, consumerNoInputLayout;
    private TextInputEditText fromDateEditText, toDateEditText, consumerNoEditText;
    private String fromDateStr, toDateStr, OTP,vipName,repeatCall="",agging="",sla="",com_seq="",departmentID="", mainComplaintID="", ORIGIN="",mainComplaintCode="",
            readerRemark="",complaintBy="";

    private AppCompatSpinner zoneSpinner, complaintTypeSpinner, complaintSubTypeSpinner, srSpinner, dmaSpinner, customerTypeSpinner, sourceTypeSpinner,serviceTypeSpinner,complaintOriginSpinner,searchForSpinner;
    private ZoneModel zoneModel;
    private SubZoneModel subZoneModel;
    private ComplaintTypeModel complaintTypeModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private List<ZoneModel> zoneModelList;
  //  private ArrayList<String> zoneList, subZoneList, complaintTypeList, complaintSubTypeList;
    //private ArrayAdapter zoneAdapter, subZoneAdapter, complaintTypeAdapter, complaintSubTypeAdapter;
    private int zoneId, subZoneId, complaintTypeId, complaintSubTypeId;

    String strConnCategory="",strConnSize="",complaintCode,complaintNoStr, consumerNoStr, complaintWorkAllocationDateStr, complaintDateTimeStr, complaintSubTypeStr, consumerNameStr, contactNoStr,
            addressStr, tariffStr, statusStr, priorityStr, zoneStr, zoneValueStr, subzone, subZoneStr, disputeBillMonthYrStr, zoneIdValueStr,
            subzoneIdValueStr="0", complaintTypeIdValueStr, complaintSubTypeIdStr, meterNoStr, address1Str, address2Str, address3Str, pincodeStr, complaintTypeStr,actionFormStr=""
            ,comType="",comPno="",
                meterTransIdStr, searchForStr, latStr, longStr, userTagStr, isAndroidStr, loggedInUserStr, consumerNo;

    private ImageButton allocatedFilter;
    private SearchView searchView;

    private ArrayAdapter srArrayAdapter, dmaArrayAdapter;
    SRModel model_sr;
    DMAModel model_dma;
    private int  dmaId;

    String  sourceTypeIdStr="", originId="", customerTypeId="",serviceTypeStr="",originStr="",srId="",fromDate="",toDate="",strClick="";;

    private DownloadSourceType complaintSourceModel;
    private DownloadCustomerType customerTypeModel;

    private ArrayAdapter zoneAdapter, subZoneAdapter, complaintTypeAdapter, complaintSubTypeAdapter, customerTypeArrayAdapter, sourceTypeAdapter;

    private ArrayList<String> zoneList, subZoneList, complaintTypeList, complaintSubTypeList,sourceTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_allocation_completion);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        userTagStr = "0";
        isAndroidStr = "Android";
        // Decrypt EmpCode
        try {
            loggedInUserStr = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // loggedInUserStr = "SE002";

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);
        realmOperations.deleteLatLongTable();

        errorLinear = findViewById(R.id.errorLinear);
        allocatedFilter = findViewById(R.id.allocatedFilter);
        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        workCompletionRecyclerView = findViewById(R.id.workCompletionRecyclerView);
        workCompletionRecyclerView.setHasFixedSize(true);
        workCompletionRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        allocatedFilter.setOnClickListener(this);


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

        if(departmentID.equalsIgnoreCase("8")) {//viren

            complaintTypeModel = realmOperations.fetchComplaintTypeByName("Operation & Maintenance");
        }
        else {
            complaintTypeModel = realmOperations.fetchComplaintTypeByName(cc);

        }

//        complaintTypeModel = realmOperations.fetchComplaintTypeByName(cc);
        try {
            mainComplaintID = String.valueOf(complaintTypeModel.getCMTM_ID());
        }catch(Exception e){

             e.printStackTrace();
        }
        mainComplaintCode = complaintTypeModel.getCMTM_CODE();
        Log.d("cc", String.valueOf(complaintTypeId));

      /*  String deptId = PreferenceUtil.getDepartmentId();
         complaintTypeModel = realmOperations.fetchComplaintTypeModelById(deptId);
        Toast.makeText(mCon, ""+complaintTypeModel.getCMTM_ID(), Toast.LENGTH_SHORT).show();
*/
        // workAllocationBottomFilterDialog();

        fromDate= getIntent().getStringExtra("fromDate");
        toDate= getIntent().getStringExtra("toDate");
        strClick= getIntent().getStringExtra("strClick");

        Log.d("workallData", ""+fromDate+" "+toDate+" "+strClick);
        getWorkCompletionDataWOFilter();

    }




    public void workAllocationBottomFilterDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_work_allocation_completion_filter, null));
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
        // subzoneSpinner = sheetView.findViewById(R.id.subzoneSpinner);
        complaintTypeSpinner = sheetView.findViewById(R.id.complaintTypeSpinner);
        complaintTypeSpinner.setOnItemSelectedListener(this);

        complaintSubTypeSpinner = sheetView.findViewById(R.id.complaintSubTypeSpinner);

        srSpinner = sheetView.findViewById(R.id.srSpinner);
        dmaSpinner = sheetView.findViewById(R.id.dmaSpinner);
        sourceTypeSpinner = sheetView.findViewById(R.id.sourceTypeSpinner);
        sourceTypeSpinner.setOnItemSelectedListener(this);

        customerTypeSpinner = sheetView.findViewById(R.id.customerTypeSpinner);
        serviceTypeSpinner = sheetView.findViewById(R.id.serviceTypeSpinner);
        serviceTypeSpinner.setOnItemSelectedListener(this);

        complaintOriginSpinner = sheetView.findViewById(R.id.complaintOriginSpinner);
        complaintOriginSpinner.setOnItemSelectedListener(this);


        searchForSpinner = sheetView.findViewById(R.id.searchForSpinner);
        searchForSpinner.setOnItemSelectedListener(this);



       // setDate();
        setSRDropDown();
        setSourceTypeDropDown("2");
        setComplaintTypeDropdown();
        setCustomerTypeDropDown();

        String zoneIdLists = PreferenceUtil.getZone();
        List<String> zoneIdList = Arrays.asList(zoneIdLists.split(","));
        // Log.d("check", String.valueOf(zoneIdList));
        ArrayList<String> zoneName = new ArrayList<>();
        zoneName.add("All");
    //    zoneName.addAll(zoneName);


        for (int i = 0; i < zoneIdList.size(); i++) {
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

        String siteEng = null;
        try {
            siteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(siteEng.equals("SiteEng")){
            complaintTypeList = realmOperations.fetchComplaintTypeWC();
        }else{
            complaintTypeList = realmOperations.fetchComplaintType();
        }


     /*   ArrayList<String> complaintType = new ArrayList<>();
        complaintType.add("All");
        complaintType.addAll(complaintTypeList);

        complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintType);
        complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintTypeSpinner.setAdapter(complaintTypeAdapter);*/




        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumerNoEditText.setText(null);
                subZoneModel = realmOperations.fetchSubZoneByName("All");
                complaintTypeModel = realmOperations.fetchComplaintTypeByName("All");

                // subzoneSpinner.setSelection(0);
                complaintTypeSpinner.setSelection(0);
                complaintSubTypeSpinner.setSelection(0);

               // setDate();
                clear();
            }
        });

        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zoneValueStr = zoneSpinner.getSelectedItem().toString();
                if(zoneValueStr.equalsIgnoreCase("All")){
                    zoneId = 0;
                    zoneIdValueStr = "All";
                }else {
                    zoneModel = realmOperations.fetchZoneByName(zoneValueStr);
                    zoneId = zoneModel.getBUM_BU_ID();
                    zoneIdValueStr = String.valueOf(zoneId);
                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


      /*  complaintTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
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
                Calendar fromDateCalendarr;
                fromDateCalendarr = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromCalendarDate, fromDateCalendar
                        .get(Calendar.YEAR), fromDateCalendarr.get(Calendar.MONTH),
                        fromDateCalendarr.get(Calendar.DAY_OF_MONTH));
             //   datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

                datePickerDialog.show();
            }
        });

        toDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar toDateCalendarr;
                toDateCalendarr = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toCalendarDate, toDateCalendar
                        .get(Calendar.YEAR), toDateCalendarr.get(Calendar.MONTH),
                        toDateCalendarr.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
        showButton.setOnClickListener(this);

      /*  showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDateStr = fromDateEditText.getText().toString().trim();
                toDateStr = toDateEditText.getText().toString().trim();
                consumerNoStr = consumerNoEditText.getText().toString().trim();

                validateBottomDialog();
            }
        });*/

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


        public void filter(String input) {
        List<WorkCompletionResponseModel> list = new ArrayList<>();

        for (WorkCompletionResponseModel item : workCompletionModelList) {
            if (item.getCOMNO().toLowerCase().contains(input.toLowerCase()) || item.getNAME().toLowerCase().contains(input.toLowerCase())||item.getCOM_SERVICE_NO().toLowerCase().contains(input.toLowerCase())) {
                list.add(item);
            }
        }
        try {
            workCompletionAdapter.filterList(list);
        }
        catch (Exception e){
            e.printStackTrace();
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
            case R.id.sourceTypeSpinner:{
                String sourceTypeStr = sourceTypeSpinner.getSelectedItem().toString();
                if(sourceTypeStr.equalsIgnoreCase("All")){
                    sourceTypeIdStr = "0";
                }else {
                    complaintSourceModel = realmOperations.fetchSourceByName(sourceTypeStr);
                    String sourceTypeId = complaintSourceModel.getSOURCECODE();
                    sourceTypeIdStr = sourceTypeId;
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

            case R.id.complaintTypeSpinner:{
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
            break;

            case R.id.searchForSpinner:{
                String selectedValue = searchForSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Allocated")){
                    searchForStr = "Allocated";
                }else if(selectedValue.equalsIgnoreCase("Forworded")){
                    searchForStr = "Forwarded";
                }else if(selectedValue.equalsIgnoreCase("Rejected")){
                    searchForStr = "Rejected";
                }



            }
            break;
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.locButton) {
            startActivity(new Intent(mCon, MapsActivity.class));
        } else if (id == R.id.action_filter) {
            workAllocationBottomFilterDialog();
            //sheetBehavior.show();
            realmOperations.deleteLatLongTable();
           // setDate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
       // getWorkCompletionData();
        getWorkCompletionDataWOFilter();

        swipeRefreshLayout.setRefreshing(false);
    }


    private void getWorkCompletionData() {
        String param[] = new String[16];

        param[0] = fromDateStr;  // test-data (08-Apr-2019)
        param[1] = toDateStr;
        param[2] = String.valueOf(zoneId);    // test-data (1-East)
        param[3] = subzoneIdValueStr; //   test-data (1)
        param[4] = consumerNoStr;
        param[5] = "";   //test-data (0)
        param[6] = complaintTypeIdValueStr;   //test-data (0)
        param[7] = complaintSubTypeIdStr;   //test-data (0)
        param[8] = "AndroidAllocated";  //  test-data (1)
        param[9] = loggedInUserStr;    // test-data (1-East)
        param[10] = srId;    // test-data (0)
        param[11] = String.valueOf(dmaId);  // test-data (Web) // from login
        param[12] = sourceTypeIdStr;
        param[13] = customerTypeId;
        param[14] = originStr;
        param[15] = serviceTypeStr;


     /*
         paraNames[0] = "FromDate";
                paraNames[1] = "ToDate";
                paraNames[2] = "BU";
                paraNames[3] = "PC";
                paraNames[4] = "Consumer";
                paraNames[5] = "Fixer";
                paraNames[6] = "CompType";
                paraNames[7] = "CompSubType";
                paraNames[8] = "UserType";
                paraNames[9] = "LoginUser";

                paraNames[10] = "SR";                //0
                paraNames[11] = "DMA";              //0
                paraNames[12] = "Source";           //0
                paraNames[13] = "CustomerType";     //0
                paraNames[14] = "Origin";
                paraNames[15] = "ServType";
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


        params = {String[17]@9775}
        0 = "0"
        1 = "01-Jul-2020"
        2 = "02-Nov-2020"
        3 = ""
        4 = "5"
        5 = "0"
        6 = "1"
        7 = "East"
        8 = "0"
        9 = "0"
        10 = "Android"
        11 = "1000"
        12 = "0"
        13 = "0"
        14 = "0"
        15 = "0"
        16 = "1000"*/

        if (connection.isConnectingToInternet()) {
            WorkCompletion workCompletion = new WorkCompletion();
            workCompletion.execute(param);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allocatedFilter:
                workAllocationBottomFilterDialog();
                break;

            case R.id.showButton:

                if(checkValidation()) {

                    getWorkCompletionData();

                    sheetBehavior.cancel();

                }

/*
                fromDateStr = fromDateEditText.getText().toString().trim();
                toDateStr = toDateEditText.getText().toString().trim();
                consumerNoStr = consumerNoEditText.getText().toString().trim();

                validateBottomDialog();*/
                break;


            default:

        }

     /*   if(v.getId() == R.id.allocatedFilter)
        {

        }*/
    }


    private boolean checkValidation() {

        fromDateStr = fromDateEditText.getText().toString().trim();
        toDateStr = toDateEditText.getText().toString().trim();
        consumerNoStr = consumerNoEditText.getText().toString().trim();


        if (fromDateStr.equals("")) {
          //  fromDateEditText.requestFocus();
              //fromDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));

            return true;
        } if (toDateStr.equals("")) {
         //   toDateEditText.requestFocus();
           // toDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));

            return true;
        }


        return true;

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

                String paraNames[] = new String[16];
                paraNames[0] = "FromDate";
                paraNames[1] = "ToDate";
                paraNames[2] = "BU";
                paraNames[3] = "PC";
                paraNames[4] = "Consumer";
                paraNames[5] = "Fixer";
                paraNames[6] = "CompType";
                paraNames[7] = "CompSubType";
                paraNames[8] = "UserType";
                paraNames[9] = "LoginUser";

                paraNames[10] = "SR";                //0
                paraNames[11] = "DMA";              //0
                paraNames[12] = "Source";           //0
                paraNames[13] = "CustomerType";     //0
                paraNames[14] = "Origin";
                paraNames[15] = "ServType";




                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Complaint_GetWorkCompletionData, params, paraNames);
            //    jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetWorkCompletionData, params, paraNames);
                  Log.e("check ", jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                String error = e.toString();
                ErrorClass.errorData(mCon, "Complaint_GetWorkCompletionData API", "jsonresponse null", error);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
//                workCompletionModelList = new ArrayList<>();

                workCompletionModelList.clear();
                allocatedFilter.setEnabled(true);


                WorkCompletionResponseModel[] enums = gson.fromJson(jsonResponse, WorkCompletionResponseModel[].class);
                //Log.d("check", "data" + enums);

                if (enums != null && enums.length > 0) {
                    for (WorkCompletionResponseModel model : enums) {
                        complaintCode = model.getCOM_NO_TYPE();
                        complaintNoStr = model.getCOMNO();
                        consumerNo = model.getCOM_SERVICE_NO();
                        complaintWorkAllocationDateStr = model.getCOM_ALLOCATIONDATE();
                        complaintDateTimeStr = model.getCOM_COMPDATE();
                        complaintSubTypeStr = model.getOCRM_NAME();
                        consumerNameStr = model.getCONSUMER_NAME();
                        contactNoStr = model.getCONTACTNO();
                        addressStr = model.getADDRESS_NO();
                        tariffStr = model.getTARIFF();
                        statusStr = model.getSTATUS();
                        priorityStr = model.getPRIORITY();
                        zoneStr = model.getBUM_BU_NAME();
                        subZoneStr = model.getGROUP_NAME();
                        disputeBillMonthYrStr = model.getCOM_YEAR_BILL();
                        latStr = model.getSRM_LATITUDE();
                        longStr = model.getSRM_LONGITUDE();
                        vipName=model.getVIP();
                        repeatCall=model.getCOM_CALLS();
                        agging=model.getAGINGOFCOMPLAINT();
                        sla=model.getSLA();
                        com_seq=model.getCOM_SEQ();

                        // List<LatLongModel> latLongModelList = new ArrayList<>();
                        realmOperations.deleteLatLongTable();
                        if (model.getSRM_LATITUDE() != null && model.getSRM_LONGITUDE() != null) {
                            LatLongModel LatLongModelExist = realmOperations.getLatLongExist();
                            if (LatLongModelExist == null) {
                                LatLongModel LatLongModel = new LatLongModel(model.getSRM_LATITUDE(), model.getSRM_LONGITUDE(), model.getNAME());
                                realmOperations.addLatLong(LatLongModel);
                        } else {
                            if (!LatLongModelExist.getTitle().equals(model.getNAME())) {
                                LatLongModel LatLongModel = new LatLongModel(model.getSRM_LATITUDE(), model.getSRM_LONGITUDE(), model.getNAME());
                                realmOperations.addLatLong(LatLongModel);
                            }
                        }
                        }
                       /* List<String> latLongModelList = model.getSRM_LATITUDE();
                        for(LatLongModel latLongModel : latLongModelList){
                        }*/
                        // pass parameters
                        meterNoStr = model.getCOM_METER_REPLACE();
                        meterTransIdStr = model.getCOM_METER_TRANSID();
                        address1Str = model.getADDRESS1();
                        address2Str = model.getADDRESS2();
                        address3Str = model.getADDRESS3();
                        pincodeStr = model.getPIN();
                        complaintTypeStr = model.getCMTM_NAME();
                        actionFormStr = model.getCOM_REMARKS();
                        comType = model.getCOM_TYPE();
                        comPno = model.getCOM_PROCESSCODE();
                        ORIGIN=model.getORIGIN();
                        readerRemark = model.getREADERREMARK();
                        complaintBy = model.getNAME();

                        strConnCategory = model.getSRM_CATEGORY_ID();
                        strConnSize = model.getSRM_CONNSIZE_ID();

                        WorkCompletionResponseModel data = new WorkCompletionResponseModel(complaintCode,complaintNoStr, consumerNo, complaintWorkAllocationDateStr,
                                complaintDateTimeStr, complaintSubTypeStr, consumerNameStr, contactNoStr, addressStr, tariffStr, statusStr, priorityStr,
                                zoneStr, subZoneStr, disputeBillMonthYrStr, address1Str, address2Str, address3Str, pincodeStr, complaintTypeStr,meterNoStr,
                                meterTransIdStr, latStr, longStr,vipName,actionFormStr,repeatCall,agging,sla,comType,com_seq,comPno,ORIGIN,readerRemark,complaintBy,strConnCategory,strConnSize);
                        workCompletionModelList.add(data);
                    }
                    sheetBehavior.cancel();
                    errorLinear.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    workCompletionRecyclerView.setVisibility(View.VISIBLE);

                    workCompletionAdapter = new WorkAllocationCompletionAdapter(mCon, searchForStr,fromDate,toDate,strClick);
                    workCompletionAdapter.addList(workCompletionModelList);
                    workCompletionRecyclerView.setAdapter(workCompletionAdapter);

                    workCompletionAdapter.notifyDataSetChanged();
                } else {


                    workCompletionRecyclerView.setVisibility(View.GONE);
                    errorLinear.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    //Toast.makeText(mCon, R.string.no_complaints, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                //  Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "WorkCompletionActivity", "show button of WorkCompletion", error);
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

    private void getWorkCompletionDataWOFilter() {
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

        Log.e("ComplaintWorkAllocated",Arrays.toString(params));
        if (connection.isConnectingToInternet()) {
            WorkCompletion workCompletion = new WorkCompletion();
            workCompletion.execute(params);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
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
