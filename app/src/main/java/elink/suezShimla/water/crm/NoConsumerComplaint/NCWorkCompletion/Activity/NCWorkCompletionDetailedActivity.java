package elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Activity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.FinishActionModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Model.NCUpdateWorkResponseModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class NCWorkCompletionDetailedActivity extends AppCompatActivity {
    private Context mCon;
    private BottomSheetDialog sheetBehavior;
    private CheckBox checkbox;
    private MaterialButton submitButton;
    private AppCompatImageView closeImageView;

    private String jsonResponse = "", jsonSMSResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;

    private AppCompatSpinner finishActionSpinner, observationSpinner;
    private FinishActionModel finishActionModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private ArrayList<String> actionModelsList;
    private ArrayList<String> observationList;
    private ArrayAdapter finishActionAdapter, observationAdapter;

    private MaterialDialog progress, smsProgress;
    private TextInputLayout dateTextInputLayout, timeTextInputLayout, remarkTextInputLayout, otpTextInputLayout, reasonTextInputLayout;
    private TextInputEditText dateEditText, timeEditText, remarkEditText, otpEditText, reasonEditText;
    private Calendar dateCalendar;
    private TimePickerDialog mTimePicker;

    private TextView complaintNumberTextView, consumerNoTextView, dateTimeTextView, consumerNameTextView, contactNoTextView, consumerAddressTextView,
            priorityTextView, complaintSubTypeTextView, workAllocationDateTextView, tariffTextView, statusTypeTextView, zoneTextView, subZoneTextView,
            disputeBillTextView, submitTextView, otpLabelTextView, reasonLabelTextView,vipTextView;

    private String consumerNoStr, complaintNoStr, complaintWorkAllocateDateStr, complaintDateStr, complaintSubTypeStr, tariffStr, statusStr,
            consumerNameStr, addressStr, priorityStr, contactNoStr, zoneStr, subZoneStr, disputeBillMonthYrStr, meterNoStr, meterTransIdStr, address1Str, address2Str,
            address3Str, pincodeStr, complaintTypeStr, completionDateStr, completionTimeStr, remarkStr, actionStr, actionIdStr, workCompletionDateTime,
            observationStr, observationIdStr, searchForStr, otpStr, otpReasonStr, timeFormat, AM_PM, OTP, userType, filterIdStr,vipName,allocateDate;

    private int actionId, observationId, filterId;
    private ZoneModel zoneModel;
    private SubZoneModel subZoneModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_ncwork_completion_detailed);
        mCon = this;

        dateCalendar = Calendar.getInstance();

        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        consumerNoStr = getIntent().getStringExtra("consumerNo");
        complaintNoStr = getIntent().getStringExtra("complaintNo");
        complaintWorkAllocateDateStr = getIntent().getStringExtra("complaintWorkAllocateDate");
        complaintDateStr = getIntent().getStringExtra("complaintDate");
        complaintSubTypeStr = getIntent().getStringExtra("complaintSubType");
        tariffStr = getIntent().getStringExtra("tariff");
        statusStr = getIntent().getStringExtra("status");
        consumerNameStr = getIntent().getStringExtra("consumerName");
        addressStr = getIntent().getStringExtra("address");
        contactNoStr = getIntent().getStringExtra("contactNo");
        zoneStr = getIntent().getStringExtra("zone");
        subZoneStr = getIntent().getStringExtra("subZone");
        disputeBillMonthYrStr = getIntent().getStringExtra("disputeBillMonthYr");
        complaintTypeStr = getIntent().getStringExtra("complaintType");
        searchForStr = getIntent().getStringExtra("searchForStr");
        vipName = getIntent().getStringExtra("vipName");
        allocateDate = getIntent().getStringExtra("allocateDate");
        priorityStr = getIntent().getStringExtra("priorityStr");


        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_otp, null));
        sheetBehavior.setContentView(sheetView);
        sheetBehavior.setCancelable(false);
        sheetBehavior.setCanceledOnTouchOutside(false);

        checkbox = sheetView.findViewById(R.id.checkbox);

        closeImageView = sheetView.findViewById(R.id.closeImageView);
        reasonLabelTextView = sheetView.findViewById(R.id.reasonLabelTextView);
        submitButton = sheetView.findViewById(R.id.submitButton);

        otpTextInputLayout = sheetView.findViewById(R.id.otpTextInputLayout);
        reasonTextInputLayout = sheetView.findViewById(R.id.reasonTextInputLayout);

        otpEditText = sheetView.findViewById(R.id.otpEditText);
        reasonEditText = sheetView.findViewById(R.id.reasonEditText);

        complaintNumberTextView = findViewById(R.id.complaintNumberTextView);
        consumerNoTextView = findViewById(R.id.consumerNoTextView);
        dateTimeTextView = findViewById(R.id.dateTimeTextView);
        consumerNameTextView = findViewById(R.id.consumerNameTextView);
        contactNoTextView = findViewById(R.id.contactNoTextView);
        consumerAddressTextView = findViewById(R.id.consumerAddressTextView);
        priorityTextView = findViewById(R.id.priorityTextView);
        complaintSubTypeTextView = findViewById(R.id.complaintSubTypeTextView);
        workAllocationDateTextView = findViewById(R.id.workAllocationDateTextView);
        tariffTextView = findViewById(R.id.tariffTextView);
        statusTypeTextView = findViewById(R.id.statusTypeTextView);
        zoneTextView = findViewById(R.id.zoneTextView);
        subZoneTextView = findViewById(R.id.subZoneTextView);
        disputeBillTextView = findViewById(R.id.disputeBillTextView);
        submitTextView = findViewById(R.id.submitTextView);

        dateTextInputLayout = findViewById(R.id.dateTextInputLayout);
        timeTextInputLayout = findViewById(R.id.timeTextInputLayout);
        remarkTextInputLayout = findViewById(R.id.remarkTextInputLayout);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        remarkEditText = findViewById(R.id.remarkEditText);

        finishActionSpinner = findViewById(R.id.finishActionSpinner);
        observationSpinner = findViewById(R.id.observationSpinner);
        vipTextView = findViewById(R.id.vipTextView);

        complaintNumberTextView.setText(complaintNoStr);
        consumerNoTextView.setText(consumerNoStr);
        dateTimeTextView.setText(complaintDateStr);
        consumerNameTextView.setText(consumerNameStr);
        consumerAddressTextView.setText(addressStr);
        priorityTextView.setText(priorityStr);
        complaintSubTypeTextView.setText(complaintSubTypeStr);
        workAllocationDateTextView.setText(complaintWorkAllocateDateStr);
        tariffTextView.setText(tariffStr);
        statusTypeTextView.setText(statusStr);
        zoneTextView.setText(zoneStr);
        subZoneTextView.setText(subZoneStr);
        contactNoTextView.setText(contactNoStr);
        disputeBillTextView.setText(disputeBillMonthYrStr);
        vipTextView.setText(vipName);

        actionModelsList = realmOperations.fetchFinishActionNC();
        finishActionAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, actionModelsList);
        finishActionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        finishActionSpinner.setAdapter(finishActionAdapter);

        String siteEng = null;
        try {
            siteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (siteEng != null) {
            if (siteEng.equals("SiteEng")) {
                userType = "Android";
            } else {
                userType = "Web";
            }
        } else {
            userType = "Web";
        }

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
                datePickerDialog.getDatePicker().setMinDate(-1);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
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

                        // timeFormat = dateResult;

                        // For 12 hour format use this code
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

                        timeFormat = dResult + AM_PM;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });

        finishActionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actionStr = finishActionSpinner.getSelectedItem().toString().trim();
                finishActionModel = realmOperations.fetchFinishActionByName(actionStr);

                filterIdStr = finishActionModel.getFILTER();
                filterId = Integer.parseInt(filterIdStr);

                actionId = finishActionModel.getCSCM_ID();
                actionIdStr = String.valueOf(actionId);

                observationList = realmOperations.fetchComplaintSubType(filterId);
                observationAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, observationList);
                observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                observationSpinner.setAdapter(observationAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        observationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                observationStr = observationSpinner.getSelectedItem().toString().trim();
                if (observationStr.equals("--Select--")) {
                    observationIdStr = "0";
                } else {
                    complaintSubTypeModel = realmOperations.fetchComplaintSubTypeByName(observationStr);
                    observationId = complaintSubTypeModel.getCOMPLAINTSUBTYPEID();
                    observationIdStr = String.valueOf(observationId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completionDateStr = dateEditText.getText().toString().trim();
                completionTimeStr = timeEditText.getText().toString().trim();
                remarkStr = remarkEditText.getText().toString().trim();

                validate();
            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    otpTextInputLayout.setVisibility(View.GONE);

                    reasonLabelTextView.setVisibility(View.VISIBLE);
                    reasonTextInputLayout.setVisibility(View.VISIBLE);

                } else {
                    otpTextInputLayout.setVisibility(View.VISIBLE);

                    reasonLabelTextView.setVisibility(View.GONE);
                    reasonTextInputLayout.setVisibility(View.GONE);
                }
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.cancel();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpStr = otpEditText.getText().toString().trim();
                otpReasonStr = reasonEditText.getText().toString().trim();

                validateSubmitOtp();
            }
        });

        // Randomly generated OTP Code

        // OTP = getRandomNumberString();
        OTP = "123456";
        Log.d("check", "" + OTP);
    }

    @SuppressLint("DefaultLocale")
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private void validateSubmitOtp() {
        boolean isValidOtp = false;

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (otpTextInputLayout.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(otpStr)) {
                otpTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            } else if (otpStr.equals(OTP)) {
                otpTextInputLayout.setError(null);
                isValidOtp = true;
            } else {
                Toast.makeText(mCon, R.string.invalid_otp, Toast.LENGTH_SHORT).show();
            }
        } else if (reasonTextInputLayout.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(otpReasonStr)) {
                reasonTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            } else {
                reasonTextInputLayout.setError(null);
                otpStr = "NO-OTP";
                isValidOtp = true;
            }
        }

        if (isValidOtp) {
            workCompletionDateTime = completionDateStr + " " + timeFormat;
            zoneModel = realmOperations.fetchZoneByName(zoneStr.substring(zoneStr.lastIndexOf("-") + 1));
            String zoneId= String.valueOf(zoneModel.getBUM_BU_ID());
            subZoneModel=realmOperations.fetchSubZoneByName(subZoneStr);
            String subZoneId=String.valueOf(subZoneModel.getPCM_PC_ID());

            String param[] = new String[15];
            param[0] = complaintSubTypeStr;
            param[1] = complaintNoStr;
            if(actionStr.equals("REJECT")){
                param[2] = "1";
            }else{
                param[2] = actionIdStr;
            }
            param[3] = workCompletionDateTime;
            param[4] = "1";
            param[5] = observationIdStr;
            param[6] = empcode;
            param[7]  =zoneId;
            param[8] = subZoneId;
            param[9] = remarkStr;
            param[10] = "";
            param[11] = empcode;
            param[12] = "123456";
            param[13] = userType;
            param[14] = otpStr;

                Log.d("check", "" + Arrays.toString(param));

                if (connection.isConnectingToInternet()) {
                    UpdateWorkCompletion updateWorkCompletion = new UpdateWorkCompletion();
                    updateWorkCompletion.execute(param);
                } else {
                    Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }







            // sheetBehavior.cancel();
            // clearData();
        }
    }

    private void updateDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEditText.setText(sdf.format(dateCalendar.getTime()));
    }

    public void validate() {
        boolean isValidDate = false, isValidTime = false, isValidRemark = false;

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String siteEng = null;
        try {
            siteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(completionDateStr)) {
            dateTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            dateTextInputLayout.setError(null);
            isValidDate = true;
        }

        if (TextUtils.isEmpty(completionTimeStr)) {
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

            if ( siteEng!= null) {


                    String param[] = new String[12];

                    param[0] = "2";
                    param[1] = "25";
                    param[2] = param[2] = contactNoStr; // param[2] = contactNoStr;
                    param[3] = consumerNameStr;
                    param[4] = "";
                    param[5] = subZoneStr;
                    param[6] = zoneStr;
                    param[7] = complaintNoStr;
                    param[8] = OTP; // param[8] = "123456";
                    param[9] = consumerNoStr;
                    param[10] = empcode;
                    param[11] = PreferenceUtil.getMac();

                    Log.d("check", "" + Arrays.toString(param));

                    if (connection.isConnectingToInternet()) {
                        if(siteEng.equals("ZonalManager")){
                            workCompletionDateTime = completionDateStr + " " + timeFormat;
                            zoneModel = realmOperations.fetchZoneByName(zoneStr.substring(zoneStr.lastIndexOf("-") + 1));
                            String zoneId= String.valueOf(zoneModel.getBUM_BU_ID());
                            subZoneModel=realmOperations.fetchSubZoneByName(subZoneStr);
                            String subZoneId=String.valueOf(subZoneModel.getPCM_PC_ID());

                            String param1[] = new String[15];
                            param1[0] = complaintSubTypeStr;
                            param1[1] = complaintNoStr;
                            if(actionStr.equals("REJECT")){
                                param[2] = "1";
                            }else{
                                param[2] = actionIdStr;
                            }
                            param1[3] = workCompletionDateTime;
                            param1[4] = "1";
                            param1[5] = observationIdStr;
                            param1[6] = empcode;
                            param1[7]  =zoneId;
                            param1[8] = subZoneId;
                            param1[9] = remarkStr;
                            param1[10] = "";
                            param1[11] = empcode;
                            param1[12] = "123456";
                            param1[13] = userType;
                            param1[14] = "NO-OTP";

                            Log.d("check", "" + Arrays.toString(param));

                            if (connection.isConnectingToInternet()) {
                                UpdateWorkCompletion updateWorkCompletion = new UpdateWorkCompletion();
                                updateWorkCompletion.execute(param1);
                            } else {
                                Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            sendSMS sendSMS = new sendSMS();
                            sendSMS.execute(param);
                        }

                    } else {
                        Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                    Log.d("check", "" + Arrays.toString(param));
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class sendSMS extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            smsProgress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .widgetColorRes(R.color.colorPrimary)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraNames[] = new String[12];

                paraNames[0] = "msgTypeID";
                paraNames[1] = "msgSubTypeID";
                paraNames[2] = "MobileNo";
                paraNames[3] = "ConsName";
                paraNames[4] = "EmpName";
                paraNames[5] = "Area1";
                paraNames[6] = "Zone";
                paraNames[7] = "refNo";
                paraNames[8] = "Number";
                paraNames[9] = "ConsNo";
                paraNames[10] = "UserName";
                paraNames[11] = "IPAddress";

                jsonSMSResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.sendSMS, params, paraNames);

                Log.d("check", jsonSMSResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                String[] enums = gson.fromJson(jsonSMSResponse, String[].class);
                //Log.d("check", "enum " + enums);
                if (enums.length > 0) {

                    if (enums[0].equalsIgnoreCase("Success")) {
                        sheetBehavior.show();
                    } else {
                        Toast.makeText(mCon, enums[0], Toast.LENGTH_SHORT).show();
                    }
                    //finish();
                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCWorkCompletionDetailedActivity", "send SMS event", error);
            }
            smsProgress.dismiss();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class UpdateWorkCompletion extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .widgetColorRes(R.color.colorPrimary)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraNames[] = new String[15];

                paraNames[0] = "ComplaintSubType";
                paraNames[1] = "ComplaintNo";
                paraNames[2] = "ActionId";
                paraNames[3] = "W_Wcdate";
                paraNames[4] = "ObservationId";
                paraNames[5] = "work_done";
                paraNames[6] = "EmpId";
                paraNames[7] = "zone_id";
                paraNames[8] = "subzone_id";
                paraNames[9] = "Remarks";
                paraNames[10] = "EmpType";
                paraNames[11] = "Emp_Code";
                paraNames[12] = "IP";
                paraNames[13] = "UserType";
                paraNames[14] = "OTP";


                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.NComplaint_WorkCompletion, params, paraNames);

                Log.d("check", "response : " + jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                NCUpdateWorkResponseModel enums = gson.fromJson(jsonResponse.trim(), NCUpdateWorkResponseModel.class);

                Log.d("check", "enum " + enums);

                if (enums.getSuccess() == null || enums.getSuccess().equals("")) {
                    Toast.makeText(mCon, enums.getDiv_CError(), Toast.LENGTH_SHORT).show();
                    //finish();
                } else {
                    if(actionStr.equals("Finish")){
                        Toast.makeText(mCon, R.string.work_completion_successfully, Toast.LENGTH_SHORT).show();
                        finish();
                    } else if(actionStr.equals("REJECT")){
                        Toast.makeText(mCon, R.string.work_rejected_successfully, Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(mCon,"Complaint forwarded to"+actionStr +" successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    // Toast.makeText(mCon, enums.getDiv_Cmsg(), Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCWorkCompletionDetailedActivity", "submit btn click if valid OTP update work completion event", error);
            }
            progress.dismiss();
        }
    }

    private void clearData() {
        otpEditText.setText("");
        reasonEditText.setText("");
    }

    }
