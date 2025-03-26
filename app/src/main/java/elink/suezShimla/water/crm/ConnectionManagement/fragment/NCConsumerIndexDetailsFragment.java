package elink.suezShimla.water.crm.ConnectionManagement.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tiper.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.ZoneAndWard.Model.ZoneWardModel;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListActivityDetails;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.DMAModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MSRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.SRModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.AreaModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.LotModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.WardModel;
import elink.suezShimla.water.crm.constant.AppConstant;
import io.realm.RealmResults;

public class NCConsumerIndexDetailsFragment extends Fragment implements MaterialSpinner.OnItemSelectedListener, View.OnClickListener {

    MaterialSpinner zoneSpinner, wardSpinner, msrSpinner, srSpinner, groupSpinner, lotSpinner, areaSpinner;

    private RealmOperations realmOperations;
    private Context mCon;

    private List<ZoneModel> zoneModelList;
    private List<ZoneWardModel> zoneWardModelList;
    private List<SubZoneModel> subZoneModelList;
    ArrayList<String> zoneName;


    private ArrayAdapter zoneAdapter, zoneWardAdapter, msrArrayAdapter, srArrayAdapter, groupAdapter, lotAdapter,areaAdapter;
    RealmResults<MSRModel> msrModels;
    String MSRIDStr = "", msrValue = "", msrStr = "", srValue = "", SRIDStr = "", zoneIdValueStr = "",groupId="",groupIdValueStr="",
            wardId="",lotId="";
    int zoneId;

    SRModel model_sr;
    DMAModel model_dma;
    MSRModel msrModel;
    private ZoneModel zoneModel;
    private LotModel lotModel;
    private AreaModel areaModel;
    private  MMGSubZoneModel groupModel;
    private MMGSubZoneModel mmgSubZoneModel;
    private WardModel wardModel;
    private List<MMGSubZoneModel> mmgSubZoneModelList;
    private List<WardModel> WardModelList;
    private List<LotModel> lotModelList;

    RealmResults<SRModel> srModelRealmResults;
    ArrayList<String> srStringList = new ArrayList<String>();
    ArrayList<String> srValueList = new ArrayList<String>();
    String[] seSelect = {"Select"};
    private ArrayList<String> subZoneList, subZoneIdList,lotList,lotIdList;
    private ArrayList<String> wardList, wardeIdList;

    SiteVisitModel technicalFeasibilityModel,model;
    SiteVisitListActivityDetails activity;
    Button btn_submit_consumer,btn_consumer_index_back;

    String zoneStr="",wardStr="",srStr="",groupStr="",lotStr="",areaStr="",areaId="";

    ConsumerIndex CID;
    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
      //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View rootView = inflater.inflate(R.layout.fragment_consumer_index, container, false);
        mCon = getActivity();

        activity = (SiteVisitListActivityDetails) getActivity();
        model = activity.getSiteVisitDataData();

        zoneStr = model.getAM_APP_ZONE();
        wardStr = model.getAM_APP_CIRCLE();
        msrStr = model.getSBM_NAME();
        srStr = model.getTRM_NAME();
        groupStr = model.getAM_APP_PC();
        lotStr = model.getAM_APP_MRC();
        areaStr = model.getAM_APP_AREA();


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

        //showDialog();
        initializeViews(rootView);
        // getFirmTypeRequest();

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
    private void initializeViews(View rootView) {
        mCon = getActivity();
        activity = (SiteVisitListActivityDetails) getActivity();

        lotList=new ArrayList<>();
        lotIdList=new ArrayList<>();

        zoneSpinner = rootView.findViewById(R.id.zoneSpinner);
        wardSpinner = rootView.findViewById(R.id.wardSpinner);
        msrSpinner = rootView.findViewById(R.id.msrSpinner);
        srSpinner = rootView.findViewById(R.id.srSpinner);
        groupSpinner = rootView.findViewById(R.id.groupSpinner);
        lotSpinner = rootView.findViewById(R.id.lotSpinner);
        lotSpinner.setOnItemSelectedListener(this);
        areaSpinner = rootView.findViewById(R.id.areaSpinner);
        areaSpinner.setOnItemSelectedListener(this);
        btn_submit_consumer = rootView.findViewById(R.id.btn_submit_consumer);
        btn_submit_consumer.setOnClickListener(this);

        btn_consumer_index_back = rootView.findViewById(R.id.btn_consumer_index_back);
        btn_consumer_index_back.setOnClickListener(this);

        realmOperations = new RealmOperations(mCon);
        wardSelectDropDown();
        zoneDropDown();

        msrDropDown();
        srDropDown();
        groupSelectDropDown();
        lotSelectDropDown();
        areaDropDown();


    }

    private void groupSelectDropDown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, seSelect);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);
        groupSpinner.setSelection(0);
    }
 private void lotSelectDropDown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, seSelect);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lotSpinner.setAdapter(adapter);
        lotSpinner.setSelection(0);
    }

    private void wardSelectDropDown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, seSelect);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wardSpinner.setAdapter(adapter);
     //   wardSpinner.setSelection(0);
    }

    private void srDropDown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, seSelect);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        srSpinner.setAdapter(adapter);
        srSpinner.setSelection(0);
    }

    private void msrDropDown() {
        msrModels = realmOperations.fetchMSRNameID();
        ArrayList<String> msrList = new ArrayList<>();
        msrList.add("Select");

        for (int i = 0; i < msrModels.size(); i++) {
            MSRModel msrModel = msrModels.get(i);
            MSRIDStr = msrModel.getSBM_ID();
            String msrName = msrModel.getSBM_NAME();
            msrList.add(msrName);
        }

       /* ArrayList<String> msrName = new ArrayList<>();

        msrName = realmOperations.fetchMSRDetails();
        ArrayList<String>  msrList = new ArrayList<>();
        msrList.add("--Select--");
        msrList.addAll(msrName);*/

        msrArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, msrList);
        msrArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msrSpinner.setAdapter(msrArrayAdapter);
        msrSpinner.setSelection(0);
        msrSpinner.setOnItemSelectedListener(this);
    }

    private void zoneDropDown() {
        String zoneIdLists = PreferenceUtil.getZone();
        List<String> zoneIdList = Arrays.asList(zoneIdLists.split(","));
        // Log.d("check", String.valueOf(zoneIdList));
       zoneName = new ArrayList<>();

        for (int i = 0; i < zoneIdList.size(); i++) {
            int id = Integer.parseInt(zoneIdList.get(i));
            zoneModelList = realmOperations.fetchZoneById(id);


            // Log.d("check List", String.valueOf(zoneModelList));

            for (ZoneModel zoneModel : zoneModelList) {

                zoneName.add(zoneModel.getBU_NAME());
                //Log.d("check", String.valueOf(zoneName));

            }
            ArrayList<String> zoneList = new ArrayList<>();
            zoneList.add("All");
            zoneList.addAll(zoneName);
            zoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, zoneList);
            zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            zoneSpinner.setAdapter(zoneAdapter);

            zoneSpinner.setOnItemSelectedListener(this);
            zoneSpinner.setSelection(1);

            zoneModel = realmOperations.fetchZoneByName("East");
            zoneId = Integer.parseInt(String.valueOf(zoneModel.getBUM_BU_ID()));
            zoneIdValueStr = String.valueOf(zoneId);
            wordDropDown(zoneIdValueStr);


        }
    }

    private void wordDropDown(String zoneId) {
        try {
            ArrayList<String> wardStringList = new ArrayList<>();
            WardModelList = realmOperations.fetchWardData(zoneId);


            wardList = new ArrayList<>();
            wardeIdList = new ArrayList<>();

            for (WardModel wardModel : WardModelList) {
                wardList.add(wardModel.getNAME());
                wardeIdList.add(String.valueOf(wardModel.getID()));
                //Log.d("check", String.valueOf(zoneName));

            }

            ArrayList<String> wardArrayList = new ArrayList<>();
            wardArrayList.add("Select");
            wardArrayList.addAll(wardList);

            zoneWardAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, wardArrayList);
            zoneWardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            wardSpinner.setAdapter(zoneWardAdapter);
            wardSpinner.setSelection(0);
            wardSpinner.setOnItemSelectedListener(this);


        } catch (Exception e) {
            Log.d("check", e.getMessage());
        }

    }

    private void setSRDropdown(String msrValue) {
        try {

            msrModel = realmOperations.fetchMSRByString(msrValue);
            MSRIDStr = msrModel.getSBM_ID();

            ArrayList<String> srName = new ArrayList<>();
            // srName = realmOperations.fetchSRList();
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
            srSpinner.setOnItemSelectedListener(this);


        } catch (Exception e) {

        }
    }

    private void groupDropDown(int zoneId) {
        mmgSubZoneModelList = realmOperations.fetchGroupData(zoneId);

        subZoneList = new ArrayList<>();
        subZoneIdList = new ArrayList<>();

        for (MMGSubZoneModel mmgSubZoneModel : mmgSubZoneModelList) {
            subZoneList.add(mmgSubZoneModel.getPCM_PC_NAME());
            subZoneIdList.add(String.valueOf(mmgSubZoneModel.getPCM_PC_ID()));
            //Log.d("check", String.valueOf(zoneName));

        }

        ArrayList<String> subZoneArrayList = new ArrayList<>();
        subZoneArrayList.add("Select");
        subZoneArrayList.addAll(subZoneList);
        groupAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, subZoneArrayList);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupAdapter);
        groupSpinner.setSelection(0);
        groupSpinner.setOnItemSelectedListener(this);
    }

    private void lotDropDown(String zoneId,String groupId ) {

        lotModelList = realmOperations.fetchLotData(String.valueOf(zoneId), groupId);

        lotList = new ArrayList<>();
        lotIdList = new ArrayList<>();

        for (LotModel lotModel : lotModelList) {
            lotList.add(lotModel.getMR_NAME());

            //Log.d("check", String.valueOf(zoneName));

        }

        ArrayList<String> lotArrayList = new ArrayList<>();
        lotArrayList.add("Select");
        lotArrayList.addAll(lotList);
        lotAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, lotArrayList);
        lotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lotSpinner.setAdapter(lotAdapter);
        lotSpinner.setSelection(0);
        lotSpinner.setOnItemSelectedListener(this);

    }

    private void areaDropDown() {

        ArrayList<String>  areaStringList = new ArrayList<>();
        areaStringList = realmOperations.fetchAreaListName();
        ArrayList<String> areaList = new ArrayList<>();
        areaList.add("Select");
        areaList.addAll(areaStringList);

        areaAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, areaList);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);
        areaSpinner.setSelection(0);

    }

    @Override
    public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
        switch (materialSpinner.getId()) {
            case R.id.zoneSpinner: {
                String zoneName = zoneSpinner.getSelectedItem().toString();
                if (zoneName.equalsIgnoreCase("All")) {
                    groupSelectDropDown();

                } else {
                    zoneModel = realmOperations.fetchZoneByName(zoneName);
                    zoneId = Integer.parseInt(String.valueOf(zoneModel.getBUM_BU_ID()));
                    zoneIdValueStr = String.valueOf(zoneId);
                    wordDropDown(zoneIdValueStr);
                }

            }
            break;
            case R.id.wardSpinner: {
                String wardName = wardSpinner.getSelectedItem().toString();
                if (wardName.equalsIgnoreCase("Select")) {

                } else {
                    wardModel = realmOperations.fetchWardByNameId(wardName);
                    wardId = wardModel.getID();


                    groupDropDown(zoneId);
                }
                msrDropDown();
            }
            break;
            case R.id.groupSpinner: {
                String group = groupSpinner.getSelectedItem().toString();
                if (group.equalsIgnoreCase("Select")) {

                } else {
                    groupModel = realmOperations.fetchGroupName(group);
                    groupId = String.valueOf(groupModel.getPCM_PC_ID());
                    groupIdValueStr = String.valueOf(zoneId);

                 /*   MMGSubZoneModel{PCM_PC_ID=147, PCM_PC_NAME='NZ09', PCM_BU_ID=4}
                 groupDropDown(zoneId);
                    wordDropDown(zoneIdValueStr);*/
                    lotDropDown(String.valueOf(zoneId),groupId);
                }

            }

            break;
            case R.id.msrSpinner: {

                msrValue = msrSpinner.getSelectedItem().toString();

                if (msrValue.equalsIgnoreCase("Select")) {

                    ArrayList<String> srList12 = new ArrayList<>();
                    srList12.add("Select");

                    srArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, srList12);
                    srArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    srSpinner.setAdapter(srArrayAdapter);
                    srSpinner.setOnItemSelectedListener(this);
                } else {
                    msrModel = realmOperations.fetchMSRByString(msrValue);
                    MSRIDStr = msrModel.getSBM_ID();

                    int a = 0;
                    if (msrStr == null) {
                        setSRDropdown(msrValue);
                    } else {
                        if (msrStr.equalsIgnoreCase(MSRIDStr)) {
                            //srDropDownSelection();

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


                } else {
                    model_sr = realmOperations.fetchSRByString(srValue);
                    SRIDStr = model_sr.getTRM_ID();

                }
            }
            break;
            case R.id.lotSpinner: {
               lotStr = lotSpinner.getSelectedItem().toString();

                if (lotStr.equalsIgnoreCase("Select")) {

                } else {
                    lotModel = realmOperations.fetchLotId(lotStr);
                    lotId = lotModel.getMR_ID();

                }
            }
            break;
            case R.id.areaSpinner: {
                areaStr = areaSpinner.getSelectedItem().toString();

                if (areaStr.equalsIgnoreCase("Select")) {

                } else {
                    areaModel = realmOperations.fetchAreaId(areaStr);
                    areaId = areaModel.getAREAID();

                }
            }
            break;


            default:

        }

    }

    @Override
    public void onNothingSelected(MaterialSpinner materialSpinner) {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser) {
                new NCConsumerIndexDetailsFragment();
                getData();

            }
        }
    }

    private void getData() {
        technicalFeasibilityModel = activity.getComercialFeasibilityList();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit_consumer:
            {
                if(validation()){
                    consumerIndexData();
                }
            }
            break;
            case R.id.btn_consumer_index_back:

                backFragmentActivity();


                break;
            default:
                break;
        }
    }

    private void backFragmentActivity() {
        ((SiteVisitListActivityDetails)getActivity()).onClickPrev();
    }

    private void consumerIndexData() {
        SiteVisitModel siteVisitModel = new SiteVisitModel(zoneIdValueStr,wardId,MSRIDStr,SRIDStr,groupId,lotId,areaId);
        CID.consumerIndexData(siteVisitModel);
   //     Toast.makeText(getActivity(), getResources().getString(R.string.details_submitted_successfully), Toast.LENGTH_SHORT).show();
        ((SiteVisitListActivityDetails) getActivity()).onClickNext();
    }

    public interface ConsumerIndex {
        //        void sendData(String submitStatus, String radiobuttonValStr,  String makerCodeId,String serialNoStr,String installDtStr,String meterSizeStr,String sealNoStr,String pastMeterReadingStr);
        void consumerIndexData(SiteVisitModel siteVisitModel);
    }

    private boolean validation() {

        zoneStr = zoneSpinner.getSelectedItem().toString();
        wardStr = wardSpinner.getSelectedItem().toString();
        msrStr = msrSpinner.getSelectedItem().toString();
        srStr = srSpinner.getSelectedItem().toString();
        groupStr = groupSpinner.getSelectedItem().toString();
        lotStr = lotSpinner.getSelectedItem().toString();
        areaStr = areaSpinner.getSelectedItem().toString();


        if (zoneStr.equalsIgnoreCase("Select")) {

            zoneSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_zone, Toast.LENGTH_SHORT).show();
            return false;
        }   else {
            zoneSpinner.setError(null);

        }
        if (wardStr.equalsIgnoreCase("Select")) {

            wardSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_ward, Toast.LENGTH_SHORT).show();
            return false;
        }   else {
            wardSpinner.setError(null);

        }
        if (msrStr.equalsIgnoreCase("Select")) {

            msrSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_msr, Toast.LENGTH_SHORT).show();
            return false;
        }   else {
            msrSpinner.setError(null);

        }

        if (srStr.equalsIgnoreCase("Select")) {
            srSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_select_sr, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            srSpinner.setError(null);

        }if (groupStr.equalsIgnoreCase("Select")) {
            groupSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_select_group, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            groupSpinner.setError(null);

        }
        if (lotStr.equalsIgnoreCase("Select")) {
            lotSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_unit, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            lotSpinner.setError(null);
        }
        if (areaStr.equalsIgnoreCase("Select")) {
            areaSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_select_area, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            areaSpinner.setError(null);
        }

        return true;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            CID = (NCConsumerIndexDetailsFragment.ConsumerIndex) getActivity();
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
        ((App) mCon.getApplicationContext()).startActivityTransitionTimer();
    }
}
