package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MeterStatusModel extends RealmObject {

    @SerializedName("MSM_METERSTATUS_ID")
    private String MSM_METERSTATUS_ID;

    @SerializedName("MSM_METERSTATUS_NAME")
    private String MSM_METERSTATUS_NAME;

    @SerializedName("BILLEDID")
    private String BILLEDID;


    public MeterStatusModel() {
    }

    public MeterStatusModel(String MSM_METERSTATUS_ID, String MSM_METERSTATUS_NAME, String BILLEDID) {
        this.MSM_METERSTATUS_ID = MSM_METERSTATUS_ID;
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
        this.BILLEDID = BILLEDID;
    }

    public String getMSM_METERSTATUS_ID() {
        return MSM_METERSTATUS_ID;
    }

    public void setMSM_METERSTATUS_ID(String MSM_METERSTATUS_ID) {
        this.MSM_METERSTATUS_ID = MSM_METERSTATUS_ID;
    }

    public String getMSM_METERSTATUS_NAME() {
        return MSM_METERSTATUS_NAME;
    }

    public void setMSM_METERSTATUS_NAME(String MSM_METERSTATUS_NAME) {
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
    }

    public String getBILLEDID() {
        return BILLEDID;
    }

    public void setBILLEDID(String BILLEDID) {
        this.BILLEDID = BILLEDID;
    }


    @Override
    public String toString() {
        return "MeterStatusModel{" +
                "MSM_METERSTATUS_ID='" + MSM_METERSTATUS_ID + '\'' +
                ", MSM_METERSTATUS_NAME='" + MSM_METERSTATUS_NAME + '\'' +
                ", BILLEDID='" + BILLEDID + '\'' +

                '}';
    }
}
