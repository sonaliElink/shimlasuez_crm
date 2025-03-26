package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class DownloadSourceType extends RealmObject {
    @SerializedName("SOURCECODE")
    private String SOURCECODE;

    @SerializedName("SOURCEDESC")
    private String SOURCEDESC;

    @SerializedName("SOURCETYPE")
    private String SOURCETYPE;

    public DownloadSourceType() {
    }

    public DownloadSourceType(String SOURCECODE, String SOURCEDESC, String SOURCETYPE) {
        this.SOURCECODE = SOURCECODE;
        this.SOURCEDESC = SOURCEDESC;
        this.SOURCETYPE = SOURCETYPE;
    }

    public String getSOURCECODE() {
        return SOURCECODE;
    }

    public String getSOURCEDESC() {
        return SOURCEDESC;
    }

    public String getSOURCETYPE() {
        return SOURCETYPE;
    }

    @Override
    public String toString() {
        return "DownloadSourceType{" +
                "SOURCECODE='" + SOURCECODE + '\'' +
                ", SOURCEDESC='" + SOURCEDESC + '\'' +
                ", SOURCETYPE='" + SOURCETYPE + '\'' +
                '}';
    }
}
