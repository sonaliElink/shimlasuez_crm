package elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.IssueMeterToFixerActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Model.DownloaddFixerData;
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Model.FixerDataListModel;
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Model.IssueMeterFixerResponseModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class IssueMeterToFixerDetailedActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextView consumerNoTextView, zoneTextView, subZoneTextView, requestNoTextView, contactNoTextView, nameAddressTextView, requestSubTypeTextView, forwardToMmgDateTextView, oldMeterNoTextView,
            rejectReasonTextView, observationTextView, allocateButton;
    private TextInputEditText newMeterNoEditText, remarkEditText;
    private AppCompatSpinner fixerNameSpinner, vendorSpinner;
    private TextInputLayout newMeterNoTextInputLayout, remarkTextInputLayout;
    String newMeterNoStr, remarkStr = "", fixerNameStr, vendorStr;
    private Context mCon;
    private LinearLayout linerFixerName, linerVendorName;
    private MaterialDialog progress;
    private String jsonResponse = "", jsonFixerDataResponse = "", requestTypeId = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    String V_zone = "", V_subZone = "", V_consumerNo = "", V_reqNo = "", V_contactNo = "", V_nameAdd = "", V_oldMtrNo = "", V_reqSubType = "", V_fwdmmgDt = "", V_rejRes = "",
            V_observ = "", oldeMeterNoStr = "",currentDate = "";
    private RealmOperations realmOperations;

    String fixerName = "", fixerNameId = "", V_bu = "", V_pc = "", V_orm = "", empId = "", macAddress = "", V_sourceType = "", SOURCETYPE = "";

    private List<FixerDataListModel> fixerDataListModelsList;
    ArrayList<String> fixerStringArrayList, fixerStringIdList;
    private ArrayAdapter fixerDataAdapter;

    private FixerDataListModel fixerDataListModel;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_meter_to_fixer_detailed);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
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

        Bundle bundle = getIntent().getExtras();
        V_zone = bundle.getString("zone");
        V_subZone = bundle.getString("subZone");
        V_consumerNo = bundle.getString("consumerNo");
        V_reqNo = bundle.getString("reqNo");
        V_contactNo = bundle.getString("contactNo");
        V_nameAdd = bundle.getString("nameAdd");
        V_oldMtrNo = bundle.getString("oldMtrNo");
        V_reqSubType = bundle.getString("reqSubType");
        V_fwdmmgDt = bundle.getString("fwdmmgDt");
        V_rejRes = bundle.getString("rejRes");
        V_observ = bundle.getString("observ");
        V_bu = bundle.getString("bu");
        V_pc = bundle.getString("pc");
        V_orm = bundle.getString("orm");
        V_sourceType = bundle.getString("sourceType");//MC

        V_sourceType = V_sourceType.substring(0, 1);
        //V_sourceType = String.valueOf(V_sourceType.charAt(0));


        consumerNoTextView = findViewById(R.id.consumerNoTextView);
        zoneTextView = findViewById(R.id.zoneTextView);
        subZoneTextView = findViewById(R.id.subZoneTextView);
        requestNoTextView = findViewById(R.id.requestNoTextView);
        contactNoTextView = findViewById(R.id.contactNoTextView);
        nameAddressTextView = findViewById(R.id.nameAddressTextView);
        requestSubTypeTextView = findViewById(R.id.requestSubTypeTextView);
        forwardToMmgDateTextView = findViewById(R.id.forwardToMmgDateTextView);
        oldMeterNoTextView = findViewById(R.id.oldMeterNoTextView);
        rejectReasonTextView = findViewById(R.id.rejectReasonTextView);
        observationTextView = findViewById(R.id.observationTextView);
        newMeterNoEditText = findViewById(R.id.newMeterNoEditText);
        remarkEditText = findViewById(R.id.remarkEditText);
        fixerNameSpinner = findViewById(R.id.fixerNameSpinner);
        vendorSpinner = findViewById(R.id.vendorSpinner);
        allocateButton = findViewById(R.id.allocateButton);
        newMeterNoTextInputLayout = findViewById(R.id.newMeterNoTextInputLayout);
        remarkTextInputLayout = findViewById(R.id.remarkTextInputLayout);
        linerFixerName = findViewById(R.id.linerFixerName);
        linerVendorName = findViewById(R.id.linerVendorName);

        requestTypeId = UtilitySharedPreferences.getPrefs(mCon, AppConstant.REQUESTTYPEID);
        empId = UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE);
        try {
            empId = aes.decrypt( empId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        macAddress = PreferenceUtil.getMac();
        allocateButton.setOnClickListener(this);
      /*  allocateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateDetails();
            }
        });
*/
        fnLoad();

        fixerDataList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        currentDate = simpleDateFormat.format(new Date());

    }

    private void setFixerNameSpinner() {


        fixerDataListModelsList = realmOperations.fetchFixerDataList();

        fixerStringArrayList = new ArrayList<>();
        fixerStringIdList = new ArrayList<>();
        fixerStringIdList.add("Select");
        for (FixerDataListModel fixerDataListModel : fixerDataListModelsList) {
            fixerStringArrayList.add(fixerDataListModel.getNAME());
            fixerStringIdList.add(fixerDataListModel.getEM_EMP_CODE());
            //Log.d("check", String.valueOf(zoneName));

        }
        ArrayList<String> fixerData = new ArrayList<>();
        fixerData.add("Select");
        fixerData.addAll(fixerStringArrayList);
        fixerDataAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, fixerData);
        fixerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fixerNameSpinner.setAdapter(fixerDataAdapter);

        fixerNameSpinner.setOnItemSelectedListener(this);

    }

    private void fixerDataList() {

        FixerDataListModel fixerDataListModelExit = realmOperations.getFixerDataListModelExit();

        if (connection.isConnectingToInternet()) {
            if (fixerDataListModelExit == null) {
                FixerData fixerData = new FixerData();
                fixerData.execute();

                progress = new MaterialDialog.Builder(mCon)
                        .title(R.string.loading)
                        .content(R.string.loading)
                        .progress(true, 0)
                        .autoDismiss(false)
                        .canceledOnTouchOutside(false)
                        .widgetColorRes(R.color.colorPrimary)
                        .show();
            } else {
                setFixerNameSpinner();

            }
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.fixerNameSpinner: {
                fixerName = fixerNameSpinner.getSelectedItem().toString();
                String poss = String.valueOf(fixerNameSpinner.getSelectedItemPosition());


                if (fixerName.equalsIgnoreCase("Select")) {
                    fixerNameId = "0";
                } else {


                    fixerDataListModel = realmOperations.fetchFixerId(fixerName);
                    fixerNameId = String.valueOf(fixerDataListModel.getEM_EMP_CODE());
                }
            }
            break;

            default:
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allocateButton:
                if (checkValidation()) {
                    saveIssuetoMeterFixerDetail();
                }
                break;

            default:
                break;
        }
    }

    private boolean checkValidation() {
        fixerName = fixerNameSpinner.getSelectedItem().toString();
        remarkStr = remarkEditText.getText().toString().trim();

        if (fixerName.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, R.string.please_select_fixer_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (remarkStr.equalsIgnoreCase("")) {
            remarkTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(mCon, R.string.please_enter_remark, Toast.LENGTH_SHORT).show();

            return false;
        } else {
            remarkTextInputLayout.setError(null);

        }

        return true;
    }


    private class FixerData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                jsonFixerDataResponse = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.MMG_ContractorAndMMGEmp);
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                DownloaddFixerData[] enums = gson.fromJson(jsonFixerDataResponse, DownloaddFixerData[].class);
                if (enums.length > 0) {// && !enums[0].getMTRSTATUS().equalsIgnoreCase("Null")
                    realmOperations.deleteFixerDataTable();
                    for (DownloaddFixerData downloaddFixerData : enums) {
                        String emEmpCode = downloaddFixerData.getEM_EMP_CODE();
                        String name = downloaddFixerData.getNAME();

                        FixerDataListModel model = new FixerDataListModel(emEmpCode, name);
                        realmOperations.addFixerData(model);
                    }
                }
                progress.dismiss();

            } catch (Exception e) {
                progress.dismiss();

            }
            setFixerNameSpinner();

        }
    }


    /*  <w_str>string</w_str>
      <ServiceTOD>string</ServiceTOD>
      <W_meterNo>string</W_meterNo>
      <oldmeterno>string</oldmeterno>
      <W_Phase>string</W_Phase>
      <W_TOD>string</W_TOD>
      <W_Mfg>string</W_Mfg>
      <w_type>string</w_type>
      <w_fixer>string</w_fixer>
      <w_fixername>string</w_fixername>
      <Phase>string</Phase>
      <TODType>string</TODType>
      <ServiceType>string</ServiceType>
      <ServiceNo>string</ServiceNo>
      <ReferenceNo>string</ReferenceNo>
      <MeterSeqCode>string</MeterSeqCode>
      <IssueDate>string</IssueDate>
      <NewMeterNo>string</NewMeterNo>
      <MeterSize>string</MeterSize>
      <ServiceTODFlag>string</ServiceTODFlag>
      <BU>string</BU>
      <PC>string</PC>
      <w_remarks>string</w_remarks>
      <TransactionId>string</TransactionId>
      <Observation>string</Observation>
      <Vendor>string</Vendor>
      <MeterIndication>string</MeterIndication>
      <EmpCode>string</EmpCode>
      <IP>string</IP>*/

    private void validateDetails() {
        boolean isValidnewMeterNo = false;
        newMeterNoStr = newMeterNoEditText.getText().toString();
        fixerNameStr = fixerNameSpinner.getSelectedItem().toString().trim();
        vendorStr = vendorSpinner.getSelectedItem().toString().trim();

        if (TextUtils.isEmpty(newMeterNoStr)) {
            newMeterNoTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            newMeterNoTextInputLayout.setError(null);
            isValidnewMeterNo = true;
        }

        if (isValidnewMeterNo) {
            if (newMeterNoStr != "") {
                finish();
            }
        } else {
            Toast.makeText(mCon, "Please fill Meter no.", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveIssuetoMeterFixerDetail() {
        String params[] = new String[29];

                /*
       <w_str>""</w_str>
      <ServiceTOD>"STANDARD"</ServiceTOD>
      <W_meterNo>""</W_meterNo>
      <oldmeterno>responce</oldmeterno>
      <W_Phase>""</W_Phase>
      <W_TOD>""</W_TOD>
      <W_Mfg>""</W_Mfg>
      <w_type>REF_NO ch 1 character"M/C"</w_type>
      <w_fixer>0</w_fixer>
      <w_fixername>""</w_fixername>
      <Phase>0</Phase>
      <TODType>"-1"</TODType>
      <ServiceType>"C" filter data request type </ServiceType>
      <ServiceNo>consumer number</ServiceNo>
      <ReferenceNo>referance number</ReferenceNo>
      <MeterSeqCode>1</MeterSeqCode>
      <IssueDate>today date"16-Jul-2020 06:47 PM"</IssueDate>
      <NewMeterNo>""</NewMeterNo>
      <MeterSize>""</MeterSize>
      <ServiceTODFlag>0</ServiceTODFlag>
      <BU>BU</BU>
      <PC>PC</PC>
      <w_remarks>remark user input</w_remarks>
      <TransactionId>1</TransactionId>
      <Observation>OCRM_ID</Observation>
      <Vendor>spinner id</Vendor>
      <MeterIndication>1</MeterIndication>
      <EmpCode>1000</EmpCode>
      <IP>mac address</IP>

      */

        params[0] = "";
        params[1] = "STANDARD";
        params[2] = "";
        params[3] = V_oldMtrNo;
        params[4] = "";
        params[5] = "";
        params[6] = "";
        params[7] = V_sourceType;
        params[8] = "0";
        params[9] = "";
        params[10] = "0";
        params[11] = "-1";
        params[12] = requestTypeId;
        params[13] = V_consumerNo;
        params[14] = V_reqNo;
        params[15] = "1";
        params[16] = currentDate;
        params[17] = "";
        params[18] = "";
        params[19] = "0";
        params[20] = V_bu;
        params[21] = V_pc;
        params[22] = remarkStr;
        params[23] = "1";
        params[24] = V_orm;
        params[25] = fixerNameId;
        params[26] = "1";
        params[27] = empId;
        params[28] = macAddress;


        if (connection.isConnectingToInternet()) {
            IssueToMeterFixerDetailsSaveAsyncTask issueToMeterFixerDetailsSaveAsyncTask = new IssueToMeterFixerDetailsSaveAsyncTask();
            issueToMeterFixerDetailsSaveAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class IssueToMeterFixerDetailsSaveAsyncTask extends AsyncTask<String, Void, Void> {

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
                String paraNames[] = new String[29];

                paraNames[0] = "w_str";
                paraNames[1] = "ServiceTOD";
                paraNames[2] = "W_meterNo";
                paraNames[3] = "oldmeterno";
                paraNames[4] = "W_Phase";
                paraNames[5] = "W_TOD";
                paraNames[6] = "W_Mfg";
                paraNames[7] = "w_type";
                paraNames[8] = "w_fixer";
                paraNames[9] = "w_fixername";
                paraNames[10] = "Phase";
                paraNames[11] = "TODType";
                paraNames[12] = "ServiceType";
                paraNames[13] = "ServiceNo";
                paraNames[14] = "ReferenceNo";
                paraNames[15] = "MeterSeqCode";
                paraNames[16] = "IssueDate";
                paraNames[17] = "NewMeterNo";
                paraNames[18] = "MeterSize";
                paraNames[19] = "ServiceTODFlag";
                paraNames[20] = "BU";
                paraNames[21] = "PC";
                paraNames[22] = "w_remarks";
                paraNames[23] = "TransactionId";
                paraNames[24] = "Observation";
                paraNames[25] = "Vendor";
                paraNames[26] = "MeterIndication";
                paraNames[27] = "EmpCode";
                paraNames[28] = "IP";


                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_IssueMeterToFixerSave, params, paraNames);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {

             //   { 'Success': 'True','Message': '','ErrorMessage': ' This consumer is already issue to fixer.','ValidationMessage': '1'}
                IssueMeterFixerResponseModel enums = gson.fromJson(jsonResponse, IssueMeterFixerResponseModel.class);
                if (enums.getSuccess() != null || !enums.getMessage().equals("")) {
                    //int count = Integer.parseInt(enums.getCount());
                    if (enums.getSuccess().equals("True")) {

                        //    "Fixer allocated successfully";
                        Toast.makeText(mCon, R.string.accept_mmg_successfully, Toast.LENGTH_LONG).show();
                        startBackActivity();
                    } else {
                        Toast.makeText(mCon, enums.getErrorMessage(), Toast.LENGTH_LONG).show();
                        startBackActivity();
                    }
                } else {
                    Toast.makeText(mCon, enums.getErrorMessage(), Toast.LENGTH_LONG).show();
                   finish();
                }
            } catch (Exception e) {
                Log.e("IssuemeterToFixerExpn", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "IssueMeterToFixerDetailedActivity", "MMG_IssueMeterToFixerShow", error);
            }
            progress.dismiss();
        }
    }

    private void startBackActivity() {
        Intent intent = new Intent(IssueMeterToFixerDetailedActivity.this, IssueMeterToFixerActivity.class);
        startActivity(intent);
        finish();
    }


    public void fnLoad() {

        consumerNoTextView.setText(V_consumerNo);
        zoneTextView.setText(V_zone);
        subZoneTextView.setText(V_subZone);
        requestNoTextView.setText(V_reqNo);
        contactNoTextView.setText(V_contactNo);
        nameAddressTextView.setText(V_nameAdd);
        requestSubTypeTextView.setText(V_reqSubType);
        forwardToMmgDateTextView.setText(V_fwdmmgDt);
        oldMeterNoTextView.setText(V_oldMtrNo);
        rejectReasonTextView.setText(V_rejRes);
        observationTextView.setText(V_observ);

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
