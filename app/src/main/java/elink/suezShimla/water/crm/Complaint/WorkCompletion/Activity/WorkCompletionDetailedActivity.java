package elink.suezShimla.water.crm.Complaint.WorkCompletion.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.Allocation.Activity.WorkAllocationCompletionActivity;
import elink.suezShimla.water.crm.Complaint.Allocation.Reading.MeterReadingActivity;
import elink.suezShimla.water.crm.Complaint.Reallocation.Activity.WorkReAllocationCompletionActivity;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.FinishDataModel;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.UpdateCompletionResponseModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.WorkCompObservationModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.FinishActionModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

import static elink.suezShimla.water.crm.Base.App.getContext;

public class WorkCompletionDetailedActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private BottomSheetDialog sheetBehavior;
    private CheckBox checkbox;
    private MaterialButton submitButton;
    private AppCompatImageView closeImageView;

    private String jsonResponse = "", jsonSMSResponse = "", jsonPhotoResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Context mCon;
    private Gson gson;
    private RealmOperations realmOperations;

    private AppCompatSpinner finishActionSpinner, observationSpinner, actionFormSpinner;
    private FinishActionModel finishActionModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private WorkCompObservationModel workCompObservationModel;
    private ArrayList<String> actionModelsList, actionModelIdList, actionModelFinishList, actionModelFinishIdList;
    private ArrayList<String> observationList;
    private ArrayList<String> observationSelectList;
    private ArrayAdapter finishActionAdapter, observationAdapter, actionFormArrayAdapter;

    private MaterialDialog progress, smsProgress;
    private TextInputLayout dateTextInputLayout, timeTextInputLayout, remarkTextInputLayout, otpTextInputLayout, reasonTextInputLayout;
    private TextInputEditText dateEditText, timeEditText, remarkEditText, otpEditText, reasonEditText;
    private Calendar dateCalendar;
    private TimePickerDialog mTimePicker;

    private TextView complaintNumberTextView, consumerNoTextView, dateTimeTextView, consumerNameTextView, contactNoTextView, consumerAddressTextView,
            priorityTextView, complaintSubTypeTextView, workAllocationDateTextView, tariffTextView, statusTypeTextView, zoneTextView, subZoneTextView,
            disputeBillTextView, submitTextView, otpLabelTextView, reasonLabelTextView, vipTextView, complaintTypeTextView, repeatCallTextView, agingTextView, slaStatusTextView, readingMetersubmitTextView;

    private String consumerNoStr, complaintNoStr, complaintWorkAllocateDateStr, complaintDateStr, complaintSubTypeStr, tariffStr, statusStr,
            consumerNameStr, addressStr, priorityStr, contactNoStr, zoneStr, subZoneStr, disputeBillMonthYrStr, meterNoStr, meterTransIdStr, address1Str, address2Str,
            address3Str, pincodeStr = "", complaintTypeStr, completionDateStr, completionTimeStr, remarkStr, actionStr, actionPosstion, actionIdStr, workCompletionDateTime,
            observationStr, observationIdStr, searchForStr, otpStr, otpReasonStr, timeFormat, AM_PM, OTP, userType, filterIdStr, vipName, actionFormStr = "", complaintCode = "";

    private int actionId;

    String cscmName = "", cscmID = "", JsonFinishActionData = "", departmentID = "", mainComplaintID = "", mainComplaintCode = "", filterId = "",
            repeatCall = "", agging = "", sla = "", observationId = "", comType = "", wType = "W", pastReading = "";
    private ArrayList<String> complaintTypeList;
    private ComplaintTypeModel complaintTypeModel;
    private String strConnCategory = "", strConnSize = "", fromDate = "", toDate = "", clickStr = "", UserType = "M", comPno = "", comseq = "", empCodeStr = "", docTypeId = "", docSubTypeId = "", docSource = "",
            otherdocTypeId = "", otherdocSubTypeId = "", otherdocSource = "";

    MaterialDialog finishActionProgress;

    LinearLayout ll_meter_reading;
    RelativeLayout relativeLayout2, relativeLayout3;
    ImageView uploadDoc, otheruploadDoc;

    public static final int RequestPermissionCode = 1;
    private int reqcode_img = 1010;
    String base64String = "", otherbase64String = "";
    public static final int REQUEST_CAMERA = 10;
    File storageDir;
    private String imageName, otherImgName;
    private static int PROFILE_CAMERA = 11;
    double newImageHeight = 512.0;
    int imageQuality = 95;
    private int distributionCode = 1011, photoCode = 1012, otherPhotoCode = 1013;

    private MaterialDialog progressPhoto;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_completion_detailed);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        dateCalendar = Calendar.getInstance();

        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

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

        consumerNoStr = getIntent().getStringExtra("consumerNo");
        complaintNoStr = getIntent().getStringExtra("complaintNo");
        complaintWorkAllocateDateStr = getIntent().getStringExtra("complaintWorkAllocateDate");
        complaintDateStr = getIntent().getStringExtra("complaintDate");

        complaintSubTypeStr = getIntent().getStringExtra("complaintSubType");
        tariffStr = getIntent().getStringExtra("tariff");
        statusStr = getIntent().getStringExtra("status");
        consumerNameStr = getIntent().getStringExtra("consumerName");
        addressStr = getIntent().getStringExtra("address");
        priorityStr = getIntent().getStringExtra("priority");
        contactNoStr = getIntent().getStringExtra("contactNo");
        zoneStr = getIntent().getStringExtra("zone");
        subZoneStr = getIntent().getStringExtra("subZone");
        disputeBillMonthYrStr = getIntent().getStringExtra("disputeBillMonthYr");
        meterNoStr = getIntent().getStringExtra("meterNo");
        meterTransIdStr = getIntent().getStringExtra("meterTransId");

        repeatCall = getIntent().getStringExtra("repeatCall");
        agging = getIntent().getStringExtra("agging");
        sla = getIntent().getStringExtra("sla");
        remarkStr = getIntent().getStringExtra("remark");
        comType = getIntent().getStringExtra("comType");
        fromDate = getIntent().getStringExtra("fromDate");
        toDate = getIntent().getStringExtra("toDate");
        clickStr = getIntent().getStringExtra("strClick");
        comseq = getIntent().getStringExtra("comseq");
        //Toast.makeText(mCon, comseq, Toast.LENGTH_SHORT).show();
        comPno = getIntent().getStringExtra("comPno");
        strConnCategory = getIntent().getStringExtra("connCategory");
        strConnSize = getIntent().getStringExtra("connSize");


        if (meterTransIdStr.contains(".")) {
            meterTransIdStr = meterTransIdStr.substring(0, meterTransIdStr.indexOf('.'));
        }

        address1Str = getIntent().getStringExtra("address1");
        address2Str = getIntent().getStringExtra("address2");
        address3Str = getIntent().getStringExtra("address3");
        pincodeStr = getIntent().getStringExtra("pincode");
/*
        if (pincodeStr.contains(".")) {
            pincodeStr = pincodeStr.substring(0, pincodeStr.indexOf('.'));
        }*/
        complaintTypeStr = getIntent().getStringExtra("complaintType");


        searchForStr = getIntent().getStringExtra("searchForStr");
        vipName = getIntent().getStringExtra("vipName");
        actionFormStr = getIntent().getStringExtra("actionForm");
        complaintCode = getIntent().getStringExtra("complaintCode");


        departmentID = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEPARTMENTID);

        empCodeStr = UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE);
        try {
            departmentID=  new AesAlgorithm().decrypt(departmentID);

            empCodeStr = aes.decrypt( empCodeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        complaintTypeList = realmOperations.fetchComplaintTypeByDept(departmentID);
        String cc = "";
        for (int i = 0; i < complaintTypeList.size(); i++) {
            cc = complaintTypeList.get(i);
        }
        Log.d("cc", cc);
        complaintTypeModel = realmOperations.fetchComplaintTypeByName(complaintTypeStr);
        mainComplaintID = String.valueOf(complaintTypeModel.getCMTM_ID());
        mainComplaintCode = complaintTypeModel.getCMTM_CODE();
        //  Log.d("cc", String.valueOf(complaintTypeId));


        ll_meter_reading = findViewById(R.id.ll_meter_reading);
        relativeLayout2 = findViewById(R.id.relativeLayout2);
        relativeLayout3 = findViewById(R.id.relativeLayout3);


        uploadDoc = findViewById(R.id.uploadDoc);
        uploadDoc.setOnClickListener(this);

        otheruploadDoc = findViewById(R.id.otheruploadDoc);
        otheruploadDoc.setOnClickListener(this);


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
        complaintTypeTextView = findViewById(R.id.complaintTypeTextView);

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
        readingMetersubmitTextView = findViewById(R.id.readingMetersubmitTextView);

        if (complaintTypeStr.equalsIgnoreCase("Billing Management")) {

            uplopadDocumentId("340", "354", "B");   // Site visit report copy (Post Resolution)
            uplopadOtherDocumentId("340", "356", "B"); // Other (Post Resolution)
            // uplopadDocumentId("340","356","B");


        } else if (complaintTypeStr.equalsIgnoreCase("Collection Management")) {

            uplopadDocumentId("358", "368", "COL"); // Cheque copy (Post Resolution)
            uplopadOtherDocumentId("358", "367", "COL"); // Wallet Payment Statement (Post Resolution)
            //  uplopadDocumentId("358","367","COL");  Wallet Payment Statement (Post Resolution)

        } else if (complaintTypeStr.equalsIgnoreCase("Connection Management")) {

            uplopadDocumentId("398", "399", "N");  //
            uplopadOtherDocumentId("398", "404", "N");
            //uplopadDocumentId("398","399","N");


        } else if (complaintTypeStr.equalsIgnoreCase("Construction Management")) {

            uplopadDocumentId("400", "401", "W");
            uplopadOtherDocumentId("400", "405", "W");


        } else if (complaintTypeStr.equalsIgnoreCase("Enforcement Management")) {

            uplopadDocumentId("395", "396", "E");
            uplopadOtherDocumentId("395", "403", "E");


        } //else if (complaintTypeStr.equalsIgnoreCase("Meter Management"))
        else if (complaintTypeStr.equalsIgnoreCase("Metering")) {

            uplopadDocumentId("308", "322", "M"); // Meter photo  (Post Resolution)
            //uplopadDocumentId("308","321","M");
            uplopadOtherDocumentId("308", "321", "M");// Site photo (Post Resolution)


        } else if (complaintTypeStr.equalsIgnoreCase("Meter Reading Management")) {

            uplopadDocumentId("330", "337", "MR"); // Site Photo (Post Resolution)
            // uplopadDocumentId("330","337","MR");
            uplopadOtherDocumentId("330", "338", "MR"); //Reading Photo (Post Resolution)


        } else if (complaintTypeStr.equalsIgnoreCase("Operation & Maintenance")) {

            uplopadDocumentId("370", "378", "OM");//Site photo (Post Resolution)
            uplopadOtherDocumentId("370", "379", "OM");//Leak Attended Photo (Post Resolution)
            //     uplopadDocumentId("370","371","OM");


        } else if (complaintTypeStr.equalsIgnoreCase("Recovery Management")) {

            uplopadDocumentId("392", "393", "R");
            uplopadOtherDocumentId("392", "402", "R");

        }


        repeatCallTextView = findViewById(R.id.repeatCallTextView);
        agingTextView = findViewById(R.id.agingTextView);
        slaStatusTextView = findViewById(R.id.slaStatusTextView);


        dateTextInputLayout = findViewById(R.id.dateTextInputLayout);
        timeTextInputLayout = findViewById(R.id.timeTextInputLayout);
        remarkTextInputLayout = findViewById(R.id.remarkTextInputLayout);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        remarkEditText = findViewById(R.id.remarkEditText);

        finishActionSpinner = findViewById(R.id.finishActionSpinner);
        observationSpinner = findViewById(R.id.observationSpinner);
        observationSpinner.setOnItemSelectedListener(this);
        vipTextView = findViewById(R.id.vipTextView);

        complaintTypeTextView.setText(complaintTypeStr);
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
//to remove decimal from mobile no
        double number = Double.parseDouble(contactNoStr);

        // Convert the double to long (to handle large values)
        long longValue = (long) number;
        contactNoTextView.setText(String.valueOf(longValue));
        disputeBillTextView.setText(disputeBillMonthYrStr);
        vipTextView.setText(vipName);
        repeatCallTextView.setText(repeatCall);
        slaStatusTextView.setText(sla);
        agingTextView.setText(agging);
        remarkEditText.setText(remarkStr);

        actionModelsList = new ArrayList<>();
        actionModelIdList = new ArrayList<>();

        if (departmentID.equalsIgnoreCase("2")) {
            ll_meter_reading.setVisibility(View.GONE);
            relativeLayout2.setVisibility(View.GONE);
            relativeLayout3.setVisibility(View.VISIBLE);

        } else {
            ll_meter_reading.setVisibility(View.VISIBLE);
            relativeLayout2.setVisibility(View.VISIBLE);
            relativeLayout3.setVisibility(View.GONE);

        }

        getFinishActionData();

        //actionModelsList = realmOperations.fetchFinishAction();

        userType = "Android";

      /*  if (PreferenceUtil.getSiteEng() != null) {
            if (PreferenceUtil.getSiteEng().equals("SiteEng")) {
                userType = "Android";
            } else {
                userType = "Android";
            }
        } else {
            userType = "Android";
        }*/

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

        /*finishActionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actionStr = finishActionSpinner.getSelectedItem().toString().trim();
                actionPosstion = String.valueOf(finishActionSpinner.getSelectedItemPosition());
                //finishActionModel = realmOperations.fetchFinishActionByName(actionStr);

                if(actionStr.equalsIgnoreCase("For Billing Adjustment")){


                    Intent intent = new Intent(mCon, WorkCompletionBillingAdjustmentActivity.class);
                    intent.putExtra("consumerNo", consumerNoStr);
                    intent.putExtra("complaintNo", complaintNoStr);
                    intent.putExtra("complaintWorkAllocateDate", complaintWorkAllocateDateStr);
                    intent.putExtra("complaintDate",complaintDateStr);
                    intent.putExtra("complaintSubType", complaintSubTypeStr);
                    intent.putExtra("tariff", tariffStr);
                    intent.putExtra("status", statusStr);
                    intent.putExtra("consumerName", consumerNameStr);
                    intent.putExtra("address", addressStr);
                    intent.putExtra("priority", priorityStr);
                    intent.putExtra("contactNo",contactNoStr);
                    intent.putExtra("zone",zoneStr);
                    intent.putExtra("subZone", subZoneStr);
                    intent.putExtra("disputeBillMonthYr", disputeBillMonthYrStr);
                    intent.putExtra("meterNo", meterNoStr);
                    intent.putExtra("meterTransId",meterTransIdStr);
                    intent.putExtra("address1",address1Str);
                    intent.putExtra("address2", address2Str);
                    intent.putExtra("address3", address3Str);
                    intent.putExtra("pincode", pincodeStr);
                    intent.putExtra("complaintType", complaintTypeStr);
                    intent.putExtra("searchForStr", searchForStr);
                    intent.putExtra("vipName",vipName);
                    intent.putExtra("actionForm",actionFormStr);
                    intent.putExtra("complaintCode",complaintCode);

                    startActivity(intent);



                }else {


                    actionIdStr = actionModelIdList.get(Integer.parseInt(actionPosstion));

                    observationList = realmOperations.fetchWorkObservation(actionIdStr, mainComplaintID);

                    observationSelectList = new ArrayList<>();
                    observationSelectList.add("Select");
                    observationSelectList.addAll(observationList);

                    observationAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, observationSelectList);
                    observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    observationSpinner.setAdapter(observationAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        observationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                observationStr = observationSpinner.getSelectedItem().toString();
                if (observationStr.equals("Select")) {
                    observationIdStr = "0";
                }/*else  if(observationStr.equalsIgnoreCase("Work completed - Bill Distributed")||observationStr.equalsIgnoreCase("Work completed - Reading taken")||observationStr.equalsIgnoreCase("Work Completed"))
                {

                }*/ else {
                    workCompObservationModel = realmOperations.fetchWorkObservationTypeByName(observationStr);
                    observationId = workCompObservationModel.getID();
                    observationIdStr = String.valueOf(observationId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        setDate();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidation()) {

                    validateSubmitOtp();
                }
            }
        });

        // Randomly generated OTP Code

        // OTP = getRandomNumberString();
        OTP = "123456";
        Log.d("check", "" + OTP);

        actionFormSpinner = findViewById(R.id.actionFormSpinner);
        setActionFormropDown();


        submitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   Intent intent = new Intent(v.getContext(), WorkCompletionDetailedActivity.class);
                intent.putExtra("consumerNo", consumerNoStr);
                intent.putExtra("complaintNo", complaintNoStr);
                intent.putExtra("complaintWorkAllocateDate", complaintWorkAllocateDateStr);
                intent.putExtra("complaintDate", complaintDateStr);
                intent.putExtra("complaintSubType", complaintSubTypeStr);
                intent.putExtra("tariff", tariffStr);
                intent.putExtra("status", statusStr);
                intent.putExtra("consumerName", consumerNameStr);
                intent.putExtra("address", addressStr);
                intent.putExtra("priority", priorityStr);
                intent.putExtra("contactNo", contactNoStr);
                intent.putExtra("zone", zoneStr);
                intent.putExtra("subZone", subZoneStr);
                intent.putExtra("disputeBillMonthYr", disputeBillMonthYrStr);
                intent.putExtra("meterNo", meterNoStr);
                intent.putExtra("meterTransId", meterTransIdStr);
                intent.putExtra("address1", address1Str);
                intent.putExtra("address2", address2Str);
                intent.putExtra("address3", address3Str);
                intent.putExtra("pincode", pincodeStr);
                intent.putExtra("complaintType", complaintTypeStr);
                intent.putExtra("searchForStr", searchForStr);
                intent.putExtra("vipName", vipName);
                intent.putExtra("actionForm", actionFormStr);
                intent.putExtra("complaintCode", complaintCode);
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate", toDate);
                intent.putExtra("strClick", clickStr);
                intent.putExtra("comseq",comseq );
                intent.putExtra("comPno", comPno);

                startActivity(intent);
                finish();*/


                if (checkValidation()) {
                    validate();
                }
            }
        });

        readingMetersubmitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), MeterReadingActivity.class);
                intent.putExtra("consumerNo", consumerNoStr);
                intent.putExtra("complaintNo", complaintNoStr);
                intent.putExtra("complaintWorkAllocateDate", complaintWorkAllocateDateStr);
                intent.putExtra("complaintDate", complaintDateStr);
                intent.putExtra("complaintSubType", complaintSubTypeStr);
                intent.putExtra("tariff", tariffStr);
                intent.putExtra("status", statusStr);
                intent.putExtra("consumerName", consumerNameStr);
                intent.putExtra("address", addressStr);
                intent.putExtra("priority", priorityStr);
                intent.putExtra("contactNo", contactNoStr);
                intent.putExtra("zone", zoneStr);
                intent.putExtra("subZone", subZoneStr);
                intent.putExtra("disputeBillMonthYr", disputeBillMonthYrStr);
                intent.putExtra("meterNo", meterNoStr);
                intent.putExtra("meterTransId", meterTransIdStr);
                intent.putExtra("address1", address1Str);
                intent.putExtra("address2", address2Str);
                intent.putExtra("address3", address3Str);
                intent.putExtra("pincode", pincodeStr);
                intent.putExtra("complaintType", complaintTypeStr);
                intent.putExtra("searchForStr", searchForStr);
                intent.putExtra("vipName", vipName);
                intent.putExtra("actionForm", actionFormStr);
                intent.putExtra("complaintCode", complaintCode);
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate", toDate);
                intent.putExtra("strClick", clickStr);
                intent.putExtra("comseq", comseq);
                intent.putExtra("comPno", comPno);
                intent.putExtra("pastReading", pastReading);

                startActivity(intent);
                finish();


                  /*  if (checkValidation()) {
                        validate();
                    }
*/
            }
        });

    }

    private void uplopadDocumentId(String typeId, String subTypeId, String docSourceId) {
        docTypeId = typeId;
        docSubTypeId = subTypeId;
        docSource = docSourceId;
    }

    private void uplopadOtherDocumentId(String typeId, String subTypeId, String docSourceId) {
        otherdocTypeId = typeId;
        otherdocSubTypeId = subTypeId;
        otherdocSource = docSourceId;
    }

    private void getFinishActionData() {

        String[] params = new String[4];

        params[0] = wType;
        params[1] = comType;
        params[2] = UserType;
        params[3] = consumerNoStr;

        {
            GetFinishActionData getFinishActionData = new GetFinishActionData();
            getFinishActionData.execute(params);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.uploadDoc:
                enableRuntimePermission();
                // cam_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    //startCameraIntent(REQUEST_CAMERA);

                    showUploadPrescriptionDialog("", 3);
                    //   startActivityForResult(cam_intent, reqcode_img);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
                break;

            case R.id.otheruploadDoc:  // Added other camera upload doc. by Ajit
                enableRuntimePermission();
                // cam_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    //startCameraIntent(REQUEST_CAMERA);

                    showUploadPrescriptionDialog("", 5);
                    //   startActivityForResult(cam_intent, reqcode_img);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
                break;

            default:
                break;
        }
    }

    private void enableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            Toast.makeText(mCon, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(),"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void showUploadPrescriptionDialog(String s, int requestCode) {
        startCameraIntent(REQUEST_CAMERA + requestCode);

    }


    private void startCameraIntent(int requestCode) {
        //Toast.makeText(mCon, requestCode+"",Toast.LENGTH_SHORT).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's activity_login ic_camera activity to handle the intent
       // if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "elink.suezShimla.water.crm",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, requestCode);
            }
      //  }
    }


    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        Bitmap bitmap = null;
        Uri imageUri;
        if (requestcode == 13 && resultcode == this.RESULT_OK) {

            imageUri = Uri.fromFile(new File(imageName));

            uploadDoc.setImageURI(imageUri);
            uploadDoc.setVisibility(View.VISIBLE);
            uploadDoc.setPadding(0, 0, 0, 0);
            bitmap = ((BitmapDrawable) uploadDoc.getDrawable()).getBitmap();
            int nh = (int) (bitmap.getHeight() * (newImageHeight / bitmap.getWidth()));
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) newImageHeight, nh, true);
            // iv_visiting_card_proprietor.setImageBitmap(scaled);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            uploadDoc.setImageBitmap(bitmap);
            base64String = Base64.encodeToString(b, Base64.DEFAULT);
            if (!base64String.equalsIgnoreCase("")) {

            } else {
                base64String = "NOPHOTO";
            }

        } else if (requestcode == 15 && resultcode == this.RESULT_OK) {

            imageUri = Uri.fromFile(new File(imageName));

            otheruploadDoc.setImageURI(imageUri);
            otheruploadDoc.setVisibility(View.VISIBLE);
            otheruploadDoc.setPadding(0, 0, 0, 0);
            bitmap = ((BitmapDrawable) otheruploadDoc.getDrawable()).getBitmap();
            int nh = (int) (bitmap.getHeight() * (newImageHeight / bitmap.getWidth()));
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) newImageHeight, nh, true);
            // iv_visiting_card_proprietor.setImageBitmap(scaled);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            otheruploadDoc.setImageBitmap(bitmap);
            otherbase64String = Base64.encodeToString(b, Base64.DEFAULT);
            if (!otherbase64String.equalsIgnoreCase("")) {

            } else {
                otherbase64String = "NOOTHERPHOTO";
            }
        }

    }


    private File createImageFile() {

        File image = null;
        try {
            String imageFileName = "PROFILE";

            storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//            File dir = new File (mCon.getExternalFilesDir(null), "Water Meter Reading");

            if (storageDir != null) {
                // ExifInterface exif = null;
                File[] files = storageDir.listFiles();
                for (File child : files) {
                    if (child.getAbsolutePath().contains("PROFILE"))

                        child.delete();
                }
                image = File.createTempFile(imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */

                );

                imageName = image.getAbsolutePath();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    @SuppressLint("StaticFieldLeak")
    private class GetFinishActionData extends AsyncTask<String, Void, Void> implements AdapterView.OnItemSelectedListener {
        @Override
        protected void onPreExecute() {
            finishActionProgress = new MaterialDialog.Builder(mCon)
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
//                JsonMMGMasterData = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.GetMasterDataForAndroid_MMG);
                String paraNames[] = new String[4];
                paraNames[0] = "W_type";
                paraNames[1] = "COM_Type";
                paraNames[2] = "UserType"; // new parameter add
                paraNames[3] = "ConsumerNo"; //new parameter add

                JsonFinishActionData = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.S_ComplaintSection, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                //  actionModelsList.clear();
                //actionModelIdList.clear();
               // if(aVoid != null){
                FinishDataModel[] finishDataModels = gson.fromJson(JsonFinishActionData, FinishDataModel[].class);
                if (finishDataModels.length > 0) {
                    for (FinishDataModel dataModel : finishDataModels) {
                        cscmID = dataModel.getCSCM_ID();
                        cscmName = dataModel.getCSCM_SECNAME();
                        pastReading = dataModel.getPASTREADING();
                        actionModelsList.add(cscmName);
                        actionModelIdList.add(cscmID);
                    }

                    //  X6#D5h@IlD%n&jY0mI
                    actionModelFinishIdList = new ArrayList<>();
                    actionModelFinishIdList.add("0");
                    actionModelFinishIdList.addAll(actionModelIdList);
                    Log.d("actionModelFinish0", actionModelFinishIdList.toString());
                    actionModelFinishList = new ArrayList<>();
                    actionModelFinishList.add("Select");
                    actionModelFinishList.addAll(actionModelsList);
                    Log.d("actionModelFinishList", actionModelFinishList.toString());


                    finishActionAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, actionModelFinishList);
                    finishActionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    finishActionSpinner.setAdapter(finishActionAdapter);
                    finishActionSpinner.setOnItemSelectedListener(this);
                    finishActionProgress.dismiss();


                }
               // }
              //  Toast.makeText(mCon, "Server not responding", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.e("Exception", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid_MMG", error);
            }

        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.finishActionSpinner) {
                actionStr = finishActionSpinner.getSelectedItem().toString().trim();
                actionPosstion = String.valueOf(finishActionSpinner.getSelectedItemPosition());
                //finishActionModel = realmOperations.fetchFinishActionByName(actionStr);

                if (actionStr.equalsIgnoreCase("For Billing Adjustment")) {


                    Intent intent = new Intent(mCon, WorkCompletionBillingAdjustmentActivity.class);
                    intent.putExtra("consumerNo", consumerNoStr);
                    intent.putExtra("complaintNo", complaintNoStr);
                    intent.putExtra("complaintWorkAllocateDate", complaintWorkAllocateDateStr);
                    intent.putExtra("complaintDate", complaintDateStr);
                    intent.putExtra("complaintSubType", complaintSubTypeStr);
                    intent.putExtra("tariff", tariffStr);
                    intent.putExtra("status", statusStr);
                    intent.putExtra("consumerName", consumerNameStr);
                    intent.putExtra("address", addressStr);
                    intent.putExtra("priority", priorityStr);
                    intent.putExtra("contactNo", contactNoStr);
                    intent.putExtra("zone", zoneStr);
                    intent.putExtra("subZone", subZoneStr);
                    intent.putExtra("disputeBillMonthYr", disputeBillMonthYrStr);
                    intent.putExtra("meterNo", meterNoStr);
                    intent.putExtra("meterTransId", meterTransIdStr);
                    intent.putExtra("address1", address1Str);
                    intent.putExtra("address2", address2Str);
                    intent.putExtra("address3", address3Str);
                    intent.putExtra("pincode", pincodeStr);
                    intent.putExtra("complaintType", complaintTypeStr);
                    intent.putExtra("searchForStr", searchForStr);
                    intent.putExtra("vipName", vipName);
                    intent.putExtra("actionForm", actionFormStr);
                    intent.putExtra("complaintCode", complaintCode);
                    intent.putExtra("connCategory", strConnCategory);
                    intent.putExtra("connSize", strConnSize);
                    intent.putExtra("fromDate", fromDate);
                    intent.putExtra("toDate", toDate);
                    intent.putExtra("strClick", clickStr);

                    startActivity(intent);
                    finish();


                }/*else if(actionStr.equalsIgnoreCase("Finish")){
                  actionIdStr="0";
              }*/ else {


                    actionIdStr = actionModelFinishIdList.get(Integer.parseInt(actionPosstion));
                    observationList = realmOperations.fetchWorkObservation(actionIdStr, mainComplaintID);

                    observationSelectList = new ArrayList<>();
                    if (observationList.size() == 1) {

                    } else {
                        observationSelectList.add("Select");
                    }
                    observationSelectList.addAll(observationList);


                    observationAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, observationSelectList);
                    observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    observationSpinner.setAdapter(observationAdapter);// finishActionSpinner
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    private boolean checkValidation() {
        completionDateStr = dateEditText.getText().toString().trim();
        completionTimeStr = timeEditText.getText().toString().trim();
        // remarkStr = actionFormSpinner.getSelectedItem().toString();
        otpStr = otpEditText.getText().toString().trim();
        remarkStr = remarkEditText.getText().toString();
        otpReasonStr = reasonEditText.getText().toString().trim();
        observationStr = observationSpinner.getSelectedItem().toString();
        actionFormStr = finishActionSpinner.getSelectedItem().toString();

        if (completionDateStr.equalsIgnoreCase("")) {
            dateTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            return false;
        } else {
            dateTextInputLayout.setError(null);

        }
        if (completionTimeStr.equalsIgnoreCase("")) {
            timeTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            return false;
        } else {
            timeTextInputLayout.setError(null);

        }


        if (actionFormStr.equalsIgnoreCase("Select")) {
            // Toast.makeText(mCon, "Please select finish / Fwd to section", Toast.LENGTH_SHORT).show();
            Toast.makeText(mCon, "Please select status", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (observationStr.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, "Please select observation", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (remarkStr.equalsIgnoreCase("") || remarkStr.isEmpty()) {
            Toast.makeText(mCon, "Please enter remark", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void setActionFormropDown() {
        ArrayList<String> actionFormName = new ArrayList<>();
        actionFormName = realmOperations.fetchActionFormList(complaintCode);
        ArrayList<String> actionForm = new ArrayList<>();
        actionForm.add("Select");
        actionForm.addAll(actionFormName);
        actionFormArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, actionForm);
        actionFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionFormSpinner.setAdapter(actionFormArrayAdapter);

        for (int i = 0; i < actionForm.size(); i++) {
            String name = actionForm.get(i);


            if (actionFormStr.equalsIgnoreCase(name)) {
                actionFormSpinner.setOnItemSelectedListener(this);
                actionFormSpinner.setSelection(i);
                //  int spinnerPosition = msrArrayAdapter.getPosition(msrName);
                // msrSpinner.setSelection(spinnerPosition);
                break;
            }
        }


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
            dateEditText.setText(FromDateFormat);

            cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
            Date currentLocalTime = cal.getTime();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat date = new SimpleDateFormat("HH:mm a");
            date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

            String localTime = date.format(currentLocalTime);
            timeEditText.setText(localTime);


//            @SuppressLint("SimpleDateFormat") SimpleDateFormat toDf = new SimpleDateFormat("dd-MMM-yyyy");
//            String toDateFormat = toDf.format(Calendar.getInstance().getTime());
//
        } catch (Exception e) {
            Log.d("check", e.getMessage());
        }
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
                otpStr = "No-OTP";
                isValidOtp = true;
            }
        }

        if (isValidOtp) {
            workCompletionDateTime = completionDateStr + " " + timeFormat;
            if (actionStr.toString().equals("MMG")) {
                String param[] = new String[24];
                param[0] = searchForStr;
                param[1] = meterNoStr;  // 15051025
                param[2] = "3108";
                param[3] = workCompletionDateTime;   // test data "09-May-2019 12:33 PM";
                param[4] = consumerNoStr;  // test data 111
                param[5] = observationIdStr;
                param[6] = remarkStr;
                param[7] = empcode;
                param[8] = address1Str;
                param[9] = null;
                param[10] = null;
                param[11] = null;
                param[12] = complaintNoStr;
                param[13] = null;
                param[14] = complaintTypeStr;  // test data "Meter Reading Management";
                param[15] = complaintSubTypeStr;   // test data "Wrong Reading";
                param[16] = actionStr;    // test data  "MMG";
                param[17] = PreferenceUtil.getRights();// param[17] = PreferenceUtil.getRights(); param[17] = "SYSADM001";
                if (meterTransIdStr == null) {
                    meterTransIdStr = "";
                } else {
                    if (meterTransIdStr.contains(".")) {
                        param[18] = meterTransIdStr.substring(0, meterTransIdStr.indexOf('.'));
                    } else {
                        param[18] = meterTransIdStr;
                    }
                }

                // test data  "1";
                param[19] = "Android"; // from login
                param[20] = otpStr;
                param[21] = empcode;
                param[22] = "123456";
                if (otpReasonStr.equalsIgnoreCase("")){
                    param[23] = null;
                }else {

                    param[23] = otpReasonStr;
                }
                Log.d("check", "" + Arrays.toString(param));

                if (connection.isConnectingToInternet()) {
                    UpdateWorkCompletion updateWorkCompletion = new UpdateWorkCompletion();
                    updateWorkCompletion.execute(param);
                } else {
                    Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

            } else {
                String param[] = new String[24];
                param[0] = searchForStr;
                param[1] = meterNoStr;  // 15051025
                if (actionIdStr.equalsIgnoreCase("20") || actionIdStr.equalsIgnoreCase("16")) {
                    param[2] = "0";
                } else {
                    param[2] = actionIdStr;
                }
                param[3] = workCompletionDateTime;   // test data "09-May-2019 12:33 PM";
                param[4] = consumerNoStr;  // test data 111
                param[5] = observationIdStr;
                param[6] = remarkStr;
                param[7] = empcode;
                param[8] = address1Str;
                param[9] = null;
                param[10] = null;
                param[11] = null;
                param[12] = complaintNoStr;
                param[13] = null;
                param[14] = complaintTypeStr;  // test data "Meter Reading Management";
                param[15] = complaintSubTypeStr;   // test data "Wrong Reading";
                param[16] = actionStr;    // test data  "MMG";
                param[17] = PreferenceUtil.getRights();// param[17] = PreferenceUtil.getRights(); param[17] = "SYSADM001";

                param[18] = meterTransIdStr;
                param[19] = userType; // from login
                param[20] = otpStr;
                param[21] = empcode;
                param[22] = "123456";
                if (otpReasonStr.equalsIgnoreCase("")){
                    param[23] = null;
                }else {

                    param[23] = otpReasonStr;
                }
//                param[23] = otpReasonStr;
                Log.d("check", "" + Arrays.toString(param));
                Log.d("actionIdStr", "" + actionIdStr);

                if (connection.isConnectingToInternet()) {
                    UpdateWorkCompletion updateWorkCompletion = new UpdateWorkCompletion();
                    updateWorkCompletion.execute(param);
                } else {
                    Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
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

        if (siteEng != null) {

                 /*   String param[] = new String[12];

                    param[0] = "2";
                    param[1] = "25";
                    param[2] = contactNoStr; // param[2] = contactNoStr;
                    param[3] = consumerNameStr;
                    param[4] = "";
                    param[5] = subZoneStr;
                    param[6] = zoneStr;
                    param[7] = complaintNoStr;
                    param[8] = OTP; // param[8] = "123456";
                    param[9] = consumerNoStr;
                    param[10] = PreferenceUtil.getUserName();
                    param[11] = PreferenceUtil.getMac();*/

            //Log.d("check", "" + Arrays.toString(param));

            if (connection.isConnectingToInternet()) {
//                        if (PreferenceUtil.getSiteEng().equals("ZonalManager")) {
                workCompletionDateTime = completionDateStr + " " + timeFormat;
                String param1[] = new String[24];

                param1[0] = "0";
                param1[1] = meterNoStr;  // 15051025
                if (actionIdStr.equalsIgnoreCase("20") || actionIdStr.equalsIgnoreCase("16")) {
                    param1[2] = "0";
                } else {
                    param1[2] = actionIdStr;
                }
                param1[3] = complaintWorkAllocateDateStr;   // test data "09-May-2019 12:33 PM";
                param1[4] = consumerNoStr;  // test data 111
                param1[5] = observationIdStr;
                param1[6] = remarkStr;
                param1[7] = empcode;
                param1[8] = address1Str;
                param1[9] = null;
                param1[10] = null;
                param1[11] = null;
                param1[12] = complaintNoStr;
                param1[13] = null;
                param1[14] = complaintTypeStr;  // test data "Meter Reading Management";
                param1[15] = complaintSubTypeStr;   // test data "Wrong Reading";
                param1[16] = actionStr;    // test data  "MMG";
                param1[17] = PreferenceUtil.getRights();// param[17] = PreferenceUtil.getRights(); param[17] = "SYSADM001";


                param1[18] = meterTransIdStr;  // test data  "1";
                param1[19] = userType; // from login
                param1[20] = "No-OTP";
                param1[21] = empcode;
                param1[22] = "123456";
                param1[23] = null;

                Log.d("check", "" + Arrays.toString(param1));
                if (connection.isConnectingToInternet()) {
                    UpdateWorkCompletion updateWorkCompletion = new UpdateWorkCompletion();
                    updateWorkCompletion.execute(param1);
                } else {
                    Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

//                        }


            } else {
                Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.actionFormSpinner: {
                String actionFormValue = actionFormSpinner.getSelectedItem().toString();
                if (actionFormValue.equalsIgnoreCase("Select")) {
                    //   Toast.makeText(mCon, "Selcte", Toast.LENGTH_SHORT).show();
                } else {
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
                if (enums.length > 0) {
                    if (enums[0].equalsIgnoreCase("Success")) {
                        sheetBehavior.show();
                    } else {
                        Toast.makeText(mCon, enums[0], Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("check", "" + jsonSMSResponse);
                String error = e.toString();
                ErrorClass.errorData(mCon, "WorkCompletionDetailedActivity", "sendSMS", error);
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
                String paraNames[] = new String[24];

                paraNames[0] = "SearchFor";
                paraNames[1] = "MeterNo";   //COM_METER_REPLACE
                paraNames[2] = "Action";
                paraNames[3] = "WCDate";   // work completion date and time
                paraNames[4] = "ConsumerNo";
                paraNames[5] = "Observation";  // observation id
                paraNames[6] = "Remarks";
                paraNames[7] = "EmpId";  // string of employees
                paraNames[8] = "Address1";
                paraNames[9] = "Address2";
                paraNames[10] = "Address3";
                paraNames[11] = "Pin";
                paraNames[12] = "RefNo";  // test data(OC/30/03/19/150)
                paraNames[13] = "EmpType";
                paraNames[14] = "CompType";
                paraNames[15] = "CompSubType";
                paraNames[16] = "ActionText";
                paraNames[17] = "CSVRights";   // rights test-data (SYSADM001)
                paraNames[18] = "COM_METER_TRANSID";
                paraNames[19] = "UserType";  // test-data(Web)
                paraNames[20] = "OTP";  // test-data (NO-OTP)
                paraNames[21] = "Emp_Code";
                paraNames[22] = "IP";
                paraNames[23] = "NonOTPRemarks";


                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.UpdateWorkCompletionData, params, paraNames);

                Log.d("check", "response : " + params);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                UpdateCompletionResponseModel enums = gson.fromJson(jsonResponse, UpdateCompletionResponseModel.class);

                Log.d("check", "enum " + enums);
                if (enums.getDiv_Cmsg() == null || enums.getDiv_Cmsg().equals("")) {
                    Toast.makeText(mCon, enums.getDiv_CError(), Toast.LENGTH_SHORT).show();

                    //finish();
                } else {
                    if (actionStr.equals("REJECT")) {
                        Toast.makeText(mCon, R.string.work_rejected_successfully, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        //Toast.makeText(mCon, "Complaint forwarded to" + actionStr + " successfully", Toast.LENGTH_SHORT).show();
                        if (!base64String.equalsIgnoreCase("")) {
                            uploadPhoto();
                        }

                        if (!otherbase64String.equalsIgnoreCase("")) {
                            uploadOtherPhoto();
                        }
                        if (actionIdStr.equalsIgnoreCase("20") || actionIdStr.equalsIgnoreCase("19")) {
                            MessageWindow.msgComplaintComplaintWindow(mCon, "Complaint submitted successfully", "Success", MainActivity.class);

                        } else {
                            MessageWindow.msgComplaintWindow(mCon, "Complaint submitted successfully", "Success", MainActivity.class);
                        }
                    }
                    progress.dismiss();


                    // Toast.makeText(mCon, enums.getDiv_Cmsg(), Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                Log.d("check", e.getMessage());
                //Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("check", "" + jsonResponse);
                String error = e.toString();
                ErrorClass.errorData(mCon, "WorkCompletionDetailedActivity", "validate Submit Otp", error);
            }
            progress.dismiss();
        }
    }

    private void uploadOtherPhoto() {

        String params[] = new String[11];

        params[0] = otherbase64String;
        params[1] = consumerNoStr;
        params[2] = complaintNoStr;
        params[3] = "";
        params[4] = "";
        params[5] = otherdocSource;
        params[6] = otherdocTypeId;
        params[7] = otherdocSubTypeId;
        params[8] = empCodeStr;
        params[9] = PreferenceUtil.getMac();
        params[10] = remarkStr;

        if (connection.isConnectingToInternet()) {
            PhotoUpload photoUpload = new PhotoUpload();
            photoUpload.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    // docSource="C"
    private void uploadPhoto() {

        String params[] = new String[11];

        params[0] = base64String;
        params[1] = consumerNoStr;
        params[2] = complaintNoStr;
        params[3] = "";
        params[4] = "";
        params[5] = docSource;
        params[6] = docTypeId;
        params[7] = docSubTypeId;
        params[8] = empCodeStr;
        params[9] = PreferenceUtil.getMac();
        params[10] = remarkStr;

        if (connection.isConnectingToInternet()) {
            PhotoUpload photoUpload = new PhotoUpload();
            photoUpload.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    private class PhotoUpload extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressPhoto = new MaterialDialog.Builder(mCon)
                    .content(R.string.upload_documents)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .autoDismiss(false)
                    .progress(true, 0)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[11];
                paraName[0] = "PHOTO";
                paraName[1] = "consumerNo";
                paraName[2] = "RefNo";
                paraName[3] = "oldMeter";
                paraName[4] = "newMeter";
                paraName[5] = "DocSource";
                paraName[6] = "DocType";
                paraName[7] = "DocSubType";
                paraName[8] = "EmpCode";
                paraName[9] = "IP";
                paraName[10] = "Remarks";


                jsonPhotoResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.UploadPhoto, params, paraName);
                Log.d("check", "json : " + jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                String[] enums = gson.fromJson(jsonPhotoResponse, String[].class);
                if (enums[0].equalsIgnoreCase("Success")) {
                    // Toast.makeText(mCon, "Comaplaint Successfully registered with document", Toast.LENGTH_SHORT).show();
//                    MessageWindow.msgComplaintWindow(mCon, "Complaint submitted successfully", "Success", MainActivity.class);

                } else {
                    Toast.makeText(mCon, "Error while submitting", Toast.LENGTH_SHORT).show();
                    finish();


                }
                progressPhoto.dismiss();
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "" + jsonPhotoResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintRegisterActivity", "register complaint button", error);
            }

        }
    }

    private void clearData() {
        otpEditText.setText("");
        reasonEditText.setText("");
    }

    @Override
    public void onBackPressed() {
        startBackActivity();
        //super.onBackPressed();
    }

    private void startBackActivity() {
        if (clickStr.equalsIgnoreCase("AndroidAllocated")) {
            Intent intent = new Intent(this, WorkAllocationCompletionActivity.class);
            intent.putExtra("fromDate", fromDate);
            intent.putExtra("toDate", toDate);
            intent.putExtra("strClick", clickStr);

            //  Intent intent = new Intent(this, WorkCompletionActivity.class);
            startActivity(intent);
            finish();
        } else if (clickStr.equalsIgnoreCase("ReAllocated")) {
            Intent intent = new Intent(this, WorkReAllocationCompletionActivity.class);
            intent.putExtra("fromDate", fromDate);
            intent.putExtra("toDate", toDate);
            intent.putExtra("strClick", clickStr);

            //  Intent intent = new Intent(this, WorkCompletionActivity.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(this, WorkReAllocationCompletionActivity.class);
          /*  intent.putExtra("fromDate", fromDate);
            intent.putExtra("toDate", toDate);
            intent.putExtra("strClick", clickStr);


*/
            //  Intent intent = new Intent(this, WorkCompletionActivity.class);
            startActivity(intent);
            finish();
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
