package elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Adapter.SetEmployeeAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Model.SetEmployeeModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SiteEngineerModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Model.NCAllocateWorkModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Adapter.NCAssignedEmployeeAdapter;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class NCWorkReallocationDetailsActivity extends AppCompatActivity {
    private Context mCon;
    private RealmOperations realmOperations;
    private MaterialDialog progress;

    private ActionBar actionBar;
    private BottomSheetDialog setEmployeeDialog, setJobCard;
    private MaterialButton selectEmployeeButton, jobCardButton, submitButton, calculateButton, clearButton, jobSubmitButton;
    private AppCompatImageView employeeCloseImageView, jobCloseImageView;

    private RadioGroup radioGroup;
    private RadioButton areaRadioButton, departmentRadioButton;
    private AppCompatSpinner selectAreaSpinner, mainPipelineSpinner, serviceLineSpinner;

    private TextView reAllocateTextView, selectEmployeeLabel, complaintNumberTextView, consumerNoTextView, dateTimeTextView, consumerNameTextView, contactNoTextView,
            consumerAddressTextView, complaintTypeTextView, complaintSubTypeTextView, tariffTextView, statusTypeTextView,
            zoneTextView, subZoneTextView, disputeBillTextView, priorityTextView, sourceTypeTextView, assignedEmployeeName,vipTextView;

    private Calendar dateCalendar;
    private TimePickerDialog mTimePicker;

    private TextInputLayout boringChargesInputLayout, securityDepositInputLayout, newPipelineExpenditureInputLayout, visitingChargesInputLayout, meterCostInputLayout,
            bituminousFirstNumberInputLayout, bituminousSecondNumberInputLayout, bituminousTotalInputLayout, reinforceFirstNumberInputLayout,
            reinforceSecondNumberInputLayout, reinforceTotalInputLayout, waterFirstNumberInputLayout, waterSecondNumberInputLayout, waterTotalInputLayout,
            totalAmountInputLayout, dateTextInputLayout, timeTextInputLayout, remarkTextInputLayout;

    private TextInputEditText boringChargesEditText, securityDepositEditText, newPipelineExpenditureEditText, visitingChargesEditText, meterCostEditText,
            bituminousFirstNumberEditText, bituminousSecondNumberEditText, bituminousTotalEditText, reinforceFirstNumberEditText, reinforceSecondNumberEditText,
            reinforceTotalEditText, waterFirstNumberEditText, waterSecondNumberEditText, waterTotalEditText, totalAmountEditText, dateEditText, timeEditText,
            remarkEditText;

    private RecyclerView setEmployeeRecyclerView, assignedEmployeeRecycler;
    private SetEmployeeAdapter setEmployeeAdapter;
    private NCAssignedEmployeeAdapter ncassignSetEmployeeAdapter;
    private List<SetEmployeeModel> setEmployeeModelList;
    private List<SiteEngineerModel> siteEngineerModelList, getSiteEngineerModelList;

    private String selectAreaStr, mainPipelineStr, serviceLineStr, boringChargesStr, securityDepositStr, newPipelineExpenditureStr, visitingChargesStr,
            meterCostStr, bituminousFirstNumberStr, bituminousSecondNumberStr, bituminousTotalStr, reinforceFirstNumberStr, reinforceSecondNumberStr, remarkStr,
            reinforceTotalStr, waterFirstNumberStr, waterSecondNumberStr, waterTotalStr, totalAmountStr, complaintNoStr, consumerNoStr, dateTimeStr,
            consumerNameStr, contactNoStr, addressStr, complaintTypeStr, complaintSubTypeStr, workAllocationDateStr, tariffStr, statusStr, zoneStr,
            subZoneStr, disputeBillStr, priorityStr, sourceTypeStr, dateStr, timeStr, assignedCodeStr, assignedNameStr, employeeStr, timeFormat, AM_PM,vipName;

    private StringBuilder employeeCode;

    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String jsonResponse = "",consumerNo="000000000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncwork_reallocation_details);
        mCon = this;
        realmOperations = new RealmOperations(mCon);

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        dateCalendar = Calendar.getInstance();

        complaintNoStr = getIntent().getStringExtra("complaintNo");
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(complaintNoStr);
        }
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
        priorityStr = getIntent().getStringExtra("priority");
        assignedCodeStr = getIntent().getStringExtra("assignedCode");
        assignedNameStr = getIntent().getStringExtra("assignedName");
        vipName = getIntent().getStringExtra("vipName");

        complaintNumberTextView = findViewById(R.id.complaintNumberTextView);
        consumerNoTextView = findViewById(R.id.consumerNoTextView);
        dateTimeTextView = findViewById(R.id.dateTimeTextView);
        consumerNameTextView = findViewById(R.id.consumerNameTextView);
        contactNoTextView = findViewById(R.id.contactNoTextView);
        consumerAddressTextView = findViewById(R.id.consumerAddressTextView);
        complaintTypeTextView = findViewById(R.id.complaintTypeTextView);
        complaintSubTypeTextView = findViewById(R.id.complaintSubTypeTextView);
        tariffTextView = findViewById(R.id.tariffTextView);
        statusTypeTextView = findViewById(R.id.statusTypeTextView);
        zoneTextView = findViewById(R.id.zoneTextView);
        subZoneTextView = findViewById(R.id.subZoneTextView);
        disputeBillTextView = findViewById(R.id.disputeBillTextView);
        priorityTextView = findViewById(R.id.priorityTextView);
        sourceTypeTextView = findViewById(R.id.sourceTypeTextView);
        reAllocateTextView = findViewById(R.id.reAllocateTextView);

        dateTextInputLayout = findViewById(R.id.dateTextInputLayout);
        timeTextInputLayout = findViewById(R.id.timeTextInputLayout);
        remarkTextInputLayout = findViewById(R.id.remarkTextInputLayout);

        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        remarkEditText = findViewById(R.id.remarkEditText);

        setEmployeeDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));
        //setJobCard = new BottomSheetDialog(Objects.requireNonNull(mCon));
        selectEmployeeButton = findViewById(R.id.selectEmployeeButton);
        //jobCardButton = findViewById(R.id.jobCardButton);
        selectEmployeeLabel = findViewById(R.id.selectEmployeeLabel);
        assignedEmployeeName = findViewById(R.id.assignedEmployeeName);
        vipTextView = findViewById(R.id.vipTextView);

        setEmployeeRecyclerView = findViewById(R.id.setEmployeeRecyclerView);
        setEmployeeAdapter = new SetEmployeeAdapter(mCon);

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
        disputeBillTextView.setText(disputeBillStr);
        priorityTextView.setText(priorityStr);
        sourceTypeTextView.setText(sourceTypeStr);
        assignedEmployeeName.setText(assignedNameStr);
        vipTextView.setText(vipName);

        final DatePickerDialog.OnDateSetListener Date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                dateCalendar.set(Calendar.YEAR, year);
                dateCalendar.set(Calendar.MONTH, monthOfYear);
                dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateCalendar();
            }
        };

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, Date, dateCalendar
                        .get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH),
                        dateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mTime = Calendar.getInstance();
                Calendar cTime = Calendar.getInstance();
                int hour = mTime.get(Calendar.HOUR);
                int minute = mTime.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(mCon, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        SimpleDateFormat format;
                        format = new SimpleDateFormat("HH:mm");
                        cTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        cTime.set(Calendar.MINUTE, selectedMinute);
                        Date date = cTime.getTime();
                        String dateResult = format.format(date);

                        timeFormat = dateResult + ":00";

                        if (selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }

                        SimpleDateFormat dateFormat;
                        if (DateFormat.is24HourFormat(mCon)) {
                            dateFormat = new SimpleDateFormat("HH:mm ");
                        } else {
                            dateFormat = new SimpleDateFormat("hh:mm ");
                        }
                        mTime.set(Calendar.HOUR, selectedHour); // Or HOUR_OF_DAY
                        mTime.set(Calendar.MINUTE, selectedMinute);
                        Date d = mTime.getTime();
                        String dResult = dateFormat.format(d);

                        timeEditText.setText(dResult + AM_PM);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });

        selectEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmployeeDialog.show();
            }
        });

      /*  jobCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setJobCard.show();
            }
        });
*/
        reAllocateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateStr = dateEditText.getText().toString().trim();
                timeStr = timeEditText.getText().toString().trim();
                remarkStr = remarkEditText.getText().toString().trim();

                validateData();
            }
        });
    }

    private void updateDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA.getDefault());

        dateEditText.setText(sdf.format(dateCalendar.getTime()));
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

        ncassignSetEmployeeAdapter = new NCAssignedEmployeeAdapter(mCon);
        siteEngineerModelList = new ArrayList<>();

        siteEngineerModelList = realmOperations.fetchSiteEngineerModel();
        ncassignSetEmployeeAdapter.addList(siteEngineerModelList);
        assignedEmployeeRecycler.setAdapter(ncassignSetEmployeeAdapter);

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
  /*  public void setJobCardDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialog_job_card, null));
        setJobCard.setContentView(sheetView);
        FrameLayout bottomSheet = setJobCard.findViewById(android.support.design.R.id.design_bottom_sheet);
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
    }*/

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
        assignedEmployeeName.setVisibility(View.GONE);
        setEmployeeDialog.cancel();
    }

    private void validateData() {
        boolean isValidDate = false, isValidTime = false, isValidRemark = false;

        if (TextUtils.isEmpty(dateStr)) {
            dateTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            dateTextInputLayout.setError(null);
            isValidDate = true;
        }

        if (TextUtils.isEmpty(timeStr)) {
            timeTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            timeTextInputLayout.setError(null);
            isValidTime = true;
        }

        if (TextUtils.isEmpty(remarkStr)) {
            remarkTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            remarkTextInputLayout.setError(null);
            isValidRemark = true;
        }

        if (isValidDate && isValidTime && isValidRemark) {

            String dateTime = dateStr + " " + timeFormat;

            String empCode = "";

            if (assignedEmployeeName.getVisibility() == View.VISIBLE) {
                empCode = assignedCodeStr;
            } else if (setEmployeeRecyclerView.getVisibility() == View.VISIBLE) {
                empCode = String.valueOf(employeeCode);
            }

            Log.d("check", "" + timeFormat + ", " + empCode);


            String sqNo=complaintNoStr.replace("/", "");

            String empcode2 = null;
            try {
                // Decrypt EmpCode
                empcode2 = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String params[] = new String[12];

            params[0] = empCode;
            params[1] = complaintNoStr;
            params[2] = remarkStr;
            params[3] = dateStr;
            params[4] = complaintNoStr.substring(0, 2);
            params[5] = sqNo.substring(2,sqNo.length());
            params[6] = consumerNo;
            params[7] = complaintTypeStr;
            params[8] = complaintSubTypeStr;
            params[9] = empcode2;
            params[10] = "123456";
            params[11] = "ReAllocation";

            if (connection.isConnectingToInternet()) {
                ReallocateWork reallocateWork = new ReallocateWork();
                reallocateWork.execute(params);
            } else {
                Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ReallocateWork extends AsyncTask<String, Void, Void> {
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
                paraName[0] = "EmpId";
                paraName[1] = "ComplaintNo";
                paraName[2] = "Remarks";
                paraName[3] = "WADate";
                paraName[4] = "ComplaintType";
                paraName[5] = "W_sq";
                paraName[6] = "CompRefNo";
                paraName[7] = "CompType";
                paraName[8] = "CompSubType";
                paraName[9] = "Emp_code";
                paraName[10] = "IP";
                paraName[11] = "Type";
                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.N_Complaint_WorkAllocation, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                NCAllocateWorkModel enums = gson.fromJson(jsonResponse, NCAllocateWorkModel.class);
                if (enums.getDiv_Cmsg() != null && !enums.getDiv_Cmsg().equals("")) {
                    if (enums.getDiv_Cmsg().equals("True")) {
                        Toast.makeText(mCon, R.string.work_reallocated_successfully, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(mCon, enums.getDiv_Cmsg(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mCon, enums.getDiv_CError(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCWorkReallocationDetailsActivity", "ReAllocate TextView click", error);
            }
            progress.dismiss();
        }
    }

}
