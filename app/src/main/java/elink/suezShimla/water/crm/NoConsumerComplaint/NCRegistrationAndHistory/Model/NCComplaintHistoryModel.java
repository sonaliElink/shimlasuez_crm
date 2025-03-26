package elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

public class NCComplaintHistoryModel {
    @SerializedName("COM_NO_SEQUENCE")
    private String COM_NO_SEQUENCE;

    @SerializedName("COMNO")
    private String COMNO;

    @SerializedName("COM_COMPDATE")
    private String COM_COMPDATE;

    @SerializedName("NAME")
    private String NAME;

    @SerializedName("CMTM_NAME")
    private String CMTM_NAME;

    @SerializedName("OCRM_NAME")
    private String OCRM_NAME;

    @SerializedName("COM_INSPCD")
    private String COM_INSPCD;

    @SerializedName("COM_ALLOCATIONDATE")
    private String COM_ALLOCATIONDATE;

    @SerializedName("COM_WORKCOMPLETIONDATE")
    private String COM_WORKCOMPLETIONDATE;

    @SerializedName("COM_CALLS")
    private String COM_CALLS;

    @SerializedName("COM_STATUS")
    private String COM_STATUS;

    @SerializedName("CSCM_SECNAME")
    private String CSCM_SECNAME;

    @SerializedName("COM_METER_REPLACE")
    private String COM_METER_REPLACE;

    public NCComplaintHistoryModel() {
    }

    public NCComplaintHistoryModel(String COM_NO_SEQUENCE, String COMNO, String COM_COMPDATE, String NAME, String CMTM_NAME, String OCRM_NAME, String COM_INSPCD, String COM_ALLOCATIONDATE, String COM_WORKCOMPLETIONDATE, String COM_CALLS, String COM_STATUS, String CSCM_SECNAME, String COM_METER_REPLACE) {
        this.COM_NO_SEQUENCE = COM_NO_SEQUENCE;
        this.COMNO = COMNO;
        this.COM_COMPDATE = COM_COMPDATE;
        this.NAME = NAME;
        this.CMTM_NAME = CMTM_NAME;
        this.OCRM_NAME = OCRM_NAME;
        this.COM_INSPCD = COM_INSPCD;
        this.COM_ALLOCATIONDATE = COM_ALLOCATIONDATE;
        this.COM_WORKCOMPLETIONDATE = COM_WORKCOMPLETIONDATE;
        this.COM_CALLS = COM_CALLS;
        this.COM_STATUS = COM_STATUS;
        this.CSCM_SECNAME = CSCM_SECNAME;
        this.COM_METER_REPLACE = COM_METER_REPLACE;
    }

    public String getCOM_NO_SEQUENCE() {
        return COM_NO_SEQUENCE;
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

    public String getCMTM_NAME() {
        return CMTM_NAME;
    }

    public String getOCRM_NAME() {
        return OCRM_NAME;
    }

    public String getCOM_INSPCD() {
        return COM_INSPCD;
    }

    public String getCOM_ALLOCATIONDATE() {
        return COM_ALLOCATIONDATE;
    }

    public String getCOM_WORKCOMPLETIONDATE() {
        return COM_WORKCOMPLETIONDATE;
    }

    public String getCOM_CALLS() {
        return COM_CALLS;
    }

    public String getCOM_STATUS() {
        return COM_STATUS;
    }

    public String getCSCM_SECNAME() {
        return CSCM_SECNAME;
    }

    public String getCOM_METER_REPLACE() {
        return COM_METER_REPLACE;
    }

    @Override
    public String toString() {
        return "NCComplaintHistoryModel{" +
                "COM_NO_SEQUENCE='" + COM_NO_SEQUENCE + '\'' +
                ", COMNO='" + COMNO + '\'' +
                ", COM_COMPDATE='" + COM_COMPDATE + '\'' +
                ", NAME='" + NAME + '\'' +
                ", CMTM_NAME='" + CMTM_NAME + '\'' +
                ", OCRM_NAME='" + OCRM_NAME + '\'' +
                ", COM_INSPCD='" + COM_INSPCD + '\'' +
                ", COM_ALLOCATIONDATE='" + COM_ALLOCATIONDATE + '\'' +
                ", COM_WORKCOMPLETIONDATE='" + COM_WORKCOMPLETIONDATE + '\'' +
                ", COM_CALLS='" + COM_CALLS + '\'' +
                ", COM_STATUS='" + COM_STATUS + '\'' +
                ", CSCM_SECNAME='" + CSCM_SECNAME + '\'' +
                ", COM_METER_REPLACE='" + COM_METER_REPLACE + '\'' +
                '}';
    }
}
