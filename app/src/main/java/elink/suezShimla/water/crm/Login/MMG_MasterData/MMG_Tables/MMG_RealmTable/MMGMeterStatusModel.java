package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGMeterStatusModel extends RealmObject {
    @SerializedName("MSM_METERSTATUS_ID")
    private int MSM_METERSTATUS_ID;

    @SerializedName("MSM_METERSTATUS_NAME")
    private String MSM_METERSTATUS_NAME;

    @SerializedName("FINALMETERSTATUSTAG")
    private String FINALMETERSTATUSTAG;


    public MMGMeterStatusModel(int MSM_METERSTATUS_ID, String MSM_METERSTATUS_NAME, String FINALMETERSTATUSTAG) {
        this.MSM_METERSTATUS_ID = MSM_METERSTATUS_ID;
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
        this.FINALMETERSTATUSTAG = FINALMETERSTATUSTAG;
    }

    public int getMSM_METERSTATUS_ID() {
        return MSM_METERSTATUS_ID;
    }

    public void setMSM_METERSTATUS_ID(int MSM_METERSTATUS_ID) {
        this.MSM_METERSTATUS_ID = MSM_METERSTATUS_ID;
    }

    public String getMSM_METERSTATUS_NAME() {
        return MSM_METERSTATUS_NAME;
    }

    public void setMSM_METERSTATUS_NAME(String MSM_METERSTATUS_NAME) {
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
    }

    public String getFINALMETERSTATUSTAG() {
        return FINALMETERSTATUSTAG;
    }

    public void setFINALMETERSTATUSTAG(String FINALMETERSTATUSTAG) {
        this.FINALMETERSTATUSTAG = FINALMETERSTATUSTAG;
    }

    public MMGMeterStatusModel() {
    }

    @Override
    public String toString() {
        return "MMGMeterStatusModel{" +
                "MSM_METERSTATUS_ID=" + MSM_METERSTATUS_ID +
                ", MSM_METERSTATUS_NAME='" + MSM_METERSTATUS_NAME + '\'' +
                ", FINALMETERSTATUSTAG='" + FINALMETERSTATUSTAG + '\'' +
                '}';
    }
}
