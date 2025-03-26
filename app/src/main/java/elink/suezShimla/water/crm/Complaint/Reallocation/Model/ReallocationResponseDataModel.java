package elink.suezShimla.water.crm.Complaint.Reallocation.Model;

import com.google.gson.annotations.SerializedName;

public class ReallocationResponseDataModel {
    @SerializedName("COM_NO_TYPE")
    private String COM_NO_TYPE;

    @SerializedName("BUM_BU_NAME")
    private String BUM_BU_NAME;

    @SerializedName("CIRCLE_NAME")
    private String CIRCLE_NAME;

    @SerializedName("SRM_MRC_ID")
    private String SRM_MRC_ID;

    @SerializedName("SRM_LOT_ID")
    private String SRM_LOT_ID;

    @SerializedName("COMNO")
    private String COMNO;

    @SerializedName("COM_COMPDATE")
    private String COM_COMPDATE;

    @SerializedName("NAME")
    private String NAME;

    @SerializedName("CONSUMER_NAME")
    private String CONSUMER_NAME;

    @SerializedName("ADDRESS_NO")
    private String ADDRESS_NO;

    @SerializedName("DTCNO")
    private String DTCNO;

    @SerializedName("POLE")
    private String POLE;

    @SerializedName("MOBILE")
    private String MOBILE;

    @SerializedName("CMTM_NAME")
    private String CMTM_NAME;

    @SerializedName("OCRM_NAME")
    private String OCRM_NAME;

    @SerializedName("COM_REMARKS")
    private String COM_REMARKS;

    @SerializedName("SRM_PHASE")
    private String SRM_PHASE;

    @SerializedName("COMPFOR")
    private String COMPFOR;

    @SerializedName("COM_STATUS")
    private String COM_STATUS;

    @SerializedName("COM_SERVICE_NO")
    private String COM_SERVICE_NO;

    @SerializedName("COM_CALLS")
    private String COM_CALLS;

    @SerializedName("COM_SOURCECD")
    private String COM_SOURCECD;

    @SerializedName("CSCM_ID")
    private String CSCM_ID;

    @SerializedName("CSCM_SECNAME")
    private String CSCM_SECNAME;

    @SerializedName("FWDTOSEC")
    private String FWDTOSEC;

    @SerializedName("FWDCOM")
    private String FWDCOM;

    @SerializedName("CATEGORY")
    private String CATEGORY;

    @SerializedName("PURPOSE")
    private String PURPOSE;

    @SerializedName("SRM_TARIFF_ID")
    private String SRM_TARIFF_ID;

    @SerializedName("TARIFF")
    private String TARIFF;

    @SerializedName("VIP")
    private String VIP;

    @SerializedName("AREAM_AREA_ID")
    private String AREAM_AREA_ID;

    @SerializedName("AREA")
    private String AREA;

    @SerializedName("SRM_BILL_AMOUNT")
    private String SRM_BILL_AMOUNT;

    @SerializedName("STATUS")
    private String STATUS;

    @SerializedName("DAYS")
    private String DAYS;

    @SerializedName("COM_YEAR_BILL")
    private String COM_YEAR_BILL;

    @SerializedName("PRIORITY")
    private String PRIORITY;

    @SerializedName("COM_SEQ")
    private String COM_SEQ;

    @SerializedName("SOURCETYPE")
    private String SOURCETYPE;

    @SerializedName("ASSIGNEDCODE")
    private String ASSIGNEDCODE;

    @SerializedName("ASSIGNEDNAME")
    private String ASSIGNEDNAME;


    @SerializedName("AGINGOFCOMPLAINT")
    private String AGINGOFCOMPLAINT;

    @SerializedName("SLA")
    private String SLA;

    @SerializedName("SRM_LATITUDE")
    private String SRM_LATITUDE;

    @SerializedName("SRM_LONGITUDE")
    private String SRM_LONGITUDE;



    public ReallocationResponseDataModel() {
    }

    public ReallocationResponseDataModel(String COMNO, String BUM_BU_NAME, String CIRCLE_NAME, String COM_COMPDATE, String CONSUMER_NAME, String ADDRESS_NO,
                                         String MOBILE, String CMTM_NAME, String OCRM_NAME, String COM_SERVICE_NO, String TARIFF, String STATUS,
                                         String COM_YEAR_BILL, String PRIORITY, String SOURCETYPE, String EMPCODE, String EMPNAME,String VIP,String COM_SEQ,String COM_NO_TYPE,String COM_CALLS,String AGINGOFCOMPLAINT,
                                         String SLA ,String SRM_LATITUDE ,String SRM_LONGITUDE) {
        this.COMNO = COMNO;
        this.BUM_BU_NAME = BUM_BU_NAME;
        this.CIRCLE_NAME = CIRCLE_NAME;
        this.COM_COMPDATE = COM_COMPDATE;
        this.CONSUMER_NAME = CONSUMER_NAME;
        this.ADDRESS_NO = ADDRESS_NO;
        this.MOBILE = MOBILE;
        this.CMTM_NAME = CMTM_NAME;
        this.OCRM_NAME = OCRM_NAME;
        this.COM_SERVICE_NO = COM_SERVICE_NO;
        this.TARIFF = TARIFF;
        this.STATUS = STATUS;
        this.COM_YEAR_BILL = COM_YEAR_BILL;
        this.PRIORITY = PRIORITY;
        this.SOURCETYPE = SOURCETYPE;
        this.ASSIGNEDCODE = EMPCODE;
        this.ASSIGNEDNAME = EMPNAME;
        this.VIP=VIP;
        this.COM_SEQ=COM_SEQ;
        this.COM_NO_TYPE=COM_NO_TYPE;
        this.COM_CALLS=COM_CALLS;
        this.AGINGOFCOMPLAINT=AGINGOFCOMPLAINT;
        this.SLA=SLA;
        this.SRM_LATITUDE=SRM_LATITUDE;
        this.SRM_LONGITUDE=SRM_LONGITUDE;

    }

    public ReallocationResponseDataModel(String COM_NO_TYPE, String BUM_BU_NAME, String CIRCLE_NAME, String SRM_MRC_ID, String SRM_LOT_ID, String COMNO, String COM_COMPDATE, String NAME, String CONSUMER_NAME, String ADDRESS_NO, String DTCNO, String POLE, String MOBILE, String CMTM_NAME, String OCRM_NAME, String COM_REMARKS, String SRM_PHASE, String COMPFOR, String COM_STATUS, String COM_SERVICE_NO, String COM_CALLS, String COM_SOURCECD, String CSCM_ID, String CSCM_SECNAME, String FWDTOSEC, String FWDCOM, String CATEGORY, String PURPOSE, String SRM_TARIFF_ID, String TARIFF, String VIP, String AREAM_AREA_ID, String AREA, String SRM_BILL_AMOUNT, String STATUS, String DAYS, String COM_YEAR_BILL, String PRIORITY, String COM_SEQ, String SOURCETYPE, String ASSIGNEDCODE, String ASSIGNEDNAME) {
        this.COM_NO_TYPE = COM_NO_TYPE;
        this.BUM_BU_NAME = BUM_BU_NAME;
        this.CIRCLE_NAME = CIRCLE_NAME;
        this.SRM_MRC_ID = SRM_MRC_ID;
        this.SRM_LOT_ID = SRM_LOT_ID;
        this.COMNO = COMNO;
        this.COM_COMPDATE = COM_COMPDATE;
        this.NAME = NAME;
        this.CONSUMER_NAME = CONSUMER_NAME;
        this.ADDRESS_NO = ADDRESS_NO;
        this.DTCNO = DTCNO;
        this.POLE = POLE;
        this.MOBILE = MOBILE;
        this.CMTM_NAME = CMTM_NAME;
        this.OCRM_NAME = OCRM_NAME;
        this.COM_REMARKS = COM_REMARKS;
        this.SRM_PHASE = SRM_PHASE;
        this.COMPFOR = COMPFOR;
        this.COM_STATUS = COM_STATUS;
        this.COM_SERVICE_NO = COM_SERVICE_NO;
        this.COM_CALLS = COM_CALLS;
        this.COM_SOURCECD = COM_SOURCECD;
        this.CSCM_ID = CSCM_ID;
        this.CSCM_SECNAME = CSCM_SECNAME;
        this.FWDTOSEC = FWDTOSEC;
        this.FWDCOM = FWDCOM;
        this.CATEGORY = CATEGORY;
        this.PURPOSE = PURPOSE;
        this.SRM_TARIFF_ID = SRM_TARIFF_ID;
        this.TARIFF = TARIFF;
        this.VIP = VIP;
        this.AREAM_AREA_ID = AREAM_AREA_ID;
        this.AREA = AREA;
        this.SRM_BILL_AMOUNT = SRM_BILL_AMOUNT;
        this.STATUS = STATUS;
        this.DAYS = DAYS;
        this.COM_YEAR_BILL = COM_YEAR_BILL;
        this.PRIORITY = PRIORITY;
        this.COM_SEQ = COM_SEQ;
        this.SOURCETYPE = SOURCETYPE;
        this.ASSIGNEDCODE = ASSIGNEDCODE;
        this.ASSIGNEDNAME = ASSIGNEDNAME;
    }

    public String getCOM_NO_TYPE() {
        return COM_NO_TYPE;
    }

    public void setCOM_NO_TYPE(String COM_NO_TYPE) {
        this.COM_NO_TYPE = COM_NO_TYPE;
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

    public String getSRM_MRC_ID() {
        return SRM_MRC_ID;
    }

    public void setSRM_MRC_ID(String SRM_MRC_ID) {
        this.SRM_MRC_ID = SRM_MRC_ID;
    }

    public String getSRM_LOT_ID() {
        return SRM_LOT_ID;
    }

    public void setSRM_LOT_ID(String SRM_LOT_ID) {
        this.SRM_LOT_ID = SRM_LOT_ID;
    }

    public String getCOMNO() {
        return COMNO;
    }

    public void setCOMNO(String COMNO) {
        this.COMNO = COMNO;
    }

    public String getCOM_COMPDATE() {
        return COM_COMPDATE;
    }

    public void setCOM_COMPDATE(String COM_COMPDATE) {
        this.COM_COMPDATE = COM_COMPDATE;
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

    public String getADDRESS_NO() {
        return ADDRESS_NO;
    }

    public void setADDRESS_NO(String ADDRESS_NO) {
        this.ADDRESS_NO = ADDRESS_NO;
    }

    public String getDTCNO() {
        return DTCNO;
    }

    public void setDTCNO(String DTCNO) {
        this.DTCNO = DTCNO;
    }

    public String getPOLE() {
        return POLE;
    }

    public void setPOLE(String POLE) {
        this.POLE = POLE;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getCMTM_NAME() {
        return CMTM_NAME;
    }

    public void setCMTM_NAME(String CMTM_NAME) {
        this.CMTM_NAME = CMTM_NAME;
    }

    public String getOCRM_NAME() {
        return OCRM_NAME;
    }

    public void setOCRM_NAME(String OCRM_NAME) {
        this.OCRM_NAME = OCRM_NAME;
    }

    public String getCOM_REMARKS() {
        return COM_REMARKS;
    }

    public void setCOM_REMARKS(String COM_REMARKS) {
        this.COM_REMARKS = COM_REMARKS;
    }

    public String getSRM_PHASE() {
        return SRM_PHASE;
    }

    public void setSRM_PHASE(String SRM_PHASE) {
        this.SRM_PHASE = SRM_PHASE;
    }

    public String getCOMPFOR() {
        return COMPFOR;
    }

    public void setCOMPFOR(String COMPFOR) {
        this.COMPFOR = COMPFOR;
    }

    public String getCOM_STATUS() {
        return COM_STATUS;
    }

    public void setCOM_STATUS(String COM_STATUS) {
        this.COM_STATUS = COM_STATUS;
    }

    public String getCOM_SERVICE_NO() {
        return COM_SERVICE_NO;
    }

    public void setCOM_SERVICE_NO(String COM_SERVICE_NO) {
        this.COM_SERVICE_NO = COM_SERVICE_NO;
    }

    public String getCOM_CALLS() {
        return COM_CALLS;
    }

    public void setCOM_CALLS(String COM_CALLS) {
        this.COM_CALLS = COM_CALLS;
    }

    public String getCOM_SOURCECD() {
        return COM_SOURCECD;
    }

    public void setCOM_SOURCECD(String COM_SOURCECD) {
        this.COM_SOURCECD = COM_SOURCECD;
    }

    public String getCSCM_ID() {
        return CSCM_ID;
    }

    public void setCSCM_ID(String CSCM_ID) {
        this.CSCM_ID = CSCM_ID;
    }

    public String getCSCM_SECNAME() {
        return CSCM_SECNAME;
    }

    public void setCSCM_SECNAME(String CSCM_SECNAME) {
        this.CSCM_SECNAME = CSCM_SECNAME;
    }

    public String getFWDTOSEC() {
        return FWDTOSEC;
    }

    public void setFWDTOSEC(String FWDTOSEC) {
        this.FWDTOSEC = FWDTOSEC;
    }

    public String getFWDCOM() {
        return FWDCOM;
    }

    public void setFWDCOM(String FWDCOM) {
        this.FWDCOM = FWDCOM;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getPURPOSE() {
        return PURPOSE;
    }

    public void setPURPOSE(String PURPOSE) {
        this.PURPOSE = PURPOSE;
    }

    public String getSRM_TARIFF_ID() {
        return SRM_TARIFF_ID;
    }

    public void setSRM_TARIFF_ID(String SRM_TARIFF_ID) {
        this.SRM_TARIFF_ID = SRM_TARIFF_ID;
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

    public String getAREAM_AREA_ID() {
        return AREAM_AREA_ID;
    }

    public void setAREAM_AREA_ID(String AREAM_AREA_ID) {
        this.AREAM_AREA_ID = AREAM_AREA_ID;
    }

    public String getAREA() {
        return AREA;
    }

    public void setAREA(String AREA) {
        this.AREA = AREA;
    }

    public String getSRM_BILL_AMOUNT() {
        return SRM_BILL_AMOUNT;
    }

    public void setSRM_BILL_AMOUNT(String SRM_BILL_AMOUNT) {
        this.SRM_BILL_AMOUNT = SRM_BILL_AMOUNT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getDAYS() {
        return DAYS;
    }

    public void setDAYS(String DAYS) {
        this.DAYS = DAYS;
    }

    public String getCOM_YEAR_BILL() {
        return COM_YEAR_BILL;
    }

    public void setCOM_YEAR_BILL(String COM_YEAR_BILL) {
        this.COM_YEAR_BILL = COM_YEAR_BILL;
    }

    public String getPRIORITY() {
        return PRIORITY;
    }

    public void setPRIORITY(String PRIORITY) {
        this.PRIORITY = PRIORITY;
    }

    public String getCOM_SEQ() {
        return COM_SEQ;
    }

    public void setCOM_SEQ(String COM_SEQ) {
        this.COM_SEQ = COM_SEQ;
    }

    public String getSOURCETYPE() {
        return SOURCETYPE;
    }

    public void setSOURCETYPE(String SOURCETYPE) {
        this.SOURCETYPE = SOURCETYPE;
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

    public String getAGINGOFCOMPLAINT() {
        return AGINGOFCOMPLAINT;
    }

    public void setAGINGOFCOMPLAINT(String AGINGOFCOMPLAINT) {
        this.AGINGOFCOMPLAINT = AGINGOFCOMPLAINT;
    }

    public String getSLA() {
        return SLA;
    }

    public void setSLA(String SLA) {
        this.SLA = SLA;
    }

    public String getSRM_LATITUDE() {
        return SRM_LATITUDE;
    }

    public void setSRM_LATITUDE(String SRM_LATITUDE) {
        this.SRM_LATITUDE = SRM_LATITUDE;
    }

    public String getSRM_LONGITUDE() {
        return SRM_LONGITUDE;
    }

    public void setSRM_LONGITUDE(String SRM_LONGITUDE) {
        this.SRM_LONGITUDE = SRM_LONGITUDE;
    }

    @Override
    public String toString() {
        return "ReallocationResponseDataModel{" +
                "COM_NO_TYPE='" + COM_NO_TYPE + '\'' +
                ", BUM_BU_NAME='" + BUM_BU_NAME + '\'' +
                ", CIRCLE_NAME='" + CIRCLE_NAME + '\'' +
                ", SRM_MRC_ID='" + SRM_MRC_ID + '\'' +
                ", SRM_LOT_ID='" + SRM_LOT_ID + '\'' +
                ", COMNO='" + COMNO + '\'' +
                ", COM_COMPDATE='" + COM_COMPDATE + '\'' +
                ", NAME='" + NAME + '\'' +
                ", CONSUMER_NAME='" + CONSUMER_NAME + '\'' +
                ", ADDRESS_NO='" + ADDRESS_NO + '\'' +
                ", DTCNO='" + DTCNO + '\'' +
                ", POLE='" + POLE + '\'' +
                ", MOBILE='" + MOBILE + '\'' +
                ", CMTM_NAME='" + CMTM_NAME + '\'' +
                ", OCRM_NAME='" + OCRM_NAME + '\'' +
                ", COM_REMARKS='" + COM_REMARKS + '\'' +
                ", SRM_PHASE='" + SRM_PHASE + '\'' +
                ", COMPFOR='" + COMPFOR + '\'' +
                ", COM_STATUS='" + COM_STATUS + '\'' +
                ", COM_SERVICE_NO='" + COM_SERVICE_NO + '\'' +
                ", COM_CALLS='" + COM_CALLS + '\'' +
                ", COM_SOURCECD='" + COM_SOURCECD + '\'' +
                ", CSCM_ID='" + CSCM_ID + '\'' +
                ", CSCM_SECNAME='" + CSCM_SECNAME + '\'' +
                ", FWDTOSEC='" + FWDTOSEC + '\'' +
                ", FWDCOM='" + FWDCOM + '\'' +
                ", CATEGORY='" + CATEGORY + '\'' +
                ", PURPOSE='" + PURPOSE + '\'' +
                ", SRM_TARIFF_ID='" + SRM_TARIFF_ID + '\'' +
                ", TARIFF='" + TARIFF + '\'' +
                ", VIP='" + VIP + '\'' +
                ", AREAM_AREA_ID='" + AREAM_AREA_ID + '\'' +
                ", AREA='" + AREA + '\'' +
                ", SRM_BILL_AMOUNT='" + SRM_BILL_AMOUNT + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", DAYS='" + DAYS + '\'' +
                ", COM_YEAR_BILL='" + COM_YEAR_BILL + '\'' +
                ", PRIORITY='" + PRIORITY + '\'' +
                ", COM_SEQ='" + COM_SEQ + '\'' +
                ", SOURCETYPE='" + SOURCETYPE + '\'' +
                ", ASSIGNEDCODE='" + ASSIGNEDCODE + '\'' +
                ", ASSIGNEDNAME='" + ASSIGNEDNAME + '\'' +
                ", AGINGOFCOMPLAINT='" + AGINGOFCOMPLAINT + '\'' +
                ", SLA='" + SLA + '\'' +
                ", SRM_LATITUDE='" + SRM_LATITUDE + '\'' +
                ", SRM_LONGITUDE='" + SRM_LONGITUDE + '\'' +
                '}';
    }
}
