package elink.suezShimla.water.crm.DisconnectionMeter;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
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

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitRequestModel;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterStatusModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGObersvationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MStatusObservationModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGCustDetModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class ActivityDisconnectMeter extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private ConnectionDetector connection;
    private Invoke invServices;
    private Button submitbtn;
    String frgPass = "", remarkStr = "";
    private int reqcode_img = 1010, distributionCode = 1011, photoMarkedCode = 1012, otherPhotoCode = 1013;

    private Calendar fromDateCalendar, toDateCalendar;
    static String serialNoStr = "", contList = "", pagename = "", mtrSizeId = "";
    private ArrayList<String> documentImages = new ArrayList<>();
    private ArrayList<String> documentSubtype = new ArrayList<>();
    private boolean flag = false;
    LinearLayout ll_upload_document,liner_FinalStatus;
    private String updateJsonResponse = "", jsonPhotoResponse = "", MAC = "";

    Uri imageUri;

    Bitmap bitmap;
    String jsonSiteVisitResponse = "", base64String = "", docSource = "NC", MI_N_METEROWNERSHIP, distributionBase64String = "", photoMarkedBase64String = "", otherPhotoBase64String = "";


    String applicationNumberStr = "";

    private Gson gson;

    private MMGMeterStatusModel mmgMeterStatusModel;
    private MMGObersvationModel mmgObersvationModel;

    String mfgNameRecvd = "", serialNoRecvd = "", installDtRecvd = "", meterSizeRecvd = "", sealNoRecvd = "", pastMeterReadingRecvd = "",
            meterStatusStr = "", meterTypeStr = "", finalStatusSelected = "", observationSelectedName = "", connSizeStr = "", reasonOfReplacementName = "";
    private MaterialDialog progressDisconMeter;
    public static final int RequestPermissionCode = 1;
    Spinner finalStatusSpinner, observationSpinner;
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
    String construction_completed="", applied_dwelling_unit_has_connection= "",
            internal_network_available, disposal_of_water_available,
            rainwater_harvesting_system_available, any_existing_connection= "", add_consumer= "", meterSizeId= "", selectStorageCapacity, storageCapacity= "", storageCapacityType= "";
    String connection_feasibility, water_availability= "", road_cutting_required= "",
            distribution_pipeline= "", distribution_id= "", leftConsumerStr= "", rightConsumerStr= "", road_length_id= "", meterLocation = "", connection_size = "";
    String processDate = "", EMPCODE = "", sDivision = "", sSubDivision = "", sSection = "", sBU = "", sAppType = "", sType = "P", sSourceType = "", sFather = "", sMiddleName = "";
    String zoneId = "", wardId = "", MSRIDStr = "", SRIDStr = "", groupId = "", lotId = "", areaId = "";

    private ArrayAdapter meterTypeAdapter, meterStatusAdapter, meterReasonAdapter, finalStatusAdapter, observationAdapter;


    private RealmOperations realmOperations;

    String meterTypeId = "", meterTypeIdStr = "", meterSttausName = "", meterStatusId = "", makerCodeIdStr = "",
            meterStatusIdStr = "", finalReadingValStr = "", finalStsValStr = "", reasnFrReplacementValStr = "", meterReasonId,
            meterResonStr = "", meterReasonIdStr = "", finalStatsuId = "", finalStatusIdStr = "", coonectionLoad = "",
            makercodename = "", serachById = "", consumerNoStr = "", consumerNameStr = "", phonenum = "", meternum = "", radiobuttonValStr = "", refNoStr = "", BU, PC,
            meterObservationId = "", meterObservationName = "",
            jsonCustomerDetailResponse = "", jsonMeterInstallSaveResponse = "";

    private MStatusObservationModel mStatusObservationModel;
    private ArrayList<String> meterObservationList = new ArrayList<>();
    ArrayList<MMGCustDetModel> customerDetailList = new ArrayList<>();
    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = new ArrayList<>();
    ArrayList<InstallDetails> installDetails = new ArrayList<>();
    ArrayList<MMGValidateDetails> validateDetailList = new ArrayList<>();
    private String imageName;
    File storageDir;

    double newImageHeight = 512.0;
    int imageQuality = 65;


    private EditText etconsumerNo, etConsumerName, edtMeterNo, edttotalflat, edtcontactnum, etDate,edtMeterReading;
    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    String currentDateTimeStr = "", fromDate = "", toDate = "", empCode = "",disconDate="",finalmeter,totalflat="";
    private LinearLayout lldate;

    private ImageView uploadDoc, handovermeterDoc;
    private MaterialDialog progress, progressPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnect_meter);

        gson = new Gson();
        MAC = PreferenceUtil.getMac();
        connection = new ConnectionDetector(this);
        invServices = new Invoke();
        realmOperations = new RealmOperations(this);


        try {
            // AES Algorithm for encryption / decryption

            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            random = new SecureRandom();
            random.nextBytes(IV);

            aes = new AesAlgorithm();

        } catch (Exception e) {
            e.printStackTrace();
        }

        init();


    }

    private void init() {

        finalStatusSpinner = findViewById(R.id.finalStatusSpinner);
        submitbtn = findViewById(R.id.submitButton);
        observationSpinner = findViewById(R.id.observationSpinner);
        edtMeterReading = findViewById(R.id.edtMeterReading);
        liner_FinalStatus = findViewById(R.id.liner_FinalStatus);
        fromDate = getIntent().getStringExtra("fromDate");
        toDate = getIntent().getStringExtra("toDate");
        consumerNoStr = getIntent().getStringExtra("consnum");
        firstNameStr = getIntent().getStringExtra("firstName");
        consumerNameStr = getIntent().getStringExtra("conName");
        meternum = getIntent().getStringExtra("meternum");
        phonenum = getIntent().getStringExtra("contact");
        totalflat = getIntent().getStringExtra("flat");
        applicationNumberStr = getIntent().getStringExtra("applicationnum");


        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();


        etDate = findViewById(R.id.etDate);
        lldate = findViewById(R.id.lldate);
        etconsumerNo = findViewById(R.id.etconsumerNo);
        etConsumerName = findViewById(R.id.etConsumerName);
        edtMeterNo = findViewById(R.id.edtMeterNo);

        edttotalflat = findViewById(R.id.edttotalflat);
        edttotalflat.setText(totalflat);

        edtcontactnum = findViewById(R.id.edtcontactnum);

        etconsumerNo.setText(consumerNoStr);
        etConsumerName.setText(consumerNameStr);
        edtcontactnum.setText(phonenum);

        edtMeterNo.setText(meternum);
        uploadDoc = findViewById(R.id.uploadDoc);
        handovermeterDoc = findViewById(R.id.handovermeterDoc);
        uploadDoc.setOnClickListener(this);
        handovermeterDoc.setOnClickListener(this);
        submitbtn.setOnClickListener(this);


        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        currentDateTimeStr = simpleDateFormat.format(new Date());


        lldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityDisconnectMeter.this, fromCalendarDate, fromDateCalendar.get(Calendar.YEAR), fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();

            }
        });


        //  Toast.makeText(this, ""+fromDate, Toast.LENGTH_SHORT).show();

/*
        gson = new Gson();
        connection = new ConnectionDetector(this);
        invServices = new Invoke();
*/

        empCode = UtilitySharedPreferences.getPrefs(this, AppConstant.EMPCODE);
        try {
            empCode = aes.decrypt(empCode);
        } catch (Exception e) {
            e.printStackTrace();
        }


        finalStatusDD();
        ArrayList<String> meterStatusName = new ArrayList<>();
        ArrayList<String> meterStatusList = new ArrayList<>();
        meterStatusList.add("--Select--");
        meterStatusList.addAll(meterStatusName);

        meterStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, meterStatusList);
        meterStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        observationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                observationSelectedName = observationSpinner.getSelectedItem().toString();
                if (observationSelectedName.equalsIgnoreCase("--Select--")) {

                } else {
                    String meterStatusObservationName = observationSpinner.getSelectedItem().toString();

                    mStatusObservationModel = realmOperations.fetchMeterObservationById(meterStatusObservationName);
                    if (mStatusObservationModel != null) {
                        meterObservationId = mStatusObservationModel.getMSNM_MSUBSTATUS_ID();
                        //  MStatusObservationModel{MSNM_MSTATUS_ID='3', MSNM_MSUBSTATUS_ID='9', MSNM_MSUBSTATUS_NAME='Meter Stop'}

                     /*   if ((pagename == null) || (pagename.equalsIgnoreCase("MeterInstallationContractorDet"))) {
                            meterReasonDefaultDropdown();
                        } else {
                            //  if(MI_O_REASON.equalsIgnoreCase("")) {//pinky added on 31/03/2022  commented by sonali 22/07/20222
                            if (!pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                                if (meterObservationId.equalsIgnoreCase("9")) {//Meter Stop
                                    setReasonOfReplacement("40");
                                } else if (meterObservationId.equalsIgnoreCase("10")) {//Meter Damage
                                    setReasonOfReplacement("48");//pinky changes from 45 to 48
                                } else if (meterObservationId.equalsIgnoreCase("11")) {//Display Faulty
                                    setReasonOfReplacement("43");
                                } else if (meterObservationId.equalsIgnoreCase("12")) {//Meter Reverse
                                    setReasonOfReplacement("46");
                                } else if (meterObservationId.equalsIgnoreCase("15")) {//Meter Not Found
                                    setReasonOfReplacement("41");
                                } else {

                                }
                            }
                        }
                        //  }
*/

                     /*   if (meterObservationId.equalsIgnoreCase("1") || meterObservationId.equalsIgnoreCase("2")
                                || meterObservationId.equalsIgnoreCase("25") || meterObservationId.equalsIgnoreCase("26")
                                || meterObservationId.equalsIgnoreCase("27") || meterObservationId.equalsIgnoreCase("9")
                                || meterObservationId.equalsIgnoreCase("12")) {
//                            finalReadingEdittext.setEnabled(true);
                            isValidFinalReading = false;

                        } else {
                            finalReadingEdittext.setText("");
                            finalReadingEdittext.setEnabled(false);
                            isValidFinalReading = true;
                        }*/
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        finalStatusDD();

    /*    tv_toolbar= findViewById(R.id.tv_toolbar);
        iv_back= findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_toolbar.setText(getResources().getString(R.string.site_visit_list));

        errorLinearr = findViewById(R.id.errorLinearr);
        siteVisitListRecyclerView = findViewById(R.id.siteVisitListRecyclerView);
        siteVisitListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);


        siteVisitListFilter= findViewById(R.id.siteVisitListFilter);
        siteVisitListFilter.setOnClickListener(this);
*/
//        getFilterSiteVisitWithOutData();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent i = new Intent(ActivityDisconnectMeter.this, MainActivity.class);
        startActivity(i);
        finish();
    }


    private void startBackActivity () {

            ActivityDisconnectMeter.this.finish();


    }





/*
    private void finalStatusDropdown() {
        try {
            ArrayList<String> finalStatusName = new ArrayList<>();
            finalStatusName = realmOperations.fetchMeterStatusDetails("1");

            ArrayList<String> finalStatusNameList = new ArrayList<>();
            finalStatusNameList.add("--Select--");
            finalStatusNameList.addAll(finalStatusName);

            mmgMeterStatusModel = realmOperations.fetchContractrStatusById(Integer.parseInt(MI_O_FINALSTATUS));
            finalStatusIdStr = mmgMeterStatusModel.getMSM_METERSTATUS_NAME();

            int reasonId = finalStatusNameList.indexOf(finalStatusIdStr);

            finalStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, finalStatusNameList);
            finalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            finalStatusSpinner.setAdapter(finalStatusAdapter);
            finalStatusSpinner.setSelection(reasonId);

        } catch (Exception ex) {
            ex.printStackTrace();
            //finalStatusDD();
        }
        finalStatusSpinner.setOnItemSelectedListener(this);
    }
*/

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == reqcode_img && resultCode == RESULT_OK) {

                base64String = setImage(uploadDoc);
                if (!base64String.equalsIgnoreCase("")) {
                    documentImages.add(base64String);
                    documentSubtype.add("428");
                } else {
                    base64String = "";
//                     uploadDoc.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_mmg));
                }


            } else {
                //  Toast.makeText(activity, "dguyuyfvuy", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == distributionCode && resultCode == RESULT_OK) {
                distributionBase64String = setImage(handovermeterDoc);
                if (!distributionBase64String.equalsIgnoreCase("")) {
                    documentImages.add(distributionBase64String);
                    documentSubtype.add("429");
                } else {
                    distributionBase64String = "";
                    handovermeterDoc.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_mmg));

                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void enableRuntimePermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                Manifest.permission.CAMERA)) {
//            Toast.makeText(mCon, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
//
//        } else {
        ActivityCompat.requestPermissions(ActivityDisconnectMeter.this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

//        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(),"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
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



/*
    private void finalStatusDropdown() {
        try {
            ArrayList<String> finalStatusName = new ArrayList<>();
            finalStatusName = realmOperations.fetchMeterStatusDetails("1");

            ArrayList<String> finalStatusNameList = new ArrayList<>();
            finalStatusNameList.add("--Select--");
            finalStatusNameList.addAll(finalStatusName);

            mmgMeterStatusModel = realmOperations.fetchContractrStatusById(Integer.parseInt(MI_O_FINALSTATUS));
            finalStatusIdStr = mmgMeterStatusModel.getMSM_METERSTATUS_NAME();

            int reasonId = finalStatusNameList.indexOf(finalStatusIdStr);

            finalStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, finalStatusNameList);
            finalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            finalStatusSpinner.setAdapter(finalStatusAdapter);
            finalStatusSpinner.setSelection(reasonId);

        } catch (Exception ex) {
            ex.printStackTrace();
            //finalStatusDD();
        }
        finalStatusSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    }
*/

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.finalStatusSpinner:
                finalStatusSelected = finalStatusSpinner.getSelectedItem().toString();

                if (finalStatusSelected.equalsIgnoreCase("--Select--")) {
                    ArrayList<String> subAll = new ArrayList<>();
                    subAll.add("--Select--");

                    observationAdapter = new ArrayAdapter(ActivityDisconnectMeter.this, android.R.layout.simple_spinner_item, subAll);
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

    private void startCameraIntent(int requestCode) {//there
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
//        }

    }

    private File createImageFile() {//this

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

    private boolean checkPhotoValidation() {



     /*   typeString = typeSpinner.getSelectedItem().toString();
        subtypeString = subTypeSpinner.getSelectedItem().toString();
        if (typeString.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, "Please select type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (subtypeString.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, "Please select sub type", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        if (base64String.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please capture Before Disconnection Meter", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (distributionBase64String.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please capture Handover to consumer photo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etDate.getText().toString().isEmpty()){
            etDate.setError(getResources().getString(R.string.cannot_be_empty));
            MessageWindow.messageWindow(this, "Initial Reading field can't be empty", "Alert");




        }else
            etDate.setError(null);


        if (edtMeterReading.getText().toString().isEmpty()){
            edtMeterReading.setError(getResources().getString(R.string.cannot_be_empty));
            MessageWindow.messageWindow(this, "Initial Reading field can't be empty", "Alert");




        }else
            edtMeterReading.setError(null);

      /*  if (photoMarkedBase64String.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please capture marked space meter to be install ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (otherPhotoBase64String.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please capture other document", Toast.LENGTH_SHORT).show();
            return false;
        }
*/
        /*remarkStr = remarkEditText.getText().toString();
        if (remarkStr.equalsIgnoreCase("")) {
            remarkEditText.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(this, R.string.please_enter_remark, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            remarkEditText.setError(null);
        }
*/

        return true;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

          /*  case R.id.btn_update: {
                if (validation()) {
//                    UpdateSiteVisitData();
                }
            }
            break;

            case R.id.btn_update_back:
                backFragmentActivity();
                break;
*/
            case R.id.uploadDoc:
                try {
                    enableRuntimePermission();
                    //cam_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startCameraIntent(reqcode_img);
                    // startActivityForResult(cam_intent, reqcode_img);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
                break;

            case R.id.handovermeterDoc:
                //cam_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    enableRuntimePermission();
                    startCameraIntent(distributionCode);

                    //startActivityForResult(cam_intent, distributionCode);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
                break;


            case R.id.submitButton: {
                if (checkPhotoValidation()) {

            /*    for (int i = 0; i < documentImages.size(); i++) {
                    //  Log.d("Running " + i, "doctype " + documentSubtype.get(i));
                    uploadPhoto(documentImages.get(i), consumerNoStr, applicationNumberStr, "", "", "A", "304", documentSubtype.get(i), empCode, MAC, remarkStr);*/
//                    }
//                    if (validation()) {

                    validation();

//                    }
                }
            }
            break;
            default:
                break;
        }

    }

    private void validation() {

        boolean isValidFinalStatus = false, isObservation = false,isValidFinalReading = true;;
        if (finalStatusSelected.equalsIgnoreCase("--Select--") || observationSelectedName.equalsIgnoreCase("--Select--") ) {
            if (finalStatusSelected.equalsIgnoreCase("--Select--") ) {
                liner_FinalStatus.requestFocus();
                MessageWindow.messageWindow(this,"Please Select Final Status","Alert");
                return;
            } else if (observationSelectedName.equalsIgnoreCase("--Select--") && !(radiobuttonValStr.equalsIgnoreCase("N"))) {
                MessageWindow.messageWindow(this,"Please Select Observation","Alert");
                return;
            }

        } else {
            isValidFinalStatus = true;
            isObservation = true;
            isValidFinalReading = true;

        }

        if (finalReadingValStr.equalsIgnoreCase("")) {
            if (finalStatsuId.equalsIgnoreCase("")) {
                isValidFinalReading = true;
            } else {
                if (meterObservationId.equalsIgnoreCase("")) {
                    MessageWindow.messageWindow(this, "Please Check Status or Observation", "Alert");

                }
            }

        }

        if (isValidFinalReading && isValidFinalStatus  && isObservation) {



            for (int i = 0; i < documentImages.size(); i++) {
                //  Log.d("Running " + i, "doctype " + documentSubtype.get(i));
                uploadPhoto(documentImages.get(i), consumerNoStr, applicationNumberStr, "", "", "E", "110", documentSubtype.get(i), empCode, MAC, remarkStr);
                UpdateSiteVisitData();
            }


        }
       /* if (finalStatusIdStr.equalsIgnoreCase("Select")) {

            finalStatusSpinner.setEr(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.meter_make_mandatory2, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            smetermake.setError(null);

        }

        if (dialdigitstr.equalsIgnoreCase("Select")) {

            sdigit.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.dial_digit_mandatory, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            sdigit.setError(null);
        }
       */






        }

    @SuppressLint("StaticFieldLeak")
    private class PhotoUpload extends AsyncTask<String, Void, Void> {
        MaterialDialog progressImageSite;

        @Override
        protected void onPreExecute() {
            progressImageSite = new MaterialDialog.Builder(ActivityDisconnectMeter.this)
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
                    Toast.makeText(ActivityDisconnectMeter.this, R.string.failure, Toast.LENGTH_SHORT).show();
                } else {
                    progressImageSite.dismiss();

                    if (enums[0].equalsIgnoreCase("Success")) {
                        // Toast.makeText(mCon, "Site visit verification document uploaded successfully", Toast.LENGTH_SHORT).show();
                        progressImageSite.dismiss();


                    } else {
                        Toast.makeText(ActivityDisconnectMeter.this, "Site visit verification document not uploaded", Toast.LENGTH_SHORT).show();

                        progressImageSite.dismiss();


                    }
                    progressImageSite.dismiss();
                    //    ll_upload_document.setVisibility(View.GONE);
                }
                progressImageSite.dismiss();
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(ActivityDisconnectMeter.this, "" + jsonPhotoResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(ActivityDisconnectMeter.this, "ComplaintRegisterActivity", "register complaint button", error);
                progressImageSite.dismiss();
            }

        }
    }

    private void UpdateSiteVisitData() {
        String params[] = new String[77];
        SharedPreferences pref = ActivityDisconnectMeter.this.getPreferences(Context.MODE_PRIVATE);

        finalmeter = edtMeterReading.getText().toString();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        processDate = simpleDateFormat.format(new Date());
        /*String maker = smetermake.getSelectedItem().toString();*/
//         maker = String.valueOf(makerId);


//         typemeter = stypeofMeter.getSelectedItem().toString();
//         ownership = spownership.getSelectedItem().toString();

        params[0] = applicationNumberStr;
        params[1] = salutationStr;
        params[2] = firstNameStr;
        params[3] = middleStr;
        params[4] = lastNameStr;
        params[5] = sFather;
        params[6] = addresStr;
        params[7] = streetLocalityStr;//
        params[8] = landmarkStr;
        params[9] = postalCodeStr;
        params[10] = connectionTypeId;

        params[11] = connectionCategoryId;
        params[12] = propertyTypeId;
        params[13] = connectionPurposeId;
        params[14] = noOfFloorStr;
        params[15] = populationStr;
        params[16] = noOfBedsStr;
        params[17] = "0";
        params[18] = location;
        params[19] = noOfDwellingUnitId;//checking
        params[20] = "0";

        params[21] = "0";
        params[22] = technicalFeasibilityId;
        params[23] = distribution_pipeline;
        params[24] = "0";
        params[25] = road_length_id;
        params[26] = roadTypeId;
        params[27] = roadOwnerShipId;
        params[28] = connection_size;// p sir
        params[29] = zoneId;
        params[30] = wardId;

        params[31] = MSRIDStr;
        params[32] = SRIDStr;
        params[33] = groupId;
        params[34] = lotId;
        params[35] = areaId;
        params[36] = remarkStr;
        params[37] = processDate;
        params[38] = empCode;
        params[39] = MAC;
        params[40] = sDivision;

        params[41] = sSubDivision;
        params[42] = sSection;
        params[43] = sBU;
        params[44] = "18";
        params[45] = sType;
        params[46] = empCode;
        params[47] = sSourceType;
        params[48] = water_availability;
        params[49] = distribution_id;
        params[50] = rightConsumerStr;


        params[51] = leftConsumerStr;
        params[52] = road_cutting_required;
        params[53] = construction_completed;
        params[54] = any_existing_connection;
        params[55] = add_consumer;

        params[56] = applied_dwelling_unit_has_connection;//noOfDwellingUnitId;
        params[57] = meterSizeId;
        params[58] = storageCapacityType; //underground
        params[59] = storageCapacity;//overhead
        params[60] = "";

        params[61] = "";
        params[62] = "";
        params[63] = meterLocation;
        params[64] = "";
        params[65] = "";
        params[66] = "";
        params[67] = "";
        params[68] = "";
        params[69] = "";
        params[70] = "";
        params[71] = "";
        params[72] = "";
        params[73] = disconDate;
        params[74] = finalStatusIdStr;
        params[75] = meterObservationId;
        params[76] = finalmeter;

        Log.e("updateJsonParams", "json : " + Arrays.toString(params));


        if (connection.isConnectingToInternet()) {
            UpdateSiteVisit updateSiteVisit = new UpdateSiteVisit();
            updateSiteVisit.execute(params);
        } else {
            Toast.makeText(ActivityDisconnectMeter.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class
    UpdateSiteVisit extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(ActivityDisconnectMeter.this)
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
                String paraName[] = new String[77];
                paraName[0] = "sAppNo";
                paraName[1] = "sInitial";
                paraName[2] = "sFirstName";
                paraName[3] = "sMiddleName";
                paraName[4] = "sLastName";
                paraName[5] = "sFatherName";
                paraName[6] = "sAddress1";
                paraName[7] = "sAddress2";
                paraName[8] = "sAddress3";
                paraName[9] = "sPinCode";
                paraName[10] = "sTariff";

                paraName[11] = "sConnCategory";
                paraName[12] = "sPremiseType";
                paraName[13] = "sPurpose";
                paraName[14] = "sNoOfFloors";
                paraName[15] = "sFamilyMember";
                paraName[16] = "sNoOfRooms";
                paraName[17] = "sUnAuthConn";
                paraName[18] = "sLocation";
                paraName[19] = "sDwellingUnits";
                paraName[20] = "sOccupierSecurity";

                paraName[21] = "sGovtEmployee";
                paraName[22] = "sTechFesibility";
                paraName[23] = "sDistConnSize";
                paraName[24] = "sRoadLenMeasure";
                paraName[25] = "sRoadLength";
                paraName[26] = "sRoadType";
                paraName[27] = "sRoadOwner";
                paraName[28] = "sConnSize";
                paraName[29] = "sZone";
                paraName[30] = "sCircle";

                paraName[31] = "sMSR";
                paraName[32] = "sSR";
                paraName[33] = "sPC";
                paraName[34] = "sMRC";
                paraName[35] = "sArea";
                paraName[36] = "sRemarks";
                paraName[37] = "sProcessDate";
                paraName[38] = "sEmpCode";
                paraName[39] = "sIP";
                paraName[40] = "sDivision";

                paraName[41] = "sSubDivision";
                paraName[42] = "sSection";
                paraName[43] = "sBU";
                paraName[44] = "sAppType";
                paraName[45] = "sType";
                paraName[46] = "sEmpID";
                paraName[47] = "sSourceType";
                paraName[48] = "sIsWaterAvailInDP";
                paraName[49] = "sDistId";
                paraName[50] = "sRightConsumer";

                paraName[51] = "sLeftConsumer";
                paraName[52] = "sIsRoadCuttingReqd";
                paraName[53] = "sIsConstrCompl";
                paraName[54] = "sIsExistConn";
                paraName[55] = "sExistConn";
                paraName[56] = "sIsDwellHasConn";
                paraName[57] = "sMeterSize";
                paraName[58] = "sStorageCapType";
                paraName[59] = "sStorageCapacity";
                paraName[60] = "sIsInternalNetwork";

                paraName[61] = "sIsDisposalOfWater";
                paraName[62] = "sIsRainWaterHarwest";
                paraName[63] = "sMeterLocation";

                paraName[64] = "sMeterMake";
                paraName[65] = "sMeterNo";
                paraName[66] = "sDialDigit";
                paraName[67] = "sTypeOfMeter";
                paraName[68] = "sInitialReading";
                paraName[69] = "sMeterOwnership";
                paraName[70] = "sMobileNo";
                paraName[71] = "sEmail";
                paraName[72] = "sBuildingId";
                paraName[73] = "sDisconDate";
                paraName[74] = "sMeterStatus";
                paraName[75] = "sMeterObservation";
                paraName[76] = "sFinalReading";
                updateJsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.SaveSiteVisitData, params, paraName);
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
                        MessageWindow.successNSCFragment(ActivityDisconnectMeter.this, "" + enums.getMessage(), "Information", MainActivity.class);
                       /* PreferenceUtil.setWardId("");
                        PreferenceUtil.setMsrID("");
                        PreferenceUtil.setSrid("");
*/
                    } else {
                        MessageWindow.connectionFragment(ActivityDisconnectMeter.this, "" + enums.getMessage(), "Alert", MainActivity.class);
                    }
                    progress.dismiss();
                } else {
                    Toast.makeText(ActivityDisconnectMeter.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(ActivityDisconnectMeter.this, "" + updateJsonResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                progress.dismiss();
            }
            progress.dismiss();
        }


    }
}



