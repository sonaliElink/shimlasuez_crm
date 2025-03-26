package elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Model;

import com.google.gson.annotations.SerializedName;

public class NCGetWorkReAllocateDataModel {
    @SerializedName("SRM_BU_ID")
    private String SRM_BU_ID;

    @SerializedName("SRM_PC_ID")
    private String SRM_PC_ID;

    @SerializedName("BUM_BU_NAME")
    private String BUM_BU_NAME;

    @SerializedName("CIRCLE_NAME")
    private String CIRCLE_NAME;

    @SerializedName("FLM_APP_NO")
    private String FLM_APP_NO;

    @SerializedName("FLM_APP_TCODE")
    private String FLM_APP_TCODE;


    @SerializedName("NAME")
    private String NAME;

    @SerializedName("CONSUMER_NAME")
    private String CONSUMER_NAME;


    @SerializedName("FRM_REASONNAME")
    private String FRM_REASONNAME;

    @SerializedName("FLM_APP_DATE")
    private String FLM_APP_DATE;

    @SerializedName("FLM_SOURCE")
    private String FLM_SOURCE;

    @SerializedName("FLM_COMPTYPE")
    private String FLM_COMPTYPE;

    @SerializedName("REMARKS")
    private String REMARKS;

    @SerializedName("SRM_S_ADDRESS")
    private String SRM_S_ADDRESS;

    @SerializedName("COMPFOR")
    private String COMPFOR;

    @SerializedName("FLM_PHONE")
    private String FLM_PHONE;

    @SerializedName("STATUS")
    private String STATUS;

    @SerializedName("FCOMNO")
    private String FCOMNO;

    @SerializedName("FLM_SERVICENUMBER")
    private String FLM_SERVICENUMBER;

    @SerializedName("TARIFF")
    private String TARIFF;

    @SerializedName("VIP")
    private String VIP;

    @SerializedName("SRM_BILL_AMOUNT")
    private String SRM_BILL_AMOUNT;


    @SerializedName("ASSIGNEDCODE")
    private String ASSIGNEDCODE;


    @SerializedName("ASSIGNEDNAME")
    private String ASSIGNEDNAME;


    @SerializedName("PRIORITY")
    private String PRIORITY;



    public NCGetWorkReAllocateDataModel(){}


    public NCGetWorkReAllocateDataModel(String BUM_BU_NAME, String CIRCLE_NAME, String FLM_APP_NO, String FLM_APP_TCODE, String NAME, String CONSUMER_NAME, String FRM_REASONNAME, String FLM_APP_DATE, String FLM_SOURCE, String FLM_COMPTYPE, String REMARKS, String SRM_S_ADDRESS, String COMPFOR, String FLM_PHONE, String STATUS, String FCOMNO, String FLM_SERVICENUMBER,
                                       String TARIFF, String VIP, String SRM_BILL_AMOUNT,String SRM_BU_ID,String SRM_PC_ID,String ASSIGNEDCODE, String ASSIGNEDNAME, String PRIORITY) {
        this.BUM_BU_NAME = BUM_BU_NAME;
        this.CIRCLE_NAME = CIRCLE_NAME;
        this.FLM_APP_NO = FLM_APP_NO;
        this.FLM_APP_TCODE = FLM_APP_TCODE;
        this.NAME = NAME;
        this.CONSUMER_NAME = CONSUMER_NAME;
        this.FRM_REASONNAME = FRM_REASONNAME;
        this.FLM_APP_DATE = FLM_APP_DATE;
        this.FLM_SOURCE = FLM_SOURCE;
        this.FLM_COMPTYPE = FLM_COMPTYPE;
        this.REMARKS = REMARKS;
        this.SRM_S_ADDRESS = SRM_S_ADDRESS;
        this.COMPFOR = COMPFOR;
        this.FLM_PHONE = FLM_PHONE;
        this.STATUS = STATUS;
        this.FCOMNO = FCOMNO;
        this.FLM_SERVICENUMBER = FLM_SERVICENUMBER;
        this.TARIFF = TARIFF;
        this.VIP = VIP;
        this.SRM_BILL_AMOUNT = SRM_BILL_AMOUNT;
        this.SRM_BU_ID = SRM_BU_ID;
        this.SRM_PC_ID = SRM_PC_ID;
        this.ASSIGNEDCODE = ASSIGNEDCODE;
        this.ASSIGNEDNAME = ASSIGNEDNAME;
        this.PRIORITY = PRIORITY;
    }

    public String getBUM_BU_NAME() {
        return BUM_BU_NAME;
    }

    public void setBUM_BU_NAME(String BUM_BU_NAME) {
        this.BUM_BU_NAME = BUM_BU_NAME;
    }

    public String getCIRCLE_NAME() {
        return CIRCLE_NAME;
    }

    public void setCIRCLE_NAME(String CIRCLE_NAME) {
        this.CIRCLE_NAME = CIRCLE_NAME;
    }

    public String getFLM_APP_NO() {
        return FLM_APP_NO;
    }

    public String getSRM_BU_ID() {
        return SRM_BU_ID;
    }

    public void setSRM_BU_ID(String SRM_BU_ID) {
        this.SRM_BU_ID = SRM_BU_ID;
    }

    public String getSRM_PC_ID() {
        return SRM_PC_ID;
    }

    public void setSRM_PC_ID(String SRM_PC_ID) {
        this.SRM_PC_ID = SRM_PC_ID;
    }

    public void setFLM_APP_NO(String FLM_APP_NO) {
        this.FLM_APP_NO = FLM_APP_NO;
    }

    public String getFLM_APP_TCODE() {
        return FLM_APP_TCODE;
    }

    public void setFLM_APP_TCODE(String FLM_APP_TCODE) {
        this.FLM_APP_TCODE = FLM_APP_TCODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCONSUMER_NAME() {
        return CONSUMER_NAME;
    }

    public void setCONSUMER_NAME(String CONSUMER_NAME) {
        this.CONSUMER_NAME = CONSUMER_NAME;
    }

    public String getFRM_REASONNAME() {
        return FRM_REASONNAME;
    }

    public void setFRM_REASONNAME(String FRM_REASONNAME) {
        this.FRM_REASONNAME = FRM_REASONNAME;
    }

    public String getFLM_APP_DATE() {
        return FLM_APP_DATE;
    }

    public void setFLM_APP_DATE(String FLM_APP_DATE) {
        this.FLM_APP_DATE = FLM_APP_DATE;
    }

    public String getFLM_SOURCE() {
        return FLM_SOURCE;
    }

    public void setFLM_SOURCE(String FLM_SOURCE) {
        this.FLM_SOURCE = FLM_SOURCE;
    }

    public String getFLM_COMPTYPE() {
        return FLM_COMPTYPE;
    }

    public void setFLM_COMPTYPE(String FLM_COMPTYPE) {
        this.FLM_COMPTYPE = FLM_COMPTYPE;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getSRM_S_ADDRESS() {
        return SRM_S_ADDRESS;
    }

    public void setSRM_S_ADDRESS(String SRM_S_ADDRESS) {
        this.SRM_S_ADDRESS = SRM_S_ADDRESS;
    }

    public String getCOMPFOR() {
        return COMPFOR;
    }

    public void setCOMPFOR(String COMPFOR) {
        this.COMPFOR = COMPFOR;
    }

    public String getFLM_PHONE() {
        return FLM_PHONE;
    }

    public void setFLM_PHONE(String FLM_PHONE) {
        this.FLM_PHONE = FLM_PHONE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getFCOMNO() {
        return FCOMNO;
    }

    public void setFCOMNO(String FCOMNO) {
        this.FCOMNO = FCOMNO;
    }


    public String getFLM_SERVICENUMBER() {
        return FLM_SERVICENUMBER;
    }

    public void setFLM_SERVICENUMBER(String FLM_SERVICENUMBER) {
        this.FLM_SERVICENUMBER = FLM_SERVICENUMBER;
    }

    public String getTARIFF() {
        return TARIFF;
    }

    public void setTARIFF(String TARIFF) {
        this.TARIFF = TARIFF;
    }

    public String getVIP() {
        return VIP;
    }

    public void setVIP(String VIP) {
        this.VIP = VIP;
    }

    public String getSRM_BILL_AMOUNT() {
        return SRM_BILL_AMOUNT;
    }

    public void setSRM_BILL_AMOUNT(String SRM_BILL_AMOUNT) {
        this.SRM_BILL_AMOUNT = SRM_BILL_AMOUNT;
    }

    public String getASSIGNEDCODE() {
        return ASSIGNEDCODE;
    }

    public void setASSIGNEDCODE(String ASSIGNEDCODE) {
        this.ASSIGNEDCODE = ASSIGNEDCODE;
    }

    public String getASSIGNEDNAME() {
        return ASSIGNEDNAME;
    }

    public void setASSIGNEDNAME(String ASSIGNEDNAME) {
        this.ASSIGNEDNAME = ASSIGNEDNAME;
    }

    public String getPRIORITY() {
        return PRIORITY;
    }

    public void setPRIORITY(String PRIORITY) {
        this.PRIORITY = PRIORITY;
    }
}
