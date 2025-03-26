package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGContEmpModel extends RealmObject {
    @SerializedName("EM_EMP_CODE")
    private String EM_EMP_CODE;

    @SerializedName("NAME")
    private String NAME;

    @SerializedName("EM_EMAIL")
    private String EM_EMAIL;

    @SerializedName("EM_PHONEM")
    private String EM_PHONEM;

    @SerializedName("EM_DESIGNATION")
    private String EM_DESIGNATION;


    @SerializedName("DGM_DES_NAME")
    private String DGM_DES_NAME;

    @SerializedName("EM_VENDOR_ID")
    private String EM_VENDOR_ID;


    public MMGContEmpModel(String EM_EMP_CODE, String NAME, String EM_EMAIL, String EM_PHONEM, String EM_DESIGNATION, String DGM_DES_NAME,String EM_VENDOR_ID) {
        this.EM_EMP_CODE = EM_EMP_CODE;
        this.NAME = NAME;
        this.EM_EMAIL = EM_EMAIL;
        this.EM_PHONEM = EM_PHONEM;
        this.EM_DESIGNATION = EM_DESIGNATION;
        this.DGM_DES_NAME = DGM_DES_NAME;
        this.EM_VENDOR_ID = EM_VENDOR_ID;
    }

    public MMGContEmpModel() {
    }

    public String getEM_VENDOR_ID() {
        return EM_VENDOR_ID;
    }

    public void setEM_VENDOR_ID(String EM_VENDOR_ID) {
        this.EM_VENDOR_ID = EM_VENDOR_ID;
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

    public String getEM_EMAIL() {
        return EM_EMAIL;
    }

    public void setEM_EMAIL(String EM_EMAIL) {
        this.EM_EMAIL = EM_EMAIL;
    }

    public String getEM_PHONEM() {
        return EM_PHONEM;
    }

    public void setEM_PHONEM(String EM_PHONEM) {
        this.EM_PHONEM = EM_PHONEM;
    }

    public String getEM_DESIGNATION() {
        return EM_DESIGNATION;
    }

    public void setEM_DESIGNATION(String EM_DESIGNATION) {
        this.EM_DESIGNATION = EM_DESIGNATION;
    }

    public String getDGM_DES_NAME() {
        return DGM_DES_NAME;
    }

    public void setDGM_DES_NAME(String DGM_DES_NAME) {
        this.DGM_DES_NAME = DGM_DES_NAME;
    }

    @Override
    public String toString() {
        return "MMGContEmpModel{" +
                "EM_EMP_CODE ='" + EM_EMP_CODE  + '\'' +
                ", NAME=" + NAME +
                ", EM_EMAIL=" + EM_EMAIL +
                ", EM_PHONEM=" + EM_PHONEM +
                ", EM_DESIGNATION=" + EM_DESIGNATION +
                ", DGM_DES_NAME=" + DGM_DES_NAME +
                ", EM_VENDOR_ID=" + EM_VENDOR_ID +
                '}';
    }
}
