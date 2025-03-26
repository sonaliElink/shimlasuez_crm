package elink.suezShimla.water.crm.Complaint.Allocation.Activity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.Allocation.Model.AllocateWorkModel;
import elink.suezShimla.water.crm.Complaint.Reallocation.Adapter.AssignedEmployeeAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Adapter.SetEmployeeAdapter;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SiteEngineerModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class WorkAllocationDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private Context mCon;

    private ActionBar actionBar;
    private BottomSheetDialog setEmployeeDialog, setJobCard;
    private MaterialDialog progress;
    private MaterialButton selectEmployeeButton, jobCardButton, submitButton, calculateButton, clearButton, jobSubmitButton;
    private AppCompatImageView employeeCloseImageView, jobCloseImageView;

    private RadioGroup radioGroup;
    private RadioButton areaRadioButton, departmentRadioButton;
    private AppCompatSpinner selectAreaSpinner, mainPipelineSpinner, serviceLineSpinner, selectEmployeeSpinner, srSpinner, dmaSpinner,actionFormSpinner;

    private TextView allocateButton, selectEmployeeLabel, complaintNumberTextView, consumerNoTextView, dateTimeTextView, consumerNameTextView, contactNoTextView,
            consumerAddressTextView, complaintTypeTextView, complaintSubTypeTextView, workAllocationDateTextView, tariffTextView, statusTypeTextView, zoneTextView,
            subZoneTextView, disputeBillTextView, assignedEmployeeName,vipTextView,requestNumberTextView, repeatCallTextView, agingTextView, slaStatusTextView,priorityTextView;
    private View dividerView;

    private TextInputLayout remarkTextInputLayout, boringChargesInputLayout, securityDepositInputLayout, newPipelineExpenditureInputLayout, visitingChargesInputLayout,
            meterCostInputLayout, bituminousFirstNumberInputLayout, bituminousSecondNumberInputLayout, bituminousTotalInputLayout, reinforceFirstNumberInputLayout,
            reinforceSecondNumberInputLayout, reinforceTotalInputLayout, waterFirstNumberInputLayout, waterSecondNumberInputLayout, waterTotalInputLayout,
            totalAmountInputLayout, dateTextInputLayout, timeTextInputLayout;

    private TextInputEditText remarkEditText, boringChargesEditText, securityDepositEditText, newPipelineExpenditureEditText, visitingChargesEditText, meterCostEditText, bituminousFirstNumberEditText,
            bituminousSecondNumberEditText, bituminousTotalEditText, reinforceFirstNumberEditText, reinforceSecondNumberEditText, reinforceTotalEditText, waterFirstNumberEditText,
            waterSecondNumberEditText, waterTotalEditText, totalAmountEditText, dateEditText, timeEditText;

    private Calendar dateCalendar;
    private TimePickerDialog mTimePicker;

    private ArrayAdapter employeeAdapter;
    private RecyclerView setEmployeeRecyclerView, assignedEmployeeRecycler;
    private SetEmployeeAdapter setEmployeeAdapter;
    private AssignedEmployeeAdapter assignSetEmployeeAdapter;
    private StringBuilder employeeCode;
    private List<SiteEngineerModel> siteEngineerModelList;
    private List<SiteEngineerModel> getSiteEngineerModelList;

    private String remarkStr, dateStr, timeStr, selectAreaStr, mainPipelineStr, serviceLineStr, boringChargesStr, securityDepositStr, newPipelineExpenditureStr,
            visitingChargesStr, meterCostStr, bituminousFirstNumberStr, bituminousSecondNumberStr, bituminousTotalStr, reinforceFirstNumberStr,
            reinforceSecondNumberStr, reinforceTotalStr, waterFirstNumberStr, waterSecondNumberStr, waterTotalStr, totalAmountStr, complaintNoStr, consumerNoStr,
            dateTimeStr,currentDateTimeStr="", consumerNameStr, contactNoStr, addressStr, complaintTypeStr, complaintSubTypeStr, workAllocationDateStr, tariffStr, statusStr,
            zoneStr, subZoneStr, disputeBillStr, timeFormat, AM_PM,vipname, sla_status, aging, repeat_calls,com_seq,complaintCode, selectedEmpCode="", selectedEmpValue,priority;

    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String jsonResponse = "",departmentID="";
    private RealmOperations realmOperations;
    int hour,minute, srId, dmaId;
    SiteEngineerModel siteEngineerModel;

    private ArrayAdapter actionFormArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_allocation_details);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        dateCalendar = Calendar.getInstance();

        complaintNoStr = getIntent().getStringExtra("complaintNo");

        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

       /* actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(complaintNoStr);
        }*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        currentDateTimeStr = simpleDateFormat.format(new Date());
        consumerNoStr = getIntent().getStringExtra("consumerNo");
        dateTimeStr = getIntent().getStringExtra("complaintDateTime");
        consumerNameStr = getIntent().getStringExtra("consumerName");
        contactNoStr = getIntent().getStringExtra("mobileNo");
        addressStr = getIntent().getStringExtra("address");
        complaintTypeStr = getIntent().getStringExtra("complaintType");
        complaintSubTypeStr = getIntent().getStringExtra("complaintSubType");
        tariffStr = getIntent().getStringExtra("tariff");
        statusStr = getIntent().getStringExtra("status");
        zoneStr = getIntent().getStringExtra("zone");
        subZoneStr = getIntent().getStringExtra("subzone");
        disputeBillStr = getIntent().getStringExtra("disputeBillMonthYr");
        vipname=getIntent().getStringExtra("vipname");
        sla_status = getIntent().getStringExtra("sla_status");
        aging = getIntent().getStringExtra("aging");
        repeat_calls = getIntent().getStringExtra("repeat_calls");
        com_seq = getIntent().getStringExtra("com_seq");
        complaintCode = getIntent().getStringExtra("complaintCode");
        priority = getIntent().getStringExtra("priority");

        allocateButton = findViewById(R.id.allocateButton);
        remarkTextInputLayout = findViewById(R.id.remarkTextInputLayout);
        remarkEditText = findViewById(R.id.remarkEditText);
        complaintNumberTextView = findViewById(R.id.complaintNumberTextView);
        consumerNoTextView = findViewById(R.id.consumerNoTextView);
        dateTimeTextView = findViewById(R.id.dateTimeTextView);
        consumerNameTextView = findViewById(R.id.consumerNameTextView);
        consumerNameTextView.setSelected(true);
        contactNoTextView = findViewById(R.id.contactNoTextView);
        consumerAddressTextView = findViewById(R.id.consumerAddressTextView);

        complaintTypeTextView = findViewById(R.id.complaintTypeTextView);
        complaintTypeTextView.setSelected(true);
        complaintSubTypeTextView = findViewById(R.id.complaintSubTypeTextView);
        workAllocationDateTextView = findViewById(R.id.workAllocationDateTextView);
        tariffTextView = findViewById(R.id.tariffTextView);
        statusTypeTextView = findViewById(R.id.statusTypeTextView);
        zoneTextView = findViewById(R.id.zoneTextView);
        subZoneTextView = findViewById(R.id.subZoneTextView);
        priorityTextView = findViewById(R.id.priorityTextView);
//        disputeBillTextView = findViewById(R.id.disputeBillTextView);

        selectEmployeeSpinner = findViewById(R.id.selectEmployeeSpinner);
        srSpinner = findViewById(R.id.srSpinner);
        dmaSpinner = findViewById(R.id.dmaSpinner);


//        dateTextInputLayout = findViewById(R.id.dateTextInputLayout);
//        timeTextInputLayout = findViewById(R.id.timeTextInputLayout);
        remarkTextInputLayout = findViewById(R.id.remarkTextInputLayout);

//        dateEditText = findViewById(R.id.dateEditText);
//        timeEditText = findViewById(R.id.timeEditText);
        remarkEditText = findViewById(R.id.remarkEditText);

        setEmployeeDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));
        setJobCard = new BottomSheetDialog(Objects.requireNonNull(mCon));
        selectEmployeeButton = findViewById(R.id.selectEmployeeButton);
        jobCardButton = findViewById(R.id.jobCardButton);
        selectEmployeeLabel = findViewById(R.id.selectEmployeeLabel);
        assignedEmployeeName = findViewById(R.id.assignedEmployeeName);
        dividerView = findViewById(R.id.dividerView);
        vipTextView = findViewById(R.id.vipTextView);
        repeatCallTextView = findViewById(R.id.repeatCallTextView);
        agingTextView = findViewById(R.id.agingTextView);
        slaStatusTextView = findViewById(R.id.slaStatusTextView);

        setEmployeeRecyclerView = findViewById(R.id.setEmployeeRecyclerView);
        setEmployeeAdapter = new SetEmployeeAdapter(mCon);

        setEmployeeRecyclerView.setHasFixedSize(true);
        setEmployeeRecyclerView.setLayoutManager(new GridLayoutManager(mCon, 2));

        SelectEmployeeDialog();
        //setJobCardDialog();

        complaintNumberTextView.setText(complaintNoStr);
        consumerNoTextView.setText(consumerNoStr);
        dateTimeTextView.setText(dateTimeStr);
        consumerNameTextView.setText(consumerNameStr);
        contactNoTextView.setText(contactNoStr);
        consumerAddressTextView.setText(addressStr);
        complaintTypeTextView.setText(complaintTypeStr);
        complaintSubTypeTextView.setText(complaintSubTypeStr);
        tariffTextView.setText(tariffStr);
        statusTypeTextView.setText(statusStr);
        zoneTextView.setText(zoneStr);
        subZoneTextView.setText(subZoneStr);
        priorityTextView.setText(priority);
//        disputeBillTextView.setText(disputeBillStr);
        vipTextView.setText(vipname);
        repeatCallTextView.setText(repeat_calls);
        slaStatusTextView.setText(sla_status);
        agingTextView.setText(aging);
        departmentID = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEPARTMENTID);

        try {
            departmentID=  new AesAlgorithm().decrypt(departmentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setEmployeeDropdown();


        selectEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmployeeDialog.show();
            }
        });

        jobCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setJobCard.show();
            }
        });

        allocateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // remarkStr = remarkEditText.getText().toString().trim();
              /*  selectedEmpValue = selectEmployeeSpinner.getSelectedItem().toString();
                remarkStr = actionFormSpinner.getSelectedItem().toString();*/
                if(checkValidation()){

                    validateAllocation();
                }
            }
        });


        actionFormSpinner = findViewById(R.id.actionFormSpinner);
     //   actionFormSpinner.setOnItemSelectedListener(this);
        setActionFormropDown();
    }





    private void setActionFormropDown() {
        ArrayList<String> actionFormName = new ArrayList<>();
        actionFormName = realmOperations.fetchActionFormList(complaintCode);
        ArrayList<String>  actionForm = new ArrayList<>();
        actionForm.add("Select");
        actionForm.addAll(actionFormName);

        actionFormArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, actionForm);
        actionFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionFormSpinner.setAdapter(actionFormArrayAdapter);
        actionFormSpinner.setOnItemSelectedListener(this);
    }

    private void setEmployeeDropdown(){
        ArrayList<String> siteEngineerList = new ArrayList<>();
        siteEngineerList = realmOperations.fetchSiteEngineer(departmentID);

        ArrayList<String>  eList = new ArrayList<>();
        eList.add("Select");
        eList.addAll(siteEngineerList);

        employeeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, eList);
        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectEmployeeSpinner.setAdapter(employeeAdapter);
        selectEmployeeSpinner.setOnItemSelectedListener(this);
    }

    private void validateAllocation() {

                String dateTime = dateStr + " " + timeFormat;

            String empcodee = null;
            try {
                // Decrypt EmpCode
                empcodee = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            } catch (Exception e) {
                e.printStackTrace();
            }

                String params[] = new String[12];

                params[0] = complaintNoStr;
                params[1] = consumerNoStr;
                params[2] = currentDateTimeStr;
                params[3] = empcodee;
                params[4] = PreferenceUtil.getMac();
                params[5] = remarkStr;
                params[6] = selectedEmpCode;
                params[7] = selectedEmpValue;
                params[8] = com_seq;
                params[9] = complaintTypeStr;
                params[10] = complaintSubTypeStr;
                params[11] = "A";

                if (connection.isConnectingToInternet()) {
                    AllocateWork allocateWork = new AllocateWork();
                    allocateWork.execute(params);
                } else {
                    Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }

//            else {
//                Toast.makeText(mCon, R.string.select_at_least_one_employee_allocate, Toast.LENGTH_SHORT).show();
//                setEmployeeDialog.show();
//            }
//        }
    }

    private boolean checkValidation() {

        selectedEmpValue = selectEmployeeSpinner.getSelectedItem().toString();
        remarkStr = actionFormSpinner.getSelectedItem().toString();
        if (selectedEmpValue.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, R.string.select_at_least_one_employee_allocate, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (remarkStr.equalsIgnoreCase("Select")) {
                Toast.makeText(mCon, "Please select action form type", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }


        public void SelectEmployeeDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialog_select_employee, null));
        setEmployeeDialog.setContentView(sheetView);
        FrameLayout bottomSheet = setEmployeeDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(Objects.requireNonNull(bottomSheet)).setState(BottomSheetBehavior.STATE_EXPANDED);
        setEmployeeDialog.setCancelable(false);

        employeeCloseImageView = sheetView.findViewById(R.id.employeeCloseImageView);
        submitButton = sheetView.findViewById(R.id.submitButton);
        radioGroup = sheetView.findViewById(R.id.radioGroup);
        areaRadioButton = sheetView.findViewById(R.id.areaRadioButton);
        selectAreaSpinner = sheetView.findViewById(R.id.selectAreaSpinner);
        departmentRadioButton = sheetView.findViewById(R.id.departmentRadioButton);
        assignedEmployeeRecycler = sheetView.findViewById(R.id.assignedEmployeeRecycler);

        assignedEmployeeRecycler.setHasFixedSize(true);
        assignedEmployeeRecycler.setLayoutManager(new LinearLayoutManager(mCon));
        assignSetEmployeeAdapter = new AssignedEmployeeAdapter(mCon);

        siteEngineerModelList = new ArrayList<>();
        siteEngineerModelList = realmOperations.fetchSiteEngineerModel();
        assignSetEmployeeAdapter.addList(siteEngineerModelList);
        assignedEmployeeRecycler.setAdapter(assignSetEmployeeAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAreaStr = selectAreaSpinner.getSelectedItem().toString().trim();
                validateSelectEmployee();
            }
        });

        employeeCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmployeeDialog.cancel();
            }
        });
    }

    // Set Job Dialog
    public void setJobCardDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialog_job_card, null));
        setJobCard.setContentView(sheetView);
        FrameLayout bottomSheet = setJobCard.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(Objects.requireNonNull(bottomSheet)).setState(BottomSheetBehavior.STATE_EXPANDED);
        setJobCard.setCancelable(false);

        jobCloseImageView = sheetView.findViewById(R.id.jobCloseImageView);
        mainPipelineSpinner = sheetView.findViewById(R.id.mainPipelineSpinner);
        serviceLineSpinner = sheetView.findViewById(R.id.serviceLineSpinner);
        calculateButton = sheetView.findViewById(R.id.calculateButton);
        jobSubmitButton = sheetView.findViewById(R.id.jobSubmitButton);

        boringChargesInputLayout = sheetView.findViewById(R.id.boringChargesInputLayout);
        securityDepositInputLayout = sheetView.findViewById(R.id.securityDepositInputLayout);
        newPipelineExpenditureInputLayout = sheetView.findViewById(R.id.newPipelineExpenditureInputLayout);
        visitingChargesInputLayout = sheetView.findViewById(R.id.visitingChargesInputLayout);
        meterCostInputLayout = sheetView.findViewById(R.id.meterCostInputLayout);
        bituminousFirstNumberInputLayout = sheetView.findViewById(R.id.bituminousFirstNumberInputLayout);
        bituminousSecondNumberInputLayout = sheetView.findViewById(R.id.bituminousSecondNumberInputLayout);
        bituminousTotalInputLayout = sheetView.findViewById(R.id.bituminousTotalInputLayout);
        reinforceFirstNumberInputLayout = sheetView.findViewById(R.id.reinforceFirstNumberInputLayout);
        reinforceSecondNumberInputLayout = sheetView.findViewById(R.id.reinforceSecondNumberInputLayout);
        reinforceTotalInputLayout = sheetView.findViewById(R.id.reinforceTotalInputLayout);
        waterFirstNumberInputLayout = sheetView.findViewById(R.id.waterFirstNumberInputLayout);
        waterSecondNumberInputLayout = sheetView.findViewById(R.id.waterSecondNumberInputLayout);
        waterTotalInputLayout = sheetView.findViewById(R.id.waterTotalInputLayout);
        totalAmountInputLayout = sheetView.findViewById(R.id.totalAmountInputLayout);

        boringChargesEditText = sheetView.findViewById(R.id.boringChargesEditText);
        securityDepositEditText = sheetView.findViewById(R.id.securityDepositEditText);
        newPipelineExpenditureEditText = sheetView.findViewById(R.id.newPipelineExpenditureEditText);
        visitingChargesEditText = sheetView.findViewById(R.id.visitingChargesEditText);
        meterCostEditText = sheetView.findViewById(R.id.meterCostEditText);
        bituminousFirstNumberEditText = sheetView.findViewById(R.id.bituminousFirstNumberEditText);
        bituminousSecondNumberEditText = sheetView.findViewById(R.id.bituminousSecondNumberEditText);
        bituminousTotalEditText = sheetView.findViewById(R.id.bituminousTotalEditText);
        reinforceFirstNumberEditText = sheetView.findViewById(R.id.reinforceFirstNumberEditText);
        reinforceSecondNumberEditText = sheetView.findViewById(R.id.reinforceSecondNumberEditText);
        reinforceTotalEditText = sheetView.findViewById(R.id.reinforceTotalEditText);
        waterFirstNumberEditText = sheetView.findViewById(R.id.waterFirstNumberEditText);
        waterSecondNumberEditText = sheetView.findViewById(R.id.waterSecondNumberEditText);
        waterTotalEditText = sheetView.findViewById(R.id.waterTotalEditText);
        totalAmountEditText = sheetView.findViewById(R.id.totalAmountEditText);

        requestNumberTextView.setText(complaintNoStr);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bituminousFirstNumberStr = bituminousFirstNumberEditText.getText().toString().trim();
                bituminousSecondNumberStr = bituminousSecondNumberEditText.getText().toString().trim();
                bituminousTotalStr = bituminousTotalEditText.getText().toString().trim();
                reinforceFirstNumberStr = reinforceFirstNumberEditText.getText().toString().trim();
                reinforceSecondNumberStr = reinforceSecondNumberEditText.getText().toString().trim();
                reinforceTotalStr = reinforceTotalEditText.getText().toString().trim();
                waterFirstNumberStr = waterFirstNumberEditText.getText().toString().trim();
                waterSecondNumberStr = waterSecondNumberEditText.getText().toString().trim();
                waterTotalStr = waterTotalEditText.getText().toString().trim();

                validateCalculate();
            }
        });

        jobSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPipelineStr = mainPipelineSpinner.getSelectedItem().toString().trim();
                serviceLineStr = serviceLineSpinner.getSelectedItem().toString().trim();
                boringChargesStr = boringChargesEditText.getText().toString().trim();
                securityDepositStr = securityDepositEditText.getText().toString().trim();
                newPipelineExpenditureStr = newPipelineExpenditureEditText.getText().toString().trim();
                visitingChargesStr = visitingChargesEditText.getText().toString().trim();
                meterCostStr = meterCostEditText.getText().toString().trim();
                totalAmountStr = totalAmountEditText.getText().toString().trim();

                validateJobSubmit();
            }
        });

        jobCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setJobCard.cancel();
            }
        });
    }

    // Validate select Employee
    private void validateSelectEmployee() {

        boolean isValidRadioGroup = false, isValidArea = false;

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            areaRadioButton.setError(getResources().getString(R.string.select_options));
            departmentRadioButton.setError(getResources().getString(R.string.select_options));
        } else {
            isValidRadioGroup = true;
            areaRadioButton.setError(null);
            departmentRadioButton.setError(null);
        }


        if (selectAreaStr.equalsIgnoreCase("Select Area*")) {
            TextView view = (TextView) selectAreaSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidArea = true;
            TextView view = (TextView) selectAreaSpinner.getSelectedView();
            view.setError(null);
        }

        if (isValidRadioGroup && isValidArea) {
            if (realmOperations.isSiteEngineerChecked() > 0) {
                setEmployeeRecycler();

                SiteEngineerModel siteEngineerModel = realmOperations.getSiteEngineerLastId();
                employeeCode = new StringBuilder();
                for (SiteEngineerModel model : getSiteEngineerModelList) {

                    if (siteEngineerModel.getEMPLOYEE_CODE().equals(model.getEMPLOYEE_CODE())) {
                        employeeCode.append(model.getEMPLOYEE_CODE());
                    } else {
                        employeeCode.append(model.getEMPLOYEE_CODE()).append(", ");
                    }
                }
            } else {
                Toast.makeText(mCon, R.string.select_at_least_one_employee_reallocate, Toast.LENGTH_SHORT).show();
                /*Toast toast = Toast.makeText(mCon, R.string.select_at_least_one_employee_reallocate, Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.recycler_corner);
                *//*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*//*
                toast.show();*/
            }
        }
    }

    // Validate Calculate Job
    private void validateCalculate() {
        boolean isValidBituminousFirstNumber = false, isValidBituminousSecondNumber = false, isValidBituminousTotal = false, isValidReinforceFirstNumber = false,
                isValidReinforceSecondNumber = false, isValidReinforceTotal = false, isValidWaterFirstNumber = false, isValidWaterSecondNumber = false, isValidWaterTotal = false;

        if (TextUtils.isEmpty(bituminousFirstNumberStr)) {
            bituminousFirstNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            bituminousFirstNumberInputLayout.setError(null);
            isValidBituminousFirstNumber = true;
        }

        if (TextUtils.isEmpty(bituminousSecondNumberStr)) {
            bituminousSecondNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            bituminousSecondNumberInputLayout.setError(null);
            isValidBituminousSecondNumber = true;
        }

        if (TextUtils.isEmpty(bituminousTotalStr)) {
            bituminousTotalInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            bituminousTotalInputLayout.setError(null);
            isValidBituminousTotal = true;
        }

        if (TextUtils.isEmpty(reinforceFirstNumberStr)) {
            reinforceFirstNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            reinforceFirstNumberInputLayout.setError(null);
            isValidReinforceFirstNumber = true;
        }

        if (TextUtils.isEmpty(reinforceSecondNumberStr)) {
            reinforceSecondNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            reinforceSecondNumberInputLayout.setError(null);
            isValidReinforceSecondNumber = true;
        }

        if (TextUtils.isEmpty(reinforceTotalStr)) {
            reinforceTotalInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            reinforceTotalInputLayout.setError(null);
            isValidReinforceTotal = true;
        }

        if (TextUtils.isEmpty(waterFirstNumberStr)) {
            waterFirstNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            waterFirstNumberInputLayout.setError(null);
            isValidWaterFirstNumber = true;
        }

        if (TextUtils.isEmpty(waterSecondNumberStr)) {
            waterSecondNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            waterSecondNumberInputLayout.setError(null);
            isValidWaterSecondNumber = true;
        }

        if (TextUtils.isEmpty(waterTotalStr)) {
            waterTotalInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            waterTotalInputLayout.setError(null);
            isValidWaterTotal = true;
        }

        if (isValidBituminousFirstNumber && isValidBituminousSecondNumber && isValidBituminousTotal && isValidReinforceFirstNumber && isValidReinforceSecondNumber &&
                isValidReinforceTotal && isValidWaterFirstNumber && isValidWaterSecondNumber && isValidWaterTotal) {
            totalAmountEditText.setText("0");
        }
    }

    // Validate Job Submit
    private void validateJobSubmit() {
        boolean isValidMainPipeline = false, isValidServiceLine = false, isValidBoringCharges = false, isValidSecurityDeposit = false, isValidNewPipelineExpenditure = false,
                isValidVisitingCharges = false, isValidMeterCost = false, isValidTotalAmount = false;


        if (mainPipelineStr.equalsIgnoreCase("Main Pipeline of Municipal Corporation*")) {
            TextView view = (TextView) mainPipelineSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidMainPipeline = true;
            TextView view = (TextView) mainPipelineSpinner.getSelectedView();
            view.setError(null);
        }

        if (serviceLineStr.equalsIgnoreCase("Service Line of Municipal Corporation*")) {
            TextView view = (TextView) serviceLineSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidServiceLine = true;
            TextView view = (TextView) serviceLineSpinner.getSelectedView();
            view.setError(null);
        }

        if (TextUtils.isEmpty(boringChargesStr)) {
            boringChargesInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            boringChargesInputLayout.setError(null);
            isValidBoringCharges = true;
        }

        if (TextUtils.isEmpty(securityDepositStr)) {
            securityDepositInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            securityDepositInputLayout.setError(null);
            isValidSecurityDeposit = true;
        }

        if (TextUtils.isEmpty(newPipelineExpenditureStr)) {
            newPipelineExpenditureInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            newPipelineExpenditureInputLayout.setError(null);
            isValidNewPipelineExpenditure = true;
        }

        if (TextUtils.isEmpty(visitingChargesStr)) {
            visitingChargesInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            visitingChargesInputLayout.setError(null);
            isValidVisitingCharges = true;
        }

        if (TextUtils.isEmpty(meterCostStr)) {
            meterCostInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            meterCostInputLayout.setError(null);
            isValidMeterCost = true;
        }

        if (TextUtils.isEmpty(totalAmountStr)) {
            totalAmountInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            totalAmountInputLayout.setError(null);
            isValidTotalAmount = true;
        }

        if (isValidMainPipeline && isValidServiceLine && isValidBoringCharges && isValidSecurityDeposit && isValidNewPipelineExpenditure && isValidVisitingCharges &&
                isValidMeterCost && isValidTotalAmount) {
            setJobCard.cancel();
        }

    }

    // Load Recycler Data
    private void setEmployeeRecycler() {
        getSiteEngineerModelList = new ArrayList<>();
        getSiteEngineerModelList = realmOperations.fetchSiteEngineerByCheck();
        setEmployeeAdapter.addList(getSiteEngineerModelList);
        setEmployeeRecyclerView.setAdapter(setEmployeeAdapter);
        setEmployeeRecyclerView.setVisibility(View.VISIBLE);
        selectEmployeeLabel.setVisibility(View.VISIBLE);
        dividerView.setVisibility(View.VISIBLE);
        //assignedEmployeeName.setVisibility(View.GONE);
        setEmployeeDialog.cancel();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.selectEmployeeSpinner: {
                String value = selectEmployeeSpinner.getSelectedItem().toString();
                if(value.equalsIgnoreCase("Select")){
                    selectedEmpCode = "";
                }else {
                    String selectEmployee = selectEmployeeSpinner.getSelectedItem().toString();

                    siteEngineerModel = realmOperations.fetchSiteEngineerName(selectEmployee);
                    String selectedEmpId = String.valueOf(siteEngineerModel.getEMPLOYEE_CODE());

                    selectedEmpCode = String.valueOf(selectedEmpId);
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.actionFormSpinner: {
                String actionFormValue = actionFormSpinner.getSelectedItem().toString();
                if (actionFormValue.equalsIgnoreCase("Select")) {
                 //   Toast.makeText(mCon, "Selcte", Toast.LENGTH_SHORT).show();
                } else {
                 //   Toast.makeText(mCon, ""+actionFormValue, Toast.LENGTH_SHORT).show();

                }
            }
            break;


            default:
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class AllocateWork extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {

            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .autoDismiss(false)
                    .progress(true, 0)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[12];

                paraName[0] = "com_no";
                paraName[1] = "w_consumer";
                paraName[2] = "W_Wadt";
                paraName[3] = "EmpCode";
                paraName[4] = "IP_Address";
                paraName[5] = "Remarks";
                paraName[6] = "empid";
                paraName[7] = "empName";
                paraName[8] = "com_seq";
                paraName[9] = "ComplaintType";
                paraName[10] = "ComplaintSubType";
                paraName[11] = "ActionFrom";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.WorkAllocation, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                AllocateWorkModel enums = gson.fromJson(jsonResponse, AllocateWorkModel.class);
                if (enums.getDiv_Cmsg() != null && !enums.getDiv_Cmsg().equals("")) {
                    int count = Integer.parseInt(enums.getCount());
                    if (count > 0) {
                        Toast.makeText(mCon, R.string.work_allocated_successfully, Toast.LENGTH_LONG).show();
                        /*startActivity(new Intent(mCon, WorkAllocationActivity.class));*/
                        finish();
                    } else {
                        Toast.makeText(mCon, enums.getDiv_Cmsg(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mCon, enums.getDiv_CError(), Toast.LENGTH_LONG).show();
                }
                finish();
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "WorkAllocationDetailsActivity", "click allocateButton", error);
            }
            finish();
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
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }
}
