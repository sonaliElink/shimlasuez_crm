package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGZoneModel extends RealmObject {

    @SerializedName("BU_NAME")
    private String BU_NAME;

    @SerializedName("BUM_BU_ID")
    private int BUM_BU_ID;

    public MMGZoneModel() {
    }

    public MMGZoneModel(String BU_NAME, int BUM_BU_ID) {
        this.BU_NAME = BU_NAME;
        this.BUM_BU_ID = BUM_BU_ID;
    }

    public String getBU_NAME() {
        return BU_NAME;
    }

    public int getBUM_BU_ID() {
        return BUM_BU_ID;
    }

    @Override
    public String toString()
    {
        return "MMGZoneModel{" +
                "BU_NAME='" + BU_NAME + '\'' +
                ", BUM_BU_ID=" + BUM_BU_ID +
                '}';
    }
}
