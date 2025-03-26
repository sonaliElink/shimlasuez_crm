package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGRampAndRRModel extends RealmObject {

    @SerializedName("RRR_ID")
    private int RRR_ID;

    @SerializedName("RRR_DESC")
    private String RRR_DESC;

    public MMGRampAndRRModel() {
    }

    public MMGRampAndRRModel(int RRR_ID, String RRR_DESC) {
        this.RRR_ID = RRR_ID;
        this.RRR_DESC = RRR_DESC;
    }

    public int getRRR_ID() {
        return RRR_ID;
    }

    public String getRRR_DESC() {
        return RRR_DESC;
    }

    @Override
    public String toString() {
        return "MMGRampAndRRModel{" +
                "RRR_ID='" + RRR_ID + '\'' +
                ", RRR_DESC=" + RRR_DESC +
                '}';
    }
}
