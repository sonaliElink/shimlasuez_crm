package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGMeterSizeModel extends RealmObject {

    @SerializedName("MCS_ID")
    private int  MCS_ID;

    @SerializedName("CONNSIZEMM")
    private String CONNSIZEMM;

    public MMGMeterSizeModel() {
    }

    public MMGMeterSizeModel(int MCS_ID, String CONNSIZEMM) {
        this.MCS_ID = MCS_ID;
        this.CONNSIZEMM = CONNSIZEMM;
    }

    public int getMCS_ID() {
        return MCS_ID;
    }

    public String getCONNSIZEMM() {
        return CONNSIZEMM;
    }

    @Override
    public String toString() {
        return "MMGMeterSizeModel{" +
                "MCS_ID='" + MCS_ID + '\'' +
                ", CONNSIZEMM=" + CONNSIZEMM +
                '}';
    }
}
