package elink.suezShimla.water.crm.androidhsc.hscModelTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class LotModel extends RealmObject {
    @SerializedName("MR_ID")
    private String MR_ID;

    @SerializedName("MR_NAME")
    private String MR_NAME;

    @SerializedName("BUID")
    private String BUID;

    @SerializedName("PCID")
    private String PCID;


    public LotModel(String MR_ID, String MR_NAME, String BUID, String PCID) {
        this.MR_ID = MR_ID;
        this.MR_NAME = MR_NAME;
        this.BUID = BUID;
        this.PCID = PCID;
    }

    public LotModel() {
    }

    public String getMR_ID() {
        return MR_ID;
    }

    public void setMR_ID(String MR_ID) {
        this.MR_ID = MR_ID;
    }

    public String getMR_NAME() {
        return MR_NAME;
    }

    public void setMR_NAME(String MR_NAME) {
        this.MR_NAME = MR_NAME;
    }

    public String getBUID() {
        return BUID;
    }

    public void setBUID(String BUID) {
        this.BUID = BUID;
    }

    public String getPCID() {
        return PCID;
    }

    public void setPCID(String PCID) {
        this.PCID = PCID;
    }

    @Override
    public String toString() {
        return "LotModel{" +
                "MR_ID='" + MR_ID + '\'' +
                ", MR_NAME='" + MR_NAME + '\'' +
                ", BUID=" + BUID +
                ", PCID='" + PCID + '\'' +
                '}';
    }
}
