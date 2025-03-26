package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGSaddleAndPitExcavModel extends RealmObject {

    @SerializedName("EC_ID")
    private int EC_ID;

    @SerializedName("EC_DESC")
    private String EC_DESC;

    public MMGSaddleAndPitExcavModel() {
    }

    public MMGSaddleAndPitExcavModel(int EC_ID, String EC_DESC) {
        this.EC_ID = EC_ID;
        this.EC_DESC = EC_DESC;
    }

    public int getEC_ID() {
        return EC_ID;
    }

    public String getEC_DESC() {
        return EC_DESC;
    }

    @Override
    public String toString() {
        return "MMGSaddleAndPitExcavModel{" +
                "EC_ID='" + EC_ID + '\'' +
                ", EC_DESC=" + EC_DESC +
                '}';
    }
}
