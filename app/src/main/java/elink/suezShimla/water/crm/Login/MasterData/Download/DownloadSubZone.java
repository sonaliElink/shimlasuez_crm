package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadSubZone {

    @SerializedName("PCM_PC_ID")
    private String PCM_PC_ID;

    @SerializedName("PCM_PC_NAME")
    private String PCM_PC_NAME;

    @SerializedName("PCM_BU_ID")
    private String PCM_BU_ID;

    public DownloadSubZone() {
    }

    public DownloadSubZone(String PCM_PC_ID, String PCM_PC_NAME, String PCM_BU_ID) {
        this.PCM_PC_ID = PCM_PC_ID;
        this.PCM_PC_NAME = PCM_PC_NAME;
        this.PCM_BU_ID = PCM_BU_ID;
    }

    public String getPCM_PC_ID() {
        return PCM_PC_ID;
    }

    public String getPCM_PC_NAME() {
        return PCM_PC_NAME;
    }

    public String getPCM_BU_ID() {
        return PCM_BU_ID;
    }

    @Override
    public String toString() {
        return "DownloadSubZone{" +
                "PCM_PC_ID='" + PCM_PC_ID + '\'' +
                ", PCM_PC_NAME='" + PCM_PC_NAME + '\'' +
                ", PCM_BU_ID='" + PCM_BU_ID + '\'' +
                '}';
    }
}
