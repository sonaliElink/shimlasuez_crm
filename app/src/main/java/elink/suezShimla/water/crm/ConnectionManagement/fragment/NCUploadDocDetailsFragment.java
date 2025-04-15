package elink.suezShimla.water.crm.ConnectionManagement.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.tiper.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListActivityDetails;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitRequestModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment.AuthenticationDetFragment;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MasterData.Tables.DocSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.DocTypeModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static android.app.Activity.RESULT_OK;

public class NCUploadDocDetailsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,View.OnClickListener, MaterialSpinner.OnItemSelectedListener {
    String frgPass = "", remarkStr = "";
    Dialog dialogSignature;
String  imgDigitalSignature = "",signature="",documentSize = "";
    View view;
    private Signature mSignature;
    LinearLayout mContent;
    private Button mClear, mGetSign, mCancel;
    Bitmap bitmap;
    Bitmap signBitmap;//= Bitmap.createBitmap(500,300,Bitmap.Config.ARGB_8888);
    private ArrayList<String>  imageSize;

    SiteVisitModel model, customerModel, connectionModel, commercialFeasibilityModel, technicalFeasibilityModel, consumerIndex;
    SiteVisitListActivityDetails activity;

    Button btn_update, btn_update_back, btn_upload_document;
    TextInputEditText remarkEditText;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String updateJsonResponse = "", jsonPhotoResponse = "";
    private Context mCon;
    private boolean flag = false;


    private MaterialDialog progress, progressPhoto;
    String isgovtemp="", applitype="",appno="", applicationNumberStr = "", applicationDateStr = "", appointmentDateStr = "", firstNameStr = "", fatherStr = "", middleStr = "", lastNameStr = "", addresStr = "", landmarkStr = "", location="",
            streetLocalityStr = "", districtStr = "", cityStr = "", postalCodeStr = "", mobileNoStr = "", lanLineStr = "", emailStr = "", salutationStr = "";
    String connectionTypeId = "", connectionCategoryId = "", propertyTypeId = "", connectionPurposeId = "", noOfFloorStr = "", populationStr = "", noOfDwellingStr = "", noOfBedsStr = "0";
    String unAuthorizedConnectionId = "", civilConsWithCcmcWaterId = "", noOfDwellingUnitId = "", isOccupierSecurityId = "", governmentEmployeeId = "";
    String technicalFeasibilityId = "1", distanceNetworkLineSizeId = "", roadRestorationLengthId = "", roadTypeId = "", roadOwnerShipId = "", meterSanctionTypeId = "", lengthStr = "";
    String construction_completed, applied_dwelling_unit_has_connection,
            internal_network_available, disposal_of_water_available,
            rainwater_harvesting_system_available, any_existing_connection, add_consumer, meterSizeId, selectStorageCapacity, storageCapacity, storageCapacityType;
    String govValue, borevalue="",operstionvalue, borewell,operstional,connection_feasibility, water_availability, road_cutting_required,
            distribution_pipeline, distribution_id, leftConsumerStr, rightConsumerStr, road_length_id="", meterLocation = "", connection_size = "";
    String processDate = "", EMPCODE = "", MAC = "", sDivision = "", sSubDivision = "", sSection = "", sBU = "", sAppType = "", sType = "P", sSourceType = "", sFather = "", sMiddleName = "";
    String zoneId = "", wardId = "", MSRIDStr = "", SRIDStr = "", groupId = "", lotId = "", areaId = "";
    String StoredPath = "",consumerNo="";

    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/DigitSign/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected static Location mCurrentLocation;
    protected Boolean mRequestingLocationUpdates;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 12;
    private static final int REQUEST_PERMISSION_SETTING = 2;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double currentLatitude;
    private double currentLongitude;

    private ImageView uploadDoc, ivDistributionNetworkPhoto, ivPhotoOfMarkedInstallation, ivOtherPhoto;
    Intent cam_intent;
    public static final int RequestPermissionCode = 1;
    private int reqcode_img = 1010, distributionCode = 1011, photoMarkedCode = 1012, otherPhotoCode = 1013;
    Bitmap rotatedBitmap;
    String base64String = "",latitude="", longitude="", docSource = "NC", distributionBase64String = "", photoMarkedBase64String = "", otherPhotoBase64String = "";
    MaterialSpinner typeSpinner, subTypeSpinner;

    private DocTypeModel docTypeModel;
    private DocSubTypeModel docSubTypeModel;

    private List<DocTypeModel> docTypeModelList;
    private List<DocSubTypeModel> docSubTypeModelList;

    private String typeString = "", docTypeId = "", subtypeString = "", docSubTypeId = "";
    private ArrayList<String> docTypeList, docTypeIdList, docSubTypeList, docSubTypeIdList;
    private ArrayAdapter docTypeAdapter, docSubTypeAdapter;

    private RealmOperations realmOperations;

    private ArrayList<String> documentImages = new ArrayList<>();
    private ArrayList<String> documentSubtype = new ArrayList<>();

    LinearLayout ll_upload_document;

    private String imageName;
    File storageDir;
    Uri imageUri;
    double newImageHeight = 512.0;
    int imageQuality = 65;
    private FusedLocationProviderClient mFusedLocationClient;
    LocationManager lm;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    ImageView imgDigitalSign;

    double lat, lng;
    private static final int REQUEST_LOCATION = 1;
    TextView tvloctest;

    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;
    String getpipe,getsewerage,getconsumerlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View rootView = inflater.inflate(R.layout.fragment_upload_document, container, false);

        SiteVisitListActivityDetails activity = (SiteVisitListActivityDetails) getActivity();
        mCon = getContext();
        model = activity.getSiteVisitDataData();
        sDivision = model.getAM_APP_DIVISION();
        sSubDivision = model.getAM_APP_SUB_DIVISION();
        sSection = model.getAM_APP_SECTION();
        sBU = model.getAM_APP_BU();
     //   Toast.makeText(activity, ""+model.getAM_APP_ISMSCDCL_EMPLOYEE_ID(), Toast.LENGTH_SHORT).show();
        sAppType = model.getAM_AAPP_NO_TYPE();
        sType = model.getAM_AAPP_NO_TYPE();
        sSourceType = model.getAM_APP_SOURCE_TYPE();
      //  distribution_pipeline = model.getAM_APP_SPIPELINE();

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
         getpipe = preferences.getString("pipe", "");
         getsewerage = preferences.getString("seweragep", "");
         getconsumerlist = preferences.getString("adconsumer", "");
        // sFather = model.getFATHERNAME();
       /* if (sFather.equalsIgnoreCase("")) {
            sFather = "ABC";
        }*/
        sMiddleName = model.getAM_APP_MNAME();


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

        //showDialog();
        initializeViews(rootView);

        // getFirmTypeRequest();
        STARTTIME = UtilitySharedPreferences.getPrefs(mCon, AppConstant.STARTTIME);
        ENDTIME = UtilitySharedPreferences.getPrefs(mCon, AppConstant.ENDTIME);
        Calendar c = Calendar.getInstance();

        try {
            final SimpleDateFormat sdff = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdff.parse(STARTTIME);
            final Date dateObji = sdff.parse(ENDTIME);
            ALERTSTARTTIME = new SimpleDateFormat("hh:mm aa").format(dateObj);
            ALERTENDTIME = new SimpleDateFormat("hh:mm aa").format(dateObji);
        } catch (final ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        time = df.format(c.getTime());

        checkTimes(dateParsing(STARTTIME), dateParsing(time), dateParsing(ENDTIME));

        return rootView;
    }

    private void initializeViews(View rootView) {

        gson = new Gson();
        realmOperations = new RealmOperations(mCon);
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        customerModel = new SiteVisitModel();
        connectionModel = new SiteVisitModel();
        commercialFeasibilityModel = new SiteVisitModel();
        technicalFeasibilityModel = new SiteVisitModel();



        //consumerIndex = new SiteVisitModel();
        remarkEditText = rootView.findViewById(R.id.remarkEditText);
        subTypeSpinner = rootView.findViewById(R.id.subTypeSpinner);
        subTypeSpinner.setOnItemSelectedListener(this);
        typeSpinner = rootView.findViewById(R.id.typeSpinner);
        typeSpinner.setOnItemSelectedListener(this);
        imgDigitalSign= rootView.findViewById(R.id.imgDigitalSignDoc);
        imgDigitalSign.setOnClickListener(this);
        uploadDoc = rootView.findViewById(R.id.uploadDoc);
        uploadDoc.setOnClickListener(this);

        ivDistributionNetworkPhoto = rootView.findViewById(R.id.ivDistributionNetworkPhoto);
        ivDistributionNetworkPhoto.setOnClickListener(this);

        ivPhotoOfMarkedInstallation = rootView.findViewById(R.id.ivPhotoOfMarkedInstallation);
        ivPhotoOfMarkedInstallation.setOnClickListener(this);

        ivOtherPhoto = rootView.findViewById(R.id.ivOtherPhoto);
        ivOtherPhoto.setOnClickListener(this);

        btn_update = rootView.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);

        btn_upload_document = rootView.findViewById(R.id.btn_upload_document);
        btn_upload_document.setOnClickListener(this);

        btn_update_back = rootView.findViewById(R.id.btn_update_back);
        btn_update_back.setOnClickListener(this);

        ll_upload_document = rootView.findViewById(R.id.ll_upload_document);
        tvloctest= rootView.findViewById(R.id.tvloctest);
        consumerNo = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMER_NO);
        activity = (SiteVisitListActivityDetails) getActivity();
        StoredPath = DIRECTORY + pic_name + consumerNo + ".png";

        EMPCODE = UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE);
        try {
            EMPCODE = aes.decrypt( EMPCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MAC = PreferenceUtil.getMac();
        getLocation();

        setDocSourceDropDown(docSource);

    }
    private void checkTimes(Date startime, Date current_time, Date endtime) {

        boolean isInBetween = false;
        if (endtime.after(startime)) {
            if (startime.before(current_time) && endtime.after(current_time)) {
                isInBetween = true;
            }
        } else if (current_time.after(startime) || current_time.before(endtime)) {
            isInBetween = true;
        }
        if (isInBetween) {
            submitData = true;
        } else {

            timeoutAlertBox();
        }

    }
    private Date dateParsing(String dtStart) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    private void timeoutAlertBox() {
        MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                .title(R.string.alert)
                .titleColorRes(R.color.red_500)
                .content(rtimem + " " + ALERTSTARTTIME + " " + "to" + " " + ALERTENDTIME)
                .contentColor(this.getResources().getColor(R.color.colorPrimary))
                .canceledOnTouchOutside(false)
                .positiveText(R.string.ok)

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        Intent intent = new Intent(mCon, SplashScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                        dialog.dismiss();
                    }
                }).show();
    }
    private void setDocSourceDropDown(String docSource) {
        docTypeModelList = realmOperations.fetchDocTypeName(docSource);

        docTypeList = new ArrayList<>();
        docTypeIdList = new ArrayList<>();

        for (DocTypeModel docTypeModel : docTypeModelList) {
            String documentName = docTypeModel.getDOCUMENTTYPE();
            if (!documentName.equalsIgnoreCase("Mandatory Documents For Meter Installed")) {
                docTypeList.add(docTypeModel.getDOCUMENTTYPE());
                docTypeIdList.add(docTypeModel.getDOCUMENTTYPEID());
                //Log.d("check", String.valueOf(zoneName));
            }
        }

        ArrayList<String> docTypeArrayList = new ArrayList<>();
        docTypeArrayList.add("Select");
        docTypeArrayList.addAll(docTypeList);
        docTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, docTypeArrayList);
        docTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(docTypeAdapter);
        typeSpinner.setSelection(5);

        typeSpinner.setOnItemSelectedListener(this);
    }

    private void getConsumerData() {
        customerModel = activity.getCustomerList();

        applicationNumberStr = customerModel.getREQUEST_NO();
        applicationDateStr = customerModel.getAM_AAPP_DATE();
        appointmentDateStr = customerModel.getAM_APP_APPOINT_DATE();
        salutationStr = customerModel.getAM_APP_NMTITLE();
        firstNameStr = customerModel.getAM_APP_FNAME();
        middleStr = customerModel.getAM_APP_MNAME();
        sFather = customerModel.getFATHERNAME();
        lastNameStr = customerModel.getAM_APP_SURNAME();
        addresStr = customerModel.getAM_APP_ADDRESS1();
        landmarkStr = customerModel.getAM_APP_ADDRESS3();
        location = customerModel.getAM_APP_LOCALITY();
        streetLocalityStr = customerModel.getAM_APP_ADDRESS2();
        postalCodeStr = customerModel.getAM_APP_PINCODE();
        isgovtemp=customerModel.getIsgovtemp();
       // govValue= customerModel.getAM_APP_ISMSCDCL_EMPLOYEE_ID();
        try {
            if (isgovtemp.equals("Yes")) {
                govValue = "1";
            } else {
                govValue = "0";
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        Log.d("customerModel", "" + applicationNumberStr + " " + applicationDateStr + " " + appointmentDateStr + " " + firstNameStr + " " + lastNameStr + " " + addresStr);

    }

    private void getConnectionData() {

        connectionModel = activity.getConnectionList();

        Log.e("cdata","ID 1 :"+connectionModel.getAM_APP_ISMSCDCL_EMPLOYEE_ID());
      /*  sgovtemp=connectionModel.getAM_APP_ISMSCDCL_EMPLOYEE_ID();*/
        connectionTypeId = connectionModel.getAM_APP_TARIFF();
        connectionCategoryId = connectionModel.getAM_APP_CATEGORY();
        propertyTypeId = connectionModel.getAM_APP_PREMTYPE();
        connectionPurposeId = connectionModel.getAM_APP_PURPOSE();
        noOfFloorStr = connectionModel.getAM_APP_NO_OF_FLOORS();
        noOfBedsStr = connectionModel.getAM_APP_NO_OF_ROOMS();
        populationStr = connectionModel.getAM_APP_FAMILY_MEMBER();
        noOfDwellingStr = connectionModel.getAM_APP_NO_OF_DWELLING_UNITS();
       // applitype = connectionModel.getAM_APP_APPLI_TYPE();
       // appno = connectionModel.getAM_APP_NOOF_APPS();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getContext());
     //    applitype = sp.getString("aaplicationtype",null);
         appno = sp.getString("appno",null);
     /*   borewell = sp.getString("BORE",null);
        if(borewell.equals("yes")){
            borevalue="1";
        }else {
            borevalue="2";
        }*/
        operstional = sp.getString("OPRATIONAL",null);
        if(operstional.equals("yes")){
            operstionvalue="1";
        }else {
            operstionvalue="2";
        }
        int a = 0;

    }

    private void getCommercialData() {
        commercialFeasibilityModel = activity.getComercialFeasibilityList();

        unAuthorizedConnectionId = commercialFeasibilityModel.getAM_APP_ISAUTH_CONNECTION();
        civilConsWithCcmcWaterId = commercialFeasibilityModel.getAM_APP_CIVIL_CONS_CCMC_WATER();
        noOfDwellingUnitId = commercialFeasibilityModel.getAM_APP_NO_OF_DWELLING_UNITS();
        isOccupierSecurityId = commercialFeasibilityModel.getAM_APP_ISOCCUPIER_SECURITY();
        governmentEmployeeId = commercialFeasibilityModel.getAM_APP_ISMSCDCL_EMPLOYEE_ID();


        construction_completed = commercialFeasibilityModel.getAM_APP_ISCONSTRUCTION_COMP();
        applied_dwelling_unit_has_connection = commercialFeasibilityModel.getAM_APP_ISDWELLING_HASCONN();
        internal_network_available = commercialFeasibilityModel.getAM_APP_ISINTERNAL_NETWORK();
        disposal_of_water_available = commercialFeasibilityModel.getAM_APP_ISDISPOSAL_OFWATER();
        rainwater_harvesting_system_available = commercialFeasibilityModel.getAM_APP_ISRAINWATERHARVEST();
        any_existing_connection = commercialFeasibilityModel.getAM_APP_ISEXISTINGCONN();
        add_consumer = commercialFeasibilityModel.getAM_APP_EXISTINGCONN();
        noOfDwellingUnitId = commercialFeasibilityModel.getAM_APP_NO_OF_DWELLING_UNITS();
        add_consumer = commercialFeasibilityModel.getAM_APP_EXISTINGCONN();
        meterSizeId = commercialFeasibilityModel.getAM_APP_METERSIZE();
        storageCapacityType = commercialFeasibilityModel.getAM_APP_STORAGECAPACITYTYPE();
        storageCapacity = commercialFeasibilityModel.getAM_APP_STORAGECAPACITY();
        meterLocation = commercialFeasibilityModel.getAM_APP_METERLOCATION();
        connection_size = commercialFeasibilityModel.getAM_APP_CONNECTION_SIZE();
        // AM_APP_CONNECTION_SIZE

    }

    private void getTechnicalFeasibilityData() {
        technicalFeasibilityModel = activity.getTechnicalFeasibilityList();
       /* borewell = model.getAM_APP_BOREWELL();
        if(borewell.equals("yes")){
            borevalue="1";
        }else {
            borevalue="2";
        }
        operstional = model.getAM_APP_IS_OPERATIONAL();
        if(borewell.equals("yes")){
            operstionvalue="1";
        }else {
            operstionvalue="2";
        }*/
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        leftConsumerStr = pref.getString("RIGHTCONSUMER", "empty");
        distribution_pipeline = pref.getString("distributionpipeline", "empty");
        road_length_id = pref.getString("roadlength", "empty");
        if(road_length_id.equals("")){
            road_length_id="0";
        }
        water_availability = pref.getString("wateravailability", "empty");
        road_cutting_required = pref.getString("roadcut", "empty");

        connection_feasibility = technicalFeasibilityModel.getAM_APP_ISTECH_FESIBILITY();

       // water_availability = technicalFeasibilityModel.getAM_APP_ISWATER_AVAILINDP();
      //  road_cutting_required = technicalFeasibilityModel.getAM_APP_ISROADCUTTING_REQD();
       // distribution_pipeline = technicalFeasibilityModel.getAM_APP_SPIPELINE();
        distribution_id = technicalFeasibilityModel.getAM_APP_DISTRIBUTIONID();
        //leftConsumerStr = technicalFeasibilityModel.getAM_APP_SLEFTCONSUMER();
        //rightConsumerStr = technicalFeasibilityModel.getAM_APP_SRIGHTCONSUMER();
      //  road_length_id = technicalFeasibilityModel.getAM_APP_ROADCUTMTR();
        roadTypeId = technicalFeasibilityModel.getAM_APP_ROADTYPE();
        roadOwnerShipId = technicalFeasibilityModel.getAM_APP_ROADOWNER();
     //   roadTypeId = technicalFeasibilityModel.getAM_APP_NO_OF_DWELLING_UNITS();

      //  technicalFeasibilityId = technicalFeasibilityModel.getAM_APP_ISTECH_FESIBILITY();
        /* distanceNetworkLineSizeId = technicalFeasibilityModel.getAM_APP_SPIPELINE();
        lengthStr = technicalFeasibilityModel.getAM_APP_LENGTH_MEASURE();
        roadRestorationLengthId = technicalFeasibilityModel.getAM_APP_ROADCUTMTR();
        roadTypeId = technicalFeasibilityModel.getAM_APP_ROADTYPE();
        roadOwnerShipId = technicalFeasibilityModel.getAM_APP_ROADOWNER();
        meterSanctionTypeId = technicalFeasibilityModel.getAM_APP_CONNECTION_SIZE();*/


    }

    private void getConsumerIndexData() {

        // consumerIndex = activity.getConsumerIndexData();

     /*   zoneId = consumerIndex.getAM_APP_ZONE();
        wardId = consumerIndex.getAM_APP_CIRCLE();
        MSRIDStr = consumerIndex.getSBM_NAME();
        SRIDStr = consumerIndex.getTRM_NAME();
        groupId = consumerIndex.getAM_APP_PC();
        areaId = consumerIndex.getAM_APP_AREA();
        lotId = consumerIndex.getAM_APP_MRC();*/
        zoneId = "0";
        wardId = "0";
        MSRIDStr = "0";
        SRIDStr = "0";
        groupId = "0";
        areaId = "0";
        lotId = "0";
        Log.d("getConsumerIndexData", "" + lotId);


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser) {
                new NCUploadDocDetailsFragment();
                gson = new Gson();
                realmOperations = new RealmOperations(mCon);
                connection = new ConnectionDetector(mCon);
                invServices = new Invoke();
                customerModel = new SiteVisitModel();
                connectionModel = new SiteVisitModel();
                commercialFeasibilityModel = new SiteVisitModel();
                technicalFeasibilityModel = new SiteVisitModel();
                getConsumerData();
                getConnectionData();
                getCommercialData();
                getTechnicalFeasibilityData();
                getConsumerIndexData();

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_update: {
                if (validation()) {

                    UpdateSiteVisitData();
                }
            }
            break;

            case R.id.btn_update_back:
                backFragmentActivity();
                break;

            case R.id.imgDigitalSignDoc:
                dialog_digi_signature();
                break;

            case R.id.uploadDoc:
                enableRuntimePermission();

                try {
                    //cam_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startCameraIntent(reqcode_img);
                    // startActivityForResult(cam_intent, reqcode_img);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
                break;

            case R.id.ivDistributionNetworkPhoto:
                enableRuntimePermission();
                //cam_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startCameraIntent(distributionCode);

                    //startActivityForResult(cam_intent, distributionCode);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
                break;

            case R.id.ivPhotoOfMarkedInstallation:
                enableRuntimePermission();
                try {
                    startCameraIntent(photoMarkedCode);

                    //startActivityForResult(cam_intent, photoMarkedCode);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
                break;

            case R.id.ivOtherPhoto:
                enableRuntimePermission();
                // cam_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startCameraIntent(otherPhotoCode);

                    //  startActivityForResult(cam_intent, otherPhotoCode);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
                break;

            case R.id.btn_upload_document: {
                  if (checkPhotoValidation()) {
                if (validation()) {
                    gson = new Gson();
                    realmOperations = new RealmOperations(mCon);
                    connection = new ConnectionDetector(mCon);
                    invServices = new Invoke();
                    customerModel = new SiteVisitModel();
                    connectionModel = new SiteVisitModel();
                    commercialFeasibilityModel = new SiteVisitModel();
                    technicalFeasibilityModel = new SiteVisitModel();
                    getConsumerData();
                    getConnectionData();
                    getCommercialData();
                    getTechnicalFeasibilityData();
                    getConsumerIndexData();

                    remarkStr = remarkEditText.getText().toString();
                    for (int i = 0; i < documentImages.size(); i++) {
                        //  Log.d("Running " + i, "doctype " + documentSubtype.get(i));
                        uploadPhoto(documentImages.get(i), "", applicationNumberStr, "", "", "A", "304", documentSubtype.get(i), EMPCODE, MAC, remarkStr);
                    }

                    //  if (validation()) {
                    UpdateSiteVisitData();
                    // }
                }
            }
            }
            break;
            default:
                break;
        }
    }
    public  class Signature extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public Signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        @SuppressLint("WrongThread")
        public void save(View v, String StoredPath) {
            Log.v("tag", "Width: " + v.getWidth());
            Log.v("tag", "Height: " + v.getHeight());

            File dir = new File(getContext().getExternalFilesDir(null), "Suez CRM");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File insideDigitalSignatureFolder = new File(dir, "Digital Signature");
            if (!insideDigitalSignatureFolder.exists()) {
                insideDigitalSignatureFolder.mkdirs();
            }

            File destination = new File(insideDigitalSignatureFolder, EMPCODE + "_" + System.currentTimeMillis() + ".jpg");

            if (signBitmap == null) {
                signBitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }

            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(destination);
                ByteArrayOutputStream out = new ByteArrayOutputStream();

             /*   BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeFile(destination.getAbsolutePath(), bmOptions);*/

                Canvas canvas = new Canvas(signBitmap);
                v.draw(canvas);
                // Convert the output file to Image such as .png
                signBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

                byte[] profileImage = out.toByteArray();

                imgDigitalSignature = Base64.encodeToString(profileImage, Base64.NO_WRAP);

                // Log.e("imgDigitalSignature", imgDigitalSignature);
                //Log.e("destination", String.valueOf(destination));

                if (imgDigitalSignature.equals("") || imgDigitalSignature == null) {
                    MessageWindow.messageWindow(mCon, "Please Sign", "Alert");
                } else {
                    //Call api to send digital image to server whose id is 298
                    documentSize = "298";
                    imgDigitalSign.setImageBitmap(signBitmap);

                    setImageToList("464", imgDigitalSignature);
                    //setImageIdToList(5, "298");

                    String ImageSize = getImageSize(imgDigitalSignature);
                  //  setImageSizeToList(5, ImageSize);

                    // documentImages.add(imgDigitalSignature);
                    //documentSubtype.add("298");

                    dialogSignature.dismiss();
                   // buttonName = "digital_signature";

                    //  saveImage(imgDigitalSignature, consumerNo, oldmeterNumStr, meterNumStr, "298", empCode, ip, "digital_signature");

                    /*if (buttonName.equalsIgnoreCase("digital_signature")) {
                        digitalImage = "y";
                    }*/

                }
                mFileOutStream.flush();
                mFileOutStream.close();
            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }
        }

        public void clear() {
            path.reset();
            paint.reset();

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);

            if (signBitmap == null) {
                signBitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(signBitmap);
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:
                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {
            Log.v("log_tag", string);
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    private String getImageSize(String imgString) {
        String imgSize = "";
        int stringLength = imgString.length() - "data:image/png;base64,".length();
        double sizeInBytes = 4 * Math.ceil((stringLength / 3)) * 0.5624896334383812;
        double sizeInKb = sizeInBytes / 1000;
        imgSize = String.valueOf((int) Math.round(sizeInKb));
        return imgSize;
    }
    public void setImageToList(String pos, String strImage) {
        documentImages.add(strImage);
        documentSubtype.add(pos);
      //  documentImages.set(pos, strImage);

    }

    public void setImageIdToList(int pos, String strImage) {

        documentSubtype.set(pos, strImage);
    }

    public void setImageSizeToList(int pos, String imgSize) {
        imageSize.set(pos, imgSize);
    }

    public void dialog_digi_signature() {
        dialogSignature = new Dialog(getContext());
        dialogSignature.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSignature.setContentView(R.layout.digital_signature);
        dialogSignature.setCancelable(true);

        mContent = (LinearLayout) dialogSignature.findViewById(R.id.linearLayout);
        mSignature = new Signature(getContext(),null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mClear = (Button) dialogSignature.findViewById(R.id.clear);
        mGetSign = (Button) dialogSignature.findViewById(R.id.getsign);
        mGetSign.setEnabled(true);
        mCancel = (Button) dialogSignature.findViewById(R.id.cancel);
        view = mContent;

        if (!mClear.hasOnClickListeners()) {
            mClear.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mSignature.clear();
                }
            });
        }

        mGetSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                view.setDrawingCacheEnabled(true);
                mSignature.save(view, StoredPath);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogSignature.dismiss();
                mClear.setOnClickListener(null);
                mGetSign.setOnClickListener(null);
               // btn_digital_signature.setChecked(false);
            }
        });

        dialogSignature.show();
    }

    private void startCameraIntent(int requestCode) {//there
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's activity_login ic_camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "elink.suezShimla.water.crm", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    private File createImageFile() {//this

        File image = null;
        try {
            String imageFileName = "PROFILE";

            storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
            Toast.makeText(mCon, "Please capture photo of road", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (distributionBase64String.equalsIgnoreCase("")) {
            Toast.makeText(mCon, "Please capture distribution network photo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (photoMarkedBase64String.equalsIgnoreCase("")) {
            Toast.makeText(mCon, "Please capture marked space meter to be install ", Toast.LENGTH_SHORT).show();
            return false;
        }

    /*    if (otherPhotoBase64String.equalsIgnoreCase("")) {
            Toast.makeText(mCon, "Please capture other document", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if (imgDigitalSignature.equalsIgnoreCase("")) {
            Toast.makeText(mCon, "Please take sign of Consumer ", Toast.LENGTH_SHORT).show();
            return false;
        }

        remarkStr = remarkEditText.getText().toString();
        if (remarkStr.equalsIgnoreCase("")) {
            remarkEditText.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_remark, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            remarkEditText.setError(null);
        }




        return true;
    }

    private void uploadPhoto(String base64String, String consumerNo, String applicationNumberStr, String oldMeter, String newMeter, String DocSource, String docTypeId, String docSubTypeId, String EMPCODE, String MAC, String remarkStr) {

        String params[] = new String[11];

        params[0] = base64String;
        params[1] = consumerNo;
        params[2] = applicationNumberStr;//applicationNumberStr;
        params[3] = "";
        params[4] = "";
        params[5] = "A";
        params[6] = docTypeId;
        params[7] = docSubTypeId;
        params[8] = EMPCODE;
        params[9] = MAC;
        params[10] = remarkStr;//remarkStr;

        if (connection.isConnectingToInternet()) {
            PhotoUpload photoUpload = new PhotoUpload();
            photoUpload.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class PhotoUpload extends AsyncTask<String, Void, Void> {
        MaterialDialog progressImageSite;

        @Override
        protected void onPreExecute() {
            progressImageSite = new MaterialDialog.Builder(mCon)
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


                jsonPhotoResponse = invServices.getuploadphoto(Constants.URL, Constants.NameSpace, Constants.UploadPhoto, params, paraName);

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
                    Toast.makeText(getActivity(), R.string.failure, Toast.LENGTH_SHORT).show();
                } else {
                    progressImageSite.dismiss();

                    if (enums[0].equalsIgnoreCase("Success")) {
                        // Toast.makeText(mCon, "Site visit verification document uploaded successfully", Toast.LENGTH_SHORT).show();
                        progressImageSite.dismiss();


                    } else {
                        Log.d("check", jsonPhotoResponse);
                        Toast.makeText(mCon, "Site visit verification document not uploaded", Toast.LENGTH_SHORT).show();

                        progressImageSite.dismiss();


                    }
                    progressImageSite.dismiss();
                    //    ll_upload_document.setVisibility(View.GONE);
                }
                progressImageSite.dismiss();
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "Document" + jsonPhotoResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintRegisterActivity", "register complaint button", error);
                progressImageSite.dismiss();
            }

        }
    }

    private void backFragmentActivity() {
        ((SiteVisitListActivityDetails) getActivity()).onClickPrev();
    }

    private void UpdateSiteVisitData() {
        String params[] = new String[74];
        String newapptype="";
      /*  if (applitype.equalsIgnoreCase("Single")) {
            //newapptype= appno;
            newapptype="0";
        }else {
            newapptype="1";
        }*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.ENGLISH);

        processDate = df.format(new Date());
       String newlatitude= String.valueOf(lat);
        String newlong= String.valueOf(lng);
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

        params[21] = govValue;
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
        params[38] = EMPCODE;
        params[39] = MAC;
        params[40] = sDivision;

        params[41] = sSubDivision;
        params[42] = sSection;
        params[43] = sBU;
        params[44] = sAppType;
        params[45] = sType;
        params[46] = EMPCODE;
        params[47] = sSourceType;
        params[48] = water_availability;
        params[49] = distribution_id;
        params[50] = leftConsumerStr;//rightConsumerStr;


        params[51] = rightConsumerStr;
        params[52] = road_cutting_required;
        params[53] = construction_completed;
        params[54] = any_existing_connection;
        params[55] = add_consumer;

        params[56] = applied_dwelling_unit_has_connection;//noOfDwellingUnitId;
        params[57] = meterSizeId;
        params[58] = storageCapacityType; //underground null
        params[59] = storageCapacity;//overhead
        params[60] = internal_network_available;

        params[61] = disposal_of_water_available;
        params[62] = rainwater_harvesting_system_available;
        params[63] = borevalue;
        params[64] = operstionvalue;

        params[65] = meterLocation;
        params[66] = newlatitude;
        params[67] = newlong;
        params[68] = "0";// newapptype;
        params[69] = "1";// appno;
        params[70] = govValue;
        params[71] = getpipe;
        params[72] = getconsumerlist;
        params[73] = getsewerage;
        Log.e("updateJsonParams", "json : " + Arrays.toString(params));


        if (connection.isConnectingToInternet()) {
            UpdateSiteVisit updateSiteVisit = new UpdateSiteVisit();
            updateSiteVisit.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
        switch (materialSpinner.getId()) {
            case R.id.typeSpinner: {
                typeString = typeSpinner.getSelectedItem().toString();


                if (typeString.equalsIgnoreCase("Select")) {

                } else {


                    docTypeModel = realmOperations.fetchDocTypeId(typeString);
                    docTypeId = String.valueOf(docTypeModel.getDOCUMENTTYPEID());

                    setSubTypeDropDown(docTypeId);
                }
            }
            break;

            case R.id.subTypeSpinner: {
                subtypeString = subTypeSpinner.getSelectedItem().toString();


                if (subtypeString.equalsIgnoreCase("Select")) {

                } else {

                    docSubTypeModel = realmOperations.fetchDocSubTypeId(subtypeString, docTypeId);
                    docSubTypeId = String.valueOf(docSubTypeModel.getDOCUMENTTYPEID());

                }
            }
            break;

            default:
        }
    }

    private void setSubTypeDropDown(String docTypeId) {
        docSubTypeModelList = realmOperations.fetchDocSubTypeName(docTypeId);

        docSubTypeList = new ArrayList<>();
        docSubTypeIdList = new ArrayList<>();

        for (DocSubTypeModel docTypeModel : docSubTypeModelList) {
            docSubTypeList.add(docTypeModel.getDOCUMENTSUBTYPE());
            docTypeIdList.add(docTypeModel.getDOCUMENTTYPEID());
            //Log.d("check", String.valueOf(zoneName));

        }

        ArrayList<String> docSubTypeArrayList = new ArrayList<>();
        docSubTypeArrayList.add("Select");
        docSubTypeArrayList.addAll(docSubTypeList);
        docSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, docSubTypeArrayList);
        docSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subTypeSpinner.setAdapter(docSubTypeAdapter);
        subTypeSpinner.setSelection(0);

        subTypeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onNothingSelected(MaterialSpinner materialSpinner) {

    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateSiteVisit extends AsyncTask<String, Void, Void> {
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
                String paraName[] = new String[74];
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
                paraName[63] = "sBorewell";
                paraName[64] = "sOperational";
                paraName[65] = "sMeterLocation";
                paraName[66] = "sLat";
                paraName[67] = "sLong";
                paraName[68] = "sAppTypes";
                paraName[69] = "sNoOfApps";
                paraName[70] = "isgovtemp";
                paraName[71] = "sPassConsumer";
                paraName[72] = "sPassConList";
                paraName[73] = "sSewerage";




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
                        MessageWindow.successNSCFragment(mCon, "" + enums.getMessage(), "Information", MainActivity.class);
                    } else {
                        MessageWindow.connectionFragment(mCon, "" + enums.getMessage(), "Alert", MainActivity.class);
                    }
                    progress.dismiss();
                } else {
                    Toast.makeText(mCon, "Something went wrong", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "" + updateJsonResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                progress.dismiss();
            }
            progress.dismiss();
        }
    }

    private boolean validation() {
        remarkStr = remarkEditText.getText().toString();
        if (remarkStr.equalsIgnoreCase("")) {
            remarkEditText.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_remark, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            remarkEditText.setError(null);
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == reqcode_img && resultCode == RESULT_OK) {

                base64String = setImage(uploadDoc);
                if (!base64String.equalsIgnoreCase("")) {
                    documentImages.add(base64String);
                    documentSubtype.add("301");
                } else {
                    base64String = "";
                    // uploadDoc.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_mmg));
                }


            } else {
                //  Toast.makeText(activity, "dguyuyfvuy", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == distributionCode && resultCode == RESULT_OK) {
                distributionBase64String = setImage(ivDistributionNetworkPhoto);
                if (!distributionBase64String.equalsIgnoreCase("")) {
                    documentImages.add(distributionBase64String);
                    documentSubtype.add("300");
                } else {
                    distributionBase64String = "";
                    ivDistributionNetworkPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_mmg));

                }
            }


            if (requestCode == photoMarkedCode && resultCode == RESULT_OK) {
                photoMarkedBase64String = setImage(ivPhotoOfMarkedInstallation);
                if (!photoMarkedBase64String.equalsIgnoreCase("")) {
                    documentImages.add(photoMarkedBase64String);
                    documentSubtype.add("302");
                } else {
                    photoMarkedBase64String = "";
                    ivPhotoOfMarkedInstallation.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_mmg));

                }
            }


            if (requestCode == otherPhotoCode && resultCode == RESULT_OK) {
                otherPhotoBase64String = setImage(ivOtherPhoto);
                if (!otherPhotoBase64String.equalsIgnoreCase("")) {
                    documentImages.add(otherPhotoBase64String);
                    documentSubtype.add("303");
                } else {
                    photoMarkedBase64String = "";
                    ivOtherPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_mmg));

                }
            }
            else if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    startLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    checkLocationSettings();
                    break;
            }
        } else if (requestCode == REQUEST_PERMISSION_SETTING) {
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        detectLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        getLocation();
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void enableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            Toast.makeText(mCon, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
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

            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (PResult.length > 0
                        && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(mCon,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                    }

                } else {
                    boolean showRationale = false;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        showRationale = shouldShowRequestPermissionRationale(per[0]);
                    }
                    if (!showRationale) {

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                        alertBuilder.setCancelable(false);
                        alertBuilder.setTitle(R.string.permissionNecessary);
                        alertBuilder.setMessage(R.string.locationPermissionIsNecessaryGivePermissionFromSetting);
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", mCon.getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();


                    } else {
                        Toast.makeText(mCon, R.string.permissionDenied, Toast.LENGTH_LONG).show();
                        getLocation();
                    }


//                     checkLocationPermission();
                }
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
            Matrix  matrix = new Matrix();

            int nh = (int) (bitmap.getHeight() * (newImageHeight / bitmap.getWidth()));
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) newImageHeight, nh, true);
            rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

            // iv_visiting_card_proprietor.setImageBitmap(scaled);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//====================================
            String texst=  lat + "-" + lng + "";


            Canvas cs = new Canvas(rotatedBitmap);
            Paint tPaint = new Paint();
            tPaint.setTextSize(25);//30
            tPaint.setColor(Color.WHITE);
            tPaint.setStyle(Paint.Style.FILL);
            cs.drawBitmap(rotatedBitmap, 0f, 0f, null);
            float height = tPaint.measureText("yY");

            cs.drawText(texst,10f, height + 10f, tPaint);
            //======================================


            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            img.setImageBitmap(rotatedBitmap);
            imgString = Base64.encodeToString(b, Base64.DEFAULT);


        } catch (Exception ex) {
            ex.getMessage();
        }

        return imgString;
    }

    ///====================added sonali============================
       @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    private void getLocation() {

        final boolean result = checkPermission(mCon);

        if (result) {
           // getLocations();
            //buildGoogleApiClient();
            locationService();

           // createLocationRequest();

          //  buildLocationSettingsRequest();

           // checkLocationSettings();
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Location permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }


    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(mCon)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        // result.setResultCallback(this);
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        if (mGoogleApiClient.isConnected()) {
                            startLocationUpdates();
                        } else {
                            buildGoogleApiClient();
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {

                            status.startResolutionForResult(
                                    getActivity(),
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });

    }

    protected void startLocationUpdates() {
        detectLocation();
    }

    private void detectLocation() {

        if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    mRequestingLocationUpdates = true;
                }
            });
        }
    }


    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            final boolean mock = isMockSettingsON(mCon, mCurrentLocation);
            // Log.d("check", "mock = " + mock);
            if (mock) {
                Toast.makeText(mCon, R.string.turnOffMockLocation, Toast.LENGTH_SHORT).show();
                //finish();
            } else {

                latitude = String.valueOf(mCurrentLocation.getLatitude());
                longitude = String.valueOf(mCurrentLocation.getLongitude());
                // Log.d("check", latitude + ", " + longitude);
                //   Toast.makeText(mCon, String.valueOf(mCurrentLocation.getLatitude()) + String.valueOf(mCurrentLocation.getLongitude()), Toast.LENGTH_LONG).show();
            }
        }
    }


    public static boolean isMockSettingsON(Context context, Location location) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            boolean mockSettingOff = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
            return !mockSettingOff;
        } else {
            return location.isFromMockProvider(); //should use location object to detect mock locaiton
        }

    }


    @Override
    public void onConnected(@Nullable  Bundle bundle) {
        if (mCurrentLocation == null) {
            if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                updateLocationUI();
            }
        }
    }
    private void getLocations() {
        if (ActivityCompat.checkSelfPermission(
                getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager =(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria c=new Criteria();
            //if we pass false than
            //it will check first satellite location than Internet and than Sim Network
            String   provider=locationManager.getBestProvider(c, false);
            Location locationGPS = locationManager.getLastKnownLocation(provider);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

            } else {
                //Toast.makeText(getActivity(), "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull  ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(activity, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            //Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

    }


    ////////////----new-----------


    private void locationService() {

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait while fetching data from GPS .......");
            progressDialog.setCancelable(false);
            progressDialog.show();


            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            final android.location.LocationListener locationListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                progressDialog.dismiss();

                return;
            }

            progressDialog.dismiss();

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        tvloctest.setText(String.valueOf(lat));


                    } else {
                        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }

                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                        } else if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
                        }
                    }
                }
            });
        } else {
//            checkGpsStatus();
            Toast.makeText(getContext(), "GPS off", Toast.LENGTH_SHORT).show();
        }
    }
    public static class MyLocationListener implements android.location.LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

         String   longitude = String.valueOf(loc.getLongitude());
            String latitude = String.valueOf(+loc.getLatitude());

          double  lat = loc.getLatitude();
            double  lng = loc.getLongitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

/*
    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App)mCon.getApplicationContext();
        if (myApp.wasInBackground) {
            getActivity().finish();
            startActivity(new Intent(mCon, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }
    @Override
    public void onPause() {
        super.onPause();
        ((App) mCon.getApplicationContext()).startActivityTransitionTimer();
    }*/

}

