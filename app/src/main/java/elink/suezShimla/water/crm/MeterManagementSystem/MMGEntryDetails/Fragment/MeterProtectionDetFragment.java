package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCvlMeasurementResponseModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGTypeOfRoadcuttingModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGScreenActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGCustDetModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMaterialResponseModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;


public class MeterProtectionDetFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Context mCon;
    private MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel;
    private String roadCuttingId = "", roadCuttingIdStr = "", radiobuttonValStr = "", radioCommNonCommStr, consumerNoStr, refNoStr, BU, PC;
    private ArrayAdapter roadCuttingAdapter;
    private RealmOperations realmOperations;
    EditText pcclengthEdittext, pccwidthEdittext, pccdepthEdittext, pcctotalEdittext, rdlengthEdittext, rdwidthEdittext, rddepthEdittext, rdtotalEdittext;
    AppCompatSpinner roadcuttingSpinner;
    Button submitButton, saveAndExit;
    String pccLengthStr = "", pccWidthStr = "", pccDepthStr = "", pccTotalStr = "", rdLengthStr = "", rdWidthStr = "", rdDepthStr = "", rdTotalStr = "", rdCuttingSpinnerName = "";
    String pcclengthStr = "", pccwidthStr = "", pccdepthStr = "", pcctotalval = "", rdlengthStr = "", rdwidthStr = "", rddepthStr = "", rdtotalval = "";
    SendMeterProtectionDetails sendMeterProtectionDetails;
    String MI_METERINSTALLID, MI_ACTION, MI_CONSUMER, MI_BU, MI_PC, MI_REFNO, MI_O_SIZE = "", MI_O_METER = "", MI_O_MAKE = "", MI_O_PREVIOUSREADING = "", MI_O_FINALREADING, MI_O_FINALSTATUS, MI_O_REASON = "", MI_O_METERTYPE,
            MI_N_MAKE = "", MI_N_SIZE = "", MI_N_SEAL = "", MI_N_METER = "", MI_INSTALLATIONDATE = "", MI_N_INITIALREADING = "", MI_N_METERTYPE = "", MI_N_METERLOCATION = "", MI_N_ISPROTECTED = "", MI_PROPERTYTAXNO, MI_N_ISMETERHANDOVER = "",
            MI_CONTRACTOR, MI_CONTRACTOREMP, MI_N_ISMATERIALHANDOVER, MI_PCCBEDDINGLEN, MI_PCCBEDDINGWID,
            MI_PCCBEDDINGDEP, MI_ROADCUTTINGTYPE, MI_ROADCUTTINGLEN, MI_ROADCUTTINGWID, MI_ROADCUTTINGDEP, MI_FROMNODE = "",
            MI_TONODE = "", MI_REGMOBILE = "", MI_ALTMOBILE = "", MI_GIS = "", MI_DMA = "", MI_SR = "", MI_MODIFIEDBY, MI_MODIFIEDDATE, MI_IP, MI_AUTHENTICATEDATE,
            MI_AUTHREJECTBY, MI_REJECTEDDATE, MI_STATUS, MI_ISACTIVE, MI_XMLMATERIAL = "", MI_XMLCIVIL = "", MI_O_OBSERVATION,
            MI_SOURCE, MI_ISCOMMISSIONED, MI_CONTRACTOROTHER, MI_CONTRACTOREMPOTHER, MI_N_DIGIT = "", MSRID, dataFound, jsonMeterInstallSaveResponse;
    static String contList = "", pagename = "", to_node_str = "",
            from_node_str, primary_mob_str, alternate_mob_str, gis_bid_str, dma_id, sr_id, property_assess,
            mtrSizeId, old_maker_code_id, serialNoStr, previousReading, finalReadingValStr, finalStatusIdStr, meterObservationId,
            meterReasonId, makerCodeIdStr, meterNumStr, installDtStr, sealNoStr, meterHandoverIdStr, protectedBoxStr,
            meterLocationStr, meterTypeStr, sizeStr, initialReadingStr, installDateStr, dial_digit, meterSizeIdStr, sealNumStr,
            initialReadingNoStr, meterTypeIdStr, meterLocationIdNo, protectedBoxIdNoStr, meterHandoverStr,
            taxNumStr, materialHandoverStr, c_employee_id, contractorId, cntrctrNameIdStr = "", otherContractorStr = "", otherContractorEmpStr = "";
    private boolean isConsumerSubmitted = false, isOldSubmitted = false, is_newmeter_submitted = false,
            is_contractor_submitted = false, is_material_submitted = false, is_civil_submitted = false;
    StringBuilder materialDetailXml = new StringBuilder();
    StringBuilder cvlMeasurementXml = new StringBuilder();

    MaterialDialog progress;

    private Invoke invServices;
    private Gson gson;

    ArrayList<MMGCustDetModel> customerDetailList = new ArrayList<>();
    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = new ArrayList<>();
    ArrayList<InstallDetails> installDetails = new ArrayList<>();
    ArrayList<MMGValidateDetails> validateDetailList = new ArrayList<>();

    String STARTTIME = "", ALERTSTARTTIME = "",deviceAuthorization = "", appIsLogged = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
      //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = getActivity();
        realmOperations = new RealmOperations(mCon);
        gson = new Gson();
        invServices = new Invoke();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meter_protection_det, container, false);


        MMGScreenActivity activity = (MMGScreenActivity) getActivity();
        customerDetailList = activity.getMMGCustomerDetailsData();
        meterConnectionList = activity.getMMGMeterConnectionDetailsData();
        installDetails = activity.getMMGInstallDetailsData();
        validateDetailList = activity.getMmgvalidateDetailList();

        Log.println(Log.DEBUG, "METERPROTECTIONDATA", "" + customerDetailList.toString() + "\n" + meterConnectionList.toString() + "\n" + installDetails.toString() + "\n" + validateDetailList.toString());


        pagename = getArguments().getString("pagename");
        contList = getArguments().getString("contList");

        consumerNoStr = getArguments().getString("consumerNoStr");
        refNoStr = getArguments().getString("refNoStr");

        BU = UtilitySharedPreferences.getPrefs(mCon, Constants.BU);
        PC = UtilitySharedPreferences.getPrefs(mCon, Constants.PC);
        radioCommNonCommStr = UtilitySharedPreferences.getPrefs(mCon, Constants.COMMISIONED_NONCOMMISIONED);

        pcclengthEdittext = view.findViewById(R.id.pcclengthEdittext);
        pccwidthEdittext = view.findViewById(R.id.pccwidthEdittext);
        pccdepthEdittext = view.findViewById(R.id.pccdepthEdittext);
        pcctotalEdittext = view.findViewById(R.id.pcctotalEdittext);
        roadcuttingSpinner = view.findViewById(R.id.roadcuttingSpinner);
        rdlengthEdittext = view.findViewById(R.id.rdlengthEdittext);
        rdwidthEdittext = view.findViewById(R.id.rdwidthEdittext);
        rddepthEdittext = view.findViewById(R.id.rddepthEdittext);
        rdtotalEdittext = view.findViewById(R.id.rdtotalEdittext);
        submitButton = view.findViewById(R.id.submitButton);
        saveAndExit = view.findViewById(R.id.saveAndExit);
        submitButton.setOnClickListener(this);
        saveAndExit.setOnClickListener(this);
        STARTTIME = UtilitySharedPreferences.getPrefs(mCon, AppConstant.STARTTIME);
        ENDTIME = UtilitySharedPreferences.getPrefs(mCon, AppConstant.ENDTIME);
        deviceAuthorization = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEVICEAUTHORIZATION);
        appIsLogged = UtilitySharedPreferences.getPrefs(mCon, AppConstant.APP_ISLOGGED);

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
        if (deviceAuthorization.equalsIgnoreCase("0")) {
            ((MainActivity) mCon).deviceAuthorizedDialog();

        } else {
            if (appIsLogged.equalsIgnoreCase("0")) {
                ((MainActivity) getActivity()).adminLogout();
            }
        }
        pccdepthEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    pcclengthStr = pcclengthEdittext.getText().toString();
                    pccwidthStr = pccwidthEdittext.getText().toString();
                    pccdepthStr = pccdepthEdittext.getText().toString();
                    if (pcclengthStr.equals("")) {
                        pcclengthStr = "0";
                    } else if (pccwidthStr.equals("")) {
                        pccwidthStr = "0";
                    } else if (pccdepthStr.equals("")) {
                        pccdepthStr = "0";
                    }
                    pcctotalval = String.valueOf(Integer.parseInt(pcclengthStr) * Integer.parseInt(pccwidthStr) * Integer.parseInt(pccdepthStr));
                    pcctotalEdittext.setText((pcctotalval));
                } catch (NumberFormatException ne) {

                }

            }
        });
        rddepthEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rdlengthStr = rdlengthEdittext.getText().toString();
                    rdwidthStr = rdwidthEdittext.getText().toString();
                    rddepthStr = rddepthEdittext.getText().toString();

                    rdtotalval = String.valueOf(Integer.parseInt(rdlengthStr) * Integer.parseInt(rdwidthStr) * Integer.parseInt(rddepthStr));
                    rdtotalEdittext.setText((rdtotalval));
                } catch (NumberFormatException ne) {

                }

            }
        });

        fetchInstallDetails();

        roadCuttingDropdown();

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
    private void fetchInstallDetails() {
        if (installDetails != null) {

            for (int k = 0; k <= installDetails.size() - 1; k++) {

                dataFound = installDetails.get(k).getNODATAFOUND();
                if (dataFound.equalsIgnoreCase("DataFound")) {

                    MI_METERINSTALLID = installDetails.get(k).getMI_METERINSTALLID();

                    MI_PCCBEDDINGLEN = installDetails.get(k).getMI_PCCBEDDINGLEN();
                    if (MI_PCCBEDDINGLEN != null)
                        pcclengthEdittext.setText(MI_PCCBEDDINGLEN);

                    MI_PCCBEDDINGWID = installDetails.get(k).getMI_PCCBEDDINGWID();
                    if (MI_PCCBEDDINGWID != null)
                        pccwidthEdittext.setText(MI_PCCBEDDINGWID);

                    MI_PCCBEDDINGDEP = installDetails.get(k).getMI_PCCBEDDINGDEP();
                    if (MI_PCCBEDDINGDEP != null)
                        pccdepthEdittext.setText(MI_PCCBEDDINGDEP);

                    MI_ROADCUTTINGTYPE = installDetails.get(k).getMI_ROADCUTTINGTYPE();

                    MI_ROADCUTTINGLEN = installDetails.get(k).getMI_ROADCUTTINGLEN();
                    if (MI_ROADCUTTINGLEN != null)
                        rdlengthEdittext.setText(MI_ROADCUTTINGLEN);

                    MI_ROADCUTTINGWID = installDetails.get(k).getMI_ROADCUTTINGWID();
                    if (MI_ROADCUTTINGWID != null)
                        rdwidthEdittext.setText(MI_ROADCUTTINGWID);

                    MI_ROADCUTTINGDEP = installDetails.get(k).getMI_ROADCUTTINGDEP();
                    if (MI_ROADCUTTINGDEP != null)
                        rddepthEdittext.setText(MI_ROADCUTTINGDEP);
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

    public void displayMeterProt(String mContList) {
        contList = mContList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton: {
                pccLengthStr = pcclengthEdittext.getText().toString().trim();
                pccWidthStr = pccwidthEdittext.getText().toString().trim();
                pccDepthStr = pccdepthEdittext.getText().toString().trim();
                pccTotalStr = pcctotalEdittext.getText().toString().trim();
                rdCuttingSpinnerName = roadcuttingSpinner.getSelectedItem().toString().trim();
                rdLengthStr = rdlengthEdittext.getText().toString().trim();
                rdWidthStr = rdwidthEdittext.getText().toString().trim();
                rdDepthStr = rddepthEdittext.getText().toString().trim();
                rdTotalStr = rdtotalEdittext.getText().toString().trim();

                boolean is_mtr_protection_submitted = true;

                sendMeterProtectionDetails.sndMtrPrtctnDet(pccLengthStr, pccWidthStr, pccDepthStr, pccTotalStr,
                        roadCuttingIdStr, rdLengthStr, rdWidthStr, rdDepthStr, rdTotalStr, is_mtr_protection_submitted);
                //validate();
                Toast.makeText(mCon, getResources().getString(R.string.details_submitted_successfully), Toast.LENGTH_SHORT).show();
                ((MMGScreenActivity) getActivity()).onClickNext(6);
                MMGScreenActivity.animationOnArrow();
            }
            break;

            case R.id.saveAndExit:
                saveDataToServer();
                break;

            default:
                break;
        }
    }

    public void validate() {
        boolean isValidPccLength = false, isValidPccWidth = false, isValidPccDepth = false, isValidPccTotal = false, isValidRdSpinnerName = false, isValidRdCutLen = false, isValidRdCutWidth = false,
                isValidRdCutDepth = false, isValidRdCutTotal = false;

        if (pccLengthStr.equalsIgnoreCase("")) {
            pcclengthEdittext.requestFocus();
            pcclengthEdittext.setError("Select PCC Bedding Water Meter Protection Box Length");
        } else {
            isValidPccLength = true;
        }

        if (pccWidthStr.equalsIgnoreCase("")) {
            pccwidthEdittext.requestFocus();
            pccwidthEdittext.setError("Select PCC Bedding Water Meter Protection Box Width");
        } else {
            isValidPccWidth = true;
        }

        if (pccDepthStr.equalsIgnoreCase("")) {
            pccdepthEdittext.requestFocus();
            pccdepthEdittext.setError("Select PCC Bedding Water Meter Protection Box Depth");
        } else {
            isValidPccDepth = true;
        }
        if (pccTotalStr.equalsIgnoreCase("")) {
            pcctotalEdittext.requestFocus();
            pcctotalEdittext.setError("Select PCC Bedding Water Meter Protection Box Total");
        } else {
            isValidPccTotal = true;
        }

        if (rdCuttingSpinnerName.equalsIgnoreCase("--Select--")) {
            MessageWindow.messageWindow(mCon,"Select Road Cutting","Alert");
        } else {
            isValidRdSpinnerName = true;
        }

        if (rdLengthStr.equalsIgnoreCase("")) {
            rdlengthEdittext.requestFocus();
            rdlengthEdittext.setError("Select RoadCutting Length");
        } else {
            isValidRdCutLen = true;
        }


        if (TextUtils.isEmpty(rdWidthStr)) {
            rdwidthEdittext.requestFocus();
            rdwidthEdittext.setError("Select RoadCutting Width");
        } else {
            isValidRdCutWidth = true;
        }


        if (TextUtils.isEmpty(rdDepthStr)) {
            rddepthEdittext.requestFocus();
            rddepthEdittext.setError("Select RoadCutting Depth");
        } else {
            isValidRdCutDepth = true;
        }

        if (TextUtils.isEmpty(rdTotalStr)) {
            rdtotalEdittext.requestFocus();
            rdtotalEdittext.setError("Select RoadCutting Total");
        } else {
            isValidRdCutTotal = true;
        }

        if (isValidPccLength && isValidPccWidth && isValidPccDepth && isValidPccTotal && isValidRdSpinnerName && isValidRdCutLen && isValidRdCutWidth &&
                isValidRdCutDepth && isValidRdCutTotal) {

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCLEN);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.PCCLEN, pccLengthStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCWIDTH);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.PCCWIDTH, pccWidthStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCWIDTH);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.PCCWIDTH, pccDepthStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCTOTAL);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.PCCTOTAL, pccTotalStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGID);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.RDCUTTINGID, roadCuttingIdStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGLENGTH);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.RDCUTTINGLENGTH, rdLengthStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGWIDTH);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.RDCUTTINGWIDTH, rdWidthStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGDEPTH);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.RDCUTTINGDEPTH, rdDepthStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGTOTAL);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.RDCUTTINGTOTAL, rdTotalStr);

            boolean is_mtr_protection_submitted = true;
            sendMeterProtectionDetails.sndMtrPrtctnDet(pccLengthStr, pccWidthStr, pccDepthStr, pccTotalStr, roadCuttingIdStr, rdLengthStr, rdWidthStr, rdDepthStr, rdTotalStr, is_mtr_protection_submitted);
            Toast.makeText(mCon, getResources().getString(R.string.details_submitted_successfully), Toast.LENGTH_SHORT).show();
            MMGScreenActivity.animationOnArrow();
        } else {
            MessageWindow.messageWindow(mCon,"Please fill all mandatory fields.","Alert");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sendMeterProtectionDetails = (SendMeterProtectionDetails) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.roadcuttingSpinner: {
                String roadcuttingName = roadcuttingSpinner.getSelectedItem().toString();
                if (roadcuttingName.equalsIgnoreCase("--Select--")) {

                } else {
                    mmgTypeOfRoadcuttingModel = realmOperations.fetchRoadcuttingByName(roadcuttingName);
                    roadCuttingId = String.valueOf(mmgTypeOfRoadcuttingModel.getRC_ID());
                    Log.e("roadCuttingId", String.valueOf(roadCuttingId));
                    roadCuttingIdStr = String.valueOf(roadCuttingId);
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

    public interface SendMeterProtectionDetails {
        void sndMtrPrtctnDet(String pccLengthStr, String pccWidthStr, String pccDepthStr, String pccTotalStr, String roadCuttingIdStr
                , String rdLengthStr, String rdWidthStr, String rdDepthStr, String rdTotalStr, boolean is_mtr_protection_submitted);
    }

    private void fetchDetailsFromContractor() {
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
            }

            //meterProtectionSetter();

        } catch (Exception e) {
            Log.e("Excpn NewMetStr", "" + e.getMessage());
        }
    }

    private void meterProtectionSetter() {
        if (MI_PCCBEDDINGLEN != null)
            pcclengthEdittext.setText(MI_PCCBEDDINGLEN);

        if (MI_PCCBEDDINGWID != null)
            pccwidthEdittext.setText(MI_PCCBEDDINGWID);

        if (MI_PCCBEDDINGDEP == null) {
        } else
            pccdepthEdittext.setText(MI_PCCBEDDINGDEP);

        if (MI_ROADCUTTINGTYPE != null)
            roadCuttingDropdown();

        if (MI_ROADCUTTINGLEN != null)
            rdlengthEdittext.setText(MI_ROADCUTTINGLEN);

        if (MI_ROADCUTTINGWID != null)
            rdwidthEdittext.setText(MI_ROADCUTTINGWID);

        if (MI_ROADCUTTINGDEP != null)
            rddepthEdittext.setText(MI_ROADCUTTINGDEP);

    }

    private void roadCuttingDropdown() {
        ArrayList<String> roadCuttingName = new ArrayList<>();
        roadCuttingName = realmOperations.fetchRoadcutting();
        ArrayList<String> roadCuttingList = new ArrayList<>();
        roadCuttingList.add("--Select--");
        roadCuttingList.addAll(roadCuttingName);

        pagename = getArguments().getString("pagename");
        contList = getArguments().getString("contList");

        if (MI_ROADCUTTINGTYPE != null) {
//            mmgTypeOfRoadcuttingModel = realmOperations.fetchRoadCutCodeId(Integer.parseInt(MI_ROADCUTTINGTYPE));
//            String makerType = mmgTypeOfRoadcuttingModel.getRC_DESC();
//            int makerId = roadCuttingList.indexOf(makerType);

            roadCuttingAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, roadCuttingList);
            roadCuttingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            roadcuttingSpinner.setAdapter(roadCuttingAdapter);
            roadcuttingSpinner.setSelection(2);
            roadcuttingSpinner.setOnItemSelectedListener(this);
        } else {
            roadCuttingAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, roadCuttingList);
            roadCuttingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            roadcuttingSpinner.setAdapter(roadCuttingAdapter);
            roadcuttingSpinner.setOnItemSelectedListener(this);
        }
    }

    private void disableNewMeterFields() {
        radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);
        if (radiobuttonValStr.equalsIgnoreCase("R")) {
            roadcuttingSpinner.setEnabled(false);
            rddepthEdittext.setEnabled(false);
            pcclengthEdittext.setEnabled(false);
            pccwidthEdittext.setEnabled(false);
            pccdepthEdittext.setEnabled(false);
            pcctotalEdittext.setEnabled(false);
            rdlengthEdittext.setEnabled(false);
            rdwidthEdittext.setEnabled(false);
            rddepthEdittext.setEnabled(false);
            rdtotalEdittext.setEnabled(false);
            MessageWindow.messageAuthenticationWindow(mCon, "You can direclty go to upload document", "Alert");
        }
    }

    private void saveDataToServer() {
        String mtrTypeCode = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_TYPE_CODE_ID);
        String oldmeterSizeNum = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_SIZE_ID);

//        String emp_code = PreferenceUtil.getUserName();
        String emp_code = null;
        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ip_str = PreferenceUtil.getMac();

        pcclengthStr = pcclengthEdittext.getText().toString();
        pccwidthStr = pccwidthEdittext.getText().toString();
        pccdepthStr = pccdepthEdittext.getText().toString();

        rdlengthStr = rdlengthEdittext.getText().toString();
        rdwidthStr = rdwidthEdittext.getText().toString();
        rddepthStr = rddepthEdittext.getText().toString();

        String params[] = new String[55];
        params[0] = "N";                            //IsSubmit
        if (isConsumerSubmitted) {
            params[1] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);             //Action
            params[2] = consumerNoStr;                 //Consumer
        } else {
            params[1] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);             //Action
            params[2] = consumerNoStr;                 //Consumer
        }

        params[3] = ConsumerDetFragmentNew.BU;
        params[4] = ConsumerDetFragmentNew.PC;
        params[5] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMERREFERENCENUMBER);             //Action

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
            params[6] = "";
            params[7] = "";
            params[8] = "";
            params[9] = "";
            params[10] = "";
            params[11] = "";
            params[12] = "";
            params[13] = "";
            params[14] = "";
        }

        if (radiobuttonValStr.equals("OH")) {
            params[15] = "";
            params[16] = "";                     // N_Size
            params[17] = "";                    // N_Seal
            params[18] = "";                    // N_Meter
            if (installDtStr != null)
                params[19] = installDtStr;          // InstallationDate
            else
                params[19] = MI_INSTALLATIONDATE;
            params[20] = "";                    // N_InitialReading
            params[21] = "";                    // N_MeterType
            params[22] = "";                    // N_MeterLocation
            params[23] = "";                    // N_IsProtected
            params[25] = "";                   // N_IsMeterHandovertoConsumer
        } else {
            if (is_newmeter_submitted) {
                params[15] = NewMeterDetFragment.MI_N_MAKE;
                params[16] = NewMeterDetFragment.MI_N_SIZE;
                params[17] = NewMeterDetFragment.MI_N_SEAL;
                params[18] = NewMeterDetFragment.MI_N_METER;
                params[19] = NewMeterDetFragment.strCurrentDate;
                params[20] = NewMeterDetFragment.MI_N_INITIALREADING;
                params[21] = NewMeterDetFragment.MI_N_METERTYPE;
                params[22] = NewMeterDetFragment.MI_N_METERLOCATION;
                params[23] = NewMeterDetFragment.MI_N_ISPROTECTED;
                params[25] = NewMeterDetFragment.MI_N_ISMETERHANDOVER;
            } else {
                params[15] = NewMeterDetFragment.MI_N_MAKE;
                params[16] = NewMeterDetFragment.MI_N_SIZE;
                params[17] = NewMeterDetFragment.MI_N_SEAL;
                params[18] = NewMeterDetFragment.MI_N_METER;
                params[19] = NewMeterDetFragment.strCurrentDate;
                params[20] = NewMeterDetFragment.MI_N_INITIALREADING;
                params[21] = NewMeterDetFragment.MI_N_METERTYPE;
                params[22] = NewMeterDetFragment.MI_N_METERLOCATION;
                params[23] = NewMeterDetFragment.MI_N_ISPROTECTED;
                params[25] = NewMeterDetFragment.MI_N_ISMETERHANDOVER;
            }
        }

        if (isConsumerSubmitted)
            params[24] = ConsumerDetFragmentNew.property_assessmnt;      // PropertyTaxNo
        else
            params[24] = ConsumerDetFragmentNew.property_assessmnt;      // PropertyTaxNo


//        if(is_contractor_submitted) {
//            if(contractorId.equalsIgnoreCase("OTHER") || contractorId.equalsIgnoreCase("") ||
//                    cntrctrNameIdStr.equalsIgnoreCase("OTHER")){
//                params[26] = "-99";
//            } else {
//                params[26] = contractorId;          // Contractor id in general case and (-99) in case of OTHER
//            }
//            params[27] = c_employee_id;
//            params[28] = otherContractorStr;
//            params[29] = otherContractorEmpStr;
//            params[30] = materialHandoverStr;
//        }else {
//            if(MI_CONTRACTOR == null || MI_CONTRACTOR.equalsIgnoreCase("")|| MI_CONTRACTOR.equalsIgnoreCase("0")){
//                String mmgFixer = PreferenceUtil.getMMGFixer();
//                params[26] = mmgFixer;
//            }else {
//                params[26] = MI_CONTRACTOR;
//            }
//            params[27] = MI_CONTRACTOREMP;
//            params[28] = MI_CONTRACTOROTHER;
//            params[29] = MI_CONTRACTOREMPOTHER;
//            params[30] = MI_N_ISMATERIALHANDOVER;
//        }

        params[26] = ContractorDetFragmentNew.c_id;
        params[27] = ContractorDetFragmentNew.c_emp_id;
        params[28] = ContractorDetFragmentNew.vendorCodeStr;
        params[29] = ContractorDetFragmentNew.other_code_id;
        params[30] = ContractorDetFragmentNew.removedHandverIdStr;

        if (is_material_submitted) {
            params[31] = MaterialDetFragment.materialDetailXml.toString();
        } else {
            params[31] = MaterialDetFragment.materialDetailXml.toString();
        }

        params[32] = CivilMeasurementFragment.cvlMeasurementXml.toString();

        params[33] = pcclengthStr;
        params[34] = pccwidthStr;
        params[35] = pccdepthStr;
        params[36] = roadCuttingIdStr;
        params[37] = rdlengthStr;
        params[38] = rdwidthStr;
        params[39] = rddepthStr;

        if (isConsumerSubmitted) {
            params[40] = ConsumerDetFragmentNew.fromNodeStr;
            params[41] = ConsumerDetFragmentNew.toNodeStr;
            params[42] = ConsumerDetFragmentNew.primaryMobStr;
            params[43] = ConsumerDetFragmentNew.alternateMobStr;
            params[44] = ConsumerDetFragmentNew.gis_bidStr;
            params[45] = ConsumerDetFragmentNew.dmaStr;
            params[46] = ConsumerDetFragmentNew.srStr;
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
            params[48] = NewMeterDetFragment.dial_digit;                    //N_DIGIT
        else
            params[48] = NewMeterDetFragment.dial_digit;

        params[49] = emp_code;
        params[50] = ip_str;
        params[51] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_METERINSTALLID);             //Action
        params[52] = NewMeterDetFragment.sealmakeId;
        params[53] = NewMeterDetFragment.meterboxmakeId;
        params[54] = "MPF";

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
                String paraName[] = new String[55];
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


                jsonMeterInstallSaveResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "MeterInstallationSave", params, paraName);
                Log.e("jsonResponse", jsonMeterInstallSaveResponse);
                Log.e("params", String.valueOf(params));

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
                    MessageWindow.throwOutMMMFragment(mCon, "Meter Protection Details Saved Successfully", "Success", MainActivity.class);
                    clearAllSharedPrefs();
                    return;
                } else if (enums[0].equalsIgnoreCase("Failure")) {
                    MessageWindow.throwOutFromWindow(mCon, "Details Not Saved", "Alert", MainActivity.class);

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

    public void displayReceivedData(String makerCodeId, String oldmeterno, String sealNoStr, String installDtStr,
                                    String coonectionLoad, String pastMeterReadingStr, String submitStatus, String radiobuttonValStr,
                                    String consumerNoStr, String zoneStr, String groupStr, String refTypeStr, String connSizeStr,
                                    String property_assessmnt, String fromNodeStr, String toNodeStr, String primaryMobStr, String alternateMobStr,
                                    String gis_bidStr, String dmaId, String srId, String msrId, String commisioned_noncommisioned,
                                    String employee_code, String mac_address, String mContList, boolean isSubmitted) {

//        zone_str = zoneStr;
//        group_str = groupStr;
        from_node_str = fromNodeStr;
        to_node_str = toNodeStr;
        primary_mob_str = primaryMobStr;
        alternate_mob_str = alternateMobStr;
        gis_bid_str = gis_bidStr;
        dma_id = dmaId;
        sr_id = srId;
        property_assess = property_assessmnt;
        isConsumerSubmitted = isSubmitted;
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
    }

    public void displayContracterDet(String c_id, String c_emp_id, String contractorIdStr, String vendorCodeStr,
                                     String contractorEmpIdStr, String removedHandverIdStr, String otherContractor,
                                     String otherContractorEmp, String other_code_id, boolean isContractorSubmitted) {

        cntrctrNameIdStr = contractorIdStr;//other
//        vendorcodeStr = vendorCodeStr; //fixer emp
//        cntrctrEmpIdStr = contractorEmpIdStr; //
        materialHandoverStr = removedHandverIdStr;
        contractorId = c_id;
        c_employee_id = c_emp_id;
        otherContractorStr = vendorCodeStr;
        otherContractorEmpStr = otherContractorEmp;
//        other_code_idStr = other_code_id;
        is_contractor_submitted = isContractorSubmitted;

    }


    public void displayMaterialDet(List materiallist, boolean isMaterialSubmitted) {
        is_material_submitted = isMaterialSubmitted;

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


    }

    public void displayCvlMeasurementDet(List cvlMeasurementList) {

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
            Log.e("MeasurementDetails", cvlMeasurementXml.toString());
        }
    }

    public void displayCvlMeasurementDet(List cvlMeasurementList, boolean isCivilSubmitted) {
        is_civil_submitted = isCivilSubmitted;

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
            Log.e("MeasurementDetails", cvlMeasurementXml.toString());
        }
    }

    private void clearAllSharedPrefs(){
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
    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);
        if (this.isVisible()) {
            if (isFragmentVisible_) {
                new MeterProtectionDetFragment();
                disableNewMeterFields();
                //fetchDetailsFromContractor();
//                _hasLoadedOnce = false;
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


    @Override
    public void onDetach() {
        super.onDetach();
        mCon = null;
    }
}



