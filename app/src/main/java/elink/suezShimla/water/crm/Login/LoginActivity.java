package elink.suezShimla.water.crm.Login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
/*import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;*/

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ChangePassword.ChangePasswordFragment;
import elink.suezShimla.water.crm.ChangePassword.Model.ChangePinModel;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.BilllingAdjustmentdataDownloadModel;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadAdjustmentType;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadAdjustmentTypeTable;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadMeterStatusBillAdjust;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadMeterStatusBillAdjustTable;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.BankMasterModel;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.ConnectionStatusModel;
import elink.suezShimla.water.crm.SharedPrefManager;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_IssuetoMeterFixer_MasterDataModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MDialDigitModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.ActionFormModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.ComplaintByModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.DMAModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCgRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGContEmpModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCvlMeasurementResponseModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGFcRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMakerCodeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMaterialDetailsModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterLocationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterSizeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterStatusModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGObersvationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRampAndRRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRequestTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSaddleAndPitExcavModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGTypeOfRoadcuttingModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGVendorDetModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGWallBoringModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGZoneModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MSRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MStatusObservationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterObservationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterStatusModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.SRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.WorkCompObservationModel;
import elink.suezShimla.water.crm.Login.MasterData.Download.DocSource;
import elink.suezShimla.water.crm.Login.MasterData.Download.DocSubType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DocType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadActionCRM;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintBy;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintSource;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintSubType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadConnCategory;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadConnSize;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadCustomerType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadFinishAction;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSiteEngineer;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSourceType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSubZone;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadZone;
import elink.suezShimla.water.crm.Login.MasterData.Download.WorkCompObservation;
import elink.suezShimla.water.crm.Login.MasterData.MasterDataModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSourceModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.DocSourceModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.DocSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.DocTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.FinishActionModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SiteEngineerModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Register.AuthenticateRegistrationActivity;
import elink.suezShimla.water.crm.Register.RegisterActivity;
import elink.suezShimla.water.crm.SelectLanguage.ChangeLanguageUtil;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static elink.suezShimla.water.crm.Utils.Constants.GetRights;

public class LoginActivity extends AppCompatActivity implements
         View.OnFocusChangeListener, View.OnKeyListener, TextWatcher, View.OnClickListener {

    private TextInputLayout employeeIdInputLayout;
    private Context mCon;
    private TextInputEditText mPinHiddenEditText;
    private EditText employeeIdEditText;
    private TextView passwordErrorTextView;
    private String employeeIdStr, pinOneStr, pinTwoStr, pinThreeStr, pinFourStr, password, imei, mac,
            requestEmpCodeStr, versionName = "";
    private LinearLayout fingerPrintLinearLayout;
    private int isValid;
    private EditText pinOneEditText, pinTwoEditText, pinThreeEditText, pinFourEditText;

    // bottom dialog
    private BottomSheetDialog forgotPinDialog;
    private TextInputLayout empIdInputLayout;
    private TextInputEditText empIdEditText;

    private MaterialDialog progress, masterProgress, rightsProgress, mmgMasterProgress;
    private String jsonResponse = "", jsonResponseBillAdjustment = "", jsonMasterDataResponse = "", jsonRightsResponse = "", jsonErrorResponse = "", jsonValidateUser = "",
            JsonMMGMasterData = "", language;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;
    private boolean isInitialCall = true;
  //  private FingerPrintAuthHelper mFingerPrintAuthHelper;
    private SiteEngineerModel siteEngineerModel = new SiteEngineerModel();
    private String lastLogin = "", empCode = "", ap_ver = "", empMasterDataCode = "";
    // Finger Print

    private static final String MYTAG = "";
    String fcmToken = "";
    Fragment fragment = new ChangePasswordFragment();
    String lASTLOGIN = "",jwt="", EMPCODE = "", AppVersion = "", ERMAIL = "", APP_ISLOGGED = "",
            DESIGNATION = "", MMGFIXER = "", DEPARTMENTID = "",STARTTIME="",ENDTIME="",Sessionid="",DEPARTMENTrights="";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;
    String EMPCODEEncrypted = "",sessionid="",PasswordEncrypted="",DepartmentIdEncrypted="",DesignationEncrypted="";
    public static byte[] encToken,encAppVersion,encEmail,encEmpCode;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        realmOperations = new RealmOperations(mCon);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
         /*jwt = Jwts.builder().claim("emailId","test123@gmail.com").claim("emailIdOTP", "123456")
                .claim("phoneNo", "1111111111")
                .signWith(SignatureAlgorithm.HS256, "secret".getBytes())
                .compact();
        Log.v("JWT : - ",jwt);*/
        //new code here
        try {
            if (!realmOperations.checkDeviceIdData().get(0).equalsIgnoreCase("") || realmOperations.checkDeviceIdData().get(0) != null) {
                imei = realmOperations.checkDeviceIdData().get(0);
            }

        } catch (Exception e) {
            imei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        try {
            // AES Algorithm for encryption / decryption


            SharedPrefManager manager = new SharedPrefManager(getApplicationContext());

             sessionid = manager.getsessionid();

            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            random = new SecureRandom();
            random.nextBytes(IV);

            aes=new AesAlgorithm();

        } catch (Exception e) {
            e.printStackTrace();
        }

        mac = getIntent().getStringExtra("MACAddress");
        versionName = getIntent().getStringExtra("versionName");

        PreferenceUtil.setMac(mac);
        PreferenceUtil.setImei(imei);

        TextView registerLink = findViewById(R.id.registerLink);
        TextView forgotPinTextView = findViewById(R.id.forgotPinTextView);
        TextView textVersionName = findViewById(R.id.textVersionName);

        MaterialButton loginButton = findViewById(R.id.loginButton);
        AppCompatSpinner languageSpinner = findViewById(R.id.languageSpinner);

        employeeIdInputLayout = findViewById(R.id.employeeIdInputLayout);
        employeeIdEditText = findViewById(R.id.employeeIdEditText);
        pinOneEditText = findViewById(R.id.pinOneEditText);
        pinTwoEditText = findViewById(R.id.pinTwoEditText);
        pinThreeEditText = findViewById(R.id.pinThreeEditText);
        pinFourEditText = findViewById(R.id.pinFourEditText);
        passwordErrorTextView = findViewById(R.id.passwordErrorTextView);
        fingerPrintLinearLayout = findViewById(R.id.fingerPrintLinearLayout);
        mPinHiddenEditText = findViewById(R.id.pin_hidden_edittext);
        forgotPinDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));

        registerLink.setOnClickListener(this);
        forgotPinTextView.setOnClickListener(this);


        lastLogin = PreferenceUtil.getLastLogin();
        if (lastLogin == null || lastLogin.equals("")) {
            fingerPrintLinearLayout.setVisibility(View.GONE);
        } else {
            fingerPrintLinearLayout.setVisibility(View.VISIBLE);
        }

        try {
            // Decrypt EmpCode
            empCode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //String a= siteEngineerModel.getEMPLOYEE_CODE();
        // forgetPin();

        // Language Code
        if (PreferenceUtil.getString() != null && PreferenceUtil.getString().equals(PreferenceUtil.LANGUAGE_TAM)) {
            languageSpinner.setSelection(1);
        } else {
            languageSpinner.setSelection(0);
        }

        //siteEngineerModel=realmOperations.fetchDataReadingByImei(imei);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!isInitialCall) {
                    switch (position) {
                        case 0:
                            language = PreferenceUtil.LANGUAGE_ENG;
                            PreferenceUtil.putString(mCon, PreferenceUtil.LANGUAGE_ENG);
                            break;
                        case 1:
                            language = PreferenceUtil.LANGUAGE_TAM;
                            PreferenceUtil.putString(mCon, PreferenceUtil.LANGUAGE_TAM);
                            break;
                    }
                    try {
                        //---- your language
                        ChangeLanguageUtil.yourLanguage(mCon, language);
                        Intent i = new Intent(mCon, LoginActivity.class);
                        i.putExtra("IMEINumber", imei);
                        i.putExtra("MACAddress", mac);
                        startActivity(i);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isInitialCall = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setPINListeners();
        // FingerPrint
      /*  try {
            mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);


        } catch (Exception e) {
            Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
            fingerPrintLinearLayout.setVisibility(View.GONE);
        }
*/
        String emp_code = null;
        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (emp_code != null) {
            employeeIdEditText.setText(emp_code);
        }

        pinFourEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    hideSoftKeyboard();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                employeeIdStr = employeeIdEditText.getText().toString().trim();
                pinOneStr = pinOneEditText.getText().toString().trim();
                pinTwoStr = pinTwoEditText.getText().toString().trim();
                pinThreeStr = pinThreeEditText.getText().toString().trim();
                pinFourStr = pinFourEditText.getText().toString().trim();

                password = pinOneStr + pinTwoStr + pinThreeStr + pinFourStr;

                validate();
            }
        });

        SiteEngineerModel isExist = realmOperations.getSiteEngineerExist();
        // FingerPrint Code
        try {
            if (isExist != null) {
                fingerPrintLinearLayout.setVisibility(View.VISIBLE);
                //mFingerPrintAuthHelper.startAuth();
            }
        } catch (Exception e) {
            Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        forgetPin();

        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = pinfo.versionName;
        textVersionName.setText(getResources().getString(R.string.version) + versionName);
        TextView textview_imei = findViewById(R.id.textview_imei);
        textview_imei.setText(getResources().getString(R.string.unique_id) + imei);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                fcmToken = instanceIdResult.getToken();
                // Log.e("token", fcmToken);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.FCMTOKEN);
                try {
                    //set encrypted value
                    String fcmTokenEncrypted = aes.encrypt(fcmToken);
                    UtilitySharedPreferences.setPrefs(mCon, Constants.FCMTOKEN, fcmTokenEncrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra("IMEINumber", imei);
        intent.putExtra("MACAddress", mac);
        intent.putExtra("versionName", versionName);
        startActivity(intent);
        finish();
    }

    private void masterDataDownload() {

        ComplaintTypeModel complaintTypeModelExist = realmOperations.getComplaintTypeExist();
        ComplaintSubTypeModel complaintSubTypeModelExist = realmOperations.getComplaintSubTypeExist();
        ComplaintSourceModel complaintSourceModelExist = realmOperations.getComplaintSourceExist();
        ZoneModel zoneModelExist = realmOperations.getZoneExist();
        SubZoneModel subZoneModelExist = realmOperations.getSubZoneExist();
        SiteEngineerModel siteEngineerModelExist = realmOperations.getSiteEngineerExist();
        FinishActionModel finishActionModelExist = realmOperations.getFinishActionExist();
        ActionFormModel actionFormModelExist = realmOperations.getActionFormExist();
        DocSourceModel docSourceModelExist = realmOperations.getDocSourcenModelExist();
        DocTypeModel docTypeModeExist = realmOperations.getDocTypeModelExist();
        DocSubTypeModel docSubTypeModelModelExist = realmOperations.getDocSubTypeModelExist();
        MeterStatusModel meterStatusModel = realmOperations.getMeterStatusModelExist();
        MeterObservationModel meterObservationModel = realmOperations.getMeterObservationModelExist();

        if (complaintTypeModelExist == null && complaintSubTypeModelExist == null && complaintSourceModelExist == null && zoneModelExist == null
                && subZoneModelExist == null && siteEngineerModelExist == null && actionFormModelExist == null && docSourceModelExist == null
                && docTypeModeExist == null && docSubTypeModelModelExist == null && meterStatusModel == null && meterObservationModel == null) {

            String empcode = null;
            try {
                // Decrypt EmpCode
                empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String[] params = new String[2];
            params[0] = empcode;
            params[1] = Sessionid;
            //  params[0] = "001";
            if (connection.isConnectingToInternet()) {

                DownloadMaster downloadMaster = new DownloadMaster();
                downloadMaster.execute(params);

              /*  masterProgress = new MaterialDialog.Builder(mCon)
                        .content(R.string.loading)
                        .progress(true, 0)
                        .autoDismiss(false)
                        .canceledOnTouchOutside(false)
                        .widgetColorRes(R.color.colorPrimary)
                        .show();*/

            } else {
                Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }

        } else {
            getBilllingAdjustmentdataDownload();
        }


    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        switch (id) {
            case R.id.pinOneEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pinTwoEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pinThreeEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pinFourEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            switch (id) {
                case R.id.pin_hidden_edittext:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (mPinHiddenEditText.getText().length() == 4)
                            pinFourEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 3)
                            pinThreeEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 2)
                            pinTwoEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 1)
                            pinOneEditText.setText("");

                        if (mPinHiddenEditText.length() > 0)
                            mPinHiddenEditText.setText(mPinHiddenEditText.getText().subSequence(0, mPinHiddenEditText.length() - 1));

                        return true;
                    }

                    break;

                default:
                    return false;
            }
        }

        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0) {
            pinOneEditText.setText("");
        } else if (s.length() == 1) {
            pinOneEditText.setText(s.charAt(0) + "");
            pinTwoEditText.setText("");
            pinThreeEditText.setText("");
            pinFourEditText.setText("");
        } else if (s.length() == 2) {
            pinTwoEditText.setText(s.charAt(1) + "");
            pinThreeEditText.setText("");
            pinFourEditText.setText("");
        } else if (s.length() == 3) {
            pinThreeEditText.setText(s.charAt(2) + "");
            pinFourEditText.setText("");
        } else if (s.length() == 4) {
            pinFourEditText.setText(s.charAt(3) + "");
            hideSoftKeyboard(pinFourEditText);
        }
    }

    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void setPINListeners() {
        mPinHiddenEditText.addTextChangedListener(this);

        pinOneEditText.setOnFocusChangeListener(this);
        pinTwoEditText.setOnFocusChangeListener(this);
        pinThreeEditText.setOnFocusChangeListener(this);
        pinFourEditText.setOnFocusChangeListener(this);

        pinOneEditText.setOnKeyListener(this);
        pinTwoEditText.setOnKeyListener(this);
        pinThreeEditText.setOnKeyListener(this);
        pinFourEditText.setOnKeyListener(this);
        mPinHiddenEditText.setOnKeyListener(this);
    }

    public void showSoftKeyboard(TextInputEditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    public static void setFocus(TextInputEditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // mFingerPrintAuthHelper.stopAuth();
        //realmOperations.close();
        ((App) this.getApplication()).startActivityTransitionTimer();

        screenActive();
        //PreferenceUtil.clearAll();
    }

    private void screenActive() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean screenOn;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            screenOn = pm.isInteractive();
        } else {
            screenOn = pm.isScreenOn();
        }
        if (!screenOn) {    //Screen off by lock or power
            Intent checkingIntent = new Intent(this, SplashScreen.class);
            checkingIntent.putExtra("checking", true);
            checkingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(checkingIntent);
            finish();
        }
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) mCon.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        }
    }

    @SuppressLint("NewApi")
    private void validate() {
        boolean isValidEmployeeId = false, isValidPassword = false;

        if (TextUtils.isEmpty(employeeIdStr)) {
            employeeIdInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            employeeIdEditText.setBackground(getDrawable(R.drawable.edittext_background));
        } else {
            employeeIdInputLayout.setError(null);
            isValidEmployeeId = true;
        }

        if (TextUtils.isEmpty(pinOneStr) && TextUtils.isEmpty(pinTwoStr) && TextUtils.isEmpty(pinThreeStr) && TextUtils.isEmpty(pinFourStr)) {
            passwordErrorTextView.setVisibility(View.VISIBLE);
            passwordErrorTextView.setText(getResources().getString(R.string.enter_4_digit_pin));
        } else {
            passwordErrorTextView.setVisibility(View.INVISIBLE);
            isValidPassword = true;
        }

        if (isValidEmployeeId && isValidPassword) {
            String params[] = new String[7];

            params[0] = employeeIdStr;
            params[1] = password;
            params[2] = imei;
            params[3] = mac;
            params[4] = "1";   //deviceID
            params[5] = "";
            params[6] = fcmToken;

            if (connection.isConnectingToInternet()) {
                LoginUser loginUser = new LoginUser();
                loginUser.execute(params);
            } else {
                Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // FingerPrint Methods
  /*  @Override
    public void onNoFingerPrintHardwareFound() {
        fingerPrintLinearLayout.setVisibility(View.GONE);
        //  Toast.makeText(mCon, "Your Device does not have a Fingerprint Sensor", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNoFingerPrintRegistered() {
        Toast.makeText(mCon, "Register at least one fingerprint in Settings", Toast.LENGTH_LONG).show();
        //  mFingerPrintAuthHelper.startAuth();
    }

    @Override
    public void onBelowMarshmallow() {
        fingerPrintLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        employeeIdStr = employeeIdEditText.getText().toString().trim();
        boolean isValidEmpCode = false;

        String emp_code = null;
        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(employeeIdStr)) {
            employeeIdInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            return;
        }
        //getUserName
        else if (emp_code.equals(employeeIdStr)) {
            employeeIdInputLayout.setError(null);
            isValidEmpCode = true;
        } else {
            employeeIdInputLayout.setError(getResources().getString(R.string.emp_code_not_match));
            mFingerPrintAuthHelper.startAuth();
            return;
        }

        if (connection.isConnectingToInternet()) {
            ChangePinModel isExist = realmOperations.isemployeeExist();
            if (isExist == null) {
                MessageWindow.throwOutFromWindow(mCon, "deviceAuthorizedDialog", "Error", SplashScreen.class);
                return;
            } else {
                String restoredText = null;
                try {
                    // Decrypt EmpCode
                    restoredText = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ChangePinModel empDetails;
                empDetails = realmOperations.fetchDataReadingByEmpCode(PreferenceUtil.getEmployeeCode());

                JSONArray jArray = new JSONArray();
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put("USERID", restoredText);
                    jsonObj.put("IMEI", empDetails.getDeviceImei());
                    jsonObj.put("MAC", empDetails.getMacAddress());
                    jsonObj.put("MODEL", Build.MODEL + " " + Build.MANUFACTURER);
                    jsonObj.put("DI", fcmToken);
                    jsonObj.put("OSV ", Build.VERSION.RELEASE);

                    jArray.put(jsonObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String validateUserParams[] = new String[1];
                validateUserParams[0] = String.valueOf(jArray);

                ValidateUser validateUser = new ValidateUser();
                validateUser.execute(validateUserParams);

                mFingerPrintAuthHelper.startAuth();

            }
        } else {
            MessageWindow.messageWindow(mCon, getResources().getString(R.string.no_internet_connection), "Login");
        }
        // validate();
        //   Toast.makeText(mCon, "Finger touch", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                //Cannot recognize the fingerprint scanned.
                Toast.makeText(mCon, R.string.fingerprint_does_not_recognized, Toast.LENGTH_LONG).show();
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //This is not recoverable error. Try other options for user authentication. like pin, password.
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                //Any recoverable error. Display message to the user.
                break;
        }
    }*/

    // Forget mPin Bottom Dialog Method
    private void forgetPin() {
        View sheetView = Objects.requireNonNull(getLayoutInflater()
                .inflate(R.layout.forget_password_dialouge, null));
        forgotPinDialog.setContentView(sheetView);
        forgotPinDialog.setCancelable(false);

        AppCompatImageView closeImageView = sheetView.findViewById(R.id.closeImageView);
        empIdInputLayout = sheetView.findViewById(R.id.empIdInputLayout);
        empIdEditText = sheetView.findViewById(R.id.empIdEditText);
        MaterialButton requestPinButton = sheetView.findViewById(R.id.requestPinButton);

        requestPinButton.setOnClickListener(this);
        closeImageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.registerLink:
                startRegisterActivity();
                break;

            case R.id.requestPinButton:
                requestEmpCodeStr = empIdEditText.getText().toString().trim();

                boolean isValidEmp = false;

                if (TextUtils.isEmpty(requestEmpCodeStr)) {
                    empIdInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                } else {
                    empIdInputLayout.setError(null);
                    isValidEmp = true;
                }

                if (isValidEmp) {
                    String params[] = new String[7];

                    params[0] = empCode;
                    params[1] = "";
                    params[2] = PreferenceUtil.getImei();
                    params[3] = PreferenceUtil.getMac();
                    params[4] = "3";
                    params[5] = "";
                    params[6] = fcmToken;

                    if (connection.isConnectingToInternet()) {
                        LoginUser loginUser = new LoginUser();
                        loginUser.execute(params);
                        progress = new MaterialDialog.Builder(mCon)
                                .title(R.string.app_name)
                                .content(R.string.loading)
                                .progress(true, 0)
                                .autoDismiss(false)
                                .canceledOnTouchOutside(false)
                                .widgetColorRes(R.color.colorPrimary)
                                .show();

                        forgotPinDialog.cancel();
                    } else {
                        Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                        forgotPinDialog.cancel();
                    }
                    forgotPinDialog.cancel();
                }
                break;

            case R.id.closeImageView:
                forgotPinDialog.cancel();
                break;

            case R.id.forgotPinTextView:
                forgotPinDialog.show();
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ForgotPin extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[6];

                paraName[0] = "NAME";
                paraName[1] = "MPIN";
                paraName[2] = "IMEI";
                paraName[3] = "MAC";
                paraName[4] = "TYPE";
                paraName[5] = "NEWMPIN";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "UserLogin", params, paraName);

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                progress.dismiss();
                ChangePinModel[] enums = gson.fromJson(jsonResponse, ChangePinModel[].class);
                if (enums.length > 0) {
                    if (enums[0].getQueryStatus().equalsIgnoreCase("Success")) {

                        Toast.makeText(mCon, "MPin Chnaged Succesfully", Toast.LENGTH_SHORT).show();
                        // realmOperations.updateMPin(empCode, newPin);
                        Intent i = new Intent(mCon, LoginActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(mCon, enums[0].getQueryStatus(), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                String error = e.toString();
                ErrorClass.errorData(mCon, "changeMPin", "Click changeMPin Button", error);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LoginUser extends AsyncTask<String, Void, Void> {
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
                String paraName[] = new String[7];
                paraName[0] = "NAME";
                paraName[1] = "MPIN";
                paraName[2] = "IMEI";
                paraName[3] = "MAC";
                paraName[4] = "TYPE";
                paraName[5] = "NEWMPIN";
                paraName[6] = "deviceID";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Login, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("LOGIN", "onPostExecute: "+jsonResponse);
            progress.dismiss();
            try {
                LoginModel[] enums = gson.fromJson(jsonResponse, LoginModel[].class);

                if (enums.length > 0) {

                    if (!enums[0].getEMPCODE().equalsIgnoreCase("")) {
                        if (enums[0].getQueryStatus().equalsIgnoreCase("Success") && enums[0].getISVALID().equalsIgnoreCase("1")) {
                            progress.dismiss();
                            lASTLOGIN = enums[0].getLASTLOGIN();
                            EMPCODE = enums[0].getEMPCODE();
                            AppVersion = enums[0].getAV();
                            ERMAIL = enums[0].getERMAIL();
                            APP_ISLOGGED = enums[0].getAPP_ISLOGGED();

                            DESIGNATION = enums[0].getEM_DESIGNATION();
                            MMGFIXER = enums[0].getMMGFIXER();
                            DEPARTMENTrights = enums[0].getEM_DEPT_RIGHTS();
                            DEPARTMENTID = enums[0].getEM_DEPARTMENT();
                            STARTTIME = enums[0].getSTARTTIME();
                            ENDTIME = enums[0].getENDTIME();
                            Sessionid=enums[0].getSessionToken();
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("deptright", DEPARTMENTrights);

                            editor.commit();

                            //check only app version and ignore sub version
                            // Split by dot
                            String[] parts1 = AppVersion.split("\\.");
                            String[] parts2 = versionName.split("\\.");
                            // Get first two parts and form the version strings
                            String version1 = parts1[0] + "." + parts1[1];//version from api
                            String version2 = parts2[0] + "." + parts2[1];//version from device
                           // if (AppVersion.equalsIgnoreCase(versionName)) {
                            if (version1.equalsIgnoreCase(version2)) {

                                if (lASTLOGIN.equalsIgnoreCase("")) {
                                    Intent i = new Intent(mCon, AuthenticateRegistrationActivity.class);
                                    i.putExtra("IMEINumber", imei);
                                    i.putExtra("MACAddress", mac);
                                    i.putExtra("employeeID", employeeIdStr);
                                    i.putExtra("versionName", versionName);
                                    startActivity(i);
                                    finish();


                                } else {

                                    isValid = Integer.parseInt(enums[0].getISVALID());
                                    String deviceAuthorization = enums[0].getISVALID();
                                    String AppVersion1 = enums[0].getAV();
                                    if (enums[0].getISVALID().equals("1")) {
                                        // String AppLogged ="0";
                                        if (enums[0].getAPP_ISLOGGED().equals("1") || enums[0].getAPP_ISLOGGED().equals("0")) {
                                            if (DESIGNATION.equalsIgnoreCase("65") || DESIGNATION.equalsIgnoreCase("65.0")) {
                                                DESIGNATION = "SiteEng";
                                            }

                                            // set all encrypted value
                                            String AppVersion1Encrypted = aes.encrypt(AppVersion1);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.APPVERSION, AppVersion1Encrypted);

                                            String ERMAILEncrypted =aes.encrypt(ERMAIL);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.ERMAIL, ERMAILEncrypted);

                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.APP_ISLOGGED, APP_ISLOGGED);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.DEVICEAUTHORIZATION, deviceAuthorization);

                                            DesignationEncrypted=aes.encrypt(DESIGNATION);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.DESIGNATION, DesignationEncrypted);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.MMGFIXER, MMGFIXER);

                                            DepartmentIdEncrypted=aes.encrypt(DEPARTMENTID);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.DEPARTMENTID, DepartmentIdEncrypted);

                                            EMPCODEEncrypted = aes.encrypt(EMPCODE);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.EMPCODE, EMPCODEEncrypted);

                                            PasswordEncrypted = aes.encrypt(password);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.PASSWORD, PasswordEncrypted);

                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.STARTTIME, STARTTIME);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.ENDTIME, ENDTIME);
                                            String zoneIdLists = enums[0].getZONEID();
                                            List<String> zoneIdList = Arrays.asList(zoneIdLists.split(","));

                                            int id = Integer.parseInt(zoneIdList.get(0));   // get(0) because only 1st zone we r going to use in the mmg and complaint menus


                                            PreferenceUtil.setSiteEng(DesignationEncrypted);
                                            PreferenceUtil.setDepartmentId(DepartmentIdEncrypted);
                                            PreferenceUtil.setLastLogin(lASTLOGIN);
                                            PreferenceUtil.setUserName(EMPCODEEncrypted);
                                            String sessionidEncrypted = aes.encrypt(Sessionid);
                                            UtilitySharedPreferences.setPrefs(mCon, AppConstant.SID, sessionidEncrypted);


                                            //Encrypt empCode
                                            PreferenceUtil.setEmployeeCode(EMPCODEEncrypted);
                                            PreferenceUtil.setZone(enums[0].getZONEID());
                                            PreferenceUtil.setEmpName(aes.encrypt(enums[0].getEMPNAME()));
                                            //  PreferenceUtil.setFirstZoneAvailable(String.valueOf(id));
                                            PreferenceUtil.setFirstZoneAvailable(zoneIdLists);


                                            String[] params = new String[1];
                                            params[0] = enums[0].getEMPCODE();
                                            siteEngineerModel = new SiteEngineerModel(enums[0].getEMPCODE(), enums[0].getEMPNAME(), true,
                                                    enums[0].getZONEID(), enums[0].getEM_DEPARTMENT());
                                            int isExist2 = realmOperations.isSiteEngineerChecked();
                                            ChangePinModel isExist = realmOperations.isemployeeExist();
                                            if (isExist == null) {
                                                Number currentId = realmOperations.getEmpIDCount();

                                                int nextId;
                                                if (currentId == null) {
                                                    nextId = 1;
                                                } else {
                                                    nextId = currentId.intValue() + 1;
                                                }
                                                ChangePinModel changePinModel = new ChangePinModel(nextId, enums[0].getEMPNAME(), EMPCODEEncrypted,
                                                        enums[0].getEMPNAME(), "",
                                                        ERMAILEncrypted, "", 0, enums[0].getZONEID(), enums[0].getAREA(),
                                                        password, "",
                                                        Integer.parseInt("1"), imei, mac, enums[0].getQueryStatus(), lastLogin);

                                                realmOperations.insertLoginData(changePinModel);
                                            }

                                        } else {

                                            Toast.makeText(mCon, "Admin Logout", Toast.LENGTH_SHORT).show();

                                        }

                                        masterDataDownload();
                                        getMasterDataDownload();//pinky uncommented this method


                                   /* String[] params = new String[1];
                                    params[0] = enums[0].getEMPCODE();
                                    if (connection.isConnectingToInternet()) {
                                        GetRights getRights = new GetRights();
                                        getRights.execute(params);

                                    } else {
                                        MessageWindow.messageWindow(mCon, getResources().getString(R.string.no_internet_connection), "Login");
                                        //  Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                                    }*/
                                    } else if (enums[0].getISVALID().equalsIgnoreCase("0")) {
                                        MessageWindow.messageWindow(mCon, getResources().getString(R.string.user_blocked), "Error");
                                        progress.dismiss();
                                    } else {

                                        //adminLogout();
                                        Toast.makeText(mCon, "Admin Logout", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            } else {

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                                alertDialog.setCancelable(false);
                                alertDialog.setTitle(getResources().getString(R.string.alert_version));
                                alertDialog.setMessage(getResources().getString(R.string.update_version_message) + versionName);
                                alertDialog.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        /*Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);*/
                                        finish();
                                        dialog.cancel();
                                    }
                                });

                                alertDialog.show();
                            }

                        }
                        else if (enums[0].getQueryStatus().equalsIgnoreCase("Wrong Password")) {
                            pinOneEditText.setText(null);
                            pinTwoEditText.setText(null);
                            pinThreeEditText.setText(null);
                            pinFourEditText.setText(null);
                            mPinHiddenEditText.setText(null);
                            Toast.makeText(mCon, "Wrong mPIN", Toast.LENGTH_LONG).show();
                            progress.dismiss();
                        } else if (enums[0].getQueryStatus().equalsIgnoreCase("You are not belongs from MRBD department")) {
                            // Toast.makeText(mCon, "You don't belong from MRBD department", Toast.LENGTH_SHORT).show();
                            departmentAuthorizedDialog();
                            progress.dismiss();

                        } else if (enums[0].getQueryStatus().equalsIgnoreCase("SMS Sent")) {
                            // Toast.makeText(mCon, "You don't belong from MRBD department", Toast.LENGTH_SHORT).show();
                            //forgotPasswordDialog();
                            //  Toast.makeText(mCon, "SMS Sent", Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                        } else {
                            progress.dismiss();
                            deviceAuthorizedDialog();

                            // Toast.makeText(mCon, enums[0].getQueryStatus(), Toast.LENGTH_LONG).show();
                        }
                        progress.dismiss();

                    } else {


                        Toast.makeText(mCon, "User does not exist Or Registered with another device", Toast.LENGTH_SHORT).show();

                        pinOneEditText.setText(null);
                        pinTwoEditText.setText(null);
                        pinThreeEditText.setText(null);
                        pinFourEditText.setText(null);
                        mPinHiddenEditText.setText(null);
                        employeeIdEditText.setText(null);
                        // startRegisterActivity();
                        progress.dismiss();


                    }
                }

            } catch (Exception e) {
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "Click Login Button", error);
            }
            progress.dismiss();
        }
    }

    private void deviceAuthorizedDialog() {

        MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                .title(R.string.alert)
                .titleColorRes(R.color.red_500)
                .content(R.string.device_authorization)
                .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                .canceledOnTouchOutside(false)
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }).show();
    }

    private void departmentAuthorizedDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                .title(R.string.alert)
                .titleColorRes(R.color.red_500)
                .content(R.string.department_authorization)
                .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                .canceledOnTouchOutside(false)
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }).show();
    }

    private void getMasterDataDownload() {

        try {
            empMasterDataCode = UtilitySharedPreferences.getPrefs(this, AppConstant.EMPCODE);
            empMasterDataCode = aes.decrypt(empMasterDataCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[1];
        params[0] = empMasterDataCode;
        GetMasterData_IssueToMeterFixer getMasterData_issueToMeterFixer = new GetMasterData_IssueToMeterFixer();
        getMasterData_issueToMeterFixer.execute(params);
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadMaster extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            mmgMasterProgress = new MaterialDialog.Builder(mCon)
                    .content("Downloading master data....")
                    .progress(true, 0)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                //jsonMasterDataResponse = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.MasterData);
                String paraNames[] = new String[2];
                paraNames[0] = "EmpCode";
                paraNames[1] = "SessionToken";
                String username=null,password=null;
                username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.EMPCODE));
                password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                jsonMasterDataResponse = invServices.getOtherData(Constants.URL, Constants.NameSpace, Constants.MasterData,username,password, params, paraNames);

                Log.e("jsonMasterDataResponse", jsonMasterDataResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                Log.d("BANK", "onPostExecute: "+jsonMasterDataResponse);
                MasterDataModel masterDataModel = gson.fromJson(jsonMasterDataResponse, MasterDataModel.class);

                if (masterDataModel != null) {

                    // shantanu master for bank/micr
                    for (BankMasterModel bankMasterModdel : masterDataModel.getBankMaster()) {
                        BankMasterModel bmm = new BankMasterModel();
                        bmm.setID(bankMasterModdel.getID());
                        bmm.setNAME(bankMasterModdel.getNAME());
                        realmOperations.addBankMaster(bmm);
                    }

                    for (ConnectionStatusModel connectionStatusModel : masterDataModel.getConnectionStatus()) {
                        ConnectionStatusModel csm = new ConnectionStatusModel();
                        csm.setDTM_DISCONN_TAG_ID(connectionStatusModel.getDTM_DISCONN_TAG_ID());
                        csm.setDTM_DISCONN_TAG_NAME(connectionStatusModel.getDTM_DISCONN_TAG_NAME());
                        realmOperations.addConnectionStatus(csm);
                    }

                    ComplaintTypeModel complaintTypeModelExist = realmOperations.getComplaintTypeExist();
                    //   if (complaintTypeModelExist == null) {
                    for (DownloadComplaintType downloadComplaintType : masterDataModel.getDownloadComplaintTypes()) {
                        int id = stringToInt(downloadComplaintType.getCMTM_ID());
//                        ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel(id, downloadComplaintType.getCMTM_CODE(), downloadComplaintType.getCMTM_NAME());
                        ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel(id, downloadComplaintType.getCMTM_CODE(), downloadComplaintType.getCMTM_NAME()
                                , downloadComplaintType.getREQUEST(), downloadComplaintType.getNOCONSUMER(), downloadComplaintType.getDEPTID(), downloadComplaintType.getCOMPLAINT(), downloadComplaintType.getENQUIRY());
                        realmOperations.addComplaintType(complaintTypeModel);
                    }
                    //  }

                    ComplaintSubTypeModel complaintSubTypeModelExist = realmOperations.getComplaintSubTypeExist();
                    //   if (complaintSubTypeModelExist == null) {
                    for (DownloadComplaintSubType downloadComplaintSubType : masterDataModel.getDownloadComplaintSubTypes()) {
                        int id = stringToInt(downloadComplaintSubType.getCOMPLAINTSUBTYPEID());
                        int compId = stringToInt(downloadComplaintSubType.getCOMPLAINTTYPEID());
                        ComplaintSubTypeModel complaintSubTypeModel = new ComplaintSubTypeModel(id, downloadComplaintSubType.getCOMPLAINTSUBTYPENAME(), compId, downloadComplaintSubType.getCOMPLAINT(), downloadComplaintSubType.getREQUEST());
                        realmOperations.addComplaintSubType(complaintSubTypeModel);
                    }

                    ComplaintSourceModel complaintSourceModelExist = realmOperations.getComplaintSourceExist();
                    //   if (complaintSourceModelExist == null) {
                    for (DownloadComplaintSource downloadComplaintSource : masterDataModel.getDownloadComplaintSources()) {
                        int id = stringToInt(downloadComplaintSource.getCSM_SOURCECODE());
                        ComplaintSourceModel complaintSourceModel = new ComplaintSourceModel(id, downloadComplaintSource.getCSM_SOURCEDESC());
                        realmOperations.addComplaintSource(complaintSourceModel);
                    }
                    //     }

                    ZoneModel zoneModelExist = realmOperations.getZoneExist();
                    //   if (zoneModelExist == null) {
                    for (DownloadZone downloadZone : masterDataModel.getDownloadZones()) {
                        int id = stringToInt(downloadZone.getBUM_BU_ID());
                        ZoneModel zoneModel = new ZoneModel(downloadZone.getBU_NAME(), id);
                        realmOperations.addZone(zoneModel);
                    }
                    //  }

                    SubZoneModel subZoneModelExist = realmOperations.getSubZoneExist();
                    //   if (subZoneModelExist == null) {
                    for (DownloadSubZone downloadSubZone : masterDataModel.getDownloadSubZones()) {
                        int id = stringToInt(downloadSubZone.getPCM_PC_ID());
                        int buId = stringToInt(downloadSubZone.getPCM_BU_ID());
                        SubZoneModel subZoneModel = new SubZoneModel(id, downloadSubZone.getPCM_PC_NAME(), buId);
                        realmOperations.addSubZone(subZoneModel);
                    }
                    //   }

                    // Insert Site Engineer in SiteEngineerModel Table
                    SiteEngineerModel siteEngineerModelExist = realmOperations.getSiteEngineerExist();
                    //    if (siteEngineerModelExist == null) {
                    for (DownloadSiteEngineer downloadSiteEngineer : masterDataModel.getDownloadSiteEngineers()) {
                        SiteEngineerModel siteEngineerModel = new SiteEngineerModel(downloadSiteEngineer.getEMPLOYEE_CODE(),
                                downloadSiteEngineer.getEMPLOYEE_NAME(), false, downloadSiteEngineer.getEM_ZONE(),
                                downloadSiteEngineer.getEM_DEPT());
                        realmOperations.addSiteEngineer(siteEngineerModel);
                    }
                    //  }

                    // Insert Finish Action in FinishActionModel Table
                    FinishActionModel finishActionModelExist = realmOperations.getFinishActionExist();
                    // if (finishActionModelExist == null) {

                    for (DownloadFinishAction downloadFinishAction : masterDataModel.getDownloadFinishActions()) {
                        int id = stringToInt(downloadFinishAction.getCSCM_ID());
                        FinishActionModel finishActionModel = new FinishActionModel(id, downloadFinishAction.getCSCM_SECNAME(), downloadFinishAction.getFILTER());
                        realmOperations.addFinishAction(finishActionModel);
                    }
                    //   }

                    for (DownloadComplaintBy downloadComplaintBy : masterDataModel.getDownloadComplaintBy()) {
                        ComplaintByModel complaintByModel = new ComplaintByModel(downloadComplaintBy.getVCM_CATCD(), downloadComplaintBy.getVCM_CATNAME());
                        realmOperations.addComplaintBy(complaintByModel);
                    }

                    for (DownloadCustomerType downloadComplaintBy : masterDataModel.getCustType()) {
                        DownloadCustomerType customerType = new DownloadCustomerType(downloadComplaintBy.getCUSTTYPEID(), downloadComplaintBy.getCUSTTYPETEXT(),downloadComplaintBy.getCUSTBUID());
                        realmOperations.addCustomerType(customerType);
                    }

                    for (DownloadSourceType downloadSourceType : masterDataModel.getSource()) {
                        DownloadSourceType sourceType = new DownloadSourceType(downloadSourceType.getSOURCECODE(), downloadSourceType.getSOURCEDESC(), downloadSourceType.getSOURCETYPE());
                        realmOperations.addSourceType(sourceType);
                    }


                    ActionFormModel actionFormModel = realmOperations.getActionFormExist();

                    if (actionFormModel == null) {

                        for (DownloadActionCRM downloadActionCRM : masterDataModel.getActionCRM()) {
                            ActionFormModel actionModel = new ActionFormModel(downloadActionCRM.getA_NAME(), downloadActionCRM.getA_COM_TYPE());
                            realmOperations.addActionForm(actionModel);
                        }
                    }

                    WorkCompObservationModel workCompObservationModel = realmOperations.getWorkCompObservationModelExist();

                    if (workCompObservationModel == null) {

                        for (WorkCompObservation workCompObservation : masterDataModel.getWorkCompObservations()) {
                            WorkCompObservationModel WorkCompObservationModel = new WorkCompObservationModel(workCompObservation.getID(), workCompObservation.getOBSERVATION(), workCompObservation.getCOMPTYPEID(), workCompObservation.getACTIONID());
                            realmOperations.addWorkCompObservation(WorkCompObservationModel);
                        }
                    }
                    DocSourceModel docSourceModel = realmOperations.getDocSourcenModelExist();
                    if (docSourceModel == null) {

                        for (DocSource docSource : masterDataModel.getDocSources()) {
                            DocSourceModel docSourceModels = new DocSourceModel(docSource.getDOCSOURCE(), docSource.getDOCCOMPTYPE(), docSource.getDOCSOURCECODE());
                            realmOperations.addDocSources(docSourceModels);
                        }
                    }

                    DocTypeModel docTypeModel = realmOperations.getDocTypeModelExist();
                    if (docTypeModel == null) {

                        for (DocType docType : masterDataModel.getDocTypes()) {
                            String docSource = docType.getDOCUMENTSOURCE();
                            if (docSource == null) {
                                docSource = "NC";
                            } else {
                                docSource = docSource;
                            }

                            DocTypeModel docTypeModels = new DocTypeModel(docType.getDOCUMENTTYPE(), docType.getDOCUMENTTYPEID(), docSource);
                            realmOperations.addDocTypes(docTypeModels);
                        }
                    }

                    DocSubTypeModel docSubTypeModel = realmOperations.getDocSubTypeModelExist();
                    if (docSubTypeModel == null) {

                        for (DocSubType subType : masterDataModel.getDocSubTypes()) {
                            DocSubTypeModel docSubTypeModels = new DocSubTypeModel(subType.getDOCUMENTTYPEID(), subType.getDOCUMENTSUBTYPEID(), subType.getDOCUMENTSUBTYPE());
                            realmOperations.addDocSubTypes(docSubTypeModels);
                        }
                    }

                    //      }

                    //    if (meterStatusModel == null) {

                    for (MeterStatusModel statusModel : masterDataModel.getMeterStatus()) {
                        MeterStatusModel meterStatusModel1 = new MeterStatusModel(statusModel.getMSM_METERSTATUS_ID(), statusModel.getMSM_METERSTATUS_NAME().substring(2), statusModel.getBILLEDID());
                        realmOperations.addMeterStatus(meterStatusModel1);
                    }
                    //   }

                    /// if (meterStatusModel == null) {

                    for (MeterObservationModel statusModel : masterDataModel.getMeterObservation()) {
                        MeterObservationModel observationModel = new MeterObservationModel(statusModel.getMTRSTATUS(), statusModel.getDFM_CODE(), statusModel.getDFM_DEFNAME(), statusModel.getMSNM_READING_MANDATORY(), statusModel.getMSNM_PHOTO_REQ());
                        realmOperations.addMeterObservation(observationModel);
                        //  }
                    }

                    for (DownloadConnCategory connCategory : masterDataModel.getConnCategory()) {
                        DownloadConnCategory connCategory1 = new DownloadConnCategory(connCategory.getCATEGORY_ID(), connCategory.getCATEGORY_NAME());
                        realmOperations.addConnCategory(connCategory1);
                        //  }
                    }
                    for (DownloadConnSize connSize : masterDataModel.getConnSize()) {
                        DownloadConnSize connSize1 = new DownloadConnSize(connSize.getID(), connSize.getNAME());
                        realmOperations.addConnSize(connSize1);
                        //  }
                    }

                    // masterProgress.dismiss();
                    // masterProgress.cancel();
                    mmgMasterProgress.dismiss();

                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    //  masterProgress.dismiss();
                }

            } catch (Exception e) {
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid", error);
                // masterProgress.dismiss();
            }

            mmgMasterProgress.dismiss();
            getBilllingAdjustmentdataDownload();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void getDepartmentRights() {
        try {
            empMasterDataCode = UtilitySharedPreferences.getPrefs(this, AppConstant.EMPCODE);
            empMasterDataCode = aes.decrypt( empMasterDataCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[1];
        params[0] = empMasterDataCode;
        if (connection.isConnectingToInternet()) {
            GetRights getRights = new GetRights();
            getRights.execute(params);

        } else {
            MessageWindow.messageWindow(mCon, getResources().getString(R.string.no_internet_connection), "Login");
            //  Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void getBilllingAdjustmentdataDownload() {

        if (connection.isConnectingToInternet()) {


            BilllingAdjustmentdataDownload billlingAdjustmentdataDownload = new BilllingAdjustmentdataDownload();
            billlingAdjustmentdataDownload.execute();


           /* masterProgress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .autoDismiss(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();*/
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private class BilllingAdjustmentdataDownload extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                jsonResponseBillAdjustment = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.Complaint_BillAdjRequestDropdown);
                // Log.e("jsonResponseBillAdjustment", jsonResponseBillAdjustment);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                //Log.e("BillAdjustResponse", jsonResponseBillAdjustment);
                BilllingAdjustmentdataDownloadModel billlingAdjustmentdataDownloadModel = gson.fromJson(jsonResponseBillAdjustment, BilllingAdjustmentdataDownloadModel.class);

                if (billlingAdjustmentdataDownloadModel != null) {
                    DownloadAdjustmentTypeTable downloadAdjustmentTypeExist = realmOperations.getDownloadAdjustmentTypeExist();
                    if (downloadAdjustmentTypeExist == null) {
                        for (DownloadAdjustmentType downloadAdjustmentTypeTable : billlingAdjustmentdataDownloadModel.getDownloadAdjustmentTypes()) {
                            String id = downloadAdjustmentTypeTable.getREM_REASONCD();
//                        ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel(id, downloadComplaintType.getCMTM_CODE(), downloadComplaintType.getCMTM_NAME());
                            DownloadAdjustmentTypeTable downloadAdjustmentTypeTabledownloadAdjustmentTypeTable = new DownloadAdjustmentTypeTable(id, downloadAdjustmentTypeTable.getREM_REASONNM());
                            realmOperations.addAdjustmentTypeType(downloadAdjustmentTypeTabledownloadAdjustmentTypeTable);
                        }
                    }

                    DownloadMeterStatusBillAdjustTable downloadMeterStatusBillAdjustTableExist = realmOperations.getDownloadMeterStatusBillAdjustTableExist();
                    if (downloadMeterStatusBillAdjustTableExist == null) {
                        for (DownloadMeterStatusBillAdjust downloadMeterStatusBillAdjust : billlingAdjustmentdataDownloadModel.getDownloadMeterStatusBillAdjusts()) {
                            String id = downloadMeterStatusBillAdjust.getMSM_METERSTATUS_ID();

                            DownloadMeterStatusBillAdjustTable meterStatusBillAdjustTable = new DownloadMeterStatusBillAdjustTable(id, downloadMeterStatusBillAdjust.getMSM_METERSTATUS_NAME(), downloadMeterStatusBillAdjust.getBILLEDID(), downloadMeterStatusBillAdjust.getMSM_AVG_MONTHS()
                                    , downloadMeterStatusBillAdjust.getMSM_AVG_METERSTATUS(), downloadMeterStatusBillAdjust.getMSM_READING_MANDATORY(), downloadMeterStatusBillAdjust.getMSM_PHOTO_REQ(), downloadMeterStatusBillAdjust.getHIGH(), downloadMeterStatusBillAdjust.getLOW());
                            realmOperations.addDownloadMeterStatusBillAdjiustmentType(meterStatusBillAdjustTable);
                        }
                    }

/*

                    for (DownloadAdjustmentType  downloadAdjustmentType : billlingAdjustmentdataDownloadModel.getDownloadAdjustmentTypes()) {
                        ComplaintByModel complaintByModel = new ComplaintByModel(downloadComplaintBy.getVCM_CATCD(), downloadComplaintBy.getVCM_CATNAME());
                        realmOperations.addComplaintBy(complaintByModel);
                    }

                    for (DownloadCustomerType downloadComplaintBy : billlingAdjustmentdataDownloadModel.getCustType()) {
                        DownloadCustomerType customerType = new DownloadCustomerType(downloadComplaintBy.getCUSTTYPEID(), downloadComplaintBy.getCUSTTYPETEXT());
                        realmOperations.addCustomerType(customerType);
                    }
*/
                    // masterProgress.dismiss();
                    //masterProgress.cancel();

                    getDepartmentRights();

                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    // masterProgress.dismiss();
                }


            } catch (Exception e) {
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid", error);
                // masterProgress.dismiss();
            }

        }


    }

    private int stringToInt(String strId) {
        try {
            return Integer.parseInt(strId);
        } catch (Exception e) {
            return (int) Double.parseDouble(strId);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetRights extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            rightsProgress = new MaterialDialog.Builder(mCon)
                    .content("Download Rights...")
                    .progress(true, 0)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraNames[] = new String[1];
                paraNames[0] = "EmpCode";
                jsonRightsResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, GetRights, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                rightsProgress.dismiss();
                rightsProgress.cancel();
                //progress.dismiss();
                //Log.e("RightsRsponse", jsonRightsResponse);
                String[] enums = gson.fromJson(jsonRightsResponse, String[].class);
                Log.d("RIGHTS", "onPostExecute: "+jsonRightsResponse);
                if (enums.length > 0) {
                    if (enums[0].equalsIgnoreCase("Success")) {
                        String rights = Arrays.toString(enums);
                        List<String> rightsList = new ArrayList<>(Arrays.asList(enums).subList(2, enums.length));

                        StringBuilder builder = new StringBuilder();
                        for (String s : rightsList) {
                            builder.append(s);
                            builder.append(",");
                        }
                        String strRights = builder.toString();

                        if (strRights.contains("',,")) {
                            strRights = strRights.replace("',,", "");
                            PreferenceUtil.setRights(strRights);
                        } else {
                            PreferenceUtil.setRights(strRights);
                        }
                        //Log.e("check", "" + strRights);

                     /*   if(lastLogin == null || lastLogin.equals("")){
                            fingerPrintLinearLayout.setVisibility(View.GONE);
                            fragment = new ChangePasswordFragment();
                            setFragment();

                        } else*/
                        Log.d("rights", "onPostExecute: "+rights);
                        if (rights.contains("SYSADM001")
                                || rights.contains("SADMIN423")
                                || (rights.contains("CM0000015"))
                                || rights.contains("NCON0906")
                                || rights.contains("MEMMAR203")
                                || rights.contains("ME0000201")
                                || rights.contains("NC0000002")
                                || rights.contains("CCAP001002")) {   // Shantanu Code for Payment Collection module

                            Intent intent = new Intent(mCon, MainActivity.class);
                            intent.putExtra("IMEINumber", imei);
                            intent.putExtra("MACAddress", mac);
                            intent.putExtra("versionName", versionName);
                            intent.putExtra("Tag", "0");

                            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            rightsProgress.dismiss();
                            rightsProgress.cancel();
                            //progress.dismiss();
                            MessageWindow.messageWindow(mCon, getResources().getString(R.string.you_have_no_role_rights), "Login Error");
                        }

                    } else {
                        Toast.makeText(mCon, enums[0], Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (Exception e) {
                progress.dismiss();
                // Toast.makeText(mCon, jsonRightsResponse, Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetRights", error);
            }
            rightsProgress.dismiss();
            rightsProgress.cancel();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ValidateUser extends AsyncTask<String, Void, Void> {
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
                String paraName[] = new String[1];
                paraName[0] = "EMPDATA";
                jsonValidateUser = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "ValidateUser", params, paraName);
            } catch (Exception e) {
                Log.d("Catch", e.toString());

            }
            return null;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(Void result) {
            try {
                LoginModel[] enums = gson.fromJson(jsonValidateUser, LoginModel[].class);
                if (enums.length > 0) {
                    progress.dismiss();
                    isValid = Integer.parseInt(enums[0].getISVALID());
                    realmOperations.updateEmployeeData(UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE), enums[0].getEMPNAME(), enums[0].getMOBILENO(),
                            new AesAlgorithm().encrypt(enums[0].getEMAIL()), enums[0].getRDRID(), enums[0].getZONEID(), enums[0].getAREA(),
                            isValid, enums[0].getAV(), enums[0].getAPP_ISLOGGED());
                    empCode = enums[0].getEMPCODE();
                    ap_ver = enums[0].getAV();
                    String version = PreferenceUtil.getVersionName();
                    if (!ap_ver.equalsIgnoreCase(version)) {
                        progress.dismiss();
                        MessageWindow.errorWindow(LoginActivity.this, "You are allowed to use App Version: " + versionName);
                        return;
                    }

                    statrPost();

                    if (isValid == 1) {
                        String[] params = new String[1];
                        params[0] = employeeIdStr;

                       /* GetRights getRights = new GetRights();
                        getRights.execute(params);*/

                        // getMasterDataDownload();
                        masterDataDownload();
                      /*  GetMasterData_IssueToMeterFixer getMasterData_issueToMeterFixer = new GetMasterData_IssueToMeterFixer();
                        getMasterData_issueToMeterFixer.execute(params);*/
                    }

                 /*   UtilitySharedPreferences.clearPrefKey(mCon, Constants.APPVERSION);
                    UtilitySharedPreferences.setPrefs(mCon, Constants.APPVERSION, enums[0].getAV());
                    PreferenceUtil.setDeviceAuthorize(enums[0].getISVALID());
                    PreferenceUtil.setAPP_ISLOGGED(enums[0].getAPP_ISLOGGED());*/

                    progress.dismiss();

                }
            } catch (Exception ex) {
                Log.e("Exception", ex.toString());
                String error = ex.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "ValidateUser", error);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void statrPost() {
        employeeIdStr = employeeIdEditText.getText().toString().trim();

        boolean isValidEmpCode = false;

        if (TextUtils.isEmpty(employeeIdStr)) {
            employeeIdInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else if (empCode.equals(employeeIdStr)) {
            employeeIdInputLayout.setError(null);
            isValidEmpCode = true;
        }

        if (isValidEmpCode) {

            if (isValid == 1) {
                Intent i = new Intent(mCon, MainActivity.class);
                i.putExtra("IMEINumber", imei);
                i.putExtra("MACAddress", mac);
                i.putExtra("employeeID", employeeIdStr);
                i.putExtra("Tag", "0");
                i.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            } else {
                MessageWindow.messageWindow(mCon, getResources().getString(R.string.user_blocked), "Cannot Login");
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetMasterData_IssueToMeterFixer extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            mmgMasterProgress = new MaterialDialog.Builder(mCon)
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
                String paraNames[] = new String[1];
                paraNames[0] = "EmpCode";

                JsonMMGMasterData = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetMasterDataForAndroid_MMG, params, paraNames);
                Log.e("MASTEeRDATA", JsonMMGMasterData);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MMG_IssuetoMeterFixer_MasterDataModel mmg_issuetoMeterFixer_masterDataModel = gson.fromJson(JsonMMGMasterData, MMG_IssuetoMeterFixer_MasterDataModel.class);
                Log.d(MYTAG, "" + mmg_issuetoMeterFixer_masterDataModel);
                // if (mmg_issuetoMeterFixer_masterDataModel != null) {

                MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel = realmOperations.getRoadcuttingExist();
                //if (mmgTypeOfRoadcuttingModel == null) {
                realmOperations.deleteRoadcuttingTable();
                for (MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel1 : mmg_issuetoMeterFixer_masterDataModel.getRoadcutting()) {
                    MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel2 = new MMGTypeOfRoadcuttingModel(mmgTypeOfRoadcuttingModel1.getRC_ID(), mmgTypeOfRoadcuttingModel1.getRC_DESC());
                    realmOperations.addRoadcutting(mmgTypeOfRoadcuttingModel2);
                }
                //   }

                MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel = realmOperations.getExcavationExist();
                //  if (mmgSaddleAndPitExcavModel == null) {

                realmOperations.deleteExcavationTable();
                for (MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel1 : mmg_issuetoMeterFixer_masterDataModel.getExcavation()) {
                    MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel2 = new MMGSaddleAndPitExcavModel(mmgSaddleAndPitExcavModel1.getEC_ID(), mmgSaddleAndPitExcavModel1.getEC_DESC());
                    realmOperations.addExcavation(mmgSaddleAndPitExcavModel2);
                }
                //  }

                MMGMakerCodeModel mmgMakerCodeModel = realmOperations.getMakerCodeExist();
                //     if (mmgMakerCodeModel == null) {

                realmOperations.deleteMakerCodeTable();
                for (MMGMakerCodeModel mmgMakerCodeModel1 : mmg_issuetoMeterFixer_masterDataModel.getMakerCode()) {
                    MMGMakerCodeModel mmgMakerCodeModel2 = new MMGMakerCodeModel(mmgMakerCodeModel1.getMMFG_MFGCODE(), mmgMakerCodeModel1.getMMFG_MFGNAME(), mmgMakerCodeModel1.getMMFG_MATERIAL_TYPE());
                    realmOperations.addMakerCode(mmgMakerCodeModel2);
                }

                //   }

                MMGMeterSizeModel mmgMeterSizeModel = realmOperations.getMeterSizeExist();
                // if (mmgMeterSizeModel == null) {

                realmOperations.deleteMeterSizeTable();
                for (MMGMeterSizeModel mmgMeterSizeModel1 : mmg_issuetoMeterFixer_masterDataModel.getMeterSizeModel()) {
                    MMGMeterSizeModel mmgMeterSizeModel2 = new MMGMeterSizeModel(mmgMeterSizeModel1.getMCS_ID(), mmgMeterSizeModel1.getCONNSIZEMM());
                    realmOperations.addMeterSize(mmgMeterSizeModel2);
                }
                //  }

                MMGRampAndRRModel mmgRampAndRRModel = realmOperations.getRAMPRRExist();
                // if (mmgRampAndRRModel == null) {

                realmOperations.deleteRAMPRRTable();
                for (MMGRampAndRRModel mmgRampAndRRModel1 : mmg_issuetoMeterFixer_masterDataModel.getRAMPRR()) {
                    MMGRampAndRRModel mmgRampAndRRModel2 = new MMGRampAndRRModel(mmgRampAndRRModel1.getRRR_ID(), mmgRampAndRRModel1.getRRR_DESC());
                    realmOperations.addRAMPRR(mmgRampAndRRModel2);
                }
                //    }

                MMGMeterTypeModel mmgMeterTypeModel = realmOperations.getMeterTypeExist();
                //  if (mmgMeterTypeModel == null) {

                realmOperations.deleteMeterTypeTable();
                for (MMGMeterTypeModel mmgMeterTypeModel1 : mmg_issuetoMeterFixer_masterDataModel.getMeterType()) {
                    MMGMeterTypeModel mmgMeterTypeModel2 = new MMGMeterTypeModel(mmgMeterTypeModel1.getMTC_METERTYPE_CODE(), mmgMeterTypeModel1.getMTC_TYPEDESC());
                    realmOperations.addMeterType(mmgMeterTypeModel2);
                }
                //   }
                MMGWallBoringModel mmgWallBoringModel = realmOperations.getWallBoringExist();
                //  if (mmgWallBoringModel == null) {

                realmOperations.deleteWallBoringTable();
                for (MMGWallBoringModel mmgWallBoringModel1 : mmg_issuetoMeterFixer_masterDataModel.getWallBoring()) {
                    MMGWallBoringModel mmgWallBoringModel2 = new MMGWallBoringModel(mmgWallBoringModel1.getWB_ID(), mmgWallBoringModel1.getWB_DESC());
                    realmOperations.addWallBoring(mmgWallBoringModel2);
                }
                // }

                MMGCgRestroModel mmgCgRestroModel = realmOperations.getCGRExist();
                // if (mmgCgRestroModel == null) {

                realmOperations.deleteCGRTable();
                for (MMGCgRestroModel mmgCgRestroModel1 : mmg_issuetoMeterFixer_masterDataModel.getCGR()) {
                    MMGCgRestroModel mmgCgRestroModel2 = new MMGCgRestroModel(mmgCgRestroModel1.getCGR_ID(), mmgCgRestroModel1.getCGR_DESC());
                    realmOperations.addCGR(mmgCgRestroModel2);
                }
                //}

                MMGFcRestroModel mmgFcRestroModel = realmOperations.getFCRExist();
                //   if (mmgFcRestroModel == null) {

                realmOperations.deleteFCRTable();
                for (MMGFcRestroModel mmgFcRestroModel1 : mmg_issuetoMeterFixer_masterDataModel.getFCR()) {
                    MMGFcRestroModel mmgFcRestroModel2 = new MMGFcRestroModel(mmgFcRestroModel1.getFCR_ID(), mmgFcRestroModel1.getFCR_DESC());
                    realmOperations.addFCR(mmgFcRestroModel2);
                }
                //  }

                MMGMaterialDetailsModel mmgMaterialDetailsModel = realmOperations.getMaterialDetailsExist();
                //if (mmgMaterialDetailsModel == null) {

                realmOperations.deleteMaterialDetailsTable();
                for (MMGMaterialDetailsModel mmgMaterialDetailsModel1 : mmg_issuetoMeterFixer_masterDataModel.getMaterialDetails()) {
                    MMGMaterialDetailsModel mmgMaterialDetailsModel2 = new MMGMaterialDetailsModel(mmgMaterialDetailsModel1.getMM_ID(), mmgMaterialDetailsModel1.getMM_NAME(), mmgMaterialDetailsModel1.getM_UNIT(), mmgMaterialDetailsModel1.getMM_DEF_QTY(), mmgMaterialDetailsModel1.getSIZEID());
                    realmOperations.addMaterialDetails(mmgMaterialDetailsModel2);
                }
                //  }

                MMGZoneModel IssueToMeter_ZoneExist = realmOperations.getIssueToMeter_ZoneExist();
                //  if (IssueToMeter_ZoneExist == null) {
                realmOperations.deleteIssueToMeterFixrZoneTable();
                for (elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGZoneModel MMGZoneModel : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterZones()) {
                    int id = MMGZoneModel.getBUM_BU_ID();
                    elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGZoneModel MMGZoneModelData = new MMGZoneModel(MMGZoneModel.getBU_NAME(), id);
                    realmOperations.addIssueToMeterFixrZone(MMGZoneModelData);
                }
                //    }

                MMGSubZoneModel MMGSubZoneModelExist = realmOperations.getIssueToMeterSubZoneExist();
                //  if (MMGSubZoneModelExist == null) {

                realmOperations.deleteIssueToMeterSubZoneTable();
                for (elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel MMGSubZoneModel : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterSubZones()) {
                    int id = (MMGSubZoneModel.getPCM_PC_ID());
                    int buId = (MMGSubZoneModel.getPCM_BU_ID());
                    elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel MMGSubZoneModelData = new MMGSubZoneModel(id, MMGSubZoneModel.getPCM_PC_NAME(), buId);
                    realmOperations.addIssueToMeterSubZone(MMGSubZoneModelData);
                }
                // }

                // Insert Request Type  in RequestTypeModel Table
                MMGRequestTypeModel MMGRequestTypeModel = realmOperations.getIssueToMeterFixrRequestTypeExist();
                // if (MMGRequestTypeModel == null) {

                realmOperations.deleteIssueToMeterFixrRequestTypeTable();
                for (MMGRequestTypeModel MMGRequestTypeModel1 : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterRequstType()) {
                    MMGRequestTypeModel MMGRequestTypeModel2 = new MMGRequestTypeModel(MMGRequestTypeModel1.getSelectVal(), MMGRequestTypeModel1.getAllVal());
                    realmOperations.addIssueToMeterFixrRequestType(MMGRequestTypeModel2);
                }
                //    }

                MMGObersvationModel mmgObersvationModel = realmOperations.getObservationExist();
                //  if (mmgObersvationModel == null) {

                realmOperations.deleteObservationTable();
                for (MMGObersvationModel mmgObersvationModel1 : mmg_issuetoMeterFixer_masterDataModel.getObersvation()) {
                    MMGObersvationModel mmgMeterPrefixModel2 = new MMGObersvationModel(mmgObersvationModel1.getOCRM_ID(), mmgObersvationModel1.getOCRM_NAME());
                    realmOperations.addObservation(mmgMeterPrefixModel2);
                }
                //  }

                MStatusObservationModel mStatusObservationModel = realmOperations.getMStatusObservationExist();
                //    if (mStatusObservationModel == null) {

                realmOperations.deleteMStatusObservationTable();
                for (MStatusObservationModel mStatusObservationModel1 : mmg_issuetoMeterFixer_masterDataModel.getMStatusObservation()) {
                    MStatusObservationModel mmgMeterPrefixModel22 = new MStatusObservationModel(mStatusObservationModel1.getMSNM_MSTATUS_ID(), mStatusObservationModel1.getMSNM_MSUBSTATUS_ID(), mStatusObservationModel1.getMSNM_MSUBSTATUS_NAME());
                    realmOperations.addMStatusObservation(mmgMeterPrefixModel22);
                }
                //    }

                MMGMeterLocationModel mmgMeterLocationModel = realmOperations.getMtrLocationExist();
                //     if (mmgMeterLocationModel == null) {

                realmOperations.deleteMtrLocationTable();
                for (MMGMeterLocationModel mmgMeterLocationModel37 : mmg_issuetoMeterFixer_masterDataModel.getMeterLocation()) {
                    MMGMeterLocationModel mmgMeterLocationModel2 = new MMGMeterLocationModel(mmgMeterLocationModel37.getML_ID(), mmgMeterLocationModel37.getML_DESC());
                    realmOperations.addMtrLocation(mmgMeterLocationModel2);
                }
                //    }

                MMGMeterStatusModel mmgMeterStatusModel = realmOperations.getMeterStatusExist();
                //    if (mmgMeterStatusModel == null) {

                realmOperations.deleteMeterStatusTable();
                for (MMGMeterStatusModel mmgMeterStatusModel1 : mmg_issuetoMeterFixer_masterDataModel.getMeterStatus()) {
                    MMGMeterStatusModel mmgMeterStatusModel2 = new MMGMeterStatusModel(mmgMeterStatusModel1.getMSM_METERSTATUS_ID(), mmgMeterStatusModel1.getMSM_METERSTATUS_NAME(), mmgMeterStatusModel1.getFINALMETERSTATUSTAG());
                    realmOperations.addMeterStatusDetails(mmgMeterStatusModel2);
                }
                //    }

                MMGVendorDetModel mmgVendorDetModel = realmOperations.getVendorDetExist();
                //  if (mmgVendorDetModel == null) {
                realmOperations.deletVendorTable();
                for (MMGVendorDetModel mmgVendorDetModel1 : mmg_issuetoMeterFixer_masterDataModel.getVendor()) {
                    MMGVendorDetModel mmgVendorDetModel2 = new MMGVendorDetModel(mmgVendorDetModel1.getEM_EMP_CODE(), mmgVendorDetModel1.getNAME(), mmgVendorDetModel1.getEM_EMAIL(), mmgVendorDetModel1.getEM_PHONEM(), mmgVendorDetModel1.getEM_DESIGNATION(), mmgVendorDetModel1.getDGM_DES_NAME());
                    realmOperations.addVendorDet(mmgVendorDetModel2);
                }
                //   }

                MSRModel msrModel = realmOperations.getMSRExist();
                //  if (msrModel == null) {
                realmOperations.deleteMSRTable();
                for (MSRModel mMsrModel1 : mmg_issuetoMeterFixer_masterDataModel.getMSR()) {
                    //Log.e("MSR", mMsrModel1.getSBM_NAME());
                    MSRModel msrModel22 = new MSRModel(mMsrModel1.getSBM_ID(), mMsrModel1.getSBM_NAME());
                    realmOperations.addMSR(msrModel22);
                }
                //  }

                realmOperations.deleteSRTable();
                for (SRModel srModel1 : mmg_issuetoMeterFixer_masterDataModel.getSR()) {
                    //Log.e("SRName", srModel1.getTRM_NAME());
                    SRModel srModel22 = new SRModel(srModel1.getTRM_ID(), srModel1.getTRM_NAME(), srModel1.getMSRID());
                    realmOperations.addSR(srModel22);
                }

                realmOperations.deleteDMATable();
                for (DMAModel dmaModel1 : mmg_issuetoMeterFixer_masterDataModel.getDMA()) {
                    DMAModel dmaModel2 = new DMAModel(dmaModel1.getPM_ID(), dmaModel1.getPM_NAME(), dmaModel1.getSRID());
                    realmOperations.addDMA(dmaModel2);
                }

                MMGContEmpModel MMGContEmpModel = realmOperations.getContEmpExist();
                realmOperations.deletContEmpTable();
                for (MMGContEmpModel mmgContEmpModel : mmg_issuetoMeterFixer_masterDataModel.getContEmp()) {
                    MMGContEmpModel mmgVendorDetModel2 = new MMGContEmpModel(mmgContEmpModel.getEM_EMP_CODE(), mmgContEmpModel.getNAME(), mmgContEmpModel.getEM_EMAIL(), mmgContEmpModel.getEM_PHONEM(), mmgContEmpModel.getEM_DESIGNATION(), mmgContEmpModel.getDGM_DES_NAME(), mmgContEmpModel.getEM_VENDOR_ID());
                    realmOperations.addContEmpDet(mmgVendorDetModel2);
                }

                MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel = realmOperations.getCvlMesurementExist();
                //  if (mmgCvlMeasurementResponseModel == null) {

                realmOperations.deletCvlMesurementTable();
                for (MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel1 : mmg_issuetoMeterFixer_masterDataModel.getCivilDetails()) {
                    MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel2 = new MMGCvlMeasurementResponseModel(mmgCvlMeasurementResponseModel1.getSLNO(), mmgCvlMeasurementResponseModel1.getMCD_MATERIAL_ID(), mmgCvlMeasurementResponseModel1.getMCD_MATERIAL_NAME(), mmgCvlMeasurementResponseModel1.getMCD_ISDROPDOWN(), mmgCvlMeasurementResponseModel1.getMCD_ISQUANTITY(), mmgCvlMeasurementResponseModel1.getDDLID(), mmgCvlMeasurementResponseModel1.getQUANTITY(), mmgCvlMeasurementResponseModel1.getLENGTH(), mmgCvlMeasurementResponseModel1.getWIDTH(), mmgCvlMeasurementResponseModel1.getDEPTH());
                    realmOperations.addCvlMesurementDet(mmgCvlMeasurementResponseModel2);
                }
                //   }

                MDialDigitModel mDialDigitModel = realmOperations.getDialDigitExists();

                realmOperations.deletDialDigitTable();
                for (MDialDigitModel mDialDigitModel1 : mmg_issuetoMeterFixer_masterDataModel.getDialDigit()) {
                    MDialDigitModel mDialDigitModel2 = new MDialDigitModel(mDialDigitModel1.getDIGITTEXT(), mDialDigitModel1.getDIGITID());
                    realmOperations.addDialDigit(mDialDigitModel2);
                }

                mmgMasterProgress.dismiss();

            } catch (Exception e) {
                Log.e("Exception", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid_MMG", error);
            }

            getBilllingAdjustmentdataDownload();

        }
    }




}
