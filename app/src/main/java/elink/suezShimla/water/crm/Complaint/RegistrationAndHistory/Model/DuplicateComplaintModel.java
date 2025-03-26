package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

public class DuplicateComplaintModel {
    @SerializedName("COMNO")
    private String COMNO;

    @SerializedName("SUBTYPE")
    private int SUBTYPE;

    @SerializedName("ORIGIN")
    private String ORIGIN;

    @SerializedName("FRM_REASONNAME")
    private String FRM_REASONNAME;

    @SerializedName("COM_COMPDATE")
    private String COM_COMPDATE;

    @SerializedName("SLA")
    private String SLA;

    @SerializedName("TODAYREPEATCOUNT")
    private String TODAYREPEATCOUNT;

    @SerializedName("PROCCODE")
    private String PROCCODE;

    @SerializedName("COM_NO_TYPE")
    private String COM_NO_TYPE;

    @SerializedName("COM_NO_SEQUENCE")
    private String COM_NO_SEQUENCE;

    @SerializedName("COM_CALLS")
    private String COM_CALLS;

    @SerializedName("COM_FWDSEC1")
    private String COM_FWDSEC1;

    @SerializedName("COMPBY")
    private String COMPBY;

    public DuplicateComplaintModel() {
    }



    public DuplicateComplaintModel(String COMNO, int SUBTYPE, String ORIGIN, String FRM_REASONNAME, String COM_COMPDATE, String SLA, String TODAYREPEATCOUNT, String PROCCODE, String COM_NO_TYPE, String COM_NO_SEQUENCE, String COM_CALLS, String COM_FWDSEC1, String COMPBY) {
        this.COMNO = COMNO;
        this.SUBTYPE = SUBTYPE;
        this.ORIGIN = ORIGIN;
        this.FRM_REASONNAME = FRM_REASONNAME;
        this.COM_COMPDATE = COM_COMPDATE;
        this.SLA = SLA;
        this.TODAYREPEATCOUNT = TODAYREPEATCOUNT;
        this.PROCCODE = PROCCODE;
        this.COM_NO_TYPE = COM_NO_TYPE;
        this.COM_NO_SEQUENCE = COM_NO_SEQUENCE;
        this.COM_CALLS = COM_CALLS;
        this.COM_FWDSEC1 = COM_FWDSEC1;
        this.COMPBY = COMPBY;
    }

    public String getCOMNO() {
        return COMNO;
    }

    public void setCOMNO(String COMNO) {
        this.COMNO = COMNO;
    }

    public int getSUBTYPE() {
        return SUBTYPE;
    }

    public void setSUBTYPE(int SUBTYPE) {
        this.SUBTYPE = SUBTYPE;
    }

    public String getORIGIN() {
        return ORIGIN;
    }

    public void setORIGIN(String ORIGIN) {
        this.ORIGIN = ORIGIN;
    }

    public String getFRM_REASONNAME() {
        return FRM_REASONNAME;
    }

    public void setFRM_REASONNAME(String FRM_REASONNAME) {
        this.FRM_REASONNAME = FRM_REASONNAME;
    }


    public String getCOM_COMPDATE() {
        return COM_COMPDATE;
    }

    public void setCOM_COMPDATE(String COM_COMPDATE) {
        this.COM_COMPDATE = COM_COMPDATE;
    }

    public String getSLA() {
        return SLA;
    }

    public void setSLA(String SLA) {
        this.SLA = SLA;
    }

    public String getTODAYREPEATCOUNT() {
        return TODAYREPEATCOUNT;
    }

    public void setTODAYREPEATCOUNT(String TODAYREPEATCOUNT) {
        this.TODAYREPEATCOUNT = TODAYREPEATCOUNT;
    }

    public String getPROCCODE() {
        return PROCCODE;
    }

    public void setPROCCODE(String PROCCODE) {
        this.PROCCODE = PROCCODE;
    }

    public String getCOM_NO_TYPE() {
        return COM_NO_TYPE;
    }

    public void setCOM_NO_TYPE(String COM_NO_TYPE) {
        this.COM_NO_TYPE = COM_NO_TYPE;
    }

    public String getCOM_NO_SEQUENCE() {
        return COM_NO_SEQUENCE;
    }

    public void setCOM_NO_SEQUENCE(String COM_NO_SEQUENCE) {
        this.COM_NO_SEQUENCE = COM_NO_SEQUENCE;
    }

    public String getCOM_CALLS() {
        return COM_CALLS;
    }

    public void setCOM_CALLS(String COM_CALLS) {
        this.COM_CALLS = COM_CALLS;
    }

    public String getCOM_FWDSEC1() {
        return COM_FWDSEC1;
    }

    public void setCOM_FWDSEC1(String COM_FWDSEC1) {
        this.COM_FWDSEC1 = COM_FWDSEC1;
    }

    public String getCOMPBY() {
        return COMPBY;
    }

    public void setCOMPBY(String COMPBY) {
        this.COMPBY = COMPBY;
    }

    @Override
    public String toString() {
        return "DuplicateComplaintModel{" +
                "COMNO='" + COMNO + '\'' +
                ", SUBTYPE=" + SUBTYPE +
                ", ORIGIN='" + ORIGIN + '\'' +
                ", FRM_REASONNAME='" + FRM_REASONNAME + '\'' +
                ", COM_COMPDATE='" + COM_COMPDATE + '\'' +
                ", SLA='" + SLA + '\'' +
                ", TODAYREPEATCOUNT='" + TODAYREPEATCOUNT + '\'' +
                ", PROCCODE='" + PROCCODE + '\'' +
                ", COM_NO_TYPE='" + COM_NO_TYPE + '\'' +
                ", COM_NO_SEQUENCE='" + COM_NO_SEQUENCE + '\'' +
                ", COM_CALLS='" + COM_CALLS + '\'' +
                ", COM_FWDSEC1='" + COM_FWDSEC1 + '\'' +
                ", COMPBY='" + COMPBY + '\'' +
                '}';
    }
}
