package elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSourceModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class NCComplaintRegistrationActivity extends AppCompatActivity {
    private Context mCon;
    private MaterialButton registerButton;
    private MaterialDialog progress;
    private AppCompatSpinner  complaintTypeSpinner, complaintSubTypeSpinner, namePrefixSpinner;
    private TextInputLayout firstNameInputLayout, middleNameInputLayout, lastNameInputLayout
          , remarkTextInputLayout,callerCntctNoInputLayout,cntctNoInputLayout,addressInputLayout;
    private TextInputEditText firstNameEditText, middleNameEditText, lastNameEditText,remarkEditText,callerCntctNoDateEditText,cntctNoEditText,addressEditText;
    private String firstName, middleName, lastName, lastBillAmount, arrearAmt, mobileNo, address, consumerNoStr, emailStr,subzoneIdValueStr,zoneNameStr,
         fullName, mobStr = "",groupStr;

    private int hdnTheft, lblSAPhase, zoneStr;
    private RadioButton complaintRadioButton, requestRadioButton,enquiryRadioButton;
    private RadioGroup radioGroup;
    String radioBtnTag="";
    private ArrayAdapter zoneAdapter, subZoneAdapter;
    private AppCompatSpinner zoneSpinner, subzoneSpinner;
    private ZoneModel zoneModel;
    private SubZoneModel subZoneModel;
    private ArrayList<String> zoneList, subZoneList, complaintTypeList, complaintSubTypeList;
    private List<ZoneModel> zoneModelList;
    // rest parameters
    private String originStr, originIdStr, complaintTypeStr, complaintTypeIdStr, complaintSubTypeStr, complaintSubTypeIdStr, sourceTypeStr, sourceTypeIdStr,
            priorityStr, informDateStr, remarkStr, meterNoStr, yearMonthStr, hdnYearMonth, hdnBaStr, sendSmsStr, namePrefixStr, firstNameStr, meterNo,
            middleNameStr, lastNameStr, complaintForStr, oldComplaintNoStr, empCodeStr, ipAddressStr, zoneId, subZoneId, compOriginStr,cntctNoStr,callerCntctNoStr,zoneIdValueStr,fullNameStr;

    private String wServiceNo = "000000000000";

    private int complaintOriginId, complaintTypeId, complaintSubTypeId, sourceTypeId;

    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String jsonResponse = "", jsonResponseDateTime = "", complaintTypeName, complaintSubTypeName,jsonResponseReqEnq="";
    private RealmOperations realmOperations;

    private ComplaintTypeModel complaintTypeModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private ComplaintSourceModel complaintSourceModel;
    private ArrayAdapter originAdapter, meterNumberAdapter, complaintTypeAdapter, complaintSubTypeAdapter, sourceTypeAdapter;
    String statusStr="",bundlezoneId="",bundlesubZoneId="",bundlemobileNo="",bundleaddress="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nccomplaint_registration);
        mCon = this;

        realmOperations = new RealmOperations(mCon);
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        if (connection.isConnectingToInternet()) {
            GetDateTime getDateTime = new GetDateTime();
            getDateTime.execute();
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        empCodeStr = empcode;
        registerButton = findViewById(R.id.registerButton);
        complaintTypeSpinner = findViewById(R.id.typeSpinner);
        complaintSubTypeSpinner = findViewById(R.id.subTypeSpinner);
        zoneSpinner = findViewById(R.id.ZoneSpinner);
        subzoneSpinner = findViewById(R.id.GroupSpinner);
        namePrefixSpinner = findViewById(R.id.namePrefixSpinner);

        firstNameInputLayout = findViewById(R.id.firstNameInputLayout);
        middleNameInputLayout = findViewById(R.id.middleNameInputLayout);
        lastNameInputLayout = findViewById(R.id.lastNameInputLayout);
        callerCntctNoInputLayout = findViewById(R.id.callerCntctNoInputLayout);
        cntctNoInputLayout = findViewById(R.id.cntctNoInputLayout);
        addressInputLayout = findViewById(R.id.addressInputLayout);
        remarkTextInputLayout = findViewById(R.id.remarkTextInputLayout);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        middleNameEditText = findViewById(R.id.middleNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        callerCntctNoDateEditText = findViewById(R.id.callerCntctNoDateEditText);
        cntctNoEditText = findViewById(R.id.cntctNoEditText);
        addressEditText = findViewById(R.id.addressEditText);
        remarkEditText = findViewById(R.id.remarkEditText);
        radioGroup = findViewById(R.id.radioGroup);
        complaintRadioButton = findViewById(R.id.complaintRadioButton);
        requestRadioButton = findViewById(R.id.requestRadioButton);
        enquiryRadioButton = findViewById(R.id.enquiryRadioButton);

   /*     if(PreferenceUtil.getSiteEng().equals("SiteEng")){
            complaintTypeList = realmOperations.fetchComplaintTypeBySE();
        }else{*/
            complaintTypeList = realmOperations.fetchComplaintTypeForNC();
        //}
        complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintTypeList);
        complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintTypeSpinner.setAdapter(complaintTypeAdapter);

        String zoneIdLists = PreferenceUtil.getZone();
        List<String> zoneIdList = Arrays.asList(zoneIdLists.split(","));
        // Log.d("check", String.valueOf(zoneIdList));
        ArrayList<String> zoneName = new ArrayList<>();
        zoneName=realmOperations.fetchZone();
        zoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, zoneName);
        //Log.d("check", String.valueOf(zoneList));
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneSpinner.setAdapter(zoneAdapter);

        complaintTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                complaintTypeName = complaintTypeSpinner.getSelectedItem().toString();

                complaintTypeModel = realmOperations.fetchComplaintTypeByName(complaintTypeName);
                complaintTypeId = complaintTypeModel.getCMTM_ID();
                complaintTypeIdStr = String.valueOf(complaintTypeId);

                Log.d("check", "complaint id " + complaintTypeIdStr);

                complaintSubTypeList = realmOperations.fetchComplaintSubType(complaintTypeId);
                complaintSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintSubTypeList);
                complaintSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                complaintSubTypeSpinner.setAdapter(complaintSubTypeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String zoneName = zoneSpinner.getSelectedItem().toString();
                zoneModel = realmOperations.fetchZoneByName(zoneName);
                zoneId = String.valueOf(zoneModel.getBUM_BU_ID());
                zoneIdValueStr = String.valueOf(zoneId);

                subZoneList = realmOperations.fetchSubZoneById(Integer.parseInt(zoneId));
                ArrayList<String> subZone = new ArrayList<>();
               // subZone.add("All");
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
                String subZone = subzoneSpinner.getSelectedItem().toString();
                subZoneModel = realmOperations.fetchSubZoneByName(subZone);
                try {
                    if (subZone.equals("All")) {
                        subZoneId = "-1";
                    } else {
                        subZoneId = String.valueOf(subZoneModel.getPCM_PC_ID());
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
        complaintSubTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                complaintSubTypeName = complaintSubTypeSpinner.getSelectedItem().toString();
                complaintSubTypeModel = realmOperations.fetchComplaintSubTypeByName(complaintSubTypeName);
                complaintSubTypeId = complaintSubTypeModel.getCOMPLAINTSUBTYPEID();
                complaintSubTypeIdStr = String.valueOf(complaintSubTypeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaintTypeStr = complaintTypeSpinner.getSelectedItem().toString().trim();
                complaintSubTypeStr = complaintSubTypeSpinner.getSelectedItem().toString().trim();
                namePrefixStr = namePrefixSpinner.getSelectedItem().toString();
                firstNameStr = firstNameEditText.getText().toString().trim();
                middleNameStr = middleNameEditText.getText().toString().trim();
                lastNameStr = lastNameEditText.getText().toString().trim();
                remarkStr = remarkEditText.getText().toString().trim();
                callerCntctNoStr=callerCntctNoDateEditText.getText().toString();
                cntctNoStr=cntctNoEditText.getText().toString();
                address=addressEditText.getText().toString();
                zoneNameStr=zoneSpinner.getSelectedItem().toString();
                groupStr=subzoneSpinner.getSelectedItem().toString();
                fullNameStr = firstNameStr + " " + middleNameStr + " " + lastNameStr;
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == complaintRadioButton.getId()) {
                    radioBtnTag = "C";
                } else if (selectedId == requestRadioButton.getId()) {
                    radioBtnTag = "2";
                } else if (selectedId == enquiryRadioButton.getId()) {
                    radioBtnTag = "1";
                }
                validateComplaintRequest();
            }
        });
    }

    // Validate Complaint Request
    private void validateComplaintRequest() {
        //String MobilePattern = "[0-9]{10}";
        String MobilePattern = "^[7-9][0-9]{9}$";
        boolean  isValidFirstName = false, isValidAddress = false,
                isValidMiddleName = false, isValidLastName = false, isValidRemark = false,isValidcallerCnctNo=false,isValidCntctNo=false,isValidGroupName=false,isValidZoneName=false;

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(firstNameStr)) {
            firstNameInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            firstNameInputLayout.setError(null);
            isValidFirstName = true;
        }

        /*if (TextUtils.isEmpty(lastNameStr)) {
            lastNameInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            isValidLastName = false;
        } else {
            lastNameInputLayout.setError(null);
            isValidLastName = true;
        }*/
        if(callerCntctNoStr.toString().matches(MobilePattern)) {
            callerCntctNoInputLayout.setError(null);
            isValidcallerCnctNo=true;

        }else {
            callerCntctNoInputLayout.setError("Please enter valid Caller Contact Number");
            isValidcallerCnctNo=false;
        }

        /*if(cntctNoStr.toString().matches(MobilePattern)) {
            cntctNoInputLayout.setError(null);
            isValidCntctNo=true;

        }else{
            cntctNoInputLayout.setError("Please enter valid Contact No.");
            isValidCntctNo=false;
        }*/

        if(TextUtils.isEmpty(address)) {
            addressInputLayout.setError("Please enter Address.");
            isValidAddress=false;
        }else{
            addressInputLayout.setError(null);
            isValidAddress=true;
        }

        if (TextUtils.isEmpty(remarkStr)) {
            remarkTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        }else if (remarkStr.toString().length()>160) {
            remarkTextInputLayout.setError("Remarks 160 Character Only");
            isValidRemark = false;
        } else {
            remarkTextInputLayout.setError(null);
            isValidRemark = true;
        }

        if (TextUtils.isEmpty(zoneNameStr)) {
            Toast.makeText(mCon,"Zone should not be empty",Toast.LENGTH_SHORT);
            isValidZoneName = false;
        } else {
            isValidZoneName = true;
        }
        if (TextUtils.isEmpty(groupStr)) {
            Toast.makeText(mCon,"Group should not be empty",Toast.LENGTH_SHORT);
            isValidGroupName = false;
        } else {
            isValidGroupName = true;
        }
        if ( isValidFirstName &&
                isValidRemark&&isValidcallerCnctNo&&isValidZoneName&&isValidGroupName&&isValidAddress) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy h:mm a");
            String date = sdf.format(Calendar.getInstance().getTime());

                if(radioBtnTag=="C"){
                    String params[] = new String[12];

                    params[0] = wServiceNo;
                    params[1] = fullNameStr;
                    params[2] = String.valueOf(complaintTypeId);
                    params[3] = String.valueOf(complaintSubTypeId);
                    params[4] = remarkStr;
                    params[5] = zoneIdValueStr;
                    params[6] = subzoneIdValueStr;
                    params[7] = callerCntctNoStr;
                    params[8] = address;
                    params[9] = complaintTypeName;
                    params[10] = empcode;
                    params[11] = "123456";


            Log.d("check", Arrays.toString(params));

            if (connection.isConnectingToInternet()) {
                ComplaintRegister complaintRegister = new ComplaintRegister();
                complaintRegister.execute(params);
            }
            }else if(radioBtnTag!="C"){
                    registerViaAnotherOptn();
                } else {
                Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ComplaintRegister extends AsyncTask<String, Void, Void> {
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
                paraName[0] = "W_ServiceNo";
                paraName[1] = "name";   // complaint type Id
                paraName[2] = "W_Comptype";     // complaint date (current date)
                paraName[3] = "W_Compreason";   // complaint Subtype Id
                paraName[4] = "W_Remarks";    // remark
                paraName[5] = "W_Zone";   // meter no
                paraName[6] = "W_PC";  // name prefix
                paraName[7] = "w_CallerContactNo";   // first name
                paraName[8] = "W_Address";  // middle name
                paraName[9] = "SType";   // last name
                paraName[10] = "W_Modifiedby";   // complaint origin Id
                paraName[11] = "W_IPadd";   // complaint for value

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.NCComplaintRegistration, params, paraName);
                Log.d("check", "json : " + jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
               // NCComplaintRegisterResponseModel enums = gson.fromJson(jsonResponse, NCComplaintRegisterResponseModel.class);
                JSONObject jsonObj = new JSONObject(jsonResponse);

                String complaintNo =jsonObj.getString("div_Cmsg");
                String errormsg =jsonObj.getString("Errormsg");

                if (complaintNo != null) {
                    Toast.makeText(mCon, "Complaint no." + complaintNo + " generated succesfully", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(mCon, errormsg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "" + jsonResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCComplaintRegistrationActivity", "NC Registration button click", error);

            }
            progress.dismiss();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ComplaintRegisterViaReqEnq extends AsyncTask<String, Void, Void> {
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
                String paraName[] = new String[11];
                paraName[0] = "CallTypeId";
                paraName[1] = "ComplaintTypeId";   // complaint type Id
                paraName[2] = "ComplaintSubTypeId";     // complaint date (current date)
                paraName[3] = "w_CallerContactNo";   // complaint Subtype Id
                paraName[4] = "Name";    // remark
                paraName[5] = "Address";   // meter no
                paraName[6] = "Remarks";   // meter no
                paraName[7] = "ZoneId";  // name prefix
                paraName[8] = "GroupId";   // first name
                paraName[9] = "Emp_Code";  // middle name
                paraName[10] = "IP";   // last name

                jsonResponseReqEnq = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.NComplaint_CallRecordsNoConsumer, params, paraName);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                JSONObject jsonObj = new JSONObject(jsonResponseReqEnq);
                String callNo =jsonObj.getString("div_Cmsg");
                if (callNo != null) {
                    Toast.makeText(mCon, callNo, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(mCon, "Error Found", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "" + jsonResponseReqEnq, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCComplaintRegistrationActivity", "Register Via Another Optn", error);
            }
            progress.dismiss();
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class GetDateTime extends AsyncTask<Void, Void, Void> {
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
        protected Void doInBackground(Void... voids) {
            try {
                jsonResponseDateTime = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.ServerDateTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SimpleDateFormat")
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                String[] enums = gson.fromJson(jsonResponseDateTime, String[].class);
                if (enums.length > 0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String currentDate = sdf.format(Calendar.getInstance().getTime());

                    int selectedHour = Integer.parseInt(enums[3]);
                    String AM_PM;

                    Calendar cTime = Calendar.getInstance();

                    SimpleDateFormat format;
                    if (DateFormat.is24HourFormat(mCon)) {
                        format = new SimpleDateFormat("HH:mm");
                    } else {
                        format = new SimpleDateFormat("hh:mm");
                    }
                    cTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    cTime.set(Calendar.MINUTE, Integer.parseInt(enums[4]));
                    Date date = cTime.getTime();
                    String dateResult = format.format(date);

                    if (selectedHour < 12) {
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }

                    informDateStr = currentDate + " " + dateResult + " " + AM_PM;
                    Log.d("check", informDateStr);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault());
                    informDateStr = sdf.format(Calendar.getInstance().getTime());
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCComplaintRegistrationActivity", "Get server date time", error);
            }
            progress.dismiss();
        }
    }

    public void registerViaAnotherOptn(){

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[11];
        params[0] = radioBtnTag ;
        params[1] = String.valueOf(complaintTypeId);
        params[2] = String.valueOf(complaintSubTypeId);
        params[3] = cntctNoStr;
        params[4] = fullNameStr;
        params[5] = address;
        params[6] = remarkStr;
        params[7] = zoneIdValueStr;
        params[8] = subzoneIdValueStr;
        params[9] = empcode;
        params[10] = "123456";

        Log.d("check", Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            ComplaintRegisterViaReqEnq complaintRegisterViaReqEnq = new ComplaintRegisterViaReqEnq();
            complaintRegisterViaReqEnq.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }
}
