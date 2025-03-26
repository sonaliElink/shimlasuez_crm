package elink.suezShimla.water.crm.MasterData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.BilllingAdjustmentdataDownloadModel;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadAdjustmentType;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadAdjustmentTypeTable;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadMeterStatusBillAdjust;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadMeterStatusBillAdjustTable;
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
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class ActivityDownloadMaster extends AppCompatActivity implements View.OnClickListener {
    Button btn_master_download;

    private MaterialDialog progress, masterProgress, rightsProgress, mmgMasterProgress,
            mmgMasterMeterReplacementProgress, errorProgress;
    private String jsonResponse = "", jsonResponseBillAdjustment = "", jsonMasterDataResponse = "", jsonRightsResponse = "", jsonErrorResponse = "", jsonValidateUser = "",
            JsonMMGMasterData = "",sessionid="", JsonMMGMasterMeterReplacementData = "", language;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;
    private Context mCon;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_download_master);

        Log.e("EMPCDE::",PreferenceUtil.getEmployeeCode());
        init();
    }

    private void init() {
        btn_master_download = findViewById(R.id.btn_master_download);
        btn_master_download.setOnClickListener(this);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);


        mCon = this;
        realmOperations = new RealmOperations(mCon);

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_master_download:
                downloadMasterData();
                break;
              case R.id.iv_back:
                startBackActivity();
                break;
            default:
                break;
        }
    }

    private void startBackActivity() {
            Intent intent = new Intent(ActivityDownloadMaster.this, MainActivity.class);
            startActivity(intent);
            finish();
    }

    private void downloadMasterData() {
      /*  ComplaintTypeModel complaintTypeModelExist = realmOperations.getComplaintTypeExist();
        ComplaintSubTypeModel complaintSubTypeModelExist = realmOperations.getComplaintSubTypeExist();
        ComplaintSourceModel complaintSourceModelExist = realmOperations.getComplaintSourceExist();
        ZoneModel zoneModelExist = realmOperations.getZoneExist();
        SubZoneModel subZoneModelExist = realmOperations.getSubZoneExist();
        SiteEngineerModel siteEngineerModelExist = realmOperations.getSiteEngineerExist();
        FinishActionModel finishActionModelExist = realmOperations.getFinishActionExist();
        ActionFormModel actionFormModelExist = realmOperations.getActionFormExist();
        DocSourceModel docSourceModelExist = realmOperations.getDocSourcenModelExist();
        DocTypeModel docTypeModeExist = realmOperations.getDocTypeModelExist();
        DocSubTypeModel docSubTypeModelModelExist = realmOperations.getDocSubTypeModelExist();*/
/*
        if (complaintTypeModelExist == null && complaintSubTypeModelExist == null && complaintSourceModelExist == null && zoneModelExist == null
                && subZoneModelExist == null && siteEngineerModelExist == null && actionFormModelExist == null && docSourceModelExist == null
                && docTypeModeExist == null && docSubTypeModelModelExist == null) {*/
            String[] params = new String[2];

        try {
            sessionid=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.SID));

            // Decrypt EmpCode
            params[0] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            params[1]=sessionid;
        } catch (Exception e) {
            e.printStackTrace();
        }

            if (connection.isConnectingToInternet()) {

                DownloadMaster downloadMaster = new DownloadMaster();
                downloadMaster.execute(params);

                masterProgress = new MaterialDialog.Builder(mCon)
                        .content(R.string.loading)
                        .progress(true, 0)
                        .autoDismiss(false)
                        .canceledOnTouchOutside(false)
                        .widgetColorRes(R.color.colorPrimary)
                        .show();

            } else {
                Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }

       /* }else{*/
            //getBilllingAdjustmentdataDownload();
        //}

    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadMaster extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            mmgMasterProgress = new MaterialDialog.Builder(mCon)
                    .content("MasterData....")
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
                username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE));
                password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                jsonMasterDataResponse = invServices.getOtherData(Constants.URL, Constants.NameSpace, Constants.MasterData,username,password, params, paraNames);

                Log.e("jsonMasterDataResponse",jsonMasterDataResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MasterDataModel masterDataModel = gson.fromJson(jsonMasterDataResponse, MasterDataModel.class);

                if (masterDataModel != null) {
                    btn_master_download.setEnabled(false);
                    btn_master_download.setBackgroundColor(btn_master_download.getContext().getResources().getColor(R.color.red_700));

                    realmOperations.deleteComplaintTypeTable();
                    realmOperations.deleteComplaintSubTypeTable();
                    realmOperations.deleteComplaintSourceTable();
                    realmOperations.deleteZoneTable();
                    realmOperations.deleteSubZoneTable();
                    realmOperations.deleteSiteEngineerTable();
                    realmOperations.deleteSRTable();
                    realmOperations.deleteDMATable();
                    realmOperations.deleteDownloadCustomerTypeTable();
                    realmOperations.deleteDownloadSourceTypeTable();
                    realmOperations.deleteComplaintByTable();
                    realmOperations.deleteAreaTable();
                    realmOperations.deleteWallBoringTable();
                    realmOperations.deleteDocSourceTable();
                    realmOperations.deleteDocTypeTable();
                    realmOperations.deleteSubDocTypeTable();

                    ComplaintTypeModel complaintTypeModelExist = realmOperations.getComplaintTypeExist();
                    //   if (complaintTypeModelExist == null) {
                    for (DownloadComplaintType downloadComplaintType : masterDataModel.getDownloadComplaintTypes()) {
                        int id = stringToInt(downloadComplaintType.getCMTM_ID());
//                        ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel(id, downloadComplaintType.getCMTM_CODE(), downloadComplaintType.getCMTM_NAME());
                        ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel(id, downloadComplaintType.getCMTM_CODE(), downloadComplaintType.getCMTM_NAME()
                                , downloadComplaintType.getREQUEST(), downloadComplaintType.getNOCONSUMER(), downloadComplaintType.getDEPTID(),  downloadComplaintType.getCOMPLAINT(),downloadComplaintType.getENQUIRY());
                        realmOperations.addComplaintType(complaintTypeModel);
                    }
                    //  }

                    ComplaintSubTypeModel complaintSubTypeModelExist = realmOperations.getComplaintSubTypeExist();
                    //   if (complaintSubTypeModelExist == null) {
                    for (DownloadComplaintSubType downloadComplaintSubType : masterDataModel.getDownloadComplaintSubTypes()) {
                        int id = stringToInt(downloadComplaintSubType.getCOMPLAINTSUBTYPEID());
                        int compId = stringToInt(downloadComplaintSubType.getCOMPLAINTTYPEID());
                        ComplaintSubTypeModel complaintSubTypeModel = new ComplaintSubTypeModel(id, downloadComplaintSubType.getCOMPLAINTSUBTYPENAME(), compId,downloadComplaintSubType.getCOMPLAINT(),downloadComplaintSubType.getREQUEST());
                        realmOperations.addComplaintSubType(complaintSubTypeModel);
                    }
                    //      }


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




try {


    // Insert Finish Action in FinishActionModel Table
    FinishActionModel finishActionModelExist = realmOperations.getFinishActionExist();
    // if (finishActionModelExist == null) {

    for (DownloadFinishAction downloadFinishAction : masterDataModel.getDownloadFinishActions()) {
        int id = stringToInt(downloadFinishAction.getCSCM_ID());
        FinishActionModel finishActionModel = new FinishActionModel(id, downloadFinishAction.getCSCM_SECNAME(), downloadFinishAction.getFILTER());
        realmOperations.addFinishAction(finishActionModel);
    }
    //   }
}catch (Exception e){
    e.printStackTrace();
}
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
                            ActionFormModel actionModel = new ActionFormModel(downloadActionCRM.getA_NAME(),downloadActionCRM.getA_COM_TYPE());
                            realmOperations.addActionForm(actionModel);
                        }
                    }

                    WorkCompObservationModel workCompObservationModel = realmOperations.getWorkCompObservationModelExist();

                    if (workCompObservationModel == null) {

                        for (WorkCompObservation workCompObservation : masterDataModel.getWorkCompObservations()) {
                            WorkCompObservationModel WorkCompObservationModel = new WorkCompObservationModel(workCompObservation.getID(),workCompObservation.getOBSERVATION(),workCompObservation.getCOMPTYPEID(),workCompObservation.getACTIONID());
                            realmOperations.addWorkCompObservation(WorkCompObservationModel);
                        }
                    }
                    DocSourceModel docSourceModel= realmOperations.getDocSourcenModelExist();
                    if (docSourceModel == null) {

                        for (DocSource docSource : masterDataModel.getDocSources()) {
                            DocSourceModel docSourceModels = new DocSourceModel(docSource.getDOCSOURCE(),docSource.getDOCCOMPTYPE(),docSource.getDOCSOURCECODE());
                            realmOperations.addDocSources(docSourceModels);
                        }
                    }

                    DocTypeModel docTypeModel= realmOperations.getDocTypeModelExist();
                    if (docTypeModel == null) {

                        for (DocType docType : masterDataModel.getDocTypes()) {
                            String docSource =docType.getDOCUMENTSOURCE();
                            if (docSource == null) {
                                docSource="NC";
                            }else {
                                docSource= docSource;
                            }

                            DocTypeModel docTypeModels = new DocTypeModel(docType.getDOCUMENTTYPE(),docType.getDOCUMENTTYPEID(),docSource);
                            realmOperations.addDocTypes(docTypeModels);
                        }
                    }

                    DocSubTypeModel docSubTypeModel= realmOperations.getDocSubTypeModelExist();
                    if (docSubTypeModel == null) {

                        for (DocSubType subType : masterDataModel.getDocSubTypes()) {
                            DocSubTypeModel docSubTypeModels = new DocSubTypeModel(subType.getDOCUMENTTYPEID(),subType.getDOCUMENTSUBTYPEID(),subType.getDOCUMENTSUBTYPE());
                            realmOperations.addDocSubTypes(docSubTypeModels);
                        }
                    }

                    for (SRModel srModel1 : masterDataModel.getSrModels()) {
                        SRModel srModel22 = new SRModel(srModel1.getTRM_ID(), srModel1.getTRM_NAME(), srModel1.getMSRID());
                        realmOperations.addSR(srModel22);
                    }


                    for (DMAModel dmaModel1 : masterDataModel.getDmaModel()) {
                        DMAModel dmaModel2 = new DMAModel(dmaModel1.getPM_ID(), dmaModel1.getPM_NAME(), dmaModel1.getSRID());
                        realmOperations.addDMA(dmaModel2);
                    }

                    masterProgress.dismiss();
                    masterProgress.cancel();
                    mmgMasterProgress.dismiss();


                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    masterProgress.dismiss();
                }



            }

            catch (Exception e) {
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid", error);
                masterProgress.dismiss();
            }


            mmgMasterProgress.dismiss();



            getBilllingAdjustmentdataDownload();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }



    private void getBilllingAdjustmentdataDownload() {

        if (connection.isConnectingToInternet()) {


           BilllingAdjustmentdataDownload billlingAdjustmentdataDownload = new BilllingAdjustmentdataDownload();
            billlingAdjustmentdataDownload.execute();


            masterProgress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .autoDismiss(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();

        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private class BilllingAdjustmentdataDownload extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                jsonResponseBillAdjustment = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.Complaint_BillAdjRequestDropdown);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
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


                    masterProgress.dismiss();
                    masterProgress.cancel();
                    Toast.makeText(mCon, R.string.download_master_data_completed, Toast.LENGTH_LONG).show();
                    btn_master_download.setEnabled(false);
                    btn_master_download.setBackgroundColor(btn_master_download.getContext().getResources().getColor(R.color.red_700));
                    getMMGMasterDataDownload();


                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    masterProgress.dismiss();
                }


            } catch (Exception e) {
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid", error);
                masterProgress.dismiss();
            }

        }


    }

    private void getMMGMasterDataDownload() {
        String[] params = new String[1];

        try {
            // Decrypt EmpCode
            params[0] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        GetMasterData_IssueToMeterFixer getMasterData_issueToMeterFixer = new GetMasterData_IssueToMeterFixer();
        getMasterData_issueToMeterFixer.execute(params);
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

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MMG_IssuetoMeterFixer_MasterDataModel mmg_issuetoMeterFixer_masterDataModel =
                        gson.fromJson(JsonMMGMasterData, MMG_IssuetoMeterFixer_MasterDataModel.class);
                if (mmg_issuetoMeterFixer_masterDataModel != null) {

                    realmOperations.deleteRoadcuttingTable();
                    for (MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel1 : mmg_issuetoMeterFixer_masterDataModel.getRoadcutting()) {
                        MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel2 = new MMGTypeOfRoadcuttingModel(mmgTypeOfRoadcuttingModel1.getRC_ID(), mmgTypeOfRoadcuttingModel1.getRC_DESC());
                        realmOperations.addRoadcutting(mmgTypeOfRoadcuttingModel2);
                    }

                    realmOperations.deleteExcavationTable();
                    for (MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel1 : mmg_issuetoMeterFixer_masterDataModel.getExcavation()) {
                        MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel2 = new MMGSaddleAndPitExcavModel(mmgSaddleAndPitExcavModel1.getEC_ID(), mmgSaddleAndPitExcavModel1.getEC_DESC());
                        realmOperations.addExcavation(mmgSaddleAndPitExcavModel2);
                    }
                    realmOperations.deleteMakerCodeTable();
                    for (MMGMakerCodeModel mmgMakerCodeModel1 : mmg_issuetoMeterFixer_masterDataModel.getMakerCode()) {
                        MMGMakerCodeModel mmgMakerCodeModel2 = new MMGMakerCodeModel(mmgMakerCodeModel1.getMMFG_MFGCODE(), mmgMakerCodeModel1.getMMFG_MFGNAME(), mmgMakerCodeModel1.getMMFG_MATERIAL_TYPE());
                        realmOperations.addMakerCode(mmgMakerCodeModel2);
                    }
                    realmOperations.deleteMeterSizeTable();
                    for (MMGMeterSizeModel mmgMeterSizeModel1 : mmg_issuetoMeterFixer_masterDataModel.getMeterSizeModel()) {
                        MMGMeterSizeModel mmgMeterSizeModel2 = new MMGMeterSizeModel(mmgMeterSizeModel1.getMCS_ID(), mmgMeterSizeModel1.getCONNSIZEMM());
                        realmOperations.addMeterSize(mmgMeterSizeModel2);
                    }

                    realmOperations.deleteRAMPRRTable();
                    for (MMGRampAndRRModel mmgRampAndRRModel1 : mmg_issuetoMeterFixer_masterDataModel.getRAMPRR()) {
                        MMGRampAndRRModel mmgRampAndRRModel2 = new MMGRampAndRRModel(mmgRampAndRRModel1.getRRR_ID(), mmgRampAndRRModel1.getRRR_DESC());
                        realmOperations.addRAMPRR(mmgRampAndRRModel2);
                    }

                    realmOperations.deleteMeterTypeTable();
                    for (MMGMeterTypeModel mmgMeterTypeModel1 : mmg_issuetoMeterFixer_masterDataModel.getMeterType()) {
                        MMGMeterTypeModel mmgMeterTypeModel2 = new MMGMeterTypeModel(mmgMeterTypeModel1.getMTC_METERTYPE_CODE(), mmgMeterTypeModel1.getMTC_TYPEDESC());
                        realmOperations.addMeterType(mmgMeterTypeModel2);

                    }

                    realmOperations.deleteWallBoringTable();
                    for (MMGWallBoringModel mmgWallBoringModel1 : mmg_issuetoMeterFixer_masterDataModel.getWallBoring()) {
                        MMGWallBoringModel mmgWallBoringModel2 = new MMGWallBoringModel(mmgWallBoringModel1.getWB_ID(), mmgWallBoringModel1.getWB_DESC());
                        realmOperations.addWallBoring(mmgWallBoringModel2);
                    }




                    realmOperations.deleteCGRTable();
                    for (MMGCgRestroModel mmgCgRestroModel1 : mmg_issuetoMeterFixer_masterDataModel.getCGR()) {
                        MMGCgRestroModel mmgCgRestroModel2 = new MMGCgRestroModel(mmgCgRestroModel1.getCGR_ID(), mmgCgRestroModel1.getCGR_DESC());
                        realmOperations.addCGR(mmgCgRestroModel2);

                    }



                    realmOperations.deleteFCRTable();
                    for (MMGFcRestroModel mmgFcRestroModel1 : mmg_issuetoMeterFixer_masterDataModel.getFCR()) {
                        MMGFcRestroModel mmgFcRestroModel2 = new MMGFcRestroModel(mmgFcRestroModel1.getFCR_ID(), mmgFcRestroModel1.getFCR_DESC());
                        realmOperations.addFCR(mmgFcRestroModel2);

                    }



                    realmOperations.deleteMaterialDetailsTable();
                    for (MMGMaterialDetailsModel mmgMaterialDetailsModel1 : mmg_issuetoMeterFixer_masterDataModel.getMaterialDetails()) {
                        MMGMaterialDetailsModel mmgMaterialDetailsModel2 = new MMGMaterialDetailsModel(mmgMaterialDetailsModel1.getMM_ID(), mmgMaterialDetailsModel1.getMM_NAME(), mmgMaterialDetailsModel1.getM_UNIT(), mmgMaterialDetailsModel1.getMM_DEF_QTY(), mmgMaterialDetailsModel1.getSIZEID());
                        realmOperations.addMaterialDetails(mmgMaterialDetailsModel2);

                    }


                    realmOperations.deleteIssueToMeterFixrZoneTable();
                    for (MMGZoneModel MMGZoneModel : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterZones()) {
                        int id = MMGZoneModel.getBUM_BU_ID();
                        MMGZoneModel MMGZoneModelData = new MMGZoneModel(MMGZoneModel.getBU_NAME(), id);
                        realmOperations.addIssueToMeterFixrZone(MMGZoneModelData);

                    }



                    realmOperations.deleteIssueToMeterSubZoneTable();
                    for (MMGSubZoneModel MMGSubZoneModel : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterSubZones()) {
                        int id = (MMGSubZoneModel.getPCM_PC_ID());
                        int buId = (MMGSubZoneModel.getPCM_BU_ID());
                        MMGSubZoneModel MMGSubZoneModelData = new MMGSubZoneModel(id, MMGSubZoneModel.getPCM_PC_NAME(), buId);
                        realmOperations.addIssueToMeterSubZone(MMGSubZoneModelData);

                    }

                    // Insert Request Type  in RequestTypeModel Table


                    realmOperations.deleteIssueToMeterFixrRequestTypeTable();
                    for (MMGRequestTypeModel MMGRequestTypeModel1 : mmg_issuetoMeterFixer_masterDataModel.getissueToMeterRequstType()) {

                        if (MMGRequestTypeModel1.getAllVal()!=null && !MMGRequestTypeModel1.getSelectVal().equalsIgnoreCase("Select")) {
                            MMGRequestTypeModel MMGRequestTypeModel2 = new MMGRequestTypeModel(MMGRequestTypeModel1.getSelectVal(), MMGRequestTypeModel1.getAllVal());
                            realmOperations.addIssueToMeterFixrRequestType(MMGRequestTypeModel2);
                        }
                    }



                    realmOperations.deleteObservationTable();
                    for (MMGObersvationModel mmgObersvationModel1 : mmg_issuetoMeterFixer_masterDataModel.getObersvation()) {
                        MMGObersvationModel mmgMeterPrefixModel2 = new MMGObersvationModel(mmgObersvationModel1.getOCRM_ID(), mmgObersvationModel1.getOCRM_NAME());
                        realmOperations.addObservation(mmgMeterPrefixModel2);
                    }




                    realmOperations.deleteMStatusObservationTable();
                    for (MStatusObservationModel mStatusObservationModel1 : mmg_issuetoMeterFixer_masterDataModel.getMStatusObservation()) {
                        MStatusObservationModel mmgMeterPrefixModel22 = new MStatusObservationModel(mStatusObservationModel1.getMSNM_MSTATUS_ID(), mStatusObservationModel1.getMSNM_MSUBSTATUS_ID(), mStatusObservationModel1.getMSNM_MSUBSTATUS_NAME());
                        realmOperations.addMStatusObservation(mmgMeterPrefixModel22);
                    }



                    realmOperations.deleteMtrLocationTable();
                    for (MMGMeterLocationModel mmgMeterLocationModel37 : mmg_issuetoMeterFixer_masterDataModel.getMeterLocation()) {
                        MMGMeterLocationModel mmgMeterLocationModel2 = new MMGMeterLocationModel(mmgMeterLocationModel37.getML_ID(), mmgMeterLocationModel37.getML_DESC());
                        realmOperations.addMtrLocation(mmgMeterLocationModel2);
                    }




                    realmOperations.deleteMeterStatusTable();
                    for (MMGMeterStatusModel mmgMeterStatusModel1 : mmg_issuetoMeterFixer_masterDataModel.getMeterStatus()) {
                        MMGMeterStatusModel mmgMeterStatusModel2 = new MMGMeterStatusModel(mmgMeterStatusModel1.getMSM_METERSTATUS_ID(), mmgMeterStatusModel1.getMSM_METERSTATUS_NAME(), mmgMeterStatusModel1.getFINALMETERSTATUSTAG());
                        realmOperations.addMeterStatusDetails(mmgMeterStatusModel2);
                    }



                    realmOperations.deletVendorTable();
                    for (MMGVendorDetModel mmgVendorDetModel1 : mmg_issuetoMeterFixer_masterDataModel.getVendor()) {
                        MMGVendorDetModel mmgVendorDetModel2 = new MMGVendorDetModel(mmgVendorDetModel1.getEM_EMP_CODE(), mmgVendorDetModel1.getNAME(), mmgVendorDetModel1.getEM_EMAIL(), mmgVendorDetModel1.getEM_PHONEM(), mmgVendorDetModel1.getEM_DESIGNATION(), mmgVendorDetModel1.getDGM_DES_NAME());
                        realmOperations.addVendorDet(mmgVendorDetModel2);
                    }



                    realmOperations.deleteMSRTable();
                    for (MSRModel mMsrModel1 : mmg_issuetoMeterFixer_masterDataModel.getMSR()) {
                        MSRModel msrModel22 = new MSRModel(mMsrModel1.getSBM_ID(), mMsrModel1.getSBM_NAME());
                        realmOperations.addMSR(msrModel22);
                    }


                    realmOperations.deleteSRTable();
                    for (SRModel srModel1 : mmg_issuetoMeterFixer_masterDataModel.getSR()) {
                        SRModel srModel22 = new SRModel(srModel1.getTRM_ID(), srModel1.getTRM_NAME(), srModel1.getMSRID());
                        realmOperations.addSR(srModel22);
                    }

                    realmOperations.deleteDMATable();
                    for (DMAModel dmaModel1 : mmg_issuetoMeterFixer_masterDataModel.getDMA()) {
                        DMAModel dmaModel2 = new DMAModel(dmaModel1.getPM_ID(), dmaModel1.getPM_NAME(), dmaModel1.getSRID());
                        realmOperations.addDMA(dmaModel2);
                    }



                    realmOperations.deletContEmpTable();
                    for (MMGContEmpModel mmgContEmpModel : mmg_issuetoMeterFixer_masterDataModel.getContEmp()) {
                        MMGContEmpModel mmgVendorDetModel2 = new MMGContEmpModel(mmgContEmpModel.getEM_EMP_CODE(), mmgContEmpModel.getNAME(), mmgContEmpModel.getEM_EMAIL(), mmgContEmpModel.getEM_PHONEM(), mmgContEmpModel.getEM_DESIGNATION(), mmgContEmpModel.getDGM_DES_NAME(), mmgContEmpModel.getEM_VENDOR_ID());
                        realmOperations.addContEmpDet(mmgVendorDetModel2);
                    }


                    realmOperations.deletCvlMesurementTable();
                    for (MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel1 : mmg_issuetoMeterFixer_masterDataModel.getCivilDetails()) {
                        MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel2 = new MMGCvlMeasurementResponseModel(mmgCvlMeasurementResponseModel1.getSLNO(), mmgCvlMeasurementResponseModel1.getMCD_MATERIAL_ID(), mmgCvlMeasurementResponseModel1.getMCD_MATERIAL_NAME(), mmgCvlMeasurementResponseModel1.getMCD_ISDROPDOWN(), mmgCvlMeasurementResponseModel1.getMCD_ISQUANTITY(), mmgCvlMeasurementResponseModel1.getDDLID(), mmgCvlMeasurementResponseModel1.getQUANTITY(), mmgCvlMeasurementResponseModel1.getLENGTH(), mmgCvlMeasurementResponseModel1.getWIDTH(), mmgCvlMeasurementResponseModel1.getDEPTH());
                        realmOperations.addCvlMesurementDet(mmgCvlMeasurementResponseModel2);
                    }



                    realmOperations.deletDialDigitTable();
                    for (MDialDigitModel mDialDigitModel1 : mmg_issuetoMeterFixer_masterDataModel.getDialDigit()) {
                        MDialDigitModel mDialDigitModel2 = new MDialDigitModel(mDialDigitModel1.getDIGITTEXT(), mDialDigitModel1.getDIGITID());
                        realmOperations.addDialDigit(mDialDigitModel2);
                    }

                    // ComplaintByModel mComplaintModel = realmOperations.getDialDigitExists();
//                realmOperations.deletDialDigitTable();
//                        for (ComplaintByModel complaintByModel : mmg_issuetoMeterFixer_masterDataModel.getDialDigit()) {
//                            ComplaintByModel complaintByModel1   = new ComplaintByModel(complaintByModel.getDIGITTEXT(),complaintByModel.getDIGITID());
//                            realmOperations.addDialDigit(complaintByModel1);
//                        }


                    // Insert Finish Action in FinishActionModel Table
                    mmgMasterProgress.dismiss();
                }
                mmgMasterProgress.dismiss();
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid_MMG", error);
            }
        }


    }
    private int stringToInt(String strId) {
        // return Integer.parseInt(strId);
        try {
            return Integer.parseInt(strId);
        } catch (Exception e) {
            return (int) Double.parseDouble(strId);
        }
    }

}
