package elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.Model;

import com.google.gson.annotations.SerializedName;

public class TodayWorkCompletionResponseModel {
    @SerializedName("SUBZONE")
    private String SUBZONE;

    @SerializedName("SRM_SERVICE_NO")
    private String SRM_SERVICE_NO;

    @SerializedName("CONSUMERNAME")
    private String CONSUMERNAME;

    @SerializedName("ADDRESS")
    private String ADDRESS;

    @SerializedName("PHONENO")
    private String PHONENO;

    @SerializedName("TARIFF")
    private String TARIFF;

    @SerializedName("COMPTYPE")
    private String COMPTYPE;

    @SerializedName("COMPSUBTYPE")
    private String COMPSUBTYPE;

    @SerializedName("COM_WORKCOMPLETIONDATE")
    private String COM_WORKCOMPLETIONDATE;

    @SerializedName("ATTENDEDBY")
    private String ATTENDEDBY;

    @SerializedName("COM_REMARKS")
    private String COM_REMARKS;

    @SerializedName("COMNO")
    private String COMNO;

    @SerializedName("COM_ALLOCATIONDATE")
    private String COM_ALLOCATIONDATE;

    @SerializedName("COM_COMPDATE")
    private String COM_COMPDATE;

    @SerializedName("COM_STATUS")
    private String COM_STATUS;

    @SerializedName("COM_EMP_ALLOCATE")
    private String COM_EMP_ALLOCATE;

    @SerializedName("VIP")
    private String VIP;

      @SerializedName("ORIGIN")
    private String ORIGIN;

    public TodayWorkCompletionResponseModel(){}

    public TodayWorkCompletionResponseModel(String SUBZONE, String SRM_SERVICE_NO, String CONSUMERNAME, String ADDRESS, String PHONENO, String TARIFF, String COMPTYPE, String COMPSUBTYPE, String COM_WORKCOMPLETIONDATE, String ATTENDEDBY, String COM_REMARKS, String COMNO, String COM_ALLOCATIONDATE, String COM_COMPDATE, String COM_STATUS, String COM_EMP_ALLOCATE,String VIP,String ORIGIN) {
        this.SUBZONE = SUBZONE;
        this.SRM_SERVICE_NO = SRM_SERVICE_NO;
        this.CONSUMERNAME = CONSUMERNAME;
        this.ADDRESS = ADDRESS;
        this.PHONENO = PHONENO;
        this.TARIFF = TARIFF;
        this.COMPTYPE = COMPTYPE;
        this.COMPSUBTYPE = COMPSUBTYPE;
        this.COM_WORKCOMPLETIONDATE = COM_WORKCOMPLETIONDATE;
        this.ATTENDEDBY = ATTENDEDBY;
        this.COM_REMARKS = COM_REMARKS;
        this.COMNO = COMNO;
        this.COM_ALLOCATIONDATE = COM_ALLOCATIONDATE;
        this.COM_COMPDATE = COM_COMPDATE;
        this.COM_STATUS = COM_STATUS;
        this.COM_EMP_ALLOCATE = COM_EMP_ALLOCATE;
        this.VIP=VIP;
        this.ORIGIN=ORIGIN;
    }

    public String getSUBZONE() {
        return SUBZONE;
    }

    public void setSUBZONE(String SUBZONE) {
        this.SUBZONE = SUBZONE;
    }

    public String getSRM_SERVICE_NO() {
        return SRM_SERVICE_NO;
    }

    public void setSRM_SERVICE_NO(String SRM_SERVICE_NO) {
        this.SRM_SERVICE_NO = SRM_SERVICE_NO;
    }

    public String getCONSUMERNAME() {
        return CONSUMERNAME;
    }

    public void setCONSUMERNAME(String CONSUMERNAME) {
        this.CONSUMERNAME = CONSUMERNAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getPHONENO() {
        return PHONENO;
    }

    public void setPHONENO(String PHONENO) {
        this.PHONENO = PHONENO;
    }

    public String getTARIFF() {
        return TARIFF;
    }

    public void setTARIFF(String TARIFF) {
        this.TARIFF = TARIFF;
    }

    public String getCOMPTYPE() {
        return COMPTYPE;
    }

    public void setCOMPTYPE(String COMPTYPE) {
        this.COMPTYPE = COMPTYPE;
    }

    public String getCOMPSUBTYPE() {
        return COMPSUBTYPE;
    }

    public void setCOMPSUBTYPE(String COMPSUBTYPE) {
        this.COMPSUBTYPE = COMPSUBTYPE;
    }

    public String getCOM_WORKCOMPLETIONDATE() {
        return COM_WORKCOMPLETIONDATE;
    }

    public void setCOM_WORKCOMPLETIONDATE(String COM_WORKCOMPLETIONDATE) {
        this.COM_WORKCOMPLETIONDATE = COM_WORKCOMPLETIONDATE;
    }

    public String getATTENDEDBY() {
        return ATTENDEDBY;
    }

    public void setATTENDEDBY(String ATTENDEDBY) {
        this.ATTENDEDBY = ATTENDEDBY;
    }

    public String getCOM_REMARKS() {
        return COM_REMARKS;
    }

    public void setCOM_REMARKS(String COM_REMARKS) {
        this.COM_REMARKS = COM_REMARKS;
    }

    public String getCOMNO() {
        return COMNO;
    }

    public void setCOMNO(String COMNO) {
        this.COMNO = COMNO;
    }

    public String getCOM_ALLOCATIONDATE() {
        return COM_ALLOCATIONDATE;
    }

    public void setCOM_ALLOCATIONDATE(String COM_ALLOCATIONDATE) {
        this.COM_ALLOCATIONDATE = COM_ALLOCATIONDATE;
    }

    public String getCOM_COMPDATE() {
        return COM_COMPDATE;
    }

    public void setCOM_COMPDATE(String COM_COMPDATE) {
        this.COM_COMPDATE = COM_COMPDATE;
    }

    public String getCOM_STATUS() {
        return COM_STATUS;
    }

    public void setCOM_STATUS(String COM_STATUS) {
        this.COM_STATUS = COM_STATUS;
    }

    public String getCOM_EMP_ALLOCATE() {
        return COM_EMP_ALLOCATE;
    }

    public void setCOM_EMP_ALLOCATE(String COM_EMP_ALLOCATE) {
        this.COM_EMP_ALLOCATE = COM_EMP_ALLOCATE;
    }
    public String getVIP() {
        return VIP;
    }

    public void setVIP(String VIP) {
        this.VIP = VIP;

    }

    public String getORIGIN() {
        return ORIGIN;
    }

    public void setORIGIN(String ORIGIN) {
        this.ORIGIN = ORIGIN;
    }

    @Override
    public String toString() {
        return "TodayWorkCompletionResponseModel{" +
                "SUBZONE='" + SUBZONE + '\'' +
                ", SRM_SERVICE_NO='" + SRM_SERVICE_NO + '\'' +
                ", CONSUMERNAME='" + CONSUMERNAME + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", PHONENO='" + PHONENO + '\'' +
                ", TARIFF='" + TARIFF + '\'' +
                ", COMPTYPE='" + COMPTYPE + '\'' +
                ", COMPSUBTYPE='" + COMPSUBTYPE + '\'' +
                ", COM_WORKCOMPLETIONDATE='" + COM_WORKCOMPLETIONDATE + '\'' +
                ", ATTENDEDBY='" + ATTENDEDBY + '\'' +
                ", COM_REMARKS='" + COM_REMARKS + '\'' +
                ", COMNO='" + COMNO + '\'' +
                ", COM_ALLOCATIONDATE='" + COM_ALLOCATIONDATE + '\'' +
                ", COM_COMPDATE='" + COM_COMPDATE + '\'' +
                ", COM_STATUS='" + COM_STATUS + '\'' +
                ", COM_EMP_ALLOCATE='" + COM_EMP_ALLOCATE + '\'' +
                ", VIP='" + VIP + '\'' +
                ", ORIGIN='" + ORIGIN + '\'' +
                '}';
    }
}
