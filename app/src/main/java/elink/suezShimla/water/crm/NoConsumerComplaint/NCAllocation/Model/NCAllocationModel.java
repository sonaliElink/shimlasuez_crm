package elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Model;

public class NCAllocationModel {

    private String COMNO;
    private String BUM_BU_NAME;
    private String CIRCLE_NAME;
    private String COM_COMPDATE;
    private String CONSUMER_NAME;
    private String ADDRESS_NO;
    private String MOBILE;
    private String CMTM_NAME;
    private String OCRM_NAME;
    private String COM_SERVICE_NO;
    private String TARIFF;
    private String STATUS;
    private String COM_YEAR_BILL;
    private String COM_LAT;
    private String CON_LONG;
    private String VIP;

    public NCAllocationModel() {
    }

    public NCAllocationModel(String COMNO, String BUM_BU_NAME, String CIRCLE_NAME, String COM_COMPDATE, String CONSUMER_NAME, String ADDRESS_NO, String MOBILE, String CMTM_NAME, String OCRM_NAME, String COM_SERVICE_NO, String TARIFF, String STATUS, String COM_YEAR_BILL, String COM_LAT, String CON_LONG, String VIP) {
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
        this.COM_LAT = COM_LAT;
        this.CON_LONG = CON_LONG;
        this.VIP = VIP;
    }

    public String getCOMNO() {
        return COMNO;
    }

    public void setCOMNO(String COMNO) {
        this.COMNO = COMNO;
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

    public String getCOM_COMPDATE() {
        return COM_COMPDATE;
    }

    public void setCOM_COMPDATE(String COM_COMPDATE) {
        this.COM_COMPDATE = COM_COMPDATE;
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

    public String getCOM_SERVICE_NO() {
        return COM_SERVICE_NO;
    }

    public void setCOM_SERVICE_NO(String COM_SERVICE_NO) {
        this.COM_SERVICE_NO = COM_SERVICE_NO;
    }

    public String getTARIFF() {
        return TARIFF;
    }

    public void setTARIFF(String TARIFF) {
        this.TARIFF = TARIFF;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCOM_YEAR_BILL() {
        return COM_YEAR_BILL;
    }

    public void setCOM_YEAR_BILL(String COM_YEAR_BILL) {
        this.COM_YEAR_BILL = COM_YEAR_BILL;
    }

    public String getCOM_LAT() {
        return COM_LAT;
    }

    public void setCOM_LAT(String COM_LAT) {
        this.COM_LAT = COM_LAT;
    }

    public String getCON_LONG() {
        return CON_LONG;
    }

    public void setCON_LONG(String CON_LONG) {
        this.CON_LONG = CON_LONG;

    }
    public String getVIP() {
        return VIP;
    }

    public void setVIP(String VIP) {
        this.VIP = VIP;

    }
    @Override
    public String toString() {
        return "NCAllocationModel{" +
                "COMNO='" + COMNO + '\'' +
                ", BUM_BU_NAME='" + BUM_BU_NAME + '\'' +
                ", CIRCLE_NAME='" + CIRCLE_NAME + '\'' +
                ", COM_COMPDATE='" + COM_COMPDATE + '\'' +
                ", CONSUMER_NAME='" + CONSUMER_NAME + '\'' +
                ", ADDRESS_NO='" + ADDRESS_NO + '\'' +
                ", MOBILE='" + MOBILE + '\'' +
                ", CMTM_NAME='" + CMTM_NAME + '\'' +
                ", OCRM_NAME='" + OCRM_NAME + '\'' +
                ", COM_SERVICE_NO='" + COM_SERVICE_NO + '\'' +
                ", TARIFF='" + TARIFF + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", COM_YEAR_BILL='" + COM_YEAR_BILL + '\'' +
                ", VIP='" + VIP+ '\'' +
                '}';
    }
}
