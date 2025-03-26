package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.DMAModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MSRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.SRModel;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSRModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGScreenActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6.CustomerDetailsAnnex6;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGCustDetModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGGetDetailsResponseModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMaterialResponseModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterManagementSystemFragment;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.Model.MMGGetMeterInstallByIdSingleContractor;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import io.realm.RealmResults;

import static android.content.Context.MODE_PRIVATE;

public class ConsumerDetFragmentNew extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Context mCon;
    private Button submitConsumerBtn, saveAndExit;
    int number, dmaval;

    TextView tv_old_hsc_meter, tv_old_hsc, tv_new_hsc_meter, tv_only_meter_replacement, catgryConnTextView,
            connSizeTextView;
    EditText tv_noofFlat, et_consumer_account_no, et_nsc_application_no, etFromNode, etToNode, mobTextView, alternateNo,
            et_property_assessement, tv_gisBid, consumerNo;
    TextView tv_name_of_consumer, tv_houseNo, tv_roadName, tv_landmarkName, tv_pincode, tv_zone, tv_group, tv_ward;
    private RealmOperations realmOperations;
    private ArrayAdapter msrArrayAdapter, srArrayAdapter, dmaArrayAdapter;
    private MMGMeterConnectedDetailsModel mmgMeterConnectedDetailsModel;
    private MMGValidateDetails mmgValidateDetails;
    private Spinner msrSpinner, srSpinner, dmaSpinner;
    public static String oldmeterno = "";

    SendMessage SM;

    String serachById = "", jsonSRresponse = "", jsonDMAresponse = "", msrValue = "";
    CardView card_view;
    MaterialDialog detailprogress, mmgMasterProgress;
    String jsonResponse = "", consumerNoStr = "", jsonResponseFromContractor = "", jsonMaterialCont = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String connSizeId = "", consumerNameStr = "", categoryConnStr = "", houseNoStr = "", areaNameStr = "",
            roadNameStr = "", pincodeStr = "", zoneStr = "", groupStr = "", wardStr = "", connSizeStr = "", mfgNameStr = "", manufactureCode = "", serialNoStr = "",
            installDtStr = "", meterSizeStr = "", sealNoStr = "", pastMeterReadingStr = "", refTypeStr = "", statusStr = "",
            consumerMatchingStr = "", radiobuttonValStr = "", subType = "", circleName = "", connectionLoad = "", landmark = "",
            meterStatusStr = "", meterTypeStr = "", commisioned_noncommisioned = "", msrStr = "", mtrSizeId = "", mtrTypeCodeId = "", pagename = "", radioButtonVal = "", old_meter_status_str = "", old_meter_status_id_str = "",
            old_meter_status_name_str = "", manufactureTypeCode_str = "", meter_install_id_str = "", status_str = "",
            m_digits_str = "", contractor_str = "",no_flat= "", cont_emp_code_str = "", my_sr_id = "", jsonMeterInstallSaveResponse = "", refNoStr = "", lastReadingDate = "";

    MMGGetMeterInstallByIdSingleContractor contModels;
    static String BU = "", PC = "", submitStatus = "", meterID = "", contList = "", MSRIDStr = "", SRIDStr = "", property_assessmnt = "",
            primaryMobStr = "", alternateMobStr = "", fromNodeStr = "", toNodeStr = "", gis_bidStr = "", srStr = "", dmaStr = "", refNoStrStatic = "",avrageConsumtion = "",averageEstimateConsum="";
    int msrId, srId, dmaId;
    private boolean isSubmitted = false;
    SharedPreferences sharedPrefRadio;

    int dmaSpinId;
    String MI_METERINSTALLID = "";

    String MI_ACTION = "", MI_CONSUMER = "", MI_BU = "", MI_PC = "", MI_REFNO = "", MI_O_SIZE = "", MI_O_METER = "", MI_O_MAKE = "", MI_O_PREVIOUSREADING = "", MI_O_FINALREADING = "", MI_O_FINALSTATUS = "", MI_O_REASON = "", MI_O_METERTYPE = "",
            MI_N_MAKE = "", MI_N_SIZE = "", MI_N_SEAL = "", MI_N_METER = "", MI_INSTALLATIONDATE = "", MI_N_INITIALREADING = "", MI_N_METERTYPE = "", MI_N_METERLOCATION = "", MI_N_ISPROTECTED = "", MI_PROPERTYTAXNO = "", MI_N_ISMETERHANDOVER = "",
            MI_CONTRACTOR = "", MI_CONTRACTOREMP = "", MI_N_ISMATERIALHANDOVER = "", MI_PCCBEDDINGLEN = "", MI_PCCBEDDINGWID = "",
            MI_PCCBEDDINGDEP = "", MI_ROADCUTTINGTYPE = "", MI_ROADCUTTINGLEN = "", MI_ROADCUTTINGWID = "", MI_ROADCUTTINGDEP = "", MI_FROMNODE = "",
            MI_TONODE = "", MI_REGMOBILE = "", MI_ALTMOBILE = "", MI_GIS = "", MI_DMA = "", MI_SR = "", MI_MODIFIEDBY = "", MI_MODIFIEDDATE = "", MI_IP = "", MI_AUTHENTICATEDATE = "",
            MI_AUTHREJECTBY = "", MI_REJECTEDDATE = "", MI_STATUS = "", MI_ISACTIVE = "", MI_XMLMATERIAL = "", MI_XMLCIVIL = "", MI_O_OBSERVATION = "",
            MI_SOURCE = "", MI_ISCOMMISSIONED = "", MI_CONTRACTOROTHER = "", MI_CONTRACTOREMPOTHER = "", MI_N_DIGIT = "0", MSRID = "", MI_N_SEAL_MAKE = "", MI_N_METER_BOX_MAKE = "";

    private String serialNo = "", srNo = "", materialId = "",
            materialName = "", defaultQty = "", uomName = "", mrmGroupID = "", srValue = "";
    boolean consumerDetailVisible = false;


    private List<DownloadSRModel> srList = new ArrayList<>();
    private ArrayList<DownloadSRModel> srListDummy = new ArrayList<>();
    private ArrayList<String> downloadSRList = new ArrayList<>();
    private ArrayList<String> srListSTR = new ArrayList<>();
    private ArrayList<String> dmaBySr = new ArrayList<>();

    SRModel model_sr;
    DMAModel model_dma;
    MSRModel msrModel;

    RealmResults<SRModel> srModelRealmResults;
    RealmResults<DMAModel> dmaModelRealmResults;

    ArrayList<String> srStringList = new ArrayList<String>();
    ArrayList<String> srValueList = new ArrayList<String>();

    ArrayList<String> dmaStringList = new ArrayList<String>();
    ArrayList<String> dmaValueList = new ArrayList<String>();

    private MaterialDialog progress, srProgress, dmaProgress;
    private String nscAppStr = "", consAccNoStr = "";
    private ArrayList<MMGGetMeterInstallByIdSingleContractor> contractorList = new ArrayList<>();
    RealmResults<MSRModel> msrModels;
    RealmResults<SRModel> srModels;
    RealmResults<DMAModel> dmaModels;
    int makerId = 0;
    String STARTTIME = "", ALERTSTARTTIME = "",deviceAuthorization = "", appIsLogged = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";

    ArrayList<MMGCustDetModel> customerDetailList = new ArrayList<>();
    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = new ArrayList<>();
    ArrayList<InstallDetails> installDetails = new ArrayList<>();
    ArrayList<MMGValidateDetails> validateDetailList = new ArrayList<>();

    public ConsumerDetFragmentNew() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCon = getActivity();
        realmOperations = new RealmOperations(mCon);
        //pinky
        dmaModels = realmOperations.fetchDMANameID();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View view = inflater.inflate(R.layout.fragment_consumer_det_fragement, container, false);

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        meterID = getArguments().getString("meterID");
        serachById = getArguments().getString("serachById");
        consumerNoStr = getArguments().getString("consumerNoStr");
        refNoStr = getArguments().getString("refNoStr");
        pagename = getArguments().getString("pagename");
        contList = getArguments().getString("contList");



        radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);
        subType = UtilitySharedPreferences.getPrefs(getActivity(), Constants.COMPSUBTYPE);

        MMGScreenActivity activity = (MMGScreenActivity) getActivity();
        customerDetailList = activity.getMMGCustomerDetailsData();
        meterConnectionList = activity.getMMGMeterConnectionDetailsData();
        installDetails = activity.getMMGInstallDetailsData();
        validateDetailList = activity.getMmgvalidateDetailList();

        catgryConnTextView = view.findViewById(R.id.catgryConnTextView);
        connSizeTextView = view.findViewById(R.id.connSizeTextView);
        et_consumer_account_no = view.findViewById(R.id.et_consumer_account_no);
        tv_noofFlat = view.findViewById(R.id.tv_noofFlat);
        et_nsc_application_no = view.findViewById(R.id.et_nsc_application_no);
        mobTextView = view.findViewById(R.id.mobTextView);
        alternateNo = view.findViewById(R.id.alternateNo);
        tv_name_of_consumer = view.findViewById(R.id.tv_name_of_consumer);
        tv_houseNo = view.findViewById(R.id.tv_houseNo);
        tv_roadName = view.findViewById(R.id.tv_roadName);
        tv_landmarkName = view.findViewById(R.id.tv_landmarkName);
        tv_pincode = view.findViewById(R.id.tv_pincode);
        tv_zone = view.findViewById(R.id.tv_zone);
        tv_group = view.findViewById(R.id.tv_group);
        tv_ward = view.findViewById(R.id.tv_ward);
        tv_gisBid = view.findViewById(R.id.tv_gisBid);
        consumerNo = view.findViewById(R.id.consumerNo);
        msrSpinner = view.findViewById(R.id.msrSpinner);
        msrSpinner.setOnItemSelectedListener(this);

        srSpinner = view.findViewById(R.id.srSpinner);
        srSpinner.setOnItemSelectedListener(this);

        dmaSpinner = view.findViewById(R.id.dmaSpinner);
        dmaSpinner.setOnItemSelectedListener(this);
        sharedPrefRadio = getActivity().getSharedPreferences("RadioBtnMeterInstallation", MODE_PRIVATE);

        card_view = view.findViewById(R.id.card_view);

        saveAndExit = view.findViewById(R.id.saveAndExit);
        submitConsumerBtn = view.findViewById(R.id.submitConsumerBtn);
        saveAndExit.setOnClickListener(this);
        submitConsumerBtn.setOnClickListener(this);

        tv_old_hsc_meter = view.findViewById(R.id.tv_old_hsc_meter);
        tv_old_hsc = view.findViewById(R.id.tv_old_hsc);
        tv_new_hsc_meter = view.findViewById(R.id.tv_new_hsc_meter);


        et_property_assessement = view.findViewById(R.id.et_property_assessement);
        etFromNode = view.findViewById(R.id.etFromNode);
        etToNode = view.findViewById(R.id.etToNode);
        tv_only_meter_replacement = view.findViewById(R.id.tv_only_meter_replacement);

        etFromNode.setEnabled(false);
        etToNode.setEnabled(false);
        alternateNo.setEnabled(false);
        et_property_assessement.setEnabled(false);
        mobTextView.setEnabled(false);


        setMSRDropDown();
        currentDate();


        setMMGData();

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
    private void setMMGData() {

        try {
            validations();
            if (installDetails != null) {
                try {

                    for (int k = 0; k <= installDetails.size() - 1; k++) {

                        String dataFound = installDetails.get(k).getNODATAFOUND();

                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.DATAFOUND, dataFound);
                        if (dataFound.equalsIgnoreCase("DataFound")) {

                            property_assessmnt = installDetails.get(k).getMI_PROPERTYTAXNO();
                            alternateMobStr = installDetails.get(k).getMI_ALTMOBILE();
                            fromNodeStr = installDetails.get(k).getMI_FROMNODE();
                            toNodeStr = installDetails.get(k).getMI_TONODE();
                            msrStr = installDetails.get(k).getMSRID();
                            try {
                                srStr = installDetails.get(k).getMI_SR();

                            }catch(Exception e) {
                                e.printStackTrace();
                            }


                            dmaStr = installDetails.get(k).getMI_DMA();
                            gis_bidStr = installDetails.get(k).getMI_GIS();
                            MI_METERINSTALLID = installDetails.get(k).getMI_METERINSTALLID();
                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MI_METERINSTALLID);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.MI_METERINSTALLID, MI_METERINSTALLID);

                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.MI_N_METEROWNERSHIP, installDetails.get(k).getMI_N_METEROWNERSHIP());
                            et_property_assessement.setText(property_assessmnt);
                            etFromNode.setText(fromNodeStr);
                            etToNode.setText(toNodeStr);
                            alternateNo.setText(alternateMobStr);
                            tv_gisBid.setText(gis_bidStr);
                            //Log.e("installDetails", msrStr + " " + srStr + " " + dmaStr);

                            installedDetails(msrStr, srStr, dmaStr);

                        } else {
                            for (int i = 0; i <= customerDetailList.size() - 1; i++) {

                                if (customerDetailList.get(i).getMSR() == null ||
                                        customerDetailList.get(i).getMSR().equalsIgnoreCase("")) {
                                    msrStr = "";
                                } else {
                                    msrStr = customerDetailList.get(i).getMSR();
                                }

                                if (customerDetailList.get(i).getSR() == null ||
                                        customerDetailList.get(i).getSR().equalsIgnoreCase("")) {
                                    srStr = "";
                                } else {
                                    srStr = customerDetailList.get(i).getSR();
                                }
                                if (customerDetailList.get(i).getDMA() == null ||
                                        customerDetailList.get(i).getDMA().equalsIgnoreCase("")) {
                                    dmaStr = "";
                                } else {
                                    dmaStr = customerDetailList.get(i).getDMA();
                                }

                                //pinky changed this condition from && to || on 10/03/2022
                                if (!msrStr.equalsIgnoreCase("") ||
                                        !srStr.equalsIgnoreCase("") || !dmaStr.equalsIgnoreCase("")) {
                                    //Log.e("CustInfoInstallDetails", msrStr + " " + srStr + " " + dmaStr);


                                    installedDetails(msrStr, srStr, dmaStr);
                                }
                            }
                        }
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }


            for (int i = 0; i <= customerDetailList.size() - 1; i++) {

                if (customerDetailList.get(i).getSRM_S_FIRST_NAME() == null) {
                    consumerNameStr = "";
                    tv_name_of_consumer.setText(consumerNameStr);
                } else {
                    consumerNameStr = customerDetailList.get(i).getSRM_S_FIRST_NAME().trim();
                    tv_name_of_consumer.setText(customerDetailList.get(i).getSRM_S_FIRST_NAME());
                }

                if (customerDetailList.get(i).getNo_OfFlats() == null ||
                        customerDetailList.get(i).getNo_OfFlats().equalsIgnoreCase("")) {
                    no_flat= "";

                } else {
                    tv_noofFlat.setText(customerDetailList.get(i).getNo_OfFlats());
                }
                if (customerDetailList.get(i).getSRM_S_MOBILE_NO() == null || customerDetailList.get(i).getSRM_S_MOBILE_NO().equalsIgnoreCase("")) {
                    primaryMobStr = "";
                    mobTextView.setText(primaryMobStr);
                    mobTextView.setEnabled(true);
                } else {
                    primaryMobStr = customerDetailList.get(i).getSRM_S_MOBILE_NO().trim();
                    mobTextView.setText(customerDetailList.get(i).getSRM_S_MOBILE_NO());
                }

                           /* if (customerDetailList.get(i).getALTMOBILE() == null || customerDetailList.get(i).getALTMOBILE().equalsIgnoreCase("")) {
                                alternateMobStr = "";*/
             //   alternateNo.setText(alternateMobStr);commented by sonali
                            /*} else {
                                alternateMobStr = customerDetailList.get(i).getALTMOBILE().trim();
                                alternateNo.setText(customerDetailList.get(i).getALTMOBILE());
                            }
*/
                if (customerDetailList.get(i).getSRM_SERVICE_NO() == null || customerDetailList.get(i).getSRM_SERVICE_NO().equalsIgnoreCase("")) {
                    consumerNo.setText("");
                } else {
                    consumerNo.setText(customerDetailList.get(i).getSRM_SERVICE_NO());
                }




                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PRIMARY_MOBILE);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.PRIMARY_MOBILE, primaryMobStr);

                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.ALTERNATE_MOBILE);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.ALTERNATE_MOBILE, alternateMobStr);

                if (customerDetailList.get(i).getTFM_TARIFF_NAME() == null || customerDetailList.get(i).getTFM_TARIFF_NAME().equalsIgnoreCase("")) {
                    categoryConnStr = "";
                    catgryConnTextView.setText(categoryConnStr);
                } else {
                    categoryConnStr = customerDetailList.get(i).getTFM_TARIFF_NAME();
                    catgryConnTextView.setText(customerDetailList.get(i).getTFM_TARIFF_NAME());
                }

                if (customerDetailList.get(i).getSRM_CONNECTION_LOAD() == null ||
                        customerDetailList.get(i).getSRM_CONNECTION_LOAD().equalsIgnoreCase("")) {
                    connSizeStr = "";
                    connSizeTextView.setText(connSizeStr);

                } else {
                    connSizeStr = customerDetailList.get(i).getSRM_CONNECTION_LOAD();
                    connSizeTextView.setText(customerDetailList.get(i).getSRM_CONNECTION_LOAD());

                }

                houseNoStr = customerDetailList.get(i).getSRM_B_DOOR_NO();
                tv_houseNo.setText(houseNoStr);

                if (customerDetailList.get(i).getCM_CIRCLE_NAME() == null ||
                        customerDetailList.get(i).getCM_CIRCLE_NAME().equalsIgnoreCase("")) {
                    circleName = "";
                    tv_group.setText(circleName);
                } else {
                    circleName = customerDetailList.get(i).getCM_CIRCLE_NAME();
                    tv_group.setText(circleName);
                }

                if (customerDetailList.get(i).getSRM_CONNECTION_LOAD() == null ||
                        customerDetailList.get(i).getSRM_CONNECTION_LOAD().equalsIgnoreCase("")) {
                    connectionLoad = "";
                } else {
                    connectionLoad = customerDetailList.get(i).getSRM_CONNECTION_LOAD();
                }

                if (customerDetailList.get(i).getCM_CIRCLE_NAME() == null ||
                        customerDetailList.get(i).getCM_CIRCLE_NAME().equalsIgnoreCase("")) {
                    circleName = "";
                    tv_group.setText(circleName);

                } else {
                    circleName = customerDetailList.get(i).getCM_CIRCLE_NAME();
                    tv_group.setText(circleName);
                }

                if (customerDetailList.get(i).getPROPERTY_ASSESSMENT_NO() == null ||
                        customerDetailList.get(i).getPROPERTY_ASSESSMENT_NO().equalsIgnoreCase("")) {
                    property_assessmnt = "";
                } else {
                    property_assessmnt = customerDetailList.get(i).getPROPERTY_ASSESSMENT_NO();
                    et_property_assessement.setText(property_assessmnt);
                }


                if (customerDetailList.get(i).getSRM_B_ADDRESS1() == null) {
                    areaNameStr = "";
                } else {
                    areaNameStr = customerDetailList.get(i).getSRM_B_ADDRESS1();
                }

                if (customerDetailList.get(i).getSRM_B_ADDRESS2() == null) {
                    roadNameStr = "";
                    tv_roadName.setText(roadNameStr);
                } else {
                    roadNameStr = customerDetailList.get(i).getSRM_B_ADDRESS2();
                    tv_roadName.setText(customerDetailList.get(i).getSRM_B_ADDRESS2());
                }

                if (customerDetailList.get(i).getSRM_B_PIN() == null) {
                    pincodeStr = "";
                    tv_pincode.setText(pincodeStr);

                } else {
                    pincodeStr = customerDetailList.get(i).getSRM_B_PIN();
                    tv_pincode.setText(customerDetailList.get(i).getSRM_B_PIN());
                }

                if (customerDetailList.get(i).getBUM_BU_NAME() == null) {
                    zoneStr = "";
                    tv_zone.setText(zoneStr);

                } else {
                    zoneStr = customerDetailList.get(i).getBUM_BU_NAME();
                    tv_zone.setText(customerDetailList.get(i).getBUM_BU_NAME());
                }

                if (customerDetailList.get(i).getGROUPNAME() == null) {
                    groupStr = "";
                    tv_group.setText(groupStr);

                } else {
                    tv_group.setText(customerDetailList.get(i).getGROUPNAME());
                }

                if (customerDetailList.get(i).getWARD() == null) {
                    wardStr = "";
                    tv_ward.setText(wardStr);

                } else {
                    tv_ward.setText(customerDetailList.get(i).getWARD());
                }

                if (tv_gisBid.getText().toString().equalsIgnoreCase("")) {
                    if (customerDetailList.get(i).getSRM_S_GIS_ID() == null) {
                        gis_bidStr = "";
                        tv_gisBid.setText(gis_bidStr);
                        tv_gisBid.setEnabled(true);
                    } else {
                        gis_bidStr = customerDetailList.get(i).getSRM_S_GIS_ID();
                        tv_gisBid.setText(customerDetailList.get(i).getSRM_S_GIS_ID());
                        tv_gisBid.setEnabled(true);
                    }
                }

                if (customerDetailList.get(i).getSRM_B_ADDRESS3() == null) {
                    landmark = "";
                    tv_landmarkName.setText("");
                } else {
                    landmark = customerDetailList.get(i).getBUM_BU_NAME();
                    tv_landmarkName.setText(customerDetailList.get(i).getSRM_B_ADDRESS3());
                }

                if (customerDetailList.get(i).getSRM_BU_ID() == null) {
                    BU = "";
                } else {
                    BU = customerDetailList.get(i).getSRM_BU_ID();
                }

                if (customerDetailList.get(i).getSRM_PC_ID() == null) {
                    PC = "";
                } else {
                    PC = customerDetailList.get(i).getSRM_PC_ID();
                }

                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.BU);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.BU, BU);

                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PC);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.PC, PC);




                if (meterConnectionList.size() == 0||meterConnectionList.get(i).getMSM_METERSTATUS_NAME() == null ) {
                    meterStatusStr = "";
                } else {
                    meterStatusStr = meterConnectionList.get(i).getMSM_METERSTATUS_NAME();
                }

                if (meterConnectionList.size() == 0||meterConnectionList.get(i).getLASTREADINGDATE() == null) {
                    lastReadingDate = "";
                } else {
                    lastReadingDate = meterConnectionList.get(i).getLASTREADINGDATE();
                }

                if (meterConnectionList.size() == 0||meterConnectionList.get(i).getMTRM_MANUFACTURE_TYPE_CODE() == null) {
                    mtrTypeCodeId = "";
                } else {

                    mtrTypeCodeId = meterConnectionList.get(i).getMTRM_MANUFACTURE_TYPE_CODE();
                }

                if (meterConnectionList.size() == 0||meterConnectionList.get(i).getSRM_CONNSIZE_ID() == null) {
                    mtrSizeId = "";
                } else {
                    mtrSizeId = meterConnectionList.get(i).getSRM_CONNSIZE_ID();
                }

                /*Connection Size ID*/
                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MTR_SIZE_ID);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.MTR_SIZE_ID, mtrSizeId);

                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MTR_TYPE_CODE_ID);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.MTR_TYPE_CODE_ID, mtrTypeCodeId);

                if (meterConnectionList.size() == 0||meterConnectionList.get(i).getO_METERTYPE() == null) {
                    meterTypeStr = "";
                } else {
                    meterTypeStr = meterConnectionList.get(i).getO_METERTYPE();
                }

                if (meterConnectionList.size() == 0||meterConnectionList.get(i).getREFNO() == null) {
                    refNoStrStatic = "";
                } else {
                    refNoStrStatic = meterConnectionList.get(i).getREFNO();
                }
                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.GIS_BID);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.GIS_BID, gis_bidStr);

            /*    UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.BU);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.BU, BU);

                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PC);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.PC, PC);
*/
                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONNECTIONLOAD);
                UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONNECTIONLOAD, connectionLoad);
            }

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_NAME);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONSUMER_NAME, consumerNameStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERSTATUS);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.METERSTATUS, meterStatusStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERTYPENAME);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.METERTYPENAME, meterTypeStr);
            try {
                if (meterConnectionList.size() != 0) {
                    if (meterConnectionList == null) {
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MAKERCODENAME);
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METER_NO);
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDATE);
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDSEALNUM);

                    }  else
                     {
                        ArrayList<MMGMeterConnectedDetailsModel> mmgMeterConnectionDetailList = new ArrayList<>();
                        for (int j = 0; j <= meterConnectionList.size() - 1; j++) {

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_MANUFACTURE_CODE() == null) {
                                manufactureCode = "";
                            } else {
                                manufactureCode = meterConnectionList.get(j).getMTRM_MANUFACTURE_CODE();
                            }
                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMMFG_MFGNAME() == null) {
                                mfgNameStr = "";

                            } else {
                                mfgNameStr = meterConnectionList.get(j).getMMFG_MFGNAME();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getLASTREADINGDATE() == null) {
                                lastReadingDate = "";
                            } else {
                                lastReadingDate = meterConnectionList.get(j).getLASTREADINGDATE();
                            }

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MAKERCODENAME);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.MAKERCODENAME, mfgNameStr);

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_SERIAL_NO() == null) {
                                serialNoStr = "";
                            } else {
                                serialNoStr = meterConnectionList.get(j).getMTRM_SERIAL_NO();
                                oldmeterno = serialNoStr;
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMRT_OLDMETER_STATUS() == null) {
                                old_meter_status_str = "";
                            } else {
                                old_meter_status_str = meterConnectionList.get(j).getMRT_OLDMETER_STATUS();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_METERSTATUS_ID() == null) {
                                old_meter_status_id_str = "";
                            } else {
                                old_meter_status_id_str = meterConnectionList.get(j).getMTRM_METERSTATUS_ID();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMSM_METERSTATUS_NAME() == null) {
                                old_meter_status_name_str = "";
                            } else {
                                old_meter_status_name_str = meterConnectionList.get(j).getMSM_METERSTATUS_NAME();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_MANUFACTURE_TYPE_CODE() == null) {
                                manufactureTypeCode_str = "";
                            } else {
                                manufactureTypeCode_str = meterConnectionList.get(j).getMTRM_MANUFACTURE_TYPE_CODE();
                                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.O_MANUFACTURE_CODE);
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.O_MANUFACTURE_CODE, manufactureTypeCode_str);

                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_FROMNODE() == null) {
                                fromNodeStr = "";
                            } else {
                                fromNodeStr = meterConnectionList.get(j).getMTRM_FROMNODE();
                            }
                            etFromNode.setText(fromNodeStr);

                            //testcase
//                            if (meterConnectionList.get(j).get() == null) {
//                                property_assessmnt = "";
//                            } else {
//                                property_assessmnt = meterConnectionList.get(j).getMTRM_FROMNODE();
//                            }
//                            etFromNode.setText(property_assessmnt);

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_TONODE() == null) {
                                toNodeStr = "";
                                etToNode.setText(toNodeStr);

                            } else {
                                toNodeStr = meterConnectionList.get(j).getMTRM_TONODE();
                                etToNode.setText(toNodeStr);
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMI_METERINSTALLID() == null) {
                                meter_install_id_str = "";
                            } else {
                                meter_install_id_str = meterConnectionList.get(j).getMI_METERINSTALLID();
                                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDATE);
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_INSTALLDATE, meter_install_id_str);

                            }
//                            etToNode.setText(meter_install_id_str);

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMI_STATUS() == null) {
                                status_str = "";
                            } else {
                                status_str = meterConnectionList.get(j).getMI_STATUS();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_DIGITS() == null) {
                                m_digits_str = "";
                            } else {
                                m_digits_str = meterConnectionList.get(j).getMTRM_DIGITS();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getCONTRACTOR() == null) {
                                contractor_str = "";
                            } else {
                                contractor_str = meterConnectionList.get(j).getMTRM_DIGITS();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getCONTEMPCODE() == null) {
                                cont_emp_code_str = "";
                            } else {
                                cont_emp_code_str = meterConnectionList.get(j).getCONTEMPCODE();
                            }
                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getREFNO() == null) {
                                refNoStrStatic = "";
                            } else {
                                refNoStrStatic = meterConnectionList.get(j).getREFNO();
                            }
//                            etToNode.setText(meter_install_id_str);

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METER_NO);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_METER_NO, serialNoStr);

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FROM_NODE);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.FROM_NODE, fromNodeStr);

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PROPERTY_ASSESSMENT);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.PROPERTY_ASSESSMENT, property_assessmnt);

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.TO_NODE);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.TO_NODE, toNodeStr);

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_INSTALLATION_DATE() == null) {
                                installDtStr = "";
                            } else {
                                installDtStr = meterConnectionList.get(j).getMTRM_INSTALLATION_DATE();
                                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDATE);
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_INSTALLDATE, installDtStr);
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getSIZEOFMETER() == null) {
                                meterSizeStr = "";
                            } else {
                                meterSizeStr = meterConnectionList.get(j).getSIZEOFMETER();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getSEALNO() == null) {
                                sealNoStr = "";

                            } else {
                                sealNoStr = meterConnectionList.get(j).getSEALNO();
                                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDSEALNUM);
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLDSEALNUM, sealNoStr);
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_PAST_READING() == null) {
                                pastMeterReadingStr = "";
                            } else {
                                pastMeterReadingStr = meterConnectionList.get(j).getMTRM_PAST_READING();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_AVERAGE_CONSUMPTION() == null) {
                                avrageConsumtion = "";
                            } else {
                                avrageConsumtion = meterConnectionList.get(j).getMTRM_AVERAGE_CONSUMPTION();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_ESTIMATED_AVRG_CONSUMP() == null) {
                                averageEstimateConsum = "";
                            } else {
                                averageEstimateConsum = meterConnectionList.get(j).getMTRM_ESTIMATED_AVRG_CONSUMP();
                            }



                            mmgMeterConnectedDetailsModel = new MMGMeterConnectedDetailsModel(manufactureCode, mfgNameStr, serialNoStr,
                                    installDtStr, meterSizeStr, sealNoStr, pastMeterReadingStr, lastReadingDate, connectionLoad,
                                    old_meter_status_str, old_meter_status_id_str, old_meter_status_name_str,
                                    manufactureTypeCode_str, meterTypeStr, fromNodeStr, toNodeStr, meter_install_id_str,
                                    status_str, m_digits_str, contractor_str, cont_emp_code_str, refNoStrStatic,avrageConsumtion,averageEstimateConsum);
                            mmgMeterConnectionDetailList.add(mmgMeterConnectedDetailsModel);

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

            if (validateDetailList.size() > 0) {
                ArrayList<MMGValidateDetails> mmgvalidateDetailList = new ArrayList<>();

                for (int z = 0; z <= validateDetailList.size() - 1; z++) {
                    if (validateDetailList.get(z).getREFTYPE() == null) {
                        refTypeStr = "";
                    } else {
                        refTypeStr = validateDetailList.get(z).getREFTYPE();
                    }

                    if (validateDetailList.get(z).getSTATUS() == null) {
                        statusStr = "";
                    } else {
                        statusStr = validateDetailList.get(z).getSTATUS();
                    }

                    if (validateDetailList.get(z) == null) {
                        consumerMatchingStr = "";
                    } else {
                        consumerMatchingStr = validateDetailList.get(z).toString();
                    }

                    mmgValidateDetails = new MMGValidateDetails(refTypeStr, statusStr, consumerMatchingStr);
                    mmgvalidateDetailList.add(mmgValidateDetails);
                    UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_SOURCE);
                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONSUMER_SOURCE, refTypeStr);
                }
            }

            if (meter_install_id_str != null) {

                if (!meter_install_id_str.equalsIgnoreCase("")) {
                    String params[] = new String[1];
                    params[0] = meter_install_id_str;

                    GetMeterInstallById getMeterInstallById = new GetMeterInstallById();
                    getMeterInstallById.execute(params);
                } else {


                    if (meterConnectionList != null) {

                        for (int m = 0; m <= meterConnectionList.size() - 1; m++) {

                            MI_O_SIZE = meterConnectionList.get(m).getSIZEOFMETER();
                            MI_O_METER = meterConnectionList.get(m).getMTRM_SERIAL_NO();
                            MI_O_MAKE = meterConnectionList.get(m).getMTRM_MANUFACTURE_CODE();
                            MI_O_PREVIOUSREADING = meterConnectionList.get(m).getMTRM_PAST_READING();
                            MI_O_METERTYPE = meterConnectionList.get(m).getMTRM_MANUFACTURE_TYPE_CODE();
                            MI_INSTALLATIONDATE = meterConnectionList.get(m).getMTRM_INSTALLATION_DATE() + " 05:00:41 PM";


                        }
                    }
                    if (customerDetailList != null) {

                        for (int c = 0; c <= customerDetailList.size() - 1; c++) {

                           /* MI_O_FINALREADING = "";
                            MI_O_FINALSTATUS = "";
                            MI_O_OBSERVATION = "";
                            MI_O_REASON = "";
                            MI_N_MAKE = "";
                            MI_N_SIZE = "";
                            MI_N_SEAL = "";
                            MI_N_METER = "";
                          MI_N_INITIALREADING;
                            params[21] = MI_N_METERTYPE;
                            params[22] = MI_N_METERLOCATION;
                            params[23] = MI_N_ISPROTECTED;*/
                        }
                    }
                }

            }





            if (radiobuttonValStr.equalsIgnoreCase("N")&& !subType.equalsIgnoreCase("TD Reconnection") ) {

                if(!serialNoStr.equalsIgnoreCase("") ) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(false);
                    builder.setTitle(getContext().getString(R.string.failed_to_update));
                    builder.setMessage("You cannot select New HSC & Meter because this consumer already exists in the system.");

                    builder.setPositiveButton(R.string.go_back, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(mCon, MainActivity.class);
                            startActivity(i);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
            else {

            }
            //mmgMasterProgress.dismiss();

        } catch (Exception e) {
            Log.e("Get_CustomerDetails As", e.toString());
            //  mmgMasterProgress.dismiss();
            String error = e.toString();
            ErrorClass.errorData(mCon, "Consumer Details Fragment", "Get Customer details task", error);
        }
    }

    private void currentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        MI_INSTALLATIONDATE = simpleDateFormat.format(new Date());
    }

    private void setMSRDropDown() {
        msrModels = realmOperations.fetchMSRNameID();
        ArrayList<String> msrList = new ArrayList<>();
        msrList.add("Select");

        for (int i = 0; i < msrModels.size(); i++) {
            MSRModel msrModel = msrModels.get(i);
            MSRIDStr = msrModel.getSBM_ID();
            String msrName = msrModel.getSBM_NAME();
            msrList.add(msrName);
        }

        msrArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, msrList);
        msrArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msrSpinner.setAdapter(msrArrayAdapter);
       // msrSpinner.setOnItemSelectedListener(this);
    }

    private void setSRDropdown(String msrValue) {
        try {

            msrModel = realmOperations.fetchMSRByString(msrValue);
            MSRIDStr = msrModel.getSBM_ID();
            srModelRealmResults = realmOperations.fetchSRByMSRIDList(MSRIDStr);
            srStringList.clear();
            srStringList.add("Select");
            for (SRModel model : srModelRealmResults) {
                srStringList.add(model.getTRM_NAME());
                srValueList.add(String.valueOf(model.getTRM_ID()));
            }

            srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, srStringList);
            srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            srSpinner.setAdapter(srArrayAdapter);
           // srSpinner.setOnItemSelectedListener(this);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setDMADropdown(String srValue) {
        try {
            model_sr = realmOperations.fetchSRByString(srValue);
            SRIDStr = model_sr.getTRM_ID();

            dmaModelRealmResults = realmOperations.fetchDMABySRIDList(SRIDStr);
            dmaStringList.clear();
            dmaStringList.add("Select");
            for (DMAModel model : dmaModelRealmResults) {
                dmaStringList.add(model.getPM_NAME());
                dmaValueList.add(String.valueOf(model.getPM_ID()));
            }

            dmaArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, dmaStringList);
            dmaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dmaSpinner.setAdapter(dmaArrayAdapter);

            //pinky added below for loop
            for (int i = 0; i < dmaValueList.size(); i++) {
               // Log.e("llll", dmaValueList.get(i));
                if (dmaStr.equalsIgnoreCase(dmaValueList.get(i))) {
                    int position = dmaValueList.indexOf(dmaStr);
                    MI_DMA = dmaValueList.get(i);
                    dmaval = Integer.parseInt(dmaStr);
                    dmaSpinner.setOnItemSelectedListener(ConsumerDetFragmentNew.this);
                    dmaSpinner.setSelection(position + 1);
                    break;
                }
            }
         //   dmaSpinner.setOnItemSelectedListener(this);

        } catch (Exception e) {
            Log.d("DMA", "" + e.toString() + "DMA Message" + e.getMessage());

        }
    }

    private void setDMADropdown() {
        try {
            ArrayList<String> meterDMAName = new ArrayList<>();
            meterDMAName = realmOperations.fetchDMAList();
            String SRName = srSpinner.getSelectedItem().toString();
            model_sr = realmOperations.fetchSRNameByMSRName(SRName);
            srStr = String.valueOf(model_sr.getTRM_ID());

            model_dma = realmOperations.fetchDMABySRName(srStr);
            String meterStatusId = String.valueOf(model_dma.getSRID());
            ArrayList<String> fetchedDMAList = new ArrayList<>();
            fetchedDMAList = realmOperations.fetchDMAList(meterStatusId);
            dmaArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, fetchedDMAList);
            dmaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dmaSpinner.setAdapter(dmaArrayAdapter);
          //  dmaSpinner.setOnItemSelectedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit_consumer) {
            submitStatus = "Y";
            consumerNoStr = et_consumer_account_no.getText().toString();
            refNoStr = et_nsc_application_no.getText().toString().trim();
/*
            no_flat = tv_noofFlat.getText().toString();

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.No_ofFlat);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.No_ofFlat, no_flat);
*/


            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_NO);
            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMERREFERENCENUMBER);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONSUMER_NO, consumerNoStr);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONSUMERREFERENCENUMBER, refNoStr);
            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.SUBMIT_STATUS);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.SUBMIT_STATUS, submitStatus);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.RADIOBUTTONVAL, radiobuttonValStr);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.COMMISIONED_NONCOMMISIONED, commisioned_noncommisioned);

            onSubmitConsumerDetails();

        }
        if (v.getId() == R.id.saveAndExit) {
//            saveConsumerDetails();
            saveAndExit();
        }
        //next button
        if (v.getId() == R.id.submitConsumerBtn) {
            newSubmitBtnConsumerDetails();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.msrSpinner: {

                msrValue = msrSpinner.getSelectedItem().toString();

                if (msrValue.equalsIgnoreCase("Select")) {
                    ArrayList<String> dmalist = new ArrayList<>();
                    dmalist.add("Select");

                    dmaArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, dmalist);
                    dmaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dmaSpinner.setAdapter(dmaArrayAdapter);

                    ArrayList<String> srList12 = new ArrayList<>();
                    srList12.add("Select");

                    srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, srList12);
                    srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    srSpinner.setAdapter(srArrayAdapter);
                  //  srSpinner.setOnItemSelectedListener(this);
                } else {
                    msrModel = realmOperations.fetchMSRByString(msrValue);
                    MSRIDStr = msrModel.getSBM_ID();

                    if (msrStr == null) {
                        setSRDropdown(msrValue);
                    } else {
                        if (msrStr.equalsIgnoreCase(MSRIDStr)) {
                            srDropDownSelection();
                        } else {
                            setSRDropdown(msrValue);
                        }
                    }
                }
            }
            break;
            case R.id.srSpinner: {
                srValue = srSpinner.getSelectedItem().toString();

                if (srValue.equalsIgnoreCase("Select")) {
                    ArrayList<String> dmalist = new ArrayList<>();
                    dmalist.add("Select");

                    dmaArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, dmalist);
                    dmaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dmaSpinner.setAdapter(dmaArrayAdapter);
                } else {
                    model_sr = realmOperations.fetchSRByString(srValue);
                    SRIDStr = model_sr.getTRM_ID();
                    try {


                        srStr = model_sr.getTRM_ID();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (srStr == null) {
                                setDMADropdown(srValue);
                            } else {
                                if (srStr.equalsIgnoreCase(MI_SR)) {
                                    dmaDropdownSelection();
                                } else {
                                    setDMADropdown(srValue);
                                }
                            }
                        }
                    }, 500);

                }
            }
            break;

            case R.id.dmaSpinner: {
                String dmaSelectedValue = dmaSpinner.getSelectedItem().toString();
                if (dmaSelectedValue.equalsIgnoreCase("Select")) {
                    dmaId = 0;
                } else {
                    model_dma = realmOperations.getDMAId(dmaSelectedValue);
                    String dmaIDStr = model_dma.getPM_ID();
                    dmaId = Integer.parseInt(dmaIDStr);
                }
            }
            break;
            default:

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetConsumerAndMeterDetails, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MMGGetDetailsResponseModel mmgGetDetailsResponseModel = gson.fromJson(jsonResponse, MMGGetDetailsResponseModel.class);
                if (mmgGetDetailsResponseModel != null) {
                    validations();

                    ArrayList<MMGCustDetModel> customerDetailList = (ArrayList<MMGCustDetModel>) mmgGetDetailsResponseModel.getMMGCustomerDetails();
                    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = (ArrayList<MMGMeterConnectedDetailsModel>) mmgGetDetailsResponseModel.getMMGMeterConnectionDetails();
                    ArrayList<InstallDetails> installDetails = (ArrayList<InstallDetails>) mmgGetDetailsResponseModel.getInstallDetailsList();

                    if (installDetails != null) {

                        for (int k = 0; k <= installDetails.size() - 1; k++) {

                            String dataFound = installDetails.get(k).getNODATAFOUND();

                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.DATAFOUND, dataFound);
                            if (dataFound.equalsIgnoreCase("DataFound")) {


                                property_assessmnt = installDetails.get(k).getMI_PROPERTYTAXNO();
                                Log.d("property_asses", "" + property_assessmnt);
                                alternateMobStr = installDetails.get(k).getMI_ALTMOBILE();
                                Log.d("alternate", "" + alternateMobStr);
                                fromNodeStr = installDetails.get(k).getMI_FROMNODE();
                                toNodeStr = installDetails.get(k).getMI_TONODE();
                                msrStr = installDetails.get(k).getMSRID();
                                srStr = installDetails.get(k).getMI_SR();
                                dmaStr = installDetails.get(k).getMI_DMA();
                                gis_bidStr = installDetails.get(k).getMI_GIS();
                                MI_METERINSTALLID = installDetails.get(k).getMI_METERINSTALLID();
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.MI_METERINSTALLID, MI_METERINSTALLID);


                                et_property_assessement.setText(property_assessmnt);
                                etFromNode.setText(fromNodeStr);
                                etToNode.setText(toNodeStr);
                                alternateNo.setText(alternateMobStr);
                                tv_gisBid.setText(gis_bidStr);

                                installedDetails(msrStr, srStr, dmaStr);
                            }

                        }

                    }

                    for (int i = 0; i <= customerDetailList.size() - 1; i++) {
                        if (customerDetailList.get(i).getSRM_S_FIRST_NAME() == null) {
                            consumerNameStr = "";
                            tv_name_of_consumer.setText(consumerNameStr);
                        } else {
                            consumerNameStr = customerDetailList.get(i).getSRM_S_FIRST_NAME().trim();
                            tv_name_of_consumer.setText(customerDetailList.get(i).getSRM_S_FIRST_NAME());
                        }
                        if (customerDetailList.get(i).getSRM_S_MOBILE_NO() == null || customerDetailList.get(i).getSRM_S_MOBILE_NO().equalsIgnoreCase("")) {
                            primaryMobStr = "";
                            mobTextView.setText(primaryMobStr);
                            mobTextView.setEnabled(true);
                        } else {
                            primaryMobStr = customerDetailList.get(i).getSRM_S_MOBILE_NO().trim();
                            mobTextView.setText(customerDetailList.get(i).getSRM_S_MOBILE_NO());
                        }

                        alternateNo.setText(alternateMobStr);

                        if (customerDetailList.get(i).getSRM_SERVICE_NO() == null || customerDetailList.get(i).getSRM_SERVICE_NO().equalsIgnoreCase("")) {
                            consumerNo.setText("");
                        } else {
                            consumerNo.setText(customerDetailList.get(i).getSRM_SERVICE_NO());
                        }

                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PRIMARY_MOBILE);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.PRIMARY_MOBILE, primaryMobStr);

                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.ALTERNATE_MOBILE);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.ALTERNATE_MOBILE, alternateMobStr);

                        if (customerDetailList.get(i).getTFM_TARIFF_NAME() == null || customerDetailList.get(i).getTFM_TARIFF_NAME().equalsIgnoreCase("")) {
                            categoryConnStr = "";
                            catgryConnTextView.setText(categoryConnStr);
                        } else {
                            categoryConnStr = customerDetailList.get(i).getTFM_TARIFF_NAME();
                            catgryConnTextView.setText(customerDetailList.get(i).getTFM_TARIFF_NAME());
                        }

                        if (customerDetailList.get(i).getSRM_CONNECTION_LOAD() == null ||
                                customerDetailList.get(i).getSRM_CONNECTION_LOAD().equalsIgnoreCase("")) {
                            connSizeStr = "";
                            connSizeTextView.setText(connSizeStr);

                        } else {
                            connSizeStr = customerDetailList.get(i).getSRM_CONNECTION_LOAD();
                            connSizeTextView.setText(customerDetailList.get(i).getSRM_CONNECTION_LOAD());

                        }

                        houseNoStr = customerDetailList.get(i).getSRM_B_DOOR_NO();
                        tv_houseNo.setText(houseNoStr);

                        if (customerDetailList.get(i).getCM_CIRCLE_NAME() == null ||
                                customerDetailList.get(i).getCM_CIRCLE_NAME().equalsIgnoreCase("")) {
                            circleName = "";
                            tv_group.setText(circleName);
                        } else {
                            circleName = customerDetailList.get(i).getCM_CIRCLE_NAME();
                            tv_group.setText(circleName);
                        }

                        if (customerDetailList.get(i).getSRM_CONNECTION_LOAD() == null ||
                                customerDetailList.get(i).getSRM_CONNECTION_LOAD().equalsIgnoreCase("")) {
                            connectionLoad = "";
                        } else {
                            connectionLoad = customerDetailList.get(i).getSRM_CONNECTION_LOAD();
                        }

                        if (customerDetailList.get(i).getCM_CIRCLE_NAME() == null ||
                                customerDetailList.get(i).getCM_CIRCLE_NAME().equalsIgnoreCase("")) {
                            circleName = "";
                            tv_group.setText(circleName);

                        } else {
                            circleName = customerDetailList.get(i).getCM_CIRCLE_NAME();
                            tv_group.setText(circleName);
                        }

                        et_property_assessement.setText(property_assessmnt);

                        if (customerDetailList.get(i).getSRM_B_ADDRESS1() == null) {
                            areaNameStr = "";
                        } else {
                            areaNameStr = customerDetailList.get(i).getSRM_B_ADDRESS1();
                        }

                        if (customerDetailList.get(i).getSRM_B_ADDRESS2() == null) {
                            roadNameStr = "";
                            tv_roadName.setText(roadNameStr);
                        } else {
                            roadNameStr = customerDetailList.get(i).getSRM_B_ADDRESS2();
                            tv_roadName.setText(customerDetailList.get(i).getSRM_B_ADDRESS2());
                        }

                        if (customerDetailList.get(i).getSRM_B_PIN() == null) {
                            pincodeStr = "";
                            tv_pincode.setText(pincodeStr);

                        } else {
                            pincodeStr = customerDetailList.get(i).getSRM_B_PIN();
                            tv_pincode.setText(customerDetailList.get(i).getSRM_B_PIN());
                        }

                        if (customerDetailList.get(i).getBUM_BU_NAME() == null) {
                            zoneStr = "";
                            tv_zone.setText(zoneStr);

                        } else {
                            zoneStr = customerDetailList.get(i).getBUM_BU_NAME();
                            tv_zone.setText(customerDetailList.get(i).getBUM_BU_NAME());
                        }

                        if (customerDetailList.get(i).getGROUPNAME() == null) {
                            groupStr = "";
                            tv_group.setText(groupStr);

                        } else {
                            tv_group.setText(customerDetailList.get(i).getGROUPNAME());
                        }

                        if (customerDetailList.get(i).getWARD() == null) {
                            wardStr = "";
                            tv_ward.setText(wardStr);

                        } else {
                            tv_ward.setText(customerDetailList.get(i).getWARD());
                        }

                        if (tv_gisBid.getText().toString().equalsIgnoreCase("")) {
                            if (customerDetailList.get(i).getSRM_S_GIS_ID() == null) {
                                gis_bidStr = "";
                                tv_gisBid.setText(gis_bidStr);
                                tv_gisBid.setEnabled(true);
                            } else {
                                gis_bidStr = customerDetailList.get(i).getSRM_S_GIS_ID();
                                tv_gisBid.setText(customerDetailList.get(i).getSRM_S_GIS_ID());
                                tv_gisBid.setEnabled(true);
                            }
                        }

                        if (customerDetailList.get(i).getSRM_B_ADDRESS3() == null) {
                            landmark = "";
                            tv_landmarkName.setText("");
                        } else {
                            landmark = customerDetailList.get(i).getBUM_BU_NAME();
                            tv_landmarkName.setText(customerDetailList.get(i).getSRM_B_ADDRESS3());
                        }

                        if (customerDetailList.get(i).getSRM_BU_ID() == null) {
                            BU = "";
                        } else {
                            BU = customerDetailList.get(i).getSRM_BU_ID();
                        }

                        if (customerDetailList.get(i).getSRM_PC_ID() == null) {
                            PC = "";
                        } else {
                            PC = customerDetailList.get(i).getSRM_PC_ID();
                        }


                        if (meterConnectionList.get(i).getMSM_METERSTATUS_NAME() == null) {
                            meterStatusStr = "";
                        } else {
                            meterStatusStr = meterConnectionList.get(i).getMSM_METERSTATUS_NAME();
                        }

                        if (meterConnectionList.get(i).getMTRM_MANUFACTURE_TYPE_CODE() == null) {
                            mtrTypeCodeId = "";
                        } else {

                            mtrTypeCodeId = meterConnectionList.get(i).getMTRM_MANUFACTURE_TYPE_CODE();
                        }

                        if (meterConnectionList.get(i).getSRM_CONNSIZE_ID() == null) {
                            mtrSizeId = "";
                        } else {
                            mtrSizeId = meterConnectionList.get(i).getSRM_CONNSIZE_ID();
                        }

                        /*Connection Size ID*/
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MTR_SIZE_ID);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.MTR_SIZE_ID, mtrSizeId);

                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MTR_TYPE_CODE_ID);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.MTR_TYPE_CODE_ID, mtrTypeCodeId);

                        if (meterConnectionList.get(i).getO_METERTYPE() == null) {
                            meterTypeStr = "";
                        } else {
                            meterTypeStr = meterConnectionList.get(i).getO_METERTYPE();
                        }

                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.GIS_BID);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.GIS_BID, gis_bidStr);

                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.BU);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.BU, BU);

                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PC);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.PC, PC);

                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONNECTIONLOAD);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONNECTIONLOAD, connectionLoad);
                    }


                    UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_NAME);
                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONSUMER_NAME, consumerNameStr);

                    UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERSTATUS);
                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.METERSTATUS, meterStatusStr);

                    UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERTYPENAME);
                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.METERTYPENAME, meterTypeStr);

                    if (meterConnectionList == null) {
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MAKERCODENAME);
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METER_NO);
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDATE);
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDSEALNUM);

                    } else {
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

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MAKERCODENAME);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.MAKERCODENAME, mfgNameStr);

                            if (meterConnectionList.get(j).getMTRM_SERIAL_NO() == null) {
                                serialNoStr = "";
                            } else {
                                serialNoStr = meterConnectionList.get(j).getMTRM_SERIAL_NO();
                                oldmeterno = serialNoStr;
                            }

                            if (meterConnectionList.get(j).getMRT_OLDMETER_STATUS() == null) {
                                old_meter_status_str = "";
                            } else {
                                old_meter_status_str = meterConnectionList.get(j).getMRT_OLDMETER_STATUS();
                            }

                            if (meterConnectionList.get(j).getMTRM_METERSTATUS_ID() == null) {
                                old_meter_status_id_str = "";
                            } else {
                                old_meter_status_id_str = meterConnectionList.get(j).getMTRM_METERSTATUS_ID();
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
                                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.O_MANUFACTURE_CODE);
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.O_MANUFACTURE_CODE, manufactureTypeCode_str);

                            }

                            if (meterConnectionList.get(j).getMTRM_FROMNODE() == null) {
                                fromNodeStr = "";
                            } else {
                                fromNodeStr = meterConnectionList.get(j).getMTRM_FROMNODE();
                            }
                            etFromNode.setText(fromNodeStr);

                            if (meterConnectionList.get(j).getMTRM_TONODE() == null) {
                                toNodeStr = "";
                                etToNode.setText(toNodeStr);

                            } else {
                                toNodeStr = meterConnectionList.get(j).getMTRM_TONODE();
                                etToNode.setText(toNodeStr);
                            }

                            if (meterConnectionList.get(j).getMI_METERINSTALLID() == null) {
                                meter_install_id_str = "";
                            } else {
                                meter_install_id_str = meterConnectionList.get(j).getMI_METERINSTALLID();
                                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDATE);
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_INSTALLDATE, meter_install_id_str);

                            }
//                            etToNode.setText(meter_install_id_str);

                            if (meterConnectionList.get(j).getMI_STATUS() == null) {
                                status_str = "";
                            } else {
                                status_str = meterConnectionList.get(j).getMI_STATUS();
                            }

                            if (meterConnectionList.get(j).getMTRM_DIGITS() == null) {
                                m_digits_str = "";
                            } else {
                                m_digits_str = meterConnectionList.get(j).getMTRM_DIGITS();
                            }

                            if (meterConnectionList.get(j).getCONTRACTOR() == null) {
                                contractor_str = "";
                            } else {
                                contractor_str = meterConnectionList.get(j).getMTRM_DIGITS();
                            }

                            if (meterConnectionList.get(j).getCONTEMPCODE() == null) {
                                cont_emp_code_str = "";
                            } else {
                                cont_emp_code_str = meterConnectionList.get(j).getCONTEMPCODE();
                            }
//                            etToNode.setText(meter_install_id_str);

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METER_NO);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_METER_NO, serialNoStr);

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FROM_NODE);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.FROM_NODE, fromNodeStr);

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.TO_NODE);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.TO_NODE, toNodeStr);

                            UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);

                            if (meterConnectionList.get(j).getMTRM_INSTALLATION_DATE() == null) {
                                installDtStr = "";
                            } else {
                                installDtStr = meterConnectionList.get(j).getMTRM_INSTALLATION_DATE();
                                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDATE);
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLD_INSTALLDATE, installDtStr);
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
                                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDSEALNUM);
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.OLDSEALNUM, sealNoStr);
                            }

                            if (meterConnectionList.get(j).getMTRM_PAST_READING() == null) {
                                pastMeterReadingStr = "";
                            } else {
                                pastMeterReadingStr = meterConnectionList.get(j).getMTRM_PAST_READING();
                            }

                            if (meterConnectionList.get(j).getLASTREADINGDATE() == null) {
                                lastReadingDate = "";
                            } else {
                                lastReadingDate = meterConnectionList.get(j).getLASTREADINGDATE();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_AVERAGE_CONSUMPTION() == null) {
                                avrageConsumtion = "";
                            } else {
                                avrageConsumtion = meterConnectionList.get(j).getMTRM_AVERAGE_CONSUMPTION();
                            }

                            if (meterConnectionList.size() == 0||meterConnectionList.get(j).getMTRM_ESTIMATED_AVRG_CONSUMP() == null) {
                                averageEstimateConsum = "";
                            } else {
                                averageEstimateConsum = meterConnectionList.get(j).getMTRM_ESTIMATED_AVRG_CONSUMP();
                            }

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_AVERAGECONSUMTION);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_AVERAGECONSUMTION, averageEstimateConsum);





                            mmgMeterConnectedDetailsModel = new MMGMeterConnectedDetailsModel(manufactureCode, mfgNameStr, serialNoStr,
                                    installDtStr, meterSizeStr, sealNoStr, pastMeterReadingStr, lastReadingDate, connectionLoad,
                                    old_meter_status_str, old_meter_status_id_str, old_meter_status_name_str,
                                    manufactureTypeCode_str, meterTypeStr, fromNodeStr, toNodeStr, meter_install_id_str,
                                    status_str, m_digits_str, contractor_str, cont_emp_code_str, refNoStrStatic,avrageConsumtion,averageEstimateConsum);
                            mmgMeterConnectionDetailList.add(mmgMeterConnectedDetailsModel);
                        }

                    }


                    ArrayList<MMGValidateDetails> validateDetailList = (ArrayList<MMGValidateDetails>) mmgGetDetailsResponseModel.getMmgValidateDetails();

                    ArrayList<MMGValidateDetails> mmgvalidateDetailList = new ArrayList<>();

                    for (int z = 0; z <= validateDetailList.size() - 1; z++) {
                        if (validateDetailList.get(z).getREFTYPE() == null) {
                            refTypeStr = "";
                        } else {
                            refTypeStr = validateDetailList.get(z).getREFTYPE();
                        }

                        if (validateDetailList.get(z).getSTATUS() == null) {
                            statusStr = "";
                        } else {
                            statusStr = validateDetailList.get(z).getSTATUS();
                        }

                        if (validateDetailList.get(z) == null) {
                            consumerMatchingStr = "";
                        } else {
                            consumerMatchingStr = validateDetailList.get(z).toString();
                        }
//                        if(validateDetailList.get(z).getSTATUS()==null){
//                            statusStr="";
//                        }else{
//                            statusStr=validateDetailList.get(z).getSTATUS();
//                        }
                        mmgValidateDetails = new MMGValidateDetails(refTypeStr, statusStr, consumerMatchingStr);
                        mmgvalidateDetailList.add(mmgValidateDetails);
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_SOURCE);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONSUMER_SOURCE, refTypeStr);
                    }

                } else {
                    MessageWindow.throwOutFromWindow(getContext(), "Invalid Consumer Number", "Invalid Data", MainActivity.class);
                }

                if (meter_install_id_str != null) {
                    /*    if (pagename != null) {
                     *//* if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                            fetchDetailsFromContractor();
                        }*//*
                    } else {*/
                    if (!meter_install_id_str.equalsIgnoreCase("")) {
                        String params[] = new String[1];
                        params[0] = meter_install_id_str;

                        GetMeterInstallById getMeterInstallById = new GetMeterInstallById();
                        getMeterInstallById.execute(params);
                    }
                    //}
                }
                radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);



                if (radiobuttonValStr.equalsIgnoreCase("N") && !serialNoStr.equalsIgnoreCase("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getContext().getString(R.string.failed_to_update))
                            .setCancelable(false)
                            .setMessage("You cannot select New HSC & Meter because this consumer already exists in the system.");

                    builder.setPositiveButton(getContext().getString(R.string.go_back),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(mCon, MainActivity.class);
                                    startActivity(i);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

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

    private void installedDetails(String msrStr, String srStr, String dma) {

        msrModels = realmOperations.fetchMSRNameID();
        for (int i = 0; i < msrModels.size(); i++) {
            MSRModel msrModel = msrModels.get(i);
            MSRIDStr = msrModel.getSBM_ID();

            if (msrStr.equalsIgnoreCase(MSRIDStr)) {
                int val = Integer.parseInt(msrStr);
                msrSpinner.setOnItemSelectedListener(ConsumerDetFragmentNew.this);
                msrSpinner.setSelection(val);
                break;
            }
        }

        srModels = realmOperations.fetchSRNameID();
        for (int i = 0; i < srModels.size(); i++) {
            SRModel srModel = srModels.get(i);

            String srid = String.valueOf(srModel.getTRM_ID());

            if (srStr.equalsIgnoreCase(srid)) {
                number = Integer.parseInt(srStr);
                srSpinner.setOnItemSelectedListener(ConsumerDetFragmentNew.this);
                srSpinner.setSelection(number);
                break;
            }
        }
        for (int i = 0; i < dmaModels.size(); i++) {
            DMAModel dmaModel = dmaModels.get(i);
            String id = dmaModel.getPM_ID();

            try {


                if (dmaStr.equalsIgnoreCase(id)) {
                    int position = dmaValueList.indexOf(dmaStr);
                    MI_DMA = id;
                    dmaval = Integer.parseInt(dmaStr);
                    dmaSpinner.setOnItemSelectedListener(ConsumerDetFragmentNew.this);
                    dmaSpinner.setSelection(position + 1);
                    break;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
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
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.no_internet_connection),"Alert");
        }
    }

    public interface SendMessage {
        //        void sendData(String submitStatus, String radiobuttonValStr,  String makerCodeId,String serialNoStr,String installDtStr,String meterSizeStr,String sealNoStr,String pastMeterReadingStr);
        void sendData(String manufacturedata, String oldmeterno, String sealNoStr, String installDtStr, String meterSizeStr,
                      String pastMeterReadingStr, String submitStatus, String radiobuttonValStr, String consumerNoStr, String zoneStr,
                      String groupStr, String refTypeStr, String connSizeStr, String property_assessmnt,
                      String fromNodeStr, String toNodeStr, String primaryMobStr, String alternateMobStr,
                      String gis_bidStr, String dmaId, String srId, String msrId, String commisioned_noncommisioned,
                      String employee_code, String mac_address, String contList, boolean isSubmitted);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
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



    private void validations() {
        etFromNode.setEnabled(true);
        etToNode.setEnabled(true);
        alternateNo.setEnabled(true);
        et_property_assessement.setEnabled(true);
    }

//    @SuppressLint("StaticFieldLeak")
//    private class GetSRByMSRId extends AsyncTask<String, Void, Void>  {
//        @Override
//        protected void onPreExecute() {
//            srProgress = new MaterialDialog.Builder(mCon)
//                    .content(R.string.loading)
////                    .content("SR")
//                    .progress(true, 0)
//                    .cancelable(true)
//                    .canceledOnTouchOutside(true)
//                    .widgetColorRes(R.color.colorPrimary)
//                    .show();
//        }
//
//        @Override
//        protected Void doInBackground(String... params) {
//            try {
//                String paraNames[] = new String[1];
//                paraNames[0] = "MSRId";
//                jsonSRresponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetSRByMSRIdDetails, params, paraNames);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            try {
//                DownloadSRModel[] enums = gson.fromJson(jsonSRresponse, DownloadSRModel[].class);
//                if (enums.length > 0) {
//                    srList.clear();
//                    srListDummy.clear();
//
//                    srList.add(new DownloadSRModel(0,"--Select--"));
//
//                    for (DownloadSRModel srNameDetails : enums) {
//                        srModel = new DownloadSRModel(srNameDetails.getTRM_NAME());
//                        srList.add(srModel);
//
//                        srModelDummy = new DownloadSRModel(srNameDetails.getTRM_ID(), srNameDetails.getTRM_NAME());
//                        srListDummy.add(srModelDummy);
//                    }
//
//                    SRModel srModels = realmOperations.getSRExist();
//                    if (srModels == null) {
//                        for (DownloadSRModel srModel1 : enums) {
//                            SRModel  srModel22= new SRModel(srModel1.getTRM_ID(), srModel1.getTRM_NAME());
////                            realmOperations.updateSRData(srModel1.getTRM_ID(), srModel1.getTRM_NAME());
//                            realmOperations.addSR(srModel22);
//                        }
//                    }
//                    srDropDownSelection();
//                }
//                srProgress.dismiss();
//            }catch (Exception e){
//                Log.e("Exception",e.toString());
//                MessageWindow.errorWindow(getActivity(), "Something went wrong");
//                srProgress.dismiss();
//                String error = e.toString();
//                ErrorClass.errorData(mCon, "Consumer Details Fragment", "SR Dropdown", error);
//            }
//        }
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class GetDMABySRId extends AsyncTask<String, Void, Void> {
//        @Override
//        protected Void doInBackground(String... params) {
//            try {
//                String paraNames[] = new String[1];
//                paraNames[0] = "SRId";  //1
//                jsonDMAresponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetDMABySRIdDetails, params, paraNames);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            try {
//                DownloadDMA[] enums = gson.fromJson(jsonDMAresponse, DownloadDMA[].class);
//                dmaProgress.dismiss();
//                realmOperations.deleteDMATable();
//                if (enums.length > 0) {
//                    for (DownloadDMA downloadDMA : enums) {
//                        dmaModel = new DownloadDMA(downloadDMA.getPM_ID(), downloadDMA.getPM_NAME());
//                        dmaBySr.add(dmaModel.getPM_NAME());
//                    }
//                        for (DownloadDMA dmaModel1 : enums) {
//                            DMAModel  srModel22= new DMAModel(dmaModel1.getPM_ID(), dmaModel1.getPM_NAME());
//                            realmOperations.addDMA(srModel22);
//                        }
//                    dmaDropdownSelection();
//                }
//
//            } catch (Exception e) {
//                Log.e("DMA Exception", e.toString());
//                dmaProgress.dismiss();
//                String error = e.toString();
//                ErrorClass.errorData(mCon, "Consumer Details Fragment", "DMA Dropdown", error);
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            dmaProgress = new MaterialDialog.Builder(mCon)
//                    .content(R.string.loading)
////                    .content("DMA")
//                    .progress(true, 0)
//                    .cancelable(true)
//                    .canceledOnTouchOutside(true)
//                    .widgetColorRes(R.color.colorPrimary)
//                    .show();
//        }
//    }

    private void onSubmitConsumerDetails() {
        getCustomerDetails();
    }

    private void saveConsumerDetails() {
        property_assessmnt = et_property_assessement.getText().toString();
        alternateMobStr = alternateNo.getText().toString();
        fromNodeStr = etFromNode.getText().toString();
        toNodeStr = etToNode.getText().toString();
        zoneStr = tv_zone.getText().toString();
        groupStr = tv_group.getText().toString();
        wardStr = tv_ward.getText().toString();

        CustomerDetailsAnnex6 customerDetailsAnnex6 = new CustomerDetailsAnnex6(consumerNoStr, consumerNameStr,
                primaryMobStr, alternateMobStr, categoryConnStr, connSizeId, fromNodeStr, toNodeStr, houseNoStr,
                gis_bidStr, roadNameStr, landmark, property_assessmnt, pincodeStr, zoneStr, groupStr, wardStr, msrId,
                srId, dmaId);
        realmOperations.insertCustomerDetails(customerDetailsAnnex6);

    }

    private void getAllFields() {
        property_assessmnt = et_property_assessement.getText().toString();
        alternateMobStr = alternateNo.getText().toString();
        fromNodeStr = etFromNode.getText().toString();
        toNodeStr = etToNode.getText().toString();
        primaryMobStr = mobTextView.getText().toString();
        gis_bidStr = tv_gisBid.getText().toString();
        UtilitySharedPreferences.setPrefs(getActivity(), Constants.GIS_BID,gis_bidStr);
        //Log.e("gis_bidStr",gis_bidStr);
        msrStr = String.valueOf(msrId);
        srStr = String.valueOf(srId);
        dmaStr = String.valueOf(dmaId);
        zoneStr = tv_zone.getText().toString();
        groupStr = tv_group.getText().toString();
        wardStr = tv_ward.getText().toString();
    }

    private void saveAndExit() {
        commisioned_noncommisioned = UtilitySharedPreferences.getPrefs(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);
        radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);

        getAllFields();
        saveDataToServer();
    }

    private void newSubmitBtnConsumerDetails() {
        submitStatus = "Y";
        commisioned_noncommisioned = UtilitySharedPreferences.getPrefs(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);
        radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);
        no_flat = tv_noofFlat.getText().toString();

        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.No_ofFlat);
        UtilitySharedPreferences.setPrefs(getActivity(), Constants.No_ofFlat, no_flat);

        getAllFields();

        String emp_code = null;
        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        dmaStr = dmaSpinner.getSelectedItem().toString();
        srStr = srSpinner.getSelectedItem().toString();
        msrStr = msrSpinner.getSelectedItem().toString();

       /* if (property_assessmnt.equalsIgnoreCase("") || gis_bidStr.equalsIgnoreCase("")
                || dmaStr.equalsIgnoreCase("Select")) {
            if (property_assessmnt.equalsIgnoreCase("")) {
                MessageWindow.messageWindow(mCon,"Property Assessment is mandatory","Alert");
            } else if (gis_bidStr.equalsIgnoreCase("")) {
                MessageWindow.messageWindow(mCon,"GIS BID is mandatory","Alert");
            }*/
           /* else if (!msrStr.equalsIgnoreCase("Select") && (srStr.equalsIgnoreCase("Select"))) {
                MessageWindow.messageWindow(mCon,"Please Select SR","Alert");
            } else if (!srStr.equalsIgnoreCase("Select") && dmaStr.equalsIgnoreCase("Select")) {
                Toast.makeText(mCon, "Please Select DMA", Toast.LENGTH_SHORT).show();
            }  else {*/
                zoneStr = tv_zone.getText().toString();
                groupStr = tv_group.getText().toString();
                wardStr = tv_ward.getText().toString();
                isSubmitted = true;

                SM.sendData(manufactureCode, oldmeterno, sealNoStr, installDtStr, connSizeStr, pastMeterReadingStr,
                        submitStatus, radiobuttonValStr, consumerNoStr, zoneStr, groupStr, refTypeStr,
                        connSizeStr, property_assessmnt, fromNodeStr, toNodeStr, primaryMobStr, alternateMobStr, gis_bidStr,
                        String.valueOf(dmaId), SRIDStr, String.valueOf(msrId), commisioned_noncommisioned,
                        emp_code, PreferenceUtil.getMac(), contList, isSubmitted);
//                 MessageWindow.msgWindow(getContext(), "Details Submitted Succesfully Slide Right for Old Meter Details");
                ((MMGScreenActivity) getActivity()).onClickNext(0);

                MMGScreenActivity.animationOnArrow();
           /* }
        } else {
            zoneStr = tv_zone.getText().toString();
            groupStr = tv_group.getText().toString();
            wardStr = tv_ward.getText().toString();
            isSubmitted = true;

            SM.sendData(manufactureCode, oldmeterno, sealNoStr, installDtStr, connSizeStr, pastMeterReadingStr,
                    submitStatus, radiobuttonValStr, consumerNoStr, zoneStr, groupStr, refTypeStr,
                    connSizeStr, property_assessmnt, fromNodeStr, toNodeStr, primaryMobStr, alternateMobStr, gis_bidStr,
                    String.valueOf(dmaId), SRIDStr, String.valueOf(msrId), commisioned_noncommisioned,
                    emp_code, PreferenceUtil.getMac(), contList, isSubmitted);

            ((MMGScreenActivity) getActivity()).onClickNext(0);

            MMGScreenActivity.animationOnArrow();
        }*/
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_MaterialDetailsSpinnerTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            detailprogress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }

        //meter replacement new screen async task for getting master data
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[2];
                paraName[0] = "MeterSizeId";
                paraName[1] = "Type";

                jsonMaterialCont = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetMaterialDetailsByMCSID, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                detailprogress.dismiss();
                MMGMaterialResponseModel[] mmgGetDetailsResponseModel = gson.fromJson(jsonMaterialCont, MMGMaterialResponseModel[].class);

                if (mmgGetDetailsResponseModel != null) {
                    MMGMaterialResponseModel mmgMaterialResponseModelExist = realmOperations.getdrpdownExist();

                    if (mmgGetDetailsResponseModel != null && mmgGetDetailsResponseModel.length > 0) {
                        for (MMGMaterialResponseModel model : mmgGetDetailsResponseModel) {

                            srNo = model.getSLNO();
                            materialId = model.getMRM_MATERIAL_ID();
                            materialName = model.getMRM_MATERIAL_NAME();
                            defaultQty = model.getDEFAULTQUANTITY();
                            uomName = model.getUOM_NAME();
                            mrmGroupID = model.getMRM_GROUPID();
                            // serialNo=srNo.substring(0,srNo.indexOf('.'));
                            serialNo = srNo;

                            MMGMaterialResponseModel mmgMaterialResponseModel = new MMGMaterialResponseModel(serialNo, materialId, materialName, defaultQty.substring(0, defaultQty.indexOf('.')), uomName, mrmGroupID.substring(0, mrmGroupID.indexOf('.')));
                            if (mmgMaterialResponseModelExist == null) {
                                realmOperations.adddrpdownDet(mmgMaterialResponseModel);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                Log.e("Material Exception", e.toString());
                detailprogress.dismiss();
                String error = e.toString();
                ErrorClass.errorData(mCon, "Consumer Details Fragment", "Get Material Details Dropdown", error);
            }
        }
    }

    private void getSpinnerDropDownDetails() {
        //Ashwini: This method is for downloading Dropdown of Material Details, Where tag is "1"
        String params[] = new String[2];
        params[0] = meterSizeStr;
        params[1] = "1";

        Get_MaterialDetailsSpinnerTask get_materialDetailsSpinnerTask = new Get_MaterialDetailsSpinnerTask();
        get_materialDetailsSpinnerTask.execute(params);

    }

    @SuppressLint("StaticFieldLeak")
    class GetMeterInstallById extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[1];
                paraName[0] = "MtrInstallId";

                jsonResponseFromContractor = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetMeterInstallById, params, paraName);

                // Log.e("ConsResponse", jsonResponseFromContractor);
            } catch (Exception e) {
                progress.dismiss();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                progress.dismiss();
                contractorList.clear();
                MMGGetMeterInstallByIdSingleContractor[] enums = gson.fromJson(jsonResponseFromContractor, MMGGetMeterInstallByIdSingleContractor[].class);
                if (!enums[0].getMI_METERINSTALLID().equalsIgnoreCase("NULL")) {
                    for (MMGGetMeterInstallByIdSingleContractor model : enums) {
                        contModels = new MMGGetMeterInstallByIdSingleContractor(model.getMI_METERINSTALLID(),
                                model.getMI_ACTION(),
                                model.getMI_CONSUMER(),
                                model.getMI_BU(),
                                model.getMI_PC(),
                                model.getMI_REFNO(),
                                model.getMI_O_SIZE(),
                                model.getMI_O_METER(),
                                model.getMI_O_MAKE(),
                                model.getMI_O_PREVIOUSREADING(),
                                model.getMI_O_FINALREADING(),
                                model.getMI_O_FINALSTATUS(),
                                model.getMI_O_REASON(),
                                model.getMI_O_METERTYPE(),
                                model.getMI_N_MAKE(),
                                model.getMI_N_SIZE(),
                                model.getMI_N_SEAL(),
                                model.getMI_N_METER(),
                                model.getMI_INSTALLATIONDATE(),
                                model.getMI_N_INITIALREADING(),
                                model.getMI_N_METERTYPE(),
                                model.getMI_N_METERLOCATION(),
                                model.getMI_N_ISPROTECTED(),
                                model.getMI_PROPERTYTAXNO(),
                                model.getMI_N_ISMETERHANDOVER(),
                                model.getMI_CONTRACTOR(),
                                model.getMI_CONTRACTOREMP(),
                                model.getMI_N_ISMATERIALHANDOVER(),
                                model.getMI_PCCBEDDINGLEN(),
                                model.getMI_PCCBEDDINGWID(),
                                model.getMI_PCCBEDDINGDEP(),
                                model.getMI_ROADCUTTINGTYPE(),
                                model.getMI_ROADCUTTINGLEN(),
                                model.getMI_ROADCUTTINGWID(),
                                model.getMI_ROADCUTTINGDEP(),
                                model.getMI_FROMNODE(),
                                model.getMI_TONODE(),
                                model.getMI_REGMOBILE(),
                                model.getMI_ALTMOBILE(),
                                model.getMI_GIS(),
                                model.getMI_DMA(),
                                model.getMI_SR(),
                                model.getMI_MODIFIEDBY(),
                                model.getMI_MODIFIEDDATE(),
                                model.getMI_IP(),
                                model.getMI_AUTHENTICATEDATE(),
                                model.getMI_AUTHREJECTBY(),
                                model.getMI_REJECTEDDATE(),
                                model.getMI_STATUS(),
                                model.getMI_ISACTIVE(),
                                model.getMI_XMLMATERIAL(),
                                model.getMI_XMLCIVIL(),
                                model.getMI_O_OBSERVATION(),
                                model.getMI_SOURCE(),
                                model.getMI_ISCOMMISSIONED(),
                                model.getMI_CONTRACTOROTHER(),
                                model.getMI_CONTRACTOREMPOTHER(),
                                model.getMI_N_DIGIT(),
                                model.getMSRID());
                        contractorList.add(contModels);

                        String action = contModels.getMI_ACTION();
                        String consumerNo = contModels.getMI_CONSUMER();
                        String ref_no = contModels.getMI_REFNO();
                        String is_commisioned = contModels.getMI_ISCOMMISSIONED();

                        if (action != null && !action.equals("")) {
                            // UtilitySharedPreferences.clearPrefKey(mCon, Constants.RADIOBUTTONVAL);
                            // UtilitySharedPreferences.setPrefs(mCon, Constants.RADIOBUTTONVAL, action);
                        }

                        if (is_commisioned != null && !is_commisioned.equals("")) {
                            UtilitySharedPreferences.clearPrefKey(mCon, Constants.COMMISIONED_NONCOMMISIONED);
                            UtilitySharedPreferences.setPrefs(mCon, Constants.COMMISIONED_NONCOMMISIONED, is_commisioned);
                        }

                        JSONArray jArray = new JSONArray();

                        for (MMGGetMeterInstallByIdSingleContractor installByContModel : contractorList) {
                            JSONObject jsonObj = new JSONObject();
                            try {
                                jsonObj.put("MI_METERINSTALLID", installByContModel.getMI_METERINSTALLID());
                                jsonObj.put("MI_ACTION", installByContModel.getMI_ACTION());
                                jsonObj.put("MI_CONSUMER", installByContModel.getMI_CONSUMER());
                                jsonObj.put("MI_BU", installByContModel.getMI_BU());
                                jsonObj.put("MI_PC", installByContModel.getMI_PC());
                                jsonObj.put("MI_REFNO", installByContModel.getMI_REFNO());
                                jsonObj.put("MI_O_SIZE", installByContModel.getMI_O_SIZE());
                                jsonObj.put("MI_O_METER", installByContModel.getMI_O_METER());
                                jsonObj.put("MI_O_MAKE", installByContModel.getMI_O_MAKE());
                                jsonObj.put("MI_O_PREVIOUSREADING", installByContModel.getMI_O_PREVIOUSREADING());
                                jsonObj.put("MI_O_FINALREADING", installByContModel.getMI_O_FINALREADING());
                                jsonObj.put("MI_O_FINALSTATUS", installByContModel.getMI_O_FINALSTATUS());
                                jsonObj.put("MI_O_REASON", installByContModel.getMI_O_REASON());
                                jsonObj.put("MI_O_METERTYPE", installByContModel.getMI_O_METERTYPE());
                                jsonObj.put("MI_N_MAKE", installByContModel.getMI_N_MAKE());
                                jsonObj.put("MI_N_SIZE", installByContModel.getMI_N_SIZE());
                                jsonObj.put("MI_N_SEAL", installByContModel.getMI_N_SEAL());
                                jsonObj.put("MI_N_METER", installByContModel.getMI_N_METER());
                                jsonObj.put("MI_INSTALLATIONDATE", installByContModel.getMI_INSTALLATIONDATE());
                                jsonObj.put("MI_N_INITIALREADING", installByContModel.getMI_N_INITIALREADING());
                                jsonObj.put("MI_N_METERTYPE", installByContModel.getMI_N_METERTYPE());
                                jsonObj.put("MI_N_METERLOCATION", installByContModel.getMI_N_METERLOCATION());
                                jsonObj.put("MI_N_ISPROTECTED", installByContModel.getMI_N_ISPROTECTED());
                                jsonObj.put("MI_PROPERTYTAXNO", installByContModel.getMI_PROPERTYTAXNO());
                                jsonObj.put("MI_N_ISMETERHANDOVER", installByContModel.getMI_N_ISMETERHANDOVER());
                                jsonObj.put("MI_CONTRACTOR", installByContModel.getMI_CONTRACTOR());
                                jsonObj.put("MI_CONTRACTOREMP", installByContModel.getMI_CONTRACTOREMP());
                                jsonObj.put("MI_N_ISMATERIALHANDOVER", installByContModel.getMI_N_ISMATERIALHANDOVER());
                                jsonObj.put("MI_PCCBEDDINGLEN", installByContModel.getMI_PCCBEDDINGLEN());
                                jsonObj.put("MI_PCCBEDDINGWID", installByContModel.getMI_PCCBEDDINGWID());
                                jsonObj.put("MI_PCCBEDDINGDEP", installByContModel.getMI_PCCBEDDINGDEP());
                                jsonObj.put("MI_ROADCUTTINGTYPE", installByContModel.getMI_ROADCUTTINGTYPE());
                                jsonObj.put("MI_ROADCUTTINGLEN", installByContModel.getMI_ROADCUTTINGLEN());
                                jsonObj.put("MI_ROADCUTTINGWID", installByContModel.getMI_ROADCUTTINGWID());
                                jsonObj.put("MI_ROADCUTTINGDEP", installByContModel.getMI_ROADCUTTINGDEP());
                                jsonObj.put("MI_FROMNODE", installByContModel.getMI_FROMNODE());
                                jsonObj.put("MI_TONODE", installByContModel.getMI_TONODE());
                                jsonObj.put("MI_REGMOBILE", installByContModel.getMI_REGMOBILE());
                                jsonObj.put("MI_ALTMOBILE", installByContModel.getMI_ALTMOBILE());
                                jsonObj.put("MI_GIS", installByContModel.getMI_GIS());
                                jsonObj.put("MI_DMA", installByContModel.getMI_DMA());
                                jsonObj.put("MI_SR", installByContModel.getMI_SR());
                                jsonObj.put("MI_MODIFIEDBY", installByContModel.getMI_MODIFIEDBY());
                                jsonObj.put("MI_MODIFIEDDATE", installByContModel.getMI_MODIFIEDDATE());
                                jsonObj.put("MI_IP", installByContModel.getMI_IP());
                                jsonObj.put("MI_AUTHENTICATEDATE", installByContModel.getMI_AUTHENTICATEDATE());
                                jsonObj.put("MI_AUTHREJECTBY", installByContModel.getMI_AUTHREJECTBY());
                                jsonObj.put("MI_REJECTEDDATE", installByContModel.getMI_REJECTEDDATE());
                                jsonObj.put("MI_STATUS", installByContModel.getMI_STATUS());
                                jsonObj.put("MI_ISACTIVE", installByContModel.getMI_ISACTIVE());
                                jsonObj.put("MI_XMLMATERIAL", installByContModel.getMI_XMLMATERIAL());
                                jsonObj.put("MI_XMLCIVIL", installByContModel.getMI_XMLCIVIL());
                                jsonObj.put("MI_O_OBSERVATION", installByContModel.getMI_O_OBSERVATION());
                                jsonObj.put("MI_SOURCE", installByContModel.getMI_SOURCE());
                                jsonObj.put("MI_ISCOMMISSIONED", installByContModel.getMI_ISCOMMISSIONED());
                                jsonObj.put("MI_CONTRACTOROTHER", installByContModel.getMI_CONTRACTOROTHER());
                                jsonObj.put("MI_CONTRACTOREMPOTHER", installByContModel.getMI_CONTRACTOREMPOTHER());
                                jsonObj.put("MI_N_DIGIT", installByContModel.getMI_N_DIGIT());
                                jsonObj.put("MSRID", installByContModel.getMSRID());
                                jArray.put(jsonObj);

                                contList = String.valueOf(jArray);

                                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTLIST);
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTLIST, contList);

                                showInstalledMeterDetailsFromContractor();

                            } catch (Exception e) {
                                progress.dismiss();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Exception ", "" + e.getMessage());
                progress.dismiss();
                String error = e.toString();
                ErrorClass.errorData(mCon, "Consumer Details Fragment", "Get MeterInstall By ID", error);
            }
        }

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
    }

    private void showInstalledMeterDetailsFromContractor() {
        //u can convert list to string (contractorList)
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

            if (MI_ISCOMMISSIONED == null || MI_ISCOMMISSIONED.equals("")) {
                //Do nothing
            } else {
                UtilitySharedPreferences.setPrefs(mCon, Constants.COMMISIONED_NONCOMMISIONED, MI_ISCOMMISSIONED);
            }

            if (MI_ACTION == null || MI_ACTION.equals("")) {
                //Do nothing
            } else {
                //  UtilitySharedPreferences.setPrefs(mCon, Constants.RADIOBUTTONVAL, MI_ACTION);
            }

            if (MI_REGMOBILE.equalsIgnoreCase(""))
                mobTextView.setText("");
            else
                mobTextView.setText(MI_REGMOBILE);

            if (MI_ALTMOBILE.equalsIgnoreCase(""))
                alternateNo.setText("");
            else
                alternateNo.setText(MI_ALTMOBILE);

            if (MI_FROMNODE.equalsIgnoreCase(""))
                etFromNode.setText("");
            else
                etFromNode.setText(MI_FROMNODE);

            if (MI_TONODE.equalsIgnoreCase(""))
                etToNode.setText(MI_TONODE);
            else
                etToNode.setText(MI_TONODE);

            if (MI_PROPERTYTAXNO.equalsIgnoreCase(""))
                et_property_assessement.setText(MI_PROPERTYTAXNO);
            else
                et_property_assessement.setText(MI_PROPERTYTAXNO);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void srDropDownSelection() {
        try {
            if (MI_SR != null &&!(MI_SR.equalsIgnoreCase(""))) {
                ArrayList<String> meterSRName = new ArrayList<>();
                //  meterSRName = realmOperations.fetchSRList();
                meterSRName = realmOperations.fetchSRList();

                ArrayList<String> meterSRList = new ArrayList<>();
                meterSRList.add("Select");
                meterSRList.addAll(meterSRName);

                //  model_sr = realmOperations.fetchSRIdByMSRName(MI_SR);
                model_sr = realmOperations.fetchSRIdByMSRName(MSRIDStr);
                String makerType = model_sr.getTRM_NAME();
                int makerId = meterSRList.indexOf(makerType);

                srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterSRList);
                srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                srSpinner.setAdapter(srArrayAdapter);
                srSpinner.setSelection(number);//rupali
               // srSpinner.setOnItemSelectedListener(this);


            } else if (srStr != null) {
                if (!srStr.equalsIgnoreCase("")) {
                    if (srStr.equalsIgnoreCase("Select")) {
                        srStr = "0";
                    }
                    ArrayList<String> meterSRName = new ArrayList<>();
                    meterSRName = realmOperations.fetchSRList();

                    ArrayList<String> meterSRList = new ArrayList<>();
                    meterSRList.add("Select");
                    meterSRList.addAll(meterSRName);

                    if (!srStr.equalsIgnoreCase("0")) {
                        model_sr = realmOperations.fetchSRIdByMSRName(srStr);
                        String makerType = model_sr.getTRM_NAME();
                        makerId = meterSRList.indexOf(makerType);

                        srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterSRList);
                        srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        srSpinner.setAdapter(srArrayAdapter);
                        srSpinner.setSelection(makerId);
                    } else {
                        srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterSRList);
                        srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        srSpinner.setAdapter(srArrayAdapter);
                    }
                } else {
                    srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, srList);
                    srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    srSpinner.setAdapter(srArrayAdapter);
                }
            } else {
                srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, srList);
                srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                srSpinner.setAdapter(srArrayAdapter);
                srSpinner.setSelection(makerId);
            }

        } catch (Exception ex) {
            Log.e("SRSpinner", "" + ex.getMessage());
            srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, srList);
            srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            srSpinner.setAdapter(srArrayAdapter);
        }
    }

    private void dmaDropdownSelection() {
        ArrayList<String> srList1 = new ArrayList<>();
        srList1.add("Select");
        // srList1.addAll(dmaBySr);
        try {
            if (MI_DMA != null || !MI_DMA.isEmpty()) {
                String SRName = srSpinner.getSelectedItem().toString();
                model_sr = realmOperations.fetchSRNameByMSRName(SRName);
                String srID = String.valueOf(model_sr.getTRM_ID());

                model_dma = realmOperations.fetchDMABySRName(srID);
                String meterStatusId = String.valueOf(model_dma.getSRID());

                ArrayList<String> fetchedDMAList = new ArrayList<>();
                fetchedDMAList = realmOperations.fetchDMAList(meterStatusId);

                ArrayList<String> meterSRList = new ArrayList<>();
                meterSRList.add("Select");
                meterSRList.addAll(fetchedDMAList);
                int dmaSpinId;
                String dmaIdtoSend;
                if (!MI_DMA.equalsIgnoreCase("0")) {
                    model_dma = realmOperations.fetchDMABySRId(MI_DMA);
                    String dmaSpinName = model_dma.getPM_NAME();
                    dmaIdtoSend = model_dma.getPM_ID();
                    dmaSpinId = meterSRList.indexOf(dmaSpinName);
                } else {
                    dmaSpinId = 0;
                    dmaIdtoSend = "0";
                }

                dmaArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterSRList);
                dmaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dmaSpinner.setAdapter(dmaArrayAdapter);
                dmaSpinner.setSelection(dmaSpinId);
                dmaId = Integer.parseInt(dmaIdtoSend);
               // dmaSpinner.setOnItemSelectedListener(this);

            } else if (dmaStr != null || !dmaStr.isEmpty()) {
                if (!dmaStr.equalsIgnoreCase("")) {
                    ArrayList<String> meterDMAName = new ArrayList<>();
                    meterDMAName = realmOperations.fetchDMAList();

                    ArrayList<String> meterSRList = new ArrayList<>();
                    meterSRList.add("Select");
                    meterSRList.addAll(meterDMAName);

                    model_dma = realmOperations.fetchDMABySRId(dmaStr);
                    String dmaSpinName = model_dma.getPM_NAME();
                    String dmaIdtoSend = model_dma.getPM_ID();
                    int dmaSpinId = meterSRList.indexOf(dmaSpinName);

                    dmaArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterSRList);
                    dmaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dmaSpinner.setAdapter(dmaArrayAdapter);
                    dmaSpinner.setSelection(dmaSpinId);
                    // dmaSpinner.setSelection(dmaval);
                    dmaId = Integer.parseInt(dmaIdtoSend);
                    //dmaSpinner.setOnItemSelectedListener(this);
                } else {
                    setDMADropdown();
                }
            } else {
                setDMADropdown();
            }
        } catch (Exception ex) {
            Log.d("message", ex.getMessage());

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

        String params[] = new String[59];

        params[0] = "N";                            //IsSubmit
        params[1] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);              //Action
        params[2] = consumerNoStr;                 //Consumer
        params[3] = BU;
        params[4] = PC;
        params[5] = refNoStr;
        if(MI_O_SIZE==null){
            params[6] = null;
        }else{
            params[6] = MI_O_SIZE;
        }

        if(MI_O_SIZE==null){
            params[7] = null;
        }else{
            params[7] = MI_O_METER;
        }

        params[8] = MI_O_MAKE;

        if (MI_O_PREVIOUSREADING == null) {
            params[9] = null;
        } else {
            params[9] = MI_O_PREVIOUSREADING;
        }
        if (MI_O_PREVIOUSREADING == null) {
            params[10] = null;
        } else {
            params[10] = MI_O_PREVIOUSREADING;
        }
//            params[10] = finalReadingNumStr;
        if (MI_O_FINALSTATUS == null) {
            params[11] = null;
        } else {
            params[11] = MI_O_FINALSTATUS;
        }

//            params[11] = finalStatusNumStr;             //final Status

        if (MI_O_OBSERVATION == null) {
            params[12] = null;
        } else {
            params[12] = MI_O_OBSERVATION;
        }
//            params[12] = meterObservationIdStr;
        if (MI_O_REASON == null) {
            params[13] = "0";
        } else {
            params[13] = MI_O_REASON;
        }

//            params[13] = reasonId;

        if (MI_O_METERTYPE == null) {
            params[14] = null;
        } else {
            params[14] = MI_O_METERTYPE;
        }
       /* params[9] = MI_O_PREVIOUSREADING;
        params[10] = MI_O_FINALREADING;
        params[11] = MI_O_FINALSTATUS;
        params[12] = MI_O_OBSERVATION;
        params[13] = MI_O_REASON;
        params[14] = MI_O_METERTYPE;
       */
       /* params[15] = MI_N_MAKE;
        params[16] = MI_N_SIZE;
        params[17] = MI_N_SEAL;
        params[18] = MI_N_METER;
        params[19] = MI_INSTALLATIONDATE;
        params[20] = MI_N_INITIALREADING;
        params[21] = MI_N_METERTYPE;
        params[22] = MI_N_METERLOCATION;
        params[23] = MI_N_ISPROTECTED;
        params[24] = property_assessmnt;
        params[25] = MI_N_ISMETERHANDOVER;*/
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
        params[19] = MI_INSTALLATIONDATE;// InstallationDate

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
        if (property_assessmnt == null) {
            params[24] = null;
        } else {
            params[24] = property_assessmnt;
        }

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


       /* params[40] = fromNodeStr;
        params[41] = toNodeStr;
        params[42] = primaryMobStr;
        params[43] = alternateMobStr;
        params[44] = gis_bidStr;
        params[45] = dmaStr;
        params[46] = SRIDStr;
*/
        if (fromNodeStr == null) {
            params[40] = null;                   // N_MeterType
        } else {
            params[40] = fromNodeStr;
        }

//            params[40] = MI_FROMNODE;
        if (toNodeStr == null) {
            params[41] = null;                   // N_MeterType
        } else {
            params[41] = toNodeStr;
        }

//            params[41] = MI_TONODE;
        params[42] = primaryMobStr;
//            params[43] = MI_ALTMOBILE;
        if (alternateMobStr == null) {
            params[43] = null;                   // N_MeterType
        } else {
            params[43] = alternateMobStr;
        }
        if (gis_bidStr == null) {
            params[44] = null;                   // N_MeterType
        } else {
            params[44] = gis_bidStr;
        }
//            params[44] = MI_GIS;

        if (dmaStr == null) {
            params[45] = null;                   // N_MeterType
        } else {
            params[45] = dmaStr;
        }
//            params[45] = MI_DMA;

        if (SRIDStr == null) {
            params[46] = null;                   // N_MeterType
        } else {
            params[46] = SRIDStr;
        }

        params[47] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);
        params[48] = MI_N_DIGIT;
        params[49] = emp_code;

        params[50] = ip_str;
        if (MI_METERINSTALLID == null) {
            params[51] = "0";                   // N_MeterType
        } else {
            params[51] = MI_METERINSTALLID;
        }

//        params[51] = MI_METERINSTALLID; // Query String
        if (MI_N_SEAL_MAKE == null) {
            params[52] = "0";                   // N_MeterType
        } else {
            params[52] =MI_N_SEAL_MAKE;
        }

//        params[52] = NewMeterDetFragment.sealmakeId; //pinky changed this to sealMakeIdStr 21/01/2022
        if (MI_N_METER_BOX_MAKE == null) {
            params[53] = "0";                   // N_MeterType
        } else {
            params[53] = MI_N_METER_BOX_MAKE;
        }

     /*   params[51] = MI_METERINSTALLID;
        params[52] = MI_N_SEAL_MAKE;
        params[53] = MI_N_METER_BOX_MAKE;*/
        params[54] = "CF";
        String metrown = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_N_METEROWNERSHIP);
        params[55] = metrown;//Rupali
        params[56] = "OK";
        params[57] = averageEstimateConsum;
        params[58] = tv_noofFlat.getText().toString();

        //Query String
        //Log.e("consumerParams",Arrays.toString(params));

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
               // Log.e("ConsumerSaveResponse", jsonMeterInstallSaveResponse);
               // Log.e("ConsumerSaveParams", Arrays.toString(params));

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
                    MessageWindow.throwOutMMMFragment(mCon, "Consumer Details Saved", "Success", MainActivity.class);

                    clearAllSharedPrefs();
                  /*  AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Success");
                    alertBuilder.setMessage("Details Saved");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            App.backPressMMGFragment="Y";

//                            MainActivity m = new MainActivity();
//                            m.setBackFrag();
                            Intent i = new Intent(mCon, MainActivity.class);
                            i.putExtra("Tag", "2");
                            startActivity(i);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();*/

                } else if (enums[0].equalsIgnoreCase("Failure")) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Failure");
                    alertBuilder.setMessage("Meter Installation Save Not Completed");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else if (enums[0].equalsIgnoreCase("Duplicate")) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Alert");
                    alertBuilder.setMessage(getResources().getString(R.string.please_forward_complaint_to_mmg));
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(mCon, MainActivity.class);
//                            startActivity(intent);
                            MeterManagementSystemFragment nextFrag = new MeterManagementSystemFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                            dialog.cancel();

                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
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
        super.setUserVisibleHint(isFragmentVisible_);
        if (isFragmentVisible_) {
            new ConsumerDetFragmentNew();
            consumerDetailVisible = true;

        } else {
            consumerDetailVisible = false;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCon = null;
    }


}