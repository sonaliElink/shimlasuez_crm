package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.ComplaintRegisterResponseModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.DuplicateComplaintModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.HistoryModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.RequestModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.ComplaintByModel;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSourceType;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.DocSourceModel;
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

import static elink.suezShimla.water.crm.Base.App.getContext;

public class ComplaintRegistrationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Bitmap rotatedBitmap;
    private Context mCon;
    private MaterialButton registerButton, btn_upload_document;
    private MaterialDialog progress, progressPhoto;
    private AppCompatSpinner originSpinner, complaintTypeSpinner, complaintSubTypeSpinner, sourceTypeSpinner, prioritySpinner, serviceTypeSpinner,
            meterNoSpinner, namePrefixSpinner, complaintForSpinner, complaintBySpinner, TypeSpinner, subTypeSpinner;
    private TextInputLayout firstNameInputLayout, middleNameInputLayout, lastNameInputLayout, informDateInputLayout, sendSmsInputLayout, lastBillAmtInputLayout,
            arrearAmtInputLayout, remarkTextInputLayout, whatsAppInputLayout;
    private TextInputEditText firstNameEditText, middleNameEditText, lastNameEditText, informDateEditText, sendSmsEditText, lastBillAmtEditText,
            arrearAmtEditText, remarkEditText, whatsAppEditText;

    private TextView compByTv, compTypeTv, compSubTypeTv, tv_complaint_number_upload;

    private ImageView uploadDoc;

    private ArrayList<String> documentImages = new ArrayList<>();
    Intent cam_intent;
    public static final int RequestPermissionCode = 1;
    private int reqcode_img = 1010;
    String base64String = "";
    public static final int REQUEST_CAMERA = 10;
    File storageDir;
    private String imageName;
    private static int PROFILE_CAMERA = 11;
    double newImageHeight = 512.0;
    int imageQuality = 95;
    // string to get from Intent & 1st parameter values;
    private String firstName, middleName, lastName, lastBillAmount, arrearAmt, mobileNo, consumerNoStr, referanceNoStr, emailStr,
            value1, value2, subZoneStr, statusStr, fullName = "", mobStr = "", complaintById = "", departmentID = "", mainComplaintID = "", mainComplaintCode = "",
            whatsAppNumberStr = "", isRepeatCallStr = "";


    private int hdnTheft, lblSAPhase, zoneStr;

    // rest parameters
    private String originStr, originIdStr, complaintTypeStr, complaintTypeIdStr, complaintSubTypeStr, complaintSubTypeIdStr, sourceTypeStr, sourceTypeIdStr = "5",
            priorityStr, informDateStr, remarkStr, meterNoStr, yearMonthStr, hdnYearMonth, hdnBaStr, sendSmsStr, namePrefixStr, firstNameStr = "", meterNo,
            middleNameStr, lastNameStr, complaintForStr, oldComplaintNoStr, empCodeStr, ipAddressStr, zoneId, subZoneId, compOriginStr = "1", landmark = "", city = "",
            postal_code = "", mobileNoStr = "", serviceId = "", serviceTypeStr = "", addressStr = "", serviceComplaintSubTypeStr = "", serviceType = "", remarkString = "";

    private String serviceComplaintSubTypeStrOne = "", serviceComplaintSubTypeStrTwo = "", headerStr = "";
    private ArrayAdapter complaintByArrayAdapter, sourceTypeArrayAdapter;

    private String firstParameter;

    private int complaintTypeId, complaintSubTypeId;

    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String jsonResponse = "", jsonPhotoResponse = "", jsonDuplicateResponse = "", jsonRequestResponse = "", jsonResponseDateTime = "", jsonRequestRepeatCallResponse = "", complaintTypeName, complaintSubTypeName, complaintForId = "", sourceTypeId = "", originId = "";
    private RealmOperations realmOperations;

    private ComplaintTypeModel complaintTypeModel;

    private DocSourceModel docSourceModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private DownloadSourceType complaintSourceModel;
    private ComplaintByModel model_complaint_by;

    private ArrayList<String> meterNumberList, complaintTypeList, complaintSubTypeList, sourceTypeList, docTypeList, docTypeIdList, docSubTypeList, docSubTypeIdList;

    private ArrayAdapter originAdapter, meterNumberAdapter, complaintTypeAdapter, complaintSubTypeAdapter, sourceTypeAdapter, docTypeAdapter, docSubTypeAdapter;

    AlertDialog.Builder builder;

    LinearLayout ll_complaint, ll_upload_document;

    String docSource = "", complaintCMTMCode = "";

    private DocTypeModel docTypeModel;
    private DocSubTypeModel docSubTypeModel;

    private List<DocTypeModel> docTypeModelList;
    private List<DocSubTypeModel> docSubTypeModelList;

    private String typeString = "", docTypeId = "", subtypeString = "", docSubTypeId = "", duplicateCOMstr = "";

    String complRefComNoSeq = "", slaStr = "", todayRepeatCount = "", complaintByName = "", comFWDSecDepartmetId = "";
    int wCountComCalls;
    List<HistoryModel> historyModelList = new ArrayList<>();
    ArrayList<String> complaintSubArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_registraion);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        realmOperations = new RealmOperations(mCon);
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        builder = new AlertDialog.Builder(this);


        if (connection.isConnectingToInternet()) {
            GetDateTime getDateTime = new GetDateTime();
            getDateTime.execute();
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

        value1 = "C";
        value2 = "C";
        hdnTheft = 0;
        lblSAPhase = 0;
        //compOriginStr = "2";
        zoneStr = 5;
        subZoneStr = "93-SZ01";

        try {
            // Decrypt EmpCode
            empCodeStr = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        departmentID = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEPARTMENTID);

        try {
            departmentID=  new AesAlgorithm().decrypt(departmentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("departmentID", departmentID);
        complaintTypeList = realmOperations.fetchComplaintTypeByDept(departmentID);

        try {
            if (complaintTypeList.size() != 0) {
                String cc = "";
                for (int i = 0; i < complaintTypeList.size(); i++) {
                    cc = complaintTypeList.get(i);
                }
                Log.d("cc", cc);
                complaintTypeModel = realmOperations.fetchComplaintTypeByName(cc);
                mainComplaintID = String.valueOf(complaintTypeModel.getCMTM_ID());
                mainComplaintCode = complaintTypeModel.getCMTM_CODE();
                Log.d("cc", String.valueOf(complaintTypeId));

               /* docSourceModel = realmOperations.fetchDocSourceTypeByName(mainComplaintCode);
                docSource = docSourceModel.getDOCCOMPTYPE();*/
            }
        } catch (Exception e) {

        }

        consumerNoStr = getIntent().getStringExtra("consumerNo");
        firstName = getIntent().getStringExtra("firstName");
        middleName = getIntent().getStringExtra("middleName");
        lastName = getIntent().getStringExtra("lastName");
        lastBillAmount = getIntent().getStringExtra("lastBillAmt");
        arrearAmt = getIntent().getStringExtra("arrearAmt");
        mobileNo = getIntent().getStringExtra("sendSMS");
        emailStr = getIntent().getStringExtra("email");
        statusStr = getIntent().getStringExtra("status");
        zoneId = getIntent().getStringExtra("zone");
        subZoneId = getIntent().getStringExtra("subZone");
        landmark = getIntent().getStringExtra("landmark");
        city = getIntent().getStringExtra("city");
        postal_code = getIntent().getStringExtra("postal_code");
        mobileNoStr = getIntent().getStringExtra("mobileNoStr");
        //serviceId = getIntent().getStringExtra("serviceId");
        addressStr = getIntent().getStringExtra("addressStr");
        meterNoStr = getIntent().getStringExtra("meterNoStr");
        //serviceId = getIntent().getStringExtra("serviceId");
        historyModelList = getIntent().getParcelableArrayListExtra("history");



       /* if (emailStr.equals("-NA-")) {
            emailStr = "";
        }*/
        if (firstName.trim().contains(" ")) {
            fullName = firstName;
        } else {
            fullName = firstName + " " + middleName + " " + lastName;
        }

        firstParameter = consumerNoStr + "," + fullName + "," + mobileNo + "," + emailStr + "," + lastBillAmount + "," + arrearAmt + "," + value1 + ","
                + zoneId + "," + value2 + "," + subZoneId + "," + statusStr + "," + hdnTheft;

        registerButton = findViewById(R.id.registerButton);
        btn_upload_document = findViewById(R.id.btn_upload_document);
        btn_upload_document.setOnClickListener(this);
        originSpinner = findViewById(R.id.originSpinner);
        originSpinner.setSelection(2);

        complaintTypeSpinner = findViewById(R.id.complaintTypeSpinner);
        complaintSubTypeSpinner = findViewById(R.id.complaintSubTypeSpinner);
        sourceTypeSpinner = findViewById(R.id.sourceTypeSpinner);
        namePrefixSpinner = findViewById(R.id.namePrefixSpinner);
        //complaintForSpinner = findViewById(R.id.complaintForSpinner);
        complaintBySpinner = findViewById(R.id.complaintBySpinner);
        complaintBySpinner.setSelection(0);
        TypeSpinner = findViewById(R.id.TypeSpinner);
        subTypeSpinner = findViewById(R.id.subTypeSpinner);
        meterNoSpinner = findViewById(R.id.meterNoSpinner);
        prioritySpinner = findViewById(R.id.prioritySpinner);
        serviceTypeSpinner = findViewById(R.id.serviceTypeSpinner);
        serviceTypeSpinner.setOnItemSelectedListener(this);

        firstNameInputLayout = findViewById(R.id.firstNameInputLayout);
        middleNameInputLayout = findViewById(R.id.middleNameInputLayout);
        lastNameInputLayout = findViewById(R.id.lastNameInputLayout);
        informDateInputLayout = findViewById(R.id.informDateInputLayout);
        sendSmsInputLayout = findViewById(R.id.sendSmsInputLayout);
        lastBillAmtInputLayout = findViewById(R.id.lastBillAmtInputLayout);
        arrearAmtInputLayout = findViewById(R.id.arrearAmtInputLayout);
        remarkTextInputLayout = findViewById(R.id.remarkTextInputLayout);
        whatsAppInputLayout = findViewById(R.id.whatsAppInputLayout);

        compByTv = findViewById(R.id.compByTv);//,
        compTypeTv = findViewById(R.id.compTypeTv);
        compSubTypeTv = findViewById(R.id.compSubTypeTv);
        tv_complaint_number_upload = findViewById(R.id.tv_complaint_number_upload);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        middleNameEditText = findViewById(R.id.middleNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        informDateEditText = findViewById(R.id.informDateEditText);
        sendSmsEditText = findViewById(R.id.sendSmsEditText);
        lastBillAmtEditText = findViewById(R.id.lastBillAmtEditText);
        arrearAmtEditText = findViewById(R.id.arrearAmtEditText);
        remarkEditText = findViewById(R.id.remarkEditText);
        whatsAppEditText = findViewById(R.id.whatsAppEditText);


        uploadDoc = findViewById(R.id.uploadDoc);
        ll_complaint = findViewById(R.id.ll_complaint);
        ll_upload_document = findViewById(R.id.ll_upload_document);


        firstNameEditText.setText(firstName);
        lastBillAmtEditText.setText(lastBillAmount);
        arrearAmtEditText.setText(arrearAmt);
        sendSmsEditText.setText(mobileNoStr);

        //informDateEditText.setText(informDateStr);

        meterNumberList = realmOperations.fetchMeterNumber();


        //  complaintSubTypeList = realmOperations.fetchComplaintSubType(complaintTypeId);

        meterNumberAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterNumberList);
        meterNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meterNoSpinner.setAdapter(meterNumberAdapter);

        //complaintTypeList = realmOperations.fetchComplaintType();
        complaintTypeList.clear();
        ArrayList<String> complaintTypeArrayList = new ArrayList<>();
        complaintTypeArrayList.add("Select");
        complaintTypeArrayList.addAll(complaintTypeList);

        complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintTypeArrayList);
        complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintTypeSpinner.setAdapter(complaintTypeAdapter);

        //    setComplaintByDropDown();
        setSourceTypeDropDown("2");


       /* complaintSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintSubTypeList);
        complaintSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintSubTypeSpinner.setAdapter(complaintSubTypeAdapter);*/

        originSpinner.setEnabled(false);

        complaintTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                complaintTypeName = complaintTypeSpinner.getSelectedItem().toString();
                if (complaintTypeName.equalsIgnoreCase("Select")) {
                    complaintSubTypeSpinner.setSelection(0);
                    complaintSubArrayList=new ArrayList<>();
                } else {
                    complaintSubArrayList=new ArrayList<>();
                    complaintTypeModel = realmOperations.fetchComplaintTypeByName(complaintTypeName);
                    complaintTypeId = complaintTypeModel.getCMTM_ID();
                    complaintCMTMCode = complaintTypeModel.getCMTM_CODE();
                    docSourceModel = realmOperations.fetchDocSourceTypeByName(complaintCMTMCode);
                    if (docSourceModel != null) {
                        docSource = docSourceModel.getDOCCOMPTYPE();
                        setDocSourceDropDown(docSource);
                        setSubDocSourceDropDown();
                        complaintTypeIdStr = String.valueOf(complaintTypeId);
                    } else {
                        Toast.makeText(mCon, "Data not found", Toast.LENGTH_SHORT).show();
                        complaintTypeSpinner.setSelection(0);
                    }

                    if (complaintTypeName.equalsIgnoreCase("Select")) {
                        complaintSubTypeSpinner.setSelection(0);
                        complaintSubArrayList=new ArrayList<>();
                    } else {
                        ArrayList<String> compByName = new ArrayList<>();
                     /*   if (serviceComplaintSubTypeStr.equalsIgnoreCase("")) {
                           // Toast.makeText(mCon, "Please select source type", Toast.LENGTH_SHORT).show();

                        } else {*/

                        compByName = realmOperations.fetchComplaintSubtypeList(complaintTypeId,serviceTypeStr);
                        complaintSubArrayList.add("Select");
                        complaintSubArrayList.addAll(compByName);
                        complaintSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintSubArrayList);
                        complaintSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        complaintSubTypeSpinner.setAdapter(complaintSubTypeAdapter);
                        // }
                    }
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
                if (complaintSubTypeName.equalsIgnoreCase("Select")) {

                } else {
                    complaintSubTypeModel = realmOperations.fetchComplaintSubTypeByName(complaintSubTypeName);
                    try {
                        complaintSubTypeId = complaintSubTypeModel.getCOMPLAINTSUBTYPEID();
                        complaintSubTypeIdStr = String.valueOf(complaintSubTypeId);

                        getDuplicateEntryCheck();
                    } catch (Exception e) {

                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        originSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String originSpinnerStr = originSpinner.getSelectedItem().toString();
                if (originSpinnerStr.equalsIgnoreCase("internal")) {
                    setSourceTypeDropDown("2");
                    compOriginStr = "1";
                } else if (originSpinnerStr.equalsIgnoreCase("external")) {
                    setSourceTypeDropDown("2");
                    compOriginStr = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sourceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceTypeStr = sourceTypeSpinner.getSelectedItem().toString();
                if (sourceTypeStr.equalsIgnoreCase("Select")) {
                    //   Toast.makeText(mCon, "Please select source type", Toast.LENGTH_SHORT).show();
                    complaintTypeSpinner.setSelection(0);
                    complaintSubTypeSpinner.setSelection(0);
                    prioritySpinner.setSelection(0);
                    complaintSubArrayList=new ArrayList<>();

                } else {
                    complaintSourceModel = realmOperations.fetchSourceByName(sourceTypeStr);
                    sourceTypeId = complaintSourceModel.getSOURCECODE();
                    sourceTypeIdStr = String.valueOf(sourceTypeId);
                    complaintSubTypeSpinner.setSelection(0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    priorityStr = "H";
                } else if (position == 1) {
                    priorityStr = "M";
                } else if (position == 2) {
                    priorityStr = "L";
                } else if (position == 3) {
                    priorityStr = "S";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        complaintBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String complaintByString = complaintBySpinner.getSelectedItem().toString();
                if (complaintByString.equalsIgnoreCase("Self")) {
                    complaintById = "1";
                    firstNameEditText.setEnabled(false);
                    firstNameEditText.setText(firstName);

                } else if (complaintByString.equalsIgnoreCase("On behalf of customer")) {
                    serviceType = serviceTypeSpinner.getSelectedItem().toString();
                    if(serviceType.equalsIgnoreCase("Select")){
                        Toast.makeText(mCon, "Please select source type", Toast.LENGTH_SHORT).show();
                        complaintBySpinner.setSelection(0);
                    }else {
                        if (serviceType.equalsIgnoreCase("Request")) {
                            complaintById = "2";
                            firstNameEditText.setEnabled(true);
                            firstNameInputLayout.setHint("Please enter requested by name *");
                            firstNameEditText.setText("");
                        } else {
                            complaintById = "2";
                            firstNameEditText.setEnabled(true);
                            firstNameEditText.setText("");
                        }
                    }

                }


                /*else {
                    model_complaint_by = realmOperations.fetchComplaintByName(complaintByString);
                    complaintById = model_complaint_by.getVCM_CATCD();
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        serviceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceType = serviceTypeSpinner.getSelectedItem().toString();
                if (serviceType.equalsIgnoreCase("Select")) {
                    serviceId = "0";
                    complaintTypeSpinner.setSelection(0);
                    complaintSubTypeSpinner.setSelection(0);
                    complaintSubArrayList = new ArrayList<>();
                    complaintSubArrayList.add("Select");
                    complaintSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintSubArrayList);
                    complaintSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    complaintSubTypeSpinner.setAdapter(complaintSubTypeAdapter);
                } else if (serviceType.equalsIgnoreCase("Complaint")) {
                    serviceId = "1";
                    serviceTypeStr = "C";

                    complaintTypeList = realmOperations.fetchComplaintTypeRegistrationList("1", serviceTypeStr);
                    ArrayList<String> complaintTypeArrayList = new ArrayList<>();
                    complaintTypeArrayList.add("Select");
                    complaintTypeArrayList.addAll(complaintTypeList);
                    firstNameInputLayout.setHint("Complainant");
                    compByTv.setText("Complaint By");
                    complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintTypeArrayList);
                    complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    complaintTypeSpinner.setAdapter(complaintTypeAdapter);


                } else if (serviceType.equalsIgnoreCase("Request")) {
                    serviceId = "2";
                    serviceTypeStr = "R";

                    serviceComplaintSubTypeStr = null;

                    complaintTypeList = realmOperations.fetchComplaintTypeRegistrationList("1", serviceTypeStr);
                    informDateInputLayout.setHint("Request Date");
                    compByTv.setText("Request By");
                    firstNameInputLayout.setHint("Requested by");
                    // compTypeTv.setText("Request type");
                    //   compSubTypeTv.setText("Request Subtype");
                    setTitle("Request Registration");
                    ArrayList<String> complaintTypeArrayList = new ArrayList<>();
                    complaintTypeArrayList.add("Select");
                    complaintTypeArrayList.addAll(complaintTypeList);

                    complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintTypeArrayList);
                    complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    complaintTypeSpinner.setAdapter(complaintTypeAdapter);

                } else if (serviceType.equalsIgnoreCase("Query")) {
                    serviceId = "3";
                    serviceTypeStr = "Q";

                    complaintTypeList = realmOperations.fetchComplaintTypeRegistrationList("1", serviceTypeStr);
                    ArrayList<String> complaintTypeArrayList = new ArrayList<>();
                    complaintTypeArrayList.add("Select");
                    complaintTypeArrayList.addAll(complaintTypeList);


                    complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintTypeArrayList);
                    complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    complaintTypeSpinner.setAdapter(complaintTypeAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableRuntimePermission();
                // cam_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    //startCameraIntent(REQUEST_CAMERA);

                    showUploadPrescriptionDialog("", 3);
                    //   startActivityForResult(cam_intent, reqcode_img);
                } catch (Exception ex) {
                    Log.e("Exception: " + ex.getMessage(), "");
                }
            }
        });

        registerButton.setOnClickListener(this);


    }

    private void setComplaintCatgorySpinner(String typeId) {
        complaintTypeModel = realmOperations.fetchComplaintTypeByNameRegistration(typeId);
        ArrayList<String> complaintTypeArrayList = new ArrayList<>();
        complaintTypeArrayList.add("Select");
        complaintTypeArrayList.addAll(complaintTypeList);

        complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintTypeArrayList);
        complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintTypeSpinner.setAdapter(complaintTypeAdapter);
    }

    private boolean checkValidationforMobileNumber() {
        if ((whatsAppEditText.getText().toString().length() != 10)
                || (whatsAppEditText.getText().toString().equals("6666666666"))
                || (whatsAppEditText.getText().toString().equals("7777777777"))
                || (whatsAppEditText.getText().toString().equals("8888888888"))
                || (whatsAppEditText.getText().toString().equals("9999999999"))
                || (Integer.parseInt(whatsAppEditText.getText().toString()
                .trim().substring(0, 1)) < 6)) {
            Toast.makeText(this, "Please enter valid number", Toast.LENGTH_SHORT).show();
            whatsAppEditText.requestFocus();
            return false;
        }
        return true;


    }

    private void showUploadPrescriptionDialog(String s, int requestCode) {
        startCameraIntent(REQUEST_CAMERA + requestCode);

    }


    private void startCameraIntent(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's activity_login ic_camera activity to handle the intent
      //  if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
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
       // }
    }


    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        Bitmap bitmap = null;
        Uri imageUri;
        if (requestcode == 13 && resultcode == this.RESULT_OK) {

            imageUri = Uri.fromFile(new File(imageName));

            uploadDoc.setImageURI(imageUri);
            uploadDoc.setVisibility(View.VISIBLE);
            uploadDoc.setPadding(0, 0, 0, 0);
            Matrix matrix = new Matrix();
            bitmap = ((BitmapDrawable) uploadDoc.getDrawable()).getBitmap();
            int nh = (int) (bitmap.getHeight() * (newImageHeight / bitmap.getWidth()));
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) newImageHeight, nh, true);
            rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

            // iv_visiting_card_proprietor.setImageBitmap(scaled);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            uploadDoc.setImageBitmap(bitmap);
            base64String = Base64.encodeToString(b, Base64.DEFAULT);
            if (!base64String.equalsIgnoreCase("")) {
                documentImages.add(base64String);

            } else {
                base64String = "NOPHOTO";
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


    private void setDocSourceDropDown(String docSource) {

        docTypeModelList = realmOperations.fetchDocTypeName(docSource);

        docTypeList = new ArrayList<>();
        docTypeIdList = new ArrayList<>();

        for (DocTypeModel docTypeModel : docTypeModelList) {
            docTypeList.add(docTypeModel.getDOCUMENTTYPE());
            docTypeIdList.add(docTypeModel.getDOCUMENTTYPEID());
            //Log.d("check", String.valueOf(zoneName));

        }

        ArrayList<String> docTypeArrayList = new ArrayList<>();
        docTypeArrayList.add("Select");
        docTypeArrayList.addAll(docTypeList);
        Log.d("docTypeList", "" + docTypeList);
        docTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, docTypeArrayList);
        docTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeSpinner.setAdapter(docTypeAdapter);
        if (docTypeList.size() == 1) {
            TypeSpinner.setSelection(1);
        }
        TypeSpinner.setOnItemSelectedListener(this);


    }

    private void setSubDocSourceDropDown() {


        ArrayList<String> docSubTypeArrayList = new ArrayList<>();
        docSubTypeArrayList.add("Select");
        docSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, docSubTypeArrayList);
        docSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subTypeSpinner.setAdapter(docSubTypeAdapter);
        subTypeSpinner.setOnItemSelectedListener(this);


    }

    private void getDuplicateEntryCheck() {

        String params[] = new String[4];
        params[0] = consumerNoStr;
        params[1] = complaintTypeIdStr;
        params[2] = complaintSubTypeIdStr;
        params[3] = serviceTypeStr;

        if (connection.isConnectingToInternet()) {
            GetDuplicateEntry getDuplicateEntry = new GetDuplicateEntry();
            getDuplicateEntry.execute(params);

        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.TypeSpinner: {
                typeString = TypeSpinner.getSelectedItem().toString();
                String poss = String.valueOf(TypeSpinner.getSelectedItemPosition());


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
                String poss = String.valueOf(TypeSpinner.getSelectedItemPosition());


                if (subtypeString.equalsIgnoreCase("Select")) {

                } else {


                    docSubTypeModel = realmOperations.fetchDocSubTypeId(subtypeString, docTypeId);
                    try {
                        if (docSubTypeModel != null) {
                            docSubTypeId = String.valueOf(docSubTypeModel.getDOCUMENTTYPEID());
                        } else {
                            Toast.makeText(mCon, "Check maaster data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception exception) {
                        Toast.makeText(mCon, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
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
        if (docSubTypeList.size() == 1) {
            subTypeSpinner.setSelection(1);
        }
        subTypeSpinner.setOnItemSelectedListener(this);
    }


    private class GetDuplicateEntry extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[4];
                paraName[0] = "ServiceNo";
                paraName[1] = "CompType";
                paraName[2] = "CompSubType";
                paraName[3] = "ServiceType";

                jsonDuplicateResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Check_DuplicateComplaints, params, paraName);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {

                DuplicateComplaintModel[] duplicateComplaintModels = gson.fromJson(jsonDuplicateResponse, DuplicateComplaintModel[].class);
                if (duplicateComplaintModels.length > 0) {
                    String subCategoryTypeStr = "", originStr = "";
                    boolean duplicate = false;
                    for (DuplicateComplaintModel duplicateComplaintModel : duplicateComplaintModels) {
                        duplicateCOMstr = duplicateComplaintModel.getCOMNO();
                        subCategoryTypeStr = duplicateComplaintModel.getFRM_REASONNAME();
                        originStr = duplicateComplaintModel.getORIGIN();
                        complRefComNoSeq = duplicateComplaintModel.getCOM_NO_SEQUENCE();
                        wCountComCalls = 1 + Integer.parseInt(duplicateComplaintModel.getCOM_CALLS());
                        slaStr = duplicateComplaintModel.getSLA();
                        if (slaStr == null) {
                            slaStr = "";
                        }
                        todayRepeatCount = duplicateComplaintModel.getTODAYREPEATCOUNT();
                        complaintByName = duplicateComplaintModel.getCOMPBY();
                        comFWDSecDepartmetId = duplicateComplaintModel.getCOM_FWDSEC1();

                        if (duplicateComplaintModel.getSUBTYPE() == complaintSubTypeId) {
                            // duplicateCOMstr=  duplicateComplaintModel.getCOMNO();
                            duplicate = true;
                            break;
                        } else {
                            duplicate = false;
                        }

                    }
                    if (duplicate) {
                        String messagae = serviceType + " " + duplicateCOMstr + " " + getResources().getString(R.string.allready_register);

                        // String messagae = duplicateCOMstr+" already registered, You can give Repeat Call. ";
                        builder.setMessage(messagae)
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        toShowRemrkDialogBox(duplicateCOMstr, complRefComNoSeq, wCountComCalls, slaStr, todayRepeatCount, complaintByName, comFWDSecDepartmetId);
                                        //  InsterConfirmData();
                                        dialog.dismiss();
                                    }

                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                complaintSubTypeSpinner.setSelection(0);

                                dialog.cancel();

                            }

                        });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle(getResources().getString(R.string.information));
                        alert.show();

                        //  Toast.makeText(mCon, duplicateCOMstr+" already registered, You can give Repeat Call.", Toast.LENGTH_SHORT).show();

                    } else {
                        String messagae = "";
                     /*
                        External Complaint for Pipe line not layed - WC22102257867 is already pending,
                                Do you want to proceed?*/
                        if (serviceTypeStr.equalsIgnoreCase("C")) {
                            messagae = originStr + " " + getResources().getString(R.string.complaint) + " for" + " " + subCategoryTypeStr + " " + duplicateCOMstr + " " + getResources().getString(R.string.do_you_want_to_proceed);
                        } else if (serviceTypeStr.equalsIgnoreCase("R")) {
                            messagae = originStr + " " + getResources().getString(R.string.request) + " for" + " " + subCategoryTypeStr + " " + duplicateCOMstr + " " + getResources().getString(R.string.do_you_want_to_proceed);
                        }

                        builder.setMessage(messagae)
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        //  InsterConfirmData();
                                        dialog.dismiss();


                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        complaintSubTypeSpinner.setSelection(0);

                                        dialog.cancel();

                                    }
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle(getResources().getString(R.string.complaint));
                        alert.show();


                    }

                } else {

                }
              /*  if(jsonResponse.equalsIgnoreCase("[]")){
                    if(commisioned_noncommisioned.equalsIgnoreCase("N")){
                        saveAndExit.setEnabled(true);

                    }else {

                    }
                }*/

            } catch (Exception e) {
                Log.e("Exception", e.toString());

                String error = e.toString();
                ErrorClass.errorData(mCon, "New Meter Details Fragment", "Get Dial digit task", error);
            }
        }

        @Override
        protected void onPreExecute() {
          /*  digit_progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();*/
        }
    }

    private void toShowRemrkDialogBox(String requestNumber, String complRefStr, int wCountCompCallsStr, String slaStatusStr, String totalRepeatCountStr, String compByNameStr, String comFWDSecDepartmetIdStr) {
        String message = "Remark";

        if (slaStatusStr.equalsIgnoreCase("Beyond SLA") && (totalRepeatCountStr.equalsIgnoreCase("0"))) {
            message = "Repeat Call";
            headerStr = serviceType + " repeat call";
            isRepeatCallStr = "Y";
        } else {
            headerStr = "Remark";
            isRepeatCallStr = "N";

        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.text_inpu_remark, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.input);

        dialogBuilder.setTitle(message);
        /*    dialogBuilder.setMessage("Enter text below");*/
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                remarkString = edt.getText().toString();

                complaintRepeatCall(remarkString, requestNumber, complRefStr, wCountCompCallsStr, slaStatusStr, totalRepeatCountStr, compByNameStr, comFWDSecDepartmetIdStr, headerStr);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                complaintSubTypeSpinner.setSelection(0);

                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void complaintRepeatCall(String remarkString, String requestNumber, String compRefStr, int wCountCompCallsStr, String slaStatusStr, String totalRepeatCountStr, String compByNameStr, String comFWDSecDepartmetIdStr, String headerStr) {

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[13];
        params[0] = compRefStr;
        params[1] = String.valueOf(wCountCompCallsStr);
        params[2] = complaintTypeIdStr;
        params[3] = complaintSubTypeIdStr;
        params[4] = consumerNoStr;
        params[5] = headerStr;
        params[6] = empcode;
        params[7] = PreferenceUtil.getMac();
        params[8] = compByNameStr;
        params[9] = remarkString;
        params[10] = comFWDSecDepartmetIdStr;
        params[11] = "C";
        params[12] = isRepeatCallStr;


        Log.d("check", Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            ComplaintRepeatCall complaintRepeatCall = new ComplaintRepeatCall();
            complaintRepeatCall.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }


    private boolean checkValidation() {
        sourceTypeStr = serviceTypeSpinner.getSelectedItem().toString().trim();
        complaintTypeStr = complaintTypeSpinner.getSelectedItem().toString().trim();
        complaintSubTypeStr = complaintSubTypeSpinner.getSelectedItem().toString().trim();
        priorityStr = prioritySpinner.getSelectedItem().toString().trim();
        sendSmsStr = sendSmsEditText.getText().toString().trim();
        remarkStr = remarkEditText.getText().toString().trim();
        firstNameStr = firstNameEditText.getText().toString();
        whatsAppNumberStr = whatsAppEditText.getText().toString().trim();
        String MobilePattern = "^[6-9][0-9]{9}$";

        if (sourceTypeStr.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, "Please select service type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (complaintTypeStr.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, "Please select category", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (complaintSubTypeStr.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, "Please select sub category", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (priorityStr.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, "Please select priority type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (firstNameStr.equalsIgnoreCase("")) {
            if (sourceTypeStr.equalsIgnoreCase("Request")) {
                Toast.makeText(mCon, "Please enter requested by name", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mCon, "Please enter complainant by name", Toast.LENGTH_SHORT).show();

            }
            return false;
        }



        if (!sendSmsStr.equals("")) {
            int length = sendSmsStr.length();
            if (length != 10) {
                Toast.makeText(mCon, "" + getResources().getString(R.string.please_enter_valid_sms_number), Toast.LENGTH_SHORT).show();
                sendSmsEditText.requestFocus();
                return false;
            }
        }

        if (!sendSmsStr.matches(MobilePattern)) {

            Toast.makeText(mCon, getResources().getString(R.string.please_enter_valid_sms_number), Toast.LENGTH_SHORT).show();
            sendSmsEditText.requestFocus();

            return false;
        }else{
            sendSmsInputLayout.setError(null);
            sendSmsEditText.setError(null);
        }
        if (!whatsAppNumberStr.equals("")) {
            int length = whatsAppNumberStr.length();
            if (length>0) {
                if (!whatsAppNumberStr.matches(MobilePattern)) {

                    Toast.makeText(mCon, getResources().getString(R.string.please_enter_valid_mobile_number), Toast.LENGTH_SHORT).show();
                    whatsAppEditText.requestFocus();

                    return false;
                }else{
                    whatsAppEditText.setError(null);
                }
            }
        }







        if (remarkStr.equalsIgnoreCase("")) {
            remarkTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            remarkEditText.requestFocus();
            return false;
        } else {
            remarkTextInputLayout.setError(null);
            remarkEditText.setError(null);

        }

        return true;

    }


    // Validate Complaint Request
    private void validateComplaintRequest() {
        //String MobilePattern = "[0-9]{10}";
        String MobilePattern = "^[7-9][0-9]{9}$";
        boolean isValidPriority = false, isValidFirstName = false,
                isValidMiddleName = false, isValidLastName = false, isValidInformDate = false, isValidSendSms = false, isValidRemark = false;

        if (priorityStr.equalsIgnoreCase("Priority*")) {
            TextView view = (TextView) prioritySpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidPriority = true;
            TextView view = (TextView) prioritySpinner.getSelectedView();
            view.setError(null);
        }

        if (TextUtils.isEmpty(firstNameStr)) {
            firstNameInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            firstNameInputLayout.setError(null);
            isValidFirstName = true;
        }

        if (TextUtils.isEmpty(informDateStr)) {
            informDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            informDateInputLayout.setError(null);
            isValidInformDate = true;
        }

        if (TextUtils.isEmpty(sendSmsStr)) {
            sendSmsInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            sendSmsInputLayout.setError(null);
            isValidSendSms = true;
        }

        if (TextUtils.isEmpty(remarkStr)) {
            remarkTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else if (remarkStr.toString().length() > 160) {
            remarkTextInputLayout.setError("Remarks 160 Character Only");
            isValidRemark = false;
        } else {
            remarkTextInputLayout.setError(null);
            isValidRemark = true;
        }
        if (sendSmsStr.toString().matches(MobilePattern)) {

            // Toast.makeText(getApplicationContext(), "phone number is valid", Toast.LENGTH_SHORT).show();
            isValidSendSms = true;

        } else if (!sendSmsStr.matches(MobilePattern)) {

            sendSmsInputLayout.setError("Please enter valid mobile number");
            //Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            isValidSendSms = false;
        }
        if (isValidPriority && isValidFirstName && isValidInformDate &&
                isValidSendSms && isValidRemark) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy h:mm a");
            String date = sdf.format(Calendar.getInstance().getTime());

            if (serviceId.equalsIgnoreCase("0")) {
                Toast.makeText(mCon, "Please select service type", Toast.LENGTH_SHORT).show();
            } else if (serviceId.equalsIgnoreCase("1")) {
                register_complaint();
            } else if (serviceId.equalsIgnoreCase("2")) {
                register_request();
            } /*else if (serviceId.equalsIgnoreCase("3")) {
                register_request();
            }*/

        }
    }///Ajit's code changes

    private void register_request() {
        String params[] = new String[23];

        params[0] = consumerNoStr;
        params[1] = fullName;
        params[2] = complaintTypeIdStr;
        params[3] = complaintSubTypeIdStr;
        params[4] = remarkStr;
        params[5] = mobileNoStr;
        params[6] = addressStr;
        params[7] = sourceTypeIdStr; // "SType";
        params[8] = "";                 //W_Modifiedby
        params[9] = PreferenceUtil.getMac();             //"W_IPadd";
        params[10] = informDateStr;                  //"W_Compdate";
        params[11] = compOriginStr;                //"Origin";
        params[12] = sourceTypeIdStr;            //"SourceTypeVal";
        params[13] = priorityStr;                //"Priority";

        params[14] = complaintById;
        params[15] = middleNameStr;
        params[16] = "";
        params[17] = landmark;
        params[18] = city;
        params[19] = postal_code;
        params[20] = emailStr;
        params[21] = mobileNoStr;
        params[22] = "";

        Log.d("check", Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            RequestRegister requestRegister = new RequestRegister();
            requestRegister.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void register_complaint() {

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[30];

        params[0] = firstParameter;
        params[1] = complaintTypeIdStr;
        params[2] = informDateStr;
        params[3] = complaintSubTypeIdStr;
        params[4] = remarkStr;
        params[5] = meterNoStr;
        params[6] = "";
        params[7] = firstNameStr;
        params[8] = middleNameStr;
        params[9] = lastNameStr;
        params[10] = compOriginStr;
        params[11] = complaintForStr;
        params[12] = sourceTypeStr;
        params[13] = sourceTypeIdStr;
        params[14] = sendSmsStr;
        params[15] = priorityStr;
        params[16] = empcode;

        params[17] = PreferenceUtil.getMac();
        params[18] = complaintById;
        params[19] = middleNameStr;
        params[20] = addressStr;
        params[21] = "";
        params[22] = landmark; //
        params[23] = city;
        params[24] = postal_code;
        params[25] = mobileNoStr;
        params[26] = "0";
        params[27] = emailStr;
        params[28] = serviceTypeStr;
        params[29] = whatsAppNumberStr;

        Log.d("check", Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            ComplaintRegister complaintRegister = new ComplaintRegister();
            complaintRegister.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void setComplaintByDropDown() {
        ArrayList<String> compByName = new ArrayList<>();
        compByName = realmOperations.fetchComplaintByName();
        ArrayList<String> commByList = new ArrayList<>();
        commByList.add("None");
        commByList.addAll(compByName);

        complaintByArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, commByList);
        complaintByArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintBySpinner.setAdapter(complaintByArrayAdapter);
    }

    private void setSourceTypeDropDown(String orginId) {
        this.originId = orginId;
        sourceTypeList = realmOperations.fetchSourceTypeName(orginId);
        ArrayList<String> sourceTypeArrayList = new ArrayList<>();
        sourceTypeArrayList.add("Select");
        sourceTypeArrayList.addAll(sourceTypeList);
        sourceTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, sourceTypeArrayList);
        sourceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceTypeSpinner.setAdapter(sourceTypeAdapter);
        int position = sourceTypeArrayList.indexOf("Mobile APP");


        sourceTypeSpinner.setSelection(position);
        sourceTypeSpinner.setOnItemSelectedListener(this);
        sourceTypeSpinner.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButton: {
                if (checkValidation()) {
                    showSubmitAlertDilogBox();
                }
            }
            break;
            case R.id.btn_upload_document: {
                if (checkPhotoValidation()) {

                    uploadPhoto();
                }
            }
            break;

            default:
                break;
        }

    }

    private void uploadPhoto() {

        String params[] = new String[11];

        params[0] = base64String;
        params[1] = consumerNoStr;
        params[2] = referanceNoStr;
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


    private void showSubmitAlertDilogBox() {

        builder.setMessage(getResources().getString(R.string.are_you_sure_want_to_submit))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        register_complaint();

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
        alert.setTitle(getResources().getString(R.string.information));
        alert.show();
    }

    @SuppressLint("StaticFieldLeak")
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
                    MessageWindow.msgComplaintWindow(mCon, "Document uploaded successfully", "Success", MainActivity.class);


                } else {
                    //Toast.makeText(mCon, "Failed to upload document", Toast.LENGTH_SHORT).show();
                    MessageWindow.errorWindow(mCon, "Failed to upload document");
                    finish();


                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "" + jsonPhotoResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintRegisterActivity", "register complaint button", error);
            }
            progressPhoto.dismiss();
        }
    }

    private boolean checkPhotoValidation() {

        typeString = TypeSpinner.getSelectedItem().toString();
        subtypeString = subTypeSpinner.getSelectedItem().toString();
        if (typeString.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, "Please select type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (subtypeString.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, "Please select sub type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (base64String.equalsIgnoreCase("")) {
            Toast.makeText(mCon, "Please capture photo", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
                String paraName[] = new String[30];
                paraName[0] = "CommaSepareted";
                paraName[1] = "CompType";
                paraName[2] = "InformedDt";
                paraName[3] = "CompSubType";
                paraName[4] = "Remarks";
                paraName[5] = "Meter";
                paraName[6] = "Initial";
                paraName[7] = "FName";
                paraName[8] = "MName";
                paraName[9] = "LName";
                paraName[10] = "ComplOrigin";
                paraName[11] = "ComplaintFor";
                paraName[12] = "SourceType";
                paraName[13] = "SourceTypeValue";
                paraName[14] = "Mobile";
                paraName[15] = "Priority";
                paraName[16] = "Emp_Code";
                paraName[17] = "IP";
                paraName[18] = "COMRegBy";
                paraName[19] = "FatherName";
                paraName[20] = "Address";
                paraName[21] = "Locality";
                paraName[22] = "Landmark";
                paraName[23] = "City";
                paraName[24] = "Pin";
                paraName[25] = "RegMobile1";
                paraName[26] = "RegMobile2";
                paraName[27] = "RegEmail";
                paraName[28] = "ServiceType";
                paraName[29] = "WhatsApp";


                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.ComplaintRegistration, params, paraName);
                Log.d("check", "json : " + jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                ComplaintRegisterResponseModel enums = gson.fromJson(jsonResponse, ComplaintRegisterResponseModel.class);
                Log.d("check", "" + enums);
                if (enums.getInnerHTML() != null && !enums.getInnerHTML().equals("")) {
                    //Toast.makeText(mCon, enums.getInnerHTML(), Toast.LENGTH_LONG).show();
                    referanceNoStr = enums.getComplaint_Number();

                    messageAlertBox(enums.getInnerHTML());

                } else {
                    Toast.makeText(mCon, enums.getMsg(), Toast.LENGTH_LONG).show();
                    ll_complaint.setVisibility(View.GONE);
                    ll_upload_document.setVisibility(View.VISIBLE);
                }
                if (enums.getMsg().equalsIgnoreCase("Duplicate")) {
                    MessageWindow.throwOutFromWindow(mCon, "Complaint with same type and subtype is already registered.", "Alert", ComplaintHistoryActivity.class);
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "" + jsonResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintRegisterActivity", "register complaint button", error);
            }
            progress.dismiss();
        }
    }

    private void messageAlertBox(String innerHTML) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ComplaintRegistrationActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(getResources().getString(R.string.alert));
        alertDialog.setMessage(innerHTML);
        //alertDialog.setMessage(getResources().getString(R.string.complaint_no_) + " : " + referanceNoStr);
        alertDialog.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                ll_complaint.setVisibility(View.GONE);
                ll_upload_document.setVisibility(View.VISIBLE);
                tv_complaint_number_upload.setText(referanceNoStr);

                //   finish();
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    private class RequestRegister extends AsyncTask<String, Void, Void> {
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
                String paraName[] = new String[23];
                paraName[0] = "W_ServiceNo";
                paraName[1] = "name";
                paraName[2] = "W_Comptype";
                paraName[3] = "W_SubType";
                paraName[4] = "W_Remarks";
                paraName[5] = "w_CallerContactNo";
                paraName[6] = "W_Address";
                paraName[7] = "SType";
                paraName[8] = "W_Modifiedby";
                paraName[9] = "W_IPadd";
                paraName[10] = "W_Compdate";
                paraName[11] = "Origin";
                paraName[12] = "SourceTypeVal";
                paraName[13] = "Priority";
                paraName[14] = "COMRegBy";
                paraName[15] = "FatherName";
                paraName[16] = "Locality";
                paraName[17] = "Landmark";
                paraName[18] = "City";
                paraName[19] = "Pin";
                paraName[20] = "RegEmail";
                paraName[21] = "Mobile1";
                paraName[22] = "Mobile2";

                jsonRequestResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Request_Registeration, params, paraName);
                Log.d("check", "json : " + jsonRequestResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                RequestModel enums = gson.fromJson(jsonRequestResponse, RequestModel.class);

                if (enums.getMessage() != null && enums.getMessage().equals("200")) {
                    Toast.makeText(mCon, "Request registered successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else if (enums.getMessage() != null && enums.getMessage().equals("500")) {
                    Toast.makeText(mCon, "Please check details before registering", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(mCon, "Something went wrong", Toast.LENGTH_LONG).show();
                    finish();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "" + jsonRequestResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintRegisterActivity", "register complaint button", error);
            }
            progress.dismiss();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class ComplaintRepeatCall extends AsyncTask<String, Void, Void> {
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
                String paraName[] = new String[13];
                paraName[0] = "ComplRef";
                paraName[1] = "W_Count";
                paraName[2] = "ComType";
                paraName[3] = "SubTypeId";
                paraName[4] = "ConsumerNo";
                paraName[5] = "Header";
                paraName[6] = "EmpCode";
                paraName[7] = "IP";
                paraName[8] = "ComBy";
                paraName[9] = "Remark";
                paraName[10] = "w_Section";
                paraName[11] = "ComplaintType";
                paraName[12] = "IsRepeatCall ";

                jsonRequestRepeatCallResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Complaint_Repeat_call, params, paraName);
                Log.d("check", "json : " + jsonRequestRepeatCallResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {

                String[] enums = gson.fromJson(jsonRequestRepeatCallResponse, String[].class);
                if (enums[0].equalsIgnoreCase("Success")) {
                    if (isRepeatCallStr.equalsIgnoreCase("Y")) {
                        //remainder remark
                        MessageWindow.messageWindow(mCon, "Reminder Given Successfully.", "Success");

                    } else {
                        MessageWindow.messageWindow(mCon, "Remarks saved Successfully.", "Success");
                    }
                } else {

                    Toast.makeText(mCon, "Something went wrong", Toast.LENGTH_LONG).show();

                }

            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "" + jsonRequestResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintRegisterActivity", "register complaint button", error);
            }
            complaintSubTypeSpinner.setSelection(0);

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
                informDateEditText.setText(informDateStr);
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintRegistrationActivity", "GetDateTime from server", error);
            }
            progress.dismiss();
        }
    }


    /*  public void onActivityResult(int requestCode, int resultCode, Intent data) {
          if (resultCode == RESULT_OK && data != null) {
              Bitmap bitmap = (Bitmap) data.getExtras().get("data");

              if (requestCode == reqcode_img && resultCode == this.RESULT_OK) {


                  if (bitmap != null) {
                      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


                    //  bitmap.compress(Bitmap.CompressFormat.PNG, 00, outputStream);
                      byte[] profileImage = outputStream.toByteArray();

                      base64String = Base64.encodeToString(profileImage, Base64.DEFAULT);

                  } else {
                      base64String = "";
                  }
                  uploadDoc.setImageBitmap(bitmap);

              }
              Log.d("imgString" , "" +base64String);
          }
      }
  */
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

    private void exitDialog() {

        builder.setMessage(getResources().getString(R.string.are_you_sure_you_want_to_leave))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //finish();
                        //Toast.makeText(mCon, "Yes clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mCon, MainActivity.class));
                        finish();
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
        alert.setTitle(getResources().getString(R.string.information));
        alert.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (ll_upload_document.getVisibility() == View.VISIBLE) {
            exitDialog();
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
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }
}