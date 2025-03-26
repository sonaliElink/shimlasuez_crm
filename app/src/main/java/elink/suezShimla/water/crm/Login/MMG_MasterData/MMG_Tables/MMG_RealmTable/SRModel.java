package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SRModel extends RealmObject {
    @SerializedName("SRID")
    private String TRM_ID;

    @SerializedName("SRNAME")
    private String TRM_NAME;

    @SerializedName("MSRID")
    private String MSRID;

    public SRModel() {
    }

    public SRModel(String TRM_NAME) {
        this.TRM_NAME = TRM_NAME;
    }

    public SRModel(String TRM_ID, String TRM_NAME, String MSRID) {
        this.TRM_ID = TRM_ID;
        this.TRM_NAME = TRM_NAME;
        this.MSRID = MSRID;
    }

    public String getTRM_ID() {
        return TRM_ID;
    }

    public void setTRM_ID(String TRM_ID) {
        this.TRM_ID = TRM_ID;
    }

    public String getTRM_NAME() {
        return TRM_NAME;
    }

    public void setTRM_NAME(String TRM_NAME) {
        this.TRM_NAME = TRM_NAME;
    }

    public String getMSRID() {
        return MSRID;
    }

    public void setMSRID(String MSRID) {
        this.MSRID = MSRID;
    }

    @Override
    public String toString() {
        return "SRModel{" +
                "TRM_ID='" + TRM_ID + '\'' +
                ", TRM_NAME='" + TRM_NAME + '\'' +
                ", MSRID='" + MSRID + '\'' +
                '}';
    }
}
