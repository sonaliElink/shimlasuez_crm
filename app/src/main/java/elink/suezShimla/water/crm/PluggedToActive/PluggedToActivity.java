package elink.suezShimla.water.crm.PluggedToActive;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.ActiveToPlugged.ActiveToPluggedActivity;
import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitRequestModel;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterStatusModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGObersvationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MStatusObservationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterObservationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterStatusModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGCustDetModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.ConnectionStatusModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import io.realm.RealmResults;

public class PluggedToActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ConnectionDetector connection;
    private Invoke invServices;
    private Button submitbtn;
    String frgPass = "", remarkStr = "";
    private int reqcode_img = 1010, plugImgCode = 1011, photoMarkedCode = 1012, otherPhotoCode = 1013;

    private Calendar fromDateCalendar, toDateCalendar;
    static String serialNoStr = "", contList = "", pagename = "", mtrSizeId = "";
    private ArrayList<String> documentImages = new ArrayList<>();
    private ArrayList<String> documentSubtype = new ArrayList<>();
    private boolean flag = false;
    LinearLayout ll_upload_document, liner_FinalStatus;
    private String updateJsonResponse = "", jsonPhotoResponse = "", MAC = "";

    Uri imageUri;

    Bitmap bitmap;
    Bitmap thumbnail, optionalThumbnail, rotatedBitmap, optionalBitmap;
    String jsonSiteVisitResponse = "", base64String = "", docSource = "NC", MI_N_METEROWNERSHIP, distributionBase64String = "", photoMarkedBase64String = "", otherPhotoBase64String = "";


    String applicationNumberStr = "", address = "", cStatus = "", pastReading = "";

    private Gson gson;

    private MMGMeterStatusModel mmgMeterStatusModel;
    private MMGObersvationModel mmgObersvationModel;

    String mfgNameRecvd = "", serialNoRecvd = "", installDtRecvd = "", meterSizeRecvd = "", sealNoRecvd = "", pastMeterReadingRecvd = "",
            meterStatusStr = "", meterTypeStr = "", finalStatusSelected = "", observationSelectedName = "", connSizeStr = "", reasonOfReplacementName = "";
    private MaterialDialog progressDisconMeter;
    public static final int RequestPermissionCode = 1;
    Spinner finalStatusSpinner, observationSpinner;
    Uri photoURI;
    private File destination = null, destinationOptional = null;
    String MI_METERINSTALLID = "", MI_ACTION = "", MI_CONSUMER = "", MI_BU = "", MI_PC = "", MI_REFNO = "", MI_O_SIZE = "", O_SIZE = "", MI_O_METER = "", MI_O_MAKE = "", MI_O_PREVIOUSREADING = "", MI_O_FINALREADING = "", MI_O_FINALSTATUS = "", MI_O_REASON = "", dataFound, MI_O_METERTYPE = "",
            MI_N_MAKE = "", MI_N_SIZE = "", MI_N_SEAL = "", MI_N_METER = "", MI_INSTALLATIONDATE = "", MI_N_INITIALREADING = "", MI_N_METERTYPE = "", MI_N_METERLOCATION = "", MI_N_ISPROTECTED = "", MI_PROPERTYTAXNO = "", MI_N_ISMETERHANDOVER = "",
            MI_CONTRACTOR = "", MI_CONTRACTOREMP = "", MI_N_ISMATERIALHANDOVER = "", MI_PCCBEDDINGLEN = "", MI_PCCBEDDINGWID = "",
            MI_PCCBEDDINGDEP = "", MI_ROADCUTTINGTYPE = "", MI_ROADCUTTINGLEN = "", MI_ROADCUTTINGWID = "", MI_ROADCUTTINGDEP = "", MI_FROMNODE = "",
            MI_TONODE = "", MI_REGMOBILE = "", MI_ALTMOBILE = "", MI_GIS = "", MI_DMA = "", MI_SR = "", MI_MODIFIEDBY = "", MI_MODIFIEDDATE = "", MI_IP = "", MI_AUTHENTICATEDATE = "",
            MI_AUTHREJECTBY = "", MI_REJECTEDDATE = "", MI_STATUS = "", MI_ISACTIVE = "", MI_XMLMATERIAL = "", MI_XMLCIVIL = "", MI_O_OBSERVATION = "",
            MI_SOURCE = "", MI_ISCOMMISSIONED = "", MI_CONTRACTOROTHER = "", MI_CONTRACTOREMPOTHER = "", MI_N_DIGIT = "", MSRID = "", MI_DATA_FOUND, MI_N_METER_BOX_MAKE = "", MI_N_SEAL_MAKE = "";

    String applicationDateStr = "", appointmentDateStr = "", firstNameStr = "", fatherStr = "", middleStr = "", lastNameStr = "", addresStr = "", landmarkStr = "", location = "",
            streetLocalityStr = "", districtStr = "", cityStr = "", postalCodeStr = "", mobileNoStr = "", lanLineStr = "", emailStr = "", salutationStr = "";
    String connectionTypeId = "", connectionCategoryId = "", propertyTypeId = "", connectionPurposeId = "", noOfFloorStr = "", populationStr = "", noOfDwellingStr = "", noOfBedsStr = "";
    String unAuthorizedConnectionId = "", civilConsWithCcmcWaterId = "", noOfDwellingUnitId = "", isOccupierSecurityId = "", governmentEmployeeId = "";
    String technicalFeasibilityId = "", distanceNetworkLineSizeId = "", roadRestorationLengthId = "", roadTypeId = "", roadOwnerShipId = "", meterSanctionTypeId = "", lengthStr = "";
    String construction_completed = "", applied_dwelling_unit_has_connection = "",
            internal_network_available, disposal_of_water_available,
            rainwater_harvesting_system_available, any_existing_connection = "", add_consumer = "", meterSizeId = "", selectStorageCapacity, storageCapacity = "", storageCapacityType = "";
    String connection_feasibility, water_availability = "", road_cutting_required = "",
            distribution_pipeline = "", distribution_id = "", leftConsumerStr = "", rightConsumerStr = "", road_length_id = "", meterLocation = "", connection_size = "";
    String processDate = "", EMPCODE = "", sDivision = "", sSubDivision = "", sSection = "", sBU = "", sAppType = "", sType = "P", sSourceType = "", sFather = "", sMiddleName = "";
    String zoneId = "", wardId = "", MSRIDStr = "", SRIDStr = "", groupId = "", lotId = "", areaId = "";

    private ArrayAdapter meterTypeAdapter, meterReasonAdapter, finalStatusAdapter, observationAdapter;

    private ArrayAdapter meterObservationAdapter, meterStatusAdapter, connectionStatusAdapter;
    private ArrayList<String> meterStatusData = new ArrayList<>();

    RealmResults<ConnectionStatusModel> connectionStatusModels;
    ArrayList<String> connectionStatusList;
    String ConnStatusId = "";

    private RealmOperations realmOperations;
    String meterStatusId = "";
    RealmResults<MeterObservationModel> meterObservationModels;

    String meterTypeId = "", meterTypeIdStr = "", meterSttausName = "", makerCodeIdStr = "",
            meterStatusIdStr = "", finalReadingValStr = "", finalStsValStr = "", reasnFrReplacementValStr = "", meterReasonId,
            meterResonStr = "", meterReasonIdStr = "", finalStatsuId = "", finalStatusIdStr = "", coonectionLoad = "",
            makercodename = "", serachById = "", consumerNoStr = "", consumerNameStr = "", phonenum = "", meternum = "", radiobuttonValStr = "", refNoStr = "", BU, PC,
            meterObservationName = "",
            jsonCustomerDetailResponse = "", jsonMeterInstallSaveResponse = "", spinMeterObservation = "", nameObservationStr = "";
    private MeterObservationModel meterObservationModel;

    private MStatusObservationModel mStatusObservationModel;
    private ArrayList<String> meterObservationList = new ArrayList<>();
    ArrayList<MMGCustDetModel> customerDetailList = new ArrayList<>();
    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = new ArrayList<>();
    ArrayList<InstallDetails> installDetails = new ArrayList<>();
    ArrayList<MMGValidateDetails> validateDetailList = new ArrayList<>();
    private String imageName;
    File storageDir;
    private AppCompatSpinner meterStatusSpinner, readerObservationSpinner, connectionStatusSpinner;

    ArrayList<String> conStatus = new ArrayList<String>();
    ArrayList<String> msList = new ArrayList<String>();
    ArrayList<String> msValueList = new ArrayList<String>();
    ArrayList<String> moList = new ArrayList<String>();
    ArrayList<String> moValueList = new ArrayList<String>();

    private String encodedBase64Image = "", docName, docType, formulaValue;
    private String encodedBase64OptionalImage = "", docNameOptional, docTypeOptional, docTypeNameOptional;

    double newImageHeight = 512.0;
    int imageQuality = 65;
    String meterObservationId = "";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 11;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 12;

    private int rotation;
    String strLatitude = "00.0000000", strLongitude = "00.0000000", format = "";

    private EditText etconsumerNo, etConsumerName, edtMeterNo, etaddress, edtcontactnum, etDate, edtRemark, applicationNo, edtMeterReading, edPastReading;
    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;
    Context mCon;
    String statusName = "", spinMeterStatus;

    String currentDateTimeStr = "", fromDate = "", toDate = "", empCode = "", empCode1 = "", disconDate = "", finalmeter;
    private LinearLayout lldate;

    private ImageView uploadDoc, pluggedmeterDoc;
    private MaterialDialog progress, progressPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugged_to);
        mCon = this;
        realmOperations = new RealmOperations(mCon);

        aes = new AesAlgorithm();

        init();

    }

    private void init() {


        meterStatusSpinner = findViewById(R.id.pluggedStatusSpinner);
        connectionStatusSpinner = findViewById(R.id.connectionStatus);
        submitbtn = findViewById(R.id.submitButton);
        readerObservationSpinner = findViewById(R.id.pluggedObservationSpinner);
        edtRemark = findViewById(R.id.edtRemarks);
        liner_FinalStatus = findViewById(R.id.liner_FinalStatus);
        fromDate = getIntent().getStringExtra("fromDate");
        toDate = getIntent().getStringExtra("toDate");
        consumerNoStr = getIntent().getStringExtra("consnum");
        firstNameStr = getIntent().getStringExtra("firstName");
        consumerNameStr = getIntent().getStringExtra("conName");
        meternum = getIntent().getStringExtra("meternum");
        phonenum = getIntent().getStringExtra("contact");

        applicationNumberStr = getIntent().getStringExtra("applicationnum");
        address = getIntent().getStringExtra("address");
        cStatus = getIntent().getStringExtra("status");
        pastReading = getIntent().getStringExtra("MTRM_CURRENT_READING");


        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        processDate = simpleDateFormat.format(new Date());

        etDate = findViewById(R.id.etDate);
        etDate.setEnabled(false);
        etDate.setClickable(false);
        etDate.setText(processDate);
        lldate = findViewById(R.id.lldate);
        etconsumerNo = findViewById(R.id.etconsumerNo);
        etConsumerName = findViewById(R.id.etConsumerName);
        edtMeterNo = findViewById(R.id.edtMeterNo);
        applicationNo = findViewById(R.id.edapplicationnum);
        edtMeterReading = findViewById(R.id.edtMeterReading);

        etaddress = findViewById(R.id.edaddress);
        etaddress.setText(address);

        edtcontactnum = findViewById(R.id.edtcontactnum);
        edPastReading = findViewById(R.id.tvPastReading);

        etconsumerNo.setText(consumerNoStr);
        etConsumerName.setText(consumerNameStr);
        edtcontactnum.setText(phonenum);
        applicationNo.setText(applicationNumberStr);
        edPastReading.setText(pastReading);


        edtMeterNo.setText(meternum);
        uploadDoc = findViewById(R.id.uploadDoc);
        pluggedmeterDoc = findViewById(R.id.handovermeterDoc);

         /*       uploadDoc.setOnClickListener((View.OnClickListener) this);
                handovermeterDoc.setOnClickListener((View.OnClickListener) this);
                submitbtn.setOnClickListener((View.OnClickListener) this);*/

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        currentDateTimeStr = simpleDateFormat.format(new Date());


   /*     lldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PluggedToActivity.this, fromCalendarDate, fromDateCalendar.get(Calendar.YEAR), fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();

            }
        });*/


        //  Toast.makeText(this, ""+fromDate, Toast.LENGTH_SHORT).show();

        gson = new Gson();
        connection = new ConnectionDetector(this);
        invServices = new Invoke();

        empCode = UtilitySharedPreferences.getPrefs(this, AppConstant.EMPCODE);

        try {
            empCode = aes.decrypt(empCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RealmResults<MeterStatusModel> meterStatusModels = realmOperations.fetchMeterStatusNameID();
        for (MeterStatusModel model : meterStatusModels) {

            if (!model.getMSM_METERSTATUS_ID().equals("2")&& !model.getMSM_METERSTATUS_ID().equals("4") && !model.getMSM_METERSTATUS_ID().equals("5") && !model.getMSM_METERSTATUS_ID().equals("7") ) {
                msList.add(model.getMSM_METERSTATUS_NAME());
                msValueList.add(String.valueOf(model.getMSM_METERSTATUS_ID()));
            }
           /* msList.add(model.getMSM_METERSTATUS_NAME());
            msValueList.add(String.valueOf(model.getMSM_METERSTATUS_ID()));*/
        }

        meterStatusData.add(getResources().getString(R.string.select_meter_status));
        meterStatusData.addAll(msList);

        meterStatusAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, meterStatusData);
        meterStatusAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        meterStatusSpinner.setAdapter(meterStatusAdapter);

        ArrayList<String> observationData = new ArrayList<>();
        observationData.add(getResources().getString(R.string.select_meter_observation));

        meterObservationAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, observationData);
        meterObservationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        readerObservationSpinner.setAdapter(meterObservationAdapter);
        readerObservationSpinner.setSelection(0);


        connectionStatusModels = realmOperations.fetchConnectionStatus();
        connectionStatusList = new ArrayList<>();
        //  connectionStatusList.add("Select");
        for (int i = 0; i < connectionStatusModels.size(); i++) {
            ConnectionStatusModel csm = connectionStatusModels.get(i);
            connectionStatusList.add(csm.getDTM_DISCONN_TAG_NAME());
            if (i == 0) {
                ConnStatusId = csm.getDTM_DISCONN_TAG_ID();
                // Toast.makeText(mCon, ConnStatusId, Toast.LENGTH_SHORT).show();
            }
            // Toast.makeText(mCon, "status"+ connectionStatusList.get(i).toString(), Toast.LENGTH_SHORT).show();
        }
        Log.e("ststus", connectionStatusList.toString());

        connectionStatusAdapter = new ArrayAdapter(mCon, R.layout.spin_list, connectionStatusList);
        connectionStatusSpinner.setAdapter(connectionStatusAdapter);

        connectionStatusSpinner.setSelection(0);


        meterStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //strMeterStatus = meterStatusData.get(1);

                statusName = meterStatusSpinner.getSelectedItem().toString();


                spinMeterStatus = adapterView.getItemAtPosition(i).toString();
                try {
                    if (statusName.equals(getResources().getString(R.string.select_meter_status))) {
                        ArrayList<String> subAll = new ArrayList<>();
                        subAll.add(getResources().getString(R.string.select_meter_observation));

                        meterObservationAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, subAll);
                        meterObservationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        readerObservationSpinner.setAdapter(meterObservationAdapter);
                        //ashwini
                    }/*else if(statusName.equalsIgnoreCase("Normal Meter")){

                        if(strPastMeterStatus.equalsIgnoreCase("3")){

                          //  MessageWindow.messageWindow(mCon, getString(R.string.faluty_status_msg), "Alert");

                            meterStatusSpinner.setSelection(0);
                        }else {
                            setSpinnerValue();
                        }

                    }*/ else {

                        setSpinnerValue();

                    }

                } catch (Exception ex) {
                    Log.e("exe", ex.toString());

                    // Toast.makeText(getApplicationContext(), "Problem is: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    //message Last month
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                strMeterStatus = meterStatusData.get(1);
            }
        });





        readerObservationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                strMeterObservation = meterObservationData.get(i);
                nameObservationStr = readerObservationSpinner.getSelectedItem().toString();

                //   meterStatusModel = realmOperations.fetchMeterStatusByName(statusName);


                  spinMeterObservation = adapterView.getItemAtPosition(i).toString();

                if (!nameObservationStr.equals(getResources().getString(R.string.select_meter_observation))) {


                    // meterObservationId = Integer.parseInt(moValueList.get(readerObservationSpinner.getSelectedItemPosition()-1).toString());

                    meterObservationModel = realmOperations.fetchMeterObservationByIdMeterReading(nameObservationStr);
                    Log.d("meterObservationModel", "" + meterObservationModel);
                    meterObservationId = meterObservationModel.getDFM_CODE();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPhotoValidation()) {

                    Toast.makeText(mCon, "submitting...", Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < documentImages.size(); i++) {
                        //  Log.d("Running " + i, "doctype " + documentSubtype.get(i));
                        uploadPhoto(documentImages.get(i), consumerNoStr, applicationNumberStr, "", "", "E", "110", documentSubtype.get(i), empCode, MAC, remarkStr);

                    }
                    UpdateSiteVisitData();

                }

            }
        });

        uploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(PluggedToActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                } else if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(PluggedToActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                } else {

                    if (isTimeAutomatic(mCon)) {
                        cameraIntent();
                    } else {

                        startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                    }

                    // cameraIntent();
                }
            }
        });


        pluggedmeterDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(PluggedToActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                } else if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(PluggedToActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                } else {

                    optionalCameraIntent();
                    // cameraIntent();
                }
            }
        });




    }

    private void uploadPhoto(String base64String, String consumerNo, String applicationNumberStr, String oldMeter, String newMeter, String DocSource, String docTypeId, String docSubTypeId, String EMPCODE, String MAC, String remarkStr) {

        String params[] = new String[11];

        params[0] = base64String;
        params[1] = consumerNo;
        params[2] = applicationNumberStr;//applicationNumberStr;
        params[3] = "";
        params[4] = "";
        params[5] = "E";
        params[6] = docTypeId;
        params[7] = docSubTypeId;
        params[8] = EMPCODE;
        params[9] = MAC;
        params[10] = remarkStr;//remarkStr;

        if (connection.isConnectingToInternet()) {
            PhotoUpload photoUpload = new PhotoUpload();
            photoUpload.execute(params);
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class PhotoUpload extends AsyncTask<String, Void, Void> {
        MaterialDialog progressImageSite;

        @Override
        protected void onPreExecute() {
            progressImageSite = new MaterialDialog.Builder(PluggedToActivity.this)
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

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                String[] enums = gson.fromJson(jsonPhotoResponse, String[].class);

                if (flag) {
                    Toast.makeText(PluggedToActivity.this, R.string.failure, Toast.LENGTH_SHORT).show();
                } else {
                    progressImageSite.dismiss();

                    if (enums[0].equalsIgnoreCase("Success")) {
                        // Toast.makeText(mCon, "Site visit verification document uploaded successfully", Toast.LENGTH_SHORT).show();
                        progressImageSite.dismiss();
                        Toast.makeText(PluggedToActivity.this, "Site visit verification documents uploaded", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(PluggedToActivity.this, "Site visit verification document not uploaded", Toast.LENGTH_SHORT).show();

                        progressImageSite.dismiss();


                    }
                    progressImageSite.dismiss();
                    //    ll_upload_document.setVisibility(View.GONE);
                }
                progressImageSite.dismiss();
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(PluggedToActivity.this, "" + jsonPhotoResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(PluggedToActivity.this, "ComplaintRegisterActivity", "register complaint button", error);
                progressImageSite.dismiss();
            }

        }
    }

    private void UpdateSiteVisitData() {
        String params[] = new String[13];
        SharedPreferences pref = PluggedToActivity.this.getPreferences(Context.MODE_PRIVATE);

        finalmeter = edtMeterReading.getText().toString();

        remarkStr = edtRemark.getText().toString();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        processDate = simpleDateFormat.format(new Date());
        /*String maker = smetermake.getSelectedItem().toString();*/
//         maker = String.valueOf(makerId);
        String imei = "";

        if (!realmOperations.checkDeviceIdData().get(0).equalsIgnoreCase("") || realmOperations.checkDeviceIdData().get(0) != null) {
            imei = realmOperations.checkDeviceIdData().get(0);
        }

        params[0] = applicationNumberStr;
        params[1] = null;
        params[2] = processDate;
        params[3] = empCode;
        params[4] = MAC;
        params[5] = remarkStr;
        params[6] = processDate;
        params[7] = ConnStatusId;
        params[8] = meterStatusId;
        params[9] = meterObservationId;
        params[10] = finalmeter;

        params[11] = null;
        params[12] = processDate;


//         typemeter = stypeofMeter.getSelectedItem().toString();
//         ownership = spownership.getSelectedItem().toString();


        Log.e("updateJsonParams", "json : " + Arrays.toString(params));


        if (connection.isConnectingToInternet()) {

            UpdateSiteVisit updateSiteVisit = new UpdateSiteVisit();
            updateSiteVisit.execute(params);
        } else {
            Toast.makeText(PluggedToActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class
    UpdateSiteVisit extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(PluggedToActivity.this)
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
                String paraName[] = new String[13];
                paraName[0] = "appno";
                paraName[1] = "processcode";
                paraName[2] = "ModifiedDate";
                paraName[3] = "empcode";
                paraName[4] = "IPAddress";
                paraName[5] = "Remarks";
                paraName[6] = "ProcessDate";
                paraName[7] = "ConnStatusId";
                paraName[8] = "MeterStatus";
                paraName[9] = "MeterObservation";
                paraName[10] = "FinalReading";
                paraName[11] = "ReasonCode";
                paraName[12] = "ReadingDate";



                updateJsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.SaveSiteVisitActiveToPlugData, params, paraName);
                Log.e("updateJsonResponse", "json : " + updateJsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                SiteVisitRequestModel enums = gson.fromJson(updateJsonResponse, SiteVisitRequestModel.class);

                if (enums.getMessage() != null) {
                    if (enums.getMessage().equalsIgnoreCase("Site visit completed successfully.")) {
                        MessageWindow.successNSCFragment(PluggedToActivity.this, "" + enums.getMessage(), "Information", MainActivity.class);
                       /* PreferenceUtil.setWardId("");
                        PreferenceUtil.setMsrID("");
                        PreferenceUtil.setSrid("");
*/
                    } else {
                        MessageWindow.connectionFragment(PluggedToActivity.this, "" + enums.getMessage(), "Alert", MainActivity.class);
                    }
                    progress.dismiss();
                } else {
                    Toast.makeText(PluggedToActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(PluggedToActivity.this, "" + updateJsonResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                progress.dismiss();
            }
            progress.dismiss();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(PluggedToActivity.this,MainActivity.class);
        intent.putExtra("goToConnectionM",true);
        startActivity(intent);
    }

    private void optionalCameraIntent() {
        // updateLocationUI();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //File dir = new File(Environment.getExternalStorageDirectory(), "Water Meter Reading");
        File dir = new File(mCon.getExternalFilesDir(null), "Water Meter Reading");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File insideBillMonthFolder = new File(dir, "Bill Month");
        if (!insideBillMonthFolder.exists()) {
            insideBillMonthFolder.mkdirs();
        }
        File insideBillMonthLotFolder = new File(insideBillMonthFolder, "");
        if (!insideBillMonthLotFolder.exists()) {
            insideBillMonthLotFolder.mkdirs();
        }
        File insideBillMonthOtherPhotoFolder = new File(insideBillMonthLotFolder, "Other Photo");
        if (!insideBillMonthOtherPhotoFolder.exists()) {
            insideBillMonthOtherPhotoFolder.mkdirs();
        }

        destinationOptional = new File(insideBillMonthOtherPhotoFolder, consumerNoStr + "_" + meternum + "_" + System.currentTimeMillis() + ".jpg");
        //   Uri photoUri = FileProvider.getUriForFile(mCon, BuildConfig.APPLICATION_ID + ".provider", destinationOptional);
        photoURI = FileProvider.getUriForFile(this,
                "elink.suezShimla.water.crm",
                destinationOptional);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(intent, plugImgCode);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  File dir = new File(Environment.getExternalStorageDirectory(), "Water Meter Reading");
        File dir = new File(mCon.getExternalFilesDir(null), "CRM");

        if (!dir.exists()) {
            dir.mkdirs();
        }
/*
    File insideFolder = new File(dir, getResources().getString(R.string.documents));
        if (!insideFolder.exists()) {
            insideFolder.mkdirs();
        }*/
        File insideBillMonthFolder = new File(dir, "Reading");
        if (!insideBillMonthFolder.exists()) {
            insideBillMonthFolder.mkdirs();
        }

        // strConsumerNo + "_" + strMeterNo + "_"+

        // destination = new File(insideFolder, System.currentTimeMillis() + ".jpg");
        destination = new File(insideBillMonthFolder, "111" + "_" + consumerNoStr + "_" + System.currentTimeMillis() + ".jpg");
        photoURI = FileProvider.getUriForFile(this,
                "elink.suezShimla.water.crm",
                destination);
        // photoURI = FileProvider.getUriForFile(mCon, BuildConfig.APPLICATION_ID + ".provider", destination);


        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(intent, reqcode_img);
    }

    private boolean isTimeAutomatic(Context mCon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(mCon.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return Settings.System.getInt(mCon.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == reqcode_img) {

            try {
                rotation = getRotation(destination);

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                thumbnail = BitmapFactory.decodeFile(destination.getAbsolutePath(), bmOptions);

                uploadDoc.setImageURI(Uri.parse(destination.getAbsolutePath()));
                //   optionalThumbnail = ((BitmapDrawable) cameraOptionalImageView.getDrawable()).getBitmap();
                thumbnail = BitmapFactory.decodeFile(String.valueOf(destination), bmOptions);


                if (thumbnail != null) {


                    int h = thumbnail.getHeight();
                    int w = thumbnail.getWidth();
                    h = h / 4;
                    w = w / 4;

                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotation);
                    //thumbnail = Bitmap.createBitmap(thumbnail, 0, 0, w, h, matrix, true);

                    thumbnail = Bitmap.createScaledBitmap(thumbnail, w, h, true);
                    rotatedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    //  rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);


                    Canvas cs = new Canvas(rotatedBitmap);
                    Paint tPaint = new Paint();
                    tPaint.setTextSize(30);
                    tPaint.setColor(Color.WHITE);
                    tPaint.setStyle(Paint.Style.FILL);
                    cs.drawBitmap(rotatedBitmap, 0f, 0f, null);
                    float height = tPaint.measureText("yY");
                    cs.drawText(format + " (" + consumerNoStr + "-" + meternum + ")", 10f, height + 10f, tPaint);
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);

                    //rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File("/sdcard/timeStampedImage.jpg")));

                    //  tvUploadImageViewCureentTimeStamp.setText(format);

                    if (!destination.exists()) {
                        destination.createNewFile();
                    }

                    String strPath1 = String.valueOf(destination);

                    FileOutputStream fo;
                    try {
                        //  destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (!strPath1.equals("")) {
                            /*docTextView.setText(R.string.no_file_chosen);
                            imageView.setVisibility(View.GONE);*/
                        encodedBase64Image = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);

                        documentImages.add(encodedBase64Image);
                        documentSubtype.add("441");

                        docName = strPath1.substring(strPath1.lastIndexOf('/') + 1);
                        docType = "image";

                        uploadDoc.setImageBitmap(rotatedBitmap);
                        // cameraCardView.setCardBackgroundColor(getResources().getColor(R.color.divider));

                        uploadDoc.setVisibility(View.VISIBLE);
                        // cameraLinearLayout.setVisibility(View.GONE);

                    }
                } else {


                    //  thumbnail = GaussianBlur.with(this).maxSixe(100).render(R.drawable.ic_add_photo);
                    //  cameraImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_add_photo)));

                    if (rotatedBitmap == null) {
                        uploadDoc.setVisibility(View.VISIBLE);
                    } else {
                        uploadDoc.setImageBitmap(rotatedBitmap);
                        uploadDoc.setVisibility(View.VISIBLE);
                        //   cameraLinearLayout.setVisibility(View.GONE);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("image", e.toString());

                String error = e.getMessage();
/*
                Number errorIdNum = realmOperations.getErrorIDCount();

                if (errorIdNum == null) {
                    errorNextId = 1;
                } else {
                    errorNextId = errorIdNum.intValue() + 1;
                }

                ErrorLogModel errorLogModel = new ErrorLogModel(errorNextId, empCode, imei, Build.MODEL, Build.MANUFACTURER, Build.VERSION.SDK_INT, AppVersion, "Consumer Reading Activity Demo", "Camera Intent", error, 0, timeStamp, "1");
                realmOperations.insertErrorLogData(errorLogModel);*/


            }
        } else if (requestCode == plugImgCode) {
            try {
                rotation = getRotation(destinationOptional);

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                optionalThumbnail = BitmapFactory.decodeFile(destinationOptional.getAbsolutePath(), bmOptions);
                pluggedmeterDoc.setImageURI(Uri.parse(destinationOptional.getAbsolutePath()));
                //   optionalThumbnail = ((BitmapDrawable) cameraOptionalImageView.getDrawable()).getBitmap();

                if (optionalThumbnail != null) {


                    int h = optionalThumbnail.getHeight();
                    int w = optionalThumbnail.getWidth();


                    h = h / 4;
                    w = w / 4;

                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotation);

                    optionalThumbnail = Bitmap.createScaledBitmap(optionalThumbnail, w, h, true);
                    optionalBitmap = Bitmap.createBitmap(optionalThumbnail, 0, 0, optionalThumbnail.getWidth(), optionalThumbnail.getHeight(), matrix, true);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    //  optionalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);


                    Canvas cs = new Canvas(optionalBitmap);
                    Paint tPaint = new Paint();
                    tPaint.setTextSize(30);
                    tPaint.setColor(Color.WHITE);
                    tPaint.setStyle(Paint.Style.FILL);
                    cs.drawBitmap(optionalBitmap, 0f, 0f, null);
                    float height = tPaint.measureText("yY");
                    cs.drawText(format + " (" + consumerNoStr + "-" + meternum + ")", 10f, height + 10f, tPaint);

                    // cs.drawText(format+" ("+strConsumerNo+"-"+strMeterNo+")", 10f, height + 10f, tPaint);
                    optionalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
                    // optionalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);

                    //   tvUploadImageViewOptionalCureentTimeStamp.setText(format);
                    if (!destinationOptional.exists()) {
                        destinationOptional.createNewFile();
                    }

                    String strPath1 = String.valueOf(destinationOptional);

                    FileOutputStream fo;
                    try {
                        //  destination.createNewFile();
                        fo = new FileOutputStream(destinationOptional);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (!strPath1.equals("")) {
                        /*docTextView.setText(R.string.no_file_chosen);
                        imageView.setVisibility(View.GONE);*/
                        encodedBase64OptionalImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);

                        documentImages.add(encodedBase64OptionalImage);
                        documentSubtype.add("442");

                        docNameOptional = strPath1.substring(strPath1.lastIndexOf('/') + 1);
                        docTypeOptional = "image";




                        pluggedmeterDoc.setImageBitmap(optionalBitmap);
                        pluggedmeterDoc.setVisibility(View.VISIBLE);
                        //cameraOptionalLinearLayout.setVisibility(View.GONE);

                    }

                } else {
                    if (optionalBitmap == null) {
                        pluggedmeterDoc.setVisibility(View.VISIBLE);
                    } else {
                        pluggedmeterDoc.setImageBitmap(optionalBitmap);
                        pluggedmeterDoc.setVisibility(View.VISIBLE);
                        //  cameraOptionalLinearLayout.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("image", e.toString());

                String error = e.getMessage();

              /*  Number errorIdNum = realmOperations.getErrorIDCount();

                if (errorIdNum == null) {
                    errorNextId = 1;
                } else {
                    errorNextId = errorIdNum.intValue() + 1;
                }

                ErrorLogModel errorLogModel = new ErrorLogModel(errorNextId, empCode, imei, Build.MODEL, Build.MANUFACTURER, Build.VERSION.SDK_INT, AppVersion, "Consumer Reading Activity Demo", "Camera Intent optional", error, 0, timeStamp, "1");
                realmOperations.insertErrorLogData(errorLogModel);*/


            }
        }
    }

    private static int getRotation(File imageFile) {
        if (imageFile == null)
            return 0;
        try {
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            // We only recognize a subset of orientation tag values
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                default:
                    return ExifInterface.ORIENTATION_UNDEFINED;
            }
        } catch (IOException e) {
            //  Log.e("Error getting Exif data", e);
            return 0;
        }
    }

    private String setImage(ImageView img) {

        String imgString = "";
        imageUri = Uri.fromFile(new File(imageName));

        img.setVisibility(View.VISIBLE);
        img.setImageURI(imageUri);
        img.setPadding(0, 0, 0, 0);
        try {
            bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            int a = 0;

            int nh = (int) (bitmap.getHeight() * (newImageHeight / bitmap.getWidth()));
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) newImageHeight, nh, true);
            // iv_visiting_card_proprietor.setImageBitmap(scaled);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            img.setImageBitmap(bitmap);
            imgString = Base64.encodeToString(b, Base64.DEFAULT);


        } catch (Exception ex) {
            ex.getMessage();
        }

        return imgString;
    }

    private boolean checkPhotoValidation() {
        if (statusName.equals(getResources().getString(R.string.select_meter_status))) {
            // Toast.makeText(mCon, R.string.meter_status_mandatory, Toast.LENGTH_LONG).show();

            MessageWindow.messageWindow(mCon, getResources().getString(R.string.meter_status_mandatory), "Alert");

            return false;
        }
        if (nameObservationStr.equals(getResources().getString(R.string.select_meter_observation))) {
            //  Toast.makeText(mCon, R.string.meter_observation_mandatory, Toast.LENGTH_LONG).show();
            MessageWindow.messageWindow(mCon, getResources().getString(R.string.meter_observation_mandatory), "Alert");
            return false;
        }


        if (encodedBase64Image.equalsIgnoreCase("")) {
            MessageWindow.messageWindow(this, "Please capture Meter reading photo", "Alert");

            // Toast.makeText(this, "Please capture Meter reading photo", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (encodedBase64OptionalImage.equalsIgnoreCase("")) {
            MessageWindow.messageWindow(this, "Please capture Active to plugged photo", "Alert");

            // Toast.makeText(this, "Please capture Active to plugged photo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etDate.getText().toString().isEmpty()) {
            etDate.setError(getResources().getString(R.string.cannot_be_empty));
            MessageWindow.messageWindow(this, "Reading date field can't be empty", "Alert");
            etDate.requestFocus();
            return false;

        }


        if (edtMeterReading.getText().toString().isEmpty()) {
            edtMeterReading.setError(getResources().getString(R.string.cannot_be_empty));
            MessageWindow.messageWindow(this, "Final Reading field can't be empty", "Alert");
            edtMeterReading.requestFocus();
            return false;

        }
        if (Integer.parseInt(edtMeterReading.getText().toString()) < Integer.parseInt(pastReading)) {
            edtMeterReading.setError(getResources().getString(R.string.cannot_be_empty));
            MessageWindow.messageWindow(this, "Current Reading is can not be less than Past reading", "Alert");
            edtMeterReading.requestFocus();
            return false;

        }

        if (edtRemark.getText().toString().isEmpty()) {
            edtRemark.setError(getResources().getString(R.string.cannot_be_empty));
            MessageWindow.messageWindow(this, "Remark field can't be empty", "Alert");
            edtRemark.requestFocus();
            return false;

        }

        return true;
    }

    private void setSpinnerValue() {
        meterStatusId = msValueList.get(meterStatusSpinner.getSelectedItemPosition() - 1).toString();

        meterObservationModels = realmOperations.fetchMeterObservationNameID(String.valueOf(meterStatusId));
        moList.clear();

        for (MeterObservationModel model : meterObservationModels) {
            moList.add(model.getDFM_DEFNAME());
            moValueList.add(String.valueOf(model.getDFM_CODE()));
        }


        if (meterStatusId == "1" || meterStatusId == "0" || meterStatusId == "4") {
            edtRemark.setText(null);
            edtRemark.setEnabled(true);


        } else {
/*
            meterReadingMandatoryEditText.setText(strPastMeterReading);
            meterReadingMandatoryInputLayout.setError(null);
            meterReadingMandatoryEditText.setEnabled(false);*/
        }


        ArrayList<String> observationData = new ArrayList<>();
        observationData.add(getResources().getString(R.string.select_meter_observation));
        observationData.addAll(moList);
        int observation_list_size = observationData.size();


        meterObservationAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, observationData);
        meterObservationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        readerObservationSpinner.setAdapter(meterObservationAdapter);
        readerObservationSpinner.setSelection(0);

    }

    private void finalStatusDD() {
        ArrayList<String> finalStatusName = new ArrayList<>();
        finalStatusName = realmOperations.fetchMeterStatusDetails("1");
        ArrayList<String> finalStatusNameList = new ArrayList<>();
        finalStatusNameList.add("--Select--");
        finalStatusNameList.addAll(finalStatusName);

        finalStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, finalStatusNameList);
        finalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        finalStatusSpinner.setAdapter(finalStatusAdapter);

        if (radiobuttonValStr.equalsIgnoreCase("S") || radiobuttonValStr.equalsIgnoreCase("MB")) {
            int spinnerPosition = finalStatusAdapter.getPosition("Normal Meter");
            finalStatusSpinner.setSelection(spinnerPosition);
        }

        finalStatusSpinner.setOnItemSelectedListener(this);
    }


    private void startCameraIntent(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's activity_login ic_camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
            Uri photoURI = FileProvider.getUriForFile(this, "elink.suezShimla.water.crm", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            startActivityForResult(takePictureIntent, requestCode);
        }
    }

    private File createImageFile() {
        File image = null;
        try {
            String imageFileName = "READING";

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


    private void enableRuntimePermission() {
        ActivityCompat.requestPermissions(PluggedToActivity.this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

    }

    /*    private void finalStatusDD() {
                ArrayList<String> finalStatusName = new ArrayList<>();
                finalStatusName = realmOperations.fetchMeterStatusDetails("1");
                ArrayList<String> finalStatusNameList = new ArrayList<>();
                finalStatusNameList.add("--Select--");
                finalStatusNameList.addAll(finalStatusName);

                finalStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, finalStatusNameList);
                finalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                finalStatusSpinner.setAdapter(finalStatusAdapter);

                if (radiobuttonValStr.equalsIgnoreCase("S") || radiobuttonValStr.equalsIgnoreCase("MB")) {
                        int spinnerPosition = finalStatusAdapter.getPosition("Normal Meter");
                        finalStatusSpinner.setSelection(spinnerPosition);
                }

                finalStatusSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        }*/
    final DatePickerDialog.OnDateSetListener fromCalendarDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            fromDateCalendar.set(Calendar.YEAR, year);
            fromDateCalendar.set(Calendar.MONTH, monthOfYear);
            fromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFromDateCalendar();
        }
    };

    private void updateFromDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDate.setText(sdf.format(fromDateCalendar.getTime()));
        disconDate = sdf.format(fromDateCalendar.getTime());
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.finalStatusSpinner:
                finalStatusSelected = finalStatusSpinner.getSelectedItem().toString();

                if (finalStatusSelected.equalsIgnoreCase("--Select--")) {
                    ArrayList<String> subAll = new ArrayList<>();
                    subAll.add("--Select--");

                    observationAdapter = new ArrayAdapter(PluggedToActivity.this, android.R.layout.simple_spinner_item, subAll);
                    observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    observationSpinner.setAdapter(observationAdapter);
                } else {
                    if (pagename != null) {
                        if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                            observReasonDropdown();
                        }
                        if (pagename.equalsIgnoreCase("MeterInstallationEntry")) {
                            observReasonDropdown();
                        } else {
                            mmgMeterStatusModel = realmOperations.fetchMeterStatusByName(finalStatusSelected);
                            finalStatsuId = String.valueOf(mmgMeterStatusModel.getMSM_METERSTATUS_ID());
                            finalStatusIdStr = String.valueOf(finalStatsuId);

                            mmgObersvationModel = realmOperations.fetchObservationByName(finalStatusIdStr);
                            meterStatusId = String.valueOf(mmgMeterStatusModel.getMSM_METERSTATUS_ID());
                            meterStatusIdStr = String.valueOf(meterStatusId);

                            meterObservationList = realmOperations.fetchStatusObservationList(meterStatusIdStr);

                            ArrayList<String> contractorEmpList = new ArrayList<>();
//                            contractorEmpList.add("--Select--");
                            contractorEmpList.addAll(meterObservationList);

                            observationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, contractorEmpList);
                            observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            observationSpinner.setAdapter(observationAdapter);
//                            finalReadingEdittext.setText("");
                        }
                    } else {
                        if (MI_O_OBSERVATION != null) {
                            observReasonDropdown();
                        } else {
                            mmgMeterStatusModel = realmOperations.fetchMeterStatusByName(finalStatusSelected);
                            finalStatsuId = String.valueOf(mmgMeterStatusModel.getMSM_METERSTATUS_ID());
                            finalStatusIdStr = String.valueOf(finalStatsuId);

                            mmgObersvationModel = realmOperations.fetchObservationByName(finalStatusIdStr);
                            meterStatusId = String.valueOf(mmgMeterStatusModel.getMSM_METERSTATUS_ID());
                            meterStatusIdStr = String.valueOf(meterStatusId);

                            meterObservationList = realmOperations.fetchStatusObservationList(meterStatusIdStr);

                            ArrayList<String> contractorEmpList = new ArrayList<>();
//                            contractorEmpList.add("--Select--");
                            contractorEmpList.addAll(meterObservationList);

                            observationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, contractorEmpList);
                            observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            observationSpinner.setAdapter(observationAdapter);
//                            finalReadingEdittext.setText("");
                        }
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

    private void observReasonDropdown() {
        try {
            String finalStatusName = finalStatusSpinner.getSelectedItem().toString();
            mmgMeterStatusModel = realmOperations.fetchMeterStatusByName(finalStatusName);
            finalStatsuId = String.valueOf(mmgMeterStatusModel.getMSM_METERSTATUS_ID());
            finalStatusIdStr = String.valueOf(finalStatsuId);

            mmgObersvationModel = realmOperations.fetchObservationByName(finalStatusIdStr);
            meterStatusId = String.valueOf(mmgMeterStatusModel.getMSM_METERSTATUS_ID());
            meterStatusIdStr = String.valueOf(meterStatusId);
            meterObservationList = realmOperations.fetchStatusObservationList(meterStatusIdStr);

            if (MI_O_OBSERVATION == null || MI_O_OBSERVATION.equalsIgnoreCase("") ||
                    MI_O_OBSERVATION.equalsIgnoreCase("0")) {
                defaultObservation();
            } else {

                if (MI_O_FINALSTATUS.equals(finalStatsuId)) {
                    mStatusObservationModel = realmOperations.fetchContractrObservationById(MI_O_OBSERVATION);
                    meterObservationName = mStatusObservationModel.getMSNM_MSUBSTATUS_NAME();

                    int observn = meterObservationList.indexOf(meterObservationName);

                    ArrayList<String> contractorEmpList = new ArrayList<>();
//            contractorEmpList.add("--Select--");
                    contractorEmpList.addAll(meterObservationList);

                    observationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, contractorEmpList);
                    observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    observationSpinner.setAdapter(observationAdapter);
                    observationSpinner.setSelection(observn);
                } else {
//                    ArrayList<String> subAll = new ArrayList<>();
//                    subAll.add("--Select--");

                    observationAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, meterObservationList);
                    observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    observationSpinner.setAdapter(observationAdapter);
                }


                if (MI_O_FINALREADING == null || MI_O_FINALREADING.equalsIgnoreCase("")) {
//                    finalReadingEdittext.setText("");
                } else {
//                    finalReadingEdittext.setText(MI_O_FINALREADING);
                }

            }

        } catch (Exception ex) {
            MessageWindow.errorWindow(this, ex.getMessage());

         /*   ArrayList<String> contractorEmpList = new ArrayList<>();
            contractorEmpList.addAll(meterObservationList);

            observationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, contractorEmpList);
            observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            observationSpinner.setAdapter(observationAdapter);*/


        }
    }

    private void defaultObservation() {
        meterObservationList = realmOperations.fetchStatusObservationList(meterStatusIdStr);

        ArrayList<String> metrObsList = new ArrayList<>();
        metrObsList.addAll(meterObservationList);

        observationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, metrObsList);
        observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        observationSpinner.setAdapter(observationAdapter);
//        finalReadingEdittext.setText("");

        if (radiobuttonValStr.equalsIgnoreCase("S") || radiobuttonValStr.equalsIgnoreCase("MB")) {
            int spinnerPosition = observationAdapter.getPosition("Can be Retained");
            observationSpinner.setSelection(spinnerPosition);
        }
    }
}

