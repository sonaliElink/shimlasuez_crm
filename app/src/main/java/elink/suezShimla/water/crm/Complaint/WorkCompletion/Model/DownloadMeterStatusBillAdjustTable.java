package elink.suezShimla.water.crm.Complaint.WorkCompletion.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class DownloadMeterStatusBillAdjustTable extends RealmObject {

    @SerializedName("MSM_METERSTATUS_ID")
    private String MSM_METERSTATUS_ID;

    @SerializedName("MSM_METERSTATUS_NAME")
    private String MSM_METERSTATUS_NAME;

    @SerializedName("BILLEDID")
    private String BILLEDID;

    @SerializedName("MSM_AVG_MONTHS")
    private String MSM_AVG_MONTHS;

    @SerializedName("MSM_AVG_METERSTATUS")
    private String MSM_AVG_METERSTATUS;

    @SerializedName("MSM_READING_MANDATORY")
    private String MSM_READING_MANDATORY;

      @SerializedName("MSM_PHOTO_REQ")
    private String MSM_PHOTO_REQ;

    @SerializedName("HIGH")
    private String HIGH;

    @SerializedName("LOW")
    private String LOW;


    public DownloadMeterStatusBillAdjustTable() {
    }

    public DownloadMeterStatusBillAdjustTable(String MSM_METERSTATUS_ID, String MSM_METERSTATUS_NAME, String BILLEDID, String MSM_AVG_MONTHS, String MSM_AVG_METERSTATUS, String MSM_READING_MANDATORY, String MSM_PHOTO_REQ, String HIGH, String LOW) {

        this.MSM_METERSTATUS_ID = MSM_METERSTATUS_ID;
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
        this.BILLEDID = BILLEDID;
        this.MSM_AVG_MONTHS = MSM_AVG_MONTHS;
        this.MSM_AVG_METERSTATUS = MSM_AVG_METERSTATUS;
        this.MSM_READING_MANDATORY = MSM_READING_MANDATORY;
        this.MSM_PHOTO_REQ = MSM_PHOTO_REQ;
        this.HIGH = HIGH;
        this.LOW = LOW;
    }

    public String getMSM_METERSTATUS_ID() {
        return MSM_METERSTATUS_ID;
    }

    public void setMSM_METERSTATUS_ID(String MSM_METERSTATUS_ID) {
        this.MSM_METERSTATUS_ID = MSM_METERSTATUS_ID;
    }

    public String getMSM_METERSTATUS_NAME() {
        return MSM_METERSTATUS_NAME;
    }

    public void setMSM_METERSTATUS_NAME(String MSM_METERSTATUS_NAME) {
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
    }

    public String getBILLEDID() {
        return BILLEDID;
    }

    public void setBILLEDID(String BILLEDID) {
        this.BILLEDID = BILLEDID;
    }

    public String getMSM_AVG_MONTHS() {
        return MSM_AVG_MONTHS;
    }

    public void setMSM_AVG_MONTHS(String MSM_AVG_MONTHS) {
        this.MSM_AVG_MONTHS = MSM_AVG_MONTHS;
    }

    public String getMSM_AVG_METERSTATUS() {
        return MSM_AVG_METERSTATUS;
    }

    public void setMSM_AVG_METERSTATUS(String MSM_AVG_METERSTATUS) {
        this.MSM_AVG_METERSTATUS = MSM_AVG_METERSTATUS;
    }

    public String getMSM_READING_MANDATORY() {
        return MSM_READING_MANDATORY;
    }

    public void setMSM_READING_MANDATORY(String MSM_READING_MANDATORY) {
        this.MSM_READING_MANDATORY = MSM_READING_MANDATORY;
    }

    public String getMSM_PHOTO_REQ() {
        return MSM_PHOTO_REQ;
    }

    public void setMSM_PHOTO_REQ(String MSM_PHOTO_REQ) {
        this.MSM_PHOTO_REQ = MSM_PHOTO_REQ;
    }

    public String getHIGH() {
        return HIGH;
    }

    public void setHIGH(String HIGH) {
        this.HIGH = HIGH;
    }

    public String getLOW() {
        return LOW;
    }

    public void setLOW(String LOW) {
        this.LOW = LOW;
    }

    @Override
    public String toString() {
        return "DownloadMeterStatusBillAdjustTable{" +
                "MSM_METERSTATUS_ID='" + MSM_METERSTATUS_ID + '\'' +
                ", MSM_METERSTATUS_NAME='" + MSM_METERSTATUS_NAME + '\'' +
                ", BILLEDID='" + BILLEDID + '\'' +
                ", MSM_AVG_MONTHS='" + MSM_AVG_MONTHS + '\'' +
                ", MSM_AVG_METERSTATUS='" + MSM_AVG_METERSTATUS + '\'' +
                ", MSM_READING_MANDATORY='" + MSM_READING_MANDATORY + '\'' +
                ", MSM_PHOTO_REQ='" + MSM_PHOTO_REQ + '\'' +
                ", HIGH='" + HIGH + '\'' +
                ", LOW='" + LOW + '\'' +
                '}';
    }
}
