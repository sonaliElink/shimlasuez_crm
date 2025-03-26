package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MStatusObservationModel extends RealmObject {
    @SerializedName("MSNM_MSTATUS_ID")
    String MSNM_MSTATUS_ID;

    @SerializedName("MSNM_MSUBSTATUS_ID")
    String MSNM_MSUBSTATUS_ID;

    @SerializedName("MSNM_MSUBSTATUS_NAME")
    String MSNM_MSUBSTATUS_NAME;

    public MStatusObservationModel() {
    }

    public MStatusObservationModel(String MSNM_MSTATUS_ID, String MSNM_MSUBSTATUS_ID, String MSNM_MSUBSTATUS_NAME) {
        this.MSNM_MSTATUS_ID = MSNM_MSTATUS_ID;
        this.MSNM_MSUBSTATUS_ID = MSNM_MSUBSTATUS_ID;
        this.MSNM_MSUBSTATUS_NAME = MSNM_MSUBSTATUS_NAME;
    }

    public String getMSNM_MSTATUS_ID() {
        return MSNM_MSTATUS_ID;
    }

    public String getMSNM_MSUBSTATUS_ID() {
        return MSNM_MSUBSTATUS_ID;
    }

    public String getMSNM_MSUBSTATUS_NAME() {
        return MSNM_MSUBSTATUS_NAME;
    }

    @Override
    public String toString() {
        return "MStatusObservationModel{" +
                "MSNM_MSTATUS_ID='" + MSNM_MSTATUS_ID + '\'' +
                ", MSNM_MSUBSTATUS_ID='" + MSNM_MSUBSTATUS_ID + '\'' +
                ", MSNM_MSUBSTATUS_NAME='" + MSNM_MSUBSTATUS_NAME + '\'' +
                '}';
    }
}
