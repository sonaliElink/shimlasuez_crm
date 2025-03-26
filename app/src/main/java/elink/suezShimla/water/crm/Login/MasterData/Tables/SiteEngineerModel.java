package elink.suezShimla.water.crm.Login.MasterData.Tables;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SiteEngineerModel extends RealmObject {

    @SerializedName("EMPLOYEE_CODE")
    private String EMPLOYEE_CODE;

    @SerializedName("EMPLOYEE_NAME")
    private String EMPLOYEE_NAME;

    @SerializedName("isChecked")
    private boolean isChecked;

    @SerializedName("EM_ZONE")
    private String EM_ZONE;

    @SerializedName("EM_DEPT")
    private String EM_DEPT;

    public SiteEngineerModel() {
    }

//    public SiteEngineerModel(String EMPLOYEE_CODE, String EMPLOYEE_NAME, boolean isChecked) {
//        this.EMPLOYEE_CODE = EMPLOYEE_CODE;
//        this.EMPLOYEE_NAME = EMPLOYEE_NAME;
//        this.isChecked = isChecked;
//    }


    public SiteEngineerModel(String EMPLOYEE_CODE, String EMPLOYEE_NAME, boolean isChecked, String EM_ZONE, String EM_DEPT) {
        this.EMPLOYEE_CODE = EMPLOYEE_CODE;
        this.EMPLOYEE_NAME = EMPLOYEE_NAME;
        this.isChecked = isChecked;
        this.EM_ZONE = EM_ZONE;
        this.EM_DEPT = EM_DEPT;
    }

    public String getEMPLOYEE_CODE() {
        return EMPLOYEE_CODE;
    }

    public void setEMPLOYEE_CODE(String EMPLOYEE_CODE) {
        this.EMPLOYEE_CODE = EMPLOYEE_CODE;
    }

    public String getEMPLOYEE_NAME() {
        return EMPLOYEE_NAME;
    }

    public void setEMPLOYEE_NAME(String EMPLOYEE_NAME) {
        this.EMPLOYEE_NAME = EMPLOYEE_NAME;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getEM_ZONE() {
        return EM_ZONE;
    }

    public String getEM_DEPT() {
        return EM_DEPT;
    }


    @Override
    public String toString() {
        return "SiteEngineerModel{" +
                "EMPLOYEE_CODE='" + EMPLOYEE_CODE + '\'' +
                ", EMPLOYEE_NAME='" + EMPLOYEE_NAME + '\'' +
                ", isChecked=" + isChecked +
                ", EM_ZONE='" + EM_ZONE + '\'' +
                ", EM_DEPT='" + EM_DEPT + '\'' +
                '}';
    }
}
