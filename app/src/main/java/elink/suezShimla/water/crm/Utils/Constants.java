
package elink.suezShimla.water.crm.Utils;

public class  Constants {


    //shimla URLs
    //test
    public static final String URL = "https://test.camssjpnl.suezindia.in/WebService/FieldForce.asmx";
    public static final String MIC_URL = "https://test.camssjpnl.suezindia.in/WebUserControls/show_img.aspx?url=";

    //test url not used from 28/02/2025
 /*    public static final String URL = "https://ssmc.elinkjal.com/WebService/FieldForce.asmx";
    public static final String MIC_URL = "https://ssmc.elinkjal.com/WebUserControls/show_img.aspx?url=";*/

    //live url

/*
    public static final String URL = "https://subdom.camscbe.in/WebService/FieldForce.asmx";
    public static final String MIC_URL = "https://subdom.camscbe.in/WebUserControls/show_img.aspx?url=";

*/





    //    test pst_grey
 /*   public static final String URL = "https://mjp.elinkjal.com/WebService/FieldForce.asmx";
    public static final String MIC_URL = "https://mjp.elinkjal.com/WebUserControls/show_img.aspx?url=";
    //receipt download test url
    public static final String ReceiptUrl = "https://mjpwss.elinkjal.com/consumer/frm_Receiptprint1.aspx?ReqNo=";*/
    //    test oracle
/*
    public static final String URL = "https://mjps.elinkjal.com/WebService/FieldForce.asmx";
    public static final String MIC_URL = "https://mjps.elinkjal.com/WebUserControls/show_img.aspx?url=";
    //receipt download test url
    public static final String ReceiptUrl = "https://mjpwss.elinkjal.com/consumer/frm_Receiptprint1.aspx?ReqNo=";
*/

//live url

/*  public static final String URL = "https://billing.mjpwms.in/WebService/FieldForce.asmx";
  public static final String MIC_URL = "https://billing.mjpwms.in/WebUserControls/show_img.aspx?url=";*/
//    receipt download live url
    public static final String ReceiptUrl = "https://mjpwms.in/consumer/frm_Receiptprint1.aspx?ReqNo=";
    /*

    public static final String URL = "https://smc.elinkjal.com/WebService/FieldForce.asmx";
    public static final String MIC_URL = "https://smc.elinkjal.com/WebUserControls/show_img.aspx?url=";
*/


    public static final String NameSpace = "http://tempuri.org/";

    // ---------------------------- Register ----------------------------

    public static final String RegisterDevice = "RegisterDevice";
    
    // ---------------------------- Server Date Time ----------------------------

    public static final String ServerDateTime = "GetDateTime";

    // ---------------------------- Get Employee Rights ----------------------------

    public static final String GetRights = "GetRights";

    // ---------------------------- Send SMS -------------------------------------

    public static final String sendSMS = "SendSMS";

    // ---------------------------- Master Data ----------------------------

    // Spinner Master Data - LoginActivity.java
    public static final String  MasterData = "GetMasterDataForAndroid";

    // ---------------------------- Complaints ----------------------------

    // search consumer by consumerNo on complaint module - ComplaintRegistrationActivity.java
    public static final String ComplaintSearchByConsumerNo = "Complaint_SearchConsumerByConsumerNo";


    public static final String Complaint_BillAdjRequestDropdown = "Complaint_BillAdjRequestDropdown";



    // Complaint Dashboard Service - ZoneAndWardDetailsActivity.java
    public static final String ComplaintDashboard = "Complaint_WaterComplaintStatusSummaryWithConsumer";

    // Check if Complaint Type is Duplicate or Not - ComplaintRegisterActivity.java
    public static final String DuplicateComplaints = "Check_DuplicateComplaints";

    // complaint history - ComplaintRegisterActivity.java
    public static final String ComplaintRegistration = "Complaint_Registeration";

    // request history - ComplaintRegisterActivity.java
    public static final String Request_Registeration = "Request_Registeration";

    // complaint register - ComplaintRegisterActivity.java
    public static final String ComplaintHistory = "Complaint_History";

    // get work allocation - WorkAllocationActivity.java
    public static final String GetWorkAllocationData = "Complaint_GetWorkAllocationData";

    // auto allocation work - WorkAllocationActivity.java
    public static final String ComplaintAutoAllocate = "Complaint_AutoAllocate";

    // re-allocation work - WorkReallocationActivity.java
    public static final String GetWorkReallocation = "GetWorkReallocation";

    // work completion - WorkCompletionActivity.java
    public static final String GetWorkCompletionData = "Complaint_GetWorkCompletionData";


    // work allocation - WorkAllocationDetailsActivity.java
    public static final String WorkAllocation = "Complaint_WorkAllocation";

    // work completion - WorkCompletionDetailedActivity (submit button)
    public static final String UpdateWorkCompletionData = "Complaint_WorkCompletion";

    // work completion - TodayWorkCompletionActivity
    public static final String TodayWorkCompletionData="Complaint_TodaysWorkCompletedBySE";

    // login- LoginActivity
    public static final String Login = "UserLogin";

 // login- master Count
    public static final String MobileDashboard = "MobileDashboard";

    public static final String NSCDASHBOARD = "NSCDASHBOARD";

     //MMG Module Starts Here...
    //MMG  accept at mmg dept records summery


    // Spinner MMG Master Data - LoginActivity.java

    public static final String GetMasterDataForAndroid_MMG  = "GetMasterDataForAndroid_MMG";

    public static final String MMG_AcceptAtMMGSummary = "MMG_AcceptAtMMGSummary";

    //MMG  accept at mmg Detail
    public static final String MMG_AcceptAtMMGDetail = "MMG_AcceptAtMMGDetail";

    //MMG  accept at mmg Detail submit button  action
    public static final String MMG_AcceptAtMMGSubmit = "MMG_AcceptAtMMGSubmit";

    //MMG  Issue To Meter Fixer Show list
    public static final String MMG_IssueMeterToFixerShow = "MMG_IssueMeterToFixerShow";


    //MMG  Strore Management Accept Meter Save
    public static final String MMG_StoreManagementAcceptMeterSave ="MMG_StoreManagementAcceptMeterSave";


    //MMG  Strore Management Authentication Show
    public static final String MMG_StoreManagementAuthenticationShow ="MMG_StoreManagementAuthenticationShow";

    //MMG  Issue to MMG Dept Show
    public static final String MMG_StoreManagementIssueToMMGSummary ="MMG_StoreManagementIssueToMMGSummary";

    //MMG  Issue to MMG Dept Details
    public static final String MMG_StoreManagementIssueToMMGDetail = "MMG_StoreManagementIssueToMMGDetail";

    //MMG  Issue to MMG Dept Details Save
    public static final String MMG_StoreManagementIssueToMMGSave = "MMG_StoreManagementIssueToMMGSave";

    //MMG  Meter Installation Show
    public static final String MMG_MeterInstallationReplacementShow = "MMG_MeterInstallationReplacementShow";


    //MMG Replacement Entry Show
    public static final String MMG_MeterInstallationReplacementEntryShow = "MMG_MeterInstallationReplacementEntryShow";

    public static final String MMG_MeterInstallationReplacementEntryShow_1 = "MMG_MeterInstallationReplacementEntryShow_1";

    // No Consumer Registration
    public static final String NCComplaintRegistration = "NComplaint_Registeration";


    // No Consumer Work Allocation Get data
    public static final String NComplaint_WorkAllocationGetData = "NComplaint_WorkAllocationGetData";

    // No Consumer Work Allocation Get data
    public static final String NComplaint_WorkReAllocationGetData = "NComplaint_WorkReAllocationGetData";

    // No Consumer WRegistration with another option
    public static final String NComplaint_CallRecordsNoConsumer ="NComplaint_CallRecordsNoConsumer";

    // No Consumer Work Allocation
    public static final String N_Complaint_WorkAllocation = "N_Complaint_WorkAllocation";

    // No Consumer Work Completion Submit Action
    public static final String NComplaint_WorkCompletion = "NComplaint_WorkCompletion";

    // No Consumer Work Completion Submit Action
    public static final String NCon_TodaysWorkCompBySE = "NCon_TodaysWorkCompBySE";

    // No Consumer Auto Allocate Work
    public static final String NOCons_Comp_AutoAlloc = "NOCons_Comp_AutoAlloc";

    // MMG Get Customer Details Fetching
    public static final String MMG_GetConsumerAndMeterDetails = "MMG_GetConsumerAndMeterDetails";


    // MMG Get Material  Details Fetching
    public static final String MMG_GetMaterialDetailsByMCSID = "MMG_GetMaterialDetailsByMCSID";

     //MMG Annexure 6 final Meter Installation Submit
    public static final String MeterInstallation = "MeterInstallation";

    //MMG Annexure 6 final Meter Installation Submit NEW API
    public static final String MeterInstallationSave = "MeterInstallationSave";

    //MMG Annexure 6 get civil details
    public static final String MMG_GetCivilDetails = "MMG_GetCivilDetails";


    //MMG Annexure 6 get Contractor details
    public static final String MMG_GetMeterContractorDetails = "MMG_GetMeterContractorDetails";

    //GET_SR_BY_MSRID
    public static final String MMG_GetSRByMSRIdDetails = "GetSRByMSRId";

    //GET_DMA_BY_SRID
    public static final String MMG_GetDMABySRIdDetails = "GetDMABySRId";

    //GET_Complaint_Source
    public static final String Complaint_Source = "Complaint_Source";

    //GET_DuplicateEntry
    public static final String Check_DuplicateComplaints = "Check_DuplicateComplaints";

    //Complaint bill month
    public static final String Complaint_BillAdjBillMonth = "Complaint_BillAdjBillMonth";


    // work allocation - WorkCompletionBillingAdjustmentActivity.java
    public static final String Complaint_BillAdjRequest = "Complaint_BillAdjRequest";

    //new Allocation data CRM
    public static final String Complaint_GetWorkCompletionData = "Complaint_GetWorkCompletionData";


    //new MeterReading CRM
    public static final String ComplaintReading = "ComplaintReading";


     //new tracking
    public static final String Complaint_Detail = "Complaint_Detail";

 //reapeat call
    public static final String Complaint_Repeat_call = "COMPLAINT_REPEATCALL";

//UpdateApplicationStatus
    public static final String Update_Application_Status = "UpdateApplicationStatus";


    public static final String GetComplaintAndConsumerNo = "GetComplaintAndConsumerNo";
    public static final String MMG_GetConsumerAndMeterDetailsForIntNetAudit = "MMG_GetConsumerAndMeterDetailsForIntNetAudit";
    public static final String MeterIntNetAuditForsave = "MeterIntNetAuditForsave";
    public static final String getBalContractorQty = "getBalContractorQty";
    public static final String MMG_GetSealAvailabilityDetails = "MMG_GetSealAvailabilityDetails";


    //SharedPrefenence for MMG Annexure 6

    public static final String METERSIZEID = "meterSizeId";
    public static final String OLD_METER_NO = "oldmeterNo";
    public static final String NEW_METER_NO = "newmeterNo";
    public static final String CONSUMER_NAME = "consumerName";
    public static final String CONSUMER_NO = "consumerNo";
    public static final String CONSUMER_SOURCE = "consumerSource";
    public static final String BU = "zone";
    public static final String No_ofFlat = "noofFlat";
    public static final String PC = "group";
    public static final String Meterowner = "MeterOwners";


    public static final String CONSUMERREFERENCENUMBER = "connsumerReferenceNumber";

    public static final String CONTACTORNAME = "contactorname";
    public static final String VENDORCODE = "vendorcode";
    public static final String  CONTACTOREMP= "contactoremp";
    public static final String  MATERIALHANDOVER= "materialhandover";
    public static final String  NEW_MAKERCODE= "new_makercode";
    public static final String  NEW_METERNUM= "new_meterNum";
    public static final String  OLD_INSTALLDATE= "old_installDate";
    public static final String  O_MANUFACTURE_CODE= "o_manufacute_code";
    public static final String  NEW_INSTALLDATE= "new_installDate";
    public static final String  NEW_METERSIZE= "new_metersize";
    public static final String  NEW_SEALNO= "new_sealno";
    public static final String  NEW_INITIALREADING= "new_initialreading";
    public static final String  NEW_AVERAGECONSUMTION= "new_averageconsumtion";
    public static final String  NEW_METERTYPE= "new_metertype";
    public static final String  NEW_METERLOCATION= "new_meterLocation";
    public static final String  NEW_PROTECTEDBOX= "new_ protectedBox";
    public static final String  NEW_TAXNO= "new_taxno";
    public static final String  PCCLEN= "pcclen";
    public static final String  PCCWIDTH= "pccwidth";
    public static final String  PCCDEPTH= "pccdepth";
    public static final String  PCCTOTAL= "pcctotal";
    public static final String  RDCUTTINGID= "rdcuttingid";
    public static final String  RDCUTTINGLENGTH= "rdcuttinglength";
    public static final String  RDCUTTINGWIDTH= "rdcuttingwidth";
    public static final String  RDCUTTINGDEPTH= "rdcuttingdepth";
    public static final String  RDCUTTINGTOTAL= "rdcuttingtotal";
    public static final String  MATERIALXML= "materialxml";
    public static final String  CIVILMEASUREMENTXML= "civilmeasurementxml";
    public static final String  OLD_MAKERCODE= "old_makercode";
    public static final String  OLD_METERNUM= "old_meternum";
    public static final String  OLD_INSTALLDT= "old_installdt";
    public static final String  OLD_METERSIZE= "old_metersize";
    public static final String  OLDSEALNUM= "oldsealno";
    public static final String  PASTMETERNO= "pastmeterno";
    public static final String  OLDMTRSTS= "oldmtrsts";
    public static final String  OLDMETERTYPE= "oldmetertype";
    public static final String  FINALREADING= "finalReading";
    public static final String  FINALSTATUS= "finalStatus";
    public static final String  REASONID= "reasonId";
    public static final String  RADIOBUTTONVAL= "radiobuttonval";
    public static final String  COMPSUBTYPE= "compsubtype";
    public static final String  METERSTATUS= "meterstatus";
    public static final String  CONNECTIONLOAD= "connectionload";
    public static final String  SUBMITMATERIALBUTTONTAG="submitmaterialtag";
    public static final String  SUBMITCVLMEASUREMENTBUTTONTAG="submitcvlmeasurementtag";
    public static final String  SUBMITCIVILLIST="submitcivillist";
    public static final String  MAKERCODENAME = "makercodename";
    public static final String  METERTYPENAME = "metertypename";
    public static final String  serachById="serachById";
    public static final String  COMMISIONED_NONCOMMISIONED="commisioned_noncommisioned";
    public static final String  PROPERTY_ASSESSMENT="property_assessment";
    public static final String  FROM_NODE="from_node";
    public static final String  TO_NODE="to_node";
    public static final String  PRIMARY_MOBILE = "primaryMob";
    public static final String  ALTERNATE_MOBILE = "alternateMob";
    public static final String  GIS_BID = "gis_bidStr";
    public static final String  SUBMIT_STATUS = "submitStatus";
    public static final String  MTR_SIZE_ID = "mtrSizeId";
    public static final String  ALLOCATED_WORK_LIST = "allocatedWorkList";
    public static final String  MTR_TYPE_CODE_ID = "mtrTypeCodeId";
    public static final String  VALIDMETER = "valid_meter";
    public static final String  CONTLIST = "contlist";


    public static final String MMGGetAllMtrInstallByCont = "MMGGetAllMtrInstallByCont";
    public static final String GetMeterInstallById = "GetMeterInstallById";
    public static final String MMG_ContractorAndMMGEmp = "MMG_ContractorAndMMGEmp";
    public static final String MMG_IssueMeterToFixerSave = "MMG_IssueMeterToFixerSave";
    public static final String S_ComplaintSection = "S_ComplaintSection";
    public static final String UploadPhoto = "UploadPhoto";
    public static final String GetApplicationData = "GetApplicationData";
    public static final String GetMasterDataForAndroid_HSC = "GetMasterDataForAndroid_HSC";
    public static final String SaveSiteVisitData = "SaveSiteVisitData";
    public static final String SaveSiteVisitActiveToPlugData = "SaveSiteVisitActiveToPlugData";
    public static final String SCISPayment = "S_CIS_Payment";
    public static final String CheckSameBldg_Consumer = "CheckSameBldg_Consumer";
    public static final String CheckSameZone_Consumer = "CheckSameZone_Consumer";
    public static final String MI_N_METEROWNERSHIP = "MI_N_METEROWNERSHIP";

    public static final String  APPVERSION = "APPVERSION";
    public static final String  IMEI = "IMEI";
    public static final String  FCMTOKEN = "FCMTOKEN";
    public static final String  APP_ISLOGGED = "APP_ISLOGGED";
    public static final String  DATAFOUND = "DATAFOUND";
    public static final String  MI_METERINSTALLID = "MI_METERINSTALLID";
    public static final String  RADIOBUTTONVALSTR = "RADIOBUTTONVALSTR";
}
