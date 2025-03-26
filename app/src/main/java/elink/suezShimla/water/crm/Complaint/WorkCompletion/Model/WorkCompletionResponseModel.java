package elink.suezShimla.water.crm.Complaint.WorkCompletion.Model;

import com.google.gson.annotations.SerializedName;

public class WorkCompletionResponseModel {
    @SerializedName("COM_NO_TYPE")
    private String COM_NO_TYPE;

    @SerializedName("BUM_BU_NAME")
    private String BUM_BU_NAME;

    @SerializedName("SRM_PHASE")
    private String SRM_PHASE;

    @SerializedName("GROUP_NAME")
    private String GROUP_NAME;

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

    @SerializedName("COMPFOR")
    private String COMPFOR;

    @SerializedName("CMTM_NAME")
    private String CMTM_NAME;

    @SerializedName("OCRM_NAME")
    private String OCRM_NAME;

    @SerializedName("COM_STATUS")
    private String COM_STATUS;

    @SerializedName("COM_SERVICE_NO")
    private String COM_SERVICE_NO;

    @SerializedName("COM_INSPCD")
    private String COM_INSPCD;

    @SerializedName("COM_ALLOCATIONDATE")
    private String COM_ALLOCATIONDATE;

    @SerializedName("COM_CALLS")
    private String COM_CALLS;

    @SerializedName("COM_SOURCECD")
    private String COM_SOURCECD;

    @SerializedName("COM_REMARKS")
    private String COM_REMARKS;

    @SerializedName("COM_METER_REPLACE")
    private String COM_METER_REPLACE;

    @SerializedName("COM_DEFCD1")
    private String COM_DEFCD1;

    @SerializedName("COM_TYPE")
    private String COM_TYPE;

    @SerializedName("COM_COMPDATE1")
    private String COM_COMPDATE1;

    @SerializedName("CONSUMER_NAME")
    private String CONSUMER_NAME;

    @SerializedName("ADDRESS_NO")
    private String ADDRESS_NO;

    @SerializedName("DTCNO")
    private String DTCNO;

    @SerializedName("POLE")
    private String POLE;

    @SerializedName("CONTACTNO")
    private String CONTACTNO;

    @SerializedName("AREAM_AREA_ID")
    private String AREAM_AREA_ID;

    @SerializedName("AREA")
    private String AREA;

    @SerializedName("TARIFF")
    private String TARIFF;

    @SerializedName("SRM_BILL_AMOUNT")
    private String SRM_BILL_AMOUNT;

    @SerializedName("STATUS")
    private String STATUS;

    @SerializedName("COM_PRIMSEC")
    private String COM_PRIMSEC;

    @SerializedName("COM_PROCESSCODE")
    private String COM_PROCESSCODE;

    @SerializedName("COM_ACTIONCD")
    private String COM_ACTIONCD;

    @SerializedName("COM_NOTINSTALLED_REASON")
    private String COM_NOTINSTALLED_REASON;

    @SerializedName("ADDRESS1")
    private String ADDRESS1;

    @SerializedName("ADDRESS2")
    private String ADDRESS2;

    @SerializedName("ADDRESS3")
    private String ADDRESS3;

    @SerializedName("PIN")
    private String PIN;

    @SerializedName("COM_METER_TRANSID")
    private String COM_METER_TRANSID;

    @SerializedName("FIXER")
    private String FIXER;

    @SerializedName("CSCM_SECNAME")
    private String CSCM_SECNAME;

    @SerializedName("COM_YEAR_BILL")
    private String COM_YEAR_BILL;

    @SerializedName("PRIORITY")
    private String PRIORITY;

    @SerializedName("COM_SEQ")
    private String COM_SEQ;

    @SerializedName("COM_OBSCD")
    private String COM_OBSCD;

    @SerializedName("SOURCETYPE")
    private String SOURCETYPE;

    @SerializedName("SRM_LATITUDE")
    private String SRM_LATITUDE;

    @SerializedName("SRM_LONGITUDE")
    private String SRM_LONGITUDE;

    @SerializedName("VIP")
    private String VIP;

    @SerializedName("AGINGOFCOMPLAINT")
    private String AGINGOFCOMPLAINT;

    @SerializedName("SLA")
    private String SLA;

    @SerializedName("ORIGIN")
    private String ORIGIN;

    @SerializedName("READERREMARK")
    private String READERREMARK;

    @SerializedName("SRM_CATEGORY_ID")
    private String SRM_CATEGORY_ID;

    @SerializedName("SRM_CONNSIZE_ID")
    private String SRM_CONNSIZE_ID;


    public WorkCompletionResponseModel() {
    }

    public WorkCompletionResponseModel(String COM_NO_TYPE, String COMNO, String COM_SERVICE_NO, String COM_ALLOCATIONDATE, String COM_COMPDATE, String OCRM_NAME, String CONSUMER_NAME,
                                       String CONTACTNO, String ADDRESS_NO, String TARIFF, String STATUS, String PRIORITY, String BUM_BU_NAME,
                                       String GROUP_NAME, String COM_YEAR_BILL, String ADDRESS1, String ADDRESS2, String ADDRESS3, String PIN,
                                       String CMTM_NAME, String COM_METER_REPLACE, String COM_METER_TRANSID, String COM_LAT, String COM_LONG, String VIP, String COM_REMARKS,
                                       String COM_CALLS, String AGINGOFCOMPLAINT, String SLA, String COM_TYPE, String COM_SEQ, String COM_PROCESSCODE, String ORIGIN, String READERREMARK, String Name,String strConnCategory,String strConnSize) {
        this.COM_NO_TYPE = COM_NO_TYPE;
        this.COMNO = COMNO;
        this.COM_SERVICE_NO = COM_SERVICE_NO;
        this.COM_ALLOCATIONDATE = COM_ALLOCATIONDATE;
        this.COM_COMPDATE = COM_COMPDATE;
        this.OCRM_NAME = OCRM_NAME;
        this.CONSUMER_NAME = CONSUMER_NAME;
        this.CONTACTNO = CONTACTNO;
        this.ADDRESS_NO = ADDRESS_NO;
        this.TARIFF = TARIFF;
        this.STATUS = STATUS;
        this.PRIORITY = PRIORITY;
        this.BUM_BU_NAME = BUM_BU_NAME;
        this.GROUP_NAME = GROUP_NAME;
        this.COM_YEAR_BILL = COM_YEAR_BILL;
        this.ADDRESS1 = ADDRESS1;
        this.ADDRESS2 = ADDRESS2;
        this.ADDRESS3 = ADDRESS3;
        this.PIN = PIN;
        this.COM_METER_REPLACE = COM_METER_REPLACE;
        this.CMTM_NAME = CMTM_NAME;
        this.COM_METER_TRANSID = COM_METER_TRANSID;
        this.SRM_LATITUDE = COM_LAT;
        this.SRM_LONGITUDE = COM_LONG;
        this.VIP = VIP;
        this.COM_REMARKS = COM_REMARKS;
        this.COM_CALLS = COM_CALLS;
        this.AGINGOFCOMPLAINT = AGINGOFCOMPLAINT;
        this.SLA = SLA;
        this.COM_TYPE = COM_TYPE;
        this.COM_SEQ = COM_SEQ;
        this.COM_PROCESSCODE = COM_PROCESSCODE;
        this.ORIGIN = ORIGIN;
        this.READERREMARK = READERREMARK;
        this.NAME = Name;
        this.SRM_CATEGORY_ID = strConnCategory;
        this.SRM_CONNSIZE_ID = strConnSize;
    }

    public WorkCompletionResponseModel(String BUM_BU_NAME, String SRM_PHASE, String GROUP_NAME, String SRM_MRC_ID, String SRM_LOT_ID, String COMNO, String COM_COMPDATE, String NAME, String COMPFOR, String CMTM_NAME, String OCRM_NAME, String COM_STATUS, String COM_SERVICE_NO, String COM_INSPCD, String COM_ALLOCATIONDATE, String COM_CALLS, String COM_SOURCECD, String COM_REMARKS, String COM_METER_REPLACE, String COM_DEFCD1, String COM_TYPE, String COM_COMPDATE1, String CONSUMER_NAME, String ADDRESS_NO, String DTCNO, String POLE, String CONTACTNO, String AREAM_AREA_ID, String AREA, String TARIFF, String SRM_BILL_AMOUNT, String STATUS, String COM_PRIMSEC, String COM_PROCESSCODE, String COM_ACTIONCD, String COM_NOTINSTALLED_REASON, String ADDRESS1, String ADDRESS2, String ADDRESS3, String PIN, String COM_METER_TRANSID, String FIXER, String CSCM_SECNAME, String COM_YEAR_BILL, String PRIORITY, String COM_SEQ, String COM_OBSCD, String SOURCETYPE, String SRM_LATITUDE, String SRM_LONGITUDE, String VIP, String ORIGIN, String READERREMARK) {
        this.BUM_BU_NAME = BUM_BU_NAME;
        this.SRM_PHASE = SRM_PHASE;
        this.GROUP_NAME = GROUP_NAME;
        this.SRM_MRC_ID = SRM_MRC_ID;
        this.SRM_LOT_ID = SRM_LOT_ID;
        this.COMNO = COMNO;
        this.COM_COMPDATE = COM_COMPDATE;
        this.NAME = NAME;
        this.COMPFOR = COMPFOR;
        this.CMTM_NAME = CMTM_NAME;
        this.OCRM_NAME = OCRM_NAME;
        this.COM_STATUS = COM_STATUS;
        this.COM_SERVICE_NO = COM_SERVICE_NO;
        this.COM_INSPCD = COM_INSPCD;
        this.COM_ALLOCATIONDATE = COM_ALLOCATIONDATE;
        this.COM_CALLS = COM_CALLS;
        this.COM_SOURCECD = COM_SOURCECD;
        this.COM_REMARKS = COM_REMARKS;
        this.COM_METER_REPLACE = COM_METER_REPLACE;
        this.COM_DEFCD1 = COM_DEFCD1;
        this.COM_TYPE = COM_TYPE;
        this.COM_COMPDATE1 = COM_COMPDATE1;
        this.CONSUMER_NAME = CONSUMER_NAME;
        this.ADDRESS_NO = ADDRESS_NO;
        this.DTCNO = DTCNO;
        this.POLE = POLE;
        this.CONTACTNO = CONTACTNO;
        this.AREAM_AREA_ID = AREAM_AREA_ID;
        this.AREA = AREA;
        this.TARIFF = TARIFF;
        this.SRM_BILL_AMOUNT = SRM_BILL_AMOUNT;
        this.STATUS = STATUS;
        this.COM_PRIMSEC = COM_PRIMSEC;
        this.COM_PROCESSCODE = COM_PROCESSCODE;
        this.COM_ACTIONCD = COM_ACTIONCD;
        this.COM_NOTINSTALLED_REASON = COM_NOTINSTALLED_REASON;
        this.ADDRESS1 = ADDRESS1;
        this.ADDRESS2 = ADDRESS2;
        this.ADDRESS3 = ADDRESS3;
        this.PIN = PIN;
        this.COM_METER_TRANSID = COM_METER_TRANSID;
        this.FIXER = FIXER;
        this.CSCM_SECNAME = CSCM_SECNAME;
        this.COM_YEAR_BILL = COM_YEAR_BILL;
        this.PRIORITY = PRIORITY;
        this.COM_SEQ = COM_SEQ;
        this.COM_OBSCD = COM_OBSCD;
        this.SOURCETYPE = SOURCETYPE;
        this.SRM_LATITUDE = SRM_LATITUDE;
        this.SRM_LONGITUDE = SRM_LONGITUDE;
        this.VIP = VIP;
        this.ORIGIN = ORIGIN;
        this.READERREMARK = READERREMARK;
    }

    public void setSRM_PHASE(String SRM_PHASE) {
        this.SRM_PHASE = SRM_PHASE;
    }

    public String getSRM_CATEGORY_ID() {
        return SRM_CATEGORY_ID;
    }

    public void setSRM_CATEGORY_ID(String SRM_CATEGORY_ID) {
        this.SRM_CATEGORY_ID = SRM_CATEGORY_ID;
    }

    public String getSRM_CONNSIZE_ID() {
        return SRM_CONNSIZE_ID;
    }

    public void setSRM_CONNSIZE_ID(String SRM_CONNSIZE_ID) {
        this.SRM_CONNSIZE_ID = SRM_CONNSIZE_ID;
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

    public String getSRM_PHASE() {
        return SRM_PHASE;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public String getSRM_MRC_ID() {
        return SRM_MRC_ID;
    }

    public String getSRM_LOT_ID() {
        return SRM_LOT_ID;
    }

    public String getCOMNO() {
        return COMNO;
    }

    public String getCOM_COMPDATE() {
        return COM_COMPDATE;
    }

    public String getNAME() {
        return NAME;
    }

    public String getCOMPFOR() {
        return COMPFOR;
    }

    public String getCMTM_NAME() {
        return CMTM_NAME;
    }

    public String getOCRM_NAME() {
        return OCRM_NAME;
    }

    public String getCOM_STATUS() {
        return COM_STATUS;
    }

    public String getCOM_SERVICE_NO() {
        return COM_SERVICE_NO;
    }

    public String getCOM_INSPCD() {
        return COM_INSPCD;
    }

    public String getCOM_ALLOCATIONDATE() {
        return COM_ALLOCATIONDATE;
    }

    public String getCOM_CALLS() {
        return COM_CALLS;
    }

    public String getCOM_SOURCECD() {
        return COM_SOURCECD;
    }

    public String getCOM_REMARKS() {
        return COM_REMARKS;
    }

    public String getCOM_METER_REPLACE() {
        return COM_METER_REPLACE;
    }

    public String getCOM_DEFCD1() {
        return COM_DEFCD1;
    }

    public String getCOM_TYPE() {
        return COM_TYPE;
    }

    public String getCOM_COMPDATE1() {
        return COM_COMPDATE1;
    }

    public String getCONSUMER_NAME() {
        return CONSUMER_NAME;
    }

    public String getADDRESS_NO() {
        return ADDRESS_NO;
    }

    public String getDTCNO() {
        return DTCNO;
    }

    public String getPOLE() {
        return POLE;
    }

    public String getCONTACTNO() {
        return CONTACTNO;
    }

    public String getAREAM_AREA_ID() {
        return AREAM_AREA_ID;
    }

    public String getAREA() {
        return AREA;
    }

    public String getTARIFF() {
        return TARIFF;
    }

    public String getSRM_BILL_AMOUNT() {
        return SRM_BILL_AMOUNT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getCOM_PRIMSEC() {
        return COM_PRIMSEC;
    }

    public String getCOM_PROCESSCODE() {
        return COM_PROCESSCODE;
    }

    public String getCOM_ACTIONCD() {
        return COM_ACTIONCD;
    }

    public String getCOM_NOTINSTALLED_REASON() {
        return COM_NOTINSTALLED_REASON;
    }

    public String getADDRESS1() {
        return ADDRESS1;
    }

    public String getADDRESS2() {
        return ADDRESS2;
    }

    public String getADDRESS3() {
        return ADDRESS3;
    }

    public String getPIN() {
        return PIN;
    }

    public String getCOM_METER_TRANSID() {
        return COM_METER_TRANSID;
    }

    public String getFIXER() {
        return FIXER;
    }

    public String getCSCM_SECNAME() {
        return CSCM_SECNAME;
    }

    public String getCOM_YEAR_BILL() {
        return COM_YEAR_BILL;
    }

    public String getPRIORITY() {
        return PRIORITY;
    }

    public String getCOM_SEQ() {
        return COM_SEQ;
    }

    public String getCOM_OBSCD() {
        return COM_OBSCD;
    }

    public String getSOURCETYPE() {
        return SOURCETYPE;
    }

    public String getSRM_LATITUDE() {
        return SRM_LATITUDE;
    }

    public String getSRM_LONGITUDE() {
        return SRM_LONGITUDE;
    }

    public String getVIP() {
        return VIP;
    }

    public void setVIP(String VIP) {
        this.VIP = VIP;
    }

    public void setBUM_BU_NAME(String BUM_BU_NAME) {
        this.BUM_BU_NAME = BUM_BU_NAME;
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

    public String getORIGIN() {
        return ORIGIN;
    }

    public void setORIGIN(String ORIGIN) {
        this.ORIGIN = ORIGIN;
    }

    public String getREADERREMARK() {
        return READERREMARK;
    }

    public void setREADERREMARK(String READERREMARK) {
        this.READERREMARK = READERREMARK;
    }

    @Override
    public String toString() {
        return "WorkCompletionResponseModel{" +
                "COM_NO_TYPE='" + COM_NO_TYPE + '\'' +
                ", BUM_BU_NAME='" + BUM_BU_NAME + '\'' +
                ", SRM_PHASE='" + SRM_PHASE + '\'' +
                ", GROUP_NAME='" + GROUP_NAME + '\'' +
                ", SRM_MRC_ID='" + SRM_MRC_ID + '\'' +
                ", SRM_LOT_ID='" + SRM_LOT_ID + '\'' +
                ", COMNO='" + COMNO + '\'' +
                ", COM_COMPDATE='" + COM_COMPDATE + '\'' +
                ", NAME='" + NAME + '\'' +
                ", COMPFOR='" + COMPFOR + '\'' +
                ", CMTM_NAME='" + CMTM_NAME + '\'' +
                ", OCRM_NAME='" + OCRM_NAME + '\'' +
                ", COM_STATUS='" + COM_STATUS + '\'' +
                ", COM_SERVICE_NO='" + COM_SERVICE_NO + '\'' +
                ", COM_INSPCD='" + COM_INSPCD + '\'' +
                ", COM_ALLOCATIONDATE='" + COM_ALLOCATIONDATE + '\'' +
                ", COM_CALLS='" + COM_CALLS + '\'' +
                ", COM_SOURCECD='" + COM_SOURCECD + '\'' +
                ", COM_REMARKS='" + COM_REMARKS + '\'' +
                ", COM_METER_REPLACE='" + COM_METER_REPLACE + '\'' +
                ", COM_DEFCD1='" + COM_DEFCD1 + '\'' +
                ", COM_TYPE='" + COM_TYPE + '\'' +
                ", COM_COMPDATE1='" + COM_COMPDATE1 + '\'' +
                ", CONSUMER_NAME='" + CONSUMER_NAME + '\'' +
                ", ADDRESS_NO='" + ADDRESS_NO + '\'' +
                ", DTCNO='" + DTCNO + '\'' +
                ", POLE='" + POLE + '\'' +
                ", CONTACTNO='" + CONTACTNO + '\'' +
                ", AREAM_AREA_ID='" + AREAM_AREA_ID + '\'' +
                ", AREA='" + AREA + '\'' +
                ", TARIFF='" + TARIFF + '\'' +
                ", SRM_BILL_AMOUNT='" + SRM_BILL_AMOUNT + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", COM_PRIMSEC='" + COM_PRIMSEC + '\'' +
                ", COM_PROCESSCODE='" + COM_PROCESSCODE + '\'' +
                ", COM_ACTIONCD='" + COM_ACTIONCD + '\'' +
                ", COM_NOTINSTALLED_REASON='" + COM_NOTINSTALLED_REASON + '\'' +
                ", ADDRESS1='" + ADDRESS1 + '\'' +
                ", ADDRESS2='" + ADDRESS2 + '\'' +
                ", ADDRESS3='" + ADDRESS3 + '\'' +
                ", PIN='" + PIN + '\'' +
                ", COM_METER_TRANSID='" + COM_METER_TRANSID + '\'' +
                ", FIXER='" + FIXER + '\'' +
                ", CSCM_SECNAME='" + CSCM_SECNAME + '\'' +
                ", COM_YEAR_BILL='" + COM_YEAR_BILL + '\'' +
                ", PRIORITY='" + PRIORITY + '\'' +
                ", COM_SEQ='" + COM_SEQ + '\'' +
                ", COM_OBSCD='" + COM_OBSCD + '\'' +
                ", SOURCETYPE='" + SOURCETYPE + '\'' +
                ", SRM_LATITUDE='" + SRM_LATITUDE + '\'' +
                ", SRM_LONGITUDE='" + SRM_LONGITUDE + '\'' +
                ", VIP='" + VIP + '\'' +
                ", ORIGIN='" + ORIGIN + '\'' +
                '}';
    }
}
