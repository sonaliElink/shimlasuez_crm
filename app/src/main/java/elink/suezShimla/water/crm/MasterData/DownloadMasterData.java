package elink.suezShimla.water.crm.MasterData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.ActionFormModel;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadActionCRM;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintSource;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintSubType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadFinishAction;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSiteEngineer;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSubZone;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadZone;
import elink.suezShimla.water.crm.Login.MasterData.MasterDataModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSourceModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.FinishActionModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SiteEngineerModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class DownloadMasterData {

    private String jsonMasterDataResponse = "",employeeIdStr="",sessionid="";
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;
    private MaterialDialog masterProgress;
    Context mCon;
    private int actionNextId;

    public void downloadData(Context mCon) {
        realmOperations = new RealmOperations(mCon);

        gson = new Gson();
        ConnectionDetector connection = new ConnectionDetector(mCon);
        invServices = new Invoke();



        ComplaintTypeModel complaintTypeModelExist = realmOperations.getComplaintTypeExist();
        ComplaintSubTypeModel complaintSubTypeModelExist = realmOperations.getComplaintSubTypeExist();
        ComplaintSourceModel complaintSourceModelExist = realmOperations.getComplaintSourceExist();
        ZoneModel zoneModelExist = realmOperations.getZoneExist();
        SubZoneModel subZoneModelExist = realmOperations.getSubZoneExist();
        SiteEngineerModel siteEngineerModelExist = realmOperations.getSiteEngineerExist();
        FinishActionModel finishActionModelExist = realmOperations.getFinishActionExist();
        ActionFormModel actionFormModelExist = realmOperations.getActionFormExist();


        MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                .title(mCon.getResources().getString(R.string.master_data_missing))
                .content(mCon.getResources().getString(R.string.download_master_data))
                .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                .canceledOnTouchOutside(false)
                .positiveText(mCon.getResources().getString(R.string.yes))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //dialog.dismiss();

                        String empcode = null;
                        try {
                            // Decrypt EmpCode
                            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
                            sessionid=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.SID));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (complaintTypeModelExist == null || complaintSubTypeModelExist == null || complaintSourceModelExist == null || zoneModelExist == null
                                || subZoneModelExist == null || siteEngineerModelExist == null || finishActionModelExist == null || actionFormModelExist == null) {
                            String[] params = new String[2];
                            params[0] = empcode;
                            params[1]=sessionid;
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
                        }
                    }
                })
                .negativeText(mCon.getResources().getString(R.string.cancel))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public DownloadMasterData(){
        //No parameter constructor
    }

    public DownloadMasterData(Context mCon){
        this.mCon = mCon;
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadMaster extends AsyncTask<String, Void, Void> {




        @Override
        protected Void doInBackground(String... params) {

            try {
                String paraNames[] = new String[2];
                paraNames[0] = "EmpCode";
                paraNames[1] = "SessionToken";
                String username=null,password=null;
                username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE));
                password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                jsonMasterDataResponse = invServices.getOtherData(Constants.URL, Constants.NameSpace, Constants.MasterData,username,password, params, paraNames);

              // jsonMasterDataResponse = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.MasterData);

            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MasterDataModel masterDataModel = gson.fromJson(jsonMasterDataResponse, MasterDataModel.class);

                if (masterDataModel != null) {



                    // Insert Complaint Type in ComplaintTypeModel Table
                    ComplaintTypeModel complaintTypeModelExist = realmOperations.getComplaintTypeExist();
                    if (complaintTypeModelExist == null) {

                        for (DownloadComplaintType downloadComplaintType : masterDataModel.getDownloadComplaintTypes()) {
                            int id = stringToInt(downloadComplaintType.getCMTM_ID());
//                            ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel(id, downloadComplaintType.getCMTM_CODE(), downloadComplaintType.getCMTM_NAME());
                            ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel(id, downloadComplaintType.getCMTM_CODE(), downloadComplaintType.getCMTM_NAME()
                                , downloadComplaintType.getREQUEST(), downloadComplaintType.getNOCONSUMER(), downloadComplaintType.getDEPTID(), downloadComplaintType.getCOMPLAINT(),downloadComplaintType.getENQUIRY());
                            realmOperations.addComplaintType(complaintTypeModel);
                        }
                    }

                    // Insert Complaint Sub Type in ComplaintSubTypeModel Table
                    ComplaintSubTypeModel complaintSubTypeModelExist = realmOperations.getComplaintSubTypeExist();
                    if (complaintSubTypeModelExist == null) {

                        for (DownloadComplaintSubType downloadComplaintSubType : masterDataModel.getDownloadComplaintSubTypes()) {
                            int id = stringToInt(downloadComplaintSubType.getCOMPLAINTSUBTYPEID());
                            int compId = stringToInt(downloadComplaintSubType.getCOMPLAINTTYPEID());
                            ComplaintSubTypeModel complaintSubTypeModel = new ComplaintSubTypeModel(id, downloadComplaintSubType.getCOMPLAINTSUBTYPENAME(), compId,downloadComplaintSubType.getCOMPLAINT(),downloadComplaintSubType.getREQUEST());
                            realmOperations.addComplaintSubType(complaintSubTypeModel);
                        }
                    }

                    // Insert Complaint Source in ComplaintSourceModel Table
                    ComplaintSourceModel complaintSourceModelExist = realmOperations.getComplaintSourceExist();
                    if (complaintSourceModelExist == null) {
                        for (DownloadComplaintSource downloadComplaintSource : masterDataModel.getDownloadComplaintSources()) {
                            int id = stringToInt(downloadComplaintSource.getCSM_SOURCECODE());
                            ComplaintSourceModel complaintSourceModel = new ComplaintSourceModel(id, downloadComplaintSource.getCSM_SOURCEDESC());
                            realmOperations.addComplaintSource(complaintSourceModel);
                        }
                    }

                    // Insert Zone in MMGZoneModel Table
                    ZoneModel zoneModelExist = realmOperations.getZoneExist();
                    if (zoneModelExist == null) {
                        for (DownloadZone downloadZone : masterDataModel.getDownloadZones()) {
                            int id = stringToInt(downloadZone.getBUM_BU_ID());
                            ZoneModel zoneModel = new ZoneModel(downloadZone.getBU_NAME(), id);
                            realmOperations.addZone(zoneModel);
                        }
                    }

                    // Insert Zone in MMGZoneModel Table
                    SubZoneModel subZoneModelExist = realmOperations.getSubZoneExist();
                    if (subZoneModelExist == null) {

                        for (DownloadSubZone downloadSubZone : masterDataModel.getDownloadSubZones()) {
                            int id = stringToInt(downloadSubZone.getPCM_PC_ID());
                            int buId = stringToInt(downloadSubZone.getPCM_BU_ID());
                            SubZoneModel subZoneModel = new SubZoneModel(id, downloadSubZone.getPCM_PC_NAME(), buId);
                            realmOperations.addSubZone(subZoneModel);
                        }
                    }

                    // Insert Site Engineer in SiteEngineerModel Table
                    SiteEngineerModel siteEngineerModelExist = realmOperations.getSiteEngineerExist();
                    if (siteEngineerModelExist == null) {

                        for (DownloadSiteEngineer downloadSiteEngineer : masterDataModel.getDownloadSiteEngineers()) {
                            SiteEngineerModel siteEngineerModel = new SiteEngineerModel(downloadSiteEngineer.getEMPLOYEE_CODE(), downloadSiteEngineer.getEMPLOYEE_NAME(),
                                    false, downloadSiteEngineer.getEM_ZONE(), downloadSiteEngineer.getEM_DEPT());
                            realmOperations.addSiteEngineer(siteEngineerModel);
                        }
                    }
                    // Insert Finish Action in FinishActionModel Table
                    FinishActionModel finishActionModelExist = realmOperations.getFinishActionExist();
                    if (finishActionModelExist == null) {

                        for (DownloadFinishAction downloadFinishAction : masterDataModel.getDownloadFinishActions()) {
                            int id = stringToInt(downloadFinishAction.getCSCM_ID());
                            FinishActionModel finishActionModel = new FinishActionModel(id, downloadFinishAction.getCSCM_SECNAME(), downloadFinishAction.getFILTER());
                            realmOperations.addFinishAction(finishActionModel);
                        }
                    }
                    ActionFormModel actionFormModel = realmOperations.getActionFormExist();

                    if (actionFormModel == null) {

                        for (DownloadActionCRM downloadActionCRM : masterDataModel.getActionCRM()) {
                            ActionFormModel actionModel = new ActionFormModel( downloadActionCRM.getA_NAME());
                            realmOperations.addActionForm(actionModel);
                        }
                    }

                    masterProgress.dismiss();
                    masterProgress.cancel();

                }

            }catch (Exception e){

                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintHistory", "filterButton", error);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
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
