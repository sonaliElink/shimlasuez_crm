package elink.suezShimla.water.crm.Login.MasterData.Tables;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ComplaintSourceModel extends RealmObject {

    @SerializedName("CSM_SOURCECODE")
    private int CSM_SOURCECODE;

    @SerializedName("CSM_SOURCEDESC")
    private String CSM_SOURCEDESC;

    @SerializedName("CSM_SOURCE_TYPE")
    private String CSM_SOURCE_TYPE;


    public ComplaintSourceModel() {
    }

    public ComplaintSourceModel(int CSM_SOURCECODE, String CSM_SOURCEDESC) {
        this.CSM_SOURCECODE = CSM_SOURCECODE;
        this.CSM_SOURCEDESC = CSM_SOURCEDESC;
    }

    public ComplaintSourceModel(int CSM_SOURCECODE, String CSM_SOURCEDESC, String CSM_SOURCE_TYPE) {
        this.CSM_SOURCECODE = CSM_SOURCECODE;
        this.CSM_SOURCEDESC = CSM_SOURCEDESC;
        this.CSM_SOURCE_TYPE = CSM_SOURCE_TYPE;
    }

    public int getCSM_SOURCECODE() {
        return CSM_SOURCECODE;
    }

    public String getCSM_SOURCEDESC() {
        return CSM_SOURCEDESC;
    }

    public String getCSM_SOURCE_TYPE() {
        return CSM_SOURCE_TYPE;
    }

    @Override
    public String toString() {
        return "ComplaintSourceModel{" +
                "CSM_SOURCECODE=" + CSM_SOURCECODE +
                ", CSM_SOURCEDESC='" + CSM_SOURCEDESC + '\'' +
                ", CSM_SOURCE_TYPE='" + CSM_SOURCE_TYPE + '\'' +
                '}';
    }
}