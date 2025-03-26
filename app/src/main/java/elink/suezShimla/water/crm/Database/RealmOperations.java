package elink.suezShimla.water.crm.Database;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ChangePassword.Model.ChangePinModel;
import elink.suezShimla.water.crm.Complaint.MapScreen.Model.LatLongModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.MeterNumberModel;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadAdjustmentTypeTable;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadMeterStatusBillAdjustTable;
import elink.suezShimla.water.crm.ErrorModel.ErrorLogModel;
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
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterConditionModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterLocationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterPrefixModel;
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
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterObservationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterStatusModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.SRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.WorkCompObservationModel;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadConnCategory;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadConnSize;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadCustomerType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSourceType;
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
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Model.FixerDataListModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6.ContractorDetailAnnex6;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6.CustomerDetailsAnnex6;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6.NewMeterDetailAnnex6;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6.OldMeterDetailAnnex6;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMaterialResponseModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model.NCMeterNumberModel;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.BankMasterModel;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.ConnectionStatusModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.AreaModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.ConnCategoryModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.ConnPurposeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.DwellingUnitModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.LotModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.PropertyTypeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RejectModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadOwnershipModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadRestorationLenRoadModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadTypeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.SizeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.WardModel;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmOperations {

    private final Realm realm;

    public RealmOperations(Context mCon) {
        realm = Realm.getDefaultInstance();
    }

    public RealmOperations(Realm realm) {
        this.realm = realm;
    }

    public void close() {
        realm.close();
    }


    // ----------------------  ComplaintTypeTable ----------------------

    public ComplaintTypeModel getComplaintTypeExist() {
        return realm.where(ComplaintTypeModel.class).findFirst();
    }

    public DownloadAdjustmentTypeTable getDownloadAdjustmentTypeExist() {
        return realm.where(DownloadAdjustmentTypeTable.class).findFirst();
    }

    public void addComplaintType(ComplaintTypeModel complaintTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(complaintTypeModel);
            }
        });
    }


    public DownloadCustomerType getCustomername(String id) {
        return realm.where(DownloadCustomerType.class).equalTo("CUSTTYPEID", id).findFirst();
    }
    public void addAdjustmentTypeType(DownloadAdjustmentTypeTable downloadAdjustmentTypeTable) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(downloadAdjustmentTypeTable);
            }
        });
    }

    public ComplaintTypeModel fetchComplaintTypeByName(String name) {
        return realm.where(ComplaintTypeModel.class).equalTo("CMTM_NAME", name).findFirst();
    }

    public RejectModel fetchRejectTypeByName(String name) {
        return realm.where(RejectModel.class).equalTo("REM_REASONNM", name).findFirst();
    }

    public ComplaintTypeModel fetchComplaintTypeByNameRegistration(String name) {
        return realm.where(ComplaintTypeModel.class).equalTo("COMPLAINT", name).findFirst();
    }

    public DocSourceModel fetchDocSourceTypeByName(String name) {
        return realm.where(DocSourceModel.class).equalTo("DOCSOURCECODE", name).findFirst();
    }


    public ComplaintTypeModel fetchComplaintTypeModelById(String name) {
        return realm.where(ComplaintTypeModel.class).equalTo("CMTM_ID", name).findFirst();
    }


    public ArrayList<String> fetchComplaintType() {
        ArrayList<String> ComplaintList = new ArrayList<>();

        RealmResults<ComplaintTypeModel> complaintTypeModels = realm.where(ComplaintTypeModel.class).sort("CMTM_NAME", Sort.ASCENDING).findAll();

        for (ComplaintTypeModel model : complaintTypeModels) {
            ComplaintList.add(model.getCMTM_NAME());

        }
        return ComplaintList;
    }

    public ArrayList<String> fetchComplaintTypeRegistrationList(String typeID, String serviceType) {

        ArrayList<String> ComplaintList = new ArrayList<>();
        RealmResults<ComplaintTypeModel> complaintTypeModels = null;
        if (serviceType.equalsIgnoreCase("C")) {
            complaintTypeModels = realm.where(ComplaintTypeModel.class).equalTo("COMPLAINT", typeID).sort("CMTM_NAME", Sort.ASCENDING).findAll();
        }
        if (serviceType.equalsIgnoreCase("R")) {
            complaintTypeModels = realm.where(ComplaintTypeModel.class).equalTo("REQUEST", typeID).sort("CMTM_NAME", Sort.ASCENDING).findAll();
        }
        if (serviceType.equalsIgnoreCase("Q")) {
            complaintTypeModels = realm.where(ComplaintTypeModel.class).equalTo("ENQUIRY", typeID).sort("CMTM_NAME", Sort.ASCENDING).findAll();
        }
        for (ComplaintTypeModel model : complaintTypeModels) {
            ComplaintList.add(model.getCMTM_NAME());

        }
        return ComplaintList;
    }

    public ArrayList<String> fetchComplaintTypeByDept(String deptid) {
        ArrayList<String> ComplaintList = new ArrayList<>();

        RealmResults<ComplaintTypeModel> complaintTypeModels = realm.where(ComplaintTypeModel.class).equalTo("DEPTID", deptid).findAll();

        for (ComplaintTypeModel model : complaintTypeModels) {
            ComplaintList.add(model.getCMTM_NAME());

        }
        return ComplaintList;
    }

    public ArrayList<String> fetchComplaintTypeDeptID(String deptid) {
        ArrayList<String> ComplaintList = new ArrayList<>();

        RealmResults<ComplaintTypeModel> complaintTypeModels = realm.where(ComplaintTypeModel.class).equalTo("DEPTID", deptid).findAll();

        for (ComplaintTypeModel model : complaintTypeModels) {
            ComplaintList.add(model.getCMTM_CODE());

        }
        return ComplaintList;
    }


    public ArrayList<String> fetchComplaintType(String req) {
        ArrayList<String> ComplaintList = new ArrayList<>();

        RealmResults<ComplaintTypeModel> complaintTypeModels = realm.where(ComplaintTypeModel.class).equalTo("REQUEST", req).findAll();

        for (ComplaintTypeModel model : complaintTypeModels) {
            ComplaintList.add(model.getCMTM_NAME());

        }
        return ComplaintList;
    }

    public ArrayList<String> fetchComplaintTypeWC() {
        ArrayList<String> ComplaintList = new ArrayList<>();

        RealmResults<ComplaintTypeModel> complaintTypeModels = realm.where(ComplaintTypeModel.class).findAll();

        for (ComplaintTypeModel model : complaintTypeModels) {
            ComplaintList.add(model.getCMTM_NAME());

        }
        ComplaintList.remove(6);
        return ComplaintList;
    }

    public ArrayList<String> fetchComplaintTypeBySE() {
        ArrayList<String> ComplaintList = new ArrayList<>();

        RealmResults<ComplaintTypeModel> complaintTypeModels = realm.where(ComplaintTypeModel.class).findAll();

        for (ComplaintTypeModel model : complaintTypeModels) {
            ComplaintList.add(model.getCMTM_NAME());

        }
        ComplaintList.remove(4);
        ComplaintList.remove(4);
        ComplaintList.remove(4);
        ComplaintList.remove(4);
        ComplaintList.remove(4);
        return ComplaintList;
    }

    public ArrayList<String> fetchComplaintTypeForNC() {
        ArrayList<String> ComplaintList = new ArrayList<>();

        RealmResults<ComplaintTypeModel> complaintTypeModels = realm.where(ComplaintTypeModel.class).equalTo("NOCONSUMER", "1").findAll();

        for (ComplaintTypeModel model : complaintTypeModels) {
            ComplaintList.add(model.getCMTM_NAME());

        }

        return ComplaintList;
    }

    public void deleteComplaintTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ComplaintTypeModel.class);
            }
        });
    }

    public void insertLoginData(final SiteEngineerModel siteEngineerModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                //realm.copyToRealmOrUpdate(employeeLoginModels);
                realm.insert(siteEngineerModel);
            }
        });
    }

    // ----------------------  ComplaintSubTypeTable ----------------------

    public ComplaintSubTypeModel getComplaintSubTypeExist() {
        return realm.where(ComplaintSubTypeModel.class).findFirst();
    }

    public DownloadMeterStatusBillAdjustTable getDownloadMeterStatusBillAdjustTableExist() {
        return realm.where(DownloadMeterStatusBillAdjustTable.class).findFirst();
    }

    public void addComplaintSubType(ComplaintSubTypeModel complaintTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(complaintTypeModel);
            }
        });
    }

    public void addDownloadMeterStatusBillAdjiustmentType(DownloadMeterStatusBillAdjustTable downloadMeterStatusBillAdjustTable) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(downloadMeterStatusBillAdjustTable);
            }
        });
    }

    public ComplaintSubTypeModel fetchComplaintSubTypeByID(int id) {
        return realm.where(ComplaintSubTypeModel.class).equalTo("COMPLAINTSUBTYPEID", id).findFirst();
    }

    public SiteEngineerModel fetchDataReadingByImei(String imei) {
        return realm.where(SiteEngineerModel.class).equalTo("deviceImei", imei).findFirst();
    }

    public ArrayList<String> fetchComplaintSubType(int id) {
        ArrayList<String> complaintSubList = new ArrayList<>();
        RealmResults<ComplaintSubTypeModel> complaintSubTypeModels = realm.where(ComplaintSubTypeModel.class).equalTo("COMPLAINTTYPEID", id).findAll();

        for (ComplaintSubTypeModel model : complaintSubTypeModels) {
            complaintSubList.add(model.getCOMPLAINTSUBTYPENAME());
        }
        return complaintSubList;
    }


    public ArrayList<String> fetchWorkObservation(String actionId, String complaintId) {
        ArrayList<String> workCompObservationList = new ArrayList<>();
        RealmResults<WorkCompObservationModel> workCompObservationModels = realm.where(WorkCompObservationModel.class).equalTo("ACTIONID", actionId).equalTo("COMPTYPEID", complaintId).findAll();

        for (WorkCompObservationModel model : workCompObservationModels) {

            workCompObservationList.add(model.getOBSERVATION());

        }
        return workCompObservationList;
    }


    public RealmResults<ComplaintSubTypeModel> fetchComplaintSubTypeObservation(int id) {
        return realm.where(ComplaintSubTypeModel.class).equalTo("COMPLAINTTYPEID", id).findAll();
    }

    public RealmResults<MStatusObservationModel> fetchMeterObservation(String id) {
        return realm.where(MStatusObservationModel.class).equalTo("MSNM_MSTATUS_ID", id).findAll();
    }


    public ArrayList<String> fetchComplaintSubTypeforSE(int id) {
        ArrayList<String> complaintSubList = new ArrayList<>();
        RealmResults<ComplaintSubTypeModel> complaintSubTypeModels = realm.where(ComplaintSubTypeModel.class).equalTo("COMPLAINTTYPEID", id).findAll();

        for (ComplaintSubTypeModel model : complaintSubTypeModels) {
            complaintSubList.add(model.getCOMPLAINTSUBTYPENAME());
        }
        return complaintSubList;
    }

    public ComplaintSubTypeModel fetchComplaintSubTypeByName(String name) {
        return realm.where(ComplaintSubTypeModel.class).equalTo("COMPLAINTSUBTYPENAME", name).findFirst();
    }

    public WorkCompObservationModel fetchWorkObservationTypeByName(String name) {
        return realm.where(WorkCompObservationModel.class).equalTo("OBSERVATION", name).sort("OBSERVATION", Sort.ASCENDING).findFirst();
    }

    public void deleteComplaintSubTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ComplaintSubTypeModel.class);
            }
        });
    }


    // ----------------------  ComplaintSourceTable ----------------------

    public ComplaintSourceModel getComplaintSourceExist() {
        return realm.where(ComplaintSourceModel.class).findFirst();
    }

    public void addComplaintSource(ComplaintSourceModel complaintTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(complaintTypeModel);
            }
        });
    }

    public ComplaintSourceModel fetchComplaintSourceByName(String name) {
        return realm.where(ComplaintSourceModel.class).equalTo("CSM_SOURCEDESC", name).findFirst();
    }

    public ArrayList<String> fetchComplaintSource() {
        ArrayList<String> complaintSourceList = new ArrayList<>();
        RealmResults<ComplaintSourceModel> complaintSourceModels = realm.where(ComplaintSourceModel.class).findAll();

        for (ComplaintSourceModel model : complaintSourceModels) {
            complaintSourceList.add(model.getCSM_SOURCEDESC());
        }
        return complaintSourceList;
    }

    public ArrayList<String> fetchComplaintSourceByType(String id) {
        ArrayList<String> complaintByList = new ArrayList<>();
        RealmResults<ComplaintSourceModel> complaintByModels = realm.where(ComplaintSourceModel.class).equalTo("CSM_SOURCE_TYPE", id).findAll();

        for (ComplaintSourceModel model : complaintByModels) {
            complaintByList.add(model.getCSM_SOURCEDESC());
        }
        return complaintByList;
    }

    public void deleteComplaintSourceTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ComplaintSourceModel.class);
            }
        });
    }


//    public ComplaintSubTypeModel fetchComplaintSubTypeByType(String subTypeId) {
//        return realm.where(ComplaintSubTypeModel.class).equalTo("CSM_SOURCECODE", subTypeId).findFirst();
//    }

    public ArrayList<String> fetchComplaintSubTypeByType(int categoryTypeId, String serviceType) {

        ArrayList<String> complaintByList = new ArrayList<>();
        RealmResults<ComplaintSubTypeModel> complaintByModels = null;
        RealmResults<ComplaintSubTypeModel> complaintByModels1 = null;
        RealmResults<ComplaintSubTypeModel> complaintByModels2 = null;

        if (serviceType.equalsIgnoreCase("C")) {
            complaintByModels = realm.where(ComplaintSubTypeModel.class)
                    .equalTo("COMPLAINT", "C").equalTo("COMPLAINTTYPEID", categoryTypeId)
                    .sort("COMPLAINTSUBTYPENAME", Sort.ASCENDING)
                    .findAll();
            complaintByModels1 = realm.where(ComplaintSubTypeModel.class)
                    .equalTo("COMPLAINT", "CR").equalTo("COMPLAINTTYPEID", categoryTypeId)
                    .sort("COMPLAINTSUBTYPENAME", Sort.ASCENDING)
                    .findAll();
            complaintByModels2 = realm.where(ComplaintSubTypeModel.class)
                    .equalTo("COMPLAINT", "CRQ").equalTo("COMPLAINTTYPEID", categoryTypeId)
                    .sort("COMPLAINTSUBTYPENAME", Sort.ASCENDING)
                    .findAll();

        } else if (serviceType.equalsIgnoreCase("R")) {
            complaintByModels = realm.where(ComplaintSubTypeModel.class)
                    .equalTo("COMPLAINT", "R").equalTo("COMPLAINTTYPEID", categoryTypeId)
                    .sort("COMPLAINTSUBTYPENAME", Sort.ASCENDING)
                    .findAll();
            complaintByModels1 = realm.where(ComplaintSubTypeModel.class)
                    .equalTo("COMPLAINT", "CR").equalTo("COMPLAINTTYPEID", categoryTypeId)
                    .sort("COMPLAINTSUBTYPENAME", Sort.ASCENDING)
                    .findAll();
            complaintByModels2 = realm.where(ComplaintSubTypeModel.class)
                    .equalTo("COMPLAINT", "CRQ").equalTo("COMPLAINTTYPEID", categoryTypeId)
                    .sort("COMPLAINTSUBTYPENAME", Sort.ASCENDING)
                    .findAll();
        }

        if (complaintByModels != null) {
            for (ComplaintSubTypeModel model : complaintByModels) {
                complaintByList.add(model.getCOMPLAINTSUBTYPENAME());
            }
        }
        if (complaintByModels1 != null) {
            for (ComplaintSubTypeModel model : complaintByModels1) {
                complaintByList.add(model.getCOMPLAINTSUBTYPENAME());
            }
        }
        if (complaintByModels2 != null) {
            for (ComplaintSubTypeModel model : complaintByModels2) {
                complaintByList.add(model.getCOMPLAINTSUBTYPENAME());
            }
        }
        return complaintByList;
    }


    public ArrayList<String> fetchComplaintSubtypeList(int complaintTypeId, String serviceType) {
        ArrayList<String> complaintSubtypeList = new ArrayList<>();
        RealmResults<ComplaintSubTypeModel> complaintSubTypeModels = null;
        if (serviceType.equalsIgnoreCase("C")) {
            complaintSubTypeModels = realm.where(ComplaintSubTypeModel.class).equalTo("COMPLAINT", serviceType).equalTo("COMPLAINTTYPEID", complaintTypeId).sort("COMPLAINTSUBTYPENAME", Sort.ASCENDING).findAll();
        }
        if (serviceType.equalsIgnoreCase("R")) {
            complaintSubTypeModels = realm.where(ComplaintSubTypeModel.class).equalTo("REQUEST", serviceType).equalTo("COMPLAINTTYPEID", complaintTypeId).sort("COMPLAINTSUBTYPENAME", Sort.ASCENDING).findAll();
        }
        for (ComplaintSubTypeModel model : complaintSubTypeModels) {

            complaintSubtypeList.add(model.getCOMPLAINTSUBTYPENAME());

        }
        return complaintSubtypeList;
    }


    // ----------------------  IssueToMeterFixer ZoneTable ----------------------


    public MMGZoneModel getIssueToMeter_ZoneExist() {
        return realm.where(MMGZoneModel.class).findFirst();
    }

    public void addIssueToMeterFixrZone(MMGZoneModel MMGZoneModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(MMGZoneModel);
            }
        });
    }

    public MMGZoneModel fetchIssueToMeterZoneByName(String name) {
        return realm.where(MMGZoneModel.class).equalTo("BU_NAME", name).findFirst();

    }

    public void deleteIssueToMeterZoneTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGZoneModel.class);
            }
        });
    }

   /* public List<MMGZoneModel> fetchZoneById(int id) {
        return realm.where(MMGZoneModel.class).equalTo("BUM_BU_ID", id).findAll();
    }*/

    public RealmResults<MMGZoneModel> fetchIssueToMeterZoneList() {
        return realm.where(MMGZoneModel.class).findAll();
    }


    public RealmResults<MMGZoneModel> fetchIssueToMeterZoneById(int id) {
        return realm.where(MMGZoneModel.class).equalTo("BUM_BU_ID", id).findAll();
    }


    public ArrayList<String> fetchIssueToMeterZone() {
        ArrayList<String> issueToMeterFixrZoneModelzoneList = new ArrayList<>();
        RealmResults<MMGZoneModel> MMGZoneModels = realm.where(MMGZoneModel.class).findAll();

        for (MMGZoneModel model : MMGZoneModels) {

            issueToMeterFixrZoneModelzoneList.add(model.getBU_NAME());
            //issueToMeterFixrZoneModelzoneList.add(String.valueOf(model.getBUM_BU_ID()));
        }
        return issueToMeterFixrZoneModelzoneList;
    }

    public MMGZoneModel fetchZoneId(int id) {
        return realm.where(MMGZoneModel.class).equalTo("BUM_BU_ID", id).findFirst();
    }

    public ArrayList<String> fetchIssueToMeterZoneListById(int zoneId) {
        ArrayList<String> issueToMeterFixrZoneModelzoneList = new ArrayList<>();
        RealmResults<MMGZoneModel> MMGZoneModels = realm.where(MMGZoneModel.class).findAll();

        for (MMGZoneModel model : MMGZoneModels) {
            if (model.getBUM_BU_ID() == zoneId) {
                issueToMeterFixrZoneModelzoneList.add(model.getBU_NAME());
            }
        }
        return issueToMeterFixrZoneModelzoneList;
    }


    public void deleteIssueToMeterFixrZoneTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGZoneModel.class);
            }
        });
    }


    // ----------------------  ZoneTable ----------------------

    public ZoneModel getZoneExist() {
        return realm.where(ZoneModel.class).findFirst();
    }

    public void addZone(ZoneModel complaintTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(complaintTypeModel);
            }
        });
    }

    public ZoneModel fetchZoneByName(String name) {
        return realm.where(ZoneModel.class).equalTo("BU_NAME", name).findFirst();
    }

   /* public List<MMGZoneModel> fetchZoneById(int id) {
        return realm.where(MMGZoneModel.class).equalTo("BUM_BU_ID", id).findAll();
    }*/

    public RealmResults<ZoneModel> fetchZoneList() {
        return realm.where(ZoneModel.class).findAll();
    }

    public RealmResults<ZoneModel> fetchZoneById(int id) {
        return realm.where(ZoneModel.class).equalTo("BUM_BU_ID", id).findAll();
    }

    public ArrayList<String> fetchZone() {
        ArrayList<String> zoneList = new ArrayList<>();
        RealmResults<ZoneModel> zoneModels = realm.where(ZoneModel.class).findAll();

        for (ZoneModel model : zoneModels) {
            zoneList.add(model.getBU_NAME());
        }
        return zoneList;
    }


    public void deleteZoneTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ZoneModel.class);
            }
        });
    }

    public ArrayList<String> fetchSubZoneList(int id) {
        ArrayList<String> zoneSubZonelist = new ArrayList<>();
        RealmResults<MMGSubZoneModel> mZoneModel = realm.where(MMGSubZoneModel.class).equalTo("PCM_BU_ID", id).findAll();

        for (MMGSubZoneModel zoneModel : mZoneModel) {
            zoneSubZonelist.add(zoneModel.getPCM_PC_NAME());
        }
        return zoneSubZonelist;
    }

    // ----------------------  SubZoneTable ----------------------

    public MMGSubZoneModel getIssueToMeterSubZoneExist() {
        return realm.where(MMGSubZoneModel.class).findFirst();
    }

    public void addIssueToMeterSubZone(MMGSubZoneModel MMGSubZoneModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(MMGSubZoneModel);
            }
        });
    }

    public MMGSubZoneModel fetchIssueToMeterSubZoneByName(String name) {
        return realm.where(MMGSubZoneModel.class).equalTo("PCM_PC_NAME", name).findFirst();
    }

    public MMGSubZoneModel fetchIssueToMeterSubZoneId(String name) {
        return realm.where(MMGSubZoneModel.class).equalTo("PCM_PC_NAME", name).findFirst();
    }

    public RealmResults<MMGSubZoneModel> fetchIssueToMeterSubzoneList() {
        return realm.where(MMGSubZoneModel.class).findAll();
    }

    public ArrayList<String> fetchIssueToMeterSubZoneById(int id) {
        ArrayList<String> issueToMeterFixrSubZoneList = new ArrayList<>();
        RealmResults<MMGSubZoneModel> MMGSubZoneModel = realm.where(elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel.class).equalTo("PCM_BU_ID", id).findAll();

        for (elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel model : MMGSubZoneModel) {
            issueToMeterFixrSubZoneList.add(model.getPCM_PC_NAME());
        }
        return issueToMeterFixrSubZoneList;
    }

    public RealmResults<MMGSubZoneModel> fetchIssueToMeterSubZoneByZoneId(int id) {
        return realm.where(MMGSubZoneModel.class).equalTo("PCM_BU_ID", id).findAll();
    }

    public ArrayList<String> fetchIssueToMeterSubZone() {
        ArrayList<String> issueToMeterFixrSubZoneList = new ArrayList<>();
        RealmResults<MMGSubZoneModel> MMGSubZoneModel = realm.where(elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel.class).findAll();

        for (elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel model : MMGSubZoneModel) {
            issueToMeterFixrSubZoneList.add(model.getPCM_PC_NAME());
            // issueToMeterFixrSubZoneList.add(String.valueOf(model.getPCM_PC_ID()));
            // issueToMeterFixrSubZoneList.add(String.valueOf(model.getPCM_BU_ID()));
        }
        return issueToMeterFixrSubZoneList;
    }

    public void deleteIssueToMeterSubZoneTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGSubZoneModel.class);
            }
        });
    }


    // ----------------------  SubZoneTable ----------------------

    public SubZoneModel getSubZoneExist() {
        return realm.where(SubZoneModel.class).findFirst();
    }

    public void addSubZone(SubZoneModel subZoneModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(subZoneModel);
            }
        });
    }

    public SubZoneModel fetchSubZoneByName(String name) {
        return realm.where(SubZoneModel.class).equalTo("PCM_PC_NAME", name).findFirst();
    }

    public ArrayList<String> fetchSubZoneById(int id) {
        ArrayList<String> subZoneList = new ArrayList<>();
        RealmResults<SubZoneModel> subZoneModels = realm.where(SubZoneModel.class).equalTo("PCM_BU_ID", id).findAll();

        for (SubZoneModel model : subZoneModels) {
            subZoneList.add(model.getPCM_PC_NAME());
        }
        return subZoneList;
    }

    public ArrayList<String> fetchSubZoneByPCId(int id) {
        ArrayList<String> subZoneList = new ArrayList<>();
        RealmResults<SubZoneModel> subZoneModels = realm.where(SubZoneModel.class).equalTo("PCM_PC_ID", id).findAll();

        for (SubZoneModel model : subZoneModels) {
            subZoneList.add(model.getPCM_PC_NAME());
        }
        return subZoneList;
    }

    public RealmResults<SubZoneModel> fetchSubZoneByZoneId(int id) {
        return realm.where(SubZoneModel.class).equalTo("PCM_BU_ID", id).findAll();
    }

    public ArrayList<String> fetchSubZone() {
        ArrayList<String> subZoneList = new ArrayList<>();
        RealmResults<SubZoneModel> subZoneModels = realm.where(SubZoneModel.class).findAll();

        for (SubZoneModel model : subZoneModels) {
            subZoneList.add(model.getPCM_PC_NAME());
        }
        return subZoneList;
    }

    public void deleteSubZoneTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(SubZoneModel.class);
            }
        });
    }

    // ----------------------  Request Type Table ----------------------
    public RealmResults<MMGRequestTypeModel> fetchIssueToMeterRequestTypList() {
        return realm.where(MMGRequestTypeModel.class).findAll();
    }

    public MMGRequestTypeModel getIssueToMeterFixrRequestTypeExist() {
        return realm.where(MMGRequestTypeModel.class).findFirst();
    }

    public void addIssueToMeterFixrRequestType(MMGRequestTypeModel MMGRequestTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(MMGRequestTypeModel);
            }
        });
    }

    public MMGRequestTypeModel fetchIssueToMeterFixrRequestTypeByName(String name) {
        return realm.where(MMGRequestTypeModel.class).equalTo("SelectVal", name).findFirst();
    }

    public ArrayList<String> fetchIssueToMeterFixrRequestTypeById(String id) {
        ArrayList<String> issueToMeterFixrRequestTypeList = new ArrayList<>();
        RealmResults<MMGRequestTypeModel> MMGRequestTypeModels = realm.where(MMGRequestTypeModel.class).equalTo("PCM_BU_ID", id).findAll();

        for (MMGRequestTypeModel model : MMGRequestTypeModels) {
            issueToMeterFixrRequestTypeList.add(model.getAllVal());
        }
        return issueToMeterFixrRequestTypeList;
    }

    public RealmResults<MMGRequestTypeModel> fetchIssueToMeterFixrRequestTypeByZoneId(int id) {
        return realm.where(MMGRequestTypeModel.class).equalTo("PCM_BU_ID", id).findAll();
    }

    public ArrayList<String> fetchIssueToMeterFixrRequestType() {
        ArrayList<String> issueToMeterFixrRequestTypeList = new ArrayList<>();
        RealmResults<MMGRequestTypeModel> MMGRequestTypeModels = realm.where(MMGRequestTypeModel.class).findAll();

        for (MMGRequestTypeModel model : MMGRequestTypeModels) {
            //issueToMeterFixrRequestTypeList.add(model.getAllVal());
            issueToMeterFixrRequestTypeList.add(model.getSelectVal());
        }
        return issueToMeterFixrRequestTypeList;
    }


    public RealmResults<MMGRequestTypeModel> fetchReuqestTypeList() {
        return realm.where(MMGRequestTypeModel.class).findAll();
    }

    public MMGRequestTypeModel fetchReqTypeById(String name) {
        return realm.where(MMGRequestTypeModel.class).equalTo("SelectVal", name).findFirst();
    }

    public void deleteIssueToMeterFixrRequestTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGRequestTypeModel.class);
            }
        });
    }

    // ----------------------  SiteEngineerTable ----------------------

    public SiteEngineerModel getSiteEngineerExist() {
        return realm.where(SiteEngineerModel.class).findFirst();
    }

    public void addSiteEngineer(SiteEngineerModel siteEngineerModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(siteEngineerModel);
            }
        });
    }

    public RealmResults<SiteEngineerModel> fetchSiteEngineerModel() {
        return realm.where(SiteEngineerModel.class).findAll();
    }

    public RealmResults<SiteEngineerModel> fetchSiteEngineerByCheck() {
        return realm.where(SiteEngineerModel.class).equalTo("isChecked", true).findAll();
    }

    public ArrayList<String> fetchSiteEngineer() {
        ArrayList<String> siteEngineerList = new ArrayList<>();
        RealmResults<SiteEngineerModel> siteEngineerModels = realm.where(SiteEngineerModel.class).findAll();

        for (SiteEngineerModel model : siteEngineerModels) {
            //issueToMeterFixrRequestTypeList.add(model.getAllVal());
            siteEngineerList.add(model.getEMPLOYEE_NAME());
        }
        return siteEngineerList;
    }

    public ArrayList<String> fetchSiteEngineer(String deptId) {
        ArrayList<String> siteEngineerList = new ArrayList<>();
//        RealmResults<SiteEngineerModel> siteEngineerModels = realm.where(SiteEngineerModel.class).equalTo("EM_DEPT", deptId).findAll();
        RealmResults<SiteEngineerModel> siteEngineerModels = realm.where(SiteEngineerModel.class).equalTo("EM_DEPT", deptId).findAll();
        siteEngineerModels = siteEngineerModels.sort("EMPLOYEE_NAME", Sort.ASCENDING);
        for (SiteEngineerModel model : siteEngineerModels) {
            //issueToMeterFixrRequestTypeList.add(model.getAllVal());
            siteEngineerList.add(model.getEMPLOYEE_NAME());
        }
        return siteEngineerList;
    }

    public SiteEngineerModel getSiteEngineerLastId() {
        return realm.where(SiteEngineerModel.class)
                .equalTo("isChecked", true)
                .findAll()
                .last(null);
    }

    public int isSiteEngineerChecked() {
        return realm.where(SiteEngineerModel.class).equalTo("isChecked", true).findAll().size();
    }

    public void setEngineerCheck(String id, boolean check) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SiteEngineerModel siteEngineerModels = realm.where(SiteEngineerModel.class)
                        .equalTo("EMPLOYEE_CODE", id)
                        .findFirst();
                assert siteEngineerModels != null;
                siteEngineerModels.setChecked(check);
            }
        });
    }

    public void setEngineerUnCheck() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<SiteEngineerModel> siteEngineerModels = realm.where(SiteEngineerModel.class).findAll();
                assert siteEngineerModels != null;
                for (SiteEngineerModel engineerModel : siteEngineerModels) {
                    engineerModel.setChecked(false);
                }
            }
        });
    }

    public void deleteSiteEngineerTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(SiteEngineerModel.class);
            }
        });
    }

    public SiteEngineerModel fetchSiteEngineerName(String name) {
        return realm.where(SiteEngineerModel.class).equalTo("EMPLOYEE_NAME", name).findFirst();
    }


    // ----------------------  FinishTable ----------------------

    public FinishActionModel getFinishActionExist() {
        return realm.where(FinishActionModel.class).findFirst();
    }

    public ActionFormModel getActionFormExist() {
        return realm.where(ActionFormModel.class).findFirst();
    }

    public WorkCompObservationModel getWorkCompObservationModelExist() {
        return realm.where(WorkCompObservationModel.class).findFirst();
    }


    public DocSourceModel getDocSourcenModelExist() {
        return realm.where(DocSourceModel.class).findFirst();
    }

    public DocTypeModel getDocTypeModelExist() {
        return realm.where(DocTypeModel.class).findFirst();
    }

    public DocSubTypeModel getDocSubTypeModelExist() {
        return realm.where(DocSubTypeModel.class).findFirst();
    }

    public MeterStatusModel getMeterStatusModelExist() {
        return realm.where(MeterStatusModel.class).findFirst();
    }

    public MeterObservationModel getMeterObservationModelExist() {
        return realm.where(MeterObservationModel.class).findFirst();
    }

    public Number getActionFormCount() {
        return realm.where(ActionFormModel.class).max("id");
    }

    public void addFinishAction(FinishActionModel finishActionModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(finishActionModel);
            }
        });
    }

    public void addActionForm(ActionFormModel actionFormModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(actionFormModel);
            }
        });
    }

    public void addWorkCompObservation(WorkCompObservationModel workCompObservationModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(workCompObservationModel);
            }
        });
    }

    public void addDocSources(DocSourceModel docSourceModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(docSourceModel);
            }
        });
    }

    public void addDocTypes(DocTypeModel docTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(docTypeModel);
            }
        });
    }

    public void addDocSubTypes(DocSubTypeModel docSubTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(docSubTypeModel);
            }
        });
    }

    public void addMeterStatus(MeterStatusModel meterStatusModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(meterStatusModel);
            }
        });
    }

    public void addMeterObservation(MeterObservationModel observationModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(observationModel);
            }
        });
    }

    public RealmResults<MeterObservationModel> fetchMeterObservationNameID(String Id) {
        return realm.where(MeterObservationModel.class).equalTo("MTRSTATUS", Id).findAll();
    }

    public RealmResults<MeterStatusModel> fetchMeterStatusNameID() {
        return realm.where(MeterStatusModel.class).findAll();
    }


    public FinishActionModel fetchFinishActionByName(String name) {
        return realm.where(FinishActionModel.class).equalTo("CSCM_SECNAME", name).findFirst();
    }

    public ArrayList<String> fetchFinishActionById(int id) {
        ArrayList<String> finishActionList = new ArrayList<>();
        RealmResults<FinishActionModel> finishActionModels = realm.where(FinishActionModel.class).equalTo("CSCM_ID", id).findAll();

        for (FinishActionModel model : finishActionModels) {
            finishActionList.add(model.getCSCM_SECNAME());
        }
        return finishActionList;
    }

    public ArrayList<String> fetchFinishActionNC() {
        ArrayList<String> finishActionList = new ArrayList<>();
        RealmResults<FinishActionModel> finishActionModels = realm.where(FinishActionModel.class).findAll();

        for (FinishActionModel model : finishActionModels) {
            finishActionList.add(model.getCSCM_SECNAME());
        }
        finishActionList.remove(2);
        finishActionList.remove(2);
        return finishActionList;
    }

    public ArrayList<String> fetchFinishAction() {
        ArrayList<String> finishActionList = new ArrayList<>();
        RealmResults<FinishActionModel> finishActionModels = realm.where(FinishActionModel.class).findAll();

        for (FinishActionModel model : finishActionModels) {
            finishActionList.add(model.getCSCM_SECNAME());
        }
        return finishActionList;
    }

    public void deleteFinishActionTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(FinishActionModel.class);
            }
        });
    }


    // ----------------------  Billing Adjust  By ----------------------


    /*    public ArrayList<String> fetchBilllingAdjustType() {
            ArrayList<String> billlingAdjustTypeList = new ArrayList<>();
            RealmResults<DownloadAdjustmentTypeTable> finishActionModels = realm.where(DownloadAdjustmentTypeTable.class).findAll();

            for (DownloadAdjustmentTypeTable model : finishActionModels) {
                billlingAdjustTypeList.add(model.getREM_REASONNM());
            }
            return billlingAdjustTypeList;
        }*/
    public RealmResults<DownloadAdjustmentTypeTable> fetchBilllingAdjustType() {
        return realm.where(DownloadAdjustmentTypeTable.class).findAll();
    }
   /* public ArrayList<String> fetchBilllinMeterStatusType() {
        ArrayList<String> billlingMeterStatusTypeList = new ArrayList<>();
        RealmResults<DownloadMeterStatusBillAdjustTable> downloadMeterStatusBillAdjustTables = realm.where(DownloadMeterStatusBillAdjustTable.class).findAll();

        for (DownloadMeterStatusBillAdjustTable model : downloadMeterStatusBillAdjustTables) {
            billlingMeterStatusTypeList.add(model.getMSM_METERSTATUS_NAME());
        }
        return billlingMeterStatusTypeList;
    }*/

    public RealmResults<DownloadMeterStatusBillAdjustTable> fetchBilllinMeterStatusType() {
        return realm.where(DownloadMeterStatusBillAdjustTable.class).findAll();
    }
    // ----------------------  Complaint By ----------------------

    public ComplaintByModel getcomplaintByExist() {
        return realm.where(ComplaintByModel.class).findFirst();
    }

    public void addComplaintBy(ComplaintByModel complaintByModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(complaintByModel);
            }
        });
    }

    public ComplaintByModel fetchComplaintByName(String name) {
        return realm.where(ComplaintByModel.class).equalTo("VCM_CATNAME", name).findFirst();
    }

    public ArrayList<String> fetchComplaintById(int id) {
        ArrayList<String> complaintByList = new ArrayList<>();
        RealmResults<ComplaintByModel> complaintByModels = realm.where(ComplaintByModel.class).equalTo("VCM_CATNAME", id).findAll();

        for (ComplaintByModel model : complaintByModels) {
            complaintByList.add(model.getVCM_CATNAME());
        }
        return complaintByList;
    }

    public ArrayList<String> fetchComplaintByName() {
        ArrayList<String> commByArraylist = new ArrayList<>();
        RealmResults<ComplaintByModel> commByModelRealmResults = realm.where(ComplaintByModel.class).findAll();

        for (ComplaintByModel complaintByModel : commByModelRealmResults) {
            commByArraylist.add(complaintByModel.getVCM_CATNAME());
        }
        return commByArraylist;
    }

    public void deleteComplaintByTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ComplaintByModel.class);
            }
        });
    }

    // -----------------------Customer Type------------------------------------
    public void addCustomerType(DownloadCustomerType customerTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(customerTypeModel);
            }
        });
    }

//    public DownloadCustomerType fetchCustomerTypeByName(String name) {
//        return realm.where(DownloadCustomerType.class).equalTo("VCM_CATNAME", name).findFirst();
//    }

    public DownloadCustomerType getCustomerTypeId(String name) {
        return realm.where(DownloadCustomerType.class).equalTo("CUSTTYPETEXT", name).findFirst();
    }

    public ArrayList<String> fetchCustomerTypeName() {
        ArrayList<String> commByArraylist = new ArrayList<>();
        RealmResults<DownloadCustomerType> commByModelRealmResults = realm.where(DownloadCustomerType.class).findAll();

        for (DownloadCustomerType complaintByModel : commByModelRealmResults) {
            commByArraylist.add(complaintByModel.getCUSTTYPETEXT());
        }
        return commByArraylist;
    }

    public ArrayList<String> fetchCustomerTypeNameByBuID(String custType, String buId) {
        ArrayList<String> commByArraylist = new ArrayList<>();

        RealmResults<DownloadCustomerType> commByModelRealmResults = realm.where(DownloadCustomerType.class).equalTo("CUSTTYPETEXT", custType).equalTo("CUSTBUID", buId).findAll();

        for (DownloadCustomerType model : commByModelRealmResults) {
            commByArraylist.add(model.getCUSTTYPEID());


        }
        return commByArraylist;
    }


    public RealmResults<DownloadCustomerType> fetchTariffName() {
        return realm.where(DownloadCustomerType.class).findAll();
    }

    public void deleteCustomerTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DownloadCustomerType.class);
            }
        });
    }

    //pinky added on 02/02/2022
    public void deleteConnCategory() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DownloadConnCategory.class);
            }
        });
    }

    public void addConnCategory(DownloadConnCategory downloadConnCategory) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(downloadConnCategory);
            }
        });
    }

    public void deleteConnsize() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DownloadConnSize.class);
            }
        });
    }

    public RealmResults<DownloadConnCategory> fetchConnCategory() {
        return realm.where(DownloadConnCategory.class).findAll();
    }
    public DownloadConnCategory getDownloadConnCategoryId(String name) {
        return realm.where(DownloadConnCategory.class).equalTo("CATEGORY_NAME", name).findFirst();
    }
    public RealmResults<DownloadConnSize> fetchConnSize() {
        return realm.where(DownloadConnSize.class).findAll();
    }
    public void addConnSize(DownloadConnSize connSize) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(connSize);
            }
        });
    }

    public DownloadConnSize getDownloadConnSizeId(String name) {
        return realm.where(DownloadConnSize.class).equalTo("NAME", name).findFirst();
    }

    // ---------------------- Souretype table --------------------------
    public void addSourceType(DownloadSourceType sourceType) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(sourceType);
            }
        });
    }

    public ArrayList<String> fetchSourceTypeName() {
        ArrayList<String> sourceTypeArraylist = new ArrayList<>();
        RealmResults<DownloadSourceType> sourceTypeRealmResults = realm.where(DownloadSourceType.class).findAll();

        for (DownloadSourceType sourceType : sourceTypeRealmResults) {
            sourceTypeArraylist.add(sourceType.getSOURCEDESC());
        }
        return sourceTypeArraylist;
    }

    public ArrayList<String> fetchSourceTypeName(String type) {
        ArrayList<String> sourceTypeList = new ArrayList<>();

        RealmResults<DownloadSourceType> sourceTypeModels = realm.where(DownloadSourceType.class).equalTo("SOURCETYPE", type).findAll();

        for (DownloadSourceType model : sourceTypeModels) {
            sourceTypeList.add(model.getSOURCEDESC());

        }
        return sourceTypeList;
    }

    public DownloadSourceType fetchSourceByName(String name) {
        return realm.where(DownloadSourceType.class).equalTo("SOURCEDESC", name).findFirst();
    }

    public void deleteSourceTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DownloadSourceType.class);
            }
        });
    }

    // ----------------------  Meter Number Table ----------------------

    public MeterNumberModel getMeterNumberExist() {
        return realm.where(MeterNumberModel.class).findFirst();
    }

    public void addMeterNumber(MeterNumberModel meterNumberModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(meterNumberModel);
            }
        });
    }

    public ArrayList<String> fetchMeterNumber() {
        ArrayList<String> meterNumber = new ArrayList<>();
        RealmResults<MeterNumberModel> meterNumberModels = realm.where(MeterNumberModel.class).findAll();

        for (MeterNumberModel model : meterNumberModels) {
            meterNumber.add(model.getMeterNumber());
        }
        return meterNumber;
    }

    public ArrayList<String> fetchRejectReason() {
        ArrayList<String> rejectReason = new ArrayList<>();
        RealmResults<RejectModel> rejectModels = realm.where(RejectModel.class).findAll();

        for (RejectModel model : rejectModels) {
            rejectReason.add(model.getREM_REASONNM());
        }
        return rejectReason;
    }

    public void deleteMeterNumberTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MeterNumberModel.class);
            }
        });
    }

    // ----------------------  Lat Long Table ----------------------

    public LatLongModel getLatLongExist() {
        return realm.where(LatLongModel.class).findFirst();
    }

    public void addLatLong(LatLongModel LatLongModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(LatLongModel);
            }
        });
    }

    public RealmResults<LatLongModel> fetchLatLong() {
        return realm.where(LatLongModel.class).findAll();

    }

    public void deleteLatLongTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(LatLongModel.class);
            }
        });
    }
    // ----------------------  MakerCode Table ----------------------

    public MMGMakerCodeModel getMakerCodeExist() {
        return realm.where(MMGMakerCodeModel.class).findFirst();
    }

    public void addMakerCode(MMGMakerCodeModel mmgMakerCodeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgMakerCodeModel);
            }
        });
    }

    public MMGMakerCodeModel fetchMakerCodeByName(String name) {
        return realm.where(MMGMakerCodeModel.class).equalTo("MMFG_MFGNAME", name).findFirst();
    }

    public MMGMakerCodeModel fetchMakerCodeId(String name) {
        return realm.where(MMGMakerCodeModel.class).equalTo("MMFG_MFGCODE", name).findFirst();
    }

    public ArrayList<String> fetchMakerCodeById(int id) {
        ArrayList<String> MakerCodeList = new ArrayList<>();
        RealmResults<MMGMakerCodeModel> MakerCodeModels = realm.where(MMGMakerCodeModel.class).equalTo("MMFG_MFGCODE", id).findAll();

        for (MMGMakerCodeModel model : MakerCodeModels) {
            MakerCodeList.add(model.getMMFG_MFGNAME());
        }
        return MakerCodeList;
    }

    public RealmResults<MMGMakerCodeModel> fetchMakerCodeByZoneId(int id) {
        return realm.where(MMGMakerCodeModel.class).equalTo("MMFG_MFGCODE", id).findAll();
    }

    public ArrayList<String> fetchMakerCode() {
        ArrayList<String> MakerCodeList = new ArrayList<>();
        RealmResults<MMGMakerCodeModel> mmgMakerCodeModels = realm.where(MMGMakerCodeModel.class).findAll();

        for (MMGMakerCodeModel model : mmgMakerCodeModels) {
            MakerCodeList.add(model.getMMFG_MFGNAME());
        }
        return MakerCodeList;
    }

    public ArrayList<Float> fetchMakerType() {
        ArrayList<Float> MakerTypeList = new ArrayList<>();
        RealmResults<MMGMakerCodeModel> mmgMakerCodeModels = realm.where(MMGMakerCodeModel.class).findAll();

        for (MMGMakerCodeModel model : mmgMakerCodeModels) {
            MakerTypeList.add(model.getMMFG_MATERIAL_TYPE());
        }
        return MakerTypeList;
    }

    public void deleteMakerCodeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGMakerCodeModel.class);
            }
        });
    }

    // ----------------------  MeterPrefix Table ----------------------

    public MMGMeterPrefixModel getMeterPrefixExist() {
        return realm.where(MMGMeterPrefixModel.class).findFirst();
    }

    public void addMeterPrefix(MMGMeterPrefixModel mmgMeterPrefixModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgMeterPrefixModel);
            }
        });
    }

    public MMGMeterPrefixModel fetchMeterPrefixByName(String name) {
        return realm.where(MMGMeterPrefixModel.class).equalTo("NOPREFIX", name).findFirst();
    }

    public ArrayList<String> fetchMeterPrefixById(int id) {
        ArrayList<String> MeterPrefixList = new ArrayList<>();
        RealmResults<MMGMeterPrefixModel> mmgMeterPrefixModels = realm.where(MMGMeterPrefixModel.class).equalTo("PCM_BU_ID", id).findAll();

        for (MMGMeterPrefixModel model : mmgMeterPrefixModels) {
            MeterPrefixList.add(model.getNOPREFIX());
        }
        return MeterPrefixList;
    }

    public RealmResults<MMGMeterPrefixModel> fetchMeterPrefixId(int id) {
        return realm.where(MMGMeterPrefixModel.class).equalTo("VALUE", id).findAll();
    }

    public ArrayList<String> fetchMeterPrefix() {
        ArrayList<String> MeterPrefixList = new ArrayList<>();
        RealmResults<MMGMeterPrefixModel> mmgMeterPrefixModels = realm.where(MMGMeterPrefixModel.class).findAll();

        for (MMGMeterPrefixModel model : mmgMeterPrefixModels) {
            MeterPrefixList.add(model.getNOPREFIX());
        }
        return MeterPrefixList;
    }

    public void deleteMeterPrefixTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGMeterPrefixModel.class);
            }
        });
    }


    // ----------------------  Observation Table ----------------------

    public MMGObersvationModel getObservationExist() {
        return realm.where(MMGObersvationModel.class).findFirst();
    }

    public void addObservation(MMGObersvationModel mmgObersvationModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgObersvationModel);
            }
        });
    }

    public MMGObersvationModel fetchObservationByName(String name) {
        return realm.where(MMGObersvationModel.class).equalTo("OCRM_NAME", name).findFirst();
    }

    public MMGObersvationModel fetchObservationByID(String id) {
        return realm.where(MMGObersvationModel.class).equalTo("OCRM_ID", id).findFirst();
    }

    public ArrayList<String> fetchObservationById(String id) {
        ArrayList<String> ObservationList = new ArrayList<>();
        RealmResults<MMGObersvationModel> mmgObersvationModels = realm.where(MMGObersvationModel.class).equalTo("PCM_BU_ID", id).findAll();

        for (MMGObersvationModel model : mmgObersvationModels) {
            ObservationList.add(model.getOCRM_NAME());
        }
        return ObservationList;
    }

    public RealmResults<MMGObersvationModel> fetchObservationId(int id) {
        return realm.where(MMGObersvationModel.class).equalTo("OCRM_ID", id).findAll();
    }

    public RealmResults<MStatusObservationModel> fetchObservationIdValues() {
        return realm.where(MStatusObservationModel.class).findAll();
    }

    public ArrayList<String> fetchObservation() {
        ArrayList<String> ObservationList = new ArrayList<>();
        RealmResults<MMGObersvationModel> mmgObersvationModels = realm.where(MMGObersvationModel.class).findAll();

        for (MMGObersvationModel model : mmgObersvationModels) {
            ObservationList.add(model.getOCRM_NAME());
        }
        return ObservationList;
    }

    public void deleteObservationTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGObersvationModel.class);
            }
        });
    }

    // ----------------------  Meter Type Table ----------------------

    public MMGMeterTypeModel getMeterTypeExist() {
        return realm.where(MMGMeterTypeModel.class).findFirst();
    }

    public void addMeterType(MMGMeterTypeModel mmgMeterTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgMeterTypeModel);
            }
        });
    }

    public MMGMeterTypeModel fetchMeterTypeByName(String name) {
        return realm.where(MMGMeterTypeModel.class).equalTo("MTC_TYPEDESC", name).findFirst();
    }

    public MMGMeterTypeModel fetchMeterTypeId(String name) {
        return realm.where(MMGMeterTypeModel.class).equalTo("MTC_METERTYPE_CODE", name).findFirst();
    }

    public ArrayList<String> fetchMeterTypeById(int id) {
        ArrayList<String> MeterTypeList = new ArrayList<>();
        RealmResults<MMGMeterTypeModel> mmgMeterTypeModel = realm.where(MMGMeterTypeModel.class).equalTo("PCM_BU_ID", id).findAll();

        for (MMGMeterTypeModel model : mmgMeterTypeModel) {
            MeterTypeList.add(model.getMTC_TYPEDESC());
        }
        return MeterTypeList;
    }

    public RealmResults<MMGMeterTypeModel> fetchMeterTypeId(int id) {
        return realm.where(MMGMeterTypeModel.class).equalTo("MTC_METERTYPE_CODE", id).findAll();
    }

    public ArrayList<String> fetchMeterType() {
        ArrayList<String> MeterTypeList = new ArrayList<>();
        RealmResults<MMGMeterTypeModel> mmgMeterTypeModel = realm.where(MMGMeterTypeModel.class).findAll();

        for (MMGMeterTypeModel model : mmgMeterTypeModel) {
            MeterTypeList.add(model.getMTC_TYPEDESC());
        }
        return MeterTypeList;
    }

    public void deleteMeterTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGMeterTypeModel.class);
            }
        });
    }


    // ----------------------  Meter Condition Table ----------------------

    public MMGMeterConditionModel getMeterCdnExist() {
        return realm.where(MMGMeterConditionModel.class).findFirst();
    }

    public void addMeterCdn(MMGMeterConditionModel mmgMeterConditionModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgMeterConditionModel);
            }
        });
    }

    public MMGMeterConditionModel fetchMeterCdnByName(String name) {
        return realm.where(MMGMeterConditionModel.class).equalTo("MSM_METERSTATUS_NAME", name).findFirst();
    }

    public ArrayList<String> fetchMeterCdnById(int id) {
        ArrayList<String> MeterConditionList = new ArrayList<>();
        RealmResults<MMGMeterConditionModel> mmgMeterConditionModels = realm.where(MMGMeterConditionModel.class).equalTo("PCM_BU_ID", id).findAll();

        for (MMGMeterConditionModel model : mmgMeterConditionModels) {
            MeterConditionList.add(model.getMSM_METERSTATUS_NAME());
        }
        return MeterConditionList;
    }

    public RealmResults<MMGMeterConditionModel> fetchMeterCdnId(int id) {
        return realm.where(MMGMeterConditionModel.class).equalTo("MSM_METERSTATUS_ID", id).findAll();
    }


    public ArrayList<String> fetchMeterCdn() {
        ArrayList<String> MeterConditionList = new ArrayList<>();
        RealmResults<MMGMeterConditionModel> meterConditionModels = realm.where(MMGMeterConditionModel.class).findAll();

        for (MMGMeterConditionModel model : meterConditionModels) {
            MeterConditionList.add(model.getMSM_METERSTATUS_NAME());
        }
        return MeterConditionList;
    }

    public void deleteMeterCdnTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGMeterConditionModel.class);
            }
        });
    }

    public void updateMPin(final String empCode, String password) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ChangePinModel changePinModel = realm.where(ChangePinModel.class).equalTo("empCode", empCode).findFirst();
                assert changePinModel != null;
                changePinModel.setEmpPassword(password);
            }
        });
    }

    public ChangePinModel fetchDataReadingByEmpCode(String empCode) {
        return realm.where(ChangePinModel.class).equalTo("empCode", empCode).findFirst();
    }

    public void insertLoginData(final ChangePinModel changePinModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insert(changePinModel);
            }
        });
    }

    public ChangePinModel fetchchangepinDataReadingByImei(String imei) {
        return realm.where(ChangePinModel.class).equalTo("deviceImei", imei).findFirst();
    }

    public ArrayList<String> checkDeviceIdData() {
        ArrayList<String> deviceImeiList = new ArrayList<>();
        RealmResults<ChangePinModel> deviceModels = realm.where(ChangePinModel.class).findAll();

        for (ChangePinModel model : deviceModels) {
            deviceImeiList.add(model.getDeviceImei());
        }
        return deviceImeiList;
    }

    public Number getEmpIDCount() {
        return realm.where(ChangePinModel.class).max("id");
    }

    public void updateEmployeeData(final String empCode, String userName, String mobile, String email, String RDRID, String zone, String area, int isValid) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ChangePinModel changePinModel = realm.where(ChangePinModel.class).equalTo("empCode", empCode).findFirst();
                assert changePinModel != null;
                changePinModel.setEmpName(userName);
                changePinModel.setEmpMobileNo(mobile);
                changePinModel.setEmpEmail(email);
                changePinModel.setRDRID(RDRID);
                changePinModel.setZone(zone);
                changePinModel.setArea(area);
                changePinModel.setIsValid(isValid);
            }
        });
    }


    public void updateEmployeeData(final String empCode, String userName, String mobile, String email, String RDRID,
                                   String zone, String area, int isValid, String AV, String APP_ISLOGGED) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ChangePinModel changePinModel = realm.where(ChangePinModel.class).equalTo("empCode", empCode).findFirst();
                assert changePinModel != null;
                changePinModel.setEmpName(userName);
                changePinModel.setEmpMobileNo(mobile);
                changePinModel.setEmpEmail(email);
                changePinModel.setRDRID(RDRID);
                changePinModel.setZone(zone);
                changePinModel.setArea(area);
                changePinModel.setIsValid(isValid);
                changePinModel.setAV(AV);
                changePinModel.setAPP_ISLOGGED(APP_ISLOGGED);
            }
        });
    }

    public void updateSRData(final String TRM_ID, String TRM_NAME) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SRModel srModel = realm.where(SRModel.class).equalTo("TRM_ID", TRM_ID).findFirst();
                assert srModel != null;
                srModel.setTRM_ID(TRM_ID);
                srModel.setTRM_NAME(TRM_NAME);
            }
        });
    }

    public ChangePinModel isemployeeExist() {
        return realm.where(ChangePinModel.class).findFirst();
    }

    public ChangePinModel isEmployeeLoginExist() {
        return realm.where(ChangePinModel.class).findFirst();
    }


    public void deleteRow() {
        RealmResults<ChangePinModel> results = realm.where(ChangePinModel.class).equalTo("id", 1).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });


    }

    public void addNCMeterNumber(NCMeterNumberModel ncMeterNumberModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(ncMeterNumberModel);
            }
        });
    }

    public ArrayList<String> fetchNCMeterNumber() {
        ArrayList<String> meterNumber = new ArrayList<>();
        RealmResults<NCMeterNumberModel> meterNumberModels = realm.where(NCMeterNumberModel.class).findAll();

        for (NCMeterNumberModel model : meterNumberModels) {
            meterNumber.add(model.getMeterNumber());
        }
        return meterNumber;
    }

    public void deleteNCMeterNumberTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(NCMeterNumberModel.class);
            }
        });
    }

    public ChangePinModel fetchlastloginById() {
        return realm.where(ChangePinModel.class).equalTo("id", 1).findFirst();
    }
    // ----------------------  Meter Location Table ----------------------

    public MMGMeterLocationModel getMtrLocationExist() {
        return realm.where(MMGMeterLocationModel.class).findFirst();
    }

    public void addMtrLocation(MMGMeterLocationModel mmgMeterLocationModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgMeterLocationModel);
            }
        });
    }

    public MMGMeterLocationModel fetchMtrLocationByName(String name) {
        return realm.where(MMGMeterLocationModel.class).equalTo("ML_DESC", name).findFirst();
    }

    public MMGMeterLocationModel fetchMeterLocationId(int name) {
        return realm.where(MMGMeterLocationModel.class).equalTo("ML_ID", name).findFirst();
    }

    public ArrayList<String> fetchMtrLocationById(int id) {
        ArrayList<String> MtrLocationlist = new ArrayList<>();
        RealmResults<MMGMeterLocationModel> mmgMeterLocationModels = realm.where(MMGMeterLocationModel.class).equalTo("ML_ID", id).findAll();

        for (MMGMeterLocationModel model : mmgMeterLocationModels) {
            MtrLocationlist.add(model.getML_DESC());
        }
        return MtrLocationlist;
    }

    public RealmResults<MMGMeterLocationModel> fetchMtrLocationId(int id) {
        return realm.where(MMGMeterLocationModel.class).equalTo("ML_ID", id).findAll();
    }

    public ArrayList<String> fetchMtrLocation() {
        ArrayList<String> MtrLocationlist = new ArrayList<>();
        RealmResults<MMGMeterLocationModel> mmgMeterLocationModels = realm.where(MMGMeterLocationModel.class).findAll();

        for (MMGMeterLocationModel model : mmgMeterLocationModels) {
            MtrLocationlist.add(model.getML_DESC());
        }
        return MtrLocationlist;
    }

    public void deleteMtrLocationTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGMeterLocationModel.class);
            }
        });
    }


    // ----------------------  Roadcutting Table ----------------------

    public MMGTypeOfRoadcuttingModel getRoadcuttingExist() {
        return realm.where(MMGTypeOfRoadcuttingModel.class).findFirst();
    }

    public void addRoadcutting(MMGTypeOfRoadcuttingModel mmgTypeOfRoadcuttingModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgTypeOfRoadcuttingModel);
            }
        });
    }

    public MMGTypeOfRoadcuttingModel fetchRoadcuttingByName(String name) {
        return realm.where(MMGTypeOfRoadcuttingModel.class).equalTo("RC_DESC", name).findFirst();
    }

    public ArrayList<String> fetchRoadcuttingById(int id) {
        ArrayList<String> Roadcuttinglist = new ArrayList<>();
        RealmResults<MMGTypeOfRoadcuttingModel> mmgTypeOfRoadcuttingModels = realm.where(MMGTypeOfRoadcuttingModel.class).equalTo("RC_ID", id).findAll();

        for (MMGTypeOfRoadcuttingModel model : mmgTypeOfRoadcuttingModels) {
            Roadcuttinglist.add(model.getRC_DESC());
        }
        return Roadcuttinglist;
    }

    public RealmResults<MMGTypeOfRoadcuttingModel> fetchRoadcuttingId(int id) {
        return realm.where(MMGTypeOfRoadcuttingModel.class).equalTo("RC_ID", id).findAll();
    }

    public MMGTypeOfRoadcuttingModel fetchRoadCutCodeId(int id) {
        return realm.where(MMGTypeOfRoadcuttingModel.class).equalTo("RC_ID", id).findFirst();
    }

    public ArrayList<String> fetchRoadcutting() {
        ArrayList<String> Roadcuttinglist = new ArrayList<>();
        RealmResults<MMGTypeOfRoadcuttingModel> mmgTypeOfRoadcuttingModels = realm.where(MMGTypeOfRoadcuttingModel.class).findAll();

        for (MMGTypeOfRoadcuttingModel model : mmgTypeOfRoadcuttingModels) {
            Roadcuttinglist.add(model.getRC_DESC());
        }
        return Roadcuttinglist;
    }

    public void deleteRoadcuttingTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGTypeOfRoadcuttingModel.class);
            }
        });
    }

    // ----------------------  Excavation Table ----------------------

    public MMGSaddleAndPitExcavModel getExcavationExist() {
        return realm.where(MMGSaddleAndPitExcavModel.class).findFirst();
    }

    public void addExcavation(MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgSaddleAndPitExcavModel);
            }
        });
    }

    public MMGSaddleAndPitExcavModel fetchExcavationByName(String name) {
        return realm.where(MMGSaddleAndPitExcavModel.class).equalTo("EC_DESC", name).findFirst();
    }

    public ArrayList<String> fetchExcavationById(int id) {
        ArrayList<String> Excavationlist = new ArrayList<>();
        RealmResults<MMGSaddleAndPitExcavModel> mmgSaddleAndPitExcavModels = realm.where(MMGSaddleAndPitExcavModel.class).equalTo("EC_ID", id).findAll();

        for (MMGSaddleAndPitExcavModel model : mmgSaddleAndPitExcavModels) {
            Excavationlist.add(model.getEC_DESC());
        }
        return Excavationlist;
    }

    public RealmResults<MMGSaddleAndPitExcavModel> fetchExcavationId(int id) {
        return realm.where(MMGSaddleAndPitExcavModel.class).equalTo("EC_ID", id).findAll();
    }

    public ArrayList<String> fetchExcavation() {
        ArrayList<String> Excavationlist = new ArrayList<>();
        RealmResults<MMGSaddleAndPitExcavModel> mmgSaddleAndPitExcavModels = realm.where(MMGSaddleAndPitExcavModel.class).findAll();

        for (MMGSaddleAndPitExcavModel model : mmgSaddleAndPitExcavModels) {
            Excavationlist.add(model.getEC_DESC());
        }
        return Excavationlist;
    }

    public void deleteExcavationTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGSaddleAndPitExcavModel.class);
            }
        });
    }


    // ---------------------- MeterSize Table ----------------------

    public MMGMeterSizeModel getMeterSizeExist() {
        return realm.where(MMGMeterSizeModel.class).findFirst();
    }

    public void addMeterSize(MMGMeterSizeModel mmgMeterSizeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgMeterSizeModel);
            }
        });
    }

    public MMGMeterSizeModel fetchMeterSizeByName(String name) {
        return realm.where(MMGMeterSizeModel.class).equalTo("CONNSIZEMM", name).findFirst();
    }

    public MMGMeterSizeModel fetchMeterSize(int name) {
        return realm.where(MMGMeterSizeModel.class).equalTo("MCS_ID", name).findFirst();
    }

    public ArrayList<String> fetchMeterSizeById(int id) {
        ArrayList<String> MeterSizelist = new ArrayList<>();
        RealmResults<MMGMeterSizeModel> mmgMeterSizeModels = realm.where(MMGMeterSizeModel.class).equalTo("MCS_ID", id).findAll();

        for (MMGMeterSizeModel model : mmgMeterSizeModels) {
            MeterSizelist.add(model.getCONNSIZEMM());
        }
        return MeterSizelist;
    }

    public RealmResults<MMGMeterSizeModel> fetchMeterSizeId(int id) {
        return realm.where(MMGMeterSizeModel.class).equalTo("MCS_ID", id).findAll();
    }

    public ArrayList<String> fetchMeterSize() {
        ArrayList<String> MeterSizelist = new ArrayList<>();
        RealmResults<MMGMeterSizeModel> mmgMeterSizeModels = realm.where(MMGMeterSizeModel.class).findAll();

        for (MMGMeterSizeModel model : mmgMeterSizeModels) {
            MeterSizelist.add(model.getCONNSIZEMM());
        }
        return MeterSizelist;
    }

    public void deleteMeterSizeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGMeterSizeModel.class);
            }
        });
    }

    // ---------------------- RAMPRR Table ----------------------

    public MMGRampAndRRModel getRAMPRRExist() {
        return realm.where(MMGRampAndRRModel.class).findFirst();
    }

    public void addRAMPRR(MMGRampAndRRModel mmgRampAndRRModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgRampAndRRModel);
            }
        });
    }

    public MMGRampAndRRModel fetchRAMPRRByName(String name) {
        return realm.where(MMGRampAndRRModel.class).equalTo("RRR_DESC", name).findFirst();
    }

    public ArrayList<String> fetchRAMPRRById(int id) {
        ArrayList<String> RAMPRRlist = new ArrayList<>();
        RealmResults<MMGRampAndRRModel> mmgRampAndRRModels = realm.where(MMGRampAndRRModel.class).equalTo("RRR_ID", id).findAll();

        for (MMGRampAndRRModel model : mmgRampAndRRModels) {
            RAMPRRlist.add(model.getRRR_DESC());
        }
        return RAMPRRlist;
    }

    public RealmResults<MMGRampAndRRModel> fetchRAMPRRId(int id) {
        return realm.where(MMGRampAndRRModel.class).equalTo("RRR_ID", id).findAll();
    }

    public ArrayList<String> fetchRAMPRR() {
        ArrayList<String> RAMPRRlist = new ArrayList<>();
        RealmResults<MMGRampAndRRModel> mmgRampAndRRModels = realm.where(MMGRampAndRRModel.class).findAll();

        for (MMGRampAndRRModel model : mmgRampAndRRModels) {
            RAMPRRlist.add(model.getRRR_DESC());
        }
        return RAMPRRlist;
    }

    public void deleteRAMPRRTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGRampAndRRModel.class);
            }
        });
    }

    // ---------------------- WallBoringTable ----------------------

    public MMGWallBoringModel getWallBoringExist() {
        return realm.where(MMGWallBoringModel.class).findFirst();
    }

    public void addWallBoring(MMGWallBoringModel mmgWallBoringModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgWallBoringModel);
            }
        });
    }

    public MMGWallBoringModel fetchWallBoringByName(String name) {
        return realm.where(MMGWallBoringModel.class).equalTo("WB_DESC", name).findFirst();
    }

    public ArrayList<String> fetchWallBoringById(int id) {
        ArrayList<String> WallBoringlist = new ArrayList<>();
        RealmResults<MMGWallBoringModel> mmgWallBoringModels = realm.where(MMGWallBoringModel.class).equalTo("WB_ID", id).findAll();

        for (MMGWallBoringModel model : mmgWallBoringModels) {
            WallBoringlist.add(model.getWB_DESC());
        }
        return WallBoringlist;
    }

    public RealmResults<MMGWallBoringModel> fetchWallBoringId(int id) {
        return realm.where(MMGWallBoringModel.class).equalTo("WB_ID", id).findAll();
    }

    public ArrayList<String> fetchWallBoring() {
        ArrayList<String> WallBoringlist = new ArrayList<>();
        RealmResults<MMGWallBoringModel> mmgRampAndRRModels = realm.where(MMGWallBoringModel.class).findAll();

        for (MMGWallBoringModel model : mmgRampAndRRModels) {
            WallBoringlist.add(model.getWB_DESC());
        }
        return WallBoringlist;
    }

    public void deleteWallBoringTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGWallBoringModel.class);
            }
        });
    }

    public void deleteDocSourceTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DocSourceModel.class);
            }
        });
    }

    public void deleteDocTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DocTypeModel.class);
            }
        });
    }

    public void deleteSubDocTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DocSubTypeModel.class);
            }
        });
    }


    // ---------------------- CGR Table ----------------------

    public MMGCgRestroModel getCGRExist() {
        return realm.where(MMGCgRestroModel.class).findFirst();
    }

    public void addCGR(MMGCgRestroModel mmgCgRestroModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgCgRestroModel);
            }
        });
    }

    public MMGCgRestroModel fetchCGRByName(String name) {
        return realm.where(MMGCgRestroModel.class).equalTo("CGR_DESC", name).findFirst();
    }

    public ArrayList<String> fetchCGRyId(int id) {
        ArrayList<String> CgRestrolist = new ArrayList<>();
        RealmResults<MMGCgRestroModel> mmgCgRestroModels = realm.where(MMGCgRestroModel.class).equalTo("CGR_ID", id).findAll();

        for (MMGCgRestroModel model : mmgCgRestroModels) {
            CgRestrolist.add(model.getCGR_DESC());
        }
        return CgRestrolist;
    }

    public RealmResults<MMGCgRestroModel> fetchCGRId(int id) {
        return realm.where(MMGCgRestroModel.class).equalTo("CGR_ID", id).findAll();
    }

    public ArrayList<String> fetchCGR() {
        ArrayList<String> CgRestrolist = new ArrayList<>();
        RealmResults<MMGCgRestroModel> mmgCgRestroModels = realm.where(MMGCgRestroModel.class).findAll();

        for (MMGCgRestroModel model : mmgCgRestroModels) {
            CgRestrolist.add(model.getCGR_DESC());
        }
        return CgRestrolist;
    }

    public void deleteCGRTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGCgRestroModel.class);
            }
        });
    }


    // ---------------------- FCR Table ----------------------

    public MMGFcRestroModel getFCRExist() {
        return realm.where(MMGFcRestroModel.class).findFirst();
    }

    public void addFCR(MMGFcRestroModel mmgFcRestroModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgFcRestroModel);
            }
        });
    }

    public MMGFcRestroModel fetchFCRByName(String name) {
        return realm.where(MMGFcRestroModel.class).equalTo("FCR_DESC", name).findFirst();
    }

    public ArrayList<String> fetchFCRyId(int id) {
        ArrayList<String> FcRestrolist = new ArrayList<>();
        RealmResults<MMGFcRestroModel> mmgFcRestroModels = realm.where(MMGFcRestroModel.class).equalTo("FCR_ID", id).findAll();

        for (MMGFcRestroModel model : mmgFcRestroModels) {
            FcRestrolist.add(model.getFCR_DESC());
        }
        return FcRestrolist;
    }

    public RealmResults<MMGFcRestroModel> fetchFCRId(int id) {
        return realm.where(MMGFcRestroModel.class).equalTo("FCR_ID", id).findAll();
    }

    public ArrayList<String> fetchFCR() {
        ArrayList<String> FcRestrolist = new ArrayList<>();
        RealmResults<MMGFcRestroModel> mmgFcRestroModels = realm.where(MMGFcRestroModel.class).findAll();

        for (MMGFcRestroModel model : mmgFcRestroModels) {
            FcRestrolist.add(model.getFCR_DESC());
        }
        return FcRestrolist;
    }

    public void deleteFCRTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGFcRestroModel.class);
            }
        });
    }


    // ----------------------Material Details Table ----------------------

    public MMGMaterialDetailsModel getMaterialDetailsExist() {
        return realm.where(MMGMaterialDetailsModel.class).findFirst();
    }

    public void addMaterialDetails(MMGMaterialDetailsModel mmgMaterialDetailsModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgMaterialDetailsModel);
            }
        });
    }

    public MMGMaterialDetailsModel fetchMaterialDetailsByName(String name) {
        return realm.where(MMGMaterialDetailsModel.class).equalTo("MM_NAME", name).findFirst();
    }

    public ArrayList<String> fetchMaterialDetailsyId(int id) {
        ArrayList<String> MaterialDetlist = new ArrayList<>();
        RealmResults<MMGMaterialDetailsModel> mmgMaterialDetailsModels = realm.where(MMGMaterialDetailsModel.class).equalTo("MM_ID", id).findAll();

        for (MMGMaterialDetailsModel model : mmgMaterialDetailsModels) {
            MaterialDetlist.add(model.getMM_NAME());
        }
        return MaterialDetlist;
    }

    public RealmResults<MMGMaterialDetailsModel> fetchMaterialDetailsId(int id) {
        return realm.where(MMGMaterialDetailsModel.class).equalTo("MM_ID", id).findAll();
    }

    public ArrayList<String> fetchMaterialDetails() {
        ArrayList<String> MaterialDetlist = new ArrayList<>();
        RealmResults<MMGMaterialDetailsModel> mmgMaterialDetailsModels = realm.where(MMGMaterialDetailsModel.class).findAll();

        for (MMGMaterialDetailsModel model : mmgMaterialDetailsModels) {
            MaterialDetlist.add(model.getMM_NAME());
        }
        return MaterialDetlist;
    }

    public void deleteMaterialDetailsTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGMaterialDetailsModel.class);
            }
        });
    }

    // ---------------------Meter Status Table ----------------------

    public MMGMeterStatusModel getMeterStatusExist() {
        return realm.where(MMGMeterStatusModel.class).findFirst();
    }

    public void addMeterStatusDetails(MMGMeterStatusModel mmgMeterStatusModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgMeterStatusModel);
            }
        });
    }

    public MMGMeterStatusModel fetchMeterStatusByName(String name) {
        return realm.where(MMGMeterStatusModel.class).equalTo("MSM_METERSTATUS_NAME", name).findFirst();
    }
    public MMGMeterStatusModel fetchMeterStatus() {
        return realm.where(MMGMeterStatusModel.class).findFirst();
    }

    public ArrayList<String> fetchMeterStatusById(int id) {
        ArrayList<String> MeterStatuslist = new ArrayList<>();
        RealmResults<MMGMeterStatusModel> mmgMeterStatusModels = realm.where(MMGMeterStatusModel.class).equalTo("MSM_METERSTATUS_ID", id).findAll();

        for (MMGMeterStatusModel model : mmgMeterStatusModels) {
            MeterStatuslist.add(model.getMSM_METERSTATUS_NAME());
        }
        return MeterStatuslist;
    }

    public RealmResults<MMGMeterStatusModel> fetchMeterStatusId(int id) {
        return realm.where(MMGMeterStatusModel.class).equalTo("MSM_METERSTATUS_ID", id).findAll();
    }

    public ArrayList<String> fetchMeterStatusDetails(String id) {
        ArrayList<String> MeterStatuslist = new ArrayList<>();
        RealmResults<MMGMeterStatusModel> mmgMaterialDetailsModels = realm.where(MMGMeterStatusModel.class)
                .equalTo("FINALMETERSTATUSTAG", id).findAll();

        for (MMGMeterStatusModel model : mmgMaterialDetailsModels) {
            MeterStatuslist.add(model.getMSM_METERSTATUS_NAME());
        }
        return MeterStatuslist;
    }


    public void deleteMeterStatusTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGMeterStatusModel.class);
            }
        });
    }

    // ---------------------Vendor Detail Table ----------------------

    public MMGVendorDetModel getVendorDetExist() {
        return realm.where(MMGVendorDetModel.class).findFirst();
    }

    public void addVendorDet(MMGVendorDetModel mmgVendorDetModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgVendorDetModel);
            }
        });
    }

    public MMGVendorDetModel fetchVendorByName(String name) {
        return realm.where(MMGVendorDetModel.class).equalTo("NAME", name).findFirst();
    }

    public MMGContEmpModel fetchcontractorEmpByName(String name) {
        return realm.where(MMGContEmpModel.class).equalTo("NAME", name).findFirst();
    }

    public MMGContEmpModel fetchcontractorEmpByVendor(String id) {
        return realm.where(MMGContEmpModel.class).equalTo("EM_VENDOR_ID", id).findFirst();
    }

    public ArrayList<String> fetchVendorById(String id) {
        ArrayList<String> Vendorlist = new ArrayList<>();
        RealmResults<MMGVendorDetModel> mmgVendorDetModels = realm.where(MMGVendorDetModel.class).equalTo("EM_EMP_CODE", id).findAll();

        for (MMGVendorDetModel model : mmgVendorDetModels) {
            Vendorlist.add(model.getNAME());
        }
        return Vendorlist;
    }

    public MMGVendorDetModel fetchContEmpName(String id) {//InternalEmployee
        return realm.where(MMGVendorDetModel.class).equalTo("EM_EMP_CODE", id).findFirst();
    }

    public MMGContEmpModel fetchMMGContractorName(String id) {
        return realm.where(MMGContEmpModel.class).equalTo("EM_EMP_CODE", id).findFirst();
    }

    public ArrayList<String> fetchVendorDetails() {
        ArrayList<String> VendorDetlist = new ArrayList<>();
        RealmResults<MMGVendorDetModel> mmgVendorDetModels = realm.where(MMGVendorDetModel.class).findAll();

        for (MMGVendorDetModel model : mmgVendorDetModels) {
            VendorDetlist.add(model.getNAME());
        }
        return VendorDetlist;
    }

    public void deletVendorTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGVendorDetModel.class);
            }
        });
    }


    // ---------------------Cont Emp Table ----------------------

    public MMGContEmpModel getContEmpExist() {
        return realm.where(MMGContEmpModel.class).findFirst();
    }

    public SRModel getSRModelExist() {
        return realm.where(SRModel.class).findFirst();
    }

    public DMAModel getDMAModelExist() {
        return realm.where(DMAModel.class).findFirst();
    }

    public void addContEmpDet(MMGContEmpModel mmgContEmpModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgContEmpModel);
            }
        });
    }

    public MMGContEmpModel fetchContEmpByName(String name) {
        return realm.where(MMGContEmpModel.class).equalTo("NAME", name).findFirst();
    }

    public ArrayList<String> fetchContEmpById(String id) {
        ArrayList<String> ContEmplist = new ArrayList<>();
        RealmResults<MMGContEmpModel> mmgContEmpModels = realm.where(MMGContEmpModel.class).equalTo("EM_EMP_CODE", id).findAll();

        for (MMGContEmpModel model : mmgContEmpModels) {
            ContEmplist.add(model.getEM_VENDOR_ID());
        }
        return ContEmplist;
    }

    public RealmResults<MMGContEmpModel> fetchContEmpId(int id) {
        return realm.where(MMGContEmpModel.class).equalTo("NAME", id).findAll();
    }

    public ArrayList<String> fetchContEmpDetails() {
        ArrayList<String> ContEmpDetlist = new ArrayList<>();
        RealmResults<MMGContEmpModel> mmgContEmpModels = realm.where(MMGContEmpModel.class).findAll();

        for (MMGContEmpModel model : mmgContEmpModels) {
            ContEmpDetlist.add(model.getNAME());
        }
        return ContEmpDetlist;
    }

    public void deletContEmpTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGContEmpModel.class);
            }
        });
    }

    /*Code by Ashwini*/
    public ArrayList<String> fetchVendorByEmpCode(String id) {
        ArrayList<String> ContEmplist = new ArrayList<>();
        RealmResults<MMGContEmpModel> mmgContEmpModels = realm.where(MMGContEmpModel.class).equalTo("EM_VENDOR_ID", id).findAll();

        for (MMGContEmpModel model : mmgContEmpModels) {
            ContEmplist.add(model.getNAME());
        }
        return ContEmplist;
    }

    public ArrayList<String> fetchVendorByEmpName(String name) {
        ArrayList<String> ContEmplist = new ArrayList<>();
        RealmResults<MMGContEmpModel> mmgContEmpModels = realm.where(MMGContEmpModel.class).equalTo("NAME", name).findAll();

        for (MMGContEmpModel model : mmgContEmpModels) {
            ContEmplist.add(model.getEM_EMP_CODE());
        }
        return ContEmplist;
    }


    public RealmResults<MMGContEmpModel> fetchVendorByContractorList(String id) {
        return realm.where(MMGContEmpModel.class).equalTo("EM_VENDOR_ID", id).findAll();
    }


    // ---------------------Civil Measurement Details Table ----------------------

    public MMGCvlMeasurementResponseModel getCvlMesurementExist() {
        return realm.where(MMGCvlMeasurementResponseModel.class).findFirst();
    }

    public void addCvlMesurementDet(MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgCvlMeasurementResponseModel);
            }
        });
    }

    public MMGCvlMeasurementResponseModel fetchCvlMesurementByName(String name) {
        return realm.where(MMGCvlMeasurementResponseModel.class).equalTo("MCD_MATERIAL_NAME", name).findFirst();
    }

    public ArrayList<String> fetchCvlMesurementById(int id) {
        ArrayList<String> CvlMesurementlist = new ArrayList<>();
        RealmResults<MMGCvlMeasurementResponseModel> mmgCvlMeasurementResponseModels = realm.where(MMGCvlMeasurementResponseModel.class).equalTo("MCD_MATERIAL_ID", id).findAll();

        for (MMGCvlMeasurementResponseModel model : mmgCvlMeasurementResponseModels) {
            CvlMesurementlist.add(model.getMCD_MATERIAL_NAME());
        }
        return CvlMesurementlist;
    }

    public RealmResults<MMGCvlMeasurementResponseModel> fetchCvlMesurementId(int id) {
        return realm.where(MMGCvlMeasurementResponseModel.class).equalTo("MCD_MATERIAL_NAME", id).findAll();
    }

    public ArrayList<MMGCvlMeasurementResponseModel> fetchCvlMesurementDetails() {
        ArrayList<MMGCvlMeasurementResponseModel> CvlMesurementlist = new ArrayList<>();
        RealmResults<MMGCvlMeasurementResponseModel> mmgCvlMeasurementResponseModels = realm.where(MMGCvlMeasurementResponseModel.class).findAll();

        for (MMGCvlMeasurementResponseModel model : mmgCvlMeasurementResponseModels) {
            CvlMesurementlist.add(new MMGCvlMeasurementResponseModel(model.getSLNO(), model.getMCD_MATERIAL_ID(),
                    model.getMCD_MATERIAL_NAME(), model.getMCD_ISDROPDOWN(), model.getMCD_ISQUANTITY(),
                    model.getDDLID(), model.getQUANTITY(), model.getLENGTH(), model.getWIDTH(), model.getDEPTH()));
        }
        return CvlMesurementlist;
    }

    public void deletCvlMesurementTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGCvlMeasurementResponseModel.class);
            }
        });
    }


    // ---------------------Dropdown Details Table ----------------------

    public MMGMaterialResponseModel getdrpdownExist() {
        return realm.where(MMGMaterialResponseModel.class).findFirst();
    }

    public void adddrpdownDet(MMGMaterialResponseModel mmgMaterialResponseModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mmgMaterialResponseModel);
            }
        });
    }

    public MMGMaterialResponseModel fetchdrpdownByName(String name) {
        return realm.where(MMGMaterialResponseModel.class).equalTo("MRM_MATERIAL_NAME", name).findFirst();
    }


    public MMGMaterialResponseModel fetchdrpdownById(String id) {
        return realm.where(MMGMaterialResponseModel.class).equalTo("MRM_MATERIAL_ID", id).findFirst();
    }

    public ArrayList<String> fetchdrpdownById(int id) {
        ArrayList<String> drpdownlist = new ArrayList<>();
        RealmResults<MMGMaterialResponseModel> mmgMaterialResponseModels = realm.where(MMGMaterialResponseModel.class).equalTo("MRM_MATERIAL_ID", id).findAll();

        for (MMGMaterialResponseModel model : mmgMaterialResponseModels) {
            drpdownlist.add(model.getMRM_MATERIAL_NAME());
        }
        return drpdownlist;
    }

    public RealmResults<MMGMaterialResponseModel> fetchdrpdownId(int id) {
        return realm.where(MMGMaterialResponseModel.class).equalTo("MRM_MATERIAL_NAME", id).findAll();
    }

    public ArrayList<String> fetchdrpdownDetails() {
        ArrayList<String> drpdownlist = new ArrayList<>();
        RealmResults<MMGMaterialResponseModel> mmgMaterialResponseModels = realm.where(MMGMaterialResponseModel.class).findAll();

        for (MMGMaterialResponseModel model : mmgMaterialResponseModels) {
            drpdownlist.add(model.getMRM_MATERIAL_NAME());
        }
        return drpdownlist;
    }

    public void deledrpdownTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MMGMaterialResponseModel.class);
            }
        });
    }

    //meter status observation

    public MStatusObservationModel getMStatusObservationExist() {
        return realm.where(MStatusObservationModel.class).findFirst();
    }

    public void addMStatusObservation(MStatusObservationModel mStatusObservationModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mStatusObservationModel);
            }
        });
    }

    public MStatusObservationModel fetchStatusObservationByID(String id) {
        return realm.where(MStatusObservationModel.class).equalTo("MSNM_MSTATUS_ID", id).findFirst();
    }

    public void deleteMStatusObservationTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MStatusObservationModel.class);
            }
        });
    }

    public ArrayList<String> fetchStatusObservationList(String id) {
        ArrayList<String> statusObservationlist = new ArrayList<>();
        RealmResults<MStatusObservationModel> mStatusObservationModels = realm.where(MStatusObservationModel.class).equalTo("MSNM_MSTATUS_ID", id).findAll();

        statusObservationlist.add("--Select--");
        for (MStatusObservationModel mStatusObservationModel : mStatusObservationModels) {
            statusObservationlist.add(mStatusObservationModel.getMSNM_MSUBSTATUS_NAME());
        }
        return statusObservationlist;
    }

    public RealmResults<MStatusObservationModel> fetchMeterStatusObservationList(int id) {
        return realm.where(MStatusObservationModel.class).equalTo("MSNM_MSTATUS_ID", id).findAll();
    }


    public MStatusObservationModel fetchMeterObservationById(String name) {
        return realm.where(MStatusObservationModel.class).equalTo("MSNM_MSUBSTATUS_NAME", name).findFirst();
    }

    public MeterObservationModel fetchMeterObservationByIdMeterReading(String name) {
        return realm.where(MeterObservationModel.class).equalTo("DFM_DEFNAME", name).findFirst();
    }

    public MMGMeterStatusModel fetchContractrStatusById(int id) {
        return realm.where(MMGMeterStatusModel.class).equalTo("MSM_METERSTATUS_ID", id).findFirst();
    }

    public MStatusObservationModel fetchContractrObservationById(String id) {
        return realm.where(MStatusObservationModel.class).equalTo("MSNM_MSUBSTATUS_ID", id).findFirst();
    }


    public MMGObersvationModel fetchContractrReasonById(String id) {
        return realm.where(MMGObersvationModel.class).equalTo("OCRM_ID", id).findFirst();
    }

    //----------MSR--------SR---------DMA------

    public MSRModel getMSRExist() {
        return realm.where(MSRModel.class).findFirst();
    }

    public void addMSR(MSRModel msrModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(msrModel);
            }
        });
    }

    public ArrayList<String> fetchMSRDetails() {
        ArrayList<String> msrDetailArrayList = new ArrayList<>();
        RealmResults<MSRModel> msrModelRealmResults = realm.where(MSRModel.class).findAll();

        for (MSRModel msrModel : msrModelRealmResults) {
            msrDetailArrayList.add(msrModel.getSBM_NAME());
        }
        return msrDetailArrayList;
    }

    public RealmResults<MSRModel> fetchMSRNameID() {
        return realm.where(MSRModel.class).findAll();
    }

    public RealmResults<SRModel> fetchSRNameID() {
        return realm.where(SRModel.class).findAll();
    }

    public RealmResults<DMAModel> fetchDMANameID() {
        return realm.where(DMAModel.class).findAll();
    }

    public void deleteMSRTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MSRModel.class);
            }
        });
    }

    public SRModel getSRExist() {
        return realm.where(SRModel.class).findFirst();
    }

    public void addSR(SRModel srModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(srModel);
            }
        });
    }

    public SRModel fetchSRIdByMSRName(String id) {
        return realm.where(SRModel.class).equalTo("TRM_ID", id).findFirst();
    }

    public SRModel fetchSRNameByMSRName(String name) {
        return realm.where(SRModel.class).equalTo("TRM_NAME", name).findFirst();
    }

  /*  public MSRModel fetchMSRById(String name) {
        return realm.where(MeterObservationModel.class).equalTo("DFM_DEFNAME", name).findFirst();
    }*/

    public ArrayList<String> fetchSRList() {
        ArrayList<String> srlist = new ArrayList<>();
        RealmResults<SRModel> srModels = realm.where(SRModel.class).findAll();


        for (SRModel srModel : srModels) {
            srlist.add(srModel.getTRM_NAME());
        }
        return srlist;
    }


    public RealmResults<SRModel> fetchSRByMSRIDList(String id) {
        return realm.where(SRModel.class).equalTo("MSRID", id).findAll();
    }


    public ArrayList<String> fetchActionFormList(String code) {
        ArrayList<String> actionFormlist = new ArrayList<>();
        RealmResults<ActionFormModel> actionFormModels = realm.where(ActionFormModel.class).equalTo("A_COM_TYPE", code).findAll();


        for (ActionFormModel actionFormModel : actionFormModels) {
            actionFormlist.add(actionFormModel.getA_NAME());
        }
        return actionFormlist;
    }

    public ArrayList<String> fetchCompleteActionFormList() {
        ArrayList<String> actionFormlist = new ArrayList<>();
        RealmResults<ActionFormModel> actionFormModels = realm.where(ActionFormModel.class).findAll();


        for (ActionFormModel actionFormModel : actionFormModels) {
            actionFormlist.add(actionFormModel.getA_NAME());
        }
        return actionFormlist;
    }

    public ArrayList<String> fetchSRListName(String msrID) {
        ArrayList<String> srlist = new ArrayList<>();
        RealmResults<SRModel> srModels = realm.where(SRModel.class).equalTo("MSRID", msrID).findAll();


        for (SRModel srModel : srModels) {
            srlist.add(srModel.getTRM_NAME());
        }
        return srlist;
    }

    public ArrayList<String> fetchSRListById(String id) {
        ArrayList<String> statusObservationlist = new ArrayList<>();
        RealmResults<SRModel> mStatusObservationModels = realm.where(SRModel.class).equalTo("MSNM_MSTATUS_ID", id).findAll();

        statusObservationlist.add("--Select--");
        for (SRModel mStatusObservationModel : mStatusObservationModels) {
            statusObservationlist.add(mStatusObservationModel.getTRM_NAME());
        }
        return statusObservationlist;
    }

    public void deleteSRTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(SRModel.class);
            }
        });
    }

    public DMAModel getDMAExist() {
        return realm.where(DMAModel.class).findFirst();
    }

    public void addDMA(DMAModel srModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(srModel);
            }
        });
    }

    public DMAModel fetchDMABySRId(String id) {
        return realm.where(DMAModel.class).equalTo("PM_ID", id).findFirst();
    }

    public DMAModel fetchDMABySRName(String srid) {
        return realm.where(DMAModel.class).equalTo("SRID", srid).findFirst();
    }

    public DMAModel getDMAId(String name) {
        return realm.where(DMAModel.class).equalTo("PM_NAME", name).findFirst();
    }

    public SRModel getSrId(String name) {
        return realm.where(SRModel.class).equalTo("TRM_NAME", name).findFirst();
    }

    public ArrayList<String> fetchDMAList() {
        ArrayList<String> dmalist = new ArrayList<>();
        RealmResults<DMAModel> dmaModels = realm.where(DMAModel.class).findAll();

        for (DMAModel dmaModel : dmaModels) {
            dmalist.add(dmaModel.getPM_NAME());
        }
        return dmalist;
    }

    public ArrayList<String> fetchDMAList(String id) {

        ArrayList<String> dmalist = new ArrayList<>();
        RealmResults<DMAModel> dmaModels = realm.where(DMAModel.class).equalTo("SRID", id).findAll();

        dmalist.add("Select");
        for (DMAModel dmaModel : dmaModels) {
            dmalist.add(dmaModel.getPM_NAME());
        }
        return dmalist;
    }

    public ArrayList<String> fetchDMAListAll(String id) {
        ArrayList<String> dmalist = new ArrayList<>();
        RealmResults<DMAModel> dmaModels = realm.where(DMAModel.class).equalTo("SRID", id).findAll();

        dmalist.add("All");
        for (DMAModel dmaModel : dmaModels) {
            dmalist.add(dmaModel.getPM_NAME());
        }
        return dmalist;
    }

    public void deleteDMATable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DMAModel.class);
            }
        });
    }

    public void deleteDownloadCustomerTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DownloadCustomerType.class);
            }
        });
    }

    public void deleteDownloadSourceTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DownloadSourceType.class);
            }
        });
    }

    public ArrayList<String> fetchSrbyMSRCode(String id) {
        ArrayList<String> SrbyMSRCode = new ArrayList<>();
        // RealmResults<SRModel> mmgContEmpModels  = realm.where(SRModel.class).equalTo("TRM_ID", id).findAll();
        RealmResults<SRModel> mmgContEmpModels = realm.where(SRModel.class).equalTo("TRM_ID", id).findAll();

        for (SRModel model : mmgContEmpModels) {
            SrbyMSRCode.add(model.getTRM_NAME());
        }
        return SrbyMSRCode;
    }

    public ArrayList<String> fetchSrbyMSRCode1() {
        ArrayList<String> SrbyMSRCode = new ArrayList<>();
        // RealmResults<SRModel> mmgContEmpModels  = realm.where(SRModel.class).equalTo("TRM_ID", id).findAll();
        RealmResults<SRModel> mmgContEmpModels = realm.where(SRModel.class).findAll();

        for (SRModel model : mmgContEmpModels) {
            SrbyMSRCode.add(model.getTRM_NAME());
        }
        return SrbyMSRCode;
    }

    public ArrayList<String> fetchDMAbySRCode(String id) {
        ArrayList<String> SrbyMSRCode = new ArrayList<>();
        // RealmResults<SRModel> mmgContEmpModels  = realm.where(SRModel.class).equalTo("TRM_ID", id).findAll();
        RealmResults<DMAModel> mmgContEmpModels = realm.where(DMAModel.class).equalTo("PM_ID", id).findAll();

        for (DMAModel model : mmgContEmpModels) {
            SrbyMSRCode.add(model.getPM_NAME());
        }
        return SrbyMSRCode;
    }

    public ArrayList<String> fetchOtherConDetails() {
        ArrayList<String> VendorDetlist = new ArrayList<>();
        RealmResults<MMGVendorDetModel> mmgVendorDetModels = realm.where(MMGVendorDetModel.class).findAll();

        for (MMGVendorDetModel model : mmgVendorDetModels) {
            VendorDetlist.add("Other");
            VendorDetlist.add(model.getNAME());
        }
        return VendorDetlist;
    }


    public MMGVendorDetModel fetchVendorEmpByName(String name) {
        return realm.where(MMGVendorDetModel.class).equalTo("NAME", name).findFirst();
    }

    public MMGVendorDetModel fetchVendorEmpById(String id) {
        return realm.where(MMGVendorDetModel.class).equalTo("EM_EMP_CODE", id).findFirst();
    }

    // Consumer detail
    public void insertCustomerDetails(CustomerDetailsAnnex6 customerDetailsAnnex6) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(customerDetailsAnnex6);
            }
        });
    }

    // Old Meter detail
    public void insertOldMeterDetail(OldMeterDetailAnnex6 oldMeterDetailAnnex6) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(oldMeterDetailAnnex6);
            }
        });
    }

    // New Meter detail
    public void insertNewMeterDetail(NewMeterDetailAnnex6 newMeterDetailAnnex6) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(newMeterDetailAnnex6);
            }
        });
    }

    // Contractor Detail
    public void insertContractorDetail(ContractorDetailAnnex6 contractorDetailAnnex6) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(contractorDetailAnnex6);
            }
        });
    }

    // MeterDigit (DialDigit)
    public ArrayList<String> fetchDiaDigit() {
        ArrayList<String> dialDigitList = new ArrayList<>();
        RealmResults<MDialDigitModel> mDialDigitModels = realm.where(MDialDigitModel.class).findAll();

        for (MDialDigitModel model : mDialDigitModels) {
            dialDigitList.add(model.getDIGITTEXT());
        }
        return dialDigitList;
    }

    public MDialDigitModel getDialDigitExists() {
        return realm.where(MDialDigitModel.class).findFirst();
    }

    public void addDialDigit(MDialDigitModel mDialDigitModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(mDialDigitModel);
            }
        });
    }

    public void deletDialDigitTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MDialDigitModel.class);
            }
        });
    }

    public MDialDigitModel fetchDigitById(String id) {
        return realm.where(MDialDigitModel.class).equalTo("OCRM_ID", id).findFirst();
    }

    //ERROR LOG MODEL

    public Number getErrorIDCount() {
        return realm.where(ErrorLogModel.class).max("id");
    }

    public void insertErrorLogData(ErrorLogModel errorLogModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                //realm.copyToRealmOrUpdate(employeeLoginModels);
                realm.insert(errorLogModel);
            }
        });
    }


    public void insertErrorLogDataa(int id, ErrorLogModel errorLogModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                Number num = realm.where(ErrorLogModel.class).max("id");
                int nextID;
                if (num == null) {
                    nextID = 1;
                } else {
                    nextID = num.intValue() + 1;
                }
                ErrorLogModel errorLogModel = realm.createObject(ErrorLogModel.class, nextID);
                realm.insert(errorLogModel);
            }
        });
    }


    //error log model

    public ErrorLogModel getErrorLogExit() {
        return realm.where(ErrorLogModel.class).findFirst();
    }


    public RealmResults<ErrorLogModel> uploadErrorData(int isUploaded) {
        return realm.where(ErrorLogModel.class).equalTo("UPLOADSTATUS", isUploaded).limit(10).findAll();
    }


    public void updateErrorLogCount(final int ID, int uploadCount) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ErrorLogModel errorLogModel = realm.where(ErrorLogModel.class).equalTo("id", ID).and().equalTo("UPLOADSTATUS", 0).findFirst();
                assert errorLogModel != null;
                errorLogModel.setUPLOADSTATUS(uploadCount);
            }
        });
    }


    public MSRModel fetchMSRByString(String name) {
        return realm.where(MSRModel.class).equalTo("SBM_NAME", name).findFirst();
    }

    public SRModel fetchSRByString(String name) {
        return realm.where(SRModel.class).equalTo("TRM_NAME", name).findFirst();
    }

    public RealmResults<SRModel> fetchMSRById(String name) {
        return realm.where(SRModel.class).equalTo("MSRID", name).findAll();
    }


    public RealmResults<DMAModel> fetchDMABySRIDList(String id) {
        return realm.where(DMAModel.class).equalTo("SRID", id).findAll();
    }


    public FixerDataListModel getFixerDataListModelExit() {
        return realm.where(FixerDataListModel.class).findFirst();
    }

    public void deleteFixerDataTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(FixerDataListModel.class);
            }
        });
    }

    public void addFixerData(FixerDataListModel fixerDataListModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(fixerDataListModel);
            }
        });
    }

    public RealmResults<FixerDataListModel> fetchFixerDataList() {
        return realm.where(FixerDataListModel.class).findAll();
    }


    public FixerDataListModel fetchFixerId(String name) {
        return realm.where(FixerDataListModel.class).equalTo("NAME", name).findFirst();
    }

    public MMGRequestTypeModel fetchRequestTypeId(String name) {
        return realm.where(MMGRequestTypeModel.class).equalTo("SelectVal", name).findFirst();
    }

    public RealmResults<DocTypeModel> fetchDocTypeName(String name) {

        return realm.where(DocTypeModel.class).equalTo("DOCUMENTSOURCE", name).sort("DOCUMENTSOURCE", Sort.ASCENDING).findAll();
    }

    public RealmResults<DocSubTypeModel> fetchDocSubTypeName(String name) {
        return realm.where(DocSubTypeModel.class).equalTo("DOCUMENTSUBTYPEID", name).sort("DOCUMENTSUBTYPE", Sort.ASCENDING).findAll();
    }

    public DocTypeModel fetchDocTypeId(String name) {
        return realm.where(DocTypeModel.class).equalTo("DOCUMENTTYPE", name).sort("DOCUMENTSOURCE", Sort.ASCENDING).findFirst();
    }

    public DocSubTypeModel fetchDocSubTypeId(String name, String id) {
        return realm.where(DocSubTypeModel.class).equalTo("DOCUMENTSUBTYPE", name).equalTo("DOCUMENTSUBTYPEID", id).sort("DOCUMENTSUBTYPE", Sort.ASCENDING).findFirst();
    }

    public RealmResults<MMGSubZoneModel> fetchGroupData(int name) {
        return realm.where(MMGSubZoneModel.class).equalTo("PCM_BU_ID", name).findAll();
    }


    // ----------------------  Android HSC Table ----------------------

    public void deleteConnCategoryTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ConnCategoryModel.class);
            }
        });
    }

    public void addConnCategory(ConnCategoryModel connCategoryModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(connCategoryModel);
            }
        });
    }

    public void deletePropertyTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(PropertyTypeModel.class);
            }
        });
    }

    public void addPropertyType(PropertyTypeModel propertyTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(propertyTypeModel);
            }
        });
    }

    public void deleteConnPurposeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ConnPurposeModel.class);
            }
        });
    }

    public void addConnPurpose(ConnPurposeModel connPurposeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(connPurposeModel);
            }
        });
    }

    public void deleteDwellingUnitTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(DwellingUnitModel.class);
            }
        });
    }

    public void addDwellingUnit(DwellingUnitModel dwellingUnitModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(dwellingUnitModel);
            }
        });
    }

    public void deleteNetworkDistLineSize_MeterSanctionSizeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(SizeModel.class);
            }
        });
    }

    public void addNetworkDistLineSize_MeterSanctionSize(SizeModel sizeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(sizeModel);
            }
        });
    }

    public void deleteRoadRestorationLenRoadTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(RoadRestorationLenRoadModel.class);
            }
        });
    }

    public void addRoadRestorationLenRoad(RoadRestorationLenRoadModel roadRestorationLenRoadModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(roadRestorationLenRoadModel);
            }
        });
    }

    public void deleteRoadTypeTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(RoadTypeModel.class);
            }
        });
    }

    public void addRoadType(RoadTypeModel roadTypeModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(roadTypeModel);
            }
        });
    }

    public void deleteRoadOwnershipTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(RoadOwnershipModel.class);
            }
        });
    }

    public void addRoadOwnership(RoadOwnershipModel roadOwnershipModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(roadOwnershipModel);
            }
        });
    }


    public void deleteWardTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(WardModel.class);
            }
        });
    }

    public void addWard(WardModel wardModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(wardModel);
            }
        });
    }

    public void deleteLotTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(LotModel.class);
            }
        });
    }

    public void addLot(LotModel lotModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(lotModel);
            }
        });
    }

    public void deleteAreaTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(AreaModel.class);
            }
        });
    }

    public void deleteRejectTable() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(RejectModel.class);
            }
        });
    }

    public void addArea(AreaModel areaModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(areaModel);
            }
        });
    }

    public void addReject(RejectModel rejectModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(rejectModel);
            }
        });
    }

    public AreaModel getAreaModel() {
        return realm.where(AreaModel.class).findFirst();
    }

    public ConnCategoryModel getConnCategory() {
        return realm.where(ConnCategoryModel.class).findFirst();
    }

    public ConnPurposeModel getConnPurpose() {
        return realm.where(ConnPurposeModel.class).findFirst();
    }

    public DwellingUnitModel getDwellingUnit() {
        return realm.where(DwellingUnitModel.class).findFirst();
    }

    public LotModel getLot() {
        return realm.where(LotModel.class).findFirst();
    }

    public PropertyTypeModel getPropertyType() {
        return realm.where(PropertyTypeModel.class).findFirst();
    }

    public RoadOwnershipModel getRoadOwnership() {
        return realm.where(RoadOwnershipModel.class).findFirst();
    }

    public RoadRestorationLenRoadModel getRoadRestorationLenRoad() {
        return realm.where(RoadRestorationLenRoadModel.class).findFirst();
    }

    public RoadTypeModel getRoadTypeModel() {
        return realm.where(RoadTypeModel.class).findFirst();
    }

    public SizeModel getSizeModel() {
        return realm.where(SizeModel.class).findFirst();
    }

    public WardModel getWardModel() {
        return realm.where(WardModel.class).findFirst();
    }

    public ArrayList<String> fetchConnectionCategoryName() {
        ArrayList<String> connectionCategorylist = new ArrayList<>();
        RealmResults<ConnCategoryModel> connCategoryModels = realm.where(ConnCategoryModel.class).findAll();

        for (ConnCategoryModel connCategoryModel : connCategoryModels) {
            connectionCategorylist.add(connCategoryModel.getCATEGORY_NAME());
        }
        return connectionCategorylist;
    }

    public ArrayList<String> fetchPropertyTypeName() {
        ArrayList<String> PropertyTypelist = new ArrayList<>();
        RealmResults<PropertyTypeModel> propertyTypeModels = realm.where(PropertyTypeModel.class).findAll();

        for (PropertyTypeModel propertyTypeModel : propertyTypeModels) {
            PropertyTypelist.add(propertyTypeModel.getPRM_NAME());
        }
        return PropertyTypelist;
    }


    public PropertyTypeModel fetchPropertyTypeNameByID(String id) {
        return realm.where(PropertyTypeModel.class).equalTo("PRM_ID", id).findFirst();
    }

    public ConnPurposeModel fetchConnPurposeTypeNameByID(String id) {
        return realm.where(ConnPurposeModel.class).equalTo("MCT_ID", id).findFirst();
    }


    public ArrayList<String> fetchConnectionPurposeName() {
        ArrayList<String> connectionPurposelist = new ArrayList<>();
        RealmResults<ConnPurposeModel> connPurposeModels = realm.where(ConnPurposeModel.class).sort("MCT_CONNTYPE_NAME", Sort.ASCENDING).findAll();

        for (ConnPurposeModel model : connPurposeModels) {
            connectionPurposelist.add(model.getMCT_CONNTYPE_NAME());
        }
        return connectionPurposelist;
    }

    public ArrayList<String> fetchnumberOfDwellingListName() {
        ArrayList<String> numberOfDwellingList = new ArrayList<>();
        RealmResults<DwellingUnitModel> dwellingUnitModels = realm.where(DwellingUnitModel.class).findAll();

        for (DwellingUnitModel model : dwellingUnitModels) {
            numberOfDwellingList.add(model.getTEXT());
        }
        return numberOfDwellingList;
    }

    public ArrayList<String> fetchDistanceNetworkLineSizeListName() {
        ArrayList<String> distanceNetworkLineSizeList = new ArrayList<>();
        RealmResults<SizeModel> sizeModels = realm.where(SizeModel.class).findAll();

        for (SizeModel model : sizeModels) {
            distanceNetworkLineSizeList.add(model.getNAME());
        }
        return distanceNetworkLineSizeList;
    }

    public ArrayList<String> fetchRoadOwnerShipListName() {
        ArrayList<String> roadOwnerShipStringList = new ArrayList<>();
        RealmResults<RoadOwnershipModel> sizeModels = realm.where(RoadOwnershipModel.class).findAll();

        for (RoadOwnershipModel model : sizeModels) {
            roadOwnerShipStringList.add(model.getREM_REASONNM());
        }
        return roadOwnerShipStringList;
    }

    public ArrayList<String> fetchRoadTypeListListName() {
        ArrayList<String> roadTypeListStringList = new ArrayList<>();
        RealmResults<RoadTypeModel> roadTypeModels = realm.where(RoadTypeModel.class).findAll();

        for (RoadTypeModel model : roadTypeModels) {
            roadTypeListStringList.add(model.getREM_REASONNM());
        }
        return roadTypeListStringList;
    }

    public ArrayList<String> roadRestorationListName() {
        ArrayList<String> roadRestorationStringList = new ArrayList<>();
        RealmResults<RoadRestorationLenRoadModel> roadRestorationLenRoadModels = realm.where(RoadRestorationLenRoadModel.class).findAll();

        for (RoadRestorationLenRoadModel model : roadRestorationLenRoadModels) {
            roadRestorationStringList.add(model.getTEXT());
        }
        return roadRestorationStringList;
    }

    public RealmResults<WardModel> fetchWardData(String id) {
        return realm.where(WardModel.class).equalTo("ZONEID", id).findAll();
    }

    public RealmResults<LotModel> fetchLotData(String zoneId, String subZoneId) {
        return realm.where(LotModel.class).equalTo("BUID", zoneId).equalTo("PCID", subZoneId).findAll();
    }

    public MMGSubZoneModel fetchGroupName(String name) {
        return realm.where(MMGSubZoneModel.class).equalTo("PCM_PC_NAME", name).findFirst();
    }

    public ArrayList<String> fetchAreaListName() {
        ArrayList<String> areaList = new ArrayList<>();
        RealmResults<AreaModel> areaModels = realm.where(AreaModel.class).findAll();

        for (AreaModel model : areaModels) {
            areaList.add(model.getNAME());
        }
        return areaList;
    }

    public WardModel fetchWardByNameId(String name) {
        return realm.where(WardModel.class).equalTo("NAME", name).findFirst();
    }

    public LotModel fetchLotId(String name) {
        return realm.where(LotModel.class).equalTo("MR_NAME", name).findFirst();
    }

    public AreaModel fetchAreaId(String name) {
        return realm.where(AreaModel.class).equalTo("NAME", name).findFirst();
    }

    public PropertyTypeModel getPropertyTypeId(String name) {
        return realm.where(PropertyTypeModel.class).equalTo("PRM_NAME", name).findFirst();
    }

    public ConnPurposeModel getConnPurposeTypeId(String name) {
        return realm.where(ConnPurposeModel.class).equalTo("MCT_ID", name).findFirst();
    }

    public SizeModel getSizeTypeId(String name) {
        return realm.where(SizeModel.class).equalTo("NAME", name).findFirst();
    }

    public RoadRestorationLenRoadModel getRoadRestortionId(String name) {
        return realm.where(RoadRestorationLenRoadModel.class).equalTo("TEXT", name).findFirst();
    }

    public RoadTypeModel getRoadTypeId(String name) {
        return realm.where(RoadTypeModel.class).equalTo("REM_REASONNM", name).findFirst();
    }

    public RoadOwnershipModel getRoadOwnershipId(String name) {
        return realm.where(RoadOwnershipModel.class).equalTo("REM_REASONNM", name).findFirst();
    }

    public ConnPurposeModel getConnPurposeId(String name) {
        return realm.where(ConnPurposeModel.class).equalTo("MCT_CONNTYPE_NAME", name).findFirst();
    }

    public ConnCategoryModel getConnCategoryId(String name) {
        return realm.where(ConnCategoryModel.class).equalTo("CATEGORY_NAME", name).findFirst();
    }

//    shantanu's tables for collection module

    public void addBankMaster(BankMasterModel bankMasterModel) {
    realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
            realm.insert(bankMasterModel);
        }
    });
}

    public RealmResults<BankMasterModel> fetchAllBankMaster() {
        return realm.where(BankMasterModel.class).findAll();
    }

    public String isExistBankMicr(String colName,String val) {
        BankMasterModel user = realm.where(BankMasterModel.class).equalTo(colName, val).findFirst();

        if (user != null) {
            return user.getID()+"@"+user.getNAME();
        } else {
            return "Failure";
        }
    }
    public void addConnectionStatus(ConnectionStatusModel connectionStatusModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(connectionStatusModel);
            }
        });
    }

    public RealmResults<ConnectionStatusModel> fetchConnectionStatus() {
        return realm.where(ConnectionStatusModel.class).findAll();
    }
}
