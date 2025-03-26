package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadComplaintBy {
    @SerializedName("VCM_CATCD")
    private String VCM_CATCD;

    @SerializedName("VCM_CATNAME")
    private String VCM_CATNAME;

    public DownloadComplaintBy() {
    }

    public DownloadComplaintBy(String VCM_CATCD, String VCM_CATNAME) {
        this.VCM_CATCD = VCM_CATCD;
        this.VCM_CATNAME = VCM_CATNAME;
    }

    public String getVCM_CATCD() {
        return VCM_CATCD;
    }

    public void setVCM_CATCD(String VCM_CATCD) {
        this.VCM_CATCD = VCM_CATCD;
    }

    public String getVCM_CATNAME() {
        return VCM_CATNAME;
    }

    public void setVCM_CATNAME(String VCM_CATNAME) {
        this.VCM_CATNAME = VCM_CATNAME;
    }

    @Override
    public String toString() {
        return "DownloadComplaintBy{" +
                "VCM_CATCD='" + VCM_CATCD + '\'' +
                ", VCM_CATNAME='" + VCM_CATNAME + '\'' +
                '}';
    }
}
