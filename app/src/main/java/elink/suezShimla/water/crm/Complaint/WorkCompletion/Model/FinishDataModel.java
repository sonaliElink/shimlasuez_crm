package elink.suezShimla.water.crm.Complaint.WorkCompletion.Model;

import com.google.gson.annotations.SerializedName;

public class FinishDataModel {
    @SerializedName("CSCM_ID")
    private String CSCM_ID;

    @SerializedName("CSCM_SECNAME")
    private String CSCM_SECNAME;

    @SerializedName("PASTREADING")
    private String PASTREADING;

    public FinishDataModel() {
    }

    public FinishDataModel(String CSCM_ID, String CSCM_SECNAME, String PASTREADING) {
        this.CSCM_ID = CSCM_ID;
        this.CSCM_SECNAME = CSCM_SECNAME;
        this.PASTREADING = PASTREADING;
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

    public String getPASTREADING() {
        return PASTREADING;
    }

    public void setPASTREADING(String PASTREADING) {
        this.PASTREADING = PASTREADING;
    }

    @Override
    public String toString() {
        return "FinishDataModel{" +
                "CSCM_ID='" + CSCM_ID + '\'' +
                ", CSCM_SECNAME='" + CSCM_SECNAME + '\'' +
                ", PASTREADING='" + PASTREADING + '\'' +
                '}';
    }
}
