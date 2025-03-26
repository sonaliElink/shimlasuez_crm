package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadSRModel {
    @SerializedName("TRM_ID")
    private int TRM_ID;

    @SerializedName("TRM_NAME")
    private String TRM_NAME;

    public DownloadSRModel(String TRM_NAME) {
        this.TRM_NAME = TRM_NAME;
    }

    public DownloadSRModel(int TRM_ID, String TRM_NAME) {
        this.TRM_ID = TRM_ID;
        this.TRM_NAME = TRM_NAME;
    }

    public int getTRM_ID() {
        return TRM_ID;
    }

    public void setTRM_ID(int TRM_ID) {
        this.TRM_ID = TRM_ID;
    }

    public String getTRM_NAME() {
        return TRM_NAME;
    }

    public void setTRM_NAME(String TRM_NAME) {
        this.TRM_NAME = TRM_NAME;
    }

    @Override
    public String toString() {
        return  TRM_NAME;
    }
}
