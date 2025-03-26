package elink.suezShimla.water.crm.Login.MasterData.Tables;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class FinishActionModel extends RealmObject {

    @SerializedName("CSCM_ID")
    private int CSCM_ID;

    @SerializedName("CSCM_SECNAME")
    private String CSCM_SECNAME;

    @SerializedName("FILTER")
    private String FILTER;

    public FinishActionModel() {
    }

    public FinishActionModel(int CSCM_ID, String CSCM_SECNAME, String FILTER) {
        this.CSCM_ID = CSCM_ID;
        this.CSCM_SECNAME = CSCM_SECNAME;
        this.FILTER = FILTER;
    }

    public int getCSCM_ID() {
        return CSCM_ID;
    }

    public void setCSCM_ID(int CSCM_ID) {
        this.CSCM_ID = CSCM_ID;
    }

    public String getCSCM_SECNAME() {
        return CSCM_SECNAME;
    }

    public void setCSCM_SECNAME(String CSCM_SECNAME) {
        this.CSCM_SECNAME = CSCM_SECNAME;
    }

    public String getFILTER() {
        return FILTER;
    }

    public void setFILTER(String FILTER) {
        this.FILTER = FILTER;
    }

    @Override
    public String toString() {
        return "FinishActionModel{" +
                "CSCM_ID=" + CSCM_ID +
                ", CSCM_SECNAME='" + CSCM_SECNAME + '\'' +
                ", FILTER='" + FILTER + '\'' +
                '}';
    }
}
