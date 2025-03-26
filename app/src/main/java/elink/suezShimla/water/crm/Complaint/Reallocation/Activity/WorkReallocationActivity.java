package elink.suezShimla.water.crm.Complaint.Reallocation.Activity;

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

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.Reallocation.Adapter.ReallocationAdapter;
import elink.suezShimla.water.crm.Complaint.Reallocation.Model.ReallocationResponseDataModel;
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

public class WorkReallocationActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context mCon;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView reallocationRecyclerView;
    private List<ReallocationResponseDataModel> reallocationModelList;
    private ReallocationAdapter reallocationAdapter;
    private BottomSheetDialog sheetBehavior;
    private MaterialDialog progress;
    private LinearLayout errorLinear;

    private Calendar fromDateCalendar, toDateCalendar;

    private AppCompatSpinner zoneSpinner, complaintTypeSpinner, complaintSubTypeSpinner, srSpinner, dmaSpinner, customerTypeSpinner, sourceTypeSpinner,serviceTypeSpinner,complaintOriginSpinner;
    private ZoneModel zoneModel;
    private SubZoneModel subZoneModel;
    private ComplaintTypeModel complaintTypeModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private List<ZoneModel> zoneModelList;
    private int zoneId, subZoneId, complaintTypeId, complaintSubTypeId;

    private MaterialButton showButton, clearButton;
    private AppCompatImageView closeImageView;
    private TextInputLayout fromDateInputLayout, toDateInputLayout, consumerNoInputLayout;
    private TextInputEditText fromDateEditText, toDateEditText, consumerNoEditText;
    private String fromDateStr, toDateStr;

    private String complaintNoStr, consumerNoStr, complaintDateTimeStr, complaintTypeStr, complaintSubTypeStr, consumerNameStr, mobileNoStr, addressStr,
            workAllocationDateTimeStr, tariffStr, statusStr, zoneStr, subZoneStr, disputeBillMonthYearStr, zoneIdValueStr, subzoneIdValueStr,
            complaintTypeIdValueStr, complaintSubTypeIdStr, priorityStr, sourceTypeStr, assignedCodeStr, assignedNameStr, wSecStr, userTagStr, consumerNo,vipName,com_seq="",complaintCode="";

    private String jsonResponse = "", imei, mac, employeeID,repeatCall="",agging="" ,sla="",latitude="",longitude="";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;
    private ImageButton allocatedFilter;


    private ArrayAdapter srArrayAdapter, dmaArrayAdapter;
    SRModel model_sr;
    DMAModel model_dma;

    private int dmaId;

    String  sourceTypeIdStr="", originId="", customerTypeId="",serviceTypeStr="",originStr="", srId="";

    private DownloadSourceType complaintSourceModel;
    private DownloadCustomerType customerTypeModel;

    private ArrayAdapter zoneAdapter, subZoneAdapter, complaintTypeAdapter, complaintSubTypeAdapter, customerTypeArrayAdapter, sourceTypeAdapter;

    private ArrayList<String> zoneList, subZoneList, complaintTypeList, complaintSubTypeList,sourceTypeList;



    String departmentID="",mainComplaintID="",fromDate="",toDate="",strClick="";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_reallocation);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        wSecStr = "0";
        userTagStr = "0";

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

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

        errorLinear = findViewById(R.id.errorLinear);
        allocatedFilter = findViewById(R.id.allocatedFilter);
        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        reallocationRecyclerView = findViewById(R.id.reallocationRecyclerView);
        reallocationRecyclerView.setHasFixedSize(true);
        reallocationRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        reallocationAdapter = new ReallocationAdapter(mCon);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        allocatedFilter.setOnClickListener(this);

        departmentID = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEPARTMENTID);
//        departmentID = aes.decrypt( departmentID.getBytes(), secretKey, IV);

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


        fromDate= getIntent().getStringExtra("fromDate");
        toDate= getIntent().getStringExtra("toDate");
        strClick= getIntent().getStringExtra("strClick");

        getWorkReallocatedDataWOFilter();

        realmOperations.setEngineerUnCheck();

        setDate();
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
       // subzoneSpinner = sheetView.findViewById(R.id.subzoneSpinner);
        complaintTypeSpinner = sheetView.findViewById(R.id.complaintTypeSpinner);
        complaintSubTypeSpinner = sheetView.findViewById(R.id.complaintSubTypeSpinner);

        srSpinner = sheetView.findViewById(R.id.srSpinner);
        dmaSpinner = sheetView.findViewById(R.id.dmaSpinner);
        sourceTypeSpinner = sheetView.findViewById(R.id.sourceTypeSpinner);
        customerTypeSpinner = sheetView.findViewById(R.id.customerTypeSpinner);
        serviceTypeSpinner = sheetView.findViewById(R.id.serviceTypeSpinner);
        serviceTypeSpinner.setOnItemSelectedListener(this);

        complaintOriginSpinner = sheetView.findViewById(R.id.complaintOriginSpinner);
        complaintOriginSpinner.setOnItemSelectedListener(this);


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
        zoneName.addAll(zoneName);


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
                complaintTypeModel = realmOperations.fetchComplaintTypeByName("All");

               // subzoneSpinner.setSelection(0);
                complaintTypeSpinner.setSelection(0);
                complaintSubTypeSpinner.setSelection(0);

                setDate();
            }
        });

        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String zoneName = zoneSpinner.getSelectedItem().toString();
               // zoneModel = realmOperations.fetchZoneByName(zoneName);
              //  zoneId = zoneModel.getBUM_BU_ID();
             //   zoneIdValueStr = String.valueOf(zoneId);

                if(zoneName.equalsIgnoreCase("All")){
                    zoneId = 0;
                    zoneIdValueStr = "0";
                }else {
                    zoneModel = realmOperations.fetchZoneByName(zoneName);
                    zoneId = zoneModel.getBUM_BU_ID();
                    zoneIdValueStr = String.valueOf(zoneId);
                }

           /*     subZoneList = realmOperations.fetchSubZoneById(zoneId);

                ArrayList<String> subZone = new ArrayList<>();
                subZone.add("All");
                subZone.addAll(subZoneList);

                subZoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, subZone);
                subZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subzoneSpinner.setAdapter(subZoneAdapter);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* subzoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
*/
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
                if (reallocationModelList != null) {
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
       // getWorkReallocatedData();
        getWorkReallocatedDataWOFilter();

        swipeRefreshLayout.setRefreshing(false);
    }

    private void getWorkReallocatedData() {

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
       /* String params[] = new String[9];

        params[0] = fromDateStr;
        params[1] = toDateStr;
        params[2] = zoneIdValueStr;
        params[3] = subzoneIdValueStr;
        params[4] = wSecStr;
        params[5] = consumerNoStr;
        params[6] = userTagStr;
        params[7] = complaintTypeIdValueStr;
        params[8] = complaintSubTypeIdStr;
*/
        Log.d("check", "" + Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            WorkReallocation workReallocation = new WorkReallocation();
            workReallocation.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.allocatedFilter){
            workAllocationBottomFilterDialog();
            setDate();
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
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

              /*  String paraNames[] = new String[14];

                paraNames[0] = "W_FromDate";
                paraNames[1] = "W_ToDate";
                paraNames[2] = "W_BU";
                paraNames[3] = "W_PC";
                paraNames[4] = "W_Sec";
                paraNames[5] = "w_consumer";
                paraNames[6] = "w_usertag";
                paraNames[7] = "comp_type";
                paraNames[8] = "comp_sub_type";

                paraNames[9] = "SR";
                paraNames[10] = "DMA";
                paraNames[11] = "Source";
                paraNames[12] = "CustomerType";
                paraNames[13] = "LoginUser";*/


                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Complaint_GetWorkCompletionData, params, paraNames);
              //  jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetWorkReallocation, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                reallocationModelList = new ArrayList<>();

                ReallocationResponseDataModel[] enums = gson.fromJson(jsonResponse, ReallocationResponseDataModel[].class);

                if (enums != null && enums.length > 0) {
                    for (ReallocationResponseDataModel model : enums) {
                        complaintNoStr = model.getCOMNO();
                        consumerNo = model.getCOM_SERVICE_NO();
                        complaintDateTimeStr = model.getCOM_COMPDATE();
                        complaintTypeStr = model.getCMTM_NAME();
                        complaintSubTypeStr = model.getOCRM_NAME();
                        consumerNameStr = model.getCONSUMER_NAME();
                        mobileNoStr = model.getMOBILE();
                        addressStr = model.getADDRESS_NO();
                        //workAllocationDateTimeStr = current date
                        tariffStr = model.getTARIFF();
                        statusStr = model.getSTATUS();
                        zoneStr = model.getBUM_BU_NAME();
                        subZoneStr = model.getCIRCLE_NAME();
                        disputeBillMonthYearStr = model.getCOM_YEAR_BILL();
                        priorityStr = model.getPRIORITY();
                        sourceTypeStr = model.getSOURCETYPE();
                        assignedCodeStr = model.getASSIGNEDCODE();
                        assignedNameStr = model.getASSIGNEDNAME();
                        vipName=model.getVIP();
                        com_seq=model.getCOM_SEQ();
                        complaintCode=model.getCOM_NO_TYPE();
                        repeatCall=model.getCOM_CALLS();
                        agging=model.getAGINGOFCOMPLAINT();
                        sla=model.getSLA();
                        latitude=model.getSRM_LATITUDE();
                        longitude=model.getSRM_LONGITUDE();

                        ReallocationResponseDataModel data = new ReallocationResponseDataModel(complaintNoStr, zoneStr, subZoneStr, complaintDateTimeStr,
                                consumerNameStr, addressStr, mobileNoStr, complaintTypeStr, complaintSubTypeStr, consumerNo, tariffStr, statusStr,
                                disputeBillMonthYearStr, priorityStr, sourceTypeStr, assignedCodeStr, assignedNameStr,vipName,com_seq,complaintCode,repeatCall,agging,sla,latitude,longitude);
                        reallocationModelList.add(data);
                    }
                    sheetBehavior.cancel();
                    errorLinear.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    reallocationRecyclerView.setVisibility(View.VISIBLE);
                    reallocationAdapter.addList(reallocationModelList);
                    reallocationRecyclerView.setAdapter(reallocationAdapter);
                    reallocationAdapter.notifyDataSetChanged();

                } else {
                    errorLinear.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    reallocationRecyclerView.setVisibility(View.GONE);

                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();

                String error = e.toString();
                ErrorClass.errorData(mCon, "WorkReallocationActivity", "show button of workreallocation", error);
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

    private void getWorkReallocatedDataWOFilter() {
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


        if (connection.isConnectingToInternet()) {
            WorkReallocation workReallocation = new WorkReallocation();
            workReallocation.execute(params);
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
  /*  @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }*/
}
