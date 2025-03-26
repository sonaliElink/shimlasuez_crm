package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

public class TractingModel {

    @SerializedName("TYPE")
    private String TYPE; // complaint type

    @SerializedName("FCOMNO")
    private String FCOMNO;

    @SerializedName("CMM_SERVICE_NO")
    private String CMM_SERVICE_NO;

    @SerializedName("CMM_COMPBY")
    private String CMM_COMPBY; // complaint type

    @SerializedName("OCRM_NAME")
    private String OCRM_NAME;

    @SerializedName("CMM_PROCDT")
    private String CMM_PROCDT;

    @SerializedName("CSCM_SECNAME")
    private String CSCM_SECNAME;

    @SerializedName("CMM_REMARKS")
    private String CMM_REMARKS; // complaint type

    @SerializedName("CMM_STATUS")
    private String CMM_STATUS;

    @SerializedName("EMP")
    private String EMP;

     @SerializedName("CMM_PROCCODE")
    private String CMM_PROCCODE;

     @SerializedName("REPEATCALL")
    private String REPEATCALL;

     @SerializedName("REASON")
    private String REASON;

     @SerializedName("CMM_COMNO_SEQUENCE")
    private String CMM_COMNO_SEQUENCE;

     @SerializedName("COM_NO_TYPE")
    private String COM_NO_TYPE;

     @SerializedName("SUBTYPEID")
    private String SUBTYPEID;

     @SerializedName("COMPBY")
    private String COMPBY;

     @SerializedName("CSM_SOURCEDESC")
    private String CSM_SOURCEDESC;

     @SerializedName("AGING")
    private String AGING;

     @SerializedName("TODAYREPEATCOUNT")
    private String TODAYREPEATCOUNT;


    public TractingModel() {
    }

    public TractingModel(String TYPE, String FCOMNO, String CMM_SERVICE_NO, String CMM_COMPBY, String OCRM_NAME, String CMM_PROCDT, String CSCM_SECNAME, String CMM_REMARKS, String CMM_STATUS, String EMP, String CMM_PROCCODE, String REPEATCALL, String REASON, String CMM_COMNO_SEQUENCE, String COM_NO_TYPE, String SUBTYPEID, String COMPBY, String CSM_SOURCEDESC, String AGING, String TODAYREPEATCOUNT) {
        this.TYPE = TYPE;
        this.FCOMNO = FCOMNO;
        this.CMM_SERVICE_NO = CMM_SERVICE_NO;
        this.CMM_COMPBY = CMM_COMPBY;
        this.OCRM_NAME = OCRM_NAME;
        this.CMM_PROCDT = CMM_PROCDT;
        this.CSCM_SECNAME = CSCM_SECNAME;
        this.CMM_REMARKS = CMM_REMARKS;
        this.CMM_STATUS = CMM_STATUS;
        this.EMP = EMP;
        this.CMM_PROCCODE = CMM_PROCCODE;
        this.REPEATCALL = REPEATCALL;
        this.REASON = REASON;
        this.CMM_COMNO_SEQUENCE = CMM_COMNO_SEQUENCE;
        this.COM_NO_TYPE = COM_NO_TYPE;
        this.SUBTYPEID = SUBTYPEID;
        this.COMPBY = COMPBY;
        this.CSM_SOURCEDESC = CSM_SOURCEDESC;
        this.AGING = AGING;
        this.TODAYREPEATCOUNT = TODAYREPEATCOUNT;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getFCOMNO() {
        return FCOMNO;
    }

    public void setFCOMNO(String FCOMNO) {
        this.FCOMNO = FCOMNO;
    }

    public String getCMM_SERVICE_NO() {
        return CMM_SERVICE_NO;
    }

    public void setCMM_SERVICE_NO(String CMM_SERVICE_NO) {
        this.CMM_SERVICE_NO = CMM_SERVICE_NO;
    }

    public String getCMM_COMPBY() {
        return CMM_COMPBY;
    }

    public void setCMM_COMPBY(String CMM_COMPBY) {
        this.CMM_COMPBY = CMM_COMPBY;
    }

    public String getOCRM_NAME() {
        return OCRM_NAME;
    }

    public void setOCRM_NAME(String OCRM_NAME) {
        this.OCRM_NAME = OCRM_NAME;
    }

    public String getCMM_PROCDT() {
        return CMM_PROCDT;
    }

    public void setCMM_PROCDT(String CMM_PROCDT) {
        this.CMM_PROCDT = CMM_PROCDT;
    }

    public String getCSCM_SECNAME() {
        return CSCM_SECNAME;
    }

    public void setCSCM_SECNAME(String CSCM_SECNAME) {
        this.CSCM_SECNAME = CSCM_SECNAME;
    }

    public String getCMM_REMARKS() {
        return CMM_REMARKS;
    }

    public void setCMM_REMARKS(String CMM_REMARKS) {
        this.CMM_REMARKS = CMM_REMARKS;
    }

    public String getCMM_STATUS() {
        return CMM_STATUS;
    }

    public void setCMM_STATUS(String CMM_STATUS) {
        this.CMM_STATUS = CMM_STATUS;
    }

    public String getEMP() {
        return EMP;
    }

    public void setEMP(String EMP) {
        this.EMP = EMP;
    }

    public String getCMM_PROCCODE() {
        return CMM_PROCCODE;
    }

    public void setCMM_PROCCODE(String CMM_PROCCODE) {
        this.CMM_PROCCODE = CMM_PROCCODE;
    }

    public String getREPEATCALL() {
        return REPEATCALL;
    }

    public void setREPEATCALL(String REPEATCALL) {
        this.REPEATCALL = REPEATCALL;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }

    public String getCMM_COMNO_SEQUENCE() {
        return CMM_COMNO_SEQUENCE;
    }

    public void setCMM_COMNO_SEQUENCE(String CMM_COMNO_SEQUENCE) {
        this.CMM_COMNO_SEQUENCE = CMM_COMNO_SEQUENCE;
    }

    public String getCOM_NO_TYPE() {
        return COM_NO_TYPE;
    }

    public void setCOM_NO_TYPE(String COM_NO_TYPE) {
        this.COM_NO_TYPE = COM_NO_TYPE;
    }

    public String getSUBTYPEID() {
        return SUBTYPEID;
    }

    public void setSUBTYPEID(String SUBTYPEID) {
        this.SUBTYPEID = SUBTYPEID;
    }

    public String getCOMPBY() {
        return COMPBY;
    }

    public void setCOMPBY(String COMPBY) {
        this.COMPBY = COMPBY;
    }

    public String getCSM_SOURCEDESC() {
        return CSM_SOURCEDESC;
    }

    public void setCSM_SOURCEDESC(String CSM_SOURCEDESC) {
        this.CSM_SOURCEDESC = CSM_SOURCEDESC;
    }

    public String getAGING() {
        return AGING;
    }

    public void setAGING(String AGING) {
        this.AGING = AGING;
    }

    public String getTODAYREPEATCOUNT() {
        return TODAYREPEATCOUNT;
    }

    public void setTODAYREPEATCOUNT(String TODAYREPEATCOUNT) {
        this.TODAYREPEATCOUNT = TODAYREPEATCOUNT;
    }

    @Override
    public String toString() {
        return "TractingModel{" +
                "TYPE='" + TYPE + '\'' +
                ", FCOMNO='" + FCOMNO + '\'' +
                ", CMM_SERVICE_NO='" + CMM_SERVICE_NO + '\'' +
                ", CMM_COMPBY='" + CMM_COMPBY + '\'' +
                ", OCRM_NAME='" + OCRM_NAME + '\'' +
                ", CMM_PROCDT='" + CMM_PROCDT + '\'' +
                ", CSCM_SECNAME='" + CSCM_SECNAME + '\'' +
                ", CMM_REMARKS='" + CMM_REMARKS + '\'' +
                ", CMM_STATUS='" + CMM_STATUS + '\'' +
                ", EMP='" + EMP + '\'' +
                ", CMM_PROCCODE='" + CMM_PROCCODE + '\'' +
                ", REPEATCALL='" + REPEATCALL + '\'' +
                ", REASON='" + REASON + '\'' +
                ", CMM_COMNO_SEQUENCE='" + CMM_COMNO_SEQUENCE + '\'' +
                ", COM_NO_TYPE='" + COM_NO_TYPE + '\'' +
                ", SUBTYPEID='" + SUBTYPEID + '\'' +
                ", COMPBY='" + COMPBY + '\'' +
                ", CSM_SOURCEDESC='" + CSM_SOURCEDESC + '\'' +
                ", AGING='" + AGING + '\'' +
                ", TODAYREPEATCOUNT='" + TODAYREPEATCOUNT + '\'' +
                '}';
    }
}
