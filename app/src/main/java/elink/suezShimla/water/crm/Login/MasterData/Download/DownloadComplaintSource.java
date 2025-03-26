package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadComplaintSource {

    @SerializedName("CSM_SOURCECODE")
    private String CSM_SOURCECODE;

    @SerializedName("CSM_SOURCEDESC")
    private String CSM_SOURCEDESC;

    public DownloadComplaintSource() {
    }

    public DownloadComplaintSource(String CSM_SOURCECODE, String CSM_SOURCEDESC) {
        this.CSM_SOURCECODE = CSM_SOURCECODE;
        this.CSM_SOURCEDESC = CSM_SOURCEDESC;
    }

    public String getCSM_SOURCECODE() {
        return CSM_SOURCECODE;
    }

    public String getCSM_SOURCEDESC() {
        return CSM_SOURCEDESC;
    }

    @Override
    public String toString() {
        return "DownloadComplaintSource{" +
                "CSM_SOURCECODE='" + CSM_SOURCECODE + '\'' +
                ", CSM_SOURCEDESC='" + CSM_SOURCEDESC + '\'' +
                '}';
    }
}