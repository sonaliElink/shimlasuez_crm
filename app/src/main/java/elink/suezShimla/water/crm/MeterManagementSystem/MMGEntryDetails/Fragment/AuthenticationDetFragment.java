package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ConnectionManagement.fragment.NCUploadDocDetailsFragment;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCvlMeasurementResponseModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGScreenActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMaterialResponseModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

import static android.app.Activity.RESULT_OK;

public class AuthenticationDetFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,View.OnClickListener {

    //unused
    private Button common_save_btn,btn_saveANDexist;
    private RadioButton through_otp, btn_digital_signature;

    //used
    private Boolean submitBtnClicked = false;

    private ImageView imgExistingMeterReading, imgNewMeterBoxSeal, imgMeterHandover, imgRestoration, imgOther, imgDigitalSign;
    private AlertDialog.Builder builder_through_otp;
    private Button mClear, mGetSign, mCancel;
    private EditText phone_number_edt;
    private Signature mSignature;

    File file;
    Dialog dialogSignature;
    LinearLayout mContent, ll_otp_authentication, ll_otp_verification;
    TextInputEditText remarkEditText;
    String remarkStr = "";
    View view;
    Bitmap bitmap, signBitmap;
    boolean uploadFlag = false, uploadDocFlag = false;

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
    private FusedLocationProviderClient mFusedLocationClient;
    LocationManager lm;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    double lat, lng;
    private static final int REQUEST_LOCATION = 1;
    Bitmap rotatedBitmap;


    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/DigitSign/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    static String vendorcodeStr = "", makerCodeIdStr = "",latitude="", longitude="",
            meterNumStr = "", meterSizeIdStr = "", sealNumStr = "", initialReadingNoStr = "",
            meterTypeIdStr = "", meterLocationIdNo = "", protectedBoxIdNoStr = "", taxNumStr = "", sourceTag = "",
            cntrctrNameIdStr = "", radioButtonVal = "", otherContractorStr = "", otherContractorEmpStr = "",
            other_code_idStr = "", sealMakeIdStr = "", meterBoxMakeIdStr = "";

    String oldmakerCodeNumId = "", oldmeterNumStr = "", oldinstallDtValStr = "",
            oldsealNumStr = "", oldMtrStsNumId = "", finalReadingNumStr = "",
            pastMeterNoRcvdStr = "", gis_bid_str = "", oldmeterSizeNum = "";

    static String pccLengthNumStr = "", pccWidthNumStr = "", pccDepthNumStr = "", pccTotalNumStr = "", roadCuttingIdNumStr = "",
            rdLengthNumStr = "", rdWidthNumStr = "", rdDepthNumStr = "", rdTotalNumStr = "", fromNode = "", toNode = "",
            primary_mobile = "", alt_mobile = "", dma_str = "", property_assessmnt_num = "", sr_str = "", msr_str = "",
            commisioned_noncommissioned_str = "", emp_login_str = "", ip_str = "", materialHandoverStr = "",
            finalStatusNumStr = "", meterObservationIdStr = "", oldmeterTypeNumId = "", contractorId = "",
            c_employee_id = "", installDateStr = "", cntrctrEmpIdStr = "", reasonId = "", meterHandoverStr = "",
            dial_digit = "", BU = "", PC = "";

    public static final int RequestPermissionCode = 1;
    private String mtrTypeCode = "";
    private int reqcode_img1 = 1010;
    private int reqcode_img3 = 1030;
    private int reqcode_img4 = 1040;
    private int reqcode_img5 = 1050;
    private int reqcode_img6 = 1060;

    private String imageName;
    File storageDir;
    Uri imageUri;
    double newImageHeight = 512.0;
    int imageQuality = 65;
    int imgCount = 0, strCounter = 0, errorCounter = 0, uploadedImages = 0;

    FloatingActionButton fab_otp, fab_verified;

    Context mCon;
    String imgStringOne = "", imgStringThree = "", imgStringFour = "", imgStringFive = "",
            imgStringSix = "", imgDigitalSignature = "", consumerNo = "", jsonResponse = "",
            jsonMeterInstallSaveResponse = "", refNo = "", submit_status = "", contList = "", empCode = "", ip = "",
            jsonSMSResponse = "", confirmOTP = "",newavrageConsumtion, msgTypeID = "1", buttonName = "", consName = "", jsonErrorResponce = "";

    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    static private boolean isConsumerSubmitted = false, isOldSubmitted = false, isNewMeterSubmitted = false,
            isContractorSubmitted = false, is_civil_submitted = false, isMaterialSubmitted = false, isMtrProtectionSubmitted = false;
    SharedPreferences sharedpreferences,metershred;
    EditText et_one, et_two, et_three, et_four;
    //random number
    Random Number;
    int Rnumber;
    String mobile = "";
    private boolean flag = false;
    List<MMGMaterialResponseModel> materialDetaillist = new ArrayList<MMGMaterialResponseModel>();
    List<MMGMaterialResponseModel> list;
    List<MMGCvlMeasurementResponseModel> cvllist;
    private String prefName = "NewmeterImagesupload";

    StringBuilder materialDetailXml = new StringBuilder();
    StringBuilder cvlMeasurementXml = new StringBuilder();
    String StoredPath = "", exisitinImage = "", newMetrBoxImage = "", mtrHandOvrImage = "", restortationImage = "",
            otherImage = "", digitalImage = "", documentSize = "", newMaterialDetailXml = "", newCivilDetailsXml = "";

    private MaterialDialog progress;
    private ArrayList<String> documentImages, documentSubtype, imageSize;

    TextView tv_send_mobile;
    RelativeLayout relative_layout_upload_photo;

    String MI_METERINSTALLID = "", MI_ACTION, MI_CONSUMER, MI_BU, MI_PC, MI_REFNO, MI_O_SIZE = "", MI_O_METER = "", MI_O_MAKE = "", MI_O_PREVIOUSREADING = "", MI_O_FINALREADING = "", MI_O_FINALSTATUS = "", MI_O_REASON = "", MI_O_METERTYPE = "",
            MI_N_MAKE, MI_N_SIZE, MI_N_SEAL, MI_N_METER, MI_INSTALLATIONDATE, MI_N_INITIALREADING, MI_N_METERTYPE, MI_N_METERLOCATION, MI_N_ISPROTECTED, MI_PROPERTYTAXNO, MI_N_ISMETERHANDOVER,
            MI_CONTRACTOR, MI_CONTRACTOREMP, MI_N_ISMATERIALHANDOVER, MI_PCCBEDDINGLEN, MI_PCCBEDDINGWID,
            MI_PCCBEDDINGDEP, MI_ROADCUTTINGTYPE, MI_ROADCUTTINGLEN, MI_ROADCUTTINGWID, MI_ROADCUTTINGDEP, MI_FROMNODE,
            MI_TONODE, MI_REGMOBILE, MI_ALTMOBILE, MI_GIS, MI_DMA, MI_SR, MI_MODIFIEDBY, MI_MODIFIEDDATE, MI_IP, MI_AUTHENTICATEDATE,
            MI_AUTHREJECTBY, MI_REJECTEDDATE, MI_STATUS, MI_ISACTIVE, MI_XMLMATERIAL, MI_XMLCIVIL, MI_O_OBSERVATION,
            MI_SOURCE, MI_ISCOMMISSIONED, MI_CONTRACTOROTHER, MI_CONTRACTOREMPOTHER, MI_N_DIGIT, MSRID, MI_N_SEAL_MAKE, MI_N_METER_BOX_MAKE, meter_own;
    ArrayList<String> arrayImageName;
    String  metercapture="",newmeter="",meterhandover="",restor="",otherimg="",signature="",Remark="";
    String img1,img2,img3,img4,img5,img6,remark;
    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;


    public AuthenticationDetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View rootView = inflater.inflate(R.layout.fragment_authentication_vertical_images, container, false);
        mCon = getActivity();
        gson = new Gson();
        invServices = new Invoke();
        connection = new ConnectionDetector(mCon);

        sharedpreferences = getActivity().getSharedPreferences("upload", Context.MODE_PRIVATE);
        // metershred = getActivity().getSharedPreferences("meterImagesupload", Context.MODE_PRIVATE);

        list = null;
        list = MMGScreenActivity.materialDemoList;
        cvllist = MMGScreenActivity.cvlMeasurementResponseModelList;
        builder_through_otp = new AlertDialog.Builder(getActivity());
        LayoutInflater lay_inflater = this.getLayoutInflater();
        builder_through_otp.setView(lay_inflater.inflate(R.layout.alert_dailog_through_otp, null));


        btn_saveANDexist= rootView.findViewById(R.id.saveAndExitDocument);
        RadioGroup radioGroup = rootView.findViewById(R.id.radioGroup);
        through_otp = rootView.findViewById(R.id.through_otp);
        btn_digital_signature = rootView.findViewById(R.id.btn_digital_signature);
        Button saveAuthenticationButton = rootView.findViewById(R.id.saveAuthenticationButton);
        Button submitButton = rootView.findViewById(R.id.submitButton);
        common_save_btn = rootView.findViewById(R.id.common_save_btn);
        relative_layout_upload_photo = rootView.findViewById(R.id.relative_layout_upload_photo);
        getLocation();
        try {
            empCode = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ip = PreferenceUtil.getMac();
        imgExistingMeterReading = rootView.findViewById(R.id.imgExistingMeterReading);
        imgNewMeterBoxSeal = rootView.findViewById(R.id.imgNewMeterBoxSeal);
        imgMeterHandover = rootView.findViewById(R.id.imgMeterHandover);
        imgRestoration = rootView.findViewById(R.id.imgRestoration);
        imgOther = rootView.findViewById(R.id.imgOther);
        imgDigitalSign = rootView.findViewById(R.id.imgDigitalSign);

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

        documentImages = new ArrayList<>();
        documentImages.add(null);
        documentImages.add(null);
        documentImages.add(null);
        documentImages.add(null);
        documentImages.add(null);
        documentImages.add(null);

        documentSubtype = new ArrayList<>();
        documentSubtype.add(null);
        documentSubtype.add(null);
        documentSubtype.add(null);
        documentSubtype.add(null);
        documentSubtype.add(null);
        documentSubtype.add(null);

        imageSize = new ArrayList<>();
        imageSize.add(null);
        imageSize.add(null);
        imageSize.add(null);
        imageSize.add(null);
        imageSize.add(null);
        imageSize.add(null);

        arrayImageName = new ArrayList<>();
        arrayImageName.add("Existing Meter Reading");
        arrayImageName.add("New Meter, Box & Seal");
        arrayImageName.add("Meter Handover");
        arrayImageName.add("Restoration");
        arrayImageName.add("Other");
        arrayImageName.add("Signature");

        saveAuthenticationButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        tv_send_mobile = rootView.findViewById(R.id.tv_send_mobile);
        et_one = rootView.findViewById(R.id.et_one);
        et_two = rootView.findViewById(R.id.et_two);
        et_three = rootView.findViewById(R.id.et_three);
        et_four = rootView.findViewById(R.id.et_four);

        ll_otp_verification = rootView.findViewById(R.id.ll_otp_verification);
        ll_otp_authentication = rootView.findViewById(R.id.ll_otp_authentication);
        phone_number_edt = rootView.findViewById(R.id.phone_number_edt);
        fab_otp = rootView.findViewById(R.id.fab_otp);
        fab_verified = rootView.findViewById(R.id.fab_verified);
        remarkEditText = rootView.findViewById(R.id.remarkEditText);

//      submit_status = UtilitySharedPreferences.getPrefs(mCon, Constants.SUBMIT_STATUS);
//      builder_through_otp= new AlertDialog.Builder(getActivity());

        through_otp.setOnClickListener(this);
        btn_digital_signature.setOnClickListener(this);
        contList = getArguments().getString("contList");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.through_otp) {
                    ll_otp_authentication.setVisibility(View.VISIBLE);
                    checkedId = 0;
                } else if (checkedId == R.id.btn_digital_signature) {
                    ll_otp_authentication.setVisibility(View.GONE);
                    dialog_digi_signature();
                    checkedId = 0;
                }
            }
        });
        btn_saveANDexist.setOnClickListener(this);
        imgExistingMeterReading.setOnClickListener(this);
        imgNewMeterBoxSeal.setOnClickListener(this);
        imgMeterHandover.setOnClickListener(this);
        imgRestoration.setOnClickListener(this);
        imgOther.setOnClickListener(this);
        imgDigitalSign.setOnClickListener(this);

        common_save_btn.setOnClickListener(this);

        ll_otp_authentication.setOnClickListener(this);
        ll_otp_verification.setOnClickListener(this);
        fab_otp.setOnClickListener(this);
        fab_verified.setOnClickListener(this);

        // Method to create Directory, if the Directory doesn't exists
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }

        radioButtonVal = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);
        oldmeterNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.OLD_METER_NO);
        // meterNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_METER_NO);
        meterNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_METERNUM);
        consumerNo = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMER_NO);
        newavrageConsumtion = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_AVERAGECONSUMTION);

        StoredPath = DIRECTORY + pic_name + consumerNo + ".png";

        consName = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMER_NAME);
        refNo = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMERREFERENCENUMBER);
        sourceTag = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMER_SOURCE);
        BU = UtilitySharedPreferences.getPrefs(getActivity(), Constants.BU);
        PC = UtilitySharedPreferences.getPrefs(getActivity(), Constants.PC);
        oldmakerCodeNumId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.OLD_MAKERCODE);
        pastMeterNoRcvdStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.PASTMETERNO);
        finalReadingNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.FINALREADING);
//      reasonId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.REASONID);
//      oldmeterTypeNumId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.OLDMETERTYPE);
        makerCodeIdStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_MAKERCODE);
        meterSizeIdStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_METERSIZE);
        sealNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_SEALNO);
        meterNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_METER_NO);
        initialReadingNoStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_INITIALREADING);
        meterTypeIdStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_METERTYPE);
        meterLocationIdNo = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_METERLOCATION);
        //protectedBoxIdNoStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_PROTECTEDBOX);//pinky commented on 19/03/2022
        taxNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_TAXNO);

        sealMakeIdStr = UtilitySharedPreferences.getPrefs(getActivity(), consumerNo + "sealmake");
        meterBoxMakeIdStr = UtilitySharedPreferences.getPrefs(getActivity(), consumerNo + "meterboxmake");

        //materialHandoverStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MATERIALHANDOVER);
        // materialDetaillist = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MATERIALXML);
        // cvlMeasurementlist = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CIVILMEASUREMENTXML);
        pccLengthNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.PCCLEN);
        pccWidthNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.PCCWIDTH);
        pccDepthNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.PCCDEPTH);
        roadCuttingIdNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RDCUTTINGID);
        rdLengthNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RDCUTTINGLENGTH);
        rdWidthNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RDCUTTINGWIDTH);
        rdDepthNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RDCUTTINGDEPTH);
        rdTotalNumStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RDCUTTINGTOTAL);
        commisioned_noncommissioned_str = UtilitySharedPreferences.getPrefs(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);
        gis_bid_str = UtilitySharedPreferences.getPrefs(getActivity(), Constants.GIS_BID);
        oldmeterSizeNum = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_SIZE_ID);
        mtrTypeCode = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_TYPE_CODE_ID);

        fetchDetailsFromContractor();

        if (radioButtonVal.equalsIgnoreCase("N")) {
            imgExistingMeterReading.setEnabled(false);
            imgRestoration.setEnabled(false);
            imgExistingMeterReading.setAlpha(45);
            imgRestoration.setAlpha(45);
            exisitinImage = "y";
            restortationImage = "y";
            documentImages.set(3, "Disable");
            documentImages.set(0, "Disable");
        } else if (radioButtonVal.equalsIgnoreCase("OH")) {
            imgNewMeterBoxSeal.setEnabled(false);
            imgNewMeterBoxSeal.setAlpha(45);
            newMetrBoxImage = "y";
            documentImages.set(1, "Disable");
        }
        if (radioButtonVal.equalsIgnoreCase("S") || radioButtonVal.equalsIgnoreCase("MB")) {
            imgMeterHandover.setEnabled(false);
            imgRestoration.setEnabled(false);
            imgMeterHandover.setAlpha(45);
            imgRestoration.setAlpha(45);
            mtrHandOvrImage = "y";
            restortationImage = "y";
            documentImages.set(2, "Disable");
            documentImages.set(3, "Disable");
        }

        if (meterHandoverStr.equalsIgnoreCase("2") || meterHandoverStr.equalsIgnoreCase("")) {
            imgMeterHandover.setEnabled(false);
            imgMeterHandover.setAlpha(45);
            mtrHandOvrImage = "y";
            documentImages.set(2, "Disable");
        }
        getdata();
        return rootView;
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

    private void getdata() {
        metershred =getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);

        metercapture = metershred.getString("metercapture"+oldmeterNumStr,"");
        newmeter = metershred.getString("newmeter"+oldmeterNumStr,"").toString();
        meterhandover = metershred.getString("meterHandover"+oldmeterNumStr,"").toString();
        restor = metershred.getString("restore"+oldmeterNumStr,"").toString();
        otherimg = metershred.getString("other"+oldmeterNumStr,"").toString();
        signature = metershred.getString("sign"+oldmeterNumStr,"").toString();
        Remark = metershred.getString("remark"+oldmeterNumStr,"").toString();

        if(metercapture!=("") || !metercapture.equals("")||!newmeter.equals("")||!meterhandover.equals("")||!restor.equals("")||!otherimg.equals("")||!signature.equals("")) {
            try {
                if(!metercapture.equals("")) {
                    exisitinImage="y";
                    setImageToList(0, metercapture);
                    setImageIdToList(0, "282");
                    String ImageSize = getImageSize(metercapture);
                    setImageSizeToList(0, ImageSize);

                    byte[] imageBytes = Base64.decode(metercapture, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imgExistingMeterReading.setImageBitmap(decodedImage);
                }else {
                    Drawable myDrawable = getResources().getDrawable(R.drawable.ic_camera_mmg);
                    imgExistingMeterReading.setImageDrawable(myDrawable);
                }
                if(!newmeter.equals("")) {
                    newMetrBoxImage="y";
                    setImageToList(1, newmeter);
                    setImageIdToList(1, "284");
                    String ImageSize = getImageSize(newmeter);
                    setImageSizeToList(1, ImageSize);
                    byte[] imageByte2 = Base64.decode(newmeter, Base64.DEFAULT);
                    Bitmap decodedImage2 = BitmapFactory.decodeByteArray(imageByte2, 0, imageByte2.length);
                    imgNewMeterBoxSeal.setImageBitmap(decodedImage2);
                }else {
                    Drawable myDrawable = getResources(). getDrawable(R. drawable. ic_camera_mmg);
                    imgNewMeterBoxSeal. setImageDrawable(myDrawable);
                }


                if(!meterhandover.equals("Disable")&&!meterhandover.equals("")) {
                    mtrHandOvrImage="y";
                    setImageToList(2, meterhandover);
                    setImageIdToList(2, "286");
                    String ImageSize = getImageSize(meterhandover);
                    setImageSizeToList(2, ImageSize);
                    byte[] imageByte3 = Base64.decode(meterhandover, Base64.DEFAULT);
                    Bitmap decodedImage3 = BitmapFactory.decodeByteArray(imageByte3, 0, imageByte3.length);
                    imgMeterHandover.setImageBitmap(decodedImage3);
                }else{
                    documentImages.set(3,"Disable");
                    setImageIdToList(2, "286");
                    Drawable myDrawable = getResources(). getDrawable(R. drawable. ic_camera_mmg);
                    imgMeterHandover. setImageDrawable(myDrawable);
                }
                if(!restor.equals("Disable")&&!restor.equals("")) {
                    restortationImage="y";
                    setImageToList(3, restor);
                    setImageIdToList(3, "288");
                    String ImageSize = getImageSize(restor);
                    setImageSizeToList(3, ImageSize);
                    byte[] imageBytes4 = Base64.decode(restor, Base64.DEFAULT);
                    Bitmap decodedImage4 = BitmapFactory.decodeByteArray(imageBytes4, 0, imageBytes4.length);
                    imgRestoration.setImageBitmap(decodedImage4);
                }else{
                    Drawable myDrawable = getResources(). getDrawable(R. drawable. ic_camera_mmg);
                    imgRestoration. setImageDrawable(myDrawable);
                }
                if(!otherimg.equals("")) {
                    setImageToList(4, otherimg);
                    setImageIdToList(4, "290");
                    String ImageSize = getImageSize(otherimg);
                    setImageSizeToList(4, ImageSize);
                    byte[] imageBytes5 = Base64.decode(otherimg, Base64.DEFAULT);
                    Bitmap decodedImage5 = BitmapFactory.decodeByteArray(imageBytes5, 0, imageBytes5.length);
                    imgOther.setImageBitmap(decodedImage5);
                }else{
                    Drawable myDrawable = getResources(). getDrawable(R. drawable. ic_camera_mmg);
                    imgOther. setImageDrawable(myDrawable);
                }
                if(!signature.equals("")){
                    digitalImage="y";
                    setImageToList(5, signature);
                    setImageIdToList(5, "298");
                    String ImageSize = getImageSize(signature);
                    setImageSizeToList(5, ImageSize);
                    byte[] imageBytes6 = Base64.decode(signature, Base64.DEFAULT);
                    Bitmap decodedImage6 = BitmapFactory.decodeByteArray(imageBytes6, 0, imageBytes6.length);
                    imgDigitalSign.setImageBitmap(decodedImage6);}
                else{
                    Drawable myDrawable = getResources(). getDrawable(R. drawable. digisig);
                    imgDigitalSign. setImageDrawable(myDrawable);
                }
                remarkEditText.setText(Remark);
            }catch (Exception E){
                E.printStackTrace();
            }
        }





    }
    public void setImageToList(int pos, String strImage) {
        documentImages.set(pos, strImage);

    }

    public void setImageIdToList(int pos, String strImage) {

        documentSubtype.set(pos, strImage);
    }

    public void setImageSizeToList(int pos, String imgSize) {
        imageSize.set(pos, imgSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_otp:
                if (checkValidation()) {
                    createRandomOTP();
                }

                break;
            case R.id.fab_verified:
                ll_otp_verification.setVisibility(View.GONE);
                ll_otp_verification.setVisibility(View.GONE);

                break;
            case R.id.imgExistingMeterReading:
                enableRuntimePermission();
                try {
                    startCameraIntent(reqcode_img1);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
                break;
            case R.id.imgNewMeterBoxSeal:
                enableRuntimePermission();
                try {
                    startCameraIntent(reqcode_img3);
                } catch (Exception ex) {

                }
                break;
            case R.id.imgMeterHandover:
                enableRuntimePermission();
                try {
                    startCameraIntent(reqcode_img4);
                } catch (Exception ex) {

                }
                break;
            case R.id.imgRestoration:
                enableRuntimePermission();
                try {
                    startCameraIntent(reqcode_img5);
                } catch (Exception ex) {

                }
                break;
            case R.id.imgOther:
                enableRuntimePermission();
                try {
                    startCameraIntent(reqcode_img6);
                } catch (Exception ex) {

                }
                break;
            case R.id.saveAndExitDocument:

                documentSize = String.valueOf(documentImages.size());

                if(metercapture.equals("")){
                    img1 = documentImages.get(0);//newdocumeuri
                }else{img1=metercapture;}

                if(newmeter.equals("")) {
                    img2 = documentImages.get(1);
                }else{ img2=newmeter;}

                if(meterhandover.equals("")) {
                    img3 = documentImages.get(2);
                }else{ img3=meterhandover;}

                if(restor.equals("")) {
                    img4 = documentImages.get(3);
                }else{ img4=restor;}

                if(otherimg.equals("")) {
                    img5 = documentImages.get(4);
                }else{ img5=otherimg;}
                if(signature.equals("")) {
                    img6 = documentImages.get(5);
                }else{     img6=signature; }
                if(Remark.equals("")) {
                    remark = remarkEditText.getText().toString();
                }else{ remark =Remark;}

                metershred = getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = metershred.edit();
                editor.putString("oldmeterNumStr" + oldmeterNumStr, oldmeterNumStr);
                editor.putString("metercapture" + oldmeterNumStr, img1);
                editor.putString("newmeter" + oldmeterNumStr, img2);
                editor.putString("meterHandover" + oldmeterNumStr, img3);
                editor.putString("restore" + oldmeterNumStr, img4);
                editor.putString("other" + oldmeterNumStr, img5);
                editor.putString("sign" + oldmeterNumStr, img6);
                editor.putString("remark" + oldmeterNumStr, remark);

                editor.commit();

                submit_status = "N";
                submitNewDetails();
/*else{if(s1.equals("")){
     img1 = documentImages.get(0);//newdocumeuri
    }else{img1=s1;}

    if(s2.equals("")) {
    img2 = documentImages.get(1);
    }else{ img2=s2;}

    if(s3.equals("")) {
         img3 = documentImages.get(2);
    }else{ img3=s3;}

    if(s4.equals("")) {
         img4 = documentImages.get(3);
    }else{ img4=s4;}

    if(s5.equals("")) {
         img5 = documentImages.get(4);
    }else{ img5=s5;}

    if(s6.equals("")) {
        img6 = documentImages.get(5);
    }else{     img6=s6; }


     remark = remarkEditText.getText().toString();
    metershred = getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);

    SharedPreferences.Editor editor = metershred.edit();
    editor.putString("oldmeterNumStr" + oldmeterNumStr, oldmeterNumStr);
    editor.putString("metercapture" + oldmeterNumStr, img1);
    editor.putString("newmeter" + oldmeterNumStr, img2);
    editor.putString("meterHandover" + oldmeterNumStr, img3);
    editor.putString("restore" + oldmeterNumStr, img4);
    editor.putString("other" + oldmeterNumStr, img5);
    editor.putString("sign" + oldmeterNumStr, img6);
    editor.putString("remark" + oldmeterNumStr, remark);

    editor.commit();

    submit_status = "N";
    submitNewDetails();
}
*/
                //sonali currently working.....
                // saveImage(documentImages.get(i), consumerNo, oldmeterNumStr, meterNumStr, documentSubtype.get(i), empCode, ip, imageSize.get(i), String.valueOf(imgCount));
                // }
                break;
            case R.id.submitButton:

                if (oldmeterNumStr == null){
                    oldmeterNumStr = "";
                }

                submit_status = "Y";
                errorCounter = 0;
                img1 = documentImages.get(0);//newdocumeuri
                img2 = documentImages.get(1);
                img3 = documentImages.get(2);
                img4 = documentImages.get(3);
                img5 = documentImages.get(4);
                img6 = documentImages.get(5);
                remark = remarkEditText.getText().toString();

                SharedPreferences.Editor submitedior = metershred.edit();
                submitedior.putString("oldmeterNumStr" + oldmeterNumStr, oldmeterNumStr);
                submitedior.putString("metercapture" + oldmeterNumStr, img1);
                submitedior.putString("newmeter" + oldmeterNumStr, img2);
                submitedior.putString("meterHandover" + oldmeterNumStr, img3);
                submitedior.putString("restore" + oldmeterNumStr, img4);
                submitedior.putString("other" + oldmeterNumStr, img5);
                submitedior.putString("sign" + oldmeterNumStr, img6);
                submitedior.putString("remark" + oldmeterNumStr, remark);

                submitedior.commit();
                if ((exisitinImage.equalsIgnoreCase("y") && newMetrBoxImage.equalsIgnoreCase("y")
                        && mtrHandOvrImage.equalsIgnoreCase("y") && restortationImage.equalsIgnoreCase("y")
                        && digitalImage.equalsIgnoreCase("y"))) {
                    if (submitBtnClicked == false) {//applied this condition on 28/03/2022
                        submitBtnClicked = true;
                        if (CheckValidation()) {

                            for (int i = 0; i < documentImages.size(); i++) {
                                if (!documentImages.get(i).equalsIgnoreCase("Disable") &&
                                        documentImages.get(i) != null
                                        && !documentImages.get(i).equalsIgnoreCase("")
                                        && !documentImages.get(i).isEmpty()) {
                                    imgCount++;
                                }
                            }
                            for (int i = uploadedImages; i < documentImages.size(); i++) {
                                if (errorCounter != -1) {
                                    if (documentImages.get(i) != null
                                            && !documentImages.get(i).equalsIgnoreCase("")
                                            && !documentImages.get(i).isEmpty()) {
                                        if (documentImages.get(i).equalsIgnoreCase("Disable")) {
                                            strCounter++;
                                            uploadedImages++;
                                            continue;
                                        }
                                        saveImage(documentImages.get(i), consumerNo, oldmeterNumStr, meterNumStr, documentSubtype.get(i), empCode, ip, imageSize.get(i), String.valueOf(imgCount));

                                    } else {
                                        saveImage(convertDefaultImage(), consumerNo, oldmeterNumStr, meterNumStr, documentSubtype.get(i), empCode, ip, imageSize.get(i), String.valueOf(imgCount));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    MessageWindow.messageWindow(mCon, "Please upload all document images", "Alert");
                }
                break;

            case R.id.saveAuthenticationButton:
                submit_status = "N";
                submitNewDetails();
                break;

            case R.id.common_save_btn:
                //submit_status="N";
                if (exisitinImage.equalsIgnoreCase("y") && newMetrBoxImage.equalsIgnoreCase("y")
                        && mtrHandOvrImage.equalsIgnoreCase("y") && restortationImage.equalsIgnoreCase("y")
                        && otherImage.equalsIgnoreCase("y")) {

                    documentSize = String.valueOf(documentImages.size());
                    for (int i = 0; i < documentImages.size(); i++) {

                        //pinky comment because this component not in use currently.....
                        // saveImage(documentImages.get(i), consumerNo, oldmeterNumStr, meterNumStr, documentSubtype.get(i), empCode, ip, "button_seven",);
                        if (i == 4) {
                            uploadDocFlag = true;
                            uploadFlag = true;
                            common_save_btn.setText("Documents Uploaded");
                            common_save_btn.setEnabled(false);
                            if (uploadFlag == true) {
                                //   btn_digital_signature.setVisibility(View.VISIBLE);
                                // dialog_digi_signature();
                                relative_layout_upload_photo.setVisibility(View.GONE);
                            } else {
                                btn_digital_signature.setVisibility(View.GONE);
                            }
                        }

                    }
                } else {
                    MessageWindow.messageWindow(mCon, "Please upload all document images", "Alert");
                }
                break;

            case R.id.imgDigitalSign:
                dialog_digi_signature();
                break;

            default:
        }
    }

    private void startCameraIntent(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's activity_login ic_camera activity to handle the intent
        // if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {//pinky commented this 01/02/2022
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
        //}
    }

    private File createImageFile() {

        File image = null;
        try {
            String imageFileName = "PROFILE";

            storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

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
            uploadErrorLog("Authentication Fragment", "Upload Document Image", e.toString());
        }
        return image;
    }

    private boolean CheckValidation() {
        remarkStr = remarkEditText.getText().toString();

        if (remarkStr.equalsIgnoreCase("")) {
            submitBtnClicked = false;
            remarkEditText.setError(getResources().getString(R.string.cannot_be_empty));
            MessageWindow.errorWindow(mCon, mCon.getResources().getString(R.string.please_enter_remark));
            return false;
        } else {
            remarkEditText.setError(null);
        }

        return true;
    }

    private void saveImage(String photo, String consumerNo, String oldMeter, String newMeter,
                           String docSubtype, String empcode, String ip, String imgSize, String imgCount) {
        try {
            String params[] = new String[9];

            params[0] = photo;
            params[1] = consumerNo;
            params[2] = oldMeter;
            params[3] = newMeter;
            params[4] = docSubtype;
            params[5] = empcode;
            params[6] = ip;
            params[7] = imgSize;
            params[8] = imgCount;

            //Log.e("UploadImageParams",Arrays.toString(params));

            if (connection.isConnectingToInternet()) {
                uploadImage uploadImage = new uploadImage();
                uploadImage.execute(params);
            } else {
                MessageWindow.errorWindow(mCon, mCon.getResources().getString(R.string.no_internet_connection));
            }

        } catch (Exception e) {
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class uploadImage extends AsyncTask<String, Void, String> {
        MaterialDialog progressImage;

        @Override
        protected void onPreExecute() {
            progressImage = new MaterialDialog.Builder(getActivity())
                    .content(R.string.document_uploading)
                    .progress(true, 0)
                    .cancelable(true)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String paraname[] = new String[9];

                paraname[0] = "METERPHOTO";
                paraname[1] = "consumerNo";
                paraname[2] = "oldMeter";
                paraname[3] = "newMeter";
                    paraname[4] = "DocSubType";
                paraname[5] = "EmpCode";
                paraname[6] = "IP";
                paraname[7] = "ImgSize";
                paraname[8] = "ImgCount";

                //Log.e("ImageParams", Arrays.toString(params));

                if (errorCounter != -1) {

                    jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "MMG_PushPhoto", params, paraname);

                    JSONArray jsonArray = new JSONArray(jsonResponse);
                    if (jsonArray.get(0).toString().equalsIgnoreCase("Success")) {
                        strCounter++;
                        uploadedImages++;
                    } else {
                        errorCounter = -1;
                        MessageWindow.errorWindow(getActivity(), jsonArray.get(0).toString());
                        uploadErrorLog("Authentication Fragment", "Upload Document Image", jsonArray.get(0).toString());
                    }
                } else {
                    jsonResponse = "[-1]";
                }

            } catch (Exception e) {
                flag = true;
                progressImage.dismiss();
                errorCounter = -1;
                MessageWindow.errorWindow(mCon, e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Upload Document Image", error);
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String responseData) {
            try {
                JSONArray jsonArray = new JSONArray(responseData);
                progressImage.dismiss();
                if (strCounter == documentImages.size() && errorCounter != -1) {
                    submitNewDetails();
                }
                if (jsonArray.get(0).toString().equalsIgnoreCase("Failure")) {
                    String message = "";
                    for (int i = 0; i < documentImages.size(); i++) {
                        if (documentImages.get(i).equalsIgnoreCase("Disable")) {
                            continue;
                        } else if (i < uploadedImages) {
                            message += "Document for " + arrayImageName.get(i) + " uploaded\n";
                        } else {
                            message += "Document for " + arrayImageName.get(i) + " NOT uploaded\n";
                        }
                    }
                    submitBtnClicked = false;
                    MessageWindow.errorWindow(mCon, jsonArray.get(0).toString() + "\n\n" + message + "\nMeter Installation data NOT submitted\n\nPlease re-try to capture photo & Submit again.");
                }

            } catch (Exception e) {
                submitBtnClicked = false;
                errorCounter = -1;
                MessageWindow.errorWindow(mCon, e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Upload Document Image", error);
                progressImage.dismiss();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  Uri imageUri;
        Log.println(Log.DEBUG, "tag", "" + requestCode);
/*
        if (resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");*/

        if (requestCode == reqcode_img1 && resultCode == RESULT_OK) {

            imgStringOne = setImage(imgExistingMeterReading);
            imgExistingMeterReading.setImageBitmap(rotatedBitmap);

            if (!imgStringOne.equalsIgnoreCase("") && imgStringOne != null) {
                exisitinImage = "y";

                setImageToList(0, imgStringOne);
                setImageIdToList(0, "282");
                String ImageSize = getImageSize(imgStringOne);
                setImageSizeToList(0, ImageSize);
            }
        }

        if (requestCode == reqcode_img3 && resultCode == RESULT_OK) {

            imgStringThree = setImage(imgNewMeterBoxSeal);
            imgNewMeterBoxSeal.setImageBitmap(rotatedBitmap);//bitmap

            if (!imgStringThree.equalsIgnoreCase("") && imgStringThree != null) {
                newMetrBoxImage = "y";
                setImageToList(1, imgStringThree);
                setImageIdToList(1, "284");
                String ImageSize = getImageSize(imgStringThree);
                setImageSizeToList(1, ImageSize);
            }
        }
        if (requestCode == reqcode_img4 && resultCode == RESULT_OK) {

            imgStringFour = setImage(imgMeterHandover);
            imgMeterHandover.setImageBitmap(rotatedBitmap);

            if (!imgStringFour.equalsIgnoreCase("") && imgStringFour != null) {
                mtrHandOvrImage = "y";
                setImageToList(2, imgStringFour);
                setImageIdToList(2, "286");
                String ImageSize = getImageSize(imgStringFour);
                setImageSizeToList(2, ImageSize);
            }
        }
        if (requestCode == reqcode_img5 && resultCode == RESULT_OK) {

            imgStringFive = setImage(imgRestoration);
            imgRestoration.setImageBitmap(rotatedBitmap);

            if (!imgStringFive.equalsIgnoreCase("") && imgStringFive != null) {
                restortationImage = "y";
                setImageToList(3, imgStringFive);
                setImageIdToList(3, "288");

                String ImageSize = getImageSize(imgStringFive);
                setImageSizeToList(3, ImageSize);

            }
        }
        if (requestCode == reqcode_img6 && resultCode == RESULT_OK) {
            try {
                imgStringSix = setImage(imgOther);
                imgOther.setImageBitmap(rotatedBitmap);

                if (!imgStringSix.equalsIgnoreCase("") && imgStringSix != null) {
                    otherImage = "y";
                    setImageToList(4, imgStringSix);
                    setImageIdToList(4, "290");

                    String ImageSize = getImageSize(imgStringSix);
                    setImageSizeToList(4, ImageSize);

                }

            } catch (Exception ex) {
                Log.d("Image7", "" + ex.toString());
            }
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

    private String setImage(ImageView img) {
        String imgString = "";
        imageUri = Uri.fromFile(new File(imageName));
try {
    img.setImageURI(imageUri);
    img.setVisibility(View.VISIBLE);
    img.setPadding(0, 0, 0, 0);
    bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
    Matrix matrix = new Matrix();

    int nh = (int) (bitmap.getHeight() * (newImageHeight / bitmap.getWidth()));
    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) newImageHeight, nh, true);
    // iv_visiting_card_proprietor.setImageBitmap(scaled);
    rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);


    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //====================================
    String texst = lat + "-" + lng + "";


    Canvas cs = new Canvas(rotatedBitmap);
    Paint tPaint = new Paint();
    tPaint.setTextSize(25);//30
    tPaint.setColor(Color.WHITE);
    tPaint.setStyle(Paint.Style.FILL);
    cs.drawBitmap(rotatedBitmap, 0f, 0f, null);
    float height = tPaint.measureText("yY");

    cs.drawText(texst, 10f, height + 10f, tPaint);
    //======================================
    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, baos); //bm is the bitmap object
    byte[] b = baos.toByteArray();
    img.setImageBitmap(rotatedBitmap);
    imgString = Base64.encodeToString(b, Base64.DEFAULT);

}catch (Exception e){
    e.printStackTrace();
}

        return imgString;
    }

    private void enableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            MessageWindow.errorWindow(mCon, "CAMERA permission allows us to Access CAMERA app");
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    MessageWindow.errorWindow(mCon, "Permission Canceled, Now your application cannot access CAMERA.");
                }
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

            File destination = new File(insideDigitalSignatureFolder, empCode + "_" + System.currentTimeMillis() + ".jpg");

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

                    setImageToList(5, imgDigitalSignature);
                    setImageIdToList(5, "298");

                    String ImageSize = getImageSize(imgDigitalSignature);
                    setImageSizeToList(5, ImageSize);

                    // documentImages.add(imgDigitalSignature);
                    //documentSubtype.add("298");

                    dialogSignature.dismiss();
                    buttonName = "digital_signature";

                    //  saveImage(imgDigitalSignature, consumerNo, oldmeterNumStr, meterNumStr, "298", empCode, ip, "digital_signature");

                    if (buttonName.equalsIgnoreCase("digital_signature")) {
                        digitalImage = "y";
                    }

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

    public void dialog_digi_signature() {
        dialogSignature = new Dialog(getContext());
        dialogSignature.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSignature.setContentView(R.layout.digital_signature);
        dialogSignature.setCancelable(true);

        mContent = (LinearLayout) dialogSignature.findViewById(R.id.linearLayout);
        mSignature = new Signature(getContext(), null);
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
                btn_digital_signature.setChecked(false);
            }
        });

        dialogSignature.show();
    }

    private void fetchDetailsFromContractor() {
        if (contList == null || contList.equalsIgnoreCase("")) {
            contList = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTLIST);
        } else {
            contList = getArguments().getString("contList");
        }
        try {
            JSONArray jsonArray = new JSONArray(contList);
            JSONObject jsnobject = new JSONObject();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsnobject = jsonArray.getJSONObject(i);

                MI_METERINSTALLID = jsnobject.getString("MI_METERINSTALLID");
                MI_ACTION = jsnobject.getString("MI_ACTION");
                MI_CONSUMER = jsnobject.getString("MI_CONSUMER");
                MI_BU = jsnobject.getString("MI_BU");
                MI_PC = jsnobject.getString("MI_PC");
                MI_REFNO = jsnobject.getString("MI_REFNO");
                MI_O_SIZE = jsnobject.getString("MI_O_SIZE");
                MI_O_METER = jsnobject.getString("MI_O_METER");
                MI_O_MAKE = jsnobject.getString("MI_O_MAKE");
                MI_O_PREVIOUSREADING = jsnobject.getString("MI_O_PREVIOUSREADING");
                MI_O_FINALREADING = jsnobject.getString("MI_O_FINALREADING");
                MI_O_FINALSTATUS = jsnobject.getString("MI_O_FINALSTATUS");
                MI_O_REASON = jsnobject.getString("MI_O_REASON");
                MI_O_METERTYPE = jsnobject.getString("MI_O_METERTYPE");
                MI_N_MAKE = jsnobject.getString("MI_N_MAKE");
                MI_N_SIZE = jsnobject.getString("MI_N_SIZE");
                MI_N_SEAL = jsnobject.getString("MI_N_SEAL");
                MI_N_METER = jsnobject.getString("MI_N_METER");
                MI_INSTALLATIONDATE = jsnobject.getString("MI_INSTALLATIONDATE");
                MI_N_INITIALREADING = jsnobject.getString("MI_N_INITIALREADING");
                MI_N_METERTYPE = jsnobject.getString("MI_N_METERTYPE");
                MI_N_METERLOCATION = jsnobject.getString("MI_N_METERLOCATION");
                MI_N_ISPROTECTED = jsnobject.getString("MI_N_ISPROTECTED");
                MI_PROPERTYTAXNO = jsnobject.getString("MI_PROPERTYTAXNO");
                MI_N_ISMETERHANDOVER = jsnobject.getString("MI_N_ISMETERHANDOVER");
                MI_CONTRACTOR = jsnobject.getString("MI_CONTRACTOR");
                MI_CONTRACTOREMP = jsnobject.getString("MI_CONTRACTOREMP");
                MI_N_ISMATERIALHANDOVER = jsnobject.getString("MI_N_ISMATERIALHANDOVER");
                MI_PCCBEDDINGLEN = jsnobject.getString("MI_PCCBEDDINGLEN");
                MI_PCCBEDDINGWID = jsnobject.getString("MI_PCCBEDDINGWID");
                MI_PCCBEDDINGDEP = jsnobject.getString("MI_PCCBEDDINGDEP");
                MI_ROADCUTTINGTYPE = jsnobject.getString("MI_ROADCUTTINGTYPE");
                MI_ROADCUTTINGLEN = jsnobject.getString("MI_ROADCUTTINGLEN");
                MI_ROADCUTTINGWID = jsnobject.getString("MI_ROADCUTTINGWID");
                MI_ROADCUTTINGDEP = jsnobject.getString("MI_ROADCUTTINGDEP");
                MI_FROMNODE = jsnobject.getString("MI_FROMNODE");
                MI_TONODE = jsnobject.getString("MI_TONODE");
                MI_REGMOBILE = jsnobject.getString("MI_REGMOBILE");
                MI_ALTMOBILE = jsnobject.getString("MI_ALTMOBILE");
                MI_GIS = jsnobject.getString("MI_GIS");
                MI_DMA = jsnobject.getString("MI_DMA");
                MI_SR = jsnobject.getString("MI_SR");
                MI_MODIFIEDBY = jsnobject.getString("MI_MODIFIEDBY");
                MI_MODIFIEDDATE = jsnobject.getString("MI_MODIFIEDDATE");
                MI_IP = jsnobject.getString("MI_IP");
                MI_AUTHENTICATEDATE = jsnobject.getString("MI_AUTHENTICATEDATE");
                MI_AUTHREJECTBY = jsnobject.getString("MI_AUTHREJECTBY");
                MI_REJECTEDDATE = jsnobject.getString("MI_REJECTEDDATE");
                MI_STATUS = jsnobject.getString("MI_STATUS");
                MI_ISACTIVE = jsnobject.getString("MI_ISACTIVE");
                MI_XMLMATERIAL = jsnobject.getString("MI_XMLMATERIAL");
                MI_XMLCIVIL = jsnobject.getString("MI_XMLCIVIL");
                MI_O_OBSERVATION = jsnobject.getString("MI_O_OBSERVATION");
                MI_SOURCE = jsnobject.getString("MI_SOURCE");
                MI_ISCOMMISSIONED = jsnobject.getString("MI_ISCOMMISSIONED");
                MI_CONTRACTOROTHER = jsnobject.getString("MI_CONTRACTOROTHER");
                MI_CONTRACTOREMPOTHER = jsnobject.getString("MI_CONTRACTOREMPOTHER");
                MI_N_DIGIT = jsnobject.getString("MI_N_DIGIT");
                MSRID = jsnobject.getString("MSRID");
                MI_N_SEAL_MAKE = jsnobject.getString("MI_N_SEAL_MAKE");
                MI_N_METER_BOX_MAKE = jsnobject.getString("MI_N_METER_BOX_MAKE");
                meter_own = jsnobject.getString("MI_N_METEROWNERSHIP");

            }

        } catch (Exception e) {
            Log.e("Excpn NewMetStr", "" + e.getMessage());
        }
    }

    @SuppressLint("SetTextI18n")
    public void displayReceivedData(String makerCodeId, String oldmeterno, String sealNoStr, String installDtStr,
                                    String coonectionLoad, String pastMeterReadingStr, String submitStatus, String radiobuttonValStr,
                                    String consumerNoStr, String zoneStr, String groupStr, String refTypeStr, String connSizeStr,
                                    String property_assessmnt, String fromNodeStr, String toNodeStr, String primaryMobStr,
                                    String alternateMobStr, String gis_bidStr, String dmaId, String srId, String msrId,
                                    String commisioned_noncommisioned, String employee_code, String mac_address, String contList, boolean isSubmitted) {
        property_assessmnt_num = property_assessmnt;
        fromNode = fromNodeStr;
        toNode = toNodeStr;
        primary_mobile = primaryMobStr;
        alt_mobile = alternateMobStr;
        gis_bid_str = gis_bidStr;
        dma_str = dmaId;
        sr_str = srId;
        msr_str = msrId;
        emp_login_str = employee_code;
        ip_str = mac_address;
        isConsumerSubmitted = isSubmitted;
    }

    public void displayOldMeterDetails(String oldmakerCodeId, String oldmeterNoStr, String oldinstallDtStr, String oldmeterSizeStr,
                                       String oldsealNoStr, String pastReadingStr, String oldMtrStsId, String oldmeterTypeId,
                                       String finalReadingStr, String finalStatusStr, String meterObservationId,
                                       String meterReasonId, boolean isSubmitted) {

        oldmakerCodeNumId = oldmakerCodeId;
        oldmeterNumStr = oldmeterNoStr;
        oldinstallDtValStr = oldinstallDtStr;
        oldsealNumStr = oldsealNoStr;
        pastMeterNoRcvdStr = pastReadingStr;
        oldMtrStsNumId = oldMtrStsId;
        oldmeterTypeNumId = oldmeterTypeId;
        finalReadingNumStr = finalReadingStr;
        finalStatusNumStr = finalStatusStr;
        reasonId = meterReasonId;
        meterObservationIdStr = meterObservationId;
        isOldSubmitted = isSubmitted;
    }

    public void displayNewMeterDet(String makerCodeId, String meterNoStr, String installDtStr, String meterSizeId, String sealNoStr
            , String initialReadingStr, String meterTypeId, String meterLocationId, String protectedBoxIdStr
            , String taxNoStr, String meterHandoverIdStr, String contList, String ndial_digit, boolean isSubmitted) {

        makerCodeIdStr = makerCodeId;
        meterNumStr = meterNoStr;
        installDateStr = installDtStr;
        meterSizeIdStr = meterSizeId;
        sealNumStr = sealNoStr;
        initialReadingNoStr = initialReadingStr;
        meterTypeIdStr = meterTypeId;
        meterLocationIdNo = meterLocationId;
        protectedBoxIdNoStr = protectedBoxIdStr;
        taxNumStr = taxNoStr;
        meterHandoverStr = meterHandoverIdStr;
        dial_digit = ndial_digit;
        isNewMeterSubmitted = isSubmitted;
    }

    public void displayMaterialDet(List materiallist, boolean isSubmitted) {
        int a = 1;
//            materialDetailXml=new StringBuilder();
        materialDetailXml.append("<MaterialDetails>");

        for (int i = 0; i <= materiallist.size() - 1; i++) {
            MMGMaterialResponseModel mmgMaterialResponseModel = (MMGMaterialResponseModel) materiallist.get(i);
            String materialNameR = mmgMaterialResponseModel.getMRM_MATERIAL_NAME();
            String materialName = materialNameR.replace("&deg;", "\u00B0");

            materialDetailXml.append("<Details>");
            //materialDetailXml.append("<SLNO>" + mmgMaterialResponseModel.getSLNO() + "</SLNO>");
            materialDetailXml.append("<MRM_MATERIAL_ID>" + mmgMaterialResponseModel.getMRM_MATERIAL_ID() + "</MRM_MATERIAL_ID>");
            materialDetailXml.append("<MRM_MATERIAL_NAME>" + materialName + "</MRM_MATERIAL_NAME>");
            materialDetailXml.append("<DEFAULTQUANTITY>" + mmgMaterialResponseModel.getDEFAULTQUANTITY() + "</DEFAULTQUANTITY>");
            materialDetailXml.append("<UOM_NAME>" + mmgMaterialResponseModel.getUOM_NAME() + "</UOM_NAME>");
            materialDetailXml.append("</Details>");
        }

        materialDetailXml.append("</MaterialDetails>");

        Log.e("materialDetailXml", materialDetailXml.toString());

        isMaterialSubmitted = isSubmitted;
    }

    public void displayCvlMeasurementDet(List cvlMeasurementList, boolean is_submitted) {

        if (cvlMeasurementList == null) {
            cvlMeasurementList = Collections.<String>emptyList();
            Log.e("cvlMeasurementList", String.valueOf(cvlMeasurementList));


        } else {
            Log.e("cvlMeasurementList", cvlMeasurementList.toString());
            //cvlMeasurementXml=new StringBuilder();
            cvlMeasurementXml.append("<MeasurementDetails>");
            for (int i = 0; i <= cvlMeasurementList.size() - 1; i++) {
                MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel = (MMGCvlMeasurementResponseModel) cvlMeasurementList.get(i);
                String mcd_name = mmgCvlMeasurementResponseModel.getMCD_MATERIAL_NAME();
                cvlMeasurementXml.append("<Details>");
                cvlMeasurementXml.append("<SLNO>" + mmgCvlMeasurementResponseModel.getSLNO() + "</SLNO>");
                cvlMeasurementXml.append("<MCD_MATERIAL_ID>" + mmgCvlMeasurementResponseModel.getMCD_MATERIAL_ID() + "</MCD_MATERIAL_ID>");
                cvlMeasurementXml.append("<MCD_MATERIAL_NAME>" + mcd_name.replace("&", "amp;") + "</MCD_MATERIAL_NAME>");
                cvlMeasurementXml.append("<MCD_ISDROPDOWN>" + mmgCvlMeasurementResponseModel.getMCD_ISDROPDOWN() + "</MCD_ISDROPDOWN>");
                cvlMeasurementXml.append("<MCD_ISQUANTITY>" + mmgCvlMeasurementResponseModel.getMCD_ISQUANTITY() + "</MCD_ISQUANTITY>");
                cvlMeasurementXml.append("<DDLID>" + mmgCvlMeasurementResponseModel.getDDLID() + "</DDLID>");
                cvlMeasurementXml.append("<QUANTITY>" + mmgCvlMeasurementResponseModel.getQUANTITY() + "</QUANTITY>");
                cvlMeasurementXml.append("<LENGTH>" + mmgCvlMeasurementResponseModel.getLENGTH() + "</LENGTH>");
                cvlMeasurementXml.append("<WIDTH>" + mmgCvlMeasurementResponseModel.getWIDTH() + "</WIDTH>");
                cvlMeasurementXml.append("<DEPTH>" + mmgCvlMeasurementResponseModel.getDEPTH() + "</DEPTH>");
                cvlMeasurementXml.append("</Details>");

            }
            cvlMeasurementXml.append("</MeasurementDetails>");

        }
        is_civil_submitted = is_submitted;
    }

    public void displayMeterProtectionDet(String pccLengthStr, String pccWidthStr, String pccDepthStr,
                                          String pccTotalStr, String roadCuttingIdStr,
                                          String rdLengthStr, String rdWidthStr, String rdDepthStr,
                                          String rdTotalStr, boolean isSubmitted) {
        pccLengthNumStr = pccLengthStr;
        pccWidthNumStr = pccWidthStr;
        pccDepthNumStr = pccDepthStr;
        pccTotalNumStr = pccTotalStr;
        roadCuttingIdNumStr = roadCuttingIdStr;
        rdLengthNumStr = rdLengthStr;
        rdWidthNumStr = rdWidthStr;
        rdDepthNumStr = rdDepthStr;
        rdTotalNumStr = rdTotalStr;
        isMtrProtectionSubmitted = isSubmitted;
    }

    public void displayContracterDet(String c_id, String c_emp_id, String contractorIdStr, String vendorCodeStr,
                                     String contractorEmpIdStr, String removedHandverIdStr, String otherContractor,
                                     String otherContractorEmp, String other_code_id, boolean isSubmitted) {

        cntrctrNameIdStr = contractorIdStr;//other
        vendorcodeStr = vendorCodeStr; //fixer emp
        cntrctrEmpIdStr = contractorEmpIdStr; //
        materialHandoverStr = removedHandverIdStr;
        contractorId = c_id;
        c_employee_id = c_emp_id;
        otherContractorStr = vendorCodeStr;
        otherContractorEmpStr = otherContractorEmp;
        other_code_idStr = other_code_id;
        isContractorSubmitted = isSubmitted;

    }

    @SuppressLint("StaticFieldLeak")
    private class SendDataToMeterInstallation extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.submitting)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[59];
                paraName[0] = "IsSubmit";
                paraName[1] = "Action";
                paraName[2] = "Consumer";
                paraName[3] = "BU";
                paraName[4] = "PC";
                paraName[5] = "RefNo";
                paraName[6] = "O_Size";
                paraName[7] = "O_Meter";
                paraName[8] = "O_Make";
                paraName[9] = "O_PreviousReading";
                paraName[10] = "O_FinalReading";
                paraName[11] = "O_FinalStatus";
                paraName[12] = "O_StatusObservation";
                paraName[13] = "O_Reason";
                paraName[14] = "O_MeterType";
                paraName[15] = "N_Make";
                paraName[16] = "N_Size";
                paraName[17] = "N_Seal";
                paraName[18] = "N_Meter";
                paraName[19] = "InstallationDate";
                paraName[20] = "N_InitialReading";
                paraName[21] = "N_MeterType";
                paraName[22] = "N_MeterLocation";
                paraName[23] = "N_IsProtected";
                paraName[24] = "PropertyTaxNo";
                paraName[25] = "N_IsMeterHandovertoConsumer";
                paraName[26] = "Contractor";
                paraName[27] = "ContractorEmp";
                paraName[28] = "OtherContractor";
                paraName[29] = "OtherContractorEmp";
                paraName[30] = "N_IsMaterialHandovertoConsumer";
                paraName[31] = "XMLMaterial";
                paraName[32] = "XMLCivil";
                paraName[33] = "PCCBeddingLen";
                paraName[34] = "PCCBeddingWid";
                paraName[35] = "PCCBeddingDep";
                paraName[36] = "RoadCuttingType";
                paraName[37] = "RoadCuttingLen";
                paraName[38] = "RoadCuttingWid";
                paraName[39] = "RoadCuttingDep";
                paraName[40] = "FromNode";
                paraName[41] = "ToNode";
                paraName[42] = "RegMobile";
                paraName[43] = "AltMobile";
                paraName[44] = "GIS";
                paraName[45] = "DMA";
                paraName[46] = "SR";
                paraName[47] = "IsCommissioned";
                paraName[48] = "N_Digit";
                paraName[49] = "Emp_Code";
                paraName[50] = "IP";
                paraName[51] = "MeterInstallId";
                paraName[52] = "SealMake";
                paraName[53] = "MeterBoxMake";
                paraName[54] = "FragmentPosition";
                paraName[55] = "MeterOwnership";//added by rupali
                paraName[56] = "Remarks";
                paraName[57] = "AvgConsumption";
                paraName[58] = "No_OfFlats";
                jsonMeterInstallSaveResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "MeterInstallationSave", params, paraName);
                //Log.e("UploadDocResponse", "UploadDocResponse : "+jsonMeterInstallSaveResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                progress.dismiss();
                String[] enums = gson.fromJson(jsonMeterInstallSaveResponse, String[].class);
                String msgString = "";
                String pdfLink = enums[1];

                if (enums[0].equalsIgnoreCase("Success")) {
                    clearAllSharedPrefs();
                    if (submit_status.equalsIgnoreCase("N")) {
                        msgString = "Meter Installation Record Saved Successfully";
                    } else if (submit_status.equalsIgnoreCase("Y")) {
                        if (radioButtonVal.equalsIgnoreCase("MB")) {
                            msgString = "Meter Box Installation Record Submitted Successfully";
                        } else if (radioButtonVal.equalsIgnoreCase("S")) {
                            msgString = "Seal Installation Record Submitted Successfully";
                        } else {
                            msgString = "Meter Installation Record Submitted Successfully";
                        }
                    }

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Success");
                    alertBuilder.setMessage(msgString);
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            clearAllSharedPrefs();
//                            if (!pdfLink.equalsIgnoreCase("")) {
////                                String format = "https://drive.google.com/viewerng/viewer?embedded=true&url=%s";
////                                String fullPath = String.format(Locale.ENGLISH, format, MIC_URL + pdfLink);
//                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MIC_URL + pdfLink));
//                                //browserIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                startActivity(browserIntent);
//
////                                Intent webview = new Intent(mCon, WebviewActivity.class);
////                                webview.putExtra("pdfUrl", pdfLink);
////                                dialog.cancel();
////                                startActivity(webview);
//                            }
//                            else
//                            {
                            App.backPressMMGFragment = "Y";
                            Intent intent = new Intent(mCon, MainActivity.class);
                            intent.putExtra("Tag", "2");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//pinky added on 07/03/2022 because of backpress issue on mmg
                            startActivity(intent);
                            getActivity().finish();
                            dialog.cancel();
                            //}

                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(
                                    R.color.white));
                        }
                    });
                    alert.show();

                } else if (enums[0].equalsIgnoreCase("Duplicate")) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Alert");
                    alertBuilder.setMessage(getResources().getString(R.string.please_forward_complaint_to_mmg));
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            App.backPressMMGFragment = "Y";
                            Intent intent = new Intent(mCon, MainActivity.class);
                            intent.putExtra("Tag", "2");

                            startActivity(intent);
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Failure");
                    alertBuilder.setMessage(enums[0] + "");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                }

                clearAllSharedPrefs();

            } catch (Exception e) {
                progress.dismiss();
                MessageWindow.errorWindow(mCon, e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Send Data to meter Installation", error);
            }
        }
    }

    private void clearAllSharedPrefs() {
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.DATAFOUND);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERSIZEID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METER_NO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METER_NO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_NAME);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_NO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_SOURCE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.BU);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PC);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMERREFERENCENUMBER);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTACTORNAME);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTACTOREMP);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.VENDORCODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MATERIALHANDOVER);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_MAKERCODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERNUM);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.O_MANUFACTURE_CODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_SEALNO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_INITIALREADING);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERTYPE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERLOCATION);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_PROTECTEDBOX);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_TAXNO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCLEN);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCWIDTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCDEPTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCTOTAL);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGLENGTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGWIDTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGDEPTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGTOTAL);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MATERIALXML);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CIVILMEASUREMENTXML);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_MAKERCODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METERNUM);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDT);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDSEALNUM);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PASTMETERNO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDMTRSTS);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDMETERTYPE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FINALREADING);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FINALSTATUS);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.REASONID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RADIOBUTTONVAL);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERSTATUS);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONNECTIONLOAD);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.SUBMITMATERIALBUTTONTAG);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.SUBMITCVLMEASUREMENTBUTTONTAG);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.SUBMITCIVILLIST);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MAKERCODENAME);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERTYPENAME);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PROPERTY_ASSESSMENT);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FROM_NODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.TO_NODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PRIMARY_MOBILE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.ALTERNATE_MOBILE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.GIS_BID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.SUBMIT_STATUS);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MTR_SIZE_ID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.ALLOCATED_WORK_LIST);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MTR_TYPE_CODE_ID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.VALIDMETER);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTLIST);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MI_METERINSTALLID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.Meterowner);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_AVERAGECONSUMTION);
    }

    public void submitNewDetails() {
        if (radioButtonVal.equalsIgnoreCase("R")) {
            try {
                materialDetailXml.toString().equals("");
                cvlMeasurementXml.toString().equals("");
                newMaterialDetailXml = "";
                newCivilDetailsXml = "";

            } catch (Exception ex) {
            }

        } else {
            newMaterialDetailXml = UtilitySharedPreferences.getPrefs(getActivity(), consumerNo + "material");
            newCivilDetailsXml = UtilitySharedPreferences.getPrefs(getActivity(), consumerNo + "civil");
            Log.e("materialDetailXml", materialDetailXml.toString());
            try {
                displayMaterialDet(list, isMaterialSubmitted);
                displayCvlMeasurementDet(cvllist, is_civil_submitted);

            } catch (Exception ex) {
            }
        }

        String emp_code = null;
        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ip_str = PreferenceUtil.getMac();

        String params[] = new String[59];
        params[0] = submit_status;              //IsSubmit

        if (isConsumerSubmitted) {
            if (radioButtonVal == null) {
                params[1] = null;
            } else {
                params[1] = radioButtonVal;
            }
            if (consumerNo == null) {
                params[2] = null;
            } else {
                params[2] = consumerNo;
            }
            if (BU == null) {
                params[3] = null;
            } else {
                params[3] = BU;
            }
            if (PC == null) {
                params[4] = null;
            } else {
                params[4] = PC;
            }
/*
            params[1] = radioButtonVal;             //Action
            params[2] = consumerNo;                 //Consumer
            params[3] = BU;
            params[4] = PC;
*/
        } else {
            if (radioButtonVal == null) {
                params[1] = null;
            } else {
                params[1] = radioButtonVal;
            }
            if (consumerNo == null) {
                params[2] = null;
            } else {
                params[2] = consumerNo;
            }
            if (BU == null) {
                params[3] = null;
            } else {
                params[3] = BU;
            }
            if (PC == null) {
                params[4] = null;
            } else {
                params[4] = PC;
            }
         /*   params[1] = radioButtonVal;             //Action
            params[2] = consumerNo;                 //Consumer
            params[3] = BU;
            params[4] = PC;*/
        }
        //  params[5] = ConsumerDetFragmentNew.refNoStrStatic;
        if (refNo == null) {
            params[5] = null;
        } else {
            params[5] = refNo;
        }
//        params[5] = refNo;

        if (isOldSubmitted) {
            if (oldmeterSizeNum.equalsIgnoreCase("")) {
                params[6] = null;
            } else {
                params[6] = oldmeterSizeNum;
            }
            if (oldmeterNumStr.equalsIgnoreCase("")) {
                params[7] = null;
            } else {
                params[7] = oldmeterNumStr;
            }
            if (oldmakerCodeNumId == null) {
                params[8] = null;
            } else {
                params[8] = oldmakerCodeNumId;
            }
//            params[6] = oldmeterSizeNum;
//            params[7] = oldmeterNumStr;
//            params[8] = oldmakerCodeNumId;
            if (pastMeterNoRcvdStr == null) {
                params[9] = null;
            } else {
                params[9] = pastMeterNoRcvdStr;
            }
            if (finalReadingNumStr == null) {
                params[10] = null;
            } else {
                params[10] = finalReadingNumStr;
            }
//            params[10] = finalReadingNumStr;
            if (finalStatusNumStr.equalsIgnoreCase("")) {
                params[11] = null;
            } else {
                params[11] = finalStatusNumStr;
            }

//            params[11] = finalStatusNumStr;             //final Status

            if (meterObservationIdStr.equalsIgnoreCase("")) {
                params[12] = null;
            } else {
                params[12] = meterObservationIdStr;
            }
//            params[12] = meterObservationIdStr;
            if (reasonId.equalsIgnoreCase("")) {
                params[13] = "0";
            } else {
                params[13] = reasonId;
            }

//            params[13] = reasonId;

            if (mtrTypeCode.equalsIgnoreCase("")) {
                params[14] = null;
            } else {
                params[14] = mtrTypeCode;
            }

//            params[14] = mtrTypeCode;
        } else {
            if (MI_O_SIZE.equalsIgnoreCase("")) {
                params[6] = null;
            } else {
                params[6] = MI_O_SIZE;
            }
            if (MI_O_METER.equalsIgnoreCase("")) {
                params[7] = null;
            } else {
                params[7] = MI_O_METER;
            }
            if (MI_O_MAKE.equalsIgnoreCase("")) {
                params[8] = null;
            } else {
                params[8] = MI_O_MAKE;
            }
           /* params[6] = MI_O_SIZE;
            params[7] = MI_O_METER;
            params[8] = MI_O_MAKE;*/
            if (MI_O_PREVIOUSREADING.equalsIgnoreCase("")) {
                params[9] = null;
            } else {
                params[9] = MI_O_PREVIOUSREADING;
            }


//            params[9] = MI_O_PREVIOUSREADING;
            if (MI_O_FINALREADING.equalsIgnoreCase("")) {
                params[10] = null;
            } else {
                params[10] = MI_O_FINALREADING;
            }

//            params[10] = MI_O_FINALREADING;
            if (MI_O_FINALSTATUS.equalsIgnoreCase("")) {
                params[11] = null;
            } else {
                params[11] = MI_O_FINALSTATUS;
            }
//            params[11] = MI_O_FINALSTATUS;
            if (MI_O_OBSERVATION == null) {
                params[12] = null;
            } else {
                params[12] = MI_O_OBSERVATION;
            }
//            params[12] = MI_O_OBSERVATION;

            if (MI_O_REASON.equalsIgnoreCase("")) {
                params[13] = "0";
            } else {
                params[13] = MI_O_REASON;
            }

//            params[13] = MI_O_REASON;
            if (MI_O_METERTYPE.equalsIgnoreCase("")) {
                params[14] = null;
            } else {
                params[14] = MI_O_METERTYPE;
            }
//            params[14] = MI_O_METERTYPE;
        }

        if (radioButtonVal.equals("OH")) {
            params[15] = "0";
            params[16] = "0";                     // N_Size
            params[17] = null;                    // N_Seal
            params[18] = null;                    // N_Meter
            params[19] = installDateStr;        // InstallationDate
            params[20] = null;                    // N_InitialReading
            params[21] = "0";                    // N_MeterType
            params[22] = "0";                    // N_MeterLocation
            params[23] = null;                    // N_IsProtected
            if (property_assessmnt_num.equalsIgnoreCase("")){
                params[24] = null;
            }else {
                params[24] = property_assessmnt_num;
            }
            params[25] = null;                    // N_IsMeterHandovertoConsumer
        } else {
            if (isNewMeterSubmitted) {
                if (makerCodeIdStr == null) {
                    params[15] = "0";
                } else {
                    params[15] = makerCodeIdStr;
                }
                if (meterSizeIdStr == null) {
                    params[16] = "0";
                } else {
                    params[16] = meterSizeIdStr;
                }

//                params[16] = meterSizeIdStr;            // N_Size
                if (sealNumStr.equalsIgnoreCase("")) {
                    params[17] = "0";
                } else {
                    params[17] = sealNumStr;
                }

//                params[17] = sealNumStr;                // N_Seal
                if (meterNumStr == null) {
                    params[18] = "0";
                } else {
                    params[18] = meterNumStr;
                }

//                params[18] = meterNumStr;               // N_Meter
                if (installDateStr == null) {
                    params[19] = "0";
                } else {
                    params[19] = installDateStr;
                }

//                params[19] = installDateStr;// InstallationDate

                if (initialReadingNoStr == null) {
                    params[20] = "0";
                } else {
                    params[20] = initialReadingNoStr;
                }

//                params[20] = initialReadingNoStr;       // N_InitialReading

                if (meterTypeIdStr == null) {
                    params[21] = "0";                   // N_MeterType
                } else {
                    params[21] = meterTypeIdStr;
                }
//                params[22] = meterLocationIdNo;// N_MeterLocation
                if (meterLocationIdNo == null) {
                    params[22] = "0";                   // N_MeterType
                } else {
                    params[22] = meterLocationIdNo;
                }
//                params[23] = protectedBoxIdNoStr;       // N_IsProtected
                if (protectedBoxIdNoStr.equalsIgnoreCase("")) {
                    params[23] = null;
                } else {
                    params[23] = protectedBoxIdNoStr;
                }

                if (property_assessmnt_num.equalsIgnoreCase("")) {
                    params[24] = null;
                } else {
                    params[24] = property_assessmnt_num;
                }

//                params[24] = property_assessmnt_num;                 // Property Assessment

                if (radioButtonVal.equals("OH") || radioButtonVal.equals("N")) {
                    params[25] = null;       // N_IsMeterHandovertoConsumer
                } else {
                    params[25] = meterHandoverStr;
                }
            } else {
                if (MI_N_MAKE == null) {
                    params[15] = "0";
                } else {
                    params[15] = MI_N_MAKE;
                }
                if (MI_N_SIZE == null) {
                    params[16] = "0";
                } else {
                    params[16] = MI_N_SIZE;
                }

//                params[16] = meterSizeIdStr;            // N_Size
                if (MI_N_SEAL == null) {
                    params[17] = "0";
                } else {
                    params[17] = MI_N_SEAL;
                }

//                params[17] = sealNumStr;                // N_Seal
                if (MI_N_METER == null) {
                    params[18] = "0";
                } else {
                    params[18] = MI_N_METER;
                }

//                params[18] = meterNumStr;               // N_Meter
                if (MI_INSTALLATIONDATE == null) {
                    params[19] = "0";
                } else {
                    params[19] = MI_INSTALLATIONDATE;
                }

//                params[19] = installDateStr;// InstallationDate

                if (MI_N_INITIALREADING == null) {
                    params[20] = "0";
                } else {
                    params[20] = MI_N_INITIALREADING;
                }

//                params[20] = initialReadingNoStr;       // N_InitialReading

                if (MI_N_METERTYPE == null) {
                    params[21] = "0";                   // N_MeterType
                } else {
                    params[21] = MI_N_METERTYPE;
                }
//                params[22] = meterLocationIdNo;// N_MeterLocation
                if (MI_N_METERLOCATION == null) {
                    params[22] = "0";                   // N_MeterType
                } else {
                    params[22] = MI_N_METERLOCATION;
                }
//                params[23] = protectedBoxIdNoStr;       // N_IsProtected
                if (protectedBoxIdNoStr.equalsIgnoreCase("")) {
                    params[23] = null;
                } else {
                    params[23] = protectedBoxIdNoStr;
                }
             /*   params[15] = MI_N_MAKE;
                params[16] = MI_N_SIZE;
                params[17] = MI_N_SEAL;
                params[18] = MI_N_METER;
                params[19] = MI_INSTALLATIONDATE;
                params[20] = MI_N_INITIALREADING;
                params[21] = MI_N_METERTYPE;
                params[22] = MI_N_METERLOCATION;
                params[23] = MI_N_ISPROTECTED;*/
                params[24] = null;                 // Property Assessment
//                params[25] = MI_N_ISMETERHANDOVER;
                if (MI_N_ISMETERHANDOVER == null) {
                    params[25] = null;
                } else {
                    params[25] = MI_N_ISMETERHANDOVER;
                }
            }
        }

        if (isContractorSubmitted) {
            if (contractorId.equalsIgnoreCase("OTHER") || contractorId.equalsIgnoreCase("") ||
                    cntrctrNameIdStr.equalsIgnoreCase("OTHER")) {
                params[26] = "-99";
            } else {
                if (contractorId == null) {
                    params[26] = null;
                } else {
                    params[26] = contractorId;
                }
//                params[26] = contractorId;          // Contractor id in general case and (-99) in case of OTHER
            }
            if (c_employee_id == null) {
                params[27] = null;
            } else {
                params[27] = c_employee_id;
            }
//            params[27] = c_employee_id;           // ContractorEmp
            if (otherContractorStr == null) {
                params[28] = null;
            } else {
                params[28] = otherContractorStr;
            }
//            params[28] = otherContractorStr;       // Other Code getText of Fixer code
            if (otherContractorEmpStr.equalsIgnoreCase("")) {
                params[29] = null;
            } else {
                params[29] = otherContractorEmpStr;
            }
            // OtherContractor emp getText of Fixer name
//            params[29] = otherContractorEmpStr;       // OtherContractor emp getText of Fixer name
            params[30] = materialHandoverStr;       // OtherContractor
        } else {
            if (MI_CONTRACTOR == null || MI_CONTRACTOR.equalsIgnoreCase("") || MI_CONTRACTOR.equalsIgnoreCase("0")) {
                String mmgFixer = PreferenceUtil.getMMGFixer();
                if (mmgFixer == null) {
                    params[26] = null;
                } else {
                    params[26] = mmgFixer;
                }
//                params[26] = mmgFixer;
            } else {
                if (MI_CONTRACTOR == null) {
                    params[26] = null;
                } else {
                    params[26] = MI_CONTRACTOR;
                }
//                params[26] = MI_CONTRACTOR;
            }
            if (MI_CONTRACTOREMP == null) {
                params[27] = null;
            } else {
                params[27] = MI_CONTRACTOREMP;
            }
            if (MI_CONTRACTOROTHER == null) {
                params[28] = null;
            } else {
                params[28] = MI_CONTRACTOROTHER;
            }
/*            params[27] = MI_CONTRACTOREMP;
            params[28] = MI_CONTRACTOROTHER;*/
            if (MI_CONTRACTOREMPOTHER == null) {
                params[29] = null;
            } else {
                params[29] = MI_CONTRACTOREMPOTHER;
            }

//            params[29] = MI_CONTRACTOREMPOTHER;
            params[30] = MI_N_ISMATERIALHANDOVER;
        }

        if (radioButtonVal.equalsIgnoreCase("R")) {
            params[31] = null;                    // OtherContractorEmp
            params[32] = null;
        } else {
            if (isMaterialSubmitted) {

                if (newMaterialDetailXml == null) {
                    params[31] = null;
                } else {
                    params[31] = newMaterialDetailXml;
                }


//                params[31] = newMaterialDetailXml;
//                params[31] = null;

            } else {
                if (newMaterialDetailXml == null) {
                    params[31] = null;
                } else {
                    params[31] = newMaterialDetailXml;
                }

//                params[31] = newMaterialDetailXml;
            }

            if (is_civil_submitted) {

                if (newCivilDetailsXml == null) {
                    params[32] = null;
                } else {
                    params[32] = newCivilDetailsXml;
                }

//                params[32] = newCivilDetailsXml;
            } else
//                params[32] = newCivilDetailsXml;
                if (newCivilDetailsXml == null) {
                    params[32] = null;
                } else {
                    params[32] = newCivilDetailsXml;
                }
        }

        if (isMtrProtectionSubmitted) {
           /* params[33] = "0";
            params[34] = "0";
            params[35] = "0";
            params[36] = null;
            params[37] = "0";
            params[38] = "0";
            params[39] = "0";
*/
            if (pccLengthNumStr == null) {
                params[33] = "0";                   // N_MeterType
            } else {
                params[33] = pccLengthNumStr;
            }

            if (pccWidthNumStr == null) {
                params[34] = "0";                   // N_MeterType
            } else {
                params[34] = pccWidthNumStr;
            }

            if (pccDepthNumStr == null) {
                params[35] = "0";                   // N_MeterType
            } else {
                params[35] = pccDepthNumStr;
            }

            if (roadCuttingIdNumStr == null) {
                params[36] = null;                   // N_MeterType
            } else {
                params[36] = roadCuttingIdNumStr;
            }

            if (rdLengthNumStr == null) {
                params[37] = "0";                   // N_MeterType
            } else {
                params[37] = rdLengthNumStr;
            }

            if (rdWidthNumStr == null) {
                params[38] = "0";                   // N_MeterType
            } else {
                params[38] = rdWidthNumStr;
            }
            if (rdDepthNumStr == null) {
                params[39] = "0";                   // N_MeterType
            } else {
                params[39] = rdDepthNumStr;
            }


         /*   params[33] = pccLengthNumStr;
            params[34] = pccWidthNumStr;
            params[35] = pccDepthNumStr;
            params[36] = roadCuttingIdNumStr;
            params[37] = rdLengthNumStr;
            params[38] = rdWidthNumStr;
            params[39] = rdDepthNumStr;*/
        } else {
            if (MI_PCCBEDDINGLEN == null) {
                params[33] = "0";                   // N_MeterType
            } else {
                params[33] = MI_PCCBEDDINGLEN;
            }

            if (MI_PCCBEDDINGWID == null) {
                params[34] = "0";                   // N_MeterType
            } else {
                params[34] = MI_PCCBEDDINGWID;
            }

            if (MI_PCCBEDDINGDEP == null) {
                params[35] = "0";                   // N_MeterType
            } else {
                params[35] = MI_PCCBEDDINGDEP;
            }

            if (MI_ROADCUTTINGTYPE == null) {
                params[36] = null;                   // N_MeterType
            } else {
                params[36] = MI_ROADCUTTINGTYPE;
            }

            if (MI_ROADCUTTINGLEN == null) {
                params[37] = "0";                   // N_MeterType
            } else {
                params[37] = MI_ROADCUTTINGLEN;
            }

            if (MI_ROADCUTTINGWID == null) {
                params[38] = "0";                   // N_MeterType
            } else {
                params[38] = MI_ROADCUTTINGWID;
            }
            if (MI_ROADCUTTINGDEP == null) {
                params[39] = "0";                   // N_MeterType
            } else {
                params[39] = MI_ROADCUTTINGDEP;
            }


        /*    params[35] = "0";
            params[36] = null;
            params[37] = "0";
            params[38] = "0";
            params[39] = "0";
    */       /* params[33] = MI_PCCBEDDINGLEN;
            params[34] = MI_PCCBEDDINGWID;
            params[35] = MI_PCCBEDDINGDEP;
            params[36] = MI_ROADCUTTINGTYPE;
            params[37] = MI_ROADCUTTINGLEN;
            params[38] = MI_ROADCUTTINGWID;
            params[39] = MI_ROADCUTTINGDEP;*/
        }

        if (isConsumerSubmitted) {
/*
            params[40] = fromNode;
            params[41] = toNode;
            params[42] = primary_mobile;
            params[43] = alt_mobile;
            params[44] = gis_bid_str;
            params[45] = dma_str;
            params[46] = sr_str;
*/

            if (fromNode.equalsIgnoreCase("")) {
                params[40] = null;                   // N_MeterType
            } else {
                params[40] = fromNode;
            }

//            params[40] = MI_FROMNODE;
            if (toNode.equalsIgnoreCase("")) {
                params[41] = null;                   // N_MeterType
            } else {
                params[41] = toNode;
            }
            if (primary_mobile.equalsIgnoreCase("")) {
                params[42] = null;                   // N_MeterType
            } else {
                params[42] = primary_mobile;
            }


//            params[41] = MI_TONODE;
//            params[42] = primary_mobile;
//            params[43] = MI_ALTMOBILE;
            if (alt_mobile.equalsIgnoreCase("")) {
                params[43] = null;                   // N_MeterType
            } else {
                params[43] = alt_mobile;
            }
            if (gis_bid_str.equalsIgnoreCase("")) {
                params[44] = null;                   // N_MeterType
            } else {
                params[44] = gis_bid_str;
            }
//            params[44] = MI_GIS;

            if (dma_str == null) {
                params[45] = null;                   // N_MeterType
            } else {
                params[45] = dma_str;
            }
//            params[45] = MI_DMA;

            if (sr_str.equalsIgnoreCase("")) {
                params[46] = null;                   // N_MeterType
            } else {
                params[46] = sr_str;
            }

        } else {
            if (MI_FROMNODE == null) {
                params[40] = null;                   // N_MeterType
            } else {
                params[40] = MI_FROMNODE;
            }

//            params[40] = MI_FROMNODE;
            if (MI_TONODE == null) {
                params[41] = null;                   // N_MeterType
            } else {
                params[41] = MI_TONODE;
            }

//            params[41] = MI_TONODE;
            params[42] = MI_REGMOBILE;
//            params[43] = MI_ALTMOBILE;
            if (MI_ALTMOBILE == null) {
                params[43] = null;                   // N_MeterType
            } else {
                params[43] = MI_ALTMOBILE;
            }
            if (MI_GIS == null) {
                params[44] = null;                   // N_MeterType
            } else {
                params[44] = MI_GIS;
            }
//            params[44] = MI_GIS;

            if (MI_DMA == null) {
                params[45] = null;                   // N_MeterType
            } else {
                params[45] = MI_DMA;
            }
//            params[45] = MI_DMA;

            if (MI_SR == null) {
                params[46] = null;                   // N_MeterType
            } else {
                params[46] = MI_SR;
            }

//            params[46] = MI_SR;
        }

        if (commisioned_noncommissioned_str == null) {
            params[47] = null;                   // N_MeterType
        } else {
            params[47] = commisioned_noncommissioned_str;
        }
        if (dial_digit == null) {
            params[48] = null;                   // N_MeterType
        } else {
            params[48] = dial_digit;
        }
        if (emp_code == null) {
            params[49] = null;                   // N_MeterType
        } else {
            params[49] = emp_code;
        }
        if (ip_str == null) {
            params[50] = null;                   // N_MeterType
        } else {
            params[50] = ip_str;
        }
        /*params[47] = commisioned_noncommissioned_str;
        params[48] = dial_digit;
        params[49] = emp_code;
        params[50] = ip_str;*/
        //params[51] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_METERINSTALLID);                    // Query String
        if (MI_METERINSTALLID.equalsIgnoreCase("")) {
            params[51] = "0";                   // N_MeterType
        } else {
            params[51] = MI_METERINSTALLID;
        }

//        params[51] = MI_METERINSTALLID; // Query String
        if (NewMeterDetFragment.sealmakeId.equalsIgnoreCase("")) {
            params[52] = "0";                   // N_MeterType
        } else {
            params[52] =NewMeterDetFragment.sealmakeId;
        }

//        params[52] = NewMeterDetFragment.sealmakeId; //pinky changed this to sealMakeIdStr 21/01/2022
        if (NewMeterDetFragment.meterboxmakeId.equalsIgnoreCase("")) {
            params[53] = "0";                   // N_MeterType
        } else {
            params[53] = NewMeterDetFragment.meterboxmakeId;
        }

//        params[53] = NewMeterDetFragment.meterboxmakeId; //pinky changed this to meterBoxMakeIdStr 21/01/2022
        params[54] = "AF";
        String metrown = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_N_METEROWNERSHIP);

        if (metrown == null) {
            params[55] = null;                   // N_MeterType
        } else {
            params[55] = metrown;
        }
//        params[55] = metrown;//Rupali

        if (remarkStr == null) {
            params[56] = null;                   // N_MeterType
        } else {
            params[56] = remarkStr;
        }
//        params[56] = remarkStr;
        if (newavrageConsumtion.equalsIgnoreCase("")) {
            params[57] = null;                   // N_MeterType
        } else {
            params[57] = newavrageConsumtion;
        }
//        params[57] = newavrageConsumtion;
        if (UtilitySharedPreferences.getPrefs(getActivity(),Constants.No_ofFlat).equalsIgnoreCase("")) {
            params[58] = null;                   // N_MeterType
        } else {
            params[58] = UtilitySharedPreferences.getPrefs(getActivity(),Constants.No_ofFlat);
        }
//        params[58] = UtilitySharedPreferences.getPrefs(getActivity(),Constants.No_ofFlat);

        SendDataToMeterInstallation sendDataToMeterInstallation = new SendDataToMeterInstallation();
        sendDataToMeterInstallation.execute(params);
        Log.e("AuthParam", Arrays.toString(params));
    }

    private void uploadErrorLog(String activity_name, String event_name, String jsonResponse) {

        RealmOperations realmOperations = new RealmOperations(getActivity());
        Number errorIdNum = realmOperations.getErrorIDCount();

        int errorNextId;
        if (errorIdNum == null) {
            errorNextId = 1;
        } else {
            errorNextId = errorIdNum.intValue() + 1;
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        String timeStamp = simpleDateFormat.format(new Date());
        String app_version = UtilitySharedPreferences.getPrefs(getActivity(), AppConstant.APPVERSION);
        try {
            app_version =aes.decrypt( app_version);
        } catch (Exception e) {
            e.printStackTrace();
        }

        selectErrorData(errorNextId, timeStamp, app_version, activity_name, event_name, jsonResponse);
    }

    private void selectErrorData(int errorNextId, String timeStamp, String app_version, String activity_name, String event_name, String error) {
        if (connection.isConnectingToInternet()) {

            String empcode = null;
            try {
                // Decrypt EmpCode
                empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONArray jArray = new JSONArray();
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("USERID", empcode);
                jsonObj.put("IMEI", PreferenceUtil.getImei());
                jsonObj.put("MODEL", Build.MODEL);
                jsonObj.put("DNAME", Build.MANUFACTURER);
                jsonObj.put("VER", Build.VERSION.SDK_INT);
                jsonObj.put("ACTIVITY", activity_name);
                jsonObj.put("EVENT", event_name);
                jsonObj.put("ERROR", error);
                jsonObj.put("EDATE", timeStamp);
                jsonObj.put("AV", app_version);
                jsonObj.put("AI", "2");
                jsonObj.put("ID", errorNextId);

                jArray.put(jsonObj);
            } catch (JSONException e) {
                e.printStackTrace();
                MessageWindow.errorWindow(mCon, e.getMessage());
            }

            String params[] = new String[1];
            params[0] = String.valueOf(jArray);

            UploadErrorLog uploadErrorLog = new UploadErrorLog();
            uploadErrorLog.execute(params);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UploadErrorLog extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[1];
                paraName[0] = "ERRORDATA";
                jsonErrorResponce = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "ErrorLog", params, paraName);
            } catch (Exception e) {
            }
            return null;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(Void result) {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        exisitinImage = "";
        newMetrBoxImage = "";
        mtrHandOvrImage = "";
        restortationImage = "";
        digitalImage = "";
        submitBtnClicked = false;
    }

    public String convertDefaultImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mjp);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;
    }

    //no use below all methos...
    private void createRandomOTP() {
        Number = new Random();
        Rnumber = Number.nextInt(1000);
        String otp = Integer.toString(Rnumber);

        String one = String.valueOf(otp.charAt(0));
        String two = String.valueOf(otp.charAt(1));
        String three = String.valueOf(otp.charAt(2));
        String four = String.valueOf(otp.charAt(3));

        tv_send_mobile.setText(getResources().getText(R.string.please_type_the_verification_code_sent_to_n_9xxxxxxx19) + " " + mobile);
        et_one.setText(one);
        et_two.setText(two);
        et_three.setText(three);
        et_four.setText(four);
        confirmOTP = one + two + three + four;
        ll_otp_authentication.setVisibility(View.GONE);
        phone_number_edt.getText().clear();
        ll_otp_verification.setVisibility(View.VISIBLE);

        try {

            String empcode = null;
            try {
                // Decrypt EmpCode
                empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String params[] = new String[6];

            params[0] = msgTypeID;
            params[1] = confirmOTP;
            params[2] = mobile;
            params[3] = empcode;
            params[4] = consumerNo;
            params[5] = consName;

            if (connection.isConnectingToInternet()) {
                sendOTP sendOTP = new sendOTP();
                sendOTP.execute(params);
            } else {
                MessageWindow.messageWindow(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), "Alert");
            }
        } catch (Exception e) {

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class sendOTP extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(getActivity())
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
                String paraMeterName[] = new String[6];

                paraMeterName[0] = "msgTypeID";
                paraMeterName[1] = "smsValue";
                paraMeterName[2] = "MobileNo";
                paraMeterName[3] = "UserName";
                paraMeterName[4] = "ConsNo";
                paraMeterName[5] = "consName";

                jsonSMSResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "MobileAppSMS", params, paraMeterName);
            } catch (Exception e) {
                flag = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            try {
                if (flag) {
                    // setRequestDialog.cancel();
                    Toast.makeText(getActivity(), "OTP is not sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "OTP is sent successfully.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Get_CustomerDetailsTask", error);
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private boolean checkValidation() {
        mobile = phone_number_edt.getText().toString().trim();
        if (mobile.equals("")) {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.enter_mobile_number),"Alert");
            phone_number_edt.requestFocus();
//            et_phone.setCursorVisible(true);
//            root_frame.setAlpha(1f);
//            fab_progress_circle.hide();
            return false;
        } else if (!mobile.equals("")) {
            int length = mobile.length();
            if (length != 10) {
                MessageWindow.messageWindow(mCon,getResources().getString(R.string.invalid_mobile_no),"Alert");

                phone_number_edt.requestFocus();
//                et_phone.setCursorVisible(true);
//                root_frame.setAlpha(1f);
//                fab_progress_circle.hide();
                return false;
            }
        }
        return true;
    }

    private void authencticate_through_otp() {

        builder_through_otp.setMessage("Wish To Authencticate through OTP?")
                .setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                })
                .setNegativeButton("Resend OTP", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder_through_otp.create();
        alert.setTitle("Authencticate");
        alert.show();
    }


    public void onBackPressed()
    {
        Toast.makeText(getActivity(),"HelGETlo Javatpoint",Toast.LENGTH_SHORT).show();

        //Pop Fragments off backstack and do your other checks
    }



    //=========================

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
                    androidx.appcompat.app.AlertDialog.Builder alertBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Location permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    });
                    androidx.appcompat.app.AlertDialog alert = alertBuilder.create();
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
    public void onConnected(@Nullable Bundle bundle) {
        if (mCurrentLocation == null) {
            if (ContextCompat.checkSelfPermission(mCon, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
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




    private void locationService() {

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait while fetching data from GPS .......");
            progressDialog.setCancelable(false);
            progressDialog.show();


            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            final android.location.LocationListener locationListener = new NCUploadDocDetailsFragment.MyLocationListener();
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
            //checkGpsStatus();
            Toast.makeText(getContext(), "GPS off", Toast.LENGTH_SHORT).show();
        }
    }

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
    }
}
