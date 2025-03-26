package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadFinishAction {

    @SerializedName("CSCM_ID")
    private String CSCM_ID;

    @SerializedName("CSCM_SECNAME")
    private String CSCM_SECNAME;

    @SerializedName("FILTER")
    private String FILTER;

    public DownloadFinishAction() {
    }

    public DownloadFinishAction(String CSCM_ID, String CSCM_SECNAME, String FILTER) {
        this.CSCM_ID = CSCM_ID;
        this.CSCM_SECNAME = CSCM_SECNAME;
        this.FILTER = FILTER;
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

    public String getFILTER() {
        return FILTER;
    }

    public void setFILTER(String FILTER) {
        this.FILTER = FILTER;
    }

    @Override
    public String toString() {
        return "DownloadFinishAction{" +
                "CSCM_ID='" + CSCM_ID + '\'' +
                ", CSCM_SECNAME='" + CSCM_SECNAME + '\'' +
                ", FILTER='" + FILTER + '\'' +
                '}';
    }
}
