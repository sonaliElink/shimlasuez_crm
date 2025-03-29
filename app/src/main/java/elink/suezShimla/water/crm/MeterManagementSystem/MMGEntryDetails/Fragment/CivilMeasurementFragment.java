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
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCvlMeasurementResponseModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGScreenActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Adapter.CivilMeasurementDetailsAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGCustDetModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMaterialResponseModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;


public class CivilMeasurementFragment extends Fragment {
    Context  mCon;
    RecyclerView recyclerView;
    private RealmOperations realmOperations;
    MaterialDialog cvldetailprogress;
    private ConnectionDetector connection;
    private CivilMeasurementDetailsAdapter civilMeasurementDetailsAdapter;
    private Invoke invServices;
    private Gson gson;
    List<MMGCvlMeasurementResponseModel> cvlMeasurementList= new ArrayList<MMGCvlMeasurementResponseModel>();
    List<MMGCvlMeasurementResponseModel> civilList = new ArrayList<MMGCvlMeasurementResponseModel>();
    SendCvlMeasurementDetails sendCvlMeasurementDetails;
    //ArrayList<MMGCvlMeasurementResponseModel> mmgCvlMeasurementResponseModels=new ArrayList<>();
    String pagename="", contList="", consumerNoStr, refNoStr, BU, PC;
    MaterialButton submitButton;
    Button saveAndExit;
    MaterialDialog progress;
    static String newCivilXml;
    String submitButtonTag="", radioCommNonCommStr="", radiobuttonValStr="";
    String MI_METERINSTALLID, MI_ACTION, MI_CONSUMER, MI_BU, MI_PC, MI_REFNO, MI_O_SIZE="", MI_O_METER=""
            ,MI_O_MAKE="", MI_O_PREVIOUSREADING="", MI_O_FINALREADING="",MI_O_FINALSTATUS="", MI_O_REASON="", MI_O_METERTYPE="",
            MI_N_MAKE="", MI_N_SIZE="", MI_N_SEAL="",MI_N_METER="", MI_INSTALLATIONDATE="", MI_N_INITIALREADING=""
            ,MI_N_METERTYPE="", MI_N_METERLOCATION="", MI_N_ISPROTECTED="", MI_PROPERTYTAXNO, MI_N_ISMETERHANDOVER="",
            MI_CONTRACTOR, MI_CONTRACTOREMP, MI_N_ISMATERIALHANDOVER, MI_PCCBEDDINGLEN, MI_PCCBEDDINGWID,
            MI_PCCBEDDINGDEP, MI_ROADCUTTINGTYPE, MI_ROADCUTTINGLEN, MI_ROADCUTTINGWID, MI_ROADCUTTINGDEP, MI_FROMNODE="",
            MI_TONODE="", MI_REGMOBILE="", MI_ALTMOBILE="", MI_GIS="", MI_DMA="", MI_SR="", MI_MODIFIEDBY, MI_MODIFIEDDATE, MI_IP, MI_AUTHENTICATEDATE,
            MI_AUTHREJECTBY, MI_REJECTEDDATE, MI_STATUS, MI_ISACTIVE, MI_XMLMATERIAL="", MI_XMLCIVIL="", MI_O_OBSERVATION="",MI_N_SEAL_MAKE="", MI_N_METER_BOX_MAKE="",
            MI_SOURCE, MI_ISCOMMISSIONED, MI_CONTRACTOROTHER, MI_CONTRACTOREMPOTHER, MI_N_DIGIT="", MSRID, dataFound, jsonMeterInstallSaveResponse;
    String sl_no="", mcd_mat_id="", mcd_mat_name="", mcd_dropdown="", mcd_is_quantity="", mcd_ddlid="",
            mcd_quantity="", mcd_length="", mcd_width="", mcd_depth="", civil="";

    boolean isPageVisible = false;
    String materialIdStr ="",materialNameStr="",isDropdownStr="",isQuantityStr="",ddldStr="",quantityStr="",lengthStr="",widthStr="",depthStr="";
    private static String zone_str="", group_str="", to_node_str="",
            from_node_str, primary_mob_str, alternate_mob_str, gis_bid_str, dma_id, sr_id, property_assess,
            mtrSizeId, old_maker_code_id, serialNoStr,previousReading,finalReadingValStr, finalStatusIdStr, meterObservationId,
            meterReasonId, makerCodeIdStr, meterNumStr, installDtStr, sealNoStr, meterHandoverIdStr, protectedBoxStr,
            meterLocationStr, meterTypeStr, sizeStr, initialReadingStr, installDateStr, dial_digit, meterSizeIdStr,sealNumStr,
            initialReadingNoStr, meterTypeIdStr, meterLocationIdNo, protectedBoxIdNoStr, meterHandoverStr,
            taxNumStr, materialHandoverStr, c_employee_id,contractorId, cntrctrNameIdStr = "", otherContractorStr="", otherContractorEmpStr="";
    private boolean isConsumerSubmitted = false, isOldSubmitted = false,is_newmeter_submitted = false,
            is_contractor_submitted = false, is_material_submitted = false, is_civil_submitted = false ;

    StringBuilder materialDetailXml = new StringBuilder();
    static StringBuilder cvlMeasurementXml = new StringBuilder();

    ArrayList<MMGCustDetModel> customerDetailList = new ArrayList<>();
    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = new ArrayList<>();
    ArrayList<InstallDetails> installDetails = new ArrayList<>();
    ArrayList<MMGValidateDetails> validateDetailList = new ArrayList<>();
    String STARTTIME = "", ALERTSTARTTIME = "",deviceAuthorization = "", appIsLogged = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";
    public CivilMeasurementFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
      //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        MMGScreenActivity activity = (MMGScreenActivity) getActivity();
        customerDetailList = activity.getMMGCustomerDetailsData();
        meterConnectionList = activity.getMMGMeterConnectionDetailsData();
        installDetails = activity.getMMGInstallDetailsData();
        validateDetailList = activity.getMmgvalidateDetailList();

        View view = inflater.inflate(R.layout.fragment_civil_measurement, container, false);
        mCon=getActivity();
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);
        init(view);
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
    public void init(View view) {
        recyclerView= view.findViewById(R.id.recyclerView);
        submitButton= view.findViewById(R.id.submitButton);
        saveAndExit= view.findViewById(R.id.saveAndExit);
        radioCommNonCommStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);
        radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);
      //  civil = UtilitySharedPreferences.getPrefs(getActivity(), Constants.SUBMITCIVILLIST);

        pagename = getArguments().getString("pagename");
        consumerNoStr = getArguments().getString("consumerNoStr");
        refNoStr = getArguments().getString("refNoStr");

        BU = UtilitySharedPreferences.getPrefs(mCon, Constants.BU);
        PC = UtilitySharedPreferences.getPrefs(mCon, Constants.PC);
        radioCommNonCommStr = UtilitySharedPreferences.getPrefs(mCon, Constants.COMMISIONED_NONCOMMISIONED);

        cvlMeasurementList.clear();
        if(pagename != null){//MeterInstallationEntry
            if(pagename.equalsIgnoreCase("MeterInstallationContractorDet")){
                listAfterDownload();
            } else {
                cvlMeasurementList = realmOperations.fetchCvlMesurementDetails();
                civilMeasurementDetailsAdapter = new CivilMeasurementDetailsAdapter(getActivity(), cvlMeasurementList);
                recyclerView.setAdapter(civilMeasurementDetailsAdapter);
                recyclerView.invalidate();
                civilMeasurementDetailsAdapter.notifyDataSetChanged();
            }
        }else {
            cvlMeasurementList = realmOperations.fetchCvlMesurementDetails();
            civilMeasurementDetailsAdapter = new CivilMeasurementDetailsAdapter(getActivity(), cvlMeasurementList);
            recyclerView.setAdapter(civilMeasurementDetailsAdapter);
            recyclerView.invalidate();
            civilMeasurementDetailsAdapter.notifyDataSetChanged();
        }

        fetchInstallDetails();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButtonTag = CivilMeasurementDetailsAdapter.submitCivilmeasurementTag;

                    if(radiobuttonValStr.equalsIgnoreCase("R")) {
                        if(isPageVisible) {
                            MessageWindow.messageAuthenticationWindow(getContext(), "You can directly go to upload documents","Alert");
                        }
                    }

                    if (cvlMeasurementList.size() != 0) {
                        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CIVILMEASUREMENTXML);
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.CIVILMEASUREMENTXML, cvlMeasurementList.toString());

                        displayCvlMeasurementDet(cvlMeasurementList);

                        is_civil_submitted = true;
                        sendCvlMeasurementDetails.sndCvlMeasurementDet(cvlMeasurementList, is_civil_submitted);

                        if(!radiobuttonValStr.equalsIgnoreCase("R")) {

                            ((MMGScreenActivity)getActivity()).onClickNext (5);

                            MMGScreenActivity.animationOnArrow();
                        }
//                            MessageWindow.msgWindow(getContext(), "Details Submitted Succesfully");

                    }
            }
        });

        saveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               saveDataToServer();
            }
        });
    }

    private void fetchInstallDetails() {
        if (installDetails != null) {

            for (int k = 0; k <= installDetails.size() - 1; k++) {

                dataFound = installDetails.get(k).getNODATAFOUND();
                if (dataFound.equalsIgnoreCase("DataFound")) {

                    MI_METERINSTALLID = installDetails.get(k).getMI_METERINSTALLID();
                    MI_XMLCIVIL = installDetails.get(k).getMI_XMLCIVIL();
                    Log.d("civilDetails", ""+MI_XMLCIVIL);
                    if (MI_XMLCIVIL != null && !(MI_XMLCIVIL.equalsIgnoreCase(""))){
                        civilSetter();
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

    public interface SendCvlMeasurementDetails {
        void sndCvlMeasurementDet(List cvlMeasurementList, boolean is_civil_submitted);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sendCvlMeasurementDetails = (SendCvlMeasurementDetails) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    public void displayCvlMeasurementDet(List cvlMeasurementList) {

        if (cvlMeasurementList == null) {
            cvlMeasurementList = Collections.<String>emptyList();
            Log.e("cvlMeasurementList", String.valueOf(cvlMeasurementList));


        } else {
            Log.e("cvlMeasurementList", cvlMeasurementList.toString());
            //cvlMeasurementXml=new StringBuilder();
            //cvlMeasurementXml.setLength(0);
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

            newCivilXml = cvlMeasurementXml.toString();
            UtilitySharedPreferences.setPrefs(getActivity(),consumerNoStr+"civil", newCivilXml);
        }
    }

    private void listAfterDownload(){
        if(contList == null || contList.equalsIgnoreCase("")){
            contList = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTLIST);
        }else {
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
            }

            //civilSetter();

        }catch (Exception e){
            Log.e("Exp MtrlStr", ""+e.getMessage());
        }
    }

    private void civilSetter() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(MI_XMLCIVIL)));

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("Details");

            cvlMeasurementList.clear();
            for (int i=0; i<nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    //sl_no = getValue("SLNO", element2);
                    mcd_mat_id = getValue("MCD_MATERIAL_ID", element2);
                    mcd_mat_name = getValue("MCD_MATERIAL_NAME", element2);
                    mcd_dropdown = getValue("MCD_ISDROPDOWN", element2);
                    mcd_is_quantity = getValue("MCD_ISQUANTITY", element2);
                    mcd_ddlid = getValue("DDLID", element2);
//                    mcd_quantity = getValue("QUANTITY", element2);
                    mcd_length = getValue("LENGTH", element2);
                    mcd_width = getValue("WIDTH", element2);
                    mcd_depth = getValue("DEPTH", element2);
                }

                MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel= new MMGCvlMeasurementResponseModel(sl_no, mcd_mat_id, mcd_mat_name, mcd_dropdown,
                        mcd_is_quantity, mcd_ddlid, mcd_quantity, mcd_length, mcd_width, mcd_depth);
                cvlMeasurementList.add(mmgCvlMeasurementResponseModel);

            }
            if (getActivity()!=null) {
                civilMeasurementDetailsAdapter = new CivilMeasurementDetailsAdapter(getActivity(), cvlMeasurementList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(civilMeasurementDetailsAdapter);
                civilMeasurementDetailsAdapter.notifyDataSetChanged();
            }
        }catch (Exception e) {
            Log.e("Civil", ""+e.getMessage());
         //   MessageWindow.errorWindow(getContext(), "Cannot download incorrect XML");
        }
    }

    private void disableFields(){
        if(radiobuttonValStr.equalsIgnoreCase("R"))
            MessageWindow.messageAuthenticationWindow(mCon, "You can direclty go to upload document","Alert");

    }

    private static String getValue(String tag, Element element) {

        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        if(node == null){
         return "0";
        }
        return node.getNodeValue();
    }

    private void saveDataToServer(){
        String mtrTypeCode = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_TYPE_CODE_ID);
        String oldmeterSizeNum = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_SIZE_ID);
        displayCvlMeasurementDet(cvlMeasurementList);
        String emp_code = null;
        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ip_str = PreferenceUtil.getMac();

        String params[] = new String[59];
        params[0] = "N";                            //IsSubmit
        if(isConsumerSubmitted) {
            params[1] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);             //Action
            params[2] = consumerNoStr;                 //Consumer
        } else {
            params[1] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);             //Action
            params[2] = consumerNoStr;                 //Consumer
        }

        params[3] = ConsumerDetFragmentNew.BU;
        params[4] = ConsumerDetFragmentNew.PC;
        params[5] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMERREFERENCENUMBER);

        if(isOldSubmitted) {

            params[6] = oldmeterSizeNum;
            params[7] = serialNoStr;
            params[8] = old_maker_code_id;
            params[9] = previousReading;
            params[10] = finalReadingValStr;
            params[11] = finalStatusIdStr;
            params[12] = meterObservationId;
            params[13] = meterReasonId;
            params[14] = mtrTypeCode;
        }else {
            params[6] =  MI_O_SIZE;
            params[7] =  MI_O_METER;
            params[8] =  MI_O_MAKE;
            params[9] =  MI_O_PREVIOUSREADING;
            params[10] = MI_O_FINALREADING;
            params[11] = MI_O_FINALSTATUS;
            params[12] = MI_O_OBSERVATION;
            params[13] = MI_O_REASON;
            params[14] = MI_O_METERTYPE;
        }

        if(radiobuttonValStr.equals("OH")){
            params[15] = "";
            params[16] = "";                     // N_Size
            params[17] = "";                    // N_Seal
            params[18] = "";                    // N_Meter
            if(installDtStr != null)
                params[19] = installDtStr;          // InstallationDate
            else
                params[19] = MI_INSTALLATIONDATE;
            params[20] = "";                    // N_InitialReading
            params[21] = "";                    // N_MeterType
            params[22] = "";                    // N_MeterLocation
            params[23] = "";                    // N_IsProtected
            params[25] = "";                   // N_IsMeterHandovertoConsumer
        }else {
            if(is_newmeter_submitted) {
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
            }else {
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
        if(isConsumerSubmitted)
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
//            if(MI_CONTRACTOR == null || MI_CONTRACTOR.equalsIgnoreCase("") || MI_CONTRACTOR.equalsIgnoreCase("0")){
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

        if(is_material_submitted) {
            params[31] = MaterialDetFragment.materialDetailXml.toString();
        }else {
            params[31] = MaterialDetFragment.materialDetailXml.toString();
        }

        params[32] = cvlMeasurementXml.toString() ;

        params[33] = "";
        params[34] = "";
        params[35] = "";
        params[36] = "";
        params[37] = "";
        params[38] = "";
        params[39] = "";

        if(isConsumerSubmitted) {
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

        if(is_newmeter_submitted)
            params[48] = NewMeterDetFragment.dial_digit;                    //N_DIGIT
        else
            params[48] = NewMeterDetFragment.dial_digit;

        params[49] = emp_code;
        params[50] = ip_str;
        params[51] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_METERINSTALLID);                    // Query String
        params[52] = NewMeterDetFragment.sealmakeId;
        params[53] = NewMeterDetFragment.meterboxmakeId;
        params[54] = "CVF";
        String metrown =  UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_N_METEROWNERSHIP);
        params[55] = metrown;//Rupali
        params[56] =  "OK";
        params[57] =  UtilitySharedPreferences.getPrefs(getActivity(), Constants.NEW_AVERAGECONSUMTION);
        params[58] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.No_ofFlat);

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
                    MessageWindow.throwOutFromWindow(mCon, "Civil Measurement Details Saved Successfully", "Success", MainActivity.class);
                    clearAllSharedPrefs();
                    return;
                }else if(enums[0].equalsIgnoreCase("Failure")){
                    MessageWindow.throwOutFromWindow(mCon, "Details Not Saved", "Alert", MainActivity.class);

                } else if(enums[0].equalsIgnoreCase("Duplicate")){
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
                                    String consumerNoStr,String zoneStr,String groupStr,String refTypeStr,String connSizeStr,
                                    String property_assessmnt, String fromNodeStr,String toNodeStr, String primaryMobStr, String alternateMobStr,
                                    String gis_bidStr, String dmaId,String srId, String msrId, String commisioned_noncommisioned,
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
    }

    public void displayOldMeterDetails(String oldmakerCodeId, String oldmeterNoStr, String oldinstallDtStr, String oldmeterSizeStr,
                                       String oldsealNoStr, String pastReadingStr, String oldMtrStsId, String oldmeterTypeId,String finalReadingStr,
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
            String materialNameR=mmgMaterialResponseModel.getMRM_MATERIAL_NAME();
            String materialName = materialNameR.replace("&deg;","\u00B0");

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
            isPageVisible = true;
            if (isFragmentVisible_) {
                new CivilMeasurementFragment();
                disableFields();

                if(pagename != null) {
                    if (pagename.equalsIgnoreCase("MeterInstallationContractorDet"))
                        listAfterDownload();
                }
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
