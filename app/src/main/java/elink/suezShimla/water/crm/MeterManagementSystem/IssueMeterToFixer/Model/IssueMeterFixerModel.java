package elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Model;

import com.google.gson.annotations.SerializedName;

public class IssueMeterFixerModel {


    @SerializedName("BUM_BU_ID")
    private String BUM_BU_ID;

    @SerializedName("BU")
    private String BU;

    @SerializedName("PC")
    private String PC;

    @SerializedName("PCNAME")
    private String PCNAME;

    @SerializedName("REF_NO")
    private String REF_NO;

    @SerializedName("SERVICENO")
    private String SERVICENO;

    @SerializedName("METER_SEQ_CODE")
    private String METER_SEQ_CODE;


    @SerializedName("SERVICE_TOD_FLAG")
    private String SERVICE_TOD_FLAG;

    @SerializedName("SERVICE_TOD_FLAG_ID")
    private String SERVICE_TOD_FLAG_ID;

    @SerializedName("PHASE")
    private String PHASE;


    @SerializedName("PURPOSE")
    private String PURPOSE;

    @SerializedName("CONSUMERNAME")
    private String CONSUMERNAME;

    @SerializedName("NAME_ADDRESS")
    private String NAME_ADDRESS;

    @SerializedName("OLDMETERNO")
    private String OLDMETERNO;

    @SerializedName("METER_INDICATION_ID")
    private String METER_INDICATION_ID;

    @SerializedName("METER_INDICATION")
    private String METER_INDICATION;


    @SerializedName("TODFLAG")
    private String TODFLAG;

    @SerializedName("METER_TOD_FLAG_ID")
    private String METER_TOD_FLAG_ID;

    @SerializedName("SOURCETYPE")
    private String SOURCETYPE;

    @SerializedName("SRM_S_MOBILE_NO")
    private String SRM_S_MOBILE_NO;

    @SerializedName("MRT_PROCESS_CODE")
    private String MRT_PROCESS_CODE;

    @SerializedName("COMPLAINT_SUBTYPE")
    private String COMPLAINT_SUBTYPE;

    @SerializedName("MRT_REMARKS")
    private String MRT_REMARKS;

    @SerializedName("SRM_TRANSFORMER_ID")
    private String SRM_TRANSFORMER_ID;

    @SerializedName("REJ_RES")
    private String REJ_RES;

    @SerializedName("OBS")
    private String OBS;

    @SerializedName("SUBTYPE")
    private String SUBTYPE;

    @SerializedName("OCRM_ID")
    private String OCRM_ID;

    @SerializedName("MRT_METER_CONNECTIONLOAD")
    private String MRT_METER_CONNECTIONLOAD;

    @SerializedName("MRT_LOAD_UNIT")
    private String MRT_LOAD_UNIT;

    @SerializedName("MRT_PRIORITY")
    private String MRT_PRIORITY;

    @SerializedName("MRT_TRANSACTION_ID")
    private String MRT_TRANSACTION_ID;

    @SerializedName("MRT_REQUESTDATE")
    private String MRT_REQUESTDATE;

    @SerializedName("SRM_CONNSIZE_ID")
    private String SRM_CONNSIZE_ID;


    public IssueMeterFixerModel() {
    }

    public IssueMeterFixerModel(String BUM_BU_ID, String BU, String PC, String PCNAME, String REF_NO, String SERVICENO, String METER_SEQ_CODE, String SERVICE_TOD_FLAG, String SERVICE_TOD_FLAG_ID, String PHASE, String PURPOSE, String CONSUMERNAME, String NAME_ADDRESS, String OLDMETERNO, String METER_INDICATION_ID, String METER_INDICATION, String TODFLAG, String METER_TOD_FLAG_ID, String SOURCETYPE, String SRM_S_MOBILE_NO, String MRT_PROCESS_CODE, String COMPLAINT_SUBTYPE, String MRT_REMARKS, String SRM_TRANSFORMER_ID, String REJ_RES, String OBS, String SUBTYPE, String OCRM_ID, String MRT_METER_CONNECTIONLOAD, String MRT_LOAD_UNIT, String MRT_PRIORITY, String MRT_TRANSACTION_ID, String MRT_REQUESTDATE, String SRM_CONNSIZE_ID) {
        this.BUM_BU_ID = BUM_BU_ID;
        this.BU = BU;
        this.PC = PC;
        this.PCNAME = PCNAME;
        this.REF_NO = REF_NO;
        this.SERVICENO = SERVICENO;
        this.METER_SEQ_CODE = METER_SEQ_CODE;
        this.SERVICE_TOD_FLAG = SERVICE_TOD_FLAG;
        this.SERVICE_TOD_FLAG_ID = SERVICE_TOD_FLAG_ID;
        this.PHASE = PHASE;
        this.PURPOSE = PURPOSE;
        this.CONSUMERNAME = CONSUMERNAME;
        this.NAME_ADDRESS = NAME_ADDRESS;
        this.OLDMETERNO = OLDMETERNO;
        this.METER_INDICATION_ID = METER_INDICATION_ID;
        this.METER_INDICATION = METER_INDICATION;
        this.TODFLAG = TODFLAG;
        this.METER_TOD_FLAG_ID = METER_TOD_FLAG_ID;
        this.SOURCETYPE = SOURCETYPE;
        this.SRM_S_MOBILE_NO = SRM_S_MOBILE_NO;
        this.MRT_PROCESS_CODE = MRT_PROCESS_CODE;
        this.COMPLAINT_SUBTYPE = COMPLAINT_SUBTYPE;
        this.MRT_REMARKS = MRT_REMARKS;
        this.SRM_TRANSFORMER_ID = SRM_TRANSFORMER_ID;
        this.REJ_RES = REJ_RES;
        this.OBS = OBS;
        this.SUBTYPE = SUBTYPE;
        this.OCRM_ID = OCRM_ID;
        this.MRT_METER_CONNECTIONLOAD = MRT_METER_CONNECTIONLOAD;
        this.MRT_LOAD_UNIT = MRT_LOAD_UNIT;
        this.MRT_PRIORITY = MRT_PRIORITY;
        this.MRT_TRANSACTION_ID = MRT_TRANSACTION_ID;
        this.MRT_REQUESTDATE = MRT_REQUESTDATE;
        this.SRM_CONNSIZE_ID = SRM_CONNSIZE_ID;
    }



    public IssueMeterFixerModel(String BU, String PCNAME, String NAME_ADDRESS, String SRM_S_MOBILE_NO, String SERVICENO, String REF_NO, String OLDMETERNO, String SUBTYPE, String MRT_REQUESTDATE, String REJ_RES, String OBS, String SOURCETYPE, String BUM_BU_ID,String PC,String OCRM_ID){
        this.BU = BU;
        this.PCNAME = PCNAME;
        this.NAME_ADDRESS = NAME_ADDRESS;
        this.SRM_S_MOBILE_NO = SRM_S_MOBILE_NO;
        this.SERVICENO = SERVICENO;
        this.REF_NO = REF_NO;
        this.OLDMETERNO = OLDMETERNO;
        this.SUBTYPE = SUBTYPE;
        this.MRT_REQUESTDATE = MRT_REQUESTDATE;
        this.REJ_RES = REJ_RES;
        this.OBS = OBS;
        this.SOURCETYPE = SOURCETYPE;
        this.BUM_BU_ID = BUM_BU_ID;
        this.PC = PC;
        this.OCRM_ID = OCRM_ID;






    }

    public String getBUM_BU_ID() {
        return BUM_BU_ID;
    }

    public void setBUM_BU_ID(String BUM_BU_ID) {
        this.BUM_BU_ID = BUM_BU_ID;
    }

    public String getBU() {
        return BU;
    }

    public void setBU(String BU) {
        this.BU = BU;
    }

    public String getPC() {
        return PC;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public String getPCNAME() {
        return PCNAME;
    }

    public void setPCNAME(String PCNAME) {
        this.PCNAME = PCNAME;
    }

    public String getREF_NO() {
        return REF_NO;
    }

    public void setREF_NO(String REF_NO) {
        this.REF_NO = REF_NO;
    }

    public String getSERVICENO() {
        return SERVICENO;
    }

    public void setSERVICENO(String SERVICENO) {
        this.SERVICENO = SERVICENO;
    }

    public String getMETER_SEQ_CODE() {
        return METER_SEQ_CODE;
    }

    public void setMETER_SEQ_CODE(String METER_SEQ_CODE) {
        this.METER_SEQ_CODE = METER_SEQ_CODE;
    }



    public String getSERVICE_TOD_FLAG() {
        return SERVICE_TOD_FLAG;
    }

    public void setSERVICE_TOD_FLAG(String SERVICE_TOD_FLAG) {
        this.SERVICE_TOD_FLAG = SERVICE_TOD_FLAG;
    }

    public String getSERVICE_TOD_FLAG_ID() {
        return SERVICE_TOD_FLAG_ID;
    }

    public void setSERVICE_TOD_FLAG_ID(String SERVICE_TOD_FLAG_ID) {
        this.SERVICE_TOD_FLAG_ID = SERVICE_TOD_FLAG_ID;
    }

    public String getPHASE() {
        return PHASE;
    }

    public void setPHASE(String PHASE) {
        this.PHASE = PHASE;
    }

    public String getPURPOSE() {
        return PURPOSE;
    }

    public void setPURPOSE(String PURPOSE) {
        this.PURPOSE = PURPOSE;
    }

    public String getCONSUMERNAME() {
        return CONSUMERNAME;
    }

    public void setCONSUMERNAME(String CONSUMERNAME) {
        this.CONSUMERNAME = CONSUMERNAME;
    }

    public String getNAME_ADDRESS() {
        return NAME_ADDRESS;
    }

    public void setNAME_ADDRESS(String NAME_ADDRESS) {
        this.NAME_ADDRESS = NAME_ADDRESS;
    }

    public String getOLDMETERNO() {
        return OLDMETERNO;
    }

    public void setOLDMETERNO(String OLDMETERNO) {
        this.OLDMETERNO = OLDMETERNO;
    }

    public String getMETER_INDICATION_ID() {
        return METER_INDICATION_ID;
    }

    public void setMETER_INDICATION_ID(String METER_INDICATION_ID) {
        this.METER_INDICATION_ID = METER_INDICATION_ID;
    }

    public String getMETER_INDICATION() {
        return METER_INDICATION;
    }

    public void setMETER_INDICATION(String METER_INDICATION) {
        this.METER_INDICATION = METER_INDICATION;
    }

    public String getTODFLAG() {
        return TODFLAG;
    }

    public void setTODFLAG(String TODFLAG) {
        this.TODFLAG = TODFLAG;
    }

    public String getMETER_TOD_FLAG_ID() {
        return METER_TOD_FLAG_ID;
    }

    public void setMETER_TOD_FLAG_ID(String METER_TOD_FLAG_ID) {
        this.METER_TOD_FLAG_ID = METER_TOD_FLAG_ID;
    }

    public String getSOURCETYPE() {
        return SOURCETYPE;
    }

    public void setSOURCETYPE(String SOURCETYPE) {
        this.SOURCETYPE = SOURCETYPE;
    }

    public String getSRM_S_MOBILE_NO() {
        return SRM_S_MOBILE_NO;
    }

    public void setSRM_S_MOBILE_NO(String SRM_S_MOBILE_NO) {
        this.SRM_S_MOBILE_NO = SRM_S_MOBILE_NO;
    }

    public String getMRT_PROCESS_CODE() {
        return MRT_PROCESS_CODE;
    }

    public void setMRT_PROCESS_CODE(String MRT_PROCESS_CODE) {
        this.MRT_PROCESS_CODE = MRT_PROCESS_CODE;
    }

    public String getCOMPLAINT_SUBTYPE() {
        return COMPLAINT_SUBTYPE;
    }

    public void setCOMPLAINT_SUBTYPE(String COMPLAINT_SUBTYPE) {
        this.COMPLAINT_SUBTYPE = COMPLAINT_SUBTYPE;
    }

    public String getMRT_REMARKS() {
        return MRT_REMARKS;
    }

    public void setMRT_REMARKS(String MRT_REMARKS) {
        this.MRT_REMARKS = MRT_REMARKS;
    }

    public String getSRM_TRANSFORMER_ID() {
        return SRM_TRANSFORMER_ID;
    }

    public void setSRM_TRANSFORMER_ID(String SRM_TRANSFORMER_ID) {
        this.SRM_TRANSFORMER_ID = SRM_TRANSFORMER_ID;
    }

    public String getREJ_RES() {
        return REJ_RES;
    }

    public void setREJ_RES(String REJ_RES) {
        this.REJ_RES = REJ_RES;
    }

    public String getOBS() {
        return OBS;
    }

    public void setOBS(String OBS) {
        this.OBS = OBS;
    }

    public String getSUBTYPE() {
        return SUBTYPE;
    }

    public void setSUBTYPE(String SUBTYPE) {
        this.SUBTYPE = SUBTYPE;
    }

    public String getOCRM_ID() {
        return OCRM_ID;
    }

    public void setOCRM_ID(String OCRM_ID) {
        this.OCRM_ID = OCRM_ID;
    }

    public String getMRT_METER_CONNECTIONLOAD() {
        return MRT_METER_CONNECTIONLOAD;
    }

    public void setMRT_METER_CONNECTIONLOAD(String MRT_METER_CONNECTIONLOAD) {
        this.MRT_METER_CONNECTIONLOAD = MRT_METER_CONNECTIONLOAD;
    }

    public String getMRT_LOAD_UNIT() {
        return MRT_LOAD_UNIT;
    }

    public void setMRT_LOAD_UNIT(String MRT_LOAD_UNIT) {
        this.MRT_LOAD_UNIT = MRT_LOAD_UNIT;
    }

    public String getMRT_PRIORITY() {
        return MRT_PRIORITY;
    }

    public void setMRT_PRIORITY(String MRT_PRIORITY) {
        this.MRT_PRIORITY = MRT_PRIORITY;
    }

    public String getMRT_TRANSACTION_ID() {
        return MRT_TRANSACTION_ID;
    }

    public void setMRT_TRANSACTION_ID(String MRT_TRANSACTION_ID) {
        this.MRT_TRANSACTION_ID = MRT_TRANSACTION_ID;
    }

    public String getMRT_REQUESTDATE() {
        return MRT_REQUESTDATE;
    }

    public void setMRT_REQUESTDATE(String MRT_REQUESTDATE) {
        this.MRT_REQUESTDATE = MRT_REQUESTDATE;
    }

    public String getSRM_CONNSIZE_ID() {
        return SRM_CONNSIZE_ID;
    }

    public void setSRM_CONNSIZE_ID(String SRM_CONNSIZE_ID) {
        this.SRM_CONNSIZE_ID = SRM_CONNSIZE_ID;
    }

    @Override
    public String toString() {
        return "IssueMeterFixerModel{" +
                "BUM_BU_ID='" + BUM_BU_ID + '\'' +
                ", BU='" + BU + '\'' +
                ", PC='" + PC + '\'' +
                ", PCNAME='" + PCNAME + '\'' +
                ", REF_NO='" + REF_NO + '\'' +
                ", SERVICENO='" + SERVICENO + '\'' +
                ", METER_SEQ_CODE='" + METER_SEQ_CODE + '\'' +
                ", SERVICE_TOD_FLAG='" + SERVICE_TOD_FLAG + '\'' +
                ", SERVICE_TOD_FLAG_ID='" + SERVICE_TOD_FLAG_ID + '\'' +
                ", PHASE='" + PHASE + '\'' +
                ", PURPOSE='" + PURPOSE + '\'' +
                ", CONSUMERNAME='" + CONSUMERNAME + '\'' +
                ", NAME_ADDRESS='" + NAME_ADDRESS + '\'' +
                ", OLDMETERNO='" + OLDMETERNO + '\'' +
                ", METER_INDICATION_ID='" + METER_INDICATION_ID + '\'' +
                ", METER_INDICATION='" + METER_INDICATION + '\'' +
                ", TODFLAG='" + TODFLAG + '\'' +
                ", METER_TOD_FLAG_ID='" + METER_TOD_FLAG_ID + '\'' +
                ", SOURCETYPE='" + SOURCETYPE + '\'' +
                ", SRM_S_MOBILE_NO='" + SRM_S_MOBILE_NO + '\'' +
                ", MRT_PROCESS_CODE='" + MRT_PROCESS_CODE + '\'' +
                ", COMPLAINT_SUBTYPE='" + COMPLAINT_SUBTYPE + '\'' +
                ", MRT_REMARKS='" + MRT_REMARKS + '\'' +
                ", SRM_TRANSFORMER_ID='" + SRM_TRANSFORMER_ID + '\'' +
                ", REJ_RES='" + REJ_RES + '\'' +
                ", OBS='" + OBS + '\'' +
                ", SUBTYPE='" + SUBTYPE + '\'' +
                ", OCRM_ID='" + OCRM_ID + '\'' +
                ", MRT_METER_CONNECTIONLOAD='" + MRT_METER_CONNECTIONLOAD + '\'' +
                ", MRT_LOAD_UNIT='" + MRT_LOAD_UNIT + '\'' +
                ", MRT_PRIORITY='" + MRT_PRIORITY + '\'' +
                ", MRT_TRANSACTION_ID='" + MRT_TRANSACTION_ID + '\'' +
                ", MRT_REQUESTDATE='" + MRT_REQUESTDATE + '\'' +
                ", SRM_CONNSIZE_ID='" + SRM_CONNSIZE_ID + '\'' +
                '}';
    }
}
