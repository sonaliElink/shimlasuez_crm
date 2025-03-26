package elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class FixerDataListModel extends RealmObject {

    @SerializedName("EM_EMP_CODE")
    private String EM_EMP_CODE;

    @SerializedName("NAME")
    private String NAME;

    public FixerDataListModel() {
    }

    public FixerDataListModel(String EM_EMP_CODE, String NAME) {
        this.EM_EMP_CODE = EM_EMP_CODE;
        this.NAME = NAME;
    }

    public String getEM_EMP_CODE() {
        return EM_EMP_CODE;
    }

    public void setEM_EMP_CODE(String EM_EMP_CODE) {
        this.EM_EMP_CODE = EM_EMP_CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return "DownloaddFixerData{" +
                "EM_EMP_CODE='" + EM_EMP_CODE + '\'' +
                ", NAME='" + NAME + '\'' +
                '}';
    }
    }
