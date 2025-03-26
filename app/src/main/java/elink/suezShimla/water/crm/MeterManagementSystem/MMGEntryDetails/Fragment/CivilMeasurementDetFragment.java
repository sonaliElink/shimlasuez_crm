package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCgRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGFcRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRampAndRRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSaddleAndPitExcavModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGWallBoringModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGScreenActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGCustDetModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.constant.AppConstant;

public class CivilMeasurementDetFragment extends Fragment implements View.OnClickListener {
    Context mCon;
    LinearLayout liner_saddlelen,liner_saddlewidth,liner_saddledepth,liner_saddleTotal,liner_pePipelen,liner_pePipewidth,liner_pePipedepth,liner_pePipeTotal,liner_lShapeQty,
            liner_lShapeLen,liner_lShapeWidth,liner_lShapeDepth,liner_lShapeTotal,liner_concrtDrnLen,liner_concrtDrnWidth,liner_concrtDrnDepth,liner_concrtDrnTotal,liner_ramprrQty,
            liner_ramprrLen,liner_ramprrWidth,liner_ramprrDepth,liner_ramprrTotal,liner_ssmQty,liner_ssmLen,liner_ssmWidth,liner_ssmDepth,liner_ssmTotal,liner_cgRestroLen,
            liner_cgRestroQty,liner_cgRestroWidth,liner_cgRestroDepth,liner_cgRestroTotal,liner_wBoringQty,liner_wBoringLen,liner_wBoringWidth,liner_wBoringDepth,liner_wBoringTotal,
            liner_cgQty,liner_cgLenth,liner_cgWidth,liner_cgDepth,liner_cgTotal,liner_fcLen,liner_fcWidth,liner_fcDepth,liner_fcTotal,liner_drBurntQty,liner_drBurntLen,
            liner_drBurntWidth,liner_drBurntDepth,liner_drBurntTotal,liner_plasterQty,liner_plasterLen,liner_plasterWidth,liner_plasterDepth,liner_plasterTotal,
            liner_ccmrtrQty,liner_ccmrtrLen,liner_ccmrtrWidth,liner_ccmrtrDepth,liner_ccmrtrTotal,liner_othercvlQty,liner_othercvlLen,liner_othercvlWidth,liner_othercvlDepth,
            liner_othercvlTotal,liner_fcQty;

    private RealmOperations realmOperations;
    private ArrayAdapter saddleAndPitAdapter, pepipeAdapter, rampRRAdapter, wallBoringAdapter, cgRestroAdapter, fcRestroAdapter;
    private MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel;
    private MMGRampAndRRModel mmgRampAndRRModel;
    private MMGWallBoringModel mmgWallBoringModel;
    private MMGCgRestroModel mmgCgRestroModel;
    private MMGFcRestroModel mmgFcRestroModel;
    SendCivilMeasurementDetails sendCivilMeasurementDetails;

    EditText saddleLenEdittxt, saddleWidtEdittext, saddleDepthEdiitext, saddleTotalEdittext, pepipeLenEdittext, pepipeWidthEdittext, pepipeDepthEdittext,
            pepipeTotalEdittext, lshapeLenEdittext, lshapeWidthEdittext, lshapeDepthEdittext, lshapeTotalEdittext, concreteLenEdittext, concreteWidthEdittext,
            concreteDepthEdittext, concreteTotalEdittext, rampRRQtyEdittext, rampRRLenEdittext, rampRRWidthEdittext, rampRRDepthEdittext, rampRRTotalEdittext,
            ssmQtyEdittext, ssmLenEdittext, ssmWidthEdittext, ssmDepthEdittext, ssmTotalEdittext,
            ccgRestroQtyEditttext, ccgRestroLenEdittext, ccgRestroWidthEdittext, ccgRestroDepthEdittext, ccgRestroTotalEdittext, wBoringQtyEdittext, wBoringLenEdittext, wBoringWidthEdittext,
            wBoringDepthEdittext, wBoringTotalEdittext, cgQtyEdittext, cgLenEdittext, cgWidthEdittext, cgDepthEdittext, cgTotalEdittext, fcQtyEdittext, fcLenEdittext, fcWidthEditttext, fcDepthEdittext,
            fcTotalEdittext, drBurntQtyEdittext, drBurntLenEdittext, drBurntWidthEdittext, drBurntDepthEdittext, drBurntTotalEdittext, plasterQtyEdittext, plasterLenEdittext, plasterWidthEdittext, plasterDepthEdittext, plasterTotalEdittext,
            ccmrtrQtyEdittext, ccmrtrLenEdittext, ccmrtrWidthEdittext, ccmrtrDepthEdittext, ccmrtrTotalEdittext, othercvlQtyEdittext, othercvlLenEdittext,
            othercvlWidthEdittext, othercvlDepthEdittext, othercvlTotalEdittext;
    Spinner saddleSpinner, pepipeSpinner, rampRRSpinner, wBoringSpinner, cgSpinner, fcSpinner;
    String saddleAndPitId = "", saddleAndPitIdStr = "", pepipeId = "", pepipeIdStr = "", rampRRiId = "", rampRRIdStr = "", wallBoringId = "", wallBoringIdStr = "", cgRestroId = "", cgRestroIdStr = "", fcRestroId = "", fcRestroIdStr = "";
    Button submitCivilMeasurementButton;

    String saddlePitExcav = "", pePipeTrnExcav = "", rampRR = "", wBoring = "", cgRestro = "", fcRestorationWithCementMortal = "";
    String saddleLenStr="",saddleWidthStr="",saddleDepthStr="",saddleTotalStr="",pepipeLenStr="",pepipeWidthStr="",pepipeDepthStr="",pepipeTotalStr="",lShapeLenStr="",
           lShapeWidthStr="",lShapeDepthStr="",lShapeTotalStr="",concreteLenStr="",concreteWidthStr="",concreteDepthStr="",concreteTotalStr="",ramprrLenStr="",ramprrWidthStr="",ramprrDepthStr="",
           ramprrTotalStr="",ramprrQtyStr="",ssmQtyStr="",ssmLenStr="",ssmWidthStr="",ssmDepthStr="",ssmTotalStr="",ccgQtyStr="",ccgLenStr="",ccgWidthStr="",ccgDepthStr="",
          ccgTotalStr="",wBoringLenStr="",wBoringWidthStr="",wBoringDepthStr="",wBoringQtyStr="",wBoringTotalStr="",cgQtyStr="",cgLenStr="",cgWidthStr="",cgDepthStr="",cgTotalStr="",
           fcQtyStr="",fcLenStr="",fcWidthStr="",fcDepthStr="",fcTotalStr="",drBurntLenStr="",drBurntWidthStr="",drBurntDepthStr="",drBurntTotalStr="",drBurntQtyStr="",
          plasterLenStr="",plasterWidthStr="",plasterDepthStr="",plasterTotalStr="",plasterQtyStr="",ccmrtrQtyStr="",ccmrtrLenStr="",ccmrtrWidthStr="",ccmrtrDepthStr="",ccmrtrTotalStr="",
         otherCvlQtyStr="",otherCvlLenStr="",otherCvlWidthStr="",otherCvlDepthStr="",otherCvlTotalStr="";

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
      //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        mCon = getActivity();
        realmOperations = new RealmOperations(mCon);

        View view = inflater.inflate(R.layout.fragment_civil_measurement_det, container, false);


        MMGScreenActivity activity = (MMGScreenActivity) getActivity();
        customerDetailList = activity.getMMGCustomerDetailsData();
        meterConnectionList = activity.getMMGMeterConnectionDetailsData();
        installDetails = activity.getMMGInstallDetailsData();
        validateDetailList = activity.getMmgvalidateDetailList();

        Log.println(Log.DEBUG, "CIVILDATA", "" + customerDetailList.toString()+"\n" + meterConnectionList.toString()+"\n" + installDetails.toString()+"\n" + validateDetailList.toString());

        liner_saddlelen = view.findViewById(R.id.liner_saddlelen);
        liner_saddlewidth = view.findViewById(R.id.liner_saddlewidth);
        liner_saddledepth = view.findViewById(R.id.liner_saddledepth);
        liner_saddleTotal = view.findViewById(R.id.liner_saddleTotal);
        liner_pePipelen = view.findViewById(R.id.liner_pePipelen);
        liner_pePipewidth = view.findViewById(R.id.liner_pePipewidth);
        liner_pePipedepth = view.findViewById(R.id.liner_pePipedepth);
        liner_pePipeTotal = view.findViewById(R.id.liner_pePipeTotal);
        liner_lShapeQty = view.findViewById(R.id.liner_lShapeQty);
        liner_lShapeLen = view.findViewById(R.id.liner_lShapeLen);
        liner_lShapeWidth = view.findViewById(R.id.liner_lShapeWidth);
        liner_lShapeDepth = view.findViewById(R.id.liner_lShapeDepth);
        liner_lShapeTotal = view.findViewById(R.id.liner_lShapeTotal);
        liner_concrtDrnLen = view.findViewById(R.id.liner_concrtDrnLen);
        liner_concrtDrnWidth = view.findViewById(R.id.liner_concrtDrnWidth);
        liner_concrtDrnDepth = view.findViewById(R.id.liner_concrtDrnDepth);
        liner_concrtDrnTotal = view.findViewById(R.id.liner_concrtDrnTotal);
        liner_ramprrQty = view.findViewById(R.id.liner_ramprrQty);
        liner_ramprrLen = view.findViewById(R.id.liner_ramprrLen);
        liner_ramprrWidth = view.findViewById(R.id.liner_ramprrWidth);
        liner_ramprrDepth = view.findViewById(R.id.liner_ramprrDepth);
        liner_ramprrTotal = view.findViewById(R.id.liner_ramprrTotal);
        liner_ssmQty = view.findViewById(R.id.liner_ssmQty);
        liner_ssmLen = view.findViewById(R.id.liner_ssmLen);
        liner_ssmWidth = view.findViewById(R.id.liner_ssmWidth);
        liner_ssmDepth = view.findViewById(R.id.liner_ssmDepth);
        liner_ssmTotal = view.findViewById(R.id.liner_ssmTotal);
        liner_cgRestroQty = view.findViewById(R.id.liner_cgRestroQty);
        liner_cgRestroLen = view.findViewById(R.id.liner_cgRestroLen);
        liner_cgRestroWidth = view.findViewById(R.id.liner_cgRestroWidth);
        liner_cgRestroDepth = view.findViewById(R.id.liner_cgRestroDepth);
        liner_cgRestroTotal = view.findViewById(R.id.liner_cgRestroTotal);
        liner_wBoringQty = view.findViewById(R.id.liner_wBoringQty);
        liner_wBoringLen = view.findViewById(R.id.liner_wBoringLen);
        liner_wBoringWidth = view.findViewById(R.id.liner_wBoringWidth);
        liner_wBoringDepth = view.findViewById(R.id.liner_wBoringDepth);
        liner_wBoringTotal = view.findViewById(R.id.liner_wBoringTotal);
        liner_cgQty = view.findViewById(R.id.liner_cgQty);
        liner_cgLenth = view.findViewById(R.id.liner_cgLenth);
        liner_cgWidth = view.findViewById(R.id.liner_cgWidth);
        liner_cgDepth = view.findViewById(R.id.liner_cgDepth);
        liner_cgTotal = view.findViewById(R.id.liner_cgTotal);
        liner_fcLen = view.findViewById(R.id.liner_fcLen);
        liner_fcWidth = view.findViewById(R.id.liner_fcWidth);
        liner_fcDepth = view.findViewById(R.id.liner_fcDepth);
        liner_fcTotal = view.findViewById(R.id.liner_fcTotal);
        liner_fcQty = view.findViewById(R.id.liner_fcQty);
        liner_drBurntQty = view.findViewById(R.id.liner_drBurntQty);
        liner_drBurntLen = view.findViewById(R.id.liner_drBurntLen);
        liner_drBurntWidth = view.findViewById(R.id.liner_drBurntWidth);
        liner_drBurntDepth = view.findViewById(R.id.liner_drBurntDepth);
        liner_drBurntTotal = view.findViewById(R.id.liner_drBurntTotal);
        liner_ccmrtrQty = view.findViewById(R.id.liner_ccmrtrQty);
        liner_ccmrtrLen = view.findViewById(R.id.liner_ccmrtrLen);
        liner_ccmrtrWidth = view.findViewById(R.id.liner_ccmrtrWidth);
        liner_ccmrtrDepth = view.findViewById(R.id.liner_ccmrtrDepth);
        liner_ccmrtrTotal = view.findViewById(R.id.liner_ccmrtrTotal);
        liner_plasterQty = view.findViewById(R.id.liner_plasterQty);
        liner_plasterLen = view.findViewById(R.id.liner_plasterLen);
        liner_plasterWidth = view.findViewById(R.id.liner_plasterWidth);
        liner_plasterDepth = view.findViewById(R.id.liner_plasterDepth);
        liner_othercvlQty = view.findViewById(R.id.liner_othercvlQty);
        liner_plasterTotal = view.findViewById(R.id.liner_plasterTotal);
        liner_othercvlLen = view.findViewById(R.id.liner_othercvlLen);
        liner_othercvlWidth = view.findViewById(R.id.liner_othercvlWidth);
        liner_othercvlDepth = view.findViewById(R.id.liner_othercvlDepth);
        liner_othercvlTotal = view.findViewById(R.id.liner_othercvlTotal);
        saddleLenEdittxt = view.findViewById(R.id.saddleLenEdittxt);
        saddleWidtEdittext = view.findViewById(R.id.saddleWidtEdittext);
        saddleDepthEdiitext = view.findViewById(R.id.saddleDepthEdiitext);
        saddleTotalEdittext = view.findViewById(R.id.saddleTotalEdittext);
        pepipeLenEdittext = view.findViewById(R.id.pepipeLenEdittext);
        pepipeWidthEdittext = view.findViewById(R.id.pepipeWidthEdittext);
        pepipeDepthEdittext = view.findViewById(R.id.pepipeDepthEdittext);
        pepipeTotalEdittext = view.findViewById(R.id.pepipeTotalEdittext);
        lshapeLenEdittext = view.findViewById(R.id.lshapeLenEdittext);
        lshapeWidthEdittext = view.findViewById(R.id.lshapeWidthEdittext);
        lshapeDepthEdittext = view.findViewById(R.id.lshapeDepthEdittext);
        lshapeTotalEdittext = view.findViewById(R.id.lshapeTotalEdittext);
        concreteLenEdittext = view.findViewById(R.id.concreteLenEdittext);
        concreteWidthEdittext = view.findViewById(R.id.concreteWidthEdittext);
        concreteDepthEdittext = view.findViewById(R.id.concreteDepthEdittext);
        concreteTotalEdittext = view.findViewById(R.id.concreteTotalEdittext);
        rampRRQtyEdittext = view.findViewById(R.id.rampRRQtyEdittext);
        rampRRLenEdittext = view.findViewById(R.id.rampRRLenEdittext);
        rampRRWidthEdittext = view.findViewById(R.id.rampRRWidthEdittext);
        rampRRDepthEdittext = view.findViewById(R.id.rampRRDepthEdittext);
        rampRRTotalEdittext = view.findViewById(R.id.rampRRTotalEdittext);
        ssmQtyEdittext = view.findViewById(R.id.ssmQtyEdittext);
        ssmLenEdittext = view.findViewById(R.id.ssmLenEdittext);
        ssmWidthEdittext = view.findViewById(R.id.ssmWidthEdittext);
        ssmDepthEdittext = view.findViewById(R.id.ssmDepthEdittext);
        ssmTotalEdittext = view.findViewById(R.id.ssmTotalEdittext);
        ccgRestroQtyEditttext = view.findViewById(R.id.ccgRestroQtyEditttext);
        ccgRestroLenEdittext = view.findViewById(R.id.ccgRestroLenEdittext);
        ccgRestroWidthEdittext = view.findViewById(R.id.ccgRestroWidthEdittext);
        ccgRestroDepthEdittext = view.findViewById(R.id.ccgRestroDepthEdittext);
        ccgRestroTotalEdittext = view.findViewById(R.id.ccgRestroTotalEdittext);
        wBoringQtyEdittext = view.findViewById(R.id.wBoringQtyEdittext);
        wBoringLenEdittext = view.findViewById(R.id.wBoringLenEdittext);
        wBoringWidthEdittext = view.findViewById(R.id.wBoringWidthEdittext);
        wBoringDepthEdittext = view.findViewById(R.id.wBoringDepthEdittext);
        wBoringTotalEdittext = view.findViewById(R.id.wBoringTotalEdittext);
        cgQtyEdittext = view.findViewById(R.id.cgQtyEdittext);
        cgLenEdittext = view.findViewById(R.id.cgLenEdittext);
        cgWidthEdittext = view.findViewById(R.id.cgWidthEdittext);
        cgDepthEdittext = view.findViewById(R.id.cgDepthEdittext);
        cgTotalEdittext = view.findViewById(R.id.cgTotalEdittext);
        fcQtyEdittext = view.findViewById(R.id.fcQtyEdittext);
        fcLenEdittext = view.findViewById(R.id.fcLenEdittext);
        fcWidthEditttext = view.findViewById(R.id.fcWidthEditttext);
        fcDepthEdittext = view.findViewById(R.id.fcDepthEdittext);
        fcTotalEdittext = view.findViewById(R.id.fcTotalEdittext);
        drBurntQtyEdittext = view.findViewById(R.id.drBurntQtyEdittext);
        drBurntLenEdittext = view.findViewById(R.id.drBurntLenEdittext);
        drBurntWidthEdittext = view.findViewById(R.id.drBurntWidthEdittext);
        drBurntDepthEdittext = view.findViewById(R.id.drBurntDepthEdittext);
        drBurntTotalEdittext = view.findViewById(R.id.drBurntTotalEdittext);
        plasterQtyEdittext = view.findViewById(R.id.plasterQtyEdittext);
        plasterLenEdittext = view.findViewById(R.id.plasterLenEdittext);
        plasterWidthEdittext = view.findViewById(R.id.plasterWidthEdittext);
        plasterDepthEdittext = view.findViewById(R.id.plasterDepthEdittext);
        plasterTotalEdittext = view.findViewById(R.id.plasterTotalEdittext);
        ccmrtrQtyEdittext = view.findViewById(R.id.ccmrtrQtyEdittext);
        ccmrtrLenEdittext = view.findViewById(R.id.ccmrtrLenEdittext);
        ccmrtrWidthEdittext = view.findViewById(R.id.ccmrtrWidthEdittext);
        ccmrtrDepthEdittext = view.findViewById(R.id.ccmrtrDepthEdittext);
        ccmrtrTotalEdittext = view.findViewById(R.id.ccmrtrTotalEdittext);
        othercvlQtyEdittext = view.findViewById(R.id.othercvlQtyEdittext);
        othercvlLenEdittext = view.findViewById(R.id.othercvlLenEdittext);
        othercvlWidthEdittext = view.findViewById(R.id.othercvlWidthEdittext);
        othercvlDepthEdittext = view.findViewById(R.id.othercvlDepthEdittext);
        othercvlTotalEdittext = view.findViewById(R.id.othercvlTotalEdittext);
        saddleSpinner = view.findViewById(R.id.saddleSpinner);
        pepipeSpinner = view.findViewById(R.id.pepipeSpinner);
        rampRRSpinner = view.findViewById(R.id.rampRRSpinner);
        wBoringSpinner = view.findViewById(R.id.wBoringSpinner);
        wBoringSpinner = view.findViewById(R.id.wBoringSpinner);
        cgSpinner = view.findViewById(R.id.cgSpinner);
        fcSpinner = view.findViewById(R.id.fcSpinner);
        submitCivilMeasurementButton = view.findViewById(R.id.submitCivilMeasurementButton);
        submitCivilMeasurementButton.setOnClickListener(this);

        ArrayList<String> saddleAndPitName = new ArrayList<>();
        saddleAndPitName = realmOperations.fetchExcavation();
        ArrayList<String> saddleAndPitList = new ArrayList<>();
        saddleAndPitList.add("--Select--");
        saddleAndPitList.addAll(saddleAndPitName);

        saddleAndPitAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, saddleAndPitList);
        //Log.d("check", String.valueOf(zoneList));
        saddleAndPitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        saddleSpinner.setAdapter(saddleAndPitAdapter);
        saddleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String saddleAndPitName = saddleSpinner.getSelectedItem().toString();
                if (saddleAndPitName.equalsIgnoreCase("--Select--")) {
                    //  Toast.makeText(mCon, "Please select meter make", Toast.LENGTH_SHORT).show();
                } else {
                    mmgSaddleAndPitExcavModel = realmOperations.fetchExcavationByName(saddleAndPitName);
                    saddleAndPitId = String.valueOf(mmgSaddleAndPitExcavModel.getEC_ID());
                    Log.e("saddleAndPitId", String.valueOf(saddleAndPitId));
                    saddleAndPitIdStr = String.valueOf(saddleAndPitId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        deviceAuthorization = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEVICEAUTHORIZATION);
        appIsLogged = UtilitySharedPreferences.getPrefs(mCon, AppConstant.APP_ISLOGGED);

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
        if (deviceAuthorization.equalsIgnoreCase("0")) {
            ((MainActivity) mCon).deviceAuthorizedDialog();

        } else {
            if (appIsLogged.equalsIgnoreCase("0")) {
                ((MainActivity) getActivity()).adminLogout();
            }
        }
        ArrayList<String> pepipeName = new ArrayList<>();
        pepipeName = realmOperations.fetchExcavation();
        ArrayList<String> pepipeList = new ArrayList<>();
        pepipeList.add("--Select--");
        pepipeList.addAll(pepipeName);

        pepipeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, pepipeList);
        //Log.d("check", String.valueOf(zoneList));
        pepipeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pepipeSpinner.setAdapter(pepipeAdapter);
        pepipeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pepipeName = saddleSpinner.getSelectedItem().toString();
                if (pepipeName.equalsIgnoreCase("--Select--")) {
                    //  Toast.makeText(mCon, "Please select meter make", Toast.LENGTH_SHORT).show();
                } else {
                    mmgSaddleAndPitExcavModel = realmOperations.fetchExcavationByName(pepipeName);
                    pepipeId = String.valueOf(mmgSaddleAndPitExcavModel.getEC_ID());
                    Log.e("pepipeId", String.valueOf(pepipeId));
                    pepipeIdStr = String.valueOf(pepipeId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayList<String> rampRRName = new ArrayList<>();
        rampRRName = realmOperations.fetchRAMPRR();
        ArrayList<String> rampRRList = new ArrayList<>();
        rampRRList.add("--Select--");
        rampRRList.addAll(rampRRName);

        rampRRAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, rampRRList);
        //Log.d("check", String.valueOf(zoneList));
        rampRRAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rampRRSpinner.setAdapter(rampRRAdapter);
        rampRRSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rampRRName = rampRRSpinner.getSelectedItem().toString();
                if (rampRRName.equalsIgnoreCase("--Select--")) {
                    //  Toast.makeText(mCon, "Please select meter make", Toast.LENGTH_SHORT).show();
                } else {
                    mmgRampAndRRModel = realmOperations.fetchRAMPRRByName(rampRRName);
                    rampRRiId = String.valueOf(mmgRampAndRRModel.getRRR_ID());
                    Log.e("rampRRiId", String.valueOf(rampRRiId));
                    rampRRIdStr = String.valueOf(rampRRiId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> wBoringName = new ArrayList<>();
        wBoringName = realmOperations.fetchWallBoring();
        ArrayList<String> wBoringList = new ArrayList<>();
        wBoringList.add("--Select--");
        wBoringList.addAll(wBoringName);

        wallBoringAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, wBoringList);
        //Log.d("check", String.valueOf(zoneList));
        wallBoringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wBoringSpinner.setAdapter(wallBoringAdapter);
        wBoringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String wallBoringName = wBoringSpinner.getSelectedItem().toString();
                if (wallBoringName.equalsIgnoreCase("--Select--")) {
                    //  Toast.makeText(mCon, "Please select meter make", Toast.LENGTH_SHORT).show();
                } else {
                    mmgWallBoringModel = realmOperations.fetchWallBoringByName(wallBoringName);
                    wallBoringId = String.valueOf(mmgWallBoringModel.getWB_ID());
                    Log.e("wallBoringId", String.valueOf(wallBoringId));
                    wallBoringIdStr = String.valueOf(wallBoringId);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> cgRestroName = new ArrayList<>();
        cgRestroName = realmOperations.fetchCGR();
        ArrayList<String> cgRestroList = new ArrayList<>();
        cgRestroList.add("--Select--");
        cgRestroList.addAll(cgRestroName);

        cgRestroAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, cgRestroList);
        //Log.d("check", String.valueOf(zoneList));
        cgRestroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cgSpinner.setAdapter(cgRestroAdapter);
        cgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cgRestroName = cgSpinner.getSelectedItem().toString();
                if (cgRestroName.equalsIgnoreCase("--Select--")) {
                    //  Toast.makeText(mCon, "Please select meter make", Toast.LENGTH_SHORT).show();
                } else {
                    mmgCgRestroModel = realmOperations.fetchCGRByName(cgRestroName);
                    cgRestroId = String.valueOf(mmgCgRestroModel.getCGR_ID());
                    Log.e("cgRestroId", String.valueOf(cgRestroId));
                    cgRestroIdStr = String.valueOf(cgRestroId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> fcRestroName = new ArrayList<>();
        fcRestroName = realmOperations.fetchFCR();
        ArrayList<String> fcRestroList = new ArrayList<>();
        fcRestroList.add("--Select--");
        fcRestroList.addAll(fcRestroName);

        fcRestroAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, fcRestroList);
        //Log.d("check", String.valueOf(zoneList));
        fcRestroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fcSpinner.setAdapter(fcRestroAdapter);
        fcSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String fcRestroName = fcSpinner.getSelectedItem().toString();
                if(fcRestroName.equalsIgnoreCase("--Select--")) {
                    //  Toast.makeText(mCon, "Please select meter make", Toast.LENGTH_SHORT).show();
                } else {
                    mmgFcRestroModel = realmOperations.fetchFCRByName(fcRestroName);
                    fcRestroId = String.valueOf(mmgFcRestroModel.getFCR_ID());
                    Log.e("fcRestroId", String.valueOf(fcRestroId));
                    fcRestroIdStr = String.valueOf(fcRestroId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submitCivilMeasurementButton: {

                saddlePitExcav = saddleSpinner.getSelectedItem().toString().trim();
                pePipeTrnExcav = pepipeSpinner.getSelectedItem().toString().trim();
                rampRR = rampRRSpinner.getSelectedItem().toString().trim();
                wBoring = wBoringSpinner.getSelectedItem().toString().trim();
                cgRestro = cgSpinner.getSelectedItem().toString().trim();
                fcRestorationWithCementMortal = fcSpinner.getSelectedItem().toString().trim();

                 saddleLenStr=saddleLenEdittxt.getText().toString().trim();
                 saddleWidthStr=saddleWidtEdittext.getText().toString().trim();
                 saddleDepthStr=saddleDepthEdiitext.getText().toString().trim();
                 saddleTotalStr=saddleTotalEdittext.getText().toString().trim();

                pepipeLenStr=pepipeLenEdittext.getText().toString().trim();
                pepipeWidthStr=pepipeWidthEdittext.getText().toString().trim();
                pepipeDepthStr=pepipeDepthEdittext.getText().toString().trim();
                pepipeTotalStr=pepipeTotalEdittext.getText().toString().trim();

                lShapeLenStr=lshapeLenEdittext.getText().toString().trim();
                lShapeWidthStr=lshapeWidthEdittext.getText().toString().trim();
                lShapeDepthStr=lshapeDepthEdittext.getText().toString().trim();
                lShapeTotalStr=lshapeTotalEdittext.getText().toString().trim();


                concreteLenStr=concreteLenEdittext.getText().toString();
                concreteWidthStr=concreteWidthEdittext.getText().toString();
                concreteDepthStr=concreteDepthEdittext.getText().toString();
                concreteTotalStr=concreteTotalEdittext.getText().toString();


                ramprrQtyStr=rampRRQtyEdittext.getText().toString().trim();
                ramprrLenStr=rampRRLenEdittext.getText().toString().trim();
                ramprrWidthStr=rampRRWidthEdittext.getText().toString().trim();
                ramprrDepthStr=rampRRDepthEdittext.getText().toString().trim();
                ramprrTotalStr=rampRRTotalEdittext.getText().toString().trim();

                ssmQtyStr=ssmQtyEdittext.getText().toString().trim();
                ssmLenStr=ssmLenEdittext.getText().toString().trim();
                ssmWidthStr=ssmWidthEdittext.getText().toString().trim();
                ssmDepthStr=ssmDepthEdittext.getText().toString().trim();
                ssmTotalStr=ssmTotalEdittext.getText().toString().trim();

                ccgQtyStr=ccgRestroQtyEditttext.getText().toString().trim();
                ccgLenStr=ccgRestroLenEdittext.getText().toString().trim();
                ccgWidthStr=ccgRestroWidthEdittext.getText().toString().trim();
                ccgDepthStr=ccgRestroDepthEdittext.getText().toString().trim();
                ccgTotalStr=ccgRestroTotalEdittext.getText().toString().trim();

                wBoringQtyStr=wBoringQtyEdittext.getText().toString().trim();
                wBoringLenStr=wBoringLenEdittext.getText().toString().trim();
                wBoringWidthStr=wBoringWidthEdittext.getText().toString().trim();
                wBoringDepthStr=wBoringDepthEdittext.getText().toString().trim();
                wBoringTotalStr=wBoringTotalEdittext.getText().toString().trim();

                cgQtyStr=cgQtyEdittext.getText().toString().trim();
                cgLenStr=cgLenEdittext.getText().toString().trim();
                cgWidthStr=cgWidthEdittext.getText().toString().trim();
                cgDepthStr=cgDepthEdittext.getText().toString().trim();
                cgTotalStr=cgTotalEdittext.getText().toString().trim();

                fcQtyStr=fcQtyEdittext.getText().toString().trim();
                fcLenStr=fcLenEdittext.getText().toString().trim();
                fcWidthStr=fcWidthEditttext.getText().toString().trim();
                fcDepthStr=fcDepthEdittext.getText().toString().trim();
                fcTotalStr=fcTotalEdittext.getText().toString().trim();

                drBurntQtyStr=drBurntQtyEdittext.getText().toString().trim();
                drBurntLenStr=drBurntLenEdittext.getText().toString().trim();
                drBurntWidthStr=drBurntWidthEdittext.getText().toString().trim();
                drBurntDepthStr=drBurntDepthEdittext.getText().toString().trim();
                drBurntTotalStr=drBurntTotalEdittext.getText().toString().trim();

                plasterQtyStr=plasterQtyEdittext.getText().toString().trim();
                plasterLenStr=plasterLenEdittext.getText().toString();
                plasterWidthStr=plasterWidthEdittext.getText().toString().trim();
                plasterDepthStr=plasterDepthEdittext.getText().toString().trim();
                plasterTotalStr=plasterTotalEdittext.getText().toString().trim();

                ccmrtrQtyStr=ccmrtrQtyEdittext.getText().toString().trim();
                ccmrtrLenStr=ccmrtrLenEdittext.getText().toString();
                ccmrtrWidthStr=ccmrtrWidthEdittext.getText().toString().trim();
                ccmrtrDepthStr=ccmrtrDepthEdittext.getText().toString().trim();
                ccmrtrTotalStr= ccmrtrTotalEdittext.getText().toString().trim();

                ccmrtrQtyStr=ccmrtrQtyEdittext.getText().toString().trim();
                ccmrtrLenStr=ccmrtrLenEdittext.getText().toString();
                ccmrtrWidthStr=ccmrtrWidthEdittext.getText().toString().trim();
                ccmrtrDepthStr=ccmrtrDepthEdittext.getText().toString().trim();
                ccmrtrTotalStr= ccmrtrTotalEdittext.getText().toString().trim();

                otherCvlQtyStr=othercvlQtyEdittext.getText().toString().trim();
                otherCvlLenStr=othercvlLenEdittext.getText().toString();
                otherCvlWidthStr=othercvlWidthEdittext.getText().toString().trim();
                otherCvlDepthStr=othercvlDepthEdittext.getText().toString().trim();
                otherCvlTotalStr= othercvlTotalEdittext.getText().toString().trim();

                if (checkValidation()) {
                    //  showSubmitAlertDilogBox();
                }
//                checkValidation();
            }
            break;
            default:
                break;
        }
    }

    private boolean checkValidation() {

        if (saddlePitExcav.equals("--Select--")) {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.select_saddle_pit_excav),"Alert");
            saddleSpinner.requestFocus();
            return false;
        }else if (pePipeTrnExcav.equals("--Select--")) {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.select_pe_pipe_trn_excav),"Alert");
            pepipeSpinner.requestFocus();
            return false;
        }else if (rampRR.equals("--Select--")) {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.select_ramp_rr),"Alert");
            rampRRSpinner.requestFocus();
            return false;
        }else if (wBoring.equals("--Select--")) {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.select_w_boring),"Alert");
            wBoringSpinner.requestFocus();
            return false;
        }else if (cgRestro.equals("--Select--")) {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.select_cg_restro),"Alert");
            cgSpinner.requestFocus();
            return false;
        }else if (fcRestorationWithCementMortal.equals("--Select--")) {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.select_fc_restoration_with_cement_mortal),"Alert");

            fcSpinner.requestFocus();
            return false;
        }else  if(saddleLenEdittxt.getText().toString().equals("")){
           liner_saddlelen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Saddle Length","Alert");

            return  false ;
          }else if(saddleWidtEdittext.getText().toString().equals("")){
            liner_saddlewidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Saddle Width","Alert");

            return  false ;
        }else if(saddleDepthEdiitext.getText().toString().equals("")){
            liner_saddledepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Saddle Depth","Alert");

            return  false ;
        }else if(saddleTotalEdittext.getText().toString().equals("")){
            liner_saddleTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Saddle Total","Alert");

            return  false ;
        }else if(pepipeLenEdittext.getText().toString().equals("")){
            liner_pePipelen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Pe Pipe Length","Alert");

            return  false ;
        }else if(pepipeWidthEdittext.getText().toString().equals("")){
            liner_pePipewidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Pe Pipe Width","Alert");

            return  false ;
        }else if(pepipeDepthEdittext.getText().toString().equals("")){
            liner_pePipedepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Pe Pipe Depth","Alert");

            return  false ;
        }else if(pepipeTotalEdittext.getText().toString().equals("")){
            liner_pePipeTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Pe Pipe Total","Alert");

            return  false ;
        }else if(lshapeLenEdittext.getText().toString().equals("")){
            liner_lShapeLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select L Shape length","Alert");
            return  false ;
        }else if(lshapeWidthEdittext.getText().toString().equals("")){
            liner_lShapeWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select L Shape Width","Alert");

            return  false ;
        }else if(lshapeDepthEdittext.getText().toString().equals("")){
            liner_lShapeDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select L Shape Depth","Alert");

            return  false ;
        }else if(lshapeTotalEdittext.getText().toString().equals("")){
            liner_lShapeTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select L Shape Total","Alert");

            return  false ;
        }else if(concreteLenEdittext.getText().toString().equals("")){
            liner_concrtDrnLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Concrete Length ","Alert");

            return  false ;
        }else if(concreteWidthEdittext.getText().toString().equals("")){
            liner_concrtDrnWidth.requestFocus();
           // concreteWidthEdittext.setError("Select Concrete Width ");
            MessageWindow.messageWindow(mCon,"Select Concrete Width ","Alert");

            return  false ;
        }else if(concreteDepthEdittext.getText().toString().equals("")){
            liner_concrtDrnDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Concrete Depth ","Alert");

            return  false ;
        }else if(concreteTotalEdittext.getText().toString().equals("")){
            liner_concrtDrnTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Concrete Total ","Alert");

            return  false ;
        }else if(rampRRLenEdittext.getText().toString().equals("")){
            liner_ramprrLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ramp rr Length ","Alert");

            return  false ;
        }else  if(rampRRWidthEdittext.getText().toString().equals("")){
            liner_ramprrWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ramp rr Length ","Alert");

            return  false ;
        }else if(rampRRDepthEdittext.getText().toString().equals("")){
            liner_ramprrDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ramp rr Depth ","Alert");

            return  false ;
        }else if(rampRRQtyEdittext.getText().toString().equals("")){
            liner_ramprrQty.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ramp rr Qty ","Alert");

            return  false ;
        }else if(rampRRTotalEdittext.getText().toString().equals("")){
            liner_ramprrTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ramp rr Total ","Alert");

            return  false ;
        }else if(ssmLenEdittext.getText().toString().equals("")){
            liner_ssmLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ssmr&r  Length ","Alert");

            return  false ;
        }else if(ssmWidthEdittext.getText().toString().equals("")){
            liner_ssmWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ssmr&r  Width ","Alert");

            return  false ;
        }else if(ssmDepthEdittext.getText().toString().equals("")){
            liner_ssmDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ssmr&r  Depth ","Alert");

            return  false ;
        }else if(ssmQtyEdittext.getText().toString().equals("")) {
            liner_ssmQty.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ssmr&r  Qty ","Alert");

            return false;
        }else if(ssmTotalEdittext.getText().toString().equals("")){
            liner_ssmTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select ssmr&r  Total ","Alert");

            return  false ;
        }else if(ccgRestroLenEdittext.getText().toString().equals("")){
            liner_cgRestroLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Length ","Alert");

            return  false ;
        }else if(ccgRestroWidthEdittext.getText().toString().equals("")){
            liner_cgWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Width ","Alert");

            return  false ;
        }else if(ccgRestroDepthEdittext.getText().toString().equals("")){
            liner_cgDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Depth ","Alert");

            return  false ;
        }else  if(ccgRestroQtyEditttext.getText().toString().equals("")){
            liner_cgQty.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Qty ","Alert");

            return  false ;
        }else if(ccgRestroTotalEdittext.getText().toString().equals("")){
            liner_cgTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Total ","Alert");

            return  false ;
        }else  if(wBoringLenEdittext.getText().toString().equals("")){
            liner_wBoringLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select W boring Length ","Alert");

            return  false ;
        }else if(wBoringWidthEdittext.getText().toString().equals("")){
            liner_wBoringWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select W boring Width ","Alert");

            return  false ;
        }else if(wBoringDepthEdittext.getText().toString().equals("")){
            liner_wBoringDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select W boring Depth ","Alert");

            return  false ;
        }else if(wBoringQtyEdittext.getText().toString().equals("")){
            liner_wBoringQty.requestFocus();
            MessageWindow.messageWindow(mCon,"Select W boring Qty ","Alert");

            return  false ;
        }else  if(wBoringTotalEdittext.getText().toString().equals("")){
            liner_wBoringTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select W boring Total ","Alert");

            return  false ;
        }else if(cgLenEdittext.getText().toString().equals("")){
            liner_cgLenth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Length ","Alert");

            return  false ;
        }else if(cgWidthEdittext.getText().toString().equals("")){
            liner_cgWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Width ","Alert");

            return  false ;
        }else if(cgQtyEdittext.getText().toString().equals("")){
            liner_cgQty.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Qty ","Alert");

            return  false ;
        }else if(cgDepthEdittext.getText().toString().equals("")){
            liner_cgDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Depth ","Alert");

            return  false ;
        }else if(cgTotalEdittext.getText().toString().equals("")){
            liner_cgTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CG Restro Total ","Alert");

            return  false ;
        }else if(fcLenEdittext.getText().toString().equals("")){
            liner_fcLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select FC Restro Length ","Alert");

            return  false ;
        }else if(fcWidthEditttext.getText().toString().equals("")){
            liner_fcWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select FC Restro Width ","Alert");

            return  false ;
        }else if(fcDepthEdittext.getText().toString().equals("")){
            liner_fcDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select FC Restro Depth ","Alert");

            return  false ;
        }else if(fcQtyEdittext.getText().toString().equals("")){
            liner_fcQty.requestFocus();
            MessageWindow.messageWindow(mCon,"Select FC Restro Qty ","Alert");

            return  false ;
        } else if(fcTotalEdittext.getText().toString().equals("")){
            liner_fcTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select FC Restro Total ","Alert");

            return  false ;
        }else if(drBurntLenEdittext.getText().toString().equals("")){
            liner_drBurntLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select D& R Burnt Length ","Alert");

            return  false ;
        }else if(drBurntWidthEdittext.getText().toString().equals("")){
            liner_drBurntWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select D& R Burnt Width ","Alert");

            return  false ;
        }else if(drBurntDepthEdittext.getText().toString().equals("")){
            liner_drBurntDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select D& R Burnt Depth ","Alert");

            return  false ;
        }else if(drBurntTotalEdittext.getText().toString().equals("")){
            liner_drBurntDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select D& R Burnt Total ","Alert");

            return  false ;
        }else if(plasterLenEdittext.getText().toString().equals("")){
            liner_plasterDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Plaster Length","Alert");

            return  false ;
        }else if(plasterWidthEdittext.getText().toString().equals("")){
            liner_pePipewidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Plaster Width","Alert");

            return  false ;
        }else if(plasterDepthEdittext.getText().toString().equals("")){
            liner_plasterDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Plaster Depth","Alert");

            return  false ;
        }else if(plasterQtyEdittext.getText().toString().equals("")){
            liner_plasterQty.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Plaster Qty","Alert");

            return  false ;
        }else if(plasterTotalEdittext.getText().toString().equals("")){
            liner_plasterTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Plaster Total","Alert");

            return  false ;
        }else if(ccmrtrLenEdittext.getText().toString().equals("")){
            liner_ccmrtrLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CC Mortar Length","Alert");

            return  false ;
        }else  if(ccmrtrWidthEdittext.getText().toString().equals("")){
            liner_ccmrtrWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CC Mortar Width","Alert");

            return  false ;
        }else if(ccmrtrDepthEdittext.getText().toString().equals("")){
            liner_ccmrtrDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CC Mortar Depth","Alert");

            return  false ;
        }else if(ccmrtrQtyEdittext.getText().toString().equals("")){
            liner_ccmrtrQty.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CC Mortar Qty","Alert");

            return  false ;
        }else if(ccmrtrTotalEdittext.getText().toString().equals("")){
            liner_ccmrtrTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select CC Mortar Total","Alert");

            return  false ;
        }else if(othercvlLenEdittext.getText().toString().equals("")){
            liner_othercvlLen.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Other Civil Length","Alert");

            return  false ;
        }else  if(othercvlWidthEdittext.getText().toString().equals("")){
            liner_othercvlWidth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Other Civil Width","Alert");

            return  false ;
        }else if(othercvlDepthEdittext.getText().toString().equals("")){
            liner_othercvlDepth.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Other Civil Depth","Alert");

            return  false ;
        }else if(othercvlQtyEdittext.getText().toString().equals("")){
            liner_othercvlQty.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Other Civil Qty","Alert");

            return  false ;
        }else if(othercvlTotalEdittext.getText().toString().equals("")){
            liner_othercvlTotal.requestFocus();
            MessageWindow.messageWindow(mCon,"Select Other Civil Total","Alert");

            return  false ;
        }else{

                sendCivilMeasurementDetails.sndCvlMsrmentDet(saddleAndPitIdStr, pepipeIdStr, rampRRIdStr, wallBoringIdStr,  cgRestroIdStr, fcRestroIdStr, saddleLenStr, saddleWidthStr,
                     saddleDepthStr, saddleTotalStr, pepipeLenStr, pepipeWidthStr, pepipeDepthStr, pepipeTotalStr, lShapeLenStr,
                     lShapeWidthStr, lShapeDepthStr, lShapeTotalStr, concreteLenStr, concreteWidthStr, concreteDepthStr, concreteTotalStr, ramprrLenStr, ramprrWidthStr, ramprrDepthStr,
                     ramprrTotalStr, ramprrQtyStr, ssmQtyStr, ssmLenStr, ssmWidthStr, ssmDepthStr, ssmTotalStr, ccgQtyStr, ccgLenStr, ccgWidthStr, ccgDepthStr,
                     ccgTotalStr, wBoringLenStr, wBoringWidthStr, wBoringDepthStr, wBoringQtyStr, wBoringTotalStr, cgQtyStr, cgLenStr, cgWidthStr, cgDepthStr, cgTotalStr,
                     fcQtyStr, fcLenStr, fcWidthStr, fcDepthStr, fcTotalStr, drBurntLenStr, drBurntWidthStr, drBurntDepthStr, drBurntTotalStr, drBurntQtyStr,
                     plasterLenStr, plasterWidthStr, plasterDepthStr, plasterTotalStr, plasterQtyStr, ccmrtrQtyStr, ccmrtrLenStr, ccmrtrWidthStr, ccmrtrDepthStr, ccmrtrTotalStr,
                     otherCvlQtyStr, otherCvlLenStr, otherCvlWidthStr, otherCvlDepthStr, otherCvlTotalStr);


            return true;
        }

    }

    public interface SendCivilMeasurementDetails {
        void sndCvlMsrmentDet(String saddleAndPitIdStr,String pepipeIdStr,String rampRRIdStr,String wallBoringIdStr, String cgRestroIdStr,String fcRestroIdStr,String saddleLenStr,String saddleWidthStr,String saddleDepthStr,String saddleTotalStr,String pepipeLenStr,String pepipeWidthStr,String pepipeDepthStr,String pepipeTotalStr,String lShapeLenStr,
                               String lShapeWidthStr,String lShapeDepthStr,String lShapeTotalStr,String concreteLenStr,String concreteWidthStr,String concreteDepthStr,String concreteTotalStr,String ramprrLenStr,String ramprrWidthStr,String ramprrDepthStr,
                               String ramprrTotalStr,String ramprrQtyStr,String ssmQtyStr,String ssmLenStr,String ssmWidthStr,String ssmDepthStr,String ssmTotalStr,String ccgQtyStr,String ccgLenStr,String ccgWidthStr,String ccgDepthStr,
                               String ccgTotalStr,String wBoringLenStr,String wBoringWidthStr,String wBoringDepthStr,String wBoringQtyStr,String wBoringTotalStr,String cgQtyStr,String cgLenStr,String cgWidthStr,String cgDepthStr,String cgTotalStr,
                               String fcQtyStr,String fcLenStr,String fcWidthStr,String fcDepthStr,String fcTotalStr,String drBurntLenStr,String drBurntWidthStr,String drBurntDepthStr,String drBurntTotalStr,String drBurntQtyStr,
                               String plasterLenStr,String plasterWidthStr,String plasterDepthStr,String plasterTotalStr,String plasterQtyStr,String ccmrtrQtyStr,String ccmrtrLenStr,String ccmrtrWidthStr,String ccmrtrDepthStr,String ccmrtrTotalStr,
                               String otherCvlQtyStr,String otherCvlLenStr,String otherCvlWidthStr,String otherCvlDepthStr,String otherCvlTotalStr);
                            }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    /*    try {
            sendCivilMeasurementDetails = (SendCivilMeasurementDetails) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }*/
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
