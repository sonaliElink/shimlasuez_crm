package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MDialDigitModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterStatusModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGObersvationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MStatusObservationModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGScreenActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6.OldMeterDetailAnnex6;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGCustDetModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGGetDetailsResponseModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class OldMeterDetFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Context mCon;
    LinearLayout liner_FinalReading, liner_FinalStatus, liner_ReasonForReplacement;
    TextView mtrMakeTextView, mtrNoTextView, installDtTextView, sealNoTextView, previousReadingTextView;
    EditText finalReadingEdittext, etMtrSize, etMeterStatus, et_type_of_meter,etaverage_consum;
    Spinner meterRsnSpinner, finalStatusSpinner, observationSpinner;
    String mfgNameRecvd = "", serialNoRecvd = "", installDtRecvd = "", meterSizeRecvd = "", sealNoRecvd = "", pastMeterReadingRecvd = "",
            meterStatusStr = "", meterTypeStr = "", finalStatusSelected = "", observationSelectedName = "", connSizeStr = "", reasonOfReplacementName = "";
    private RealmOperations realmOperations;
    private ArrayAdapter meterTypeAdapter, meterStatusAdapter, meterReasonAdapter, finalStatusAdapter, observationAdapter;
    private MMGMeterTypeModel mmgMeterTypeModel;
    private MMGMeterStatusModel mmgMeterStatusModel;
    private MMGObersvationModel mmgObersvationModel;
    private MDialDigitModel mDialDigitModel;
    boolean validSaveAndExit = false;
    String meterTypeId = "", meterTypeIdStr = "", meterSttausName = "", meterStatusId = "", makerCodeIdStr = "",
            meterStatusIdStr = "", finalReadingValStr = "", finalStsValStr = "", reasnFrReplacementValStr = "", meterReasonId,
            meterResonStr = "", meterReasonIdStr = "", finalStatsuId = "", finalStatusIdStr = "", coonectionLoad = "",
            makercodename = "", serachById = "", consumerNoStr = "", radiobuttonValStr = "", refNoStr = "", BU, PC,
            meterObservationId = "", meterObservationName = "", commisioned_noncommisioned = "", reasonName = "",
            jsonCustomerDetailResponse = "", jsonMeterInstallSaveResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    SendOldMeterDetails sendOldMeterDetails;
    Button submitButton, saveAndExit;
    static private boolean isOldSubmitted = false, isConsumerSubmitted = false;
    private MaterialDialog progress;

    private MStatusObservationModel mStatusObservationModel;
    private ArrayList<String> meterObservationList = new ArrayList<>();
    ArrayList<String> meterReasonList, meterResonName;
    String installDtStr = "", sealNoStr = "", pastMeterReadingStr = "";
    boolean isValidFinalReading = false;

    MaterialDialog mmgMasterProgress;

    private String old_meter_status_str = "", meterSizeStr = "", manufactureTypeCode_str = "",
            manufactureCode = "", mfgNameStr = "", old_meter_status_name_str = "",avrage_consumtion_str = "";

    String MI_METERINSTALLID = "", MI_ACTION = "", MI_CONSUMER = "", MI_BU = "", MI_PC = "", MI_REFNO = "", MI_O_SIZE = "", O_SIZE = "", MI_O_METER = "", MI_O_MAKE = "", MI_O_PREVIOUSREADING = "", MI_O_FINALREADING = "", MI_O_FINALSTATUS = "", MI_O_REASON = "", dataFound, MI_O_METERTYPE = "",
            MI_N_MAKE = "", MI_N_SIZE = "", MI_N_SEAL = "", MI_N_METER = "", MI_INSTALLATIONDATE = "", MI_N_INITIALREADING = "", MI_N_METERTYPE = "", MI_N_METERLOCATION = "", MI_N_ISPROTECTED = "", MI_PROPERTYTAXNO = "", MI_N_ISMETERHANDOVER = "",
            MI_CONTRACTOR = "", MI_CONTRACTOREMP = "", MI_N_ISMATERIALHANDOVER = "", MI_PCCBEDDINGLEN = "", MI_PCCBEDDINGWID = "",
            MI_PCCBEDDINGDEP = "", MI_ROADCUTTINGTYPE = "", MI_ROADCUTTINGLEN = "", MI_ROADCUTTINGWID = "", MI_ROADCUTTINGDEP = "", MI_FROMNODE = "",
            MI_TONODE = "", MI_REGMOBILE = "", MI_ALTMOBILE = "", MI_GIS = "", MI_DMA = "", MI_SR = "", MI_MODIFIEDBY = "", MI_MODIFIEDDATE = "", MI_IP = "", MI_AUTHENTICATEDATE = "",
            MI_AUTHREJECTBY = "", MI_REJECTEDDATE = "", MI_STATUS = "", MI_ISACTIVE = "", MI_XMLMATERIAL = "", MI_XMLCIVIL = "", MI_O_OBSERVATION = "",
            MI_SOURCE = "", MI_ISCOMMISSIONED = "", MI_CONTRACTOROTHER = "", MI_CONTRACTOREMPOTHER = "", MI_N_DIGIT = "", MSRID = "", MI_DATA_FOUND, MI_N_METER_BOX_MAKE = "", MI_N_SEAL_MAKE = "";
    static String serialNoStr = "", contList = "", pagename = "", mtrSizeId = "";
    private String makerCodeId = "", previousReading = "", sealNo = "", mtrSize = "", type_of_meter = "",
            meterStatus = "", installDt = "", mtrNo = "", zone_str = "", group_str = "", to_node_str = "",
            from_node_str, primary_mob_str, alternate_mob_str, gis_bid_str, dma_id, mrs_id,sr_id, property_assess, lastReadingDate, finalReadingDigits;
    ArrayList<MMGCustDetModel> customerDetailList = new ArrayList<>();
    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = new ArrayList<>();
    ArrayList<InstallDetails> installDetails = new ArrayList<>();
    ArrayList<MMGValidateDetails> validateDetailList = new ArrayList<>();

    String STARTTIME = "", ALERTSTARTTIME = "",deviceAuthorization = "", appIsLogged = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        mCon = getActivity();
        realmOperations = new RealmOperations(mCon);
        View view = inflater.inflate(R.layout.fragment_old_meter_det, container, false);
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        MMGScreenActivity activity = (MMGScreenActivity) getActivity();

        customerDetailList = activity.getMMGCustomerDetailsData();
        meterConnectionList = activity.getMMGMeterConnectionDetailsData();
        installDetails = activity.getMMGInstallDetailsData();
        validateDetailList = activity.getMmgvalidateDetailList();
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
        init(view);

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
        }
*/
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

    private void init(View view) {
        consumerNoStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMER_NO);
        BU = UtilitySharedPreferences.getPrefs(getActivity(), Constants.BU);
        PC = UtilitySharedPreferences.getPrefs(getActivity(), Constants.PC);

        mtrMakeTextView = view.findViewById(R.id.mtrMakeTextView);
        mtrNoTextView = view.findViewById(R.id.mtrNoTextView);
        sealNoTextView = view.findViewById(R.id.sealNoTextView);
        etMtrSize = view.findViewById(R.id.etMtrSize);
        installDtTextView = view.findViewById(R.id.installDtTextView);
        etMeterStatus = view.findViewById(R.id.etMeterStatus);
        et_type_of_meter = view.findViewById(R.id.et_type_of_meter);
        previousReadingTextView = view.findViewById(R.id.previousReadingTextView);
        etaverage_consum = view.findViewById(R.id.etaverage_consum);
        finalReadingEdittext = view.findViewById(R.id.finalReadingEdittext);

        finalStatusSpinner = view.findViewById(R.id.finalStatusSpinner);
        observationSpinner = view.findViewById(R.id.observationSpinner);
        meterRsnSpinner = view.findViewById(R.id.meterRsnSpinner);

        liner_FinalReading = view.findViewById(R.id.liner_FinalReading);
        liner_FinalStatus = view.findViewById(R.id.liner_FinalStatus);
        liner_ReasonForReplacement = view.findViewById(R.id.liner_ReasonForReplacement);

        submitButton = view.findViewById(R.id.submitButton);
        saveAndExit = view.findViewById(R.id.saveAndExit);
        submitButton.setOnClickListener(this);
        saveAndExit.setOnClickListener(this);

        commisioned_noncommisioned = UtilitySharedPreferences.getPrefs(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);
        radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);

        if (radiobuttonValStr.equalsIgnoreCase("S") || radiobuttonValStr.equalsIgnoreCase("MB")) {
            mtrMakeTextView.setEnabled(false);
            mtrNoTextView.setEnabled(false);
            installDtTextView.setEnabled(false);
            etMeterStatus.setEnabled(false);
            et_type_of_meter.setEnabled(false);
            etMtrSize.setEnabled(false);
            sealNoTextView.setEnabled(false);
            previousReadingTextView.setEnabled(false);
        }

        pagename = getArguments().getString("pagename");
        serachById = getArguments().getString("serachById");
        consumerNoStr = getArguments().getString("consumerNoStr");
        refNoStr = getArguments().getString("refNoStr");



            setOldMeterData();



        ArrayList<String> meterTypeName = new ArrayList<>();
        meterTypeName = realmOperations.fetchMeterType();
        ArrayList<String> meterTypeList = new ArrayList<>();
        meterTypeList.add("--Select--");
        meterTypeList.addAll(meterTypeName);

        meterTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterTypeList);
        meterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayList<String> meterStatusName = new ArrayList<>();
        ArrayList<String> meterStatusList = new ArrayList<>();
        meterStatusList.add("--Select--");
        meterStatusList.addAll(meterStatusName);

        meterStatusAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterStatusList);
        meterStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        meterReasonDefaultDropdown();

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

                        if ((pagename == null) || (pagename.equalsIgnoreCase("MeterInstallationContractorDet"))) {
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


                        if (meterObservationId.equalsIgnoreCase("1") || meterObservationId.equalsIgnoreCase("2")
                                || meterObservationId.equalsIgnoreCase("25") || meterObservationId.equalsIgnoreCase("26")
                                || meterObservationId.equalsIgnoreCase("27") || meterObservationId.equalsIgnoreCase("9")
                                || meterObservationId.equalsIgnoreCase("12")) {
                            finalReadingEdittext.setEnabled(true);
                            isValidFinalReading = false;

                        } else {
                            finalReadingEdittext.setText("");
                            finalReadingEdittext.setEnabled(false);
                            isValidFinalReading = true;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        meterRsnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                meterResonStr = meterRsnSpinner.getSelectedItem().toString();


                if (meterResonStr.equalsIgnoreCase("--Select--")) {
                    // Toast.makeText(mCon, "Please select meter Size", Toast.LENGTH_SHORT).show();
                } else {

                    mmgObersvationModel = realmOperations.fetchObservationByName(meterResonStr);
                    meterReasonId = String.valueOf(mmgObersvationModel.getOCRM_ID());
                    meterReasonIdStr = String.valueOf(meterReasonId);
                    //Log.e("meterReasonId",meterReasonId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setOldMeterData() {

        try {
            for (int i = 0; i <= customerDetailList.size() - 1; i++) {
                if (meterConnectionList.size() != 0) {
                    if (meterConnectionList.get(i).getO_METERTYPE() == null) {
                        meterTypeStr = "";
                    } else {
                        meterTypeStr = meterConnectionList.get(i).getO_METERTYPE();
                    }
                }

                if (customerDetailList.get(i).getSRM_CONNECTION_LOAD() == null ||
                        customerDetailList.get(i).getSRM_CONNECTION_LOAD().equalsIgnoreCase("")) {
                    connSizeStr = "";
                } else {
                    connSizeStr = customerDetailList.get(i).getSRM_CONNECTION_LOAD();
                }
                if (meterConnectionList.size() != 0) {
                    if (meterConnectionList.get(i).getSRM_CONNSIZE_ID() == null) {
                        mtrSizeId = "";
                    } else {
                        mtrSizeId = meterConnectionList.get(i).getSRM_CONNSIZE_ID();
                    }
                }

            }

            try {
                if (meterConnectionList.size() != 0) {
                    if (meterConnectionList != null) {

                        ArrayList<MMGMeterConnectedDetailsModel> mmgMeterConnectionDetailList = new ArrayList<>();
                        for (int j = 0; j <= meterConnectionList.size() - 1; j++) {

                            if (meterConnectionList.get(j).getMTRM_MANUFACTURE_CODE() == null) {
                                manufactureCode = "";
                            } else {
                                manufactureCode = meterConnectionList.get(j).getMTRM_MANUFACTURE_CODE();
                            }
                            if (meterConnectionList.get(j).getMTRM_MANUFACTURE_CODE() == null) {
                                MI_O_FINALSTATUS = "";
                            } else {
                                MI_O_FINALSTATUS = meterConnectionList.get(j).getMTRM_METERSTATUS_ID();
                            }
                            if (meterConnectionList.get(j).getMMFG_MFGNAME() == null) {
                                mfgNameStr = "";

                            } else {
                                mfgNameStr = meterConnectionList.get(j).getMMFG_MFGNAME();
                            }

                            if (meterConnectionList.get(j).getMTRM_SERIAL_NO() == null) {
                                serialNoStr = "";
                            } else {
                                serialNoStr = meterConnectionList.get(j).getMTRM_SERIAL_NO();
                            }





                            if (meterConnectionList.get(j).getLASTREADINGDATE() == null) {
                                lastReadingDate = "";
                            } else {
                                lastReadingDate = meterConnectionList.get(j).getLASTREADINGDATE();
                            }

                            if (meterConnectionList.get(j).getMRT_OLDMETER_STATUS() == null) {
                                old_meter_status_str = "";
                            } else {
                                old_meter_status_str = meterConnectionList.get(j).getMRT_OLDMETER_STATUS();
                            }

                            if (meterConnectionList.get(j).getMSM_METERSTATUS_NAME() == null) {
                                old_meter_status_name_str = "";
                            } else {
                                old_meter_status_name_str = meterConnectionList.get(j).getMSM_METERSTATUS_NAME();
                            }

                            if (meterConnectionList.get(j).getMTRM_ESTIMATED_AVRG_CONSUMP() == null) {
                                avrage_consumtion_str = "";
                            } else {
                                avrage_consumtion_str = meterConnectionList.get(j).getMTRM_ESTIMATED_AVRG_CONSUMP();
                            }


                            if (meterConnectionList.get(j).getMTRM_MANUFACTURE_TYPE_CODE() == null) {
                                manufactureTypeCode_str = "";
                            } else {
                                manufactureTypeCode_str = meterConnectionList.get(j).getMTRM_MANUFACTURE_TYPE_CODE();
                            }

                            if (meterConnectionList.get(j).getMTRM_PAST_READING() == null) {
                                pastMeterReadingStr = "";
                            } else {
                                pastMeterReadingStr = meterConnectionList.get(j).getMTRM_PAST_READING();
                            }

                            if (meterConnectionList.get(j).getMTRM_INSTALLATION_DATE() == null) {
                                installDtStr = "";
                            } else {
                                installDtStr = meterConnectionList.get(j).getMTRM_INSTALLATION_DATE();
                            }

                            if (meterConnectionList.get(j).getSIZEOFMETER() == null) {
                                meterSizeStr = "";
                            } else {
                                meterSizeStr = meterConnectionList.get(j).getSIZEOFMETER();
                            }

                            if (meterConnectionList.get(j).getSEALNO() == null) {
                                sealNoStr = "";
                            } else {
                                sealNoStr = meterConnectionList.get(j).getSEALNO();
                            }

                            if (meterConnectionList.get(j).getLASTREADINGDATE() == null) {
                                lastReadingDate = "";
                            } else {
                                lastReadingDate = meterConnectionList.get(j).getLASTREADINGDATE();
                            }

                            if (meterConnectionList.get(j).getMTRM_DIGITS() == null) {
                                finalReadingDigits = "";
                            } else {
                                finalReadingDigits = meterConnectionList.get(j).getMTRM_DIGITS();
                            }
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            if (installDetails != null) {

                for (int k = 0; k <= installDetails.size() - 1; k++) {
                    dataFound = installDetails.get(k).getNODATAFOUND();

                    if (dataFound.equalsIgnoreCase("DataFound")) {
                        MI_METERINSTALLID = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_METERINSTALLID);
                        MI_O_FINALSTATUS = installDetails.get(k).getMI_O_FINALSTATUS();
                        MI_O_REASON = installDetails.get(k).getMI_O_REASON();
                        MI_O_OBSERVATION = installDetails.get(k).getMI_O_OBSERVATION();
                        MI_O_FINALREADING = installDetails.get(k).getMI_O_FINALREADING();
                        //Log.e("MI_O_REASON",MI_O_REASON);
                    } else {
                        MI_METERINSTALLID = "";
                        MI_O_FINALSTATUS = "";
                        MI_O_REASON = "";
                        MI_O_OBSERVATION = "";
                        MI_O_FINALREADING = "";
                    }
                }
            }

            mtrMakeTextView.setText(manufactureCode + "-" + mfgNameStr);
            mtrNoTextView.setText(serialNoStr);
            installDtTextView.setText(installDtStr);
            etMeterStatus.setText(old_meter_status_name_str);
            et_type_of_meter.setText(meterTypeStr);
            etaverage_consum.setText(avrage_consumtion_str);
            etMtrSize.setText(connSizeStr);
            sealNoTextView.setText(sealNoStr);

            previousReadingTextView.setText(pastMeterReadingStr);

            if (finalReadingDigits == null ||finalReadingDigits.equalsIgnoreCase("") || finalReadingDigits.equalsIgnoreCase("NA")) {
                finalReadingEdittext.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});
            } else {
                finalReadingEdittext.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Integer.parseInt(finalReadingDigits))});
            }

            if (MI_O_FINALSTATUS == null || MI_O_FINALSTATUS.equalsIgnoreCase("")
                    || MI_O_FINALSTATUS.equalsIgnoreCase("0")) {
                finalStatusDD();
            } else {
                finalStatusDropdown();
            }

            if (MI_O_REASON == null || MI_O_REASON.equalsIgnoreCase("") || MI_O_REASON.equalsIgnoreCase("0")) {
                meterReasonDefaultDropdown();
            } else {
                meterReasonDropdown();
            }

            if (MI_O_OBSERVATION == null || MI_O_OBSERVATION.equalsIgnoreCase("") || MI_O_OBSERVATION.equalsIgnoreCase("0")) {
                defaultObservation();
            } else {
                observReasonDropdown();
            }


        } catch (Exception e) {
            Log.e("Get_CustomerDetails As", e.toString());
            String error = e.toString();
            ErrorClass.errorData(mCon, "Consumer Details Fragment", "Get Customer details task", error);
        }

    }

    private void setReasonOfReplacement(String s) {
        mmgObersvationModel = realmOperations.fetchObservationByID(s);
        if (mmgObersvationModel == null) {
            meterRsnSpinner.setSelection(0);

        } else {
            reasonOfReplacementName = mmgObersvationModel.getOCRM_NAME();
            int catInt = getCategoryPos(reasonOfReplacementName);
            meterRsnSpinner.setSelection(catInt);
        }
    }

    private int getCategoryPos(String category) {
        return meterReasonList.indexOf(category);
    }

    @SuppressLint("SetTextI18n")
    public void displayReceivedData(String makerCodeId, String oldmeterno, String sealNoStr, String installDtStr,
                                    String coonectionLoad, String pastMeterReadingStr,
                                    String submitStatus, String radiobuttonValStr, String zoneStr,
                                    String groupStr, String refTypeStr, String connSizeStr, String property_assessmnt, String fromNodeStr,
                                    String toNodeStr, String primaryMobStr, String alternateMobStr, String gis_bidStr, String dmaId,
                                    String srId, String msrId, String commisioned_noncommisioned, String employee_code, String mac_address,
                                    String mContList, boolean isSubmitted) {

        zone_str = zoneStr;
        group_str = groupStr;
        from_node_str = fromNodeStr;
        to_node_str = toNodeStr;
        primary_mob_str = primaryMobStr;
        alternate_mob_str = alternateMobStr;
        gis_bid_str = gis_bidStr;
        dma_id = dmaId;
        sr_id = srId;
        mrs_id = msrId;
        property_assess = property_assessmnt;

        makercodename = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MAKERCODENAME);
        makerCodeIdStr = makerCodeId;
        serialNoRecvd = oldmeterno;
        installDtRecvd = installDtStr;
        meterSizeRecvd = coonectionLoad;
        sealNoRecvd = sealNoStr;
        pastMeterReadingRecvd = pastMeterReadingStr;
        meterStatusStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.METERSTATUS);
        meterTypeStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.METERTYPENAME);
//        avrage_consumtion_str = UtilitySharedPreferences.getPrefs(getActivity(),Constants.NEW_AVERAGECONSUMTION);
        contList = mContList;
        isConsumerSubmitted = isSubmitted;

        if (serialNoRecvd.equalsIgnoreCase("")) {
            mtrMakeTextView.setEnabled(false);
            mtrNoTextView.setEnabled(false);
            installDtTextView.setEnabled(false);
            etMeterStatus.setEnabled(false);
            et_type_of_meter.setEnabled(false);
            etMtrSize.setEnabled(false);
            sealNoTextView.setEnabled(false);
            previousReadingTextView.setEnabled(false);
            etaverage_consum.setEnabled(false);

            mtrMakeTextView.setText("");
            mtrNoTextView.setText("");
            installDtTextView.setText("");
            etMeterStatus.setText("");
            et_type_of_meter.setText("");
            etMtrSize.setText("");
            sealNoTextView.setText("");
            previousReadingTextView.setText("");
        } else {
            mtrMakeTextView.setText(makerCodeId + "-" + makercodename);
            mtrNoTextView.setText(serialNoRecvd);
            installDtTextView.setText(installDtRecvd);
            etMeterStatus.setText(meterStatusStr);
            et_type_of_meter.setText(meterTypeStr);
            etMtrSize.setText(coonectionLoad);
            sealNoTextView.setText(sealNoRecvd);
            previousReadingTextView.setText(pastMeterReadingRecvd);
            etaverage_consum.setText(avrage_consumtion_str);

            fetchDetailsFromContractor();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton: {
                isOldSubmitted = true;
                finalReadingValStr = finalReadingEdittext.getText().toString();
                //finalStatusIdStr = String.valueOf(finalStatusSpinner.getSelectedItemPosition());
                reasnFrReplacementValStr = String.valueOf(meterRsnSpinner.getSelectedItemPosition());
                meterTypeIdStr = et_type_of_meter.getText().toString();

                validate();
            }
            break;
            case R.id.saveAndExit:
//                saveOldMeterDetails();

                finalReadingValStr = finalReadingEdittext.getText().toString();
                reasnFrReplacementValStr = String.valueOf(meterRsnSpinner.getSelectedItemPosition());

                makerCodeId = mtrMakeTextView.getText().toString();
                mtrNo = mtrNoTextView.getText().toString();
                installDt = installDtTextView.getText().toString();
                meterStatus = etMeterStatus.getText().toString();
                type_of_meter = et_type_of_meter.getText().toString();
                mtrSize = etMtrSize.getText().toString();
                sealNo = sealNoTextView.getText().toString();
                previousReading = previousReadingTextView.getText().toString();
                meterTypeIdStr = et_type_of_meter.getText().toString();

                if (meterObservationId.equalsIgnoreCase("1") || meterObservationId.equalsIgnoreCase("2")
                        || meterObservationId.equalsIgnoreCase("25") || meterObservationId.equalsIgnoreCase("26")
                        || meterObservationId.equalsIgnoreCase("27") || meterObservationId.equalsIgnoreCase("9")) {

                    int finalReading =0;
                    int prevReading = 0;

                    if(!finalReadingValStr.equalsIgnoreCase("")){
                         finalReading = Integer.parseInt(finalReadingValStr);
                    }
                    if(!previousReading.equalsIgnoreCase("")){
                         prevReading = Integer.parseInt(previousReading);
                    }
                    if (finalReading >= prevReading) {
                        validSaveAndExit = true;
                    } else {
                        finalReadingValStr = "";
                        finalReadingEdittext.setText("");
                        MessageWindow.errorWindow(mCon,"Invalid Final Reading");
                    }
                } else {
                    validSaveAndExit = true;
                }

                if (validSaveAndExit) {
                    saveDataToServer();
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.finalStatusSpinner:
                finalStatusSelected = finalStatusSpinner.getSelectedItem().toString();

                if (finalStatusSelected.equalsIgnoreCase("--Select--")) {
                    ArrayList<String> subAll = new ArrayList<>();
                    subAll.add("--Select--");

                    observationAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, subAll);
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

                            observationAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, contractorEmpList);
                            observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            observationSpinner.setAdapter(observationAdapter);
                            finalReadingEdittext.setText("");
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

                            observationAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, contractorEmpList);
                            observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            observationSpinner.setAdapter(observationAdapter);
                            finalReadingEdittext.setText("");
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

    public interface SendOldMeterDetails {
        void sndOldMtrDet(String oldmakerCodeId, String oldmeterNoStr, String oldinstallDtStr, String oldmeterSizeStr, String oldsealNoStr,
                          String pastReadingStr, String oldMtrStsId, String oldmeterTypeId, String finalReadingStr,
                          String finalStatusStr, String meterObservationId, String reasnForReplacementStr, String contList,
                          boolean isOldSubmitted);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sendOldMeterDetails = (SendOldMeterDetails) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    public void validate() {
        boolean isValidFinalStatus = false, isValidReasnForReplacement = false, isMeterMake = false, isMeterNo = false, isObservation = false;
        if ((finalStatusSelected.equalsIgnoreCase("--Select--") && !(radiobuttonValStr.equalsIgnoreCase("N")))
                || observationSelectedName.equalsIgnoreCase("--Select--") || (meterResonStr.equalsIgnoreCase("--Select--")
        )) {
            if (finalStatusSelected.equalsIgnoreCase("--Select--") && !(radiobuttonValStr.equalsIgnoreCase("N"))) {
                liner_FinalStatus.requestFocus();
                MessageWindow.messageWindow(mCon,"Please Select Final Status","Alert");
                return;
            } else if (observationSelectedName.equalsIgnoreCase("--Select--") && !(radiobuttonValStr.equalsIgnoreCase("N"))) {
                MessageWindow.messageWindow(mCon,"Please Select Observation","Alert");
                return;
            } else if (meterResonStr.equalsIgnoreCase("--Select--") && !radiobuttonValStr.equalsIgnoreCase("N")) {
                liner_ReasonForReplacement.requestFocus();
                MessageWindow.messageWindow(mCon,"Please Select Reason For Replacement","Alert");

                return;
            }

        } else {
            isValidFinalStatus = true;
            isObservation = true;
            isValidReasnForReplacement = true;
        }

        if (finalReadingValStr.equalsIgnoreCase("")) {
            if (radiobuttonValStr.equalsIgnoreCase("N") && finalStatsuId.equalsIgnoreCase("")) {
                isValidFinalReading = true;
            } else {
                if (meterObservationId.equalsIgnoreCase("")) {
                    MessageWindow.messageWindow(mCon,"Please Check Status or Observation","Alert");

                } else if (meterObservationId.equalsIgnoreCase("1") || meterObservationId.equalsIgnoreCase("2")
                        || meterObservationId.equalsIgnoreCase("25") || meterObservationId.equalsIgnoreCase("26")
                        || meterObservationId.equalsIgnoreCase("27") || meterObservationId.equalsIgnoreCase("9")
                        || meterObservationId.equalsIgnoreCase("12")) {
                    finalReadingEdittext.setEnabled(true);
                    isValidFinalReading = false;
                    liner_FinalReading.requestFocus();
                    MessageWindow.messageWindow(mCon,"Please Enter Final Reading","Alert");

                } else {
                    finalReadingEdittext.setText("");
                    finalReadingEdittext.setEnabled(false);
                    isValidFinalReading = true;
                }
            }
        } else {
            if (meterObservationId.equalsIgnoreCase("1") || meterObservationId.equalsIgnoreCase("2")
                    || meterObservationId.equalsIgnoreCase("25") || meterObservationId.equalsIgnoreCase("26")
                    || meterObservationId.equalsIgnoreCase("27") || meterObservationId.equalsIgnoreCase("9")) {

                int finalReading = Integer.parseInt(finalReadingValStr);
                int previousReading = Integer.parseInt(previousReadingTextView.getText().toString());

                if (finalReading >= previousReading) {
                    isValidFinalReading = true;
                } else {
                    finalReadingValStr = "";
                    finalReadingEdittext.setText("");
                    MessageWindow.errorWindow(mCon,"Invalid Final Reading");

                }
            } else {
                isValidFinalReading = true;
            }
        }

        if (isValidFinalReading && isValidFinalStatus && isValidReasnForReplacement && isObservation) {
            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_MAKERCODE);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_MAKERCODE, makerCodeIdStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METERNUM);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_METERNUM, serialNoRecvd);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDT);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_INSTALLDT, installDtRecvd);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METERSIZE);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_METERSIZE, meterSizeRecvd);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDSEALNUM);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLDSEALNUM, sealNoRecvd);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PASTMETERNO);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.PASTMETERNO, pastMeterReadingRecvd);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDMTRSTS);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLDMTRSTS, meterStatusIdStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDMETERTYPE);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLDMETERTYPE, meterTypeIdStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FINALREADING);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.FINALREADING, finalReadingValStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FINALSTATUS);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.FINALSTATUS, finalStatusIdStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.REASONID);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.REASONID, meterReasonId);

            sendOldMeterDetails.sndOldMtrDet(makerCodeIdStr, serialNoRecvd, installDtRecvd, meterSizeRecvd,
                    sealNoRecvd, pastMeterReadingRecvd, meterStatusIdStr, meterTypeIdStr, finalReadingValStr,
                    finalStatusIdStr, meterObservationId, meterReasonId, contList, isOldSubmitted);

//            MessageWindow.msgWindow(getContext(), "Details Submitted Succesfully slide for New meter Details");

            ((MMGScreenActivity) getActivity()).onClickNext(1);

            MMGScreenActivity.animationOnArrow();
        }
    }

    private void saveOldMeterDetails() {
        int finalStatusInteger = Integer.parseInt(finalStatsuId);
        int meterObservationInteger = Integer.parseInt(meterObservationId);
        int meterReasonInteger = Integer.parseInt(meterReasonId);

        meterTypeId = et_type_of_meter.getText().toString();

        meterSizeRecvd = etMtrSize.getText().toString();
        finalReadingValStr = finalReadingEdittext.getText().toString();

        OldMeterDetailAnnex6 oldMeterDetailAnnex6 = new OldMeterDetailAnnex6(consumerNoStr, makercodename, serialNoRecvd,
                sealNoRecvd, installDtRecvd, meterSizeRecvd, meterStatusIdStr, meterTypeId, finalStatusInteger,
                meterObservationInteger, meterReasonInteger, pastMeterReadingRecvd, finalReadingValStr);

        realmOperations.insertOldMeterDetail(oldMeterDetailAnnex6);
    }

    private void disableOldMeterFields() {
        //        if (commisioned_noncommisioned.equalsIgnoreCase("Y")){
//        if (radiobuttonValStr.equalsIgnoreCase("N")) {
//            etMeterStatus.setEnabled(false);
//            et_type_of_meter.setEnabled(false);
//            finalStatusSpinner.setEnabled(false);
//            observationSpinner.setEnabled(false);
//            meterRsnSpinner.setEnabled(false);
//            finalReadingEdittext.setEnabled(false);
//        } else {
//            etMeterStatus.setEnabled(false);
//            et_type_of_meter.setEnabled(false);
//            finalStatusSpinner.setEnabled(true);
//            observationSpinner.setEnabled(true);
//            meterRsnSpinner.setEnabled(true);
//        }
//    }

        radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);
        if (radiobuttonValStr.equalsIgnoreCase("N")) {
            backFragment(mCon, "You can directly go new meter screen", "Alert");
            //MessageWindow.messageAuthenticationWindow(mCon, "You Can Directly Go to Authentication Page","Alert");
        }
    }

    public void backFragment(Context context, String msg, String title) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(msg);

            builder.setNegativeButton(context.getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            App.backPress = "Y";
                            ((MMGScreenActivity) context).onClickPrev();

                        }
                    });
            builder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MMGScreenActivity) context).skipOldMeterDetails();

                            dialog.dismiss();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchDetailsFromContractor() {
        try {
            if (contList == null || contList.equalsIgnoreCase("")) {
                contList = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTLIST);
            } else {
                contList = getArguments().getString("contList");
            }

            if (contList != null) {
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
                    O_SIZE = jsnobject.getString("O_SIZE");
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
                oldMeterSetter();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void finalStatusDD() {
        ArrayList<String> finalStatusName = new ArrayList<>();
        finalStatusName = realmOperations.fetchMeterStatusDetails("1");
        ArrayList<String> finalStatusNameList = new ArrayList<>();
        finalStatusNameList.add("--Select--");
        finalStatusNameList.addAll(finalStatusName);

        finalStatusAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, finalStatusNameList);
        finalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        finalStatusSpinner.setAdapter(finalStatusAdapter);

        if (radiobuttonValStr.equalsIgnoreCase("S") || radiobuttonValStr.equalsIgnoreCase("MB")) {
            int spinnerPosition = finalStatusAdapter.getPosition("Normal Meter");
            finalStatusSpinner.setSelection(spinnerPosition);
        }

        finalStatusSpinner.setOnItemSelectedListener(this);
    }

    private void oldMeterSetter() {
        try {
            if (MI_O_FINALSTATUS == null || MI_O_FINALSTATUS.equalsIgnoreCase("")
                    || MI_O_FINALSTATUS.equalsIgnoreCase("0")) {
                finalStatusDD();
            } else {
                finalStatusDropdown();
            }

            if (MI_O_REASON == null || MI_O_REASON.equalsIgnoreCase("") || MI_O_REASON.equalsIgnoreCase("0")) {
                meterReasonDefaultDropdown();
            } else {
                meterReasonDropdown();
            }

            if (MI_O_OBSERVATION == null || MI_O_OBSERVATION.equalsIgnoreCase("") || MI_O_OBSERVATION.equalsIgnoreCase("0")) {
                defaultObservation();
            } else {
                observReasonDropdown();
            }

            if (MI_O_FINALREADING == null || MI_O_FINALREADING.equalsIgnoreCase("")) {
                finalReadingEdittext.setText("");
            } else {
                finalReadingEdittext.setText(MI_O_FINALREADING);
            }

            if (MI_N_SIZE.equalsIgnoreCase(""))
                etMtrSize.setText("");
            else
                etMtrSize.setText(O_SIZE);

        } catch (Exception ex) {
            Log.e("OldMeterSetter", "" + ex);
        }

    }

    private void defaultObservation() {
        meterObservationList = realmOperations.fetchStatusObservationList(meterStatusIdStr);

        ArrayList<String> metrObsList = new ArrayList<>();
        metrObsList.addAll(meterObservationList);

        observationAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, metrObsList);
        observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        observationSpinner.setAdapter(observationAdapter);
        finalReadingEdittext.setText("");

        if (radiobuttonValStr.equalsIgnoreCase("S") || radiobuttonValStr.equalsIgnoreCase("MB")) {
            int spinnerPosition = observationAdapter.getPosition("Can be Retained");
            observationSpinner.setSelection(spinnerPosition);
        }
    }

    private void meterReasonDefaultDropdown() {
        meterResonName = new ArrayList<>();
        meterResonName = realmOperations.fetchObservation();

        meterReasonList = new ArrayList<>();
        meterReasonList.add("--Select--");
        meterReasonList.addAll(meterResonName);


        meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
        meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meterRsnSpinner.setAdapter(meterReasonAdapter);

        if (meterObservationId.equalsIgnoreCase("9")) {//Meter Stop
            setReasonOfReplacement("40");
        } else if (meterObservationId.equalsIgnoreCase("10")) {//Meter Damage
            setReasonOfReplacement("45");
        } else if (meterObservationId.equalsIgnoreCase("11")) {//Display Faulty
            setReasonOfReplacement("43");
        } else if (meterObservationId.equalsIgnoreCase("12")) {//Meter Reverse
            setReasonOfReplacement("46");
        } else if (meterObservationId.equalsIgnoreCase("15")) {//Meter Not Found
            setReasonOfReplacement("41");
        } else {
            if (!(MI_O_REASON.equalsIgnoreCase("")) || MI_O_REASON != null ) {
                try {
                    meterReasonList = new ArrayList<>();
                    meterReasonList.add("--Select--");
                    meterReasonList.addAll(meterResonName);

                    mmgObersvationModel = realmOperations.fetchContractrReasonById(MI_O_REASON);
                    reasonName = mmgObersvationModel.getOCRM_NAME();

                    int reasonId = meterReasonList.indexOf(reasonName);

                    meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
                    meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    meterRsnSpinner.setAdapter(meterReasonAdapter);
                    meterRsnSpinner.setSelection(reasonId);

                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        }
//
//        if (radiobuttonValStr.equalsIgnoreCase("S")) {
//            //int spinnerPosition = meterReasonAdapter.getPosition("Meter Seal Installation / Replacement");
//            int spinnerPosition = meterReasonList.indexOf("Meter Seal Installation / Replacement");
//
//            meterRsnSpinner.setSelection(spinnerPosition);
//        } else if (radiobuttonValStr.equalsIgnoreCase("MB")) {
//            int spinnerPosition = meterReasonList.indexOf("Meter Box Missing / required");
//            //int spinnerPosition = meterReasonAdapter.getPosition("Meter Box Missing / required");
//            meterRsnSpinner.setSelection(spinnerPosition);
//        }
    }

    private void getCustomerDetails() {
        String params[] = new String[3];
        params[0] = serachById;
        params[1] = consumerNoStr;
        params[2] = refNoStr;

        if (connection.isConnectingToInternet()) {
            Get_CustomerDetailsTask get_customerDetailsTask = new Get_CustomerDetailsTask();
            get_customerDetailsTask.execute(params);
        } else {
            MessageWindow.errorWindow(mCon,getResources().getString(R.string.no_internet_connection));
        }
    }

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

            finalStatusAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, finalStatusNameList);
            finalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            finalStatusSpinner.setAdapter(finalStatusAdapter);
            finalStatusSpinner.setSelection(reasonId);

        } catch (Exception ex) {
            ex.printStackTrace();
            //finalStatusDD();
        }
        finalStatusSpinner.setOnItemSelectedListener(this);
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

                if (MI_O_FINALSTATUS.equals(finalStatsuId))
                {
                    mStatusObservationModel = realmOperations.fetchContractrObservationById(MI_O_OBSERVATION);
                    meterObservationName = mStatusObservationModel.getMSNM_MSUBSTATUS_NAME();

                    int observn = meterObservationList.indexOf(meterObservationName);

                    ArrayList<String> contractorEmpList = new ArrayList<>();
//            contractorEmpList.add("--Select--");
                    contractorEmpList.addAll(meterObservationList);

                    observationAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, contractorEmpList);
                    observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    observationSpinner.setAdapter(observationAdapter);
                    observationSpinner.setSelection(observn);
                }
                else
                {
//                    ArrayList<String> subAll = new ArrayList<>();
//                    subAll.add("--Select--");

                    observationAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterObservationList);
                    observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    observationSpinner.setAdapter(observationAdapter);
                }


                if (MI_O_FINALREADING == null || MI_O_FINALREADING.equalsIgnoreCase("")) {
                    finalReadingEdittext.setText("");
                } else {
                    finalReadingEdittext.setText(MI_O_FINALREADING);
                }

            }

        } catch (Exception ex) {
            MessageWindow.errorWindow(mCon,ex.getMessage());

         /*   ArrayList<String> contractorEmpList = new ArrayList<>();
            contractorEmpList.addAll(meterObservationList);

            observationAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, contractorEmpList);
            observationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            observationSpinner.setAdapter(observationAdapter);*/


        }
    }

    private void meterReasonDropdown() {
        meterResonName = new ArrayList<>();
        meterResonName = realmOperations.fetchObservation();

        if (pagename != null) {
            if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {

                if (MI_O_REASON == null) {
                    meterReasonList = new ArrayList<>();
                    meterReasonList.add("--Select--");
                    meterReasonList.addAll(meterResonName);

                    meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
                    meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    meterRsnSpinner.setAdapter(meterReasonAdapter);
                } else {

                    if (MI_O_REASON != null || MI_O_REASON.equalsIgnoreCase("")) {
                        try {
                            meterReasonList = new ArrayList<>();
                            meterReasonList.add("--Select--");
                            meterReasonList.addAll(meterResonName);

                            mmgObersvationModel = realmOperations.fetchContractrReasonById(MI_O_REASON);
                            reasonName = mmgObersvationModel.getOCRM_NAME();

                            int reasonId = meterReasonList.indexOf(reasonName);

                            meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
                            meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            meterRsnSpinner.setAdapter(meterReasonAdapter);
                            meterRsnSpinner.setSelection(reasonId);

                        } catch (Exception ex) {

                        }
                    }
                }

            }

            else if (pagename.equalsIgnoreCase("MeterInstallationEntry")) {
                if (MI_O_REASON.equalsIgnoreCase("")) {
                    meterReasonList = new ArrayList<>();
                    meterReasonList.add("--Select--");
                    meterReasonList.addAll(meterResonName);

                    meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
                    meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    meterRsnSpinner.setAdapter(meterReasonAdapter);
                } else {
                    if (MI_O_REASON != null) {
                        try {
                            ArrayList<String> meterReasonList = new ArrayList<>();
                            meterReasonList.add("--Select--");

                            meterReasonList.addAll(meterResonName);

                            mmgObersvationModel = realmOperations.fetchContractrReasonById(MI_O_REASON);
                            reasonName = mmgObersvationModel.getOCRM_NAME();
                            //reasonNameId = mmgObersvationModel.getOCRM_ID();

                            int reasonId = meterReasonList.indexOf(reasonName);

                            meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
                            meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            meterRsnSpinner.setAdapter(meterReasonAdapter);
                            meterRsnSpinner.setSelection(reasonId);
                        } catch (Exception ex) {
                            MessageWindow.errorWindow(mCon,ex.getMessage());
                        }
                    }
                }

            } else {
                meterReasonList = new ArrayList<>();
                meterReasonList.add("--Select--");
                meterReasonList.addAll(meterResonName);

                meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
                meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                meterRsnSpinner.setAdapter(meterReasonAdapter);
            }
        } else {

            if (dataFound.equalsIgnoreCase("DataFound")) {

                if (!MI_O_REASON.equalsIgnoreCase("")) {
                    try {
                        ArrayList<String> meterReasonList = new ArrayList<>();
                        meterReasonList.add("--Select--");

                        meterReasonList.addAll(meterResonName);

                        mmgObersvationModel = realmOperations.fetchContractrReasonById(MI_O_REASON);
                        reasonName = mmgObersvationModel.getOCRM_NAME();

                        int reasonId = meterReasonList.indexOf(reasonName);

                        meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
                        meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        meterRsnSpinner.setAdapter(meterReasonAdapter);
                        meterRsnSpinner.setSelection(reasonId);
                    } catch (Exception ex) {
                        MessageWindow.errorWindow(mCon,ex.getMessage());


                   /* ArrayList<String> meterReasonList = new ArrayList<>();
                    meterReasonList.add("--Select--");
                    meterReasonList.addAll(meterResonName);

                    meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
                    meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    meterRsnSpinner.setAdapter(meterReasonAdapter);*/
                    }
                }
            } else {
                ArrayList<String> meterReasonList = new ArrayList<>();
                meterReasonList.add("--Select--");
                meterReasonList.addAll(meterResonName);

                meterReasonAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterReasonList);
                meterReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                meterRsnSpinner.setAdapter(meterReasonAdapter);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_CustomerDetailsTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[3];
                paraName[0] = "SearchType";
                paraName[1] = "SearchStr";
                paraName[2] = "RefNo";

                jsonCustomerDetailResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetConsumerAndMeterDetails, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MMGGetDetailsResponseModel mmgGetDetailsResponseModel = gson.fromJson(jsonCustomerDetailResponse, MMGGetDetailsResponseModel.class);
                if (mmgGetDetailsResponseModel != null) {
                    ArrayList<MMGCustDetModel> customerDetailList = (ArrayList<MMGCustDetModel>) mmgGetDetailsResponseModel.getMMGCustomerDetails();
                    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = (ArrayList<MMGMeterConnectedDetailsModel>) mmgGetDetailsResponseModel.getMMGMeterConnectionDetails();
                    ArrayList<InstallDetails> installDetails = (ArrayList<InstallDetails>) mmgGetDetailsResponseModel.getInstallDetailsList();

                    for (int i = 0; i <= customerDetailList.size() - 1; i++) {
                        if (meterConnectionList.get(i).getO_METERTYPE() == null) {
                            meterTypeStr = "";
                        } else {
                            meterTypeStr = meterConnectionList.get(i).getO_METERTYPE();
                        }

                        if (customerDetailList.get(i).getSRM_CONNECTION_LOAD() == null ||
                                customerDetailList.get(i).getSRM_CONNECTION_LOAD().equalsIgnoreCase("")) {
                            connSizeStr = "";
                        } else {
                            connSizeStr = customerDetailList.get(i).getSRM_CONNECTION_LOAD();
                        }

                        if (meterConnectionList.get(i).getSRM_CONNSIZE_ID() == null) {
                            mtrSizeId = "";
                        } else {
                            mtrSizeId = meterConnectionList.get(i).getSRM_CONNSIZE_ID();
                        }
                    }

                    if (meterConnectionList != null) {

                        ArrayList<MMGMeterConnectedDetailsModel> mmgMeterConnectionDetailList = new ArrayList<>();
                        for (int j = 0; j <= meterConnectionList.size() - 1; j++) {

                            if (meterConnectionList.get(j).getMTRM_MANUFACTURE_CODE() == null) {
                                manufactureCode = "";
                            } else {
                                manufactureCode = meterConnectionList.get(j).getMTRM_MANUFACTURE_CODE();
                            }
                            if (meterConnectionList.get(j).getMMFG_MFGNAME() == null) {
                                mfgNameStr = "";

                            } else {
                                mfgNameStr = meterConnectionList.get(j).getMMFG_MFGNAME();
                            }

                            if (meterConnectionList.get(j).getMTRM_SERIAL_NO() == null) {
                                serialNoStr = "";
                            } else {
                                serialNoStr = meterConnectionList.get(j).getMTRM_SERIAL_NO();
                            }


                            if (meterConnectionList.get(j).getMRT_OLDMETER_STATUS() == null) {
                                old_meter_status_str = "";
                            } else {
                                old_meter_status_str = meterConnectionList.get(j).getMRT_OLDMETER_STATUS();
                            }

                            if (meterConnectionList.get(j).getMSM_METERSTATUS_NAME() == null) {
                                old_meter_status_name_str = "";
                            } else {
                                old_meter_status_name_str = meterConnectionList.get(j).getMSM_METERSTATUS_NAME();
                            }

                            if (meterConnectionList.get(j).getMTRM_MANUFACTURE_TYPE_CODE() == null) {
                                manufactureTypeCode_str = "";
                            } else {
                                manufactureTypeCode_str = meterConnectionList.get(j).getMTRM_MANUFACTURE_TYPE_CODE();
                            }

                            if (meterConnectionList.get(j).getMTRM_PAST_READING() == null) {
                                pastMeterReadingStr = "";
                            } else {
                                pastMeterReadingStr = meterConnectionList.get(j).getMTRM_PAST_READING();
                            }

                            if (meterConnectionList.get(j).getMTRM_INSTALLATION_DATE() == null) {
                                installDtStr = "";
                            } else {
                                installDtStr = meterConnectionList.get(j).getMTRM_INSTALLATION_DATE();
                            }

                            if (meterConnectionList.get(j).getSIZEOFMETER() == null) {
                                meterSizeStr = "";
                            } else {
                                meterSizeStr = meterConnectionList.get(j).getSIZEOFMETER();
                            }

                            if (meterConnectionList.get(j).getSEALNO() == null) {
                                sealNoStr = "";
                            } else {
                                sealNoStr = meterConnectionList.get(j).getSEALNO();
                            }

                            if (meterConnectionList.get(j).getLASTREADINGDATE() == null) {
                                lastReadingDate = "";
                            } else {
                                lastReadingDate = meterConnectionList.get(j).getLASTREADINGDATE();
                            }
                            if (meterConnectionList.get(j).getMTRM_ESTIMATED_AVRG_CONSUMP() == null) {
                                avrage_consumtion_str = "";
                            } else {
                                avrage_consumtion_str = meterConnectionList.get(j).getMTRM_ESTIMATED_AVRG_CONSUMP();
                            }

                        }
                    }

                    if (installDetails != null) {

                        for (int k = 0; k <= installDetails.size() - 1; k++) {
                            MI_METERINSTALLID = installDetails.get(k).getMI_METERINSTALLID();
                            MI_O_FINALSTATUS = installDetails.get(k).getMI_O_FINALSTATUS();
                            MI_O_REASON = installDetails.get(k).getMI_O_REASON();
                            MI_O_OBSERVATION = installDetails.get(k).getMI_O_OBSERVATION();
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
                        MessageWindow.errorWindow(mCon,"Meter install id null");
                    }
                }

                mtrMakeTextView.setText(manufactureCode + "-" + mfgNameStr);
                mtrNoTextView.setText(serialNoStr);
                installDtTextView.setText(installDtStr);
                etMeterStatus.setText(old_meter_status_name_str);
                et_type_of_meter.setText(meterTypeStr);
                etMtrSize.setText(connSizeStr);
                sealNoTextView.setText(sealNoStr);
                previousReadingTextView.setText(pastMeterReadingStr);
                etaverage_consum.setText(avrage_consumtion_str);

                if (MI_O_FINALSTATUS == null || MI_O_FINALSTATUS.equalsIgnoreCase("")
                        || MI_O_FINALSTATUS.equalsIgnoreCase("0")) {
                    finalStatusDD();
                } else {
                    finalStatusDropdown();
                }

                if (MI_O_REASON == null || MI_O_REASON.equalsIgnoreCase("") || MI_O_REASON.equalsIgnoreCase("0")) {
                    meterReasonDefaultDropdown();
                } else {
                    meterReasonDropdown();
                }

                if (MI_O_OBSERVATION == null || MI_O_OBSERVATION.equalsIgnoreCase("") || MI_O_OBSERVATION.equalsIgnoreCase("0")) {
                    defaultObservation();
                } else {
                    observReasonDropdown();
                }

                mmgMasterProgress.dismiss();

            } catch (Exception e) {
                Log.e("Get_CustomerDetails As", e.toString());
                mmgMasterProgress.dismiss();
                String error = e.toString();
                ErrorClass.errorData(mCon, "Consumer Details Fragment", "Get Customer details task", error);
            }
        }

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
    }

    private void saveDataToServer() {
//        String emp_code = PreferenceUtil.getUserName();
        String emp_code = null;
        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ip_str = PreferenceUtil.getMac();
        String oldmeterSizeNum = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_SIZE_ID);
        String mtrTypeCode = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_TYPE_CODE_ID);
        String oldmakerCodeNumId = UtilitySharedPreferences.getPrefs(getActivity(), Constants.OLD_MAKERCODE);
        Date date1 = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");

        String strCurrentDate = formatter.format(date1);

        String params[] = new String[58];
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
        params[5] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMERREFERENCENUMBER);
        params[6] = oldmeterSizeNum;
        params[7] = serialNoStr;
        params[8] = oldmakerCodeNumId;
        if (previousReading == null) {
            params[9] = null;
        } else {
            params[9] = previousReading;
        }
        if (finalReadingValStr == null) {
            params[10] = null;
        } else {
            params[10] = finalReadingValStr;
        }
//            params[10] = finalReadingNumStr;
        if (finalStatusIdStr == null) {
            params[11] = null;
        } else {
            params[11] = finalStatusIdStr;
        }

//            params[11] = finalStatusNumStr;             //final Status

        if (meterObservationId == null) {
            params[12] = null;
        } else {
            params[12] = meterObservationId;
        }
//            params[12] = meterObservationIdStr;
        if (meterReasonId == null) {
            params[13] = "0";
        } else {
            params[13] = meterReasonId;
        }

//            params[13] = reasonId;

        if (mtrTypeCode == null) {
            params[14] = null;
        } else {
            params[14] = mtrTypeCode;
        }

     /*   params[9] = previousReading;
        params[10] = finalReadingValStr;
        params[11] = finalStatusIdStr;
        params[12] = meterObservationId;
        params[13] = meterReasonId;
        params[14] = mtrTypeCode;
*/
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

        /*params[15] = MI_N_MAKE;
        params[16] = MI_N_SIZE;
        params[17] = MI_N_SEAL;
        params[18] = MI_N_METER*/;
        params[19] = strCurrentDate;//installDt;

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
        params[23] = MI_N_ISPROTECTED;       // N_IsProtected

//                params[24] = property_assessmnt_num;                 // Property Assessment

        if (MI_N_ISMETERHANDOVER == null) {
            params[25] = "";       // N_IsMeterHandovertoConsumer
        } else {
            params[25] = MI_N_ISMETERHANDOVER;
        }
        if (MI_CONTRACTOR == null || MI_CONTRACTOR.equalsIgnoreCase("") || MI_CONTRACTOR.equalsIgnoreCase("0")) {
            String mmgFixer = PreferenceUtil.getMMGFixer();
            params[26] = emp_code;
        } else {
            params[26] = MI_CONTRACTOR;
        }
      /*  params[20] = MI_N_INITIALREADING;
        params[21] = MI_N_METERTYPE;
        params[22] = MI_N_METERLOCATION;
        params[23] = MI_N_ISPROTECTED;*/
        if (isConsumerSubmitted)

            params[24] = property_assess;      // PropertyTaxNo
        else
            params[24] = MI_PROPERTYTAXNO;

        params[25] = MI_N_ISMETERHANDOVER;
        if (MI_CONTRACTOR == null || MI_CONTRACTOR.equalsIgnoreCase("") || MI_CONTRACTOR.equalsIgnoreCase("0")) {
            String mmgFixer = PreferenceUtil.getMMGFixer();
            params[26] = emp_code;
        } else {
            params[26] = MI_CONTRACTOR;
        }
        params[27] = MI_CONTRACTOREMP;
        params[28] = null;
        params[29] = null;
        params[30] = null;
        params[31] = null;
        params[32] = null;
        params[33] = "0";
        params[34] = "0";
        params[35] = "0";
        params[36] = null;
        params[37] = "0";
        params[38] = "0";
        params[39] = "0";

        if (isConsumerSubmitted) {

            if (from_node_str == null) {
                params[40] = null;                   // N_MeterType
            } else {
                params[40] = from_node_str;
            }

//            params[40] = MI_FROMNODE;
            if (to_node_str == null) {
                params[41] = null;                   // N_MeterType
            } else {
                params[41] = to_node_str;
            }

//            params[41] = MI_TONODE;
            params[42] = primary_mob_str;
//            params[43] = MI_ALTMOBILE;
            if (alternate_mob_str == null) {
                params[43] = null;                   // N_MeterType
            } else {
                params[43] = alternate_mob_str;
            }
            if (gis_bid_str == null) {
                params[44] = null;                   // N_MeterType
            } else {
                params[44] = gis_bid_str;
            }
//            params[44] = MI_GIS;

            if (dma_id == null) {
                params[45] = null;                   // N_MeterType
            } else {
                params[45] = dma_id;
            }
//            params[45] = MI_DMA;

            if (sr_id == null) {
                params[46] = null;                   // N_MeterType
            } else {
                params[46] = sr_id;
            }

          /*  params[40] = from_node_str;
            params[41] = to_node_str;
            params[42] = primary_mob_str;
            params[43] = alternate_mob_str;
            params[44] = gis_bid_str;
            params[45] = dma_id;
            params[46] = sr_id;*/
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

           /* params[40] = MI_FROMNODE;
            params[41] = MI_TONODE;
            params[42] = MI_REGMOBILE;
            params[43] = MI_ALTMOBILE;
            params[44] = MI_GIS;
            params[45] = MI_DMA;
            params[46] = MI_SR;
     */   }
        params[47] = commisioned_noncommisioned;
        params[48] = MI_N_DIGIT;                    //N_DIGIT
        params[49] = emp_code;
        params[50] = ip_str;
        params[51] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_METERINSTALLID);                    // Query String
        params[52] = MI_N_SEAL_MAKE;
        params[53] = MI_N_METER_BOX_MAKE;
        params[54] = "OF";
        String metrown =  UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_N_METEROWNERSHIP);
        params[55] = metrown;
        params[56] = "Ok";
        params[57] = avrage_consumtion_str;

        Log.e("OldParams",Arrays.toString(params));
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
                String paraName[] = new String[58];
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
                paraName[58] = "AvgConsumption";


                jsonMeterInstallSaveResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "MeterInstallationSave", params, paraName);
               // Log.e("OldMeterResponse", jsonMeterInstallSaveResponse);
               // Log.e("OldMeterParams", Arrays.toString(params));

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

               /*     AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Success");
                    alertBuilder.setMessage("Old Meter Details Saved");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
//                            MainActivity m = new MainActivity();
//                            m.setBackFrag();

                            App.backPressMMGFragment="Y";

                            Intent i = new Intent(mCon, MainActivity.class);
                            i.putExtra("Tag", "2");
                            startActivity(i);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();*/

                    MessageWindow.throwOutMMMFragment(mCon, "Old Meter Details Saved Successfully", "Success", MainActivity.class);
                    clearAllSharedPrefs();
                    return;
                } else if (enums[0].equalsIgnoreCase("Failure")) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Failure");
                    alertBuilder.setMessage("Meter Installation Process Not Completed");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else if (enums[0].equalsIgnoreCase("Duplicate")) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Duplicate");
                    alertBuilder.setMessage(getResources().getString(R.string.please_forward_complaint_to_mmg));
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mCon, MainActivity.class);

                            intent.putExtra("Tag", "2");
                            startActivity(intent);
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                }

            } catch (Exception e) {
                Log.e("Exception", e.toString());
                progress.dismiss();
                MessageWindow.errorWindow(mCon,"Something went wrong"+e.getMessage());
                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Send Data to meter Installation", error);
            }
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
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.Meterowner);

    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);
        if (this.isVisible()) {
            if (isFragmentVisible_) {
                new OldMeterDetFragment();
                disableOldMeterFields();
                //fetchDetailsFromContractor();

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
