package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadZone {

    @SerializedName("BU_NAME")
    private String BU_NAME;

    @SerializedName("BUM_BU_ID")
    private String BUM_BU_ID;

    public DownloadZone() {
    }

    public DownloadZone(String BU_NAME, String BUM_BU_ID) {
        this.BU_NAME = BU_NAME;
        this.BUM_BU_ID = BUM_BU_ID;
    }

    public String getBU_NAME() {
        return BU_NAME;
    }

    public String getBUM_BU_ID() {
        return BUM_BU_ID;
    }

    @Override
    public String toString() {
        return "DownloadZone{" +
                "BU_NAME='" + BU_NAME + '\'' +
                ", BUM_BU_ID='" + BUM_BU_ID + '\'' +
                '}';
    }
}
