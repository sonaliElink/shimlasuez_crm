package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGContEmpModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGVendorDetModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGScreenActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGContractorResponseDetail;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGCustDetModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import io.realm.RealmResults;

public class ContractorDetFragmentNew extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    LinearLayout liner_cntrctrNm, liner_venderCode, liner_cntrctrEmp, liner_materialHandover;
    EditText venderCodeEditText, contractorEmpEditText;
    Spinner removdMaterialSpinner, contractorNameSpinner, contractorEmpSpinner;
    String radiobuttonValStr = "", radioCommNonCommStr = "", materialHandoverStrDB = "", empCode = "";
    String otherContractor = "", otherContractorEmp = "", jsonResponseMeterBalance = "", jsonResponseForSealDeatils = "", jsonResponse = "", strCont_id, strCont_emp_code;
    String consumerNoStr, refNoStr, BU, PC, empName = "", fixerName = "",
            validMeterStr = "", pagename = "", MMGFIXER,
            jsonMeterInstallSaveResponse;
    public static String contractorNameStr = "", vendorCodeStr = "", contractorEmployeeStr = "",
            contractorId = "", contractorEmpIdStr = "", c_id = "", c_emp_id = "", other_code_id = "",
            removedMaterialName = "", contactorId = "", contactorEmpId = "", removedHandverIdStr = "";
    Context mCon;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private MaterialDialog progress, meterBalanceProgressDialog;
    TextView vendor_other_code;
    SendCntrctDetails sendCntrctDetails;
    private RealmOperations realmOperations;
    ArrayAdapter contractorAdapter, contractorEmpAdapter, removedMatrialAdapter;
    private MMGVendorDetModel mmgVendorDetModel;
    private MMGContEmpModel mmgContEmpModel;
    private Boolean strRemovedMaterialHandoverEnable = true;

    int spinPosition;
    RealmResults<MMGContEmpModel> mmgContEmpModelRealmResults;
    ArrayList<String> contractorEmpList = new ArrayList<>();
    ArrayList<String> contractorEmpIdList = new ArrayList<>();
    int spinnerPosition, index;
    private ArrayList<String> contractorEmployee = new ArrayList<>();
    private ArrayList<String> contractorName = new ArrayList<>();
    ArrayList<String> contractorList;

    String MI_METERINSTALLID, MI_ACTION, MI_CONSUMER, MI_BU, MI_PC, MI_REFNO, MI_O_SIZE, MI_O_METER, MI_O_MAKE, MI_O_PREVIOUSREADING, MI_O_FINALREADING, MI_O_FINALSTATUS, MI_O_REASON, MI_O_METERTYPE, dataFound,
            MI_N_MAKE, MI_N_SIZE, MI_N_SEAL, MI_N_METER, MI_INSTALLATIONDATE, MI_N_INITIALREADING, MI_N_METERTYPE, MI_N_METERLOCATION, MI_N_ISPROTECTED, MI_PROPERTYTAXNO, MI_N_ISMETERHANDOVER,
            MI_CONTRACTOR, MI_CONTRACTOREMP, MI_N_ISMATERIALHANDOVER, MI_PCCBEDDINGLEN = "", MI_PCCBEDDINGWID,
            MI_PCCBEDDINGDEP, MI_ROADCUTTINGTYPE, MI_ROADCUTTINGLEN, MI_ROADCUTTINGWID, MI_ROADCUTTINGDEP, MI_FROMNODE,
            MI_TONODE, MI_REGMOBILE, MI_ALTMOBILE, MI_GIS, MI_DMA, MI_SR, MI_MODIFIEDBY, MI_MODIFIEDDATE, MI_IP, MI_AUTHENTICATEDATE,
            MI_AUTHREJECTBY, MI_REJECTEDDATE, MI_STATUS, MI_ISACTIVE, MI_XMLMATERIAL = "", MI_XMLCIVIL = "", MI_O_OBSERVATION,
            MI_SOURCE, MI_ISCOMMISSIONED, MI_CONTRACTOROTHER, MI_CONTRACTOREMPOTHER, MI_N_DIGIT, MSRID, MI_N_SEAL_MAKE, MI_N_METER_BOX_MAKE;
    static String contList = "", zone_str = "", group_str = "", to_node_str = "",
            from_node_str, primary_mob_str, alternate_mob_str, gis_bid_str, dma_id, sr_id, property_assess,
            old_maker_code_id, mtrSizeId, serialNoStr, previousReading, finalReadingValStr, finalStatusIdStr, meterObservationId,
            meterReasonId, makerCodeIdStr, meterNumStr, installDtStr, sealNoStr, meterHandoverIdStr, protectedBoxStr,
            meterLocationStr, meterTypeStr, sizeStr, initialReadingStr, installDateStr, dial_digit, meterSizeIdStr, sealNumStr,
            initialReadingNoStr, meterTypeIdStr, meterLocationIdNo, protectedBoxIdNoStr, meterHandoverStr,
            taxNumStr, radiobuttonAction, sealmakeId, meterBoxmakeid;
    private static boolean isConsumerSubmitted = false, is_newmeter_submitted = false, isOldSubmitted = false, isContractorSubmitted = false;
    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";
    public ContractorDetFragmentNew() {
    }

    ArrayList<MMGCustDetModel> customerDetailList = new ArrayList<>();
    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = new ArrayList<>();
    ArrayList<InstallDetails> installDetails = new ArrayList<>();
    ArrayList<MMGValidateDetails> validateDetailList = new ArrayList<>();

    private Boolean isComingFromBack = false;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contractor_det, container, false);
        mCon = getActivity();
        realmOperations = new RealmOperations(mCon);
        gson = new Gson();
        invServices = new Invoke();
        connection = new ConnectionDetector(mCon);

        MMGScreenActivity activity = (MMGScreenActivity) getActivity();
        customerDetailList = activity.getMMGCustomerDetailsData();
        meterConnectionList = activity.getMMGMeterConnectionDetailsData();
        installDetails = activity.getMMGInstallDetailsData();
        validateDetailList = activity.getMmgvalidateDetailList();

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

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        time = df.format(c.getTime());

        checkTimes(dateParsing(STARTTIME), dateParsing(time), dateParsing(ENDTIME));
        initViews(view);

        return view;
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

    private void initViews(View view) {
        liner_cntrctrNm = view.findViewById(R.id.liner_cntrctrNm);
        liner_venderCode = view.findViewById(R.id.liner_venderCode);
        liner_cntrctrEmp = view.findViewById(R.id.liner_cntrctrEmp);
        liner_materialHandover = view.findViewById(R.id.liner_materialHandover);

        venderCodeEditText = view.findViewById(R.id.venderCodeEditText);
        contractorEmpEditText = view.findViewById(R.id.contractorEmpEditText);
        vendor_other_code = view.findViewById(R.id.vendor_other_code);

        removdMaterialSpinner = view.findViewById(R.id.removdMaterialSpinner);
        contractorNameSpinner = view.findViewById(R.id.contractorNameSpinner);
        contractorEmpSpinner = view.findViewById(R.id.contractorEmpSpinner);

        contractorEmpSpinner.setOnItemSelectedListener(this);
        contractorNameSpinner.setOnItemSelectedListener(this);

        Button submitButton = view.findViewById(R.id.submitButton);
        Button saveAndExit = view.findViewById(R.id.saveAndExit);

        submitButton.setOnClickListener(this);
        saveAndExit.setOnClickListener(this);

        try {
            if (pagename != null) {
                pagename = getArguments().getString("pagename");
                contList = getArguments().getString("contList");
            }
            consumerNoStr = getArguments().getString("consumerNoStr");
            refNoStr = getArguments().getString("refNoStr");
            BU = UtilitySharedPreferences.getPrefs(mCon, Constants.BU);
            PC = UtilitySharedPreferences.getPrefs(mCon, Constants.PC);
            empName = null;
            try {
                empName = new AesAlgorithm().decrypt(PreferenceUtil.getEmpName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);
            radioCommNonCommStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);
            materialHandoverStrDB = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MATERIALHANDOVER);
            meterHandoverStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MATERIALHANDOVER);
            MI_N_ISMETERHANDOVER = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MATERIALHANDOVER);
            empCode = UtilitySharedPreferences.getPrefs(getActivity(), AppConstant.EMPCODE);
            empCode = aes.decrypt( empCode);
            MMGFIXER = UtilitySharedPreferences.getPrefs(getActivity(), AppConstant.MMGFIXER);

        } catch (Exception ex) {
        }

        if (radiobuttonValStr.equalsIgnoreCase("S") || radiobuttonValStr.equalsIgnoreCase("MB") ||
                radiobuttonValStr.equalsIgnoreCase("N")) {//pinky added N condition
            strRemovedMaterialHandoverEnable = false;
            removdMaterialSpinner.setEnabled(false);
        }

        //setInstallMI_CONTRACTOR();
        contractorName = realmOperations.fetchVendorDetails();
        contractorList = new ArrayList<>();
        contractorList.add("--Select--");
        contractorList.addAll(contractorName);
        contractorList.add("MMG Employee");
//        contractorList.add("Other");//have to remove

        contractorAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, contractorList);
        contractorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contractorNameSpinner.setAdapter(contractorAdapter);
        //contractorNameSpinner.setOnItemSelectedListener(this);

        setOldFixerData();

        setFixerNameDropdown();

    //    setInstallMI_CONTRACTOREMP();

        materialHandDropdown();

    }

    private void setOldFixerData() {
        if (installDetails != null) {

            for (int k = 0; k <= installDetails.size() - 1; k++) {

                dataFound = installDetails.get(k).getNODATAFOUND();
                if (dataFound.equalsIgnoreCase("DataFound")) {

                    MI_METERINSTALLID = installDetails.get(k).getMI_METERINSTALLID();
                    // Log.e("MI_METERINSTALLID", "MI_METERINSTALLID : " + MI_METERINSTALLID);
                    MI_N_ISMATERIALHANDOVER = installDetails.get(k).getMI_N_ISMATERIALHANDOVER();
                    MI_CONTRACTOROTHER = installDetails.get(k).getMI_CONTRACTOROTHER();
                    MI_CONTRACTOREMPOTHER = installDetails.get(k).getMI_CONTRACTOREMPOTHER();

                    String checkContractor = installDetails.get(k).getMI_CONTRACTOR();
                    String checkContractorEmp = installDetails.get(k).getMI_CONTRACTOREMP();

                    if (!checkContractor.equals("") && !checkContractorEmp.equals("")) {
                        MI_CONTRACTOR = installDetails.get(k).getMI_CONTRACTOR();
                        MI_CONTRACTOREMP = installDetails.get(k).getMI_CONTRACTOREMP();

                        //Log.e("MI_CONTRACTOREMP", MI_CONTRACTOREMP);
                        
                        //pinky commented below on 07/04/2022
                        //contractorNameSpinner.setEnabled(false);
                        //contractorEmpSpinner.setEnabled(false);

                    } else {
                        contactorId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTACTORNAME);
                        contactorEmpId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTACTOREMP);
                        //Log.e("contactorId", "contactorId :" + contactorId);
                        //Log.e("contactorEmpId", "contactorEmpId :" + contactorId);
                        if (contactorId != null && contactorEmpId != null) {
                            MI_CONTRACTOR = contactorId;
                            MI_CONTRACTOREMP = contactorEmpId;

                            if (!contactorId.equals("") && !contactorEmpId.equals("")) {
                                contractorNameSpinner.setEnabled(false);
                                contractorEmpSpinner.setEnabled(false);
                            }
                        }
                    }
                } else {
                    contactorId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTACTORNAME);
                    contactorEmpId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTACTOREMP);

                    if (contactorId != null && contactorEmpId != null) {
                        MI_CONTRACTOR = contactorId;
                        MI_CONTRACTOREMP = contactorEmpId;

                        if (!contactorId.equals("") && !contactorEmpId.equals("")) {
                            contractorNameSpinner.setEnabled(false);
                            contractorEmpSpinner.setEnabled(false);
                        }
                    }
                }
            }
        } else {
            if (meterConnectionList != null) {

                for (int m = 0; m <= meterConnectionList.size() - 1; m++) {


                }
            }
            if (customerDetailList != null) {

                for (int c = 0; c <= customerDetailList.size() - 1; c++) {


                }
            }
        }

    }

    private void setFixerNameDropdown() {

        if (radiobuttonValStr.equalsIgnoreCase("OM") || radiobuttonValStr.equalsIgnoreCase("OH")
                || radiobuttonValStr.equalsIgnoreCase("N") || radiobuttonValStr.equalsIgnoreCase("R")
                || radiobuttonValStr.equalsIgnoreCase("S") || radiobuttonValStr.equalsIgnoreCase("MB")) {
            setInstallMI_CONTRACTOR();
        }
    }

    private void setInstallMI_CONTRACTOR() {
        if (MI_CONTRACTOR != null && !MI_CONTRACTOR.equalsIgnoreCase("")) {
            if (MI_CONTRACTOR.equalsIgnoreCase("-98")) {

                String compareValue = "MMG Employee";
                if (compareValue != null) {
                    spinnerPosition = contractorAdapter.getPosition(compareValue);
                    contractorNameSpinner.setSelection(spinnerPosition);
                }

           /*     contractorEmployee = realmOperations.fetchVendorByEmpCode(MI_CONTRACTOR);
                contractorEmpList = new ArrayList<>();
                contractorEmpList.add("--Select--");
                contractorEmpList.addAll(contractorEmployee);

                contractorEmpAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, contractorEmpList);
                contractorEmpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                contractorEmpSpinner.setAdapter(contractorEmpAdapter);

                for (int i = 0; i < contractorEmpList.size(); i++) {
                    Log.e("contractorEmpList.get(i)",contractorEmpList.get(i));
                    if (contractorEmployeeStr.equalsIgnoreCase(contractorEmpList.get(i))) {
                        contractorEmpSpinner.setSelection(i);
                    }
                }*/
            } else if (MI_CONTRACTOR.equalsIgnoreCase("-99")) {

                String compareValue = "Other";
                if (compareValue != null) {
                    spinnerPosition = contractorAdapter.getPosition(compareValue);
                    contractorNameSpinner.setSelection(spinnerPosition);
                }
            } else {
                MMGVendorDetModel mmgVendorDetModel = realmOperations.fetchVendorEmpById(MI_CONTRACTOR);
                if (mmgVendorDetModel == null) {
                    contractorNameSpinner.setSelection(0);
                } else {
                    String compareValue = mmgVendorDetModel.getNAME();
                    if (compareValue != null) {
                        spinnerPosition = contractorAdapter.getPosition(compareValue);
                        contractorNameSpinner.setSelection(spinnerPosition);
                    }
                }
            }
        }

    }

    private void setInstallMI_CONTRACTOREMP() {
        if (MI_CONTRACTOREMP != null && !MI_CONTRACTOREMP.equalsIgnoreCase("")) {
            mmgContEmpModel = realmOperations.fetchMMGContractorName(MI_CONTRACTOREMP);

            //Log.e("ContractorModel", "ContractorModel" + mmgContEmpModel.getNAME());
            String contractorName = mmgContEmpModel.getNAME();
            if (contractorName != null) {
                spinnerPosition = contractorEmpAdapter.getPosition(contractorName);
                contractorEmpSpinner.setSelection(spinnerPosition);
            }
        }
    }

    private void setOtherContractorDetails() {
        if (MI_CONTRACTOROTHER != null) {
            venderCodeEditText.setText(MI_CONTRACTOROTHER);
        }

        if (MI_CONTRACTOREMPOTHER != null) {
            contractorEmpEditText.setText(MI_CONTRACTOREMPOTHER);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sendCntrctDetails = (SendCntrctDetails) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton:
                if (checkValidation()) {
                   // if(radiobuttonValStr.equalsIgnoreCase("R")) {
                            MessageWindow.messageAuthenticationWindow(getContext(), "You can directly go to upload documents","Alert");

                //    }
                    isContractorSubmitted = true;
                    String meterId = UtilitySharedPreferences.getPrefs(mCon, Constants.NEW_METERSIZE);
                    sendCntrctDetails.sndContracterDet(c_id, c_emp_id, contractorNameStr, vendorCodeStr, contractorEmpIdStr,
                            removedHandverIdStr, otherContractor, otherContractorEmp, other_code_id, contList, meterId, isContractorSubmitted);
                    ((MMGScreenActivity) getActivity()).onClickNext(3);
                    MMGScreenActivity.animationOnArrow();
                }
                break;
            case R.id.saveAndExit:
                saveDataToServer();
                break;
            default:
        }
    }

    private boolean checkValidation() {
        contractorNameStr = contractorNameSpinner.getSelectedItem().toString();
        contractorEmployeeStr = contractorEmpSpinner.getSelectedItem().toString();
        removedMaterialName = removdMaterialSpinner.getSelectedItem().toString();
        vendorCodeStr = venderCodeEditText.getText().toString();
        otherContractorEmp = contractorEmpEditText.getText().toString();

        if (radioCommNonCommStr.equalsIgnoreCase("Y")
                && radiobuttonValStr.equalsIgnoreCase("OM") ||
                (radioCommNonCommStr.equalsIgnoreCase("Y")
                        && radiobuttonValStr.equalsIgnoreCase("R"))) {
            if (contractorEmployeeStr.equalsIgnoreCase("--Select--")) {
                MessageWindow.messageWindow(mCon,"Please Select Name of Employee","Alert");
                return false;
            } else if (strRemovedMaterialHandoverEnable) {
                if (removedMaterialName.equalsIgnoreCase("Select")) {
                    MessageWindow.messageWindow(mCon,"Please Select Removed Material Handover","Alert");
                    return false;
                }
            }
        } else if (radioCommNonCommStr.equalsIgnoreCase("N")) {
            if (contractorNameStr.equalsIgnoreCase("--Select--")) {
                MessageWindow.messageWindow(mCon,"Please select Plumber name","Alert");

                return false;
            }

            if (contractorNameStr.equalsIgnoreCase("OTHER")) {
                if (vendorCodeStr.equals("")) {
                    MessageWindow.messageWindow(mCon,"Please enter Plumber name ","Alert");

                    return false;
                } else if (otherContractorEmp.equalsIgnoreCase("")) {
                    MessageWindow.messageWindow(mCon,"Please enter contractor's employee name ","Alert");

                    return false;
                }
            } else {
                if (contractorEmployeeStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please Select Name of Employee ","Alert");

                    return false;
                } else if (strRemovedMaterialHandoverEnable) {
                    if (removedMaterialName.equalsIgnoreCase("Select")) {
                        MessageWindow.messageWindow(mCon,"Please Select Removed Material Handover ","Alert");

                        return false;
                    }
                }
            }
        } else {
            if (radioCommNonCommStr.equalsIgnoreCase("Y")) {
                if (contractorNameStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please select Plumber name ","Alert");

                    return false;
                }
                if (contractorEmployeeStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please Select Name of Employee ","Alert");

                    return false;
                }
            }
        }

        if (radioCommNonCommStr.equalsIgnoreCase("Y") && radiobuttonValStr.equalsIgnoreCase("N")) {
            return true;
        }

        if (strRemovedMaterialHandoverEnable) {

            if (removedMaterialName.equalsIgnoreCase("Select")) {
                MessageWindow.messageWindow(mCon,"Please Select Removed Material Handover","Alert");

                return false;
            }

        }
        if (radioCommNonCommStr.equalsIgnoreCase("N")) {
            if (contractorNameStr.equalsIgnoreCase("--Select--")) {
                MessageWindow.messageWindow(mCon,"Please select Plumber name","Alert");

                return false;
            }
            //contractorEmpEditText
            else if (contractorEmployeeStr.equals("")) {
                MessageWindow.messageWindow(mCon,"Please enter Plumber Employee Name","Alert");

                return false;

            } else if (strRemovedMaterialHandoverEnable) {
                if (removedMaterialName.equalsIgnoreCase("Select")) {
                    MessageWindow.messageWindow(mCon,"Please Select Removed Material Handover","Alert");

                    return false;
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            contractorNameStr = contractorNameSpinner.getSelectedItem().toString();
            contractorEmployeeStr = contractorEmpSpinner.getSelectedItem().toString();
            removedMaterialName = removdMaterialSpinner.getSelectedItem().toString();
            //Log.e("contractorNameStr","contractorNameStr"+ contractorNameStr);
            //Log.e("contractorEmployeeStr","contractorEmployeeStr  "+ contractorEmployeeStr);

            switch (parent.getId()) {
                case R.id.contractorNameSpinner:
                    if (contractorNameStr.equalsIgnoreCase("Select")) {
                        venderCodeEditText.setText("");
                    } else if (contractorNameStr.equalsIgnoreCase("--Select--")) {
                        venderCodeEditText.setText("");
                    } else if (contractorNameStr.equalsIgnoreCase("OTHER")) {
                        c_id = "-99";
                        c_emp_id = "";
                        contractorEmpEditText.setVisibility(View.VISIBLE);
                        contractorEmpSpinner.setVisibility(View.GONE);

                        vendor_other_code.setText("Other Fixer*");
                        venderCodeEditText.setText("");
                        contractorEmpEditText.setText("");
                        venderCodeEditText.setEnabled(true);
                        venderCodeEditText.setHint("Please Enter Plumber");

                        setOtherContractorDetails();

                    } else if (contractorNameStr.equalsIgnoreCase("MMG Employee")) {
                        c_id = "-98";

                        mmgContEmpModel = realmOperations.fetchcontractorEmpByVendor(c_id);
                        c_emp_id = String.valueOf(mmgContEmpModel.getEM_EMP_CODE());

                        contractorEmpEditText.setVisibility(View.GONE);
                        contractorEmpEditText.getText().clear();
                        contractorEmpSpinner.setVisibility(View.VISIBLE);

                        vendor_other_code.setText("Emp  Code*");
                        venderCodeEditText.setText("");
                        venderCodeEditText.setHint("Enter employee Id");

                        venderCodeEditText.setEnabled(false);
                        contractorEmployee = realmOperations.fetchVendorByEmpCode(c_id);
                        contractorEmpList = new ArrayList<>();
                        contractorEmpList.add("--Select--");
                        contractorEmpList.addAll(contractorEmployee);

                        contractorEmpAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, contractorEmpList);
                        contractorEmpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        contractorEmpSpinner.setAdapter(contractorEmpAdapter);

                        setInstallMI_CONTRACTOREMP();

                        String commisioned_noncommisioned = UtilitySharedPreferences.getPrefs(mCon, Constants.COMMISIONED_NONCOMMISIONED);

                       /* if (commisioned_noncommisioned.equalsIgnoreCase("N")) {
                            contractorEmpSpinner.setOnItemSelectedListener(this);
                        } else {

                        }*/

                        //Log.e("CHECK_NULL", "CHECK_NULL :" + contactorEmpId);
                        if (c_emp_id != null && !contactorEmpId.isEmpty()) {
                            mmgContEmpModel = realmOperations.fetchMMGContractorName(contactorEmpId);
                            fixerName = mmgContEmpModel.getNAME();

                            index = contractorEmpList.indexOf(fixerName);
                            if (index != -1) {
                                contractorEmpSpinner.setSelection(index);
                                //contractorEmpSpinner.setEnabled(false);temlrupa
                            } else {
                                contractorEmpSpinner.setSelection(0);
                                contractorEmpSpinner.setEnabled(true);
                            }
                        }

                        contractorEmpEditText.setVisibility(View.GONE);
                        contractorEmpSpinner.setVisibility(View.VISIBLE);
                    } else {
                        mmgVendorDetModel = realmOperations.fetchVendorByName(contractorNameStr);
                        c_id = String.valueOf(mmgVendorDetModel.getEM_EMP_CODE());
                        contractorEmpEditText.setVisibility(View.GONE);
                        contractorEmpEditText.getText().clear();
                        contractorEmpSpinner.setVisibility(View.VISIBLE);

                        venderCodeEditText.setText(c_id);
                        venderCodeEditText.setEnabled(false);
                        //contractorEmployee = realmOperations.fetchVendorByEmpCode(contractorId);

                        mmgContEmpModelRealmResults = realmOperations.fetchVendorByContractorList(c_id);

                        contractorEmpList.clear();
                        contractorEmpIdList.clear();

                        contractorEmpList.add("--Select--");
                        for (MMGContEmpModel model : mmgContEmpModelRealmResults) {
                            contractorEmpList.add(model.getNAME());
                            contractorEmpIdList.add(model.getEM_EMP_CODE());
                        }
                        contractorEmpAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, contractorEmpList);
                        contractorEmpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        contractorEmpSpinner.setAdapter(contractorEmpAdapter);

                        setInstallMI_CONTRACTOREMP();

                       /* if (isComingFromBack) {
                            for (int i = 0; i < contractorEmpIdList.size(); i++) {
                                String idd = contractorEmpIdList.get(i);
                                if (!c_emp_id.isEmpty()) {
                                    if (c_emp_id.equalsIgnoreCase(idd)) {//pinky changed empcode as c_emp_id
                                        contractorEmployeeStr = contractorEmpList.get(i + 1);
                                        //contractorEmpSpinner.setSelection(7); //pinky commented this
                                        contractorEmpSpinner.setSelection(i + 1); //pinky added this
                                        break;
                                    }
                                } else {
                                    c_emp_id = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTACTOREMP);
                                    //Log.e("c_emp_id", c_emp_id);
                                    if (c_emp_id.equalsIgnoreCase(idd)) {//pinky changed empcode as c_emp_id
                                        contractorEmployeeStr = contractorEmpList.get(i + 1);
                                        //contractorEmpSpinner.setSelection(7); //pinky commented this
                                        contractorEmpSpinner.setSelection(i + 1); //pinky added this
                                        break;
                                    }
                                }
                            }
                        } else {
                            setInstallMI_CONTRACTOREMP();
                        }*///pinky commented 10/02/2022

                        mmgContEmpModel = realmOperations.fetchcontractorEmpByName(contractorEmployeeStr);
                        c_emp_id = mmgContEmpModel.getEM_EMP_CODE();
                        // c_id = mmgContEmpModel.getEM_VENDOR_ID();

                        break;
                    }
                    break;
                case R.id.contractorEmpSpinner:
                    contractorEmployeeStr = contractorEmpSpinner.getSelectedItem().toString();
                    if (!contractorEmployeeStr.equalsIgnoreCase("--Select--")) {

                        mmgContEmpModel = realmOperations.fetchcontractorEmpByName(contractorEmployeeStr);
                        c_emp_id = mmgContEmpModel.getEM_EMP_CODE();

                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTACTOREMP, c_emp_id);

                        if (contractorNameStr.equalsIgnoreCase("MMG Employee")) {
                            mmgContEmpModel = realmOperations.fetchcontractorEmpByName(contractorEmployeeStr);
                            c_emp_id = mmgContEmpModel.getEM_EMP_CODE();
                            vendor_other_code.setText("Emp Id.");
                            venderCodeEditText.setText(c_emp_id);
                            // contractorEmpSpinner.setSelection(spinPosition);
                        }

                        if (radioCommNonCommStr.equalsIgnoreCase("Y")) {
                            if (!meterNumStr.equalsIgnoreCase("")) {
                                getMeterContractorDetails();
                            } else {
                                if (!sealNumStr.isEmpty() && !sealNumStr.equalsIgnoreCase("")) {
                                    checkSealAvailableTo(sealNumStr, c_emp_id);
                                } else {
                                    if (!radiobuttonValStr.equalsIgnoreCase("S")) {
                                        if (contractorEmpSpinner.isEnabled()) {
                                            if (protectedBoxIdNoStr.equalsIgnoreCase("1")) {//pinky added 01/02/2022
                                                getMeterBoxBalanceDetails(c_id, c_emp_id, "A");
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (!sealNumStr.isEmpty() && !sealNumStr.equalsIgnoreCase("")) {
                                checkSealAvailableTo(sealNumStr, c_emp_id);
                            } else {
                                if (!radiobuttonValStr.equalsIgnoreCase("S")) {
                                    if (contractorEmpSpinner.isEnabled()) {
                                        if (protectedBoxIdNoStr.equalsIgnoreCase("1")) {//pinky added 01/02/2022
                                            getMeterBoxBalanceDetails(c_id, c_emp_id, "A");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case R.id.removdMaterialSpinner:
                    removedHandverIdStr = String.valueOf(removdMaterialSpinner.getSelectedItemPosition());
                    break;

                default:
                    break;
            }
        } catch (Exception ex) {
            Log.e("ex", "" + ex.getMessage());
        }
    }

    private void checkSealAvailableTo(String sealNumStr, String c_emp_id) {
        String params[] = new String[2];
        params[0] = c_emp_id;
        params[1] = sealNumStr;

        //Log.e("CheckSealDetailParams", Arrays.toString(params));

      /*  progress = new MaterialDialog.Builder(mCon)
                .content(R.string.loading)
                .progress(true, 0)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .widgetColorRes(R.color.colorPrimary)
                .show();*/

        if (connection.isConnectingToInternet()) {
            Get_SealDetail get_sealDetail = new Get_SealDetail();
            get_sealDetail.execute(params);
        } else {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.no_internet_connection),"Alert");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface SendCntrctDetails {
        void sndContracterDet(String c_id, String c_emp_id, String contractorIdStr, String vendorCodeStr,
                              String contractorEmpIdStr, String removedHandverIdStr, String otherContractor,
                              String otherContractorEmp, String other_code_id, String contlist, String meterSizeId, boolean isContractorSubmitted);
    }

    private void materialHandDropdown() {
        ArrayList<String> removedMaterial = new ArrayList<>();
        removedMaterial.add("Select");
        removedMaterial.add("Yes");
        removedMaterial.add("No");

        removedMatrialAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, removedMaterial);
        removedMatrialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        removdMaterialSpinner.setAdapter(removedMatrialAdapter);
        removdMaterialSpinner.setOnItemSelectedListener(this);

        if (MI_N_ISMATERIALHANDOVER != null && !(MI_N_ISMATERIALHANDOVER.equalsIgnoreCase(""))) {
            if (MI_N_ISMATERIALHANDOVER.equalsIgnoreCase("1")) {
                removdMaterialSpinner.setSelection(1);
            } else if (MI_N_ISMATERIALHANDOVER.equalsIgnoreCase("2")) {
                removdMaterialSpinner.setSelection(2);
            } else {
                removdMaterialSpinner.setSelection(0);
            }
        }
        removedHandverIdStr = String.valueOf(removdMaterialSpinner.getSelectedItemPosition());
    }

    public void displayReceivedData(String makerCodeId, String oldmeterno, String sealNoStr, String installDtStr,
                                    String coonectionLoad, String pastMeterReadingStr, String submitStatus, String radiobuttonValStr,
                                    String consumerNoStr, String zoneStr, String groupStr, String refTypeStr, String connSizeStr,
                                    String property_assessmnt, String fromNodeStr, String toNodeStr, String primaryMobStr, String alternateMobStr,
                                    String gis_bidStr, String dmaId, String srId, String msrId, String commisioned_noncommisioned,
                                    String employee_code, String mac_address, String mContList, boolean isSubmitted) {

        zone_str = zoneStr;
        group_str = groupStr;
        from_node_str = fromNodeStr;
        to_node_str = toNodeStr;
        primary_mob_str = primaryMobStr;
        alternate_mob_str = alternateMobStr;
        gis_bid_str = gis_bidStr;
        dma_id = dmaId;
        sr_id = srId;
        property_assess = property_assessmnt;
        isConsumerSubmitted = isSubmitted;
        radiobuttonAction = radiobuttonValStr;
    }

    private void saveDataToServer() {
        String mtrTypeCode = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_TYPE_CODE_ID);
        String emp_code = null;
        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ip_str = PreferenceUtil.getMac();
        String oldmeterSizeNum = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_SIZE_ID);
        String radiobuttonAction = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);

        vendorCodeStr = venderCodeEditText.getText().toString();
        other_code_id = contractorEmpEditText.getText().toString();

        String params[] = new String[59];
        params[0] = "N";                            //IsSubmit
        if (isConsumerSubmitted) {
            params[1] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);             //Action
            params[2] = consumerNoStr;                 //Consumer
            params[3] = BU;
            params[4] = PC;
        } else {
            params[1] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);             //Action
            params[2] = consumerNoStr;                 //Consumer
            params[3] = MI_BU;
            params[4] = MI_PC;
        }
        // params[5] = ConsumerDetFragmentNew.refNoStrStatic;
        params[5] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMERREFERENCENUMBER);

        if (isOldSubmitted) {
            params[6] = oldmeterSizeNum;
            params[7] = serialNoStr;
            params[8] = old_maker_code_id;
            params[9] = previousReading;
            params[10] = finalReadingValStr;
            params[11] = finalStatusIdStr;
            params[12] = meterObservationId;
            params[13] = meterReasonId;
            params[14] = mtrTypeCode;
        } else {
            params[6] = MI_O_SIZE;
            params[7] = MI_O_METER;
            params[8] = MI_O_MAKE;
            params[9] = MI_O_PREVIOUSREADING;
            params[10] = MI_O_FINALREADING;
            params[11] = MI_O_FINALSTATUS;
            params[12] = MI_O_OBSERVATION;
            params[13] = MI_O_REASON;
            params[14] = MI_O_METERTYPE;
        }

        if (radiobuttonAction.equals("OH")) {
            params[15] = "";
            params[16] = "";                     // N_Size
            params[17] = "";                    // N_Seal
            params[18] = "";                    // N_Meter
            if (installDateStr != null)
                params[19] = installDateStr;          // InstallationDate
            else
                params[19] = MI_INSTALLATIONDATE;
            params[20] = "";                    // N_InitialReading
            params[21] = "";                    // N_MeterType
            params[22] = "";                    // N_MeterLocation
            params[23] = "";                    // N_IsProtected
            params[25] = "";                   // N_IsMeterHandovertoConsumer
        } else {
            if (is_newmeter_submitted) {
                params[15] = makerCodeIdStr;
                params[16] = meterSizeIdStr;
                params[17] = sealNumStr;
                params[18] = meterNumStr;
                params[19] = installDateStr;
                params[20] = initialReadingNoStr;
                params[21] = meterTypeIdStr;
                params[22] = meterLocationIdNo;
                params[23] = protectedBoxIdNoStr;
                params[25] = meterHandoverStr;
            } else {
                params[15] = MI_N_MAKE;
                params[16] = MI_N_SIZE;
                params[17] = MI_N_SEAL;
                params[18] = MI_N_METER;
                params[19] = MI_INSTALLATIONDATE;
                params[20] = MI_N_INITIALREADING;
                params[21] = MI_N_METERTYPE;
                params[22] = MI_N_METERLOCATION;
                params[23] = MI_N_ISPROTECTED;
                params[25] = MI_N_ISMETERHANDOVER;
            }
        }
        if (isConsumerSubmitted)
            params[24] = property_assess;      // PropertyTaxNo
        else
            params[24] = MI_PROPERTYTAXNO;

        params[26] = c_id;
        params[27] = c_emp_id;
        params[28] = vendorCodeStr;
        params[29] = other_code_id;
        params[30] = removedHandverIdStr;

        params[31] = "";// MI_XMLMATERIAL;
        params[32] = "";//MI_XMLCIVIL;
        params[33] = "";//MI_PCCBEDDINGLEN;
        params[34] = "";//MI_PCCBEDDINGWID;
        params[35] = "";//MI_PCCBEDDINGDEP;
        params[36] = "";// MI_ROADCUTTINGTYPE;
        params[37] = "";//MI_ROADCUTTINGLEN;
        params[38] = "";// MI_ROADCUTTINGWID;
        params[39] = "";// MI_ROADCUTTINGDEP;

        if (isConsumerSubmitted) {
            params[40] = from_node_str;
            params[41] = to_node_str;
            params[42] = primary_mob_str;
            params[43] = alternate_mob_str;
            params[44] = gis_bid_str;
            params[45] = dma_id;
            params[46] = sr_id;
        } else {
            params[40] = MI_FROMNODE;
            params[41] = MI_TONODE;
            params[42] = MI_REGMOBILE;
            params[43] = MI_ALTMOBILE;
            params[44] = MI_GIS;
            params[45] = MI_DMA;
            params[46] = MI_SR;
        }
        params[47] = radioCommNonCommStr;
        if (is_newmeter_submitted)
            params[48] = dial_digit;                    //N_DIGIT
        else
            params[48] = MI_N_DIGIT;
        params[49] = emp_code;
        params[50] = ip_str;
        params[51] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_METERINSTALLID);                    // Query String
        params[52] = NewMeterDetFragment.sealmakeId;
        params[53] = NewMeterDetFragment.meterboxmakeId;
        params[54] = "NF";
        String metrown = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_N_METEROWNERSHIP);
        params[55] = metrown;
        params[56] = "OK";
        params[57] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_AVERAGECONSUMTION);
        params[58] = UtilitySharedPreferences.getPrefs(getActivity(),Constants.No_ofFlat);

        //Log.e("fixerDetailsParams", Arrays.toString(params));

        SendDataToMeterInstallation sendDataToMeterInstallation = new SendDataToMeterInstallation();
        sendDataToMeterInstallation.execute(params);
    }

    @SuppressLint("StaticFieldLeak")
    private class SendDataToMeterInstallation extends AsyncTask<String, Void, Void> {
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
               // Log.e("FixerResponse", jsonMeterInstallSaveResponse);
                // Log.e("FixerParams", Arrays.toString(params));

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
                if (enums[0].equalsIgnoreCase("Success")) {
                    clearAllSharedPrefs();
                    MessageWindow.throwOutMMMFragment(mCon, "Contractor Details Saved Successfully", "Success", MainActivity.class);

                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTACTORNAME, c_id);
                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTACTOREMP, c_emp_id);
                    return;
                } else if (enums[0].equalsIgnoreCase("Failure")) {
                    MessageWindow.throwOutFromWindow(mCon, "Contractor Details Not Saved", "Alert", MainActivity.class);

                } else if (enums[0].equalsIgnoreCase("Duplicate")) {
                    MessageWindow.throwOutFromWindow(mCon, getResources().getString(R.string.please_forward_complaint_to_mmg), "Alert", MainActivity.class);
                }

            } catch (Exception e) {
                Log.e("Exception", e.toString());
                progress.dismiss();
                MessageWindow.errorWindow(mCon,"Something went wrong");
                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Send Data to meter Installation", error);
            }
        }
    }

    public void displayOldMeterDetails(String oldmakerCodeId, String oldmeterNoStr, String oldinstallDtStr, String oldmeterSizeStr,
                                       String oldsealNoStr, String pastReadingStr, String oldMtrStsId, String oldmeterTypeId, String finalReadingStr,
                                       String finalStatusStr, String meter_observationId, String meter_reasonId, boolean is_oldSubmitted) {

        old_maker_code_id = oldmakerCodeId;
        mtrSizeId = oldmeterSizeStr;
        serialNoStr = oldmeterNoStr;
        previousReading = pastReadingStr;
        finalReadingValStr = finalReadingStr;
        finalStatusIdStr = finalStatusStr;
        meterReasonId = meter_reasonId;
        meterObservationId = meter_observationId;
        isOldSubmitted = is_oldSubmitted;
    }

    public void displayNewMeterDet(String makerCodeId, String meterNoStr, String installDtStr, String meterSizeId, String sealNoStr
            , String initialReadingStr, String meterTypeId, String meterLocationId, String protectedBoxIdStr
            , String taxNoStr, String meterHandoverIdStr, String contList, String ndial_digit,
                                   boolean isNewMeterSubmitted) {

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
        is_newmeter_submitted = isNewMeterSubmitted;
//        sealmakeId = sealMakeCodeId;
//        meterBoxmakeid = meterBoxMakeCodeId;
    }

    public void displayContractorDet(String mContList) {
        contList = mContList;
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
    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);
        if (this.isVisible()) {
            if (isFragmentVisible_) {
                new ContractorDetFragmentNew();

                validMeterStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.VALIDMETER);
                contactorId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTACTORNAME);
                contactorEmpId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTACTOREMP);
                isComingFromBack = true;
                MI_CONTRACTOR = contactorId;
                MI_CONTRACTOREMP = contactorEmpId;

                setFixerNameDropdown();
                if (radiobuttonValStr.equalsIgnoreCase("MB")) { //commented by pinky..01/04/2021
                    setInstallMI_CONTRACTOREMP();
                }

            }
        }
    }

    public void getMeterBoxBalanceDetails(String c_id, String c_emp_id, String radiobuttonValStr) {

        String params[] = new String[3];
        params[0] = c_id;
        params[1] = c_emp_id;
        params[2] = radiobuttonValStr;

        //Log.e("params", Arrays.toString(params));

        meterBalanceProgressDialog = new MaterialDialog.Builder(mCon)
                .content(R.string.loading)
                .progress(true, 0)
                .cancelable(false)
                .canceledOnTouchOutside(true)
                .widgetColorRes(R.color.colorPrimary)
                .show();

        if (connection.isConnectingToInternet()) {
            Get_MeterBoxBalance get_meterBoxBalance = new Get_MeterBoxBalance();
            get_meterBoxBalance.execute(params);
        } else {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.no_internet_connection),"Alert");
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_MeterBoxBalance extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[3];
                paraName[0] = "Cont_code";
                paraName[1] = "Emp";
                paraName[2] = "MaterialType";

                jsonResponseMeterBalance = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.getBalContractorQty, params, paraName);
                //Log.e("meterBoxBalanceDetails", jsonResponseMeterBalance);

                meterBalanceProgressDialog.dismiss();
            } catch (Exception e) {
                meterBalanceProgressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                JSONObject object = new JSONObject(jsonResponseMeterBalance);
                JSONArray jsonArray = new JSONArray(object.getString("ContractorMeterQuantity"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                   /* if (jsonObject.has("BALQTY")) {
                        if (Float.parseFloat(jsonObject.getString("BALQTY")) == 0.0) {
                            MessageWindow.messageWindow(mCon, getResources().getString(R.string.meterbox_balance), "Alert");
                            contractorEmpSpinner.setSelection(0);
                        }
                    } else*/
                    if (jsonObject.has("METERBOX")) {
                        if (Float.parseFloat(jsonObject.getString("METERBOX")) == 0.0) {
                            MessageWindow.messageWindow(mCon, getResources().getString(R.string.meterbox_balance), "Alert");
                            contractorEmpSpinner.setSelection(0);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_SealDetail extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[2];
                paraName[0] = "ContEmpCode";
                paraName[1] = "SealNo";

                jsonResponseForSealDeatils = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetSealAvailabilityDetails, params, paraName);
                //Log.e("checkSealDetails", jsonResponseForSealDeatils);

                //progress.dismiss();
            } catch (Exception e) {
                //progress.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (jsonResponseForSealDeatils.equalsIgnoreCase("[]")) {
                MessageWindow.messageWindow(mCon, getResources().getString(R.string.seal_fixer), "Alert");
                contractorEmpSpinner.setSelection(0);
            } else {
                if (!radiobuttonValStr.equalsIgnoreCase("S")) {
                    if (contractorEmpSpinner.isEnabled()) {
                        if (protectedBoxIdNoStr.equalsIgnoreCase("1")) {//pinky added 01/02/2022
                            getMeterBoxBalanceDetails(c_id, c_emp_id, "A");
                        }
                    }
                }
            }
        }
    }

    public void getMeterContractorDetails() {

        String params[] = new String[5];
        params[0] = "M";
        params[1] = makerCodeIdStr;
        params[2] = meterSizeIdStr;
        params[3] = meterNumStr;
        params[4] = MI_METERINSTALLID;
        //Log.e("MeterParams22", Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            Get_MeterContractorDetailTask get_meterContractorDetailTask = new Get_MeterContractorDetailTask();
            get_meterContractorDetailTask.execute(params);
        } else {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.no_internet_connection),"Alert");
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_MeterContractorDetailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[5];
                paraName[0] = "Material";
                paraName[1] = "MakeId";
                paraName[2] = "SizeId";
                paraName[3] = "Meter";
                paraName[4] = "MeterInstallId";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetMeterContractorDetails, params, paraName);

                //Log.e("meterDetailsResponse", jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MMGContractorResponseDetail[] mmgContractorResponseDetails = gson.fromJson(jsonResponse, MMGContractorResponseDetail[].class);
                if (mmgContractorResponseDetails != null) {

                    if (mmgContractorResponseDetails[0].getMETERSTATUS().equals("ValidMeter")) {

                        strCont_id = mmgContractorResponseDetails[0].getM_CONT_CODE();
                        strCont_emp_code = mmgContractorResponseDetails[0].getM_CONT_EMP_CODE();

                        if (!c_emp_id.equalsIgnoreCase(strCont_emp_code)) {
                            MessageWindow.msgWindow(getActivity(), "This Meter is not available at Plumber");
                            contractorEmpSpinner.setSelection(0);
                        } else if (!sealNumStr.isEmpty() && !sealNumStr.equalsIgnoreCase("")) {
                            checkSealAvailableTo(sealNumStr, c_emp_id);
                        } else {
                            if (!radiobuttonValStr.equalsIgnoreCase("S")) {
                                if (contractorEmpSpinner.isEnabled()) {
                                    // if (radiobuttonValStr.equalsIgnoreCase("R")) {
                                    //Log.e("protectedBoxIdNoStr",protectedBoxIdNoStr+"protectedBoxStr");
                                    if (protectedBoxIdNoStr.equalsIgnoreCase("1")) {//pinky added 01/02/2022
                                        getMeterBoxBalanceDetails(c_id, c_emp_id, "A");
                                    }
                               /* } else {
                                    getMeterBoxBalanceDetails(c_id, c_emp_id, radiobuttonValStr);
                                }*/
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "New Meter Details Fragment", "Get Meter Contractor details task", error);
            }
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
        realmOperations.close();
        ((App) mCon.getApplicationContext()).startActivityTransitionTimer();
    }

}
