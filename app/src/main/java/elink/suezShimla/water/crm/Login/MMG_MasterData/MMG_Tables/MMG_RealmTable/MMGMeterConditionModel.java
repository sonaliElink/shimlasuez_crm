package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGMeterConditionModel extends RealmObject {

    @SerializedName("MSM_METERSTATUS_ID")
    private int MSM_METERSTATUS_ID;

    @SerializedName("MSM_METERSTATUS_NAME")
    private String MSM_METERSTATUS_NAME;

    public MMGMeterConditionModel() {
    }

    public MMGMeterConditionModel(int MSM_METERSTATUS_ID, String VALUE) {
        this.MSM_METERSTATUS_ID = MSM_METERSTATUS_ID;
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
    }

    public int getMSM_METERSTATUS_ID() {
        return MSM_METERSTATUS_ID;
    }

    public String getMSM_METERSTATUS_NAME() {
        return MSM_METERSTATUS_NAME;
    }

    @Override
    public String toString() {
        return "MMGMeterConditionModel{" +
                "MSM_METERSTATUS_ID='" + MSM_METERSTATUS_ID + '\'' +
                ", MSM_METERSTATUS_NAME=" + MSM_METERSTATUS_NAME +
                '}';
    }
}
