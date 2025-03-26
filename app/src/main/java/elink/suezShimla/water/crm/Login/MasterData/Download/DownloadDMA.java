package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadDMA {
    @SerializedName("PM_ID")
    private String PM_ID;

    @SerializedName("PM_NAME")
    private String PM_NAME;

    public DownloadDMA() {
    }

    public DownloadDMA(String PM_ID, String PM_NAME) {
        this.PM_ID = PM_ID;
        this.PM_NAME = PM_NAME;
    }

    public String getPM_ID() {
        return PM_ID;
    }

    public String getPM_NAME() {
        return PM_NAME;
    }

    @Override
    public String toString() {
        return "DownloadDMA{" +
                "PM_ID='" + PM_ID + '\'' +
                ", PM_NAME='" + PM_NAME + '\'' +
                '}';
    }
}
