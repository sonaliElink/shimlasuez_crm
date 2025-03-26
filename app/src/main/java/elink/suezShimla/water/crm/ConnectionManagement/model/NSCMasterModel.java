package elink.suezShimla.water.crm.ConnectionManagement.model;

import com.google.gson.annotations.SerializedName;

public class NSCMasterModel {
    @SerializedName("PENDING_ALL")
    private String PENDING_ALL;

    @SerializedName("PENDING_TODAY")
    private String PENDING_TODAY;

    @SerializedName("PENDING_MONTH")
    private String PENDING_MONTH;

    @SerializedName("PENDING_YEAR")
    private String PENDING_YEAR;

    @SerializedName("COMPLETED_ALL")
    private String COMPLETED_ALL;

@SerializedName("COMPLETED_TODAY")
    private String COMPLETED_TODAY;

@SerializedName("COMPLETED_MONTH")
    private String COMPLETED_MONTH;

@SerializedName("COMPLETED_YEAR")
    private String COMPLETED_YEAR;



    public NSCMasterModel() {
    }

    public NSCMasterModel(String PENDING_ALL, String PENDING_TODAY, String PENDING_MONTH, String PENDING_YEAR, String COMPLETED_ALL, String COMPLETED_TODAY, String COMPLETED_MONTH, String COMPLETED_YEAR) {
        this.PENDING_ALL = PENDING_ALL;
        this.PENDING_TODAY = PENDING_TODAY;
        this.PENDING_MONTH = PENDING_MONTH;
        this.PENDING_YEAR = PENDING_YEAR;
        this.COMPLETED_ALL = COMPLETED_ALL;
        this.COMPLETED_TODAY = COMPLETED_TODAY;
        this.COMPLETED_MONTH = COMPLETED_MONTH;
        this.COMPLETED_YEAR = COMPLETED_YEAR;
    }

    public String getPENDING_ALL() {
        return PENDING_ALL;
    }

    public void setPENDING_ALL(String PENDING_ALL) {
        this.PENDING_ALL = PENDING_ALL;
    }

    public String getPENDING_TODAY() {
        return PENDING_TODAY;
    }

    public void setPENDING_TODAY(String PENDING_TODAY) {
        this.PENDING_TODAY = PENDING_TODAY;
    }

    public String getPENDING_MONTH() {
        return PENDING_MONTH;
    }

    public void setPENDING_MONTH(String PENDING_MONTH) {
        this.PENDING_MONTH = PENDING_MONTH;
    }

    public String getPENDING_YEAR() {
        return PENDING_YEAR;
    }

    public void setPENDING_YEAR(String PENDING_YEAR) {
        this.PENDING_YEAR = PENDING_YEAR;
    }

    public String getCOMPLETED_ALL() {
        return COMPLETED_ALL;
    }

    public void setCOMPLETED_ALL(String COMPLETED_ALL) {
        this.COMPLETED_ALL = COMPLETED_ALL;
    }

    public String getCOMPLETED_TODAY() {
        return COMPLETED_TODAY;
    }

    public void setCOMPLETED_TODAY(String COMPLETED_TODAY) {
        this.COMPLETED_TODAY = COMPLETED_TODAY;
    }

    public String getCOMPLETED_MONTH() {
        return COMPLETED_MONTH;
    }

    public void setCOMPLETED_MONTH(String COMPLETED_MONTH) {
        this.COMPLETED_MONTH = COMPLETED_MONTH;
    }

    public String getCOMPLETED_YEAR() {
        return COMPLETED_YEAR;
    }

    public void setCOMPLETED_YEAR(String COMPLETED_YEAR) {
        this.COMPLETED_YEAR = COMPLETED_YEAR;
    }

    @Override
    public String toString() {
        return "NSCMasterModel{" +
                "PENDING_ALL='" + PENDING_ALL + '\'' +
                ", PENDING_TODAY='" + PENDING_TODAY + '\'' +
                ", PENDING_MONTH='" + PENDING_MONTH + '\'' +
                ", PENDING_YEAR='" + PENDING_YEAR + '\'' +
                ", COMPLETED_ALL='" + COMPLETED_ALL + '\'' +
                ", COMPLETED_TODAY='" + COMPLETED_TODAY + '\'' +
                ", COMPLETED_MONTH='" + COMPLETED_MONTH + '\'' +
                ", COMPLETED_YEAR='" + COMPLETED_YEAR + '\'' +
                '}';
    }
}
