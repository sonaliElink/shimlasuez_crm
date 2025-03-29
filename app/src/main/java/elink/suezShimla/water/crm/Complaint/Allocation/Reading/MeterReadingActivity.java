package elink.suezShimla.water.crm.Complaint.Allocation.Reading;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Complaint.Allocation.Activity.WorkAllocationCompletionActivity;
import elink.suezShimla.water.crm.Complaint.Allocation.Reading.model.ConsumersModel;
import elink.suezShimla.water.crm.Complaint.Reallocation.Activity.WorkReAllocationCompletionActivity;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.LoginModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterObservationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterStatusModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.LocationTrack;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import elink.suezShimla.water.crm.map.SingleMapLocationActivity;
import io.realm.RealmResults;


public class MeterReadingActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener, View.OnLongClickListener {

    private Context mCon;
    private RealmOperations realmOperations;

    private CardView consumerDetailCardView, cameraCardView, cameraOptionalCardView;
    private AppCompatImageView cameraImageView, cameraOptionalImageView, searchConsumer;
    private MaterialDialog progress;
    private LinearLayout consumerDetailLinearLayout, cameraLinearLayout, cameraOptionalLinearLayout, ll_consumer_activity;

    private AppCompatCheckBox isUpDateMobileCheckBox, isSealBrokenCheckBox, isMeterTemperedBrokenCheckBox, requiredMeterShiftingCheckBox, violationOfTariffCheckBox,
            underConstructionCheckBox, leakageInMeterFittingCheckBox, ballValveReplacementCheckBox, overflowOfWaterCheckBox, consumerAbusedManhandleCheckBox, illegalConnectionCheckBox, meterByPassCheckBox;

    private TextInputLayout consumerNoInputLayout, meterReadingMandatoryInputLayout, upMobileNoInputLayout, readerRemarkInputLayout,
            localityInputLayout, landMarkInputLayout, meterLocationInputLayout, newMeterNumberInputLayout;

    private TextInputEditText upMobileNoEditText, consumerNoEditText, meterReadingMandatoryEditText, readerRemarkEditText,
            meterLocationEditText, landMarkEditText, nearByLandMarkEditText, newMeterNumberMandatoryEditText;

    private TextView consumerNoTextView, consumerNameTextView, consumerMobileNoTextView, consumerAddressTextView, buildingIdTextView, tariffTextView, meterNoTextView, readingDateTextView,
            meterConsumptionTextView, pastMeterConsumptionTextView, pastMeterReadingTextView, pastMeterReadingDateTextView, sequencIdTextView, wardNameTextView, propertyAssessmentNumberTextView, gisbidTextView, tvUploadImageViewCureentTimeStamp,
            tvUploadImageViewOptionalCureentTimeStamp, tv_property;
    private ImageView arrowImageView;

    private AppCompatSpinner meterStatusSpinner, readerObservationSpinner;
    private MaterialButton submitButton;

    private String strConsumerNo = "", nameObservationStr = "", strName = "", strBillMonth = "", strMobileNo, strAddress, strBuldingId, strRDRID, strTariff, strMeterImg, strOptionalImg, strMeterNo, strCurrentDateTime,
            strCustomerNo, strMeterReadingMandatory, strMeterReading, strPastMeterConsumption, strPastMeterReading, strPastMeterReadingDate, strUpdatedMobileNo,
            strReadingRemark = "", strLocality = "", strMeterStatus, strMeterObservation, strNearBy, rdRid, meterObservation, employeeCode, strReaderRemark = "", strSequenceId = "",
            strPastMeterStatus, statusName = "", strPastObservation = "", CurrentDate, spinMeterStatus, spinMeterObservation, strWardname, strpropertyAssessmentNumber, ml = "", ca = "", timeStamp = "", strMeterLocation = "", strLat = "", strLon = "";

    private String isSealStr = "0", isTempStr = "0", isTariffStr = "0", isShiftingStr = "0", isUnderConstStr = "0", isLeakageStr = "0",
            isBallValStr = "0", isOverflowStr = "0", isAbusedStr = "0", isBypassStr = "0", isIllegalStr = "0";

    private int consumerID, consumertrialID, strIsMobileNoUpdated, strIsSealBroken, strIsMeterTemperedBroken, strIsRequiredMeterShifting, strViolationOfTariff, meterStatusId, meterObservationId, meterReadingInt,
            intUpdatedMobileNo, seqNumber, strMeterConsumption, meterDigit, billMonth, pastMeterConsumptionInt, currentConsumption, highConsumption, lowConsumption, rid, readingFlag,
            rereadingStatus, underConstruction, leakageInMeterFitting, ballValveReplacement, overflowOfWater, consumerAbusedManhandle, pastMeterReadingInt, illegalConection, meterByPass,
            formula = 0, positionrightId = 0, positionId = 0, consumerSwipeID = 0, positionleftId = 0, newMeterNumberInt;
    //  String  pastMeterReadingInt="";
    int rightposition = 0, leftposition = 0;
    String consumerNum = "";
    private ConsumersModel consumersModel = new ConsumersModel();

    private ArrayAdapter meterObservationAdapter, meterStatusAdapter;
    private ArrayList<String> meterStatusData = new ArrayList<>();
    //   private ArrayList<MeterStatusModel> meterStatusDataList;

    //  private ArrayList<String> meterObservationData;
    private static Location loc;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 11;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 12;
    private File destination = null, destinationOptional = null;
    private static final int REQUEST_CAMERA = 3;
    private String encodedBase64Image = "", docName, docType, formulaValue;


    Double latitude, longitude;
    private int rotation;

    private String consumerNoStr, complaintNoStr, complaintWorkAllocateDateStr, complaintDateStr, complaintSubTypeStr, tariffStr, statusStr,
            consumerNameStr, addressStr, priorityStr, contactNoStr, zoneStr, subZoneStr, disputeBillMonthYrStr, meterNoStr, meterTransIdStr, address1Str, address2Str,
            address3Str, pincodeStr, complaintTypeStr, completionDateStr, completionTimeStr, remarkStr, actionStr, actionPosstion, actionIdStr, workCompletionDateTime,
            observationStr, observationIdStr, searchForStr, otpStr, otpReasonStr, timeFormat, AM_PM, OTP, userType, filterIdStr, vipName, actionFormStr = "", complaintCode = "";

    String cscmName = "", cscmID = "", JsonFinishActionData = "", departmentID = "", mainComplaintID = "", mainComplaintCode = "", filterId = "", repeatCall = "", agging = "", sla = "", observationId = "", comType = "", wType = "W";
    private String fromDate = "", toDate = "", clickStr = "", comseqStr = "", comPno = "", pastReading = "";


    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double currentLatitude;
    private double currentLongitude;
    private static final int REQUEST_CAMERA_OPTIONAL = 4;
    private String encodedBase64OptionalImage = "NOPHOTO", docNameOptional, docTypeOptional, docTypeNameOptional;
    private MeterStatusModel meterStatusModel;
    private MeterObservationModel meterObservationModel;

    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected static Location mCurrentLocation;

    boolean isReadingValid = false, isPhotoValid = false;
    private boolean isReadingMandatory = false, isPhotoMandatory = false;
    private String consumerNo;
    //  private AllocatedReadingModel allocatedReadingModel = new AllocatedReadingModel();
    //  private EmployeeLoginModel employeeLoginModel = new EmployeeLoginModel();
    private String jsonResponse = "", jsonMeterReadingResponse = "", imei, mac, searchDataText = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private int searchCheck = 0, nextId, errorNextId;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 12;
    private static final int REQUEST_PERMISSION_SETTING = 2;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 0;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 0;

    protected Boolean mRequestingLocationUpdates;
    private String faultyObservation;

    AlertDialog.Builder builder;

    GestureDetector gestureDetector;
    //private ReadingNotDoneAdapter readingNotDoneAdapter;


    //   private List<ConsumersModel> data;

    //    private List<ConsumersModel> consumersModels = new ArrayList<>();
    //   private List<ConsumersModel> consumersModelsList = new ArrayList<>();
    ConsumersModel model;
    int consumerpositionID = 0;

    String Tag = "", mobile = "", location = "", strNewMeterNumber = "", meterStatusSubstrngName = "", empCode = "", AppVersion = "", deviceAuthorization = "", appIsLogged = "", RDRID = "";

    boolean submitData = false;

    boolean GpsStatus;

    Uri photoURI;

    Bitmap thumbnail, optionalThumbnail, rotatedBitmap, optionalBitmap;

    TextView tv_latitude, tv_longitude, billMonthTextView, pastReadingTextView;
    String strLatitude = "00.0000000", strLongitude = "00.0000000", format = "";

    PhotoView image_preview;
    ImageButton image_preview_close;
    FrameLayout fl_preview_image;
    ImageView iv_back, iv_location;
    AppCompatTextView tv_toolbar;
    //spinnerList;

    ArrayList<String> msList = new ArrayList<String>();
    ArrayList<String> msValueList = new ArrayList<String>();
    ArrayList<String> moList = new ArrayList<String>();
    ArrayList<String> moValueList = new ArrayList<String>();

    LinearLayout ll_meter_reading;
    RelativeLayout relativeLayout2, relativeLayout3;

    RealmResults<MeterObservationModel> meterObservationModels;

    private ScaleGestureDetector scaleGestureDetector = null;

    SimpleDateFormat simpleDateFormat;


    String STARTTIME = "", ALERTSTARTTIME = "", BILLMONTH = "";
    String ENDTIME = "", ALERTENDTIME = "";
    String rtimem = "Reading can be taken between";
    LocationTrack locationTrack;

    String time;
    Calendar calander;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"MissingPermission", "NewApi"})


    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;


    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meter_reading_activity);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //setTitle();

        //  tv_toolbar = findViewById(R.id.tv_toolbar);

        // tv_toolbar.setText(getResources().getString(R.string.consumer_reading));

        mCon = this;
        realmOperations = new RealmOperations(mCon);
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        builder = new AlertDialog.Builder(this);

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
        comseqStr = getIntent().getStringExtra("comseq");
        comPno = getIntent().getStringExtra("comPno");
        pastReading = getIntent().getStringExtra("pastReading");

        empCode = UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE);
        try {
            empCode = aes.decrypt( empCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        departmentID = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEPARTMENTID);
//        departmentID = aes.decrypt( departmentID.getBytes(), secretKey, IV);

        try {
            departmentID=  new AesAlgorithm().decrypt(departmentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
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


        Intent startingIntent = getIntent();
        consumerID = startingIntent.getIntExtra("consumerId", 0);
        readingFlag = startingIntent.getIntExtra("readingFlag", 0);
        searchCheck = startingIntent.getIntExtra("searchCheck", searchCheck);

        positionId = startingIntent.getIntExtra("positionId", 0);
        consumerNum = startingIntent.getStringExtra("consumerNo");

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String restoredText = prefs.getString("empCode", null);
        if (restoredText != null) {
            employeeCode = restoredText;
        }


        searchConsumer = findViewById(R.id.searchConsumer);
        consumerDetailCardView = findViewById(R.id.consumerDetailCardView);
        consumerDetailLinearLayout = findViewById(R.id.consumerDetailLinearLayout);

        isUpDateMobileCheckBox = findViewById(R.id.isUpDateMobileCheckBox);
        isSealBrokenCheckBox = findViewById(R.id.isSealBrokenCheckBox);
        isMeterTemperedBrokenCheckBox = findViewById(R.id.isMeterTemperedBrokenCheckBox);
        requiredMeterShiftingCheckBox = findViewById(R.id.requiredMeterShiftingCheckBox);
        violationOfTariffCheckBox = findViewById(R.id.violationOfTariffCheckBox);
        underConstructionCheckBox = findViewById(R.id.underConstructionCheckBox);
        leakageInMeterFittingCheckBox = findViewById(R.id.leakageInMeterFittingCheckBox);
        ballValveReplacementCheckBox = findViewById(R.id.ballValveReplacementCheckBox);
        overflowOfWaterCheckBox = findViewById(R.id.overflowOfWaterCheckBox);
        consumerAbusedManhandleCheckBox = findViewById(R.id.consumerAbusedManhandleCheckBox);
        tv_latitude = findViewById(R.id.tv_latitude);
        tv_longitude = findViewById(R.id.tv_longitude);
        billMonthTextView = findViewById(R.id.billMonthTextView);
        pastReadingTextView = findViewById(R.id.pastReadingTextView);
        pastReadingTextView.append(": " + pastReading);

        //Image preview
        fl_preview_image = findViewById(R.id.fl_preview_image);
        image_preview = findViewById(R.id.image_preview);
        image_preview_close = findViewById(R.id.image_preview_close);
        image_preview_close.setOnClickListener(this);


        illegalConnectionCheckBox = findViewById(R.id.illegalConnectionCheckBox);
        meterByPassCheckBox = findViewById(R.id.meterByPassCheckBox);

        wardNameTextView = findViewById(R.id.wardNameTextView);
        propertyAssessmentNumberTextView = findViewById(R.id.propertyAssessmentNumberTextView);
        gisbidTextView = findViewById(R.id.gisbidTextView);
        tvUploadImageViewCureentTimeStamp = findViewById(R.id.tvUploadImageViewCureentTimeStamp);
        tvUploadImageViewOptionalCureentTimeStamp = findViewById(R.id.tvUploadImageViewOptionalCureentTimeStamp);
        tv_property = findViewById(R.id.tv_property);
        tv_property.setSelected(true);

        consumerNoInputLayout = findViewById(R.id.consumerNoInputLayout);
        meterReadingMandatoryInputLayout = findViewById(R.id.meterReadingMandatoryInputLayout);
        readerRemarkInputLayout = findViewById(R.id.readerRemarkInputLayout);
        meterLocationInputLayout = findViewById(R.id.meterLocationInputLayout);
        newMeterNumberInputLayout = findViewById(R.id.newMeterNumberInputLayout);

        upMobileNoEditText = findViewById(R.id.updateMobileNoTextInputEditText);
        consumerNoEditText = findViewById(R.id.consumerNoEditText);
        meterReadingMandatoryEditText = findViewById(R.id.meterReadingMandatoryEditText);
        meterReadingMandatoryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmp = s.toString().trim();
                if(tmp.length()==1 && tmp.equals("0"))
                    s.clear();
            }
        });
        readerRemarkEditText = findViewById(R.id.readerRemarkEditText);
        newMeterNumberMandatoryEditText = findViewById(R.id.newMeterNumberMandatoryEditText);
        newMeterNumberMandatoryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmp = s.toString().trim();
                if(tmp.length()==1 && tmp.equals("0"))
                    s.clear();
            }
        });

//        localityEditText = findViewById(R.id.localityEditText);
//        nearByEditText = findViewById(R.id.nearByEditText);

        consumerNoTextView = findViewById(R.id.consumerNoTextView);
        consumerNameTextView = findViewById(R.id.consumerNameTextView);
        consumerMobileNoTextView = findViewById(R.id.consumerMobileNoTextView);
        consumerAddressTextView = findViewById(R.id.consumerAddressTextView);
        buildingIdTextView = findViewById(R.id.buildingIdTextView);
        tariffTextView = findViewById(R.id.tariffTextView);
        meterNoTextView = findViewById(R.id.meterNoTextView);
        readingDateTextView = findViewById(R.id.readingDateTextView);
        meterConsumptionTextView = findViewById(R.id.meterConsumptionTextView);
        pastMeterConsumptionTextView = findViewById(R.id.pastMeterConsumptionTextView);
        pastMeterReadingTextView = findViewById(R.id.pastMeterReadingTextView);
        pastMeterReadingDateTextView = findViewById(R.id.pastMeterReadingDateTextView);
        sequencIdTextView = findViewById(R.id.sequencIdTextView);
        meterLocationInputLayout = findViewById(R.id.meterLocationInputLayout);
        landMarkInputLayout = findViewById(R.id.landMarkInputLayout);

        meterLocationEditText = findViewById(R.id.meterLocationEditText);
        landMarkEditText = findViewById(R.id.landMarkEditText);
        nearByLandMarkEditText = findViewById(R.id.nearByLandMarkEditText);

        meterStatusSpinner = findViewById(R.id.meterStatusSpinner);
        readerObservationSpinner = findViewById(R.id.readerObservationSpinner);

        submitButton = findViewById(R.id.submitButton);

        upMobileNoInputLayout = findViewById(R.id.upMobileNoInputLayout);
        arrowImageView = findViewById(R.id.arrowImageView);

        cameraCardView = findViewById(R.id.cameraCardView);

        cameraImageView = findViewById(R.id.cameraImageView);
        cameraImageView.setOnLongClickListener(this);
        cameraImageView.setOnClickListener(this);
        cameraLinearLayout = findViewById(R.id.cameraLinearLayout);

        cameraOptionalCardView = findViewById(R.id.cameraOptionalCardView);
        cameraOptionalImageView = findViewById(R.id.cameraOptionalImageView);
        cameraOptionalImageView.setOnLongClickListener(this);
        cameraOptionalImageView.setOnClickListener(this);

        cameraOptionalLinearLayout = findViewById(R.id.cameraOptionalLinearLayout);
        ll_consumer_activity = findViewById(R.id.ll_consumer_activity);


        locationTrack = new LocationTrack(MeterReadingActivity.this);


        RealmResults<MeterStatusModel> meterStatusModels = realmOperations.fetchMeterStatusNameID();
        for (MeterStatusModel model : meterStatusModels) {
            msList.add(model.getMSM_METERSTATUS_NAME());
            msValueList.add(String.valueOf(model.getMSM_METERSTATUS_ID()));
        }

        meterStatusData.add(getResources().getString(R.string.select_meter_status));
        meterStatusData.addAll(msList);


        meterStatusAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, meterStatusData);
        meterStatusAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        meterStatusSpinner.setAdapter(meterStatusAdapter);


        fetchDetails();

        consumerDetailCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (consumerDetailLinearLayout.getVisibility() == View.GONE) {
                    expand(consumerDetailLinearLayout);
                    arrowImageView.animate().rotation(180).start();
                } else {
                    collapse(consumerDetailLinearLayout);
                    arrowImageView.animate().rotation(0).start();
                }
            }
        });

        isUpDateMobileCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    upMobileNoInputLayout.setVisibility(View.VISIBLE);
                    strIsMobileNoUpdated = 1;
                    upMobileNoEditText.setText(strMobileNo);
                } else {
                    upMobileNoInputLayout.setVisibility(View.GONE);
                    strIsMobileNoUpdated = 0;
                }
            }
        });

        cameraCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MeterReadingActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                } else if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MeterReadingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

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

        cameraOptionalCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MeterReadingActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                } else if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MeterReadingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                } else {

                    if (isTimeAutomatic(mCon)) {
                        optionalCameraIntent();
                    } else {
                        startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);

                    }
                    // optionalCameraIntent();

                }
            }
        });

        meterStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //strMeterStatus = meterStatusData.get(1);

                statusName = meterStatusSpinner.getSelectedItem().toString();
                newMeterNumberInputLayout.setVisibility(View.GONE);

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
                    Toast.makeText(getApplicationContext(), "Problem is: " + ex.getMessage(), Toast.LENGTH_LONG).show();//message Last month
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
                    newMeterNumberInputLayout.setVisibility(View.GONE);

                    // meterObservationId = Integer.parseInt(moValueList.get(readerObservationSpinner.getSelectedItemPosition()-1).toString());

                    meterObservationModel = realmOperations.fetchMeterObservationByIdMeterReading(nameObservationStr);
                    Log.d("meterObservationModel", "" + meterObservationModel);
                    meterObservationId = Integer.parseInt(meterObservationModel.getDFM_CODE());

                    if (meterObservationModel.getMSNM_READING_MANDATORY().equalsIgnoreCase("0")) {
                        isReadingMandatory = false;
                        // newMeterNumberMandatoryEditText.requestFocus();
                        //meterReadingMandatoryEditText.setText(strPastMeterReading);
                        meterReadingMandatoryInputLayout.setError(null);
                        meterReadingMandatoryEditText.setError(null);
                        //meterReadingMandatoryEditText.setEnabled(false);
                    } else {
                        isReadingMandatory = true;
                        meterReadingMandatoryEditText.setText("");
                        meterReadingMandatoryEditText.setEnabled(true);
                        meterReadingMandatoryEditText.setFocusable(true);

                    }

                    if (meterObservationModel.getMSNM_PHOTO_REQ().equalsIgnoreCase("0")) {
                        isPhotoMandatory = false;
                        cameraCardView.setCardBackgroundColor(getResources().getColor(R.color.divider));
                    } else {
                        isPhotoMandatory = true;
                        cameraCardView.setCardBackgroundColor(getResources().getColor(R.color.red_500));
                    }
                    if (thumbnail != null) {
                        cameraCardView.setCardBackgroundColor(getResources().getColor(R.color.divider));
                    }

//                    if (meterObservationModel.getMSNM_READING_MANDATORY().equalsIgnoreCase("1") && meterObservationModel.getMSNM_PHOTO_REQ().equalsIgnoreCase("1")) {
//                        meterReadingMandatoryEditText.setText(null);
//                        meterReadingMandatoryEditText.setEnabled(true);
//                        meterReadingMandatoryEditText.setFocusable(true);
//                    } else {
//                        //meterReadingMandatoryEditText.setText(strPastMeterReading);
//                        meterReadingMandatoryInputLayout.setError(null);
//                        //meterReadingMandatoryEditText.setEnabled(false);
//                    }

                    if (meterObservationId == 14 || meterObservationId == 18) {
                        newMeterNumberInputLayout.setVisibility(View.VISIBLE);
                        newMeterNumberMandatoryEditText.requestFocus();
                        newMeterNumberMandatoryEditText.setEnabled(true);
                    } else {

                        newMeterNumberInputLayout.setVisibility(View.GONE);
                        newMeterNumberMandatoryEditText.getText().clear();
                        //    newMeterNumberMandatoryEditText.setFocusable(false);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       /* meterReadingMandatoryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (spinMeterStatus.equals(getResources().getString(R.string.select_meter_status)) || spinMeterObservation.equals(getResources().getString(R.string.select_meter_observation))) {
                        meterConsumptionTextView.setText(null);
                    } else if (!s.toString().isEmpty()) {


                        String data = meterReadingMandatoryEditText.getText().toString().trim();


                        int valueOfEdit = Integer.parseInt(data);
                        int pastReading = Integer.parseInt(strPastMeterReading);

//                    if (meterStatusName.split("-")[0].trim().equals("0")) {
//                        formula = (int) (Math.pow(10, meterDigit) - pastReading + valueOfEdit);
//                    } else {
//                        formula = (int) valueOfEdit - pastReading;
//                    }
                        if (meterStatusId == 0) {
                            formula = (int) (Math.pow(10, meterDigit) - pastReading + valueOfEdit);
                        } else {
                            formula = (int) valueOfEdit - pastReading;
                        }
                        formulaValue = String.valueOf(formula);
                        currentConsumption = Integer.parseInt(formulaValue);

                        meterConsumptionTextView.setText(formulaValue);

                    } else {
                        meterConsumptionTextView.setText("");
                    }


                } catch (Exception e) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        submitButton.setOnClickListener(this);

            //submitButto
       /* submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
       /* consumersModels = realmOperations.fetchConsumers(empCode);
        if (consumersModels.size() > 0 && consumersModels != null) {
            consumersModelsList = realmOperations.fetchConsumerModelByReadingStatus(0, RDRID);
        }*/

            // setSettingsAutomaticDateTimeIfNeeded();

            simpleDateFormat =new

            SimpleDateFormat("dd-MM-yyyy HH:mm",Locale.US);

            format =simpleDateFormat.format(new

            Date());
        Log.d("MainActivity","Current Timestamp: "+format);


        readingDateTextView.setText(format);

        }

        private void setSpinnerValue () {
            meterStatusId = Integer.parseInt(msValueList.get(meterStatusSpinner.getSelectedItemPosition() - 1).toString());

            meterObservationModels = realmOperations.fetchMeterObservationNameID(String.valueOf(meterStatusId));
            moList.clear();

            for (MeterObservationModel model : meterObservationModels) {
                moList.add(model.getDFM_DEFNAME());
                moValueList.add(String.valueOf(model.getDFM_CODE()));
            }
            newMeterNumberMandatoryEditText.getText().clear();

            if (meterStatusId == 1 || meterStatusId == 0 || meterStatusId == 4) {
                meterReadingMandatoryEditText.setText(null);
                newMeterNumberMandatoryEditText.setEnabled(true);
                meterReadingMandatoryEditText.setEnabled(true);

            } else {

           meterReadingMandatoryEditText.setText(strPastMeterReading);
            meterReadingMandatoryInputLayout.setError(null);
            meterReadingMandatoryEditText.setEnabled(false);
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

        @RequiresPermission("android.permission.WRITE_SETTINGS")
        public void setSettingsAutomaticDateTimeIfNeeded () {

            startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);


        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        private void checkTime (String startTime, String endTime, String checkTime){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startLocalTime = LocalTime.parse(startTime, formatter);
            LocalTime endLocalTime = LocalTime.parse(endTime, formatter);
            LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);

            boolean isInBetween = false;
            if (endLocalTime.isAfter(startLocalTime)) {
                if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
                    isInBetween = true;
                }
            } else if (checkLocalTime.isAfter(startLocalTime) || checkLocalTime.isBefore(endLocalTime)) {
                isInBetween = true;
            }

            if (isInBetween) {
                submitData = true;
            }
        }


        private void timeoutAlertBox () {
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title(R.string.alert)
                    .titleColorRes(R.color.red_500)
                    .content(rtimem + " " + ALERTSTARTTIME + " " + "to" + " " + ALERTENDTIME)
                    .contentColor(this.getResources().getColor(R.color.colorPrimary))
                    .canceledOnTouchOutside(false)
                    .positiveText(R.string.ok)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            Intent intent = new Intent(MeterReadingActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }
                    }).show();


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
        } /*else {

            timeoutAlertBox();
        }*/

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


   /* private void searchData() {

        searchDataText = consumerNoEditText.getText().toString().trim();
        //  strConsumerNo = consumerNoEditText.getText().toString().trim();
        //Toast.makeText(mCon, "check : " + consumerNo, Toast.LENGTH_SHORT).show();

        String params[] = new String[10];

        params[0] = allocatedReadingModel.getZone();
        params[1] = allocatedReadingModel.getWard();
        params[2] = allocatedReadingModel.getMRC();
        params[3] = allocatedReadingModel.getLOT();
        params[4] = "C";
        params[5] = searchDataText;
        params[6] = imei;
        params[7] = mac;
        params[8] = "D";
        params[9] = allocatedReadingModel.getBillMonth();

        if (connection.isConnectingToInternet()) {
            DownloadConsumerData downloadConsumerData = new DownloadConsumerData();
            downloadConsumerData.execute(params);

        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }*/

/*
    protected void bindSpinner(String strQuery, Spinner spnName, List valueList) {
        Cursor cursor = ElintMcollection.rawQuery(strQuery, null);

        if (cursor.getCount() <= 0) {
            Toast.makeText(getApplicationContext(),
                    "Receipt Data Not Available!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            List<String> list = new ArrayList<String>();
            cursor.moveToFirst();
            for (int i = 0; i <= cursor.getCount() - 1; i++) {
                list.add(cursor.getString(1));
                valueList.add(cursor.getString(0));
                cursor.moveToNext();
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list);
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spnName.setAdapter(dataAdapter);

        }
    }*/


        @Override
        public void onClick (View v){
            switch (v.getId()) {

                case R.id.submitButton:

                    if (validation()) {
                        //change here
                        if (isPhotoValid && isReadingValid) {
                            if (!TextUtils.isEmpty(strMeterReading)) {
                                double pastRead = Double.parseDouble(pastReading);
                                double currMeterRead = Double.parseDouble(strMeterReading);

                                if (currMeterRead < pastRead) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCon);
                                    alertDialog.setCancelable(false);
                                    alertDialog.setTitle(getResources().getString(R.string.negative_meter_reading));
                                    alertDialog.setMessage(getResources().getString(R.string.negative_meter_reading_desc));
                                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            submtMeterReadingData();
                                        }
                                    });
                                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                }
                                else
                                {
                                    submtMeterReadingData();
                                }
                            } else {
                                submtMeterReadingData();
                            }
                        } else {
                            Toast.makeText(mCon, "Please enter the required details", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;

                case R.id.iv_right_swipe:
                    //      swipeRight();
//                swipeR();
                    break;

                case R.id.iv_left_swipe:
                    //   swipeLeft();
                    break;

                case R.id.cameraImageView:

                    if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MeterReadingActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                    } else if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MeterReadingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                    } else {

                        cameraIntent();
                        // cameraIntent();
                    }
                    break;
                case R.id.cameraOptionalImageView:

                    if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MeterReadingActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                    } else if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MeterReadingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                    } else {

                        optionalCameraIntent();
                        // cameraIntent();
                    }
                    break;

                case R.id.image_preview_close:
                    fl_preview_image.setVisibility(View.GONE);
                    ll_consumer_activity.setVisibility(View.VISIBLE);

                    break;
                case R.id.iv_location:
                    startMapLocationActivity();

                    break;
                case R.id.iv_back:
                    startBackActivity();
                    break;
                default:
            }

        }

        private void submtMeterReadingData () {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US);
            String Date = sdf.format(Calendar.getInstance().getTime());
            strCurrentDateTime = Date;

            String params[] = new String[27];
            params[0] = consumerNoStr;
            params[1] = comseqStr;   //"COM_SEQ":
            params[2] = complaintNoStr;       //COM_PROCESSCODE
            params[3] = meterNoStr; // Meter Number
            params[4] = String.valueOf(meterStatusId); //METERSTATUSID
            params[5] = String.valueOf(meterObservationId); //METEROBSID
            params[6] = strCurrentDateTime; //READING_DATE
            params[7] = "";  // BillMonth
            params[8] = strMeterReading; //READING
            params[9] = empCode; // READER_ID
            params[10] = strLatitude; //LATITUDE
            params[11] = strLongitude;//LONGITUDE
            params[12] = strReadingRemark; //READER_REMARK
            params[13] = strNewMeterNumber;//NEW_METER
            params[14] = encodedBase64Image;//PHOTO_1
            params[15] = encodedBase64OptionalImage;//PHOTO_2
            params[16] = isSealStr; //Seal checkbox
            params[17] = isTempStr;//Tempered checkbox
            params[18] = isTariffStr; //Tariff checkbox
            params[19] = isShiftingStr;//Shifting checkbox
            params[20] = isUnderConstStr;//Under construction checkbox
            params[21] = isLeakageStr;//Leakage checkbox
            params[22] = isBallValStr;//Ball Valve checkbox
            params[23] = isOverflowStr; //Overflow checkbox
            params[24] = isAbusedStr;//Abused checkbox
            params[25] = isBypassStr;//Bypass checkbox
            params[26] = isIllegalStr;//Illegal checkbox

            if (connection.isConnectingToInternet()) {
                MeterReading meterReading = new MeterReading();
                meterReading.execute(params);
            } else {
                Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }

        private boolean validation () {
            statusName = meterStatusSpinner.getSelectedItem().toString();
            nameObservationStr = readerObservationSpinner.getSelectedItem().toString();

            strMeterReading = meterReadingMandatoryEditText.getText().toString().trim();
            strNewMeterNumber = newMeterNumberMandatoryEditText.getText().toString().trim();
            strReadingRemark = readerRemarkEditText.getText().toString().trim();

            if (statusName.equals(getResources().getString(R.string.select_meter_status))) {

                Toast.makeText(this, R.string.select_meter_status, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (nameObservationStr.equals(getResources().getString(R.string.select_meter_observation))) {

                Toast.makeText(this, R.string.select_meter_observation, Toast.LENGTH_SHORT).show();
                return false;
            }

//        if (encodedBase64Image.equalsIgnoreCase("")) {
//            Toast.makeText(mCon, "Please capture meter Photo", Toast.LENGTH_SHORT).show();
//            return false;
//        }

            if (newMeterNumberInputLayout.getVisibility() == View.VISIBLE) {
                if (strNewMeterNumber.equalsIgnoreCase("")) {
                    Toast.makeText(this, R.string.please_enter_new_meter_number, Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            if (isReadingMandatory) {

                if (TextUtils.isEmpty(strMeterReading)) {
                    meterReadingMandatoryEditText.setError(getResources().getString(R.string.Please_enter_meter_reading));
                    meterReadingMandatoryInputLayout.setError(getResources().getString(R.string.Please_enter_meter_reading));
                    Toast.makeText(mCon, R.string.Please_enter_meter_reading, Toast.LENGTH_SHORT).show();

                    // MessageWindow.messageWindow(mCon, getResources().getString(R.string.Please_enter_meter_reading), "Alert");
                } else {
                    isReadingValid = true;
                    meterReadingMandatoryInputLayout.setError(null);
                    meterReadingMandatoryEditText.setError(null);
                }
            } else {
                isReadingValid = true;
            }
//        if (strMeterReading.equalsIgnoreCase("")) {
//
//            Toast.makeText(this, R.string.please_enter_meter_reading, Toast.LENGTH_SHORT).show();
//            return false;
//        }
            if (strReadingRemark.equalsIgnoreCase("")) {

                Toast.makeText(this, R.string.please_enter_remark, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (isPhotoMandatory) {
                if (TextUtils.isEmpty(encodedBase64Image)) {
                    Toast.makeText(mCon, R.string.meter_photo_mandatory, Toast.LENGTH_SHORT).show();
                    // MessageWindow.messageWindow(mCon, getResources().getString(R.string.meter_photo_mandatory), "Alert");
                    cameraCardView.setCardBackgroundColor(getResources().getColor(R.color.red_500));
                } else {
                    cameraCardView.setCardBackgroundColor(getResources().getColor(R.color.divider));
                    isPhotoValid = true;
                }
            } else {
                isPhotoValid = true;
            }



            if (isSealBrokenCheckBox.isChecked()) {
                isSealStr = "1";
            }
            if (isMeterTemperedBrokenCheckBox.isChecked()) {
                isTempStr = "1";
            }
            if (violationOfTariffCheckBox.isChecked()) {
                isTariffStr = "1";
            }
            if (requiredMeterShiftingCheckBox.isChecked()) {
                isShiftingStr = "1";
            }
            if (underConstructionCheckBox.isChecked()) {
                isUnderConstStr = "1";
            }
            if (leakageInMeterFittingCheckBox.isChecked()) {
                isLeakageStr = "1";
            }
            if (ballValveReplacementCheckBox.isChecked()) {
                isBallValStr = "1";
            }
            if (overflowOfWaterCheckBox.isChecked()) {
                isOverflowStr = "1";
            }
            if (consumerAbusedManhandleCheckBox.isChecked()) {
                isAbusedStr = "1";
            }
            if (meterByPassCheckBox.isChecked()) {
                isBypassStr = "1";
            }
            if (illegalConnectionCheckBox.isChecked()) {
                isIllegalStr = "1";
            }

            return true;

        }

        @SuppressLint("StaticFieldLeak")
        private class MeterReading extends AsyncTask<String, Void, Void> {
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
                    String paraName[] = new String[27];
                    paraName[0] = "SERVICE_NO";
                    paraName[1] = "COMSEQNO";
                    paraName[2] = "COMPNO";
                    paraName[3] = "METER_NO";
                    paraName[4] = "METERSTATUSID";
                    paraName[5] = "METEROBSID";
                    paraName[6] = "READING_DATE";
                    paraName[7] = "BILLMONTH";
                    paraName[8] = "READING";
                    paraName[9] = "READER_ID";
                    paraName[10] = "LATITUDE";
                    paraName[11] = "LONGITUDE";
                    paraName[12] = "READER_REMARK";
                    paraName[13] = "NEW_METER";
                    paraName[14] = "PHOTO_1";
                    paraName[15] = "PHOTO_2";
                    paraName[16] = "isSeal";
                    paraName[17] = "isTemp";
                    paraName[18] = "isTariffV";
                    paraName[19] = "isShiting";
                    paraName[20] = "isUnderConst";
                    paraName[21] = "isLeakage";
                    paraName[22] = "isBallVal";
                    paraName[23] = "isOverflow";
                    paraName[24] = "isAbused";
                    paraName[25] = "isBypass";
                    paraName[26] = "isIllegal";

                    jsonMeterReadingResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.ComplaintReading, params, paraName);
                    Log.d("MeterReading", jsonMeterReadingResponse.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    LoginModel[] enums = gson.fromJson(jsonMeterReadingResponse, LoginModel[].class);
                    if (enums.length > 0) {

                        if (enums[0].getQueryStatus().equalsIgnoreCase("Success")) {
                            progress.dismiss();
                            MessageWindow.msgWindowReader(mCon, getResources().getString(R.string.reading_submit_successfully), enums[0].getQueryStatus(), MainActivity.class);

                            //   MessageWindow.msgWindow(MeterReadingActivity.this, enums[0].getQueryStatus());

                        } else {
                            MessageWindow.errorWindow(MeterReadingActivity.this, enums[0].getQueryStatus());

                            progress.dismiss();

                        }
                    }


                } catch (Exception e) {
                    Log.d("check", e.getMessage());
                    String error = e.toString();
                    ErrorClass.errorData(mCon, "Meter Reading", "Submit", error);
                }
                progress.dismiss();
            }
        }


        private void submitData () {

         /*<SERVICE_NO>string</SERVICE_NO>
      <COMSEQNO>string</COMSEQNO>
      <>string</COMPNO>
      <>string</METER_NO>
      <>string</METERSTATUSID>
      <>string</METEROBSID>
      <>string</READING_DATE>
      <>string</BILLMONTH>
      <>string</READING>
      <>string</READER_ID>
      <LATITUDE>string</LATITUDE>
      <>string</LONGITUDE>
      <>string</READER_REMARK>
      <>string</NEW_METER>
      <>string</PHOTO_1>
      <PHOTO_2>string</PHOTO_2>*/
            try {
                strConsumerNo = consumerNoTextView.getText().toString().trim();
                // strUpdatedMobileNo = upMobileNoEditText.getText().toString().trim();
                //consumerNoTextView
                strMeterReadingMandatory = meterReadingMandatoryEditText.getText().toString().trim();
                //  meterReadingInt = Integer.parseInt(strMeterReadingMandatory);
                // strMeterReading = meterReadingEditText.getText().toString().trim();
                strReadingRemark = readerRemarkEditText.getText().toString().trim();

                // strLocality = localityEditText.getText().toString().trim();
                ml = meterLocationEditText.getText().toString();
                ca = landMarkEditText.getText().toString();


                strNewMeterNumber = newMeterNumberMandatoryEditText.getText().toString().trim();
                strMobileNo = upMobileNoEditText.getText().toString().trim();
                strLocality = meterLocationEditText.getText().toString().trim();
                int a = strLocality.length();


                if (TextUtils.isEmpty(strMobileNo)) {
                    if (strMobileNo.length() != 10) {
                        upMobileNoInputLayout.setError(getResources().getString(R.string.please_enter_valid_mobile_number));
                        //  MessageWindow.messageWindow(mCon, getResources().getString(R.string.please_enter_valid_mobile_number), "Alert");

                        Toast.makeText(mCon, getString(R.string.please_enter_valid_mobile_number), Toast.LENGTH_SHORT).show();
                    } else {

                        upMobileNoInputLayout.setError(null);
                    }


                } else upMobileNoInputLayout.setError(null);


                if (TextUtils.isEmpty(strLocality)) {

                    meterLocationInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                    // MessageWindow.messageWindow(mCon, getResources().getString(R.string.Please_enter_meter_location), "Alert");

                    Toast.makeText(mCon, getString(R.string.Please_enter_meter_location), Toast.LENGTH_SHORT).show();
                } else {

                    meterLocationInputLayout.setError(null);
                }

                // strNewMeterNumber = Integer.valueOf(newMeterNumberMandatoryEditText.getText().toString());

                if (spinMeterStatus.equals(getResources().getString(R.string.select_meter_status))) {
                    Toast.makeText(mCon, R.string.meter_status_mandatory, Toast.LENGTH_LONG).show();

                    //  MessageWindow.messageWindow(mCon, getResources().getString(R.string.meter_status_mandatory), "Alert");


                } else if (spinMeterObservation.equals(getResources().getString(R.string.select_meter_observation))) {
                    Toast.makeText(mCon, R.string.meter_observation_mandatory, Toast.LENGTH_LONG).show();
                    // MessageWindow.messageWindow(mCon, getResources().getString(R.string.meter_observation_mandatory), "Alert");

                } else {
                    validate();
                }
                // validate();
            } catch (Exception e) {

                String error = e.toString();
                Number errorIdNum = realmOperations.getErrorIDCount();

                if (errorIdNum == null) {
                    errorNextId = 1;
                } else {
                    errorNextId = errorIdNum.intValue() + 1;
                }
        /*    ErrorLogModel errorLogModel = new ErrorLogModel(errorNextId, empCode, imei, Build.MODEL, Build.MANUFACTURER, Build.VERSION.SDK_INT, AppVersion, "Consumer Reading Activity Demo", "Submit Button", error, 0, timeStamp, "1");
            realmOperations.insertErrorLogData(errorLogModel);

            Toast.makeText(mCon, getResources().getString(R.string.unable_capture_your_current_location), Toast.LENGTH_SHORT).show();
*/
            }


        }

        private void startBackActivity () {

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

        private void startMapLocationActivity () {
            Intent i = new Intent(mCon, SingleMapLocationActivity.class);
            i.putExtra("consumerId", consumerID);

            i.putExtra("consumerNo", strConsumerNo);
            i.putExtra("consumername", strName);
            i.putExtra("positionId", 0);
            i.putExtra("readingFlag", 0);
            i.putExtra("searchCheck", searchCheck);
            i.putExtra("Lat", strLat);
            i.putExtra("Lon", strLon);
            i.putExtra("Address", strAddress);

            i.putExtra("Tag", Tag);
            startActivity(i);
            finish();

            //   overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//
        }

   /* public void addList(List<ConsumersModel> consumersModels) {
        if (data != null) {
            clear();
            data.addAll(consumersModels);
        }
    }


    private void swipeLeft() {

        consumersModels = realmOperations.fetchConsumers(empCode);
        if (consumersModels.size() > 0 && consumersModels != null) {
            consumersModelsList = realmOperations.fetchConsumerModelByReadingStatus(0, empCode);
        }

        Number currentIdNum = realmOperations.getDataReadingIDCount();
        if (rereadingStatus == 1) {
            finish();
        } else if (rereadingStatus == 0) {
            try {


                if (consumerID != 0) {
                    Tag = "";
                    int nextId = positionId - 1;

                    //ConsumersModel model = realmOperations.fetchConsumerById(consumersModelsList.get(nextId).getId());
                    //nitin testing logic
                    ConsumersModel model = consumersModelsList.get(nextId);

                    int position = getItemPosition(model.getId());

                    if (employeeLoginModel.getIsValid() == 1) {
                        Intent i = new Intent(mCon, ConsumerReadingActivityDemo.class);
                        i.putExtra("consumerId", model.getId());
                        i.putExtra("positionId", nextId);
                        i.putExtra("readingFlag", 0);
                        i.putExtra("Tag", Tag);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_fromleft, R.anim.slide_right);
                        finish();
                    } else {
                        Toast.makeText(mCon, R.string.user_blocked, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            } catch (Exception ex) {

            }
        }


    }*/

 /*   public int getItemPosition(long id) {
        for (int position = 0; position < consumersModelsList.size(); position++)
            if (consumersModelsList.get(position).getId() == id)
                return position;
        return 0;
    }
*/
    /*private void swipeRight() {

        consumersModels = realmOperations.fetchConsumers(empCode);
        if (consumersModels.size() > 0 && consumersModels != null) {
            consumersModelsList = realmOperations.fetchConsumerModelByReadingStatus(0, empCode);
        }

        Number currentIdNum = realmOperations.getDataReadingIDCount();
        if (rereadingStatus == 1) {
            finish();
        } else if (rereadingStatus == 0) {
            try {


                if (consumerID != 0) {
                    Tag = "";
                    int nextId = positionId + 1;
                    // ConsumersModel model = realmOperations.fetchConsumerById(consumersModelsList.get(nextId).getId());
                    //nitin testing logic
                    ConsumersModel model = consumersModelsList.get(nextId);
                    int position = getItemPosition(model.getId());
                    if (employeeLoginModel.getIsValid() == 1) {
                        Intent i = new Intent(mCon, ConsumerReadingActivityDemo.class);
                        i.putExtra("consumerId", model.getId());
                        i.putExtra("positionId", nextId);
                        i.putExtra("readingFlag", 0);
                        i.putExtra("Tag", Tag);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        finish();
                        // Toast.makeText(mCon, "clickright" + next, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mCon, R.string.user_blocked, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            } catch (Exception ex) {

            }
        }
    }*/

        @Override
        public boolean onLongClick (View v){
            if (v.getId() == R.id.cameraImageView) {
                longclick();
            }
            if (v.getId() == R.id.cameraOptionalImageView) {
                longOptionalclick();
            }
            return true;
        }

        private void longclick () {
            fl_preview_image.setVisibility(View.VISIBLE);


            image_preview.setImageBitmap(rotatedBitmap);
            //image_preview.setImageResource(rotatedBitmap);


        }

        private void longOptionalclick () {
            fl_preview_image.setVisibility(View.VISIBLE);
            image_preview.setImageBitmap(optionalBitmap);

        }


    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }*/

        private class DownloadConsumerData extends AsyncTask<String, Void, Void> {

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
                    String paraName[] = new String[10];

                    paraName[0] = "ZONE";
                    paraName[1] = "WARD";
                    paraName[2] = "MRC";
                    paraName[3] = "LOT";
                    paraName[4] = "TAG";
                    paraName[5] = "CONSUMERNO";
                    paraName[6] = "IMEI";
                    paraName[7] = "MAC";
                    paraName[8] = "DUTAG";
                    paraName[9] = "BILLMONTH";

                    jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "DownloadConsumers", params, paraName);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onPostExecute(Void aVoid) {
          /*  try {
                DownloadResponseModel enums = gson.fromJson(jsonResponse, DownloadResponseModel.class);
                for (DownloadConsumerModel model : enums.getDownloadConsumerModel()) {

                    if (!model.getNAME().equalsIgnoreCase("null")) {
                        strConsumerNo = model.getSRVNO();

                        consumersModel = realmOperations.fetchConsumerByConsumerNumber(strConsumerNo);

                        if (consumersModel == null) {
                            strConsumerNo = model.getSRVNO();
                            strName = model.getNAME();
                            strMobileNo = model.getMOBILENO();
                            strAddress = model.getADDRESS();
                            strBuldingId = model.getGIS_BUILDING_ID();
                            strTariff = model.getTARIFF();
                            strMeterNo = model.getMETER_NO();
                            meterDigit = Integer.parseInt(model.getMETER_DIGIT());
                            meterReadingMandatoryEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(meterDigit)});

                            strSequenceId = model.getSEQUENCEID().trim() + " " + model.getREGNO().trim();
                            strWardname = model.getWardName();
                            strpropertyAssessmentNumber = model.getPANO();
                            // float pastReading = Float.parseFloat(model.getPAST_READING());

                            int pastReading = Integer.parseInt(model.getPAST_READING());
                            int pastConsumption = Integer.parseInt(model.getCONSUMPTION());
                            strPastMeterReadingDate = model.getPAST_READING_DATE();

                            seqNumber = Integer.parseInt(model.getSEQUENCEID());
                            pastMeterConsumptionInt = pastConsumption;
                            strPastMeterReading = String.valueOf(pastReading);
                            sequencIdTextView.setText(strSequenceId);
                            strBuldingId = model.getGIS_BUILDING_ID();
                            strPastObservation = model.getOBSRV();
                            strMeterLocation = model.getMETER_LOCATION();
                            meterLocationEditText.setText(strMeterLocation);

                            strLat = model.getLAT();

                            strLon = model.getLON();

                            if (strLat.equalsIgnoreCase("NULL") && strLon.equalsIgnoreCase("NULL")) {
                                iv_location.setVisibility(View.GONE);

                            } else {
                                iv_location.setVisibility(View.VISIBLE);

                            }


                            if (strWardname.equalsIgnoreCase("NULL") || strWardname.equalsIgnoreCase("NA")) {
                                wardNameTextView.setText("-");
                            } else {
                                wardNameTextView.setText(strWardname);

                            }
                            if (strBuldingId.equalsIgnoreCase("NULL") || strBuldingId.equalsIgnoreCase("NA")) {
                                buildingIdTextView.setText("-");
                                gisbidTextView.setText("-");
                            } else {
                                buildingIdTextView.setText(strBuldingId);
                                gisbidTextView.setText(strBuldingId);

                            }
                            if (strpropertyAssessmentNumber.equalsIgnoreCase("NULL") || strpropertyAssessmentNumber.equalsIgnoreCase("NA")) {
                                propertyAssessmentNumberTextView.setText("-");
                            } else {
                                propertyAssessmentNumberTextView.setText(strpropertyAssessmentNumber);

                            }
                            if (strMeterLocation.equalsIgnoreCase("NULL") || strMeterLocation.equalsIgnoreCase("NA")) {
                                meterLocationEditText.setText("");
                            } else {
                                meterLocationEditText.setText(strMeterLocation);

                            }

                            consumerNoTextView.setText(strConsumerNo);
                            consumerNameTextView.setText(strName);

                            if (strMobileNo.equalsIgnoreCase("0")) {
                                upMobileNoEditText.setText("");
                                consumerMobileNoTextView.setText("-");


                            } else {
                                upMobileNoEditText.setText(strMobileNo);
                                consumerMobileNoTextView.setText(strMobileNo);

                            }


                            consumerAddressTextView.setText(strAddress);

                       *//* consumerNameTextView.setText(getResources().getString(R.string.name) + " " + strName);
                        consumerMobileNoTextView.setText(getResources().getString(R.string.mobile_no) + " " + strMobileNo);
                        consumerAddressTextView.setText(getResources().getString(R.string.consumerAddress) + " " + strAddress);*//*

                            if (strBuldingId.equalsIgnoreCase("NULL") || strBuldingId.equalsIgnoreCase("NA")) {
                                wardNameTextView.setText("-");
                            } else {
                                // buildingIdTextView.setText(getResources().getString(R.string.building_id) + " " + strBuldingId);
                                buildingIdTextView.setText(strBuldingId);
                            }
                            //  tariffTextView.setText(getResources().getString(R.string.tariff) + " " + strTariff);
                            tariffTextView.setText(strTariff);
                            meterNoTextView.setText(strMeterNo);
                            // readingDateTextView.setText(date);
                            //meterConsumptionTextView.setText("");
                            //pastMeterConsumptionTextView.setText("");

                     *//*   if (strPastMeterReading.equalsIgnoreCase("0")) {
                            pastMeterReadingTextView.setText("-");*//*
                            pastMeterReadingTextView.setText(strPastMeterReading);


*//*
                        } else {
                            pastMeterReadingTextView.setText(strPastMeterReading);
                        }*//*


                            pastMeterReadingDateTextView.setText(strPastMeterReadingDate);
                            searchCheck = 1;

                        //    Toast.makeText(mCon, R.string.consumer_searched_successfully, Toast.LENGTH_SHORT).show();
                        } *//*else {

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCon);
                            alertDialog.setCancelable(false);
                            alertDialog.setTitle(getResources().getString(R.string.consumer_search));
                            alertDialog.setMessage(getResources().getString(R.string.consumer_search_message));
                            alertDialog.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                        *//**//*Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);*//**//*

                                    dialog.cancel();
                                }
                            });

                            alertDialog.show();



                        }*//*
                    }else {

                        showAlertDilogBox();
                       *//* Toast.makeText(mCon, R.string.consumer_doesnot_exist, Toast.LENGTH_SHORT).show();
                        consumerNoEditText.setText(strConsumerNo);
                        fetchDetails();*//*
                    }
                }
                progress.dismiss();
            } catch (Exception e) {
                Log.d("Exception", "" + e);

            }*/
            }
        }

        private void showAlertDilogBox () {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCon);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.consumer_search));
            alertDialog.setMessage(getResources().getString(R.string.consumer_doesnot_exist));
            alertDialog.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                                       /* Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);*/

                    dialog.cancel();
                    consumerNoEditText.setText(strConsumerNo);
                    fetchDetails();

                }
            });
            alertDialog.show();
        }

        @Override
        protected void onPause () {
            super.onPause();
            realmOperations.close();
            //  ((App) this.getApplication()).startActivityTransitionTimer();
            //  getLocation();

        }

        @SuppressLint("SetTextI18n")
        public void fetchDetails () {

            try {

                consumerNoTextView.setText(consumerNoStr);
                consumerNameTextView.setText(consumerNameStr);
                consumerMobileNoTextView.setText(contactNoStr);
                consumerAddressTextView.setText(addressStr);
                tariffTextView.setText(tariffStr);

            } catch (Exception e) {
                Log.d("exeption", "" + e);
                Number errorIdNum = realmOperations.getErrorIDCount();

                if (errorIdNum == null) {
                    errorNextId = 1;
                } else {
                    errorNextId = errorIdNum.intValue() + 1;
                }
         /*   ErrorLogModel errorLogModel = new ErrorLogModel(errorNextId, empCode, imei, Build.MODEL, Build.MANUFACTURER, Build.VERSION.SDK_INT, AppVersion, "ConsumerReading Activity Demo", "fetchDetails", e.toString(), 0, timeStamp, "1");
            realmOperations.insertErrorLogData(errorLogModel);*/
            }

            getLocation();

        }

        //public void validate
        public boolean validate () {

            if (isSealBrokenCheckBox.isChecked()) {
                strIsSealBroken = 1;
            } else {
                strIsSealBroken = 0;
            }

            if (isMeterTemperedBrokenCheckBox.isChecked()) {
                strIsMeterTemperedBroken = 1;
            } else {
                strIsMeterTemperedBroken = 0;
            }

            if (requiredMeterShiftingCheckBox.isChecked()) {
                strIsRequiredMeterShifting = 1;
            } else {
                strIsRequiredMeterShifting = 0;
            }

            if (violationOfTariffCheckBox.isChecked()) {
                strViolationOfTariff = 1;
            } else {
                strViolationOfTariff = 0;
            }

            if (underConstructionCheckBox.isChecked()) {
                underConstruction = 1;
            } else {
                underConstruction = 0;
            }

            if (leakageInMeterFittingCheckBox.isChecked()) {
                leakageInMeterFitting = 1;
            } else {
                leakageInMeterFitting = 0;
            }

            if (ballValveReplacementCheckBox.isChecked()) {
                ballValveReplacement = 1;
            } else {
                ballValveReplacement = 0;
            }

            if (overflowOfWaterCheckBox.isChecked()) {
                overflowOfWater = 1;
            } else {
                overflowOfWater = 0;
            }

            if (consumerAbusedManhandleCheckBox.isChecked()) {
                consumerAbusedManhandle = 1;
            } else {
                consumerAbusedManhandle = 0;
            }

            if (illegalConnectionCheckBox.isChecked()) {
                illegalConection = 1;
            } else {
                illegalConection = 0;
            }

            if (meterByPassCheckBox.isChecked()) {
                meterByPass = 1;
            } else {
                meterByPass = 0;
            }

            //boolean isValidPhoto = false, isValidReading = false;
            if (isPhotoMandatory) {
                if (TextUtils.isEmpty(encodedBase64Image)) {
                    Toast.makeText(mCon, R.string.meter_photo_mandatory, Toast.LENGTH_SHORT).show();
                    // MessageWindow.messageWindow(mCon, getResources().getString(R.string.meter_photo_mandatory), "Alert");
                    cameraCardView.setCardBackgroundColor(getResources().getColor(R.color.red_500));
                } else {
                    cameraCardView.setCardBackgroundColor(getResources().getColor(R.color.divider));
                    isPhotoValid = true;
                }
            } else {
                isPhotoValid = true;
            }

            if (isReadingMandatory) {

                if (TextUtils.isEmpty(strMeterReadingMandatory)) {
                    meterReadingMandatoryEditText.setError(getResources().getString(R.string.Please_enter_meter_reading));
                    meterReadingMandatoryInputLayout.setError(getResources().getString(R.string.Please_enter_meter_reading));
                    Toast.makeText(mCon, R.string.Please_enter_meter_reading, Toast.LENGTH_SHORT).show();

                    // MessageWindow.messageWindow(mCon, getResources().getString(R.string.Please_enter_meter_reading), "Alert");
                } else {
                    isReadingValid = true;
                    meterReadingMandatoryInputLayout.setError(null);
                    meterReadingMandatoryEditText.setError(null);
                }
            } else {
                isReadingValid = true;
            }

            if (meterStatusId == 4) {
                if (newMeterNumberMandatoryEditText.length() == 0) {
                    newMeterNumberMandatoryEditText.setError(getResources().getString(R.string.please_enter_new_meter_number));
                    newMeterNumberInputLayout.setError(getResources().getString(R.string.please_enter_new_meter_number));
                    //  MessageWindow.messageWindow(mCon, getResources().getString(R.string.please_enter_new_meter_number), "Alert");

                    Toast.makeText(mCon, getString(R.string.please_enter_new_meter_number), Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    newMeterNumberMandatoryEditText.setError(null);
                    newMeterNumberInputLayout.setError(null);
                }
            }

            if (isReadingValid && isPhotoValid) {
                checkMeterValidation();
            }


            return false;
        }


        private void cameraIntent () {
      /*  fl_preview_image.setVisibility(View.GONE);
        ll_consumer_activity.setVisibility(View.VISIBLE);*/
            // updateLocationUI();
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
            destination = new File(insideBillMonthFolder, "111" + "_" + strMeterNo + "_" + System.currentTimeMillis() + ".jpg");
            photoURI = FileProvider.getUriForFile(this,
                    "elink.suezShimla.water.crm",
                    destination);
            // photoURI = FileProvider.getUriForFile(mCon, BuildConfig.APPLICATION_ID + ".provider", destination);


            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_CAMERA);
        }

        private void optionalCameraIntent () {
            // updateLocationUI();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //File dir = new File(Environment.getExternalStorageDirectory(), "Water Meter Reading");
            File dir = new File(mCon.getExternalFilesDir(null), "Water Meter Reading");
            if (!dir.exists()) {
                dir.mkdirs();
            }

   /*     File insideFolder = new File(dir, "Documents");
        if (!insideFolder.exists()) {
            insideFolder.mkdirs();
        }*/
            File insideBillMonthFolder = new File(dir, "Bill Month");
            if (!insideBillMonthFolder.exists()) {
                insideBillMonthFolder.mkdirs();
            }
            File insideBillMonthLotFolder = new File(insideBillMonthFolder, "" + billMonth);
            if (!insideBillMonthLotFolder.exists()) {
                insideBillMonthLotFolder.mkdirs();
            }
            File insideBillMonthOtherPhotoFolder = new File(insideBillMonthLotFolder, "Other Photo");
            if (!insideBillMonthOtherPhotoFolder.exists()) {
                insideBillMonthOtherPhotoFolder.mkdirs();
            }

            destinationOptional = new File(insideBillMonthOtherPhotoFolder, strConsumerNo + "_" + strMeterNo + "_" + System.currentTimeMillis() + ".jpg");
            //   Uri photoUri = FileProvider.getUriForFile(mCon, BuildConfig.APPLICATION_ID + ".provider", destinationOptional);
            photoURI = FileProvider.getUriForFile(this,
                    "elink.suezShimla.water.crm",
                    destinationOptional);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_CAMERA_OPTIONAL);
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CAMERA) {

                try {
                    rotation = getRotation(destination);


                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    thumbnail = BitmapFactory.decodeFile(destination.getAbsolutePath(), bmOptions);

                    cameraImageView.setImageURI(Uri.parse(destination.getAbsolutePath()));
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
                        cs.drawText(format + " (" + consumerNoStr + "-" + meterNoStr + ")", 10f, height + 10f, tPaint);
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

                            docName = strPath1.substring(strPath1.lastIndexOf('/') + 1);
                            docType = "image";

                            cameraImageView.setImageBitmap(rotatedBitmap);
                            cameraCardView.setCardBackgroundColor(getResources().getColor(R.color.divider));

                            cameraImageView.setVisibility(View.VISIBLE);
                            cameraLinearLayout.setVisibility(View.GONE);

                        }
                    } else {


                        //  thumbnail = GaussianBlur.with(this).maxSixe(100).render(R.drawable.ic_add_photo);
                        //  cameraImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_add_photo)));

                        if (rotatedBitmap == null) {
                            cameraImageView.setVisibility(View.VISIBLE);
                        } else {
                            cameraImageView.setImageBitmap(rotatedBitmap);
                            cameraImageView.setVisibility(View.VISIBLE);
                            cameraLinearLayout.setVisibility(View.GONE);
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
            } else if (requestCode == REQUEST_CAMERA_OPTIONAL) {
                try {
                    rotation = getRotation(destinationOptional);

                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    optionalThumbnail = BitmapFactory.decodeFile(destinationOptional.getAbsolutePath(), bmOptions);
                    cameraOptionalImageView.setImageURI(Uri.parse(destinationOptional.getAbsolutePath()));
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
                        cs.drawText(format + " (" + consumerNoStr + "-" + meterNoStr + ")", 10f, height + 10f, tPaint);

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

                            docNameOptional = strPath1.substring(strPath1.lastIndexOf('/') + 1);
                            docTypeOptional = "image";


               /*   Canvas cs = new Canvas(optionalBitmap);
                Paint tPaint = new Paint();
                tPaint.setTextSize(30);
                tPaint.setColor(Color.WHITE);
                tPaint.setStyle(Paint.Style.FILL);
                cs.drawBitmap(optionalThumbnail, 0f, 0f, null);
                float height = tPaint.measureText("yY");
                cs.drawText(format, 10f, height + 10f, tPaint);*/

                            cameraOptionalImageView.setImageBitmap(optionalBitmap);
                            cameraOptionalImageView.setVisibility(View.VISIBLE);
                            cameraOptionalLinearLayout.setVisibility(View.GONE);

                        }

                    } else {
                        if (optionalBitmap == null) {
                            cameraOptionalImageView.setVisibility(View.VISIBLE);
                        } else {
                            cameraOptionalImageView.setImageBitmap(optionalBitmap);
                            cameraOptionalImageView.setVisibility(View.VISIBLE);
                            cameraOptionalLinearLayout.setVisibility(View.GONE);
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
      /*  else if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                //    startLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                 //   checkLocationSettings();
                    break;
                default:
            }
        } else if (requestCode == REQUEST_PERMISSION_SETTING) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                   // detectLocation();
                    break;
                case Activity.RESULT_CANCELED:
                //    getLocation();
                    break;
                default:
                    break;
            }
        }*/
        }

        private Bitmap addText (Bitmap toEdit){

            Bitmap dest = toEdit.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(dest);

            Paint paint = new Paint();  //set the look
            paint.setAntiAlias(true);
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(2.0f, 1.0f, 1.0f, Color.BLACK);

            int pictureHeight = dest.getHeight();
            paint.setTextSize(pictureHeight * .04629f);

            canvas.drawText("Hello World", dest.getWidth() / 2, 100, paint);
            return dest;
        }

        @SuppressLint("SetTextI18n")
        private static int getRotation (File imageFile){
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


        private static Bitmap rotateImageIfRequired (Bitmap img, Uri selectedImage) throws
        IOException {

            ExifInterface ei = new ExifInterface(selectedImage.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
                default:
                    return img;
            }
        }


        public static Bitmap rotateImage (Bitmap source,float angle){
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        }
  /*  private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }*/

        private static void expand ( final View v){
            v.measure(AbsoluteLayout.LayoutParams.MATCH_PARENT,
                    AbsoluteLayout.LayoutParams.WRAP_CONTENT);
            final int targetHeight = v.getMeasuredHeight();

            // Older versions of android (pre API 21) cancel animations for
            // views with a height of 0.
            v.getLayoutParams().height = 1;
            v.setVisibility(View.VISIBLE);
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime,
                                                   Transformation t) {
                    v.getLayoutParams().height = interpolatedTime == 1 ? AbsoluteLayout.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }

        private static void collapse ( final View v){
            final int initialHeight = v.getMeasuredHeight();

            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime,
                                                   Transformation t) {
                    if (interpolatedTime == 1) {
                        v.setVisibility(View.GONE);
                    } else {
                        v.getLayoutParams().height = initialHeight - (int)
                                (initialHeight * interpolatedTime);
                        v.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            a.setDuration((int) (initialHeight /
                    v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }

        @Override
        public void onStop () {
            super.onStop();
            if (mGoogleApiClient != null) {
                mGoogleApiClient.disconnect();
            }
        }


        @Override
        public void onResume () {
            super.onResume();
      /*  realmOperations = new RealmOperations(mCon);

        App myApp = (App) this.getApplication();
        if (myApp.wasInBackground) {
            finish();
            startActivity(new Intent(mCon, SplashActivity.class));
        }
        getLocation();

        myApp.stopActivityTransitionTimer();*/


        }

        private void getLocation () {
            final boolean result = checkPermission(mCon);
            if (result) {
                buildGoogleApiClient();
                createLocationRequest();
                buildLocationSettingsRequest();
                checkLocationSettings();
            }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public boolean checkPermission ( final Context context){
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_LOCATION: {
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                            if (mGoogleApiClient == null) {
                                buildGoogleApiClient();
                            }
                        }

                    } else {
                        boolean showRationale = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            showRationale = shouldShowRequestPermissionRationale(permissions[0]);
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
                            //getLocation();
                        }


                        // checkLocationPermission();
                    }
                }
            }
        }

        protected synchronized void buildGoogleApiClient () {

            mGoogleApiClient = new GoogleApiClient.Builder(mCon)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }

        private void createLocationRequest () {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setInterval(10000); // interval 10 seconds
            mLocationRequest.setNumUpdates(1);  // setNumUpdates(1); stops location requests after receiving 1 location
            //   mLocationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
            mLocationRequest.setSmallestDisplacement(1f);


            // PRIORITY_HIGH_ACCURACY option uses your GPS
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        }

        private void buildLocationSettingsRequest () {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            mLocationSettingsRequest = builder.build();
        }

        private void checkLocationSettings () {
            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, mLocationSettingsRequest);
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

                                status.startResolutionForResult(MeterReadingActivity.this, REQUEST_CHECK_SETTINGS);
                                // finish();
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

        protected void startLocationUpdates () {
            detectLocation();
        }

        private void detectLocation () {

            if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(mCon);

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        mRequestingLocationUpdates = true;
                    }
                });
            }

        }

        @Override
        public void onConnected (Bundle bundle){

            if (mCurrentLocation == null) {

                if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    updateLocationUI();
                }
            }
        }

        private void updateLocationUI () {
            if (mCurrentLocation != null) {

                final boolean mock = isMockSettingsON(mCon, mCurrentLocation);
                // Log.d("check", "mock = " + mock);
                if (mock) {
                    Toast.makeText(mCon, R.string.turnOffMockLocation, Toast.LENGTH_SHORT).show();
                    //finish();
                } else {

               /* latitude = String.valueOf(mCurrentLocation.getLatitude());
                longitude = String.valueOf(mCurrentLocation.getLongitude());*/
                    latitude = mCurrentLocation.getLatitude();
                    longitude = mCurrentLocation.getLongitude();

                    if (locationTrack.canGetLocation()) {


                        longitude = locationTrack.getLongitude();
                        latitude = locationTrack.getLatitude();

                        // Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
                    } else {

                        locationTrack.showSettingsAlert();
                    }


                    strLatitude = String.valueOf(latitude);
                    strLongitude = String.valueOf(longitude);

                    tv_latitude.setText("");
                    tv_longitude.setText("");


                    tv_latitude.setText(strLatitude);
                    tv_longitude.setText(strLongitude);


                    Log.d("checklatlong", latitude + ", " + longitude);
                    //  Toast.makeText(mCon, String.valueOf(mCurrentLocation.getLatitude()) + String.valueOf(mCurrentLocation.getLongitude()), Toast.LENGTH_LONG).show();
                }
            }
        }

        public static boolean isMockSettingsON (Context context, Location location){

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                boolean mockSettingOff = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
                return !mockSettingOff;
            } else {
                return location.isFromMockProvider(); //should use location object to detect mock locaiton
            }
        }

        @Override
        public void onConnectionSuspended ( int i){
        }

        @Override
        public void onConnectionFailed (ConnectionResult connectionResult){
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
            if (connectionResult.hasResolution()) {
                try {
                    // Start an Activity that tries to resolve the error
                    connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
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
        public void onLocationChanged (Location location){


            int suitableMeter = 1; // adjust your need

            if (!location.hasAccuracy()) {
                return;
            }
            if (location.getAccuracy() > 5) {
                return;
            }
            if (location.hasAccuracy() && location.getAccuracy() <= suitableMeter) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            // 20.0 lat: 19.1027377 lon: 73.0060855
            // Toast.makeText(this, currentLatitude + R.string.works + currentLongitude + "", Toast.LENGTH_LONG).show();
            Log.d("locationtesting", "accuracy: " + location.hasAccuracy() + " lat: " + latitude + " lon: " + longitude);

            //latitude = String.valueOf(currentLatitude);
            //   longitude = String.valueOf(currentLongitude);

            //Log.d("check", latitude + ", " + longitude);
            // Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();

            strLatitude = String.valueOf(latitude);
            strLongitude = String.valueOf(longitude);

            tv_latitude.setText("");
            tv_longitude.setText("");


            tv_latitude.setText(strLatitude);
            tv_longitude.setText(strLongitude);


        }

        public void checkMeterValidation () {
            if (meterObservationId == 12 || meterObservationId == 14 || meterObservationId == 18) {
                InsertData();
            } else {
                if (currentConsumption < 0) {
                    MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                            .title(R.string.alert)
                            .titleColorRes(R.color.red_500)
                            .content(R.string.meter_consumption_negative)
                            .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                            .canceledOnTouchOutside(false)
                            .positiveText(R.string.ok)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    meterReadingMandatoryEditText.requestFocus();
                                    dialog.dismiss();
                                }
                            }).show();

                } else {
                    if (pastMeterConsumptionInt == 0) {
                        InsertData();
                    } else {
                        checkConsumption();
                    }
                }

            }
        }

        public static boolean isValidDate (String s){

            Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
            Matcher m = p.matcher(s);
            return (m.find() && m.group().equals(s));
        }

        public boolean checkValidation () {

            // updateLocationUI();
            String MobilePattern = "^[6-9][0-9]{9}$";
            boolean isMobileNo = false;

            mobile = upMobileNoEditText.getText().toString().trim();
            strLocality = meterLocationEditText.getText().toString().trim();

            strNewMeterNumber = newMeterNumberMandatoryEditText.getText().toString().trim();


            if (!mobile.equals("")) {

                int length = mobile.length();
                if (length != 10) {
                    //  Toast.makeText(this, R.string.error_invalid_mobile, Toast.LENGTH_SHORT).show();
                    MessageWindow.messageWindow(mCon, getResources().getString(R.string.error_invalid_mobile), "Alert");

                    upMobileNoInputLayout.setError(getResources().getString(R.string.please_enter_valid_mobile_number));
                    upMobileNoEditText.requestFocus();
                    return false;
                }

            }


            if (!mobile.matches(MobilePattern)) {

                upMobileNoInputLayout.setError(getResources().getString(R.string.please_enter_valid_mobile_number));
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_enter_valid_mobile_number), Toast.LENGTH_SHORT).show();
                //  MessageWindow.messageWindow(mCon, getResources().getString(R.string.please_enter_valid_mobile_number), "Alert");

                return false;
            } else {
                upMobileNoEditText.setError(null);
                upMobileNoInputLayout.setError(null);

            }


            if (strLocality.equals("")) {
                //if (strLocality.equals("")) {
                meterLocationInputLayout.setError(getResources().getString(R.string.Please_enter_meter_location));

                //  Toast.makeText(this, R.string.error_location, Toast.LENGTH_SHORT).show();
                meterLocationEditText.requestFocus();

                return false;
            } else {
                meterLocationInputLayout.setError(null);
                meterLocationEditText.setError(null);

            }

//


            return true;
        }

/*    public void finalInsert() {

        InsertData();
        // finalInsert();

    }*/

        public void checkConsumption () {
            int consumptionCheck = ((currentConsumption - pastMeterConsumptionInt) * 100) / pastMeterConsumptionInt;

            if (consumptionCheck > highConsumption) {
                //finalInsert();
                InsertData();
          /*  MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                    .title(R.string.alert)
                    .titleColorRes(R.color.red_500)
                    .content(R.string.meter_consumption_hight_r_u_want_to_submit)
                    .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                    .canceledOnTouchOutside(false)
                    .positiveText(R.string.yes)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // InsertData();
                            finalInsert();
                        }
                    })
                    .negativeText(R.string.cancel)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            meterReadingMandatoryEditText.requestFocus();
                            dialog.dismiss();
                        }
                    }).show();*/
            } else if (consumptionCheck < -lowConsumption) {

                InsertData();
                //finalInsert();

                //edited by

         /*   MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                    .title(R.string.alert)
                    .titleColorRes(R.color.red_500)
                    .content(R.string.meter_consumption_low_r_u_want_to_submit)
                    .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                    .canceledOnTouchOutside(false)
                    .positiveText(R.string.yes)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            InsertData();
                        }
                    })
                    .negativeText(R.string.cancel)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            meterReadingMandatoryEditText.requestFocus();
                            dialog.dismiss();
                        }
                    }).show();*/
            } else {
                InsertData();
                // finalInsert();
            }
        }

        public void InsertData () {
            if (checkValidation()) {
                if (currentConsumption < 0) {
                    if (meterObservationId == 12 || meterObservationId == 14 || meterObservationId == 18) {
                        showSubmitAlertDilogBox();
                    } else {
                        MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                                .title(R.string.alert)
                                .titleColorRes(R.color.red_500)
                                .content(R.string.meter_consumption_negative)
                                .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                                .canceledOnTouchOutside(false)
                                .positiveText(R.string.ok)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        meterReadingMandatoryEditText.requestFocus();
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                } else {
                    showSubmitAlertDilogBox();
                }
            }
        }

        private void showSubmitAlertDilogBox () {

            //Uncomment the below code to Set the message and title from the strings.xml file
            // builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

            //Setting message manually and performing action on button click
            //Setting message manually and performing action on button click
            builder.setMessage(getResources().getString(R.string.are_you_sure_want_to_submit))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            InsterConfirmData();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button

                            dialog.cancel();

                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle(getResources().getString(R.string.app_name));
            alert.show();
        }

        public void InsterConfirmData () {


            String strConsumerNoText;
            meterReadingInt = Integer.parseInt(strMeterReadingMandatory);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US);
            String Date = sdf.format(Calendar.getInstance().getTime());
            strCurrentDateTime = Date;

            boolean isLat = false, isLong = false, validConsumerNo = false, isValidReaderRemark = false;

            strNearBy = nearByLandMarkEditText.getText().toString().trim();
            strConsumerNoText = consumerNoTextView.getText().toString().trim();


            strLatitude = tv_latitude.getText().toString();
            strLongitude = tv_longitude.getText().toString();


            if (readingFlag == 1) {
                rereadingStatus = 1;
            } else if (readingFlag == 0) {
                rereadingStatus = 0;
            }

            if (strMobileNo.equals(strUpdatedMobileNo)) {
                strUpdatedMobileNo = strMobileNo;
                //Toast.makeText(mCon, "numbers are same", Toast.LENGTH_SHORT).show();
            } else {
                strUpdatedMobileNo = upMobileNoEditText.getText().toString().trim();
            }

            int locationStatus;

            if (strLatitude.equals(null)) {
                isLat = false;
            } else {
                isLat = true;
            }

            if (strLongitude.equals(null)) {
                isLong = false;
            } else {
                isLong = true;
            }

            if (isLat && isLong) {
                locationStatus = 1;
            } else {
                locationStatus = 0;
            }

            if (TextUtils.isEmpty(encodedBase64Image)) {
                strMeterImg = "NOPHOTO";
            } else {
                strMeterImg = encodedBase64Image;
            }

            if (TextUtils.isEmpty(encodedBase64OptionalImage)) {
                strOptionalImg = "NOPHOTO";
            } else {
                strOptionalImg = encodedBase64OptionalImage;
            }

            //strCustomerNo = consumerNoEditText.getText().toString().trim();

            if (TextUtils.isEmpty(strConsumerNo)) {
                consumerNoInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                validConsumerNo = false;
            } else {
                consumerNoInputLayout.setError(null);
                validConsumerNo = true;
            }

            String tarifId = strTariff.split("-")[0].toString();

     /*   if(strTariff.equalsIgnoreCase("2-Non-Domestic"))
        {
            strTariff = "2";

        }else{
            strTariff = "1";
        }
*/

            // if (validConsumerNo && isValidReaderRemark) {
            if (validConsumerNo) {

                // Number currentIdNum = realmOperations.getDataReadingIDCount();
/*

            if (currentIdNum == null) {
                nextId = 1;
            } else {
                nextId = currentIdNum.intValue() + 1;
            }
*/

         /*   DataReadingModel dataReadingModel = new DataReadingModel(nextId, strConsumerNoText, strName, rdRid, seqNumber, tarifId, strAddress, strBuldingId, billMonth,
                    strMobileNo, strUpdatedMobileNo, strMeterImg, strOptionalImg, strCurrentDateTime, strMeterNo, meterDigit, strPastMeterReadingDate, pastMeterReadingInt,
                    meterReadingInt, pastMeterConsumptionInt, currentConsumption, strPastMeterStatus, meterStatusId, meterObservationId, strIsMobileNoUpdated,
                    strIsSealBroken, strIsMeterTemperedBroken, strIsRequiredMeterShifting, strViolationOfTariff, underConstruction, leakageInMeterFitting,
                    ballValveReplacement, overflowOfWater, consumerAbusedManhandle, strReadingRemark, strLocality, strNearBy, strLatitude, strLongitude, employeeCode,
                    0, "", "", "", 1, 0, rereadingStatus, locationStatus,
                    0, 0, ml, ca, strNewMeterNumber, "", illegalConection, meterByPass, spinMeterObservation, strPastObservation);
            realmOperations.InsertCustomerReading(dataReadingModel);*/
                //  realmOperations.updateDataReading(consumerID, 1, 0, rereadingStatus, locationStatus, 0);
                // "19.1026689", "73.0070636"
                tv_latitude.setText("");
                tv_longitude.setText("");


                if (searchCheck == 1) {

                } else {

                    //      realmOperations.updateDataReading(consumerID, 1, 0, rereadingStatus, locationStatus, 0);
                }

                if (rereadingStatus == 1) {
                    Intent i = new Intent(mCon, MainActivity.class);
                    i.putExtra("Tag", "3");
                    startActivity(i);
                    // overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();
                } else if (rereadingStatus == 0) {
                    try {
                        int next = consumerID + 1;
                        //ConsumersModel model = realmOperations.fetchConsumerById(next);
                        //      model = realmOperations.fetchConsumerById(next);
                   /* if (employeeLoginModel.getIsValid() == 1) {

                        // showSubmitAlertDilogBox();
                        Intent i = new Intent(mCon, ConsumerReadingActivityDemo.class);
                        i.putExtra("consumerId", model.getId());
                        i.putExtra("readingFlag", 0);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        finish();
                    } else {
                        Toast.makeText(mCon, R.string.user_blocked, Toast.LENGTH_SHORT).show();
                        finish();
                    }
*/
                    } catch (Exception e) {

                 /*   ErrorLogModel errorLogModel = new ErrorLogModel(empCode, imei, Build.MODEL, Build.MANUFACTURER, Build.VERSION.SDK_INT, AppVersion,"LoginActivity", "LoginButtonClick", "", "0","");
                    realmOperations.insertErrorLogData(errorLogModel);*/
                        //      Toast.makeText(mCon, getResources().getString(R.string.no_more_consumer_left_to_capture_reading), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

        }

        @Override
        protected void onDestroy () {
            // TODO Auto-generated method stub

            super.onDestroy();
        }

 /*   public ArrayList<String> fetchMeterStatusNameID() {

        RealmResults<MeterStatusModel> meterStatusModels = realm.where(MeterStatusModel.class).findAll();
        for (MeterStatusModel model : meterStatusModels) {
            msList.add( model.getMeterStatus_name());

        }
        return msList;
    }*/


        public static boolean isTimeAutomatic (Context c){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
            } else {
                return Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
            }
        }


        @Override
        public void onBackPressed () {
            super.onBackPressed();
            startBackActivity();
        }

   /* @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }*/
    }

