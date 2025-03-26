package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadSiteEngineer {

    @SerializedName("EMPLOYEE_CODE")
    private String EMPLOYEE_CODE;

    @SerializedName("EMPLOYEE_NAME")
    private String EMPLOYEE_NAME;

    @SerializedName("EM_ZONE")
    private String EM_ZONE;

    @SerializedName("EM_DEPT")
    private String EM_DEPT;

    public DownloadSiteEngineer() {
    }

    public DownloadSiteEngineer(String EMPLOYEE_CODE, String EMPLOYEE_NAME, String EM_ZONE, String EM_DEPT) {
        this.EMPLOYEE_CODE = EMPLOYEE_CODE;
        this.EMPLOYEE_NAME = EMPLOYEE_NAME;
        this.EM_ZONE = EM_ZONE;
        this.EM_DEPT = EM_DEPT;
    }

    public String getEMPLOYEE_CODE() {
        return EMPLOYEE_CODE;
    }

    public String getEMPLOYEE_NAME() {
        return EMPLOYEE_NAME;
    }

    public String getEM_ZONE() {
        return EM_ZONE;
    }

    public String getEM_DEPT() {
        return EM_DEPT;
    }

    @Override
    public String toString() {
        return "DownloadSiteEngineer{" +
                "EMPLOYEE_CODE='" + EMPLOYEE_CODE + '\'' +
                ", EMPLOYEE_NAME='" + EMPLOYEE_NAME + '\'' +
                ", EM_ZONE='" + EM_ZONE + '\'' +
                ", EM_DEPT='" + EM_DEPT + '\'' +
                '}';
    }
}
